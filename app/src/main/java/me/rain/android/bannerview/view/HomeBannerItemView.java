package me.rain.android.bannerview.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;

import me.rain.android.bannerview.R;
import me.rain.android.bannerview.databinding.ViewHomeBannerItemViewBinding;
import me.rain.android.bannerview.models.HomeBanner;

/**
 * Created by sereinli on 2016/8/24.
 */
public class HomeBannerItemView extends FrameLayout {

    private ViewHomeBannerItemViewBinding mBinding;

    public HomeBannerItemView(Context context) {
        this(context, null);
    }

    public HomeBannerItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBannerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_home_banner_item_view, this, true);
    }

    public void updateViews(HomeBanner entity) {
        Glide.with(getContext()).load(entity.getImageUrl()).into(mBinding.imageView);
        mBinding.tvTitle.setText(entity.getTitle());
    }
}
