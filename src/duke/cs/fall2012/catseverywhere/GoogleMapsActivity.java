package duke.cs.fall2012.catseverywhere;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import duke.cs.fall2012.catseverywhere.gallery.NormalImageGridActivity;
import duke.cs.fall2012.catseverywhere.model.GeoDBAdapter;


/**
 * Class that displays Google Maps with image markers overlayed on top where
 * random pictures from the online database were taken. The user can sroll
 * and usi multitouch gestures to zoom in and out. Taping on a blue image
 * marker displays the latitude and longitude of where the picture was
 * taken.
 *
 */
public class GoogleMapsActivity extends MapActivity implements OnClickListener{

	//private static final int PICK_FROM_FILE = 2;
	private MapView myMapView;
	private static MapController myMapController;
	private LocationManager myLocManager;
	private CustomLocationListener myLocListener;
	private CustomItemizedOverlay myItemizedOverlay;
	private Resources myResources;
	private Drawable myMarker;
	private GeoDBAdapter myImageAdapter;
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
        myImageAdapter = new GeoDBAdapter();
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
	
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
		//navbar
		case R.id.bMapsUploadNav:
			startActivity(new Intent(this, ImageUpload.class));
			break;
		case R.id.bMapsGalleryNav:
			startActivity(new Intent(this, NormalImageGridActivity.class));
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
