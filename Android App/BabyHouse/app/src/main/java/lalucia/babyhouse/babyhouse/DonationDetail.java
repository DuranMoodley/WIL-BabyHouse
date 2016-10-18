package lalucia.babyhouse.babyhouse;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DonationDetail extends AppCompatActivity {

    private TextView donationtv;
    private ImageView zapperImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //---if the user switches to landscape mode; destroy the activity---
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE)
        {
            finish();
            return;
        }
        //---get the data passed from the master fragment---
        String name = getIntent().getStringExtra("name");
        donationtv = (TextView) findViewById(R.id.tvSelectedItem);
        zapperImg = (ImageView) findViewById(R.id.imgZapper);
        if(name.equalsIgnoreCase("ZAPPER"))
        {
            showZapper();
        }
        else
        {
            showEft();
        }
    }
    //********************************************************************************
    public void showZapper()
    {
        zapperImg.setVisibility(View.VISIBLE);
        donationtv.setVisibility(View.INVISIBLE);
    }
    //********************************************************************************
    public void showEft()
    {
        zapperImg.setVisibility(View.INVISIBLE);
        FactoryClass objFactory = new FactoryClass(this);
        String donationText = objFactory.readDate("donations");
        donationtv.setText(donationText);
    }
}

