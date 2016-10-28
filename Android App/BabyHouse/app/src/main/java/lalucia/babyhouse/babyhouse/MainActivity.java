/*
MainActivity.java
Show the user the main screen containing navigation menu options
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
        if(getSupportActionBar() !=null)
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(this);
    }
    //*********************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }
    //**************************************************************************
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    //**************************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        Intent newActivity;
        //noinspection SimplifiableIfStatement
        if (id == R.id.title_activity_help)

        {

           newActivity = new Intent(MainActivity.this, HelpInformation.class);
            startActivity(newActivity);
        }
        else if(mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //**************************************************************************
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        SharedPreferences myprefs = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        int id = item.getItemId();

        Intent newActivity = null;
        if (id == R.id.nav_adoption)
        {
            newActivity = new Intent(MainActivity.this,AdoptionProcess.class);
        }
        else if (id == R.id.nav_volunteer)
        {
            newActivity = new Intent(MainActivity.this, VolunteerProcess.class);
        }
        else if (id == R.id.nav_donations)
        {
            newActivity = new Intent(MainActivity.this , DonationScreen.class);
        }
        else if (id == R.id.nav_wishlist)
        {
            newActivity = new Intent(MainActivity.this, WishListInformation.class);
        }
        else if (id == R.id.nav_our_events)
        {
            newActivity = new Intent(MainActivity.this,Events.class);
        }
        else if (id == R.id.nav_blog)
        {
            if(myprefs.getString("isVolunteer","yes").equalsIgnoreCase("yes"))
            {
                newActivity = new Intent(MainActivity.this, BlogPosts.class);
            }
            else{
                Toast.makeText(this, "Sorry you don't have the right Permissions for this Option.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else if(id == R.id.nav_our_team)
        {
            newActivity = new Intent(MainActivity.this, OurStaff.class);
        }
        else if(id == R.id.nav_contact_us)
        {
            newActivity = new Intent(MainActivity.this , ContactProcess.class);
        }
        else if(id == R.id.nav_checkin)
        {
            if(myprefs.getString("isVolunteer","yes").equalsIgnoreCase("yes"))
            {
                newActivity = new Intent(MainActivity.this, VolunteerCheckIn.class);
            }
            else{
                Toast.makeText(this, "Sorry you don't have the right Permissions for this Option.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else if(id == R.id.nav_gallery)
        {
            newActivity = new Intent(MainActivity.this , Gallery.class);
        }
        startActivity(newActivity);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
