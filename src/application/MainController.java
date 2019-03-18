package application;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;
import java.util.List;

import hamming.algorithms.*;

public class MainController {
	
	Conversor conversor = new Conversor();
	Hamming hamming = new Hamming();
	
	@FXML
	private TextField inputText;
	
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
	private TableView<List<String>> table1;
	
	@FXML
	private TableView<List<String>> table2;
	
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
				
//				---Conversiones---
				String decimalTemp = conversor.binaryToDecimal(input);
				decimalOut.setText(decimalTemp);
				hexaOut.setText(conversor.binToHex(input));
				bcdOut.setText(conversor.binaryToBCD(input));
				
//				--Hamming----
				for (int i = 0; i < table1.getColumns().size() ; i++) {
					TableColumn<List<String>,String> column1 = (TableColumn)table1.getColumns().get(i);
					final int colIndex = i ;
					column1.setCellValueFactory(data -> {
		                List<String> rowValues = data.getValue();
		                String cellValue ;
		                if (colIndex < rowValues.size()) {
		                    cellValue = rowValues.get(colIndex);
		                } else {
		                     cellValue = "" ;
		                }
		                return new ReadOnlyStringWrapper(cellValue);
		            });
				}
				List<String> listaIni = new ArrayList<>();
				listaIni.add("Sin Paridad");
				
				List<String> lista2 = new ArrayList<>();
				lista2.add("P1");
				
				List<String> lista3 = new ArrayList<>();
				lista3.add("P2");
				
				List<String> lista4 = new ArrayList<>();
				lista4.add("P3");
				
				List<String> lista5 = new ArrayList<>();
				lista5.add("P4");
				
				List<String> lista6 = new ArrayList<>();
				lista6.add("P5");
				
				List<String> listaEnd = new ArrayList<>();
				listaEnd.add("Con Paridad");
				
				table1.getItems().add(listaIni);
				table1.getItems().add(lista2);
				table1.getItems().add(lista3);
				table1.getItems().add(lista4);
				table1.getItems().add(lista5);
				table1.getItems().add(lista6);
				table1.getItems().add(listaEnd);
//				System.out.println(hamming.hammingCode(input, true));
				
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
