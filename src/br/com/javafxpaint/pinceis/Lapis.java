package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Singleton
 *
 * @author EAD-UNIPAR
 */
public class Lapis extends Pincel {

    private static Lapis instancia;

    public static synchronized Lapis getInstance() {
        if (instancia == null) {
            instancia = new Lapis();
        }
        return instancia;
    }

    private Lapis() {
        inicializarPincel();
    }

    public Color getCorAtual() {
        return corAtual;
    }

    public void setCorAtual(Color corAtual) {
        this.corAtual = corAtual;
    }

    public double getTamanhoPincelAtual() {
        return tamanhoPincelAtual;
    }

    public void setTamanhoPincelAtual(double tamanhoPincelAtual) {
        this.tamanhoPincelAtual = tamanhoPincelAtual;
    }

    @Override
    public void desenhar(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        gc.lineTo(x2, y2);
        gc.stroke();
    }

    @Override
    public final void inicializarPincel() {
        setCorAtual(Color.BLACK);
        setTamanhoPincelAtual(1);
    }
}
