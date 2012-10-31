package duke.cs.fall2012.catseverywhere;

import duke.cs.fall2012.catseverywhere.gallery.ImageGridActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener {

	//nav bar
	private Button prefsButton, galleryButton, uploadButton, mapsButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize();

    }
    
    public void initialize() {
    	
    	//nav bar
    	prefsButton = (Button) findViewById(R.id.bPrefs);
    	prefsButton.setOnClickListener(this);
    	galleryButton = (Button) findViewById(R.id.bGallery);
    	galleryButton.setOnClickListener(this);
    	uploadButton = (Button) findViewById(R.id.bLoginPage);
    	uploadButton.setOnClickListener(this);
    	mapsButton = (Button) findViewById(R.id.bLoginPage);
    	mapsButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View button) {
		//nav bar
		switch(button.getId()) {
		case R.id.bPrefs:
			startActivity(new Intent(this, Preferences.class));
			break;
		case R.id.bGallery:
			startActivity(new Intent(this, ImageGridActivity.class));
			break;
		case R.id.bLoginPage:
			startActivity(new Intent(this, Login.class));
			break;
		}
	}

    
    
}
