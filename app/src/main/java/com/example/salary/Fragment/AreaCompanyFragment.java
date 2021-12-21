package com.example.salary.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.salary.Adapter.ListViewAdapter;
import com.example.salary.R;

public class AreaCompanyFragment extends Fragment {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activtiy_allcompany, container, false);

        // 리스트 추가
        final String[] company = {"부산교통공사", "부산환경공단", "서울교통공사", "대구환경공단", "대구교통공사"};

        ListView list = (ListView) rootView.findViewById(R.id.companyList);

        ListViewAdapter adapter = new ListViewAdapter();

        for (String companyName : company) {
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.beco_logo), companyName, "부산광역시");
            System.out.println("회사이름: " + companyName);
        }
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
