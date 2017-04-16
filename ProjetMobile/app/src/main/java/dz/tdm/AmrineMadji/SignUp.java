package dz.tdm.AmrineMadji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {




    private TextView fb;
    private TextView account;
    private EditText email;
    private EditText password;
    private EditText user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);




        fb = (TextView)findViewById(R.id.fb);
        account = (TextView)findViewById(R.id.account);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        user = (EditText)findViewById(R.id.user);



        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Splash.user.setEmail("dm_amrine@esi.dz");
                Splash.user.setNom("AMRINE Moussab Amine");
                Intent it = new Intent(SignUp.this, PermisActivity.class);
                startActivity(it);
                onBackPressed();
            }
        });
    }
}
