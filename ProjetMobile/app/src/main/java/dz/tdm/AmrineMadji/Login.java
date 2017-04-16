package dz.tdm.AmrineMadji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dz.tdm.AmrineMadji.models.CarteGris;
import dz.tdm.AmrineMadji.models.Permis;

public class Login extends AppCompatActivity {


    private TextView sinscrire;
    private TextView fb;
    private TextView plusTard;
    private EditText email;
    private EditText password;
    private TextView buttonsignin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        sinscrire = (TextView) findViewById(R.id.inscrire);
        fb = (TextView)findViewById(R.id.fb);
        plusTard = (TextView)findViewById(R.id.save);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        buttonsignin = (TextView) findViewById(R.id.buttonsignin);


        plusTard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Splash.user.setEmail("dm_amrine@esi.dz");
                Splash.user.setNom("AMRINE Moussab Amine");
                Splash.user.setPermis(new Permis("B","2453954"));
                Splash.user.getCarteGris().add(new CarteGris("928474098",
                        "817829878",
                        "015932 200 16"));
                Intent it = new Intent(Login.this, Home.class);
                startActivity(it);
            }
        });

        sinscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(Login.this, SignUp.class);
                startActivity(it);
            }
        });


        buttonsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Splash.user.setEmail("dm_amrine@esi.dz");
                Splash.user.setNom("AMRINE Moussab Amine");
                Splash.user.setPermis(new Permis("B","2453954"));
                Splash.user.getCarteGris().add(new CarteGris("928474098",
                        "817829878",
                        "015932 200 16"));
                Intent it = new Intent(Login.this, Home.class);
                startActivity(it);
                onBackPressed();
            }
        });


    }
}
