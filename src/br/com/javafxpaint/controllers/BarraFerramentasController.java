package br.com.javafxpaint.controllers;

import br.com.javafxpaint.padroes.BorderPanePadrao;
import br.com.javafxpaint.padroes.DrawCanvas;
import br.com.javafxpaint.padroes.JanelaPadrao;
import br.com.javafxpaint.pinceis.Borracha;
import br.com.javafxpaint.pinceis.Caneta;
import br.com.javafxpaint.pinceis.Lapis;
import br.com.javafxpaint.pinceis.PincelController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BarraFerramentasController implements Initializable, Observer {

    @FXML
    private ColorPicker btColorPicker;
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
    private ComboBox<String> layerComboBox;
    @FXML
    private ComboBox<String> windowsComboBox;
    @FXML
    private Spinner<Double> espessuraSpinner;
    @FXML
    private Button btLimparCamada;
    @FXML
    private RadioButton btCanetaRadio;
    @FXML
    private ToggleGroup toggleGroupRadio;
    @FXML
    private RadioButton btLapisRadio;
    @FXML
    private RadioButton btBorrachaRadio;

    int qtdCamadas = 1, qtdJanelas = 1;
    int janelaAtual, camadaAtual;
    PincelController pincelController;
    private final List<JanelaPadrao> panesPadroes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) { //Inicializa algumas informações
        pincelController = PincelController.getInstance();
        pincelController.addObserver(this);
        btColorPicker.setValue(pincelController.getCorAtual());

        AdicionaJanela(); //Adiciona a primeira Janela a aplicação
        layerComboBox.getItems().add("Camada " + (qtdCamadas++));
        layerComboBox.getSelectionModel().selectFirst();

        alterarEstiloRadioButtons();
        inicializarSliderEspessura();
    }

    @FXML
    private void btColorPickerPressed(ActionEvent event) {
        pincelController.setCorAtual(btColorPicker.getValue());
    }

    @FXML
    private void btCanetaRadioAction(ActionEvent event) {
        pincelController.setPincelAtual(Caneta.getInstance());
    }

    @FXML
    private void btLapisRadioAction(ActionEvent event) {
        pincelController.setPincelAtual(Lapis.getInstance());
    }

    @FXML
    private void btBorrachaRadioAction(ActionEvent event) {
        pincelController.setPincelAtual(Borracha.getInstance());
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
    private void camadaComboBoxAction(ActionEvent event) { //Evento chamado quando uma camada é alterada
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
        listaCamadas.forEach((DrawCanvas lista) -> { //Percorre todos os items dentro da lista de camadas da janela atual
            layerComboBox.getItems().add(lista.toString());//Adiciona cada item no ComboBox
        });
        layerComboBox.getSelectionModel().select(panesPadroes.get(janelaAtual).getCamadaAtual());
    }

    @FXML
    private void btLimparCamadaAction(ActionEvent event) {
        final DrawCanvas canvasClear;
        canvasClear = panesPadroes.get(janelaAtual).getListaCamadas().get(camadaAtual);
        canvasClear.getGraphicsContext2D().clearRect(0, 0, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
    }

    private void inicializarSliderEspessura() {
        //Inicializa algumas informações sobre o Spinner de Espessura do pincel
        final SpinnerValueFactory<Double> valueFactory;
        valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 50, 1, 1);
        espessuraSpinner.setValueFactory(valueFactory);
        espessuraSpinner.setEditable(true);
        espessuraSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pincelController.setTamanhoPincelAtual(newValue);
        });
    }

    private void alterarEstiloRadioButtons() {
        //Modifica o estilo do RadioButton para parecer um ToggleButton, mas continuar com as caracteristicas de um RadioButton
        btCanetaRadio.getStyleClass().remove("radio-button");
        btCanetaRadio.getStyleClass().add("toggle-button");
        btLapisRadio.getStyleClass().remove("radio-button");
        btLapisRadio.getStyleClass().add("toggle-button");
        btBorrachaRadio.getStyleClass().remove("radio-button");
        btBorrachaRadio.getStyleClass().add("toggle-button");
    }

    private void AdicionaJanela() { //Função para criar uma nova janela de desenho
        JanelaPadrao panePadrao = new JanelaPadrao(); //Cria um Painel Padrão
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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PincelController) {
            espessuraSpinner.getValueFactory().setValue(pincelController.getTamanhoPincelAtual());
            btColorPicker.setValue(pincelController.getCorAtual());
        }
    }
}
