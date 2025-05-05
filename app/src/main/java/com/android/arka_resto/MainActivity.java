package com.android.arka_resto;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.arka_resto.models.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMakanan;
    private RecyclerView recyclerViewMinuman;
    private MenuAdapter makananAdapter;
    private MenuAdapter minumanAdapter;
    private ProgressBar progressBar;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        progressBar = findViewById(R.id.progress_bar);
        recyclerViewMakanan = findViewById(R.id.recycler_makanan);
        recyclerViewMinuman = findViewById(R.id.recycler_minuman);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        // Setup RecyclerViews
        setupRecyclerViews();

        // Setup ViewPager and TabLayout
        setupViewPagerAndTabs();

        // Load menu data
        loadMakanan();
        loadMinuman();

        // Handle logo click
        ImageView logoImageView = findViewById(R.id.logoImageView);
        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity when logo is clicked
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        // Handle admin logo click
        ImageView admin = findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity when admin logo is clicked
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        // Handle bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // We're already on the main screen
                    return true;
                } else if (id == R.id.nav_makanan) {
                    viewPager.setCurrentItem(0);
                    return true;
                } else if (id == R.id.nav_minuman) {
                    viewPager.setCurrentItem(1);
                    return true;
                }

                return false;
            }
        });
    }

    private void setupRecyclerViews() {
        // Setup Makanan RecyclerView
        makananAdapter = new MenuAdapter(this, new ArrayList<>());
        recyclerViewMakanan.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMakanan.setAdapter(makananAdapter);

        // Setup Minuman RecyclerView
        minumanAdapter = new MenuAdapter(this, new ArrayList<>());
        recyclerViewMinuman.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMinuman.setAdapter(minumanAdapter);
    }

    private void setupViewPagerAndTabs() {
        MenuPagerAdapter pagerAdapter = new MenuPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Makanan");
                    } else {
                        tab.setText("Minuman");
                    }
                }).attach();
    }

    private void loadMakanan() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<MenuItem>> call = apiService.getMakanan();

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<MenuItem> makananList = response.body();
                    makananAdapter.updateMenuItems(makananList);
                } else {
                    Toast.makeText(MainActivity.this, "Gagal memuat makanan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMinuman() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<MenuItem>> call = apiService.getMinuman();

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<MenuItem> minumanList = response.body();
                    minumanAdapter.updateMenuItems(minumanList);
                } else {
                    Toast.makeText(MainActivity.this, "Gagal memuat minuman", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}