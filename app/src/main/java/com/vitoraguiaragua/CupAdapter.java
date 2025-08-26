package com.vitoraguiaragua;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CupAdapter extends RecyclerView.Adapter<CupAdapter.CupViewHolder> {
    private List<Boolean> copos;
    private WaterViewModel viewModel;

    public CupAdapter(List<Boolean> copos, WaterViewModel viewModel) {
        this.copos = copos;
        this.viewModel = viewModel;
    }

    public void setCopos(List<Boolean> copos) {
        this.copos = copos;
        notifyDataSetChanged();
    }

    public class CupViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCup;

        public CupViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCup = itemView.findViewById(R.id.imageViewCup);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    viewModel.toggleCup(position);
                }
            });
        }
    }

    @NonNull
    @Override
    public CupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cup, parent, false);
        return new CupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CupViewHolder holder, int position) {
        boolean isFull = copos.get(position);
        holder.imageViewCup.setImageResource(isFull ? R.drawable.cup_full : R.drawable.cup_empty);
    }

    @Override
    public int getItemCount() {
        return copos != null ? copos.size() : 0;
    }
}