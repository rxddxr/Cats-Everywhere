package duke.cs.fall2012.catseverywhere;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashActivity extends Activity {
	private MediaPlayer startupSound;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startupSound = MediaPlayer.create(SplashActivity.this, R.raw.lion_roar_01);
        //startupSound.start(); //Commented out to avoid making noise in Perkins.
        
        Thread timer = new Thread() {
        	public void run(){
        		try{
        			sleep(5000);        			
        		}
        		catch(InterruptedException e)
    			{
        			e.printStackTrace();
    			}finally{
    				Intent googleMapScreen = new Intent(getApplicationContext(), GoogleMapsActivity.class);
    				startActivity(googleMapScreen);
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
