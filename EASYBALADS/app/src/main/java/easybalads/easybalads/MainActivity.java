package easybalads.easybalads;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.btnCadastrar){
            startActivity(new Intent(this, selecao_cadastrar.class));
        }else if(v.getId() == R.id.btnLogin){
            startActivity(new Intent(this, login.class));
        }
    }
}
