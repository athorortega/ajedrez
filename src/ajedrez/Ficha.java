package ajedrez;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Ficha {

    Casilla casilla;
    public Image imagen;
    public ImageIcon imagenIcono;
    public String tipo, color;
    public int movimientos;

    public Ficha(Casilla casilla, String tipo, String color) {
        this.casilla = casilla;
        this.tipo = tipo;
        this.color = color;
        if (color == "blanco") {
            imagenIcono = new ImageIcon("imagenes/blanco/" + tipo + ".png");
        } else {
            imagenIcono = new ImageIcon("imagenes/negro/" + tipo + ".png");
        }
        imagen = imagenIcono.getImage();
    }

    public void mover(Casilla casilla) {
        this.casilla = casilla;
        this.movimientos++;
    }

    public String getTipo() {
        return tipo;
    }

    public String getColor() {
        return color;
    }

    public int getMovimientos() {
        return movimientos;
    }
}
