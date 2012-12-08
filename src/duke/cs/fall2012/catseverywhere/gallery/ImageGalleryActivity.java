package duke.cs.fall2012.catseverywhere.gallery;

import duke.cs.fall2012.catseverywhere.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;

@SuppressWarnings("deprecation")
public class ImageGalleryActivity extends Activity {
	
	private Gallery gallery;
	
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
