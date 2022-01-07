package com.example.salary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CompanyDetailActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetail);

        TextView companyText = findViewById(R.id.companyInfo);
        Intent companyInfo = getIntent();
        String companyName = companyInfo.getExtras().getString("companyName");
        companyText.setText(companyName);
    }
}
