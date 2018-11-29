package com.ssd.boris.shoppingcart.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.ssd.boris.shoppingcart.HomeActivity;
import com.ssd.boris.shoppingcart.usermodel.User;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UserManager.db";

    private static final String TABLE_USER =  "user";


    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_MOBILE = "user_mobile";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER  + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_EMAIL + " TEXT NOT NULL, "
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_USER_MOBILE + " TEXT NOT NULL"
            +");" ;

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        onCreate(sqLiteDatabase);
    }


    public void addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_MOBILE, user.getMobile());

        sqLiteDatabase.insert(TABLE_USER, null, values);
        sqLiteDatabase.close();
    }

    public boolean checkUser(String email) {
        String[] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = sqLiteDatabase.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = sqLiteDatabase.query(TABLE_USER,columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public User getUser(String email){

        Cursor cursor;
        User user = null;
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_MOBILE
        };

        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        cursor = sqLiteDatabase.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
            int count = cursor.getCount();
            if(count > 0){
                cursor.moveToFirst();
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
                String useremail = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD));
                String mobile = cursor.getString(cursor.getColumnIndex(COLUMN_USER_MOBILE));
                 user = new User(id, name, email, password, mobile);
            }
            cursor.close();
            return user;
    }
}
