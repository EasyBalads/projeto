package easybalads.easybalads;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class configuracoes extends AppCompatActivity {
    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    ListView lvOpcoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
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
                        break;
                    case 1:
                        //termos();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        finish();
                        break;
                }
            }
        });
    }
    /*private void termos(){
        Intent it = new Intent(this, termos_de_uso.class);
        startActivity(it);
    }*/
}