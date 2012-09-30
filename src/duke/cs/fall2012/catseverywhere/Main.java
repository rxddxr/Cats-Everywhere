package duke.cs.fall2012.catseverywhere;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener {

	private Button prefsButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize();
    }
    
    public void initialize() {
    	prefsButton = (Button) findViewById(R.id.bPrefs);
    	prefsButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		startActivity(new Intent(this, Preferences.class));
	}

    
    
}
