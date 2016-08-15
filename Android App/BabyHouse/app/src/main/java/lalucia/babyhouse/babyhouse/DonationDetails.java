package lalucia.babyhouse.babyhouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DonationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView donationDetailsTv = (TextView) findViewById(R.id.tvBankDetails);
        FactoryClass objFactory = new FactoryClass(this);
        assert donationDetailsTv != null;
        donationDetailsTv.setText(objFactory.readDate("donations"));
    }
}
