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
import com.example.salary.Utils.Utilsdd;
import com.example.salary.data.CompanyData;
import com.example.salary.data.SalaryData;

import java.util.ArrayList;

public class LocalCompanyFragment extends Fragment {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activtiy_allcompany, container, false);

        // 리스트 추가

        ArrayList<CompanyData> localcompanyList = SalaryData.getInstance().getLocalCompanyList();


        ListView list = (ListView) rootView.findViewById(R.id.companyList);

        ListViewAdapter adapter = new ListViewAdapter(getContext());

        for (CompanyData localCompany : localcompanyList) {
            adapter.addItem(ContextCompat.getDrawable(getContext(),
                    getResources().getIdentifier(localCompany.getCompanyLogo(), "drawable", getContext().getPackageName())),
                    localCompany.getCompanyName(),
                    localCompany.getCompanyAddress(),
                    Utilsdd.FRAGMENT_LOCALCOMPANY);
        }
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), localcompanyList.get(position).getCompanyName(), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
}
