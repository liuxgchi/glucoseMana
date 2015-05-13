package com.common.library.bean.local;

/**
 * 当前位置
 * Created by xing on 2015/4/19.
 */
public class CurrentLocation {

    /**
     * 纬度
     */
   public double latitude;
    /**
     * 经度
     */
   public double longtitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "CurrentLocation{" +
                "latitude=" + latitude +
                ", longtitude=" + longtitude +
                '}';
    }
}
