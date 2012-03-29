public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int lfsr[] = {1,1,1,0,0,0,1};
		int next = 0;
		for(int i = 0; i < 1024; i++) {

			next = (lfsr[5]+lfsr[6]) % 2;
			for(int j = lfsr.length-1; j > 0; j--) lfsr[j] = lfsr[j-1];
			lfsr[0] = next;
			System.out.print(f(lfsr[0],lfsr[2],lfsr[6]));
		}
	}
	static int f(int x1, int x2, int x3) {
		
		return (x1*x2)^x1^x2^x3;
	}
}
