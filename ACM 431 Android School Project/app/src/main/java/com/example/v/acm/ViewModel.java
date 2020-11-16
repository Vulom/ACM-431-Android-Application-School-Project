package com.example.v.acm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

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
/********
 IMPORTANT
 Known bugs:
 First swiped item will not be inserted into database but after the first one it should be
 work as intended.
 IMPORTANT
 ******/
public class ViewModel extends AndroidViewModel {

    private NameRepository mRepository;

    private LiveData<List<User>> mAllNames;

    public ViewModel(Application application) {
        super(application);
        mRepository = new NameRepository(application);
        mAllNames = mRepository.getAllNames();
    }

    LiveData<List<User>> getAllNames() {
        return mAllNames;
    }

    public void insert(User name) {
        mRepository.insert(name);
    }
}