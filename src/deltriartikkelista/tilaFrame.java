/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deltriartikkelista;

import java.util.ArrayList;

/**
 *
 * @author janne
 */
public class tilaFrame {
    
    private ArrayList<Piste> kaikkiPisteet = new ArrayList<Piste>();
    private ArrayList<Jana> nonD = new ArrayList<Jana>();
    private ArrayList<Jana> D = new ArrayList<Jana>();
    private ArrayList<Piste> sisaPisteet = new ArrayList<Piste>();
    public ArrayList<Piste[]> kolmiot = new ArrayList<Piste[]>();
    private Jana kohde = null;
    
    private ArrayList<Piste> ehdokkaat = new ArrayList<Piste>();
    
    private Piste valittu = null;
    
    public tilaFrame(ArrayList<Piste> gkaikkiP, ArrayList<Jana> gnd,ArrayList<Jana> gd , ArrayList<Piste> gsPisteet, Jana gkohde)
    {
        //kopioidaan kaikki pisteet
        for(Piste p: gkaikkiP)
        {
            kaikkiPisteet.add(p.copy());
        }
        //Kopioidaan pisteiden linkit
        for(int i = 0; i < gkaikkiP.size(); i++)
        {
            for(Jana j: gkaikkiP.get(i).linkit)
            {
                //jos piste on janan alku, luodaan linkki
                if(j.b.equals(gkaikkiP.get(i))) continue;
                kaikkiPisteet.get(i).makeLinkki(kaikkiPisteet.get(gkaikkiP.indexOf(j.b)));
            }
        }
        //Kopioidaan non-delanay janat
        for(Jana j: gnd)
        {
            nonD.add(new Jana(kaikkiPisteet.get(gkaikkiP.indexOf(j.a)),kaikkiPisteet.get(gkaikkiP.indexOf(j.b))));
        }
        //kopioidaan delanay janat
        for(Jana j: gd)
        {
            D.add(new Jana(kaikkiPisteet.get(gkaikkiP.indexOf(j.a)),kaikkiPisteet.get(gkaikkiP.indexOf(j.b))));
        }
        //kopioidaan kohde
        kohde = new Jana(kaikkiPisteet.get(gkaikkiP.indexOf(gkohde.a)),kaikkiPisteet.get(gkaikkiP.indexOf(gkohde.b)));
        //kopioidaan sisäpisteet
        for(Piste p: gsPisteet)
        {
            sisaPisteet.add(kaikkiPisteet.get(gkaikkiP.indexOf(p)));
        }
        //etsitään kolmiot
        for(Piste p: kaikkiPisteet)
        {
            kolmiot.addAll(p.getKolmiot());
        }
    }
    
    public void addEhdokkaat(ArrayList<Piste> gEhdo)
    {
        for(Piste p: gEhdo)
        {
            for(Piste pp: kaikkiPisteet)
            {
                if(!p.equals(pp))continue;
                ehdokkaat.add(pp);
                break;
            }
        }
    }
    
    public void addValittu(Piste p)
    {
        if(p == null) return;
        for(Piste pp: kaikkiPisteet)
            {
                if(!p.equals(pp))continue;
                valittu = pp;
                break;
            }
    }
    
    public void scale(float sf)
    {
        for(Piste p: kaikkiPisteet)
        {
            p.scale(sf);
        }
    }
    
    public ArrayList<Piste> getBase()
    {
        return kaikkiPisteet;
    }
    
    public ArrayList<Jana> getND()
    {
        return nonD;
    }
    
    public ArrayList<Jana> getD()
    {
        return D;
    }
    
    public ArrayList<Piste> getReuna()
    {
        ArrayList<Piste> pal = new ArrayList<>();
        for(Jana j: nonD)
        {
            pal.add(j.a);
        }
        for(Jana j: D)
        {
            pal.add(j.a);
        }
        //Oletus: ei voi tulla duplikaatteja koska jana on joko D tai nonD
        return pal;
    }
    
    public ArrayList<Piste> getSisa()
    {
        return sisaPisteet;
    }
    
    public ArrayList<Piste> getEhdokas()
    {
        return ehdokkaat;
    }
    
    public Jana getKohde()
    {
        return kohde;
    }
    
    public Piste getValittu()
    {
        return valittu;
    }
}
