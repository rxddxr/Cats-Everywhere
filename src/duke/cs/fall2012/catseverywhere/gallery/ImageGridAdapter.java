package duke.cs.fall2012.catseverywhere.gallery;

import duke.cs.fall2012.catseverywhere.R;
import android.app.Activity;
import android.view.View;

public class ImageGridAdapter extends ImageAdapter {

	public ImageGridAdapter(Activity a, String[] d) {
		super(a, d);
        imageLoader=new ImageLoader(activity.getApplicationContext(), "grid");
    }
	
	public View inflate() {
		return inflater.inflate(R.layout.item_grid_image, null);
	}
}