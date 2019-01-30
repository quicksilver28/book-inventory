package abnd.datastorage.bookstoreinventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import abnd.datastorage.bookstoreinventory.data.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {

    public BookCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

        TextView tvBookName = view.findViewById(R.id.tv_book_name);
        TextView tvAuthorName = view.findViewById(R.id.tv_author_name);
        TextView tvGenre = view.findViewById(R.id.tv_genre);
        TextView tvPrice = view.findViewById(R.id.tv_price);
        final TextView tvQuantity = view.findViewById(R.id.tv_quantity);
        Button btnSell = view.findViewById(R.id.btn_sell);
        Button btnOrder = view.findViewById(R.id.btn_order);

        final int currentRowId = cursor.getInt(cursor.getColumnIndex(BookEntry.COL_ID));
        String bookName = cursor.getString(cursor.getColumnIndex(BookEntry.COL_BOOK_NAME));
        String authorName = cursor.getString(cursor.getColumnIndex(BookEntry.COL_AUTHOR_NAME));
        int genre = cursor.getInt(cursor.getColumnIndex(BookEntry.COL_BOOK_GENRE));
        final int quantity = cursor.getInt(cursor.getColumnIndex(BookEntry.COL_QUANTITY));
        int price = cursor.getInt(cursor.getColumnIndex(BookEntry.COL_PRICE));
        final String publisherPhone = cursor.getString(cursor.getColumnIndex(BookEntry.COL_PUBLISHER_PHONE));

        String priceString = String.valueOf(price);
        String quantityString = String.valueOf(quantity);

        String genreString;

        switch (genre) {
            case BookEntry.GENRE_FICTION:
                genreString = context.getString(R.string.genre_fiction);
                break;
            case BookEntry.GENRE_NON_FICTION:
                genreString = context.getString(R.string.genre_non_fiction);
                break;
            case BookEntry.GENRE_MYSTERY:
                genreString = context.getString(R.string.genre_mystery);
                break;
            case BookEntry.GENRE_CRIME:
                genreString = context.getString(R.string.genre_crime);
                break;
            case BookEntry.GENRE_ROMANCE:
                genreString = context.getString(R.string.genre_romance);
                break;
            case BookEntry.GENRE_EDUCATIONAL:
                genreString = context.getString(R.string.genre_educational);
                break;
            case BookEntry.GENRE_SELF_HELP:
                genreString = context.getString(R.string.genre_self_help);
                break;
            case BookEntry.GENRE_BIOGRAPHY:
                genreString = context.getString(R.string.genre_biography);
                break;
            default:
                genreString = context.getString(R.string.genre_default);
                break;
        }

        tvBookName.setText(bookName);
        tvAuthorName.setText(authorName);
        tvGenre.setText(genreString);

        if(price == 0){
            priceString = context.getString(R.string.free);
        }

        tvPrice.setText(priceString);

        if(quantity == 0) {
            quantityString = context.getString(R.string.no_stock);
        }

        tvQuantity.setText(quantityString);

        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri updateQtyUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, currentRowId);
                if(quantity > 0){
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COL_QUANTITY, quantity - 1);
                    context.getContentResolver().update(updateQtyUri, values, null, null);
                }else{
                    Toast.makeText(context, context.getString(R.string.no_stock) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(TextUtils.isEmpty(publisherPhone)){
                    Toast.makeText(context, "Publisher's phone number not stored.", Toast.LENGTH_SHORT).show();
                }
                Intent openPhone = new Intent(Intent.ACTION_DIAL);
                openPhone.setData(Uri.parse("tel:"+publisherPhone));
                context.startActivity(openPhone);
            }
        });

    }

}
