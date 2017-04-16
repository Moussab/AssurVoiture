package dz.tdm.AmrineMadji.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import dz.tdm.AmrineMadji.CarteGriseFormaulaire;
import dz.tdm.AmrineMadji.HistoriqueDeclaration;
import dz.tdm.AmrineMadji.ModifierPermis;
import dz.tdm.AmrineMadji.R;
import dz.tdm.AmrineMadji.Settings;
import dz.tdm.AmrineMadji.adapters.SettingdAdapter;
import dz.tdm.AmrineMadji.utils.ClickListener;
import dz.tdm.AmrineMadji.utils.DividerItemDecoration;
import dz.tdm.AmrineMadji.utils.RecyclerTouchListener;

public class ListFragment extends Fragment {

    private List<String> setiings = new ArrayList<>();
    private RecyclerView recyclerView;
    private SettingdAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    public static String string;

    public ListFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.settings_list);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        setiings.add("Ajouter carte grise");
        setiings.add("Modifier Permis");
        setiings.add("Historique de déclaration");

        mAdapter = new SettingdAdapter(setiings);

        recyclerView.setAdapter(mAdapter);




        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
                if (tabletSize) {
                    // do something

                } else {
                    Intent intent;
                    // do something else
                    switch (position){
                        case 0:
                            ListFragment.string = setiings.get(position);
                            intent = new Intent(getActivity(), CarteGriseFormaulaire.class);
                            intent.putExtra("EXTRA_SESSION_ID", "2");
                            startActivity(intent);
                            break;

                        case 1:
                            ListFragment.string = setiings.get(position);
                            intent = new Intent(getActivity(), ModifierPermis.class);
                            startActivity(intent);
                            break;

                        case 2:
                            ListFragment.string = setiings.get(position);
                            intent = new Intent(getActivity(), HistoriqueDeclaration.class);
                            startActivity(intent);
                            break;
                    }

                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        // Inflate the layout for this fragment
        return view;
    }
}
