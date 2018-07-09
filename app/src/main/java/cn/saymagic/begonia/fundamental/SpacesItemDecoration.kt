package cn.saymagic.begonia.fundamental

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacesItemDecoration : RecyclerView.ItemDecoration {

    private val space: Int

    constructor(space: Int) {
        this.space = space
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }
    }
}