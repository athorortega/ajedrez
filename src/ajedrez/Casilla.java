package ajedrez;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Casilla {

    int x, y;
    ImageIcon imagenIcono;
    ImageIcon imagenIcono2;
    ImageIcon imagenIcono3;
    ImageIcon imagenIcono4;
    Image imagenCasilla;
    Image imagenCasillaSeleccionada;
    Image imagenCasillaPosible;
    Image imagenCasillaComer;
    Image imagenActual;
    Color c;
    

    Casilla(int x, int y) {
        this.x = x;
        this.y = y;
//        if ((x + y) % 2 == 0) {
//            c = new Color(255, 255, 255);
//        } else {
//            c = new Color(0, 0, 0);

        if ((x + y) % 2 == 0) {
            imagenIcono = new ImageIcon("imagenes/casillas/casillablanca.jpg");
            imagenIcono2 = new ImageIcon("imagenes/casillas/casillablancaseleccionada.jpg");
            imagenIcono3 = new ImageIcon("imagenes/casillas/casillablancaposible.jpg");
            imagenIcono4 = new ImageIcon("imagenes/casillas/casillablancacomer.jpg");
        } else {
            imagenIcono = new ImageIcon("imagenes/casillas/casillanegra.jpg");
            imagenIcono2 = new ImageIcon("imagenes/casillas/casillanegraseleccionada.jpg");
            imagenIcono3 = new ImageIcon("imagenes/casillas/casillanegraposible.jpg");
            imagenIcono4 = new ImageIcon("imagenes/casillas/casillanegracomer.jpg");
        }
        imagenCasilla = imagenIcono.getImage();
        imagenCasillaSeleccionada = imagenIcono2.getImage();
        imagenCasillaPosible=imagenIcono3.getImage();
        imagenCasillaComer=imagenIcono4.getImage();
        imagenActual = imagenCasilla;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void seleccionar() {
        imagenActual = imagenCasillaSeleccionada;
    }

    public void deseleccionar() {
        imagenActual = imagenCasilla;
    }
    
    public void posible() {
        imagenActual = imagenCasillaPosible;
    }
    
    public void comible() {
        imagenActual = imagenCasillaComer;
    }
    
}
