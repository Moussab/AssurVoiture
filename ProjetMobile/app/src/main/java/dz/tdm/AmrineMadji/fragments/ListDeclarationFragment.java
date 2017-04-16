package dz.tdm.AmrineMadji.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import dz.tdm.AmrineMadji.R;
import dz.tdm.AmrineMadji.ShowSinistre;
import dz.tdm.AmrineMadji.Splash;
import dz.tdm.AmrineMadji.adapters.SettingdAdapter;
import dz.tdm.AmrineMadji.models.Localisation;
import dz.tdm.AmrineMadji.models.Sinistre;
import dz.tdm.AmrineMadji.models.SinistreType;
import dz.tdm.AmrineMadji.models.User;
import dz.tdm.AmrineMadji.utils.ClickListener;
import dz.tdm.AmrineMadji.utils.DividerItemDecoration;
import dz.tdm.AmrineMadji.utils.RecyclerTouchListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListDeclarationFragment extends Fragment {

    private List<String> setiings = new ArrayList<>();
    private RecyclerView recyclerView;
    private SettingdAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    public static String string;
    public static Sinistre sinistre;
    private List<Sinistre> sinistres = new ArrayList<>();

    public ListDeclarationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_declaration, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.settings_list);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        setiings.add("Face à face -18/06/2016-");
        setiings.add("Dérapage -26/01/2017-");

        mAdapter = new SettingdAdapter(setiings);

        recyclerView.setAdapter(mAdapter);

        data();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
                if (tabletSize) {
                    // do something

                } else {
                    string = setiings.get(position);
                    sinistre = sinistres.get(position);
                    Intent intent = new Intent(getContext(), ShowSinistre.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    public void data(){

        User user = new User("AMRINE Moussab Amine","","");

        List<User> users = new ArrayList();
        users.add(new User(""+R.drawable.sinistre_face_a_face+"",""));
        users.add(new User(""+R.drawable.person2+"",""));
        Localisation localisation = new Localisation("Cherchell Algeria","DZ",2.17977488,36.5993021);
        Sinistre sinistre = new Sinistre("2017/04/02 03:24:44",
                SinistreType.ENTRE_VEHICULE,
                users,
                localisation
        );
        sinistre.setSender(user);
        sinistre.setTypeDeclaration("E-Mail");

        sinistres.add(sinistre);

        users = new ArrayList();
        users.add(new User(""+R.drawable.sinistre_derapage+"",""));
        users.add(new User(""+R.drawable.person2+"",""));
        users.add(new User(""+R.drawable.person2+"",""));
        localisation = new Localisation("Oued Smar","DZ",2.17977488,36.5993021);
        sinistre = new Sinistre("2017/04/15 15:24:44",
                SinistreType.SEUL,
                users,
                localisation
        );
        sinistre.setSender(user);
        sinistre.setTypeDeclaration("DRIVE");

        sinistres.add(sinistre);

    }


}
