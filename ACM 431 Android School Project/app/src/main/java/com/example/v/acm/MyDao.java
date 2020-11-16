package com.example.v.acm;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
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

@Dao
public interface MyDao {


    @Insert
    void insert(User name);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table ORDER BY name ASC")
    LiveData<List<User>> getAllNames();


}
