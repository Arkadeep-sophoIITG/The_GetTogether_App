package in.ernet.arkadeepiitg.the_gettogether_app;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "GTApp";
    
    static final int DATABASE_VERSION = 1;

   

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
           
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Friends");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        db=DBHelper.getReadableDatabase();
        return this;
    }

    //---closes the database---
    public void close() 
    {
        DBHelper.close();
    }

    //---insert a friend into the database---
    public long insertFriend(String name, String email, String phone) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", name);
        initialValues.put("email", email);
        initialValues.put("phone", phone);
        return db.insert("Friends", null, initialValues);
    }

    //---deletes a particular friend---
    public boolean deleteFriend(int rowId) 
    {
        return db.delete("Friends", "id" + "=" + rowId, null) > 0;
    }

    //---retrieves all the friends---
    public Cursor getAllFriends()
    {
        return db.query("Friends", new String[] {"id", "name",
                "email", "phone"}, null, null, null, null, null);
    }

    //---retrieves a particular friend---
    public Cursor getFriend(int rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, "Friends", new String[] {"id",
                "name", "email","phone"}, "id" + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a friend---
    public boolean updateFriend(int id, String name, String email, String phone) 
    {
        ContentValues args = new ContentValues();
        args.put("name", name);
        args.put("email", email);
        args.put("phone", phone);
        return db.update("Friends", args, "id" + "=" + id, null) > 0;
    }

    //---insert a place into the database---
    public long insertPlace(String name, String address, String phone, 
    		String popularfood, String rating)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", name);
        initialValues.put("address", address);
        initialValues.put("phone", phone);
        initialValues.put("popularfood", popularfood);
        initialValues.put("rating", rating);
        return db.insert("Places", null, initialValues);
    }

    //---deletes a particular place---
    public boolean deletePlace(int rowId) 
    {
        return db.delete("Places", "id" + "=" + rowId, null) > 0;
    }

    //---retrieves all the places---
    public Cursor getAllPlaces()
    {
        return db.query("Places", new String[] {"id", "name",
                "address", "phone","popularfood","rating"}, null, null, null, null, null);
    }

    //---retrieves a particular place---
    public Cursor getPlace(int rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, "Places", new String[] {"id",
                "name", "address","phone","popularfood","rating"}, "id" + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a place---
    public boolean updatePlace(int id, String name, String address, String phone, 
    		String popularfood, String rating)
    {
        ContentValues args = new ContentValues();
        args.put("name", name);
        args.put("address", address);
        args.put("phone", phone);
        args.put("popularfood", popularfood);
        args.put("rating", rating);
        return db.update("Places", args, "id" + "=" + id, null) > 0;
    }
    
    //---insert a get-together into the database---
    public long insertGetTogether(String name, String desc, String friends, 
    		String place, String datetime) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", name);
        initialValues.put("desc", desc);
        initialValues.put("friends", friends);
        initialValues.put("place", place);
        initialValues.put("datetime", datetime);
        return db.insert("GetTogethers", null, initialValues);
    }

    //---deletes a particular get-together---
    public boolean deleteGetTogether(int rowId) 
    {
        return db.delete("GetTogethers", "id" + "=" + rowId, null) > 0;
    }

    //---retrieves all the get-togethers---
    public Cursor getAllGetTogethers()
    {
        return db.query("GetTogethers", new String[] {"id", "name",
                "desc", "friends","place","datetime"}, null, null, null, null, null);
    }

    //---retrieves a particular get-together---
    public Cursor getGetTogether(int rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, "GetTogethers", new String[] {"id",
                "name","desc", "friends","place","datetime"}, "id" + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    //---retrieves selected friends for a particular get-together---
    public Cursor getCurrSelectedFriends(int rowId) throws SQLException 
    {
        return
                db.query(true, "GetTogethers", new String[] {"id","friends"}, "id" + "=" + rowId, null,
                null, null, null, null);
       
    }

    //---updates a get-together---
    public boolean updateGetTogether(int id, String name, String desc, String friends, 
    		String place, String datetime) 
    {
        ContentValues args = new ContentValues();
        args.put("name", name);
        args.put("desc", desc);
        args.put("friends", friends);
        args.put("place", place);
        args.put("datetime", datetime);
        
        return db.update("GetTogethers", args, "id" + "=" + id, null) > 0;
    }



}
