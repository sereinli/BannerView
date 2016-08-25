# BannerView
可循环自动轮播焦点位实现相关代码。通过抽象父类的方式，可以轻松的复用在不同的UI需求上，只需要新增少量代码即可


现在焦点轮播是每款App必备的功能，各种效果的都有，鉴于ViewPager不能循环，也不支持自动轮播，因此，我在JeasonWong/QingtingBannerView 的基础上，结合我之前工作中的一些经验，写了这个小demo，可以让初学者很快的实现自己需要的循环轮播。

通过泛型、抽象等方式，如果新增一种轮播显示样式，只需要简单的实现2个View即可


BannerView.java           -- ViewPager及当前焦点显示
BaseBannerView.java       -- 抽象封装的父类

BannerIdentifyBar         -- 焦点位置显示

显示Demo（如果显示效果不同，只需自定义实现下面2个类即可）：
HomeBannerView.java       -- 主界面的广告轮播
HomeBannerItemView.java   -- 主界面的广告Item View
