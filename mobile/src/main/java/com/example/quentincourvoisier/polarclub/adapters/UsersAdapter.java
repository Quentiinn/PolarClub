package com.example.quentincourvoisier.polarclub.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.model.User;
import com.example.quentincourvoisier.polarclub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antho on 07/02/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> users;

    public UsersAdapter() {
        users = new ArrayList<>();
    }

    public void testListUser() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = users.get(position);
        ((UserViewHolder)holder).bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView itemUserName;

        UserViewHolder(View itemView) {
            super(itemView);
            itemUserName = itemView.findViewById(R.id.itemUser_name);
        }

        void bind(User user) {
            itemUserName.setText(Html.fromHtml("<b>" + user.getName() + "</b>"));
        }
    }
}
