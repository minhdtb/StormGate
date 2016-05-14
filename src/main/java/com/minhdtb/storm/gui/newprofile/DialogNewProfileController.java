package com.minhdtb.storm.gui.newprofile;

import com.minhdtb.storm.base.AbstractController;
import static com.minhdtb.storm.common.GlobalConstants.ERROR_PROFILE_EXISTS_KEY;
import static com.minhdtb.storm.common.GlobalConstants.NEW_PROFILE_KEY;

import com.minhdtb.storm.common.Utils;
import com.minhdtb.storm.entities.Profile;
import com.minhdtb.storm.services.DataManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DialogNewProfileController extends AbstractController {

    @FXML
    private TextField editNewProfileName;

    @Autowired
    private DataManager dataManager;

    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    public void actionCancel() {
        this.close();
    }

    public void actionOK() {
        Profile profile = new Profile(editNewProfileName.getText());

        if (!dataManager.existProfile(profile)) {
            getPublisher().publish("application:newProfile", profile);
            close();
        } else {
            Utils.showError(getView(), String.format(resources.getString(ERROR_PROFILE_EXISTS_KEY), profile.getName()));
        }
    }

    @Override
    public void onShow(WindowEvent event) {
        editNewProfileName.setText(resources.getString(NEW_PROFILE_KEY));
    }
}
