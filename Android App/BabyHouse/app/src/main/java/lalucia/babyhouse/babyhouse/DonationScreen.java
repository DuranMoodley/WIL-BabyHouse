/*
DonationScreen.java
Object class that contains blog details
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class DonationScreen extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    private Animation fadeIn,fadeOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Checks the screen orientatian and makes the necessary changes to the layouts
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
           setContentView(R.layout.activity_donations_landscape);
        }
        else
        {
            setContentView(R.layout.activity_donation_screen);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(fadeIn);
        viewFlipper.setOutAnimation(fadeOut);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();

    }
}
