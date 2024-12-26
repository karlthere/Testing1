package com.example.testing1.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.testing1.Domain.ItemDomain;
import com.example.testing1.R;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ItemDomain> itemList;
    private OnItemActionListener actionListener;

    public ItemAdapter(Context context, ArrayList<ItemDomain> itemList, OnItemActionListener actionListener) {
        this.context = context;
        this.itemList = itemList;  
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemDomain item = itemList.get(position);

        // Set data teks
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.fee.setText(String.format("Rp %.2f", item.getFee()));

        // Memuat gambar menggunakan Glide
        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(item.getPic(), "drawable", holder.itemView.getContext().getPackageName());

        if (drawableResourceId != 0) {
            Glide.with(holder.itemView.getContext())
                    .load(drawableResourceId)
                    .into(holder.imageView);
        }

        // Mengatur klik tombol
        holder.btnView.setOnClickListener(v -> actionListener.onViewClicked(item));
        holder.btnUpdate.setOnClickListener(v -> actionListener.onUpdateClicked(item));
        holder.btnDelete.setOnClickListener(v -> actionListener.onDeleteClicked(item));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, fee;
        ImageView imageView;
        Button btnView, btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemTitle);
            description = itemView.findViewById(R.id.itemDescription);
            fee = itemView.findViewById(R.id.itemFee);
            imageView = itemView.findViewById(R.id.itemImage);
            btnView = itemView.findViewById(R.id.btnView);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Interface untuk menangani aksi tombol
    public interface OnItemActionListener {
        void onViewClicked(ItemDomain item);
        void onUpdateClicked(ItemDomain item);
        void onDeleteClicked(ItemDomain item);
    }
}
