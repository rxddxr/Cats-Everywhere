package duke.cs.fall2012.catseverywhere;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * Class used to keep a collection of items/markers that will be placed on top of Google Maps.
 *
 */
@SuppressWarnings("rawtypes")
public class CustomItemizedOverlay extends ItemizedOverlay{
	private List<OverlayItem> myOverlayItems = new ArrayList<OverlayItem>();
	private Context myContext;

	public CustomItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	
	public CustomItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  myContext = context;
		}
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = myOverlayItems.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(myContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}
	@Override
	protected OverlayItem createItem(int arg0) {
		return myOverlayItems.get(arg0);
	}

	@Override
	public int size() {
		return myOverlayItems.size();
	}
	
	public void addOverlayItem(OverlayItem item)
	{
		myOverlayItems.add(item);
		populate();
	}

}
