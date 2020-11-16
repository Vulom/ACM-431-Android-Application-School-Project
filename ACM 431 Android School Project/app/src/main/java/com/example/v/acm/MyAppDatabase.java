package com.example.v.acm;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
/********
 IMPORTANT
 Known bugs:
 First swiped item will not be inserted into database but after the first one it should be
 work as intended.
 IMPORTANT
 ******/
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract MyDao myDao();
    private static MyAppDatabase INSTANCE;

    static MyAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyAppDatabase.class, "name_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    public static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        public final MyDao mDao;



        PopulateDbAsync(MyAppDatabase db) {
            mDao = db.myDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            mDao.deleteAll();


            return null;

        }

    }

}
