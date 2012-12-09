package duke.cs.fall2012.catseverywhere;


import duke.cs.fall2012.catseverywhere.gallery.NormalImageGridActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

/**
 * Placeholder class for the preferences page. Each developer will have to
 * decide what user preferences such as privacy settings to include.
 *
 */
public class Preferences extends Activity implements OnClickListener {
	
	
	EditText email, password, name;
	Button submit;
	SeekBar seekBar;
	
	//nav bar
	private ImageButton uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
        initialize();
    }
    
    public void initialize() {	
    	//nav bar
    	uploadButtonNav = (ImageButton) findViewById(R.id.bPrefUploadNav);
    	uploadButtonNav.setOnClickListener(this);
    	galleryButtonNav = (ImageButton) findViewById(R.id.bPrefGalleryNav);
    	galleryButtonNav.setOnClickListener(this);
    	mapsButtonNav = (ImageButton) findViewById(R.id.bPrefMapsNav);
    	mapsButtonNav.setOnClickListener(this);
    	prefButtonNav = (ImageButton) findViewById(R.id.bPrefPrefNav);
    	prefButtonNav.setOnClickListener(this);
    	
    	seekBar = (SeekBar) findViewById(R.id.seekBar1);  	
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
		//navbar
		case R.id.bPrefUploadNav:
			startActivity(new Intent(this, ImageUpload.class));
			break;
		case R.id.bPrefGalleryNav:
			startActivity(new Intent(this, NormalImageGridActivity.class));
			break;
		case R.id.bPrefMapsNav:
			startActivity(new Intent(this, GoogleMapsActivity.class));
			break;
		case R.id.bPrefPrefNav:
			startActivity(new Intent(this, Preferences.class));
			break;
		}
	}
}
