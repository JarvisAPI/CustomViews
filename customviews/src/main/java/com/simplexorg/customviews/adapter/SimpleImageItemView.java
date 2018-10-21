package com.simplexorg.customviews.adapter;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.simplexorg.customviews.R;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ImageItemViewHolder;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ViewHolderToken;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ViewToken;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapterContract.Presenter;
import com.simplexorg.customviews.util.BitmapDecodeTask;
import com.simplexorg.customviews.util.VUtil;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class SimpleImageItemView implements SimpleImageItemAdapterContract.View {
    private Presenter mPresenter;
    private ImageView mMainImageIndicator;
    SimpleImageItemAdapter mAdapter;

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void notifyItemRemoved(int position) {
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemChanged(int position) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemInserted(int position) {
        mAdapter.notifyItemInserted(position);
    }

    @Override
    public void displayToast(String msg) {
        VUtil.getInstance().displayToast(mAdapter.getContext(), msg);
    }

    @Override
    public int getImagePosition(ViewHolderToken token) {
        ImageItemViewHolder vh = (ImageItemViewHolder) token;
        return (int) vh.mImageCardView.getTag(R.id.customviews_tag0);
    }

    @Override
    public void setMainImageIndicator(ViewHolderToken token) {
        ImageItemViewHolder vh = (ImageItemViewHolder) token;
        mMainImageIndicator = vh.mImageItemIndicator;
        mMainImageIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToolTip(ViewToken token0) {
        TooltipViewToken token = (TooltipViewToken) token0;
        new SimpleTooltip.Builder(mAdapter.getContext())
                .anchorView(token.mAnchor)
                .text(token.text)
                .textColor(token.textColor)
                .gravity(Gravity.TOP)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
    }

    @Override
    public void clearMainImageIndicator() {
        if (mMainImageIndicator != null) {
            mMainImageIndicator.setVisibility(View.GONE);
            mMainImageIndicator = null;
        }
    }

    void bind(ImageItemViewHolder holder, int position) {
        Log.d("View", "ImageItemViewHolder: " + holder);
        BitmapDecodeTask task = mPresenter.onImageViewReady(holder, position);
        task.setReceiver(holder.mProductImage);
        task.execute();

        holder.mImageCardView.setTag(R.id.customviews_tag0, position);
        holder.mImageCardView.setTag(R.id.customviews_tag1, holder);
        holder.mImageCardView.setOnLongClickListener((View view) ->
                mPresenter.onImageItemLongClick(holder, position));
        holder.mImageCardView.setOnClickListener((View view) ->
                mPresenter.onImageItemClick((int) view.getTag(R.id.customviews_tag0)));

        holder.mImageItemIndicator.setOnClickListener((View view) -> {
            TooltipViewToken token = new TooltipViewToken();
            token.mAnchor = view;
            mPresenter.onImageIndicatorClick(token);
        });
    }

    private class TooltipViewToken extends ViewToken {
        private View mAnchor;
    }
}
