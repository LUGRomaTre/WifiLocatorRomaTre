package it.uniroma3.stud.cidici.wifilocator.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import it.uniroma3.stud.cidici.wifilocator.data.contracts.EventContract;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 01/08/16.
 */
public class LinuxDayProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DataConstants.AUTHORITY, EventContract.PATH, 1);
        sUriMatcher.addURI(DataConstants.AUTHORITY, EventContract.PATH + "/#", 2);
    }

    private LinuxDayDatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new LinuxDayDatabaseHelper(getContext());
        return true;
    }

    public Cursor query(
            @NonNull Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        String table;
        switch (sUriMatcher.match(uri)) {
            case 1:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
                table = EventContract.Entry.TABLE_NAME;
                break;

            case 2:
                table = EventContract.Entry.TABLE_NAME;
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Received an invalid content URI");
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(table, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case 1:
                return EventContract.MIME_DIR;
            case 2:
                return EventContract.MIME_ITEM;
            default:
                throw new IllegalArgumentException("Received an invalid content URI");
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (values.size() == 0) throw new IllegalArgumentException("Received empty ContentValues");

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String table;

        switch (sUriMatcher.match(uri)) {
            case 1:
                table = EventContract.Entry.TABLE_NAME;
                break;

            default:
                throw new IllegalArgumentException("Received an invalid content URI");
        }

        long newRowId;
        newRowId = db.replace(table, null, values);
        if (newRowId == -1) throw new IllegalArgumentException("Cannot insert new row to table '" + table + "'");
        return ContentUris.withAppendedId(uri, newRowId);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}