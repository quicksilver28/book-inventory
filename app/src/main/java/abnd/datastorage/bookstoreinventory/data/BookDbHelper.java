package abnd.datastorage.bookstoreinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import abnd.datastorage.bookstoreinventory.data.BookContract.BookEntry;

public class BookDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookstore.db";

    private static final int DATABASE_VERSION = 1;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_BOOK_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + "(" +
                BookEntry.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BookEntry.COL_BOOK_NAME + " TEXT NOT NULL," +
                BookEntry.COL_AUTHOR_NAME + " TEXT NOT NULL," +
                BookEntry.COL_BOOK_TYPE + " INTEGER DEFAULT 0," +
                BookEntry.COL_BOOK_GENRE + " INTEGER DEFAULT 0," +
                BookEntry.COL_PRICE + " INTEGER NOT NULL," +
                BookEntry.COL_QUANTITY + " INTEGER NOT NULL," +
                BookEntry.COL_PUBLISHER_NAME + " TEXT NOT NULL," +
                BookEntry.COL_PUBLISHER_PHONE + " TEXT);";

        db.execSQL(SQL_CREATE_BOOK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
