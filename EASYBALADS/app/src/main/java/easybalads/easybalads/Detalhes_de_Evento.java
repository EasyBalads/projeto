package easybalads.easybalads;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Detalhes_de_Evento extends AppCompatActivity {
    private String titulo,descricao,data, hora, valor,organizador, endereco,participantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_de_evento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView tit = (TextView) findViewById(R.id.txtT);
        TextView desc = (TextView) findViewById(R.id.txtDescricao);
        TextView dt = (TextView) findViewById(R.id.txtD);
        TextView hr = (TextView) findViewById(R.id.txtH);
        TextView vl = (TextView) findViewById(R.id.txtEntrada);
        TextView org = (TextView) findViewById(R.id.txtOrganizador);
        TextView end = (TextView) findViewById(R.id.txtEndereco);
        TextView part = (TextView) findViewById(R.id.txtParticipantes);
        Intent i = getIntent();
        if(i!=null){
            titulo = i.getStringExtra("msg");
            descricao = i.getStringExtra("msg2");
            data = i.getStringExtra("msg3");
            hora = i.getStringExtra("msg4");
            valor = i.getStringExtra("msg5");
            organizador = i.getStringExtra("msg6");
            endereco = i.getStringExtra("msg7");
            participantes = i.getStringExtra("msg8");
            tit.setText(titulo);
            desc.setText(descricao);
            dt.setText(data);
            hr.setText(hora);
            vl.setText(valor);
            org.setText(organizador);
            end.setText(endereco);
            part.setText(participantes);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        this.finish();
        return;
    }


}
