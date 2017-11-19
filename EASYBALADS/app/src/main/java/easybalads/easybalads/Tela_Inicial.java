package easybalads.easybalads;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Tela_Inicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__inicial);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMapa();
            }
        }, 2000);
    }

    public void mostrarMapa(){
        startActivity(new Intent(this, Inicial_Usuario.class));
        finish();
    }
}
