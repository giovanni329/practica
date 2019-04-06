package co.edu.cur.practica.interfaces;

import co.edu.cur.practica.Persona;

public interface ITransaccionPersonas {

	public Persona consultar(String documento);
	public int ingresar(Persona persona);
	public int actualizar (Persona persona);
	public int eliminar (String documento);
}