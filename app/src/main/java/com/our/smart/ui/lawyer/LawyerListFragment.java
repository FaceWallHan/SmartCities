package com.our.smart.ui.lawyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.our.smart.bean.Lawyer;
import com.our.smart.bean.LawyerPage;
import com.our.smart.bean.LoginResponse;
import com.our.smart.bean.Page;
import com.our.smart.net.EndUrlUtil;
import com.our.smart.net.HttpUtil;
import com.our.smart.net.NetStateListener;
import com.our.smart.ui.BasePageableFragment;
import com.our.smart.ui.MainActivity;
import com.our.smart.ui.login.LoginActivity;
import com.our.smart.utils.LocalKeyUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LawyerListFragment extends BasePageableFragment<Lawyer> {

    public static final String TAG = "LawyerListFragment";

    public static final String EXTRA_LAWYER_TYPE = "extra_lawyer_type";

    private static final int PAGE_SIZE = 10;

    public enum LawyerType {
        //从业年限
        WORKING_YEAR("workStartAt"),
        //服务人数
        SERVICE_PEOPLE("serviceTimes"),
        //好评率
        GOOD_RATE("favorableRate");

        private final String sort;

        LawyerType(String day) {
            this.sort = day;
        }

        public String getSort() {
            return sort;
        }
    }

    private LawyerType mType;

    private int mLegalExpertiseId;

    private String mName;

    public static LawyerListFragment newInstance(LawyerType type) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_LAWYER_TYPE, type);
        LawyerListFragment fragment = new LawyerListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LawyerListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mType = (LawyerType) getArguments().getSerializable(EXTRA_LAWYER_TYPE);
    }

    @Override
    protected RecyclerView.Adapter onInitAdapter(Context context, List<Lawyer> list) {
        return new LawyerAdapter(list);
    }

    @Override
    protected void loadPageData(int page) {
        HttpUtil.getInstance()
                .inflateEndUrl(getRequest(page))
                .startRealRequest(requireActivity(), LawyerPage.class,new NetStateListener<LawyerPage>() {
                    @Override
                    public void onSuccess(@NonNull LawyerPage response) {
                        List<Lawyer> lawyers = response.getRows();
                        loadSuccess(page,response.getTotal(),lawyers);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private String getRequest(int mPage) {
        Map<String, String> map = new HashMap<>();
        map.put("pageSize", String.valueOf(PAGE_SIZE));
        if (mLegalExpertiseId != 0){
            map.put("legalExpertiseId", String.valueOf(mLegalExpertiseId));
        }
        if (mName != null){
            map.put("name", mName);
        }
        map.put("sort", mType.getSort());
        StringBuilder builder = new StringBuilder(EndUrlUtil.Lawyer_List);
        builder.append("?pageNum=");
        builder.append(mPage);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append("&");
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
        }
        return builder.toString();
    }

    public void onRefresh() {
        loadPageData(0);
    }

    //修改type后刷新
    public void setmType(LawyerType mType) {
        this.mType = mType;
        onRefresh();
    }

    public LawyerType getmType() {
        return mType;
    }
}
