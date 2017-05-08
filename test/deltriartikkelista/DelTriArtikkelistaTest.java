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
public class DelTriArtikkelistaTest extends TestCase {
    
    public DelTriArtikkelistaTest(String testName) {
        super(testName);
    }

    /**
     * Test of main method, of class DelTriArtikkelista.
     */
    public void testMain() {
        System.out.println("main");
    }

    /**
     * Test of calculateCCent method, of class DelTriArtikkelista.
     */
    public void testCalculateCCent() {
        System.out.println("calculateCCent");
        Piste p1 = new Piste(0, 1);
        Piste p2 = new Piste(1, 0);
        Piste p3 = new Piste(0, -1);
        Jana j1 = new Jana(p1,p2);
        Jana j2 = new Jana(p2,p3);
        Jana j3 = new Jana(p3,p1);
        float[] expResult = new float[]{0f,0f};
        float[] result = DelTriArtikkelista.calculateCCent(j1, j2, j3);
        assertEquals(expResult[0], result[0], 0.001f);
        assertEquals(expResult[1], result[1],0.001f);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of calculateCRad method, of class DelTriArtikkelista.
     */
    public void testCalculateCRad() {
        System.out.println("calculateCRad");
        Piste p1 = new Piste(0, 1);
        Piste p2 = new Piste(1, 0);
        Piste p3 = new Piste(0, -1);
        Jana j1 = new Jana(p1,p2);
        Jana j2 = new Jana(p2,p3);
        Jana j3 = new Jana(p3,p1);
        float expResult = 1.0F;
        float result = DelTriArtikkelista.calculateCRad(j1, j2, j3);
        assertEquals(expResult, result, 0.001f);
    }

    /**
     * Test of leikkaa method, of class DelTriArtikkelista.
     */
    public void testLeikkaa() {
        System.out.println("leikkaa");
        //paralel sama suunta
        Piste p1 = new Piste(0, 0);
        Piste p2 = new Piste(0, 1);
        Piste p3 = new Piste(1, 0);
        Piste p4 = new Piste(1, 1);
        boolean expResult = false;
        boolean result = DelTriArtikkelista.leikkaa(new Jana(p1, p2), new Jana(p3, p4));
        assertEquals(expResult, result);
        expResult = false;
        result = DelTriArtikkelista.leikkaa(new Jana(p1, p2), new Jana(p4, p3));
        assertEquals(expResult, result);
        //pristeää
        p1 = new Piste(0, 0);
        p2 = new Piste(0, 2);
        p3 = new Piste(-1, 1);
        p4 = new Piste(1, 1);
        expResult = true;
        result = DelTriArtikkelista.leikkaa(new Jana(p1, p2), new Jana(p3, p4));
        assertEquals(expResult, result);
        //colinear erillään
        p1 = new Piste(0, 0);
        p2 = new Piste(0, 1);
        p3 = new Piste(0, 2);
        p4 = new Piste(0, 3);
        expResult = false;
        result = DelTriArtikkelista.leikkaa(new Jana(p1, p2), new Jana(p3, p4));
        assertEquals(expResult, result);
        p1 = new Piste(0, 0);
        p2 = new Piste(0, 2);
        p3 = new Piste(0, 1);
        p4 = new Piste(0, 3);
        expResult = true;
        result = DelTriArtikkelista.leikkaa(new Jana(p1, p2), new Jana(p4, p3));
        assertEquals(expResult, result);
        p1 = new Piste(0, 0);
        p2 = new Piste(0, 1);
        p3 = new Piste(0, 1);
        p4 = new Piste(0, 2);
        expResult = false;
        result = DelTriArtikkelista.leikkaa(new Jana(p1, p2), new Jana(p3, p4));
        assertEquals(expResult, result);
        p1 = new Piste(0, 0);
        p2 = new Piste(0, 1);
        p3 = new Piste(2, 1);
        p4 = new Piste(0, 3);
        expResult = false;
        result = DelTriArtikkelista.leikkaa(new Jana(p1, p2), new Jana(p3, p4));
        assertEquals(expResult, result);
    }

    /**
     * Test of validInner method, of class DelTriArtikkelista.
     */
    public void testValidInner() {
        System.out.println("validInner");
        //rakennetaan kuvio
        Piste p1 = new Piste(0, 1);
        Piste p2 = new Piste(1, 0);
        Piste p3 = new Piste(2, 0);
        Piste p4 = new Piste(3, 1);
        Piste p5 = new Piste(2, 2);
        Piste p6 = new Piste(1, 2);
        ArrayList<Jana> janat = new ArrayList<Jana>();
        janat.add(new Jana(p1,p2));
        janat.add(new Jana(p2,p3));
        janat.add(new Jana(p3,p4));
        janat.add(new Jana(p4,p5));
        janat.add(new Jana(p5,p6));
        janat.add(new Jana(p6,p1));
        Piste p = new Piste(0.75f,0.75f);
        boolean expResult = true;
        boolean result = DelTriArtikkelista.validInner(p, janat, 0f);
        assertEquals(expResult, result);
    }
    
}
