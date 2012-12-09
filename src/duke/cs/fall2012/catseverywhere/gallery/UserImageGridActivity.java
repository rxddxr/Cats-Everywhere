package duke.cs.fall2012.catseverywhere.gallery;

import java.io.BufferedReader;
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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import duke.cs.fall2012.catseverywhere.GoogleMapsActivity;
import duke.cs.fall2012.catseverywhere.ImageUpload;
import duke.cs.fall2012.catseverywhere.MyApplication;
import duke.cs.fall2012.catseverywhere.Preferences;
import duke.cs.fall2012.catseverywhere.R;

public class UserImageGridActivity extends ImageGridActivity {
	
    private MyApplication myApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.user_image_grid);
        gridView=(GridView)findViewById(R.id.userGridView);
        myApp = (MyApplication) this.getApplication();
        super.onCreate(savedInstanceState);
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
		switch(v.getId()) {

		//navbar
		case R.id.bUserGridUploadNav:
			startActivity(new Intent(this, ImageUpload.class));
			break;
		case R.id.bUserGridGalleryNav:
			startActivity(new Intent(this, NormalImageGridActivity.class));
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
