package com.ehighsun.contactasnycread;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zlp on 2017/6/6.
 */

public class Contacts implements Parcelable {

    private String phoneNum;
    private String phontName;
    private String sortKey;


    public Contacts(Parcel in) {
        phoneNum = in.readString();
        phontName = in.readString();
        sortKey = in.readString();
    }

    public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    public Contacts() {

    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhontName() {
        return phontName;
    }

    public void setPhontName(String phontName) {
        this.phontName = phontName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phoneNum);
        dest.writeString(phontName);
        dest.writeString(sortKey);
    }
}
