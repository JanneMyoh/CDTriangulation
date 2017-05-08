/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deltriartikkelista;

import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author janne
 */
public class PisteTest extends TestCase {
    
    public PisteTest(String testName) {
        super(testName);
    }

    /**
     * Test of makeLinkki method, of class Piste.
     */
    public void testMakeLinkki() {
        
    }

    /**
     * Test of addLinkki method, of class Piste.
     */
    public void testAddLinkki() {

    }

    /**
     * Test of shift method, of class Piste.
     */
    public void testShift() {
        
    }

    /**
     * Test of scale method, of class Piste.
     */
    public void testScale() {
        
    }

    /**
     * Test of unlink method, of class Piste.
     */
    public void testUnlink() {
        
    }

    /**
     * Test of removeLinkki method, of class Piste.
     */
    public void testRemoveLinkki() {
        
    }

    /**
     * Test of toString method, of class Piste.
     */
    public void testToString() {
                
    }

    /**
     * Test of copy method, of class Piste.
     */
    public void testCopy() {

    }

    /**
     * Test of equals method, of class Piste.
     */
    public void testEquals() {

    }

    /**
     * Test of comapre method, of class Piste.
     */
    public void testCompare() {
        Piste base = new Piste(10,10);
        Piste p1 = new Piste(11,10); //isompi
        Piste p2 = new Piste(9,10); //pienempi
        Piste p3 = new Piste(10,11);//isompi
        Piste p4 = new Piste(10,9);//Pienempi
        if(!base.compare(p1))fail("p1 kusi");
        if(base.compare(p2))fail("p2 kusi");
        if(!base.compare(p3))fail("p3 kusi");
        if(base.compare(p4))fail("p4 kusi");
    }

    /**
     * Test of getKolmiot method, of class Piste.
     */
    public void testGetKolmiot() {
        Piste p1 = new Piste(0,1);
        Piste p2 = new Piste(1,0);
        Piste p3 = new Piste(1,1);
        Piste p4 = new Piste(1,2);
        Piste p5 = new Piste(2,1);
        Piste p6 = new Piste(2,0);
        p1.makeLinkki(p2);
        p2.makeLinkki(p3);
        p3.makeLinkki(p4);
        p3.makeLinkki(p5);
        p3.makeLinkki(p1);
        p5.makeLinkki(p2);
        p5.makeLinkki(p6);
        p6.makeLinkki(p2);
        //0 kolmiota
        ArrayList<Piste[]> k1 = p5.getKolmiot();
        ArrayList<Piste[]> v1 = new ArrayList<>();
        if(!compareKolmioLists(k1, v1))fail("k1 feilas");
        //1 kolmio
        ArrayList<Piste[]> k2 = p1.getKolmiot();
        ArrayList<Piste[]> v2 = new ArrayList<>();
        v2.add(new Piste[]{p1,p2,p3});
        if(!compareKolmioLists(k2, v2))fail("k2 feilas");
        //2 kolmiota
        ArrayList<Piste[]> k3 = p2.getKolmiot();
        ArrayList<Piste[]> v3 = new ArrayList<>();
        v3.add(new Piste[]{p2,p3,p5});
        v3.add(new Piste[]{p2,p6,p5});
        if(!compareKolmioLists(k3, v3))fail("k3 feilas");
    }
    
    private boolean compareKolmioLists(ArrayList<Piste[]> a1, ArrayList<Piste[]> a2)
    {
        int i = 0;
        boolean pal = a1.size() == a2.size();
        while(pal && i < a1.size())
        {
            pal = pal && compareArrays(a1.get(i), a2.get(i));
            i++;
        }
        return pal;
    }
    
    private boolean compareArrays(Piste[] a1,Piste[] a2 )
    {
        if(a1.length != a2.length) return false;
        for(int i = 0; i < a1.length; i++)
        {
            if(!a1[i].equals(a2[i]))return false;
        }
        return true;
    }
    
}
