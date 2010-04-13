/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FTipoCli.java <BR>
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
 * Tela para cadastro de tipos de clientes.
 * 
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.modulos.std.view.dialog.report.DLRTipoCli;

public class FTipoCli extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCod = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDesc = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtSgTpCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JPanelPad pinInfoFicha = new JPanelPad( 300, 150 );

	private JCheckBoxPad cbTipoCadFis = new JCheckBoxPad( "Pessoa f�sica", "S", "N" );

	private JCheckBoxPad cbTipoCadJur = new JCheckBoxPad( "Pessoa jur�dica", "S", "N" );

	private JCheckBoxPad cbTipoCadCheq = new JCheckBoxPad( "Cheque", "S", "N" );

	private JCheckBoxPad cbTipoCadFil = new JCheckBoxPad( "Filia��o", "S", "N" );

	private JCheckBoxPad cbTipoCadLocTrab = new JCheckBoxPad( "Local de trabalho", "S", "N" );

	private JCheckBoxPad cbTipoCadRefComl = new JCheckBoxPad( "Refer�ncias comerciais", "S", "N" );

	private JCheckBoxPad cbTipoCadRefBanc = new JCheckBoxPad( "Refer�ncias banc�rias", "S", "N" );

	private JCheckBoxPad cbTipoCadRefPess = new JCheckBoxPad( "Refer�ncias pessoais", "S", "N" );

	private JCheckBoxPad cbTipoCadRefConj = new JCheckBoxPad( "Informa��es do c�njuge", "S", "N" );

	private JCheckBoxPad cbTipoCadRefVeic = new JCheckBoxPad( "Informa��es de ve�culos", "S", "N" );

	private JCheckBoxPad cbTipoCadRefImov = new JCheckBoxPad( "Informa��es de im�veis", "S", "N" );

	private JCheckBoxPad cbTipoCadRefTerra = new JCheckBoxPad( "Informa��es de terras", "S", "N" );

	private JCheckBoxPad cbTipoCadRefPesAutCp = new JCheckBoxPad( "Autoriza��o de compra", "S", "N" );

	private JCheckBoxPad cbTipoCadRefAval = new JCheckBoxPad( "Avalista", "S", "N" );

	private JCheckBoxPad cbTipoCadRefSocio = new JCheckBoxPad( "Quadro de s�cios", "S", "N" );
	
//	private JCheckBoxPad cbTipoProdRual = new JCheckBoxPad( "Produtor rural", "S", "N" );

	private JLabelPad lbInfoFicha = new JLabelPad( " Informa��es complementares na ficha cadastral" );

	private JPanelPad pinLbInfoCaixa = new JPanelPad( 53, 15 );

	public FTipoCli() {

		super();
		setTitulo( "Cadastro de tipos de clientes" );
		setAtribos( 50, 50, 440, 330 );
		adicCampo( txtCod, 7, 20, 70, 20, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, true );
		adicCampo( txtDesc, 80, 20, 250, 20, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, true );
		adicCampo( txtSgTpCli, 333, 20, 71, 20, "SiglaTipoCli", "Sigla.tp.cli.", ListaCampos.DB_SI, false );
		adicDB( cbTipoCadFis, 10, 70, 200, 20, "FisTipoCli", "", true );
		adicDB( cbTipoCadJur, 220, 70, 170, 20, "JurTipoCli", "", true );
		adicDB( cbTipoCadCheq, 10, 90, 200, 20, "CheqTipoCli", "", true );
		adicDB( cbTipoCadFil, 220, 90, 170, 20, "FilTipoCli", "", true );
		adicDB( cbTipoCadLocTrab, 10, 110, 200, 20, "LocTrabTipoCli", "", true );
		adicDB( cbTipoCadRefComl, 220, 110, 170, 20, "RefComlTipoCli", "", true );
		adicDB( cbTipoCadRefBanc, 10, 130, 200, 20, "BancTipoCli", "", true );
		adicDB( cbTipoCadRefPess, 220, 130, 170, 20, "RefPesTipoCli", "", true );
		adicDB( cbTipoCadRefConj, 10, 150, 200, 20, "ConjTipoCli", "", true );
		adicDB( cbTipoCadRefVeic, 220, 150, 170, 20, "VeicTipoCli", "", true );
		adicDB( cbTipoCadRefImov, 10, 170, 200, 20, "ImovTipoCli", "", true );
		adicDB( cbTipoCadRefTerra, 220, 170, 170, 20, "TerraTipoCli", "", true );
		adicDB( cbTipoCadRefPesAutCp, 10, 190, 200, 20, "PesAutCpTipoCli", "", true );
		adicDB( cbTipoCadRefAval, 220, 190, 170, 20, "AvalTipoCli", "", true );
//		adicDB( cbTipoProdRual, 220, 210, 170, 20, "ProdRuralTipoCli", "", true );
		adicDB( cbTipoCadRefSocio, 10, 210, 200, 20, "SocioTipoCli", "", true );

		pinLbInfoCaixa.adic( lbInfoFicha, 0, 0, 350, 15 );
		pinLbInfoCaixa.tiraBorda();

		adic( pinLbInfoCaixa, 10, 52, 350, 15 );
		adic( pinInfoFicha, 7, 60, 400, 180 );

		setListaCampos( true, "TIPOCLI", "VD" );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		setImprimir( true );
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
		StringBuffer sSQL = new StringBuffer();
		DLRTipoCli dl = new DLRTipoCli();

		try {

			dl.setVisible( true );
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}
			
			sSQL.append( "SELECT TP.CODTIPOCLI AS CODIGO,TP.DESCTIPOCLI AS DESCRICAO," );
			sSQL.append( "(SELECT COUNT(CLI.CODCLI) FROM VDCLIENTE CLI " );
			sSQL.append( "WHERE CLI.CODEMPTI=TP.CODEMP AND CLI.CODFILIALTI=TP.CODFILIAL AND CLI.CODTIPOCLI=TP.CODTIPOCLI) AS QTD " );
			sSQL.append( "FROM VDTIPOCLI TP " );
			sSQL.append( "WHERE TP.CODEMP=? AND TP.CODFILIAL=? " );
			sSQL.append( "ORDER BY " + dl.getValor() );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
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
			Funcoes.mensagemErro( this, "Erro ao montar relat�rio de classifica��o de cliente!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();

		}
	}

	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs ) {

		String sLinhaFina = StringFunctions.replicate( "-", 133 );
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();

		try {

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Classifica��o do tipo de cliente" );

			while ( rs.next() ) {

				if ( imp.pRow() == linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();
				}

				if ( imp.pRow() == 0 ) {
					imp.impCab( 80, false );
					imp.say( 0, imp.normal() );
					imp.say( 2, "C�d.tp.cli." );
					imp.say( 20, "Descri��o" );
					imp.say( 70, "Qtd.cli." );
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, StringFunctions.replicate( "-", 79 ) );
				}

				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodTipoCli" ) );
				imp.say( 20, rs.getString( "DescTipoCli" ) );
				imp.say( 70, Funcoes.alinhaDir( rs.getInt( 3 ), 8 ) );

				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, StringFunctions.replicate( "=", 79 ) );
			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, "|" );
			imp.say( 50, "Total de clientes:" );
			imp.say( 79, "|" );
			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, StringFunctions.replicate( "=", 79 ) );

			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de tipos de cliente!\n" + err.getMessage(), true, con, err );

		}
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/TipoCli.jasper", "Tipo de Cliente", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de vendas por cliente!" + err.getMessage(), true, con, err );
			}
		}
	}
}
