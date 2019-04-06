package co.edu.cur.practica.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import co.edu.cur.practica.Persona;
import co.edu.cur.practica.implementaciones.TransaccionMYSQL;
import co.edu.cur.practica.interfaces.ITransaccion;
import co.edu.cur.practica.interfaces.ITransaccionPersonas;

public class TransaccionPersona implements ITransaccionPersonas {

	@Override
	public Persona consultar(String documento) {
		// TODO Auto-generated method stub
		//Creo la persona vacia
		Persona personaRetorna = new Persona();
		//creo una estructura preparada
		PreparedStatement ps = null;
		
		try {
		    //llamo a la conexion a MYSQL
			ITransaccion transaccionMySQL = new TransaccionMYSQL();
			Connection con = transaccionMySQL.conectar();
			//hago la consulta al documento del que envien en documento
			String sql = "select nombres,documento from t_personas where documento=?";
			//valido si el sql es correcto para enviar a la BD
			ps = con.prepareStatement(sql);
				//envio parametro documento para cambiarlo en la interrogacion
			ps.setString(1, documento);
			
				//ejecuto la consulta en la bdç
           ResultSet rs = ps.executeQuery();
           		//recupero los datos de la base de datos
           if(rs.next()) {
        	   personaRetorna.setNombre(rs.getString("nombres"));
        	   personaRetorna.setDocumento(rs.getString(2));
           }
           		//cierro cursor(trae los datos)
           rs.close();
           		// cierro la estructura preparada
           ps.close();
           		// cierro la conexion
           con.close();
	
		} catch (Exception e) {
           e.printStackTrace();
		}
		return personaRetorna;
	}
	

	@Override
	public int ingresar(Persona persona) {
			
		//Creo una estructura preparada
		PreparedStatement ps = null;
		//validar si lo ingreso o no
		int retorno = 0;
		
		try {
		    //llamo a la conexion a MYSQL
			ITransaccion transaccionMySQL = new TransaccionMYSQL();
			Connection con = transaccionMySQL.conectar();
			
			//hago el insert de la persona con el documento y el nombre
			String sql = "insert into t_personas(documento,nombres)values(?,?)";
            						//valido si el sql es correcto para enviar a la bd
			ps = con.prepareStatement(sql);
									//envio parametros documento y nombre para cambiarlo en las interrogaciones
			ps.setString(1, persona.getDocumento());
			ps.setString(2, persona.getNombre());

			//ejecuto el insert en la bd
             retorno = ps.executeUpdate();
            // cierro la estructura preparada
            ps.close();
            // cierro la conexion
            con.close();
	
		} catch (Exception e) {
           e.printStackTrace();
		}
		return retorno;
	}
	
	@Override
	public int actualizar(Persona persona) {
		// TODO Auto-generated method stub
		//creo una estructura preparada
		PreparedStatement ps = null;
		//validar si lo ingreso o no
		int retorno = 0;
		
		try {
		    //llamo a la conexion a MYSQL
			ITransaccion transaccionMySQL = new TransaccionMYSQL();
			Connection con = transaccionMySQL.conectar();
			
			//hago el update de la persona con el documento y el nombre
			String sql = "update t_personas  set nombres=? where documento=?";
            //valido si el sql es correcto para enviar a la bd
			ps = con.prepareStatement(sql);
			//envio parametros documento y nombre para cambiarlo en las interrogaciones
			ps.setString(1, persona.getNombre());
			ps.setString(2, persona.getDocumento());

			//ejecuto el insert en la bd
             retorno = ps.executeUpdate();
             // cierro la estructura preparada
           ps.close();
           // cierro la conexion
           con.close();
	
		} catch (Exception e) {
           e.printStackTrace();
		}
		
		return retorno;

	}

	@Override
	public int eliminar(String documento) {
		// TODO Auto-generated method stub
		//creo una estructura preparada
		PreparedStatement ps = null;
		//validar si lo ingreso o no
		int retorno = 0;

		try {
		    //llamo a la conexion a MYSQL
			ITransaccion transaccionMySQL = new TransaccionMYSQL();
			Connection con = transaccionMySQL.conectar();
			
			//hago el delete de la persona con el documento 
			String sql = "delete from t_personas  where documento=?";
            //valido si el sql es correcto para enviar a la bd
			ps = con.prepareStatement(sql);
			//envio parametros documento y nombre para cambiarlo en las interrogaciones
			ps.setString(1, documento);
			
			//ejecuto el insert en la bd
             retorno = ps.executeUpdate();
             // cierro la estructura preparada
             ps.close();
           // cierro la conexion
             con.close();
	
		} catch (Exception e) {
           e.printStackTrace();
		}
				
		return retorno;
	}

	public static void main(String[] args) {
		
		ITransaccionPersonas tp = new TransaccionPersona();
	       Persona p = tp.consultar(args[0]);
	      System.out.println("Nombre es:"+p.getNombre());
	    //int retornoInsert =tp.ingresar(new Persona(args[0],args[1]));
	     //int retornoUpdate =tp.actualizar(new Persona(args[0],args[1]));
	       int retornoUpdate =tp.eliminar(args[0]);
	    
	      
	       if(retornoUpdate >0) {
	    	System.out.println("ingreso la persona");}
	       else {
	    	  System.out.println("error en la persona");
	    	   
	    	   
	       }
	}

}