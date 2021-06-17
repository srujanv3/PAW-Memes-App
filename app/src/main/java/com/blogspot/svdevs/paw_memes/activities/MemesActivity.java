package com.blogspot.svdevs.paw_memes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.adefruandta.spinningwheel.SpinningWheelView;
import com.blogspot.svdevs.paw_memes.R;

import java.util.ArrayList;
import java.util.Random;

public class MemesActivity extends AppCompatActivity implements SpinningWheelView.OnRotationListener<String> {

    private SpinningWheelView wheelView;

    Button spinBtn;
    ImageButton close;
    ImageView fullImg;

    //Create array lists to hold the image files from drawable
    ArrayList<Integer> mirzapur = new ArrayList<>();
    ArrayList<Integer> loki = new ArrayList<>();
    ArrayList<Integer> joker = new ArrayList<>();
    ArrayList<Integer> spongebob = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memes);

        //Init views
        wheelView = findViewById(R.id.wheelImg);
        spinBtn = findViewById(R.id.btnSpin);

        //setup params for the spin wheel
        wheelView.setItems(R.array.array_one);
        wheelView.setWheelTextSize(22f);
        wheelView.setOnRotationListener(MemesActivity.this);

        addToList();


        //onClickListener
        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wheelView.rotate(80, 3000, 50);
            }
        });

    }

    private void addToList() {

        // add items to the array lists
        mirzapur.add(R.drawable.mirza01);
        mirzapur.add(R.drawable.mirza02);
        mirzapur.add(R.drawable.mirza03);
        mirzapur.add(R.drawable.mirza04);
        mirzapur.add(R.drawable.mirza05);

        loki.add(R.drawable.loki01);
        loki.add(R.drawable.loki02);
        loki.add(R.drawable.loki03);
        loki.add(R.drawable.loki04);
        loki.add(R.drawable.loki05);

        spongebob.add(R.drawable.sponge01);
        spongebob.add(R.drawable.sponge02);
        spongebob.add(R.drawable.sponge03);
        spongebob.add(R.drawable.sponge04);
        spongebob.add(R.drawable.sponge05);

        joker.add(R.drawable.joker01);
        joker.add(R.drawable.joker02);
        joker.add(R.drawable.joker03);
        joker.add(R.drawable.joker04);
        joker.add(R.drawable.joker05);

    }

    @Override
    public void onRotation() {
        Toast.makeText(this, "Spinning...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopRotation(String item) {
        Random rr = new Random();
        int random = rr.nextInt(5);

        if(item.equals("Loki")){
            showMemeDialog(loki.get(random));
        }else if(item.equals("Joker")){
            showMemeDialog(joker.get(random));
        }else if(item.equals("SpongeBob")){
           showMemeDialog(spongebob.get(random));
        }else if(item.equals("Mirzapur")){
            showMemeDialog(mirzapur.get(random));
        }
    }

    public void showMemeDialog(int imgRes){

        //Create dialog to display a random meme from the array list
        Dialog dialog = new Dialog(MemesActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        close = dialog.findViewById(R.id.imageButton);
        fullImg = dialog.findViewById(R.id.full_image);
        fullImg.setImageResource(imgRes);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}