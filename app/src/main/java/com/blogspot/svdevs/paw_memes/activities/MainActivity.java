package com.blogspot.svdevs.paw_memes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.svdevs.paw_memes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init views
        Button gifsBtn = findViewById(R.id.btn_gifs);
        Button spinnerBtn = findViewById(R.id.btn_spinner);

        //Setting onClick listeners
        gifsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To GifsActivity
                startActivity(new Intent(MainActivity.this, GifsActivity.class));
            }
        });
        spinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To MemesActivity
                startActivity(new Intent(MainActivity.this, MemesActivity.class));
            }
        });
    }
}