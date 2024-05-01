package com.example.monitoringmenudapur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;
import com.example.monitoringmenudapur.fragments.FragmentHome;
import com.example.monitoringmenudapur.fragments.FragmentAcc;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new FragmentHome());

        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.home_menu){
            fragment = new FragmentHome();
        } else if (id == R.id.profile_menu) {
            fragment = new FragmentAcc();
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fr_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}