package physics.math;

public interface RowOperation {

	public static RowOperation addRow(int a, int b, Scalar c) {
		return new RowOperation() {

			@Override
			public Scalar changeDeterminant(Scalar s) {
				return s;
			}

			@Override
			public void operate(Scalar[][] arr) {
				for (int j = 0; j < arr[0].length; j++) {
					arr[a][j] = arr[a][j].add(arr[b][j].multiply(c));
				}
			}

			@Override
			public String toString() {
				return String.format("R%d <- R%d + R%d * %s", a, a, b, c);
			}
		};
	}

	public static RowOperation multiply(int a, Scalar c) {
		return new RowOperation() {

			@Override
			public Scalar changeDeterminant(Scalar s) {
				return s.multiply(c);
			}

			@Override
			public void operate(Scalar[][] arr) {
				for (int j = 0; j < arr[0].length; j++) {
					arr[a][j] = arr[a][j].multiply(c);
				}
			}

			@Override
			public String toString() {
				return String.format("R%d <- R%d * %s", a, a, c);
			}
		};
	}

	public static RowOperation swap(int a, int b) {
		return new RowOperation() {

			@Override
			public Scalar changeDeterminant(Scalar s) {
				return s.negate();
			}

			@Override
			public void operate(Scalar[][] arr) {
				for (int j = 0; j < arr[0].length; j++) {
					Scalar temp = arr[a][j];
					arr[a][j] = arr[b][j];
					arr[b][j] = temp;
				}
			}

			@Override
			public String toString() {
				return String.format("R%d <-> R%d", a, b);
			}
		};
	}

	Scalar changeDeterminant(Scalar s);

	void operate(Scalar[][] arr);
}
