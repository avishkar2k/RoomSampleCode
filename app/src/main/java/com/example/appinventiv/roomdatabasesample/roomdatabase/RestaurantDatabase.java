package com.example.appinventiv.roomdatabasesample.roomdatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created at Appinventiv on 16/4/18 at IST 2:08 AM
 * Project RoomDatabaseSample
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("WeakerAccess")
@Database(entities = {RestaurantEntity.class}, version = 1)
public abstract class RestaurantDatabase extends RoomDatabase {

    //instance of database
    private static RestaurantDatabase INSTANCE;

    //call back
   /* private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute(
                            new RestaurantEntity("name", "address",
                                    "yes/no", "4.5", ""));
                }
            };*/

    //restaurant dao
    public abstract RestaurantDao restaurantDao();

    public static RestaurantDatabase getDatabase(Context context) {

        if (INSTANCE == null) {
            synchronized (RestaurantDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        RestaurantDatabase.class, "restaurant_app_database")
//                        .addCallback(sRoomDatabaseCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return INSTANCE;
    }

    /*private static class PopulateDbAsync extends AsyncTask<RestaurantEntity, Void, Void> {

        private final RestaurantDao mDao;

        PopulateDbAsync(RestaurantDatabase db) {
            mDao = db.restaurantDao();
        }

        @Override
        protected Void doInBackground(final RestaurantEntity... params) {
            mDao.insert(params);
            return null;
        }
    }*/

}
