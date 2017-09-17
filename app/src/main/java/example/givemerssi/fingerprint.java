package example.givemerssi;

import android.util.Log;

/**
 * Created by giulioverazzo on 16/06/15.
 */

public class fingerprint {

    String tag="tag";
    String nomestanza;
    rete[] r= new rete[3];
    public int a=0,b=0,c=0;



    public void setNomestanza(String nome){

        this.nomestanza=nome;
    }

    public String getNomestanza(){
        return nomestanza;
    }

    public int distanza(rete[] rs){


       int distanzacalcolata=0;


        for(int i=0;i<rs.length ;i++) {
            if (rs[i].getSSID().equals("Stanza1")) {a=rs[i].getRSSI();}
            if (rs[i].getSSID().equals("Telecom-45680457")) {b=rs[i].getRSSI();}
            if (rs[i].getSSID().equals("Lavanderia")) {c=rs[i].getRSSI();}
        }

        distanzacalcolata=(int)Math.sqrt(Math.pow(r[0].getRSSI()-a,2)+Math.pow(r[1].getRSSI()-b,2)+Math.pow(r[2].getRSSI()-c,2));

        return distanzacalcolata;
    }
}
