package com.teamviperstrike.desde;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/********************************************************
 * 
 * @author teamviperstrike
 * @date 02/15/2014
 * @version 1.0.1
 * @see be a decent human. don't steal code, credit the author.
 *
 ********************************************************/
public class MSSQLiteHelper extends SQLiteOpenHelper {
	
	private static final int DB_VERSION = 2;


 // Constructor to build SQLiteHelper based on context.	
 public MSSQLiteHelper(Context context){
		
		super(context, DB_NAME, null, DB_VERSION);
		MSSQLiteHelper.msContext = context;

	}
	
 // DB Params
	private  String DB_PATH = "data/data/com.teamviperstrike.desde/databases/";
	private static String DB_NAME = "milestones";
	private SQLiteDatabase milestonesDB;
	public static  Context msContext;
	


//Creates an empty database on the system and rewrites it
	// with assets/Milestones db
	public void createDataBase() throws IOException{

		boolean dbExist = checkDatabase();
		if (!dbExist){		
			// Calling this method an empty database will be
			// created into the default system path of 
			// Notable so we can overwrite with Milestones.
			this.getWritableDatabase();
			try{


				copyDatabase();
				
			}catch (IOException e) {
				throw new Error("Error copying database");
				
			}
		}
	}
	
	// Check if the database already exists to avoid copying every
	// startup. Return true if exists, false if it doesn't.
	private boolean checkDatabase(){		
		
		File dbFile = new File(DB_PATH);
		if(dbFile.exists()){

			return true;
			
		}else{
			dbFile.getParentFile().mkdirs();

			return false;
		}
		
	
	}
	
	// Copies database from assets folder to the empty 
	// database in the system folder. This allows it to be
	// accessed.
	private void copyDatabase() throws IOException{
		

		// Open local database as input stream
		InputStream bnInput = msContext.getAssets().open(DB_NAME);

		

		// Path to the empty database
		String outFileName = DB_PATH + DB_NAME;

		

		// Open empty database as the output stream
		OutputStream bnOutput = new FileOutputStream(outFileName);

		// Transfer data from the input to the output
		byte[] buffer = new byte[1024];
		int length;
		while ((length = bnInput.read(buffer)) > 0){
			bnOutput.write(buffer, 0, length);
		}
		
		// Once it has completed, close the streams
		bnOutput.flush();
		bnOutput.close();
		bnInput.close();
	}
	
	// Method for opening database.
	public void openDatabase() throws SQLException{
		
		// Open the database
		String bnPath = DB_PATH + DB_NAME;
		milestonesDB = SQLiteDatabase.openDatabase(bnPath, null, SQLiteDatabase.OPEN_READWRITE);
		
	}
	
	// Method for closing database.
	@Override
	public synchronized void close(){
		
		if(milestonesDB != null){
			milestonesDB.close();

		}
		
	}
	
	// On Create
	@Override
	public void onCreate(SQLiteDatabase db){
	
	}
	
	// Upgrade instructions.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		
			msContext.deleteDatabase(DB_NAME);
			try {
				createDataBase();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

	}
	
//*****************//
//    GETTERS     //
//***************//
	// Get Milestone Name
	public String getName(int i){
		try {
			createDataBase();
		} catch (IOException e1) {		
			e1.printStackTrace();
		}

		openDatabase();
		
		String newWord = null;
		Cursor cursor = milestonesDB.rawQuery("SELECT mName FROM Milestones WHERE mID = " + i, null);
		
		try{
						
			if(cursor.moveToFirst()){
				newWord = cursor.getString(0);
			}
		}catch(Exception e){
		}finally{
			cursor.close();
			milestonesDB.close();
		}

		return newWord;
	}
	
