package com.example.root.buscavagaapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.example.root.buscavagaapp.modelo.DadosEmpresas;
import com.example.root.buscavagaapp.WebService.WebServiceUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager lm;
    private Location location;
    private double longitude = 0.0;
    private double latitude = 0.0;
    private MaterialDialog mMaterialDialog;
    private ProgressDialog progressao;

    public static final String TAG = "LOG";
    public static final int REQUEST_PERMISSIONS_CODE = 128;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                callDialog("É preciso que a permissão ACESS_FINE_LOCATION esteja liberada para acesso.", new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
            }

        } else {
            readMyCurrentCoordinates();
        }

        mMap.setMyLocationEnabled(true);

        ExecutaConexao executa = new ExecutaConexao();
        executa.execute();

    }

    private void zoomMapToMyLocation(Location location) {
        LatLng lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 13));

    }

    //Pega as coordenadas do GPS e da Internet do aparelho e vai atualizando conforme o tempo
    private void readMyCurrentCoordinates() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSHabilitado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isInternetHabilitada = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location location = null;
        double latitude = 0.0;
        double longitude = 0.0;

        if (!isGPSHabilitado && !isInternetHabilitada) {
            Log.i(TAG, "Nenhum serviço de localização está habilitado para uso.");
        } else {
            if (isInternetHabilitada) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                        callDialog("É preciso que a permissão ACESS_FINE_LOCATION esteja liberada para acesso.", new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
                    }
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);
                Log.d(TAG, "Internet");
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

            if (isGPSHabilitado) {
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
                    Log.d(TAG, "GPS Habilitado");
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        }
        zoomMapToMyLocation(location);
        Log.i(TAG, "Latitude: "+latitude+" | Longitude: "+longitude);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "Lat: "+ location.getLatitude()+" | Long: "+ location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissoes, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE: {
                for (int i = 0; i < permissoes.length; i++) {
                    if (permissoes[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        readMyCurrentCoordinates();
                    }
                }
            }
            super.onRequestPermissionsResult(requestCode, permissoes, grantResults);
        }
    }

    //Alert solicitando permissão
    private void callDialog(String mensagem, final String[] permissoes){
        mMaterialDialog = new MaterialDialog(getActivity()).setTitle("Permissão").setMessage(mensagem).setPositiveButton("Ok", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), permissoes, REQUEST_PERMISSIONS_CODE);
                mMaterialDialog.dismiss();
            }
        }).setNegativeButton("Cancelar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();
    }

    private class ExecutaConexao extends AsyncTask<Void, Void, ArrayList<DadosEmpresas>> {
        @Override
        protected void onPreExecute() {
            progressao = ProgressDialog.show(getActivity(), "Por favor aguarde", "Retornando dados do servidor");

        }

        @Override
        protected ArrayList<DadosEmpresas> doInBackground(Void... params) {
            WebServiceUtils utils = new WebServiceUtils();

            ArrayList<DadosEmpresas> dados = utils.retornaInformacoes("http://buscavagaapp.com.br:8000/conection.php");

            return dados;
        }

        @Override
        protected void onPostExecute(ArrayList<DadosEmpresas> dadosEmpresas) {
            super.onPostExecute(dadosEmpresas);

            for(final DadosEmpresas dadosEmpresa : dadosEmpresas){
                LatLng position = new LatLng(dadosEmpresa.getLatitude(), dadosEmpresa.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(position);

                InfoWindowData info = new InfoWindowData();
                info.setNome(dadosEmpresa.getNome_empresa());
                //alterar para pegar da empresa quando tiver ajustada a tabela
                info.setPrecoMeiaHora("Preço 1/2 Hora: " + "R$3,00");
                info.setPrecoUmaHora("Preço 1 Hora: " + "R$5,00");
                info.setPrecoDiaria("Preço Diária: " + "R$20,00");
                info.setPrecoSemanal("Preço Semanal: " + "R$60,00");
                info.setPrecoMensal("Preço Mensal: " + "R$150,00");

                CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
                mMap.setInfoWindowAdapter(customInfoWindow);

                Marker m = mMap.addMarker(markerOptions);
                m.setTag(info);
            }
            progressao.dismiss();
        }
    }
}
