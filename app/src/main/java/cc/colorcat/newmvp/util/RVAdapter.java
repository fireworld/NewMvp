package cc.colorcat.newmvp.util;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by cxx on 16-3-25.
 * xx.ch@outlook.com
 *
 * @see RecyclerView.Adapter
 */
public abstract class RVAdapter<T> extends RecyclerView.Adapter<RVHolder> {
    private final int mLayoutResId;
    private final List<T> mData;
    private OnItemLongClickListener mListener;

    public RVAdapter(@NonNull List<T> data, @LayoutRes int layoutResId) {
        mData = data;
        mLayoutResId = layoutResId;
    }

    @Override
    public final RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false);
        return new RVHolder(rootView, viewType, mListener);
    }

    @Override
    public final void onBindViewHolder(RVHolder holder, int position) {
        bindView(holder.getHolder(), mData.get(position));
    }

    @Override
    public final int getItemCount() {
        return mData.size();
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mListener = listener;
    }

    public abstract void bindView(VHolder holder, T t);

    public interface OnItemLongClickListener {

        void onItemLongClick(@NonNull View v, int position);
    }
}
