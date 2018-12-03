package com.example.root.buscavagaapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.root.buscavagaapp.dao.PreferenciasUsuarioDAO;
import com.example.root.buscavagaapp.modelo.DadosEmpresas;
import com.example.root.buscavagaapp.WebService.WebServiceUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    public static GoogleMap mMap;
    private ProgressDialog progressao;

    private CameraPosition mCameraPosition;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //Localização padrão
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);

    private static final String TAG = "LOG";
//    private static final int REQUEST_PERMISSIONS_CODE = 128;
    private static final int DEFAULT_ZOOM = 13;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private boolean mLocationPermissionGranted;

    private Location mLastKnownLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        getMapAsync(this);

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if(mMap != null){
            bundle.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            bundle.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(bundle);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();

        ExecutaConexao executa = new ExecutaConexao();
        executa.execute();

    }

    private void getLocationPermission(){
        if(ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted = true;
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Localização atual não encontrada! Usando localização padrão...");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e(TAG, e.getMessage());
        }
    }

    private void updateLocationUI(){
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if(mMap == null){
            return;
        }
        try {
            if(mLocationPermissionGranted){
                readMyCurrentCoordinates();
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e){
            Log.e(TAG, e.getMessage());
        }
    }

    private void zoomMapToMyLocation(Location location) {
        LatLng lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, DEFAULT_ZOOM));

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
                try {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } catch (SecurityException e){
                    Log.e(TAG, e.getMessage());
                }

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

            if (isGPSHabilitado) {
                if (location == null) {
                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
                        Log.d(TAG, "GPS Habilitado");
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    } catch (SecurityException e){
                        Log.e(TAG, e.getMessage());
                    }
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
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
            updateLocationUI();
        }
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

                PreferenciasUsuarioDAO preferenciasDao = new PreferenciasUsuarioDAO(getActivity());
                preferenciasDao.retornaPreferencias();

                String precosCarro = "Carro:\n";
                String precosMoto = "Moto:\n";

                InfoWindowData info = new InfoWindowData();
                info.setNome(dadosEmpresa.getNome_empresa());
                info.setTelefones("Fixo: " + dadosEmpresa.getTelefone_fixo() + "\nCelular: " + dadosEmpresa.getTelefone_cel());
                info.setEndereco("Endereço: " + dadosEmpresa.getEndereco());

                //valida conforme as preferencias do usuário
                if(preferenciasDao.retornaPreferencias().get(0).getCarro().equals("SIM")){
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_meiahora().equals("SIM")){
                        if(dadosEmpresa.getValor_meiahora_c() != null && dadosEmpresa.getValor_meiahora_c() > 0) {
                            precosCarro += "Valor Meia Hora: R$ " + String.format("%.2f", dadosEmpresa.getValor_meiahora_c()) + "\n";
                        } else {
                            precosCarro += "Valor Meia Hora: não informado pela empresa!\n";
                        }
                    }
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_umahora().equals("SIM")){
                        if(dadosEmpresa.getValor_umahora_c() != null && dadosEmpresa.getValor_umahora_c() > 0) {
                            precosCarro += "Valor Uma Hora:  R$ " + String.format("%.2f", dadosEmpresa.getValor_umahora_c()) + "\n";
                        } else {
                            precosCarro += "Valor Uma Hora: não informado pela empresa!\n";
                        }
                    }
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_diaria().equals("SIM")){
                        if(dadosEmpresa.getValor_diaria_c() != null && dadosEmpresa.getValor_diaria_c() > 0) {
                            precosCarro += "Valor Diária: R$ " + String.format("%.2f", dadosEmpresa.getValor_diaria_c()) + "\n";
                        } else {
                            precosCarro += "Valor Diária: não informado pela empresa!\n";
                        }
                    }
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_semanal().equals("SIM")){
                        if(dadosEmpresa.getValor_semana_c() != null && dadosEmpresa.getValor_semana_c() > 0) {
                            precosCarro += "Valor Semanal:   R$ " + String.format("%.2f", dadosEmpresa.getValor_semana_c()) + "\n";
                        } else {
                            precosCarro += "Valor Semanal: não informado pela empresa!\n";
                        }
                    }
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_mensal().equals("SIM")){
                        if(dadosEmpresa.getValor_mes_c() != null && dadosEmpresa.getValor_mes_c() > 0) {
                            precosCarro += "Valor Mensal: R$ " + String.format("%.2f", dadosEmpresa.getValor_mes_c()) + "\n";
                        } else {
                            precosCarro += "Valor Mensal: não informado pela empresa!\n";
                        }
                    }

                    if(preferenciasDao.retornaPreferencias().get(0).getCobertas().equals("SIM")){
                        if(dadosEmpresa.getQtd_cobertas_c() > 0) {
                            precosCarro += "Vagas cobertas: " + dadosEmpresa.getQtd_cobertas_c() + "\n";
                        } else {
                            precosCarro += "Vagas cobertas: Indisponível\n";
                        }
                    }

                    if(preferenciasDao.retornaPreferencias().get(0).getDescobertas().equals("SIM")){
                        if(dadosEmpresa.getQtd_descobertas_c() > 0) {
                            precosCarro += "Vagas descobertas: " + dadosEmpresa.getQtd_descobertas_c() + "\n";
                        } else {
                            precosCarro += "Vagas descobertas: Indisponível\n";
                        }
                    }

                    if(dadosEmpresa.isCarro()) {
                        info.setPrecosCarro(precosCarro);
                    } else {
                        info.setPrecosCarro("Não foram informados valores\npara esse estacionamento ainda :(");
                    }
                } else if (preferenciasDao.retornaPreferencias().get(0).getCarro().equals("NÃO")){
                    precosCarro = "Você optou por não exibir valores para carros.\nCaso desejar habilite essa opção na tela de preferências";
                    info.setPrecosCarro(precosCarro);
                }

                if(preferenciasDao.retornaPreferencias().get(0).getMoto().equals("SIM")){
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_meiahora().equals("SIM")){
                        if(dadosEmpresa.getValor_meiahora_m() != null && dadosEmpresa.getValor_meiahora_m() > 0) {
                            precosMoto += "Valor Meia Hora: R$ " + String.format("%.2f", dadosEmpresa.getValor_meiahora_m()) + "\n";
                        } else {
                            precosMoto += "Valor Meia Hora: não informado pela empresa!\n";
                        }
                    }
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_umahora().equals("SIM")){
                        if(dadosEmpresa.getValor_umahora_m() != null && dadosEmpresa.getValor_umahora_m() > 0) {
                            precosMoto += "Valor Uma Hora: R$ " + String.format("%.2f", dadosEmpresa.getValor_umahora_m()) + "\n";
                        } else {
                            precosMoto += "Valor Uma Hora: não informado pela empresa!\n";
                        }
                    }
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_diaria().equals("SIM")){
                        if(dadosEmpresa.getValor_diaria_m() != null && dadosEmpresa.getValor_diaria_m() > 0) {
                            precosMoto += "Valor Diária: R$ " + String.format("%.2f", dadosEmpresa.getValor_diaria_m()) + "\n";
                        } else {
                            precosMoto += "Valor Diária: não informado pela empresa!\n";
                        }
                    }
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_semanal().equals("SIM")){
                        if(dadosEmpresa.getValor_semana_m() != null && dadosEmpresa.getValor_semana_m() > 0) {
                            precosMoto += "Valor Semanal: R$ " + String.format("%.2f", dadosEmpresa.getValor_semana_m()) + "\n";
                        } else {
                            precosMoto += "Valor Semanal: não informado pela empresa!\n";
                        }
                    }
                    if(preferenciasDao.retornaPreferencias().get(0).getValor_mensal().equals("SIM")){
                        if(dadosEmpresa.getValor_mes_m() != null && dadosEmpresa.getValor_mes_m() > 0) {
                            precosMoto += "Valor Mensal: R$ " + String.format("%.2f", dadosEmpresa.getValor_mes_m()) + "\n";
                        } else {
                            precosMoto += "Valor Mensal: não informado pela empresa!\n";
                        }
                    }

                    if(preferenciasDao.retornaPreferencias().get(0).getCobertas().equals("SIM")){
                        if(dadosEmpresa.getQtd_cobertas_m() > 0) {
                            precosMoto += "Vagas cobertas: " + dadosEmpresa.getQtd_cobertas_m() + "\n";
                        } else {
                            precosMoto += "Vagas cobertas: Indisponível\n";
                        }
                    }

                    if(preferenciasDao.retornaPreferencias().get(0).getDescobertas().equals("SIM")){
                        if(dadosEmpresa.getQtd_descobertas_m() > 0) {
                            precosMoto += "Vagas descobertas: " + dadosEmpresa.getQtd_descobertas_m() + "\n";
                        } else {
                            precosMoto += "Vagas descobertas: Indisponível\n";
                        }
                    }

                    if(dadosEmpresa.isMoto()){
                        info.setPrecosMoto(precosMoto);
                    } else {
                        info.setPrecosMoto("Não foram informados valores\npara esse estacionamento ainda :(");
                    }
                } else if (preferenciasDao.retornaPreferencias().get(0).getMoto().equals("NÃO")){
                    precosMoto = "Você optou por não exibir valores para motos.\nCaso desejar habilite essa opção na tela de preferências";
                    info.setPrecosMoto(precosMoto);
                }

                CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
                mMap.setInfoWindowAdapter(customInfoWindow);

                markerOptions.title(dadosEmpresa.getNome_empresa()).snippet(precosCarro + "\n" + precosMoto);

                //pega hora atual do celular
                String horaAtual = new SimpleDateFormat("HH:mm").format(new Date().getTime());

                //pega dia da semana atual
                Calendar c = Calendar.getInstance();
                c.setTime(new Date(System.currentTimeMillis()));
                int diaSemana = c.get(c.DAY_OF_WEEK);

                //checa o dia da semana da hora atual
                if(diaSemana == Calendar.SUNDAY){
                    try {
                        if(new SimpleDateFormat("HH:mm").parse(dadosEmpresa.getHr_dom_fer()).before(new SimpleDateFormat("HH:mm").parse(horaAtual))
                                && new SimpleDateFormat("HH:mm").parse(dadosEmpresa.getHr_dom_fer_fim()).after(new SimpleDateFormat("HH:mm").parse(horaAtual))){
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapa_green));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (diaSemana == Calendar.SATURDAY){
                    try {
                        if(new SimpleDateFormat("HH:mm").parse(dadosEmpresa.getHr_dom_fer()).before(new SimpleDateFormat("HH:mm").parse(horaAtual))
                                && new SimpleDateFormat("HH:mm").parse(dadosEmpresa.getHr_dom_fer_fim()).after(new SimpleDateFormat("HH:mm").parse(horaAtual))){
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapa_green));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if(new SimpleDateFormat("HH:mm").parse(dadosEmpresa.getHr_dom_fer()).before(new SimpleDateFormat("HH:mm").parse(horaAtual))
                                && new SimpleDateFormat("HH:mm").parse(dadosEmpresa.getHr_dom_fer_fim()).after(new SimpleDateFormat("HH:mm").parse(horaAtual))){
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapa_green));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Marker m = mMap.addMarker(markerOptions);
                m.setTag(info);

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        builder.setTitle(marker.getTitle()).setMessage(marker.getSnippet()).show();

                    }
                });
            }
            progressao.dismiss();
        }
    }
}
