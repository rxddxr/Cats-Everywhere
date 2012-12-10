package duke.cs.fall2012.catseverywhere.gallery;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.content.Intent;
import android.os.Bundle;
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

	/**
     * Initializes the layout of the activity for components specific to this grid gallery
     * followed by a call to the superclass' onCreate()
     */
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.user_image_grid);
		gridView = (GridView) findViewById(R.id.userGridView);
		myApp = (MyApplication) this.getApplication();
		super.onCreate(savedInstanceState);
	}

	/**
	 * Initializes the navigation bar at the bottom of the gallery.
	 */
	public void initialize() {
		// nav bar
		uploadButtonNav = (ImageButton) findViewById(R.id.bUserGridUploadNav);
		uploadButtonNav.setOnClickListener(this);
		galleryButtonNav = (ImageButton) findViewById(R.id.bUserGridGalleryNav);
		galleryButtonNav.setOnClickListener(this);
		mapsButtonNav = (ImageButton) findViewById(R.id.bUserGridMapsNav);
		mapsButtonNav.setOnClickListener(this);
		prefButtonNav = (ImageButton) findViewById(R.id.bUserGridPrefNav);
		prefButtonNav.setOnClickListener(this);
	}

	/**
	 * @return HttpPost that is the PHP to get the paths of the photos of the user who is currently
	 * using the application.
	 */
	public HttpPost setHttpPost() {
		try {
			HttpPost post = new HttpPost(
					"http://squashysquash.com/CatsEverywhere/getUserImages.php");
			MultipartEntity reqEntity = new MultipartEntity();
			String user = myApp.getUser();
			reqEntity.addPart("user", new StringBody(user));
			post.setEntity(reqEntity);
			return post;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sets the functionality of the buttons in the navigation bar of the gallery.
	 */
	public void onClick(View v) {
		switch (v.getId()) {

		// navbar
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