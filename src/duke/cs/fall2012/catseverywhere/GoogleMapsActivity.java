package duke.cs.fall2012.catseverywhere;
import java.util.ArrayList;
import com.google.android.maps.MapActivity;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import duke.cs.fall2012.catseverywhere.gallery.ImageGridActivity;
import duke.cs.fall2012.catseverywhere.model.ImageAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class GoogleMapsActivity extends MapActivity implements OnClickListener{

	//private static final int PICK_FROM_FILE = 2;
	private MapView myMapView;
	private static MapController myMapController;
	private LocationManager myLocManager;
	private CustomLocationListener myLocListener;
	private CustomItemizedOverlay myItemizedOverlay;
	private Resources myResources;
	private Drawable myMarker;
	private final int MAX_ICON_WIDTH = 60;
	private final int MAX_ICON_HEIGHT = 60;
	private ImageAdapter myImageAdapter;
	private ArrayList<GeoPoint> myGeoPoints;
	private ImageButton uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav; //nav bar
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myResources = this.getResources();
        setContentView(R.layout.activity_google_maps);
        myMapView = (MapView) findViewById(R.id.mapview1);
        myMapController = myMapView.getController();
        myLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new CustomLocationListener(getApplicationContext());
        myLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocListener);
        myMarker = myResources.getDrawable(R.raw.blue_circle_60);
        myItemizedOverlay = new CustomItemizedOverlay(myMarker, this);
        myImageAdapter = new ImageAdapter();
        loadOnlineGeoPoints();
             
        final ImageButton findMeButton = (ImageButton) findViewById(R.id.button_find_me);
        findMeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
            	myMapController.animateTo(myLocListener.getCurrentGeoP());
            	myMapController.setZoom(17);
            }
           
        });
      
      initialize();

    }
    
    private void loadOnlineGeoPoints() {
        myGeoPoints = myImageAdapter.getRandomGeopoints(5);
		
		for(GeoPoint point: myGeoPoints)
		{
		String picSnippet = "Lat: " + point.getLatitudeE6() +"\nLong: " + point.getLongitudeE6();
		myItemizedOverlay.addOverlayItem(new OverlayItem(point, "Picture", picSnippet));		
		}
		myMapView.getOverlays().clear();
		myMapView.getOverlays().add(myItemizedOverlay);
	}

	public void initialize() {
    	//nav bar
    	uploadButtonNav = (ImageButton) findViewById(R.id.bMapsUploadNav);
    	uploadButtonNav.setOnClickListener(this);
    	galleryButtonNav = (ImageButton) findViewById(R.id.bMapsGalleryNav);
    	galleryButtonNav.setOnClickListener(this);
    	mapsButtonNav = (ImageButton) findViewById(R.id.bMapsMapsNav);
    	mapsButtonNav.setOnClickListener(this);
    	prefButtonNav = (ImageButton) findViewById(R.id.bMapsPrefNav);
    	prefButtonNav.setOnClickListener(this);
    }
  
    private Bitmap iconize(Bitmap pic)
    {
    	int picWidth = pic.getWidth();
    	int picHeight = pic.getHeight();
    	// Constrain to given size but keep aspect ratio
    	float scaleFactor = Math.min(((float) MAX_ICON_WIDTH) / picWidth, ((float) MAX_ICON_HEIGHT) / picHeight);
    	Matrix scale = new Matrix();
    	scale.postScale(scaleFactor, scaleFactor);
    	Bitmap icon = Bitmap.createBitmap(pic, 0, 0, picWidth, picHeight, scale, false);
    	return icon;
    }
   	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_google_maps, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	public static void animateToLoc(GeoPoint geoPoint)
	{
		myMapController.animateTo(geoPoint);
	}
	
	public String getRealPathFromURI(Uri contentUri) {
        String [] proj 		= {MediaColumns.DATA};
        Cursor cursor 		= managedQuery( contentUri, proj, null, null,null);
        
        if (cursor == null) return null;
        
        int column_index 	= cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        
        cursor.moveToFirst();

        return cursor.getString(column_index);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
		//navbar
		case R.id.bMapsUploadNav:
			startActivity(new Intent(this, ImageUpload.class));
			break;
		case R.id.bMapsGalleryNav:
			startActivity(new Intent(this, ImageGridActivity.class));
			break;
		case R.id.bMapsMapsNav:
			startActivity(new Intent(this, GoogleMapsActivity.class));
			break;
		case R.id.bMapsPrefNav:
			startActivity(new Intent(this, Preferences.class));
			break;
		}
	}
}
