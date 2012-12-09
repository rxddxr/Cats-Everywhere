package duke.cs.fall2012.catseverywhere;
 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Class for log in and account creation page.
 *
 */
public class CreateAccount extends Activity implements OnClickListener{
	 
	
	EditText email, password, name;
	Button createAccountButton;
	MyApplication myApp;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        initialize();
    }
	
	public void initialize() {
    	email = (EditText) findViewById(R.id.etEmail1);
    	password = (EditText) findViewById(R.id.etPassword1);
    	name = (EditText) findViewById(R.id.etName1);
    	createAccountButton = (Button) findViewById(R.id.bCreateAccount);
    	createAccountButton.setOnClickListener(this);
    	myApp = (MyApplication) this.getApplication();
    }

	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.bCreateAccount:
			new Thread(new Runnable() {
				@Override
				public void run() {
					addToDatabase();
				}
			}).start();
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
	        startActivity(new Intent(this, GoogleMapsActivity.class));

	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}

