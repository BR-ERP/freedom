/*
 * Projeto: Freedom
 * Pacote: org.freedom.modulos.nfe.database.jdbc
 * Classe: @(#)NFEConnectionFactory.java
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 */
package org.freedom.modulos.nfe.database.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.SystemFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modules.nfe.bean.FreedomNFEKey;
import org.freedom.modules.nfe.control.AbstractNFEFactory;
import org.freedom.modules.nfe.event.NFEEvent;
import org.freedom.modules.nfe.event.NFEListener;
import org.freedom.modulos.nfe.DLInconsistency;

/**
 * Classe para manipula��o de conex�o com banco de dados NFE.
 * 
 * @author Setpoint Inform�tica Ltda./Robson Sanchez/Alex Rodrigues
 * @version 10/11/2009
 */
public class NFEConnectionFactory implements NFEListener {

	private DbConnection con;

	private DbConnection conNFE;

	private Properties props;

	private boolean hasNFE;

	private String url;

	private String strClassNFE = "";

	private String dirNFE = "";

	private Class<AbstractNFEFactory> classNFEFactory = null;

	private AbstractNFEFactory objNFEFactory = null;

	private StringBuilder messagesError = new StringBuilder();
	
	private Integer tpNF = AbstractNFEFactory.TP_NF_OUT;
	
	public NFEConnectionFactory( final DbConnection conFreedom ) {
		this(conFreedom, null);
	}
	
	public NFEConnectionFactory( final DbConnection conFreedom, Integer TP_NF ) {

		this.con = conFreedom;
		
		if(TP_NF==null) {
			setTpNF( AbstractNFEFactory.TP_NF_OUT );
		}
		else {
			setTpNF( TP_NF );
		}
		
		if ( conFreedom != null ) {
			
			try {
				
				setHasNFE( confirmNFE() );
				
				if ( getHasNFE() ) {
					
					props = conFreedom.getProperties();
					
					setUrl( getUrlDb() );
					setConNFE( new DbConnection( getUrl(), props ) );
					
					getObjNFEFactory().setConSys( getCon() );
					getObjNFEFactory().setConNFE( getConNFE() );									
					getObjNFEFactory().setTpNF( getTpNF() );
					
				}
				else {
					displayMessages();
				}
			} catch ( SQLException e ) {
				Funcoes.mensagemInforma( null, "N�o foi poss�vel conectar no banco de dados NFE!\n" + e.getMessage() );
			}
		}
	}

	public DbConnection getCon() {
		return con;
	}

	public void setCon( DbConnection con ) {
		this.con = con;
	}

	public DbConnection getConNFE() {
		return conNFE;
	}
	
	public void setTpNF( Integer tpNF ) {
		this.tpNF = tpNF;
	}
	
	public Integer getTpNF() {
		return tpNF;
	}

	public void setConNFE( DbConnection conNFE ) {
		this.conNFE = conNFE;
	}

	public boolean getHasNFE() {
		return hasNFE;
	}

