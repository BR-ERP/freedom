/**
 * @version 02/03/2009 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FPrefereGeral.java <BR>
 * 
 *                        Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 *                        vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 *                        A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 *                        Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 *                        o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 *                        Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                        Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 *                        de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                        Tela de cadastro das prefer�ncias do sistema. Esse cadastro � utilizado para parametrizar o sistema de acordo com as necessidades espec�ficas da empresa.
 * 
 */

package org.freedom.modulos.gms.view.frame.crud.tabbed;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.exceptions.ExceptionCarregaDados;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.gms.business.object.TipoMov;
import org.freedom.modulos.gms.business.object.TipoRecMerc;
import org.freedom.modulos.gms.view.frame.crud.detail.FTipoExpedicao;
import org.freedom.modulos.gms.view.frame.crud.detail.FTipoRecMerc;
import org.freedom.modulos.std.view.frame.crud.tabbed.FTransp;

public class FPrefereGMS extends FTabDados {

	private static final long serialVersionUID = 1L;

	/****************
	 * Lista Campos *
	 ****************/

	private ListaCampos lcTipoRecMercRP = new ListaCampos( this, "TR" );

	private ListaCampos lcTipoRecMercCM = new ListaCampos( this, "CM" );

	private ListaCampos lcTipoMovCP = new ListaCampos( this, "TC" );

	private ListaCampos lcTipoRecMercOS = new ListaCampos( this, "TO" );
	
	private ListaCampos lcTipoExped = new ListaCampos( this, "TE" );
	
	private ListaCampos lcTipoMovDS = new ListaCampos( this, "DS" );
	
	private ListaCampos lcProdServ = new ListaCampos( this, "SE" );

	private ListaCampos lcCodTran = new ListaCampos( this, "TN" );
	
	private ListaCampos lcCodPagPP = new ListaCampos( this, "PP" );
	
	private JCheckBoxPad cbUsaPrecoPecaServ = new JCheckBoxPad("Usar pre�o da pe�a no or�amento de servi�os", "S", "N");
	
	private JCheckBoxPad cbSincTicket = new JCheckBoxPad("Sincronizar sequ�ncia do ticket Recebimento/Expedi��o", "S", "N");

	/****************
	 * Fields *
	 ****************/

	private JTextFieldPad txtCodTipoRecMercRP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoRecMercRP = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoExped = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoExped = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTipoRecMercCM = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoRecMercCM = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoMovTC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoMovTC = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTipoRecMercOS = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoRecMercOS = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTipoMovDS = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoMovDS = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
    
	
	/****************
	 * Paineis *
	 ****************/

	private JPanelPad pinGeral = new JPanelPad();

	private JPanelPad pinOS = new JPanelPad();
	
	private JPanelPad pinCompra = new JPanelPad();
	
	private JPanelPad pinColeta = new JPanelPad();
	
	private JTextFieldPad txtCodProdServ = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRefProdServ = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDescProdServ = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	
	public FPrefereGMS() {

		super();

		setTitulo( "Prefer�ncias GMS" );
		setAtribos( 30, 40, 440, 320 );
		lcCampos.setMensInserir( false );

		montaListaCampos();
		montaTela();
		
	}

