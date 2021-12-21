package com.example.salary.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.salary.R;
import com.example.salary.data.CompanyDataManager;

public class AllCompanyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activtiy_allcompany, container, false);

        // 리스트 추가
        final String[] company = {"한국예탁결제원", "한국수자원공사", "부산환경공단", "부산교통공사", "한국자산관리공사", "주택금융공사"};

        ListView list = (ListView) rootView.findViewById(R.id.companyList);

        String[] companyList = CompanyDataManager.getCompanyList();
//        String success = CompanyDataManager.getCompanyList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, companyList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), company[position], Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
}
