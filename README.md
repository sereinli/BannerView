# BannerView
`无限循环自动轮播焦点位`AutoCircleViewPager实现相关代码。通过`抽象父类`的方式，可以轻松的复用在不同的UI需求上，只需要新增少量代码即可。

先上效果，gif压缩比较厉害，有点花，凑合看看吧
![](https://github.com/sereinli/BannerView/blob/master/demo.gif)


现在焦点轮播是每款App必备的功能，各种效果的都有，鉴于ViewPager不能循环，也不支持自动轮播。而其他人开源的代码也存在不同的问题，比如只有1个焦点时基本没有处理、显示样式不同代码改动比较大等等一系列的问题。因此，结合我之前工作中的一些经验，写了这个小demo，可以让初学者很快的实现自己需要的循环轮播。

通过`泛型`、`抽象`等方式，如果新增一种轮播显示样式，只需要简单的实现2个View即可。
```
public class BannerView<T, V> extends FrameLayout {
}

public abstract class BaseBannerView<T, V> extends RelativeLayout {
}

public class HomeBannerView extends BaseBannerView<HomeBanner, HomeBannerItemView> {
}
```
BannerView.java           -- ViewPager及当前焦点显示
BaseBannerView.java       -- 抽象封装的父类

BannerIdentifyBar         -- 焦点位置显示

显示Demo（如果显示效果不同，只需自定义实现下面2个类即可）：
HomeBannerView.java       -- 主界面的广告轮播
HomeBannerItemView.java   -- 主界面的广告Item View

#Step 1

参照HomeBannerView，自定义一个XXXBannerView，需要继承BaseBannerView，同时传入该Banner对应的数据model和显示item view，实现2个虚函数及一个点击响应即可
```
public XXXBannerView extends BaseBannerView <XXXBanner, XXXBannerItemView> {
    @Override
    XXXBannerItemView getBannerView(XXXBanner data) {
    }

    @Override
    void updateCycleViewPager() {
    }

    private BannerView.OnBannerClickListener mOnClickListener = new BannerView.OnBannerClickListener() {
        @Override
        public void onClick(int position) {
            
        }
    };
}
```
#Step 2
参照
<me.rain.android.bannerview.view.HomeBannerView
            android:id="@+id/home_banner_view"
            android:layout_width="match_parent"
            android:layout_height="200dp"/>
            
在需要显示banner的layout文件中加入刚才自定义的XXXBannerView:
<me.rain.android.bannerview.view.XXXXBannerView
            android:id="@+id/xxx_banner_view"
            android:layout_width="match_parent"
            android:layout_height="200dp"/>
            
#Step 3
在界面业务逻辑代码获取到数据后，调用XXXXBannerView.updateViews(XXXBanner datas)方法

#Lincense
Copyright [2016] [Rain of copyright owner]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
