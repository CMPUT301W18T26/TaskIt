package com.cmput301w18t26.taskit.IntentTesting;

/**
 * Created by kevingordon on 2018-03-17.
 */

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301w18t26.taskit.HomeActivity;
import com.cmput301w18t26.taskit.LoginActivity;
import com.cmput301w18t26.taskit.R;
import com.cmput301w18t26.taskit.TaskItData;
import com.cmput301w18t26.taskit.TaskItFile;
import com.cmput301w18t26.taskit.User;
import com.cmput301w18t26.taskit.UserActivity;
import com.robotium.solo.Solo;


public class UserProfileTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public UserProfileTest(){
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testCreateEditViewUser() {
        // ADD THIS FOR ALL TEST
        Context c = getInstrumentation().getTargetContext().getApplicationContext();
        TaskItFile.setContext(c);
        TaskItData db = TaskItData.getInstance();
        // END ADD ALL THIS

        String myUsername = "Foobar";
        String name1 = "testName1";
        String name2 = "testName2";

        User foo;
        try {
            foo = db.getUserByUsername(myUsername);
            db.deleteUser(foo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button loginButton = (Button) solo.getView(R.id.login);
        Button registerButton = (Button) solo.getView(R.id.register);

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.enterText((EditText) solo.getView(R.id.username), myUsername);

        solo.clickOnView(loginButton);

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.clickOnView(registerButton);

        solo.waitForActivity(UserActivity.class, 3000);

        solo.assertCurrentActivity("Wrong Activity", UserActivity.class);

        Button confirmUserButton = (Button) solo.getView(R.id.confirmuser);

        solo.enterText((EditText) solo.getView(R.id.username1), myUsername);
        solo.enterText((EditText) solo.getView(R.id.name), name1);
        solo.enterText((EditText) solo.getView(R.id.email), "abc@def.com");
        solo.enterText((EditText) solo.getView(R.id.phone), "123456789");

        solo.clickOnView(confirmUserButton);

        solo.waitForActivity(LoginActivity.class, 3000);

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.clickOnView(loginButton);

        solo.waitForActivity(HomeActivity.class, 3000);

        solo.assertCurrentActivity("Wrong Activity", HomeActivity.class);

        Button viewProfileButton = (Button) solo.getView(R.id.profile);

        solo.clickOnView(viewProfileButton);

        solo.waitForActivity(UserActivity.class, 3000);
        solo.assertCurrentActivity("Wrong Activity", UserActivity.class);

        assertTrue(solo.waitForText(name1, 1, 3000));

        Button editProfile = (Button) solo.getView(R.id.edit);

        solo.clickOnView(editProfile);

        solo.waitForActivity(UserActivity.class, 3000);

        solo.clearEditText((EditText) solo.getView(R.id.name));

        solo.enterText((EditText) solo.getView(R.id.name), name2);

        solo.clickOnView(confirmUserButton);

        assertTrue(solo.waitForText(name2, 1, 3000));

        try {
            foo = db.getUserByUsername(myUsername);
            db.deleteUser(foo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
