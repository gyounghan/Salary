package com.example.salary.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.salary.Activity.CompanyDetailActivity;
import com.example.salary.Dialog.MyPageCustomDialog;
import com.example.salary.Adapter.ListViewAdapter;
import com.example.salary.R;
import com.example.salary.Utils.Utile;
import com.example.salary.data.CompanyData;
import com.example.salary.data.SalaryData;

import java.util.ArrayList;

public class MypageFragment extends Fragment {

    public static MypageFragment mypageFragment = null;

//    private EditText searchText;
    private SearchView searchView;

    private ArrayList<String> companyNameList = new ArrayList<String>();
    private ArrayList<String> companyAddressList = new ArrayList<String>();
    public static ListViewAdapter adapter;
    public ListView listView;

    public MyPageCustomDialog myPageCustomDialog;

    public MypageFragment() {

    }

    public static MypageFragment getInstance() {
        if (mypageFragment == null) {
            mypageFragment = new MypageFragment();
        }
        return mypageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activtiy_allcompany, container, false);

//        searchText = (EditText) rootView.findViewById(R.id.search);
        searchView = (SearchView) rootView.findViewById(R.id.search);

        // 리스트 추가
        ArrayList<CompanyData> companyList = SalaryData.getInstance().getMyPageCompanyList();

        listView = (ListView) rootView.findViewById(R.id.companyList);

        adapter = new ListViewAdapter(getContext());

        for (CompanyData companyInfo : companyList) {
            companyNameList.add(companyInfo.getCompanyName());
            companyAddressList.add(companyInfo.getCompanyAddress());
            adapter.addItem(ContextCompat.getDrawable(getContext(),
                    getResources().getIdentifier(companyInfo.getCompanyLogo(), "drawable", getContext().getPackageName())),
                    companyInfo.getCompanyName(),
                    companyInfo.getCompanyAddress(),
                    Utile.FRAGMENT_MYPAGE);
        }


        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("[MyCompanyFragement]", "clickListView");
                Intent companyInfo = new Intent(getContext(), CompanyDetailActivity.class);
                companyInfo.putExtra("companyName", adapter.getListViewItem(position).getCompanyName());
                startActivity(companyInfo);
                Toast.makeText(getContext(), adapter.getListViewItem(position).getCompanyName(), Toast.LENGTH_SHORT).show();
            }
        });
        //edittext 이벤트
//        searchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.e("fragment", "beforeTextChanged");
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e("fragment", "onTextChanged");
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable search) {
//                String text = search.toString();
//                Log.e("fragment", "afterTextChanged : " + text.length());
//                if (text.length() > 0) {
//                    listView.setFilterText(text);
//                } else {
//                    listView.clearTextFilter();
//                }
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                Log.e("fragment", "afterTextChanged : " + text.length());
                if (text.length() > 0) {
                    listView.setFilterText(text);
                } else {
                    listView.clearTextFilter();
                }
                return true;
            }
        });

        return rootView;

    }

    public void showDialog(String companyName, CheckBox checkbox) {
        myPageCustomDialog = new MyPageCustomDialog(getContext(), companyName, checkbox);
        myPageCustomDialog.show();
    }

    public ListViewAdapter getAdapter() {
        return adapter;
    }
}
