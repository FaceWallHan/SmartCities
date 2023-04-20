package com.our.smart.bean;

import java.util.List;

public class ImageCarouselResponse extends BaseBean{
    private List<ImageCarouselItem> data;

    public List<ImageCarouselItem> getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ImageCarouselResponse{");
        if (data!=null){
            for (int i = 0; i < data.size(); i++) {
                builder.append("ImageCarouselItem :").append(data.get(i).toString()).append("\n");
            }
        }else {
            builder.append("data=null}");
        }
        return builder.toString();
    }
}
