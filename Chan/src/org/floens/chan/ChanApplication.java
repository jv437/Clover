package org.floens.chan;

import org.floens.chan.core.manager.BoardManager;
import org.floens.chan.core.manager.PinnedManager;
import org.floens.chan.core.manager.ReplyManager;
import org.floens.chan.database.DatabaseManager;
import org.floens.chan.service.PinnedService;
import org.floens.chan.utils.IconCache;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.extra.BitmapLruImageCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class ChanApplication extends Application {
    public static final boolean DEVELOPER_MODE = true;

    private static ChanApplication instance;
    private static RequestQueue volleyRequestQueue;
    private static ImageLoader imageLoader;
    private static BoardManager boardManager;
    private static PinnedManager pinnedManager;
    private static ReplyManager replyManager;
    private static DatabaseManager databaseManager;

    public ChanApplication() {
        instance = this;
    }

    public static ChanApplication getInstance() {
        return instance;
    }

    public static RequestQueue getVolleyRequestQueue() {
        return volleyRequestQueue;
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static BoardManager getBoardManager() {
        return boardManager;
    }

    public static PinnedManager getPinnedManager() {
        return pinnedManager;
    }

    public static ReplyManager getReplyManager() {
        return replyManager;
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(instance);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (ChanApplication.DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        }

        IconCache.createIcons(this);

        volleyRequestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(volleyRequestQueue, new BitmapLruImageCache(1024 * 1024 * 8));

        databaseManager = new DatabaseManager(this);
        boardManager = new BoardManager(this);
        pinnedManager = new PinnedManager(this);
        replyManager = new ReplyManager(this);

        PinnedService.updateRunningState(this);
    }
}



