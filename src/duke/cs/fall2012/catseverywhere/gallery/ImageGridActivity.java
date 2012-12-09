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

public abstract class ImageGridActivity extends Activity implements
		OnClickListener {

	protected GridView gridView;
	private ImageGridAdapter adapter;
	private String[] imageUrls;
	protected InputStream is;
	protected ImageButton uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav;

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

	public abstract void initialize();
	
	public abstract HttpPost setHttpPost();

	public String[] getImagePathsFromDb() {
		// UPDATE TO PULL FROM DB
		// http post
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

		// parse json data
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

	// used to implement nav bar
	public abstract void onClick(View v);

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