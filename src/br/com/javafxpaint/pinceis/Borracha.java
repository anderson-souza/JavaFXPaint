package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Borracha extends Pincel {

    private static Borracha instancia;

    public static synchronized Borracha getInstance() {
        if (instancia == null) {
            instancia = new Borracha();
        }
        return instancia;
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

    private Borracha() {
        inicializarPincel();
    }

    @Override
    public void desenhar(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        final double espessura;
        espessura = PincelController.getInstance().getTamanhoPincelAtual();
        gc.clearRect(x2 - (espessura / 2), y2 - (espessura / 2), espessura, espessura);
    }

    @Override
    public final void inicializarPincel() {
        setTamanhoPincelAtual(5);
    }
}
