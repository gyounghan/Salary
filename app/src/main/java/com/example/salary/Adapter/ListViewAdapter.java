package com.example.salary.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.salary.R;
import com.example.salary.data.ListViewItem;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    private ArrayList<ListViewItem> companyList = new ArrayList<>();
    private ArrayList<ListViewItem> init_companyList = new ArrayList<>();
    Context mContext = null;
    Filter listFilter;

    public ListViewAdapter(Context context) {
        mContext = context;
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

    // adapter가 어떻게 보여질지
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // listviewitem 리소스를 view로 변환
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        init_companyList.add(companyListviewItem);
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            Log.e("ListViewAdater", "performFiltering :" + constraint);
            if (constraint == null || constraint.length()==0) {
                result.values = init_companyList;
                result.count = init_companyList.size();
            } else {
                ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
                for (ListViewItem item : init_companyList) {
                    Log.e("ListViewAdater", "contains :" + constraint + " " + item.getCompanyName() + " " + item.getCompanyName().contains(constraint));
                    if (item.getCompanyName().contains(constraint)) {
                        itemList.add(item);
                    }
                }
                result.values = itemList;
                result.count = itemList.size();
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            Log.e("ListViewAdater", "publishResult:" + results.count);
            companyList = (ArrayList<ListViewItem>) results.values;
            if (results.count > 0 ) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    @Override
    public Filter getFilter() {
        Log.e("listviewAdater", "getfilter");
        if (listFilter == null) {
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    public ListViewItem getListViewItem(int position) {
        return companyList.get(position);
    }
}