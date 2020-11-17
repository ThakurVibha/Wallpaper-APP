package com.example.wallpaperapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Declare the Recyclerview
    RecyclerView recyclerView;
    //Declare object of adapter
    WallpaperAdapter wallpaperAdapter;
    //create a list
    List<WallpaperModel> wallpaperModelList;

    int pageNumber = 1;
//for endless scrolling

    Boolean isScrolling = true;
    int currentItem, totalItems, scrollOutItems;
    String url = "https://api.pexels.com/v1/curated/?page=" + pageNumber + "&per_page=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        //initialise the list
        wallpaperModelList = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(this, wallpaperModelList);
        //to set adapter on recyclerview
        recyclerView.setAdapter(wallpaperAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                currentItem = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItem + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    fetchWallpaper();

                }
            }
        });
        fetchWallpaper();

    }
    //to fetch wallpaper  and add to recyclerview

    public void fetchWallpaper() {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            //        StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search?query=zaynmalik?page=" + pageNumber + "&per_page=80", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //creating json object

                    JSONObject jsonObject = new JSONObject(response);
                    //create json array
                    JSONArray jsonarray = jsonObject.getJSONArray("photos");
                    //storing the length of array
                    int length = jsonarray.length();
//to load the images from json
                    for (int i = 0; i < length; i++) {

                        JSONObject object = jsonarray.getJSONObject(i);
                        int id = object.getInt("id");
                        JSONObject objectImages = object.getJSONObject("src");

                        String orignalUrl = objectImages.getString("original");
                        String mediumlUrl = objectImages.getString("medium");
//store data in object of model class and add in list
                        WallpaperModel wallpaperModel = new WallpaperModel(id, orignalUrl, mediumlUrl);
                        //adding list
                        wallpaperModelList.add(wallpaperModel);

                    }

                    wallpaperAdapter.notifyDataSetChanged();
                    pageNumber++;

                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //authorization with respect to key pair value


                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "563492ad6f917000010000016fa29fb596874ad9a377040dfd3a08f2");

                return params;
            }
        };
        //execute String List object
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    //inflating the menu with java file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //clicklistner on menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_search) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText editText = new EditText(this);
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            alert.setMessage("Enter category");
            alert.setTitle("Search Wallpaper");
            alert.setView(editText);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String query = editText.getText().toString().toLowerCase();
                    url = "https://api.pexels.com/v1/search/?page=" + pageNumber + "&per_page=80&query=" + query;
                    wallpaperModelList.clear();//to clear and the fetch the queried one
                    fetchWallpaper();

                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}











