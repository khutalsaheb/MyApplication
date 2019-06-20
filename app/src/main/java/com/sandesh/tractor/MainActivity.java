package com.sandesh.tractor;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements User_Adapter.CompleteJobListener {
    private RecyclerView recyclerView;
    private User_Adapter completeJobAdapter;
    private DatabaseHelper databaseHelper;
    private String username;
    private String mobilenumber;
    private List<UsersModel> completeJobList = new ArrayList<>();
    private AppCompatEditText user_name_txt, user_mobile_txt;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerview);
        GetData();
        completeJobAdapter = new User_Adapter(completeJobList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(completeJobAdapter);
        // CompleteJobsTemp();


    }

    private void GetData() {

        Cursor cursor = databaseHelper.getAllEmployees();

        if (cursor.moveToFirst()) {
            do {
                completeJobList.add(new UsersModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)


                ));
            } while (cursor.moveToNext());


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContact(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchContact(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
        // return true;
    }

    private void searchContact(String keyword) {

        completeJobList.clear();
        Cursor cursor = databaseHelper.search(keyword);

        if (cursor.moveToFirst()) {
            do {
                completeJobList.add(new UsersModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)


                ));
            } while (cursor.moveToNext());


        }

        recyclerView.setAdapter(completeJobAdapter);
        completeJobAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_users clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            EXPORT();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void EXPORT() {
        Cursor c = databaseHelper.getAllCustomer();
        try {
            int rowcount;
            int colcount;
            File sdCardDir = Environment.getExternalStorageDirectory();
            String filename = "Tractor.csv";
            // the name of the file to export with

            File saveFile = new File(sdCardDir, filename);
           /* if(!saveFile.exists()){
                saveFile.mkdirs();
            }*/

            //  File saveFile = new File(sdCardDir, filename);
            FileWriter fw = new FileWriter(saveFile);


            BufferedWriter bw = new BufferedWriter(fw);
            rowcount = c.getCount();
            colcount = c.getColumnCount();
            if (rowcount > 0) {
                c.moveToFirst();

                for (int i = 0; i < colcount; i++) {
                    if (i != colcount - 1) {

                        bw.write(c.getColumnName(i) + ",");

                    } else {

                        bw.write(c.getColumnName(i));

                    }
                }
                bw.newLine();

                for (int i = 0; i < rowcount; i++) {
                    c.moveToPosition(i);

                    for (int j = 0; j < colcount; j++) {
                        if (j != colcount - 1)
                            bw.write(c.getString(j) + ",");
                        else
                            bw.write(c.getString(j));
                    }
                    bw.newLine();
                }
                bw.flush();
                System.out.println("Hiii Exported Successfully.");

/*

                String absolutePath = sdCardDir.getAbsolutePath();
                System.out.println("File path : " + absolutePath);

                String filePath = absolutePath.
                        substring(0,absolutePath.lastIndexOf(File.separator));

                System.out.println("Hiii : " + filePath+"/"+filename);



                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/"+filename);
                //Uri path = Uri.fromFile(filelocation);
                Uri path = FileProvider.getUriForFile(getApplicationContext(), "net.simplifiedcoding.androidmysqlsync.fileprovider", filelocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // set the type to 'email'
            //    emailIntent .setType("vnd.android.cursor.dir/email");
                String to[] = {"sandesh.khutal@gmail.com"};
                emailIntent.setType("application/csv");
                emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Scale Data");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "This is the body");
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // the attachment
                emailIntent .putExtra(Intent.EXTRA_STREAM, path);
               startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/

            }
        } catch (Exception ex) {
            System.out.println("Hiii" + ex);
        }

    }

    @Override
    public void onAddToCartPressed(UsersModel listData) {
        //    Toast.makeText(getApplicationContext(), "hello customer" + listData.getName() + "\n" + listData.getCustomer_id(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, Customer_Work_Record.class);
        intent.putExtra("user_cust_id", listData.getCustomer_id());
        intent.putExtra("user_cust_name", listData.getName());
        intent.putExtra("user_cust_mobile", listData.getMobile());
        startActivity(intent);
    }


    public void AddCustomer(View view) {
        mBottomSheetDialog = new BottomSheetDialog(this);
        @SuppressLint("InflateParams") View sheetView = getLayoutInflater().inflate(R.layout.customeruser_entry, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        user_name_txt = sheetView.findViewById(R.id.user_name_txt);
        user_mobile_txt = sheetView.findViewById(R.id.user_mobile_txt);
        AppCompatButton submit_btn = sheetView.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(view1 -> {
            username = user_name_txt.getText().toString().trim();
            mobilenumber = user_mobile_txt.getText().toString().trim();
            if (username.matches("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Customer Name", Toast.LENGTH_LONG).show();
            } else if (mobilenumber.matches("")) {
                Toast.makeText(getApplicationContext(), "Please Enter Customer Mobile Number", Toast.LENGTH_LONG).show();
            } else {
                databaseHelper.addName(username, mobilenumber);
                mBottomSheetDialog.dismiss();
                Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();

                completeJobList.clear();
                GetData();
                recyclerView.setAdapter(completeJobAdapter);
                completeJobAdapter.notifyDataSetChanged();

            }
        });


    }
}

