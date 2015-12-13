package cis400.m_crowdsourcing;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.List;
import java.util.ArrayList;


public class SearchActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("HIT");
        final ArrayList<String> hits = new ArrayList<String>();

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    System.out.println("Parse Object Retrieved Successfully");

                    for (ParseObject object : objects) {
                        String title = object.getString("title");
                        System.out.println(title);
                        hits.add(title);
                    }
                } else {
                    System.out.println("Parse Object Retrieval Failure");
                }
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, hits);
        setListAdapter(adapter);
        //setListAdapter(new HITListArrayListAdapter(this, hits));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    }

    private class HITListArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public HITListArrayAdapter(Context context, String[] values) {
            super(context, R.layout.list_hitlistcell, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.list_hitlistcell, parent, false);
            TextView name = (TextView) rowView.findViewById(R.id.hitName);
            name.setText(values[position]);

            return rowView;
        }
    }
}
