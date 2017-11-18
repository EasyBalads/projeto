package easybalads.easybalads;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtEmail, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                home();
                finish();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        home();
        finish();
        return;
    }

    public void clickEsqueceu(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Esqueceu a senha?");
        LayoutInflater inflater=this.getLayoutInflater();
        View layout=inflater.inflate(R.layout.txtfield,null);
        builder.setView(layout);
        final EditText input = (EditText)layout.findViewById(R.id.edtEsqueceuEmail);
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if(input.getText().toString().equals("")){
                    Toast.makeText(Login.this,"E-mail inválido!" , Toast.LENGTH_SHORT).show();
                }else if(!isValidEmail(input.getText().toString())){
                    Toast.makeText(Login.this,"E-mail inválido!" , Toast.LENGTH_SHORT).show();
                }else{
                    enviarEmail(input.getText().toString());
                }
            }
        });

        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        AlertDialog  alerta = builder.create();
        alerta.setCanceledOnTouchOutside(false);
        alerta.show();
        Button positiveButton = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
    }

    public void enviarEmail(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this,"Verifique seu e-mail!" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void clickLogin(View view){
        if(edtEmail.getText().toString().equals("")) {
            edtEmail.setError("Campo vazio!");
        }else if(edtSenha.getText().toString().equals("")) {
            edtSenha.setError("Campo vazio!");
        }else {
            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, "E-mail ou Senha inválido!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "Bem Vindo!", Toast.LENGTH_SHORT).show();
                                home();
                                finish();

                            }
                        }
                    });
        }
    }

    public void home(){
        startActivity(new Intent(this, Inicial_Usuario.class));
    }

    public void clickCad(View v){
        if(v.getId() == R.id.btnCad){
            startActivity(new Intent(this, Cadastrar.class));
            finish();
        }
    }

}
