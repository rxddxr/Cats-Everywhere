package duke.cs.fall2012.catseverywhere.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import duke.cs.fall2012.catseverywhere.R;

import android.app.Activity;
import android.content.Context;
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
	private ArrayList<Drawable> images = new ArrayList<Drawable>();
	private String mode = "";
	InputStream is;

	public ImageAdapter(Activity act, String mode) {
		String[] pathNames = getImagePathsFromDb();
		getImagesFromUrls(pathNames);
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		activity = act;
		this.mode = mode;
	}
	
	public String[] getImagePathsFromDb() {
		// UPDATE TO PULL FROM DB
		// http post
		String result = "";
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

	public void getImagesFromUrls(String[] paths)
	{
		URL url;
		try {
			for(String tempPath: paths)
			{
				url = new URL("http://squashysquash.com/CatsEverywhere/" + tempPath);
				InputStream content = (InputStream) url.getContent();
			    Drawable d = Drawable.createFromStream(content , "src");
			    System.out.println("boo");
			    images.add(d);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return images.get(position);
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
			iv.setImageDrawable(images.get(position));
		} else if (mode.equalsIgnoreCase("gallery")) {
			if (view == null) {
				view = inflater.inflate(R.layout.each_image_gallery, null);
			}
			ImageView iv = (ImageView) view.findViewById(R.id.imageView);
			iv.setImageDrawable(images.get(position));
		}
		return view;
	}

}