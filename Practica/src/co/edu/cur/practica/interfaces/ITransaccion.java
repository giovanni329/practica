package co.edu.cur.practica.interfaces;

import java.sql.Connection;
/**
 * @author GIO,
 *Esta interface me permite tener un contrato para el manejo de transacciones de Base de Datos
 */
public interface ITransaccion {
	
	public Connection conectar(); 

}