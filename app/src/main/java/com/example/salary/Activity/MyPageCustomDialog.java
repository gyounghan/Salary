package com.example.salary.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.salary.Adapter.ListViewAdapter;
import com.example.salary.Fragment.MypageFragment;
import com.example.salary.R;
import com.example.salary.data.PreferenceManager;

import java.util.HashSet;

public class MyPageCustomDialog extends Dialog {

    public ListViewAdapter adapter;

    private Context mContext;
    private String companyName;
    private CheckBox mypageCheckbox;

    public MyPageCustomDialog(Context context, String companyName, CheckBox checkbox) {
        super(context);
        mContext = context;
        this.companyName = companyName;
        this.mypageCheckbox = checkbox;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mypagepopup);

        Button confirm_button = (Button) findViewById(R.id.confirm_button);
        Button reject_button = (Button) findViewById(R.id.reject_button);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = MypageFragment.getInstance().getAdapter();
                adapter.removeItem(companyName);
                adapter.notifyDataSetChanged();
                mypageCheckbox.setChecked(false);
                PreferenceManager.getInstance().removeMypageCompanyList(companyName);
                dismiss();
            }
        });

        reject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mypageCheckbox.setChecked(true);
                dismiss();
            }
        });
    }
}
