package mee.contrologic.com.meecichat;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mee.contrologic.com.meecichat.fragment.ChatFragment;
import mee.contrologic.com.meecichat.fragment.RegisterFragment;

public class ServiceActivity extends AppCompatActivity {


//    Explicit
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

//        add Fragment

        addFragment(savedInstanceState);

//        Create toolbar

        createToolbar();


    }//Main Method

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);

//        Find Display Name
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String displayNameString = firebaseUser.getDisplayName();
        getSupportActionBar().setTitle(displayNameString);

        drawerLayout = findViewById(R.id.myDrawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(ServiceActivity.this,drawerLayout,
                R.string.open,R.string.close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return  true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void addFragment(Bundle savedInstanceState) {
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentServiceFragment,new ChatFragment())
                    .commit();
        }
    }
}// Main Class
