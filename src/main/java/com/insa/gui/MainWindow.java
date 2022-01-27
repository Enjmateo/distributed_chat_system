package com.insa.gui;

import com.insa.app.User;
import com.insa.app.UsersHandler;
import com.insa.communication.Data;
import com.insa.gui.chattabs.UserDiscussionView;
import com.insa.utils.*;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

public class MainWindow {
    Stage window = new Stage();

    //Gauche
        //Image
        Image logo = GUIUtils.getLogo();
        ImageView logoView = new ImageView(logo);

        //Pseudo set
        HBox pseudoLayout = new HBox(Consts.ELEMENTS_GAP);
            Label pseudoLabel = new Label();
            HBox pseudoLabelContainer = new HBox();
            Button settingsButton = new Button("⚙");

            TextField pseudoField = new TextField();
            Button validatePseudoButton = new Button("✔");

        //Connected users 
        static ListView<UserLabel> listView = new ListView<UserLabel>();

        //Button connectButton = new Button("Connect");

    //Right
        static HBox discussionHolder = new HBox();

    static User targetUser = null;
    public void start() {
        window.setTitle("ODD");
        window.setMinHeight(Consts.MW_MIN_HEIGHT);
        window.setMinWidth(Consts.MW_MIN_WIDTH);
        window.setWidth(Consts.MW_PREF_WIDTH);

        logoView.setPreserveRatio(true);
		logoView.setFitWidth(Consts.MW_IMAGE_SIZE);

        settingsButton.setOnAction(e->editPseudo());
        settingsButton.setCursor(Cursor.HAND);

        //Pseudo set
        pseudoLabel.setOnMouseClicked(e-> editPseudo());
        validatePseudoButton.setOnAction(e-> validateNewPseudo());
        pseudoField.setOnAction(e-> validateNewPseudo());
        
        pseudoLabelContainer.getChildren().add(pseudoLabel);
        HBox.setHgrow(pseudoLabelContainer, Priority.ALWAYS);

        pseudoLabel.textProperty().bind(UsersHandler.getLocalUser().getPseudoProperty());
        pseudoLabel.setFont(Font.font(Font.getDefault().toString(),FontWeight.BOLD,15));
        pseudoLabel.setCursor(Cursor.HAND);
        //pseudoLayout.setPrefWidth(Consts.MW_IMAGE_SIZE);
    
        pseudoLayout.getChildren().addAll(pseudoLabelContainer,settingsButton);
        //HBox.setHgrow(pseudoLayout, Priority.ALWAYS);
        //pseudoLayout.setAlignment(Pos.CENTER);
       
        VBox pseudoAndLogoLayout = new VBox(Consts.FIELDS_GAP);
        pseudoAndLogoLayout.getChildren().addAll(logoView,pseudoLayout);
        pseudoAndLogoLayout.setMinHeight(Consts.IM_AND_PSEU_MIN_HEIGHT);
        pseudoAndLogoLayout.setMinWidth(Consts.MW_IMAGE_SIZE);

        VBox.setVgrow(listView, Priority.ALWAYS);
        listView.setMaxWidth(Consts.MW_IMAGE_SIZE);

        VBox leftLayout = new VBox(Consts.FIELDS_GAP);
        leftLayout.getChildren().addAll(pseudoAndLogoLayout,listView);

        HBox.setHgrow(discussionHolder, Priority.ALWAYS);

        HBox mainlayout = new HBox(Consts.ELEMENTS_GAP);
        mainlayout.setPadding(new Insets(10)); //TODO conts
        mainlayout.getChildren().addAll(leftLayout,discussionHolder);

        Scene scene = new Scene(mainlayout);

        //discussionHolder.getChildren().setAll(new UserDiscussionView(UsersHandler.getLocalUser()));
    
        /*
        connectButton.setOnAction(e->{
            if(targetUser!=null){
                
            }
        });*/
		window.setScene(scene);
		window.showAndWait();
        ExitHandler.exit();
    }

    public static void addUser(User user){
        Platform.runLater(new Runnable() {
            public void run() {
                LogHandler.display(1,"[+] Adding new user to list");
                    UserLabel label = new UserLabel(user);
                    label.setOnMouseClicked(e->{
                        targetUser=user;
                        if(targetUser.getStatus()==User.Status.AVAILABLE){
                            try {
                                targetUser.connect();
                            } catch (Exception e1) {
                                new ErrorWindow("User unreachable");
                                return;
                            }
                        }else if(targetUser.getStatus()==User.Status.WAITING){ 
                            new ErrorWindow("User unavailable, you can't send him messages");
                        }
                        discussionHolder.getChildren().setAll(targetUser.getUserDiscussionView());
                        targetUser.resetUnreadMessagesCount();
                        
                    });
                    listView.getItems().add(label);
                }
            
        });

    }

    /*
    public synchronized static void updateList() {
        Platform.runLater(new Runnable() {
            public void run() {
                LogHandler.display(1,"[+] Updating connected list");
                listView.getItems().clear();
                for(User user : UsersHandler.getAliveUsers()) {
                    UserLabel label = new UserLabel(user);
                    label.setOnMouseClicked(e->{
                        targetUser=user;
                        try {
                            targetUser.connect();
                        } catch (Exception e1) {
                            ExitHandler.error(e1);
                        }
                        discussionHolder.getChildren().setAll(targetUser.getUserDiscussionView());
                        targetUser.resetUnreadMessagesCount();
                        
                    });
                    listView.getItems().add(label);
                }
            }
        });
        
    }
    */
    private void editPseudo(){
        pseudoField.setText(UsersHandler.getLocalUser().getPseudo());
        pseudoLayout.getChildren().setAll(pseudoField,validatePseudoButton);
    }
    private void validateNewPseudo(){
        String pseudo = pseudoField.getText();
        if(UsersHandler.getPseudos().contains(pseudo)){
            new ErrorWindow(pseudo+" is already used, please choose another pseudo.");
        }else{ 
            UsersHandler.updateSelfPseudo(pseudo);
        }
        
        pseudoLayout.getChildren().setAll(pseudoLabelContainer,settingsButton);
    }

}
