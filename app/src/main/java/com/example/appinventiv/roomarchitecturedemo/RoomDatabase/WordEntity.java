package com.example.appinventiv.roomarchitecturedemo.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created on 20/3/18 at IST 7:43 AM
 * Project RoomDemo
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */
@Entity(tableName = "word_table")
public class WordEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public WordEntity(@NonNull String word) {
        this.mWord = word;
    }

    public String getWord() {
        return this.mWord;
    }
}