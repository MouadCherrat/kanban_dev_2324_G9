package com.example.kanban;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.kanban.RetrofitClient;
import com.example.kanban.ApiService;

public class Main extends AppCompatActivity {

    private AppCompatButton getstartedButton; // Renamed for better clarity

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        // Retrieve a reference to the get started button from the XML layout
        getstartedButton = findViewById(R.id.getstarted);

        // Add an event listener to detect clicks on the get started button
        getstartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make a network request before going to the next page
                fetchDataFromBackend();
            }
        });
    }

    // Method to transition to the next activity
    private void goToNextPage() {
        Intent intent = new Intent(Main.this, Login_main_controller.class);
        startActivity(intent);
        finish();  // Finish the current activity
    }

    // Method to fetch data from the backend
    private void fetchDataFromBackend() {
        ApiService service = RetrofitClient.getInstance().create(ApiService.class);
        Call<String> call = service.getData();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Optionally process data here
                    goToNextPage();
                } else {
                    // Handle errors here
                    goToNextPage(); // Optionally move to the next page even on failure
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle the failure of the call
                goToNextPage(); // Optionally move to the next page even on failure
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            // Handle profile icon click
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
