package cc.colorcat.newmvp.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cxx on 16-3-25.
 * xx.ch@outlook.com
 */
final class RVHolder extends RecyclerView.ViewHolder {
    private final VHolder mHolder;

    RVHolder(@NonNull View v, @Nullable RVAdapter.OnItemLongClickListener listener) {
        super(v);
        mHolder = VHolder.from(v);
        if (listener != null) {
            v.setOnLongClickListener(new RVOnLongClickListener(listener));
        }
    }

    RVHolder(@NonNull View v, int viewType, @Nullable RVAdapter.OnItemLongClickListener listener) {
        super(v);
        mHolder = VHolder.from(v);
        mHolder.setType(viewType);
        if (listener != null) {
            v.setOnLongClickListener(new RVOnLongClickListener(listener));
        }
    }

    VHolder getHolder() {
        return mHolder;
    }

    private class RVOnLongClickListener implements View.OnLongClickListener {
        private RVAdapter.OnItemLongClickListener mListener;

        RVOnLongClickListener(RVAdapter.OnItemLongClickListener listener) {
            mListener = listener;
        }

        @Override
        public boolean onLongClick(View v) {
            mListener.onItemLongClick(v, getLayoutPosition());
            return false;
        }
    }
}
