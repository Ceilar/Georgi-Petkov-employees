package com.example.georgi_petkov_employees;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pairs> pairsList = new ArrayList<Pairs>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int requestcode = 1;
    //private List<Employees> employeesList = new ArrayList<>();
    private Map<String, List<Employees>> projectMap = new HashMap<String, List<Employees>>();

    public void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        Context context = getApplicationContext();
        if (requestcode == requestcode && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            readfile(uri);
            Toast.makeText(context, uri.getPath(), Toast.LENGTH_LONG).show();
        }
    }

    public void openfilechooser(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, requestcode);
    }

    public void readfile(Uri uri) {
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");

                    Employees employee = new Employees();
                    employee.setEmpID(Integer.parseInt(tokens[0]));
                    employee.setProjectID(Integer.parseInt(tokens[1]));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateFrom = format.parse(tokens[2]);
                    employee.setDateFrom(dateFrom);
                    //проверка дали DateТо < DateFrom
                    if (tokens[3].equals("NULL")) {
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        employee.setDateTo(format.parse(currentDate));
                    } else {
                        Date dateTo = format.parse(tokens[3]);
                        employee.setDateTo(dateTo);
                    }

                    if (!projectMap.containsKey(tokens[1])) {
                        projectMap.put(tokens[1], new ArrayList<Employees>());
                    }
                    projectMap.get(tokens[1]).add(employee);


                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pairsList = daysWorked(projectMap);
        Intent intent = new Intent(MainActivity.this, DatagridActivity.class);
        intent.putExtra("Pairs List", pairsList);
        startActivity(intent);


    }

    public ArrayList<Pairs> daysWorked(Map<String, List<Employees>> projectMap) {
        long daysworked = 0;
        ArrayList<Pairs> pairList = new ArrayList<>();
        Pairs pair;
        for (String project : projectMap.keySet()) {
            Collections.sort(projectMap.get(project));
            for (int i = 0; i <= projectMap.get(project).size() - 2; i++) {
                for (int j = i + 1; j <= projectMap.get(project).size() - 1; j++) {
                    if (projectMap.get(project).get(i).getDateTo().after(projectMap.get(project).get(j).getDateFrom())) {
                        if (projectMap.get(project).get(i).getDateTo().before(projectMap.get(project).get(j).getDateTo())) {
                            daysworked = projectMap.get(project).get(i).getDateTo().getTime() - projectMap.get(project).get(j).getDateFrom().getTime();

                        } else {
                            daysworked = projectMap.get(project).get(j).getDateTo().getTime() - projectMap.get(project).get(j).getDateFrom().getTime();
                        }
                        daysworked = TimeUnit.DAYS.convert(daysworked, TimeUnit.MILLISECONDS);
                        pair = new Pairs(projectMap.get(project).get(i).getEmpID(), projectMap.get(project).get(j).getEmpID(), projectMap.get(project).get(j).getProjectID(), (int) daysworked);
                        pairList.add(pair);
                        //fill arraylist<Emp1,Emp2,ProjID,daysworked> then visualise
                    }
                }
            }
        }
        return pairList;
    }


}