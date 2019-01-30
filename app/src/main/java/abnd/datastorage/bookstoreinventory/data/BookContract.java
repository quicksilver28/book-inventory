package abnd.datastorage.bookstoreinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class BookContract {

    private BookContract() {

    }

    public static final String CONTENT_AUTHORITY = "abnd.datastorage.bookstoreinventory";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_BOOKS = "book";

    public static final class BookEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY +
                        "/" + PATH_BOOKS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY +
                        "/" + PATH_BOOKS;

        public static final String TABLE_NAME = "book";

        public static final String COL_ID = BaseColumns._ID;
        public static final String COL_BOOK_NAME = "book_name";
        public static final String COL_AUTHOR_NAME = "author_name";
        public static final String COL_BOOK_TYPE = "book_type";
        public static final String COL_BOOK_GENRE = "book_genre";
        public static final String COL_PRICE = "price";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_PUBLISHER_NAME = "publisher_name";
        public static final String COL_PUBLISHER_PHONE = "publisher_phone";

        public static final int TYPE_PAPERBACK = 0;
        public static final int TYPE_HARDCOVER = 1;

        public static final int GENRE_DEFAULT = 0;
        public static final int GENRE_FICTION = 1;
        public static final int GENRE_NON_FICTION = 2;
        public static final int GENRE_MYSTERY = 3;
        public static final int GENRE_CRIME = 4;
        public static final int GENRE_ROMANCE = 5;
        public static final int GENRE_EDUCATIONAL = 6;
        public static final int GENRE_SELF_HELP = 7;
        public static final int GENRE_BIOGRAPHY = 8;

    }

}
