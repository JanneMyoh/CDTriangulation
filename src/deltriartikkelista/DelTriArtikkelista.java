/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deltriartikkelista;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author janne
 */
public class DelTriArtikkelista {
    
    static ArrayList<Piste> reunaPisteet = new ArrayList<>();
    static ArrayList<Piste> sisaPisteet = new ArrayList<>();
    static ArrayList<Jana> nonDJanat = new ArrayList<>();
    static ArrayList<Jana> DJanat = new ArrayList<>();
    static final float sisaSpacing = 3f;
    static final float amin = 0.5f;
    static float highestId = Float.MIN_VALUE;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        parseFile("testi.txt");
        /*
        //seuraavat kaksi askelta tulee tehdä manuaalisesti/jollakin muulla ohjelmalla
        //Luodaan alku pisteet, neliö näin aluksi
        //tällehän ei sitten atm kehity sisäpisteitä, koska avg janan pituus on koko homman korkeus. Solution, kierretään sitä hieman?
        reunaPisteet.add(new Piste(-10, 10));
        reunaPisteet.add(new Piste(-10, 5));
        reunaPisteet.add(new Piste(-10, 0));
        reunaPisteet.add(new Piste(-10, -5));
        reunaPisteet.add(new Piste(-5, -10));
        reunaPisteet.add(new Piste(0, -10));
        reunaPisteet.add(new Piste(5, -15));
        reunaPisteet.add(new Piste(10, -10));
        reunaPisteet.add(new Piste(15, -5));
        reunaPisteet.add(new Piste(15, 0));
        reunaPisteet.add(new Piste(15, 5));
        reunaPisteet.add(new Piste(15, 10));
        reunaPisteet.add(new Piste(10, 10));
        reunaPisteet.add(new Piste(5, 10));
        reunaPisteet.add(new Piste(5, 15));
        reunaPisteet.add(new Piste(0, 15));
        reunaPisteet.add(new Piste(-5, 15));
        reunaPisteet.add(new Piste(-5, 10));
        
        
        
               
        //linkitetään nämä yhteen.
        for(int i = 0; i < reunaPisteet.size(); i++)
        {
            nonDJanat.add(reunaPisteet.get(i).makeLinkki(reunaPisteet.get((i+1)%reunaPisteet.size())));
        }
        
        //luodaan sisään laatikko
        reunaPisteet.add(new Piste(5, 0));
        reunaPisteet.add(new Piste(5, 5));
        reunaPisteet.add(new Piste(10, 5));
        reunaPisteet.add(new Piste(10, 0));
        
        nonDJanat.add(reunaPisteet.get(reunaPisteet.size()-4).makeLinkki(reunaPisteet.get(reunaPisteet.size()-3)));
        nonDJanat.add(reunaPisteet.get(reunaPisteet.size()-3).makeLinkki(reunaPisteet.get(reunaPisteet.size()-2)));
        nonDJanat.add(reunaPisteet.get(reunaPisteet.size()-2).makeLinkki(reunaPisteet.get(reunaPisteet.size()-1)));
        nonDJanat.add(reunaPisteet.get(reunaPisteet.size()-1).makeLinkki(reunaPisteet.get(reunaPisteet.size()-4)));
        */
        //Itse algoritmi alkaa tästä
        //lasketaan maxy, miny ja keskiverto janan pituus
        float maxy = Float.MIN_VALUE;
        float miny = Float.MAX_VALUE;
        float sum = 0;
        
