/*
DonationDetail.java
Show Donation Details to the user
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
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
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        //Checks what value the user has selected in the master fragment
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
        //Set images to show if Zapper button is selected on the Master Fragment
        zapperImg.setVisibility(View.VISIBLE);
        donationtv.setVisibility(View.INVISIBLE);
    }
    //********************************************************************************
    public void showEft()
    {
        //Show eft details if the Eft options is Selected in the Master Fragment
        zapperImg.setVisibility(View.INVISIBLE);
        FactoryClass objFactory = new FactoryClass(this);
        String donationText = objFactory.readDate("donations");
        donationtv.setText(donationText);
    }
}

