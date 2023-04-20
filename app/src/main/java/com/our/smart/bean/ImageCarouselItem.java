package com.our.smart.bean;

import com.our.smart.net.EndUrlUtil;

/**
 * {
 *             "searchValue": null,
 *             "createBy": null,
 *             "createTime": null,
 *             "updateBy": null,
 *             "updateTime": null,
 *             "remark": null,
 *             "params": {},
 *             "id": 6,
 *             "title": "涉嫌哄抬价格 两家线上蔬菜店被立案",
 *             "sort": 1,
 *             "imgUrl": "/prod-api/profile/upload/image/2022/03/12/3b076043-ec6a-455c-986d-0b39302e3e27.jpeg"
 *         }
 * */
public class ImageCarouselItem {
    private String title,imgUrl;

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return EndUrlUtil.BaseUrl+imgUrl;
    }

    @Override
    public String toString() {
        return "ImageCarouselItem{" +
                "title='" + title + '\'' +
                ", imgUrl='" + getImgUrl() + '\'' +
                '}';
    }
}
