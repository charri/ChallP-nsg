package ch.hsr.nsg.themenrundgang.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Repositories;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.utils.StringUtils;

public class NsgRepository 
	extends SQLiteOpenHelper 
	implements Repositories {

	private static final String DB_NAME = "nsg.db";
	private static final int DB_VERSION = 1;
	
	private static final String TABLE_BEACON = getTableName(Beacon.class);
	private static final String TABLE_SUBJECT = getTableName(Subject.class);
	private static final String TABLE_ADDITION = getTableName(Addition.class);
	private static final String TABLE_ITEM = getTableName(Item.class);
	private static final String TABLE_ITEM_BEACON = getTableName(Item.class) + "_" + getTableName(Beacon.class);
	private static final String TABLE_ITEM_SUBJECT = getTableName(Item.class) + "_" + getTableName(Subject.class);
	private static final String TABLE_ITEM_IMAGE = getTableName(Item.class) + "_image";
	
	public NsgRepository(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create tables
		db.execSQL("CREATE TABLE " + TABLE_BEACON + " (id TEXT PRIMARY KEY, major INTEGER, minor INTEGER);");
		db.execSQL("CREATE TABLE " + TABLE_SUBJECT + " (id INTEGER PRIMARY KEY, parentId INTEGER NULL, name TEXT NULL, description TEXT);");
		db.execSQL("CREATE TABLE " + TABLE_ADDITION + " (id INTEGER PRIMARY KEY, itemId INTEGER NOT NULL, a_key TEXT, a_value TEXT)");
		db.execSQL("CREATE TABLE " + TABLE_ITEM + " (id INTEGER PRIMARY KEY, name TEXT NOT NULL, description TEXT);");
		// Foreign tables
		db.execSQL("CREATE TABLE " + TABLE_ITEM_BEACON + " (id INTEGER PRIMARY KEY autoincrement, beaconId TEXT NOT NULL, itemId INTEGER NOT NULL);");
		db.execSQL("CREATE TABLE " + TABLE_ITEM_SUBJECT + " (id INTEGER PRIMARY KEY autoincrement, subjectId INTEGER NOT NULL, itemId INTEGER NOT NULL);");
		db.execSQL("CREATE TABLE " + TABLE_ITEM_IMAGE + " (id INTEGER PRIMARY KEY autoincrement, imageId INTEGER NOT NULL, itemId INTEGER NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

	}
	
	@SuppressLint("DefaultLocale") private static String getTableName(Class<?> type) {
		return type.getSimpleName().toLowerCase();
	}
	
	private boolean contains(String tableName, String id) {
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.query(tableName, new String[] { "id" }, "id = ?", new String[] { id }, null, null, null);
		
		boolean contains = cursor.getCount() > 0;
		cursor.close();
		db.close();
		return contains;
	}
	
	private void insertOrUpdate(String tableName, String id, ContentValues contentValues) {
		
		boolean doesContain = contains(tableName, id);
		SQLiteDatabase db = getWritableDatabase();
		
		if(doesContain) {
			db.update(tableName, contentValues, "id=?", new String[] { id });
		} else {
			db.insert(tableName, null, contentValues);
		}
		
		db.close();
	}

	@Override
	public void insertOrUpdate(Subject subject) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", subject.getId());
		contentValues.put("name", subject.getName());
		contentValues.put("description", subject.getDescription());
		contentValues.put("parentId", subject.getParentId());
		
		insertOrUpdate(TABLE_SUBJECT, String.valueOf(subject.getId()), contentValues);
	}

	@Override
	public Subject[] allToplevelSubjects() {
		return allSubjectsByParent(null);
	}

	@Override
	public Subject[] allSubjectsByParent(Subject parent) {
		// parent may be null!
		
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append("SELECT "+ TABLE_SUBJECT + ".* FROM " + TABLE_SUBJECT);
		sqlBuilder.append(" WHERE ");
		if(parent == null) {
			sqlBuilder.append("parentId = 0");
		} else {
			sqlBuilder.append("parentId = " + parent.getId());
		}

        sqlBuilder.append(" ORDER BY name");
		
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.rawQuery(sqlBuilder.toString(), null);
		
		List<Subject> subjects = new ArrayList<Subject>();
		
		while(cursor.moveToNext()) {
			Subject subject = new Subject();
			subject.setId(cursor.getInt(cursor.getColumnIndex("id")));	
			subject.setName(cursor.getString(cursor.getColumnIndex("name")));
			subject.setDescription(cursor.getString(cursor.getColumnIndex("description")));
			if(cursor.isNull(cursor.getColumnIndex("parentId"))) {
				subject.setParentId(0);
			} else {
				subject.setParentId(cursor.getInt(cursor.getColumnIndex("parentId")));
			}
			subjects.add(subject);
		}
		
		cursor.close();
		db.close();
		
		return subjects.toArray(new Subject[0]);
	}

    @Override
    public int imageForSubject(Subject subject) {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT "+ TABLE_ITEM_IMAGE + ".imageId FROM " + TABLE_ITEM_IMAGE);
        sqlBuilder.append(" INNER JOIN " + TABLE_ITEM + " ON ( " + TABLE_ITEM + ".id = " + TABLE_ITEM_IMAGE + ".itemId ) ");
        sqlBuilder.append(" INNER JOIN " + TABLE_ITEM_SUBJECT + " ON ( " + TABLE_ITEM + ".id = " + TABLE_ITEM_SUBJECT + ".itemId ) ");
        sqlBuilder.append(" WHERE ");
        sqlBuilder.append(" (" + TABLE_ITEM_SUBJECT +".subjectId = "+ subject.getId() +") ");
        sqlBuilder.append(" ORDER BY RANDOM() ");
        sqlBuilder.append(" LIMIT 1 ");

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlBuilder.toString(), null);

        int imageResult = -1;

        while(cursor.moveToNext()) {
            imageResult = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return imageResult;
    }

    @Override
	public void insertOrUpdate(Item item) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", item.getId());
		contentValues.put("name", item.getName());
		contentValues.put("description", item.getDescription());
		
		insertOrUpdate(TABLE_ITEM, String.valueOf(item.getId()), contentValues);
		
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_ITEM_IMAGE, "itemId = ?", new String[] { String.valueOf(item.getId()) });
		
		for(int imageId : item.getImages()) {
			ContentValues imageValues = new ContentValues();
			imageValues.put("itemId", item.getId());
			imageValues.put("imageId", imageId);
			
			db.insert(TABLE_ITEM_IMAGE, null, imageValues);
		}
		
		for(String beaconId : item.getBeacons()) {
			ContentValues beaconValues = new ContentValues();
			beaconValues.put("itemId", item.getId());
			beaconValues.put("beaconId", beaconId);
			
			db.insert(TABLE_ITEM_BEACON, null, beaconValues);
		}
		
		for(int subjectId : item.getSubjects()) {
			ContentValues subjectValues = new ContentValues();
			subjectValues.put("itemId", item.getId());
			subjectValues.put("subjectId", subjectId);
			
			db.insert(TABLE_ITEM_SUBJECT, null, subjectValues);
		}
	}
	
	private Item[] toItems(StringBuilder sqlBuilder) {
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.rawQuery(sqlBuilder.toString(), null);
		
		List<Item> items = new ArrayList<Item>();
		
		while(cursor.moveToNext()) {
			Item item = new Item();
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setDescription(cursor.getString(cursor.getColumnIndex("description")));
			item.setId(cursor.getInt(cursor.getColumnIndex("id")));
			items.add(item);
		}
		
		cursor.close();
		db.close();
		
		return items.toArray(new Item[0]);
	}

	@Override
	public Item[] itemsForBeacon(Beacon beacon) {
	
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append("SELECT "+ TABLE_ITEM + ".* FROM " + TABLE_ITEM);
		sqlBuilder.append(" INNER JOIN " + TABLE_ITEM_BEACON + " ON ( " + TABLE_ITEM + ".id = " + TABLE_ITEM_BEACON + ".itemId ) ");
		sqlBuilder.append(" WHERE ");
		sqlBuilder.append(FUNC_Beacon.func(beacon));
				
		return toItems(sqlBuilder);
	}
	
	private final static StringUtils.Func<Beacon> FUNC_Beacon = new StringUtils.Func<Beacon>() {
		@Override
		public String func(Beacon value) {
			return "beaconId = '" +  value.getBeaconId() + "'";
		}
	};
	
	private final static StringUtils.Func<Subject> FUNC_Subject = new StringUtils.Func<Subject>() {
		@Override
		public String func(Subject value) {
			return "subjectId = '" +  value.getId() + "'";
		}
	};

	@Override
	public Item[] itemsForSubject(Beacon[] beacons, Subject[] subject) {
		
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append("SELECT "+ TABLE_ITEM + ".* FROM " + TABLE_ITEM);
		sqlBuilder.append(" INNER JOIN " + TABLE_ITEM_BEACON + " ON ( " + TABLE_ITEM + ".id = " + TABLE_ITEM_BEACON + ".itemId ) ");
		sqlBuilder.append(" INNER JOIN " + TABLE_ITEM_SUBJECT + " ON ( " + TABLE_ITEM + ".id = " + TABLE_ITEM_SUBJECT + ".itemId ) ");
		sqlBuilder.append(" WHERE ");
		sqlBuilder.append("( " + StringUtils.join(beacons, FUNC_Beacon, " OR ") + ") ");
		sqlBuilder.append(" AND ");
		sqlBuilder.append("( " + StringUtils.join(subject, FUNC_Subject, " OR ") + ") ");
				
		return toItems(sqlBuilder);
		
	}

	@Override
	public void insertOrUpdate(Beacon beacon) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", beacon.getBeaconId());
		contentValues.put("major", beacon.getMajor());
		contentValues.put("minor", beacon.getMinor());
		
		insertOrUpdate(TABLE_BEACON, beacon.getBeaconId(), contentValues);
	}

	@Override
	public boolean hasBeacon(String beaconId) {
		return contains(TABLE_BEACON, beaconId);
	}

	@Override
	public Beacon beaconById(String beaconId) {
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_BEACON, null, "beaconId=?", new String[] {beaconId }, null, null, null);
		
		if(!cursor.moveToFirst()) return null;
		
		Beacon beacon = new Beacon();
		
		beacon.setBeaconId(cursor.getString(cursor.getColumnIndex("beaconId")));
		beacon.setMajor(cursor.getInt(cursor.getColumnIndex("major")));
		beacon.setMinor(cursor.getInt(cursor.getColumnIndex("minor")));
				
		return beacon;
	}

	@Override
	public void insertOrUpdate(Addition addition) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", addition.getId());
		contentValues.put("itemId", addition.getItemId());
		contentValues.put("a_key", addition.getKey());
		contentValues.put("a_value", addition.getValue());
		
		insertOrUpdate(TABLE_ADDITION, String.valueOf(addition.getId()), contentValues);
	}

	@Override
	public Addition[] additionByItem(Item item) {
		
		List<Addition> additions = new ArrayList<Addition>();
		
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_ADDITION, null, "itemId = ?", new String[] { String.valueOf(item.getId()) }, null, null, null);
		
		while(cursor.moveToNext()) {
			Addition addition = new Addition();
			addition.setItemId(item.getId());
			addition.setId(cursor.getInt(cursor.getColumnIndex("id")));
			addition.setKey(cursor.getString(cursor.getColumnIndex("a_key")));
			addition.setValue(cursor.getString(cursor.getColumnIndex("a_value")));
			
			
			additions.add(addition);
		}
		
		cursor.close();
		db.close();
		
		return additions.toArray(new Addition[0]);
	}

	@Override
	public Item itemById(int id) {
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_ITEM, null, "id=?", new String[] {Integer.valueOf(id).toString() }, null, null, null);
		
		if(!cursor.moveToFirst()) return null;
		
		Item item = new Item();
		
		item.setName(cursor.getString(cursor.getColumnIndex("name")));
		item.setDescription(cursor.getString(cursor.getColumnIndex("description")));
		item.setId(cursor.getInt(cursor.getColumnIndex("id")));
				
		return item; 
	}

}
