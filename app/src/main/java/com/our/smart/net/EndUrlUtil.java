package com.our.smart.net;

public class EndUrlUtil {
    private EndUrlUtil() {}

    public static final String BaseUrl = "http://124.93.196.45:10001";

    /**
     * 获取所有法律专长
     * */
    public static final String LegalExpertise="/prod-api/api/lawyer-consultation/legal-expertise/list";

    /**
     * 用户登录
     * */
    public static final String Login="/prod-api/api/login";

    /**
     * 获取律师列表
     * */
    public static final String Lawyer_List="/prod-api/api/lawyer-consultation/lawyer/list";

    /**
     * 获取所有广告轮播图
     * */
    public static final String LegalAllBanner="/prod-api/api/lawyer-consultation/ad-banner/list";
}