        for(Piste p: reunaPisteet)
        {
            maxy = Math.max(maxy, p.y);
            miny = Math.min(miny, p.y);
        }
        for(Jana j: nonDJanat)
        {
            sum += j.pituus;
        }
        float avg = sum/nonDJanat.size();
        //luodaan vaakasuorat viivat
        float vPos = miny + avg;
        System.out.println("Aloitetaan viivojen luonti. miny: " + miny + " maxy: " + maxy + " avg: " + avg);
        while(vPos < maxy)
        {
            //luodaan yksittäinen vaakasuora viiva.
            System.out.println("Luodaan viiva korkeudelle " + vPos);
            ArrayList<Float> interP = new ArrayList<>();
            //etsitään ne pisteet missä viiva leikkaa jonkin janan
            for(Jana j: nonDJanat)
            {
                if(Math.min(j.a.y, j.b.y) < vPos && Math.max(j.a.y, j.b.y) > vPos)
                {
                    //janan alku ja loppu ovat viivan eri puolilla. Lasketaan leikkaus piste
                    //x_l = ((y_l - y_a)dx)/dy  + x_a
                    interP.add(((vPos - j.a.y)*(j.b.x-j.a.x))/(j.b.y - j.a.y) + j.a.x);
                }
            }
            //järjestetään liekkaus pisteet kasvavaan järjestykseen
            java.util.Collections.sort(interP);
            //luodaan sisä pisteet
            for(int i = 0; i < interP.size(); i = i+2)
            {
                float xPos = interP.get(i) + sisaSpacing;
                while(xPos < interP.get(i+1))
                {
                    //katsotaan onko ehdokas piste sallituu
                    Piste p = new Piste(xPos, vPos, highestId);
                    if(validInner(p, nonDJanat, amin))
                    {
                     sisaPisteet.add(p);
                     highestId += 1;
                     p.id = highestId;
                        System.out.println("Lisätään sisä piste");
                    }
                    //kasvatetaan xPos
                    xPos += sisaSpacing;
                }
            }
            //otetaan seuraava viiva
            vPos += avg;
        }
        //ollaan saatu luotua sisä pisteet. Aloitetaan kolmiointi
        //Tehdään kolmionti vain yhdelle janalle
        //etsitään ehdokas pisteet
        System.out.println("Aloitetaan kolmiointi");
        
