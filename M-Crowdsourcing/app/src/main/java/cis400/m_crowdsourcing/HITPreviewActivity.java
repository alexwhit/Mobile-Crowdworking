package cis400.m_crowdsourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.view.*;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import com.amazonaws.services.sns.AmazonSNS;
public class HITPreviewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitpreview);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        TextView title_text = (TextView) findViewById(R.id.job_title);
        title_text.setText(title);
        String requester = intent.getStringExtra("requester");
        TextView request_text = (TextView) findViewById(R.id.requester);
        request_text.setText("Requester: " + requester);
        Double payment = intent.getDoubleExtra("reward", 0.0);
        TextView payment_text = (TextView) findViewById(R.id.payment);
        payment_text.setText("Payment: " + "$" + payment.toString());
        while (payment_text.getText().length() < 14) {
            payment_text.setText(payment_text.getText() + "0");
        }
    }

    public void onAcceptClick(View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //create a new SNS client and set endpoint
                    AmazonSNSClient snsClient = new AmazonSNSClient(
                            new BasicAWSCredentials("AKIAIFUO2EGHWGD65YLQ", "q8wSIOCI2EvgOuIcmQxP/PZeOllg+5IZT8C3g+jf"));
                    snsClient.setRegion(Region.getRegion(Regions.US_WEST_2));

                    System.out.println("Created SNS Client");


                    PublishRequest request = new PublishRequest("arn:aws:sns:us-west-2:553615228020:MCrowd",
                            "test worked!");

                    snsClient.publish(request);

                    System.out.println("Published Request");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}
