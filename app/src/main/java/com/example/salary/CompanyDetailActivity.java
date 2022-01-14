package com.example.salary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.salary.Adapter.ImageAdapter;
import com.example.salary.Fragment.BottomSheetDialog;
import com.example.salary.data.SalaryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class CompanyDetailActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {

    private ViewPager2 mPager;
    private ImageAdapter pagerAdapter;
    private int num_page = 2;
    private CircleIndicator3 mIndicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetail);

//        ImageView companyImage = findViewById(R.id.companyImage);

        Intent companyInfo = getIntent();
        String companyName = companyInfo.getExtras().getString("companyName");

        JSONObject jsonObject = SalaryData.getInstance().getJsonData();

        try {
            JSONObject companyObject = new JSONObject(jsonObject.getString(companyName));

//            int iResId = getResources().getIdentifier( "@drawable/" + companyObject.getString("logo"), "drawable", this.getPackageName() );
//            ImageView imageView = findViewById( R.id.companyImage );
//            imageView.setImageResource( iResId );

            String companyAddress = companyObject.getString("address");
            String grade = companyObject.getString("grade");
            String senerity = companyObject.getString("senerity");
            String companyId = companyObject.getString("companyId");

            setmPager(companyId);

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

    public void setmPager(String companyId) {
        int[] imageResources = new int[2];
        imageResources[0] = getResources().getIdentifier( "@drawable/" + companyId , "drawable", this.getPackageName() );
        imageResources[1] = getResources().getIdentifier( "@drawable/" + companyId + "_map", "drawable", this.getPackageName() );


        mPager = findViewById(R.id.viewPager);

        pagerAdapter = new ImageAdapter(this, imageResources);
        mPager.setAdapter(pagerAdapter);

        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page, 0);

        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(4);

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            // 스와이프로 인해 한 이미지가 선택되기전 계속 호출
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            //스와이프로 인해 이미지 선택시 호
           public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }
        });
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
