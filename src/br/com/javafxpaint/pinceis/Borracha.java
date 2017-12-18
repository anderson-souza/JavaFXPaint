package br.com.javafxpaint.pinceis;

import javafx.scene.canvas.GraphicsContext;

public class Borracha implements Pincel {

    private static Borracha instancia;

    public static synchronized Borracha getInstance() {
        if (instancia == null) {
            instancia = new Borracha();
        }
        return instancia;
    }

    private Borracha() {
    }

    @Override
    public void desenhar(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        final double espessura;
        espessura = PincelController.getInstance().getTamanhoPincelAtual();
        gc.clearRect(x2 - (espessura / 2), y2 - (espessura / 2), espessura, espessura);
    }
}
