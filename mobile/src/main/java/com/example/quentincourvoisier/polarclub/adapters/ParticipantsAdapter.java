package com.example.quentincourvoisier.polarclub.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.model.Participant;
import com.example.quentincourvoisier.polarclub.R;
import com.example.quentincourvoisier.polarclub.activities.MainActivity;

import java.util.List;

/**
 * Created by antho on 07/02/2018.
 */

public class ParticipantsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainActivity context;
    private List<Participant> participants;
    private String uidSession;

    public ParticipantsAdapter(MainActivity context, List<Participant> participants, final String uidSession) {
        this.context = context;
        this.participants = participants;
        this.uidSession = uidSession;
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

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void changeHeart(Participant participant) {
       if (uidSession.equals(participant.getUidSession())) {
           for (Participant p: participants) {
               if (participant.getUid().equals(p.getUid())) {
                   participants.set(participants.indexOf(p), participant);
                   break;
               }
           }

           notifyDataSetChanged();
       }
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
            itemUserHeart.setText(String.valueOf(participant.getBattements()));
        }
    }
}
