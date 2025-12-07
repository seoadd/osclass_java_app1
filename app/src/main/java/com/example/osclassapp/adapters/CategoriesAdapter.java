package com.example.osclassapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.osclassapp.R;
import com.example.osclassapp.models.Category;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<Category> categories;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoriesAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category, listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateData(List<Category> newCategories) {
        this.categories = newCategories;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
        }

        public void bind(Category category, OnCategoryClickListener listener) {
            tvName.setText(category.getName());

            if (category.getIcon() != null && !category.getIcon().isEmpty()) {
                Picasso.get()
                        .load(category.getIcon())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(ivIcon);
            } else {
                ivIcon.setImageResource(R.drawable.placeholder);
            }

            itemView.setOnClickListener(v -> listener.onCategoryClick(category));
        }
    }
}