package com.example.salary.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import com.example.salary.Adapter.CompareListViewAdapter;
import com.example.salary.Adapter.ListViewAdapter;
import com.example.salary.R;
import com.example.salary.data.CompanyData;
import com.example.salary.data.SalaryData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompareCompanyDialog extends Dialog {

    private static CompareCompanyDialog compareCompanyDialog;
    private static Context mContext;

    public CompareListViewAdapter adapter;

    private ListView compareCompanyListView;
    private androidx.appcompat.widget.SearchView searchView;

    private ArrayList<String> companyNameList = new ArrayList<String>();
    private ArrayList<String> companyAddressList = new ArrayList<String>();
    private List<Entry> entries = new ArrayList<>();
    private String prev_companyName;


    public CompareCompanyDialog(Context context) {
        super(context);
        mContext = context;
    }

    public static CompareCompanyDialog getInstance(Context context) {
        if (compareCompanyDialog == null) {
            compareCompanyDialog = new CompareCompanyDialog(context);
        }
        return compareCompanyDialog;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }


    public void setPrev_companyName(String companyName) {
        prev_companyName = companyName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_selectcompare);


        ArrayList<CompanyData> companyList = SalaryData.getInstance().getCompanyList();

        compareCompanyListView = (ListView)findViewById(R.id.comparecompanyList);

        searchView = findViewById(R.id.search_view);

        adapter = new CompareListViewAdapter(mContext);

        for (CompanyData companyInfo : companyList) {
            companyNameList.add(companyInfo.getCompanyName());
            companyAddressList.add(companyInfo.getCompanyAddress());
            adapter.addItem(ContextCompat.getDrawable(mContext,
                    mContext.getResources().getIdentifier(companyInfo.getCompanyLogo(), "drawable", getContext().getPackageName())),
                    companyInfo.getCompanyName(),
                    companyInfo.getCompanyAddress());

            Log.e("[CompareCompanyDialog]", "company: " + companyInfo.getCompanyName());

        }
        compareCompanyListView.setAdapter(adapter);
        compareCompanyListView.setTextFilterEnabled(true);
        compareCompanyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("[AllCompanyFragement]", "clickListView: " + id);
                addLinechart(adapter.getListViewItem(position).getCompanyName());
                Toast.makeText(getContext(), adapter.getListViewItem(position).getCompanyName(), Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                Log.e("fragment", "afterTextChanged : " + text.length());
//                if (text.length() > 0) {
//                    compareCompanyListView.setFilterText(text);
//                } else {
//                    compareCompanyListView.clearTextFilter();
//                }
                ((CompareListViewAdapter)compareCompanyListView.getAdapter()).getFilter().filter(newText) ;
                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
        }
        return super.dispatchTouchEvent(ev);
    }


    public void addLinechart(String companyName) {
        JSONObject jsonObject = SalaryData.getInstance().getJsonData();
        List<Entry> compareCompanyEntries = new ArrayList<>();
        LineChart lineChart = ((Activity)mContext).findViewById(R.id.linechart);
        JSONArray companySalary;
        try {
            companySalary = new JSONObject(jsonObject.getString(companyName)).getJSONArray("salary");

            for (int i = 0; i < companySalary.length(); i++) {
                String value = companySalary.get(i).toString();
                value = value.replace(",", "");
                compareCompanyEntries.add(new Entry(i + 1, Integer.parseInt(value)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LineData chartData = new LineData();
        LineDataSet set1 = new LineDataSet(entries, prev_companyName);
//        set1.setColor(Color.rgb(255, 155, 155));
        set1.setLineWidth(2);
        set1.setCircleRadius(6);
        set1.setCircleColor(Color.parseColor("#FFA1B4DC"));
        set1.setColor(Color.parseColor("#FFA1B4DC"));
        set1.setDrawCircleHole(true);
        set1.setDrawCircles(true);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawValues(false);
        LineDataSet set2 = new LineDataSet(compareCompanyEntries, companyName);
//        set2.setColor(Color.rgb(100,100,100));
        set2.setLineWidth(1);
        set2.setCircleRadius(4);
        set2.setCircleColor(Color.parseColor("#FFFFBBC5"));
        set2.setColor(Color.parseColor("#FFFFBBC5"));
        set2.setDrawCircleHole(true);
        set2.setDrawCircles(true);
        set2.setDrawHorizontalHighlightIndicator(false);
        set2.setDrawHighlightIndicators(false);
        set2.setDrawValues(false);
        chartData.addDataSet(set1);
        chartData.addDataSet(set2);

        lineChart.setData(chartData);
        lineChart.invalidate();

        Button compareCompanySelectButton = ((Activity) mContext).findViewById(R.id.comparebutton);
        compareCompanySelectButton.setText(companyName);

        dismiss();
    }
}
