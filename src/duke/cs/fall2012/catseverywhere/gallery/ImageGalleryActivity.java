package duke.cs.fall2012.catseverywhere.gallery;

import duke.cs.fall2012.catseverywhere.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;

/**
 * Creates a gallery of photos displayed in fullscreen that can be scrolled through horizontally.
 * This class is used when any photo in a grid gallery is touched, creating an Intent that uses this
 * class.
 */
@SuppressWarnings("deprecation")
public class ImageGalleryActivity extends Activity {
	
	private Gallery gallery;
	
	/**
	 * Initializes the layout of the gallery, creating the ImageGalleryAdapter and 
	 * initially displaying the photo at integer IMAGE_POSITION in the grid.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_gallery);
		
		Bundle bundle = getIntent().getExtras();
		String[] imageUrls = bundle.getStringArray("IMAGES");
		int galleryPosition = bundle.getInt("IMAGE_POSITION", 0);
		
		gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageGalleryAdapter(this, imageUrls));
		gallery.setSelection(galleryPosition);
	}
	
	public void onDestroy()
    {
        gallery.setAdapter(null);
        super.onDestroy();
    }
	
}
