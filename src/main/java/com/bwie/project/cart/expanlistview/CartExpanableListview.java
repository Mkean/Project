package com.bwie.project.cart.expanlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 作者：王庆
 * 时间：2017/12/16
 */

public class CartExpanableListview extends ExpandableListView {
    public CartExpanableListview(Context context) {
        super(context);
    }

    public CartExpanableListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CartExpanableListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, height);
    }
}
