package jean.emma.sarah.hackathonapp.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

   // All Static variables
   // Database Version
   private static final int DATABASE_VERSION = 1;

   // Database Name
   private static final String DATABASE_NAME = "contactsManager";

   // Profs table name
   private static final String TABLE_CONTACTS = "contacts";

   // Profs Table Columns names
   private static final String KEY_ID = "id";
   private static final String KEY_SCHOOL = "school";
   private static final String KEY_DEPT = "department";
   private static final String KEY_NAME = "name";
   private static final String KEY_LOC = "location";
   private static final String KEY_MONDAY = "monday";
   private static final String KEY_TUESDAY = "tuesday";
   private static final String KEY_WEDNESDAY = "wednesday";
   private static final String KEY_THURSDAY = "thursday";
   private static final String KEY_FRIDAY = "friday";
   
   public DatabaseHandler(Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

   // Creating Tables
   @Override
   public void onCreate(SQLiteDatabase db) {
      /* String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
               + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SCHOOL + " TEXT,"
               + KEY_DEPT + " TEXT" + KEY_NAME + " TEXT" + KEY_LOC + " TEXT" + KEY_MONDAY + " TEXT"
               + KEY_TUESDAY + " TEXT" + KEY_WEDNESDAY + " TEXT" + KEY_THURSDAY + " TEXT" + KEY_FRIDAY + " TEXT"+ ")";
       */
	   
	   
	   String CREATE_CONTACTS_TABLE = "CREATE TABLE contacts ( id INTEGER(10), " +
       "school VARCHAR(250), department VARCHAR(250), name VARCHAR(250), " +
	   "location VARCHAR(250), monday VARCHAR(250), tuesday VARCHAR(250), " +
       "wednesday VARCHAR(250), thursday VARCHAR(250), friday VARCHAR(250))";
       db.execSQL(CREATE_CONTACTS_TABLE);
   }

   // Upgrading database
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // Drop older table if existed
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

       // Create tables again
       onCreate(db);
   }

   /**
    * All CRUD(Create, Read, Update, Delete) Operations
    */

   // Adding new contact
   void addProf(Prof contact) {
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(KEY_SCHOOL, contact.getSchool()); // Prof Name
       values.put(KEY_DEPT, contact.getDepartment()); // Prof Phone
       values.put(KEY_NAME, contact.getName()); // Prof Name
       values.put(KEY_LOC, contact.getLocation()); // Prof Phone
       values.put(KEY_MONDAY, contact.getMonday()); // Prof Name
       values.put(KEY_TUESDAY, contact.getTuesday()); // Prof Phone
       values.put(KEY_WEDNESDAY, contact.getWednesday()); // Prof Name
       values.put(KEY_THURSDAY, contact.getThursday()); // Prof Phone
       values.put(KEY_FRIDAY, contact.getFriday()); // Prof Name
       
       // Inserting Row
       db.insert(TABLE_CONTACTS, null, values);
       db.close(); // Closing database connection
   }

   // Getting single contact
   Prof getProf(int id) {
       SQLiteDatabase db = this.getReadableDatabase();

       Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
               KEY_SCHOOL, KEY_DEPT, KEY_NAME, KEY_LOC, KEY_MONDAY, KEY_TUESDAY, KEY_WEDNESDAY,
               KEY_THURSDAY, KEY_FRIDAY}, KEY_ID + "=?",
               new String[] { String.valueOf(id) }, null, null, null, null);
       if (cursor != null)
           cursor.moveToFirst();

       Prof contact = new Prof(Integer.parseInt(cursor.getString(0)),
               cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
               cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
       // return contact
       return contact;
   }
    
   // Getting All Profs
   public List<Prof> getAllProfs() {
       List<Prof> contactList = new ArrayList<Prof>();
       // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);

       // looping through all rows and adding to list
       if (cursor.moveToFirst()) {
           do {
               Prof contact = new Prof();
               contact.setID(Integer.parseInt(cursor.getString(0)));
               contact.setSchool(cursor.getString(1));
               contact.setDepartment(cursor.getString(2));
               contact.setName(cursor.getString(3));
               contact.setLocation(cursor.getString(4));
               contact.setMonday(cursor.getString(5));
               contact.setTuesday(cursor.getString(6));
               contact.setWednesday(cursor.getString(7));
               contact.setThursday(cursor.getString(8));
               contact.setFriday(cursor.getString(9));
               // Adding contact to list
               contactList.add(contact);
           } while (cursor.moveToNext());
       }

       // return contact list
       return contactList;
   }

   // Updating single contact
   public int updateProf(Prof contact) {
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(KEY_SCHOOL, contact.getSchool());
       values.put(KEY_DEPT, contact.getDepartment());
       values.put(KEY_NAME, contact.getName());
       values.put(KEY_LOC, contact.getLocation());
       values.put(KEY_MONDAY, contact.getMonday());
       values.put(KEY_TUESDAY, contact.getTuesday());
       values.put(KEY_WEDNESDAY, contact.getWednesday());
       values.put(KEY_THURSDAY, contact.getThursday());
       values.put(KEY_FRIDAY, contact.getFriday());

       // updating row
       return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
               new String[] { String.valueOf(contact.getID()) });
   }

   // Deleting single contact
   public void deleteProf(Prof contact) {
       SQLiteDatabase db = this.getWritableDatabase();
       db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
               new String[] { String.valueOf(contact.getID()) });
       db.close();
   }


   // Getting contacts Count
   public int getProfsCount() {
       String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(countQuery, null);
       cursor.close();

       // return count
       return cursor.getCount();
   }

}
