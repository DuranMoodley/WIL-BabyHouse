package lalucia.babyhouse.babyhouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WishListProcess1 extends AppCompatActivity {

    private ListView greatestNeeds;
    private ListView babyConsumables;
    private ListView cares;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list_process1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        greatestNeeds = (ListView) findViewById(R.id.lstGreatestNeeds);
        babyConsumables = (ListView) findViewById(R.id.lstBabyConsumables);
        cares = (ListView) findViewById(R.id.lstCarers);
        showListItems();
    }
    //************************************************************************
    public void showListItems()
    {
        FactoryClass objFactory = new FactoryClass(this);
        ArrayList objNeeds = new ArrayList();
        String needsItems = objFactory.readDate("greatestneeds");
        objNeeds.add(needsItems);
        ArrayAdapter<String> arrNeedsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,objNeeds);
        greatestNeeds.setAdapter(arrNeedsAdapter);

        needsItems =  objFactory.readDate("babyconsumables");
        ArrayList objbaby = new ArrayList();
        objbaby.add(needsItems);
        ArrayAdapter<String> arrBabyConsumbalesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,objbaby);
        babyConsumables.setAdapter(arrBabyConsumbalesAdapter);

        needsItems =  objFactory.readDate("carers");
        ArrayList objcares = new ArrayList();
        objcares.add(needsItems);
        ArrayAdapter<String> arrCaresAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,objcares);
        cares.setAdapter(arrCaresAdapter);
    }
}
