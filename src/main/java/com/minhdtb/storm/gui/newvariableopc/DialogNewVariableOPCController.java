package com.minhdtb.storm.gui.newvariableopc;

import com.google.common.base.Strings;
import com.minhdtb.storm.base.AbstractController;
import com.minhdtb.storm.common.NamedValueType;
import com.minhdtb.storm.common.Utils;
import com.minhdtb.storm.core.data.StormChannelOPCClient;
import com.minhdtb.storm.core.data.StormVariableOPC;
import com.minhdtb.storm.entities.Channel;
import com.minhdtb.storm.gui.listopctag.DialogListOpcTagView;
import com.minhdtb.storm.services.DataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DialogNewVariableOPCController extends AbstractController {

    @FXML
    public TextField editVariableName;
    @FXML
    public TextField editTagName;
    @FXML
    public ComboBox<NamedValueType> comboBoxVariableType;
    @FXML
    public Button buttonListTags;

    private final DataManager dataManager;

    private final DialogListOpcTagView dialogListOpcTagView;

    private ResourceBundle resources;

    @Autowired
    public DialogNewVariableOPCController(DialogListOpcTagView dialogListOpcTagView, DataManager dataManager) {
        Assert.notNull(dataManager, "DataManager must not be null");

        this.dataManager = dataManager;
        this.dialogListOpcTagView = dialogListOpcTagView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        comboBoxVariableType.getItems().addAll(
                new NamedValueType(resources.getString("TXT001"), 0),
                new NamedValueType(resources.getString("TXT002"), 1),
                new NamedValueType(resources.getString("TXT003"), 2),
                new NamedValueType(resources.getString("TXT004"), 3));
        buttonListTags.setOnAction(event -> {
            Channel channel = ((DialogNewVariableOPCView) getView()).getChannel();
            if (channel != null) {
                StormChannelOPCClient stormChannelOPCClient = new StormChannelOPCClient(channel);
                dialogListOpcTagView.setHost(stormChannelOPCClient.getHost());
                dialogListOpcTagView.setProgId(stormChannelOPCClient.getProgId());
                dialogListOpcTagView.showDialog(getView());
            }
        });

        getSubscriber().on("opc:select", object -> editTagName.setText((String) object));
    }

    @Override
    public void onShow(WindowEvent event) {
        editVariableName.setText(resources.getString("TXT005"));
        comboBoxVariableType.getSelectionModel().selectFirst();
        editTagName.setText("");
    }

    public void actionOK() {
        if (Strings.isNullOrEmpty(editTagName.getText())) {
            Utils.showError(getView(), resources.getString("MSG001"));
            return;
        }

        NamedValueType variableType = comboBoxVariableType.getValue();
        StormVariableOPC variableOPC = new StormVariableOPC();
        variableOPC.setName(editVariableName.getText());
        variableOPC.setTagName(editTagName.getText());
        variableOPC.setDataType(variableType.getValue());

        Channel channel = ((DialogNewVariableOPCView) getView()).getChannel();

        if (!dataManager.existVariable(channel, variableOPC.getRaw())) {
            getPublisher().publish("application:addVariable", variableOPC.getRaw());
            close();
        } else {
            Utils.showError(getView(), String.format(resources.getString("MSG002"), variableOPC.getName()));
        }
    }

    public void actionCancel() {
        close();
    }
}
