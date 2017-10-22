package easybalads.easybalads;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class login extends AppCompatActivity {
    EditText login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        login = (EditText)findViewById(R.id.edtLogin);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                this.finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        this.finish(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    public void onClick(View v){
        if(v.getId() == R.id.btEntrar) {
            if(login.getText().toString().equals("easy@easy.com.br")){
                startActivity(new Intent(this, inicial_usuario.class));
            }else if(login.getText().toString().equals("balads@balads.com.br")){
                startActivity(new Intent(this, inicial_cliente.class));
            }
        }
    }
}
