package me.rain.android.bannerview.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by sereinli on 2016/8/24.
 */
public class HomeBanner extends BaseObservable{

    private String imageUrl;
    private String title;

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
