/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FConsRmaItem.java <BR>
 *                       Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 *                       modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                       A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 *                       Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 *                       sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                       Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                       Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 *                       de acordo com os termos da LPG-PC <BR>
 * <BR>
 *                       Formul�rio de consulta de RMA.
 */

package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.gms.view.frame.crud.detail.FRma;

public class FConsRmaItem extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 0, 215 );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 0, 4 ) );

	private JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSeqOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtEmiOP = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFabOP = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodProdOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRefProdOP = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JCheckBoxPad cbPendentes = new JCheckBoxPad( "Itens pendentes", "S", "N" );

	private JCheckBoxPad cbAprovadas = new JCheckBoxPad( "Itens aprovadas", "S", "N" );

	private JCheckBoxPad cbExpedidas = new JCheckBoxPad( "Itens expedidas", "S", "N" );

	private JCheckBoxPad cbCanceladas = new JCheckBoxPad( "Itens canceladas", "S", "N" );

	private JTablePad tab = new JTablePad();

	private ImageIcon imgCancelada = Icone.novo( "clVencido.gif" );

	private ImageIcon imgExpedida = Icone.novo( "clPago.gif" );

	private ImageIcon imgAprovada = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgPendente = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgColuna = null;

	private JButtonPad btBusca = new JButtonPad( "Buscar", Icone.novo( "btPesquisa.gif" ) );

	private JButtonPad btPrevimp = new JButtonPad( "Imprimir", Icone.novo( "btPrevimp.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JScrollPane spnTab = new JScrollPane( tab );

	private ListaCampos lcAlmox = new ListaCampos( this, "AM" );

	private ListaCampos lcUsuario = new ListaCampos( this, "" );

	private ListaCampos lcOP = new ListaCampos( this, "OF" );

	private ListaCampos lcSeqOP = new ListaCampos( this, "OF" );

	private ListaCampos lcCC = new ListaCampos( this, "CC" );

	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	boolean bAprovaParcial = false;

	boolean bExpede = false;

	boolean bAprova = false;

	private Vector<?> vSitRMA = new Vector<Object>();

	public FConsRmaItem() {

		super( false );
		setTitulo( "Pesquisa Requisi��es de material" );
		setAtribos( 10, 10, 900, 600 );

		txtDtIni.setRequerido( true );
		txtDtFim.setRequerido( true );

		txtCodAlmoxarife.setNomeCampo( "CodAlmox" );
		txtCodAlmoxarife.setFK( true );

		lcAlmox.add( new GuardaCampo( txtCodAlmoxarife, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, null, false ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmoxarife, "DescAlmox", "Desc.almox;", ListaCampos.DB_SI, null, false ) );
		lcAlmox.setQueryCommit( false );
		lcAlmox.setReadOnly( true );

		txtDescAlmoxarife.setSoLeitura( true );
		txtCodAlmoxarife.setTabelaExterna( lcAlmox, null );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );

		txtCodOP.setNomeCampo( "CodOP" );
		txtCodOP.setFK( true );

		lcOP.add( new GuardaCampo( txtCodOP, "CodOP", "C�d. OP.", ListaCampos.DB_PK, null, false ) );
		lcOP.add( new GuardaCampo( txtDtEmiOP, "DTEMITOP", "Data de emiss�o", ListaCampos.DB_SI, null, false ) );
		lcOP.add( new GuardaCampo( txtDtFabOP, "DTFABROP", "Data de fabrica��o", ListaCampos.DB_SI, null, false ) );
		lcOP.add( new GuardaCampo( txtCodProdOP, "CodProd", "C�d.prod.", ListaCampos.DB_SI, null, false ) );
		lcOP.add( new GuardaCampo( txtRefProdOP, "RefProd", "Refer�ncia", ListaCampos.DB_SI, null, false ) );
		lcOP.setQueryCommit( false );
		lcOP.setReadOnly( true );

		txtDtEmiOP.setSoLeitura( true );
		txtDtFabOP.setSoLeitura( true );
		txtCodProdOP.setSoLeitura( true );
		txtRefProdOP.setSoLeitura( true );
		txtCodOP.setTabelaExterna( lcOP, null );
		lcOP.montaSql( false, "OP", "PP" );

		txtSeqOP.setNomeCampo( "SeqOP" );
		txtSeqOP.setFK( true );

		lcSeqOP.add( new GuardaCampo( txtSeqOP, "SeqOP", "Seq. OP.", ListaCampos.DB_PK, null, false ) );
		lcSeqOP.setQueryCommit( false );
		lcSeqOP.setReadOnly( true );

		txtSeqOP.setTabelaExterna( lcSeqOP, null );
		lcSeqOP.montaSql( false, "OP", "PP" );

		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, null, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, null, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia", ListaCampos.DB_SI, null, false ) );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );

		txtDescProd.setSoLeitura( true );
		txtRefProd.setSoLeitura( true );
		txtCodProd.setTabelaExterna( lcProd, null );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

		txtCodUsu.setNomeCampo( "IDUSU" );
		txtCodUsu.setFK( true );

		lcUsuario.add( new GuardaCampo( txtCodUsu, "IDUSU", "ID usuario", ListaCampos.DB_PK, null, false ) );
		lcUsuario.add( new GuardaCampo( txtNomeUsu, "NOMEUSU", "Nome do usuario", ListaCampos.DB_SI, null, false ) );
		lcUsuario.setQueryCommit( false );
		lcUsuario.setReadOnly( true );

		txtNomeUsu.setSoLeitura( true );
		txtCodUsu.setTabelaExterna( lcUsuario, null );
		lcUsuario.montaSql( false, "USUARIO", "SG" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "C�d.cc.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano.cc.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descri��o do centro de custo", ListaCampos.DB_SI, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC, null );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );

		Container c = getTela();
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pnCli, BorderLayout.CENTER );
		pnCli.add( pinCab, BorderLayout.NORTH );
		pnCli.add( spnTab, BorderLayout.CENTER );

		btSair.setPreferredSize( new Dimension( 100, 30 ) );

		pnLegenda.add( new JLabelPad( "Cancelada", imgCancelada, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Aprovada", imgAprovada, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Expedida", imgExpedida, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Pendente", imgPendente, SwingConstants.CENTER ) );

		pnRod.add( pnLegenda, BorderLayout.WEST );
		pnRod.add( btSair, BorderLayout.EAST );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbLinha2 = new JLabelPad();
		lbLinha2.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbStatus = new JLabelPad( " Filtrar:" );
		lbStatus.setOpaque( true );

		pinCab.adic( new JLabelPad( "Per�odo:" ), 7, 5, 50, 20 );
		pinCab.adic( txtDtIni, 7, 25, 95, 20 );
		pinCab.adic( new JLabelPad( "At�" ), 111, 25, 27, 20 );
		pinCab.adic( txtDtFim, 139, 25, 95, 20 );

		pinCab.adic( new JLabelPad( "C�d.usu." ), 237, 5, 70, 20 );
		pinCab.adic( txtCodUsu, 237, 25, 80, 20 );
		pinCab.adic( new JLabelPad( "Nome do usu�rio" ), 320, 5, 153, 20 );
		pinCab.adic( txtNomeUsu, 320, 25, 163, 20 );

		pinCab.adic( new JLabelPad( "C�d.prod." ), 7, 45, 80, 20 );
		pinCab.adic( txtCodProd, 7, 65, 80, 20 );
		pinCab.adic( new JLabelPad( "Descri��o do produto" ), 90, 45, 200, 20 );
		pinCab.adic( txtDescProd, 90, 65, 200, 20 );
		pinCab.adic( new JLabelPad( "C�d. OP." ), 293, 45, 80, 20 );
		pinCab.adic( txtCodOP, 293, 65, 80, 20 );
		pinCab.adic( new JLabelPad( "Seq. OP." ), 376, 45, 80, 20 );
		pinCab.adic( txtSeqOP, 376, 65, 80, 20 );

		pinCab.adic( new JLabelPad( "C�d.c.c." ), 7, 85, 70, 20 );
		pinCab.adic( txtCodCC, 7, 105, 140, 20 );
		pinCab.adic( new JLabelPad( "Centro de custo" ), 150, 85, 410, 20 );
		pinCab.adic( txtDescCC, 150, 105, 180, 20 );

		pinCab.adic( lbStatus, 15, 125, 50, 18 );
		pinCab.adic( lbLinha2, 7, 135, 373, 66 );
		pinCab.adic( cbPendentes, 15, 147, 170, 20 );
		pinCab.adic( cbAprovadas, 15, 172, 170, 20 );
		pinCab.adic( cbExpedidas, 195, 147, 180, 20 );
		pinCab.adic( cbCanceladas, 195, 172, 180, 20 );

		pinCab.adic( btBusca, 392, 135, 130, 30 );
		pinCab.adic( btPrevimp, 392, 170, 130, 30 );

		txtDtIni.setVlrDate( new Date() );
		txtDtFim.setVlrDate( new Date() );

		tab.adicColuna( "" );// 0
		tab.adicColuna( "C�d.rma." );// 1
		tab.adicColuna( "C�d.prod." );// 2
		tab.adicColuna( "Descri��o do produto" );// 3
		tab.adicColuna( "C�d.OP." );// 4
		tab.adicColuna( "Aprov." );// 5
		tab.adicColuna( "Exp." );// 6
		tab.adicColuna( "Dt.req." );// 7
		tab.adicColuna( "Qt.req." );// 8
		tab.adicColuna( "Dt.aprov." );// 9
		tab.adicColuna( "Qt.aprov." );// 10
		tab.adicColuna( "Dt.exp." );// 11
		tab.adicColuna( "Qt.exp." );// 12
		tab.adicColuna( "Saldo" );// 13

		tab.setTamColuna( 12, 0 );//0
		tab.setTamColuna( 70, 1 );//1
		tab.setTamColuna( 70, 2 );//2
		tab.setTamColuna( 200, 3 );//3
		tab.setTamColuna( 70, 4 );//4
		tab.setTamColuna( 40, 5 );//5
		tab.setTamColuna( 40, 6 );//6
		tab.setTamColuna( 60, 7 );//7
		tab.setTamColuna( 60, 8 );//8
		tab.setTamColuna( 75, 9 );//9
		tab.setTamColuna( 60, 10 );//10
		tab.setTamColuna( 60, 11 );//11
		tab.setTamColuna( 60, 12 );//12
		tab.setTamColuna( 60, 13 );//13
		
		tab.setRowHeight( 20 );

		btBusca.addActionListener( this );
		btPrevimp.addActionListener( this );

		tab.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getSource() == tab && mevt.getClickCount() == 2 )
					abreRma();
			}
		} );
		btSair.addActionListener( this );

	}

	private void habCampos() {

		getAprova();
		if ( !bExpede ) {
			if ( bAprova ) {
				if ( bAprovaParcial ) {
					txtCodCC.setVlrString( Aplicativo.strCodCCUsu );
					txtAnoCC.setVlrString( Aplicativo.strAnoCCUsu );
					txtCodCC.setNaoEditavel( true );
					lcUsuario.setWhereAdic( "CODCC='" + Aplicativo.strCodCCUsu + "' AND ANOCC=" + Aplicativo.strAnoCCUsu );
				}
				else {
					txtCodCC.setNaoEditavel( false );

				}
				txtCodUsu.setNaoEditavel( false );
			}
			else {
				txtCodUsu.setVlrString( Aplicativo.strUsuario );
				txtCodCC.setVlrString( Aplicativo.strCodCCUsu );
				txtAnoCC.setVlrString( Aplicativo.strAnoCCUsu );

				txtCodUsu.setNaoEditavel( true );
				txtCodCC.setNaoEditavel( true );
				lcUsuario.carregaDados();
				lcCC.carregaDados();
			}
		}
	}

	/**
	 * Carrega os valores para a tabela de consulta. Este m�todo � executado ap�s carregar o ListaCampos da tabela.
	 */
	private void carregaTabela() {

		String where = "";
		boolean usaOr = false;
		boolean usaWhere = false;
		boolean usuario = ( !txtCodUsu.getVlrString().trim().equals( "" ) );
		// boolean almoxarifado = (txtCodAlmoxarife.getVlrInteger().intValue() > 0);
		boolean almoxarifado = false;
		boolean CC = ( !txtCodCC.getVlrString().trim().equals( "" ) );
		String sCodOp = txtCodOP.getVlrString();
		String sSeqOp = txtSeqOP.getVlrString();
		String sCodProd = txtCodProd.getVlrString();

		if ( cbPendentes.getVlrString().equals( "S" ) ) {
			usaWhere = true;
			where = " SitItRma ='PE'";
		}
		if ( cbAprovadas.getVlrString().equals( "S" ) ) {
			if ( where.trim().equals( "" ) ) {
				where = " (SitAprovItRma ='AP' OR SitAprovItRma ='AT')";
			}
			else {
				where = where + " OR (SitAprovItRma ='AP' OR SitAprovItRma ='AT')";
				usaOr = true;
			}
			usaWhere = true;
		}
		if ( cbExpedidas.getVlrString().equals( "S" ) ) {
			if ( where.trim().equals( "" ) ) {
				where = " (SitExpItRma ='EP' OR SitExpItRma ='ET')";
			}
			else {
				where = where + " OR (SitExpItRma ='EP' OR SitExpItRma ='ET')";
				usaOr = true;
			}
			usaWhere = true;
		}
		if ( cbCanceladas.getVlrString().equals( "S" ) ) {
			if ( where.trim().equals( "" ) ) {
				where = " SitItRma ='CA'";
			}
			else {
				where = where + " OR SitItRma ='CA'";
				usaOr = true;
			}
			usaWhere = true;
		}
		if ( usaWhere && usaOr )
			where = " AND (" + where + ")";
		else if ( usaWhere )
			where = " AND " + where;
		else
			where = " AND SitItRma='PE'";

		if ( sCodOp.length() > 0 )
			where += " AND R.CODOP = '" + sCodOp + "'";

		if ( sSeqOp.length() > 0 )
			where += " AND R.SEQOP = '" + sSeqOp + "'";

		if ( sCodProd.length() > 0 )
			where += " AND IT.CODPROD = '" + sCodProd + "'";

		if ( almoxarifado )
			where += " AND IT.CODALMOX=? AND IT.CODEMPAM=? AND IT.CODFILIALAM=? ";

		if ( CC )
			where += " AND R.ANOCC=? AND R.CODCC=? AND R.CODEMPCC=? AND R.CODFILIALCC=? ";

		if ( usuario )
			where += " AND (R.IDUSU=?) ";

		String sSQL = "SELECT R.CODRMA, IT.CODPROD,IT.REFPROD,PD.DESCPROD,IT.SITITRMA," 
					+ "IT.SITAPROVITRMA,IT.SITEXPITRMA,IT.DTINS,IT.DTAPROVITRMA,IT.DTAEXPITRMA," 
					+ "IT.QTDITRMA,IT.QTDAPROVITRMA,IT.QTDEXPITRMA,PD.SLDPROD,R.CODOP " 
					+ "FROM EQRMA R, EQITRMA IT, EQPRODUTO PD "
					+ "WHERE R.CODEMP=IT.CODEMP AND R.CODFILIAL=IT.CODFILIAL AND R.CODRMA=IT.CODRMA " 
					+ "AND PD.CODEMP=IT.CODEMP AND PD.CODFILIAL=IT.CODFILIAL AND PD.CODPROD=IT.CODPROD AND PD.TIPOPROD<>'S' " 
					+ "AND ((IT.DTAPROVITRMA BETWEEN ? AND ?) OR  (R.DTAREQRMA BETWEEN ? AND ?)) " 
					+ where;

		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			int param = 1;
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );

			if ( almoxarifado ) {
				ps.setInt( param++, txtCodAlmoxarife.getVlrInteger().intValue() );
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, Aplicativo.iCodFilial );
			}

			if ( CC ) {
				ps.setInt( param++, txtAnoCC.getVlrInteger().intValue() );
				ps.setString( param++, txtCodCC.getVlrString() );
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, Aplicativo.iCodFilial );
			}

			if ( usuario ) {
				ps.setString( param++, txtCodUsu.getVlrString() );
			}

			ResultSet rs = ps.executeQuery();

			int iLin = 0;

			tab.limpa();
			vSitRMA = new Vector<Object>();
			while ( rs.next() ) {
				tab.adicLinha();

				String sitRMA = rs.getString( 5 );
				String sitAprovRMA = rs.getString( 6 );
				String sitExpRMA = rs.getString( 7 );
				if ( sitRMA.equalsIgnoreCase( "PE" ) ) {
					imgColuna = imgPendente;
				}
				else if ( sitRMA.equalsIgnoreCase( "CA" ) ) {
					imgColuna = imgCancelada;
				}
				else if ( sitExpRMA.equals( "EP" ) || sitExpRMA.equals( "ET" ) ) {
					imgColuna = imgExpedida;
				}
				else if ( sitAprovRMA.equals( "AP" ) || sitAprovRMA.equals( "AT" ) ) {
					imgColuna = imgAprovada;
				}

				tab.setValor( imgColuna, iLin, 0 );// SitItRma
				tab.setValor( new Integer( rs.getInt( 1 ) ), iLin, 1 );// CodRma
				
				tab.setValor( rs.getString( 2 ) == null ? "" : rs.getString( 2 ) + "", iLin, 2 );// CodProd
				tab.setValor( rs.getString( 4 ) == null ? "" : rs.getString( 4 ).trim() + "", iLin, 3 );// DescProd
				tab.setValor( rs.getString( 15 ) == null ? "" : rs.getString( 15 ) + "", iLin, 4 );// Cod OP
				tab.setValor( rs.getString( 6 ) == null ? "" : rs.getString( 6 ) + "", iLin, 5 );// SitAprov
				tab.setValor( rs.getString( 7 ) == null ? "" : rs.getString( 7 ) + "", iLin, 6 );// SitExp
				tab.setValor( rs.getString( 8 ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( 8 ) ) + "", iLin, 7 );// Dt Req
				tab.setValor( rs.getString( 9 ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( 9 ) ) + "", iLin, 9 );// Dt Aprov
				tab.setValor( rs.getString( 10 ) == null ? "" : StringFunctions.sqlDateToStrDate( rs.getDate( 10 ) ) + "", iLin, 11 );// Dt Exp
				tab.setValor( rs.getString( 11 ) == null ? "" : rs.getString( 11 ) + "", iLin, 8 );// Qtd Req
				tab.setValor( rs.getString( 12 ) == null ? "" : rs.getString( 12 ) + "", iLin, 10 );// Qtd Aprov
				tab.setValor( rs.getString( 13 ) == null ? "" : rs.getString( 13 ) + "", iLin, 12 );// Qdt Exp
				tab.setValor( rs.getString( 14 ) == null ? "" : rs.getString( 14 ) + "", iLin, 13 );// Saldo Prod

				iLin++;

			}

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela EQRMA!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		BigDecimal bTotalLiq = new BigDecimal( "0" );
		boolean bImpCot = false;

		/*
		 * bImpCot = Funcoes.mensagemConfirma(this, "Deseja imprimir informa��es de cota��es de pre�o?") == 0 ? true : false;
		 */

		try {
			imp.limpaPags();
			for ( int iLin = 0; iLin < tab.getNumLinhas(); iLin++ ) {
				if ( imp.pRow() == 0 ) {
					imp.montaCab();
					imp.setTitulo( "Relat�rio de Requisi��es de material" );
					imp.addSubTitulo( "Relat�rio de Requisi��es de material" );
					imp.impCab( 136, true );
					// imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say( imp.pRow() + 0, 0, "| Rma." );
					imp.say( imp.pRow() + 0, 15, "| Emiss�o" );
					imp.say( imp.pRow() + 0, 29, "| Situa��o" );
					imp.say( imp.pRow() + 0, 45, "| Motivo." );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );

					if ( bImpCot ) {
						imp.say( imp.pRow() + 0, 0, "| Nro. Pedido" );
						imp.say( imp.pRow() + 0, 15, "| Nro. Nota" );
						imp.say( imp.pRow() + 0, 29, "| Data Fat." );
						imp.say( imp.pRow() + 0, 41, "| " );
						imp.say( imp.pRow() + 0, 56, "| " );
						imp.say( imp.pRow() + 0, 87, "| Vlr. Item Fat." );
						imp.say( imp.pRow() + 0, 105, "| " );
						imp.say( imp.pRow() + 0, 124, "| " );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );

					}

					imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );

				}

				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "|" + tab.getValor( iLin, 1 ) );
				imp.say( imp.pRow() + 0, 15, "| " + tab.getValor( iLin, 2 ) );
