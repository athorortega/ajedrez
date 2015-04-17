package ajedrez;

import java.awt.Graphics;
import java.util.Vector;

public class Tablero {

    private final int N = 8;
    Casilla casillas[][];
    Ficha fichas[][];
    AppletAjedrez applet;
    private int margen;
    int tamaño;

    Tablero(AppletAjedrez applet) {
        this.applet = applet;
        casillas = new Casilla[N][N];
        fichas = new Ficha[N][N];
        margen = applet.margen;
        tamaño = (menor(applet.getWidth(), applet.getHeight()) - margen * 2) / N;
        crear_casillas();
        crear_fichas();
    }

    void crear_casillas() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
    }

    void crear_fichas() {
        //primera fila blancas
        fichas[0][7] = new Ficha(casillas[0][7], "torre", "blanco");
        fichas[7][7] = new Ficha(casillas[7][7], "torre", "blanco");
        fichas[1][7] = new Ficha(casillas[1][7], "caballo", "blanco");
        fichas[6][7] = new Ficha(casillas[6][7], "caballo", "blanco");
        fichas[2][7] = new Ficha(casillas[2][7], "alfil", "blanco");
        fichas[5][7] = new Ficha(casillas[5][7], "alfil", "blanco");
        fichas[3][7] = new Ficha(casillas[0][7], "reina", "blanco");
        fichas[4][7] = new Ficha(casillas[7][7], "rey", "blanco");
        //peones blancos
        for (int i = 0; i < N; i++) {
            fichas[i][6] = new Ficha(casillas[i][6], "peon", "blanco");
        }
        //primera fila negras
        fichas[0][0] = new Ficha(casillas[0][0], "torre", "negro");
        fichas[7][0] = new Ficha(casillas[7][0], "torre", "negro");
        fichas[1][0] = new Ficha(casillas[1][0], "caballo", "negro");
        fichas[6][0] = new Ficha(casillas[6][0], "caballo", "negro");
        fichas[2][0] = new Ficha(casillas[2][0], "alfil", "negro");
        fichas[5][0] = new Ficha(casillas[5][0], "alfil", "negro");
        fichas[3][0] = new Ficha(casillas[0][0], "reina", "negro");
        fichas[4][0] = new Ficha(casillas[7][0], "rey", "negro");
        //peones negros
        for (int i = 0; i < N; i++) {
            fichas[i][1] = new Ficha(casillas[i][1], "peon", "negro");
        }
    }

    void mover_ficha(int xOrigen, int yOrigen, int xDestino, int yDestino) {
        fichas[xDestino][yDestino] = fichas[xOrigen][yOrigen];
        fichas[xOrigen][yOrigen].mover(casillas[xDestino][yDestino]);
        fichas[xOrigen][yOrigen] = null;
    }

