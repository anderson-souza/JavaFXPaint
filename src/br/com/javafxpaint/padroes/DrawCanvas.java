package br.com.javafxpaint.padroes;

import br.com.javafxpaint.pinceis.PincelController;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import jwinpointer.JWinPointerReader;

public class DrawCanvas extends Canvas implements Observer, JWinPointerReader.PointerEventListener {

    private final GraphicsContext graphicsContext;
    private final int id;
    private double atualX, atualY, velhoX, velhoY, espessuraPincel;
    private JWinPointerReader jWinPointerReader;
    private PincelController pincelController;

    public DrawCanvas(double largura, double altura, int id) {
        super(largura, altura);

        this.id = id;

        graphicsContext = getCanvasGraphicsContext();
        pincelController = PincelController.getInstance();
        pincelController.addObserver(getInstance());
        //setCursor(new ImageCursor(PincelController.getInstance().getCursor(), 0, -1024));
        inicializarDesenhos(pincelController);

        /* final Stage stage;
        stage = (Stage) btTelaCheia.getScene().getWindow();*/
        Platform.runLater(() -> {
            jWinPointerReader = new JWinPointerReader("Unipar EAD Paint");
            jWinPointerReader.addPointerEventListener(getInstance());
        });

//        setOnMousePressed((MouseEvent event) -> {
//            if (event.isPrimaryButtonDown()) {
//                velhoX = event.getX();
//                velhoY = event.getY();
//                atualX = event.getX();
//                atualY = event.getY();
//                graphicsContext.beginPath();
//                pincelController.getPincelAtual().desenhar(graphicsContext, atualX, atualY, atualX, atualY);
//            }
//        });
//        setOnMouseDragged((MouseEvent event) -> {
//            if (event.isPrimaryButtonDown()) {
//                atualX = event.getX();
//                atualY = event.getY();
//                pincelController.getPincelAtual().desenhar(graphicsContext, velhoX, velhoY, atualX, atualY);
//                velhoX = atualX;
//                velhoY = atualY;
//            }
//        });
//        setOnMouseReleased((MouseEvent event) -> {
//            graphicsContext.closePath();
//        });
    }

    private void inicializarDesenhos(final PincelController pincelController) {
        //Configurações iniciais dos desenhos
        graphicsContext.setLineWidth(pincelController.getTamanhoPincelAtual());
        graphicsContext.setStroke(pincelController.getCorAtual());
        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
    }

    public final DrawCanvas getInstance() {
        return this;
    }

    protected GraphicsContext getCanvasGraphicsContext() {
        return getInstance().getGraphicsContext2D();
    }

    @Override
    public String toString() {
        return "Camada " + id;
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof PincelController) {
            final Color novaCor;
            novaCor = ((PincelController) observable).getCorAtual();
            graphicsContext.setStroke(novaCor);
            espessuraPincel = ((PincelController) observable).getTamanhoPincelAtual();
            graphicsContext.setLineWidth(espessuraPincel);
            setCursor(new ImageCursor(PincelController.getInstance().getCursor(), 0, 512));
        }
    }

    private static final int EVENT_TYPE_DRAG = 1;
    private static final int EVENT_TYPE_HOVER = 2;
    private static final int EVENT_TYPE_DOWN = 3;
    private static final int EVENT_TYPE_UP = 4;
    private static final int EVENT_TYPE_BUTTON_DOWN = 5;
    private static final int EVENT_TYPE_BUTTON_UP = 6;
    private static final int EVENT_TYPE_IN_RANGE = 7;
    private static final int EVENT_TYPE_OUT_OF_RANGE = 8;

    @Override
    public void pointerXYEvent(int deviceType, int pointerID, int eventType, boolean inverted, int x, int y, int pressure) {
        //System.out.println("pointerXYEvent: device: " + deviceType + " | pointerId: " + pointerID + " | eventType: " + eventType + " | x: " + x + " | y: " + y);
        if (canDraw(x, y)) {
            Point2D pointxy = this.sceneToLocal(x, y);
            System.out.println("X: " + pointxy.getX() + " | Y: " + pointxy.getY());
            switch (eventType) {
                case 1:
                    atualX = pointxy.getX();
                    atualY = pointxy.getY();
                    pincelController.getPincelAtual().desenhar(graphicsContext, velhoX, velhoY, atualX, atualY);
                    velhoX = atualX;
                    velhoY = atualY;
                    break;
                case 3:
                    velhoX = pointxy.getX();
                    velhoY = pointxy.getY();
                    atualX = pointxy.getX();
                    atualY = pointxy.getY();
                    graphicsContext.beginPath();
                    pincelController.getPincelAtual().desenhar(graphicsContext, atualX, atualY, atualX, atualY);
                    break;
                case 4:
                    graphicsContext.closePath();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void pointerButtonEvent(int deviceType, int pointerID, int eventType, boolean inverted, int buttonIndex) {
        System.out.println("pointerButtonEvent: device: " + deviceType + " | pointerId: " + pointerID + " | eventType: " + eventType);
    }

    @Override
    public void pointerEvent(int deviceType, int pointerID, int eventType, boolean inverted) {
        System.out.println("pointerEvent: device: " + deviceType + " | pointerId: " + pointerID + " | eventType: " + eventType);
    }

    private boolean canDraw(final int x, final int y) {
        return !(x < 0 || x >= getWidth() || y < 0 || y >= getHeight());
    }
}
