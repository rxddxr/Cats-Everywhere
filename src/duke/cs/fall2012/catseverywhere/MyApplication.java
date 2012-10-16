package duke.cs.fall2012.catseverywhere;

import android.app.Application;

public class MyApplication extends Application {

	/*
	 * This class serves to declare global variables across all activities
	 */
    private String myUser;

    public String getUser() {
        return myUser;
    }

    public void setMyUser(String user) {
        myUser = user;
    }
}