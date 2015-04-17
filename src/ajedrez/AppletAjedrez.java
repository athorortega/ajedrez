package ajedrez;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.*;

public class AppletAjedrez extends Applet implements MouseListener {

    Tablero t;
    int margen = 40;
    int casillaAnteriorX = -1;
    int casillaAnteriorY = -1;
    int casillaAnteriorPressX = -1;
    int casillaAnteriorPressY = -1;
    Casilla aux;
    String turno = "blanco";
    Button b1, b2, b3;
    Label label1;
    TextField tf1,tf2;

    private Font fuente;
    private Image imagenDB;
    private Graphics gDB;
    private AudioClip sonido1, sonido2;

    public Graphics getgDB() {
        return gDB;
    }

    @Override
    public void init() {
        setBackground(Color.GRAY);
        t = new Tablero(this);
        this.addMouseListener(this);
        try {
            sonido1 = getAudioClip(new File("sonidos/click.wav").toURI().toURL());
            sonido2 = getAudioClip(new File("sonidos/mover.wav").toURI().toURL());
        } catch (Exception e) {
        }
        fuente = new Font("Serif", Font.BOLD, 24);
        
        setFont(fuente);
        
        b1 = new Button("Botón 1");
        b2 = new Button("Botón 2");
        b3 = new Button("Botón 3");
        b1.setForeground(Color.white);
        b1.setBackground(Color.black);
        b2.setForeground(Color.red);
        b2.setBackground(Color.yellow);
        b3.setForeground(Color.blue);
        b3.setBackground(Color.green);
        
        
        label1=new Label();
        
        label1.setText("Etiqueta 1");
        label1.setForeground(Color.MAGENTA);
        label1.setBackground(Color.pink);
        label1.setAlignment(Label.CENTER);
        
        add(label1);
        
        b1.setBounds(10, 10, 10, 10);
        add(b1);
        add(b2);
        add(b3);
        
        tf1=new TextField("Cuadro de texto 1",25);
        tf2=new TextField("Cuadro de texto 2",25);
        
        add(tf1);add(tf2);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void paint(Graphics g) {
        t.pintar_tablero(g);
        g.setFont(fuente);

        if (turno == "blanco") {
            g.setColor(Color.WHITE);
            g.drawString("Blancas", getWidth() / 2 - margen, 25);
        } else {
            g.setColor(Color.BLACK);
            g.drawString("Negras", getWidth() / 2 - margen, 25);
        }
    }

    @Override
    public void update(Graphics g) {
        if (imagenDB == null) {
            imagenDB = createImage(getWidth(), getHeight());
            gDB = imagenDB.getGraphics();
        }
        gDB.setColor(getBackground());
        gDB.fillRect(0, 0, getWidth(), getHeight());
        gDB.setColor(getForeground());
        paint(gDB);
        g.drawImage(imagenDB, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        System.out.println("Clicked(" + e.getButton() + "): " + e.getX() + "," + e.getY());
        int casillaX = ((e.getX() - margen) / t.tamaño);
        int casillaY = ((e.getY() - margen) / t.tamaño);
        System.out.println(casillaX + "  " + casillaY);

        try {
            //No es la primera vez que se selecciona una casilla
            if (casillaAnteriorX != -1 && casillaAnteriorY != -1) {
                t.casillas[casillaAnteriorX][casillaAnteriorY].deseleccionar();
                //La casilla que hemos seleccionado anteriormente contiene una ficha
                //El color de la ficha de la casilla anteriormente seleccionada corresponde con su turno
                if (t.casillaContieneFicha(t.casillas[casillaAnteriorX][casillaAnteriorY]) && t.fichas[casillaAnteriorX][casillaAnteriorY].getColor() == turno) {
                    //La casilla donde queremos mover CONTIENE ficha
                    t.casillas[casillaX][casillaY].seleccionar();

                    if (t.casillaContieneFicha(t.casillas[casillaX][casillaY])) {
                        sonido1.play();
                        //El color de la ficha que queremos mover es diferente a la ficha que hay en la casilla donde queremos mover la anterior
                        if (t.fichas[casillaAnteriorX][casillaAnteriorY].getColor() != t.fichas[casillaX][casillaY].getColor()) {
                            //restriccion de movimiento solo a casilla donde puede comer
                            if (t.comestibles(t.fichas[casillaAnteriorX][casillaAnteriorY]).contains(t.casillas[casillaX][casillaY])) {
                                t.mover_ficha(casillaAnteriorX, casillaAnteriorY, casillaX, casillaY);
                                sonido2.play();
                                if (turno == "blanco") {
                                    turno = "negro";
                                } else {
                                    turno = "blanco";
                                }
                            }
                        }
                        //La casilla donde queremos mover NO CONTIENE ficha
                    } else {
                        //restriccion de movimiento solo a casilla donde puede moverse
                        if (t.posibilidades(t.fichas[casillaAnteriorX][casillaAnteriorY]).contains(t.casillas[casillaX][casillaY])) {
                            t.mover_ficha(casillaAnteriorX, casillaAnteriorY, casillaX, casillaY);
                            sonido2.play();
                            if (turno == "blanco") {
                                turno = "negro";
                            } else {
                                turno = "blanco";
                            }
                        }
                    }
                }
            } else {
                sonido1.play();
                t.casillas[casillaX][casillaY].seleccionar();
            }
        } catch (Exception z) {
        }

        casillaAnteriorX = casillaX;
        casillaAnteriorY = casillaY;

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Pressed(" + e.getButton() + "): " + e.getX() + "," + e.getY());
        int casillaX = ((e.getX() - margen) / t.tamaño);
        int casillaY = ((e.getY() - margen) / t.tamaño);

        try {
            for (int i = 0; i <= t.posibilidades(t.fichas[casillaX][casillaY]).capacity(); i++) {
                ((Casilla) t.posibilidades(t.fichas[casillaX][casillaY]).elementAt(i)).posible();
            }

            for (int i = 0; i <= t.comestibles(t.fichas[casillaX][casillaY]).capacity(); i++) {
                ((Casilla) t.comestibles(t.fichas[casillaX][casillaY]).elementAt(i)).comible();
            }
        } catch (Exception z) {
            System.out.println("Error al mostrar posibilidades de movimiento o de comer");
        }
        casillaAnteriorPressX = casillaX;
        casillaAnteriorPressY = casillaY;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Pressed(" + e.getButton() + "): " + e.getX() + "," + e.getY());

        try {
            for (int i = 0; i < t.posibilidades(t.fichas[casillaAnteriorPressX][casillaAnteriorPressY]).capacity(); i++) {
                ((Casilla) t.posibilidades(t.fichas[casillaAnteriorPressX][casillaAnteriorPressY]).elementAt(i)).deseleccionar();
            }

            for (int i = 0; i < t.comestibles(t.fichas[casillaAnteriorPressX][casillaAnteriorPressY]).capacity(); i++) {
                ((Casilla) t.comestibles(t.fichas[casillaAnteriorPressX][casillaAnteriorPressY]).elementAt(i)).deseleccionar();
            }
        } catch (Exception z) {
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Entered(" + e.getButton() + "): " + e.getX() + "," + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Exited(" + e.getButton() + "): " + e.getX() + "," + e.getY());
    }
}
