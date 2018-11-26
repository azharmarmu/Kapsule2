package com.meds.kapsule.carousel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.meds.kapsule.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewPagerCarouselFragment extends Fragment {
    static final String IMAGE_RESOURCE_ID = "image_resource_id";

    private int imageResourceId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_carousel_fragment, container, false);
        ImageView ivCarouselImage = view.findViewById(R.id.iv_carousel_image);
        imageResourceId = getArguments() != null ? getArguments().getInt(IMAGE_RESOURCE_ID, R.drawable.car1) : 0; // default to car1 image resource
        ivCarouselImage.setImageResource(imageResourceId);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "image: " + imageResourceId, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