        ArrayList<Piste> kaikkiP = new ArrayList<>();
        ArrayList<Piste> kaikkiPFull = new ArrayList<>();
        kaikkiP.addAll(reunaPisteet);
        kaikkiP.addAll(sisaPisteet);
        kaikkiPFull.addAll(kaikkiP);
        int count = 0;
        ArrayList<tilaFrame> frmt = new ArrayList<>();
        while((nonDJanat.size()> 0||DJanat.size() > 0)  && count < 10){
         
         Jana kohde = null;
        if(nonDJanat.size() > 0) kohde = nonDJanat.get(count%nonDJanat.size());
        else kohde = DJanat.get(count%DJanat.size());
        tilaFrame t = new tilaFrame(kaikkiPFull, nonDJanat, DJanat, sisaPisteet, kohde);
        frmt.add(t);
        ArrayList<Piste> ehdokkaat = getEhdokkaat(kaikkiP, nonDJanat, kohde);
        t.addEhdokkaat(ehdokkaat);
        System.out.println("Ehdokas pisteet etsitty. lkm: " + ehdokkaat.size() + " nonD: " + nonDJanat.size() + " d: " + DJanat.size());
        //etsitään piste joka valitaan
        Piste uusiKolmio = getBestEhdokas(ehdokkaat, kohde);
        t.addValittu(uusiKolmio);
        if(uusiKolmio != null)
        {
            float kRad = calculateCRad(kohde, new Jana(kohde.b,uusiKolmio), new Jana(uusiKolmio,kohde.a));
        float[] kccent = calculateCCent(kohde, new Jana(kohde.b,uusiKolmio), new Jana(uusiKolmio,kohde.a));

        //System.out.println("Uusi piste löydetty: " + uusiKolmio.toString());
        //lisätään 

        Jana CA = new Jana(uusiKolmio, kohde.a );
        Jana BC = new Jana(kohde.b,uusiKolmio);
        //tulee selvittää janojen Delanay ominaisuus
        boolean isDel = true;
        for(Piste p: kaikkiPFull)
        {
            float dis = (p.x-kccent[0])*(p.x-kccent[0]) + (p.y-kccent[1])*(p.y-kccent[1]);
            if(dis <= kRad)
            {
                //piste oli cCirc ulkopuolella. Ei validi piste
                isDel = false;
                break;
            }
        }
        Jana tmp = null;
        if(isDel)
        {
            tmp = kohde.a.makeLinkki(uusiKolmio);
            if(tmp != null)DJanat.add(tmp);
            else removeFromFront(nonDJanat,DJanat,new Jana(kohde.a,uusiKolmio));//tulee etsiä jana jota ei saatu lisättyä, ja poistaa se frontista...
            tmp = uusiKolmio.makeLinkki(kohde.b);
            if(tmp != null)DJanat.add(tmp);
            else removeFromFront(nonDJanat,DJanat,new Jana(uusiKolmio,kohde.b));//tulee etsiä jana jota ei saatu lisättyä, ja poistaa se frontista...
            count--;
        }else{
            tmp = kohde.a.makeLinkki(uusiKolmio);
            if(tmp != null)nonDJanat.add(tmp);
            else removeFromFront(nonDJanat,DJanat,new Jana(kohde.a,uusiKolmio));//tulee etsiä jana jota ei saatu lisättyä, ja poistaa se frontista...
            tmp = uusiKolmio.makeLinkki(kohde.b);
            if(tmp != null)nonDJanat.add(tmp);
            else removeFromFront(nonDJanat,DJanat,new Jana(uusiKolmio,kohde.b));//tulee etsiä jana jota ei saatu lisättyä, ja poistaa se frontista...
            count--;
        }
         sisaPisteet.remove(uusiKolmio);
         
        }
        if(!nonDJanat.remove(kohde)) DJanat.remove(kohde);
        //päivitetään sisä pisteet.
        ArrayList<Piste> gFrontP = new ArrayList<>();
        for(Jana j: nonDJanat){gFrontP.add(j.a);}
        for(Jana j: DJanat){gFrontP.add(j.a);}
        gFrontP.addAll(sisaPisteet);
        //Poistetaan duplikaatit
        for(int i = 0; i < gFrontP.size(); i++)
        {
            for(int j = i+1; j < gFrontP.size(); j++)
            {
                if(gFrontP.get(i) == gFrontP.get(j))
                {
                    gFrontP.remove(j);
                    j--;
                }
            }
        }
        kaikkiP = gFrontP;
        
        System.out.println("Kierros " + count);
        count++;
        }
        delVisualizer ui = new delVisualizer(frmt);
        ui.setVisible(true);
        kirjoita("output.txt", kaikkiPFull);
                
    }
    
    private static void kirjoita(String fname, ArrayList<Piste> pisteet)
    {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(fname);
            bw = new BufferedWriter(fw);
            
            ArrayList<String> jnt = new ArrayList<>();
            ArrayList<String> klmt = new ArrayList<>();
            
            bw.write("[PISTEET]\r\n");
            for(Piste p: pisteet)
            {
                bw.write(p.id + ";" + p.x + ";" + p.y + "\r\n");
                for(Jana j: p.linkit)
                {
                    if(j.b == p) continue;
                    jnt.add(p.id + "," + j.b.id + "\r\n");
                }
                for(Piste[] pa: p.getKolmiot())
                {
                    klmt.add(pa[0].id + ";" + pa[1].id + ";" + pa[2].id + "\r\n");
                }
            }
            bw.write("[JANAT]\r\n");
            for(String s:jnt)
            {
                bw.write(s);
            }
            bw.write("[KOLMIOT]\r\n");
            for(String s:klmt)
            {
                bw.write(s);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(DelTriArtikkelista.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(bw != null) bw.close();
                if(fw != null) fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DelTriArtikkelista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    private static void parseFile(String fName)
    {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(fName);
            br = new BufferedReader(fr);
            br.readLine(); //otetaan eka rivi pois
            String rivi = br.readLine();
            while(rivi.compareTo("[JANAT]") != 0)
            {
                String[] splt = rivi.split(";");
                float tmpid = Float.parseFloat(splt[0]);
                reunaPisteet.add(new Piste(Float.parseFloat(splt[1]),Float.parseFloat(splt[2]),tmpid));
                if(tmpid > highestId) highestId = tmpid;
                rivi = br.readLine();
            }
            rivi = br.readLine();
            while(rivi != null)
            {
                String[] splt = rivi.split(",");
                Piste a = null,b = null;
                for(Piste p: reunaPisteet)
                {
                    if(p.id == Float.parseFloat(splt[0])) a = p;
                    else if(p.id == Float.parseFloat(splt[1])) b = p;
                }
                nonDJanat.add(a.makeLinkki(b));
                rivi = br.readLine();
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(DelTriArtikkelista.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(DelTriArtikkelista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public static void removeFromFront(ArrayList<Jana> nd, ArrayList<Jana> d, Jana poistettava)
    {
        for(Jana j: nd)
        {
            if(poistettava.compare(j)){nd.remove(j);break;}
        }
        for(Jana j: d)
        {
            if(poistettava.compare(j)){d.remove(j);break;}
        }
    }
    
    public static Piste getBestEhdokas(ArrayList<Piste> ehdokkaat, Jana kohde)
    {
        float rad = Float.MAX_VALUE;
        Piste pal = null;
        for(Piste p: ehdokkaat)
        {
            Jana CA = new Jana(p, kohde.a);
            Jana BC = new Jana(kohde.b,p);
            float val = calculateCRad(kohde, CA, BC);
            if(val < rad) {rad = val;pal = p;}
        }
        if(pal == null) System.out.println("Pal on null. ehdokaat koko: " + ehdokkaat.size());
        return pal;
    }
    
    public static float[] calculateCCent(Jana j1, Jana j2, Jana j3)
    {
        //kaava: https://en.wikipedia.org/wiki/Circumscribed_circle

        float det = 2*(j1.a.x*(j2.a.y-j3.a.y) + 
                        j2.a.x*(j3.a.y-j1.a.y)+
                        j3.a.x*(j1.a.y-j2.a.y));
        float x = (1/det)*((j1.a.x*j1.a.x + j1.a.y*j1.a.y)*(j2.a.y-j3.a.y)+
                            (j2.a.x*j2.a.x + j2.a.y*j2.a.y)*(j3.a.y-j1.a.y)+
                            (j3.a.x*j3.a.x + j3.a.y*j3.a.y)*(j1.a.y-j2.a.y));
        
        float y = (1/det)*((j1.a.x*j1.a.x + j1.a.y*j1.a.y)*(j3.a.x-j2.a.x)+
                            (j2.a.x*j2.a.x + j2.a.y*j2.a.y)*(j1.a.x-j3.a.x)+
                            (j3.a.x*j3.a.x + j3.a.y*j3.a.y)*(j2.a.x-j1.a.x));
        //System.out.println("CicC: " + x + "," + y);
        return new float[]{x,y};
    }
    
    public static ArrayList<Piste> getEhdokkaat(ArrayList<Piste> pisteet, ArrayList<Jana> nonDjanat, Jana kohde)
    {
        ArrayList<Piste> pal = new ArrayList<>();
        for(Piste p : pisteet)
        {
            Jana CA = new Jana(p, kohde.a);
            Jana CB = new Jana(p, kohde.b);
            if(CA.pituus == 0 || CB.pituus == 0) continue; //valittu piste on kohde janan alku tai loppu
            if(CA.ristiTulo(CB) <= 0) continue;
            //tarkistetaan leikkaus
            Jana BC = new Jana(kohde.b,p);
            boolean CAok = true;
            boolean CBok = true;
            for(Jana j: nonDJanat)
            {
                //jos jana CA tai CB on jo olemassa, ei se leikkaa
                if(j == null) {
                    System.out.println("Nyt menee jotain vikaan!");}
                if(j.compare(CA) || j.compare(BC)) break;
            if(leikkaa(j, CA)){CAok = false;break;}
            if(leikkaa(j, BC)) {CBok = false;break;}
            }
            if(!CAok || !CBok) continue;
            pal.add(p);
            
        }
        return pal;
    }
    
    //huom tämä on säteen neliö!
    public static float calculateCRad(Jana s1, Jana s2,Jana s3)
    {

        return  (s1.pituus*s2.pituus*s3.pituus*s1.pituus*s2.pituus*s3.pituus)/(
                (s1.pituus+s2.pituus+s3.pituus)*
                (s2.pituus+s3.pituus-s1.pituus)*
                (s3.pituus+s1.pituus-s2.pituus)*
                (s1.pituus+s2.pituus-s3.pituus)
                );
    
    }
    
    //http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
    public static boolean leikkaa(Jana p, Jana q)
    {
        float rxs = p.ristiTulo(q);
        float qpxr = new Jana(p.a, q.a).ristiTulo(p);
        if(rxs == 0)
        {
            //colinear tai paralel
            if(qpxr != 0) return false;
            //colinear
            float t = (q.a.x - p.a.x)/(p.b.x-p.a.x);
            float u = (q.b.x - p.a.x)/(p.b.x-p.a.x);
            System.out.println("t:" + t + " u:" + u);
            //jos arvo on NaN, käytetään y muutosta, siltä varalta että vektorilla ei ole x muutosta
            //Tässä kohtaa kikkaillaan koska IEEE-754 starndardi, x == NaN -> false, paitsi jos x = NaN
            if(t != t) {t = (q.a.y - p.a.y)/(p.b.y-p.a.y);System.out.println("saatiin kiinni");}
            if(u != u) u = (q.b.y - p.a.y)/(p.b.y-p.a.y);
            //jos vieläkin NaN asetetaan nollaksi... alku tai loppu piste on toisen janan alku tai loppu. Ei välttämättä leikkausta
            if(t != t) t = 0;
            if(u != u) u = 0;
            System.out.println("final t:" + t + " u:" + u);
            if(t <= 0 && u <= 0) return false;
            else if(t>0 && u>0 && Math.abs(t)>=1&&Math.abs(u)>=1) return false;
            return true;
        }
        //ratkaistaan t ja u. Eri muuttujat kuin yllä olevassa iffissä.
        float t = new Jana(p.a, q.a).ristiTulo(q) /rxs;
        float u = qpxr/rxs;
        if(t > 0 && t < 1 && u > 0 && u < 1) return true;
        return false;
    }
    
    public static boolean validInner(Piste p, ArrayList<Jana> janat, float amin)
    {
        //etsitään jana joka on lähinnä annettua pistettä
        Jana ehdokas = null;
        float dis = Float.MAX_VALUE;
        for(Jana j: janat)
        {
            float PA = (p.x-j.a.x)*(p.x-j.a.x) + (p.y-j.a.y)*(p.y-j.a.y);
            float PB = (p.x-j.b.x)*(p.x-j.b.x) + (p.y-j.b.y)*(p.y-j.b.y);
            if(PA + PB < dis)
            {
                ehdokas = j;
                dis = PA + PB;
            }
        }
        //jana löydetty Tarkistetaan hyväksytäänkö piste
        Jana PA = new Jana(p,ehdokas.a);
        Jana PB = new Jana(p,ehdokas.b);
        float alpha =(float) (2*Math.sqrt(3)*(PA.ristiTulo(PB)/(PA.pituus*PA.pituus + PB.pituus*PB.pituus + ehdokas.pituus*ehdokas.pituus)));
        return alpha > amin;
    }
    
}
