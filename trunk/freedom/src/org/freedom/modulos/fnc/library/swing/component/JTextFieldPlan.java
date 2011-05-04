/**
 * @version 02/05/2011S <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.modulos.fnc.library.swing.component <BR>
 * Classe: @(#)JTextFieldPlan.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Classe padr�o para entrada de dados de c�digos de planejamento financeiro.
 */

package org.freedom.modulos.fnc.library.swing.component;

import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;

public class JTextFieldPlan extends JTextFieldPad {

	private static final long serialVersionUID = 1L;
	
	public JTextFieldPlan(int tipo, int tam, int dec) {
		super(tipo, tam, dec);
	}
	
	public void keyPressed(KeyEvent kevt) {
		
		if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			
			String codred = this.getVlrString();
			
			// Verifica se o valor digitado � um c�digo reduzido.
			if(codred !=null && codred.length() <13) {

				try {
				
					String codplan = getCodPlan( Integer.parseInt( codred ) );
					
					if(codplan!=null) {
						
						this.setVlrString( codplan );
						
					}
				}
				catch (NumberFormatException e) {
					System.out.println("Valor digitado inv�lido (n�o � um n�mero)");
				}
					
				
			}
			transferFocus();
		}
		
		super.keyPressed(kevt);

	}
	
	private String getCodPlan(Integer codredplan) {
		
		String ret = null;
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		DbConnection con = null; 
		ResultSet rs = null;
		
		try {   
			 
			con = Aplicativo.getInstace().getConexao();
			
			sql.append("select codplan from fnplanejamento where codemp=? and codfilial=? and codredplan=? ");
			
			ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
			ps.setInt( 3, codredplan );
			
			rs = ps.executeQuery();
			
			System.out.println("Executando busca pelo c�digo reduzido: " + codredplan );
			
			if(rs.next()) {
				ret = rs.getString( "codplan" );
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
		
	}

}
