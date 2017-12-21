package br.com.javafxpaint.main;

import br.com.javafxpaint.padroes.BorderPanePadrao;
import br.com.javafxpaint.padroes.GroupPadrao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UniparPaint extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        final GroupPadrao group = GroupPadrao.getInstance();
        final Scene scene = new Scene(group, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), true, SceneAntialiasing.BALANCED);
        final Node node = FXMLLoader.load(getClass().getResource("/br/com/javafxpaint/fxml/BarraFerramentas.fxml"));
        BorderPanePadrao.getInstance().setTop(node);
        group.getChildren().add(BorderPanePadrao.getInstance());

        //Atributos da Cena
        stage.setTitle("Unipar EAD Paint");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.getIcons().add(new Image("img/logo_mini_unipar_icon.png"));
        //ScenicView.show(scene); //Visualizador de Cena, utilizado para Depurar a construção das cenas no JavaFX
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
