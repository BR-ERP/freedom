/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FConsOrc.java <BR>
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
 * Formul�rio de consulta de or�amento.
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FConsOrc extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 0, 240 );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private Tabela tab = new Tabela();

	private JScrollPane spnTab = new JScrollPane( tab );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeComis = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescCaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtValorMenor = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtValorMaior = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JCheckBoxPad cbAberto = new JCheckBoxPad( "Aberto", "S", "N" );

	private JCheckBoxPad cbCompleto = new JCheckBoxPad( "Completo", "S", "N" );

	private JCheckBoxPad cbLiberado = new JCheckBoxPad( "Liberado", "S", "N" );

	private JCheckBoxPad cbFaturado = new JCheckBoxPad( "Faturado", "S", "N" );

	private JRadioGroup<String, String> gbVenc;

	private JButton btBusca = new JButton( "Buscar", Icone.novo( "btPesquisa.gif" ) );

	private JButton btPrevimp = new JButton( "Imprimir", Icone.novo( "btPrevimp.gif" ) );

	private JButton btConsVenda = new JButton( Icone.novo( "btSaida.gif" ) );

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcComis = new ListaCampos( this );

	private ListaCampos lcCaixa = new ListaCampos( this );

	private ListaCampos lcUsu = new ListaCampos( this );
	

	public FConsOrc() {

		super( false );
		setTitulo( "Pesquisa Or�amentos" );
		setAtribos( 20, 20, 625, 500 );

		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "D" );
		vVals.addElement( "V" );
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "Data de Emiss�o" );
		vLabs.addElement( "Data de Validade" );
		gbVenc = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );

		txtDtIni.setRequerido( true );
		txtDtFim.setRequerido( true );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, null, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, null, false ) );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

		lcComis.add( new GuardaCampo( txtCodComis, "CodVend", "C�d.comis.", ListaCampos.DB_PK, null, false ) );
		lcComis.add( new GuardaCampo( txtNomeComis, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, null, false ) );
		txtCodComis.setTabelaExterna( lcComis );
		txtCodComis.setNomeCampo( "CodVend" );
		txtCodComis.setFK( true );
		lcComis.setReadOnly( true );
		lcComis.montaSql( false, "VENDEDOR", "VD" );

		lcCaixa.add( new GuardaCampo( txtCodCaixa, "CodCaixa", "C�d.caixa", ListaCampos.DB_PK, null, false ) );
		lcCaixa.add( new GuardaCampo( txtDescCaixa, "DescCaixa", "Descri��o do caixa", ListaCampos.DB_SI, null, false ) );
		txtCodCaixa.setTabelaExterna( lcCaixa );
		txtCodCaixa.setNomeCampo( "CodCaixa" );
		txtCodCaixa.setFK( true );
		lcCaixa.setReadOnly( true );
		lcCaixa.montaSql( false, "CAIXA", "PV" );

		lcUsu.add( new GuardaCampo( txtCodUsu, "IDUsu", "id usu.", ListaCampos.DB_PK, null, false ) );
		lcUsu.add( new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome do usuario", ListaCampos.DB_SI, null, false ) );
		txtCodUsu.setTabelaExterna( lcUsu );
		txtCodUsu.setNomeCampo( "CodCli" );
		txtCodUsu.setFK( true );
		lcUsu.setReadOnly( true );
		lcUsu.montaSql( false, "USUARIO", "SG" );

		Container c = getTela();
		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pnCli, BorderLayout.CENTER );
		pnCli.add( pinCab, BorderLayout.NORTH );
		pnCli.add( spnTab, BorderLayout.CENTER );
		
		adicBotaoSair();
		
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );		
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		pinCab.adic( periodo, 20, 5, 60, 20 );
		pinCab.adic( borda, 7, 15, 266, 45 );		
		pinCab.adic( txtDtIni, 20, 30, 100, 20 );
		pinCab.adic( new JLabel( "at�", SwingConstants.CENTER ), 120, 30, 40, 20 );
		pinCab.adic( txtDtFim, 160, 30, 100, 20 );

		pinCab.adic( new JLabel( "Ordenada por", SwingConstants.CENTER ), 17, 60, 100, 20 );
		pinCab.adic( gbVenc, 7, 80, 266, 30 );
		
		JLabel valores = new JLabel( "Valores entre", SwingConstants.CENTER );
		valores.setOpaque( true );		
		JLabel borda1 = new JLabel();
		borda1.setBorder( BorderFactory.createEtchedBorder() );
		pinCab.adic( valores, 20, 110, 100, 20 );
		pinCab.adic( borda1, 7, 120, 266, 45 );		
		pinCab.adic( txtValorMenor, 20, 135, 100, 20 );
		pinCab.adic( new JLabel( "at�", SwingConstants.CENTER ), 120, 135, 40, 20 );
		pinCab.adic( txtValorMaior, 160, 135, 100, 20 );

		JLabel status = new JLabel( "Status", SwingConstants.CENTER );
		status.setOpaque( true );		
		JLabel borda2 = new JLabel();
		borda2.setBorder( BorderFactory.createEtchedBorder() );
		pinCab.adic( status, 15, 165, 50, 18 );
		pinCab.adic( borda2, 7, 175, 266, 50 );
		pinCab.adic( cbAberto, 20, 180, 80, 20 );
		pinCab.adic( cbCompleto, 20, 200, 80, 20 );
		pinCab.adic( cbLiberado, 150, 180, 80, 20 );
		pinCab.adic( cbFaturado, 150, 200, 80, 20 );

		pinCab.adic( new JLabel( "C�d.cli." ), 280, 10, 77, 20 );
		pinCab.adic( txtCodCli, 280, 30, 77, 20 );
		pinCab.adic( new JLabel( "Raz�o social do cliente" ), 360, 10, 220, 20 );
		pinCab.adic( txtNomeCli, 360, 30, 220, 20 );

		pinCab.adic( new JLabel( "C�d.comis." ), 280, 50, 77, 20 );
		pinCab.adic( txtCodComis, 280, 70, 77, 20 );
		pinCab.adic( new JLabel( "Nome do comissionado" ), 360, 50, 220, 20 );
		pinCab.adic( txtNomeComis, 360, 70, 220, 20 );

		pinCab.adic( new JLabel( "C�d.caixa" ), 280, 90, 77, 20 );
		pinCab.adic( txtCodCaixa, 280, 110, 77, 20 );
		pinCab.adic( new JLabel( "Descri��o do caixa" ), 360, 90, 220, 20 );
		pinCab.adic( txtDescCaixa, 360, 110, 220, 20 );

		pinCab.adic( new JLabel( "id.usuario" ), 280, 130, 77, 20 );
		pinCab.adic( txtCodUsu, 280, 150, 77, 20 );
		pinCab.adic( new JLabel( "Nome do usuario" ), 360, 130, 220, 20 );
		pinCab.adic( txtNomeUsu, 360, 150, 220, 20 );

		pinCab.adic( btBusca, 280, 185, 145, 30 );
		pinCab.adic( btPrevimp, 437, 185, 145, 30 );

		txtDtIni.setVlrDate( new Date() );
		txtDtFim.setVlrDate( new Date() );

		tab.adicColuna( "St" );
		tab.adicColuna( "Orc." );
		tab.adicColuna( "Ped." );
		tab.adicColuna( "NF" );
		tab.adicColuna( "C�d.cli." );
		tab.adicColuna( "Raz�o social do cliente" );

		tab.adicColuna( "Data" );
		tab.adicColuna( "Validade" );
		tab.adicColuna( "Autoriz." );
		tab.adicColuna( "Vlr.It.Orc." );
		tab.adicColuna( "Cidade" );
		tab.adicColuna( "Fone" );
		tab.adicColuna( "Vlr.it.Fat." );

		tab.setTamColuna( 30, 0 );
		tab.setTamColuna( 40, 1 );
		tab.setTamColuna( 40, 2 );
		tab.setTamColuna( 40, 3 );

		tab.setTamColuna( 50, 4 );
		tab.setTamColuna( 180, 5 );

		tab.setTamColuna( 90, 6 );
		tab.setTamColuna( 90, 7 );
		tab.setTamColuna( 90, 8 );
		tab.setTamColuna( 90, 9 );
		tab.setTamColuna( 100, 10 );
		tab.setTamColuna( 100, 11 );
		tab.setTamColuna( 100, 12 );

		tab.addMouseListener( new MouseAdapter() {
			public void mouseClicked( MouseEvent mevt ) {
				if ( mevt.getSource() == tab && mevt.getClickCount() == 2 )
					abreOrc();
			}
		} );

		btBusca.addActionListener( this );
		btPrevimp.addActionListener( this );
		btConsVenda.addActionListener( this );

		btConsVenda.setToolTipText( "Busca venda." );

	}

	/**
	 * Carrega os valores para a tabela de consulta. Este m�todo � executado ap�s carregar o ListaCampos da tabela.
	 */

	private void carregaTabela() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sSQLFatura = null;
		String sWhere = "";
		boolean bUsaOr = false;
		boolean bUsaWhere = false;
		boolean bCli = ( txtCodCli.getVlrInteger().intValue() > 0 );

		if ( cbAberto.getVlrString().equals( "S" ) ) {
			bUsaWhere = true;
			sWhere = " STATUSORC IN ('OA','*') ";
		}

		if ( cbCompleto.getVlrString().equals( "S" ) ) {
			if ( sWhere.trim().equals( "" ) )
				sWhere = " STATUSORC ='OC'";
			else {
				sWhere = sWhere + " OR STATUSORC ='OC'";
				bUsaOr = true;
			}
			bUsaWhere = true;
		}

		if ( cbLiberado.getVlrString().equals( "S" ) ) {
			if ( sWhere.trim().equals( "" ) )
				sWhere = " STATUSORC ='OL' AND IT.APROVITORC='S' AND IT.EMITITORC='N'";
			else {
				sWhere = sWhere + " OR STATUSORC ='OL'";
				bUsaOr = true;
			}
			bUsaWhere = true;
		}

		if ( cbFaturado.getVlrString().equals( "S" ) ) {
			if ( sWhere.trim().equals( "" ) )
				sWhere = " (STATUSORC ='OV' OR IT.EMITITORC='S')";
			else {
				sWhere = sWhere + " OR STATUSORC ='OV'";
				bUsaOr = true;
			}
			bUsaWhere = true;
		}

		if ( bUsaWhere && bUsaOr )
			sWhere = " AND (" + sWhere + ")";
		else if ( bUsaWhere )
			sWhere = " AND " + sWhere;
		else
			sWhere = " AND STATUSORC=''";

		if ( gbVenc.getVlrString().equals( "V" ) )
			sWhere += " AND DTVENCORC BETWEEN ? AND ?";
		else
			sWhere += " AND DTORC BETWEEN ? AND ?";

		if ( bCli )
			sWhere += " AND O.CODCLI=? AND O.CODEMPCL=? AND O.CODFILIALCL=? ";

		if ( !txtValorMenor.getVlrString().equals( "" ) ) {
			if ( !txtValorMaior.getVlrString().equals( "" ) ) {
				sWhere += " AND IT.VLRLIQITORC >= " + txtValorMenor.getVlrBigDecimal();
				sWhere += " AND IT.VLRLIQITORC<= " + txtValorMaior.getVlrBigDecimal();
			}
		}

		sSQL = "SELECT O.STATUSORC,O.CODORC,O.DTORC,O.DTVENCORC, " + "O.CODCLI,CL.NOMECLI,CL.FONECLI , IT.VENCAUTORIZORC,IT.NUMAUTORIZORC," + "CL.CIDCLI,IT.APROVITORC,IT.VLRLIQITORC, " + "VO.CODVENDA, VD.DOCVENDA , IVD.VLRLIQITVENDA " + "FROM  VDORCAMENTO O,VDCLIENTE CL, VDITORCAMENTO IT "
				+ "LEFT OUTER JOIN VDVENDAORC VO ON " + "VO.CODORC=IT.CODORC AND VO.CODEMPOR=IT.CODEMP AND VO.CODFILIALOR=IT.CODFILIAL AND " + "VO.CODITORC=IT.CODITORC " + "LEFT OUTER JOIN VDVENDA VD ON " + "VD.CODEMP=VO.CODEMP AND VD.CODFILIAL=VO.CODFILIAL AND "
				+ "VD.TIPOVENDA=VO.TIPOVENDA AND VD.CODVENDA=VO.CODVENDA " + "LEFT OUTER JOIN VDITVENDA IVD ON " + "IVD.CODEMP=VD.CODEMP AND IVD.CODFILIAL=VD.CODFILIAL AND " + "IVD.TIPOVENDA=VD.TIPOVENDA AND IVD.CODVENDA=VD.CODVENDA AND " + "IVD.CODITVENDA=VO.CODITVENDA " + "WHERE O.CODEMP=? AND "
				+ "O.CODFILIAL=? AND O.TIPOORC='O' AND " + "IT.CODORC=O.CODORC AND IT.CODEMP=O.CODEMP AND IT.CODFILIAL=O.CODFILIAL AND " + "IT.TIPOORC=O.TIPOORC AND " + "CL.CODEMP=O.CODEMPCL AND CL.CODFILIAL=O.CODFILIALCL AND " + "CL.CODCLI=O.CODCLI" + sWhere + " ORDER BY 2";

		// System.out.println("Query completa:"+sSQL);
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );

			if ( bCli ) {
				ps.setInt( 5, txtCodCli.getVlrInteger().intValue() );
				ps.setInt( 6, Aplicativo.iCodEmp );
				ps.setInt( 7, lcCli.getCodFilial() );
			}

			rs = ps.executeQuery();
			int iLin = 0;

			tab.limpa();
			while ( rs.next() ) {
				tab.adicLinha();
				tab.setValor( rs.getString( 1 ) + "", iLin, 0 );
				tab.setValor( new Integer( rs.getInt( 2 ) ), iLin, 1 );
				tab.setValor( rs.getString( 13 ) == null ? "-" : rs.getString( 13 ) + "", iLin, 2 );
				tab.setValor( rs.getString( 14 ) == null ? "-" : rs.getString( 14 ) + "", iLin, 3 );
				tab.setValor( rs.getInt( 5 ) + "", iLin, 4 );
				tab.setValor( rs.getString( 6 ) != null ? rs.getString( 6 ) : "", iLin, 5 );
				tab.setValor( Funcoes.sqlDateToStrDate( rs.getDate( 3 ) ), iLin, 6 );
				tab.setValor( Funcoes.sqlDateToStrDate( rs.getDate( 4 ) ), iLin, 7 );
				tab.setValor( Funcoes.strDecimalToStrCurrency( 2, rs.getString( 12 ) != null ? rs.getString( 12 ) : "" ), iLin, 9 );
				tab.setValor( rs.getString( 9 ) != null ? rs.getString( 9 ) : "", iLin, 8 );
				tab.setValor( rs.getString( 10 ) != null ? rs.getString( 10 ) : "", iLin, 10 );
				tab.setValor( rs.getString( 7 ) != null ? rs.getString( 7 ).trim() : "", iLin, 11 );
				// tab.setValor(Funcoes.strDecimalToStrCurrency(2, rs.getString(15) != null ? rs.getString(15) : ""), iLin, 12);
				iLin++;
			}
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela VDOR�AMENTO!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sSQLFatura = null;
			sWhere = "";
		}
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		BigDecimal bTotalLiq = new BigDecimal( "0" );

		try {
			int linPag = imp.verifLinPag() - 1;
			boolean bImpFat = false;

			bImpFat = Funcoes.mensagemConfirma( this, "Deseja imprimir informa��es de faturamento do or�amento?" ) == 0 ? true : false;

			imp.montaCab();
			imp.setTitulo( "Relat�rio de Or�amentos" );
			imp.limpaPags();

			for ( int iLin = 0; iLin < tab.getNumLinhas(); iLin++ ) {
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, false );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 1, "| N.ORC." );
					imp.say( imp.pRow() + 0, 15, "| Emiss�o" );
					imp.say( imp.pRow() + 0, 29, "| Validade." );
					imp.say( imp.pRow() + 0, 41, "| Autoriz." );
					imp.say( imp.pRow() + 0, 56, "| Nome" );
					imp.say( imp.pRow() + 0, 87, "| Vlr. Item Orc." );
					imp.say( imp.pRow() + 0, 105, "| Cidade" );
					imp.say( imp.pRow() + 0, 124, "| Telefone   " );
					imp.say( imp.pRow() + 0, 136, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );

					if ( bImpFat ) {
						imp.say( imp.pRow() + 0, 1, "| Nro. Pedido" );
						imp.say( imp.pRow() + 0, 15, "| Nro. Nota" );
						imp.say( imp.pRow() + 0, 29, "| Data Fat." );
						imp.say( imp.pRow() + 0, 41, "| " );
						imp.say( imp.pRow() + 0, 56, "| " );
						imp.say( imp.pRow() + 0, 87, "| Vlr. Item Fat." );
						imp.say( imp.pRow() + 0, 105, "| " );
						imp.say( imp.pRow() + 0, 124, "| " );
						imp.say( imp.pRow() + 0, 137, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					}

					imp.say( imp.pRow() + 0, 0, Funcoes.replicate( "-", 136 ) );
				}

				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 2, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 1 ) + "", 8 ) );
				imp.say( imp.pRow() + 0, 15, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 6 ) + "", 8 ) );
				imp.say( imp.pRow() + 0, 29, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 7 ) + "", 10 ) );
				imp.say( imp.pRow() + 0, 41, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 8 ) + "", 13 ) );
				imp.say( imp.pRow() + 0, 56, "|" + Funcoes.copy( tab.getValor( iLin, 5 ) + "", 25 ) );
				imp.say( imp.pRow() + 0, 87, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 9 ) + "", 15 ) );
				imp.say( imp.pRow() + 0, 105, "|" + Funcoes.copy( tab.getValor( iLin, 10 ) + "", 10 ) );
				imp.say( imp.pRow() + 0, 124, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 11 ) + "", 12 ) );
				imp.say( imp.pRow() + 0, 136, "|" );

				if ( bImpFat ) {
					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 2, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 2 ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 15, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 3 ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 29, "|" );
					imp.say( imp.pRow() + 0, 41, "|" );
					imp.say( imp.pRow() + 0, 56, "|" );
					imp.say( imp.pRow() + 0, 87, "|" + Funcoes.alinhaDir( tab.getValor( iLin, 12 ) + "", 15 ) );
					imp.say( imp.pRow() + 0, 105, "|" );
					imp.say( imp.pRow() + 0, 124, "|" );
					imp.say( imp.pRow() + 0, 137, "|" );
				}

				imp.say( imp.pRow() + 1, 0, "+ " + Funcoes.replicate( "-", 133 ) );
				imp.say( imp.pRow() + 0, 136, "+" );

				if ( tab.getValor( iLin, 9 ) != null )
					bTotalLiq = bTotalLiq.add( new BigDecimal( Funcoes.strCurrencyToDouble( "" + tab.getValor( iLin, 9 ) ) ) );

				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, "|" );
			imp.say( imp.pRow() + 0, 58, "   Valor total de itens:   " + Funcoes.strDecimalToStrCurrency( 11, 2, "     " + bTotalLiq ) );
			imp.say( imp.pRow() + 0, 136, "|" );

			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, Funcoes.replicate( "=", 136 ) );
			imp.eject();

			imp.fechaGravacao();

			if ( !con.getAutoCommit() )
				con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro na consulta � tabela de or�amentos!\n" + err.getMessage(), true, con, err );
		} finally {
			bTotalLiq = null;
		}

		if ( bVisualizar )
			imp.preview( this );
		else
			imp.print();
	}

	private void abreOrc() {

		int iCodOrc = ( (Integer) tab.getValor( tab.getLinhaSel(), 1 ) ).intValue();
		if ( fPrim.temTela( "Orcamento" ) == false ) {
			FOrcamento tela = new FOrcamento();
			fPrim.criatela( "Orcamento", tela, con );
			tela.exec( iCodOrc );
		}
	}

	private void consVenda() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sTipoVenda = "V";
		int iCodVenda = 0;
		int iLin = tab.getLinhaSel();

		if ( iLin >= 0 ) {
			if ( !tab.getValor( iLin, 0 ).toString().equals( "OV" ) )
				return;
			try {
				sSQL = "SELECT CODVENDA, TIPOVENDA FROM VDVENDAORC " + "WHERE CODORC=? AND CODEMPOR=? AND CODFILIALOR=?";
				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Integer.parseInt( tab.getValor( iLin, 1 ).toString() ) );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
				rs = ps.executeQuery();
				if ( rs.next() ) {
					iCodVenda = rs.getInt( "CodVenda" );
					sTipoVenda = rs.getString( "TipoVenda" );
				}
				rs.close();
				ps.close();
				if ( !con.getAutoCommit() )
					con.commit();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao buscar a venda!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
				return;
			} finally {
				ps = null;
				rs = null;
				sSQL = null;
				sTipoVenda = null;
			}
			DLConsultaVenda dl = new DLConsultaVenda( this, con, iCodVenda, sTipoVenda );
			dl.setVisible( true );
			dl.dispose();
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btConsVenda ) {
			consVenda();
		}
		else if ( evt.getSource() == btBusca ) {
			if ( txtDtIni.getVlrString().length() < 10 ) {
				Funcoes.mensagemInforma( this, "Digite a data inicial!" );
			}
			else if ( txtDtFim.getVlrString().length() < 10 ) {
				Funcoes.mensagemInforma( this, "Digite a data final!" );
			}
			else {
				carregaTabela();
			}
		}
		else if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( con );
		lcComis.setConexao( con );
		lcCaixa.setConexao( con );
		lcUsu.setConexao( con );
	}
}
