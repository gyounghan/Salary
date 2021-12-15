package com.example.salary.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.salary.R;
import com.example.salary.data.ListViewItem;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> companyList = new ArrayList<>();

    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return companyList.size();
    }

    @Override
    public Object getItem(int position) {
        return companyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listviewitem, parent, false);

        }

        ImageView companyImage = (ImageView) convertView.findViewById(R.id.companyView);
        TextView companyName = (TextView) convertView.findViewById(R.id.companyName);
        TextView companyAddress = (TextView) convertView.findViewById(R.id.companyAddress);

        ListViewItem companyListViewItem = companyList.get(position);

        companyImage.setImageDrawable(companyListViewItem.getDrawable());
        companyName.setText(companyListViewItem.getCompanyName());
        companyAddress.setText(companyListViewItem.getCompanyAddress());

        return convertView;
    }

    public void addItem(Drawable image, String name, String address) {
        ListViewItem companyListviewItem = new ListViewItem();
        companyListviewItem.setDrawable(image);
        companyListviewItem.setCompanyName(name);
        companyListviewItem.setCompanyAddress(address);

        companyList.add(companyListviewItem);
    }
}