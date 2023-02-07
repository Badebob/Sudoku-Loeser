import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.TextField;

public class Rechner
{
    Oberfläche Oberfläche;// Die Eingabefläche

    String[][] Gesamt = new String[9][9];// Die Liste mit allen Eingaben nach dem Prinzip oben, nur halt -1
    boolean[] Vergleich = new boolean[9];// Die Liste zum Überprüfen ob es Zahlen doppelt gibt (Sudoku Regeln)
    String [][][] Quadrate = new String[9][3][3];// Die Liste mit allen Eingaben in den Quadraten
    boolean [][][] Notizen_Gesamt = new boolean [9][9][9]; // Notizen für die normale Felddarstellung 
    boolean [][][][] Notizen_Quadrate = new boolean [9][3][3][9]; // Notizen für die Quadratdarstellung
    int [] Anzahl_der_Notizen = new int [9]; // Zum Überprüfen wie viele Notizen einer Zahl in einer Reihe o.ä. sind

    int x = 1;// Anzahl in x-Richtung
    int y = 1;//Anzahl in y-Richtung 
    int ii = 0;//Zähler zur Vergrößerung des Abstandes nach 3 Kästchen (vertikal)
    int nn = 0;//Zur Vergrößerung des Abstands nach 3 Kästchen (vertikal)
    int i = 0;// Zähler zur Vergrößerung des Abstandes nach 3 Kästchen (horizontal)
    int n = 5;// Zur Vergrößerung des Abstands nach 3 Kästchen (horizontal)
    // n ist gleich fünf damit die erste Reihe mit den anderen passt

    int q = 0;//Quadrat
    int r = 0;//Reihe des Quadrats
    int s = 0;//Spalte des Quadrats

    int x1 = 0;// Die folgenden Variablen sind dafür da mehrmals Positionsdaten zu haben
    int y1 = 0;

    int x5 = 0;
    int x6 = 0;
    int y5 = 0;
    int y6 = 0;
    int q4 = 0;
    int r4 = 0;
    int s4 = 0;
    int x7 = 0;
    int y7 = 0;

    int r5 = r;
    int s5 = s;
    int r6 = r;
    int s6 = s;

    int x2 = 0;
    int y2 = 0;
    int r1 = 0;
    int s1 = 0;
    
    int c;
    int v;

    Error_Ausgabe Error;//Fehlerausgabe
    Ausgabe Ausgabe;

