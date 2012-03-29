public class berlekamp {
	public static void main(String[] args) {
		int lfsr[] = {1,1,1,0,0,1,1}; //Initializing LFSR with non-zero initial state
		int next = 0;
		int[] pure = new int[60];
		int[] filter = new int[60];
		for(int i = 0; i < filter.length; i++) {
			next = (lfsr[5]+lfsr[6]) % 2;
			for(int j = lfsr.length-1; j > 0; j--) 
				lfsr[j] = lfsr[j-1];
			lfsr[0] = next;
			pure[i] = lfsr[0];
			filter[i] = f(lfsr[0],lfsr[2],lfsr[6]);
		}

		System.out.println("Before applying boolean function");
		berlekamp_massey(pure);
		System.out.println();
		System.out.println("After applying filter");
		berlekamp_massey(filter);
	}

	static void berlekamp_massey(int[] s) {
		final int N = s.length;
		int[] b = new int[N];
		int[] c = new int[N];
		int[] temp = new int[N];
		b[0] = 1; 
		c[0] = 1;
		int l = 0; int m = -1; int d = 0;
		for (int n = 0; n < N; n++) {
			d = 0;
			for(int i = 0; i <= l; i++) 
				d ^= c[i] * s[n-i]; //Computing n-th discrepancy
			if(d == 1) { //If n-th discrepancy is one, calculate new generating polynomial and complexity
				for(int i = 0; i < N; i++) 
					temp[i] = c[i];
				for (int i = 0; i < N - (n-m); i++) 
					c[(n-m) + i] ^= b[i];
				if (l <= n / 2) {
					l = n + 1 - l;
					m = n;
					for(int i = 0; i < N; i++)
						b[i] = temp[i];
				}
			}
		}

		//Checking if discrepancy is 0 for every l-subsequence
		for(int j = 1; j < N-l; j++) {
			d = s[N-j];
			for(int i = 1; i <= l; i++) 
				d ^= c[i]*s[N-i-j];
			if(d != 0) System.out.println("Error!");
		}
		System.out.println("Complexity is "+l);
		System.out.print("Generating polynomial is: ");
		for(int i = 0; i < l; i++) 
			System.out.print((c[i] > 0) ? "x^"+(l-i) + ((i<l-1) ? "+" : "") : "");
		System.out.println("+1");
	}
	static int f(int x1, int x2, int x3) {
		return (x1*x2)^x1^x2^x3;
	}
}