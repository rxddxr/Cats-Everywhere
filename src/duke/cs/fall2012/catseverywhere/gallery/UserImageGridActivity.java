package duke.cs.fall2012.catseverywhere.gallery;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import duke.cs.fall2012.catseverywhere.GoogleMapsActivity;
import duke.cs.fall2012.catseverywhere.ImageUpload;
import duke.cs.fall2012.catseverywhere.MyApplication;
import duke.cs.fall2012.catseverywhere.Preferences;
import duke.cs.fall2012.catseverywhere.R;

public class UserImageGridActivity extends Activity implements OnClickListener {

	private GridView gridView;
    private ImageGridAdapter adapter;
    private String[] imageUrls;
    private InputStream is;
    private ImageButton uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav;
    private MyApplication myApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_image_grid);
        myApp = (MyApplication) this.getApplication();
        imageUrls = getImagePathsFromDb();
        System.out.println("WOO" + imageUrls);
        gridView=(GridView)findViewById(R.id.userGridView);
        initialize();
        adapter=new ImageGridAdapter(this, imageUrls);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		startImageGalleryActivity(position);
        	}
        });
        
    }
    
    public void initialize() {
		//nav bar
    	uploadButtonNav = (ImageButton) findViewById(R.id.bUserGridUploadNav);
    	uploadButtonNav.setOnClickListener(this);
    	galleryButtonNav = (ImageButton) findViewById(R.id.bUserGridGalleryNav);
    	galleryButtonNav.setOnClickListener(this);
    	mapsButtonNav = (ImageButton) findViewById(R.id.bUserGridMapsNav);
    	mapsButtonNav.setOnClickListener(this);
    	prefButtonNav = (ImageButton) findViewById(R.id.bUserGridPrefNav);
    	prefButtonNav.setOnClickListener(this);
	}
    
    private void startImageGalleryActivity(int position) {
    	Intent i = new Intent(this, ImageGalleryActivity.class);
    	i.putExtra("IMAGES", imageUrls);
    	i.putExtra("IMAGE_POSITION", position);
    	startActivity(i);
    }
    
    @Override
    public void onDestroy()
    {
        gridView.setAdapter(null);
        super.onDestroy();
    }
    
    public String[] getImagePathsFromDb() {
		// UPDATE TO PULL FROM DB
		// http post
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://squashysquash.com/CatsEverywhere/getUserImages.php");
			MultipartEntity reqEntity = new MultipartEntity();
			String user = myApp.getUser();
			reqEntity.addPart("user", new StringBody(user));
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e1) {
			Log.e("log_tag", "Error in http connection " + e1.toString());
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e2) {
			Log.e("log_tag", "Error converting result " + e2.toString());
		}

		// parse json data
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(result);
			String[] sArray = new String[jArray.length()];
			for (int i = 0; i < jArray.length(); i++) {
				sArray[i] = "http://squashysquash.com/CatsEverywhere/" + jArray.getString(i);
			}
			return sArray;
		} catch (JSONException e3) {
			Log.e("log_tag", "Error parsing data " + e3.toString());
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		//navbar
		case R.id.bUserGridUploadNav:
			startActivity(new Intent(this, ImageUpload.class));
			break;
		case R.id.bUserGridGalleryNav:
			startActivity(new Intent(this, ImageGridActivity.class));
			break;
		case R.id.bUserGridMapsNav:
			startActivity(new Intent(this, GoogleMapsActivity.class));
			break;
		case R.id.bUserGridPrefNav:
			startActivity(new Intent(this, Preferences.class));
			break;
		}
	}
	
}