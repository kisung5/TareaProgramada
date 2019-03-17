package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import hamming.algorithms.*;

public class MainController {
	
	Conversor conversor = new Conversor();
	
	@FXML
	private TextField inputText;
	
	@FXML 
	private Button inputButton;
	
	@FXML
	private Label message;
	
	@FXML
	private ToggleGroup paridad;
	
	@FXML
	private Label decimalOut;
	
	@FXML
	private Label hexaOut;
	
	@FXML
	private Label bcdOut;
	
	@FXML
	private TableView table1;
	
	@FXML
	private TableView table2;
	
	@FXML
	private TextField parityText;
	
	@FXML
	private void onInput(ActionEvent event) {
		String input = inputText.getText();
		if (input.length() > 12) {
			message.setVisible(true);
			message.setText("Numero inválido ingresado: Solo 12 bits");
		} else {
			if (conversor.isBin(input)) {
				message.setVisible(false);
				String decimalTemp = conversor.binaryToDecimal(input);
				decimalOut.setText(decimalTemp);
				hexaOut.setText(conversor.binToHex(input));
				bcdOut.setText(conversor.binaryToBCD(input));
			} else {
				message.setVisible(true);
				message.setText("Numero inválido ingresado: "+"\n"+ input+ " no es bianrio.");
				inputText.clear();
			}
		}
	}
	
	@FXML
	private void onRevision(ActionEvent event) {
		
	}
}
