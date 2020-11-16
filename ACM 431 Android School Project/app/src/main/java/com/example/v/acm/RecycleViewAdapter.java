package com.example.v.acm;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;


/********
 IMPORTANT
 Known bugs:
 First swiped item will not be inserted into database but after the first one it should be
 work as intended.
 IMPORTANT
 ******/

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
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private  final String TAG = "RecyclerViewAdapter";
    private  ArrayList<String> mName = new ArrayList<>();
    private Context mContext;

    public RecycleViewAdapter(ArrayList<String> mName, Context mContext) {
        this.mName = mName;
        this.mContext = mContext;
    }

    public RecycleViewAdapter() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlistitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        Log.d(TAG, "onBindViewHolder: called.");
        viewHolder.name.setText(mName.get(position));
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Details = new Intent(mContext,DetailsActivity.class);
                Details.putExtra("name",mName.get(position));
                mContext.startActivity(Details);

            }
        });


    }
    public  void removeItem(int position) {
        mName.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mName.size());

    }
    @Override
    public int getItemCount() {
        return mName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dummyname);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }

    }

}
