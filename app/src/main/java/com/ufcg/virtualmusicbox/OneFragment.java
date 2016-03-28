package com.ufcg.virtualmusicbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

public class OneFragment extends Fragment{

    private CheckBox voting_button;
    private int last_checked_position;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(new String[]{"Vem pro meu Lounge - Safadao", "Aquele 1% Vagabundo - Safadao", "Camarote - Safadao", "Sosseguei - Safadao",
                "Voce Merece Cache - Safadao", "Sou ciumento mesmo - Safadao", "Novinha Vai no Chao - Safadao", "Na Hora da Raiva - Safadao", "Segunda Opcao - Safadao"});
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    private void getVotingAction(CheckBox voting_button, int last_checked_position){
        if(voting_button.isChecked()){

            Log.e("Checked", "clicou em " + last_checked_position );
        }
    }

}
