package com.minhdtb.storm.base;

import com.minhdtb.storm.common.Publisher;
import javafx.fxml.Initializable;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public abstract class AbstractController implements Initializable {

    @Autowired
    private Publisher<Object> publisher;

    private AbstractView view;

    public Publisher<Object> getPublisher() {
        return publisher;
    }

    public void setView(AbstractView view) {
        this.view = view;
    }

    public AbstractView getView() {
        return this.view;
    }

    public void close() {
        this.getView().getStage().close();
    }

    protected abstract void onShow(WindowEvent event);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
