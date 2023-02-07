import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.TextField;

public class Oberfläche
{
    Frame f;
    Button Knopf;
    TextField Feld11;TextField Feld12;TextField Feld13;TextField Feld14;TextField Feld15;TextField Feld16;TextField Feld17;TextField Feld18;TextField Feld19;
    TextField Feld21;TextField Feld22;TextField Feld23;TextField Feld24;TextField Feld25;TextField Feld26;TextField Feld27;TextField Feld28;TextField Feld29;
    TextField Feld31;TextField Feld32;TextField Feld33;TextField Feld34;TextField Feld35;TextField Feld36;TextField Feld37;TextField Feld38;TextField Feld39;
    TextField Feld41;TextField Feld42;TextField Feld43;TextField Feld44;TextField Feld45;TextField Feld46;TextField Feld47;TextField Feld48;TextField Feld49;
    TextField Feld51;TextField Feld52;TextField Feld53;TextField Feld54;TextField Feld55;TextField Feld56;TextField Feld57;TextField Feld58;TextField Feld59;
    TextField Feld61;TextField Feld62;TextField Feld63;TextField Feld64;TextField Feld65;TextField Feld66;TextField Feld67;TextField Feld68;TextField Feld69;
    TextField Feld71;TextField Feld72;TextField Feld73;TextField Feld74;TextField Feld75;TextField Feld76;TextField Feld77;TextField Feld78;TextField Feld79;
    TextField Feld81;TextField Feld82;TextField Feld83;TextField Feld84;TextField Feld85;TextField Feld86;TextField Feld87;TextField Feld88;TextField Feld89;
    TextField Feld91;TextField Feld92;TextField Feld93;TextField Feld94;TextField Feld95;TextField Feld96;TextField Feld97;TextField Feld98;TextField Feld99;
    // Erzeugen aller Eingabefelder mithilfe einer Liste aller Eingabefelder
    TextField[] Felder = {Feld11,Feld12,Feld13,Feld14,Feld15,Feld16,Feld17,Feld18,Feld19,
            Feld21,Feld22,Feld23,Feld24,Feld25,Feld26,Feld27,Feld28,Feld29,
            Feld31,Feld32,Feld33,Feld34,Feld35,Feld36,Feld37,Feld38,Feld39,
            Feld41,Feld42,Feld43,Feld44,Feld45,Feld46,Feld47,Feld48,Feld49,
            Feld51,Feld52,Feld53,Feld54,Feld55,Feld56,Feld57,Feld58,Feld59,
            Feld61,Feld62,Feld63,Feld64,Feld65,Feld66,Feld67,Feld68,Feld69,
            Feld71,Feld72,Feld73,Feld74,Feld75,Feld76,Feld77,Feld78,Feld79,
            Feld81,Feld82,Feld83,Feld84,Feld85,Feld86,Feld87,Feld88,Feld89,
            Feld91,Feld92,Feld93,Feld94,Feld95,Feld96,Feld97,Feld98,Feld99,    
        };
    boolean Knopf_gedrückt = false;

    int x = 0;// Anzahl in x-Richtung
    int y = 0;//Anzahl in y-Richtung 
    int ii = 0;//Zähler zur Vergrößerung des Abstandes nach 3 Kästchen (vertikal)
    int nn = 0;//Zur Vergrößerung des Abstands nach 3 Kästchen (vertikal)
    int i = 0;// Zähler zur Vergrößerung des Abstandes nach 3 Kästchen (horizontal)
    int n = 5;// Zur Vergrößerung des Abstands nach 3 Kästchen (horizontal)
    // n ist gleich fünf damit die erste Reihe mit den anderen passt

    int q = 0;//Quadrat
    int r = 0;//Reihe des Quadrats
    int s = 0;//Spalte des Quadrats

    public Oberfläche(String [][] Gesamt)
    {
        f = new Frame ("Sudoku Löser");//Erzeugen des Eingabefensters
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

        for (int g = 0;g<81;g++)
        {
            Felder[g] = new TextField();
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
        Knopf = new Button(); // Das Erzeugen des Knopfes zur Entgültigen Eingabe
        Knopf.setLocation(230,250);
        Knopf.setLabel("Fertig");
        Knopf.setSize(100,30);
        Knopf.setEnabled(true);
        f.add(Knopf);
        Knopf.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        Knopf_gedrückt = true;
                    }
                });
    }

    public String get_Text(int g)
    {
        return Felder[g].getText();
    }

}
