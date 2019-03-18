package application;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
	private RadioButton selected;
	
	@FXML
	private void onInput(ActionEvent event) {
		
		table1.getItems().clear();
		
		String input = inputText.getText();
		if (input.length() > 12) {
			message.setVisible(true);
			message.setText("Numero inválido ingresado: Solo 12 bits");
		} else if (input.length() == 0) {
			message.setVisible(true);
			message.setText("Ingrese un número");
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
					TableColumn<List<String>,String> columnTemp = (TableColumn)table1.getColumns().get(i);
					final int colIndex = i ;
					columnTemp.setCellValueFactory(data -> {
		                List<String> rowValues = data.getValue();
		                String cellValue ;
		                if (colIndex < rowValues.size()) {
		                    cellValue = rowValues.get(colIndex);
		                } else {
		                     cellValue = "" ;
		                }
		                return new ReadOnlyStringWrapper(cellValue);
		            });
					
					if (isTwoN(i)) {
						columnTemp.setStyle("-fx-my-cell-background: blue;");
					}
				}
				
				List<String> listaIni = new ArrayList<>();
				this.getIniList(input,listaIni);
				listaIni.add(0,"Sin Paridad");
				
				List<String> listaEnd = hamming.hammingCode(input, selected.isSelected());
				listaEnd.add(0,"Con Paridad");
				
				List<List<String>> listaParidad = new ArrayList<>();	
				List<String> lista1 = new ArrayList<>();
				lista1.add("P1");
				List<String> lista2 = new ArrayList<>();
				lista2.add("P2");
				listaParidad.add(lista1);
				listaParidad.add(lista2);
				
				if (input.length() > 1 && input.length() <= 4) {
					List<String> lista3 = new ArrayList<>();
					lista3.add("P3");
					listaParidad.add(lista3);
				} else if (input.length() > 4 && input.length() <= 11) {
					List<String> lista3 = new ArrayList<>();
					lista3.add("P3");
					listaParidad.add(lista3);
					List<String> lista4 = new ArrayList<>();
					lista4.add("P4");
					listaParidad.add(lista4);
				} else {
					List<String> lista3 = new ArrayList<>();
					lista3.add("P3");
					listaParidad.add(lista3);
					List<String> lista4 = new ArrayList<>();
					lista4.add("P4");
					listaParidad.add(lista4);
					List<String> lista5 = new ArrayList<>();
					lista5.add("P5");
					listaParidad.add(lista5);
				}
				this.getParityPos( hamming.hammingCode(input, selected.isSelected()), listaParidad);
				
				table1.getItems().add(listaIni);
				for (List<String> lista : listaParidad) {
					table1.getItems().add(lista);
				}
				table1.getItems().add(listaEnd);
				
				this.parityText.setText(String.join("",hamming.hammingCode(input, selected.isSelected())));
			} else {
				message.setVisible(true);
				message.setText("Numero inválido ingresado: "+"\n"+ input+ " no es bianrio.");
				inputText.clear();
			}
		}
	}
	
	@FXML
	private void onRevision(ActionEvent event) {
		for (int i = 0; i < table2.getColumns().size() ; i++) {
			TableColumn<List<String>,String> columnTemp = (TableColumn)table2.getColumns().get(i);
			final int colIndex = i ;
			columnTemp.setCellValueFactory(data -> {
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
	}
	
	private void getIniList(String input, List<String> list){
		
		int index = 1;
		for (int cont = 0; cont < input.length(); cont++) {
			while(isTwoN(index)) {
				list.add("");
				index++;
			}
			list.add(input.substring(cont, cont+1));
			index++;
		}
	}
	
	private boolean isTwoN (int num) {
		int cont = 0;
		int temp = 0;
		
		while (temp < num) {
			temp = (int) Math.pow(2, cont);
			
			if(temp == num) {
				return true;
			}
			cont++;
		}
		return false;
	}
	
	private void getParityPos(List<String> hamming, List<List<String>> paridad) {
		
		for (int i = 0; i < paridad.size(); i++) {
			int gap = (int)Math.pow(2, i);
			int index = 1;
			List<String> actual = paridad.get(i);
			
			while(index != gap) {
				actual.add("");
				index++;
			}
			
			int gapCounter = gap;
			boolean gapCond = true;
			
			for (int j = gap-1; j < hamming.size(); j++) {
				if (gapCond && gapCounter > 0) {
					actual.add(hamming.get(j));
					gapCounter --;
				} else if (!gapCond && gapCounter > 0) {
					actual.add("");
					gapCounter --;
				} else {
					gapCounter = gap;
					gapCond = !gapCond;
					j--;
				}
			}
		}
	}
}
