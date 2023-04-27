package com.our.smart.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.our.smart.net.EndUrlUtil;


/**
 * Date on 2023/4/27.<br>
 * {
 *       "searchValue": null,
 *       "createBy": null,
 *       "createTime": null,
 *       "updateBy": null,
 *       "updateTime": null,
 *       "remark": null,
 *       "params": {},
 *       "id": 10,
 *       "name": "拆迁安置",
 *       "sort": 1,
 *       "imgUrl": "/prod-api/profile/upload/image/2022/03/12/c97db288-c13d-4d62-9027-418b402bbc01.png"
 *     }
 */
public class LawSpecialtyItem implements Parcelable {
    private final String name,imgUrl;
    private final Integer id,sort;

    public LawSpecialtyItem(String name, String imgUrl, Integer id, Integer sort) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.id = id;
        this.sort = sort;
    }

    protected LawSpecialtyItem(Parcel in) {
        name = in.readString();
        imgUrl = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            sort = null;
        } else {
            sort = in.readInt();
        }
    }

    public static final Creator<LawSpecialtyItem> CREATOR = new Creator<LawSpecialtyItem>() {
        @Override
        public LawSpecialtyItem createFromParcel(Parcel in) {
            return new LawSpecialtyItem(in);
        }

        @Override
        public LawSpecialtyItem[] newArray(int size) {
            return new LawSpecialtyItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return EndUrlUtil.BaseUrl+imgUrl;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSort() {
        return sort;
    }

    @NonNull
    @Override
    public String toString() {
        return "LawSpecialtyItem{" +
                "name='" + name + '\'' +
                ", imgUrl='" + getImgUrl() + '\'' +
                ", id=" + id +
                ", sort=" + sort +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(imgUrl);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (sort == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(sort);
        }
    }
}
