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
    public ArrayList<Jana> linkit = new ArrayList<>();
    
    public Piste(float gx, float gy)
    {
        x= gx; y = gy;
        id = Float.NaN;
    }
    
    public Piste(float gx, float gy, float gid)
    {
        x= gx; y = gy;
        id = gid;
    }
    
    //luodaan linkki tämän ja toisen pisteen välille
    //palautetaan null jos jana on jo olemassa
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
    
    //lisätään uusi jana linkiksi. Ei tarkisteta onko se jo olemassa koska oletus on että tätä kutsutaan vain makeLinkki jälkeen
    //tarkistuksen lisäys sinäänsä ei ole hankala, mutta olisi turhaan aikaa vievää. Toisn aikavaativuus ei kasvaisi kuin vakio kertoimen verran.
    public void addLinkki(Jana given)
    {
        linkit.add(given);
    }
    
    /*
    public Jana unlink(Piste o)
    {
        Jana proto = new Jana(this,o);
        Jana pal = null;
        for(int i = 0; i < linkit.size(); i++)
        {
            if(!linkit.get(i).compare(proto))continue;
            pal = linkit.remove(i);
            o.removeLinkki(pal);
            break;
        }
        return pal;
    }
    
    public void removeLinkki(Jana j)
    {
        linkit.remove(j);
    }
    */
    
    public void shift(int xa, int ya)
    {
        x += xa;
        y += ya;
    }
    
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
    
    public Piste copy()
    {
        return new Piste(x,y);
    }
    
    public boolean equals(Piste o)
    {
        return o.x == x && o.y == y;
    }
    
    //true jos annettu piste on järjestyksessä tämän jälkeen
    public boolean compare(Piste o)
    {
        if(o.x<x) return false;
        if(o.x == x && o.y < y) return false;
        return true;
    }
    
    //etsitään kolmiot jossa tämä piste on kanta (arvoltaan pienin piste)
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
