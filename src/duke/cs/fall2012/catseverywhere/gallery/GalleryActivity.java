//package duke.cs.fall2012.catseverywhere.gallery;
//
//import duke.cs.fall2012.catseverywhere.GoogleMapsActivity;
//import duke.cs.fall2012.catseverywhere.ImageUpload;
//import duke.cs.fall2012.catseverywhere.Preferences;
//import duke.cs.fall2012.catseverywhere.R;
//import duke.cs.fall2012.catseverywhere.model.ImageAdapter;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Gallery;
//import android.widget.ImageButton;
//
//public class GalleryActivity extends Activity implements OnClickListener{
//
//	Gallery gallery = null;
//	ImageAdapter adapter = null;
//
//	//nav bar
//	private ImageButton uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav;
//	
//	
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.gallery);
//        int pos = getIntent().getExtras().getInt("selectedIntex");
//        
//        adapter = new ImageAdapter(GalleryActivity.this, "gallery");
//        
//        gallery = (Gallery)findViewById(R.id.gallery);
//        gallery.setAdapter(adapter);
//        gallery.setSelection(pos);
//        initialize();
//    }
//    
//    public void initialize() {
//    	//nav bar
//    	uploadButtonNav = (ImageButton) findViewById(R.id.bGalleryUploadNav);
//    	uploadButtonNav.setOnClickListener(this);
//    	galleryButtonNav = (ImageButton) findViewById(R.id.bGalleryGalleryNav);
//    	galleryButtonNav.setOnClickListener(this);
//    	mapsButtonNav = (ImageButton) findViewById(R.id.bGalleryMapsNav);
//    	mapsButtonNav.setOnClickListener(this);
//    	prefButtonNav = (ImageButton) findViewById(R.id.bGalleryPrefNav);
//    	prefButtonNav.setOnClickListener(this);
//    }
//    
//    @Override
//	public void onClick(View v) {
//		switch(v.getId()) {
//
//		//navbar
//		case R.id.bGalleryUploadNav:
//			startActivity(new Intent(this, ImageUpload.class));
//			break;
//		case R.id.bGalleryGalleryNav:
//			startActivity(new Intent(this, GridActivity.class));
//			break;
//		case R.id.bGalleryMapsNav:
//			startActivity(new Intent(this, GoogleMapsActivity.class));
//			break;
//		case R.id.bGalleryPrefNav:
//			startActivity(new Intent(this, Preferences.class));
//			break;
//		}
//	}
//}