    public Rechner()
    {
        for(y=0;y<9;y++)//Alle Felder der Liste Gesamt gesamt auf den Standardwert "" setzen
        {
            for(x=0;x<9;x++)
            {
                Gesamt[y][x] = "";
            }
        }

        boolean Überprüfen = false;

        do//Das Erstellen einer Eingabefläche bis es keine Fehler mehr hat
        {
            if(Oberfläche != null)//Wenn es nicht das erste Fenster ist, dass vorherige unsichtbar machen(Ansonsten wurde es mehrmals angezeigt)
            {
                Oberfläche.f.setVisible(false);
            }
            Oberfläche = new Oberfläche(Gesamt);
            while(Oberfläche.Knopf_gedrückt == false)//warten bis der Fertigknopf gedrückt wurde
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }

            x = 0;
            y = 0;
            q = 0;
            r = 0;
            s = 0;

            for (int g = 0;g<81;g++)// Weitergabe der Eingaben in die Listen
            {
                Gesamt [y][x] = Oberfläche.get_Text(g);
                Wo_in_Quadrate(y,x);
                Quadrate[q][r][s] = Gesamt[y][x];
                x++;
                if(x == 9)//Am Ende der Zeile in eine Neue vorrücken
                {
                    y++;
                    x = 0;
                }
            }

            if(Überprüfen()) //Aufrufen der Methode Uberprüfen, zum Überprüfen der Eingabe
            {
                Überprüfen = true;//Um die while-Schleife zu beenden
                for(y = 0; y<9;y++)//Die Erstellung der Notizen, hier wird bei alles auf true gesetzt, wenn das Feld keinen wert erhalten hat
                {
                    for(x = 0; x<9; x++)
                    {
                        Notizen();
                    }
                }
                Notizen_kürzen();//
                for(int g = 0;g<81;g++)//Die Widerholung der Lösungsarten, 81 mal, weil spätestens danach kein Feld mehr leer sein dürfte
                {
                    Lösungsweg1();
                    Lösungsweg2();
                }
                Überprüfen();
                Ausgabe = new Ausgabe(Gesamt);
                Oberfläche.f.setVisible(false);
            }
        }
        while(Überprüfen == false);
    }

    //Methoden
    public boolean Überprüfen()
    {
        y = 0;
        x = 0;
        for(y = 0;y<9;y++)//waagrechte Überprüfung
        {
            for(int g = 0; g<9;g++)//Das resetten der Liste Vergleich
            {
                Vergleich[g] = false;
            }
            for(x = 0; x<9;x++)
            {
                if(Zifferüberprüfung(Gesamt[y][x]) == false)// Wenn die Zahl mehrmals in der Reihe vorkommt, Errorausgabe
                {
                    Error = new Error_Ausgabe(y+1, x+1);// Hierbei wird immer die zweit Zahl als Fehler ausgegeben
                    return false;
                }
            }
        }
        y = 0;
        x = 0;
        for(x = 0;x<9;x++)//senkrechte Überprüfung
        {
            for(int g = 0; g<9;g++)//Vom Prinzio das gleiche wie oben
            {
                Vergleich[g] = false;
            }
            for(y = 0; y<9;y++)
            {
                if(Zifferüberprüfung(Gesamt[y][x]) == false)
                {
                    Error = new Error_Ausgabe(y+1, x+1);
                    return false;
                }
            }
        }
        q = 0;
        r = 0;
        s = 0;
        for(q = 0;q<9;q++)//Überprüfung der Quadtrate
        {
            for(int g = 0; g<9;g++)//Das resetten der Liste Vergleich
            {
                Vergleich[g] = false;
            }
            for(r = 0; r<3;r++)// Das gleiche nochmal nur bei den Quadraten, die sind zwei dimensional und deshlab eine for-Schleife mehr
            {
                for(s = 0;s<3;s++)
                {
                    if(Zifferüberprüfung(Quadrate[q][r][s]) == false)
                    {
                        Error = new Error_Ausgabe(y+1, x+1);
                        return false;
                    }
                }
            }
        }
        return true;// Rückgabe wenn alles in Ordnung ist
    }

    public boolean Zifferüberprüfung(String Ziffer)//Abgleich der Ziffern
    {
        if(Ziffer.equals("1"))//Wenn eine Ziffer zutrifft, Vergleich an der richtigen Stelle (also Zahl - 1) auf true setzen
        {
            if(Vergleich[0] == true)
            {
                return false;// Wenn Vergleich schon true ist, kommt die Zahl öfters vor, als sie dürfte, deshalb false und somit Errorausgabe
            }        
            Vergleich[0] = true;
            return true;
        }
        else if(Ziffer.equals("2"))
        {
            if(Vergleich[1] == true)
            {
                return false;
            }
            Vergleich[1] = true;
            return true;
        }
        else if(Ziffer.equals("3"))
        {
            if(Vergleich[2] == true)
            {
                return false;
            }
            Vergleich[2] = true;
            return true;
        }        
        else if(Ziffer.equals("4"))
        {
            if(Vergleich[3] == true)
            {
                return false;
            }
            Vergleich[3] = true;
            return true;
        }        
        else if(Ziffer.equals("5"))
        {
            if(Vergleich[4] == true)
            {
                return false;
            }
            Vergleich[4] = true;
            return true;
        }        
        else if(Ziffer.equals("6"))
        {
            if(Vergleich[5] == true)
            {
                return false;
            }
            Vergleich[5] = true;
            return true;
        }
        else if(Ziffer.equals("7"))
        {
            if(Vergleich[6] == true)
            {
                return false;
            }
            Vergleich[6] = true;
            return true;
        }
        else if(Ziffer.equals("8"))
        {
            if(Vergleich[7] == true)
            {
                return false;
            }
            Vergleich[7] = true;
            return true;
        }
        if(Ziffer.equals("9"))
        {
            if(Vergleich[8] == true)
            {
                return false;
            }
            Vergleich[8] = true;
            return true;
        }
        else if(Ziffer.equals(""))
        {
            return true;
        }
        return false;// Erfordete Rückgabe, dürfte nicht vorkommen
    }

    public void Notizen()// Wenn keine Zahl drin steht alle Möglichkeiten auf true setzen( in Notizen_Gesamt und Notizen_Quadrate)
    {
        if(Gesamt[y][x].equals(""))
        {
            Wo_in_Quadrate(y,x);
            for(int g = 0; g<9;g++)
            {
                Notizen_Gesamt[y][x][g] = true;
                Notizen_Quadrate[q][r][s][g] = true;
            }
        }
    }

    public void Notizen_kürzen()// Das kürzen der Notizen, erforderlich am Anfang und nach jeder Ziffereintragung, da 
    {                           // sich dadurch natürlich die Möglichkeiten ändern
        q = 0;
        r = 0;
        s = 0;
        x5 = 0;
        y5 = 0;
        for(y6 = 0; y6 < 9 ; y6++)//Das durchgehen jeder einzelner Stelle
        {
            for(x6 = 0; x6 < 9 ; x6++)
            {
                for(int g = 0;g<18;g++)//Kombination von waagrechter und senkrechter Überprüfung
                {
                    if(g == 0)// Am Anfang die y-Position übernehmen und die zweite x-Position auf null setzen zur waagrechten Überprüfung
                    {
                        y5 = y6;
                        x5 = 0;
                    }
                    Wo_in_Quadrate(y6,x6);
                    if(Gesamt[y5][x5].equals("1"))// Das elimineren der Möglichkeiten
                    {
                        Notizen_Gesamt[y6][x6][0] = false;
                        Notizen_Quadrate[q][r][s][0] = false;
                    }
                    else if(Gesamt[y5][x5].equals("2"))
                    {
                        Notizen_Gesamt[y6][x6][1] = false;
                        Notizen_Quadrate[q][r][s][1] = false;
                    }
                    else if(Gesamt[y5][x5].equals("3"))
                    {
                        Notizen_Gesamt[y6][x6][2] = false;
                        Notizen_Quadrate[q][r][s][2] = false;
                    }        
                    else if(Gesamt[y5][x5].equals("4"))
                    {
                        Notizen_Gesamt[y6][x6][3] = false;
                        Notizen_Quadrate[q][r][s][3] = false;
                    }        
                    else if(Gesamt[y5][x5].equals("5"))
                    {
                        Notizen_Gesamt[y6][x6][4] = false;
                        Notizen_Quadrate[q][r][s][4] = false;
                    }        
                    else if(Gesamt[y5][x5].equals("6"))
                    {
                        Notizen_Gesamt[y6][x6][5] = false;
                        Notizen_Quadrate[q][r][s][5] = false;
                    }
                    else if(Gesamt[y5][x5].equals("7"))
                    {
                        Notizen_Gesamt[y6][x6][6] = false;
                        Notizen_Quadrate[q][r][s][6] = false;
                    }
                    else if(Gesamt[y5][x5].equals("8"))
                    {
                        Notizen_Gesamt[y6][x6][7] = false;
                        Notizen_Quadrate[q][r][s][7] = false;
                    }
                    else if(Gesamt[y5][x5].equals("9"))
                    {
                        Notizen_Gesamt[y6][x6][8] = false;
                        Notizen_Quadrate[q][r][s][8] = false;
                    }
                    if(g<8)//weiterrücken in der Reihe, wenn noch die Reihe überprüft wird
                    {
                        x5++;
                    }
                    else if(g == 8)// Wenn es von Reihen zur Spaltenüberprüfung wechselt, die erste x-Position übernehemen und
                    {
                        x5 = x6;// Die zweite y-Position gleich der ersten setzen
                        y5 = 0;
                    }
                    else if ( g>8)// weiterrücken in der Spalte, wenn die Spalte überprüft wird
                    {
                        y5++;
                    }
                }
                Wo_in_Quadrate(y6,x6);
                for(int r1 = 0; r1<3;r1++)// Das eliminieren von Möglichkeiten durch den Vergleich im Quadrat 
                {
                    for(int s1 = 0;s1<3; s1++)//oben: weiter nach unten, hier: weiter nach rechts
                    {
                        if(Quadrate[q][r1][s1].equals("1"))
                        {
                            Notizen_Gesamt[y6][x6][0] = false;
                            Notizen_Quadrate[q][r][s][0] = false;
                        }
                        else if(Quadrate[q][r1][s1].equals("2"))
                        {
                            Notizen_Gesamt[y6][x6][1] = false;
                            Notizen_Quadrate[q][r][s][1] = false;
                        }
                        else if(Quadrate[q][r1][s1].equals("3"))
                        {
                            Notizen_Gesamt[y6][x6][2] = false;
                            Notizen_Quadrate[q][r][s][2] = false;
                        }        
                        else if(Quadrate[q][r1][s1].equals("4"))
                        {
                            Notizen_Gesamt[y6][x6][3] = false;
                            Notizen_Quadrate[q][r][s][3] = false;
                        }           
                        else if(Quadrate[q][r1][s1].equals("5"))
                        {
                            Notizen_Gesamt[y6][x6][4] = false;
                            Notizen_Quadrate[q][r][s][4] = false;
                        }        
                        else if(Quadrate[q][r1][s1].equals("6"))
                        {
                            Notizen_Gesamt[y6][x6][5] = false;
                            Notizen_Quadrate[q][r][s][5] = false;
                        }
                        else if(Quadrate[q][r1][s1].equals("7"))
                        {
                            Notizen_Gesamt[y6][x6][6] = false;
                            Notizen_Quadrate[q][r][s][6] = false;
                        }
                        else if(Quadrate[q][r1][s1].equals("8"))
                        {
                            Notizen_Gesamt[y6][x6][7] = false;
                            Notizen_Quadrate[q][r][s][7] = false;
                        }
                        else if(Quadrate[q][r1][s1].equals("9"))
                        {
                            Notizen_Gesamt[y6][x6][8] = false;
                            Notizen_Quadrate[q][r][s][8] = false;
                        }
                    }
                }
            }
        }
        for(y6 = 0;y5<9;y5++)// Das elemenieren von Notizen, wenn zwei Felder mit zwei identschen Notizen in einer Reihe o.ä ist
        {
            for(x6 = 0; x5<9;x5++)// Genauere Erklärung im Projektheft
            {
                if(Anzahl_der_Notizen_Feld (y6,x6) == 2)
                {
                    identische_Notizen_Spalte_el(y6,x6);
                    identische_Notizen_Reihe_el(y6,x6);
                    Wo_in_Quadrate(y6,x6);
                    identische_Notizen_Quadrat_el(q,r,s);
                }
            }
        }
    }

    public void identische_Notizen_Quadrat_el(int q, int r, int s)
    {
        r5 = r;
        s5 = s;
        if(identische_Notizen_Quadrat(q,r,s))//Überprüfung ob es zwei gleiche gibt
        {
            for(r = 0; r<3; r++)//Durchgehen aller Felder im Quadrat
            {
                for(s=0; s<3;s++)
                {
                    if((r != r5 && s != s5) || (r != r6 && s != s6))//elimination der notizen außer bei den zwei Feldern
                    {
                        for(int g = 0; g<9;g++)
                        {
                            if(Vergleich[g])
                            {
                                Notizen_Quadrate[q][r][s][g] = false;
                                Wo_in_Gesamt(q,r,s);
                                Notizen_Gesamt[y][x][g] = false;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean identische_Notizen_Quadrat(int q, int r, int s)
    {
        int f = 0;
        for(int r2 = 0;r<3;r++)
        {
            for(int s2 = 0;s<3;s++)
            {
                Wo_in_Gesamt(q,r2,s2);
                if(Anzahl_der_Notizen_Feld(y,x) == 2 && (r2 != r && s2 != s))// Überprüfen ob es ein weiteres Feld mit zwei Notizen gibt
                {
                    for(int g = 0; g<9;g++)
                    {
                        if(Notizen_Quadrate[q][r2][s2][g] && Notizen_Quadrate[q][r][s][g])//Überprüfen ob sie übereinstimmen
                        {
                            f++;
                        }
                    }
                    if(f == 2)
                    {
                        r6 = r;
                        s6 = s;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void identische_Notizen_Spalte_el(int y6, int x5)
    {
        if(identische_Notizen_Spalte(y6,x5))
        {
            for(y5 = 0; y5<9; y5++)
            {
                if(y5 != y7 && y5 == y6)
                {
                    for(int g = 0; g<9;g++)
                    {
                        if(Vergleich[g])
                        {
                            Notizen_Gesamt[y5][x5][g] = false;
                            Wo_in_Quadrate(y5,x5);
                            Notizen_Quadrate[q][r][s][g] = false;
                        }
                    }
                }
            }
        }
    }

    public boolean identische_Notizen_Spalte(int y6, int x5)
    {
        int f = 0;
        for(y5 = 0; y5<9; y5++)
        {
            if(Anzahl_der_Notizen_Feld (y5,x5) == 2 && y5 != y6)
            {
                for(int g = 0; g<9;g++)
                {
                    if(Notizen_Gesamt[y5][x5][g] && Notizen_Gesamt[y6][x5][g])
                    {
                        f++;
                    }
                }
                if(f == 2)
                {
                    y7 = x5;
                    return true;
                }
            }
        }
        return false;
    }

    public void identische_Notizen_Reihe_el(int y5, int x6)
    {
        if(identische_Notizen_Reihe(y5,x6))
        {
            for(x5 = 0; x5<9; x5++)
            {
                if(x5 != x7 && x5 != x6)
                {
                    for(int g = 0; g<9;g++)
                    {
                        if(Vergleich[g])
                        {
                            Notizen_Gesamt[y5][x5][g] = false;
                            Wo_in_Quadrate(y5,x5);
                            Notizen_Quadrate[q][r][s][g] = false;
                        }
                    }
                }
            }
        }
    }

    public boolean identische_Notizen_Reihe(int y5, int x6)
    {
        int f = 0;
        for(x5 = 0; x5<9; x5++)
        {
            if(Anzahl_der_Notizen_Feld (y5,x5) == 2 && x5 != x6)
            {
                for(int g = 0; g<9;g++)
                {
                    if(Notizen_Gesamt[y5][x6][g] && Notizen_Gesamt[y5][x6][g])
                    {
                        f++;
                    }
                }
                if(f == 2)
                {
                    x7 = x5;
                    return true;
                }
            }
        }
        return false;
    }

    public void Wo_in_Quadrate(int y2, int x2)
    {
        if(y2<3)
        {
            q = 0;
            r = y2;
        }
        else if(y2<6)
        {
            q = 3;
            r = y2-3;
        }
        else
        {
            q = 6;
            r = y2-6;
        }
        if (x2<3)
        {
            s = x2;
        }
        else if(x2<6)
        {
            q += 1;
            s = x2-3;
        }
        else
        {
            q += 2;
            s = x2-6;
        }
    }

    public void Lösungsweg1()
    {
        int a = 0;
        for(y = 0;y<9;y++)
        {
            for(x = 0;x<9; x++)
            {
                if(Anzahl_der_Notizen_Feld(y,x) == 1)
                { 
                    a = 0;
                    while(Notizen_Gesamt[y][x][a] == false)
                    {
                        a++;
                    }
                    Wo_in_Quadrate(y,x);
                    Zahl_eintragen(a,y,x,q,r,s);
                }
            }
        }
    }

    public void Zahl_eintragen(int a1, int y8, int x8, int q5, int r5, int s5)
    {
        switch (a1)
        {
            case 0:
                Gesamt[y8][x8] = "1";
                Quadrate[q5][r5][s5] = "1";
                break;
            case 1:
                Gesamt[y8][x8] = "2";
                Quadrate[q5][r5][s5] = "2";
                break;
            case 2:
                Gesamt[y8][x8] = "3";
                Quadrate[q5][r5][s5] = "3";
                break;
            case 3:
                Gesamt[y8][x8] = "4";
                Quadrate[q5][r5][s5] = "4";
                break;
            case 4:
                Gesamt[y8][x8] = "5";
                Quadrate[q5][r5][s5] = "5";
                break;
            case 5:
                Gesamt[y8][x8] = "6";
                Quadrate[q5][r5][s5] = "6";
                break;
            case 6:
                Gesamt[y8][x8] = "7";
                Quadrate[q5][r5][s5] = "7";
                break;
            case 7:
                Gesamt[y8][x8] = "8";
                Quadrate[q5][r5][s5] = "8";
                break;
            case 8:
                Gesamt[y8][x8] = "9";
                Quadrate[q5][r5][s5] = "9";
                break;
        }
        for(int g = 0;g<9;g++)
        {
            Notizen_Gesamt[y8][x8][g] = false;
            Notizen_Quadrate[q5][r5][s5][g] = false;
        }
        Notizen_kürzen();
    }

    public int Anzahl_der_Notizen_Feld(int y4, int x4)
    {
        int a = 0;
        for(int g = 0; g<9; g++)
        {
            if(Notizen_Gesamt[y4][x4][g] == true)
            {
                a++;
            }
        }
        return a;
    }

    public void Lösungsweg2 ()
    {
        x = 0;
        y = 0;
        for(x = 0;x<9;x++)
        {
            Anzahl_der_Notizen_Spalte(x);
            for(int a = 0;a<9;a++)
            {
                if(Anzahl_der_Notizen [a] == 1)
                {
                    for(y = 0; y<9;y++)
                    {
                        if(Notizen_Gesamt[y][x][a])
                        {
                            Wo_in_Quadrate(y,x);
                            Zahl_eintragen(a,y,x,q,r,s);
                        }
                    }
                }
            }
        }

        x = 0;
        y = 0;
        for(y = 0;y<9;y++)
        {
            Anzahl_der_Notizen_Reihe(y);
            for(int a = 0;a<9;a++)
            {
                if(Anzahl_der_Notizen [a] == 1)
                {
                    for(x = 0; x<9;x++)
                    {
                        if(Notizen_Gesamt[y][x][a])
                        {
                            Wo_in_Quadrate(y,x);
                            Zahl_eintragen(a,y,x,q,r,s);
                        }
                    }
                }
            }
        }

        q4 = 0;
        r4 = 0;
        s4 = 0;
        for(q4 = 0; q4<9;q4++)
        {
            Anzahl_der_Notizen_Quadrat(q4);
            for(int a = 0;a<9;a++)
            {
                if(Anzahl_der_Notizen [a] == 1)
                {
                    for(r4 = 0; r4<3;r4++)
                    {
                        for(s4 = 0;s4<3;s4++)
                        {
                            if(Notizen_Quadrate[q4][r4][s4][a])
                            {
                                Wo_in_Gesamt(q4,r4,s4);
                                Zahl_eintragen(a,y,x,q4,r4,s4);
                            }
                        }
                    }
                }
            }
        }
    }

    public void Anzahl_der_Notizen_Spalte (int x)
    {
        for(int g = 0; g<9;g++)
        {
            Anzahl_der_Notizen[g] = 0;
        }
        for(int y5 = 0; y5<9; y5++)
        {
            Zählt_die_Notizen_normal(y5, x);
        }
    }

    public void Anzahl_der_Notizen_Reihe (int y)
    {
        for(int g = 0; g<9;g++)
        {
            Anzahl_der_Notizen[g] = 0;
        }
        for(int x5 = 0; x5<9; x5++)
        {
            Zählt_die_Notizen_normal(y, x5);
        }
    }

    public void Anzahl_der_Notizen_Quadrat(int q)
    {
        for(int g = 0; g<9;g++)
        {
            Anzahl_der_Notizen[g] = 0;
        }
        for(r = 0;r<3;r++)
        {
            for(s = 0;s<3;s++)
            {
                for(int g = 0;g<9;g++)
                {
                    if(Notizen_Quadrate[q][r][s][g])
                    {
                        Anzahl_der_Notizen[g]++;    
                    }
                }
            }
        }
    }

    public void Zählt_die_Notizen_normal(int y7, int x7)
    {

        for(int g = 0;g<9;g++)
        {
            if(Notizen_Gesamt[y7][x7][g])
            {
                Anzahl_der_Notizen[g]++;
            }
        }
    }

    public void Wo_in_Gesamt(int q1, int r1, int s1)
    {
        y = r1;
        x = s1;
        switch(q1)
        {
            case 0:
                break;
            case 1:
                x += 3;
                break;
            case 2:
                x += 6;
                break;
            case 3:
                y += 3;
                break;
            case 4:
                x += 3;
                y += 3;
                break;
            case 5:
                x += 6;
                y += 3;
                break;
            case 6:
                y += 6;
                break;
            case 7:
                x += 3;
                y += 6;
                break;
            case 8:
                x += 6;
                y += 6;
                break;
        }
    }

    public void _2_mal_2_Möglichkeiten()
    {
        for(q = 0;q<9;q++)
        {
            Anzahl_der_Notizen_Quadrat(q);
            _2_mal_2_Möglichkeiten_Quadrat();
        }
    }

    public boolean _2_mal_2_Möglichkeiten_Quadrat()
    {
        for(int g = 0;q<9;g++)
        {
            if(Anzahl_der_Notizen[g] == 2)
            {
                c = g;
                while(g<9)
                {
                    if(Anzahl_der_Notizen[g] == 2)
                    {
                        v = g;
                        return true;
                    }
                    g++;
                }
            }
        }
        return false;
    }

}
