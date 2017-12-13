package br.com.javafxpaint;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class CanvasPadrao extends Canvas implements Observer {

    private GraphicsContext mainGc;
    private double atualX, atualY, velhoX, velhoY, espessuraPincel;
    private int id;
    private MeusPinceis.Pinceis pincelAtual;
    MeusPinceis pinceis;

    public CanvasPadrao(double largura, double altura, int id) {
        super(largura, altura);
        this.id = id;
        setCache(true);
        mainGc = getGraphicsContext2D();
        pinceis = MeusPinceis.getInstance();
        pinceis.addObserver(this);
        InicializarDesenho();
        setCursor(Cursor.CROSSHAIR);

        setOnMousePressed((MouseEvent event) -> { //Evento que será chamado quando o mouse for pressionado
            if (event.isPrimaryButtonDown()) { //Se for o botão primário pressionado executa a operações abaixo
                velhoX = event.getX();
                velhoY = event.getY();
                atualX = event.getX();
                atualY = event.getY();
                mainGc.beginPath();
                switch (pincelAtual) {
                    case CANETA:
                        Caneta(mainGc, event.getX(), event.getY(), event.getX(), event.getY());
                        break;
                    case BORRACHA:
                        Borracha(mainGc, atualX, atualY, espessuraPincel);
                        break;
                    case LAPIS:
                        //Lapis(mainGc, atualX, atualY, atualX, atualY);
                        mainGc.moveTo(event.getX(), event.getY());
                        mainGc.stroke();
                        break;
                    default:
                        break;
                }
            }
        });
        setOnMouseDragged((MouseEvent event) -> {
            //Evento que será chamado quando o mouse for arrastado
            if (event.isPrimaryButtonDown()) {
                atualX = event.getX();
                atualY = event.getY();
                switch (pincelAtual) {
                    case CANETA:
                        Caneta(mainGc, velhoX, velhoY, atualX, atualY);
                        break;
                    case BORRACHA:
                        Borracha(mainGc, event.getX(), event.getY(), espessuraPincel);
                        break;
                    case LAPIS:
                        //Lapis(mainGc, velhoX, velhoY, atualX, atualY);
                        mainGc.lineTo(event.getX(), event.getY());
                        mainGc.stroke();
                        break;
                    default:
                        break;
                }
            }
        });

        setOnMouseReleased((MouseEvent event) -> { //Evento que será chamado quando o mouse for liberado
            mainGc.closePath();
        });
    }

    public void AlteraCursor() {
        switch (pincelAtual) {
            case CANETA:
                Image imageCaneta = new Image("img/caneta.png");
                setCursor(new ImageCursor(imageCaneta, imageCaneta.getWidth() / 2 + 1, imageCaneta.getHeight() / 2 + 1));
                break;
            case BORRACHA:
                Image imageBorracha = new Image("img/borracha.png");
                setCursor(new ImageCursor(imageBorracha));
                break;
            case LAPIS:
                break;
            default:
                setCursor(Cursor.CROSSHAIR);
                break;
        }
    }

    private void InicializarDesenho() { //Função que inicializa algumas preferencias para o desenho
        pincelAtual = MeusPinceis.Pinceis.CANETA;
        mainGc.setStroke(pinceis.getCorAtual());
        mainGc.setLineWidth(pinceis.getEspessuraAtual());
        espessuraPincel = pinceis.getEspessuraAtual();
        mainGc.setLineCap(StrokeLineCap.ROUND); //Forma da Linha
        mainGc.setLineJoin(StrokeLineJoin.ROUND); //Junção da Linha
        mainGc.setFill(Color.BLACK); //Preenchimento das fórmas
        //AplicarEfeito(mainGc);
    }

    private void Borracha(GraphicsContext gc, double x, double y, double espessura) { //Função da Borracha
        gc.clearRect(x - (espessura / 2), y - (espessura / 2), espessura, espessura);
        //gc.setStroke(gc.getC);
    }

    private void Caneta(GraphicsContext gc, double xInicio, double yInicio, double xFim, double yFim) { //Função da Caneta
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        velhoX = atualX;
        velhoY = atualY;
    }

    private void Lapis(GraphicsContext gc, double xInicio, double yInicio, double xFim, double yFim) { //Função do Lapis *Rever funcionalidades
        gc.setLineWidth(pinceis.getEspessuraAtual()); //Alterar para um Listener Futuramente
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        velhoX = atualX;
        velhoY = atualY;
    }

    private void AplicarEfeito(GraphicsContext GcContext) {
        /*BoxBlur blur = new BoxBlur();
        blur.setWidth(1);
        blur.setHeight(1);
        blur.setIterations(3);
        GcContext.setEffect(blur);*/
    }

    @Override
    public String toString() {
        return "Camada " + id;
    }

    @Override
    public void update(Observable o, Object arg) { //Função que é executada sempre que algum parâmetro da classe MeusPinceis é executada
        Color newColor = ((MeusPinceis) o).getCorAtual();
        mainGc.setStroke(newColor);
        espessuraPincel = ((MeusPinceis) o).getEspessuraAtual();
        mainGc.setLineWidth(espessuraPincel);
        pincelAtual = ((MeusPinceis) o).getPincelAtual();
    }
}