	// Get Milestone ID
	public int getID(int mID){
		try {
			createDataBase();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		int ID = 0;
		openDatabase();
		 Cursor cursor = milestonesDB.rawQuery("SELECT mID FROM Milestones WHERE mID = " + mID, null);
		 
		 try{
			 if(cursor.moveToFirst()){
				 ID = cursor.getInt(0);
			 }
		 }catch(Exception e){
			 
		 }finally{
			 cursor.close();
			 milestonesDB.close();
		 }
		
		return ID;
		
	}
	
	// Get number of milestones
	public int getCount(){

		int count = 0;
		openDatabase();
		 Cursor cursor = milestonesDB.rawQuery("SELECT COUNT(rowID) FROM Milestones", null);
		 
		 try{
			 if(cursor.moveToFirst()){
				 count = cursor.getInt(0);
			 }
		 }catch(Exception e){
			 
		 }finally{
			 cursor.close();
			 milestonesDB.close();
		 }
		
		return count;
		
	}
	
	// Get recently created mID
	public int getNewID(){
		try {
			createDataBase();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		int ID = 0;
		openDatabase();
		 Cursor cursor = milestonesDB.rawQuery("SELECT MAX(mID) FROM Milestones", null);
		 
		 try{
			 if(cursor.moveToFirst()){
				 ID = cursor.getInt(0);
			 }
		 }catch(Exception e){
			 
		 }finally{
			 cursor.close();
			 milestonesDB.close();
		 }
		
		return ID;
		
	}
	
	// Get the start date of the Milestone
    public String getDate(int mID){
		
		String thisDate = null;
		openDatabase();
		Cursor cursor = milestonesDB.rawQuery("SELECT mStart FROM milestones WHERE mID = " + mID, null);
		try{
			 if(cursor.moveToFirst()){
				 thisDate = cursor.getString(0);
			 }
		 }catch(Exception e){
			 
		 }finally{
			 cursor.close();
			 milestonesDB.close();
		 }			
			return thisDate;

		
	}
    
    // Get the Notes value(s)
    public String getNotes(int mID){
		
		String thisNote = null;
		openDatabase();
		Cursor cursor = milestonesDB.rawQuery("SELECT mNotes FROM milestones WHERE mID = " + mID, null);
		try{
			 if(cursor.moveToFirst()){
				 thisNote = cursor.getString(0);
			 }
		 }catch(Exception e){
			 
		 }finally{
			 cursor.close();
			 milestonesDB.close();
		 }			
			return thisNote;

		
	}
    
    // Get the length of time. Sets the counter for the milestone.
    @SuppressLint("SimpleDateFormat")
    public String getTimeLength(String date){
	 
	 
	 
	 
	//HH converts hour in 24 hours format (0-23), day calculation
	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	 
	 		String timeLength = null;
	 		Date dateVar = new Date();
	 		String now = format.format(dateVar);
			Date d1 = null;
			Date d2 = null;
			
			
			

	 
			try {
				d1 = format.parse(date);
				d2 = format.parse(now);
				
				
				//in milliseconds
				long diff = d2.getTime() - d1.getTime();
	 
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				timeLength = (diffDays + " days : " + diffHours + " hours : " + diffMinutes + " minutes : " + diffSeconds + " seconds");

	 
			} catch (Exception e) {
				e.printStackTrace();
			}
	 
	 
	 
	 
	return timeLength;
	 
 }
	
    
//********************//
//		UPDATERS      //
//********************//
    // Updates the start time
	@SuppressLint("SimpleDateFormat")
	public void updateStartTime(int mID){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		openDatabase();
		Date date = new Date();
		String now = sdf.format(date);
		
		String strFilter = "mID = " + mID;
		ContentValues args = new ContentValues();
		args.put("mStart", now);
		milestonesDB.update("Milestones", args, strFilter, null);		
		
		milestonesDB.close();
	}
	
	// Updates the Milestone Name
    public void updateName(int mID, String name){
		
		openDatabase();
		
		String strFilter = "mID = " + mID;
		ContentValues args = new ContentValues();
		args.put("mName", name);
		milestonesDB.update("Milestones", args, strFilter, null);		
		
		milestonesDB.close();
	}
    
    // Updates the IsStopped value. Not used in v1.0.1
    public void updateStopped(int mID, int stopped){
    	openDatabase();
		
		String strFilter = "mID = " + mID;
		ContentValues args = new ContentValues();
		args.put("mName", stopped);
		milestonesDB.update("Milestones", args, strFilter, null);		
		
		milestonesDB.close();
    }
    
    // Clears the start time
    public void updateClearStartTime(int mID){
    	openDatabase();
		
		String strFilter = "mID = " + mID;
		ContentValues args = new ContentValues();
		args.put("mStart", "");
		milestonesDB.update("Milestones", args, strFilter, null);		
		
		milestonesDB.close();
    }
    
    // Updates mNotes
    public void updateNotes(int mID, String notes){
    	openDatabase();
		
		String strFilter = "mID = " + mID;
		ContentValues args = new ContentValues();
		args.put("mNotes", notes);
		milestonesDB.update("Milestones", args, strFilter, null);		
		
		milestonesDB.close();
    }
    
    // Updates Notify to either true or false. Not used in v1.0.1
    public void updateNotify(int mID, boolean onOff){
    	if(!onOff){
    		openDatabase();
    		
    		String strFilter = "mID = " + mID;
    		ContentValues args = new ContentValues();
    		args.put("mNotify", 0);
    		milestonesDB.update("Milestones", args, strFilter, null);		
    		
    		milestonesDB.close();
    	}else{
    		openDatabase();
    		
    		String strFilter = "mID = " + mID;
    		ContentValues args = new ContentValues();
    		args.put("mNotify", 1);
    		milestonesDB.update("Milestones", args, strFilter, null);		
    		
    		milestonesDB.close();
    	}
    }
    
    // Updates the interval for notifications
    public void updateInterval(int mID, int interval){
    	
    	openDatabase();
		
		String strFilter = "mID = " + mID;
		ContentValues args = new ContentValues();
		args.put("mNotifyInterval", interval);
		milestonesDB.update("Milestones", args, strFilter, null);		
		
		milestonesDB.close();
    }
    
    // Resets the names to defaults
    public void resetNames(int mID){
        openDatabase();
		
		String strFilter1 = "mID = " + mID;
		
		
		ContentValues args1 = new ContentValues();
		ContentValues args2 = new ContentValues();
		ContentValues args3 = new ContentValues();
		
		args1.put("mName", "Milestone One");
		args2.put("mName", "Milestone Two");
		args3.put("mName", "Milestone Three");
		
		
		if(mID == 1){
			milestonesDB.update("Milestones", args1, strFilter1, null);
		}else if(mID == 2){
			milestonesDB.update("Milestones", args2, strFilter1, null);
		}else if(mID == 3){
			milestonesDB.update("Milestones", args3, strFilter1, null);
		}
		
		
		
		milestonesDB.close();
    	
    }
    
    // Updates whether Milestone is shared to Facebook
    public void updateFacebook(int mID, boolean onOff){
    	if(!onOff){
    		openDatabase();
    		
    		String strFilter = "mID = " + mID;
    		ContentValues args = new ContentValues();
    		args.put("mFacebook", 0);
    		milestonesDB.update("Milestones", args, strFilter, null);		
    		
    		milestonesDB.close();
    	}else{
    		openDatabase();
    		
    		String strFilter = "mID = " + mID;
    		ContentValues args = new ContentValues();
    		args.put("mFacebook", 1);
    		milestonesDB.update("Milestones", args, strFilter, null);		
    		
    		milestonesDB.close();
    	}
    }
	
//****************************//
//			CHECKERS		  //
//****************************//
    // Checks if mStartDate IS NULL
    boolean checkDate(int mID){		
		
		openDatabase();
		String thisDate = null;	
		Cursor cursor = milestonesDB.rawQuery("SELECT mStart FROM Milestones WHERE mID = " + mID, null);
		
		try{
						
			if(cursor.moveToFirst()){
				thisDate = cursor.getString(0);
			}
		}catch(Exception e){
		}finally{
			cursor.close();
			milestonesDB.close();
		}
		
		// Boolean check
		if(thisDate != null){
		
			return true;
			
		}else{

			return false;
		}
}
    // Checks if mNotes is NULL
    boolean checkNotes(int mID){		
		
		openDatabase();
		String thisNote = null;
		Cursor cursor = milestonesDB.rawQuery("SELECT mNotes FROM Milestones WHERE mID = " + mID, null);
		
		try{
						
			if(cursor.moveToFirst()){
				thisNote = cursor.getString(0);
			}
		}catch(Exception e){
		}finally{
			cursor.close();
			milestonesDB.close();
		}
		
		//Boolean check
		if(thisNote != null){
		
			return true;
			
		}else{

			return false;
		}
}
 
    // Checks Notification Status
    boolean checkNotify(int mID){
	 openDatabase();
	 int checked = 0;
	 Cursor cursor = milestonesDB.rawQuery("SELECT mNotify FROM Milestones WHERE mID = " + mID, null);
	 try{
		 if(cursor.moveToFirst()){
			 checked = cursor.getInt(0);
		 }
	 }catch(Exception e){
		 
	 }finally{
		 cursor.close();
		 milestonesDB.close();
	 }
	 
	 if(checked == 1){
		 return true;
	 }else{
	return false;
	 }
	 
 }
    
    // Checks if milestone is shared to Facebook
    boolean checkFacebook(int mID){
	 openDatabase();
	 int checked = 0;
	 Cursor cursor = milestonesDB.rawQuery("SELECT mFacebook FROM Milestones WHERE mID = " + mID, null);
	 try{
		 if(cursor.moveToFirst()){
			 checked = cursor.getInt(0);
		 }
	 }catch(Exception e){
		 
	 }finally{
		 cursor.close();
		 milestonesDB.close();
	 }
	 
	 if(checked == 1){
		 return true;
	 }else{
	return false;
	 }
	 
 }
    
    // Checks if the milestone has been stopped.
    boolean checkIsStopped(int mID){
   	 openDatabase();
   	 int checked = 0;
   	 Cursor cursor = milestonesDB.rawQuery("SELECT mIsStopped FROM Milestones WHERE mID = " + mID, null);
   	 try{
   		 if(cursor.moveToFirst()){
   			 checked = cursor.getInt(0);
   		 }
   	 }catch(Exception e){
   		 
   	 }finally{
   		 cursor.close();
   		 milestonesDB.close();
   	 }
   	 
   	 if(checked == 0){
   		 return true;
   	 }else{
   	return false;
   	 }
   	 
    }
    
//****************************//
//		INSERT RECORD		  //
//****************************//
    //inserts a new record
    public void insertRecord(String name) {
    	  openDatabase();
    	  ContentValues values = new ContentValues();
    	  values.put("mName", name);
    	  milestonesDB.insert("Milestones", null, values);
    	  updatemID();
    	}
    
    //updates mID = rowID
    public void updatemID(){
		
    	int rowID = 0;
    	
    	Cursor cursor = milestonesDB.rawQuery("SELECT rowid FROM Milestones WHERE mID IS NULL", null);
      	 try{
      		 if(cursor.moveToFirst()){
      			 rowID = cursor.getInt(0);
      		 }
      	 }catch(Exception e){
      		 
      	 }finally{
      		 cursor.close();
      	 }
    	
		String strFilter = "mID IS NULL";
		ContentValues args = new ContentValues();
		args.put("mNotes", rowID);
		milestonesDB.update("Milestones", args, strFilter, null);		
		
		milestonesDB.close();
    }

}

