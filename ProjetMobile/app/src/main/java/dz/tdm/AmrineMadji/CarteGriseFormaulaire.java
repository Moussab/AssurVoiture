package dz.tdm.AmrineMadji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dz.tdm.AmrineMadji.models.CarteGris;

public class CarteGriseFormaulaire extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView next;
    private EditText n_permis;
    private EditText n_categorie;
    private EditText n_matriculation;
    private CarteGris carteGris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte_grise_formaulaire);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Nouvelle Carte Grise");


        n_permis = (EditText)findViewById(R.id.n_permis);
        n_categorie = (EditText)findViewById(R.id.n_categorie);
        n_matriculation = (EditText)findViewById(R.id.n_matriculation);

        carteGris = new CarteGris("928474098",
                                "817829878",
                                "015932 200 16");

        next = (TextView) findViewById(R.id.save);
        final String s = getIntent().getStringExtra("EXTRA_SESSION_ID");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Splash.user.getCarteGris().add(carteGris);
                if (s.equals("1")){
                    intent = new Intent(CarteGriseFormaulaire.this,Home.class);
                    startActivity(intent);
                    onBackPressed();
                }else {
                    onBackPressed();
                }
            }
        });
    }
}
