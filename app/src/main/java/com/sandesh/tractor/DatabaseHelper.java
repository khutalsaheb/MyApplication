package com.sandesh.tractor;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tractordb";
    private static final int DB_VERSION = 1;

    private static final String USER_TABLE_NAME = "user_table";
    private static final String USER_COLUMN_ID = "id";
    private static final String USER_COLUMN_NAME = "name";
    private static final String USER_COLUMN_MOBILE = "mobile";
    private static final String USER_COLUMN_STATUS = "status";


    //SECOND TABLE
    private static final String CUSTOMER_TABLE_NAME = "customer_table";
    private static final String CUSTOMER_COLUMN_ID = "customer_id";
    private static final String CUSTOMER_USER_COLUMN_ID = "uid";//useruid---user_column id
    private static final String CUSTOMER_COLUMN_NAME = "customer_name";
    private static final String CUSTOMER_COLUMN_MOBILE = "customer_mobile";

    static final String CUSTOMER_COLUMN_WORKDATE = "workdate";
    static final String CUSTOMER_COLUMN_WORKNAME = "workname";
    static final String CUSTOMER_COLUMN_STARTTIME = "timeinhours";
    static final String CUSTOMER_COLUMN_PAIDAMOUNT = "paidamount";
    static final String CUSTOMER_COLUMN_TOTALAMOUNT = "totalamount";
    static final String CUSTOMER_COLUMN_REMAININGAMOUNT = "remainamount";
    private static final String CUSTOMER_COLUMN_STATUS = "c_status";


    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + USER_TABLE_NAME
                + "(" + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_COLUMN_NAME + " VARCHAR, "
                + USER_COLUMN_MOBILE + " VARCHAR, "
                + USER_COLUMN_STATUS + " TINYINT);";
        db.execSQL(sql);
        String sql_customer = "CREATE TABLE " + CUSTOMER_TABLE_NAME
                + "(" + CUSTOMER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CUSTOMER_USER_COLUMN_ID + " VARCHAR, "
                + CUSTOMER_COLUMN_NAME + " VARCHAR, "
                + CUSTOMER_COLUMN_MOBILE + " VARCHAR, "
                + CUSTOMER_COLUMN_WORKDATE + " VARCHAR, "
                + CUSTOMER_COLUMN_WORKNAME + " VARCHAR, "
                + CUSTOMER_COLUMN_STARTTIME + " VARCHAR, "
                + CUSTOMER_COLUMN_TOTALAMOUNT + " VARCHAR, "
                + CUSTOMER_COLUMN_PAIDAMOUNT + " VARCHAR, "
                + CUSTOMER_COLUMN_REMAININGAMOUNT + " VARCHAR, "
                + CUSTOMER_COLUMN_STATUS + " TINYINT);";
        db.execSQL(sql_customer);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS '" + USER_TABLE_NAME + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CUSTOMER_TABLE_NAME + "'");
        onCreate(db);

    }


    void addName(String name, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_NAME, name);
        contentValues.put(USER_COLUMN_MOBILE, mobile);
        contentValues.put(USER_COLUMN_STATUS, 0);
        db.insert(USER_TABLE_NAME, null, contentValues);
        db.close();
    }


    void addcustomer(String customeruserid, String cust_name, String cust_mobile, String workdate, String workname, String starttime,
                     String totalamount, String paidamount, String remainamount) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CUSTOMER_USER_COLUMN_ID, customeruserid);
        contentValues.put(CUSTOMER_COLUMN_NAME, cust_name);
        contentValues.put(CUSTOMER_COLUMN_MOBILE, cust_mobile);
        contentValues.put(CUSTOMER_COLUMN_WORKDATE, workdate);
        contentValues.put(CUSTOMER_COLUMN_WORKNAME, workname);
        contentValues.put(CUSTOMER_COLUMN_STARTTIME, starttime);
        contentValues.put(CUSTOMER_COLUMN_TOTALAMOUNT, totalamount);
        contentValues.put(CUSTOMER_COLUMN_PAIDAMOUNT, paidamount);
        contentValues.put(CUSTOMER_COLUMN_REMAININGAMOUNT, remainamount);
        contentValues.put(CUSTOMER_COLUMN_STATUS, 0);
        db.insert(CUSTOMER_TABLE_NAME, null, contentValues);
        db.close();
    }

    Cursor getAllEmployees() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " ORDER BY " + USER_COLUMN_ID + " ASC;", null);

    }

    Cursor getAllCustomer(long MyID) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE_NAME + " WHERE " + CUSTOMER_USER_COLUMN_ID + " = " + MyID, null);
    }

    Cursor getAllCustomer() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE_NAME, null);
    }

    Cursor search(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from " + USER_TABLE_NAME + " where " + USER_COLUMN_NAME + " LIKE  '" + keyword + "%'", null);


    }

}