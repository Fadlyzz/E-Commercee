package com.android.arka_resto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.arka_resto.models.MenuItem;
import com.bumptech.glide.Glide;

public class PesanActivity extends AppCompatActivity {

    private TextView menuName, menuPrice, menuQuantity;
    private ImageView menuImage;
    private Button decrementBtn, incrementBtn, addToCartBtn;
    private int quantity = 1;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        // Initialize views
        menuName = findViewById(R.id.menu_name);
        menuPrice = findViewById(R.id.menu_price);
        menuQuantity = findViewById(R.id.menu_quantity);
        menuImage = findViewById(R.id.menu_image);
        decrementBtn = findViewById(R.id.decrement_btn);
        incrementBtn = findViewById(R.id.increment_btn);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);

        // Get data from intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("menu_name");
            double price = intent.getDoubleExtra("menu_price", 0);
            String imageUrl = intent.getStringExtra("menu_image");
            int id = intent.getIntExtra("menu_id", 0);

            // Set data to views
            menuName.setText(name);
            menuPrice.setText("Rp " + String.format("%,.0f", price));

            // Load image using Glide (add Glide dependency in your build.gradle)
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this)
                        .load("https://arka-resto.rf.gd/storage/" + imageUrl)
                        .placeholder(R.drawable.menu1)
                        .error(R.drawable.menu1)
                        .into(menuImage);
            } else {
                menuImage.setImageResource(R.drawable.menu1);
            }

            // Create MenuItem object
            menuItem = new MenuItem();
            menuItem.setId(id);
            menuItem.setNama(name);
            menuItem.setHarga(price);
            menuItem.setGambar(imageUrl);
        }

        // Set initial quantity
        menuQuantity.setText(String.valueOf(quantity));

        // Decrement button click listener
        decrementBtn.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                menuQuantity.setText(String.valueOf(quantity));
            }
        });

        // Increment button click listener
        incrementBtn.setOnClickListener(v -> {
            quantity++;
            menuQuantity.setText(String.valueOf(quantity));
        });

        // Add to cart button click listener
        addToCartBtn.setOnClickListener(v -> {
            // Here you would implement cart functionality
            // For now, just show a toast message
            Toast.makeText(this, quantity + " " + menuName.getText() + " ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();

            // Return to main activity
            Intent mainIntent = new Intent(PesanActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }

    public void backToMain(View view) {
        finish();
    }
}