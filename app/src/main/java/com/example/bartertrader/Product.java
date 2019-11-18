package com.example.bartertrader;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String prdName, prdDesc, category, url;

    public Product(String prdName, String prdDesc, String category, String url)
    {
        this.prdName = prdName;
        this.prdDesc = prdDesc;
        this.category = category;
        this.url = url;
    }



    protected Product(Parcel in) {
        prdName = in.readString();
        prdDesc = in.readString();
        category = in.readString();
        url = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getPrdDesc() {
        return prdDesc;
    }

    public void setPrdDesc(String prdDesc) {
        this.prdDesc = prdDesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prdName);
        dest.writeString(prdDesc);
        dest.writeString(category);
        dest.writeString(url);
    }
}
