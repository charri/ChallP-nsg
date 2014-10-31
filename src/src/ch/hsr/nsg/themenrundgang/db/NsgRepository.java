package ch.hsr.nsg.themenrundgang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;

public class NsgRepository extends SQLiteOpenHelper implements ItemRepository {

	public NsgRepository(Context context) {
		super(context, "repo", null, 1);
	}

	@Override
	public Item getItemByTitle(String title) {
		
		return null;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

	}

	@Override
	public String Foo() {
		return "Test";
	}

}
