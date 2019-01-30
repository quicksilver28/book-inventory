package abnd.datastorage.bookstoreinventory;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import abnd.datastorage.bookstoreinventory.data.BookContract.BookEntry;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int BOOK_LOADER = 0;

    private Button mIncreaseQuantity;
    private Button mDecreaseQuantity;

    private int mQuantity;

    private EditText mEtBookName;
    private EditText mEtAuthor;
    private EditText mEtPrice;
    private EditText mEtQuantity;
    private EditText mEtPublisherName;
    private EditText mEtPublisherPhone;

    private Spinner mTypeSpinner;
    private Spinner mGenreSpinner;

    private int mType = BookEntry.TYPE_PAPERBACK;
    private int mGenre = BookEntry.GENRE_DEFAULT;

    private Uri mBookUri;

    private boolean mBookHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mBookHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mBookUri = intent.getData();

        if (mBookUri == null) {
            setTitle(getString(R.string.add_book_title));
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.edit_book_title));
            getSupportLoaderManager().initLoader(BOOK_LOADER, null, this);
        }

        mEtBookName = findViewById(R.id.et_book_name);
        mEtAuthor = findViewById(R.id.et_author_name);
        mEtPrice = findViewById(R.id.et_book_price);
        mEtQuantity = findViewById(R.id.et_book_quantity);
        mEtPublisherName = findViewById(R.id.et_publisher_name);
        mEtPublisherPhone = findViewById(R.id.et_publisher_phone);
        mTypeSpinner = findViewById(R.id.spinner_type);
        mGenreSpinner = findViewById(R.id.spinner_genre);

        mIncreaseQuantity = findViewById(R.id.btn_quantity_inc);
        mDecreaseQuantity = findViewById(R.id.btn_quantity_dec);

        mEtBookName.setOnTouchListener(mTouchListener);
        mEtAuthor.setOnTouchListener(mTouchListener);
        mTypeSpinner.setOnTouchListener(mTouchListener);
        mGenreSpinner.setOnTouchListener(mTouchListener);
        mEtPrice.setOnTouchListener(mTouchListener);
        mEtQuantity.setOnTouchListener(mTouchListener);
        mEtPublisherName.setOnTouchListener(mTouchListener);
        mEtPublisherPhone.setOnTouchListener(mTouchListener);

        setUpTypeSpinner();
        setUpGenreSpinner();

        mIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuantity++;
                mEtQuantity.setText(String.valueOf(mQuantity));
            }
        });

        mDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuantity>0){
                    mQuantity--;
                    mEtQuantity.setText(String.valueOf(mQuantity));
                }
            }
        });

    }

    private void setUpTypeSpinner() {

        ArrayAdapter typeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_book_type, android.R.layout.simple_dropdown_item_1line);

        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mTypeSpinner.setAdapter(typeSpinnerAdapter);

        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_paperback))) {
                        mType = BookEntry.TYPE_PAPERBACK;
                    } else {
                        mType = BookEntry.TYPE_HARDCOVER;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = BookEntry.TYPE_PAPERBACK;
            }
        });

    }

    private void setUpGenreSpinner() {

        ArrayAdapter genreSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_book_genre, android.R.layout.simple_dropdown_item_1line);

        genreSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenreSpinner.setAdapter(genreSpinnerAdapter);

        mGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.genre_fiction))) {
                        mGenre = BookEntry.GENRE_FICTION;
                    } else if (selection.equals(getString(R.string.genre_non_fiction))) {
                        mGenre = BookEntry.GENRE_NON_FICTION;
                    } else if (selection.equals(getString(R.string.genre_mystery))) {
                        mGenre = BookEntry.GENRE_MYSTERY;
                    } else if (selection.equals(getString(R.string.genre_crime))) {
                        mGenre = BookEntry.GENRE_CRIME;
                    } else if (selection.equals(getString(R.string.genre_romance))) {
                        mGenre = BookEntry.GENRE_ROMANCE;
                    } else if (selection.equals(getString(R.string.genre_educational))) {
                        mGenre = BookEntry.GENRE_EDUCATIONAL;
                    } else if (selection.equals(getString(R.string.genre_self_help))) {
                        mGenre = BookEntry.GENRE_SELF_HELP;
                    } else if (selection.equals(getString(R.string.genre_biography))) {
                        mGenre = BookEntry.GENRE_BIOGRAPHY;
                    } else {
                        mGenre = BookEntry.GENRE_DEFAULT;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGenre = BookEntry.GENRE_DEFAULT;
            }
        });

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {BookEntry.COL_ID, BookEntry.COL_BOOK_NAME, BookEntry.COL_AUTHOR_NAME,
                BookEntry.COL_BOOK_TYPE, BookEntry.COL_BOOK_GENRE, BookEntry.COL_PRICE,
                BookEntry.COL_QUANTITY, BookEntry.COL_PUBLISHER_NAME, BookEntry.COL_PUBLISHER_PHONE};

        return new CursorLoader(this, mBookUri, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        if (data == null || data.getCount() < 1) {
            return;
        }

        if (data.moveToFirst()) {

            String bookName = data.getString(data.getColumnIndex(BookEntry.COL_BOOK_NAME));
            String authorName = data.getString(data.getColumnIndex(BookEntry.COL_AUTHOR_NAME));
            int type = data.getInt(data.getColumnIndex(BookEntry.COL_BOOK_TYPE));
            int genre = data.getInt(data.getColumnIndex(BookEntry.COL_BOOK_GENRE));
            int price = data.getInt(data.getColumnIndex(BookEntry.COL_PRICE));
            mQuantity = data.getInt(data.getColumnIndex(BookEntry.COL_QUANTITY));
            String publisherName = data.getString(data.getColumnIndex(BookEntry.COL_PUBLISHER_NAME));
            String publisherPhone = data.getString(data.getColumnIndex(BookEntry.COL_PUBLISHER_PHONE));

            mEtBookName.setText(bookName);
            mEtAuthor.setText(authorName);
            mEtPrice.setText(String.valueOf(price));
            mEtQuantity.setText(String.valueOf(mQuantity));
            mEtPublisherName.setText(publisherName);
            mEtPublisherPhone.setText(publisherPhone);

            switch (type) {
                case BookEntry.TYPE_HARDCOVER:
                    mTypeSpinner.setSelection(1);
                    break;
                default:
                    mTypeSpinner.setSelection(0);
                    break;
            }

            switch (genre) {
                case BookEntry.GENRE_FICTION:
                    mGenreSpinner.setSelection(1);
                    break;
                case BookEntry.GENRE_NON_FICTION:
                    mGenreSpinner.setSelection(2);
                    break;
                case BookEntry.GENRE_MYSTERY:
                    mGenreSpinner.setSelection(3);
                    break;
                case BookEntry.GENRE_CRIME:
                    mGenreSpinner.setSelection(4);
                    break;
                case BookEntry.GENRE_ROMANCE:
                    mGenreSpinner.setSelection(5);
                    break;
                case BookEntry.GENRE_EDUCATIONAL:
                    mGenreSpinner.setSelection(6);
                    break;
                case BookEntry.GENRE_SELF_HELP:
                    mGenreSpinner.setSelection(7);
                    break;
                case BookEntry.GENRE_BIOGRAPHY:
                    mGenreSpinner.setSelection(8);
                    break;
                default:
                    mGenreSpinner.setSelection(0);
                    break;
            }

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        mEtBookName.setText("");
        mEtAuthor.setText("");
        mEtPrice.setText("");
        mEtQuantity.setText("");
        mEtPublisherName.setText("");
        mEtPublisherPhone.setText("");
        mTypeSpinner.setSelection(0);
        mGenreSpinner.setSelection(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mBookUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if(saveBookDetails()) {
                    finish();
                }
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if(!mBookHasChanged){
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean saveBookDetails() {

        String bookName = mEtBookName.getText().toString().trim();
        String authorName = mEtAuthor.getText().toString().trim();
        String priceString = mEtPrice.getText().toString().trim();
        String quantityString = mEtQuantity.getText().toString().trim();
        String publisherName = mEtPublisherName.getText().toString().trim();
        String publisherPhone = mEtPublisherPhone.getText().toString().trim();

        int price = 0;
        int quantity = 0;

        if (mBookUri == null && TextUtils.isEmpty(bookName) && TextUtils.isEmpty(authorName) &&
                TextUtils.isEmpty(priceString) && TextUtils.isEmpty(quantityString) &&
                TextUtils.isEmpty(publisherName) && TextUtils.isEmpty(publisherPhone) &&
                mType == BookEntry.TYPE_PAPERBACK && mGenre == BookEntry.GENRE_DEFAULT) {
            return true;
        }

        if (!TextUtils.isEmpty(priceString)) {
            price = Integer.parseInt(priceString);
        }

        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(quantityString);
        }

        if(!validationCheck(bookName, authorName, price, publisherName, publisherPhone)){
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(BookEntry.COL_BOOK_NAME, bookName);
        values.put(BookEntry.COL_AUTHOR_NAME, authorName);
        values.put(BookEntry.COL_BOOK_TYPE, mType);
        values.put(BookEntry.COL_BOOK_GENRE, mGenre);
        values.put(BookEntry.COL_PRICE, price);
        values.put(BookEntry.COL_QUANTITY, quantity);
        values.put(BookEntry.COL_PUBLISHER_NAME, publisherName);
        values.put(BookEntry.COL_PUBLISHER_PHONE, publisherPhone);

        if (mBookUri == null) {

            Uri insertUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

            if (insertUri == null) {
                Toast.makeText(this, getString(R.string.fail_save), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.success_save), Toast.LENGTH_SHORT).show();
            }

        } else {

            int rowsUpdated = getContentResolver().update(mBookUri, values, null, null);

            if (rowsUpdated == 0) {
                Toast.makeText(this, getString(R.string.fail_update), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.success_update), Toast.LENGTH_SHORT).show();
            }

        }

        return true;

    }

    private boolean validationCheck(String bookName, String authorName,
                                    int price, String publisherName,
                                    String publisherPhone){

        if(TextUtils.isEmpty(bookName)){
            Toast.makeText(this, getString(R.string.invalid_title), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(authorName)){
            Toast.makeText(this, getString(R.string.invalid_author_name),Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(price<0){
            Toast.makeText(this,getString(R.string.invaild_price), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(publisherName)){
            Toast.makeText(this, getString(R.string.invalid_publisher_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(publisherPhone.length()>10){
            Toast.makeText(this, getString(R.string.invalid_publisher_phone),Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }

    }

    private void deleteBookDetails(){

        if(mBookUri != null){

            int rowsDeleted = getContentResolver().delete(mBookUri, null, null);

            if(rowsDeleted == 0){
                Toast.makeText(this, getString(R.string.fail_delete), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.success_delete), Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }

    private void showDeleteConfirmationDialog(){

        AlertDialog.Builder buildDialog = new AlertDialog.Builder(this);
        buildDialog.setMessage(getString(R.string.delete_dialog_message));
        buildDialog.setPositiveButton(getString(R.string.delete_dialog_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBookDetails();
            }
        });
        buildDialog.setNegativeButton(getString(R.string.delete_dialog_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });

        AlertDialog deleteDialog = buildDialog.create();
        deleteDialog.show();
    }

    @Override
    public void onBackPressed() {

        if(!mBookHasChanged){
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardClickListener);

    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardClickListener){

        AlertDialog.Builder buildDialog = new AlertDialog.Builder(this);
        buildDialog.setMessage(getString(R.string.discard_dialog_message));
        buildDialog.setPositiveButton(getString(R.string.discard_dialog_positive), discardClickListener);
        buildDialog.setNegativeButton(getString(R.string.discard_dialog_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = buildDialog.create();
        alertDialog.show();

    }

}
