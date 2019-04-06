package co.edu.cur.practica.implementaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.cur.practica.interfaces.ITransaccion;

public class TransaccionSQLSERVER implements ITransaccion {
	
	private String url ;
	private String usuario;
	private String clave;
	private String driver ;

	public TransaccionSQLSERVER() {
		// TODO Auto-generated constructor stub
		url = "jdbc:sqlserver://localhost:1433;databaseName=practica"; 
		usuario = "sa";
		clave = "root";
		driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	public Connection conectar() {
		// TODO Auto-generated method stub
		Connection conexion;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url,usuario,clave);
			return conexion;
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error La Clase no Existe " +e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error SQL:" +e.getMessage());
		} catch (Exception e) {
			System.out.println("Error General" +e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		try {
			
		ITransaccion transaccion = new TransaccionSQLSERVER();
		//Obtengo la conexion a SQL Server
		Connection con = transaccion.conectar();
		//Hago la consulta al documento 1
		String sql = "select nombres from t_personas  where documento = '2'";
		//Valido si el sql es correcto para enviar a la BD.
		ps = con.prepareStatement(sql);
		//EJECUTO CONSULTA EN BD
		ResultSet rs= ps.executeQuery();
		//Recupero los datos de la BD
		
		if (rs.next()) {
			//Muestro los datos recuperados
			System.out.println("nombre =" +rs.getString(1));
		}
			rs.close();
		//cierro cursor
			ps.close();
		//cierro estructura preparada
			con.close();
		//cierro la conexion
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}