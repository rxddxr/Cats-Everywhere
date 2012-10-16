package duke.cs.fall2012.catseverywhere;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener{
	
	EditText email, password;
	Button submit;
	MyApplication myApp;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initialize();
    }
	
	public void initialize() {
    	email = (EditText) findViewById(R.id.etEmail1);
    	password = (EditText) findViewById(R.id.etPassword1);
    	submit = (Button) findViewById(R.id.bLogin);
    	submit.setOnClickListener(this);
    	myApp = (MyApplication) this.getApplication();
    }

	public void onClick(View v) {
		new Thread(new Runnable() {
			public void run() {
				verifyCredentials();

			}
		}).start();
	}
	
	public void verifyCredentials() {
		//Add data
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));

        InputStream is = null;
        String result = "";
	    try {
	    	HttpClient httpclient = new DefaultHttpClient();
	    	
	    	//TODO: MAKE PHP TO CHECK CREDENTIALS
		    HttpPost httppost = new HttpPost("http://squashysquash.com/CatsEverywhere/login.php");
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
			is = entity.getContent();
			
		try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();

				result = sb.toString();
		} catch (Exception e2) {
				Log.e("log_tag", "Error converting result " + e2.toString());
		}
	      
		System.out.println("result=" + result);
		if (result.trim().equals("true")) {
			myApp.setUser(email.toString());//set user
		    System.out.println("successish");
		}
		else {
			System.out.println("INCORRECT PASSWORD");
		}
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	}
}
