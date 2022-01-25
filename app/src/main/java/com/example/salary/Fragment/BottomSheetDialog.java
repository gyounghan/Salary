package com.example.salary.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.salary.R;
import com.example.salary.data.SalaryData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private View view;

    private BottomSheetListener mListener;

    private Button btn_hide_bt_sheet;

    private String companyName;
    private int index;

    private AdView mAdView;

    public BottomSheetDialog() {

    }

    public BottomSheetDialog(String companyName, int index) {
        this.companyName = companyName;
        this.index = index;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        MobileAds.initialize(getContext());
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mListener = (BottomSheetListener) getContext();
        setDetailSalaryInfo();

        return view;
    }

    private void setDetailSalaryInfo() {
        Log.e("[BottomSheetDialog]", "setDetailSalaryInfo");
        JSONObject jsonObject = SalaryData.getInstance().getJsonData();

        try {
            TextView baseSalaryView = view.findViewById(R.id.baseSalary);
            TextView fixedAllowanceView = view.findViewById(R.id.fixedAllowance);
            TextView employeeBenefitView = view.findViewById(R.id.employeeBenefit);
            TextView performanceAllowanceView = view.findViewById(R.id.performanceAllowance);
            TextView realAmountView = view.findViewById(R.id.realAmount);

            JSONObject companyObject = new JSONObject(jsonObject.getString(companyName));
            JSONArray baseSalary_list = companyObject.getJSONArray("baseSalary");
            JSONArray fixedAllowance_list = companyObject.getJSONArray("fixedAllowance");
            JSONArray employeeBenefit_list = companyObject.getJSONArray("employeeBenefit");
            JSONArray performanceAllowance_list = companyObject.getJSONArray("performanceAllowance");
            JSONArray realAmount_list = companyObject.getJSONArray("realAmount");

            baseSalaryView.setText("기본급          :   " + baseSalary_list.get(index));
            fixedAllowanceView.setText("고정수당     :   " + fixedAllowance_list.get(index));
            employeeBenefitView.setText("복지후생비  :   " + employeeBenefit_list.get(index));
            performanceAllowanceView.setText("실적수당     :   " + performanceAllowance_list.get(index));
            realAmountView.setText("실수령액     :   " + realAmount_list.get(index));
        } catch (Exception e) {

            Log.e("[BottomSheetDialog]", "setDetailSalaryInfo exception:" + e);
        }
    }

    public interface BottomSheetListener {
        void onButtonClickeed(String text);
    }

}
