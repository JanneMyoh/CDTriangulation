/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deltriartikkelista;

/**
 *
 * @author janne
 */
public class Jana {
    
    public Piste a,b;
    public float pituus;
    
    public Jana(Piste ga, Piste gb)
    {
        a = ga;
        b = gb;
        pituus = (float)Math.sqrt((b.x-a.x)*(b.x-a.x) + (b.y-a.y)*(b.y-a.y));
    }
    
    public boolean compare(Jana other)
    {
        //katostaan onko jana suoraan tämä jana
        boolean sama = other.a == a && other.b == b;
        if(sama) return true;
        //katsotaan onko tämä annetun janan peilikuva
        return other.a == b && other.b == a;
    }
    
    //tämän ja toisen janan välinen piste tulo
    public float pisteTulo(Jana other)
    {
        return (b.x-a.x)*(other.b.x - other.a.x) + (b.y-a.y)*(other.b.y - other.a.y);
    }
    
    /*
    //palautetaan tämän janan normalisoitu versio
    public Jana normalized()
    {
        float newx = (b.x-a.x)/pituus + a.x;
        float newy = (b.y-a.y)/pituus + a.y;
        Piste p = new Piste(newx ,newy );
        return new Jana(a,p);
    }*/
    
    //tämän ja annetun janan risti tulo this x other
    //HUOM: koska ollaan 2d koordinaatistossa, on kyseinen vektori pystyssä, joten sitä ei voi järkevästi esittää jana oliolla. Palautetaan siis vain sen magnituudi.
    public float ristiTulo(Jana other)
    {
        float u1 = b.x - a.x;
        float u2 = b.y - a.y;
        float v1 = other.b.x - other.a.x;
        float v2 = other.b.y - other.a.y;
        return (u1*v2-u2*v1);
    }
    
    @Override
    public String toString()
    {
        return "Jana. Alku: " + a.x + "," + a.y + " loppu: " + b.x + ","+ b.y;
    }
    
    
    
}
