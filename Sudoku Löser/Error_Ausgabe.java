import java.awt.*;
import java.awt.event.*;

public class Error_Ausgabe
{
    Frame f;
    Label l;
    public Error_Ausgabe(int y, int x)
    {
        f = new Frame("Error");
        f.setSize(200,100);
        f.setLocation (500,400);
        f.setVisible(true);
        f.setLayout(null);
        l = new Label();
        l.setText("         Fehler bei "+ y + x);
        l.setLocation(5,20);
        l.setSize(200,100);
        l.setForeground(new Color (255,0,0));
        l.setBackground( new Color (0,0,0));
        f.add(l);
        f.addWindowListener(new WindowAdapter() // Die Funktionsfähigkeit des Schließen Knopfs
            {
                public void windowClosing(WindowEvent e)
                {
                    f.setVisible(false);
                }
            });
    }

}
