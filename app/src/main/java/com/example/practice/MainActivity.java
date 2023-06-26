package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Intent intent =null;
        int id = item.getItemId();
        if (id == R.id.m1) {
            intent = new Intent(this, theatre.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }if (id == R.id.m2) {
            intent = new Intent(this, film.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }if (id == R.id.m3) {
            intent = new Intent(this, new_film.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }if (id == R.id.m4) {
            intent = new Intent(this, new_theatre.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }if (id == R.id.m5) {
            intent = new Intent(this, new_theatre_film.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }if (id == R.id.m6) {
            intent = new Intent(this, Delete_update_theatre.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }if (id == R.id.m7) {
            intent = new Intent(this, Delete_update_film.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}