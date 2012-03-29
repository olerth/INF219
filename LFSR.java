/*
 * LFSR.java
 * Created on Feb 21, 2005
 */

/**
 * Linear Feedback Shift Register
 */
public final class LFSR {
    
    private static final int M = 128;
    // hard-coded for 128-bits
    private static final int[] TAPS = {1, 2, 22, 32};
    
    private final boolean[] bits = new boolean[M + 1];
    
    public LFSR(int seed) {
        for(int i = 0; i < M; i++) {
            bits[i] = (((1 << i) & seed) >>> i) == 1;
        }
    }
    
    public LFSR() {
        this((int)System.currentTimeMillis());
    }
    
    /* generate a random int uniformly on the interval [-2^31 + 1, 2^31 - 1] */
    public int nextInt() {
        int next = 0;
        for(int i = 0; i < M; i++) {
            next |= (bits[i] ? 1 : 0) << i;
        }
        
        if (next < 0) next++;
        
        bits[M] = false;
        for(int i = 0; i < TAPS.length; i++) {
            bits[M] ^= bits[M - TAPS[i]];
        }
        
        // shift all the registers
        for(int i = 0; i < M; i++) {
            bits[i] = bits[i + 1];
        }
        
        return mod(next);
    }
    
    /** returns random double uniformly over [0, 1) */
    public double nextDouble() {
        return ((nextInt() / (Integer.MAX_VALUE + 1.0)) + 1.0) / 2.0;
    }
    
    /** returns random boolean */
    public boolean nextBoolean() {
        return nextInt() >= 0;
    }
    
    void printBits() {
        System.out.print(bits[M] ? 1 : 0);
        System.out.print(" -> ");
        for(int i = M - 1; i >= 0; i--) {
            System.out.print(bits[i] ? 1 : 0);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {        
        LFSR rng = new LFSR();
        for(int i = 1; i <= 10; i++) {
            boolean next = rng.nextBoolean();
            System.out.println(next);
        }
    }
    
    public int getNlast(int n) {
    	
    	return n;
    }
    
    private static int mod(int n) {
    	if(n < 0) return ((-1)*n) % 2;
    	return n % 2;
    }
}