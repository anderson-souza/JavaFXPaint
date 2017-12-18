package br.com.javafxpaint.controllers;

import br.com.javafxpaint.padroes.BorderPanePadrao;
import br.com.javafxpaint.padroes.DrawCanvas;
import br.com.javafxpaint.padroes.PanePadrao;
import br.com.javafxpaint.pinceis.Borracha;
import br.com.javafxpaint.pinceis.Caneta;
import br.com.javafxpaint.pinceis.Lapis;
import br.com.javafxpaint.pinceis.PincelController;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class BarraFerramentasController implements Initializable {

    @FXML
    private Button btCaneta;
    @FXML
    private Button btLapis;
    @FXML
    private Button btBorracha;
    @FXML
    private ColorPicker btColorPicker;
    @FXML
    private Slider sliderEspessuraPincel;
    @FXML
    private Label labelEspessuraPincel;
    @FXML
    private Button btTelaCheia;
    @FXML
    private TitledPane mainTitledPane;
    @FXML
    private Button btCamadaAdd;
    @FXML
    private Button btCamadaRemove;
    @FXML
    private Button btJanelaAdd;
    @FXML
    private Button btJanelaRemove;
    @FXML
    private ComboBox layerComboBox;
    @FXML
    private ComboBox windowsComboBox;

    int qtdCamadas = 1, qtdJanelas = 1;
    int janelaAtual, camadaAtual;

    PincelController pincelController;

    private final List<PanePadrao> panesPadroes = new ArrayList<>();
    Map<String, Integer> hashmapCamadas = new HashMap<>(); //HashMap utilizado para manter registro do nome e posição do Layer no Pane

    @Override
    public void initialize(URL url, ResourceBundle rb) { //Inicializa algumas informações
        pincelController = PincelController.getInstance();
        btColorPicker.setValue(pincelController.getCorAtual());

        AdicionaJanela(); //Adiciona a primeira Janela a aplicação
        layerComboBox.getItems().add("Camada " + (qtdCamadas++));
        layerComboBox.getSelectionModel().selectFirst();

        sliderEspessuraPincel.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            labelEspessuraPincel.setText("Espessura do Pincel: " + newValue.intValue());
            pincelController.setTamanhoPincelAtual((double) newValue);
        });
    }

    @FXML
    private void btCanetaPressed(ActionEvent event) {
        pincelController.setPincelAtual(Caneta.getInstance());
    }

    @FXML
    private void btLapisPressed(ActionEvent event) {
        pincelController.setPincelAtual(Lapis.getInstance());
    }

    @FXML
    private void btBorrachaPressed(ActionEvent event) {
        pincelController.setPincelAtual(Borracha.getInstance());
    }

    @FXML
    private void btColorPickerPressed(ActionEvent event) {
        pincelController.setCorAtual(btColorPicker.getValue());
    }

    @FXML
    private void btTelaCheiaPressed(ActionEvent event) {
        final Stage stage;
        stage = (Stage) btTelaCheia.getScene().getWindow();

        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }
    }

    @FXML
    public void btCamadaAddAction(ActionEvent event) { //Evento chamado quando o botão de adicionar camada é pressionado
        AdicionaCamada();
    }

    @FXML
    private void btCamadaRemoveAction(ActionEvent event) { //Evento chamado quando o botão de remover camada é pressionado

        if (layerComboBox.getItems().size() > 1) { //Condição para verificar se existe mais do que 1 camada para remover
            int indexRemove = camadaAtual;

            if (indexRemove == layerComboBox.getItems().size() - 1) { //Condição para verificar se o indice que será removido é o último do combobox
                layerComboBox.getSelectionModel().selectPrevious();
            } else {
                layerComboBox.getSelectionModel().selectNext();
            }
            layerComboBox.getItems().remove(indexRemove);
            ObservableList<DrawCanvas> listaCamadas = FXCollections.observableArrayList(panesPadroes.get(janelaAtual).getListaCamadas());
            panesPadroes.get(janelaAtual).getChildren().remove(listaCamadas.get(indexRemove));
            panesPadroes.get(janelaAtual).getListaCamadas().remove(indexRemove);
        }
    }

    @FXML
    private void layerComboBoxAction(ActionEvent event) { //Evento chamado quando uma camada é alterada
        if (!layerComboBox.getItems().isEmpty()) { //Verifica se o ComboBox não está vazio
            camadaAtual = layerComboBox.getSelectionModel().getSelectedIndex();
            panesPadroes.get(janelaAtual).getListaCamadas().get(camadaAtual).toFront();
            panesPadroes.get(janelaAtual).AlterarCamadaAtual(camadaAtual);
        }
    }

    @FXML
    private void btJanelaAddAction(ActionEvent event) {
        AdicionaJanela();
    }

    @FXML
    private void btJanelaRemoveAction(ActionEvent event) { //Evento disparado quando o botão para remover uma janela é selecionado
        if (windowsComboBox.getItems().size() > 1) {
            int indexRemove = janelaAtual;

            if (indexRemove == windowsComboBox.getItems().size() - 1) { //Se for o último elemento, seleciona o anterior
                windowsComboBox.getSelectionModel().selectPrevious();
            } else { //Se não for o último elemento, seleciona o próximo
                windowsComboBox.getSelectionModel().selectNext();
            }
            panesPadroes.remove(indexRemove);
            windowsComboBox.getItems().remove(indexRemove);
        }
    }

    @FXML
    private void windowsComboBoxAction(ActionEvent event) { //Evento disparado quando é alterado a seleção do elemento no ComboBox
        janelaAtual = windowsComboBox.getSelectionModel().getSelectedIndex(); //Atribui a variavel janelaAtual o valor contido no indice selecionado do combobox
        BorderPanePadrao.getInstance().setCenter(panesPadroes.get(janelaAtual));

        layerComboBox.getItems().clear();//Limpa todos os items do ComboBox de Camadas
        ObservableList<DrawCanvas> listaCamadas = FXCollections.observableArrayList(panesPadroes.get(janelaAtual).getListaCamadas());
        listaCamadas.forEach((lista) -> { //Percorre todos os items dentro da lista de camadas da janela atual
            layerComboBox.getItems().add(lista.toString());//Adiciona cada item no ComboBox
        });
        layerComboBox.getSelectionModel().select(panesPadroes.get(janelaAtual).getCamadaAtual());
    }

    private void AdicionaJanela() { //Função para criar uma nova janela de desenho
        PanePadrao panePadrao = new PanePadrao(); //Cria um Painel Padrão
        BorderPanePadrao.getInstance().setCenter(panePadrao); //Adiciona este Painel no Centro do Layout
        panesPadroes.add(panePadrao); //Adiciona o painel no array de Paineis
        windowsComboBox.getItems().add(panePadrao.toString());
        windowsComboBox.getSelectionModel().selectLast();
        layerComboBox.getSelectionModel().selectLast();
    }

    private void AdicionaCamada() { //Função para criar uma nova camada de desenho
        DrawCanvas canvas = panesPadroes.get(janelaAtual).AdicionarCamadas();
        layerComboBox.getItems().add(canvas.toString());
        layerComboBox.getSelectionModel().selectLast();
    }
}
