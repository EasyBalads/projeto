package easybalads.easybalads;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Configuracoes extends AppCompatActivity {
    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    ListView lvOpcoes;
    private static String MINHAS_PREFERENCIAS = "";
    String opcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        lvOpcoes = (ListView) findViewById(R.id.lvopcoes);
        opcoes = new ArrayList<String>();
        SharedPreferences sharedPreferences = getSharedPreferences(MINHAS_PREFERENCIAS, MODE_PRIVATE);
        opcao = sharedPreferences.getString("opc", "");

        opcoes.add("Ajuda");
        opcoes.add("Sobre Nós");

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcoes);
        lvOpcoes.setAdapter(adaptador);lvOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        cliqueAjuda(view);
                        break;
                    case 1:
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


    public void cliqueAjuda(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajuda");
        builder.setMessage("\n\tDúvidas envie um e-mail para: \n\n\t\t\teasybalads@gmail.com\n");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        AlertDialog  alerta = builder.create();
        alerta.show();
        Button positiveButton = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
    }

    public void cliqueSobre(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sobre Nós");
        String msg = "Desenvolvido por:";
        String msg2 = "Agradecimentos:";
        builder.setMessage("\t\t\t\t"+msg+"\n\n"+"Elias Dourado"+"\n"+"Gustavo Soares"+"\n"+"Leonardo dos Anjos"+"\n"+"Vitor Oliveira"+"\n\n"+"\t\t\t\t\t"+msg2+"\n\n"+"Professor Aristóteles"+"\n"+"Professora Tatiana"+"\n"+"Equipe AlugueLugue");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        AlertDialog  alerta = builder.create();
        alerta.show();
        Button positiveButton = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
    }
}
