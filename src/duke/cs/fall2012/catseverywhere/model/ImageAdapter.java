package duke.cs.fall2012.catseverywhere.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import duke.cs.fall2012.catseverywhere.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * hardcoded image adapter to showcase the use of grid and gallery project
 */
public class ImageAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	private Activity activity;
	private Drawable[] images;
	private String mode = "";
	InputStream is;

	public String[] getImagePathsFromDb() {
		// UPDATE TO PULL FROM DB
		// http post
		String result = "";
		new Thread(new Runnable() {
			public void run() {
				getPaths();
			}
		}).start();
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://squashysquash.com/CatsEverywhere/getImages.php");
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e1) {
			Log.e("log_tag", "Error in http connection " + e1.toString());
		}
		// convert response to string
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

		// parse json data
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(result);
			String[] sArray = new String[jArray.length()];
			for (int i = 0; i < jArray.length(); i++) {
				sArray[i] = jArray.getString(i);
				System.out.println(sArray[i]);
			}
			return sArray;
		} catch (JSONException e3) {
			Log.e("log_tag", "Error parsing data " + e3.toString());
		}
		return null;
	}
	
	private void getPaths() {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://squashysquash.com/CatsEverywhere/getImages.php");
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e1) {
			Log.e("log_tag", "Error in http connection " + e1.toString());
		}
	}

	public void getImages(String[] paths) {
		URL myFileUrl = null;
		try {
			myFileUrl = new URL("http://squashysquash.com/CatsEverywhere/" + paths[0]);
		} catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       }
		try {
            HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            
            Bitmap bmImg = BitmapFactory.decodeStream(is);
       } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       }
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("image_path", paths[0]));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
				"http://squashysquash.com/CatsEverywhere/getPictures.php");
			httppost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httppost);
			//String responseText = EntityUtils.toString(response.getEntity());
			//System.out.println(responseText);
			InputStream is_image = response.getEntity().getContent();
			System.out.println(is_image);
			if (is_image == null) {
				Log.d("isNull", "is_image");
			}
			Drawable drawable = Drawable.createFromStream(is_image, null);
			if (drawable == null) {
				Log.d("isNull", "drawable");
			}
			images[0] = drawable;
		} catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }

	}

	/*
	 * int[] images = { R.drawable.buds, R.drawable.cherry_34,
	 * R.drawable.clouds_2, R.drawable.coffee_beans_2,
	 * R.drawable.death_valley_sand_dunes, R.drawable.morning_glory_pool,
	 * R.drawable.pink_flowers, R.drawable.sun_flower, R.drawable.sunrise_3,
	 * R.drawable.yellow_rose_3, };
	 */

	public ImageAdapter(Activity act, String mode) {
		String[] pathNames = getImagePathsFromDb();
		getImages(pathNames);
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		activity = act;
		this.mode = mode;
	}

	public int getCount() {
		return images.length;
	}

	public Object getItem(int position) {
		return images[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		if (mode.equalsIgnoreCase("grid")) {
			if (view == null) {
				view = inflater.inflate(R.layout.each_image, null);
			}
			ImageView iv = (ImageView) view.findViewById(R.id.imageView);
			iv.setImageDrawable(images[position]);
		} else if (mode.equalsIgnoreCase("gallery")) {
			if (view == null) {
				view = inflater.inflate(R.layout.each_image_gallery, null);
			}
			ImageView iv = (ImageView) view.findViewById(R.id.imageView);
			iv.setImageDrawable(images[position]);
		}
		return view;
	}
}