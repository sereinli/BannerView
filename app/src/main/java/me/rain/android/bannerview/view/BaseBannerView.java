package me.rain.android.bannerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.rain.android.bannerview.R;

/**
 * Created by sereinli on 2016/8/24.
 */
public abstract class BaseBannerView<T, V> extends RelativeLayout {
    protected BannerView<T, V> mCyclerViewPager;
    protected List<T> mBanner;
    protected List<V> mViews = new ArrayList<V>();

    public BaseBannerView(Context context) {
        super(context);
        initViews();
    }

    public BaseBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BaseBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    protected void initViews() {
        inflate(getContext(), R.layout.view_base_banner_view, this);
        mCyclerViewPager = (BannerView)findViewById(R.id.base_banner_view);
        mCyclerViewPager.setWheel(false);

    }

    public void updateViews(List<T> banner) {
        if(banner == null || mCyclerViewPager == null) {
            return;
        }

        mBanner = banner;
        setupCycleViewPager();
    }

    private void setupCycleViewPager() {
        mCyclerViewPager.setWheel(false);
        mViews.clear();

        if(mBanner.size() == 0) {
            return;
        }

        if(mBanner.size() > 1) {
            // 将最后一个ImageView添加进来
            mViews.add(getBannerView(mBanner.get(mBanner.size() - 1)));
            for (int i = 0; i < mBanner.size(); i++) {
                mViews.add(getBannerView(mBanner.get(i)));
            }
            // 将第一个ImageView添加进来
            mViews.add(getBannerView(mBanner.get(0)));
        }else if(mBanner.size() == 1) {
            for (int i = 0; i < mBanner.size(); i++) {
                mViews.add(getBannerView(mBanner.get(i)));
            }
        }

        mCyclerViewPager.refreshData();

        updateCycleViewPager();
    }

    abstract V getBannerView(T data);
    abstract void updateCycleViewPager();
}
