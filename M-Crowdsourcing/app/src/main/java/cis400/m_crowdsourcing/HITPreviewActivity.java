package cis400.m_crowdsourcing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

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
        payment_text.setText("Payment: " + payment.toString());
    }

}
