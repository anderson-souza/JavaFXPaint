package br.com.javafxpaint;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class PanePadrao extends Pane {

    protected final List<CanvasPadrao> listaCamadas;
    protected static int count = 0; //Variavel para contar o número de Janelas criadas a fim de gerar um nome para a mesma
    int qtdCanvas = 1; //Varível para contar o número de canvas criado para este objeto a fim de gerar um nome para o mesmo
    private int camadaAtual = 0;

    public int getCamadaAtual() {
        return camadaAtual;
    }

    private void setCamadaAtual(int camadaAtual) {
        this.camadaAtual = camadaAtual;
    }

    public PanePadrao() {
        super();
        listaCamadas = new ArrayList<>();
        count++;
        AdicionarCamadas();
    }

    protected CanvasPadrao AdicionarCamadas() {
        CanvasPadrao canvas = new CanvasPadrao(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), qtdCanvas);
        qtdCanvas++;
        this.getChildren().add(canvas);
        listaCamadas.add(canvas);
        //System.out.println("PanePadrao.AdicionarCamadas(): " + listaCamadas);
        return canvas;
    }

    protected void AlterarCamadaAtual(int index) {
        setCamadaAtual(index);
    }

    @Override
    public String toString() { //Função toString foi sobrescrita para retornar o texto Janela + número identificador da janela
        return "Janela " + count;
    }

    /*  CanvasPadrao canvas = new CanvasPadrao(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        panesPadroes.get(janelaAtual).getChildren().add(canvas);
        layerComboBox.getItems().add("Camada " + (qtdLayer++));
        //layerComboBox.getItems().add(canvas.toString());
        layerComboBox.getSelectionModel().selectLast();*/
}
