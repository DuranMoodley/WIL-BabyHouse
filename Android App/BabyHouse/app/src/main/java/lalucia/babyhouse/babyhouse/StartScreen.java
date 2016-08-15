package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView homePageDetailstv = (TextView) findViewById(R.id.tvDetailsHome);
        FactoryClass objFactory = new FactoryClass(this);
        assert homePageDetailstv != null;
        homePageDetailstv.setText(objFactory.readDate("homepage"));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.title_activity_help)
        {
            Intent newActivity = new Intent();
            newActivity = new Intent(StartScreen.this, Help.class);
            startActivity(newActivity);
        }
        return super.onOptionsItemSelected(item);
    }
}
