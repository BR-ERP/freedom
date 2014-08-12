/**
 * @version 25/09/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp <BR>
 *         Classe: @(#)FPrefereProd.java <BR>
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
 *         Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.pcp.view.frame.crud.tabbed;

import java.awt.Color;
import java.util.Vector;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.PainelImagem;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.gms.business.object.TipoMov;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FTipoMov;

public class FPrefereProd extends FTabDados  implements InsertListener {

	private JPanelPad pinOp = new JPanelPad( "Ordem de produ��o", Color.RED );

	private JPanelPad pinRespon = new JPanelPad( "Repons�vel t�cnico da Produ��o", Color.BLUE );

	private JPanelPad pinRma = new JPanelPad( "Requisi��o de material", Color.BLUE );

	private JPanelPad pinCQ = new JPanelPad( "Controle de qualidade", Color.BLUE );

	private JPanelPad pinConv = new JPanelPad( "Convers�o de produtos", Color.BLUE );

	private static final long serialVersionUID = 1L;

	private final JPanelPad pinGeral = new JPanelPad();

	private final JPanelPad pinAss = new JPanelPad( 470, 300 );

	private final JPanelPad pinFichaTecnica = new JPanelPad();

	private final JTextFieldPad txtLayoutFT = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );
	
	private final JTextFieldPad txtClass = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtNomeResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtIdentProfResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtCargoResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtCodTipoMovSP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoMovSP = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodTipoMovEN = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoMovEN = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtCodTipoMovRE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoMovRE = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtNDiaMes = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JRadioGroup<?, ?> rgNomeRelAnal = null;

	private JComboBoxPad cbSitRMAOP = null;

	private JComboBoxPad cbSitOPConv = null;

	private JComboBoxPad cbSitOP = null;

	private final JCheckBoxPad cbBaixaRmaAprov = new JCheckBoxPad( "Baixar o estoque de RMA aprovada?", "S", "N" );

	private final JCheckBoxPad cbAuto = new JCheckBoxPad( "Automatizar rateio de itens/lote?", "S", "N" );

	private final JCheckBoxPad cbExcluiRma = new JCheckBoxPad( "Permite excluir RMA de outro usu�rio?", "S", "N" );

	private final JCheckBoxPad cbHabConvCp = new JCheckBoxPad( "Permite a convers�o de produtos na compra?", "S", "N" );
	
	private final JCheckBoxPad cbProdEtapas = new JCheckBoxPad( "Permite finaliza��o em etapas?", "S", "N" );
	
	private final JCheckBoxPad cbExpedirRMA = new JCheckBoxPad( "Finalizar OP somente com RMA expedida?", "S", "N" );

	private final JCheckBoxPad cbValidaQTDOp = new JCheckBoxPad( "Valida��o de quantidade para produtos FSC?", "S", "N" );
	
	private final JCheckBoxPad cbValidaFase = new JCheckBoxPad( "Verifica fases ao finalizar?", "S", "N" );
	
	private final JCheckBoxPad cbEditQtdOP = new JCheckBoxPad( "Permite altera��o da quantidade da OP?", "S", "N" );

	private final JCheckBoxPad cbOpSeq = new JCheckBoxPad( "Ordem de produ��o sequencial?", "S", "N" );
	
	private final JCheckBoxPad cbBloqOpSemSaldo = new JCheckBoxPad( "Bloquear OP S/Saldo de Mat�ria Prima?", "S", "N" );
	
	private JTextFieldPad txtCodImgFT01 = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	
	private JTextFieldFK txtDescImgFT01 = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private ListaCampos lcImgFT01 = new ListaCampos( this, "I1" );

	private JTextFieldPad txtCodImgFT02 = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	
	private JTextFieldFK txtDescImgFT02 = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private ListaCampos lcImgFT02 = new ListaCampos( this, "I2" );

	private JTextFieldPad txtCodImgFT03 = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	
	private JTextFieldFK txtDescImgFT03 = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private ListaCampos lcImgFT03 = new ListaCampos( this, "I3" );

	private JTextFieldPad txtCodImgFT04 = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	
	private JTextFieldFK txtDescImgFT04 = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private ListaCampos lcImgFT04 = new ListaCampos( this, "I4" );

	private JTextFieldPad txtCodImgFT05 = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	
	private JTextFieldFK txtDescImgFT05 = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private ListaCampos lcImgFT05 = new ListaCampos( this, "I5" );

	private JTextFieldPad txtCodImgFT06 = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	
	private JTextFieldFK txtDescImgFT06 = new JTextFieldFK(JTextFieldPad.TP_STRING, 80, 0);

	private ListaCampos lcImgFT06 = new ListaCampos( this, "I6" );

	private final PainelImagem imgAssOrc = new PainelImagem( 65000 );

	private final ListaCampos lcTipoMov = new ListaCampos( this, "TM" );
	
	private final ListaCampos lcTipoMovSP = new ListaCampos( this, "TS" );
	
	private final ListaCampos lcTipoMovEN = new ListaCampos( this, "EN" );
	
	private final ListaCampos lcTipoMovRE = new ListaCampos( this, "RE" );

	public FPrefereProd() {

		super();
		
		setTitulo( "Prefer�ncias de Produ��o" );
		setAtribos( 50, 50, 754, 530 );

		montaListaCampos();
		montaTela();

	}

	private void montaTela() {

		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Pendente" );
		vLabs.addElement( "Aprovada" );

		vVals.addElement( "" );
		vVals.addElement( "PE" );
		vVals.addElement( "AF" );

		cbSitRMAOP = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0 );

		Vector<String> vLabsConv = new Vector<String>();
		Vector<String> vValsConv = new Vector<String>();

		vLabsConv.addElement( "Pendente" );
		vLabsConv.addElement( "Finalizada" );

		vValsConv.addElement( "PE" );
		vValsConv.addElement( "FN" );

		cbSitOPConv = new JComboBoxPad( vLabsConv, vValsConv, JComboBoxPad.TP_STRING, 2, 0 );

		Vector<String> vLabsSitOP = new Vector<String>();
		Vector<String> vValsSitOP = new Vector<String>();

		vLabsSitOP.addElement( "Pendente" );
		vLabsSitOP.addElement( "Bloqueada" );
		vLabsSitOP.addElement( "Finalizada" );

		vValsSitOP.addElement( "PE" );
		vValsSitOP.addElement( "BL" );
		vValsSitOP.addElement( "FN" );

		cbSitOP = new JComboBoxPad( vLabsSitOP, vValsSitOP, JComboBoxPad.TP_STRING, 2, 0 );

		Vector<String> vNomeRelLab = new Vector<String>();
		Vector<String> vNomeRelVal = new Vector<String>();

		vNomeRelLab.addElement( "Respons�vel t�cnico da produ��o" );
		vNomeRelLab.addElement( "Usu�rio que lan�ou a an�lise" );
		vNomeRelVal.addElement( "R" );
		vNomeRelVal.addElement( "U" );

		rgNomeRelAnal = new JRadioGroup<String, String>( 2, 1, vNomeRelLab, vNomeRelVal );

		/******* Aba Geral ***************/	

		adicTab( "Geral", pinGeral );

		/*************** Parametros da ordem de produ��o ***************/

		setPainel( pinOp ); // 

		adicCampo( txtClass, 7, 20, 333, 20, "CLASSOP", "Classe padr�o para O.P.", ListaCampos.DB_SI, false );
		
		adicDB( cbSitOP, 7, 60, 333, 30, "SITPADOP", "Status padr�o para OP", true );
		adicDB( cbProdEtapas, 5, 90, 333, 30, "PRODETAPAS", "", true );
		
		adicDB( cbValidaFase, 5, 113, 333, 30, "VALIDAFASEOP", "", true );
		adicDB( cbValidaQTDOp, 5, 136, 333, 30, "VALIDAQTDOP", "", true );
		adicDB( cbEditQtdOP, 5, 159, 333, 30, "EDITQTDOP", "", true );
		adicDB( cbOpSeq, 5, 182, 333, 30, "OPSEQ", "", true );
		adicDB( cbBloqOpSemSaldo, 5, 205, 333, 30, "BLOQOPSEMSALDO", "", true );
		
		adicCampo( txtCodTipoMov, 7, 253, 80, 20, "CODTIPOMOV", "Cod.Tip.Mov.", ListaCampos.DB_FK, txtDescTipoMov, true );
		adicDescFK( txtDescTipoMov, 90, 253, 249, 20, "DESCTIPOMOV", "Descri��o do tipo de movimento para OP" );
		
		adicCampo( txtCodTipoMovSP, 7, 295, 80, 20, "CODTIPOMOVSP", "Cod.Tip.Mov.", ListaCampos.DB_FK, txtDescTipoMov, false );
		adicDescFK( txtDescTipoMovSP, 90, 295, 249, 20, "DESCTIPOMOV", "Descri��o do tipo mov. para subprodutos" );
		
		adicCampo( txtCodTipoMovEN, 7, 337, 80, 20, "CODTIPOMOVEN", "Cod.Tip.Mov.", ListaCampos.DB_FK, txtDescTipoMov, false );
		adicDescFK( txtDescTipoMovEN, 90, 337, 249, 20, "DESCTIPOMOV", "Descri��o do tipo mov. para remessas" );
		
		adicCampo( txtCodTipoMovRE, 7, 379, 80, 20, "CODTIPOMOVRE", "Cod.Tip.Mov.", ListaCampos.DB_FK, txtDescTipoMov, false );
		adicDescFK( txtDescTipoMovRE, 90, 379, 249, 20, "DESCTIPOMOV", "Descri��o do tipo mov. para retornos" );
		
		txtCodTipoMovRE.setNomeCampo( "codtipomov" );
		
		txtCodTipoMovEN.setNomeCampo( "codtipomov" );
		
		txtCodTipoMovSP.setNomeCampo( "codtipomov" );
	
		pinGeral.adic( pinOp, 7, 5, 358, 430 );

		/*************** Parametros RMA *******************************/

		setPainel( pinRma );

		adicDB( cbSitRMAOP, 7, 20, 333, 30, "SITRMAOP", "Status padr�o para RMA", true );
		adicDB( cbBaixaRmaAprov, 2, 55, 250, 20, "BAIXARMAAPROV", "", false );
		adicDB( cbAuto, 2, 75, 250, 20, "RATAUTO", "", false );
		adicDB( cbExcluiRma, 2, 95, 250, 20, "APAGARMAOP", "", false );
		adicDB( cbExpedirRMA, 2, 115, 333,20, "EXPEDIRRMA", "", false );

		pinGeral.adic( pinRma, 368, 5, 358, 165 );

		/*************** Parametros CQ *******************************/

		setPainel( pinCQ );

		adicDB( rgNomeRelAnal, 7, 20, 335, 60, "NomeRelAnal", "Nome no relat�rio de An�lises", false );
		adic( new JLabelPad( "Meses para descarte de contra prova" ), 7, 85, 300, 20 );
		adicCampo( txtNDiaMes, 7, 105, 100, 20, "MESESDESCCP", "", ListaCampos.DB_SI, false );

		pinGeral.adic( pinCQ, 368, 280, 358, 155 );

		/*************** Convers�o de produtos *******************************/

		setPainel( pinConv );

		adicDB( cbHabConvCp, 2, 0, 333, 20, "HabConvCp", "", true );
		adicDB( cbSitOPConv, 7, 45, 333, 30, "SITPADOPCONV", "Status padr�o para OP de convers�o", true );

		pinGeral.adic( pinConv, 368, 170, 358, 110 );

		/**************** Aba Respons�vel ****************************/

		adicTab( "Respons�vel", pinAss );

		setPainel( pinAss );

		adicDB( imgAssOrc, 9, 185, 358, 85, "ImgAssResp", "Assinatura do respons�vel t�cnico ( 340 pixel X 85 pixel )", true );
		adic( pinRespon, 7, 5, 358, 155 );

		setPainel( pinRespon );

		adicCampo( txtNomeResp, 7, 20, 333, 20, "NOMERESP", "Nome do repons�vel", ListaCampos.DB_SI, false );
		adicCampo( txtIdentProfResp, 7, 60, 333, 20, "IDENTPROFRESP", "Indentifica��o do profissional", ListaCampos.DB_SI, false );
		adicCampo( txtCargoResp, 7, 100, 333, 20, "CARGORESP", "Cargo", ListaCampos.DB_SI, false );

		/**************************************************************/

		adicTab( "Ficha T�cnica", pinFichaTecnica );
		setPainel( pinFichaTecnica );
		adicCampo( txtLayoutFT, 7, 20, 333, 20, "LAYOUTFT", "Layout para ficha t�cnica", ListaCampos.DB_SI, false );
		adicCampo(txtCodImgFT01, 7, 60, 70, 20, "CODIMGFT01", "Cod.img.01", ListaCampos.DB_FK, txtDescImgFT01 , false);
		adicDescFK(txtDescImgFT01, 80, 60, 322, 20, "DESCIMG", "Imagem 01 para ficha t�cnica");
		adicCampo(txtCodImgFT02, 7, 100, 70, 20, "CODIMGFT02", "Cod.img.02", ListaCampos.DB_FK, txtDescImgFT02 , false);
		adicDescFK(txtDescImgFT02, 80, 100, 322, 20, "DESCIMG", "Imagem 02 para ficha t�cnica");
		adicCampo(txtCodImgFT03, 7, 140, 70, 20, "CODIMGFT03", "Cod.img.03", ListaCampos.DB_FK, txtDescImgFT03 , false);
		adicDescFK(txtDescImgFT03, 80, 140, 322, 20, "DESCIMG", "Imagem 03 para ficha t�cnica");
		adicCampo(txtCodImgFT04, 7, 180, 70, 20, "CODIMGFT04", "Cod.img.04", ListaCampos.DB_FK, txtDescImgFT04 , false);
		adicDescFK(txtDescImgFT04, 80, 180, 322, 20, "DESCIMG", "Imagem 04 para ficha t�cnica");
		adicCampo(txtCodImgFT05, 7, 220, 70, 20, "CODIMGFT05", "Cod.img.05", ListaCampos.DB_FK, txtDescImgFT05 , false);
		adicDescFK(txtDescImgFT05, 80, 220, 322, 20, "DESCIMG", "Imagem 05 para ficha t�cnica");
		adicCampo(txtCodImgFT06, 7, 260, 70, 20, "CODIMGFT06", "Cod.img.06", ListaCampos.DB_FK, txtDescImgFT06 , false);
		adicDescFK(txtDescImgFT06, 80, 260, 322, 20, "DESCIMG", "Imagem 06 para ficha t�cnica");

		
		/**************************************************************/

		setListaCampos( false, "PREFERE5", "SG" );

		nav.setAtivo( 0, false );
		nav.setAtivo( 1, false );
		
		
		lcCampos.addInsertListener( this );

	}

	private void montaListaCampos() {

		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setWhereAdic( " TIPOMOV='OP' " );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov, FTipoMov.class.getCanonicalName() );
		txtCodTipoMov.setFK( true );
		
		lcTipoMovSP.add( new GuardaCampo( txtCodTipoMovSP, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMovSP.add( new GuardaCampo( txtDescTipoMovSP, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMovSP.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovSP.setWhereAdic( " TIPOMOV='OP' " );
		lcTipoMovSP.setQueryCommit( false );
		lcTipoMovSP.setReadOnly( true );
		txtCodTipoMovSP.setTabelaExterna( lcTipoMovSP, FTipoMov.class.getCanonicalName() );
		txtCodTipoMovSP.setFK( true );

		lcTipoMovEN.add( new GuardaCampo( txtCodTipoMovEN, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMovEN.add( new GuardaCampo( txtDescTipoMovEN, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMovEN.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovEN.setWhereAdic( " TIPOMOV='"+TipoMov.TM_REMESSA_SAIDA.getValue()+"' " );		
		lcTipoMovEN.setQueryCommit( false );
		lcTipoMovEN.setReadOnly( true );
		txtCodTipoMovEN.setTabelaExterna( lcTipoMovEN, FTipoMov.class.getCanonicalName() );
		txtCodTipoMovEN.setFK( true );

		lcTipoMovRE.add( new GuardaCampo( txtCodTipoMovRE, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMovRE.add( new GuardaCampo( txtDescTipoMovRE, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMovRE.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovRE.setWhereAdic( " TIPOMOV='"+TipoMov.TM_DEVOLUCAO_REMESSA.getValue()+"' " );
		lcTipoMovRE.setQueryCommit( false );
		lcTipoMovRE.setReadOnly( true );
		txtCodTipoMovRE.setTabelaExterna( lcTipoMovRE, FTipoMov.class.getCanonicalName() );
		txtCodTipoMovRE.setFK( true );
		lcCampos.setMensInserir( false );
		
		lcImgFT01.add(new GuardaCampo(txtCodImgFT01, "CODIMG", "C�d.Img", ListaCampos.DB_PK, null, false));
		lcImgFT01.add(new GuardaCampo(txtDescImgFT01, "DESCIMG", "Descri��o da Imagem", ListaCampos.DB_SI, null, false));
		lcImgFT01.montaSql(false, "IMAGEM", "SG");
		lcImgFT01.setQueryCommit(false);
		lcImgFT01.setReadOnly(true);
		txtCodImgFT01.setTabelaExterna(lcImgFT01, null);
		txtCodImgFT01.setFK(true);
		txtDescImgFT01.setListaCampos(lcImgFT01);

		lcImgFT02.add(new GuardaCampo(txtCodImgFT02, "CODIMG", "C�d.Img", ListaCampos.DB_PK, null, false));
		lcImgFT02.add(new GuardaCampo(txtDescImgFT02, "DESCIMG", "Descri��o da Imagem", ListaCampos.DB_SI, null, false));
		lcImgFT02.montaSql(false, "IMAGEM", "SG");
		lcImgFT02.setQueryCommit(false);
		lcImgFT02.setReadOnly(true);
		txtCodImgFT02.setTabelaExterna(lcImgFT02, null);
		txtCodImgFT02.setFK(true);
		txtDescImgFT02.setListaCampos(lcImgFT02);

		lcImgFT03.add(new GuardaCampo(txtCodImgFT03, "CODIMG", "C�d.Img", ListaCampos.DB_PK, null, false));
		lcImgFT03.add(new GuardaCampo(txtDescImgFT03, "DESCIMG", "Descri��o da Imagem", ListaCampos.DB_SI, null, false));
		lcImgFT03.montaSql(false, "IMAGEM", "SG");
		lcImgFT03.setQueryCommit(false);
		lcImgFT03.setReadOnly(true);
		txtCodImgFT03.setTabelaExterna(lcImgFT03, null);
		txtCodImgFT03.setFK(true);
		txtDescImgFT03.setListaCampos(lcImgFT03);

		lcImgFT04.add(new GuardaCampo(txtCodImgFT04, "CODIMG", "C�d.Img", ListaCampos.DB_PK, null, false));
		lcImgFT04.add(new GuardaCampo(txtDescImgFT04, "DESCIMG", "Descri��o da Imagem", ListaCampos.DB_SI, null, false));
		lcImgFT04.montaSql(false, "IMAGEM", "SG");
		lcImgFT04.setQueryCommit(false);
		lcImgFT04.setReadOnly(true);
		txtCodImgFT04.setTabelaExterna(lcImgFT04, null);
		txtCodImgFT04.setFK(true);
		txtDescImgFT04.setListaCampos(lcImgFT04);

		lcImgFT05.add(new GuardaCampo(txtCodImgFT05, "CODIMG", "C�d.Img", ListaCampos.DB_PK, null, false));
		lcImgFT05.add(new GuardaCampo(txtDescImgFT05, "DESCIMG", "Descri��o da Imagem", ListaCampos.DB_SI, null, false));
		lcImgFT05.montaSql(false, "IMAGEM", "SG");
		lcImgFT05.setQueryCommit(false);
		lcImgFT05.setReadOnly(true);
		txtCodImgFT05.setTabelaExterna(lcImgFT05, null);
		txtCodImgFT05.setFK(true);
		txtDescImgFT05.setListaCampos(lcImgFT05);

		lcImgFT06.add(new GuardaCampo(txtCodImgFT06, "CODIMG", "C�d.Img", ListaCampos.DB_PK, null, false));
		lcImgFT06.add(new GuardaCampo(txtDescImgFT06, "DESCIMG", "Descri��o da Imagem", ListaCampos.DB_SI, null, false));
		lcImgFT06.montaSql(false, "IMAGEM", "SG");
		lcImgFT06.setQueryCommit(false);
		lcImgFT06.setReadOnly(true);
		txtCodImgFT06.setTabelaExterna(lcImgFT06, null);
		txtCodImgFT06.setFK(true);
		txtDescImgFT06.setListaCampos(lcImgFT06);

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcTipoMovSP.setConexao( cn );
		lcTipoMovEN.setConexao( cn );
		lcTipoMovRE.setConexao( cn );
		lcImgFT01.setConexao(cn);
		lcImgFT02.setConexao(cn);
		lcImgFT03.setConexao(cn);
		lcImgFT04.setConexao(cn);
		lcImgFT05.setConexao(cn);
		lcImgFT06.setConexao(cn);
		lcCampos.carregaDados();
	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void afterInsert( InsertEvent ievt ) {
		
		if(ievt.getListaCampos() == lcCampos){
			
			cbExpedirRMA.setVlrString( "N" );
			cbValidaFase.setVlrString( "N" );
			cbValidaQTDOp.setVlrString( "N" );
			cbEditQtdOP.setVlrString( "S" );
		}
	
	}	
	
}
