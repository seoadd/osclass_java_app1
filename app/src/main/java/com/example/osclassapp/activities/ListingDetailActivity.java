package com.example.osclassapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.osclassapp.R;
import com.example.osclassapp.models.Listing;
import com.squareup.picasso.Picasso;

public class ListingDetailActivity extends AppCompatActivity {

    private ImageView ivListingImage;
    private TextView tvTitle, tvPrice, tvDescription, tvLocation, tvCategory, tvContactEmail, tvContactPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail);

        initViews();
        loadListingData();
    }

    private void initViews() {
        ivListingImage = findViewById(R.id.ivListingImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        tvLocation = findViewById(R.id.tvLocation);
        tvCategory = findViewById(R.id.tvCategory);
        tvContactEmail = findViewById(R.id.tvContactEmail);
        tvContactPhone = findViewById(R.id.tvContactPhone);

        // Настройка toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Детали объявления");
        }
    }

    private void loadListingData() {
        Listing listing = (Listing) getIntent().getSerializableExtra("listing");

        if (listing != null) {
            tvTitle.setText(listing.getTitle());
            tvPrice.setText(String.format("%s %.2f", listing.getCurrency(), listing.getPrice()));
            tvDescription.setText(listing.getDescription());
            tvLocation.setText(listing.getLocation());
            tvCategory.setText(listing.getCategory());
            tvContactEmail.setText(listing.getContactEmail());
            tvContactPhone.setText(listing.getContactPhone());

            if (listing.getImage() != null && !listing.getImage().isEmpty()) {
                Picasso.get()
                        .load(listing.getImage())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(ivListingImage);
            } else {
                ivListingImage.setImageResource(R.drawable.placeholder);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}