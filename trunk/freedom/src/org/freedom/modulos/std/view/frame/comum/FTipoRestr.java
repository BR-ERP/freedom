/**
 * @version 20/05/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: FTipoVend
 * @(#)FClasCli.java <BR>
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
 * Coment�rios sobre a classe...
 */
package org.freedom.modulos.std.view.frame.comum;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.modulos.std.view.dialog.report.DLRTipoRestr;

public class FTipoRestr extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTipoRest = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescTipoRest = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JCheckBoxPad cbBloqTpRestr = new JCheckBoxPad( "Restri��o com bloqueio.", "S", "N");

	public FTipoRestr() {

		super();
		setAtribos( 50, 50, 370, 150 );
		setTitulo( "Tipo de Restri��o" );

		adicCampo( txtCodTipoRest, 7, 20, 70, 20, "CODTPRESTR", "C�d.rest.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoRest, 80, 20, 250, 20, "DESCTPRESTR", "Descri��o da Restri��o", ListaCampos.DB_SI, true );
		adicDB( cbBloqTpRestr, 7, 40, 250, 30, "BloqTpRestr", "", true );
		setListaCampos( true, "TIPORESTR", "FN" );
		setImprimir( true );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRTipoRestr dl = new DLRTipoRestr( this );

		try {
			dl.setVisible( true );
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}

			sSQL = "SELECT CODTPRESTR, DESCTPRESTR FROM FNTIPORESTR WHERE CODEMP=? AND CODFILIAL=? ORDER BY " + dl.getOrdem();
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNTIPORESTR" ) );
			rs = ps.executeQuery();

			if ( "T".equals( dl.getTipo() ) ) {
				imprimirTexto( bVisualizar, rs );
			}
			else if ( "G".equals( dl.getTipo() ) ) {
				imprimirGrafico( bVisualizar, rs );
			}

			rs.close();
			ps.close();

			con.commit();
			dl.dispose();
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		}
	}

	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();
		imp.setTitulo( "Relat�rio de Tipo de restri��o" );

		try {

			imp.limpaPags();

			while ( rs.next() ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( imp.pRow() + 0, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 0, 2, "C�digo" );
					imp.say( imp.pRow() + 0, 30, "Descri��o" );
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, StringFunctions.replicate( "-", 80 ) );

				}
				imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
				imp.say( imp.pRow() + 0, 2, rs.getString( "CODTPRESTR" ) );
				imp.say( imp.pRow() + 0, 30, rs.getString( "DESCTPRESTR" ) );
				imp.say( imp.pRow() + 0, 82, "|" + imp.normal() );
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
			imp.say( imp.pRow() + 0, 0, StringFunctions.replicate( "=", 80 ) );

			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		}
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/TipoRestr.jasper", "Tipo de restri��o ", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {

				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
			}
		}
	}
}
