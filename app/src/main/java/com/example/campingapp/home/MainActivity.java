package com.example.campingapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.campingapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AppDatabase db = AppDatabase.getInstance(this);
        /*
        Button btn = (Button) findViewById(R.id.db_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<CampingEntity> a=db.campingDao().getAll();
                        CampingEntity b=a.get(0);
                        System.out.println(b.getId());
                    }
                });
                th.start();


            }
        });

         */
        bottomNav = (BottomNavigationView) findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFragment()).commit();
                        break;
                    case R.id.nav_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new SearchFragment()).commit();
                        break;
                    case R.id.nav_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new AccountFragment()).commit();
                        break;
                }
                return true;
            }
        });


    }



}