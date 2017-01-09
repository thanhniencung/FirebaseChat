package com.rubik.chatme.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rubik.chatme.R;
import com.rubik.chatme.helper.CircleTransform;
import com.rubik.chatme.helper.ImageLoader;
import com.rubik.chatme.model.Message;
import com.rubik.chatme.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kiennguyen on 1/7/17.
 */

public class MessageAdapter extends
        RecyclerView.Adapter<MessageAdapter.RecyclerViewHolder> {
    public static final int MSG_ME = 0;
    public static final int MSG_FRIEND = 1;

    private List<Message> messageList;
    private User me;
    private User friend;

    public MessageAdapter(List<Message> messageList, User me, User friend) {
        this.messageList = messageList;
        this.me = me;
        this.friend = friend;
    }

    public void add(Message message) {
        messageList.add(message);
        notifyItemInserted(messageList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getType();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = null;
        if (viewType == MSG_ME) {
            itemView = inflater.inflate(R.layout.adapter_message_me, parent, false);
            itemView.setBackgroundColor(0xff00ff);
        } else if (viewType == MSG_FRIEND) {
            itemView = inflater.inflate(R.layout.adapter_message_friend, parent, false);
            itemView.setBackgroundColor(0xff0000);
        }
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (messageList.get(position).getType() == MSG_ME) {
            holder.rlBubble.setBackgroundResource(R.drawable.balloon_incoming_normal);
            ImageLoader.loadImageWithTransform(me.getAvatar(),
                    holder.ivAvatar, new CircleTransform());
        } else {
            holder.rlBubble.setBackgroundResource(R.drawable.balloon_outgoing_normal);
            ImageLoader.loadImageWithTransform(friend.getAvatar(),
                    holder.ivAvatar, new CircleTransform());
        }
        holder.tvMessage.setText(messageList.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_message_avatar)
        ImageView ivAvatar;

        @BindView(R.id.adapter_message_text)
        TextView tvMessage;

        @BindView(R.id.adapter_message_bubble)
        RelativeLayout rlBubble;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
