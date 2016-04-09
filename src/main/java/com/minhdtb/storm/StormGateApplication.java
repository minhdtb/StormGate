package com.minhdtb.storm;

import com.minhdtb.storm.base.AbstractApplication;
import com.minhdtb.storm.gui.application.ApplicationView;
import com.minhdtb.storm.gui.newprofile.DialogNewProfileView;
import com.minhdtb.storm.gui.openprofile.DialogOpenProfileView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.Environment;
import reactor.bus.EventBus;

@SpringBootApplication
public class StormGateApplication extends AbstractApplication {

    @Autowired
    private ApplicationView applicationView;
    @Autowired
    private DialogNewProfileView dialogNewProfileView;
    @Autowired
    private DialogOpenProfileView dialogOpenProfileView;

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Storm Gateway Service - Copyright © 2016");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("logo.png")));
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        applicationView
                .setApplication(this)
                .setStage(primaryStage)
                .show();
    }

    public void showDialogNewProfile() {
        dialogNewProfileView
                .setTitle("New Profile")
                .setModality(Modality.WINDOW_MODAL)
                .setOwner(applicationView)
                .setApplication(this)
                .show();
    }

    public void showDialogOpenProfile() {
        dialogOpenProfileView
                .setTitle("Open Profile")
                .setModality(Modality.WINDOW_MODAL)
                .setOwner(applicationView)
                .setApplication(this)
                .show();
    }

    public static void main(String[] args) {
        launchApp(StormGateApplication.class, args);
    }
}
