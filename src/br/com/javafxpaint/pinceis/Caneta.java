package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Singleton
 *
 * @author EAD-UNIPAR
 */
public class Caneta extends Pincel {

    private static Caneta instancia;

    public static synchronized Caneta getInstance() {
        if (instancia == null) {
            instancia = new Caneta();
        }
        return instancia;
    }

    private Caneta() {
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
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public final void inicializarPincel() {
        setCorAtual(Color.BLACK);
        setTamanhoPincelAtual(1);
    }
}
