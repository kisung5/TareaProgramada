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
}
