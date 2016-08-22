package it.uniroma3.stud.cidici.wifilocator.sync;

import android.accounts.Account;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import it.uniroma3.stud.cidici.wifilocator.data.contracts.EventContract;
import it.uniroma3.stud.cidici.wifilocator.network.ServerRequests;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private final ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {

        try {
            syncEvents();
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void syncList(JSONArray response, Uri uri, String[] cols, String[] keys) throws JSONException {
        for (int i = 0; i < response.length(); i++) {
            JSONObject jsonObject = response.getJSONObject(i);
            ContentValues values = new ContentValues(3);
            for (int j = 0; j < cols.length; j++) {
                String string;
                try {
                    string = jsonObject.getString(keys[j]);
                } catch (JSONException e) {
                    string = "";
                }
                values.put(cols[j], string);
            }
            mContentResolver.insert(uri, values);
        }
    }

    private void syncEvents() throws ExecutionException, InterruptedException, JSONException {
        JSONArray response = ServerRequests.eventsList().get();
        syncList(response, EventContract.URI, EventContract.COLS, EventContract.KEYS);
        getContext().getContentResolver().notifyChange(EventContract.URI, null, false);
    }

}