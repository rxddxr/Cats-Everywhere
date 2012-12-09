
package duke.cs.fall2012.catseverywhere.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import com.google.android.maps.GeoPoint;
import android.util.Log;
/**
 * Class used to access a MYSQL database and extract geolocation data. As more users download the app the database 
 * will get larger and larger, so getting the location data for all the pictures won't be practical and it would be
 * messy if all of the were displayed. To solve this problem data from a specific number of randomly chosen images
 * will be extracted.
 */
public class GeoDBAdapter {
	
	private final String LOC_SEPARATOR = "A";	
	private ArrayList<GeoPoint> myGeoPoints;	
	InputStream is;

	
	public GeoDBAdapter() {
		myGeoPoints =getGeoPointsFromLocs(getImageLocsFromDb());
	}

	/**
	 * @param Generate geoPoint objects from the location information of
	 * n randomly chosen images in the database.
	 * @return
	 */
	public ArrayList<GeoPoint> getRandomGeopoints(int n)
	{
		ArrayList<GeoPoint> myRandomGeoPoints = new ArrayList<GeoPoint>();
		HashSet<Integer> imgIndexes = new HashSet<Integer>();
		Random myRandGen = new Random();
		if(n> myGeoPoints.size() -1)
		{
			n = myGeoPoints.size() -1;
		}

		while(imgIndexes.size()< n)
		{
			imgIndexes.add(myRandGen.nextInt(n+1));
		}

		for(int index: imgIndexes)
		{
			myRandomGeoPoints.add(myGeoPoints.get(index));
		}


		return myRandomGeoPoints;

	}

	/**Method to create an Arraylist of GeoPoints given a string array of latitudes and longitudes
	 * @param Array of image locations (latitude and longitude in String form, separted by
	 * some character in the database. In this case we've set the separator to "A"
	 * @return An ArrayList of GeoPoints
	 */
	public ArrayList<GeoPoint> getGeoPointsFromLocs(String[] imgLocations)
	{

		myGeoPoints = new ArrayList<GeoPoint>();
		for(String location: imgLocations)
		{
		 if(parseLatLong(location,LOC_SEPARATOR)!=null)
		 {
			 float[] myLatLong = parseLatLong(location, LOC_SEPARATOR);
			 GeoPoint geoP = new GeoPoint((int) (myLatLong[0] * 1E6), (int) (myLatLong[1] * 1E6));
			 myGeoPoints.add(geoP);
		 }
		}

		return myGeoPoints;
	}

	/**
	 * @param Latitude and Longitude values separtead by a character or string
	 * In order for this method to work the separator should match the "separator"
	 * parameter.
	 * @param separator:
	 * @return
	 */
	private float[] parseLatLong(String location, String separator) {
		// TODO Auto-generated method stub
		float[] latLong = new float[2];
		if(location.contains(separator))
		{
			String[] latLongS = location.split(separator);
			latLong[0] = Float.parseFloat(latLongS[0]);
			latLong[1] = Float.parseFloat(latLongS[1]);
			return latLong;
		}
		return null;
	}
	/**Retrieve the image locations stored in a MYSQL database using a predetermined
	 * PHP script.
	 * @return
	 */
	public String[] getImageLocsFromDb() {
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://squashysquash.com/CatsEverywhere/getImageLocations.php");
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
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(result);
			String[] sArray = new String[jArray.length()];
			for (int i = 0; i < jArray.length(); i++) {
				sArray[i] = jArray.getString(i);
			}
			return sArray;
		} catch (JSONException e3) {
			Log.e("log_tag", "Error parsing data " + e3.toString());
		}
		return null;
	}

}