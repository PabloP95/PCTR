public class Conductor {
	private String nombre, DNI, tipo, direccion, matricula;
	private int edad, puntos;

	public Conductor(String nombre, String DNI, String Tipo, String Direccion,
			int edad) {
		this.nombre = nombre;
		this.DNI = DNI;
		this.tipo = Tipo;
		this.direccion = Direccion;
		this.edad = edad;
		puntos = 12;
	}

	public synchronized String getNombre() {
		return nombre;
	}

	public synchronized void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public synchronized String getDNI() {
		return DNI;
	}

	public synchronized void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public synchronized String getTipo() {
		return tipo;
	}

	public synchronized void setTipo(String Tipo) {
		tipo = Tipo;
	}

	public synchronized String getDireccion() {
		return direccion;
	}

	public synchronized void setDireccion(String Direccion) {
		direccion = Direccion;
	}

	public synchronized int getPuntos() {
		return puntos;
	}

	public synchronized void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public synchronized String getMatricula() {
		return matricula;
	}

	public synchronized void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public synchronized int getEdad() {
		return edad;
	}

	public synchronized void setEdad(int edad) {
		this.edad = edad;
	}

	public synchronized String toString() {
		return ("Nombre : " + nombre + "\nEdad : " + edad + " anios\nDomicilo : " + direccion
				+ "\nPosee el carnet de tipo " + tipo + "\nPuntos = "+puntos);
	}

}
