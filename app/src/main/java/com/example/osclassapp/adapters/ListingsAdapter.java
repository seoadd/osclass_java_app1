package com.example.osclassapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.osclassapp.R;
import com.example.osclassapp.models.Listing;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.ViewHolder> {
    private List<Listing> listings;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Listing listing);
    }

    public ListingsAdapter(List<Listing> listings, OnItemClickListener listener) {
        this.listings = listings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Listing listing = listings.get(position);
        holder.bind(listing, listener);
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public void updateData(List<Listing> newListings) {
        this.listings = newListings;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvTitle, tvPrice, tvLocation, tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }

        public void bind(Listing listing, OnItemClickListener listener) {
            tvTitle.setText(listing.getTitle());
            tvPrice.setText(String.format("%s %.2f", listing.getCurrency(), listing.getPrice()));
            tvLocation.setText(listing.getLocation());
            tvCategory.setText(listing.getCategory());

            if (listing.getImage() != null && !listing.getImage().isEmpty()) {
                Picasso.get()
                        .load(listing.getImage())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(ivImage);
            } else {
                ivImage.setImageResource(R.drawable.placeholder);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(listing));
        }
    }
}