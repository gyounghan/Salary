package com.example.salary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.salary.Fragment.AllCompanyFragment;
import com.example.salary.Fragment.LocalCompanyFragment;
import com.example.salary.Fragment.CentralCompanyFragment;
import com.example.salary.data.CompanyData;
import com.example.salary.data.CompanyDataManager;
import com.example.salary.data.DBHelper;
import com.example.salary.data.SalaryData;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private Fragment allCompany = new AllCompanyFragment();
    private Fragment centerCompany = new CentralCompanyFragment();
    private Fragment areaCompany = new LocalCompanyFragment();

    private EditText searchText;

    private CompanyDataManager companyDBManager = null;
    private DBHelper dbHelper;

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
        dbHelper = new DBHelper(MainActivity.this ,3);
        initCompanyInfo();

//        searchText = findViewById(R.id.search);
//
//        searchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String text = searchText.getText().toString();
//            }
//        });
    }

    private void initCompanyInfo() {
        String json = "";

        try {
            InputStream is = getAssets().open("json/companyInfo.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray companyArray = jsonObject.getJSONArray("companyInfo");

            for (int i=0;i<companyArray.length();i++) {
                JSONObject companyObject = companyArray.getJSONObject(i);
                CompanyData company = new CompanyData();

                company.setCompanyName(companyObject.getString("name"));
                company.setCompanyAddress(companyObject.getString("address"));
                company.setCompanyType(companyObject.getString("type"));

                SalaryData.getInstance().addArrayList(company);

                SalaryData.getInstance().printArrayList();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}