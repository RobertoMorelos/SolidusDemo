package com.herokuapp.soliduxample.solidus.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Roberto Morelos on 3/6/17.
 * Helper class to customize the spaces in the grid layout.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        outRect.top = space;
        outRect.left = space;
        outRect.right = space;

       /*int pos = parent.getChildLayoutPosition(view);
       if ((pos % 2) == 0){
           outRect.right = space;
       }*/
    }
}
