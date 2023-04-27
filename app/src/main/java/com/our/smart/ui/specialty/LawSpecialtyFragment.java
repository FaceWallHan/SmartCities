package com.our.smart.ui.specialty;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;

import com.our.smart.bean.LawSpecialtyItem;
import com.our.smart.databinding.LawSpecialtyFragmentBinding;

import java.util.ArrayList;


/**
 * Date on 2023/4/27.
 */
public class LawSpecialtyFragment extends Fragment {
    private ArrayList<LawSpecialtyItem> list;

    private static final String EXTRA_LAWYER_SPECIALTY="EXTRA_LAWYER_SPECIALTY";
    private LawSpecialtyFragment() {}

    private LawSpecialtyFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=LawSpecialtyFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        list = getArguments().getParcelableArrayList(EXTRA_LAWYER_SPECIALTY);
        LawSpecialtyAdapter adapter = new LawSpecialtyAdapter(list);
        adapter.setItemClickListener(itemClickListener);
        binding.lawSpecialtyList.setAdapter(adapter);
    }

    public static LawSpecialtyFragment newInstance(ArrayList<LawSpecialtyItem> list) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_LAWYER_SPECIALTY,list);
        LawSpecialtyFragment fragment = new LawSpecialtyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final Consumer<Integer> itemClickListener=new Consumer<Integer>() {
        @Override
        public void accept(Integer position) {
            Log.d("1111111111112333", "accept: _____________"+list.get(position));
            Intent intent = new Intent().putExtra("","");
            startActivity(intent);
        }
    };
}
