package com.example.v.acm;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
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
@Entity(tableName = "user_table")
public class User {
   @PrimaryKey
   @NonNull
   @ColumnInfo(name = "name")
    private String name;

    public User(@NonNull String name) {
        this.name = name;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
