package com.fatapp.oxygentoolbox.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fatapp.oxygentoolbox.R;

import java.util.List;

public class FoldLayout extends LinearLayout implements View.OnClickListener {

    private boolean init;
    private final int layoutId;
    private boolean isShow;
    private LinearLayout content;
    private ValueAnimator showAnimator;
    private ValueAnimator hideAnimator;
    private View defaultView;

    private OnItemClickListenerInterface mOnItemClickListener;

    public FoldLayout(Context context) {
        this(context, null);
    }

    public FoldLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("Recycle") TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FoldLayout, defStyleAttr, 0);
        layoutId = ta.getResourceId(R.styleable.FoldLayout_layoutId, -1);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        addDefaultLayout(context);
    }

    /**
     * Init
     */
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

        int contentHeight = content.getMeasuredHeight();
        if (!init) {
            showAnimator = ValueAnimator.ofInt(0, contentHeight);
            showAnimator.addUpdateListener(animation -> {
                LayoutParams layoutParams = (LayoutParams) content.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                content.setLayoutParams(layoutParams);
            });
            showAnimator.addListener(new AnimatorListenerAdapter() {
                @SuppressLint("UseCompatLoadingForDrawables")
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    LinearLayout linearLayout = defaultView.findViewById(R.id.fold_layout_linear_layout);
                    linearLayout.setBackground(getContext().getDrawable(R.drawable.top_radius_background));
                }
            });

            hideAnimator = ValueAnimator.ofInt(contentHeight, 0);
            hideAnimator.addUpdateListener(animation -> {
                LayoutParams layoutParams = (LayoutParams) content.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                content.setLayoutParams(layoutParams);
            });
            hideAnimator.addListener(new AnimatorListenerAdapter() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!isShow) {
                        LinearLayout linearLayout = defaultView.findViewById(R.id.fold_layout_linear_layout);
                        linearLayout.setBackground(getContext().getDrawable(R.drawable.top_bottom_radius_background));
                    }
                }
            });
            init = true;

            showItem();
        }
    }

    /**
     * Add Item
     */
    public void addItemView(List<View> views) {
        for (int i = 0; i < views.size(); i++) {
            final int position = i;
            content.addView(views.get(i));
            views.get(i).setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void showItem() {
        isShow = true;
        showAnimator.start();
        ImageView imageView = defaultView.findViewById(R.id.arrow_icon);
        imageView.setImageDrawable(getContext().getDrawable(R.drawable.right_to_down_arrow));
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageView.getDrawable();
        animatedVectorDrawable.start();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void hideItem() {
        isShow = false;
        hideAnimator.start();
        ImageView imageView = defaultView.findViewById(R.id.arrow_icon);
        imageView.setImageDrawable(getContext().getDrawable(R.drawable.down_to_right_arrow));
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) imageView.getDrawable();
        animatedVectorDrawable.start();
    }

    public void setOnItemClickListener(OnItemClickListenerInterface onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
