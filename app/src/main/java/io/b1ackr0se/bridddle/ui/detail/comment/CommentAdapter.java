package io.b1ackr0se.bridddle.ui.detail.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.data.model.Comment;
import io.b1ackr0se.bridddle.ui.common.OnUserClickListener;
import io.b1ackr0se.bridddle.ui.common.ProgressViewHolder;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_PROGRESS = 0;
    public static final int TYPE_COMMENT = 1;

    private List<Comment> comments;
    private OnUserClickListener listener;

    public CommentAdapter(List<Comment> list) {
        this.comments = list;
    }

    public void setListener(OnUserClickListener onUserClickListener) {
        this.listener = onUserClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return comments.get(position) != null ? TYPE_COMMENT : TYPE_PROGRESS;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_COMMENT)
            return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
        return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentViewHolder) {
            Comment comment = comments.get(position);
            ((CommentViewHolder) holder).bind(comment);
            ((CommentViewHolder) holder).avatar.setOnClickListener(view -> {
                if (listener != null)
                    listener.onUserClick(comment.getUser());
            });
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
