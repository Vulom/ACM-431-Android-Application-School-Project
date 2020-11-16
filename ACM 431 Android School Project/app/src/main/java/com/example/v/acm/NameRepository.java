package com.example.v.acm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/*
References:
    Coding With Mitch:
        https://codingwithmitch.com/
        https://www.youtube.com/channel/UCoNZZLhPuuRteu02rh7bzsw
    Coding in Flow:
        https://www.youtube.com/channel/UC_Fh8kvtkVPkeihBs42jGcA
    Codelabs Google Developers
        https://codelabs.developers.google.com/android-training/


 */

public class NameRepository {
    private MyDao mNameDao;
    private LiveData<List<User>> mAllName;
    NameRepository(Application application) {
        MyAppDatabase db = MyAppDatabase.getDatabase(application);
        mNameDao = db.myDao();
        mAllName = mNameDao.getAllNames();
    }
    LiveData<List<User>> getAllNames() {
        return mAllName;
    }
    public void insert (User word) {
        new insertAsyncTask(mNameDao).execute(word);
    }
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private MyDao mAsyncTaskDao;

        insertAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
