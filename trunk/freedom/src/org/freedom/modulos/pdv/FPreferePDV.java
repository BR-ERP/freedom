/**
 * @version 23/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FPrefere.java <BR>
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
 * Tela para cadastro de prefer�ncias do PDV
 * 
 */

package org.freedom.modulos.pdv;

import org.freedom.infra.model.jdbc.DbConnection;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FTabDados;

public class FPreferePDV extends FTabDados {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinVenda = new JPanelPad();

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodProdFrete = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProdeFrete = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbAutoFechaVenda = new JCheckBoxPad( "Fechar a venda apos buscar or�amento?", "S", "N" );

	private JCheckBoxPad cbHabReceber = new JCheckBoxPad( "Habilita aba receber no fechamento?", "S", "N" );

	private JCheckBoxPad cbAdicionais = new JCheckBoxPad( "Dados adicionais p/ frete no fechamento?", "S", "N" );

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PP" );

	private ListaCampos lcCliente = new ListaCampos( this, "CL" );

	private ListaCampos lcProdFrete = new ListaCampos( this, "PD" );

	public FPreferePDV() {

		super();
		setTitulo( "Prefer�ncias do PDV" );
		setAtribos( 50, 50, 355, 395 );

		lcCampos.setMensInserir( false );

		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, true ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		txtCodTipoMov.setFK( true );
		txtCodTipoMov.setNomeCampo( "CodTipoMov" );

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.tp.mov.", ListaCampos.DB_PK, true ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o do cliente padr�o", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		lcCliente.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.cli.", ListaCampos.DB_PK, true ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );

		lcProdFrete.add( new GuardaCampo( txtCodProdFrete, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProdFrete.add( new GuardaCampo( txtDescProdeFrete, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdFrete.montaSql( false, "PRODUTO", "EQ" );
		lcProdFrete.setReadOnly( true );
		txtCodProdFrete.setTabelaExterna( lcProdFrete );

		setPainel( pinVenda );
		adicTab( "Venda", pinVenda );
		adicCampo( txtCodTipoMov, 10, 30, 77, 20, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescTipoMov, 90, 30, 230, 20, "DescTipoMov", "Descri��o do tipo de movimento" );
		adicCampo( txtCodPlanoPag, 10, 70, 77, 20, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescPlanoPag, 90, 70, 230, 20, "DescPlanoPag", "Descri��o do plano de pagamento" );
		adicCampo( txtCodCli, 10, 110, 77, 20, "CodCli", "C�d.cli.", ListaCampos.DB_FK, true );
		adicDescFK( txtRazCli, 90, 110, 230, 20, "RazCli", "Raz�o do cliente padr�o" );
		adicDB( cbAutoFechaVenda, 20, 155, 270, 20, "AutoFechaVenda", "", true );
		adicDB( cbHabReceber, 20, 175, 270, 20, "HabReceber", "", true );
		adicDB( cbAdicionais, 20, 195, 270, 20, "AdicPDV", "", true );
		adic( new JLabelPad( "Produto para frete." ), 20, 220, 250, 20 );
		adicCampo( txtCodProdFrete, 20, 255, 77, 20, "CodProd", "C�d.prod.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescProdeFrete, 100, 255, 200, 20, "DescProd", "Descri��o do produto" );

		JLabel lbBorda = new JLabel();
		lbBorda.setBorder( BorderFactory.createEtchedBorder() );
		JLabel lbOpcoes = new JLabelPad( "Op��es", SwingConstants.CENTER );
		lbOpcoes.setOpaque( true );

		adic( lbOpcoes, 15, 135, 60, 20 );
		adic( lbBorda, 10, 145, 310, 140 );

		setListaCampos( false, "PREFERE4", "SG" );

		nav.setAtivo( 0, false );
		nav.setAtivo( 1, false );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcCliente.setConexao( cn );
		lcProdFrete.setConexao( cn );
		lcCampos.carregaDados();
	}
}
