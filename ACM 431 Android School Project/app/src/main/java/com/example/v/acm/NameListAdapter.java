package com.example.v.acm;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<User> mWords;
    private Context nContext;

    NameListAdapter(Context nContext) {
        mInflater = LayoutInflater.from(nContext);
        this.nContext = nContext;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, final int position) {
        if (mWords != null) {
            User current = mWords.get(position);
            holder.wordItemView.setText(current.getName());
        }  holder.wordItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                search.putExtra(SearchManager.QUERY,holder.wordItemView.getText());
                nContext.startActivity(search);


            }
        });
    }

    void setWords(List<User> names){
        mWords = names;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}