package duke.cs.fall2012.catseverywhere.gallery;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;


/**
 * Implements functionality common to all galleries in the grid style and outlines
 * what methods are required to be implemented when creating a new grid gallery. 
 *
 */
public abstract class ImageGridActivity extends Activity implements
		OnClickListener {

	protected GridView gridView;
	private ImageGridAdapter adapter;
	private String[] imageUrls;
	protected InputStream is;
	protected ImageButton uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav;

	/**
	 * Initializes the layout for components common to all grid galleries and creates the 
	 * ImageGridAdapter.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageUrls = getImagePathsFromDb();
		initialize();
		adapter = new ImageGridAdapter(this, imageUrls);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startImageGalleryActivity(position);
			}
		});
	}

	/**
	 * Initializes the navigation bar at the bottom of each gallery. Must be different for
	 * every gallery because even though the buttons have the same functionality in every gallery,
	 * in each XML files, they must have unique names. Therefore, this method must be implemented in every 
	 * ImageGridActivity subclass rather than in this class. 
	 */
	public abstract void initialize();
	
	/**
	 * @return an HttpPost that can be executed to get image paths from a developer-specified 
	 *  MYSQL database.
	 */
	public abstract HttpPost setHttpPost();
	
	/**
	 * Sets the functionality of the buttons in the navigation bar of the gallery. Like initialize(),
	 * this must be different for every gallery because even though the buttons have the same
	 * functionality in every gallery, in each XML files, they must have unique names. Therefore,
	 * this method must be implemented in every ImageGridActivity subclass rather than in this class.
	 */
	public abstract void onClick(View v);

	/**
	 * @return an array of strings containing the URLs indicating where the photos are being stored 
	 * on the webserver.
	 */
	public String[] getImagePathsFromDb() {
		// Get and execute the HttpPost
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httppost = setHttpPost();
			
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

		// parse JSON data
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(result);
			String[] sArray = new String[jArray.length()];
			for (int i = 0; i < jArray.length(); i++) {
				sArray[i] = "http://squashysquash.com/CatsEverywhere/"
						+ jArray.getString(i);
			}
			return sArray;
		} catch (JSONException e3) {
			Log.e("log_tag", "Error parsing data " + e3.toString());
		}
		return null;
	}

	/**
	 * Starts the activity that displays the gallery which displays pictures in full screen
	 * @param position references which photo should be initially opened based on which thumbnail
	 * was tapped
	 */
	private void startImageGalleryActivity(int position) {
		Intent i = new Intent(this, ImageGalleryActivity.class);
		i.putExtra("IMAGES", imageUrls);
		i.putExtra("IMAGE_POSITION", position);
		startActivity(i);
	}

	@Override
	public void onDestroy() {
		gridView.setAdapter(null);
		super.onDestroy();
	}

}