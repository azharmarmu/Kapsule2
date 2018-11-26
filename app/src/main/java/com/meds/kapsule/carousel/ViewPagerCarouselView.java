package com.meds.kapsule.carousel;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.meds.kapsule.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerCarouselView extends RelativeLayout {
    private FragmentManager fragmentManager;                // FragmentManager for managing the fragments withing the ViewPager
    private ViewPager vpCarousel;                           // ViewPager for the Carousel view
    private LinearLayout llPageIndicatorContainer;          // Carousel view item indicator, the little bullets at the bottom of the carousel
    private ArrayList<ImageView> carouselPageIndicators;    // Carousel view item, the little bullet at the bottom of the carousel
    private int[] imageResourceIds;                        // Carousel view background image
    private long carouselSlideInterval;                     // Carousel view item sliding interval
    private Handler carouselHandler;                        // Carousel view item sliding interval automation handler

    public ViewPagerCarouselView(Context context) {
        super(context);
        initView(context);
    }

    public ViewPagerCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewPagerCarouselView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Objects.requireNonNull(inflater).inflate(R.layout.view_pager_carousel_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        vpCarousel = this.findViewById(R.id.vp_carousel);
        llPageIndicatorContainer = this.findViewById(R.id.ll_page_indicator_container);
    }

    public void setData(FragmentManager fragmentManager, int[] imageResourceIds, long carouselSlideInterval) {
        this.fragmentManager = fragmentManager;
        this.imageResourceIds = imageResourceIds;
        this.carouselSlideInterval = carouselSlideInterval;
        initData();
        initCarousel();
        initCarouselSlide();
    }

    /**
     * Initialize the data for the carousel
     */
    private void initData() {
        carouselPageIndicators = new ArrayList<>();
        for (int ignored : imageResourceIds) {
            ImageView obj = new ImageView(getContext());
            obj.setImageResource(R.drawable.selector_carousel_page_indicator);
            obj.setPadding(4, 0, 4, 0); // left, top, right, bottom
            llPageIndicatorContainer.addView(obj);
            carouselPageIndicators.add(obj);
        }
    }

    /**
     * Initialize carousel views, each item in the carousel view is a fragment
     */
    private void initCarousel() {
        carouselPageIndicators.get(0).setSelected(true);

        // Update the carousel page indicator on change
        vpCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < carouselPageIndicators.size(); i++) {
                    if (i == position)
                        carouselPageIndicators.get(position).setSelected(true);
                    else
                        carouselPageIndicators.get(i).setSelected(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ViewPagerCarouselAdapter viewPagerCarouselAdapter = new ViewPagerCarouselAdapter(fragmentManager, imageResourceIds);
        vpCarousel.setPageTransformer(
                false, new ViewPager.PageTransformer() {
                    private float MIN_SCALE = 0.85f;
                    private float MIN_ALPHA = 0.5f;

                    @Override
                    public void transformPage(@NonNull View view, float position) {
                        int pageWidth = view.getWidth();
                        int pageHeight = view.getHeight();

                        if (position < -1) { // [-Infinity,-1)
                            // This page is way off-screen to the left.
                            view.setAlpha(0);

                        } else if (position <= 1) { // [-1,1]
                            // Modify the default slide transition to shrink the page as well
                            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                            float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
                            float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;
                            if (position < 0) {
                                view.setTranslationX(horizontalMargin - verticalMargin / 2);
                            } else {
                                view.setTranslationX(-horizontalMargin + verticalMargin / 2);
                            }

                            // Scale the page down (between MIN_SCALE and 1)
                            view.setScaleX(scaleFactor);
                            view.setScaleY(scaleFactor);

                            // Fade the page relative to its size.
                            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                        } else { // (1,+Infinity]
                            // This page is way off-screen to the right.
                            view.setAlpha(0);
                        }
                    }
                }
        );
        vpCarousel.setAdapter(viewPagerCarouselAdapter);
    }

    /**
     * Handler to make the view pager to slide automatically
     */
    private void initCarouselSlide() {
        final int nCount = imageResourceIds.length;
        try {
            carouselHandler = new Handler();
            carouselHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int curPos = vpCarousel.getCurrentItem();
                    curPos++;
                    if (curPos == nCount) curPos = 0;
                    vpCarousel.setCurrentItem(curPos, true);
                    carouselHandler.postDelayed(this, carouselSlideInterval);
                }
            }, carouselSlideInterval);

        } catch (Exception e) {
            Log.d("MainActivity", e.getMessage());
        }
    }

    public void onDestroy() {
        if (carouselHandler != null)
            carouselHandler.removeCallbacksAndMessages(null); // remove call backs to prevent memory leaks
    }
}
