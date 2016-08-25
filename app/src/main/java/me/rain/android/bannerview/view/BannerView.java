package me.rain.android.bannerview.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import me.rain.android.bannerview.R;
import me.rain.android.bannerview.databinding.ViewBannerViewBinding;

public class BannerView<T, V> extends FrameLayout {
    private ViewBannerViewBinding mBinding;

    private List<T> mData = new ArrayList<>();
    private BannerPagerAdapter<T, V> mAdapter;

    private int lineColor = Color.BLACK;

    private Handler mHandler;
    private boolean mIsWheel = false;
    private int TIME_DURATION = 4000;
    private long mReleasedTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private final int WHEEL = 0x100;            // 轮播
    private final  int WHEEL_WAIT = 0x101;      // 等待

    private int currentPosition = 0; // 轮播当前位置
    private boolean isScrolling = false; // 滚动框是否滚动着

    private BannerPageListener mBannerPageListener;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_banner_view, this, true);

        mBinding.identifyBar.setLineColor(lineColor);

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == WHEEL && mData.size() > 1) {
                    if (!isScrolling) {
                        int max = mData.size() + 1;
                        int position = (currentPosition + 1) % mData.size();
                        mBinding.viewPager.setCurrentItem(position, true);
                        if (position == max) { // 最后一页时回到第一页
                            mBinding.viewPager.setCurrentItem(1, false);
                        }
                    }

                    mReleasedTime = System.currentTimeMillis();
                    mHandler.removeCallbacks(mRunnable);
                    mHandler.postDelayed(mRunnable, TIME_DURATION);
                    return;
                }
                if (msg.what == WHEEL_WAIT && mData.size() > 1) {
                    mHandler.removeCallbacks(mRunnable);
                    mHandler.postDelayed(mRunnable, TIME_DURATION);
                }
            }
        };

        mAdapter = new BannerPagerAdapter();
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(mData.size() <= 1) {
                    return;
                }

                mBinding.identifyBar.setPageScrolled(position, positionOffset);
                if (positionOffsetPixels == 0.0) {
                    if (position == mData.size() - 1) {
                        mBinding.viewPager.setCurrentItem(1, false);
                        currentPosition = 1;
                    } else if (position == 0) {
                        mBinding.viewPager.setCurrentItem(mData.size() - 2, false);
                        currentPosition = mData.size() - 2;
                    } else {
                        mBinding.viewPager.setCurrentItem(position);
                        currentPosition = position;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(mBannerPageListener != null) {
                    mBannerPageListener.onPageSelected(mData.get(position), position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1) { // viewPager在滚动
                    isScrolling = true;
                    return;
                } else if (state == 0) { // viewPager滚动结束

                    mReleasedTime = System.currentTimeMillis();

                }
                isScrolling = false;
            }
        });
    }

    public void setWheel(boolean isWheel) {
        if(mData != null && mData.size() <= 1) {
            return;
        }

        mIsWheel = isWheel;
        if(isWheel) {
            if(mHandler != null) {
                mHandler.postDelayed(mRunnable, TIME_DURATION);
            }
        }
    }

    final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mIsWheel) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - mReleasedTime > TIME_DURATION - 500) {
                    mHandler.sendEmptyMessage(WHEEL);
                } else {
                    mHandler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    public ViewPager getViewPager() {
        return mBinding.viewPager;
    }

    //当前view显示时调用（可用于广告回调等）
    public interface BannerPageListener<T> {
        //View Pager 切换
        void onPageSelected(T data, int page);
    }

    public void setBannerPageListener(BannerPageListener listener) {
        mBannerPageListener = listener;
    }

    public interface OnBannerClickListener {
        void onClick(int position);
    }

    public void setData(List<V> layouts, List<T> entities, OnBannerClickListener listener) {
        if(entities == null || entities.size() == 0) {
            return;
        }

        if(entities.size() == 1) {
            mData.addAll(entities);
        }else {
            mData.add(entities.get(entities.size() - 1));
            mData.addAll(entities);
            mData.add(entities.get(0));
        }

        if(mAdapter != null) {
            mAdapter.setData(entities, layouts);
            mBinding.identifyBar.setPageWidth(mData.size());
            mBinding.viewPager.setCurrentItem(mAdapter.getCount() > 1 ? 1 : 0, false);
        }

        if(listener != null) {
            mAdapter.setOnBannerClickListener(listener);
        }
    }

    public class BannerPagerAdapter<D, W> extends PagerAdapter {

        private Context mContext;
        private List<D> mData = new ArrayList<>();
        private List<W> mLayouts = new ArrayList<>();
        private OnBannerClickListener mClickListener;

        private void setLayouts(List<W> layouts) {
            for (W layout : layouts) {
                mLayouts.add(layout);
            }
        }

        private void setData(List<D> entities, List<W> layouts) {
            this.mData = entities;
            setLayouts(layouts);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mLayouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = (View)mLayouts.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onClick(getCount() > 1 ? position - 1 : 0);
                    }
                }
            });
            view.setTag(position);
            container.addView(view, 0);
            return mLayouts.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)mLayouts.get(position));
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        public void setOnBannerClickListener(OnBannerClickListener clickListener) {
            this.mClickListener = clickListener;
        }
    }
}