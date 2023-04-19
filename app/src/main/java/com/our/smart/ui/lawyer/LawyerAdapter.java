package com.our.smart.ui.lawyer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.our.smart.bean.Lawyer;
import com.our.smart.databinding.LawyerItemLayoutBinding;
import com.our.smart.utils.TimeUtils;

import java.util.List;

//create by daihuazhi
public class LawyerAdapter extends RecyclerView.Adapter<LawyerAdapter.LawyerVH> {

    public static final String TAG = "LawyerAdapter";

    public static final String TIME_FORMAT = "yyyy-MM-dd";

    private List<Lawyer> mData;

    public LawyerAdapter(List<Lawyer> data) {
        mData = data;
    }

    @NonNull
    @Override
    public LawyerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LawyerItemLayoutBinding binding = LawyerItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LawyerVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LawyerVH holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class LawyerVH extends RecyclerView.ViewHolder {

        LawyerItemLayoutBinding mBinding;

        public LawyerVH(@NonNull LawyerItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void setData(Lawyer lawyer) {
            setonClick(lawyer);
            Glide.with(mBinding.getRoot().getContext()).load(lawyer.getAvatarUrl()).into(mBinding.lawyerAvatar);
            mBinding.lawyerName.setText(lawyer.getName());
            mBinding.lawyerExpertise.setText(lawyer.getLegalExpertiseName());
            mBinding.lawyerWorkTime.setText(TimeUtils.getYearDifference(lawyer.getWorkStartAt(),TIME_FORMAT));
            mBinding.lawyerConsultCount.setText(String.valueOf(lawyer.getServiceTimes()));
            mBinding.lawyerGoodRate.setText(String.format("%s%%",lawyer.getFavorableRate()));
        }

        public void setonClick(Lawyer lawyer) {
            mBinding.lawyerItemLayout.setOnClickListener(v -> {
                //TODO 跳转到律师详情页 传入律师id
            });
        }

    }
}
