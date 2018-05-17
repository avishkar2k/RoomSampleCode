package com.example.appinventiv.roomarchitecturedemo.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created on 20/3/18 at IST 7:52 AM
 * Project RoomDemo
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

@Database(entities = {WordEntity.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static WordRoomDatabase INSTANCE;


    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}