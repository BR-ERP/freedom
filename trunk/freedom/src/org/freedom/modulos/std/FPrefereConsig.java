/**
 * @version 02/03/2009 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FPrefereGeral.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de cadastro das prefer�ncias do sistema. Esse cadastro � utilizado para parametrizar o sistema de acordo com as necessidades espec�ficas da empresa.
 * 
 */

package org.freedom.modulos.std;

import org.freedom.infra.model.jdbc.DbConnection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FTabDados;

public class FPrefereConsig extends FTabDados {

	private static final long serialVersionUID = 1L;	
	
	/****************
	 * Lista Campos *
	 ****************/
	
	private ListaCampos lcTipoMov = new ListaCampos( this, "CO" );
	
	private ListaCampos lcTipoMovTv = new ListaCampos( this, "TV" );
	
	private ListaCampos lcTipoMovTP = new ListaCampos( this, "TP" );
	
	private ListaCampos lcPlanConsig = new ListaCampos( this, "PC" );
	
	private ListaCampos lcPlanVDConsig = new ListaCampos( this, "PV" );	
	
	/****************
	 *    Fields    *
	 ****************/
	
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTipoMovTv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescTipoMovTv = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTipoMovTp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescTipoMovTp = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodPlanConsig = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescPlanConsig = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPlanVDConsig = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescPlanVDConsig = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	/****************
	 *   Paineis    *
	 ****************/
	
	private JPanelPad pinGeral = new JPanelPad( 330, 200 );
	

	public FPrefereConsig() {

		super();
			
		setTitulo( "Prefer�ncias Vendas Consignadas" );
		setAtribos( 30, 40, 420, 200 );
		lcCampos.setMensInserir( false );

		montaListaCampos();		
		montaTela();		
	}	
	
	
	private void montaListaCampos(){
		
		/****************************
		 * Tipo de mov. consigna��o *
		 ***************************/
		
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		
		/****************************
		 * Tipo de mov. ped. venda  *
		 ***************************/
		
		lcTipoMovTv.add( new GuardaCampo( txtCodTipoMovTv, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMovTv.add( new GuardaCampo( txtDescTipoMovTv, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMovTv.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovTv.setQueryCommit( false );
		lcTipoMovTv.setReadOnly( true );
		txtCodTipoMovTv.setTabelaExterna( lcTipoMovTv );		
		
		/****************************
		 *   Tipo de mov. Venda     *
		 ***************************/
		
		lcTipoMovTP.add( new GuardaCampo( txtCodTipoMovTp, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMovTP.add( new GuardaCampo( txtDescTipoMovTp, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMovTP.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovTP.setQueryCommit( false );
		lcTipoMovTP.setReadOnly( true );
		txtCodTipoMovTp.setTabelaExterna( lcTipoMovTP );
		
		/******************************
		 *   Planejamento Consignacao *
		 *****************************/

		txtCodPlanConsig.setTabelaExterna( lcPlanConsig );
		lcPlanConsig.add( new GuardaCampo( txtCodPlanConsig, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, txtDescPlanConsig, false ) );
		lcPlanConsig.add( new GuardaCampo( txtDescPlanConsig, "DescPlan", "Descri�ao do planejamento", ListaCampos.DB_SI, false ) );
		lcPlanConsig.setWhereAdic( "TIPOPLAN = 'R'" );
		lcPlanConsig.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlanConsig.setReadOnly( true );

		/***********************************
		 *   Planejamento Venda Consignada *
		 ***********************************/

		txtCodPlanVDConsig.setTabelaExterna( lcPlanVDConsig );
		txtCodPlanVDConsig.setFK( true );
		txtCodPlanVDConsig.setNomeCampo( "CodPlan" );
		lcPlanVDConsig.add( new GuardaCampo( txtCodPlanVDConsig, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, txtDescPlanVDConsig, false ) );
		lcPlanVDConsig.add( new GuardaCampo( txtDescPlanVDConsig, "DescPlan", "Descri�ao do planejamento", ListaCampos.DB_SI, false ) );
		lcPlanVDConsig.setWhereAdic( "TIPOPLAN = 'D'" );
		lcPlanVDConsig.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlanVDConsig.setReadOnly( true );
		
	}
	
	private void montaTela(){
		
		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );
		
//		adicCampo( txtCodTipoMov, 7, 25, 75, 20, "CodTipoMovCo", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov, false );
//		adicDescFK( txtDescTipoMov, 85, 25, 250, 20, "DescTipoMov", "Tipo de movimento para venda consignada" );
//		adicCampo( txtCodTipoMovTv, 7, 70, 75, 20, "CodTipoMovTv", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMovTv, false );
//		adicDescFK( txtDescTipoMovTv, 85, 70, 250, 20, "DescTipoMov", "Tipo de movimento para pedido de venda" );
//		adicCampo( txtCodTipoMovTp, 7, 110, 75, 20, "CodTipoMovTp", "C�d.tp.mov.", ListaCampos.DB_FK, txtDescTipoMovTp, false );
//		adicDescFK( txtDescTipoMovTp, 85, 110, 250, 20, "DescTipoMov", "Tipo de movimento para venda" );

		adicCampo( txtCodPlanConsig, 7, 25, 120, 20, "CodPlanConsig", "C�d.plan.remessa", ListaCampos.DB_FK, txtDescPlanConsig, false );
		adicDescFK( txtDescPlanConsig, 130, 25, 250, 20, "DescTipoMov", "Descri��o do planejamento para remessa" );
		txtCodPlanConsig.setFK( true );
		txtCodPlanConsig.setNomeCampo( "CodPlan" );
		adicCampo( txtCodPlanVDConsig, 7, 70, 120, 20, "CodPlanVDConsig", "C�d.plan.vendas", ListaCampos.DB_FK, txtDescPlanVDConsig, false );
		adicDescFK( txtDescPlanVDConsig, 130, 70, 250, 20, "DescTipoMov", "Descri��o do planejamento para vendas" );
		txtCodPlanVDConsig.setFK( true );
		txtCodPlanVDConsig.setNomeCampo( "CodPlan" );
		
		setListaCampos( false, "PREFERE7", "SG" );
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcTipoMovTv.setConexao( cn );
		lcTipoMovTP.setConexao( cn );
		lcPlanConsig.setConexao( cn );
		lcPlanVDConsig.setConexao( cn );
		lcCampos.carregaDados();
	}
}
