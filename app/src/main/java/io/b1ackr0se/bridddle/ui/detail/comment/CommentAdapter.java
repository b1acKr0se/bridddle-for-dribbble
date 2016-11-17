package io.b1ackr0se.bridddle.ui.detail.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.data.model.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<Comment> comments;

    public CommentAdapter(List<Comment> list) {
        this.comments = list;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
