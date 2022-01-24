package com.example.salary.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.salary.Fragment.AllCompanyFragment;
import com.example.salary.Fragment.LocalCompanyFragment;
import com.example.salary.Fragment.CentralCompanyFragment;
import com.example.salary.Fragment.MypageFragment;
import com.example.salary.R;
import com.example.salary.data.CompanyData;
import com.example.salary.data.CompanyDataManager;
import com.example.salary.data.DBHelper;
import com.example.salary.data.PreferenceManager;
import com.example.salary.data.SalaryData;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private Fragment allCompany = new AllCompanyFragment();
    private Fragment centerCompany = new CentralCompanyFragment();
    private Fragment areaCompany = new LocalCompanyFragment();
    private Fragment myCompany = MypageFragment.getInstance();

    private SalaryData salaryData = null;

    private EditText searchText;
    private PreferenceManager prefs = null;

    private CompanyDataManager companyDBManager = null;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Salary);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salaryData = SalaryData.getInstance();
        // tabView
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("전체"));
        tabs.addTab(tabs.newTab().setText("중앙"));
        tabs.addTab(tabs.newTab().setText("지방"));
        tabs.addTab(tabs.newTab().setText("마이페이지"));

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
                } else if (position == 3) {
                    selected = myCompany;
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
        PreferenceManager.preferenceManager.getInstance().setContext(getApplication());
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
//                 String text = searchText.getText().toString();
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
            String jsonValue = jsonObject.getString("companyInfo");
            JSONObject companyInfo = new JSONObject(jsonValue);

            salaryData.setJsonData(companyInfo);
            salaryData.clearArrayList();
            Iterator i = companyInfo.keys();

            while(i.hasNext())
            {
                String companyName= i.next().toString();
                JSONObject companyObject = new JSONObject(companyInfo.getString(companyName));
                CompanyData company = new CompanyData();

                company.setCompanyName(companyName);
                company.setCompanyAddress(companyObject.getString("address"));
                company.setCompanyType(companyObject.getString("type"));
                company.setCompanyLogo(companyObject.getString("logo"));

                salaryData.addArrayList(company);

                salaryData.printArrayList();

                Log.d("[han]",companyName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}