package com.example.root.buscavagaapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.root.buscavagaapp.WebService.DadosEmpresas;
import com.example.root.buscavagaapp.WebService.WebServiceUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private ProgressDialog progressao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new ExecutaConexao().execute();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.container, new MapsFragment(), "MapsFragment");

        transaction.commitAllowingStateLoss();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment, String name){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, name);
        transaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.nav_mapa:
                showFragment(new MapsFragment(), "MapsFragment");
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ExecutaConexao extends AsyncTask<Void, Void, ArrayList<DadosEmpresas>> {
        @Override
        protected void onPreExecute() {
            progressao = ProgressDialog.show(MainActivity.this, "Por favor aguarde", "Retornando dados do servidor");

        }

        @Override
        protected ArrayList<DadosEmpresas> doInBackground(Void... params) {
            WebServiceUtils utils = new WebServiceUtils();

            ArrayList<DadosEmpresas> dados = utils.retornaInformacoes("35.227.77.109:8000/conection.php");

            return dados;
        }

        @Override
        protected void onPostExecute(ArrayList<DadosEmpresas> dadosEmpresas) {
            super.onPostExecute(dadosEmpresas);

            //Globals.list = new ArrayList<>();

            for(DadosEmpresas dadosEmpresa : dadosEmpresas){
                //Globals.list.add(dadosPrevisao);
                //lvLista.setAdapter(new ListAdapterTemps(MainActivity.this, Globals.list));

                Log.e("EMPRESAS", dadosEmpresa.getNome_empresa()+" - "+
                        dadosEmpresa.getTelefone_cel()+" - "+
                        dadosEmpresa.getTelefone_fixo()+" - "+
                        dadosEmpresa.getLatitude()+" - "+
                        dadosEmpresa.getLongitude());
            }

            progressao.dismiss();
        }
    }
}
