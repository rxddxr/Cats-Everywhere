package duke.cs.fall2012.catseverywhere;

import duke.cs.fall2012.catseverywhere.gallery.GridActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener {

	private Button prefsButton, galleryButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize();
    }
    
    public void initialize() {
    	prefsButton = (Button) findViewById(R.id.bPrefs);
    	prefsButton.setOnClickListener(this);
    	galleryButton = (Button) findViewById(R.id.bGallery);
    	galleryButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View button) {
		switch(button.getId()) {
		case R.id.bPrefs:
			startActivity(new Intent(this, Preferences.class));
			break;
		case R.id.bGallery:
			startActivity(new Intent(this, GridActivity.class));
		}
	}

    
    
}
