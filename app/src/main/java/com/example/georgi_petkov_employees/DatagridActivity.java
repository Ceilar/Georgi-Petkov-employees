package com.example.georgi_petkov_employees;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatagridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datagrid);
        ArrayList<Pairs> pairsList = new ArrayList<Pairs>();
        TableLayout table = (TableLayout) DatagridActivity.this.findViewById(R.id.table);
        pairsList = (ArrayList<Pairs>) getIntent().getSerializableExtra("Pairs List");
        for (int i = 0; i < pairsList.size(); i++) {
            Collections.sort(pairsList);
            Collections.reverse(pairsList);
            String days = Integer.toString(pairsList.get(i).getDaysWorked());
            String employee1 = Integer.toString(pairsList.get(i).getEmpID1());
            String employee2 = Integer.toString(pairsList.get(i).getEmpID2());
            String projID = Integer.toString(pairsList.get(i).getProjID());
            TableRow row = (TableRow) LayoutInflater.from(DatagridActivity.this).inflate(R.layout.attrb_row, null);
            ((TextView) row.findViewById(R.id.textView4)).setText(employee1);
            ((TextView) row.findViewById(R.id.textView3)).setText(employee2);
            ((TextView) row.findViewById(R.id.textView2)).setText(projID);
            ((TextView) row.findViewById(R.id.textView)).setText(days);
            table.addView(row);
        }
        table.requestLayout();
    }

}