package easybalads.easybalads;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
    String titulo2,data2, hora2, descricao2,cp2,end2, bairro2,cidade2, estado2,valor2;

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
        }else if (id == R.id.nav_termo){
            cliqueTermos();
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

    public void cliqueTermos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Termos de Uso");
        builder.setMessage("Data de Vigência:\n18 de Novembro de 2017\n\n" +
                "Os termos deste contrato (\"Termos de Uso\") regem o relacionamento entre você e a EasyBalads,em relação ao seu uso dos aplicativos, sites e serviços pertinentes da EasyBalads. O uso do Serviço é também regulado pela Política de Privacidade da EasyBalads e outras políticas relevantes, as quais são incorporadas ao presente documento por referência.\n\n" +
                "Antes de acessar ou utilizar o Serviço, o que inclui navegar nos sites da EasyBalads ou acessar seus aplicativos, você deve concordar com os Termos de Uso e a Política de Privacidade. Pode ser necessário também que você registre uma conta no Serviço (uma \"Conta\"). Ao registrar uma conta ou usar o Serviço de outro modo, você declara ter 13 anos de idade ou mais. Se tiver entre 13 e 17 anos, você declara que seu responsável legal analisou e está de acordo com estes Termos. Caso você acesse o Serviço a partir de um Site de Rede Social (\"SRS\"), como o Facebook ou o Google+, você deve obedecer aos termos de serviço/uso desses sites, bem como a estes Termos de Uso..\n\n" +
                "AO INSTALAR, UTILIZAR OU ACESSAR O SERVIÇO, VOCÊ ESTARÁ DE ACORDO COM ESTES TERMOS DE USO. SE NÃO ESTIVER DE ACORDO COM ESTES TERMOS DE USO, NÃO INSTALE, USE OU ACESSE O SERVIÇO. O USO DE SERVIÇO SERÁ CONSIDERADO NULO ONDE FOR PROIBIDO.\n\n" +
                "A EasyBalads se reserva o direito de, a seu critério, alterar, modificar, adicionar ou remover partes destes Termos de Uso, de sua Política de Privacidade, bem como de qualquer outra política da EasyBalads que seja pertinente , publicando as emendas  termos alterados no Serviço da EasyBalads. A continuação do uso do Serviço implicará na aceitação de tais alterações. Caso em algum momento você não concorde com qualquer parte da versão mais recente dos nossos Termos de Uso, da Política de Privacidade da EasyBalads, ou de qualquer outra política da EasyBalads, regras ou códigos de conduta relacionados ao seu uso do Serviço, sua licença para utilizar o Serviço cessará imediatamente, e você deverá interromper o uso do Serviço imediatamente.\n\n" +
                "Licença\n" +
                "Concessão de Licença Limitada para Uso do Serviço\n" +
                "Condicionadas à sua concordância com os presentes Termos de Uso, bem como com outras políticas aplicáveis da EasyBalads e sua conformidade continuada das mesmas, a EasyBalads concede a você uma licença não exclusiva, intransferível, não sublicenciável, revogável e limitada para o acesso e uso do Serviço para seus próprios fins de entretenimento não comerciais. Você concorda em não usar o Serviço para nenhuma outra finalidade.\n\n" +
                "Aplicam-se as seguintes restrições ao uso do Serviço:\n\n" +
                "Você não está autorizado a criar uma Conta ou acessar o Serviço caso tenha menos de 13 anos de idade; você deve restringir o uso por menores de idade e negar o acesso a crianças menores que 13 anos. Você aceita plena responsabilidade pelo uso não autorizado do Serviço por menores. Você é responsável pelo uso de seu cartão de crédito ou outro instrumento de pagamento (por exemplo, PayPal) por menores.\n\n" +
                "Você não poderá (e nem tentar) comprar, vender, alugar ou dar sua Conta, criar uma Conta usando identidade ou dados falsos, ou em nome de outrem; você não poderá usar o Serviço caso já tenha sido removido de ou banido anteriormente de usar qualquer aplicativo" +
                " da EasyBalads.\n\n" +
                "Você deve usar sua Conta apenas para fins não comerciais; você não poderá usar o Serviço para fazer propaganda ou solicitar ou transmitir propagandas comerciais, inclusive correntes, mala direta, spam ou mensagens repetitivas ou enganosas a ninguém.Dados de Acesso e sua Conta\n\n" +
                "Será solicitado que você escolha uma senha para sua conta. Alternativamente, você poderá usar outras credenciais para acessar a Conta (\"Dados de Acesso\").  Você não deve compartilhar a Conta, os Dados de Acesso nem permitir que alguém acesse sua Conta ou faça qualquer outra ação que possa comprometer a segurança da sua Conta.. Caso tome conhecimento ou suspeite de violações de segurança, incluindo, mas não limitado à, perda, roubo ou divulgação não autorizada dos Dados de Acesso, você deve notificar imediatamente a EasyBalads e modificar seus Dados de Acesso. Você é o único responsável pela manutenção da confidencialidade dos Dados de Acesso e será responsável por todos os usos dos Dados de Acesso autorizados ou não por você, incluindo compras. Você é responsável por tudo o que acontecer por meio da sua Conta.\n\n" +
                "A EasyBalads se reserva o direito de remover ou recuperar quaisquer nomes de usuário, a qualquer momento e por qualquer motivo, incluindo, mas não limitado à alegações de terceiros de que um nome de usuário viole os direitos de terceiros.\n\n" +
                "O Serviço suporta apenas uma Conta por aplicativo em um dispositivo compatível.\n\n" +
                "Limitações de Licença\n" +
                "Qualquer uso do Serviço em descumprimento a estas Limitações de Licença é estritamente proibido e poderá resultar na revogação imediata de sua licença limitada e responsabilizá-lo por violações da lei.\n\n" +
                "Você concorda em se abster, sob quaisquer circunstâncias, de:\n\n" +
                "Participar de qualquer ato que a EasyBalads julgue incompatível com o espírito ou propósito do Serviço ou fazer uso indevido dos serviços de suporte da EasyBalads.\n\n" +
                "Fazer uso ou participar (direta ou indiretamente) de trapaças, explorar erros, usar softwares de automação, bots, hacks, modificações ou qualquer software de terceiros não autorizado projetado para modificar o Serviço ou interferir no Serviço, em aplicativos da EasyBalads ou na experiência de aplicativo da EasyBalads.\n\n" +
                "Alterar ou causar a alteração de os arquivos que fazem parte do Serviço ou dos aplicativos da EasyBalads sem o consentimento expresso por escrito da EasyBalads.\n\n" +
                "Interromper, interferir ou, de outro modo, afetar adversamente o fluxo normal do Serviço ou, de outro modo, agir de maneira que possa afetar negativamente experiência de outros usuários ao utilizar o Serviço ou usar os aplicativos da EasyBalads. Isso inclui a comercialização de vitórias e qualquer outro tipo de manipulação de rankings, aproveitando-se de erros no Serviço para obter vantagem injusta sobre outros jogadores, bem como qualquer outro ato que intencionalmente viole ou não esteja de acordo com a proposta do Serviço.\n\n" +
                "Interromper, sobrecarregar,auxiliar, ou contribuir para a interrupção ou na sobrecarga de qualquer computador ou servidor (\"Servidor\") utilizado para oferecer ou dar apoio ao Serviço ou a ambientes de aplicativo da EasyBalads.\n\n" +
                "Instituir, ajudar, ou se envolver em qualquer tipo de ataque incluindo, mas não limitado à, distribuição de vírus, ataques de negação de serviço ou outras tentativas de interromper o Serviço, uso ou desfruto do mesmo por parte de outra pessoa..\n\n" +
                "Tentar obter acesso não autorizado ao Serviço, às Contas registradas para outros ou às computadores, Servidores ou redes conectadas ao Serviço por quaisquer meios que não sejam a interface de usuário fornecida pela EasyBalads, incluindo, mas não limitado ao, contorno ou modificação, tentativa de burlar ou modificar,  incentivar ou auxiliar terceiros ao burlar ou modificara  segurança, tecnologia, dispositivo ou softwares que façam parte do Serviço.\n\n" +
                "Publicar qualquer informação que seja ofensiva, ameaçadora, obscena, difamatória, caluniosa, ou, ainda de teor questionável ou ofensivo, seja de forma racial, sexual, religiosa, questionável ou ofensiva, ou, ainda, envolver-se em comportamento negativo em curso, tais como, por exemplo, publicando repetidamente informações em de forma não solicitada.\n\n" +
                "Publicar informações que contenham nudez, violência excessiva, material ofensivo ou que contenham links para tais conteúdos.\n\n" +
                "Assediar, insultar ou ferir terceiros, incluindo funcionários da EasyBalads e representantes do serviço de suporte ao cliente da EasyBalads, ou tentar praticar tais atos, ou, ainda, defender ou incitar a prática de tais atos.\n\n" +
                "Disponibilizar por meio do Serviço materiais ou informações que infrinjam direitos autorais, marcas, patentes, segredos comerciais, direito de privacidade, direito de publicidade ou outros direitos de terceiros ou de entidades jurídicas ou que personifiquem outra pessoa, incluindo, mas não limitado a,funcionários da EasyBalads.\n\n" +
                "Fazer engenharia reversa, descompilar, desmontar, decifrar ou tentar obter o Código-fonte de softwares subjacentes ou outras propriedades intelectuais usadas para prestar o Serviço ou aplicativos da EasyBalads, ou obter informações do Serviço ou de aplicativos da EasyBalads usando métodos que não sejam expressamente permitidos pela EasyBalads.\n\n" +
                "Solicitar ou tentar solicitar Informações de Login ou quaisquer outras credenciais de Login, ou informações pessoais de outros usuários do Serviço ou de aplicativos da EasyBalads.\n\n" +
                "Coletar ou publicar informações privadas de alguém, incluindo dados de identificação pessoal (seja em forma de texto, imagem ou vídeo), documentos de identificação ou informações financeiras por meio do Serviço.\n\n" +
                "A EasyBalads se reserva o direito de determinar quais condutas considera violar as regras de uso ou que, de outra forma, não estejam de acordo ou do espírito destes Termos de Uso ou do próprio Serviço. A EasyBalads se reserva o direito de tomar medidas, como resultado de tais condutas, o que pode incluir o encerramento de sua Conta e a proibição do seu uso do Serviço, no todo ou em parte.\n\n" +
                " \n\n");

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
        dialog.setMessage("Cadastrando ...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        lat = null;
        lng = null;
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
            titulo2 = titulo.getText().toString();
            data2 = data.getText().toString();
            hora2 = hora.getText().toString();
            descricao2 = descricao.getText().toString();
            cp2 = cp.getText().toString();
            end2 = end.getText().toString();
            bairro2 = bairro.getText().toString();
            cidade2 = cidade.getText().toString();
            estado2 = estado.getText().toString();
            valor2 = valor.getText().toString();
            new GetCoordinates().execute(cod.replace(" ", "+"));
        }
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
                cadastrarBD();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public void cadastrarBD(){
        DatabaseReference mDatabaseUser, mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("eventos");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        final DatabaseReference novoEvento = mDatabase.push();
        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                novoEvento.child("titulo").setValue(titulo2);
                novoEvento.child("data").setValue(data2);
                novoEvento.child("hora").setValue(hora2);
                novoEvento.child("descricao").setValue(descricao2);
                novoEvento.child("cep").setValue(cp2);
                novoEvento.child("endereco").setValue(end2);
                novoEvento.child("bairro").setValue(bairro2);
                novoEvento.child("cidade").setValue(cidade2);
                novoEvento.child("estado").setValue(estado2);
                novoEvento.child("valor").setValue(valor2);
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

}