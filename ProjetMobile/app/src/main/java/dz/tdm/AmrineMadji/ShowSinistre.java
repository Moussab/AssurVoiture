package dz.tdm.AmrineMadji;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import dz.tdm.AmrineMadji.fragments.ListDeclarationFragment;

public class ShowSinistre extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView img;
    private TextView date_heure;
    private TextView type_declaration;
    private TextView nb_personne;
    private TextView adr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sinistre);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(ListDeclarationFragment.string);

        img = (ImageView)findViewById(R.id.img);
        img.setImageResource(Integer.parseInt(ListDeclarationFragment.sinistre.getUserList().get(0).getPhotoPath()));

        date_heure = (TextView)findViewById(R.id.date_heure);
        date_heure.setText(ListDeclarationFragment.sinistre.getDate());

        type_declaration = (TextView) findViewById(R.id.type_declaration);
        type_declaration.setText(ListDeclarationFragment.sinistre.getTypeDeclaration());

        nb_personne = (TextView) findViewById(R.id.nb_personne);
        nb_personne.setText(""+ListDeclarationFragment.sinistre.getUserList().size());

        adr = (TextView) findViewById(R.id.adr);
        adr.setText(ListDeclarationFragment.sinistre.getLocalisation().getAddressLines());

    }
}
