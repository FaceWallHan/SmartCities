package com.our.smart.bean;

import com.our.smart.net.EndUrlUtil;

public class Lawyer {

    private Integer id;

    private String name;

    private Integer legalExpertiseId;

    private String avatarUrl;

    private String baseInfo;

    private String eduInfo;

    private String licenseNo;

    private String certificateImgUrl;

    private String workStartAt;

    private Integer serviceTimes;

    private Integer favorableRate;

    private String legalExpertiseName;

    private Integer favorableCount;

    private String sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLegalExpertiseId() {
        return legalExpertiseId;
    }

    public void setLegalExpertiseId(Integer legalExpertiseId) {
        this.legalExpertiseId = legalExpertiseId;
    }

    public String getAvatarUrl() {
        return EndUrlUtil.BaseUrl + avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public String getEduInfo() {
        return eduInfo;
    }

    public void setEduInfo(String eduInfo) {
        this.eduInfo = eduInfo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getCertificateImgUrl() {
        return certificateImgUrl;
    }

    public void setCertificateImgUrl(String certificateImgUrl) {
        this.certificateImgUrl = certificateImgUrl;
    }

    public String getWorkStartAt() {
        return workStartAt;
    }

    public void setWorkStartAt(String workStartAt) {
        this.workStartAt = workStartAt;
    }

    public Integer getServiceTimes() {
        return serviceTimes;
    }

    public void setServiceTimes(Integer serviceTimes) {
        this.serviceTimes = serviceTimes;
    }

    public Integer getFavorableRate() {
        return favorableRate;
    }

    public void setFavorableRate(Integer favorableRate) {
        this.favorableRate = favorableRate;
    }

    public String getLegalExpertiseName() {
        return legalExpertiseName;
    }

    public void setLegalExpertiseName(String legalExpertiseName) {
        this.legalExpertiseName = legalExpertiseName;
    }

    public Integer getFavorableCount() {
        return favorableCount;
    }

    public void setFavorableCount(Integer favorableCount) {
        this.favorableCount = favorableCount;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
