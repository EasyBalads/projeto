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

        opcoes.add("Termos de Uso");
        opcoes.add("Ajuda");
        opcoes.add("Sobre Nós");

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcoes);
        lvOpcoes.setAdapter(adaptador);lvOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        cliqueTermos(view);
                        break;
                    case 1:
                        cliqueAjuda(view);
                        break;
                    case 2:
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

    public void cliqueTermos(View view){
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
        builder.setMessage("\t\t\t\t"+msg+"\n\n"+"Elias Dourado"+"\n"+"Gustavo Soares"+"\n"+"Leonardo dos Anjos"+"\n"+"Vitor Oliveira"+"\n\n"+"\t\t\t\t\t"+msg2+"\n\n"+"Professor Aristóteles");

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