//				imp.say( imp.pRow() + 0, 29, "| " + vSitRMA.elementAt( iLin ).toString() );
				String sMotivo = "" + tab.getValor( iLin, 3 );
				imp.say( imp.pRow() + 0, 45, "| " + sMotivo.substring( 0, sMotivo.length() > 89 ? 89 : sMotivo.length() ).trim() );
				imp.say( imp.pRow() + 0, 135, "| " );

				if ( bImpCot ) {
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 2, "|" + tab.getValor( iLin, 2 ) );
					imp.say( imp.pRow() + 0, 15, "|" + tab.getValor( iLin, 3 ) );
					imp.say( imp.pRow() + 0, 29, "|" );
					imp.say( imp.pRow() + 0, 41, "|" );
					imp.say( imp.pRow() + 0, 56, "|" );
					imp.say( imp.pRow() + 0, 87, "|" + tab.getValor( iLin, 12 ) );
					imp.say( imp.pRow() + 0, 105, "|" );
					imp.say( imp.pRow() + 0, 124, "|" );
					imp.say( imp.pRow() + 0, 135, "|" );
				}

				if ( tab.getValor( iLin, 9 ) != null ) {
				//	bTotalLiq = bTotalLiq.add( new BigDecimal( Funcoes.strCurrencyToDouble( "" + tab.getValor( iLin, 9 ) ) ) );
				}

				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
			imp.eject();

			imp.fechaGravacao();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de or�amentos!\n" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	private void abreRma() {

		int iRma = ( (Integer) tab.getValor( tab.getLinhaSel(), 1 ) ).intValue();
		if ( fPrim.temTela( "Requisi��o de material" ) == false ) {
			FRma tela = new FRma();
			fPrim.criatela( "Requisi��o de material", tela, con );
			tela.exec( iRma );
		}
	}

	private void getAprova() {

		String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVRMAUSU,ALMOXARIFEUSU " 
					+ "FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? " + "AND IDUSU=?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGUSUARIO" ) );
			ps.setString( 3, Aplicativo.strUsuario );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				String sAprova = rs.getString( "APROVRMAUSU" );
				String sExpede = rs.getString( "ALMOXARIFEUSU" );
				if ( sAprova != null ) {
					if ( !sAprova.equals( "ND" ) ) {
						if ( sAprova.equals( "TD" ) )
							bAprova = true;
						else if ( ( Aplicativo.strCodCCUsu.equals( rs.getString( "CODCC" ) ) ) && ( Aplicativo.iCodEmp == rs.getInt( "CODEMPCC" ) ) && ( ListaCampos.getMasterFilial( "FNCC" ) == rs.getInt( "CODFILIALCC" ) ) && ( sAprova.equals( "CC" ) ) ) {
							bAprova = true;
							bAprovaParcial = true;
						}
					}
				}
				if ( sExpede != null ) {
					if ( sExpede.equals( "S" ) ) {
						bExpede = true;
					}
					else {
						bExpede = false;
					}
				}
			}
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		if ( evt.getSource() == btBusca ) {
			if ( txtDtIni.getVlrString().length() < 10 )
				Funcoes.mensagemInforma( this, "Digite a data inicial!" );
			else if ( txtDtFim.getVlrString().length() < 10 )
				Funcoes.mensagemInforma( this, "Digite a data final!" );
			else
				carregaTabela();
			if ( evt.getSource() == btPrevimp ) {
				imprimir( true );
			}
		}
		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}

	}

	private int buscaVlrPadrao() {

		int iRet = 0;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() )
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage() );
		}

		return iRet;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcAlmox.setConexao( cn );
		lcOP.setConexao( cn );
		lcSeqOP.setConexao( cn );
		lcProd.setConexao( cn );
		lcUsuario.setConexao( cn );
		lcCC.setConexao( cn );
		lcCC.setWhereAdic( "NIVELCC=10 AND ANOCC=" + buscaVlrPadrao() );
		habCampos();
	}
}
