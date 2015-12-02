package cis400.m_crowdsourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class SignupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_signup, menu);
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


    public void onSignupClick (View v) {
        String username = ((EditText)findViewById(R.id.signup_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.signup_password)).getText().toString();
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        try {
            user.signUp();
            ParseObject account = new ParseObject("SignupAccount");
            account.put("user", user);
            account.saveInBackground();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } catch (ParseException ex) {
            if (ex.getCode() == ParseException.CONNECTION_FAILED) {
                ((TextView) findViewById(R.id.signup_error)).setText("Error: Check connection");
            } else if (ex.getCode() == ParseException.USERNAME_TAKEN) {
                ((TextView) findViewById(R.id.signup_error)).setText("Error: username in use.");
            } else {
                Log.e("A", "B", ex);
                finish();
            }
        }
    }



    public void onBack (View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
