package duke.cs.fall2012.catseverywhere;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;


public class CreateOrLogin extends Activity implements OnClickListener {
	private Button toLoginActButton, toCreateActButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_login);
        toLoginActButton = (Button) findViewById(R.id.bToLogAct);
        toLoginActButton.setOnClickListener(this);
        toCreateActButton = (Button) findViewById(R.id.bToCreateAct);
        toCreateActButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_or_login, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.bToLogAct:
			startActivity(new Intent(this, Login.class));
			break;
			
		case R.id.bToCreateAct:
			startActivity(new Intent(this, CreateAccount.class));
			break;
			
		}
	}
}
