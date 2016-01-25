package cis400.m_crowdsourcing;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class SearchActivity extends ListActivity {

    ArrayList<ParseObject> parsed = new ArrayList<ParseObject>();
    ArrayList<String> hits = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("HIT");
        parsed = new ArrayList<ParseObject>();
        hits = new ArrayList<String>();

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    System.out.println("Parse Object Retrieved Successfully");

                    for (ParseObject object : objects) {
                        String title = object.getString("title");
                        System.out.println(title);
                        hits.add(title);
                        parsed.add(object);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, hits);
                    setListAdapter(adapter);
                } else {
                    System.out.println("Parse Object Retrieval Failure");
                }
            }
        });



    }

    public void drive(String[] args) {
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        // And now use this to visit Google
        driver.get("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        driver.quit();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        ParseObject selectedValue = parsed.get(position);



        //Intent i = new Intent(this, HITPreviewActivity.class);
        //i.putExtra("parse_id", selectedValue.getObjectId());
        String groupId = selectedValue.getString("groupID");
        String url = "http://www.mturk.com/mturk/preview?groupId=" + groupId;
       try {
           URL preview_url = new URL(url);
           new ParseSoupTask().execute(preview_url);
           System.out.println("Parsing successful!");
           //System.out.print(doc.title());
       }
       catch (Exception e) {
           System.out.println("exception in jsoup document connection");
           System.out.println(e.toString());
        }

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //startActivity(intent);

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

    private class ParseSoupTask extends AsyncTask<URL, String, Long> {
        protected Long doInBackground(URL... urls) {
            String preview_url = urls[0].toString();
            try {
                String signature = "";
                Connection connect = Jsoup.connect(preview_url);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Document doc = connect.get();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Elements signatures = doc.select("input");
                System.out.println(signatures.size());
                System.out.println("signatures size just printed");
                for (Element element : signatures) {
                    System.out.println(element);
                   // System.out.println("signature: " + element.attr("value"));
                    //signature = element.attr("value");
                }

                Elements links = doc.select("a[id = hitExternalNextAcceptLink]");
                for (Element element : links) {
                    //System.out.println(element);
                    System.out.println(element.attr("href") + "&signature=" + signature);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return new Long(0);
        }

    }

}


