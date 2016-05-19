package com.bhz.android.caiyoubang.data;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/7.
 */
public class EventSummary implements Parcelable {
    Integer resID;
    public EventSummary() {
    }
    public Integer getResID() {
        return resID;
    }
    public void setResID(Integer resID) {
        this.resID = resID;
    }

    public static final Creator<EventSummary> CREATOR = new Creator<EventSummary>() {
        @Override
        public EventSummary createFromParcel(Parcel in) {
            EventSummary es=new EventSummary();
            es.resID=in.readInt();
            return es;
        }
        @Override
        public EventSummary[] newArray(int size) {
            return new EventSummary[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resID);
    }
}
