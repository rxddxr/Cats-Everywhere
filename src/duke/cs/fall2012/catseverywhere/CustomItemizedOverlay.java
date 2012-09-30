package duke.cs.fall2012.catseverywhere;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ClipData.Item;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomItemizedOverlay extends ItemizedOverlay{
	private List<OverlayItem> myOverlayItems = new ArrayList<OverlayItem>();
	private Context myContext;

	public CustomItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return myOverlayItems.get(arg0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return myOverlayItems.size();
	}
	
	public void addOverlayItem(OverlayItem item)
	{
		myOverlayItems.add(item);
		populate();
	}

}
