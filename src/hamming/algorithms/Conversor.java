package hamming.algorithms;

public class Conversor {
	private String[] bcd;

	public Conversor() {
		this.bcd = new String[] { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001" };
	}
	
	public String binaryToBCD(String num) {
		int decimal = Integer.parseInt(num,2);
		String salida = "";
		do {
			salida = bcd[decimal % 10] + " " + salida;
			decimal /= 10;
		} while (decimal != 0);
		return salida;
	}
	
	public boolean isBin(String num) {
		String thisNum = num;
		
		if (thisNum.length() > 12) {
			return false;
		}
		while (thisNum.length() > 0) {
			if (thisNum.substring(thisNum.length()-1).compareTo("1") == 0 || 
					thisNum.substring(thisNum.length()-1).compareTo("0") == 0) {
				if ( thisNum.length() == 1) {
					thisNum = "";
				} else {
					thisNum = thisNum.substring(0, thisNum.length()-1);
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	public String binToHex(String num) {
		int decimal = Integer.parseInt(num,2);
		String hexStr = Integer.toString(decimal,16);
		return hexStr.toUpperCase();
	}
	
	public String binaryToDecimal(String binario) {
		if (binario.length() != 0) {
			int numBinario = Integer.parseInt(binario,2);
			
			String Salida = Integer.toString(numBinario);
			return Salida;
		} else {
			return "0";
		}
	}
	
//	public static void main(String[] args) {
//		Conversor  conversor = new Conversor ();
//		String decimal = conversor.binaryToDecimal("101010101010");
//		String bcd = conversor.binaryToBCD("101010101010");
//		String hex = conversor.binToHex("101010101010");
//		
//		System.out.println("Decimal: " + decimal + ", Hex: " + hex + ", BDC: " + bcd);
//	}
}
