package com.meds.kapsule.carousel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerCarouselAdapter extends FragmentStatePagerAdapter {
    private int[] imageResourceIds;

    ViewPagerCarouselAdapter(FragmentManager fm, int[] imageResourceIds) {
        super(fm);
        this.imageResourceIds = imageResourceIds;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerCarouselFragment.IMAGE_RESOURCE_ID, imageResourceIds[position]);
        ViewPagerCarouselFragment frag = new ViewPagerCarouselFragment();
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return (imageResourceIds == null) ? 0: imageResourceIds.length;
    }

}
