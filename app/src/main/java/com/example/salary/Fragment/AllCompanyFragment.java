package com.example.salary.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.salary.Adapter.ListViewAdapter;
import com.example.salary.R;
import com.example.salary.data.CompanyData;
import com.example.salary.data.CompanyDataManager;
import com.example.salary.data.DBHelper;
import com.example.salary.data.SalaryData;

import java.util.ArrayList;

public class AllCompanyFragment extends Fragment {

    public DBHelper dbHelper = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activtiy_allcompany, container, false);

        // 리스트 추가
        final String[] company = {"한국예탁결제원", "한국수자원공사", "부산환경공단", "부산교통공사", "한국자산관리공사", "주택금융공사"};
        ArrayList<CompanyData> companyList = SalaryData.getInstance().getCompanyList();

        ListView list = (ListView) rootView.findViewById(R.id.companyList);

        ListViewAdapter adapter = new ListViewAdapter();

        for (CompanyData companyInfo : companyList) {
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.beco_logo), companyInfo.getCompanyName(), companyInfo.getCompanyAddress());
        }
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), companyList.get(position).getCompanyName(), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
}
