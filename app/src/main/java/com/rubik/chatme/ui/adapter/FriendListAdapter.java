package com.rubik.chatme.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubik.chatme.R;
import com.rubik.chatme.helper.CircleTransform;
import com.rubik.chatme.helper.ImageLoader;
import com.rubik.chatme.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by kiennguyen on 1/8/17.
 */

public class FriendListAdapter extends
        RecyclerView.Adapter<FriendListAdapter.RecyclerViewHolder> {

    private List<User> userList;
    private ReplaySubject<User> notifier = ReplaySubject.create();

    public FriendListAdapter(List<User> userList) {
        this.userList = userList;
    }

    public void add(User user) {
        userList.add(user);
        notifyItemInserted(userList.size());
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_friend_list, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendListAdapter.RecyclerViewHolder holder, int position) {
        holder.setPos(position);
        holder.tvName.setText(userList.get(position).getName());
        holder.tvGender.setText(userList.get(position).getGender());
        ImageLoader.loadImageWithTransform(userList.get(position).getAvatar(), holder.ivAvatar, new CircleTransform());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.adapter_friend_list_avatar)
        ImageView ivAvatar;

        @BindView(R.id.adapter_friend_list_name)
        TextView tvName;

        @BindView(R.id.adapter_friend_list_gender)
        TextView tvGender;

        private int pos;

        public void setPos(int pos) {
            this.pos = pos;
        }

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            notifier.onNext(userList.get(pos));
        }
    }

    public Observable<User> asObservable() {
        return notifier;
    }
}