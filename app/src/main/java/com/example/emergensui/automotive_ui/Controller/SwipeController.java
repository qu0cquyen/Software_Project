package com.example.emergensui.automotive_ui.Controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.emergensui.automotive_ui.Adapter.DepartmentContactAdapter;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class SwipeController extends ItemTouchHelper.SimpleCallback {
    private DepartmentContactAdapter adapter;
    private Context mContext;
    private Drawable icon;
    private final ColorDrawable background;

    public SwipeController(Context context, DepartmentContactAdapter departAdapter)
    {
        super(0, ItemTouchHelper.LEFT);
        this.mContext = context;
        this.adapter = departAdapter;

        icon = ContextCompat.getDrawable(mContext, android.R.drawable.sym_call_outgoing);
        background = new ColorDrawable(Color.parseColor("#f09089"));
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder target)
    {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        int position = viewHolder.getAdapterPosition();
        //Doing call here
        adapter.callAction(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backGroundCornerOffset = 20;

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX < 0)
        {
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int)dX) - backGroundCornerOffset, itemView.getTop(),
                                itemView.getRight(), itemView.getBottom());
        }
        else
        {
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        icon.draw(c);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder)
    {
        //Swipe 80% of the horizontal bar to call
        return 0.8f;
    }


}
