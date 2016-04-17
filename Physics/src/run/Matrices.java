package run;

import physics.math.Matrix;

public class Matrices {
	public static void main(String[] args) {
		Matrix a = new Matrix(new double[][] { { 16, 8 }, { 7, 4 } });
		System.out.println(a.getRowCanonicalForm());
		System.out.println(a.getDeterminant());
	}
}
