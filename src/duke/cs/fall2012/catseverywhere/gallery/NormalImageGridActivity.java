package duke.cs.fall2012.catseverywhere.gallery;

import org.apache.http.client.methods.HttpPost;

import duke.cs.fall2012.catseverywhere.GoogleMapsActivity;
import duke.cs.fall2012.catseverywhere.ImageUpload;
import duke.cs.fall2012.catseverywhere.Preferences;
import duke.cs.fall2012.catseverywhere.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

public class NormalImageGridActivity extends ImageGridActivity {
	
    private Button userGalleryButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.image_grid);
        gridView=(GridView)findViewById(R.id.gridView);
        userGalleryButton = (Button) findViewById(R.id.bUserGallery);
    	final Context context = this;
        userGalleryButton.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View v) {
				startActivity(new Intent(context, UserImageGridActivity.class));
			}
		});
        super.onCreate(savedInstanceState);
    }
    
    public void initialize() {
		//nav bar
    	uploadButtonNav = (ImageButton) findViewById(R.id.bGridUploadNav);
    	uploadButtonNav.setOnClickListener(this);
    	galleryButtonNav = (ImageButton) findViewById(R.id.bGridGalleryNav);
    	galleryButtonNav.setOnClickListener(this);
    	mapsButtonNav = (ImageButton) findViewById(R.id.bGridMapsNav);
    	mapsButtonNav.setOnClickListener(this);
    	prefButtonNav = (ImageButton) findViewById(R.id.bGridPrefNav);
    	prefButtonNav.setOnClickListener(this);
	}
    
    public HttpPost setHttpPost() {
    	return new HttpPost("http://squashysquash.com/CatsEverywhere/getImages.php");
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		//navbar
		case R.id.bGridUploadNav:
			startActivity(new Intent(this, ImageUpload.class));
			break;
		case R.id.bGridGalleryNav:
			startActivity(new Intent(this, NormalImageGridActivity.class));
			break;
		case R.id.bGridMapsNav:
			startActivity(new Intent(this, GoogleMapsActivity.class));
			break;
		case R.id.bGridPrefNav:
			startActivity(new Intent(this, Preferences.class));
			break;
		}
	}
    
}