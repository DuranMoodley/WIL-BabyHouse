package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class HelpScreen1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView contentHelp = (TextView) findViewById(R.id.txtHelpContent);
        FactoryClass objFactory = new FactoryClass(this);
        String helpText = objFactory.readDate("help");
        assert contentHelp != null;
        contentHelp.setText(helpText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent newAct = new Intent(HelpScreen1.this,BabyHouseMap.class);
                startActivity(newAct);
            }
        });
    }
}
