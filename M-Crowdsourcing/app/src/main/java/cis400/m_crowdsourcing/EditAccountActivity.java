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

public class EditAccountActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        String pword = getIntent().getStringExtra("password");
        if (pword != null && !pword.isEmpty()) {
            EditText password = ((EditText)findViewById(R.id.signup_password));
            password.setText(pword);
        }
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


    public void onChangeInformationClick (View v) {
        String password = ((EditText)findViewById(R.id.signup_password)).getText().toString();
        String name = ((EditText)findViewById(R.id.signup_name)).getText().toString();
        String country = ((EditText)findViewById(R.id.signup_country)).getText().toString();
        ParseUser user = ParseUser.getCurrentUser();
        if(!password.isEmpty()) user.setPassword(password);
        if(!name.isEmpty()) user.put("name", name);
        if(!country.isEmpty()) user.put("country", country);
        user.saveInBackground();

        Intent i = new Intent(this, LandingPageActivity.class);
        startActivity(i);
    }



    public void onBack (View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

}
