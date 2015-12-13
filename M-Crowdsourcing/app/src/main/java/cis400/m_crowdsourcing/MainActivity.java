package cis400.m_crowdsourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.LogInCallback;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Enable Local Datastore.
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "DKJjvfvhnCGRK0cAdOpJN9MwR7zhIpuYya5xvbuF", "a9iCrTmreZRMhfHbmn35osllgoRe3sDkXJW9sW6l");


            ParseObject testObject = new ParseObject("TestObject");
            testObject.put("foo", "bar");
            testObject.saveInBackground();
        }
        catch (Exception e) {
            System.out.println("Parse already initialized");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //if (id == R.id.action_log_in) {
        //    Intent i = new Intent(this, LoginActivity.class);
        //    startActivity(i);
        //}

        return super.onOptionsItemSelected(item);
    }

    public void onLoginClick(View v) {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        ParseUser.logInInBackground(username, password,
                new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // If user exist and authenticated, send user to Welcome.class
                            Intent intent = new Intent(
                                    MainActivity.this,
                                    LandingPageActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),
                                    "Successfully Logged in",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "No such user exist, please signup",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void onSignUpClick(View v) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }
}