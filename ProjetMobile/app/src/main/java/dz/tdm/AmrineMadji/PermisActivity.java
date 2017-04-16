package dz.tdm.AmrineMadji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dz.tdm.AmrineMadji.models.Permis;

public class PermisActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView suivant_cg;
    private EditText n_permis;
    private EditText n_categorie;
    private Permis permis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permis);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Permis de Conduire");


        n_permis = (EditText)findViewById(R.id.n_permis);

        n_categorie = (EditText) findViewById(R.id.n_categorie);

        permis = new Permis("B","2453954");

        suivant_cg = (TextView) findViewById(R.id.save);
        suivant_cg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Splash.user.setPermis(permis);
                Intent intent = new Intent(PermisActivity.this,CarteGriseFormaulaire.class);
                intent.putExtra("EXTRA_SESSION_ID", "1");
                startActivity(intent);
                onBackPressed();
            }
        });



    }
}
