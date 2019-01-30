package abnd.datastorage.bookstoreinventory;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import abnd.datastorage.bookstoreinventory.data.BookContract.BookEntry;

public class BookDisplay extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int BOOK_LOADER = 0;
    private BookCursorAdapter mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_display);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.book_pile);

        FloatingActionButton fabAddBook = findViewById(R.id.floating_btn_action);
        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDisplay.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView bookList = findViewById(R.id.list_book);
        View emptyView = findViewById(R.id.empty_view);
        bookList.setEmptyView(emptyView);

        mBookAdapter = new BookCursorAdapter(this, null);
        bookList.setAdapter(mBookAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookDisplay.this, EditorActivity.class);
                Uri bookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                intent.setData(bookUri);
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(BOOK_LOADER, null, this);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {BookEntry.COL_ID, BookEntry.COL_BOOK_NAME,
                BookEntry.COL_AUTHOR_NAME, BookEntry.COL_BOOK_GENRE,
                BookEntry.COL_PRICE, BookEntry.COL_QUANTITY, BookEntry.COL_PUBLISHER_PHONE};

        return new CursorLoader(this, BookEntry.CONTENT_URI, projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mBookAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mBookAdapter.swapCursor(null);
    }

}