	public void setHasNFE( boolean hasNFE ) {
		this.hasNFE = hasNFE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public String getStrClassNFE() {
		return strClassNFE;
	}

	public void setStrClassNFE( String strClassNFE ) {
		this.strClassNFE = strClassNFE;
	}

	public String getDirNFE() {
		return dirNFE;
	}

	public void setDirNFE( String dirNFE ) {
		this.dirNFE = dirNFE;
	}

	public Class<AbstractNFEFactory> getClassNFEFactory() {
		return classNFEFactory;
	}

	public void setClassNFEFactory( Class<AbstractNFEFactory> classNFEFactory ) {
		this.classNFEFactory = (Class<AbstractNFEFactory>) classNFEFactory;
	}

	public AbstractNFEFactory getObjNFEFactory() {
		return objNFEFactory;
	}

	public void setObjNFEFactory( AbstractNFEFactory objNFEFactory ) {
		this.objNFEFactory = objNFEFactory;
		if ( this.objNFEFactory != null ) {
			this.objNFEFactory.addNFEListener( this );
		}
	}

	public void setKey( Integer codemp, Integer codfilial, String tipovenda, Integer codvenda, Integer docvenda ) {

		FreedomNFEKey key = new FreedomNFEKey( codemp, codfilial, tipovenda, codvenda, docvenda, getDirNFE() );
		getObjNFEFactory().setKey( key );
		
	}
	
	public void setKey( Integer codemp, Integer codfilial, Integer codcompra, Integer doccompra ) {

		FreedomNFEKey key = new FreedomNFEKey( codemp, codfilial, codcompra, doccompra, getDirNFE() );
		getObjNFEFactory().setKey( key );
		
	}


	private String getUrlDb() {
		return Aplicativo.getParameter( "banconfe" );
	}

	public void post() {

		getObjNFEFactory().post();
	}

	@SuppressWarnings ( "unchecked" )
	private boolean confirmNFE() throws SQLException {

		boolean result = false;
		
		if ( "S".equalsIgnoreCase( Aplicativo.getParameter( "nfe" ) ) ) {
			
			PreparedStatement ps = con.prepareStatement( "SELECT NFEEST FROM SGESTACAO WHERE CODEMP=? AND CODFILIAL=? AND CODEST=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGESTACAO" ) );
			ps.setInt( 3, Aplicativo.iNumEst );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				if ( "S".equalsIgnoreCase( rs.getString( "NFEEST" ) ) ) {
					result = true;
				}
			}
			
			rs.close();
			ps.close();
			
			con.commit();
			
			if ( result ) {
				
				StringBuilder sqlpref = new StringBuilder();
				
				sqlpref.append( "SELECT CLASSNFE, " );
				
				if(SystemFunctions.getOS()==SystemFunctions.OS_LINUX) {
					sqlpref.append( "DIRNFELIN " );
				}
				else if(SystemFunctions.getOS()==SystemFunctions.OS_WINDOWS) {
					sqlpref.append( "DIRNFE " );
				}
				
				sqlpref.append( "DIRNFE FROM SGPREFERE1 P WHERE P.CODEMP=? AND P.CODFILIAL=?" );
				
				ps = con.prepareStatement( sqlpref.toString() ); 
						
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
				rs = ps.executeQuery();
				
				if ( rs.next() ) {
					
					setDirNFE( rs.getString( "DIRNFE" ) );
					setStrClassNFE( rs.getString( "CLASSNFE" ) );
					
					if ( getDirNFE() == null || "".equals( getDirNFE() ) ) {
						messagesError.append( "Diret�rio de exporta��o NFE n�o definido!\n" );
						result = false;
					}
					if ( getStrClassNFE() == null || "".equals( getStrClassNFE() ) ) {
						messagesError.append( "Classe de integra��o NFE n�o definida!\n" );
						result = false;
					}
					else {
						try {
							setClassNFEFactory( (Class<AbstractNFEFactory>) Class.forName( getStrClassNFE() ) );
							setObjNFEFactory( getClassNFEFactory().newInstance() );
						} catch ( ClassNotFoundException error ) {
							messagesError.append( "Classe de integra��o NFE \"" );
							messagesError.append( getStrClassNFE() );
							messagesError.append( "\" n�o encontrada.\nVerifique a vari�vel CLASSPATH !\n" );
							result = false;
						} catch ( ClassCastException error ) {
							messagesError.append( "Classe de integra��o NFE \"" );
							messagesError.append( getStrClassNFE() );
							messagesError.append( "\" n�o � derivada do tipo esperado\n\"org.freedom.modules.nfe.control.AbstractNFEFactory\"!" );
							result = false;
						} catch ( InstantiationException error ) {
							messagesError.append( "Classe de integra��o NFE \n\"" );
							messagesError.append( getStrClassNFE() );
							messagesError.append( "\" n�o foi carregada!\n" );
							messagesError.append( error.getMessage() );
							result = false;
						} catch ( IllegalAccessException error ) {
							messagesError.append( "Classe de integra��o NFE \n\"" );
							messagesError.append( getStrClassNFE() );
							messagesError.append( "\" n�o foi carregada!\n" );
							messagesError.append( error.getMessage() );
							result = false;
						}
					}
					
					rs.close();
					ps.close();			
					con.commit();
					
				}
			}
			

		}
		
		return result;
	}

	private void displayMessages() {

		if ( messagesError.length() > 0 ) {
			
			Funcoes.mensagemInforma( null, messagesError.toString() );
			messagesError.delete( 0, messagesError.length() );
		}
	}
	
	public void beforeValidSend( NFEEvent e ) { }

	public void afterValidSend( NFEEvent e ) {

		AbstractNFEFactory nfe = e.getNfefactory();

		if ( nfe.getListInconsistency().size() > 0 ) {
			DLInconsistency inconsistency = new DLInconsistency( nfe.getListInconsistency() );
			inconsistency.setVisible( true );
		}
	}

	public void beforeRunSend( NFEEvent e ) { }

	public void afterRunSend( NFEEvent e ) {
	
		AbstractNFEFactory nfe = e.getNfefactory();
		
		if ( nfe.getListInconsistency().size() > 0 ) {
			DLInconsistency inconsistency = new DLInconsistency( nfe.getListInconsistency() );
			inconsistency.setVisible( true );
		}
		else {
			Funcoes.mensagemInforma( null, "Arquivo de NF-e criado com sucesso." );
		}
	}

}
