
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class CanvasPadrao extends Canvas {

    private GraphicsContext mainGc;
    double atualX, atualY, velhoX, velhoY;
    MeusPinceis pinceis;
    int id;
    private double espessuraPincel;
    Path path;

    public CanvasPadrao(double largura, double altura, int id) {
        super(largura, altura);
        this.id = id;
        //setCache(true);
        mainGc = getGraphicsContext2D();
        InicializarCaneta();
        pinceis = MeusPinceis.getInstance();
        setCursor(Cursor.CROSSHAIR);

        setOnMousePressed((MouseEvent event) -> { //Evento que será chamado quando o mouse for pressionado
            if (event.isPrimaryButtonDown()) { //Se for o botão primário pressionado executa a operações abaixo
                //AlteraCursor();
                mainGc.setStroke(pinceis.getCorAtual());
                mainGc.setLineWidth(pinceis.getEspessuraAtual());
                espessuraPincel = pinceis.getEspessuraAtual();
                velhoX = event.getX();
                velhoY = event.getY();
                atualX = event.getX();
                atualY = event.getY();
                switch (pinceis.getPincelAtual()) {
                    case CANETA:
                        Caneta(mainGc, atualX, atualY, atualX, atualY);
                        break;
                    case BORRACHA:
                        Borracha(mainGc, atualX, atualY, espessuraPincel);
                        break;
                    case LAPIS:
                        //Lapis(mainGc, atualX, atualY, atualX, atualY);
                        /*mainGc.beginPath();
                        mainGc.moveTo(event.getX(), event.getY());
                        mainGc.stroke();*/
                        mainGc.fillOval(event.getX(), event.getY(), espessuraPincel, espessuraPincel);
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
                switch (pinceis.getPincelAtual()) {
                    case CANETA:
                        Caneta(mainGc, velhoX, velhoY, atualX, atualY);
                        break;
                    case BORRACHA:
                        Borracha(mainGc, atualX, atualY, espessuraPincel);
                        break;
                    case LAPIS:
                        //Lapis(mainGc, velhoX, velhoY, atualX, atualY);
                        /*mainGc.lineTo(event.getX(), event.getY());
                        mainGc.stroke();*/
                        //mainGc.fillOval(event.getX(), event.getY(), espessuraPincel, espessuraPincel);
                        //mainGc.strokeArc(event.getX(), event.getY(), espessuraPincel, espessuraPincel, 45, 240, ArcType.ROUND);
                        mainGc.strokePolyline(new double[]{event.getX(), velhoX},
                                new double[]{event.getY(), velhoY}, 2);
                        velhoX = atualX;
                        velhoY = atualY;
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

    ;

    public void AlteraCursor() {
        switch (pinceis.getPincelAtual()) {
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

    private void InicializarCaneta() { //Função que inicializa algumas preferencias da caneta
        mainGc.setLineCap(StrokeLineCap.ROUND); //Forma da Linha
        mainGc.setLineJoin(StrokeLineJoin.ROUND); //Junção da Linha
        mainGc.setFill(Color.BLACK); //Preenchimento das fórmas
        mainGc.setStroke(Color.BLACK); //Cor da linha

        //Efeito para tentar melhorar a qualidade do traçejado
        /*BoxBlur blur = new BoxBlur();
        blur.setWidth(1);
        blur.setHeight(1);
        blur.setIterations(3);
        mainGc.setEffect(blur); //Desativado pois estava dando problema com a borracha*/
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
        /*Color corLapis = pinceis.getCorAtual();
        corLapis.deriveColor(corLapis.getHue(), corLapis.getSaturation(), corLapis.getBrightness(), corLapis.getOpacity() - 0.20);
        mainGc.setStroke(corLapis); //Cor da linha*/

        gc.setLineWidth(pinceis.getEspessuraAtual()); //Alterar para um Listener Futuramente
        gc.strokeLine(xInicio, yInicio, xFim, yFim);
        velhoX = atualX;
        velhoY = atualY;
    }

    @Override
    public String toString() {
        return "Camada " + id;
    }
}
