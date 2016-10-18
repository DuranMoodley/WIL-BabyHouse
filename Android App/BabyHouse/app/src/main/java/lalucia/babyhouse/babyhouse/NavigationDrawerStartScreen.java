package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class NavigationDrawerStartScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPageAdapter viewPageAdapter;
    ViewPager viewPager;
    SlidingTabLayout slidingTabLayout;
    CharSequence tabTitles [] = {"Home","Check In","Donate","Events"};
    int totalTabs = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytool_bar);
        setSupportActionBar(toolbar);

        //Creating the Viewpager and parsing fragment manager
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(),tabTitles,totalTabs);

        //Assigning view pager view and setting the adapter
        viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPager.setAdapter(viewPageAdapter);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tbSlidingTab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent newActivity = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.title_activity_help)
        {
            newActivity = new Intent(NavigationDrawerStartScreen.this,HelpScreen1.class);
            startActivity(newActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent newActivity = null;
        if (id == R.id.nav_adoption)
        {
            newActivity = new Intent(NavigationDrawerStartScreen.this,AdoptionProcess1.class);
        }
        else if (id == R.id.nav_volunteer)
        {
            newActivity = new Intent(NavigationDrawerStartScreen.this , VolunteerProcess1.class);
        }
        else if (id == R.id.nav_donations)
        {
            newActivity = new Intent(NavigationDrawerStartScreen.this , DonationScreen.class);
        }
        else if (id == R.id.nav_wishlist)
        {
            newActivity = new Intent(NavigationDrawerStartScreen.this , WishListProcess1.class);
        }
        else if (id == R.id.nav_our_events)
        {
            // newActivity = new Intent(NavigationActivity.this , );
            Toast.makeText(this,"Under Construction",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (id == R.id.nav_blog)
        {
            newActivity = new Intent(NavigationDrawerStartScreen.this , BlogPosts.class);
        }
        else if(id == R.id.nav_our_team)
        {
            Toast.makeText(this,"Under Construction",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(id == R.id.nav_contact_us)
        {
           // newActivity = new Intent(NavigationDrawerStartScreen.this , BabyHouseMap.class);
            //newActivity = new Intent(NavigationDrawerStartScreen.this , MapsActivity.class);
           // Toast.makeText(this,"Under Construction",Toast.LENGTH_LONG).show();
           // return false;
        }
        startActivity(newActivity);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
