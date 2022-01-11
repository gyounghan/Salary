package com.example.salary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salary.Fragment.BottomSheetDialog;
import com.example.salary.data.SalaryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CompanyDetailActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetail);

        TextView companyText = findViewById(R.id.companyInfo);
        ImageView companyImage = findViewById(R.id.companyImage);


        Intent companyInfo = getIntent();
        String companyName = companyInfo.getExtras().getString("companyName");
        companyText.setText(companyName);


        JSONObject jsonObject = SalaryData.getInstance().getJsonData();

        try {
            JSONObject companyObject = new JSONObject(jsonObject.getString(companyName));

            int iResId = getResources().getIdentifier( "@drawable/" + companyObject.getString("logo"), "drawable", this.getPackageName() );
            ImageView imageView = findViewById( R.id.companyImage );
            imageView.setImageResource( iResId );

            String companyAddress = companyObject.getString("address");

            TextView companyNameTextView = findViewById(R.id.companyName);
            TextView companyAddressTextView = findViewById(R.id.companyAddress);

            companyAddressTextView.setText(companyAddress);
            companyNameTextView.setText(companyName);

            JSONArray companySalary = companyObject.getJSONArray("salary");

            addButton(companySalary);
            Log.e("companyDetailActivity", "salary:" + companySalary.get(20));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addButton(JSONArray companySalary) throws JSONException {
        LinearLayout salaryView = findViewById(R.id.salaryView);

        LinearLayout detailPage = findViewById(R.id.detailPage);
        Animation translateUpAnim = AnimationUtils.loadAnimation(this, R.anim.translate_up);

        Button close_button = findViewById(R.id.close);
        close_button.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomsheet");

            }
        });

        for(int i=0;i<companySalary.length();i++) {
            Button button = new Button(this);
            System.out.println(String.valueOf(companySalary.get(i)));
            button.setText(String.valueOf(companySalary.get(i)));
            button.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    detailPage.setVisibility(View.VISIBLE);
                    detailPage.startAnimation(translateUpAnim);
                    Log.e("[detailActivity]", "onClick");
                }
            });
            salaryView.addView(button);
        }

    }

    @Override
    public void onButtonClickeed(String text) {
        
    }

    private class SlidingPageAnimationListner implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            Log.e("[detailActivity]", "onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.e("[detailActivity]", "onAnimationEnd");

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.e("[detailActivity]", "onAnimationRepeat");

        }
    }
}
