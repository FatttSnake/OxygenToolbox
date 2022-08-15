package com.fatapp.oxygentoolbox.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.fatapp.oxygentoolbox.R;

import java.util.List;

public class FoldLayout extends LinearLayout implements View.OnClickListener {

    private boolean init;
    private final int layoutId;
    private final long duration;
    private boolean isShow = true;
    private LinearLayout content;
    private ValueAnimator showAnimator;
    private ValueAnimator hideAnimator;
    private View defaultView;

    private OnItemClickListenerInterface mOnItemClickListener;

    public FoldLayout(Context context) {
        this(context, null);
    }

    public FoldLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FoldLayout, defStyleAttr, 0);
        try {
            layoutId = typedArray.getResourceId(R.styleable.FoldLayout_layoutId, -1);
            duration = typedArray.getInt(R.styleable.FoldLayout_transitionTime, 500);
        } finally {
            typedArray.recycle();
        }
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        addDefaultLayout(context);
    }

    public View getDefaultView() {
        return defaultView;
    }

    private void addDefaultLayout(Context context) {
        defaultView = LayoutInflater.from(context).inflate(layoutId, this, true);
        defaultView.setOnClickListener(this);
        content = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        content.setOrientation(VERTICAL);
        addView(content, layoutParams);
    }

    @Override
    public void onClick(View v) {
        if (isShow) {
            hideItem();
        } else {
            showItem();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initAnimation();
    }

    /**
     * Animation
     */
    private void initAnimation() {
        if (!init) {
            int contentHeight = content.getMeasuredHeight();
            LayoutParams layoutParams = (LayoutParams) content.getLayoutParams();
            layoutParams.height = contentHeight;
            LinearLayout linearLayout = defaultView.findViewById(R.id.linear_layout_head);
            linearLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.background_top_radius));
            ImageView imageView = defaultView.findViewById(R.id.image_view_arrow);
            imageView.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.animation_down_to_right_arrow));
            content.setLayoutParams(layoutParams);

            showAnimator = ValueAnimator.ofInt(0, contentHeight);
            showAnimator.setDuration(duration);
            showAnimator.addUpdateListener(animation -> {
                layoutParams.height = (int) animation.getAnimatedValue();
                content.setLayoutParams(layoutParams);
            });
            showAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (isShow) {
                        LinearLayout linearLayout = defaultView.findViewById(R.id.linear_layout_head);
                        linearLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.background_top_radius));
                    }
                }
            });

            hideAnimator = ValueAnimator.ofInt(contentHeight, 0);
            hideAnimator.setDuration(duration);
            hideAnimator.addUpdateListener(animation -> {
                layoutParams.height = (int) animation.getAnimatedValue();
                content.setLayoutParams(layoutParams);
            });
            hideAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!isShow) {
                        LinearLayout linearLayout = defaultView.findViewById(R.id.linear_layout_head);
                        linearLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.background_top_bottom_radius));
                    }
                }
            });
            init = true;
        }
    }

    /**
     * Add Item
     */
    public void addItemView(List<View> views) {
        for (int i = 0; i < views.size(); i++) {
            final int position = i;
            content.addView(views.get(i));
            views.get(i).setOnClickListener(view -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    public void showItem() {
        isShow = true;
        showAnimator.start();
        ImageView imageView = defaultView.findViewById(R.id.image_view_arrow);
        imageView.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.animation_right_to_down_arrow));
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageView.getDrawable();
        animatedVectorDrawable.start();
    }

    public void hideItem() {
        isShow = false;
        hideAnimator.start();
        ImageView imageView = defaultView.findViewById(R.id.image_view_arrow);
        imageView.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.animation_down_to_right_arrow));
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageView.getDrawable();
        animatedVectorDrawable.start();
    }

    public void setOnItemClickListener(OnItemClickListenerInterface onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
