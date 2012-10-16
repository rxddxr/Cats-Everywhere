package duke.cs.fall2012.catseverywhere.gallery;

import duke.cs.fall2012.catseverywhere.R;
import duke.cs.fall2012.catseverywhere.model.ImageAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;

public class GalleryActivity extends Activity {
	
	Gallery gallery = null;
	ImageAdapter adapter = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        int pos = getIntent().getExtras().getInt("selectedIntex");
        
        adapter = new ImageAdapter(GalleryActivity.this, "gallery");
        
        gallery = (Gallery)findViewById(R.id.gallery);
        gallery.setAdapter(adapter);
        gallery.setSelection(pos);
    }
}
