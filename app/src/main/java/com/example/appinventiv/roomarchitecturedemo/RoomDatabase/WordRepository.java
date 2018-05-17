package com.example.appinventiv.roomarchitecturedemo.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created on 20/3/18 at IST 7:54 AM
 * Project RoomDemo
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<WordEntity>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<WordEntity>> getAllWords() {
        return mAllWords;
    }


    public void insert (WordEntity word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<WordEntity, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WordEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}