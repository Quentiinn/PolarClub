package com.example.quentincourvoisier.polarclub.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.model.Participant;
import com.example.quentincourvoisier.polarclub.R;

import java.util.List;

/**
 * Created by antho on 07/02/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Participant> participants;

    public UsersAdapter(List<Participant> participants) {
        this.participants = participants;
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
        Participant participant = participants.get(position);

        ((UserViewHolder)holder).bind(participant);
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView itemUserName;
        private TextView itemUserHeart;

        UserViewHolder(View itemView) {
            super(itemView);
            itemUserName = itemView.findViewById(R.id.itemUser_name);
            itemUserHeart = itemView.findViewById(R.id.itemUser_heart);
        }

        void bind(Participant participant) {
            itemUserName.setText(Html.fromHtml("<b>" + participant.getName() + "</b>"));
            itemUserHeart.setText(participant.getBattements());
        }
    }
}
