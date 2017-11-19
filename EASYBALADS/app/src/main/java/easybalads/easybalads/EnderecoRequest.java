package easybalads.easybalads;

import android.os.AsyncTask;
import com.google.gson.Gson;
import java.lang.ref.WeakReference;


public class EnderecoRequest extends AsyncTask<Void, Void, Endereco> {

    private WeakReference<Inicial_Usuario> activity;

    public EnderecoRequest(Inicial_Usuario activity ){
        this.activity = new WeakReference<>( activity );
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activity.get() != null) {
            activity.get().lockFields(true);
        }
    }

    @Override
    protected Endereco doInBackground(Void... voids) {

        try{
            String jsonString = JsonRequest.request( activity.get().getUriRequest() );
            Gson gson = new Gson();

            return gson.fromJson(jsonString, Endereco.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Endereco endereco) {
        super.onPostExecute(endereco);

        if( activity.get() != null ){
            activity.get().lockFields(false);

            if( endereco != null ){
                activity.get().setDataViews(endereco);
            }
        }
    }
}
