package com.example.appinventiv.roomarchitecturedemo.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created on 20/3/18 at IST 7:50 AM
 * Project RoomDemo
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

@Dao
public interface WordDao {

    @Insert
    void insert(WordEntity word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<WordEntity>> getAllWords();
}