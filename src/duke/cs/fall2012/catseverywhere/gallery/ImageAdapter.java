package duke.cs.fall2012.catseverywhere.gallery;

import duke.cs.fall2012.catseverywhere.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
     * @return the length of the array data, i.e. how many images there are
     */
	public int getCount() {
		return data.length;
	}
	
	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	
	public abstract View inflate();
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflate();

		ImageView image = (ImageView) vi.findViewById(R.id.image);
		imageLoader.displayImage(data[position], image);
		return vi;
	}
    
}