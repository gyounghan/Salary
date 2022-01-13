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
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

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

        ImageView companyImage = findViewById(R.id.companyImage);


        Intent companyInfo = getIntent();
        String companyName = companyInfo.getExtras().getString("companyName");

        JSONObject jsonObject = SalaryData.getInstance().getJsonData();

        try {
            JSONObject companyObject = new JSONObject(jsonObject.getString(companyName));

            int iResId = getResources().getIdentifier( "@drawable/" + companyObject.getString("logo"), "drawable", this.getPackageName() );
            ImageView imageView = findViewById( R.id.companyImage );
            imageView.setImageResource( iResId );

            String companyAddress = companyObject.getString("address");
            String grade = companyObject.getString("grade");
            String senerity = companyObject.getString("senerity");

            TextView gradeView = findViewById(R.id.grade);
            RatingBar gradeRating = findViewById(R.id.gradeRating);
            TextView senerityView = findViewById(R.id.senerity);

            TextView companyNameTextView = findViewById(R.id.companyName);
            TextView companyAddressTextView = findViewById(R.id.companyAddress);

            companyAddressTextView.setText(companyAddress);
            companyNameTextView.setText(companyName);

            gradeView.setText(grade);
            senerityView.setText("근속연수 : " + senerity);
            gradeRating.setRating(Float.parseFloat(grade));

            JSONArray companySalary = companyObject.getJSONArray("salary");

            addButton(companySalary);
            Log.e("companyDetailActivity", "salary:" + companySalary.get(20));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addButton(JSONArray companySalary) throws JSONException {
        LinearLayout salaryView = findViewById(R.id.salaryView);

        for(int i=0;i<companySalary.length();i++) {
            Button button = new Button(this);
            System.out.println(String.valueOf(companySalary.get(i)));
            button.setText(String.valueOf(companySalary.get(i)));
            button.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                    bottomSheetDialog.show(getSupportFragmentManager(), "bottomsheet");
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
