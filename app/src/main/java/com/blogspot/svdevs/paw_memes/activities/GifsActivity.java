package com.blogspot.svdevs.paw_memes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blogspot.svdevs.paw_memes.BuildConfig;
import com.blogspot.svdevs.paw_memes.R;
import com.blogspot.svdevs.paw_memes.adapter.DataAdapter;
import com.blogspot.svdevs.paw_memes.model.DataModel;
import com.blogspot.svdevs.paw_memes.ui.SpacesItemDecoration;
import com.blogspot.svdevs.paw_memes.utils.CheckNetwork;
import com.blogspot.svdevs.paw_memes.utils.MySingleton;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GifsActivity extends AppCompatActivity implements DataAdapter.OnItemClickListener {

    RecyclerView rView;
    ArrayList<DataModel> modelArrayList = new ArrayList<>();
    DataAdapter adapter;
    RelativeLayout relativeLayout;

    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String BASE_URL = "https://api.giphy.com/v1/gifs/trending?api_key=";
    String url = BASE_URL + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifs);

        Init();

        //Network availability check
        if(CheckNetwork.isInternetAvailable(this)){
            getData();
            relativeLayout.setVisibility(View.GONE);
        }else{
           relativeLayout.setVisibility(View.VISIBLE);
        }

    }

    private void Init() {
        relativeLayout = findViewById(R.id.relativeLayout);

        rView = findViewById(R.id.recyclerView);
        rView.setLayoutManager(new GridLayoutManager(this, 2));

        //Add spaces between the items in the layout
        rView.addItemDecoration(new SpacesItemDecoration(10));

        rView.setHasFixedSize(true);
    }

    private void getData() {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Access the initial array named 'data'
                    JSONArray dataArray = response.getJSONArray("data");

                    // From the accessed array parse all the objects
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject obj = dataArray.getJSONObject(i);

                        String id = obj.getString("id");

                        // retrieve the objects with key name images
                        JSONObject obj2 = obj.getJSONObject("images");

                        // retrieve the desired resolution of the gif eg.(downsized_medium)
                        JSONObject obj3 = obj2.getJSONObject("downsized_medium");

                        // retrieve the source url of the gif
                        String sourceUrl = obj3.getString("url");

                        // Adding the source url in the list
                        modelArrayList.add(new DataModel(sourceUrl,id));
                    }

                    adapter = new DataAdapter(GifsActivity.this, modelArrayList);
                    rView.setAdapter(adapter);
                    adapter.setOnItemClickListener(GifsActivity.this);


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GifsActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //Adding to the request queue
        MySingleton.getInstance(this).addToRequestQueue(objectRequest);
    }

    @Override
    public void onItemClick(int pos) {
        //Dialog to display the gifs
        gifDialog(pos);
    }

    private void gifDialog(int pos) {

        ImageButton close;
        ImageView fullImg;

        DataModel clickedItem = modelArrayList.get(pos);

        Dialog dialog = new Dialog(GifsActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        close = dialog.findViewById(R.id.imageButton);
        fullImg = dialog.findViewById(R.id.full_image);

        Glide.with(getApplicationContext()).load(clickedItem.getImageUrl()).into(fullImg);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
}
