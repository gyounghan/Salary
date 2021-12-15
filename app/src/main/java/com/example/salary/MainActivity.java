package com.example.salary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.salary.Fragment.AllCompanyFragment;
import com.example.salary.Fragment.AreaCompanyFragment;
import com.example.salary.Fragment.CenterCompanyFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private Fragment allCompany = new AllCompanyFragment();
    private Fragment centerCompany = new CenterCompanyFragment();
    private Fragment areaCompany = new AreaCompanyFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // tabView
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("전체"));
        tabs.addTab(tabs.newTab().setText("중앙"));
        tabs.addTab(tabs.newTab().setText("지방"));

        getSupportFragmentManager().beginTransaction().replace(R.id.container, allCompany).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if (position == 0) {
                    selected = allCompany;
                } else if (position == 1) {
                    selected = centerCompany;
                } else if (position == 2) {
                    selected = areaCompany;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}