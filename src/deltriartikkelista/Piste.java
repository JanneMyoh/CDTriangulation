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
public class Piste {
    
    float x,y,id;

    /**
     *Kaikki janat jossa tämä piste on osallisena
     */
    public ArrayList<Jana> linkit = new ArrayList<>();
    
    /**
     *Luodaan piste annettuun koordinaattiin
     * @param gx pisteen x-koordinaatti
     * @param gy pisteen y-koordinaatti
     */
    public Piste(float gx, float gy)
    {
        x= gx; y = gy;
        id = Float.NaN;
    }
    
    /**
     *Luodaan piste annettuun koordinaattiin annetulla id:llä
     * @param gx pisteen x-koordinaatti
     * @param gy pisteen y-koordinaatti
     * @param gid pisteen id
     */
    public Piste(float gx, float gy, float gid)
    {
        x= gx; y = gy;
        id = gid;
    }
    
    //

    /**
     * luodaan linkki tämän ja toisen pisteen välille
     * palautetaan null jos jana on jo olemassa
     * @param otherP toinen piste
     * @return muodostettu jana. Null jos jana oli jo olemassa
     */
        public Jana makeLinkki(Piste otherP)
    {
        Jana newJana = new Jana(this,otherP);
        //tarkistetaan onko linkki jo olemassa
        for(Jana j : linkit)
        {
            if(j.compare(newJana))
            {
                return null; //jana on jo olemassa. Lopetetaan
            }
        }
        //janaa ei ollut olemassa. Lisätään se linkkeihin ja imloitetaan tästä myös toiselle pisteelle
        linkit.add(newJana);
        otherP.addLinkki(newJana);
        return newJana;
    }
    
    //

    /**
     * lisätään uusi jana linkiksi. Ei tarkisteta onko se jo olemassa koska oletus on että tätä kutsutaan vain makeLinkki jälkeen
     * tarkistuksen lisäys sinäänsä ei ole hankala, mutta olisi turhaan aikaa vievää. Toisn aikavaativuus ei kasvaisi kuin vakio kertoimen verran.
     * @param given luotu linkki
     */
        public void addLinkki(Jana given)
    {
        linkit.add(given);
    }

    /**
     *Siirretään pistettä. Visualisointia varten. Mahdollisia janojen pituuksia ei päivitetä
     * @param xa x mutos
     * @param ya y muutos
     */
    public void shift(int xa, int ya)
    {
        x += xa;
        y += ya;
    }
    
    /**
     *siirretään pistettä kertoimen avulla
     * @param kerroin
     */
    public void scale(float kerroin)
    {
        x = x*kerroin;
        y = y*kerroin;
    }
    
    @Override
    public String toString()
    {
        return "Piste " + x + "," + y;
    }
    
    /**
     *Palauttaa kopion pisteestä. ID ei kopioidu
     * @return
     */
    public Piste copy()
    {
        return new Piste(x,y);
    }
    
    /**
     * Verrataan annettua pistettä tähän
     * @param o toinen piste
     * @return oliko annettu piste samassa koordinaatissa
     */
    public boolean equals(Piste o)
    {
        return o.x == x && o.y == y;
    }
    

    /**
     * Katsotaan onko piste järjestyksessä tämän jälkeen
     * @param o verrattava piste
     * @return true jos annettu piste on järjestyksessä tämän jälkeen
     */
        public boolean compare(Piste o)
    {
        if(o.x<x) return false;
        if(o.x == x && o.y < y) return false;
        return true;
    }
    

    /**
     * etsitään kolmiot jossa tämä piste on kanta (arvoltaan pienin piste)
     * @return kaikki kolmiot jossa tämä piste on kantana
     */
        public ArrayList<Piste[]> getKolmiot()
    {
        ArrayList<Piste[]> pal = new ArrayList<>();
        for(Jana j: linkit)
        {
            Piste o = j.a;
            if(o.equals(this)) o = j.b;
            if(o.compare(this)) continue;
            //ei ihan paras tapa tehdä... ainakaan turvallisuuden kannalta
            for(Jana jj: o.linkit)
            {
                Piste oo = jj.a;
                if(oo.equals(o)) oo = jj.b;
                if(oo.compare(o))continue; // tämä jana ei muodosta kolmiota
                for(Jana jjj: oo.linkit)
                {
                    //katsotaan muodostuuko kolmio
                    if(jjj.a.equals(this) || jjj.b.equals(this)){pal.add(new Piste[]{this,o,oo});break;}
                }
                
            }
        }
        return pal;
    }
    
}
