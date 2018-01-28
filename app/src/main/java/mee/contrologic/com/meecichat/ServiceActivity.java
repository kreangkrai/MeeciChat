package mee.contrologic.com.meecichat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mee.contrologic.com.meecichat.fragment.ChatFragment;
import mee.contrologic.com.meecichat.fragment.RegisterFragment;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentServiceFragment,new ChatFragment())
                    .commit();
        }


    }//Main Method
}// Main Class
