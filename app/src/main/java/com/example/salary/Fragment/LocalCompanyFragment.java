package com.example.salary.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.salary.Activity.CompanyDetailActivity;
import com.example.salary.Adapter.ListViewAdapter;
import com.example.salary.R;
import com.example.salary.Utils.Utile;
import com.example.salary.data.CompanyData;
import com.example.salary.data.SalaryData;

import java.util.ArrayList;

public class LocalCompanyFragment extends Fragment {

    private SearchView searchView;
    private ListView listView;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activtiy_allcompany, container, false);

        // 리스트 추가

        ArrayList<CompanyData> localcompanyList = SalaryData.getInstance().getLocalCompanyList();

        searchView = (SearchView) rootView.findViewById(R.id.search);

        listView = (ListView) rootView.findViewById(R.id.companyList);

        ListViewAdapter adapter = new ListViewAdapter(getContext());

        for (CompanyData localCompany : localcompanyList) {
            adapter.addItem(ContextCompat.getDrawable(getContext(),
                    getResources().getIdentifier(localCompany.getCompanyLogo(), "drawable", getContext().getPackageName())),
                    localCompany.getCompanyName(),
                    localCompany.getCompanyAddress(),
                    Utile.FRAGMENT_LOCALCOMPANY);
        }
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), localcompanyList.get(position).getCompanyName(), Toast.LENGTH_SHORT).show();
                Log.e("[AllCompanyFragement]", "clickListView: " + id);
                Intent companyInfo = new Intent(getContext(), CompanyDetailActivity.class);
                companyInfo.putExtra("companyName", adapter.getListViewItem(position).getCompanyName());
                startActivity(companyInfo);
                Toast.makeText(getContext(), adapter.getListViewItem(position).getCompanyName(), Toast.LENGTH_SHORT).show();

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                Log.e("fragment", "afterTextChanged : " + text.length());
//                if (text.length() > 0) {
//                    listView.setFilterText(text);
//                } else {
//                    listView.clearTextFilter();
//                }
                ((ListViewAdapter)listView.getAdapter()).getFilter().filter(newText) ;
                return true;
            }
        });

        return rootView;

    }
}
