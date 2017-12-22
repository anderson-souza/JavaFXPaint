package br.com.javafxpaint.padroes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 * JanelaPadrao é uma classe que extende de Pane e é utilizada para manter as
 * camadas(Canvas) da aplicação.
 *
 * @author EAD-UNIPAR
 */
public final class JanelaPadrao extends Pane {

    protected static int count; //Variavel para contar o número de Janelas criadas a fim de gerar um nome para a mesma
    private List<DrawCanvas> listaCamadas;
    int qtdCanvas = 1; //Varível para contar o número de canvas criado para este objeto a fim de gerar um nome para o mesmo
    private int camadaAtual;

    public JanelaPadrao() {
        super();
        listaCamadas = new ArrayList<>();
        count++;
        AdicionarCamadas();
    }

    public int getCamadaAtual() {
        return camadaAtual;
    }

    private void setCamadaAtual(final int camadaAtual) {
        this.camadaAtual = camadaAtual;
    }

    public List<DrawCanvas> getListaCamadas() {
        return listaCamadas;
    }

    public void setListaCamadas(final List<DrawCanvas> listaCamadas) {
        this.listaCamadas = listaCamadas;
    }

    public DrawCanvas AdicionarCamadas() {
        final DrawCanvas canvas = new DrawCanvas(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), qtdCanvas);
        qtdCanvas++;
        this.getChildren().add(canvas);
        listaCamadas.add(canvas);
        return canvas;
    }

    public void AlterarCamadaAtual(final int index) {
        setCamadaAtual(index);
    }

    @Override
    public String toString() { //Função toString foi sobrescrita para retornar o texto Janela + número identificador da janela
        return "Janela " + count;
    }

}
