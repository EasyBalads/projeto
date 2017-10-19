package easybalads.easybalads;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class configuracoes extends AppCompatActivity {
    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    ListView lvOpcoes;
    int opcao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        lvOpcoes = (ListView) findViewById(R.id.lvopcoes);
        opcoes = new ArrayList<String>();

        opcoes.add("Notificações");
        opcoes.add("Termos de Uso");
        opcoes.add("Ajuda");
        opcoes.add("Sobre Nós");

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcoes);
        lvOpcoes.setAdapter(adaptador);lvOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        cliqueNotificações(view);
                        break;
                    case 1:
                        cliqueTermos(view);
                        break;
                    case 2:
                        cliqueAjuda(view);
                        break;
                    case 3:
                        cliqueSobre(view);
                        break;
                    default:
                        finish();
                        break;
                }
            }
        });
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

    public void cliqueNotificações(View view){
        //Cria o construtuor do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Notificações");
        /*builder.setIcon(R.drawable.desafiosdeti);*/
        //define a mensagem
        if(opcao == 0) {
            builder.setMessage("Notificações estão ativadas!");
            //define um botão negativo
            builder.setNegativeButton("Desativar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    opcao = 2;
                /*TextView message = (TextView) alerta.findViewById(android.R.id.message);
                message.setText("Notificações estão Desativadas!");*/
                }
            });
        }else{
            builder.setMessage("Notificações estão desativadas!");
            //define um botão positivo
            builder.setPositiveButton("Ativar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    opcao = 0;
                /*TextView message = (TextView) alerta.findViewById(android.R.id.message);
                message.setText("Notificações estão Ativadas!");*/
                }
            });
        }
        //define um botão neutro
        /*builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
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

    public void cliqueTermos(View view){
        //Cria o construtuor do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Termos de Uso");
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

    public void cliqueAjuda(View view){
        //Cria o construtuor do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Ajuda");
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

    public void cliqueSobre(View view){
        //Cria o construtuor do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Sobre Nós");
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
