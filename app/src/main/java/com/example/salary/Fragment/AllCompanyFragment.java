package com.example.salary.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.salary.Adapter.ListViewAdapter;
import com.example.salary.R;
import com.example.salary.data.CompanyData;
import com.example.salary.data.DBHelper;
import com.example.salary.data.SalaryData;

import java.util.ArrayList;

public class AllCompanyFragment extends Fragment {

    public DBHelper dbHelper = null;
    private EditText searchText;

    private ArrayList<String> companyNameList = new ArrayList<String>();
    private ArrayList<String> companyAddressList = new ArrayList<String>();
    private ListViewAdapter adapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activtiy_allcompany, container, false);

        searchText = (EditText) rootView.findViewById(R.id.search);

        // 리스트 추가
        ArrayList<CompanyData> companyList = SalaryData.getInstance().getCompanyList();

        listView = (ListView) rootView.findViewById(R.id.companyList);

        adapter = new ListViewAdapter(getContext());

        for (CompanyData companyInfo : companyList) {
            companyNameList.add(companyInfo.getCompanyName());
            companyAddressList.add(companyInfo.getCompanyAddress());
            adapter.addItem(ContextCompat.getDrawable(getContext(), getResources().getIdentifier(companyInfo.getCompanyLogo(),"drawable", getContext().getPackageName())), companyInfo.getCompanyName(), companyInfo.getCompanyAddress());
        }
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("[AllCompanyFragement]", "clickListView: " + id);
                Intent companyInfo = new Intent(getContext(), CompanyDetailActivity.class);
                companyInfo.putExtra("companyName", adapter.getListViewItem(position).getCompanyName());
                startActivity(companyInfo);
                Toast.makeText(getContext(), adapter.getListViewItem(position).getCompanyName(), Toast.LENGTH_SHORT).show();
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("fragment", "beforeTextChanged");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("fragment", "onTextChanged");

            }

            @Override
            public void afterTextChanged(Editable search) {
                String text = search.toString();
                Log.e("fragment", "afterTextChanged : " + text.length());
                if (text.length() > 0) {
                    listView.setFilterText(text);
                } else {
                    listView.clearTextFilter();
                }
            }
        });

        return rootView;

    }

}
