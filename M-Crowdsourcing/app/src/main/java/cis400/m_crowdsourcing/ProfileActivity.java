package cis400.m_crowdsourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by kate on 10/12/15.
 */
public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileview);

        // Set the fields for the logged-in user
        TextView n = (TextView) findViewById(R.id.profile_name);
        n.append(" " + ParseUser.getCurrentUser().getString("name"));

        TextView u = (TextView) findViewById(R.id.profile_username);
        u.append(" " + ParseUser.getCurrentUser().getUsername());

        TextView c = (TextView) findViewById(R.id.profile_country);
        c.append(" " + ParseUser.getCurrentUser().getString("country"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEditClick (View v) {
        Intent i = new Intent(this, EditAccountActivity.class);
        startActivity(i);
    }

}
