package br.com.javafxpaint.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UniparPaintController implements Initializable {

    private VBox mainVBox;
    @FXML
    private StackPane mainStackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void montaTela(Node telaNode) {
        //mainVBox.getChildren().add(telaNode);
        mainStackPane.getChildren().add(telaNode);
        mainStackPane.setAlignment(Pos.TOP_LEFT);
    }

    /*public void montaTela(Node telaNode, Orientacao orientacao) {
        switch (orientacao) {
            case TOP:
                mainAchorPane.setTopAnchor(telaNode, 5.0);
                break;
            case LEFT:
                mainAchorPane.setLeftAnchor(telaNode, 5.0);
                break;
            case CENTER:
                //mainAchorPane.set(telaNode, 5.0);
                break;
            case RIGHT:
                mainAchorPane.setRightAnchor(telaNode, 5.0);
                break;
            case BOTTOM:
                mainAchorPane.setBottomAnchor(telaNode, 5.0);
                break;
        }
    }*/
    public static enum Orientacao {
        TOP, LEFT, CENTER, RIGHT, BOTTOM;
    }
}
