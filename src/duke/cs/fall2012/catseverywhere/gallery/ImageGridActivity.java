package duke.cs.fall2012.catseverywhere.gallery;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;

public abstract class ImageGridActivity extends Activity implements OnClickListener {

	protected GridView gridView;
    private ImageGridAdapter adapter;
    private String[] imageUrls;
    protected InputStream is;
    protected ImageButton uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	imageUrls = getImagePathsFromDb();
        initialize();
        adapter=new ImageGridAdapter(this, imageUrls);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		startImageGalleryActivity(position);
        	}
        });
    }
    
    public abstract void initialize();
    
    public abstract String[] getImagePathsFromDb();
    
    //used to implement nav bar
    public abstract void onClick(View v);
    
    private void startImageGalleryActivity(int position) {
    	Intent i = new Intent(this, ImageGalleryActivity.class);
    	i.putExtra("IMAGES", imageUrls);
    	i.putExtra("IMAGE_POSITION", position);
    	startActivity(i);
    }
    
    @Override
    public void onDestroy()
    {
        gridView.setAdapter(null);
        super.onDestroy();
    }
	
}
