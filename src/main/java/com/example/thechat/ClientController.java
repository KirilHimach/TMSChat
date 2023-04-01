package com.example.thechat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClientController {
    private Client client;

    {
        try {
            client = new Client();
            ConnectDB.clients.add(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField textField;
    @FXML
    private ListView listView;



    @FXML
    private void buttonClick(ActionEvent actionEvent) {
        if (!textField.getText().isEmpty()) {
            client.sendMsg(textField.getText());
            listView.getItems().add(textField.getText());
            textField.clear();
        }
        if (client.respond() != null) {
            listView.getItems().add(client.respond());
            request();
        }
    }

    private void request() {
        client.setBufferRespond();
    }

}