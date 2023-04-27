package com.our.smart.ui.specialty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.our.smart.R;
import com.our.smart.bean.LawSpecialtyItem;
import com.our.smart.databinding.LawSpecialtyItemBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * Date on 2023/4/27.
 */
public class LawSpecialtyAdapter extends RecyclerView.Adapter<LawSpecialtyAdapter.ViewHolder>{

    public Consumer<Integer> itemClickListener;

    public void setItemClickListener(Consumer<Integer> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private final List<LawSpecialtyItem> list;

    public LawSpecialtyAdapter(ArrayList<LawSpecialtyItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LawSpecialtyItemBinding binding = LawSpecialtyItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LawSpecialtyItem item = list.get(position);
        LawSpecialtyItemBinding binding=holder.binding;
        binding.specialtyItemDesc.setText(item.getName());
        Context context = holder.itemView.getContext();
        Glide.with(context).load(item.getImgUrl()).error(R.drawable.default_law_specialty).into(binding.specialtyItemIv);
        binding.getRoot().setOnClickListener(view -> {
            if (itemClickListener != null) {
                itemClickListener.accept(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public LawSpecialtyItemBinding binding;
        public ViewHolder(@NonNull LawSpecialtyItemBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
