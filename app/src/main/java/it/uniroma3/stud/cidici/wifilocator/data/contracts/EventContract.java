package it.uniroma3.stud.cidici.wifilocator.data.contracts;

import android.net.Uri;
import android.provider.BaseColumns;
import it.uniroma3.stud.cidici.wifilocator.data.DataConstants;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 01/08/16.
 */
public final class EventContract {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +
                    Entry.COL_TITLE + " TEXT, " +
                    Entry.COL_ROOM + " TEXT, " +
                    Entry.COL_START_TIME + " TEXT, " +
                    Entry.COL_END_TIME + " TEXT, " +
                    Entry.COL_DESCRIPTION + " TEXT, " +
                    Entry.COL_TALKER + " TEXT" +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    public static final String[] KEYS = new String[]{
            "id",
            "title",
            "room",
            "startTime",
            "endTime",
            "description",
            "talker",
    };

    public static final String[] COLS = new String[]{
            Entry._ID,
            Entry.COL_TITLE,
            Entry.COL_ROOM,
            Entry.COL_START_TIME,
            Entry.COL_END_TIME,
            Entry.COL_DESCRIPTION,
            Entry.COL_TALKER,
    };

    public static final String MIME_DIR = DataConstants.VND_ANDROID_CURSOR_DIR_VND + DataConstants.AUTHORITY + ".event";
    public static final String MIME_ITEM = DataConstants.VND_ANDROID_CURSOR_ITEM_VND + DataConstants.AUTHORITY + ".event";

    public static final String PATH = "events";
    public static final Uri URI = Uri.parse("content://" + DataConstants.AUTHORITY + "/" + PATH);

    private EventContract() {
    }

    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COL_TITLE = "title";
        public static final String COL_ROOM = "room";
        public static final String COL_START_TIME = "startTime";
        public static final String COL_END_TIME = "endTime";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_TALKER = "talker";
    }

}
