package me.rain.android.bannerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toast;

import me.rain.android.bannerview.models.HomeBanner;

/**
 * Created by sereinli on 2016/8/24.
 */
public class HomeBannerView extends BaseBannerView<HomeBanner, HomeBannerItemView> {

    public HomeBannerView(Context context) {
        super(context);
    }

    public HomeBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    HomeBannerItemView getBannerView(HomeBanner data) {
        HomeBannerItemView homeBannerItemView = new HomeBannerItemView(getContext());
        homeBannerItemView.updateViews(data);
        return homeBannerItemView;
    }

    @Override
    void updateCycleViewPager() {
        mCyclerViewPager.setData(mViews, mBanner, mOnClickListener);
        mCyclerViewPager.setBannerPageListener(new BannerView.BannerPageListener<HomeBanner>() {
            @Override
            public void onPageSelected(HomeBanner data, int page) {
                if(!TextUtils.isEmpty(data.getImageUrl())) {
                    HomeBannerItemView view =  (HomeBannerItemView)mCyclerViewPager.getViewPager().findViewWithTag(page);
                    if(view != null) {
                        view.updateViews(data);
                    }
                }
            }
        });

        //开启自动轮播（需在setData之后调用）
        mCyclerViewPager.setWheel(true);
    }

    private BannerView.OnBannerClickListener mOnClickListener = new BannerView.OnBannerClickListener() {
        @Override
        public void onClick(int position) {
            Toast.makeText(getContext(), "Item " + position + "clicked! " + mBanner.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        }
    };
}
