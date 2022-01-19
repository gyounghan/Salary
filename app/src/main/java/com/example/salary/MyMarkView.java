package com.example.salary;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.text.DecimalFormat;

public class MyMarkView extends MarkerView {

    private TextView graph_markerView;
    private Context mContext;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMarkView(Context context, int layoutResource) {
        super(context, layoutResource);
        mContext = context;
        graph_markerView = findViewById(R.id.graph_markerView);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            graph_markerView.setText("" + Utils.formatNumber((int)e.getY(), 0, true) + "원");
        } else {
            graph_markerView.setText("" + Utils.formatNumber((int)e.getY(), 0, true) + "원");
        }

        TextView year = (TextView) ((Activity) mContext).findViewById(R.id.year);
        TextView salary = (TextView) ((Activity) mContext).findViewById(R.id.year_salary);

        DecimalFormat formatter = new DecimalFormat("###,###");
        year.setText(Math.round(e.getX()) + "년차 연봉");
        salary.setText(formatter.format(e.getY()) + "원");
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
