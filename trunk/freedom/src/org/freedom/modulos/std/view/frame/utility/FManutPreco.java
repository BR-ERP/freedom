/**
 * @version 12/07/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FManutPreco.java <BR>
 * 
 *                      Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 *                      modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                      A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 *                      Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 *                      sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                      Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                      Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                      Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.std.dao.DAOManutPreco;

public class FManutPreco extends FFilho implements ActionListener, RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCli = new JPanelPad( 400, 400 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodProduto = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtCodMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTab = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodClasCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProduto = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescTab = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescClasCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtMultiplic = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 10, 5 );

	private JRadioGroup<?, ?> rgTipoOper = null;

	private JRadioGroup<?, ?> rgOrigem = null;

	private JComboBoxPad cbOperador = null;

	private JButtonPad btGerar = new JButtonPad( "Gerar", Icone.novo( "btGerar.png" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.png" ) );

	private ListaCampos lcProduto = new ListaCampos( this );

	private ListaCampos lcMarca = new ListaCampos( this );

	private ListaCampos lcGrup = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private ListaCampos lcTabPreco = new ListaCampos( this );

	private ListaCampos lcClasCli = new ListaCampos( this );

	private Vector<String> vDescTipoOper = new Vector<String>();

	private Vector<String> vSelTipoOper = new Vector<String>();

	private Vector<String> vDescOrigem = new Vector<String>();

	private Vector<String> vSelOrigem = new Vector<String>();

	private Vector<String> vDescOperador = new Vector<String>();

	private Vector<String> vSelOperador = new Vector<String>();
	
	private JCheckBoxPad cbTodos = new JCheckBoxPad( "Processar todos os produtos.", "S", "N" );

	private int iCasasDec = 0;
	
	private DAOManutPreco daomanut;
	
	public FManutPreco() {

		super( false );
		
		setTitulo( "Manuten��o de Pre�os" );
		
		setAtribos( 10, 10, 430, 470 );

		Container c = getContentPane();

		vDescTipoOper.addElement( "Atualiza pre�o base" );
		vDescTipoOper.addElement( "Atualiza pre�o da tabela" );
		vSelTipoOper.addElement( "B" );
		vSelTipoOper.addElement( "P" );
		rgTipoOper = new JRadioGroup<String, String>( 1, 2, vDescTipoOper, vSelTipoOper );

		vDescOperador.addElement( "<--Selecione-->" );
		vDescOperador.addElement( "/" );
		vDescOperador.addElement( "X" );
		vSelOperador.addElement( "" );
		vSelOperador.addElement( "/" );
		vSelOperador.addElement( "*" );

		cbOperador = new JComboBoxPad( vDescOperador, vSelOperador, JComboBoxPad.TP_STRING, 1, 0 );
		cbOperador.setVlrString( "/" );

		Funcoes.setBordReq( txtMultiplic, true );
		habFiltros( "B" );

		vDescOrigem.addElement( "Custo Informado" );
		vDescOrigem.addElement( "Pre�o base" );
		vDescOrigem.addElement( "Tabela de pre�o" );
		// vDescOrigem.addElement("Custo PEPS");
		vSelOrigem.addElement( "I" );
		vSelOrigem.addElement( "B" );
		vSelOrigem.addElement("T");
		// vSelOrigem.addElement("P");
		
		rgOrigem = new JRadioGroup<String, String>( 3, 1, vDescOrigem, vSelOrigem );

		txtCodProduto.setNomeCampo( "CodProd" );
		lcProduto.add( new GuardaCampo( txtCodProduto, "CodProd", "C�d.Produto", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProduto, "DescProd", "Decri��o do produto", ListaCampos.DB_SI, false ) );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
		lcProduto.setReadOnly( true );
		txtCodProduto.setTabelaExterna( lcProduto, null );
		txtCodProduto.setFK( true );
		txtDescProduto.setListaCampos( lcProduto );

		txtCodMarca.setNomeCampo( "CodMarca" );
		lcMarca.add( new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, false ) );
		lcMarca.add( new GuardaCampo( txtDescMarca, "DescMarca", "Decri��o da marca", ListaCampos.DB_SI, false ) );
		lcMarca.montaSql( false, "MARCA", "EQ" );
		lcMarca.setReadOnly( true );
		txtCodMarca.setTabelaExterna( lcMarca, null );
		txtCodMarca.setFK( true );
		txtDescMarca.setListaCampos( lcMarca );

		txtCodGrup.setNomeCampo( "CodGrup" );
		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Decri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrup.montaSql( false, "GRUPO", "EQ" );
		lcGrup.setReadOnly( true );
		txtCodGrup.setTabelaExterna( lcGrup, null );
		txtCodGrup.setFK( true );
		txtDescGrup.setListaCampos( lcGrup );

		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, null );
		txtCodPlanoPag.setFK( true );
		txtDescPlanoPag.setListaCampos( lcPlanoPag );

		txtCodTab.setNomeCampo( "CodTab" );
		lcTabPreco.add( new GuardaCampo( txtCodTab, "CodTab", "C�d.tab.pre�o", ListaCampos.DB_PK, false ) );
		lcTabPreco.add( new GuardaCampo( txtDescTab, "DescTab", "Descri��o da tabela de pre�o", ListaCampos.DB_SI, false ) );
		lcTabPreco.montaSql( false, "TABPRECO", "VD" );
		lcTabPreco.setReadOnly( true );
		txtCodTab.setTabelaExterna( lcTabPreco, null );
		txtCodTab.setFK( true );
		txtDescTab.setListaCampos( lcTabPreco );

		txtCodClasCli.setNomeCampo( "CodClasCli" );
		lcClasCli.add( new GuardaCampo( txtCodClasCli, "CodClasCli", "C�d.c.cli", ListaCampos.DB_PK, false ) );
		lcClasCli.add( new GuardaCampo( txtDescClasCli, "DescClasCli", "Descri��o da classifica��o do cliente", ListaCampos.DB_SI, false ) );
		lcClasCli.montaSql( false, "CLASCLI", "VD" );
		lcClasCli.setReadOnly( true );
		txtCodClasCli.setTabelaExterna( lcClasCli, null );
		txtCodClasCli.setFK( true );
		txtDescClasCli.setListaCampos( lcClasCli );

		c.setLayout( new BorderLayout() );
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pinCli, BorderLayout.CENTER );

		btSair.setPreferredSize( new Dimension( 100, 30 ) );

		pnRod.setBorder( BorderFactory.createEtchedBorder() );
		pnRod.setPreferredSize( new Dimension( 300, 30 ) );
		pnRod.setLayout( new BorderLayout() );
		
		pnRod.add( btSair, BorderLayout.EAST );

		pinCli.adic( rgTipoOper, 7, 3, 395, 30 );
		pinCli.adic( new JLabelPad( " Pre�o origem: " ), 7, 35, 300, 20 );
		
		pinCli.adic( rgOrigem, 7, 55, 180, 70 );
		
		
		pinCli.adic( cbOperador			, 190	, 55	, 110	, 20, "Operador" );
		pinCli.adic( txtMultiplic		, 303	, 55	, 97	, 20, "Fator" );
		
		cbTodos.setVisible( false );
		pinCli.adic( cbTodos			, 190	, 85	, 200	, 20 );

		pinCli.adic( txtCodProduto		, 7		, 143	, 90	, 20, "C�d.produto" );
		pinCli.adic( txtDescProduto		, 100	, 143	, 300	, 20, "Descri��o do produto" );
		pinCli.adic( txtCodMarca		, 7		, 183	, 90	, 20, "C�d.marca" );
		pinCli.adic( txtDescMarca		, 100	, 183	, 300	, 20, "Descri��o da marca" );
		pinCli.adic( txtCodGrup			, 7		, 223	, 90	, 20, "C�d.grupo" );
		pinCli.adic( txtDescGrup		, 100	, 223	, 300	, 20, "Descri��o do grupo" );
		pinCli.adic( txtCodTab			, 7		, 263	, 90	, 20, "C�d.tab.pre�o" );
		pinCli.adic( txtDescTab			, 100	, 263	, 300	, 20, "Descri��o da tabela de pre�os" );
		pinCli.adic( txtCodPlanoPag		, 7		, 303	, 90	, 20, "C�d.p.pag." );
		pinCli.adic( txtDescPlanoPag	, 100	, 303	, 300	, 20, "Descri��o do plano de pagamento" );
		pinCli.adic( txtCodClasCli		, 7		, 343	, 90	, 20, "C�d.c.cli." );
		
		pinCli.adic( new JLabelPad( "Descri��o da classifica��o do cliente" ), 100, 323, 300, 20 );
		pinCli.adic( txtDescClasCli, 100, 343, 300, 20 );

		pinCli.adic( btGerar, 7, 373, 90, 30 );

		rgTipoOper.addRadioGroupListener( this );
		rgOrigem.addRadioGroupListener( this );

		btSair.addActionListener( this );
		btGerar.addActionListener( this );
	}

	private void habFiltros( String sTipo ) {

		if ( sTipo.equals( "B" ) ) {
//			Funcoes.setBordReq( txtCodPlanoPag, false );
			Funcoes.setBordReq( txtCodTab, false );
		}
		else {
//			Funcoes.setBordReq( txtCodPlanoPag, true );
			Funcoes.setBordReq( txtCodTab, true );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btGerar ) {
			if ( rgTipoOper.getVlrString().equals( "P" ) ) {
				atualizaPrecoTabela( rgTipoOper.getVlrString(), rgOrigem.getVlrString() );
			}
			else {
				atualizaPrecoBase( rgTipoOper.getVlrString(), rgOrigem.getVlrString() );
			}
		}
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		habFiltros( rgTipoOper.getVlrString() );
		
		if(rgevt.getSource() == rgOrigem) {
			
			
			cbTodos.setVisible( "B".equals( rgOrigem.getVlrString() ) );
			
			
		}
		
		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcProduto.setConexao( cn );
		lcMarca.setConexao( cn );
		lcGrup.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcTabPreco.setConexao( cn );
		lcClasCli.setConexao( cn );
		iCasasDec = getCasasDecimais();
		
		
		daomanut = new DAOManutPreco( cn );
	}

	private int getCasasDecimais() {

		int iRetorno = 2;
		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL = "SELECT CASASDECPRE FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			try {
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, Aplicativo.iCodFilial );
				rs = ps.executeQuery();
				if ( rs.next() ) {
					if ( rs.getString( "CASASDECPRE" ) != null ) {
						iRetorno = rs.getInt( "CASASDECPRE" );
					}
				}
				rs.close();
				ps.close();
				con.commit();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao verificar prefer�ncias!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		// System.out.println("Retornou setor:"+bRet);
		return iRetorno;
	}

	private void atualizaPrecoTabela( String sTipoOper, String sOrigem ) {
		
		daomanut.buscaPreco( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDPRECOPROD" ), Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDTABPRECO" ),
				txtCodTab.getVlrInteger(),  Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "FNPLANOPAG" ), txtCodPlanoPag.getVlrInteger(),  
				Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCLASCLI" ), txtCodClasCli.getVlrInteger(), 
				 Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQMARCA" ) , txtCodMarca.getVlrString(), 
				 Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQGRUPO" ), txtCodGrup.getVlrString(), rgOrigem.getVlrString(), cbOperador.getVlrString(),  txtMultiplic.getVlrBigDecimal() );
		
		/*if(sOrigem.equals( "B" ) || sOrigem.equals( "I" )){
			double deMultiplic = 0;
			String sCodProd = "";
			String sCodMarca = "";
			String sCodGrup = "";
			String sSqlConsultaProd = "";
			String sWhereConsultaProd = "";
			String sSqlConsultaPreco = "";
			String sWhereConsultaPreco = "";
			String sSqlInclusao = "";
			String sSqlAtualizar = "";
			String sOperador = "/";
			int iCodPlanoPag = 0;
			int iCodTab = 0;
			int iCodClasCli = 0;
			int iRegsAtu = 0;
			int iRegsInc = 0;
			int iRegsErr = 0;
			double dePrecoProd = 0;
			Cursor cursorAtual = getCursor();
			ResultSet rsProd = null;
			PreparedStatement psProd = null;
			ResultSet rsPreco = null;
			PreparedStatement psPreco = null;
			PreparedStatement psInclusao = null;
			PreparedStatement psAtualizar = null;
			boolean bInsert = true;
	
			try {
				
				deMultiplic = txtMultiplic.getVlrDouble().doubleValue();
				sCodProd = txtCodProduto.getVlrString().trim();
				sCodMarca = txtCodMarca.getVlrString().trim();
				sCodGrup = txtCodGrup.getVlrString().trim();
				iCodPlanoPag = txtCodPlanoPag.getVlrInteger().intValue();
				iCodTab = txtCodTab.getVlrInteger().intValue();
				iCodClasCli = txtCodClasCli.getVlrInteger().intValue();
				sOperador = cbOperador.getVlrString();
	
				if ( deMultiplic <= 0 ) {
					Funcoes.mensagemInforma( this, "Multiplicador inv�lido!" );
					txtMultiplic.requestFocus();
					return;
				}
	
				if ( !sTipoOper.equals( "P" ) ) {
					Funcoes.mensagemInforma( this, "Chamada de fun��o inv�lida!" );
					return;
				}
	
				if ( iCodTab == 0 ) {
					Funcoes.mensagemInforma( this, "Tabela de pre�os obrigat�ria!" );
					txtCodTab.requestFocus();
					return;
				}
				
				if ( sOrigem.equals( "B" ) || sOrigem.equals( "I" ) ) {
					
					sWhereConsultaProd = " AND PD.ATIVOPROD='S' AND PD.CVPROD IN ('V','A') AND TIPOPROD IN ('P','S','F') ";
	
					if ( !sCodProd.equals( "" ) ) {
						sWhereConsultaProd += " AND PD.CODPROD = '" + sCodProd + "'";
					}
	
					if ( !sCodGrup.equals( "" ) ) {
						sWhereConsultaProd += " AND PD.CODGRUP LIKE '" + sCodGrup + "%'";
					}
	
					if ( !sCodMarca.equals( "" ) ) {
						sWhereConsultaProd += " AND PD.CODMARCA='" + sCodMarca + "'";
					}
	
					sSqlConsultaProd  = "SELECT DISTINCT PD.CODEMP, PD.CODFILIAL, PD.CODPROD, PD.PRECOBASEPROD, PD.CUSTOINFOPROD, CL.CODCLASCLI ";
					if(iCodPlanoPag>0) {
						sSqlConsultaProd += ", PG.CODPLANOPAG ";
					} else {
						sSqlConsultaProd += ", null CODPLANOPAG ";					
					}
					
					sSqlConsultaProd += " FROM EQPRODUTO PD "; 
					
					
					
					
					sSqlConsultaProd += ", VDCLASCLI CL, FNPLANOPAG PG ";
					
					sSqlConsultaProd += " WHERE ";
					
					sSqlConsultaProd += "CL.CODEMP=" + Aplicativo.iCodEmp + " AND CL.CODFILIAL=" + ListaCampos.getMasterFilial( "VDCLASCLI" ) ;
					
					if ( (sOrigem.equals( "B" ) ) && ( "N".equals( cbTodos.getVlrString() ) ) )  {
						sSqlConsultaProd += " AND EXISTS (SELECT PRECOPROC FROM EQPRODUTOLOG WHERE CODEMP=PD.CODEMP AND CODFILIAL=PD.CODFILIAL AND CODPROD=PD.CODPROD AND PRECOPROC='N') ";
					}
					
					
					if(iCodClasCli>0) {
						sSqlConsultaProd += " AND CL.CODCLASCLI=" + iCodClasCli; 	
					}
						
					if(iCodPlanoPag>0) {
						sSqlConsultaProd += " AND PG.ATIVOPLANOPAG='S' AND PG.CVPLANOPAG IN ('V','A') AND PG.CODEMP=" + Aplicativo.iCodEmp + " AND PG.CODFILIAL=" + ListaCampos.getMasterFilial( "FNPLANOPAG" ) ;
						sSqlConsultaProd += " AND PG.CODPLANOPAG=" + iCodPlanoPag; 	
					}
					
					sSqlConsultaProd += sWhereConsultaProd;
	
					sWhereConsultaPreco = " CODEMPTB=" + Aplicativo.iCodEmp + " AND CODFILIALTB=" + ListaCampos.getMasterFilial( "VDTABPRECO" ) + " AND CODTAB=" + iCodTab + " AND CODEMP=? AND CODFILIAL=? AND CODPROD=?";
					
					sWhereConsultaPreco += " AND CODEMPPG=" + Aplicativo.iCodEmp + " AND CODFILIALPG=" + ListaCampos.getMasterFilial( "FNPLANOPAG" )	+ " AND CODPLANOPAG=?"; 
					
					sWhereConsultaPreco += " AND CODEMPCC=" + Aplicativo.iCodEmp + " AND CODFILIALCC=" + ListaCampos.getMasterFilial( "VDCLASCLI" ) + " AND CODCLASCLI=?"; 
					
					sSqlConsultaPreco = "SELECT CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,CODEMPTB,CODFILIALTB,CODTAB," 
						+ "CODEMPCC,CODFILIALCC,CODCLASCLI, CODEMPPG, CODFILIALPG, CODPLANOPAG,PRECOPROD " 
						+ "FROM VDPRECOPROD WHERE " + sWhereConsultaPreco;
	
					sSqlInclusao = "INSERT INTO VDPRECOPROD " 
						+ "(CODEMP,CODFILIAL,CODPROD,CODPRECOPROD,CODEMPTB,CODFILIALTB,CODTAB," 
						+ "CODEMPCC,CODFILIALCC,CODCLASCLI,CODEMPPG,CODFILIALPG,CODPLANOPAG,PRECOPROD,TIPOPRECOPROD) " 
						+ "VALUES (?,?,?,("
						+ " COALESCE( (SELECT MAX(CODPRECOPROD)+1 FROM VDPRECOPROD WHERE CODEMP=? AND " 
						+ "CODFILIAL=? AND CODPROD=?) , 1)" 
						+ "),?,?,?,?,?,?,?,?,?,?,?)";
	
					sSqlAtualizar = "UPDATE VDPRECOPROD SET PRECOPROD=?, TIPOPRECOPROD='"+sOrigem+"' WHERE " + sWhereConsultaPreco + " AND CODPRECOPROD=? ";//AND PRECOPROD!=? ";
	
				}
				else {
					Funcoes.mensagemInforma( this, "Origem de pre�o inv�lida para a opera��o!" );
					return;
				}
	
				if ( Funcoes.mensagemConfirma( this, "Confirma processamento?" ) != JOptionPane.YES_OPTION ) {
					return;
				}
	
				try {
					
					setCursor( new Cursor( Cursor.WAIT_CURSOR ) );
					
					try {
						
						psProd = con.prepareStatement( sSqlConsultaProd );
						rsProd = psProd.executeQuery();
						
						while ( rsProd.next() ) {
							
							bInsert = true;
							
							psPreco = con.prepareStatement( sSqlConsultaPreco );
							
							psPreco.setInt( 1, rsProd.getInt( "CODEMP" ) );
							psPreco.setInt( 2, rsProd.getInt( "CODFILIAL" ) );
							psPreco.setInt( 3, rsProd.getInt( "CODPROD" ) );
							if(txtCodPlanoPag.getVlrInteger().intValue() > 0){
								psPreco.setNull( 4, rsProd.getInt( "CODPLANOPAG" ) );
							} else {
								psPreco.setNull( 4, rsProd.getInt( "CODPLANOPAG" ) );
							}
							
							psPreco.setInt( 5, rsProd.getInt( "CODCLASCLI" ) );
							
							rsPreco = psPreco.executeQuery();
	
							if ( sOrigem.equals( "B" ) ) {
								dePrecoProd = rsProd.getDouble( "PRECOBASEPROD" );
							}
							else if ( sOrigem.equals( "I" ) ) {
								dePrecoProd = rsProd.getDouble( "CUSTOINFOPROD" );
							}
	
							if ( dePrecoProd == 0D ) {
								iRegsErr++;
	
								if ( Funcoes.mensagemConfirma( this, "O produto " + rsProd.getInt( "CODPROD" ) + " n�o foi atualizado pois o pre�o origem est� zerado!\n" + "Continuar o processamento?" ) != JOptionPane.YES_OPTION ) {
									break;
								}
	
							}
							else {
								if ( sOperador.equals( "/" ) )
									dePrecoProd = Funcoes.arredDouble( dePrecoProd / deMultiplic, iCasasDec );
								else
									dePrecoProd = Funcoes.arredDouble( dePrecoProd * deMultiplic, iCasasDec );
	
								while (rsPreco.next()) {
									bInsert = false;
								
									psAtualizar = con.prepareStatement( sSqlAtualizar );
									psAtualizar.setDouble( 1, dePrecoProd );
									psAtualizar.setInt( 2, rsProd.getInt( "CODEMP" ) );
									psAtualizar.setInt( 3, rsProd.getInt( "CODFILIAL" ) );
									psAtualizar.setInt( 4, rsProd.getInt( "CODPROD" ) );
									psAtualizar.setInt( 5, rsProd.getInt( "CODPLANOPAG" ) );
									psAtualizar.setInt( 6, rsProd.getInt( "CODCLASCLI" ) );
									psAtualizar.setInt( 7, rsPreco.getInt( "CODPRECOPROD" ) );
		
									iRegsAtu = iRegsAtu + psAtualizar.executeUpdate();
									psAtualizar.close();
									
									System.out.println("ATUALIZADO PRODUTO: " + rsProd.getInt( "CODPROD" ));
									System.out.print(" PLANO: " + rsProd.getInt( "CODPLANOPAG" ));
									System.out.print(" CLASSECLI: " + rsProd.getInt( "CODCLASCLI" ));
									
									
								}
								
								if(bInsert)  {
									
									psInclusao = con.prepareStatement( sSqlInclusao );
									psInclusao.setInt( 1, rsProd.getInt( "CODEMP" ) );
									psInclusao.setInt( 2, rsProd.getInt( "CODFILIAL" ) );
									psInclusao.setInt( 3, rsProd.getInt( "CODPROD" ) );
									psInclusao.setInt( 4, rsProd.getInt( "CODEMP" ) );
									psInclusao.setInt( 5, rsProd.getInt( "CODFILIAL" ) );
									psInclusao.setInt( 6, rsProd.getInt( "CODPROD" ) );
									psInclusao.setInt( 7, Aplicativo.iCodEmp );
									psInclusao.setInt( 8, ListaCampos.getMasterFilial( "VDTABPRECO" ) );
									psInclusao.setInt( 9, iCodTab );
								
									psInclusao.setInt( 10, Aplicativo.iCodEmp );
									psInclusao.setInt( 11, ListaCampos.getMasterFilial( "VDCLASCLI" ) );
									psInclusao.setInt( 12, rsProd.getInt( "CODCLASCLI" ) );
	
									if(rsProd.getInt( "CODPLANOPAG" ) > 0) {
										psInclusao.setInt( 13, Aplicativo.iCodEmp );
										psInclusao.setInt( 14, ListaCampos.getMasterFilial( "FNPLANOPAG" ) );
										psInclusao.setInt( 15, rsProd.getInt( "CODPLANOPAG" ) );
									} else {
										psInclusao.setNull( 13, Aplicativo.iCodEmp );
										psInclusao.setNull( 14, ListaCampos.getMasterFilial( "FNPLANOPAG" ) );
										psInclusao.setNull( 15, rsProd.getInt( "CODPLANOPAG" ) );
									}
									
									
									psInclusao.setDouble( 16, dePrecoProd );
									
									psInclusao.setString( 17, sOrigem );
									
									psInclusao.execute();
									psInclusao.close();
									iRegsInc++;
									
									System.out.println("INSERIDO PRODUTO: " + rsProd.getInt( "CODPROD" ));
									System.out.print(" PLANO: " + rsProd.getInt( "CODPLANOPAG" ));
									System.out.print(" CLASSECLI: " + rsProd.getInt( "CODCLASCLI" ));
									
									
								}
	
							}
	
							rsPreco.close();
							psPreco.close();
							
							
							
						}
	
						rsProd.close();
						psProd.close();
	
						con.commit();
	
						Funcoes.mensagemInforma( this, "Registros inclu�dos: " + iRegsInc + "\n" + "Registros atualizados: " + iRegsAtu + "\n" + "Registros com erro: " + iRegsErr + "\n" + "Total processados: " + ( iRegsAtu + iRegsInc ) );
	
					} catch ( SQLException err ) {
						err.printStackTrace();
						Funcoes.mensagemErro( this, "Erro atualizando tabela!\n" + err.getMessage(), true, con, err );
					}
				} finally {
					setCursor( cursorAtual );
				}
			} finally {
				deMultiplic = 0;
				sCodMarca = null;
				sCodGrup = null;
				sSqlConsultaProd = null;
				sWhereConsultaProd = null;
				sSqlConsultaPreco = null;
				sWhereConsultaPreco = null;
				sSqlInclusao = null;
				sSqlAtualizar = null;
				sOperador = null;
				iCodPlanoPag = 0;
				iCodTab = 0;
				iCodClasCli = 0;
				iRegsAtu = 0;
				iRegsInc = 0;
				cursorAtual = null;
				rsProd = null;
				psProd = null;
				rsPreco = null;
				psPreco = null;
				psInclusao = null;
				psAtualizar = null;
				bInsert = false;
				dePrecoProd = 0;
			}
		} else {*/
		
		
	}

	private void atualizaPrecoBase( String sTipoOper, String sOrigem ) {

		double deMultiplic = 0;
		String sCodProd = "";
		String sCodMarca = "";
		String sCodGrup = "";
		String sSqlAtualizar = "";
		String sSqlConsultaProd = "";
		String sWhereConsultaProd = "";
		String sOperador = "/";
		int iCodPlanoPag = 0;
		int iCodTab = 0;
		int iCodClasCli = 0;
		int iRegsAtu = 0;
		int iRegsInc = 0;
		int iRegsErr = 0;
		double dePrecoProd = 0;
		Cursor cursorAtual = getCursor();
		ResultSet rsProd = null;
		PreparedStatement psProd = null;
		PreparedStatement psAtualizar = null;

		try {
			deMultiplic = txtMultiplic.getVlrDouble().doubleValue();
			sCodProd = txtCodProduto.getVlrString().trim();
			sCodMarca = txtCodMarca.getVlrString().trim();
			sCodGrup = txtCodGrup.getVlrString().trim();
			iCodPlanoPag = txtCodPlanoPag.getVlrInteger().intValue();
			iCodTab = txtCodTab.getVlrInteger().intValue();
			iCodClasCli = txtCodClasCli.getVlrInteger().intValue();
			sOperador = cbOperador.getVlrString();

			if ( deMultiplic <= 0 ) {
				Funcoes.mensagemInforma( this, "Multiplicador inv�lido!" );
				txtMultiplic.requestFocus();
				return;
			}

			if ( !sTipoOper.equals( "B" ) ) {
				Funcoes.mensagemInforma( this, "Chamada de fun��o inv�lida!" );
				return;
			}

			if ( !sOrigem.equals( "I" ) && !sOrigem.equals( "B" ) ) {
				Funcoes.mensagemInforma( this, "Origem de pre�o inv�lida para a opera��o!" );

				return;
			}

			sWhereConsultaProd = " WHERE CVPROD IN ('V','A') AND TIPOPROD IN ('P','S','F') ";

			if ( !sCodProd.equals( "" ) ) {
				sWhereConsultaProd += " AND CODPROD='" + sCodProd + "'";
			}

			if ( !sCodGrup.equals( "" ) ) {
				sWhereConsultaProd += " AND CODGRUP LIKE '" + sCodGrup + "%'";
			}

			if ( !sCodMarca.equals( "" ) ) {
				sWhereConsultaProd += " AND CODMARCA='" + sCodMarca + "'";
			}

			sSqlConsultaProd = "SELECT CODEMP, CODFILIAL, CODPROD, PRECOBASEPROD, CUSTOINFOPROD " + " FROM EQPRODUTO " + sWhereConsultaProd;

			if ( Funcoes.mensagemConfirma( this, "Confirma processamento?" ) != JOptionPane.YES_OPTION ) {
				return;
			}

			sSqlAtualizar = " UPDATE EQPRODUTO " + " SET PRECOBASEPROD = ? " + " WHERE CODEMP = ? AND CODFILIAL = ? AND CODPROD = ? ";

			try {
				setCursor( new Cursor( Cursor.WAIT_CURSOR ) );

				try {
					psProd = con.prepareStatement( sSqlConsultaProd );
					rsProd = psProd.executeQuery();

					while ( rsProd.next() ) {

						if ( sOrigem.equals( "B" ) ) {
							dePrecoProd = rsProd.getDouble( "PRECOBASEPROD" );
						}
						else if ( sOrigem.equals( "I" ) ) {
							dePrecoProd = rsProd.getDouble( "CUSTOINFOPROD" );
						}

						if ( dePrecoProd == 0D ) {
							iRegsErr++;

							if ( Funcoes.mensagemConfirma( this, "O produto " + rsProd.getInt( "CODPROD" ) + " n�o foi atualizado pois o pre�o origem est� zerado!\n" + "Continuar o processamento?" ) != JOptionPane.YES_OPTION ) {
								break;
							}

						}
						else {

							if ( sOperador.equals( "/" ) )
								dePrecoProd = Funcoes.arredDouble( dePrecoProd / deMultiplic, iCasasDec );
							else
								dePrecoProd = Funcoes.arredDouble( dePrecoProd * deMultiplic, iCasasDec );

							psAtualizar = con.prepareStatement( sSqlAtualizar );
							psAtualizar.setDouble( 1, dePrecoProd );
							psAtualizar.setInt( 2, rsProd.getInt( "CODEMP" ) );
							psAtualizar.setInt( 3, rsProd.getInt( "CODFILIAL" ) );
							psAtualizar.setInt( 4, rsProd.getInt( "CODPROD" ) );

							iRegsAtu = iRegsAtu + psAtualizar.executeUpdate();

							psAtualizar.close();

						}

					}

					rsProd.close();
					psProd.close();

					con.commit();

					Funcoes.mensagemInforma( this, "Registros inclu�dos: " + iRegsInc + "\n" + "Registros atualizados: " + iRegsAtu + "\n" + "Registros com erro: " + iRegsErr + "\n" + "Total processados: " + ( iRegsAtu + iRegsInc ) );
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro atualizando produto!\n" + err.getMessage(), true, con, err );
				}
			} finally {
				setCursor( cursorAtual );
			}
		} finally {
			deMultiplic = 0;
			sCodMarca = null;
			sCodGrup = null;
			sSqlConsultaProd = null;
			sWhereConsultaProd = null;
			sOperador = null;
			iCodPlanoPag = 0;
			iCodTab = 0;
			iCodClasCli = 0;
			iRegsAtu = 0;
			iRegsInc = 0;
			cursorAtual = null;
			rsProd = null;
			psProd = null;
			dePrecoProd = 0;
		}

	}

}
