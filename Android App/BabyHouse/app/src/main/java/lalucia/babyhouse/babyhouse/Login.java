package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    //***************************************************
    public void loginButtonClick(View v)
    {
        Intent newActivity;
        newActivity = new Intent(Login.this, StartScreen.class);
        startActivity(newActivity);
    }
}
