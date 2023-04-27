package com.our.smart.bean;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Date on 2023/4/27.
 */
public class LawSpecialtyResponse extends BaseBean {
    private Integer total;
    private ArrayList<LawSpecialtyItem> rows;

    public Integer getTotal() {
        return total;
    }

    public ArrayList<LawSpecialtyItem> getRows() {
        return rows;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LawSpecialtyResponse{");
        if (rows!=null){
            for (int i = 0; i < rows.size(); i++) {
                builder.append("LawSpecialtyItem :").append(rows.get(i).toString()).append("\n");
            }
        }else {
            builder.append("rows=null}");
        }
        return builder.toString();
    }
}
