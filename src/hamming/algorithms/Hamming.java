package hamming.algorithms;

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
}
