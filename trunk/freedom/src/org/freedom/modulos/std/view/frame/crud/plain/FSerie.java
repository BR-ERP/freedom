/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FSerie.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Tela de cadastro de s�ries.
 * 
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;

public class FSerie extends FDados implements ActionListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDocSerie = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JButtonPad btReset = new JButtonPad( Icone.novo( "btResetcont.gif" ) );

	private JTextFieldPad txtReset = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JLabelPad lbReset = new JLabelPad( "Novo n�." );

	public FSerie() {

		super( false );
		setTitulo( "Cadastro de S�rie de Notas" );
		setAtribos( 50, 50, 310, 160 );

		btReset.setToolTipText( "Reiniciar contagem" );

		txtDocSerie.setSoLeitura( true );

		adicCampo( txtSerie, 7, 20, 90, 20, "Serie", "C�d.s�rie", ListaCampos.DB_PK, null, true );
		adicCampo( txtDocSerie, 100, 20, 180, 20, "DocSerie", "Doc. n�mero", ListaCampos.DB_SI, null, false );

		adic( btReset, 150, 50, 130, 30 );
		setListaCampos( false, "SERIE", "LF" );

		btReset.addActionListener( this );
		lcCampos.addCarregaListener( this );
		nav.setAtivo( 2, false );
	}

	private void resetar() {

		FFDialogo dlReset = new FFDialogo( this );
		dlReset.setTitulo( "Reset" );
		dlReset.setAtribos( 280, 140 );
		dlReset.adic( lbReset, 7, 5, 100, 20 );
		dlReset.adic( txtReset, 7, 25, 100, 20 );
		dlReset.setVisible( true );
		if ( dlReset.OK ) {
			gravaReset();
		}
		dlReset.dispose();
		lcCampos.carregaDados();

	}

	private void gravaReset() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( "SELECT DOCSERIE FROM LFSEQSERIE " +
					"WHERE SERIE=? AND CODEMP=? AND CODFILIAL=? AND " +
			"CODEMPSS=? AND CODFILIALSS=? AND ATIVSERIE='S'" );
			ps.setString( 1, txtSerie.getVlrString() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "LFSERIE") );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "LFSEQSERIE" ) );
            rs = con.executeQuery( ps );
            if (rs.next()) {
    			ps = con.prepareStatement( "UPDATE LFSEQSERIE SET DOCSERIE=? " +
    					"WHERE SERIE=? AND CODEMP=? AND CODFILIAL=? AND " +
    					"CODEMPSS=? AND CODFILIALSS=? AND ATIVSERIE='S'" );
    			ps.setInt( 1, txtReset.getVlrInteger().intValue() );
    			ps.setString( 2, txtSerie.getVlrString() );
    			ps.setInt( 3, Aplicativo.iCodEmp );
    			ps.setInt( 4, ListaCampos.getMasterFilial( "LFSERIE") );
    			ps.setInt( 5, Aplicativo.iCodEmp );
    			ps.setInt( 6, ListaCampos.getMasterFilial( "LFSEQSERIE" ) );
    			ps.executeUpdate();
            } else {
    			ps = con.prepareStatement( "INSERT INTO LFSEQSERIE (" +
    					"DOCSERIE, SERIE, CODEMP, CODFILIAL, CODEMPSS, CODFILIALSS, SEQSERIE, ATIVSERIE) " +
    					"VALUES (?,?,?,?,?,?,?,?)");
    			ps.setInt( 1, txtReset.getVlrInteger().intValue() );
    			ps.setString( 2, txtSerie.getVlrString() );
    			ps.setInt( 3, Aplicativo.iCodEmp );
    			ps.setInt( 4, ListaCampos.getMasterFilial( "LFSERIE") );
    			ps.setInt( 5, Aplicativo.iCodEmp );
    			ps.setInt( 6, ListaCampos.getMasterFilial( "LFSEQSERIE" ) );
    			ps.setInt( 7, 1 );
    			ps.setString( 8, "S" );
    			ps.executeUpdate();
            }
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao gravar o n�mero inicial!\n" + err.getMessage(), true, con, err );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btReset ) {
			if ( lcCampos.getStatus() == ListaCampos.LCS_SELECT ) {
				resetar();
			}
		}
		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if (cevt.getListaCampos()==lcCampos) {
			try {
			  PreparedStatement ps = con.prepareStatement( "SELECT DOCSERIE FROM LFSEQSERIE " +
			  		"WHERE CODEMP=? AND CODFILIAL=? AND SERIE=? AND " +
			  		"CODEMPSS=? AND CODFILIALSS=? AND ATIVSERIE='S'" );
			  ps.setInt( 1, Aplicativo.iCodEmp );
			  ps.setInt( 2, ListaCampos.getMasterFilial( "LFSERIE" ) );
			  ps.setString( 3, txtSerie.getVlrString() );
			  ps.setInt( 4, Aplicativo.iCodEmp );
			  ps.setInt( 5, ListaCampos.getMasterFilial( "LFSEQSERIE" ) );
			  ResultSet rs = ps.executeQuery();
			  if (rs.next()) {
				  txtDocSerie.setVlrInteger( rs.getInt( "DOCSERIE" ) );
			  } else {
				  txtDocSerie.setVlrInteger( 0 );
			  }
				  
			  con.commit();
			} catch (Exception e) {
		    	e.printStackTrace();
		    }
			
		}
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		
	}
}