//      Se puede hacer mas simple    
//    boolean casillaAnteriorContieneFicha(Casilla casillaAnterior) {
//        return fichas[casillaAnterior.getX()][casillaAnterior.getY()].casilla.getX() == casillaAnterior.getX() && fichas[casillaAnterior.getX()][casillaAnterior.getY()].casilla.getY() == casillaAnterior.getY();
//    }
    boolean casillaContieneFicha(Casilla casilla) {
        return fichas[casilla.getX()][casilla.getY()] != null;
    }

    int menor(int x, int y) {
        if (x < y) {
            return x;
        } else {
            return y;
        }
    }

    void pintar_tablero(Graphics g) {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                g.setColor(casillas[i][j].c);
                g.drawImage(casillas[i][j].imagenActual, margen + i * tamaño, margen + j * tamaño, tamaño, tamaño, applet);
                if (fichas[i][j] != null) {
                    g.drawImage(fichas[i][j].imagen, margen + i * tamaño, margen + j * tamaño, tamaño, tamaño, applet);
                }
            }
        }
    }

    Vector posibilidades(Ficha ficha) {
        String tipo = ficha.getTipo();
        String color = ficha.getColor();
        int movimientosFicha = ficha.getMovimientos();
        int casillaX = ficha.casilla.getX();
        int casillaY = ficha.casilla.getY();
        Vector posibilidadesM = new Vector();

        //La ficha es de tipo peon
        if (tipo == "peon") {
            //la ficha es de color blanco
            if (color == "blanco") {
                //es su primer movimiento
                if (movimientosFicha == 0) {
                    //la primera casilla de enfrente esta vacia
                    if (!casillaContieneFicha(casillas[casillaX][casillaY - 1])) {
                        posibilidadesM.addElement(casillas[casillaX][casillaY - 1]);
                    }
                    //la primera y segunda casillas de enfrente estan vacias
                    if (!casillaContieneFicha(casillas[casillaX][casillaY - 1]) && !casillaContieneFicha(casillas[casillaX][casillaY - 2])) {
                        posibilidadesM.addElement(casillas[casillaX][casillaY - 2]);
                    }
                    //no es su primer movimiento
                } else {
                    //la primera casilla de enfrente esta vacia
                    if (!casillaContieneFicha(casillas[casillaX][casillaY - 1])) {
                        posibilidadesM.addElement(casillas[casillaX][casillaY - 1]);
                    }
                }
                //la ficha es negra
            } else {
                //es su primer movimiento
                if (movimientosFicha == 0) {
                    //la primera casilla de enfrente esta vacia
                    if (!casillaContieneFicha(casillas[casillaX][casillaY + 1])) {
                        posibilidadesM.addElement(casillas[casillaX][casillaY + 1]);
                    }
                    //la primera y segunda casillas de enfrente estan vacias
                    if (!casillaContieneFicha(casillas[casillaX][casillaY + 1]) && !casillaContieneFicha(casillas[casillaX][casillaY + 2])) {
                        posibilidadesM.addElement(casillas[casillaX][casillaY + 2]);
                    }
                    //no es su primer movimiento
                } else {
                    if (!casillaContieneFicha(casillas[casillaX][casillaY + 1])) {
                        posibilidadesM.addElement(casillas[casillaX][casillaY + 1]);
                    }
                }
            }
        }
        if (tipo == "torre") {
            try {
                for (int i = casillaY - 1; i >= 0; i--) {
                    if (!casillaContieneFicha(casillas[casillaX][i])) {
                        posibilidadesM.addElement(casillas[casillaX][i]);
                    } else {
                        i = 0;
                    }
                }
                for (int i = casillaX - 1; i >= 0; i--) {
                    if (!casillaContieneFicha(casillas[i][casillaY])) {
                        posibilidadesM.addElement(casillas[i][casillaY]);
                    } else {
                        i = 0;
                    }
                }
                for (int i = casillaY + 1; i <= 7; i++) {
                    if (!casillaContieneFicha(casillas[casillaX][i])) {
                        posibilidadesM.addElement(casillas[casillaX][i]);
                    } else {
                        i = 7;
                    }
                }
                for (int i = casillaX + 1; i <= 7; i++) {
                    if (!casillaContieneFicha(casillas[i][casillaY])) {
                        posibilidadesM.addElement(casillas[i][casillaY]);
                    } else {
                        i = 7;
                    }
                }
            } catch (Exception e) {
                System.out.println("error movimiento torre");
            }
        }
        if (tipo == "caballo") {
            try {
                if (!casillaContieneFicha(casillas[casillaX - 1][casillaY - 2])) {
                    posibilidadesM.addElement(casillas[casillaX - 1][casillaY - 2]);
                }
                if (!casillaContieneFicha(casillas[casillaX + 1][casillaY - 2])) {
                    posibilidadesM.addElement(casillas[casillaX + 1][casillaY - 2]);
                }
                if (!casillaContieneFicha(casillas[casillaX + 2][casillaY - 1])) {
                    posibilidadesM.addElement(casillas[casillaX + 2][casillaY - 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX - 2][casillaY - 1])) {
                    posibilidadesM.addElement(casillas[casillaX - 2][casillaY - 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX - 2][casillaY + 1])) {
                    posibilidadesM.addElement(casillas[casillaX - 2][casillaY + 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX + 2][casillaY + 1])) {
                    posibilidadesM.addElement(casillas[casillaX + 2][casillaY + 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX + 1][casillaY + 2])) {
                    posibilidadesM.addElement(casillas[casillaX + 1][casillaY + 2]);
                }
                if (!casillaContieneFicha(casillas[casillaX - 1][casillaY + 2])) {
                    posibilidadesM.addElement(casillas[casillaX - 1][casillaY + 2]);
                }
            } catch (Exception e) {
                System.out.println("error movimiento caballo");
            }
        }

        if (tipo == "alfil") {

            for (int i = casillaY; i <= casillaY + 7; i++) {

                if (!casillaContieneFicha(casillas[i][i])) {
                    posibilidadesM.addElement(casillas[i][i]);
                }
            }
        }

        if (tipo == "reina") {
            try {
                //movimientos torre
                for (int i = casillaY - 1; i >= 0; i--) {
                    if (!casillaContieneFicha(casillas[casillaX][i])) {
                        posibilidadesM.addElement(casillas[casillaX][i]);
                    } else {
                        i = 0;
                    }
                }
                for (int i = casillaX - 1; i >= 0; i--) {
                    if (!casillaContieneFicha(casillas[i][casillaY])) {
                        posibilidadesM.addElement(casillas[i][casillaY]);
                    } else {
                        i = 0;
                    }
                }
                for (int i = casillaY + 1; i <= 7; i++) {
                    if (!casillaContieneFicha(casillas[casillaX][i])) {
                        posibilidadesM.addElement(casillas[casillaX][i]);
                    } else {
                        i = 7;
                    }
                }
                for (int i = casillaX + 1; i <= 7; i++) {
                    if (!casillaContieneFicha(casillas[i][casillaY])) {
                        posibilidadesM.addElement(casillas[i][casillaY]);
                    } else {
                        i = 7;
                    }
                }

                //aqui van los moviemientos del alfil
            } catch (Exception e) {
                System.out.println("error movimiento reina");
            }
        }

        if (tipo == "rey") {
            try {
                if (!casillaContieneFicha(casillas[casillaX - 1][casillaY - 1])) {
                    posibilidadesM.addElement(casillas[casillaX - 1][casillaY - 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX + 1][casillaY - 1])) {
                    posibilidadesM.addElement(casillas[casillaX + 1][casillaY - 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX][casillaY - 1])) {
                    posibilidadesM.addElement(casillas[casillaX][casillaY - 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX + 1][casillaY])) {
                    posibilidadesM.addElement(casillas[casillaX + 1][casillaY]);
                }
                if (!casillaContieneFicha(casillas[casillaX - 1][casillaY])) {
                    posibilidadesM.addElement(casillas[casillaX - 1][casillaY]);
                }
                if (!casillaContieneFicha(casillas[casillaX + 1][casillaY + 1])) {
                    posibilidadesM.addElement(casillas[casillaX + 1][casillaY + 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX - 1][casillaY + 1])) {
                    posibilidadesM.addElement(casillas[casillaX - 1][casillaY + 1]);
                }
                if (!casillaContieneFicha(casillas[casillaX][casillaY + 1])) {
                    posibilidadesM.addElement(casillas[casillaX][casillaY + 1]);
                }
            } catch (Exception e) {
                System.out.println("error movimiento rey");
            }
        }
        return posibilidadesM;
    }

    Vector comestibles(Ficha ficha) {
        String tipo = ficha.getTipo();
        String color = ficha.getColor();
        int casillaX = ficha.casilla.getX();
        int casillaY = ficha.casilla.getY();
        Vector comer = new Vector();

        //La ficha es de tipo peon
        if (tipo == "peon") {
            if (color == "blanco") {
                try {
                    //la casilla en su diagonal izquierda contiene una ficha que no es de su color y puede comer
                    if (casillaContieneFicha(casillas[casillaX - 1][casillaY - 1]) && fichas[casillaX - 1][casillaY - 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY - 1]);
                    }
                    //la casilla en su diagonal derecha contiene una ficha que no es de su color y puede comer
                    if (casillaContieneFicha(casillas[casillaX + 1][casillaY - 1]) && fichas[casillaX + 1][casillaY - 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY - 1]);
                    }
                } catch (Exception e) {
                    System.out.println("error comer peon blanco");
                }
            } else {
                try {
                    //la casilla en su diagonal izquierda contiene una ficha que no es de su color y puede comer
                    if (casillaContieneFicha(casillas[casillaX - 1][casillaY + 1]) && fichas[casillaX - 1][casillaY + 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY + 1]);
                    }
                    //la casilla en su diagonal derecha contiene una ficha que no es de su color y puede comer
                    if (casillaContieneFicha(casillas[casillaX + 1][casillaY + 1]) && fichas[casillaX + 1][casillaY + 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY + 1]);
                    }
                } catch (Exception e) {
                    System.out.println("error comer peon negro");
                }
            }
        }

        if (tipo == "torre") {
            if (color == "blanco") {
                try {
                    for (int i = casillaY - 1; i >= 0; i--) {
                        if (casillaContieneFicha(casillas[casillaX][i]) && fichas[casillaX][i].getColor() != "blanco") {
                            comer.addElement(casillas[casillaX][i]);
                        }
                    }
                    for (int i = casillaX - 1; i >= 0; i--) {
                        if (casillaContieneFicha(casillas[i][casillaY]) && fichas[casillaX][i].getColor() != "blanco") {
                            comer.addElement(casillas[i][casillaY]);
                        }
                    }
                    for (int i = casillaY + 1; i <= 7; i++) {
                        if (casillaContieneFicha(casillas[casillaX][i]) && fichas[casillaX][i].getColor() != "blanco") {
                            comer.addElement(casillas[casillaX][i]);
                        }
                    }
                    for (int i = casillaX + 1; i <= 7; i++) {
                        if (casillaContieneFicha(casillas[i][casillaY]) && fichas[casillaX][i].getColor() != "blanco") {
                            comer.addElement(casillas[i][casillaY]);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error comer torre blanca");
                }
            } else {
                try {
                    for (int i = casillaY - 1; i >= 0; i--) {
                        if (casillaContieneFicha(casillas[casillaX][i]) && fichas[casillaX][i].getColor() == "blanco") {
                            comer.addElement(casillas[casillaX][i]);
                        }
                    }
                    for (int i = casillaX - 1; i >= 0; i--) {
                        if (casillaContieneFicha(casillas[i][casillaY]) && fichas[casillaX][i].getColor() == "blanco") {
                            comer.addElement(casillas[i][casillaY]);
                        }
                    }
                    for (int i = casillaY + 1; i <= 7; i++) {
                        if (casillaContieneFicha(casillas[casillaX][i]) && fichas[casillaX][i].getColor() == "blanco") {
                            comer.addElement(casillas[casillaX][i]);
                        }
                    }
                    for (int i = casillaX + 1; i <= 7; i++) {
                        if (casillaContieneFicha(casillas[i][casillaY]) && fichas[casillaX][i].getColor() == "blanco") {
                            comer.addElement(casillas[i][casillaY]);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error comer torre negra");
                }
            }
        }
        if (tipo == "caballo") {
            if (color == "blanco") {
                try {
                    if (casillaContieneFicha(casillas[casillaX - 1][casillaY - 2]) && fichas[casillaX - 1][casillaY - 2].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY - 2]);
                    }
                    if (casillaContieneFicha(casillas[casillaX + 1][casillaY - 2]) && fichas[casillaX + 1][casillaY - 2].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY - 2]);
                    }
                    if (casillaContieneFicha(casillas[casillaX + 2][casillaY - 1]) && fichas[casillaX + 2][casillaY - 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX + 2][casillaY - 1]);
                    }
                    if (casillaContieneFicha(casillas[casillaX - 2][casillaY - 1]) && fichas[casillaX - 2][casillaY - 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX - 2][casillaY - 1]);
                    }
                    if (casillaContieneFicha(casillas[casillaX - 2][casillaY + 1]) && fichas[casillaX - 2][casillaY + 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX - 2][casillaY + 1]);
                    }
                    if (casillaContieneFicha(casillas[casillaX + 2][casillaY + 1]) && fichas[casillaX + 2][casillaY + 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX + 2][casillaY + 1]);
                    }
                    if (casillaContieneFicha(casillas[casillaX + 1][casillaY + 2]) && fichas[casillaX + 1][casillaY + 2].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY + 2]);
                    }
                    if (casillaContieneFicha(casillas[casillaX - 1][casillaY + 2]) && fichas[casillaX - 1][casillaY + 2].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY + 2]);
                    }
                } catch (Exception e) {
                    System.out.println("error comer caballo blanco");
                }
            } else {
                try {
                    if (casillaContieneFicha(casillas[casillaX - 1][casillaY - 2]) && fichas[casillaX - 1][casillaY - 2].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY - 2]);
                    }
                    if (casillaContieneFicha(casillas[casillaX + 1][casillaY - 2]) && fichas[casillaX + 1][casillaY - 2].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY - 2]);
                    }
                    if (casillaContieneFicha(casillas[casillaX + 2][casillaY - 1]) && fichas[casillaX + 2][casillaY - 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX + 2][casillaY - 1]);
                    }
                    if (casillaContieneFicha(casillas[casillaX - 2][casillaY - 1]) && fichas[casillaX - 2][casillaY - 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX - 2][casillaY - 1]);
                    }
                    if (casillaContieneFicha(casillas[casillaX - 2][casillaY + 1]) && fichas[casillaX - 2][casillaY + 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX - 2][casillaY + 1]);
                    }
                    if (casillaContieneFicha(casillas[casillaX + 2][casillaY + 1]) && fichas[casillaX + 2][casillaY + 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX + 2][casillaY + 1]);
                    }
                    if (casillaContieneFicha(casillas[casillaX + 1][casillaY + 2]) && fichas[casillaX + 1][casillaY + 2].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY + 2]);
                    }
                    if (casillaContieneFicha(casillas[casillaX - 1][casillaY + 2]) && fichas[casillaX - 1][casillaY + 2].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY + 2]);
                    }
                } catch (Exception e) {
                    System.out.println("error comer caballo negro");
                }
            }
        }
        if (tipo == "alfil") {
            if (color == "blanco") {

            } else {

            }
        }
        if (tipo == "reina") {
            if (color == "blanco") {

            } else {

            }
        }
        if (tipo == "rey") {
            if (color == "blanco") {
                try {
                    if (!casillaContieneFicha(casillas[casillaX - 1][casillaY - 1]) && fichas[casillaX - 1][casillaY - 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY - 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX + 1][casillaY - 1]) && fichas[casillaX + 1][casillaY - 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY - 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX][casillaY - 1]) && fichas[casillaX][casillaY - 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX][casillaY - 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX + 1][casillaY]) && fichas[casillaX + 1][casillaY].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX - 1][casillaY]) && fichas[casillaX - 1][casillaY].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX + 1][casillaY + 1]) && fichas[casillaX + 1][casillaY + 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY + 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX - 1][casillaY + 1]) && fichas[casillaX - 1][casillaY + 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY + 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX][casillaY + 1]) && fichas[casillaX][casillaY + 1].getColor() != "blanco") {
                        comer.addElement(casillas[casillaX][casillaY + 1]);
                    }
                } catch (Exception e) {
                    System.out.println("error movimiento rey blanco");
                }
            } else {
                try {
                    if (!casillaContieneFicha(casillas[casillaX - 1][casillaY - 1]) && fichas[casillaX - 1][casillaY - 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY - 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX + 1][casillaY - 1]) && fichas[casillaX + 1][casillaY - 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY - 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX][casillaY - 1]) && fichas[casillaX][casillaY - 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX][casillaY - 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX + 1][casillaY]) && fichas[casillaX + 1][casillaY].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX - 1][casillaY]) && fichas[casillaX - 1][casillaY].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX + 1][casillaY + 1]) && fichas[casillaX + 1][casillaY + 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX + 1][casillaY + 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX - 1][casillaY + 1]) && fichas[casillaX - 1][casillaY + 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX - 1][casillaY + 1]);
                    }
                    if (!casillaContieneFicha(casillas[casillaX][casillaY + 1]) && fichas[casillaX][casillaY + 1].getColor() == "blanco") {
                        comer.addElement(casillas[casillaX][casillaY + 1]);
                    }
                } catch (Exception e) {
                    System.out.println("error movimiento rey negro");
                }
            }
        }
        return comer;
    }
}
