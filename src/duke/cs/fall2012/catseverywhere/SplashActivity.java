package duke.cs.fall2012.catseverywhere;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

/**
 * Splash page that runs each time the application is opened. Easily customizable by replacing
 *the background image. 
 *
 */
public class SplashActivity extends Activity {
	private MediaPlayer startupSound;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startupSound = MediaPlayer.create(SplashActivity.this, R.raw.lion_roar_01);
        //startupSound.start(); 
        
        Thread timer = new Thread() {
        	@Override
			public void run(){
        		try{
        			sleep(5000);        			
        		}
        		catch(InterruptedException e)
    			{
        			e.printStackTrace();
    			}finally{
//    				Intent login = new Intent(getApplicationContext(), Login.class);
//    				startActivity(login);
    				Intent login = new Intent(getApplicationContext(), CreateOrLogin.class);
    				startActivity(login);
    			}
        	}
        };
        timer.start();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		startupSound.release();
		finish(); //Destroy Splash activity when we start the googleMapScreen
	}
    
}
