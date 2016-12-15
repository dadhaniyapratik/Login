package com.example.login.dbhelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.example.login.Item;
import com.example.login.model;

/**
 * Database Helper class
 * 
 * <p>
 * Contains all methods for insert/update/delete operation on database
 * </p>
 * 
 * @author IrishApps
 * @version 1.0
 * @since 2014-10-25
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static String DB_PATH;

	private static String DB_NAME = "Firstapp.db";

	protected SQLiteDatabase myDataBase;

	private final Context myContext;

	private static final String log_tag = "log_tag";
	private static DatabaseHelper instance;

	public static DatabaseHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseHelper(context);
			instance.openDataBase();
		}
		return instance;
	}

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	@SuppressLint("SdCardPath")
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";

		// DB_PATH =
		// Environment.getExternalStorageDirectory().getAbsolutePath()+ "/";

		try {
			createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();
				Log.d(log_tag, "Data base created.......");

			} catch (IOException e) {
				e.printStackTrace();
				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (Exception e) {
		}

		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		if (myInput != null && myOutput != null) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
		} else {
			Log.d("error", "Not Found");
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		Log.d(log_tag, "Database is opened....");
	}

	@Override
	public synchronized void close() {
		/*
		 * if (myDataBase != null) myDataBase.close(); super.close();
		 */
		Log.d(log_tag, "Database is closed....");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public long insertLoginDetails(ContentValues values) {
		return myDataBase.insert(DBKeys.LOGIN1,null,values);
	}
	public Cursor getrowid(){
		return myDataBase.query(DBKeys.ITEMS_TB, new String[]{DBKeys.ROW_ID},null,null,null,null,null);
	}
	public ArrayList<Item> getAllItems()
	{
		ArrayList<Item> list=new ArrayList<>();
		Cursor cursor=myDataBase.query(DBKeys.ITEMS_TB,null,null,null,null,null,null);
		if(cursor!=null)
		{
			if(cursor.moveToFirst())
			{
				do {
					Item item=new Item();
					item.setRowId(cursor.getLong(cursor.getColumnIndex(DBKeys.ROW_ID)));
					//item.setTitle(cursor.getString(cursor.getColumnIndex(DBKeys.TITLE)));

					list.add(item);
				}while (cursor.moveToNext());
			}
			cursor.close();
		}
		return list;
	}

	public long update_id(String Email, ContentValues value){
		return myDataBase.update(DBKeys.LOGIN1,value, DBKeys.EMAIL + "='" + Email + "'", null);
	}


	public String getdata(String Email){
		String password=null;
	Cursor c = myDataBase.query(DBKeys.LOGIN1,null,DBKeys.EMAIL+"='"+Email+"'",null,null,null,null);
		if(c!=null)
		{
			if(c.moveToFirst())
			{
				password =c.getString(c.getColumnIndex(DBKeys.PASSWORD));
			}
			c.close();
		}
		return password;

	}
	public String getSelectedItems(String Email){
		String items=null;
		Cursor c = myDataBase.query(DBKeys.LOGIN1,null,DBKeys.EMAIL+"='"+Email+"'",null,null,null,null);
		if(c!=null)
		{
			if(c.moveToFirst())
			{
				 items=c.getString(c.getColumnIndex("selectedItem"));
			}
			c.close();
		}
		return items;

	}
	public Boolean checkEmail(String Email) {
		Cursor c = myDataBase.query(DBKeys.LOGIN1, null, DBKeys.EMAIL + "='" + Email + "'", null, null, null, null);
		if (c != null) {
			if(c.moveToFirst())
			{
				return false;
			}
			c.close();
		}

		return true;
	}





	

	/*private ChannelDetail parseChannel(Cursor cursor, ChannelDetail detail) {

		detail.setRowId(cursor.getLong(cursor.getColumnIndex(ROWID)));
		detail.setId(cursor.getLong(cursor.getColumnIndex(ID)));
		detail.setName(cursor.getString(cursor.getColumnIndex(NAME)));
		detail.setImgurl(cursor.getString(cursor.getColumnIndex(IMG_URL)));
		detail.setGroup(cursor.getInt(cursor.getColumnIndex(GROUP_ID)));
		detail.setBgcolor(getColor(cursor.getString(cursor
				.getColumnIndex(BGCOLOR))));
		detail.setTxtcolor(getColor(cursor.getString(cursor
				.getColumnIndex(TXTCOLOR))));
		detail.setLastupdate(cursor.getString(cursor
				.getColumnIndex(LAST_UPDATE)));
		detail.setCh(cursor.getInt(cursor.getColumnIndex(CH)));
		detail.setStSerataId(cursor.getLong(cursor.getColumnIndex(STSERATA_ID)));
		detail.setNdSerataId(cursor.getLong(cursor.getColumnIndex(NDSERATA_ID)));
		detail.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
		return detail;
	}

	
	/*
	 * ============================================================= Get data
	 * Methods==========================================
	 */

	/**
	 * Get all channels
	 * 
	 * @return Cursor
	 */
	/*public ArrayList<ChannelDetail> getChannelList(String date,
			boolean fillPrograms) {
		ArrayList<ChannelDetail> list = new ArrayList<>();

		Cursor cursor = myDataBase.query(DBKeys.CHANNEL_TB, null, DATE + "='"
				+ date + "'", null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					ChannelDetail detail = new ChannelDetail();
					parseChannel(cursor, detail);

					if (fillPrograms) {
						detail.setProgramList(getProgramsByChannelId(date,
								detail.getId(), detail));
					}
					list.add(detail);
				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		return list;
	}

	public ChannelDetail getChannel(long id, String date) {
		ChannelDetail detail = new ChannelDetail();
		Cursor cursor = myDataBase.query(DBKeys.CHANNEL_TB, null, ID + "=" + id
				+ " and " + DATE + "='" + date + "'", null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {

				parseChannel(cursor, detail);

			}
			cursor.close();
		}
		return detail;
	}

	public void beginTransaction()
	{
		myDataBase.beginTransaction();
	}
	public void endTransaction()
	{
		myDataBase.setTransactionSuccessful();
		myDataBase.endTransaction();
	}
	public long insertChannel(ContentValues values) {
		return myDataBase.insert(DBKeys.CHANNEL_TB, null, values);
	}

	

	public void deleteAll() {
		// TODO Auto-generated method stub
		myDataBase.delete(DBKeys.CHANNEL_TB, null, null);
		myDataBase.delete(DBKeys.PROGDATA_TB, null, null);
	}

	

	public boolean isDataContainsForDate(String selDate) {
		// TODO Auto-generated method stub
		boolean isData = false;
		String query = "select " + DATE + " from " + DBKeys.CHANNEL_TB
				+ " where " + DATE + "='" + selDate + "' group by " + DATE;
		Cursor cursor = myDataBase.rawQuery(query, null);
		if (cursor != null) {
			isData = cursor.moveToFirst();
			cursor.close();
		}
		return isData;
	}
*/
}
