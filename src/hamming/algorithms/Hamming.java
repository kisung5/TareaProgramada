package hamming.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Hamming {
	private boolean evenOrOdd;

	/**
	 * M�todo principal, se utiliza para realizar la paridad de una cadena de
	 * binarios nueva
	 * 
	 * @param binary
	 *            cadena con n�meros binarios
	 * @param parity
	 *            True o False, true indica paridad Par, false indica paridad impar
	 * @return Lista con Strings, cada indice es un 1 o 0 de la paridad realizada
	 */
	public List<String> hammingCode(String binary, boolean parity) {
		this.evenOrOdd = parity;
		int n = 0;

		if (binary.length() == 1) {
			n = 1;
		} else if (binary.length() < 5) {
			n = 2;
		} else if (binary.length() < 12) {
			n = 3;
		} else {
			n = 4;
		}
		String hamming = hammingCodeAux(binary, n, false);
		return parityList(hamming, hamming.length());
	}
	

	/**
	 * Se verifica el codigo binario
	 * 
	 * @param correctBinaryCode
	 *            cadena con los binarios correctos
	 * @param binaryCode
	 *            cadena de Strings con todos los respectivos 1 y 0
	 * @return Lista con String de 1 y 0, indica, la ultima posici�n de la lista
	 *         tiene el �ndice en donde se encuentra el error, si este �ltimo es 0,
	 *         significa que no hay error
	 */
	public List<String> errorDetection(String correctBinaryCode, String binaryCode) {
		int n = (int) Math.sqrt(correctBinaryCode.length());
		int cont = 0;
		int index = 0;
		String toDetect = hammingCodeAux(binaryCode, n, true);
		int error = 0;
		while (cont <= n) {
			index = (int) Math.pow(2, cont) - 1;
			if (correctBinaryCode.charAt(index) != (toDetect.charAt(index))) {
				error += index + 1;
			}
			cont++;
		}
		return parityList(toDetect + error, toDetect.length());
	}

	private String hammingCodeAux(String binary, int n, boolean detection) {
		int cont = 0;
		int parity = 0;
		String binaryExtended;

		if (detection) {
			binaryExtended = binary;
		} else {
			binaryExtended = makeSpaces(binary, n);
		}
		String subString = "";
		boolean first = false;
		while (cont <= n) {
			first = true;
			for (int i = (int) (Math.pow(2, cont) - 1); i < binaryExtended.length(); i += (int) 2 * Math.pow(2, cont)) {

				if (i + (int) Math.pow(2, cont) > binaryExtended.length()) {
					subString = binaryExtended.substring(i, binaryExtended.length());
				} else {
					subString = binaryExtended.substring(i, i + (int) Math.pow(2, cont));
				}
				while (subString.length() != 0) {
					if (first) {
						first = false;
					} else {

						if (subString.charAt(0) == '1') {
							parity++;
						}
					}
					subString = subString.substring(1, subString.length());
				}
			}
			int pos = (int) Math.pow(2, cont) - 1;
			if ((int) parity % 2 != 0) {
				if (evenOrOdd) {
					binaryExtended = new StringBuilder(binaryExtended).replace(pos, pos + 1, "1").toString();
				} else {
					binaryExtended = new StringBuilder(binaryExtended).replace(pos, pos + 1, "0").toString();
				}
			} else {
				if (evenOrOdd) {
					binaryExtended = new StringBuilder(binaryExtended).replace(pos, pos + 1, "0").toString();
				} else {
					binaryExtended = new StringBuilder(binaryExtended).replace(pos, pos + 1, "1").toString();
				}
			}

			cont++;
			parity = 0;
		}
		
		return binaryExtended;
	}

	private List<String> parityList(String binary, int lenght) {
		List<String> parityL = new ArrayList<String>();
		for (int i = 0; i < binary.length(); i++) {
			if (i < lenght) { 
			parityL.add("" + binary.charAt(i));
			} else {
				parityL.add(binary.substring(lenght));
				break;
			}
		}
		return parityL;

	}

	private String makeSpaces(String binary, int n) {
		String str = "";
		int cont = 0;

		while (cont <= n) {
			if (cont == 1) {
				str += "0" + binary.charAt(0);
			} else if (cont == 2) {
				if (4 > binary.length()) {
					str += "0" + binary.substring(1, binary.length());
				} else {
					str += "0" + binary.substring(1, 4);
				}
			} else if (cont == 3) {
				if (11 > binary.length()) {
					str += "0" + binary.substring(4, binary.length());
				} else {
					str += "0" + binary.substring(4, 11);
				}
			} else if (cont == 4) {
				str += "0" + binary.charAt(binary.length() - 1);
			} else {
				str += "0";
			}
			cont++;
		}

		return str;
	}
}
