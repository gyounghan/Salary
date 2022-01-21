package com.example.salary.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.salary.Adapter.ImageAdapter;
import com.example.salary.Dialog.CompareCompanyDialog;
import com.example.salary.Dialog.MyPageCustomDialog;
import com.example.salary.Fragment.BottomSheetDialog;
import com.example.salary.MyMarkView;
import com.example.salary.R;
import com.example.salary.data.SalaryData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator3;

public class CompanyDetailActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {

    private ViewPager2 mPager;
    private ImageAdapter pagerAdapter;
    private int num_page = 2;
    private CircleIndicator3 mIndicator;
    private LinearLayout layoutIndicator;
    private String companyName;

    private JSONArray companySalary;
    private FloatingActionButton graphButton;
    private Button select_button;
    private CompareCompanyDialog compareCompanyDialog;
    private DrawerLayout drawerLayout;
    private static List<Entry> entries;
    private static JSONObject jsonObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetail);

//        ImageView companyImage = findViewById(R.id.companyImage);

        Intent companyInfo = getIntent();
        companyName = companyInfo.getExtras().getString("companyName");

        jsonObject = SalaryData.getInstance().getJsonData();

        ImageView companyLogoView = findViewById(R.id.company_logoView);
        drawerLayout = findViewById(R.id.drawerLayout);

        select_button = findViewById(R.id.comparebutton);

        graphButton = findViewById(R.id.graphFloatingButton);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        compareCompanyDialog = new CompareCompanyDialog(this);
        compareCompanyDialog.setPrev_companyName(companyName);
        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compareCompanyDialog.setEntries(entries);
                compareCompanyDialog.show();
            }
        });

        try {
            JSONObject companyObject = new JSONObject(jsonObject.getString(companyName));

            String companyAddress = companyObject.getString("address");
            String grade = companyObject.getString("grade");
            String senerity = companyObject.getString("senerity");
            String companyId = companyObject.getString("companyId");
            int imageId = getResources().getIdentifier( "@drawable/" + companyObject.getString("logo"), "drawable", this.getPackageName() );
            companyLogoView.setImageResource(imageId);

            setmPager(companyId);

            TextView gradeView = findViewById(R.id.grade);
            RatingBar gradeRating = findViewById(R.id.gradeRating);
            TextView senerityView = findViewById(R.id.senerity);

            TextView companyNameTextView = findViewById(R.id.companyName);
            TextView companyAddressTextView = findViewById(R.id.companyAddress);

            companyAddressTextView.setText(companyAddress);
            companyNameTextView.setText(companyName);

            gradeView.setText(grade + " ");
            senerityView.setText("근속연수 : " + senerity);
            gradeRating.setRating(Float.parseFloat(grade));

            companySalary = companyObject.getJSONArray("salary");

            addButton(companySalary);
            setSalaryInfo();
            setLineChart();
            Log.e("companyDetailActivity", "salary:" + companySalary.get(20));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (Intent.ACTION_SEARCH.equals(companyInfo.getAction())) {
            Log.e("[CompanyDetailActivity]", "recevie log : " + companyInfo.getAction());
            String query = companyInfo.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    public void setmPager(String companyId) {
        int[] imageResources = new int[2];
        imageResources[0] = getResources().getIdentifier( "@drawable/" + companyId , "drawable", this.getPackageName() );
        imageResources[1] = getResources().getIdentifier( "@drawable/" + companyId + "_map", "drawable", this.getPackageName() );


        mPager = findViewById(R.id.viewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        pagerAdapter = new ImageAdapter(this, imageResources);
        mPager.setAdapter(pagerAdapter);

        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(4);

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            // 스와이프로 인해 한 이미지가 선택되기전 계속 호출
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            //스와이프로 인해 이미지 선택시 호
           public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(imageResources.length);
    }

    public void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    public void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i=0; i<childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive));
            }
        }
    }

    public void addButton(JSONArray companySalary) throws JSONException {
        LinearLayout salaryView = findViewById(R.id.salaryView);

        for(int i=0;i<companySalary.length();i++) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.setMargins(10, 5, 10, 5);
            Button button = new Button(this);
            button.setBackgroundResource(R.drawable.custom_button);
            button.setText((i+1)+ "연차 연봉 " + companySalary.get(i));
            button.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            button.setId(i);
            button.setLayoutParams(param);
            button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(companyName, v.getId());
                    bottomSheetDialog.show(getSupportFragmentManager(), "bottomsheet");
                    Log.e("[detailActivity]", "onClick");
                }
            });
            salaryView.addView(button);
        }

    }

    //setDrawGridBackground : 그레프에 수치 구분선
    //
    //isDragEnabled : 드레그 true/false
    //isScaleYEnabled : y축으로 늘리기 true/false
    //isScaleXEnabled : x축으로 늘리기 true/false
    //
    //xAxis.isEnabled  : x축 노출 true/false
    //axisLeft.isEnabled  : y축 왼쪽 노출 true/ false
    //axisLeft.setDrawAxisLine : y축 왼쪽 라인 노출 true/ false
    //axisLeft.setDrawGridLines : y축 왼쪽 라인 gride 노출 true/ false
    //axisLeft.setDrawLabels : y축 왼쪽 라인에 라벨 노출  true/ false
    //axisRight.setDrawAxisLine : y 축 오른쪽 라인 노출 true/ fasle
    //axisRight.setDrawGridLines :  y축 오른쪽 라인 gride 노출 true/ false
    //axisRight.isEnabled  : y축 오른쪽 라인 사용 true/ false
    //xAxis.setDrawAxisLine : x축  노출 true/ false
    //xAxis.setDrawGridLines : x축  gride 노출 true/ false
    //
    //yAxis = axisLeft : y축 왼쪽 획득
    //yAxis.axisMaximum  : y축 최고값 셋팅
    //yAxis.axisMinimum : y 축 최저값 셋팅
    //highlightValue : 그래프 선택시 그 수치를 기준으로 나타나는 십자가 선
    //setMode : 라인 차트일 경우 그려지는 종류를 선택할 수 있습니다. (LINEAR/STEPPED/CUBIC_BEZIER/HORIZONTAL_BEZIER)
    //LimitLine : 제한 선으로 그래프 위에 따로 그려집니다.
    //그리고 라인 색과, 하이 라이트 색을 선택 셋팅 할 수 있으며( 셋팅은 R G B 값을 Hex 값으로 입력)
    //setDrawFilled ; 그래프 하단으로 색상 채우기
    //IFillFormatter : 색상을 어느 수치까지 채울지 선택
    //fillDrawable : 채워질 색상 선택 (그라데이션으로도 가능)
    //setDrawCircles : 각 수치를 원으로  표시
    //animateX : 그래프 그릴 시 x축 애니메이션으로
    //animateY : 그래프 그릴 시 Y축 애니메이션으로
    //invalidate : 다시 그리기
    //legend.isEnabled : 그래프 설명 사용 여부 true/false

    @SuppressLint("ClickableViewAccessibility")
    public void setLineChart() throws JSONException {
        MyMarkView marker = new MyMarkView(this, R.layout.custommaker);
        LineChart lineChart = findViewById(R.id.linechart);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.graph_scrollView);
        entries = new ArrayList<>();

        lineChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("[CompanyDeatilActivity]", "linechart event: " + event.getAction());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_SCROLL:
                    case MotionEvent.ACTION_MOVE:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        break;
                    default :
                        break;
                }

                return false;
            }
        });

        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("[CompanyDeatilActivity]", "horizontalScrollView event: " + event.getAction());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_SCROLL:
                    case MotionEvent.ACTION_MOVE:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        break;
                    default :
                        break;
                }

                return false;
            }
        });

        for (int i=0;i<companySalary.length();i++) {
            String value = companySalary.get(i).toString();
            value = value.replace(",","");
            entries.add(new Entry(i+1, Integer.parseInt(value)));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "속성명1");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.invalidate();

        marker.setChartView(lineChart);
        lineChart.setMarker(marker);
    }

    public void setSalaryInfo() {
        float average_salary_rate_rise = 0.0F;
        float sum_salary_diff = 0;
        float prev_salary = 0;
        float now_salary = 0;
        String first_salary = "";
        String max_salary = "";
        try {
            first_salary = companySalary.get(0).toString();
            max_salary = companySalary.get(companySalary.length()-1).toString();
            for (int i=1;i<companySalary.length();i++) {
                prev_salary = Float.parseFloat(companySalary.get(i - 1).toString().replace(",", ""));
                now_salary = Float.parseFloat(companySalary.get(i).toString().replace(",", ""));
                sum_salary_diff = sum_salary_diff + ((float) (now_salary / prev_salary) - 1);
                Log.e("[CompanyDetailActivity]", "sum_salary_diff:" + now_salary + " " + prev_salary + " " + (float) (now_salary / prev_salary) + " " + sum_salary_diff);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (companySalary.length() > 0) {
            average_salary_rate_rise = sum_salary_diff / companySalary.length() * 100;
            TextView average_rate_rise_textView = findViewById(R.id.average_rate_rise);
            average_rate_rise_textView.setText("" + String.format("%.2f", average_salary_rate_rise) + "%");
        }

        TextView first_salary_textView = findViewById(R.id.first_salary);
        TextView max_salary_textView = findViewById(R.id.max_salary);
        first_salary_textView.setText("" + first_salary + "원");
        max_salary_textView.setText("" + max_salary + "원");

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

    @Override
    public void finish() {
        super.finish();
        Log.e("[CompanyDetailActivity]", "finish");
    }

    public void doMySearch(String query) {
        for (int i=0;i<companySalary.length();i++) {
            try {
                if (companySalary.get(i).equals(query)) {
                    return;
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
