package com.example.v.acm;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
public class MainActivity extends AppCompatActivity {
    private static String url = "https://api.myjson.com/bins/cak1y";


    private RecyclerView recyclerView;
    private ViewModel mWordViewModel;
    private RecycleViewAdapter adapter;
    private Paint p = new Paint();

    private ArrayList<String> mNames = new ArrayList<>();
    private static final String TAG = "MainActivity";

    private static final String TAG_LOCATION = "location";
    private static final String TAG_TEMP = "temp_c";
    private static final String TAG_REGION = "region";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_HUMIDITY = "humidity";
    private static final String TAG_TEXT = "text";
    JSONArray location = null;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWordViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        final Button buttonParse = findViewById(R.id.button);



        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonParse.setVisibility(View.GONE);
                new JSONParse().execute();
            }
        });
        enableSwipe();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:

                openActivity2();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void openActivity2() {
        Intent degistir = new Intent(this, Main2Activity.class );
        startActivity(degistir);
    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView:s");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecycleViewAdapter(mNames,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Data Aliniyor");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();


            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {

                location = json.getJSONArray(TAG_LOCATION);
                JSONObject c = location.getJSONObject(0);



                String Region = c.getString(TAG_REGION);
                String Country = c.getString(TAG_COUNTRY);
                String Temp_c = c.getString(TAG_TEMP);
                String Text = c.getString(TAG_TEXT);
                String Humidity = c.getString(TAG_HUMIDITY);

                mNames.add("Region: " +Region);

                mNames.add("Country: " + Country);

                mNames.add("Temperature: " + Temp_c +"°C");

                mNames.add("Condition: " + Text);

                mNames.add("Humidity: " + Humidity);
                initRecyclerView();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }



    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public  void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {



                int position = viewHolder.getAdapterPosition();
                User user = new User(mNames.get(position));
               if (direction == ItemTouchHelper.LEFT){


                   mWordViewModel.insert(user);
                   removeItem(position);

               } else {

                   mWordViewModel.insert(user);
                   removeItem(position);
                }
            }
            public  void removeItem(int position) {

                mNames.remove(position);
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);

                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);

                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);

                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
}