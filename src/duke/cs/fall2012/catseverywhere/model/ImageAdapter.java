package duke.cs.fall2012.catseverywhere.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import duke.cs.fall2012.catseverywhere.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * hardcoded image adapter to showcase the use of grid and gallery project 
 */
public class ImageAdapter extends BaseAdapter{
	
	private static LayoutInflater inflater = null;
	private Activity activity;
	
	private String mode = "";
	
	public String[] getImagesFromDb() {
	//UPDATE TO PULL FROM DB
  //http post
    InputStream is = null;
    String result = "";
    
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://squashysquash.com/CatsEverywhere/getImages.php");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
    }catch(Exception e1){
            Log.e("log_tag", "Error in http connection "+e1.toString());
    }
    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
     
            result=sb.toString();
    }catch(Exception e2){
            Log.e("log_tag", "Error converting result "+e2.toString());
    }
     
    //parse json data
    JSONArray jArray = null;
    try{
            jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("log_tag","id: "+json_data.getInt("id")+
                            ", name: "+json_data.getString("name")+
                            ", sex: "+json_data.getInt("sex")+
                            ", birthyear: "+json_data.getInt("birthyear")
                    );
            }
    }
    catch(JSONException e3){
            Log.e("log_tag", "Error parsing data "+e3.toString());
    }
    //TODO: RETURN jArray AS A STRING ARRAY
    return null;
	}
    
	int[] images = {
			R.drawable.buds, R.drawable.cherry_34,
			R.drawable.clouds_2, R.drawable.coffee_beans_2,
			R.drawable.death_valley_sand_dunes, R.drawable.morning_glory_pool,
			R.drawable.pink_flowers, R.drawable.sun_flower,
			R.drawable.sunrise_3, R.drawable.yellow_rose_3,
			};
	
	public ImageAdapter(Activity act, String mode){
		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		activity = act;
		this.mode = mode;
	}

	public int getCount() {
		return images.length;
	}

	public Object getItem(int position) {
		return new Integer(images[position]);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		if(mode.equalsIgnoreCase("grid")){
			if (view == null) {
				view = inflater.inflate(R.layout.each_image, null);
			}
			ImageView iv = (ImageView)view.findViewById(R.id.imageView);
			iv.setImageResource(images[position]);
		} else if(mode.equalsIgnoreCase("gallery")){
			if (view == null) {
				view = inflater.inflate(R.layout.each_image_gallery, null);
			}
			ImageView iv = (ImageView)view.findViewById(R.id.imageView);
			iv.setImageResource(images[position]);
		}
		return view;
	}
}