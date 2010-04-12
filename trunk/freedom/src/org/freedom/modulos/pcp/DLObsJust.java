/**
 * @author Setpoint Inform�tica Ltda.
 * @version 15/07/2008
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)DLObsJust.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 */
package org.freedom.modulos.pcp;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;



public class DLObsJust extends FFDialogo{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDtCanc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextAreaPad txaJustCanc = new JTextAreaPad();
	
	private JTextFieldPad txtHoraCanc = new JTextFieldPad( JTextFieldPad.TP_TIME , 8,0 ); 
	
	private JScrollPane scrJus = new JScrollPane( txaJustCanc );
	
	private JTextFieldPad txtIdUsuCanc = new JTextFieldPad( JTextFieldPad.TP_STRING,  8, 0 );

	public DLObsJust(){
		
	}
	
	public DLObsJust( DbConnection cn, int seqOp, int codOp ){
		
		setConexao( cn );
		setTitulo( "Motivo do cancelamento" );
		setAtribos( 350, 230 );
		adic( new JLabelPad( "Dt.canc." ), 7, 10, 70, 20 );
		adic( txtDtCanc, 7, 30, 70, 20 );
		adic( new JLabelPad("Hora canc."), 80, 10, 70, 20 );
		adic( txtHoraCanc, 80, 30, 70, 20 );
		adic( new JLabelPad("Id.Usu."), 153, 10, 70, 20 );
		adic( txtIdUsuCanc, 153, 30, 70, 20 );
		adic( scrJus, 7, 60, 300, 70 );
	
		
		txtDtCanc.setSoLeitura( true );
		txtIdUsuCanc.setSoLeitura( true );
		txaJustCanc.setEditable( false );
		txtHoraCanc.setSoLeitura( true );
		
		btCancel.setVisible( false ); 
		
		buscaObs( seqOp, codOp );
	}
	
	public void buscaObs( int seqOp, int codOp ){
		
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sSQL.append( "SELECT P.JUSTIFICCANC, P.DTCANC, P.HCANC, P.IDUSUCANC FROM PPOP P WHERE " );
		sSQL.append( "P.CODEMP=? AND CODFILIAL=? AND P.SEQOP=? AND P.CODOP=? ");
		
		try {
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 3, seqOp );
			ps.setInt( 4, codOp );
			rs = ps.executeQuery();
			
			if( rs.next() ){
				
				txtDtCanc.setVlrDate( rs.getDate( "DTCANC" ) );
				txaJustCanc.setVlrString( rs.getString( "JUSTIFICCANC" ) );
				txtIdUsuCanc.setVlrString( rs.getString( "IDUSUCANC" ) );
				txtHoraCanc.setVlrTime( rs.getTime( "HCANC" ) );
				
			}
			
		} catch ( SQLException err ) {
			
			err.printStackTrace();
		}
	}
	
	public void setConexao( DbConnection cn ){
		super.setConexao( cn );
	}
}
