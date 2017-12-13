package br.com.javafxpaint;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 *
 */
public class MeuCanvasController implements Initializable {

    @FXML
    private Pane mainPane;
    private final List<CanvasPadrao> canvas;

    double atualX, atualY, velhoX, velhoY;
    MeusPinceis pinceis;

    public MeuCanvasController() {
        this.canvas = new ArrayList<>();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //AdicionarCanvas(); //Cria um novo Canvas
        AlteraCanvas(0); //Altera o canvas para a primeira posição do array*/
    }

    /*public void AdicionarCanvas() { //Função para criar um novo Canvas que servirá de Camada
        CanvasPadrao canvasAux = new CanvasPadrao(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        canvas.add(canvasAux);
        mainPane.getChildren().add(canvasAux);
    }*/
    public void RemoverCanvas(int posicao) { //Função para remover um Canvas
        canvas.remove(posicao);
    }

    public void AlteraCanvas(int posicao) { //Função para alterar o Canvas atual, que funcionará como uma Camada
        canvas.get(posicao).toFront();
        System.out.println("Canvas alterado para posição: " + posicao + ". Que é o objeto: " + canvas.get(posicao));
    }
}
