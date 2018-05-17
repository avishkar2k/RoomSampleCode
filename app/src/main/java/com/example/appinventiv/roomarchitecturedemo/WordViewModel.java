package com.example.appinventiv.roomarchitecturedemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.appinventiv.roomarchitecturedemo.RoomDatabase.WordEntity;
import com.example.appinventiv.roomarchitecturedemo.RoomDatabase.WordRepository;

import java.util.List;

/**
 * Created on 20/3/18 at IST 7:55 AM
 * Project RoomDemo
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private LiveData<List<WordEntity>> mAllWords;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<WordEntity>> getAllWords() { return mAllWords; }

    public void insert(WordEntity word) { mRepository.insert(word); }
}