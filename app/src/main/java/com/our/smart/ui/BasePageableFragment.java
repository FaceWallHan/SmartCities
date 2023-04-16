package com.our.smart.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.our.smart.databinding.FragmentBasePageBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 用于只包含RecyclerView的Fragment。RecyclerView的adapter
 * <p/>
 * 可以自定义布局管理器，实现ListView，GridView, 瀑布流等特效。只需要重写
 * initRecyclerViewLayoutManager()方法，默认布局管理器是线性布局
 * <p/>
 * 整合了网络数据以及本地数据的加载，关于数据的加载，只需要重写
 * loadPageData(int page)方法。
 * 重写addItemData(T data, boolean isNotify)方法，自定义数据添加时进行的筛选
 * <p/>
 * 调用loadedResult(int currentPage, int totalPage, boolean isLoadSuccess, List<T> list)方法
 * 返回加载结果
 */

public abstract class BasePageableFragment<T> extends BaseFragment {

    protected FragmentBasePageBinding baseBinding;
    private RecyclerView.Adapter mAdapter = null;
    protected RecyclerView.LayoutManager mLayoutManager = null;

    private List<T> mDataList;      //数据列表
    private int mCurrentPage;   //当前页面

    private AtomicBoolean isLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseBinding = FragmentBasePageBinding.inflate(inflater, container, false);
        return baseBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataList = new ArrayList<>();

        baseBinding.flPage.setBackgroundColor(backgroundColor());

        mLayoutManager = initRecyclerViewLayoutManager();
        if (mLayoutManager != null)
            baseBinding.baseListRecycler.setLayoutManager(mLayoutManager);

        mAdapter = onInitAdapter(getActivity(), mDataList);

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                baseBinding.loadingView.hide();
            }
        });


        baseBinding.baseListRecycler.setAdapter(mAdapter);


        mCurrentPage = -1;

        isLoading = new AtomicBoolean(false);

        loadPageDataOfState(0);
    }


    @ColorInt
    protected int backgroundColor() {
        return Color.WHITE;
    }



    private void loadMoreData() {
        loadPageDataOfState(mCurrentPage + 1);
    }

    /**
     * 初始化recyclerview的adapter
     *
     * @return
     */
    protected abstract RecyclerView.Adapter onInitAdapter(Context context, List<T> list);


    protected abstract void loadPageData(int page);

    /**
     * 初始化recyclerview的布局管理器
     *
     * @return
     */
    protected RecyclerView.LayoutManager initRecyclerViewLayoutManager() {
        return new LinearLayoutManager(requireContext());
    }

    protected RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void loadPageDataOfState(int page) {
        if (!isLoading.get()) {
            isLoading.set(true);
            loadPageData(page);
        }
    }

    public void loadSuccess(int currentPage, int totalPage, List<T> list) {

        if (currentPage == 0) {
            reset();
        }

        if (list != null && list.size() > 0) {
            addAllData(list, currentPage == 0);
            mCurrentPage = currentPage;
        } else {
        }

        isLoading.set(false);
    }

    public void reset() {
        mCurrentPage = -1;
        if (mDataList != null) {
            mDataList.clear();
            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 将list中的item全部添加到原始数据
     *
     * @param list
     * @param isClear
     */
    public void addAllData(List<T> list, boolean isClear) {
        if (list != null) {
            if (isClear)
                mDataList.clear();
            for (T data : list) {
                addItemData(data, false);
            }
        }

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void clearData() {
        mCurrentPage = -1;
        if (mDataList != null && mAdapter != null) {
            mDataList.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 如果自定义去重功能，可以重写该方法
     *
     * @param data
     * @param isNotify
     */
    public void addItemData(T data, boolean isNotify) {
        if (data != null) {
            if (mDataList.contains(data))
                return;
            mDataList.add(data);
            if (isNotify) {
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
            }
        }
    }

    public List<T> getDataList() {
        return mDataList == null ? new ArrayList<T>() : mDataList;
    }


}
