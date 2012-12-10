package duke.cs.fall2012.catseverywhere.gallery;

import duke.cs.fall2012.catseverywhere.R;
import android.app.Activity;
import android.view.View;

/**
 * An ImageAdapter that ensures images are displayed in the appropriate size for fullscreen galleries.
 */
public class ImageGalleryAdapter extends ImageAdapter {

	public ImageGalleryAdapter(Activity a, String[] d) {
		super(a, d);
		imageLoader = new ImageLoader(activity.getApplicationContext(), "gallery");
	}

	public View inflate() {
		return inflater.inflate(R.layout.item_gallery_image, null);
	}

}