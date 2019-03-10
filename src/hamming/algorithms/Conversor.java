package hamming.algorithms;

public class Conversor {
	
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
			int numBinario = Integer.parseInt(binario);
			int digito; int numDecimal = 0; int exponente = 0;
			
			do {
				digito = numBinario%10;
				numDecimal = numDecimal + digito * (int)Math.pow(2, exponente);
				numBinario /= 10;
				exponente++;
			} while (numBinario!= 0);
			
			String Salida = Integer.toString(numDecimal);
			return Salida;
		} else {
			return "0";
		}
	}
}
