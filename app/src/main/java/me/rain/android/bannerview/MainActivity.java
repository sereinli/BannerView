package me.rain.android.bannerview;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import me.rain.android.bannerview.databinding.ActivityMainBinding;
import me.rain.android.bannerview.models.HomeBanner;

public class MainActivity extends Activity {
    private ActivityMainBinding mBinding;

    private String[] mImageUrls = {
                                    "http://c1.haibao.cn/img/750_480_100_0/1472093582.0151/e0b06aef87fb80358573a54d5d34c1d4.jpg",
                                    "http://c1.haibao.cn/img/750_480_100_0/1472097945.2876/a1801ab517e4686956f04defadf4dee6.jpg",
                                    "http://c1.haibao.cn/img/750_480_100_0/1472095618.9798/99fc59ca33866fe48c78da5b7f7b1543.jpg",
                                    "http://c1.haibao.cn/img/750_480_100_0/1472092373.305/19bf948067ab20f922e82cda4706a4a7.jpg",
                                    "http://c1.haibao.cn/img/750_480_100_0/1472095784.9008/1e2843e672f39b1f18f338576e28811e.jpg"
                                    };
    private String[] mTitles = {
                                "这些A咖歌手不好好搞音乐",
                                "茜妞一顶帽能换秀英全身“装备”",
                                "有个“网红闺蜜”什么感受？",
                                "把2016早秋流行色穿在身上是怎样的体验？",
                                "帅欧巴专走轻松运动风，型格潮叔更有腔有调"
                                };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final List<HomeBanner> banners = new ArrayList<>();
        for (int i = 0; i < mImageUrls.length; i++) {
            HomeBanner homeBanner = new HomeBanner();
            homeBanner.setImageUrl(mImageUrls[i]);
            homeBanner.setTitle(mTitles[i]);
            banners.add(homeBanner);
        }

        mBinding.homeBannerView.updateViews(banners);
    }
}
