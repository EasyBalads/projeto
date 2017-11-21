package easybalads.easybalads;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.data;

public class Provider_Fragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {
    FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "Provider_Fragment";

    private GoogleMap mMap;

    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
            }
        };
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {


            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            Toast.makeText(getActivity(), "Provider: " + provider, Toast.LENGTH_SHORT);

            mMap = googleMap;
            mMap.setOnMapClickListener(this);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);

        } catch (SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }

        if (ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
            startActivity(new Intent(getContext(), Inicial_Usuario.class));
            this.getActivity().finish();
        }

        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            int cont =0;
            @Override
            public void onMyLocationChange (Location location) {
                LatLng loc = new LatLng (location.getLatitude(), location.getLongitude());
                if(cont == 0) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    cont = 1;
                }
            }
        };
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("eventos");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String lat,lng,titulo;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    lat = postSnapshot.child("latitude").getValue().toString();
                    lng = postSnapshot.child("longitude").getValue().toString();
                    titulo = postSnapshot.child("titulo").getValue().toString();
                    addMakers(lat,lng,titulo);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(Marker arg0) {
                String lat = String.valueOf(arg0.getPosition().latitude);
                String lng = String.valueOf(arg0.getPosition().longitude);
                cliqueMark(lat,lng);

                return true;
            }
        });

    }

    public void addMakers(String lat,String lng,String titulo){
        LatLng local = new LatLng(Double.valueOf(lat.toString()),Double.valueOf(lng.toString()));
        MarkerOptions marker = new MarkerOptions();
        marker.position(local);
        marker.title(titulo.toString());
        mMap.addMarker(marker);

    }


    public void cliqueMark(String lat, String lng){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("eventos");
        final String latitude = lat.toString(),longitude = lng.toString();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("latitude").getValue().toString().equals(latitude) && postSnapshot.child("longitude").getValue().toString().equals(longitude)) {
                        String titulo = postSnapshot.child("titulo").getValue().toString();
                        String organizador = postSnapshot.child("organizador").getValue().toString();
                        String descriçao = postSnapshot.child("descricao").getValue().toString();
                        String data = postSnapshot.child("data").getValue().toString();
                        String endereco = postSnapshot.child("endereco").getValue().toString()+","+postSnapshot.child("bairro").getValue().toString()+","+
                                postSnapshot.child("cidade").getValue().toString()+","+postSnapshot.child("estado").getValue().toString();
                        String hora = postSnapshot.child("hora").getValue().toString();
                        String valor = postSnapshot.child("valor").getValue().toString();
                        String key = postSnapshot.getKey().toString().trim();
                        int participantes = Integer.parseInt(postSnapshot.child("participantes").getValue().toString());
                        exibirAlert(titulo,descriçao,data,hora,valor,participantes,key,organizador,endereco);
                        break;
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void exibirAlert(final String titulo, final String descricao, final String d, final String h, final String valor, final int participantes, final String key, final String organizador, final String endereco){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo.toString());
        String msg = "Descrição: ";
        String msg2 = "Data: ";
        String msg3 = "Hora: ";
        String msg4 = "Valor: ";
        String msg5 = "Participantes: ";
        String msg6 = "Organizador: ";


        if (user != null) {
            builder.setMessage(msg+descricao.toString()+"\n"+msg2+d.toString()+"\n"+msg3+h.toString()+"\n"+msg5+participantes+"\n"+msg6+organizador.toString()+"\n"+msg4+valor.toString() );
            builder.setNegativeButton("Detalhes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    cliqueDetalhes(titulo,descricao,d,h,valor,organizador,endereco,participantes);
                }
            });

            builder.setPositiveButton("Eu Vou", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    euVou(key,participantes);
                }
            });
        } else {
            builder.setMessage(msg+descricao.toString()+"\n"+msg5+participantes+"\n"+msg6+organizador.toString());
            builder.setNegativeButton("Detalhes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    cadastre();
                }
            });

            builder.setPositiveButton("Eu Vou", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    cadastre();
                }
            });
        }
        builder.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });




        AlertDialog  alerta = builder.create();
        alerta.show();
        Button negativeButton = alerta.getButton(AlertDialog.BUTTON_NEGATIVE        );
        LinearLayout parent = (LinearLayout) negativeButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
    }

    public void cliqueDetalhes(String titulo,String descricao,String d,String h,String valor,String organizador,String endereco,int participantes){
        Intent i = new Intent(getContext(), Detalhes_de_Evento.class);
        i.putExtra("msg", titulo);
        i.putExtra("msg2", descricao);
        i.putExtra("msg3", d);
        i.putExtra("msg4", h);
        i.putExtra("msg5", valor);
        i.putExtra("msg6",organizador);
        i.putExtra("msg7",endereco);
        i.putExtra("msg8",String.valueOf(participantes));
        startActivity(i);
        //startActivity(new Intent(getContext(), Detalhes_de_Evento.class));
    }

    public void euVou(final String key, final int participantes){

        FirebaseDatabase d1 = FirebaseDatabase.getInstance();
        DatabaseReference m1 = d1.getReference("euvou");
        m1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int op = 0;
                String ukey = null;
                int posicao = 0;
                int existe = 0;
                if(dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if(op == 1){
                            euVouBanco(op,key,participantes,ukey,posicao,existe);
                            break;
                        }
                        if(postSnapshot.child("idbalada").getValue().toString().equals(key.toString())) {
                            existe = 1;
                            ukey = postSnapshot.getKey().toString().trim();
                            for(int i = 1;i < participantes+1;i++){
                                if(postSnapshot.child(String.valueOf(i)).getValue().toString().equals(user.getUid())) {
                                    posicao = i;
                                    op = 1;
                                    break;
                                }
                            }
                        }
                    }
                    euVouBanco(op, key, participantes, ukey, posicao, existe);
                }else {
                    euVouBanco(op, key, participantes, ukey, posicao, existe);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void euVouBanco(int op, final String key, final int participantes, final String ukey, final int posicao, int existe) {
        if (op == 1) {
            int pt2 = participantes - 1;

            if(pt2 > 0 && posicao < participantes){
                FirebaseDatabase d7 = FirebaseDatabase.getInstance();
                DatabaseReference m7 = d7.getReference("euvou");
                m7.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(int i = posicao;i < participantes;i++){
                            FirebaseDatabase d8 = FirebaseDatabase.getInstance();
                            DatabaseReference m8 = d8.getReference("euvou").child(ukey);
                            m8.child(String.valueOf(i)).setValue(dataSnapshot.child(ukey).child(String.valueOf(i+1)).getValue().toString());
                            m8.child(String.valueOf(i+1)).removeValue();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else{
                FirebaseDatabase d3 = FirebaseDatabase.getInstance();
                DatabaseReference m3 = d3.getReference("euvou").child(ukey);
                m3.child(String.valueOf(posicao)).removeValue();
            }

            FirebaseDatabase d2 = FirebaseDatabase.getInstance();
            DatabaseReference m2 = d2.getReference("eventos").child(key.toString());
            m2.child("participantes").setValue(pt2);
            Toast.makeText(getActivity(), "Balada desmarcada!", Toast.LENGTH_SHORT).show();
        }else{
            final int pt = participantes + 1;
            if (participantes == 0) {
                if(existe == 0) {
                    DatabaseReference mDatabaseUser, mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("euvou");
                    mDatabaseUser = FirebaseDatabase.getInstance().getReference().child(user.getUid());
                    final DatabaseReference novoVou = mDatabase.push();
                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            novoVou.child("idbalada").setValue(key.toString());
                            novoVou.child(String.valueOf(pt)).setValue(user.getUid());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    FirebaseDatabase d2 = FirebaseDatabase.getInstance();
                    DatabaseReference m2 = d2.getReference("eventos").child(key.toString());
                    m2.child("participantes").setValue(pt);
                }else{
                    FirebaseDatabase d3 = FirebaseDatabase.getInstance();
                    DatabaseReference m3 = d3.getReference("euvou").child(ukey);
                    m3.child(String.valueOf(pt)).setValue(user.getUid());

                    FirebaseDatabase d2 = FirebaseDatabase.getInstance();
                    DatabaseReference m2 = d2.getReference("eventos").child(key.toString());
                    m2.child("participantes").setValue(pt);
                }
            }else{
                FirebaseDatabase d3 = FirebaseDatabase.getInstance();
                DatabaseReference m3 = d3.getReference("euvou").child(ukey);
                m3.child(String.valueOf(pt)).setValue(user.getUid());

                FirebaseDatabase d2 = FirebaseDatabase.getInstance();
                DatabaseReference m2 = d2.getReference("eventos").child(key.toString());
                m2.child("participantes").setValue(pt);
            }
            Toast.makeText(getActivity(), "Balada marcada!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cadastre(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cadastre-se");
        builder.setMessage("Para acessar essa opção precisa Logar/Cadastrar!");

        builder.setPositiveButton("Login/Cadastrar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                cadastro();
            }
        });
        builder.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
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
    public void cadastro(){
        startActivity(new Intent(getContext(), Login.class));
        this.getActivity().finish();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //Toast.makeText(getActivity(), latLng.toString(), Toast.LENGTH_SHORT).show();
    }
}
