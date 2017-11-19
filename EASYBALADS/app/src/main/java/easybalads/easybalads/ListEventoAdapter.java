package easybalads.easybalads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by L.A.S.S on 12/11/2017.
 */

public class ListEventoAdapter extends ArrayAdapter<Eventos>{
    private Context context;
    private List<Eventos> Evento = null;

    public ListEventoAdapter(Context context,  List<Eventos> Evento) {
        super(context,0, Evento);
        this.Evento = Evento;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Eventos evento = Evento.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_list_eventos, null);

        TextView txtViewTitulo = (TextView) view.findViewById(R.id.txtTitulo);
        txtViewTitulo.setText(evento.getTitulo());

        TextView txtViewData = (TextView) view.findViewById(R.id.txtData);
        txtViewData.setText(evento.getDt());

        TextView txtViewHora = (TextView) view.findViewById(R.id.txtHora);
        txtViewHora.setText(evento.getHora());

        TextView txtViewPart = (TextView) view.findViewById(R.id.txtParticipantes);
        txtViewPart.setText(evento.getParticipantes());

        return view;
    }
}
