/**
 * @version 16/03/2006 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCodProd.java <BR>
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
 * Tela para busca de produto pelo c�digo de barras
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.ListaCampos;
import org.freedom.library.Tabela;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLCodProd extends FFDialogo implements KeyListener {

	private static final long serialVersionUID = 1L;
	
	public Tabela tab = new Tabela();
	
	private JScrollPane spnCentro = new JScrollPane(tab); 
	private int iCodProd = 0;
	
	private boolean bFilCodProd = false;
	private boolean bFilRefProd = false;
	private boolean bFilCodBar = false;
	private boolean bFilCodFab = false;
	private boolean bFilCodProdFor = false;
	
	private Integer codfor = null;
	
	private Vector<Integer> vProds = new Vector<Integer>();
	private Vector<String> vUsaLote = new Vector<String>();
	private JComponent proxFoco = null;
	
	public DLCodProd(DbConnection con, JComponent proxFoco, Integer codfor) {

		setTitulo("Pesquisa auxiliar");
		setAtribos( 575, 260);		
	    setResizable(true);	 
		setConexao(con);
		
		this.codfor = codfor;
		this.proxFoco = proxFoco;
	    
	    c.add( spnCentro, BorderLayout.CENTER);    
	    
	    addWindowFocusListener( new WindowAdapter() {
			 public void windowGainedFocus(WindowEvent e) {
	            if (tab.getNumLinhas() > 0)
	            	tab.requestFocus();
	            else
	            	btCancel.requestFocus();
			 }
	       }
	    );
		 
		tab.adicColuna("C�d.prod.");
		tab.adicColuna("Ref.prod.");
		tab.adicColuna("C�d.bar.prod.");
		tab.adicColuna("C�d.fab.prod.");
		tab.adicColuna("Descri��o do produto");    
		tab.adicColuna("C�d.amox.");
		tab.adicColuna("lote");
		tab.adicColuna("Validade");   	  
		tab.adicColuna("Saldo");   	
		tab.setTamColuna(80,0);
		tab.setTamColuna(80,1);
		tab.setTamColuna(80,2);
		tab.setTamColuna(80,3);
		tab.setTamColuna(160,4);
		tab.setTamColuna(80,5); 
		tab.setTamColuna(80,6);
		tab.setTamColuna(80,7);
		tab.setTamColuna(80,8); 
		
		tab.addKeyListener(this);
		
		getPrefere();
		
	}
	
	public void passaFocus() {
		if(proxFoco!=null)
			proxFoco.requestFocus();
	}
	
	public boolean buscaCodProd(String valor) {
		
		valor = StringFunctions.alltrim( valor ); 
		
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		String sql = null;
		String sqladiclote = null;
		String sqladic = null;
		String sqlfor = "";
		
		String where = "";
		
		String sTemp = null;
		
		boolean usaOR = false;
		boolean adicCodProd = false;		
		
		int ilinha = 0;
		
		if(valor == null || valor.trim().length() <= 0) {
			return false;		
		}
		
		try{
			
			vProds.clear();
			vUsaLote.clear();
			tab.limpa();
			iCodProd = 0;
						
			sqladiclote = "SELECT P.CODPROD, P.REFPROD, P.CODBARPROD, P.CODFABPROD, P.DESCPROD, " 
						+ "L.CODLOTE, L.VENCTOLOTE, L.SLDLOTE, A.CODALMOX " 
						+ "FROM EQPRODUTO P, EQLOTE L, EQALMOX A " 
						+ "WHERE P.CODEMP=? AND P.CODFILIAL=? AND P.CODPROD=? " 
						+ "AND A.CODEMP=P.CODEMPAX AND A.CODFILIAL=P.CODFILIALAX AND A.CODALMOX=P.CODALMOX " 
						+ "AND L.CODEMP=P.CODEMP AND L.CODFILIAL=P.CODFILIAL AND L.CODPROD=P.CODPROD " 
						+ "AND L.VENCTOLOTE = ( SELECT MIN(VENCTOLOTE) " 
						+ "FROM EQLOTE LS " 
						+ "WHERE LS.CODPROD=L.CODPROD AND LS.CODFILIAL=L.CODFILIAL " 
						+ "AND LS.CODEMP=L.CODEMP AND VENCTOLOTE >= CAST('today' AS DATE) ) ";
	
			sqladic = "SELECT P.CODPROD, P.REFPROD, P.CODBARPROD, P.CODFABPROD, P.DESCPROD, A.CODALMOX " 
					+ "FROM EQPRODUTO P, EQALMOX A " 
					+ "WHERE P.CODEMP=? AND P.CODFILIAL=? AND P.CODPROD=? " 
					+ "AND A.CODEMP=P.CODEMPAX AND A.CODFILIAL=P.CODFILIALAX AND A.CODALMOX=P.CODALMOX ";	
			
			
			sql =  "SELECT P.CODPROD, P.CLOTEPROD FROM EQPRODUTO P WHERE P.CODEMP=? AND P.CODFILIAL=? ";			
					
			if(bFilCodBar) {
						
				where = "AND (P.CODBARPROD=?) ";
						
			}
			
			if(bFilCodProd) {
						
				try {
							
					int val = Integer.parseInt(valor);
							
					if(val < Integer.MAX_VALUE && val > Integer.MIN_VALUE ) {
								
						if(where.length()>0) {
							
							where += "OR (P.CODPROD=?) ";
							
							usaOR = true;
						
						} 
						else { 
							
							where += "AND P.CODPROD=? ";
							
						}
								
						adicCodProd = true;
								
					}
				} 
				catch (NumberFormatException e) {
					System.out.println("Erro ao fazer busca generica de produtos\n"+e.getMessage());
				}
			}
			
			if(bFilRefProd) {
				
				if(where.length()>0) {
							
					where += "OR (P.REFPROD=?) ";
					usaOR = true;
							
				} 
				else {
							
					where = "AND (P.REFPROD=?) ";
							
				}
			}
			
			if(bFilCodFab){
						
				if(where.length()>0) {
							
					where += "OR (P.CODFABPROD=?) ";
					usaOR = true;
							
				} 
				else {
							
					where = "AND (P.CODFABPROD=?) ";
							
				}
			}
			if(bFilCodProdFor) {
				
				sqlfor = "UNION SELECT PF.CODPROD, P.CLOTEPROD FROM CPPRODFOR PF, EQPRODUTO P "
					   + "WHERE P.CODEMP = PF.CODEMP AND P.CODFILIAL=PF.CODFILIAL AND P.CODPROD=PF.CODPROD "
					   + "AND PF.CODEMP=? AND PF.CODFILIAL=? AND PF.REFPRODFOR = ? " 
					   + ( codfor!=null ? (" AND PF.CODFOR=" + codfor) : "");
				
			}
			
			
			if(usaOR) {
				where = " AND (" + where.substring(4,where.length()) +") ";
			}
						
			sql = sql +  where + sqlfor;		
			
			
			ps = con.prepareStatement(sql);
			
			int iparam = 1;
			
			ps.setInt(iparam++, Aplicativo.iCodEmp);
			
			ps.setInt(iparam++, ListaCampos.getMasterFilial("EQPRODUTO"));
			
			if(bFilCodBar) {
				ps.setString(iparam++, valor);
			}
			
			if(adicCodProd) {
				ps.setString(iparam++, valor);
			}
			
			if(bFilRefProd) {
				ps.setString(iparam++, valor);
			}
			
			if(bFilCodFab) {
				ps.setString(iparam++, valor);
			}
			if(bFilCodProdFor) {
				
				ps.setInt(iparam++, Aplicativo.iCodEmp);			
				ps.setInt(iparam++, ListaCampos.getMasterFilial("EQPRODUTO"));			
				ps.setString(iparam++, valor);
				
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				vProds.addElement(new Integer(rs.getString("CODPROD") != null ? rs.getString("CODPROD") : "0"));
				vUsaLote.addElement(rs.getString("CLOTEPROD") != null ? rs.getString("CLOTEPROD") : "N");
			}
			
			if(vProds.size() <= 0) {
				Funcoes.mensagemErro(this, "C�digo Invalido!");
				return false;
			} else if(vProds.size() == 1) {
				iCodProd = vProds.elementAt(0).intValue();
				ok();
				return true;
			}
			
			for(int i=0; i<vProds.size(); i++) {
				if(vUsaLote.elementAt(i).equals("S"))
					sTemp = sqladiclote;
				else 
					sTemp = sqladic;
				
				ps2 = con.prepareStatement(sTemp);
				ps2.setInt(1, Aplicativo.iCodEmp);
				ps2.setInt(2, ListaCampos.getMasterFilial("EQPRODUTO"));
				ps2.setInt(3, vProds.elementAt(i).intValue());
				
				rs2 = ps2.executeQuery();
				
				if(rs2.next()) {
					if(vUsaLote.elementAt(i).equals("S")) {
						tab.adicLinha( new Object[]{(rs2.getString("CODPROD") != null ? rs2.getString("CODPROD").trim() : ""),
													(rs2.getString("REFPROD") != null ? rs2.getString("REFPROD").trim() : ""),
													(rs2.getString("CODBARPROD") != null ? rs2.getString("CODBARPROD").trim() : ""),
													(rs2.getString("CODFABPROD") != null ? rs2.getString("CODFABPROD").trim() : ""),
													(rs2.getString("DESCPROD") != null ? rs2.getString("DESCPROD").trim() : ""),
													(rs2.getString("CODALMOX") != null ? rs2.getString("CODALMOX").trim() : ""),					
													(rs2.getString("CODLOTE") != null ? rs2.getString("CODLOTE").trim() : ""),
													(rs2.getString("VENCTOLOTE") != null ? rs2.getString("VENCTOLOTE").trim() : ""),
													(rs2.getString("SLDLOTE") != null ? rs2.getString("SLDLOTE").trim() : "")});
					} else {
						tab.adicLinha( new Object[]{(rs2.getString("CODPROD") != null ? rs2.getString("CODPROD").trim() : ""),
													(rs2.getString("REFPROD") != null ? rs2.getString("REFPROD").trim() : ""),
													(rs2.getString("CODBARPROD") != null ? rs2.getString("CODBARPROD").trim() : ""),
													(rs2.getString("CODFABPROD") != null ? rs2.getString("CODFABPROD").trim() : ""),
													(rs2.getString("DESCPROD") != null ? rs2.getString("DESCPROD").trim() : ""),
													(rs2.getString("CODALMOX") != null ? rs2.getString("CODALMOX").trim() : ""),					
													"","",""});
					}	
					ilinha++;
				}				
			}
			
			if(ilinha <= 0) {
				Funcoes.mensagemErro(this, "C�digo Invalido!");
				return false;
			} 
			else if(ilinha == 1) {
				iCodProd = vProds.elementAt(0).intValue();
				ok();
				return true;
			}
			
			tab.changeSelection(0,0,true,true);
			tab.setLinhaSel(0);
			setVisible(true);
			
		} 
			catch (SQLException e) {
			Funcoes.mensagemErro(this, "Erro ao buscar produtos por c�digo de barras!\n"+
					e.getMessage(), true, con, e);
			e.printStackTrace();
			return false;
		} finally {
			ps = null;
			rs = null;
			sql = null;			
		}
		
		return true;
		
	}

	public int getCodProd() { 
		return iCodProd;
	}
	
	private void getPrefere() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		
		try{
			
			sSQL =  "SELECT FILBUSCGENPROD, FILBUSCGENREF, FILBUSCGENCODBAR, FILBUSCGENCODFAB, FILBUSCGENCODFOR " +
					"FROM SGPREFERE1 " +
					"WHERE CODEMP=? AND CODFILIAL=?";
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				
				bFilCodProd = (rs.getString("FILBUSCGENPROD")!=null && rs.getString("FILBUSCGENPROD").equals("S")) ? true : false;
				bFilRefProd = (rs.getString("FILBUSCGENREF")!=null && rs.getString("FILBUSCGENREF").equals("S")) ? true : false;
				bFilCodBar  = (rs.getString("FILBUSCGENCODBAR")!=null && rs.getString("FILBUSCGENCODBAR").equals("S")) ? true : false;
				bFilCodFab  = (rs.getString("FILBUSCGENCODFAB")!=null && rs.getString("FILBUSCGENCODFAB").equals("S")) ? true : false;
				bFilCodProdFor  = (rs.getString("FILBUSCGENCODFOR")!=null && rs.getString("FILBUSCGENCODFOR").equals("S")) ? true : false;
				
			}
			
			// Garante pelo menos um filtro na busca...
			// Caso se defina no preferencias so a busca generica 
			// e n�o escolha as op��es...
			if(!bFilCodProd && !bFilRefProd && !bFilCodBar && !bFilCodFab)
				bFilCodProd = true;
			
		} catch (SQLException e) {
			Funcoes.mensagemErro(this, "Erro ao buscar filtros!\n"+
					e.getMessage(), true, con, e);
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;			
		}
		
	}

    public void keyPressed(KeyEvent kevt) {
    	if ( kevt.getSource() == tab && kevt.getKeyCode() == KeyEvent.VK_ENTER) {       
    		int ilin = tab.getLinhaSel();
        	iCodProd = 0;
        	if (tab.getNumLinhas() > 0 && ilin >= 0) {
        		iCodProd = Integer.parseInt((String)tab.getValor(ilin, 0));
        		passaFocus();
        		super.ok();
        	} else {
        		Funcoes.mensagemInforma(this, "Nenhum produto foi selecionado.");
        		iCodProd = 0;
        	}
    	}
    	else
    		super.keyPressed(kevt);
    }
    
    public void keyReleased(KeyEvent kevt) { }
    
    public void keyTyped(KeyEvent kevt) { }
        
}        
