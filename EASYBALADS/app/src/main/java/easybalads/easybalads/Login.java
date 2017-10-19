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
import android.widget.LinearLayout;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finish(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    public void clickEsqueceu(View view){
        if(view.getId() == R.id.txtEsqueceu){
            //Cria o construtuor do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Esqueceu a senha?");
            //define a mensagem
            builder.setMessage("Em construção!");
        /*builder.setIcon(R.drawable.desafiosdeti);*/
            //define um botão positivo
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            //define um botão negativo
        /*builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(MainActivity.this, "Vamos Melhorar!", Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão neutro
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(MainActivity.this, "Nem Sim, nem Não!", Toast.LENGTH_SHORT).show();
            }
        });*/
            //cria o AlertDialog
            AlertDialog  alerta = builder.create();
            //Exibe
            alerta.show();
            Button positiveButton = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
            LinearLayout parent = (LinearLayout) positiveButton.getParent();
            parent.setGravity(Gravity.CENTER_HORIZONTAL);
            View leftSpacer = parent.getChildAt(1);
            leftSpacer.setVisibility(View.GONE);
        }
    }
}
