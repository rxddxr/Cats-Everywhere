package duke.cs.fall2012.catseverywhere;

import java.io.IOException;
import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import duke.cs.fall2012.catseverywhere.gallery.GridActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Preferences extends Activity implements OnClickListener {

	EditText email, password, name;
	Button submit;
	
	//nav bar
	private Button uploadButtonNav, galleryButtonNav, mapsButtonNav, prefButtonNav;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
        initialize();
    }
    
    public void initialize() {
    	email = (EditText) findViewById(R.id.etEmail);
    	password = (EditText) findViewById(R.id.etPassword);
    	name = (EditText) findViewById(R.id.etName);
    	submit = (Button) findViewById(R.id.bSubmit);
    	submit.setOnClickListener(this);
    	
    	//nav bar
    	uploadButtonNav = (Button) findViewById(R.id.bPrefUploadNav);
    	uploadButtonNav.setOnClickListener(this);
    	galleryButtonNav = (Button) findViewById(R.id.bPrefGalleryNav);
    	galleryButtonNav.setOnClickListener(this);
    	mapsButtonNav = (Button) findViewById(R.id.bPrefMapsNav);
    	mapsButtonNav.setOnClickListener(this);
    	prefButtonNav = (Button) findViewById(R.id.bPrefPrefNav);
    	prefButtonNav.setOnClickListener(this);
    	
    }

	public void onClick(View v) {
		switch(v.getId()) {
		
		case R.id.bSubmit:
			new Thread(new Runnable() {
				public void run() {
					addToDatabase();
				}
			}).start();
			break;
		
		//navbar
		case R.id.bPrefUploadNav:
			startActivity(new Intent(this, ImageUpload.class));
			break;
		case R.id.bPrefGalleryNav:
			startActivity(new Intent(this, GridActivity.class));
			break;
		case R.id.bPrefMapsNav:
			startActivity(new Intent(this, GoogleMapsActivity.class));
			break;
		case R.id.bPrefPrefNav:
			startActivity(new Intent(this, Preferences.class));
			break;
		}
	}
	
	public void addToDatabase() {
		//Add data
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString()));

	    try {
	    	HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://squashysquash.com/CatsEverywhere/addEntry.php");
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        httpclient.execute(httppost);

	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	}

}
