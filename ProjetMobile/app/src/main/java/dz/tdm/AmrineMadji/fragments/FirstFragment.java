package dz.tdm.AmrineMadji.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import dz.tdm.AmrineMadji.Editer;
import dz.tdm.AmrineMadji.Guide;
import dz.tdm.AmrineMadji.Home;
import dz.tdm.AmrineMadji.Map;
import dz.tdm.AmrineMadji.R;
import dz.tdm.AmrineMadji.models.User;


/**
 * Created by Amine on 31/03/2017.
 */

public class FirstFragment extends Fragment  {
    public static final int REQUEST_IMAGE_CAPTURE = 1994;
    public static final int REQUEST_TAKE_PHOTO = 1992;
    public static final String ARG_PAGE = "ARG_PAGE";
    private TextView edit;
    private TextView guide;
    private LinearLayout call;
    private LinearLayout mail;
    private LinearLayout ecrire;
    private LinearLayout share_drive;
    private LinearLayout geoLoc;
    private int mPageNo;


    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);


        call = (LinearLayout) view.findViewById(R.id.call_layout);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((Home) getActivity()).simState == 1){
                    ((Home) getActivity()).buildAlertMessageNoSim();
                }else {
                    Intent sIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0553140453"));
                    sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(sIntent);
                }
            }
        });

        mail = (LinearLayout) view.findViewById(R.id.mail_layout);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( !((Home) getActivity()).manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    ((Home) getActivity()).buildAlertMessageNoGps();
                }else {
                    ((Home) getActivity()).geoCode();
                    ((Home) getActivity()).showAlertType(1, 1);
                    ((Home) getActivity()).dialogType.show();
                    ((Home) getActivity()).userList = new ArrayList<User>();
                }

            }
        });

        ecrire = (LinearLayout) view.findViewById(R.id.msg_layout);
        ecrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !((Home) getActivity()).manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    ((Home) getActivity()).buildAlertMessageNoGps();
                }else {
                    if (((Home) getActivity()).simState == 1){
                        ((Home) getActivity()).buildAlertMessageNoSim();
                    }else {
                        ((Home) getActivity()).geoCode();
                        ((Home) getActivity()).showAlertType(2, 0);
                        ((Home) getActivity()).dialogType.show();
                        ((Home) getActivity()).userList = new ArrayList<User>();
                    }
                }
            }
        });

        share_drive = (LinearLayout) view.findViewById(R.id.upload_layout);
        share_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !((Home) getActivity()).manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    ((Home) getActivity()).buildAlertMessageNoGps();
                }else {
                    ((Home) getActivity()).geoCode();
                    ((Home) getActivity()).showAlertType(1, 2);
                    ((Home) getActivity()).dialogType.show();
                    ((Home) getActivity()).userList = new ArrayList<User>();
                }
            }
        });

        geoLoc = (LinearLayout) view.findViewById(R.id.geo_layout);
        geoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !((Home) getActivity()).manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    ((Home) getActivity()).buildAlertMessageNoGps();
                }else {
                    Intent intent = new Intent(getContext(), Map.class);
                    startActivity(intent);
                }
            }
        });

        edit = (TextView) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Editer.class);
                startActivity(intent);
            }
        });

        guide = (TextView) view.findViewById(R.id.guide);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Guide.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
