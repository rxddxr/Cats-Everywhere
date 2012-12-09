package duke.cs.fall2012.catseverywhere;

import android.app.Application;
/*
 * This class serves to declare global variables across all activities
 */
public class MyApplication extends Application {

	
    private String myUser;

    @Override
    public void onCreate() {
        super.onCreate();
    }
 
    public String getUser() {
        return myUser;
    }

    public void setUser(String user) {
        myUser = user;
    }
}