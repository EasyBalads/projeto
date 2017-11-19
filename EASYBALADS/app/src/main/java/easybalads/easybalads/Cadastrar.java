package easybalads.easybalads;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastrar extends AppCompatActivity {
    RadioButton pf;
    FrameLayout it;
    View np;
    private FirebaseAuth mAuth;
    private EditText edtNome,edtSobrenome,edtEmail,edtSenha;
    private EditText edtNome2,edtRazao,edtEmail2,edtSenha2,edtFone,edtCnpj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        pf = (RadioButton) findViewById(R.id.pf);
        pf.setChecked(true);
        it = (FrameLayout)findViewById(R.id.container2);
        np = getLayoutInflater().inflate(R.layout.activity_cadastro_usuario,null);
        it.addView(np);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, Login.class));
                finish();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, Login.class));
        finish();
        return;
    }

    public void cadUserFisica(View v){
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtSobrenome  = (EditText) findViewById(R.id.edtSobrenome);
        edtEmail  = (EditText) findViewById(R.id.edtEmail);
        edtSenha  = (EditText) findViewById(R.id.edtSenha);

        if(edtNome.getText().toString().equals("")) {
            edtNome.setError("Campo vazio!");
        }else if(edtSobrenome.getText().toString().equals("")){
            edtSobrenome.setError("Campo vazio!");
        }else if(edtEmail.getText().toString().equals("")){
            edtEmail.setError("Campo vazio!");
        }else if(edtSenha.getText().toString().equals("")) {
            edtSenha.setError("Campo vazio!");
        }else if(!isValidEmail(edtEmail.getText().toString())){
            edtEmail.setError("E-mail inválido!");
        }else if(edtSenha.getText().toString().length() < 6){
            edtSenha.setError("Minimo 6 caracteres!");
        }else{
            mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Cadastrar.this, "Verifique sua conexão com internet!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                myRef.child("nome").setValue(edtNome.getText().toString());
                                myRef.child("sobrenome").setValue(edtSobrenome.getText().toString());
                                myRef.child("email").setValue(edtEmail.getText().toString());
                                myRef.child("senha").setValue(edtSenha.getText().toString());
                                myRef.child("tipo").setValue("1");
                                myRef.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                Toast.makeText(Cadastrar.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                home();
                                finish();
                            }
                        }
                    });
        }
    }


    public void cadUserJuridica(View v){
        edtNome2 = (EditText)findViewById(R.id.edtNome2);
        edtRazao = (EditText)findViewById(R.id.edtRazao);
        edtEmail2 = (EditText)findViewById(R.id.edtEmail2);
        edtSenha2 = (EditText)findViewById(R.id.edtSenha2);
        edtFone = (EditText)findViewById(R.id.edtFone);
        edtCnpj = (EditText)findViewById(R.id.edtCnpj);

        if(edtNome2.getText().toString().equals("")) {
            edtNome2.setError("Campo vazio!");
        }else if(edtRazao.getText().toString().equals("")){
            edtRazao.setError("Campo vazio!");
        }else if(edtEmail2.getText().toString().equals("")){
            edtEmail2.setError("Campo vazio!");
        }else if(edtSenha2.getText().toString().equals("")) {
            edtSenha2.setError("Campo vazio!");
        }else if(edtFone.getText().toString().equals("")){
            edtFone.setError("Campo vazio!");
        }else if(edtCnpj.getText().toString().equals("")){
            edtCnpj.setError("Campo vazio!");
        }else if(!isValidEmail(edtEmail2.getText().toString())){
            edtEmail2.setError("E-mail inválido!");
        }else if(edtSenha2.getText().toString().length() < 6){
            edtSenha2.setError("Minimo 6 caracteres!");
        }else {
            mAuth.createUserWithEmailAndPassword(edtEmail2.getText().toString(), edtSenha2.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Cadastrar.this, "Verifique sua conexão com internet!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                myRef.child("nome").setValue(edtNome2.getText().toString());
                                myRef.child("razao").setValue(edtRazao.getText().toString());
                                myRef.child("email").setValue(edtEmail2.getText().toString());
                                myRef.child("senha").setValue(edtSenha2.getText().toString());
                                myRef.child("telefone").setValue(edtFone.getText().toString());
                                myRef.child("cnpj").setValue(edtCnpj.getText().toString());
                                myRef.child("tipo").setValue("2");
                                myRef.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                Toast.makeText(Cadastrar.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
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

    public final static boolean isValidEmail(CharSequence target) {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void clickRadio(View v){
        boolean checked = ((RadioButton) v).isChecked();
        it.removeAllViews();

        switch(v.getId()) {
            case R.id.pf:
                if (checked)
                    np = getLayoutInflater().inflate(R.layout.activity_cadastro_usuario,null);
                    it.addView(np);
                    break;
            case R.id.pj:
                if (checked)
                    np = getLayoutInflater().inflate(R.layout.activity_cadastro_cliente,null);
                    it.addView(np);
                    break;
        }
    }
}
