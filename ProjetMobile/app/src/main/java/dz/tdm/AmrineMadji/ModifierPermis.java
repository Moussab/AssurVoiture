package dz.tdm.AmrineMadji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dz.tdm.AmrineMadji.models.Permis;

public class ModifierPermis extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView save;
    private EditText n_permis;
    private EditText n_categorie;
    private Permis permis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_permis);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Mise à jour Permis");



        n_permis = (EditText)findViewById(R.id.n_permis);

        n_categorie = (EditText) findViewById(R.id.n_categorie);

        permis = new Permis(n_categorie.getText().toString().trim(),
                n_permis.getText().toString().trim());

        save = (TextView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Splash.user.setPermis(permis);
                onBackPressed();
            }
        });
    }
}
