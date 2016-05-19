package com.bhz.android.caiyoubang.info;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/25.
 */
public class MenuInfo implements Parcelable{
    private String menuName;
    private String menuImageUrl;
    private int length;

    public MenuInfo(){}

    public MenuInfo(Parcel in){

    }

    public MenuInfo(String menuName, String menuImageUrl) {
        this.menuName = menuName;
        this.menuImageUrl = menuImageUrl;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuImageUrl() {
        return menuImageUrl;
    }

    public void setMenuImageUrl(String menuImageUrl) {
        this.menuImageUrl = menuImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menuName);
        dest.writeString(menuImageUrl);
    }
    private void readFromParcel(Parcel in){
        menuName = in.readString();
        menuImageUrl = in.readString();
    }
    public static final Creator CREATOR =
            new Creator() {
                public MenuInfo createFromParcel(Parcel in) {
                    return new MenuInfo(in);
                }

                public MenuInfo[] newArray(int size) {
                    return new MenuInfo[size];
                }
            };
}
