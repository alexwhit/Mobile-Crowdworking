package cis400.m_crowdsourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Enable Local Datastore
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
                                    "Successfully logged in!",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "No such user exists, please sign up.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void onSignUpClick(View v) {
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        try {
            user.signUp();
            ParseObject account = new ParseObject("SignupAccount");
            account.put("user", user);
            account.saveInBackground();
            Intent i = new Intent(this, LandingPageActivity.class);
            startActivity(i);
        } catch (ParseException ex) {
            if (ex.getCode() == ParseException.CONNECTION_FAILED) {
                Toast.makeText(
                        getApplicationContext(),
                        "Error: check connection.",
                        Toast.LENGTH_LONG).show();
            } else if (ex.getCode() == ParseException.USERNAME_TAKEN) {
                Toast.makeText(
                        getApplicationContext(),
                        "Error: username in use.",
                        Toast.LENGTH_LONG).show();
            } else {
                Log.e("A", "B", ex);
                finish();
            }
        }
    }
}