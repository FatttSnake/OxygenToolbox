package com.fatapp.oxygentoolbox.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class AutoLinefeedLayout extends ViewGroup {

    public AutoLinefeedLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoLinefeedLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLinefeedLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutHorizontal();
    }

    private void layoutHorizontal() {
        final int count = getChildCount();
        final int lineWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int childTop;
        int childLeft = getPaddingLeft();

        int availableLineWidth = lineWidth;
        int maxLineHeight = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();

                if (availableLineWidth < childWidth) {
                    availableLineWidth = lineWidth;
                    paddingTop = paddingTop + maxLineHeight;
                    childLeft = getPaddingLeft();
                    maxLineHeight = 0;
                }
                childTop = paddingTop;
                setChildFrame(child, childLeft, childTop, childWidth, childHeight);
                childLeft += childWidth;
                availableLineWidth = availableLineWidth - childWidth;
                maxLineHeight = Math.max(maxLineHeight, childHeight);
            }
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            final int width = MeasureSpec.getSize(widthMeasureSpec);
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(getDesiredHeight(width), MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private int getDesiredHeight(int width) {
        final int lineWidth = width - getPaddingLeft() - getPaddingRight();
        int availableLineWidth = lineWidth;
        int totalHeight = getPaddingTop() + getPaddingBottom();
        int lineHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            if (availableLineWidth < childWidth) {
                availableLineWidth = lineWidth;
                totalHeight = totalHeight + lineHeight;
                lineHeight = 0;
            }
            availableLineWidth = availableLineWidth - childWidth;
            lineHeight = Math.max(childHeight, lineHeight);
        }
        totalHeight = totalHeight + lineHeight;
        return totalHeight;
    }

}