package com.example.v.acm;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
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
public class Main2Activity extends AppCompatActivity {
    public ViewModel mWordViewModel;
    private RecyclerView recyclerView;
    private ArrayList<String> mWords;
    private RecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

       final NameListAdapter adapter = new NameListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mWordViewModel.getAllNames().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> names) {

                adapter.setWords(names);


            }
        });
    }

}