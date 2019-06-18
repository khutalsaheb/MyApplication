package com.sandesh.tractor;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.sandesh.tractor.DatabaseHelper.CUSTOMER_COLUMN_PAIDAMOUNT;
import static com.sandesh.tractor.DatabaseHelper.CUSTOMER_COLUMN_REMAININGAMOUNT;
import static com.sandesh.tractor.DatabaseHelper.CUSTOMER_COLUMN_STARTTIME;
import static com.sandesh.tractor.DatabaseHelper.CUSTOMER_COLUMN_TOTALAMOUNT;
import static com.sandesh.tractor.DatabaseHelper.CUSTOMER_COLUMN_WORKDATE;
import static com.sandesh.tractor.DatabaseHelper.CUSTOMER_COLUMN_WORKNAME;

public class Customer_Work_Record extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Customer_Adapter completeJobAdapter;
    private DatabaseHelper databaseHelper;
    private final List<UsersModel> usersModels = new ArrayList<>();
    private AppCompatEditText workdate, workname, startingtime, totalamount, paidamount;
    private AppCompatTextView remainingamount;
    private int mYear, mMonth, mDay;
    private int a;
    private int b;
    private int Total;
    private long User_Cust_ID;
    private String Cust_name;
    private String Cust_Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_work_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        User_Cust_ID = intent.getLongExtra("user_cust_id", 1L);
        Cust_name = intent.getStringExtra("user_cust_name");
        Cust_Mobile = intent.getStringExtra("user_cust_mobile");
        recyclerView = findViewById(R.id.recyclerview);
        // Get Current Date
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        GetData();

        completeJobAdapter = new Customer_Adapter(usersModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(completeJobAdapter);


    }

    private void GetData() {

        Cursor cursor = databaseHelper.getAllCustomer(User_Cust_ID);

        if (cursor.moveToFirst()) {
            do {
                usersModels.add(new UsersModel(cursor.getString(cursor.getColumnIndex(CUSTOMER_COLUMN_WORKNAME)),
                        cursor.getString(cursor.getColumnIndex(CUSTOMER_COLUMN_STARTTIME)),
                        cursor.getString(cursor.getColumnIndex(CUSTOMER_COLUMN_WORKDATE)),
                        cursor.getString(cursor.getColumnIndex(CUSTOMER_COLUMN_TOTALAMOUNT)),
                        cursor.getString(cursor.getColumnIndex(CUSTOMER_COLUMN_PAIDAMOUNT)),
                        cursor.getString(cursor.getColumnIndex(CUSTOMER_COLUMN_REMAININGAMOUNT))


                ));
            } while (cursor.moveToNext());


        }
    }


    @SuppressLint("SetTextI18n")
    public void AddWork(View view) {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        @SuppressLint("InflateParams") View sheetView = getLayoutInflater().inflate(R.layout.customer_work_entry, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        workdate = sheetView.findViewById(R.id.workdate);
        workname = sheetView.findViewById(R.id.workname);
        startingtime = sheetView.findViewById(R.id.startingtime);
        totalamount = sheetView.findViewById(R.id.totalamount);
        paidamount = sheetView.findViewById(R.id.paidamount);
        remainingamount = sheetView.findViewById(R.id.remainingamount);

        workdate.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date()));
        workdate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (views, year, monthOfYear, dayOfMonth) -> workdate.setText((dayOfMonth + "-" + (monthOfYear + 1) + "-" + year)), mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        totalamount.addTextChangedListener(new TextChangedListener() {

            @Override
            public void numberEntered(int number) {
                a = number;
                updateTotal();
            }
        });
        paidamount.addTextChangedListener(new TextChangedListener() {

            @Override
            public void numberEntered(int number) {
                b = number;
                updateTotal();
            }
        });


        AppCompatButton submit_btn = sheetView.findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(view1 -> {


            String workdates = workdate.getText().toString();
            String worknames = workname.getText().toString();
            String startingtimes = startingtime.getText().toString();
            String totalamounts = totalamount.getText().toString();
            String paidamounts = paidamount.getText().toString();

            if (workdates.matches("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Work Date", Toast.LENGTH_LONG).show();
            } else if (worknames.matches("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Complete Work", Toast.LENGTH_LONG).show();
            } else if (startingtimes.matches("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Work Total Time", Toast.LENGTH_LONG).show();
            } else if (totalamounts.matches("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Total Amount", Toast.LENGTH_LONG).show();
            } else if (paidamounts.matches("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Paid Amount", Toast.LENGTH_LONG).show();
            } else {
                databaseHelper.addcustomer(String.valueOf(User_Cust_ID),Cust_name,Cust_Mobile, workdates, worknames, startingtimes, totalamounts, paidamounts, String.valueOf(Total));
                mBottomSheetDialog.dismiss();
                Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();
                usersModels.clear();
                GetData();
                recyclerView.setAdapter(completeJobAdapter);
                completeJobAdapter.notifyDataSetChanged();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void updateTotal() {
        Total = a - b;
        remainingamount.setText("" + Total);

    }

    private abstract class TextChangedListener implements TextWatcher {

        protected abstract void numberEntered(int number);

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            try {
                int parsedInt = Integer.parseInt(text);
                numberEntered(parsedInt);
            } catch (NumberFormatException e) {
                Log.w(getPackageName(), "Could not parse '" + text + "' as a number", e);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

}
