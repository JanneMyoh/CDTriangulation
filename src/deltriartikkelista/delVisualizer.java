/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deltriartikkelista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author janne
 */
public class delVisualizer extends javax.swing.JFrame {

    private ArrayList<tilaFrame> frmt;
    private int oldx = 0;
    private int oldy = 0;
    private int tila = 0;
    private int kInd = -1; //-1 tarkoittaa että ei näytetä kolmiota
    
    
    
    /**
     * Creates new form delVisualizer
     * @param pisteet
     */
    public delVisualizer(ArrayList<tilaFrame> pisteet) {
        System.out.println("Luodaan visualizer");
        initComponents();
        frmt = pisteet;
        for(tilaFrame p: frmt)
        {
            p.scale(10);
        }
        JPanel pnl = new JPanel(){
           @Override
           public void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g,this.getWidth(),this.getHeight());
                
            } 
            
        };
        this.add(pnl);
        updateControls();
    }
    
    private void draw(Graphics g, int wid, int hei)
    {

        //shiftataan kaikki pisteet niin että panelin keskikohta on origo
        int ox = wid/2;
        int oy = hei/2;

        //piirretään pohja, eli kaikki pisteet ja niiden väliset linkit
        tilaFrame t = frmt.get(tila/3);
        ArrayList<Piste> piirrettava = t.getBase();
        for(Piste p: piirrettava)
        {
            g.setColor(Color.black);
            g.drawOval(Math.round(p.x + ox),Math.round(p.y + oy),3,3);
            //piirretään pisteen linkit. Problem: kaikki viivat tulee tuplana...
            drawLines(g, p.linkit, ox, oy, Color.red);
            
        }
        //piirretään tarkempi tieto
        switch (tila%3)
        {
            case 0:
                drawLines(g,t.getND(),ox,oy,Color.BLUE); //piirretään nonD janat
                drawLines(g,t.getD(),ox,oy,Color.CYAN); //piirretään D janat
                drawPoints(g,t.getReuna(),ox,oy,Color.GREEN); //piirretään reuna pisteet
                drawPoints(g,t.getSisa(),ox,oy,Color.GREEN); //piirretään sisa pisteet
                drawKolmio(g,ox,oy);
                break;
            case 1:
                drawPoints(g,t.getEhdokas(),ox,oy,Color.BLUE); //piirretään reuna pisteet
                g.setColor(Color.GREEN);
                Jana j = t.getKohde();
                g.drawLine(Math.round(j.a.x+ox),Math.round(j.a.y+oy), Math.round(j.b.x+ox),Math.round(j.b.y+oy));
                break;
            case 2:
                Piste pst = t.getValittu();
                g.setColor(Color.GREEN);
                if(pst != null)
                    g.drawOval(Math.round(pst.x + ox),Math.round(pst.y + oy),3,3);
                Jana jna = t.getKohde();
                g.drawLine(Math.round(jna.a.x+ox),Math.round(jna.a.y+oy), Math.round(jna.b.x+ox),Math.round(jna.b.y+oy));
                break;
                
        }
    }
    
    private void drawKolmio(Graphics g, int ox, int oy)
    {
        g.setColor(Color.BLACK);
        if(kInd == -1) return;
        Piste[] k = frmt.get(tila/3).kolmiot.get(kInd);
        for(int i = 0; i < 3; i++)
        {
            g.drawLine(Math.round(k[i].x+ox),Math.round(k[i].y+oy), Math.round(k[(i+1)%3].x+ox),Math.round(k[(i+1)%3].y+oy));
        }
    }
    
    private void drawLines(Graphics g, ArrayList<Jana> jnt, int ox, int oy, Color c)
    {
        g.setColor(c);
        for(Jana j: jnt)
            {
                g.drawLine(Math.round(j.a.x+ox),Math.round(j.a.y+oy), Math.round(j.b.x+ox),Math.round(j.b.y+oy));
            }
    }
    
    private void drawPoints(Graphics g, ArrayList<Piste> jnt, int ox, int oy, Color c)
    {
        g.setColor(c);
        for(Piste p: jnt)
            {
                g.drawOval(Math.round(p.x + ox),Math.round(p.y + oy),3,3);
            }
    }
    
    private void updateControls()
    {
        btnNext.setEnabled(true);
        btnNextIte.setEnabled(true);
        btnNxtKol.setEnabled(true);
        btnPreIte.setEnabled(true);
        btnPreKol.setEnabled(true);
        btnPrev.setEnabled(true);
        if(tila == 0) btnPrev.setEnabled(false);
        if(tila < 3) btnPreIte.setEnabled(false);
        if(tila == (frmt.size()*3)-1) btnNext.setEnabled(false);
        if(tila >(frmt.size()*3)-4) btnNextIte.setEnabled(false);
        if(tila%3 != 0)
        {
            //lukitan kolmio kontrollerit
            kInd = -1;
            btnNxtKol.setEnabled(false);
            btnPreKol.setEnabled(false);
        }
        if(kInd == -1) btnPreKol.setEnabled(false);
        if(kInd == frmt.get(tila/3).kolmiot.size()-1) btnNxtKol.setEnabled(false);
    }
    
    private void setTeksti()
    {
        String teksti = "Askel: " + tila/3 + " of " + frmt.size() + ". Tila: " + tila%3;
        lblTila.setText(teksti);
        String kolTeksti = "";
        if(kInd > -1) kolTeksti = kInd + " of " + frmt.get(tila/3).kolmiot.size();
        lblKolmio.setText(kolTeksti);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblTila = new javax.swing.JLabel();
        btnPreIte = new javax.swing.JButton();
        btnNextIte = new javax.swing.JButton();
        lblKolmio = new javax.swing.JLabel();
        btnPreKol = new javax.swing.JButton();
        btnNxtKol = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setLayout(new java.awt.GridLayout(8, 0));

        btnPrev.setText("prev");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrev);

        btnNext.setText("next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel1.add(btnNext);

        lblTila.setText("TURHA");
        jPanel1.add(lblTila);

        btnPreIte.setText("Prev Ite");
        btnPreIte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreIteActionPerformed(evt);
            }
        });
        jPanel1.add(btnPreIte);

        btnNextIte.setText("Next Ite");
        btnNextIte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextIteActionPerformed(evt);
            }
        });
        jPanel1.add(btnNextIte);

        lblKolmio.setText("KOLMIO");
        jPanel1.add(lblKolmio);

        btnPreKol.setText("Prev Tri");
        btnPreKol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreKolActionPerformed(evt);
            }
        });
        jPanel1.add(btnPreKol);

        btnNxtKol.setText("Next Tri");
        btnNxtKol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNxtKolActionPerformed(evt);
            }
        });
        jPanel1.add(btnNxtKol);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if(tila < (frmt.size()-1)*3) tila++;
        updateControls();
        setTeksti();
        this.validate();
        this.repaint();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if(tila > 0) tila--;
        updateControls();
        setTeksti();
        this.validate();
        this.repaint();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnPreIteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreIteActionPerformed
        int modi = tila%3;
        if(modi == 0) modi = 3;
        tila -= modi;
        updateControls();
        setTeksti();
        this.validate();
        this.repaint();
    }//GEN-LAST:event_btnPreIteActionPerformed

    private void btnNextIteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextIteActionPerformed
        int modi = 3-tila%3;
        tila += modi;
        updateControls();
        setTeksti();
        this.validate();
        this.repaint();
    }//GEN-LAST:event_btnNextIteActionPerformed

    private void btnPreKolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreKolActionPerformed
        kInd--;
        updateControls();
        setTeksti();
        this.validate();
        this.repaint();
    }//GEN-LAST:event_btnPreKolActionPerformed

    private void btnNxtKolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNxtKolActionPerformed
        kInd++;
        updateControls();
        setTeksti();
        this.validate();
        this.repaint();
    }//GEN-LAST:event_btnNxtKolActionPerformed

    /**
     * @param args the command line arguments
     */
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextIte;
    private javax.swing.JButton btnNxtKol;
    private javax.swing.JButton btnPreIte;
    private javax.swing.JButton btnPreKol;
    private javax.swing.JButton btnPrev;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblKolmio;
    private javax.swing.JLabel lblTila;
    // End of variables declaration//GEN-END:variables
}
