package com.example.v.acm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
/********
 IMPORTANT
 Known bugs:
 First swiped item will not be inserted into database but after the first one it should be
 work as intended.
 IMPORTANT
 ******/
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getDetails();
    }
    private void getDetails() {
        if(getIntent().hasExtra("name")) {
            String detail = getIntent().getStringExtra("name");
            setDetails(detail);
        }
    }
    private void setDetails(String detail) {
        TextView name = findViewById(R.id.description);
        name.setText(detail);
    }
}
