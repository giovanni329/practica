package co.edu.cur.practica.vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import co.edu.cur.practica.implementaciones.TransaccionSedes;
import co.edu.cur.practica.implementaciones.TransaccionSQLSERVER;
import co.edu.cur.practica.interfaces.ITransaccion;


public class PantallaSede {

	private JFrame frame;
	private JTextField txtcodigo;
	private JTextField txtNombreSede;

	/**
	 * Launch the application.
	 */
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	int retorno = 0;
	private JTextField txtMunicipio;
	
	private void limpiarcuadrotxt() {
		txtcodigo.setText(null);
		txtNombreSede.setText(null);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaSede window = new PantallaSede();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaSede() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.getContentPane().setBackground(new Color(102, 153, 255));
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblControlSedes = new JLabel("CONTROL DE SEDES");
		lblControlSedes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblControlSedes.setBounds(146, 11, 185, 14);
		frame.getContentPane().add(lblControlSedes);
		
		JLabel lblCodigo = new JLabel("Codigo Sede");
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCodigo.setBounds(34, 56, 83, 14);
		frame.getContentPane().add(lblCodigo);
		
		JLabel lblNombre = new JLabel("Nombre Sede");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombre.setBounds(34, 102, 76, 14);
		frame.getContentPane().add(lblNombre);
		
		txtcodigo = new JTextField();
		txtcodigo.setForeground(Color.RED);
		txtcodigo.setBounds(120, 54, 86, 20);
		frame.getContentPane().add(txtcodigo);
		txtcodigo.setColumns(10);
		
		txtNombreSede = new JTextField();
		txtNombreSede.setBounds(120, 99, 225, 20);
		frame.getContentPane().add(txtNombreSede);
		txtNombreSede.setColumns(10);
		
		
		txtMunicipio = new JTextField();
		txtMunicipio.setBounds(120, 130, 201, 20);
		frame.getContentPane().add(txtMunicipio);
		txtMunicipio.setColumns(10);
		
		JLabel lblMunicipio = new JLabel("Municipio");
		lblMunicipio.setBounds(34, 134, 46, 14);
		frame.getContentPane().add(lblMunicipio);
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ITransaccion transaccion= new TransaccionSedes();
					Connection con = transaccion.conectar();
					String sql = "select nombsede,municipsede from t_sedes where codsede = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1, txtcodigo.getText());
					 ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						txtNombreSede.setText(rs.getString ("nombsede"));
						txtMunicipio.setText(rs.getString ("municipsede"));
					}else {
						rs.close();
						ps.close();
						con.close();
						 transaccion= new TransaccionSQLSERVER();
						 con = transaccion.conectar();
						 sql = "select nombsede,municipsede from t_sedes where codsede = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1, txtcodigo.getText());
						 rs = ps.executeQuery();
						if(rs.next()) {
							txtNombreSede.setText(rs.getString ("nombsede"));
							txtMunicipio.setText(rs.getString ("municipsede"));
						}else {
						
						JOptionPane.showMessageDialog(null, "Documento de Persona no Encontrado");
						}
						rs.close();
						ps.close();
						con.close();
					}
					con.close();
			}catch (SQLException e1){
				JOptionPane.showMessageDialog(null,"Error al Acceder a BD");
				e1.printStackTrace();
			}
			}	
		});
		btnConsultar.setBounds(242, 53, 89, 23);
		frame.getContentPane().add(btnConsultar);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBackground(new Color(0, 153, 153));
		btnAgregar.setForeground(new Color(0, 51, 0));
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ITransaccion TransaccionSedes = new TransaccionSedes();
					Connection con = TransaccionSedes.conectar();
					ITransaccion transaccionSQLSERVER = new TransaccionSQLSERVER();
					Connection conSQLServer = transaccionSQLSERVER.conectar();
					String sql = "insert into t_sedes(codsede,nombsede,municipsede)values(?,?,?)";
					ps = con.prepareStatement(sql);
					ps.setString(1, txtcodigo.getText());
					ps.setString(2, txtNombreSede.getText());
					ps.setString(3, txtMunicipio.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Agregado Correctamente a mysql" );
						
				
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Agregar el Registro a mysql" );
					}
					ps.close();
					con.close();
					String sqlSQLServer = ( "insert into t_sedes(codsede,nombsede,municipsede)values(?,?,?)");
					ps = conSQLServer.prepareStatement(sqlSQLServer);
					ps.setString(1, txtcodigo.getText());
					ps.setString(2, txtNombreSede.getText());
					ps.setString(3, txtMunicipio.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Agregadp a SQLServer" );
						limpiarcuadrotxt();
				
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo agraegar a SQLServer" );
					}
					ps.close();
					con.close();
				}
				catch (SQLException e1){
					JOptionPane.showMessageDialog(null,"Error al Acceder a BD");
					e1.printStackTrace();
				}
			}

			
		});
		btnAgregar.setBounds(34, 159, 89, 23);
		frame.getContentPane().add(btnAgregar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ITransaccion TransaccionSedes = new TransaccionSedes();
					Connection con = TransaccionSedes.conectar();
					ITransaccion transaccionSQLSERVER = new TransaccionSQLSERVER();
					Connection conSQLServer = transaccionSQLSERVER.conectar();
					String sql = ("delete from t_sedes where codsede = ?");
					ps = con.prepareStatement(sql);
					ps.setString(1, txtcodigo.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Borradode MYSQL" );
					
				
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Borrar DE mysql" );
					}
					ps.close();
					con.close();
					String sqlSQLServer = ("delete from t_sedes where codsede = ?");
					ps = conSQLServer.prepareStatement(sqlSQLServer);
					ps.setString(1, txtcodigo.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Borrado de SQLServer" );
						limpiarcuadrotxt();
				
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Borrar  de SQLServer" );
					}
					ps.close();
					con.close();
				}
				catch (SQLException e1){
					JOptionPane.showMessageDialog(null,"Error al Acceder a BD");
					e1.printStackTrace();
				}
			}
			
		});
		btnBorrar.setBounds(317, 159, 89, 23);
		frame.getContentPane().add(btnBorrar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
			}
		});
		btnSalir.setBounds(172, 216, 89, 23);
		frame.getContentPane().add(btnSalir);
		
		JButton button = new JButton("Actualizar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ITransaccion TransaccionSedes = new TransaccionSedes();
					Connection con = TransaccionSedes.conectar();
					ITransaccion transaccionSQLSERVER = new TransaccionSQLSERVER();
					Connection conSQLServer = transaccionSQLSERVER.conectar();
					String sql = ("update t_sedes set nombsede = ? where codsede = ?");
					ps = con.prepareStatement(sql);
					ps.setString(2, txtcodigo.getText());
					ps.setString(1, txtNombreSede.getText());
										
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Actualizado Correctamente en MYSQL" );
						
					
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Actualizar el Registro EN MYSQL" );
					}
					ps.close();
					con.close();
					String sqlSQLServer = ("update t_sedes set nombsede = ? where codsede = ?");
					ps = conSQLServer.prepareStatement(sqlSQLServer);
					ps.setString(2, txtcodigo.getText());
					ps.setString(1, txtNombreSede.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Actualizado en SQLServer" );
						limpiarcuadrotxt();
				
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Actualizar registro SQLServer" );
					}
					ps.close();
					con.close();
				}
				catch (SQLException e1){
					JOptionPane.showMessageDialog(null,"Error al Acceder a BD");
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(153, 159, 122, 23);
		frame.getContentPane().add(button);
		
	
	}
}