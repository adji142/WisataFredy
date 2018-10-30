package com.uas.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper{
	 
    private static String DB_PATH = "/data/data/com.uas.app/databases/";
 
    private final static String DB_NAME = "db_wisata";
	public final static int DB_VERSION = 1;
    public static SQLiteDatabase db; 
 
    private final Context context;
    private final String ID = "ID";
	private final String TABLE_NAME = "pariwisata";
	private final String Nama_wisata = "Nama_wisata";
	private final String Image = "Image";
	private final String Lokasi = "Lokasi";
	private final String Informasi= "Informasi";
	private final String Deskripsi = "Deskripsi";
	private final String Tipe = "Tipe";
	
	
    public DBHelper(Context context) {
 
    	super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }	
 
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
    	SQLiteDatabase db_Read = null;

 
    	if(dbExist){
    		//do nothing - database already exist
    		deleteDataBase();
    		try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}else{
    		db_Read = this.getReadableDatabase();
    		db_Read.close();
 
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
 
    }
 
    private void deleteDataBase(){
    	File dbFile = new File(DB_PATH + DB_NAME);
    	
    	dbFile.delete();
    }
   
    private boolean checkDataBase(){
 
    	File dbFile = new File(DB_PATH + DB_NAME);

    	return dbFile.exists();
    	
    }
 
    
    private void copyDataBase() throws IOException{
    	
    	InputStream myInput = context.getAssets().open(DB_NAME);
 
    	String outFileName = DB_PATH + DB_NAME;
 
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	String myPath = DB_PATH + DB_NAME;
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public void close() {
    	db.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	
	/** this code is used to get all data from database */
 	public ArrayList<ArrayList<Object>> getAllData(String keyword){
		ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
 
		Cursor cursor = null;
 
		if(keyword.equals("")){
			try{
				cursor = db.query(
						TABLE_NAME,
						new String[]{ID,Nama_wisata, Image, Lokasi},
						null, null, null, null, null);
				cursor.moveToFirst();
	
				if (!cursor.isAfterLast()){
					do{
						ArrayList<Object> dataList = new ArrayList<Object>();
						
						dataList.add(cursor.getLong(0));
						dataList.add(cursor.getString(1));
						dataList.add(cursor.getString(2));
						dataList.add(cursor.getString(3));
						dataArrays.add(dataList);
					}
				
					while (cursor.moveToNext());
				}
				cursor.close();
			}catch (SQLException e){
				Log.e("DB Error", e.toString());
				e.printStackTrace();
			}
		}else{
			try{
				cursor = db.query(
						TABLE_NAME,
						new String[]{ID,Nama_wisata, Image, Lokasi},
						Tipe +" LIKE '%"+keyword+"%'",
						null, null, null, null);
				cursor.moveToFirst();
	
				if (!cursor.isAfterLast()){
					do{
						ArrayList<Object> dataList = new ArrayList<Object>();
	 
						dataList.add(cursor.getLong(0));
						dataList.add(cursor.getString(1));
						dataList.add(cursor.getString(2));
						dataList.add(cursor.getString(3));
						dataArrays.add(dataList);
					}
				
					while (cursor.moveToNext());
				}
				cursor.close();
			}catch (SQLException e){
				Log.e("DB Error", e.toString());
				e.printStackTrace();
			}
		}
		return dataArrays;
	}
	
 	/** this code is used to get data from database base on id value */
 	public ArrayList<Object> getDetail(long id){
		
		ArrayList<Object> rowArray = new ArrayList<Object>();
		Cursor cursor;
 
		try{
			cursor = db.query(
					TABLE_NAME,
					new String[] {Nama_wisata,Image,Lokasi,Informasi,Deskripsi,Tipe},
					ID + "=" + id,
					null, null, null, null, null);
 
			cursor.moveToFirst();
 
			if (!cursor.isAfterLast()){
				do{
					rowArray.add(cursor.getString(0));
					rowArray.add(cursor.getString(1));
					rowArray.add(cursor.getString(2));
					rowArray.add(cursor.getString(3));
					rowArray.add(cursor.getString(4));
					rowArray.add(cursor.getString(5));
				}
				while (cursor.moveToNext());
			}
 
			cursor.close();
		}
		catch (SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
 
		return rowArray;
	}
}