package hamming.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Hamming {
	
	public String getParity (String parityNum, int parity) {
		int onumber = 0;
		
		for (char a : parityNum.toCharArray()) {
			if ( a == '1') {
				onumber++;
			}
		}
//		Paridad impar
		if ( parity == 0 ) {
			if ( onumber%2 == 0) {
				return "0";
			} else {
				return "1";
			}
		} 
//		Paridad par
		else {
			if ( onumber%2 == 0) {
				return "1";
			} else {
				return "0";
			}
		}
	}
	
	public List<String> hammingCode(String binary) {
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
		System.out.println("El número es:" + n);
		return hammingCodeAux(binary, n);
	}

	private List<String> hammingCodeAux(String binary, int n) {
		int cont = 0;
		int parity = 0;
		
		String binaryExtended = makeSpaces(binary, n);
		String subString = "";
		while (cont <= n) {
			for (int i = (int) (Math.pow(2, cont) - 1); i < binaryExtended.length(); i += (int) 2 * Math.pow(2, cont)) {

				if (i + (int) Math.pow(2, cont) > binaryExtended.length()) {
					subString = binaryExtended.substring(i, binaryExtended.length());
				} else {
					subString = binaryExtended.substring(i, i + (int) Math.pow(2, cont));
				}
				System.out.println(subString);
				while (subString.length() != 0) {
					if (subString.charAt(0) == '1') {
						parity++;
					}
					subString = subString.substring(1, subString.length());
				}
			}
			int pos = (int) Math.pow(2, cont)-1;
			if ((int)parity%2 != 0) {
				binaryExtended = new StringBuilder(binaryExtended).replace(pos, pos+1, "1").toString();	
			}
			
			cont++;
			System.out.println("pariedad: " + parity + "\n");
			parity = 0;
		}
		
		return parityList(binaryExtended);
	}
	
	private List<String> parityList(String binary) {
		List<String> parityL = new ArrayList<String>();
		for(int i = 0; i < binary.length(); i++ ) {
			parityL.add(""+binary.charAt(i));
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
				System.out.println("hola");
				str += "0" + binary.charAt(binary.length() - 1);
			} else {
				str += "0";
			}
			cont++;
		}
		System.out.println(str);
		
		System.out.println();
		return str;
	}
}
