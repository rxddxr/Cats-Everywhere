package duke.cs.fall2012.catseverywhere.gallery;

import duke.cs.fall2012.catseverywhere.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Acts as a bridge between an AdapterView (either a Gallery or GridView, depending on the type of
 * gallery) and the images being displayed in this view.
 */
public abstract class ImageAdapter extends BaseAdapter {

    protected Activity activity;
    private String[] data;
    protected static LayoutInflater inflater = null;
    public ImageLoader imageLoader;
    
    public ImageAdapter(Activity a, String[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    /**
     * @return how many images are in data
     */
	public int getCount() {
		return data.length;
	}
	
	/**
	 * @return the image associated with the specified position
	 */
	public Object getItem(int position) {
		return position;
	}

	/**
	 * @return the row id associated with the specified position
	 */
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * @return a view hierarchy from the specified xml resource
	 */
	public abstract View inflate();
	
	/**
	 * @return a View that displays the data at the specified position
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflate();

		ImageView image = (ImageView) vi.findViewById(R.id.image);
		imageLoader.displayImage(data[position], image);
		return vi;
	}
    
}