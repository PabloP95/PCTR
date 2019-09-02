/**
 * Clase auxiliar encargada de crear matrices de 10x10. Se utiliza junto a
 * {@link monitorCadena} y {@link UsaMonitorCadena}
 * 
 * @author Pablo Piedad Garrido
 * 
 */

public class matriz {
	public double[][] mat = new double[10][10];

	/**
	 * Constructor de matrices
	 * 
	 * @param m
	 */
	public matriz(double[][] m) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j] = m[i][j];
			}
		}
	}

}
