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
	String current = "";
	int level = 0;
	int tempColumn;
	
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
	
	@SuppressWarnings("unchecked")
	@FXML
    public void initialize() {
		for (int i = 0; i < table1.getColumns().size() ; i++) {
			TableColumn<List<String>,String> columnTemp = (TableColumn<List<String>, String>)table1.getColumns().get(i);
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
		for (int i = 0; i < table2.getColumns().size() ; i++) {
			TableColumn<List<String>,String> columnTemp = (TableColumn<List<String>, String>)table2.getColumns().get(i);
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
	
	@SuppressWarnings("unchecked")
	@FXML
	private void onInput(ActionEvent event) {
		
		table1.getItems().clear();
		table2.getItems().clear();
		
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
					TableColumn<List<String>,String> columnTemp = (TableColumn<List<String>, String>)table1.getColumns().get(i);
					
					if (isTwoN(i)) {
						columnTemp.setStyle("-fx-text-fill: blue;");
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
					level = 1;
				} else if (input.length() > 4 && input.length() <= 11) {
					List<String> lista3 = new ArrayList<>();
					lista3.add("P3");
					listaParidad.add(lista3);
					List<String> lista4 = new ArrayList<>();
					lista4.add("P4");
					listaParidad.add(lista4);
					level = 2;
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
					level = 3;
				}
				this.getParityPos( hamming.hammingCode(input, selected.isSelected()), listaParidad);
				
				table1.getItems().add(listaIni);
				for (List<String> lista : listaParidad) {
					table1.getItems().add(lista);
				}
				table1.getItems().add(listaEnd);
				
				current = String.join("",hamming.hammingCode(input, selected.isSelected()));
				this.parityText.setText(String.join("",hamming.hammingCode(input, selected.isSelected())));
			} else {
				message.setVisible(true);
				message.setText("Numero inválido ingresado: "+"\n"+ input+ " no es bianrio.");
				inputText.clear();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	private void onRevision(ActionEvent event) {
		
		resetTable(table2);
		table2.getItems().clear();
		
		String sended = current;
		String recieved = parityText.getText();
		
		boolean isError;
		int errorIndex = hamming.getErrorIndex(sended, recieved);
		
		if (errorIndex == 0) {
			isError = false;
		} else {
			isError = true;
		}
		
		List<String> iniList = new ArrayList<>();
		for (int i = 0; i < recieved.length(); i++) {
			iniList.add(recieved.substring(i, i+1));
		}
		
		List<List<String>> listaParidad = new ArrayList<>();	
		List<String> lista1 = new ArrayList<>();
		lista1.add("P1");
		List<String> lista2 = new ArrayList<>();
		lista2.add("P2");
		listaParidad.add(lista1);
		listaParidad.add(lista2);
		
		if ( level == 1) {
			List<String> lista3 = new ArrayList<>();
			lista3.add("P3");
			listaParidad.add(lista3);
		} else if ( level == 2) {
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
		this.getParityPos( iniList, listaParidad);
		this.parityTest(listaParidad, errorIndex, table2.getColumns().size());
		
		for (int i = 0; i < table2.getColumns().size() ; i++) {
			TableColumn<List<String>,String> columnTemp = (TableColumn<List<String>, String>)table2.getColumns().get(i);

			if (isError) {
				if ( i == errorIndex) {
					columnTemp.setStyle("text-color: red;");
				}
				else {
					columnTemp.setStyle("text-color: black;");
				}
			}
			if (i == table2.getColumns().size()-1) {
				columnTemp.setStyle("-fx-text-fill: blue;");
			}
			if (isTwoN(i)) {
				columnTemp.setStyle("-fx-text-fill: blue;");
			}
		}
		
		iniList.add(0, "Recibido");
		table2.getItems().add(iniList);
		for (List<String> lista : listaParidad) {
			table2.getItems().add(lista);
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
	
	private void parityTest (List<List<String>> paridad, int error, int tableSize) {
		if (error == 0) {
			for (List<String> par: paridad) {
				this.fillList(par, tableSize);
				par.add(tableSize-2, "No error");
				par.add(tableSize-1, "");
			}
		} else {
			for (List<String> par: paridad) {
				this.fillList(par, tableSize);
				if (par.get(error).contains("0")) { 
					par.add(tableSize-2, "Error");
					par.add(tableSize-1, "1");
				} else  if (par.get(error).contains("1")) {
					par.add(tableSize-2, "Error");
					par.add(tableSize-1, "0");
				} else {
					par.add(tableSize-2, "No error");
					par.add(tableSize-1, "");
				}
			}
		}
	}
	
	private void fillList(List<String> lista, int fin) {
		
		int tempSize = lista.size();
		while (tempSize < fin-1) {
			lista.add("");
			tempSize++;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void resetTable (TableView<List<String>> table) {
		for (int i = 0; i < table2.getColumns().size() ; i++) {
			TableColumn<List<String>,String> columnTemp = (TableColumn<List<String>, String>)table2.getColumns().get(i);

			columnTemp.setStyle("text-color: black;");
			
		}
	}
}
