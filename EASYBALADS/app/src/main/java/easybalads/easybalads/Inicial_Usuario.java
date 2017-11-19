package easybalads.easybalads;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Inicial_Usuario extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    FrameLayout it;
    View np;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    String tipo;
    String nome,sobrenome,razao,email,fone,cnpj;
    EditText cep;
    Util util;
    String lat,lng;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        dialog = new ProgressDialog(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, new Provider_Fragment(), "Provider_Fragment" );
        transaction.commitAllowingStateLoss();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Logado();
                } else {
                    Logar();
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        it = (FrameLayout)findViewById(R.id.container);

        if (id == R.id.nav_login) {
            startActivity(new Intent(this, Login.class));
            finish();
        }else if (id == R.id.nav_home) {
            it.removeAllViews();
            getSupportActionBar().setTitle(R.string.app_name);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.container, new Provider_Fragment(), "Provider_Fragment" );
            transaction.commitAllowingStateLoss();
        }else if (id == R.id.nav_perfil) {
            it.removeAllViews();
            getSupportActionBar().setTitle(R.string.perfil);
            np = getLayoutInflater().inflate(R.layout.activity_perfil_usuario,null);
            it.addView(np);
            setInfoPerfil();
        }else if (id == R.id.nav_cad){
            it.removeAllViews();
            getSupportActionBar().setTitle("Cadastrar Evento");
            np = getLayoutInflater().inflate(R.layout.activity_cadastrar__eventos,null);
            it.addView(np);
        }else if (id == R.id.nav_eve){
            it.removeAllViews();
            getSupportActionBar().setTitle("Eventos Cadastrados");
            np = getLayoutInflater().inflate(R.layout.activity_eventos__cadastrados,null);
            it.addView(np);
            consultarEventos();
        }else if (id == R.id.nav_conf){
            startActivity(new Intent(this, Configuracoes.class));
        }else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Inicial_Usuario.class));
            finish();
        }else if (id == R.id.nav_sair) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setInfoPerfil(){
        TextView Nome = (TextView)findViewById(R.id.perfilNome);
        TextView Razao = (TextView)findViewById(R.id.perfilRazao);
        TextView Email = (TextView)findViewById(R.id.perfilEmail);
        TextView Telefone = (TextView)findViewById(R.id.perfilTelefone);
        TextView Cnpj = (TextView)findViewById(R.id.perfilCnpj);
        TableRow perfil1 = (TableRow)findViewById(R.id.perfil1);
        TableRow perfil2 = (TableRow)findViewById(R.id.perfil2);
        TableRow perfil3 = (TableRow)findViewById(R.id.perfil3);

        if(tipo.toString().equals("1")){
            perfil1.setVisibility(View.GONE);
            perfil2.setVisibility(View.GONE);
            perfil3.setVisibility(View.GONE);

            Nome.setText(nome.toString()+" "+sobrenome.toString());
            Email.setText(email.toString());

        }else{
            perfil1.setVisibility(View.VISIBLE);
            perfil2.setVisibility(View.VISIBLE);
            perfil3.setVisibility(View.VISIBLE);

            Nome.setText(nome.toString());
            Email.setText(email.toString());
            Razao.setText(razao.toString());
            Telefone.setText(fone.toString());
            Cnpj.setText(cnpj.toString());
        }

    }

        public void consultarEventos(){

        FirebaseDatabase d1 = FirebaseDatabase.getInstance();
        DatabaseReference m1 = d1.getReference("eventos");
        m1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Eventos> ev = new ArrayList<Eventos>();
                List<Eventos> ev2 = new ArrayList<Eventos>();
                String titulo,dt,hr,participantes;
                if(dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if(postSnapshot.child("idusuario").getValue().toString().equals(user.getUid())) {
                            titulo = postSnapshot.child("titulo").getValue().toString();
                            dt = postSnapshot.child("data").getValue().toString();
                            hr = postSnapshot.child("hora").getValue().toString();
                            participantes = postSnapshot.child("participantes").getValue().toString();
                            ev = gerarEvento(ev2,titulo,dt,hr,participantes);
                        }
                    }
                }
                Listar(ev);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void Listar(List<Eventos> ev){
        ListView listView;
        List<Eventos> list_events;

        list_events = ev;
        listView = (ListView)findViewById(android.R.id.list);
        listView.setAdapter(new ListEventoAdapter(this,list_events));
    }

    private List<Eventos> gerarEvento(List<Eventos> events,String titulo,String dt,String hr,String participantes) {
        events.add(criarEvento(titulo.toString(), dt.toString(), hr.toString(),participantes.toString()));

        return events;
    }

    private Eventos criarEvento(String titulo, String dt, String hora,String participantes) {
        Eventos events = new Eventos(titulo, dt, hora,participantes);
        return events;
    }

    public void Logar(){
        NavigationView navi = (NavigationView)findViewById(R.id.nav_view);
        navi.setNavigationItemSelectedListener(this);
        Menu menu = navi.getMenu();
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem login = menu.findItem(R.id.nav_login);
        logout.setVisible(false);
        login.setVisible(true);

    }

    public void Logado(){
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tipo = dataSnapshot.child("tipo").getValue().toString();
                if(tipo.toString().equals("1")) {
                    nome = dataSnapshot.child("nome").getValue().toString();
                    sobrenome = dataSnapshot.child("sobrenome").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                }else{
                    nome = dataSnapshot.child("nome").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    razao = dataSnapshot.child("razao").getValue().toString();
                    cnpj = dataSnapshot.child("cnpj").getValue().toString();
                    fone = dataSnapshot.child("telefone").getValue().toString();
                    tipo = dataSnapshot.child("tipo").getValue().toString();
                }
                tipeUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public  void tipeUser(){
        NavigationView navi = (NavigationView)findViewById(R.id.nav_view);
        navi.setNavigationItemSelectedListener(this);
        View header = navi.getHeaderView(0);
        Menu menu = navi.getMenu();
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem login = menu.findItem(R.id.nav_login);
        MenuItem perfil = menu.findItem(R.id.nav_perfil);
        MenuItem cad = menu.findItem(R.id.nav_cad);
        MenuItem eve = menu.findItem(R.id.nav_eve);
        TextView nome2 = (TextView) header.findViewById(R.id.menuNome);
        TextView email2 = (TextView) header.findViewById(R.id.menuEmail);

        nome2.setText(nome.toString());
        email2.setText(email.toString());
        logout.setVisible(true);
        login.setVisible(false);
        perfil.setVisible(true);

        if(tipo.toString().equals("1")){
            cad.setVisible(false);
            eve.setVisible(false);
        }else {
            cad.setVisible(true);
            eve.setVisible(true);
        }
    }

    public void clickBuscarCep(View v){
        cep = (EditText) findViewById(R.id.edtCep);

        util = new Util(this,
                R.id.edtCidade,
                R.id.edtEstado);
        if(cep.getText().toString().equals("")){
            cep.setError("Campo vazio!");
        }else if(cep.getText().length() < 8){
            cep.setError("Minimo 8 caracteres!");
        }else {
            ZipCodeListener zip = new ZipCodeListener(this);
            zip.execute();
            View view = this.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public String getUriRequest() {
        return "https://viacep.com.br/ws/"+cep.getText()+"/json/unicode/";
    }

    public void lockFields(boolean b) {
        util.lockFields(b);
    }

    public void setDataViews(Endereco endereco) {
        setField(R.id.edtEndereco,endereco.getLogradouro());
        setField(R.id.edtBairro,endereco.getBairro());
        setField(R.id.edtCidade,endereco.getLocalidade());
        setField(R.id.edtEstado,endereco.getUf());
    }

    private void setField( int id, String data ){
        ((EditText) findViewById(id)).setText( data );
    }

    public void clickCadEvento(View v){
        Handler handle = new Handler();
        dialog.setMessage("Cadastrando ...");
        String cod;
        final EditText titulo,data,hora,descricao,cp,end,bairro,cidade,estado,valor;
        titulo = (EditText)findViewById(R.id.edtTitulo);
        data = (EditText)findViewById(R.id.edtData);
        hora = (EditText)findViewById(R.id.edtHora);
        descricao = (EditText)findViewById(R.id.edtDescricao);
        cp = (EditText)findViewById(R.id.edtCep);
        end = (EditText)findViewById(R.id.edtEndereco);
        bairro = (EditText)findViewById(R.id.edtBairro);
        cidade = (EditText)findViewById(R.id.edtCidade);
        estado = (EditText)findViewById(R.id.edtEstado);
        valor = (EditText)findViewById(R.id.edtValor);

        if(titulo.getText().toString().equals("")) {
            titulo.setError("Campo Vazio!");
        }else if(data.getText().toString().equals("")) {
            data.setError("Campo Vazio!");
        }else if(hora.getText().toString().equals("")) {
            hora.setError("Campo Vazio!");
        }else if(descricao.getText().toString().equals("")) {
            descricao.setError("Campo Vazio!");
        }else if(cp.getText().toString().equals("")) {
            cp.setError("Campo Vazio!");
        }else if(end.getText().toString().equals("")) {
            end.setError("Campo Vazio!");
        }else if(bairro.getText().toString().equals("")) {
            bairro.setError("Campo Vazio!");
        }else if(cidade.getText().toString().equals("")) {
            cidade.setError("Campo Vazio!");
        }else if(estado.getText().toString().equals("")) {
            estado.setError("Campo Vazio!");
        }else if (valor.getText().toString().equals("")) {
            valor.setError("Campo Vazio!");
        }else {
            dialog.show();
            cod = end.getText().toString() + "," + bairro.getText().toString() + "," + cidade.getText().toString() + "," + estado.getText().toString();
            new GetCoordinates().execute(cod.replace(" ", "+"));
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                cadastrarBD(titulo.getText().toString(),data.getText().toString(),hora.getText().toString(),descricao.getText().toString(),
                cp.getText().toString(),end.getText().toString(),bairro.getText().toString(),cidade.getText().toString(),estado.getText().toString(),valor
                .getText().toString());
                }
            }, 15000);




        }
    }

    public void cadastrarBD(final String titulo, final String data, final String hora, final String descricao, final String cp, final String end, final String bairro, final String cidade, final String estado, final String valor){
        DatabaseReference mDatabaseUser, mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("eventos");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        final DatabaseReference novoEvento = mDatabase.push();
        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                novoEvento.child("titulo").setValue(titulo);
                novoEvento.child("data").setValue(data);
                novoEvento.child("hora").setValue(hora);
                novoEvento.child("descricao").setValue(descricao);
                novoEvento.child("cep").setValue(cp);
                novoEvento.child("endereco").setValue(end);
                novoEvento.child("bairro").setValue(bairro);
                novoEvento.child("cidade").setValue(cidade);
                novoEvento.child("estado").setValue(estado);
                novoEvento.child("valor").setValue(valor);
                novoEvento.child("latitude").setValue(lat);
                novoEvento.child("longitude").setValue(lng);
                novoEvento.child("participantes").setValue(0);
                novoEvento.child("organizador").setValue(razao);
                novoEvento.child("idusuario").setValue(user.getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dialog.dismiss();
        Toast.makeText(Inicial_Usuario.this, "Evento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
        it.removeAllViews();
        np = getLayoutInflater().inflate(R.layout.activity_cadastrar__eventos, null);
        it.addView(np);
    }

    public class GetCoordinates extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                JSONObject jsonObject = new JSONObject(s);

                lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}