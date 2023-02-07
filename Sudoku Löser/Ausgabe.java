import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;

public class Ausgabe
{
    //Attribute
    Frame f;
    Label Feld11;Label Feld12;Label Feld13;Label Feld14;Label Feld15;Label Feld16;Label Feld17;Label Feld18;Label Feld19;
    Label Feld21;Label Feld22;Label Feld23;Label Feld24;Label Feld25;Label Feld26;Label Feld27;Label Feld28;Label Feld29;
    Label Feld31;Label Feld32;Label Feld33;Label Feld34;Label Feld35;Label Feld36;Label Feld37;Label Feld38;Label Feld39;
    Label Feld41;Label Feld42;Label Feld43;Label Feld44;Label Feld45;Label Feld46;Label Feld47;Label Feld48;Label Feld49;
    Label Feld51;Label Feld52;Label Feld53;Label Feld54;Label Feld55;Label Feld56;Label Feld57;Label Feld58;Label Feld59;
    Label Feld61;Label Feld62;Label Feld63;Label Feld64;Label Feld65;Label Feld66;Label Feld67;Label Feld68;Label Feld69;
    Label Feld71;Label Feld72;Label Feld73;Label Feld74;Label Feld75;Label Feld76;Label Feld77;Label Feld78;Label Feld79;
    Label Feld81;Label Feld82;Label Feld83;Label Feld84;Label Feld85;Label Feld86;Label Feld87;Label Feld88;Label Feld89;
    Label Feld91;Label Feld92;Label Feld93;Label Feld94;Label Feld95;Label Feld96;Label Feld97;Label Feld98;Label Feld99;

    int x = 0;// Anzahl in x-Richtung
    int y = 0;//Anzahl in y-Richtung 
    int ii = 0;//Zähler zur Vergrößerung des Abstandes nach 3 Kästchen (vertikal)
    int nn = 0;//Zur Vergrößerung des Abstands nach 3 Kästchen (vertikal)
    int i = 0;// Zähler zur Vergrößerung des Abstandes nach 3 Kästchen (horizontal)
    int n = 5;// Zur Vergrößerung des Abstands nach 3 Kästchen (horizontal)
    // n ist gleich fünf damit die erste Reihe mit den anderen passt

    //Konstruktor
    public Ausgabe(String[][] Gesamt)
    {
        f = new Frame ("Sudoku Ausgabe");//Erzeugen des Ausgabefensters
        f.setSize(360,350);
        f.setLocation(0,30);
        f.setVisible(true);
        f.setLayout(null);

        f.addWindowListener(new WindowAdapter() // Die Funktionsfähigkeit des Schließen Knopfs
            {
                public void windowClosing(WindowEvent e)
                {
                    System.exit(1);
                }
            });
        Label[] Felder = {Feld11,Feld12,Feld13,Feld14,Feld15,Feld16,Feld17,Feld18,Feld19,
                Feld21,Feld22,Feld23,Feld24,Feld25,Feld26,Feld27,Feld28,Feld29,
                Feld31,Feld32,Feld33,Feld34,Feld35,Feld36,Feld37,Feld38,Feld39,
                Feld41,Feld42,Feld43,Feld44,Feld45,Feld46,Feld47,Feld48,Feld49,
                Feld51,Feld52,Feld53,Feld54,Feld55,Feld56,Feld57,Feld58,Feld59,
                Feld61,Feld62,Feld63,Feld64,Feld65,Feld66,Feld67,Feld68,Feld69,
                Feld71,Feld72,Feld73,Feld74,Feld75,Feld76,Feld77,Feld78,Feld79,
                Feld81,Feld82,Feld83,Feld84,Feld85,Feld86,Feld87,Feld88,Feld89,
                Feld91,Feld92,Feld93,Feld94,Feld95,Feld96,Feld97,Feld98,Feld99,    
            };    

        for (int g = 0;g<81;g++)
        {
            Felder[g] = new Label();
            Felder[g].setSize(20,20);
            Felder[g].setLocation(x*20+20 + n,50+y*20+nn);//(Anzahl in x-Richtung * Abstand + Die einmaligen Erhöhungen des Abstands) &
            //(Standard-Abstand nach oben + Anzahl in y-Richtung * Abstand + Die einmaligen Erhöhungen des Abstands)           
            Felder[g].setText(Gesamt[y][x]);
            Felder[g].setVisible(true);
            f.add(Felder[g]);

            x++;
            if(x == 9)//Falls eine Reihe fertig ist, in die nächst wechseln
            {
                y++;
                n = 0;
                x = 0;
                ii++;
            }
            i++;
            if(ii==3)// Nach drei Zeilen den Abstand einmalig erhöhen
            {
                nn +=5;
                ii=0;
            }
            if(i==3)// Nach drei Spalten den Abstand einmalig erhöhen
            {
                n += 5;
                i = 0;
            }
        }
        
        
    }

    //Methoden


}