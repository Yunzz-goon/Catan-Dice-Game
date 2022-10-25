package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestRollDice {

    public static String resourceVectorToString(int[] rv) {
	StringJoiner j = new StringJoiner(",");
	for (int i = 0; i < 6; i++)
	    j.add(Integer.toString(rv[i]));
	return "{" + j.toString() + "}";
    }

    private String errorPrefix(int n, int[] rv) {
        return "CatanDice.rollDice(" + n + ", " + resourceVectorToString(rv) + ")";
    }

    private void checkReturn(int n, int[] in, int[] out) {
	String prefix = errorPrefix(n, in);
	int n_in = 0;
	int n_out = 0;
	for (int i = 0; i < 6; i++) {
	    n_in += in[i];
	    n_out += out[i];
	    assertTrue(out[i] >= in[i], prefix); // only add, no decrement of dice.
	}
	assertTrue(n_in + n == n_out, prefix); // test the total change of resource
    }

    private void test(int n, int[] in) {
		int[] copy_of_in = Arrays.copyOf(in, 6); // would be used as output
		CatanDice.rollDice(n, copy_of_in);
		checkReturn(n, in, copy_of_in);
    }

    private void test2(int n1, int n2, int[] in) {
		int[] copy1 = Arrays.copyOf(in, 6);
		CatanDice.rollDice(n1, copy1);
		checkReturn(n1, in, copy1);
		int[] copy2 = Arrays.copyOf(copy1, 6);
		CatanDice.rollDice(n2, copy2);
		//System.out.println("rollDice: " + resourceVectorToString(copy1) + " + roll(" + n2 + ") -> " + resourceVectorToString(copy2));
		checkReturn(n2, copy1, copy2);
    }

	private void test3(int n1, int n2, int n3, int[] in) {
		int[] copy1 = Arrays.copyOf(in, 6);
		CatanDice.rollDice(n1, copy1);
		checkReturn(n1, in, copy1);
		int[] copy2 = Arrays.copyOf(copy1, 6);
		CatanDice.rollDice(n2, copy2);
		//System.out.println("rollDice: " + resourceVectorToString(copy1) + " + roll(" + n2 + ") -> " + resourceVectorToString(copy2));
		checkReturn(n2, copy1, copy2);
		int[] copy3 = Arrays.copyOf(copy2, 6);
		CatanDice.rollDice(n3, copy3);
		checkReturn(n3, copy2, copy3);
	}

	// roll once
    @Test
    public void rollAll() {
	int[] empty = { 0, 0, 0, 0, 0, 0 };
	for (int k = 0; k < 100; k++) {
	    test(6, empty);
	}
    }

	// roll twice
    @Test
    public void rollTwice() {
	int[] empty = { 0, 0, 0, 0, 0, 0 };
	for (int k = 0; k < 100; k++) {
	    test2(3, 3, empty);
	}
    }

	// roll triple
	@Test
	public void rollTriple() {
		int[] empty = { 0, 0, 0, 0, 0, 0 };
		for (int k = 0; k < 100; k++) {
			test3(1, 2, 3, empty);
		}
	}

	@Test
	public void rollFromGeneral(){
		Random random = new Random(1100);
		int[] in = new int[6];
		for (int i = 0; i < 6; i++){
			in[i] = random.nextInt(10000);
		}
		for (int k = 0; k < 100; k++) {
			test3(1, 2, 3, in);
		}
	}

	@Test
	public void rollOnlyOne(){
		int[] empty = {0, 0, 0, 0, 0, 0};
		for (int k = 0; k < 100; k++) {
			test(1, empty);
		}
	}

    // junit launcher doesn't work, so just use a simple main to run
    // the test functions...
    public static void main(String[] args) {
	TestRollDice tests = new TestRollDice();
	System.out.println("testing...");
	tests.rollAll();
	tests.rollTwice();
	tests.rollTriple();
	tests.rollFromGeneral();
	tests.rollOnlyOne();
	System.out.println("all done!");
    }
}
