package duke.cs.fall2012.catseverywhere;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import duke.cs.fall2012.catseverywhere.gallery.NormalImageGridActivity;

/**
 * Class used to upload pictures to a webserver using multipart entities. Image information, and image
 * paths are also uploaded to a developer-specified MYSQL database.
 *
 */


public class ImageUpload extends Activity implements OnClickListener{
	private static final int PICK_IMAGE = 1;
	private static final int CAMERA_DATA = 0;
	private ImageView imgView;
	private Button upload;
	private ImageButton camera;
	private ImageButton gallery;
	private EditText caption;
	private Bitmap bitmap;
	private ProgressDialog dialog;
	private HttpEntity myResEntity;
	//private TextView tv, res;
	private String filePath;
	private Uri mCapturedImageURI;
	private ExifInterface myExifInterface;
	
	private MyApplication myApp;
	
	private final String LOC_SEPARATOR = "A";

	
	//nav bar
	private ImageButton uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav;
	

	/** Called when the activity is first created.
	 * Buttons are set up */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageupload);
		imgView = (ImageView) findViewById(R.id.ImageView);
		upload = (Button) findViewById(R.id.Upload);
		camera = (ImageButton) findViewById(R.id.camera);
		gallery = (ImageButton) findViewById(R.id.bGallery);
		caption = (EditText) findViewById(R.id.Caption);
		initialize();
		
		upload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bitmap == null) {
					Toast.makeText(getApplicationContext(),
							"Please select image", Toast.LENGTH_SHORT).show();
				} else {
					dialog = ProgressDialog.show(ImageUpload.this, "Uploading",
							"Please wait...", true);
					// new ImageUploadTask().execute();
					new Thread(new Runnable() {
						@Override
						public void run() {
							doFileUpload(filePath);
						}
					}).start();
				}
			}
		});
		
		camera.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String fileName = "temp.jpg";  
		        ContentValues values = new ContentValues();  
		        values.put(MediaColumns.TITLE, fileName);  
		        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);  

		        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);  
		        startActivityForResult(intent, CAMERA_DATA);
			}
		});
		
		gallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(
							Intent.createChooser(intent, "Select Picture"),
							PICK_IMAGE);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
		});

	}
	
	public void initialize() {
		//nav bar
    	uploadButtonNav = (ImageButton) findViewById(R.id.bUploadUploadNav);
    	uploadButtonNav.setOnClickListener(this);
    	galleryButtonNav = (ImageButton) findViewById(R.id.bUploadGalleryNav);
    	galleryButtonNav.setOnClickListener(this);
    	mapsButtonNav = (ImageButton) findViewById(R.id.bUploadMapsNav);
    	mapsButtonNav.setOnClickListener(this);
    	prefButtonNav = (ImageButton) findViewById(R.id.bUploadPrefNav);
    	prefButtonNav.setOnClickListener(this);
    	
    	//myApp
    	myApp = (MyApplication) this.getApplication();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 Get image path of selected picture form gallery or picture taken with the phone's
	 built-in camera.
	 */
	@Override
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PICK_IMAGE:
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImageUri = data.getData();
				// String filePath = null;

				try {
					// OI FILE Manager
					String filemanagerstring = selectedImageUri.getPath();

					// MEDIA GALLERY
					String selectedImagePath = getPath(selectedImageUri);
					if (selectedImagePath != null) {
						filePath = selectedImagePath;
					} else if (filemanagerstring != null) {
						filePath = filemanagerstring;
					} else {
						Toast.makeText(getApplicationContext(), "Unknown path",
								Toast.LENGTH_LONG).show();
						Log.e("Bitmap", "Unknown path");
					}

					if (filePath != null) {
						decodeFile(filePath);
					} else {
						bitmap = null;
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Internal error",
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
			break;
		case CAMERA_DATA:
			if (resultCode == Activity.RESULT_OK) {

				try {					
					String selectedImagePath = getPath(mCapturedImageURI);

					if (selectedImagePath != null) {
						filePath = selectedImagePath;
					} else {
						Toast.makeText(getApplicationContext(), "Unknown path",
								Toast.LENGTH_LONG).show();
						Log.e("Bitmap", "Unknown path");
					}

					if (filePath != null) {
						decodeFile(filePath);
					} else {
						bitmap = null;
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Internal error",
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
			break;
		default:
		}
	}

	/**
	 * 
	 *Get a file path as a string given a URI
	 */
	public String getPath(Uri uri) {
		String[] projection = { MediaColumns.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	/**
	 * Decode image file given a string with its path
	 *
	 */
	
	public void decodeFile(String filePath) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 1024;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		bitmap = BitmapFactory.decodeFile(filePath, o2);

		imgView.setImageBitmap(bitmap);

	}

	/**
	 * Given a file path upload the image along with it's location, unique id,
	 * keywords and owner.
	 * @param filePath
	 */
	private void doFileUpload(String filePath) {

		File file1 = new File(filePath);
		float[] myPicLoc = new float[2];
		

		String urlString = "http://squashysquash.com/CatsEverywhere/uploadfile.php";
		try {
			myExifInterface = new ExifInterface(filePath);
			myExifInterface.getLatLong(myPicLoc);
			String myPicLatLong = "" + myPicLoc[0]+ LOC_SEPARATOR + myPicLoc[1];
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlString);
			HttpContext localContext = new BasicHttpContext();
			FileBody bin1 = new FileBody(file1);
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("uploadedFile", bin1);
			reqEntity.addPart("id", new StringBody(getId(file1)));
			reqEntity.addPart("keywords", new StringBody(caption.getText().toString()));
			reqEntity.addPart("location", new StringBody(myPicLatLong));
			
			//add owner
			String owner = myApp.getUser();
			reqEntity.addPart("owner", new StringBody(owner));
			post.setEntity(reqEntity);
			HttpResponse response = client.execute(post, localContext);
			myResEntity = response.getEntity();

			final String response_str = EntityUtils.toString(myResEntity);
			if (myResEntity != null) {
				Log.i("RESPONSE", response_str);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							Toast.makeText(
									getApplicationContext(),
									"Upload Complete. Check the server uploads directory.",
									Toast.LENGTH_LONG).show();
							dialog.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (Exception ex) {
			Log.e("Debug", "error: " + ex.getMessage(), ex);
		}
	}
	
	/*
	 * Create an id for photo to use as id in database. uses hashcode to
	 * create unique id for each file
	 */
	public String getId(File photo) {
		
		return Integer.toString(photo.hashCode());
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {

		//navbar
		case R.id.bUploadUploadNav:
			startActivity(new Intent(this, ImageUpload.class));
			break;
		case R.id.bUploadGalleryNav:
			startActivity(new Intent(this, NormalImageGridActivity.class));
			break;
		case R.id.bUploadMapsNav:
			startActivity(new Intent(this, GoogleMapsActivity.class));
			break;
		case R.id.bUploadPrefNav:
			startActivity(new Intent(this, Preferences.class));
			break;
		}
	}
}
