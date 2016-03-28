package com.ufcg.virtualmusicbox;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.ufcg.model.*;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;
    static DataBaseStorage mDdDespesasHelper;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public CheckBox mCheckBox;
        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
            mCheckBox = (CheckBox) v.findViewById(R.id.cb_voting_button);

            mDdDespesasHelper = new DataBaseStorage(v.getContext());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset[position]);

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] cardText = mDataset[position].split(" - ");
                Song votedSong = new Song(position, cardText[0], cardText[1], 0);

                if (((CheckBox) v).isChecked()) {
                    //If the song was not previously voted,
                    if(mDdDespesasHelper.getSong(position) != null)
                    {
                        votedSong.updateVotes(1);
                        mDdDespesasHelper.edit(votedSong);
                    }
                    else{
                        votedSong.updateVotes(1);
                        mDdDespesasHelper.add(votedSong);
                    }

                } else {
                    Log.e("double click", " desativar");
                    votedSong.updateVotes(-1);
                    mDdDespesasHelper.edit(votedSong);
                }

                Log.e("Votes ", mDdDespesasHelper.getSong(position).getVotes() + "  >>> number of votes: " + mDdDespesasHelper.getSong(position).getVotes());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}