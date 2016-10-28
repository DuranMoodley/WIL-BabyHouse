package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ContactProcess extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_process);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button go = (Button) findViewById(R.id.btnGo);
        if (go != null)
        {
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent googleIntent = new Intent(Intent.ACTION_VIEW);
                    googleIntent.setData(Uri.parse("geo:-29.745358, 31.057521?q=Baby house"));
                    startActivity(googleIntent);
                }
            });
        }
    }
}
