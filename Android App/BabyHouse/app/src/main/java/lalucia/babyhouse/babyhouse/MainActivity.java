package lalucia.babyhouse.babyhouse;

import android.content.Intent;
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
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ActionBarDrawerToggle mDrawerToggle;
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
            newActivity = new Intent(MainActivity.this, HelpScreen1.class);
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
        int id = item.getItemId();

        Intent newActivity = null;
        if (id == R.id.nav_adoption)
        {
            newActivity = new Intent(MainActivity.this,AdoptionProcess1.class);
        }
        else if (id == R.id.nav_volunteer)
        {
            newActivity = new Intent(MainActivity.this , VolunteerProcess1.class);
        }
        else if (id == R.id.nav_donations)
        {
            newActivity = new Intent(MainActivity.this , DonationScreen.class);
        }
        else if (id == R.id.nav_wishlist)
        {
            newActivity = new Intent(MainActivity.this, WishListProcess1.class);
        }
        else if (id == R.id.nav_our_events)
        {
            // newActivity = new Intent(NavigationActivity.this , );
            Toast.makeText(MainActivity.this,"Under Construction",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (id == R.id.nav_blog)
        {
            newActivity = new Intent(MainActivity.this , BlogPosts.class);
        }
        else if(id == R.id.nav_our_team)
        {
            Toast.makeText(MainActivity.this,"Under Construction",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(id == R.id.nav_contact_us)
        {
            newActivity = new Intent(MainActivity.this , BabyHouseMap.class);
        }
        else if(id == R.id.nav_checkin)
        {
            newActivity = new Intent(MainActivity.this , VolunteerCheckIn.class);
        }
        startActivity(newActivity);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