	private void montaListaCampos() {

		/***************************************
		 * Tipo Rec. Mercadoria Com pesagem *
		 **************************************/

		lcTipoRecMercRP.add( new GuardaCampo( txtCodTipoRecMercRP, "CodTipoRecMerc", "C�d.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMercRP.add( new GuardaCampo( txtDescTipoRecMercRP, "DescTipoRecMerc", "Tipo de recebimento para pesagem", ListaCampos.DB_SI, false ) );
		lcTipoRecMercRP.setWhereAdic( "TIPORECMERC='" + TipoRecMerc.TIPO_RECEBIMENTO_PESAGEM.getValue() + "'" );
		lcTipoRecMercRP.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMercRP.setQueryCommit( false );
		lcTipoRecMercRP.setReadOnly( true );
		txtCodTipoRecMercRP.setTabelaExterna( lcTipoRecMercRP, FTipoRecMerc.class.getCanonicalName() );

		/***************************************
		 * Tipo Rec. Mercadoria Coleta *
		 **************************************/

		lcTipoRecMercCM.add( new GuardaCampo( txtCodTipoRecMercCM, "CodTipoRecMerc", "C�d.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMercCM.add( new GuardaCampo( txtDescTipoRecMercCM, "DescTipoRecMerc", "Tipo de recebimento com coleta", ListaCampos.DB_SI, false ) );
		lcTipoRecMercCM.setWhereAdic( "TIPORECMERC='" + TipoRecMerc.TIPO_COLETA_DE_MATERIAIS.getValue() + "'" );
		lcTipoRecMercCM.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMercCM.setQueryCommit( false );
		lcTipoRecMercCM.setReadOnly( true );
		txtCodTipoRecMercCM.setTabelaExterna( lcTipoRecMercCM, FTipoRecMerc.class.getCanonicalName() );

		/***************************************
		 * Tipo de movimento de compra *
		 **************************************/

		lcTipoMovCP.add( new GuardaCampo( txtCodTipoMovTC, "CodTipoMov", "C�d.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoMovCP.add( new GuardaCampo( txtDescTipoMovTC, "DescTipoMov", "Tipo de movimento para compra", ListaCampos.DB_SI, false ) );
		lcTipoMovCP.setWhereAdic( "ESTIPOMOV='" + TipoMov.ENTRADA.getValue() + "'" );
		lcTipoMovCP.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovCP.setQueryCommit( false );
		lcTipoMovCP.setReadOnly( true );
		txtCodTipoMovTC.setTabelaExterna( lcTipoMovCP, FTipoMov.class.getCanonicalName() );

		/***************************************
		 * Tipo de recepcao de mercadoria para ordem de sevico *
		 **************************************/

		lcTipoRecMercOS.add( new GuardaCampo( txtCodTipoRecMercOS, "CodTipoRecMerc", "C�d.Tipo.Rec.", ListaCampos.DB_PK, false ) );
		lcTipoRecMercOS.add( new GuardaCampo( txtDescTipoRecMercOS, "DescTipoRecMerc", "Tipo de recep��o e mercadorias para Ordem de Servi�o", ListaCampos.DB_SI, false ) );
		lcTipoRecMercCM.setWhereAdic( "TIPORECMERC='" + TipoRecMerc.TIPO_ENTRADA_CONCERTO.getValue() + "'" );
		lcTipoRecMercOS.montaSql( false, "TIPORECMERC", "EQ" );
		lcTipoRecMercOS.setQueryCommit( false );
		lcTipoRecMercOS.setReadOnly( true );
		txtCodTipoRecMercOS.setTabelaExterna( lcTipoRecMercOS, FTipoRecMerc.class.getCanonicalName() );
		
		/*****************************************************************
		 * Tipo de movimento para devolu��o de pe�as consertadas servi�o *
		 *****************************************************************/

		lcTipoMovDS.add( new GuardaCampo( txtCodTipoMovDS, "CodTipoMov", "C�d.Tipo.Mov.", ListaCampos.DB_PK, false ) );
		lcTipoMovDS.add( new GuardaCampo( txtDescTipoMovDS, "DescTipoMov", "Tipo de movimento para devolu��o de conserto", ListaCampos.DB_SI, false ) );
		lcTipoMovDS.setWhereAdic( "ESTIPOMOV='" + TipoMov.SAIDA.getValue() + "'" );
		lcTipoMovDS.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMovDS.setQueryCommit( false );
		lcTipoMovDS.setReadOnly( true );
		txtCodTipoMovDS.setTabelaExterna( lcTipoMovDS, FTipoMov.class.getCanonicalName() );
		
		/*****************************************************************
		 * Servi�o padr�o *
		 *****************************************************************/
		lcProdServ.add( new GuardaCampo( txtCodProdServ, "Codprod", "C�d.prod.", ListaCampos.DB_PK, txtDescProdServ, false ) );
		lcProdServ.add( new GuardaCampo( txtRefProdServ, "refprod", "referencia", ListaCampos.DB_SI, false ) );
		lcProdServ.add( new GuardaCampo( txtDescProdServ, "Descprod", "Descri�ao do produto", ListaCampos.DB_SI, false ) );
		lcProdServ.setWhereAdic( "TIPOPROD='S'" );
		lcProdServ.montaSql( false, "PRODUTO", "EQ" );
		lcProdServ.setQueryCommit( false );
		lcProdServ.setReadOnly( true );
		txtCodProdServ.setTabelaExterna( lcProdServ, FProduto.class.getCanonicalName() );
		txtCodProdServ.setNomeCampo( "codprod" );
		
		/******************************************
		 * Tipo de expedi��o de produtos acabados *
		 ******************************************/

		lcTipoExped.add( new GuardaCampo( txtCodTipoExped, "CodTipoExped", "C�d.Tipo.Exp.", ListaCampos.DB_PK, false ) );
		lcTipoExped.add( new GuardaCampo( txtDescTipoExped, "DescTipoExped", "Tipo de expedi��o de produtos", ListaCampos.DB_SI, false ) );
		lcTipoExped.montaSql( false, "TIPOEXPEDICAO", "EQ" );
		lcTipoExped.setQueryCommit( false );
		lcTipoExped.setReadOnly( true );
		txtCodTipoExped.setTabelaExterna( lcTipoExped, FTipoExpedicao.class.getCanonicalName() );
		
		/***************************************
		 * C�digo da transportadora pad�o *
		 **************************************/

		lcCodTran.add( new GuardaCampo( txtCodTran, "CodTran", "C�d.Tran.", ListaCampos.DB_PK, false ) );
		lcCodTran.add( new GuardaCampo( txtNomeTran, "NomeTran", "C�digo da transportadora pad�o", ListaCampos.DB_SI, false ) );
		lcCodTran.montaSql( false, "TRANSP", "VD" );
		lcCodTran.setQueryCommit( false );
		lcCodTran.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcCodTran, FTransp.class.getCanonicalName() );
		
		/***************************************
		 * C�digo do plano de pagamento padr�o para coleta de entrada *
		 **************************************/

		lcCodPagPP.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.Plano.Pag.", ListaCampos.DB_PK, false ) );
		lcCodPagPP.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "C�digo do plano de pagamento padr�o para coleta de entrada", ListaCampos.DB_SI, false ) );
		lcCodPagPP.montaSql( false, "PLANOPAG", "FN" );
		lcCodPagPP.setQueryCommit( false );
		lcCodPagPP.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcCodPagPP, FTipoMov.class.getCanonicalName() );

	}

	private void montaTela() {

		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );

		adicCampo( txtCodTipoRecMercRP, 7, 20, 70, 20, "CodTipoRecMerc", "C�d.Tp.Rec.", ListaCampos.DB_FK, txtDescTipoRecMercRP, false );
		adicDescFK( txtDescTipoRecMercRP, 80, 20, 330, 20, "DescTipoRecMerc", "Tipo de recebimento padr�o para pesagem" );
		txtCodTipoRecMercRP.setFK( true );
		txtCodTipoRecMercRP.setNomeCampo( "CodTipoRecMerc" );

		adicCampo( txtCodTipoMovTC, 7, 60, 70, 20, "CodTipoMovTC", "C�d.Tp.Mov.", ListaCampos.DB_FK, txtDescTipoMovTC, false );
		adicDescFK( txtDescTipoMovTC, 80, 60, 330, 20, "DescTipoMov", "Tipo de movimento padr�o para compra" );
		txtCodTipoMovTC.setFK( true );
		txtCodTipoMovTC.setNomeCampo( "CodTipoMov" );

		adicCampo( txtCodTipoExped, 7, 100, 70, 20, "CodTipoExped", "C�d.Tp.Exp.", ListaCampos.DB_FK, txtDescTipoExped, false );
		adicDescFK( txtDescTipoExped, 80, 100, 330, 20, "DescTipoExped", "Tipo de expedi��o padr�o" );
		txtCodTipoExped.setFK( true );
		txtCodTipoExped.setNomeCampo( "CodTipoExped" );

		adicDB( cbSincTicket, 7, 130, 380, 20, "sincticket", "", false );
		
		setPainel( pinOS );
		adicTab( "Ordem de servi�o", pinOS );

		adicCampo( txtCodTipoRecMercOS, 7, 20, 70, 20, "CodTipoRecMercOS", "C�d.Tp.Rec.", ListaCampos.DB_FK, txtDescTipoRecMercOS, false );
		adicDescFK( txtDescTipoRecMercOS, 80, 20, 330, 20, "DescTipoRecMerc", "Tipo de recebimento padr�o para Ordem de Servi�o" );
		txtCodTipoRecMercOS.setFK( true );
		txtCodTipoRecMercOS.setNomeCampo( "CodTipoRecMerc" );
		
		adicCampo( txtCodTipoMovDS, 7, 60, 70, 20, "CodTipoMovDS", "C�d.Tp.Mov.", ListaCampos.DB_FK, txtDescTipoMovDS, false );
		adicDescFK( txtDescTipoMovDS, 80, 60, 330, 20, "DescTipoMov", "Tipo de movimento para devolu��o de conserto" );
		txtCodTipoMovDS.setFK( true );
		txtCodTipoMovDS.setNomeCampo( "CodTipoMov" );

		adicCampo( txtCodProdServ, 7, 100, 70, 20, "CodProdSe", "C�d.Serv.", ListaCampos.DB_FK, txtDescProdServ, false );
		adicDescFK( txtDescProdServ, 80, 100, 330, 20, "DescProd", "Descri��o do servi�o padr�o" );
		txtCodProdServ.setFK( true );
		txtCodProdServ.setNomeCampo( "CodProd" );
		
		adicDB( cbUsaPrecoPecaServ, 7, 130, 350, 20, "usaprecopecaserv", "", false );		
		
		setPainel( pinCompra );
		adicTab( "Compra", pinCompra );
		
		setPainel( pinColeta );
		adicTab( "Coleta", pinColeta );
		
		adicCampo( txtCodTipoRecMercCM, 7, 20, 70, 20, "CodTipoRecMercCM", "C�d.Tp.Rec.", ListaCampos.DB_FK, txtDescTipoRecMercCM, false );
		adicDescFK( txtDescTipoRecMercCM, 80, 20, 330, 20, "DescTipoRecMercCM", "Tipo de recebimento padr�o para coleta" );
		txtCodTipoRecMercCM.setFK( true );
		txtCodTipoRecMercCM.setNomeCampo( "CodTipoRecMerc" );
		
		adicCampo( txtCodTran, 7, 60, 70, 20, "CodTran", "C�d.Tran.", ListaCampos.DB_FK, txtNomeTran, false );
		adicDescFK( txtNomeTran , 80, 60, 330, 20, "NomeTran", "Nome da Transportadora" );
		txtCodTran.setFK( true );
		txtCodTran.setNomeCampo( "CodTran" );
		
		adicCampo( txtCodPlanoPag, 7, 100, 70, 20, "CodPlanoPag", "C�d.Plano.Pag.", ListaCampos.DB_FK, txtDescPlanoPag , false );
		adicDescFK( txtDescPlanoPag , 80, 100, 330, 20, "DescPlanoPag", "C�digo do plano de pagamento padr�o para coleta de entrada" );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		
		setListaCampos( false, "PREFERE8", "SG" );
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );
	}

	public void setConexao( DbConnection cn ) { // throws ExceptionSetConexao {

		super.setConexao( cn );

		lcTipoRecMercRP.setConexao( cn );
		lcTipoRecMercCM.setConexao( cn );
		lcTipoRecMercOS.setConexao( cn );
		lcTipoMovCP.setConexao( cn );
		lcTipoMovDS.setConexao( cn );
		lcProdServ.setConexao( cn );
		lcTipoExped.setConexao( cn );
		lcCodTran.setConexao( cn );
		lcCodPagPP.setConexao( cn );

		try {
			lcCampos.carregaDados();
		}

		catch ( Exception e ) {
			new ExceptionCarregaDados( "Erro ao carregar dados do lista campos " + lcCampos.getName() );
		}

	}
}
