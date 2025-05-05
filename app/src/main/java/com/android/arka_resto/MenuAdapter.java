package com.android.arka_resto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.arka_resto.models.MenuItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private List<MenuItem> menuItems;

    public MenuAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);

        holder.menuName.setText(menuItem.getNama());
        holder.menuPrice.setText("Rp " + String.format("%,.0f", menuItem.getHarga()));

        // Load image with Glide
        if (menuItem.getGambar() != null && !menuItem.getGambar().isEmpty()) {
            Glide.with(context)
                    .load("https://arka-resto.rf.gd/storage/" + menuItem.getGambar())
                    .placeholder(R.drawable.menu1)
                    .error(R.drawable.menu1)
                    .into(holder.menuImage);
        } else {
            holder.menuImage.setImageResource(R.drawable.menu1);
        }

        // Set click listener for order button
        holder.orderButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, PesanActivity.class);
            intent.putExtra("menu_id", menuItem.getId());
            intent.putExtra("menu_name", menuItem.getNama());
            intent.putExtra("menu_price", menuItem.getHarga());
            intent.putExtra("menu_image", menuItem.getGambar());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public void updateMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        notifyDataSetChanged();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView menuImage;
        TextView menuName, menuPrice;
        Button orderButton;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuImage = itemView.findViewById(R.id.menu_image);
            menuName = itemView.findViewById(R.id.menu_name);
            menuPrice = itemView.findViewById(R.id.menu_price);
            orderButton = itemView.findViewById(R.id.btn_order);
        }
    }
}