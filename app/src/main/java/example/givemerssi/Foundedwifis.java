package example.givemerssi;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class Foundedwifis extends ActionBarActivity {

    public WifiManager wifi;
    ImageView stanza1;
    ImageView stanza2;
    ImageView ospiti;
    ImageView lavanderia;

    final fingerprint f1= new fingerprint();
    final fingerprint f2= new fingerprint();
    final fingerprint f3= new fingerprint();
    final fingerprint f4= new fingerprint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundedwifis);



        f1.setNomestanza("Stanza 1");
        f2.setNomestanza("Stanza 2");
        f3.setNomestanza("Ospiti");
        f4.setNomestanza("Lavanderia");

        for (int i=0;i<3;i++){
            f1.r[i]=new rete();
            f2.r[i]=new rete();
            f3.r[i]=new rete();
            f4.r[i]=new rete();
        }

        f1.r[0].setSSID("Stanza1");
        f1.r[0].setRSSI(-47);
        f1.r[1].setSSID("Telecom-45680457");
        f1.r[1].setRSSI(-82);
        f1.r[2].setSSID("Lavanderia");
        f1.r[2].setRSSI(-86);


        f2.r[0].setSSID("Stanza1");
        f2.r[0].setRSSI(-65);
        f2.r[1].setSSID("Telecom-45680457");
        f2.r[1].setRSSI(-79);
        f2.r[2].setSSID("Lavanderia");
        f2.r[2].setRSSI(-85);


        f3.r[0].setSSID("Stanza1");
        f3.r[0].setRSSI(-81);
        f3.r[1].setSSID("Telecom-45680457");
        f3.r[1].setRSSI(-35);
        f3.r[2].setSSID("Lavanderia");
        f3.r[2].setRSSI(-71);


        f4.r[0].setSSID("Stanza1");
        f4.r[0].setRSSI(-88);
        f4.r[1].setSSID("Telecom-45680457");
        f4.r[1].setRSSI(-67);
        f4.r[2].setSSID("Lavanderia");
        f4.r[2].setRSSI(-52);

        wifi=(WifiManager) getSystemService(Context.WIFI_SERVICE);
        for (int i=0;i<5;i++){

            wifi.startScan();
            try {
                Thread.sleep(3333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        List<ScanResult> results = wifi.getScanResults();


        if(results.size()<3){

            Toast.makeText(getApplicationContext(),"Errore, effettuare una nuova scansione", Toast.LENGTH_LONG).show();
            finish();


        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        stanza1=(ImageView) findViewById(R.id.stanza1);
        stanza2=(ImageView) findViewById(R.id.stanza2);
        ospiti=(ImageView) findViewById(R.id.ospiti);
        lavanderia=(ImageView) findViewById(R.id.lavanderia);



        List<ScanResult> results = wifi.getScanResults();;

        final rete[] retiscansione = new rete[results.size()];



        for(int i=0;i<results.size();i++){
            ScanResult sr=results.get(i);
            retiscansione[i]=new rete();
            retiscansione[i].setSSID(sr.SSID);
            retiscansione[i].setRSSI(sr.level);
        }


        final int distanza1=f1.distanza(retiscansione);
        final int distanza2=f2.distanza(retiscansione);
        final int distanza3=f3.distanza(retiscansione);
        final int distanza4=f4.distanza(retiscansione);

        double distanzamin = Math.min(Math.min(distanza1,distanza2),Math.min(distanza3,distanza4));

        if(distanzamin==distanza1){
            stanza2.setVisibility(View.INVISIBLE);
            ospiti.setVisibility(View.INVISIBLE);
            lavanderia.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Ti trovi nella stanza: "+f1.getNomestanza(), Toast.LENGTH_LONG).show();
        }
        if(distanzamin==distanza2){
            stanza1.setVisibility(View.INVISIBLE);
            ospiti.setVisibility(View.INVISIBLE);
            lavanderia.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(),"Ti trovi nella stanza: "+f2.getNomestanza(), Toast.LENGTH_LONG).show();
        }
        if(distanzamin==distanza3){
            stanza1.setVisibility(View.INVISIBLE);
            stanza2.setVisibility(View.INVISIBLE);
            lavanderia.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(),"Ti trovi nella stanza: "+f3.getNomestanza(), Toast.LENGTH_LONG).show();
        }
        if(distanzamin==distanza4){
            stanza1.setVisibility(View.INVISIBLE);
            stanza2.setVisibility(View.INVISIBLE);
            ospiti.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(),"Ti trovi nella stanza: "+f4.getNomestanza(), Toast.LENGTH_LONG).show();
        }




        ArrayList<String> nomireti = new ArrayList<>(results.size());
        for(int i=0;i<results.size();i++){
            nomireti.add(i,retiscansione[i].getSSID());

        }



        //Toast.makeText(getApplicationContext(), "NOME:"+st+" RSSI:"+s, Toast.LENGTH_LONG).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row,R.id.textView2,nomireti);
        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nome=retiscansione[position].getSSID();
                int level=retiscansione[position].getRSSI();
                String stanza = f1.getNomestanza();


                Toast.makeText(getApplicationContext(), "NOME:" + nome + " RSSI:" + level, Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foundedwifis, menu);
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
}
