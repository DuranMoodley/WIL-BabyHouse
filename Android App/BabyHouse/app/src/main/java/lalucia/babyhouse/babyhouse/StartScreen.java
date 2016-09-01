package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class StartScreen extends AppCompatActivity {

    ViewPageAdapter viewPageAdapter;
    ViewPager viewPager;
    SlidingTabLayout slidingTabLayout;
    CharSequence tabTitles [] = {"Home","Check In","Donate","Events"};
    int totalTabs = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
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

        NavigationDrawer navigationDrawer = (NavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        navigationDrawer.setUp(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawerlayout),toolbar);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent newActivity;
        //noinspection SimplifiableIfStatement
        if (id == R.id.title_activity_help)
        {
            newActivity = new Intent(StartScreen.this, Help.class);
            startActivity(newActivity);
        }
        else if(id == R.id.title_activity_blog)
        {
            newActivity = new Intent(StartScreen.this,BlogPosts.class);
            startActivity(newActivity);
        }
        return super.onOptionsItemSelected(item);
    }
}
