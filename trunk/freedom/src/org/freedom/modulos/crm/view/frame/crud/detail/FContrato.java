/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.view.frame.crud.detail <BR>
 *         Classe: @(#)FContrato.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Tela de cadastro de contratos.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.detail;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.crm.business.component.Constant;
import org.freedom.modulos.crm.business.object.Contrato;
import org.freedom.modulos.crm.dao.DAOGestaoProj;
import org.freedom.modulos.crm.view.dialog.utility.DLMinutaContr;
import org.freedom.modulos.crm.view.frame.crud.plain.FModContr;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

public class FContrato extends FDetalhe implements ActionListener, InsertListener, RadioGroupListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private JCheckBoxPad cbAtivo = new JCheckBoxPad( "Ativo", "S", "N" );

	private JCheckBoxPad cbReceb = new JCheckBoxPad( "Receb�vel", "S", "N" );
	
	private JCheckBoxPad cbContHSubContr = new JCheckBoxPad( "Contabiliza horas no sub-contratos", "S", "N" );

	private JCheckBoxPad cbFranquiaItContr = new JCheckBoxPad( "Franquia", "S", "N" );
	
	private JLabelPad lbStatus = new JLabelPad();
	
	private JTextFieldPad txtCodContrato = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtDescContrato = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtMinuta = new JTextFieldPad( JTextFieldPad.TP_STRING, 32000, 0 );

	private JTextFieldPad txtDtInicioContr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFimContr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtPrevFin = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDiaVencCobr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtDiaFechCobr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtTipoCobr = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodItContrato = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtQtdProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrExcedProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtAcumuloItContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodProdPE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDescItContr = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldFK txtDescProdPE = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodContratoPai = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtDescContratoPai = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldPad txtIndexContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtIndexItContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtCodModContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtDescModContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 80, 0 );
	
	private JTextFieldFK txtLayoutModContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 80, 0 );

	private JTextAreaPad txaMinuta = new JTextAreaPad( 32000 );
	
	private JTextFieldPad txtSitContrato = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JButtonPad btMinuta = new JButtonPad( Icone.novo( "btObs.gif" ) );
	
	private JButtonPad btCancelContr = new JButtonPad( Icone.novo( "btExcluir.gif" ) );
	
	private JButtonPad btImprimir = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );

	private JRadioGroup<?, ?> rgTipoCobContr = null;

	private JRadioGroup<?, ?> rgTipoContr = null;

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcProduto = new ListaCampos( this, "PD" );

	private ListaCampos lcProdutoex = new ListaCampos( this, "PE" );
	
	private ListaCampos lcSuperProjeto = new ListaCampos( this, "SP" );
	
	private ListaCampos lcModContr = new ListaCampos( this, "MC" );

	private String sMinuta = "";
	
	private JTextFieldPad txtKeyLic = new JTextFieldPad(JTextFieldPad.TP_STRING, 500, 0);
	
	private DAOGestaoProj daogestao = null;

	public FContrato() {
		
		setTitulo( "Contratos" );

		nav.setNavigation( true );

		setAltCab( 260 );
		setAtribos( 50, 50, 715, 600 );
		pinCab = new JPanelPad( 500, 50 );

		montaListaCampos();
		montaGrupoRadio();
		txtDtPrevFin.setSoLeitura( true );
		montaTela();
		
	}
	
	public FContrato(DbConnection cn, Integer codcontr) {
		
		this();	
		setConexao( cn );
		txtCodContrato.setVlrInteger( codcontr );
		lcCampos.carregaDados();
		
	}
	
	public FContrato( DbConnection cn, Integer codcontr, Integer coditcontr){
		this(cn, codcontr);
		txtCodItContrato.setVlrInteger( coditcontr );
		lcDet.carregaDados();
	}
	
	
	private void montaTela(){
		
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		adicCampo( txtCodContrato, 7, 20, 70, 20, "CodContr", "C�d.proj.", ListaCampos.DB_PK, true );
		adicCampo( txtDescContrato, 80, 20, 500, 20, "DescContr", "Descri��o do projeto/contrato", ListaCampos.DB_SI, true );
		adicDB( cbReceb, 580, 20, 100, 20, "RecebContr", "", true);
		
		adicCampoInvisivel( txtSitContrato, "SITCONTR", "Sit. Contr.", ListaCampos.DB_SI, false );

		adicCampo( txtCodCli, 7, 60, 70, 20, "CodCli", "C�d.Cli", ListaCampos.DB_FK, txtNomeCli, true );
		adicDescFK( txtNomeCli, 80, 60, 277, 20, "RazCli", "Raz�o social do cliente" );
		adicCampo( txtIndexContr, 360, 60, 40, 20, "IndexContr", "Index", ListaCampos.DB_SI, true );
		

		adicCampo( txtDtInicioContr, 403, 60, 75, 20, "DtInicio", "Dt.inicio", ListaCampos.DB_SI, true );
		adicCampo( txtDtFimContr, 481, 60, 75, 20, "DtFim", "Dt.fim", ListaCampos.DB_SI, true );
		adicCampo( txtDiaVencCobr, 559, 60, 60, 20, "DiaVencContr", "Dia venc.", ListaCampos.DB_SI, true );
		adicCampo( txtDiaFechCobr, 622, 60, 60, 20, "DiaFechContr", "Dia fech.", ListaCampos.DB_SI, true );

		adicDB( rgTipoCobContr, 7, 100, 395, 30, "TpCobContr", "Cobran�a", true );
		adicDB( rgTipoContr, 405, 100, 277, 30, "TpContr", "Tipo", true );
		adicCampo ( txtCodContratoPai,  7, 150,  70, 20, "CodContrSp", "C�d.Proj.", ListaCampos.DB_FK, txtDescContratoPai, false);
		adicDescFK( txtDescContratoPai, 80, 150, 279, 20, "DescContr", "Descri��o do projeto principal" );
		adic( lbStatus, 363, 150, 110, 20 );
		adicCampo( txtDtPrevFin, 477, 150, 75, 20, "DtPrevFin", "Dt.prev.", ListaCampos.DB_SI, false );
		adicDB( cbAtivo, 554, 145, 60, 30, "Ativo", "", true );
		adic( btMinuta, 614,145, 30, 30 );
		adic( btCancelContr, 647,145, 30, 30 );
		btCancelContr.setToolTipText( "Cancelar Projeto/Contrato" );
		adicDB( cbContHSubContr	, 7, 170, 250, 30, "ContHSubContr", "", false );
		
		adicCampo( txtCodModContr, 260, 190, 80, 20, "CodModContr", "Mod.Contr", ListaCampos.DB_FK, txtDescModContr, false );
		adicDescFK( txtDescModContr, 343, 190, 274, 20, "DescModContr", "Descri��o do Modelo do Contrato" );
		adic( btImprimir, 621 , 185, 30, 30 );
			
		txtCodContratoPai.setNomeCampo( "CodContr" ); 
		txtCodContratoPai.setEnabled( false );
		txtDescContratoPai.setEnabled( false );

		adicDBInvisivel( txaMinuta, "MinutaContr", "Minuta do cotrato", false );

		setListaCampos( true, "CONTRATO", "VD" );
		lcCampos.setQueryInsert( false );

		txaMinuta.setVisible( false );

		setAltDet( 140 );
		pinDet = new JPanelPad( 600, 80 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodItContrato, 7, 25, 60, 20, "CodItContr", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtDescItContr, 70, 25, 569, 20, "DescItContr", "Descri��o do item de contrato", ListaCampos.DB_SI, true );
		adicCampo( txtIndexItContr, 642, 25, 40, 20, "IndexItContr", "Index", ListaCampos.DB_SI, true );
		adicCampo( txtCodProd, 7, 65, 60, 20, "CodProd", "C�d.prod.", ListaCampos.DB_FK, txtDescProd, true );
		adicDescFK( txtDescProd, 70, 65, 270, 20, "DescProd", "Descri��o do produto/servi�o" );
		adicCampo( txtCodProdPE, 343, 65, 60, 20, "CodProdPE", "C�d.prod.", ListaCampos.DB_FK, txtDescProdPE, true );
		adicDescFK( txtDescProdPE, 406, 65, 277, 20, "DescProdPE", "Descri��o do produto/servi�o excedente" );

		adicCampo( txtQtdProd, 7, 105, 100, 20, "QtdItContr", "Quantidade", ListaCampos.DB_SI, true );
		adicCampo( txtVlrProd, 110, 105, 100, 20, "VlrItContr", "Valor normal", ListaCampos.DB_SI, true );
		adicCampo( txtVlrExcedProd, 213, 105, 100, 20, "VlrItContrExced", "Valor excedente", ListaCampos.DB_SI, true );	
		adicCampo( txtAcumuloItContr, 316, 105, 100, 20, "AcumuloItContr", "Meses/Acumulo", ListaCampos.DB_SI, true );
		adicCampo( txtKeyLic, 420, 105, 200, 20, "KeyLic", "Chave de licenciamento do produto", ListaCampos.DB_SI, false);
		adicDB( cbFranquiaItContr, 623, 105, 80, 20, "FranquiaItContr", "", true );
		
		
		lbStatus.setForeground( Color.WHITE );
		lbStatus.setBackground( Color.BLACK );
		lbStatus.setFont( SwingParams.getFontboldmed() );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
		lbStatus.setOpaque( true );
		lbStatus.setText( "N�O SALVO" );

		setListaCampos( true, "ITCONTRATO", "VD" );
		lcDet.setQueryInsert( false );
		montaTab();
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this ); 
	
		tab.setTamColuna( 40, 0 );
		tab.setTamColuna( 420, 1 );
		tab.setColunaInvisivel( 2 ); 
		tab.setColunaInvisivel( 3 );
		tab.setColunaInvisivel( 4 );
		tab.setColunaInvisivel( 5 );
		
		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );
		lcCli.addCarregaListener( this );
		lcModContr.addCarregaListener( this );
		
	}

	private void montaListaCampos() {

		/*************
		 * CLIENTE * *
		 **********/

		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		lcCli.setQueryCommit( false );
		

		/**********************
		 * PRODUTO PRINCIPAL * *
		 *******************/

		txtCodProd.setTabelaExterna( lcProduto, FProduto.class.getCanonicalName() );
		txtCodProd.setFK( true );
		txtCodProd.setNomeCampo( "CodProd" );
		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
		lcProduto.setReadOnly( true );
		lcProduto.setQueryCommit( false );

		/**********************
		 * PRODUTO EXCEDENTE * *
		 *******************/

		txtCodProdPE.setTabelaExterna( lcProdutoex, FProduto.class.getCanonicalName() );
		txtCodProdPE.setFK( true );
		txtCodProdPE.setNomeCampo( "CodProdPE" );
		lcProdutoex.add( new GuardaCampo( txtCodProdPE, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ) );
		lcProdutoex.add( new GuardaCampo( txtDescProdPE, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProdutoex.montaSql( false, "PRODUTO", "EQ" );
		lcProdutoex.setReadOnly( true );
		lcProdutoex.setQueryCommit( false );
		
		/**********************
		 * MODELO DE CONTRATO  * *
		 *******************/
		txtCodModContr.setTabelaExterna( lcModContr, FModContr.class.getCanonicalName() );
		txtCodModContr.setFK( true );
		txtCodModContr.setNomeCampo( "CodModContr" );
		lcModContr.add( new GuardaCampo( txtCodModContr, "CodModContr", "C�d.Mod.Contr.", ListaCampos.DB_PK, false ) );
		lcModContr.add( new GuardaCampo( txtDescModContr, "DescModContr", "Descri��o do Modelo de Contrato", ListaCampos.DB_SI, false ) );
		lcModContr.add( new GuardaCampo( txtLayoutModContr, "LayoutModContr", "Layout do Modelo de Contrato", ListaCampos.DB_SI, false ) );
		lcModContr.montaSql( false, "MODCONTR", "VD" );
		lcModContr.setReadOnly( true );
		lcModContr.setQueryCommit( false );
		
		/**********************
		 * CONTRATO PAI   * *
		 *******************/
		//txtCodContratoPai.setFK( true );
		lcSuperProjeto.setQueryCommit( false );
		lcSuperProjeto.setReadOnly( true );
		lcSuperProjeto.add( new GuardaCampo( txtCodContratoPai, "CodContr", "C�d.Contr.", ListaCampos.DB_PK, txtDescContratoPai, false ) );
		lcSuperProjeto.add( new GuardaCampo( txtDescContratoPai, "DescContr", "Descri��o do contrato", ListaCampos.DB_SI, false ) );
		lcSuperProjeto.montaSql( false, "CONTRATO", "VD" );
		txtCodContratoPai.setTabelaExterna( lcSuperProjeto, FContrato.class.getCanonicalName() );
		txtCodContratoPai.setFK( true );
		//txtCodContratoPai.setNomeCampo( "CodContr" );
		
		btMinuta.addActionListener( this );
		btCancelContr.addActionListener( this );
		btImprimir.addActionListener( this );
		this.lcCampos.addCarregaListener( this );
	}
	
	private void montaGrupoRadio(){
		
		Vector<String> vValsTipoCob = new Vector<String>();
		Vector<String> vLabsTipoCob = new Vector<String>();
		vValsTipoCob.addElement( "ME" );
		vValsTipoCob.addElement( "BI" );
		vValsTipoCob.addElement( "AN" );
		vValsTipoCob.addElement( "ES" );
		vLabsTipoCob.addElement( "Mensal" );
		vLabsTipoCob.addElement( "Bimestral" );
		vLabsTipoCob.addElement( "Anual" );
		vLabsTipoCob.addElement( "Espor�dico" );
		rgTipoCobContr = new JRadioGroup<String, String>( 1, 4, vLabsTipoCob, vValsTipoCob );
		rgTipoCobContr.setVlrString( "AN" );

		Vector<String> vValsTipo = new Vector<String>();
		Vector<String> vLabsTipo = new Vector<String>();
		vValsTipo.addElement( "C" );
		vValsTipo.addElement( "P" );
		vValsTipo.addElement( "S" );
		vLabsTipo.addElement( "Contrato" );
		vLabsTipo.addElement( "Projeto" );
		vLabsTipo.addElement( "Sub-projeto" );
		rgTipoContr = new JRadioGroup<String, String>( 1, 3, vLabsTipo, vValsTipo );
		rgTipoContr.setVlrString( "C" );
		rgTipoContr.addRadioGroupListener( this );

	}

	private void abreDLMinuta() {

		DLMinutaContr dl = new DLMinutaContr( txaMinuta.getVlrString() );
		dl.setVisible( true );

		if ( dl.OK ) {
			txaMinuta.setVlrString( dl.getValores() );

		}
	}
	
	private void abreDLFinalizaContr() {
		FFinalizaProjeto dl = new FFinalizaProjeto();
		if (this.lcCampos.getStatus() == ListaCampos.LCS_SELECT ||				
				this.lcCampos.getStatus() == ListaCampos.LCS_EDIT)
		{
			boolean cancelado = dl.exibirCarregado( this.con, this.txtCodContrato.getVlrInteger() );
			if (cancelado){
				this.lcCampos.carregaDados();
			}
			
		}
	}
	
	public void carregaProjeto( Integer codcontr ){
		txtCodContrato.setVlrInteger( codcontr );
		lcCampos.carregaDados();
	
	}

	@ Override
	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );

		if ( evt.getSource() == btMinuta ) {
			abreDLMinuta();
		
		} else if ( evt.getSource() == btCancelContr ) {
			abreDLFinalizaContr();
		
		} else if( evt.getSource() == btImprimir){
			if(txtCodModContr.getVlrInteger() > 0){
				imprimirGrafico(true);
			} else {
				Funcoes.mensagemInforma( this, "Modelo do contrato n�o selecionado !!!" );
			}
		}
		
	}
	
	public void imprimirGrafico(boolean bVisualizar){
	
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sql.append(" select ct.codcontr, ct.dtinicio, ct.dtfim ");
		sql.append(", ct.codcli, cl.razcli ");
		sql.append(", cl.cpfcli, cl.agenciacli ");
		sql.append(", cl.ncontabcocli, itpf.codconv, pf.nomeemp ");
		sql.append(", mc.texto1, mc.texto2 ");
		sql.append("from vdcontrato ct ");
		sql.append("inner join vdcliente cl on ");
		sql.append("cl.codemp=ct.codempcl and cl.codfilial=ct.codfilialcl and cl.codcli=ct.codcli ");
		sql.append("inner join vdmodcontr mc on ");
		sql.append("mc.codemp=ct.codempmc and mc.codfilial=ct.codfilialmc and ");
		sql.append("mc.codmodcontr=ct.codmodcontr ");
		sql.append("left outer join vditcontrato ic on ");
		sql.append("ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr ");
		sql.append("left outer join sgitprefere6 itpf on ");
		sql.append("itpf.codempbo=mc.codempbo and itpf.codfilialbo=mc.codfilialbo and itpf.codbanco=mc.codbanco ");
		sql.append("and itpf.tipofebraban=(case when mc.tpmodcontr='S' then '01' else '00' end) ");
		sql.append("left outer join sgprefere6  pf on ");
		sql.append("pf.codemp=itpf.codemp and pf.codfilial=itpf.codfilial ");

		sql.append("where ct.codemp=? and ct.codfilial=? and ct.codcontr=? ");
		try {
			ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDCONTRATO" ) );
			ps.setInt( param++, txtCodContrato.getVlrInteger() );
			
			rs = ps.executeQuery();
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "Erro executando consulta: \n" + e.getMessage() );
			e.printStackTrace();
		}
		

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNLANCA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		
		dlGr = new FPrinterJob( txtLayoutModContr.getVlrString(), "Modelo de Contrato", "", rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o do balancete!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		lcCampos.setConexao( con );
		lcCli.setConexao( con );
		lcProduto.setConexao( con );
		lcProdutoex.setConexao( con );
		lcSuperProjeto.setConexao( con );
		lcModContr.setConexao( con );
		
		daogestao = new DAOGestaoProj(con);
	}
	
	public void setSeqIndiceItemContr(){
		try {
			txtIndexItContr.setVlrInteger( daogestao.getNewIndiceItemContr( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCONTRATO" ), txtCodContrato.getVlrInteger() ) );
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao buscar Indice do item do contrato!\n" + e.getMessage() );	
			e.printStackTrace();
		}
	}
	
	public void setSeqIndiceContr(){
		try {
			txtIndexContr.setVlrInteger( daogestao.getNewIndiceContr( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCONTRATO" ), txtCodCli.getVlrInteger()) );
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao buscar Indice do contrato!\n" + e.getMessage() );	
			e.printStackTrace();
		}
	}

	public void afterInsert( InsertEvent ievt ) {

		if (ievt.getListaCampos()==lcCampos) {
			cbReceb.setVlrString( "S" );
			txtSitContrato.setVlrString( "PE" );
			cbContHSubContr.setVlrString( "N" );

		} else if (ievt.getListaCampos()== lcDet){
			setSeqIndiceItemContr();
			cbFranquiaItContr.setVlrString( "S" );
		}
		
	}

	public void beforeInsert( InsertEvent ievt ) {
		
	}
	
	public void valorAlterado(RadioGroupEvent evt){
		if (evt.getSource() == rgTipoContr){
			visualizarSuperProjeto("S".equals( rgTipoContr.getVlrString()));
		}
	}
	
	private void visualizarSuperProjeto(boolean flag){
		this.txtCodContratoPai.setEnabled( flag );
		this.txtDescContratoPai.setEnabled( flag );
		if (!flag){
			this.txtCodContratoPai.setVlrString( "" );
			this.txtDescContratoPai.setVlrString( "" );
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {}

	public void afterCarrega( CarregaEvent cevt ) {
		if (cevt.getListaCampos()==lcCampos) {
			setSitcontr();
		
		}
		else if (cevt.getListaCampos() ==lcCli	){
			if(lcCampos.getStatus()==ListaCampos.LCS_INSERT) { 
				setSeqIndiceContr();
			}
		
		}
		/*
		else if ( cevt.getListaCampos() == lcModContr){
			if(txtCodModContr.getVlrInteger() != null){
				btImprimir.setEnabled( true );
			} else {
				btImprimir.setEnabled( false );
			}
		} */

		
	}
	
	private void setSitcontr() {
		String statusProj = txtSitContrato.getVlrString().trim();
		Vector<Constant> listaSit = Contrato.getListSitproj();
		Constant item = null;
		for (int i=0; i<listaSit.size(); i++) {
			item = listaSit.elementAt( i );
			if (statusProj.equals( item.getValue())) {
				lbStatus.setBackground( item.getBgcolor() );
				lbStatus.setForeground( item.getFgcolor() );
				lbStatus.setText( item.getName() );
				break;
			}
		}
	}
}
