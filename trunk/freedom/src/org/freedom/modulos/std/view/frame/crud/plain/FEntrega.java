/**
 * @version 25/03/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std.view.frame.crud.plain <BR>
 * Classe:
 * @(#)FEntrega.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela para cadastramento dos canhotos de entrega de notas fiscais.
 * 
 */

package org.freedom.modulos.std.view.frame.crud.plain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.pdv.FVenda;
import org.freedom.modulos.std.view.dialog.report.DLREntrega;

public class FEntrega extends FDados {

	private static final long serialVersionUID = 1L;

	private final JPanelPad panelIcms = new JPanelPad( 390, 100 );

	private final JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDocVenda = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtCodCli = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldFK txtDtEmitVenda = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldFK txtDtSaiVenda = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldFK txtVlrLiqVenda = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private final JTextFieldFK txtSeries = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private final ListaCampos lcVenda = new ListaCampos( this, "" );
	
	private final JTextFieldPad txtDtEntrega = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtRecebedor = new JTextFieldPad( JTextFieldPad.TP_STRING, 200, 0 );
	
	private final JTextFieldPad txtEntregador = new JTextFieldPad( JTextFieldPad.TP_STRING, 200, 0 );
	
	private final ListaCampos lcCliente = new ListaCampos( this, "CL" );
	
	private final JTextAreaPad txaObsEntrega = new JTextAreaPad( 1000 );

	private final JScrollPane spnObs = new JScrollPane( txaObsEntrega );

	private final JPanelPad panelCampos = new JPanelPad();

	private final JPanelPad pinCab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	public FEntrega() {

		super( false );

		nav.setNavigation( true );

		setTitulo( "Lan�amento de Conhotos de Entrega" );
		setAtribos( 50, 50, 500, 360 );

		pinCab.add( panelCampos, BorderLayout.NORTH );
		pinCab.add( spnObs, BorderLayout.CENTER );
		
		this.add( pinCab );
		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o Social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		lcCliente.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCliente, null );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		txtCodVenda.setNomeCampo( "CodVenda" );

		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "N.pedido", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtDocVenda, "DocVenda", "N.doc.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtSeries, "Serie", "Serie", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "V.liq.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtDtEmitVenda, "DtEmitVenda", "Dt.Emiss�o", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtDtSaiVenda, "DtEmitVenda", "Dt.Emiss�o", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli", ListaCampos.DB_FK, false ) );
		
		txtDocVenda.setListaCampos( lcVenda );
		txtCodVenda.setTabelaExterna( lcVenda, FVenda.class.getCanonicalName() );
		txtCodVenda.setFK( true );

		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setQueryCommit( false );
		lcVenda.setReadOnly( true );

		panelCampos.setPreferredSize( new Dimension( 600, 180 ) );
		setPainel( panelCampos );
		
		spnObs.setBorder( SwingParams.getPanelLabel( "Observa��es", Color.BLACK ) );
		
		adicCampo( txtCodVenda, 7, 20, 80, 20, "CodVenda", "N� pedido", ListaCampos.DB_PF, txtDocVenda, true );
		adicDescFK( txtDocVenda, 90, 20, 70, 20, "DocVenda", "N� Doc" );
		adicDescFK( txtSeries, 163, 20, 40, 20, "Serie", "S�rie" );
		adicDescFK( txtDtEmitVenda, 206, 20, 80, 20, "DtEmitVenda", "Emiss�o" );
		adicDescFK( txtDtSaiVenda, 289, 20, 80, 20, "DtSaiVenda", "Sa�da" );
		adicDescFK( txtVlrLiqVenda, 372, 20, 110, 20, "VlrLiqVenda", "Valor" );
		adicDescFK( txtCodCli, 7, 60, 80, 20, "CodCli", "C�d.Cli" );
		adicDescFK( txtRazCli, 90, 60, 392, 20, "RazCli", "Raz�o Social do Cliente" );
		
		adicCampo( txtDtEntrega, 7, 100, 80, 20, "DtEntrega", "Entrega", ListaCampos.DB_SI, true );
		adicCampo( txtEntregador, 90, 100, 392, 20, "Entregador", "Entregador", ListaCampos.DB_SI, false );
		adicCampo( txtRecebedor, 7, 140, 475, 20, "Recebedor", "Recebedor", ListaCampos.DB_SI, true );
		
		adicDBLiv( txaObsEntrega, "ObsEntrega", "OBserva��es", false );
		
		setListaCampos( true, "VENDAENTREGA", "VD" );

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		setImprimir( true );
	}


	private void imprimir( boolean bVisualizar ) {
		
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );

		String[] sValores;
		
		StringBuilder where = new StringBuilder();

		DLREntrega dl = new DLREntrega();
		dl.setVisible( true );

		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}

		sValores = dl.getValores();
		dl.dispose();


		
		String sDataini = sValores[ 1 ];
		String sDatafim = sValores[ 2 ];

		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append( "select e.codvenda, v.docvenda, v.serie, v.vlrliqvenda, v.dtemitvenda, v.dtsaidavenda, e.dtentrega, e.entregador, e.recebedor, v.codcli, c.razcli, e.obsentrega " );
			sql.append( "from vdvendaentrega e, vdvenda v, vdcliente c " );
			sql.append( "where e.codemp=v.codemp and e.codfilial=v.codfilial and e.tipovenda=v.tipovenda and e.codvenda=v.codvenda and " );
			sql.append( "c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli " );
			sql.append( "and e.codemp=? and e.codfilial=? and e.dtentrega between ? and ? ");
			
			sql.append( "order by " + sValores[0] );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDAENTREGA" ) );
			ps.setDate( 3, Funcoes.strDateToSqlDate( sValores[1] ));
			ps.setDate( 4, Funcoes.strDateToSqlDate( sValores[2] ));
			
			ResultSet rs = ps.executeQuery();
			
			hParam.put( "FILTROS", " de "+ sValores[1] + " at� " + sValores[2] );
			
			FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_ENTREGAS_01.jasper" , "Relat�rio de entregas", "", rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de entregas" + err.getMessage(), true, con, err );
				}
			}
			
			
			
			rs.close();
			ps.close();
			con.commit();

			dl.dispose();

		} 
		catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela de entregas!\n" + err.getMessage(), true, con, err );
		}

		
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

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcVenda.setConexao( cn );
		lcCliente.setConexao( cn );

	}
}
