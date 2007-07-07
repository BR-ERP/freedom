/**
 * @version 01/03/2007 <BR>
 * @author Setpoint Inform�tica Ltda./RobsonSanchez/Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRemFBN.java <BR>
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
 * Tela de remessa de arquivo, contendo os dados dos clientes e recebimentos, para o banco selecionado.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public abstract class FRemFBN extends FFilho implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	protected static final int COL_SEL = 0;

	protected static final int COL_RAZCLI = 1;

	protected static final int COL_CODCLI = 2;

	protected static final int COL_CODREC = 3;

	protected static final int COL_DOCREC = 4;

	protected static final int COL_NRPARC = 5;

	protected static final int COL_VLRAPAG = 6;

	protected static final int COL_DTREC = 7;

	protected static final int COL_DTVENC = 8;

	protected static final int COL_AGENCIACLI = 9;

	protected static final int COL_IDENTCLI = 10;

	protected static final int COL_SITREM = 11;

	protected static final int COL_SITRET = 12;

	protected static final int COL_STIPOFEBRABAN = 13;

	protected static final int COL_TIPOREMCLI = 14;

	protected static final int COL_PESSOACLI = 15;

	protected static final int COL_CPFCLI = 16;
	
	protected static final int COL_CNPJCLI = 17;
	
	protected static final String TIPO_FEBRABAN_SIACC = "01";
	
	protected static final String TIPO_FEBRABAN_CNAB = "02";
	
	protected final String TIPO_FEBRABAN;

	private JPanelPad panelRodape = null;

	private final JPanelPad panelRemessa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFiltros = new JPanelPad();

	private final JPanelPad panelTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFuncoes = new JPanelPad();

	private final JPanelPad panelStatus = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelImp = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 0, 0 ) );

	private final JPanelPad pnImp = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );

	protected final Tabela tab = new Tabela();

	protected final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	protected final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	protected final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	protected final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	protected final JRadioGroup rgData;
	
	protected final JRadioGroup rgSitRemessa;

	private final JButton btCarrega = new JButton( "Buscar", Icone.novo( "btExecuta.gif" ) );

	private final JButton btExporta = new JButton( "Exportar", Icone.novo( "btSalvar.gif" ) );

	private final JButton btSelTudo = new JButton( Icone.novo( "btTudo.gif" ) );

	private final JButton btSelNada = new JButton( Icone.novo( "btNada.gif" ) );
	
	private final JButton btImprime = new JButton( Icone.novo( "btImprime.gif" ) );
	
	private final JButton btVisImp = new JButton( Icone.novo( "btPrevimp.gif" ) );

	protected final JLabel lbStatus = new JLabel();

	protected final ListaCampos lcBanco = new ListaCampos( this );

	protected Map<Enum, Object> prefs = new HashMap<Enum, Object>();
	
	private final Vector<String> vVals = new Vector<String>();
	
	private final Vector<String> vLabs = new Vector<String>();

	private final Vector<String> vValsRem = new Vector<String>();
	
	private final Vector<String> vLabsRem = new Vector<String>();
	
	protected String where = "";
	
	
	public FRemFBN( final String tipofebraban ) {

		super( false );
		setTitulo( "Manuten��o de contas a receber" );
		setAtribos( 10, 10, 780, 540 );
		
		this.TIPO_FEBRABAN = tipofebraban;

		vVals.addElement( "E" );
		vVals.addElement( "V" );
		vLabs.addElement( "Emiss�o" );
		vLabs.addElement( "Vencimento" );
		rgData = new JRadioGroup( 2, 1, vLabs, vVals );
		
		vValsRem.addElement( "00" );
		vValsRem.addElement( "01" );
		vValsRem.addElement( "02" );
		vValsRem.addElement( "99" );
		vLabsRem.addElement( "N�o exportados" );
		vLabsRem.addElement( "Exportados" );
		vLabsRem.addElement( "Rejeitados" );
		vLabsRem.addElement( "Todos" );
		
		rgSitRemessa = new JRadioGroup( 2, 2, vLabsRem, vValsRem );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setListaCampos( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setRequerido( true );
		txtNomeBanco.setListaCampos( lcBanco );

		montaTela();

		tab.adicColuna( "Sel." );
		tab.adicColuna( "Raz�o social do cliente" );
		tab.adicColuna( "C�d.cli." );
		tab.adicColuna( "C�d.rec." );
		tab.adicColuna( "Doc" );
		tab.adicColuna( "Nro.Parc." );
		tab.adicColuna( "Valor" );
		tab.adicColuna( "Emiss�o" );
		tab.adicColuna( "Vencimento" );
		tab.adicColuna( "Ag�ncia" );
		tab.adicColuna( "Indentifica��o" );
		tab.adicColuna( "Sit. rem." );
		tab.adicColuna( "Sit. ret." );
		tab.adicColuna( "Subtipo" );
		tab.adicColuna( "Tp.r.cli." );
		tab.adicColuna( "Pessoa" );
		tab.adicColuna( "C.P.F." );
		tab.adicColuna( "C.N.P.J." );

		tab.setTamColuna( 20, COL_SEL );
		tab.setTamColuna( 150, COL_RAZCLI );
		tab.setTamColuna( 70, COL_CODCLI );
		tab.setTamColuna( 70, COL_CODREC );
		tab.setTamColuna( 80, COL_DOCREC );
		tab.setTamColuna( 70, COL_NRPARC );
		tab.setTamColuna( 70, COL_VLRAPAG );
		tab.setTamColuna( 70, COL_DTREC );
		tab.setTamColuna( 70, COL_DTVENC );
		tab.setTamColuna( 100, COL_AGENCIACLI );
		tab.setTamColuna( 100, COL_IDENTCLI );
		tab.setTamColuna( 50, COL_SITREM );
		tab.setTamColuna( 50, COL_SITRET );
		tab.setTamColuna( 30, COL_STIPOFEBRABAN );
		tab.setTamColuna( 30, COL_TIPOREMCLI );
		tab.setTamColuna( 30, COL_PESSOACLI );
		tab.setTamColuna( 80, COL_CPFCLI );
		tab.setTamColuna( 80, COL_CNPJCLI );
		

		tab.setColunaEditavel( COL_SEL, true );

		tab.addMouseListener( this );

		btCarrega.addActionListener( this );
		btSelTudo.addActionListener( this );
		btSelNada.addActionListener( this );
		btExporta.addActionListener( this );
		btImprime.addActionListener( this );
		btVisImp.addActionListener( this );
		
		
		btSelTudo.setToolTipText( "Selecionar tudo" );
		btSelNada.setToolTipText( "Limpar sele��o" );

		Calendar cal = Calendar.getInstance();
		txtDtFim.setVlrDate( cal.getTime() );
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH )-1 );
		txtDtIni.setVlrDate( cal.getTime() );

	}

	private void montaTela() {
	
		pnCliente.add( panelRemessa, BorderLayout.CENTER );
	
		panelRemessa.add( panelFiltros, BorderLayout.NORTH );
		panelRemessa.add( panelTabela, BorderLayout.CENTER );
		panelRemessa.add( panelStatus, BorderLayout.SOUTH );
	
		panelFiltros.setPreferredSize( new Dimension( 300, 145 ) );
		panelFiltros.adic( new JLabel( "C�d.banco" ), 7, 0, 90, 20 );
		panelFiltros.adic( txtCodBanco, 7, 20, 90, 20 );
		panelFiltros.adic( new JLabel( "Nome do banco" ), 100, 0, 300, 20 );
		panelFiltros.adic( txtNomeBanco, 100, 20, 300, 20 );
	
		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
	
		panelFiltros.adic( new JLabel( "filtro:" ), 7, 40, 60, 20 );
		panelFiltros.adic( rgSitRemessa, 7, 60, 250, 70 );
		panelFiltros.adic( new JLabel( "filtro:" ), 260, 40, 60, 20 );
		panelFiltros.adic( rgData, 260, 60, 120, 70 );
		panelFiltros.adic( new JLabel( "Per�odo:" ), 383, 40, 80, 20 );
		panelFiltros.adic( txtDtIni, 395, 85, 75, 20 );
		panelFiltros.adic( new JLabel( "at�", SwingConstants.CENTER ), 468, 85, 40, 20 );
		panelFiltros.adic( txtDtFim, 505, 85, 75, 20 );
		panelFiltros.adic( bordaData, 383, 60, 210, 70 );
	
		panelFiltros.adic( btCarrega, 605, 80, 150, 30 );
	
		panelTabela.add( new JScrollPane( tab ), BorderLayout.CENTER );
		panelTabela.add( panelFuncoes, BorderLayout.EAST );
	
		panelFuncoes.setPreferredSize( new Dimension( 45, 100 ) );
		panelFuncoes.adic( btSelTudo, 5, 5, 30, 30 );
		panelFuncoes.adic( btSelNada, 5, 40, 30, 30 );
		
		lbStatus.setForeground( Color.BLUE );
	
		panelStatus.setPreferredSize( new Dimension( 600, 30 ) );
		panelStatus.add( lbStatus, BorderLayout.WEST );
	
		panelRodape = adicBotaoSair();
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		panelRodape.setPreferredSize( new Dimension( 600, 32 ) );
		btExporta.setPreferredSize( new Dimension( 150, 30 ) );
		panelRodape.add( btExporta, BorderLayout.WEST );
		
		panelRodape.add( panelImp, BorderLayout.CENTER );
		panelImp.add( pnImp, BorderLayout.NORTH );
		pnImp.setPreferredSize( new Dimension( 60, 30 ) );
		pnImp.add( btImprime );
		pnImp.add( btVisImp );
		
	}

	protected String getMenssagemRet( final String codretorno ) {
		
		String msg = null; 
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		
		try {
			
			sSQL.append( " SELECT DESCRET " );
			sSQL.append( " FROM FNFBNCODRET " );
			sSQL.append( " WHERE CODEMP=? AND CODFILIAL=?  AND CODEMPBO=? " );
			sSQL.append( " AND CODFILIALBO=?  AND CODRET=? AND TIPOFEBRABAN=? "  );
			
			ps = con.prepareStatement( sSQL.toString() );
			
			ps.setInt( 1,  Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "FNBANCO" ) );
			ps.setString( 5, codretorno );
			ps.setString( 6, TIPO_FEBRABAN );
			
			ResultSet rs = ps.executeQuery();
			
			if( rs.next() ){
				
				msg = rs.getString( "DESCRET" );
			}
		} catch ( Exception e ) {
			Funcoes.mensagemInforma( this, "Erro ao montar grid. \n" + e.getMessage() );
			e.printStackTrace();
		}
		return msg;		
	}

	protected boolean setPrefs() {

		boolean retorno = false;

		try {
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT I.CODCONV, P.NOMEEMP, I.VERLAYOUT, I.IDENTSERV, I.CONTACOMPR, " );
			sql.append( "I.IDENTAMBCLI, I.IDENTAMBBCO, I.NROSEQ " );
			sql.append( "FROM SGITPREFERE6 I, SGPREFERE6 P " );
			sql.append( "WHERE I.CODEMP=? AND I.CODFILIAL=? " );
			sql.append( "AND I.CODEMPBO=? AND I.CODFILIALBO=? AND I.CODBANCO=? AND I.TIPOFEBRABAN=? " );
			sql.append( "AND P.CODEMP=I.CODEMP AND P.CODFILIAL=I.CODFILIAL" );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "FNBANCO" ) );
			ps.setString( 5, txtCodBanco.getVlrString() );
			ps.setString( 6, TIPO_FEBRABAN );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				prefs.put( FbnUtil.EPrefs.CODCONV, rs.getString( FbnUtil.EPrefs.CODCONV.toString() ) );
				prefs.put( FbnUtil.EPrefs.NOMEEMP, rs.getString( FbnUtil.EPrefs.NOMEEMP.toString() ) );
				prefs.put( FbnUtil.EPrefs.VERLAYOUT, rs.getString( FbnUtil.EPrefs.VERLAYOUT.toString() ) );
				prefs.put( FbnUtil.EPrefs.CODBANCO, txtCodBanco.getVlrString() );
				prefs.put( FbnUtil.EPrefs.NOMEBANCO, txtNomeBanco.getVlrString() );
				prefs.put( FbnUtil.EPrefs.IDENTSERV, rs.getString( FbnUtil.EPrefs.IDENTSERV.toString() ) );
				prefs.put( FbnUtil.EPrefs.CONTACOMPR, rs.getString( FbnUtil.EPrefs.CONTACOMPR.toString() ) );
				prefs.put( FbnUtil.EPrefs.IDENTAMBCLI, rs.getString( FbnUtil.EPrefs.IDENTAMBCLI.toString() ) );
				prefs.put( FbnUtil.EPrefs.IDENTAMBBCO, rs.getString( FbnUtil.EPrefs.IDENTAMBBCO.toString() ) );
				prefs.put( FbnUtil.EPrefs.NROSEQ, new Integer( rs.getInt( FbnUtil.EPrefs.NROSEQ.toString() ) ) );
				retorno = true;
			}
			else {
				retorno = false;
				Funcoes.mensagemInforma( null, "Ajuste os par�metros antes de executar!" );
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException sqlError ) {
			retorno = false;
			Funcoes.mensagemErro( this, "Carregando par�metros!\n" + sqlError.getMessage() );
			lbStatus.setText( "" );
		}
		
		return retorno;
	}

	protected ResultSet executeQuery() throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
		String sDtFiltro = "E".equals( rgData.getVlrString() ) ? "IR.DTITREC" : "IR.DTVENCITREC";

		if ( "00".equals( rgSitRemessa.getVlrString() ) ) {
			where = "AND ( FR.SITREMESSA IS NULL OR FR.SITREMESSA='00' ) AND ( FR.SITRETORNO IS NULL OR FR.SITRETORNO='00' ) ";
		}
		else if ( "01".equals( rgSitRemessa.getVlrString() ) ) {
			where = "AND ( FR.SITREMESSA IS NULL OR FR.SITREMESSA='01' ) ";
		}
		else if ( "02".equals( rgSitRemessa.getVlrString() ) ) {
			where = "AND ( FR.SITRETORNO IS NOT NULL AND FR.SITRETORNO<>'00' ) ";
		}

		sSQL.append( "SELECT IR.CODREC, IR.NPARCITREC, R.DOCREC, R.CODCLI, C.RAZCLI, IR.DTITREC, IR.DTVENCITREC," );
		sSQL.append( "IR.VLRAPAGITREC, FC.AGENCIACLI, FC.IDENTCLI, COALESCE(FR.SITREMESSA,'00') SITREMESSA, " );
		sSQL.append( "FR.SITRETORNO, COALESCE(COALESCE(FR.STIPOFEBRABAN,FC.STIPOFEBRABAN),'02') STIPOFEBRABAN, " );
		sSQL.append( "COALESCE(FC.TIPOREMCLI,'B') TIPOREMCLI, C.PESSOACLI, C.CPFCLI, C.CNPJCLI " );
		sSQL.append( "FROM VDCLIENTE C," );
		sSQL.append( "FNRECEBER R LEFT OUTER JOIN FNFBNCLI FC ON " );
		sSQL.append( "FC.CODEMP=R.CODEMPCL AND FC.CODFILIAL=R.CODFILIALCL AND FC.CODCLI=R.CODCLI ," );
		sSQL.append( "FNITRECEBER IR LEFT OUTER JOIN FNFBNREC FR ON " );
		sSQL.append( "FR.CODEMP=IR.CODEMP AND FR.CODFILIAL=IR.CODFILIAL AND " );
		sSQL.append( "FR.CODREC=IR.CODREC AND FR.NPARCITREC=IR.NPARCITREC AND " );
		sSQL.append( "FR.CODEMPBO=IR.CODEMPBO AND FR.CODFILIALBO=IR.CODFILIALBO AND FR.CODBANCO=IR.CODBANCO " );
		sSQL.append( "WHERE R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC AND " );
		sSQL.append( "C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI AND " );
		sSQL.append( sDtFiltro );
		sSQL.append( " BETWEEN ? AND ? AND IR.STATUSITREC IN ('R1','RL') AND " );
		sSQL.append( "IR.CODEMPBO=? AND IR.CODFILIALBO=? AND IR.CODBANCO=? " );
		sSQL.append( where );
		sSQL.append( "ORDER BY C.RAZCLI, R.CODREC, IR.NPARCITREC " );

		ps = con.prepareStatement( sSQL.toString() );
		ps.setDate( 1, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
		ps.setDate( 2, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
		ps.setInt( 3, Aplicativo.iCodEmp );
		ps.setInt( 4, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		ps.setInt( 5, txtCodBanco.getVlrInteger() );
		rs = ps.executeQuery();
		return rs;
	}

	protected void carregaTab() {

		if ( txtCodBanco.getVlrString().trim().length() < 1 ) {
			Funcoes.mensagemErro( this, "O c�digo do banco � obrigatorio!" );
			return;
		}

		try {
			
			lbStatus.setText( "      carregando tabela ..." );

			tab.limpa();

			ResultSet rs = executeQuery();

			int i = 0;
			for ( i = 0; rs.next(); i++ ) {

				tab.adicLinha();
				tab.setValor( new Boolean( true ), i, COL_SEL );
				tab.setValor( rs.getString( "RAZCLI" ), i, COL_RAZCLI );
				tab.setValor( new Integer( rs.getInt( "CODCLI" ) ), i, COL_CODCLI );
				tab.setValor( new Integer( rs.getInt( "CODREC" ) ), i, COL_CODREC );
				tab.setValor( rs.getString( "DOCREC" ), i, COL_DOCREC );
				tab.setValor( new Integer( rs.getInt( "NPARCITREC" ) ), i, COL_NRPARC );
				tab.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRAPAGITREC" ) ), i, COL_VLRAPAG );
				tab.setValor( rs.getDate( "DTITREC" ), i, COL_DTREC );
				tab.setValor( rs.getDate( "DTVENCITREC" ), i, COL_DTVENC );
				tab.setValor( rs.getString( "AGENCIACLI" ), i, COL_AGENCIACLI );
				tab.setValor( rs.getString( "IDENTCLI" ), i, COL_IDENTCLI );
				tab.setValor( rs.getString( "SITREMESSA" ), i, COL_SITREM );
				tab.setValor( rs.getString( "SITRETORNO" ), i, COL_SITRET );
				tab.setValor( rs.getString( "STIPOFEBRABAN" ), i, COL_STIPOFEBRABAN );
				tab.setValor( rs.getString( "TIPOREMCLI" ), i, COL_TIPOREMCLI );
				tab.setValor( rs.getString( "PESSOACLI" ), i, COL_PESSOACLI );
				tab.setValor( rs.getString( "CPFCLI" ), i, COL_CPFCLI );
				tab.setValor( rs.getString( "CNPJCLI" ), i, COL_CNPJCLI );
			}

			rs.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			if ( i > 0 ) {
				lbStatus.setText( "     tabela carregada com " + i + " itens..." );
			}
			else {
				lbStatus.setText( "" );
			}

		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao busca dados!\n" + e.getMessage() );
			e.printStackTrace();
			lbStatus.setText( "" );
		} finally {
			System.gc();
		}

	}
	
	private void selecionaTudo() {
	
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}
	

	private void selecionaNada() {
	
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( false ), i, 0 );
		}
	}
	

	protected boolean consisteExporta( HashSet<FbnUtil.StuffCli> hsCli, HashSet<FbnUtil.StuffRec> hsRec ) {

		boolean retorno = true;
		Vector vLinha = null;

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {

			vLinha = tab.getLinha( i );

			if ( (Boolean) vLinha.elementAt( COL_SEL ) ) {
				if ( ( "".equals( (String) vLinha.elementAt( COL_AGENCIACLI ) ) ) || ( "".equals( (String) vLinha.elementAt( COL_IDENTCLI ) ) ) ) {
					if ( ! completaTabela( i, (Integer) vLinha.elementAt( COL_CODCLI ), (String) vLinha.elementAt( COL_RAZCLI ), (String) vLinha.elementAt( COL_AGENCIACLI ), (String) vLinha.elementAt( COL_IDENTCLI ), (String) vLinha.elementAt( COL_STIPOFEBRABAN ) ) ) {
						retorno = false;
						break;
					}
				}
				hsCli.add( new FbnUtil().new StuffCli( (Integer) vLinha.elementAt( COL_CODCLI ), 
						new String[] { txtCodBanco.getVlrString(), TIPO_FEBRABAN, 
						(String) vLinha.elementAt( COL_STIPOFEBRABAN ),
						(String) vLinha.elementAt( COL_AGENCIACLI ), 
						(String) vLinha.elementAt( COL_IDENTCLI ),
						(String) vLinha.elementAt( COL_TIPOREMCLI ) } ) );
				hsRec.add( new FbnUtil().new StuffRec( (Integer) vLinha.elementAt( COL_CODREC ), 
						(Integer) vLinha.elementAt( COL_NRPARC ),
				/*
				 * String codBanco, String tipoFebraban, String stipoFebraban, 
				 * String sitRemessa {CODBANCO, TIPOFEBRABAN, 
				 * STIPOFEBRABAN, SITREMESSA, CODCLI, AGENCIACLI, 
				 * IDENTCLI, DTVENC, VLRPARC, PESSOACLI, CPJCLI, CNPJCLI}
				 */
				new String[] { txtCodBanco.getVlrString(), TIPO_FEBRABAN, 
						(String) vLinha.elementAt( COL_STIPOFEBRABAN ), 
						(String) vLinha.elementAt( COL_SITREM ), 
						String.valueOf( (Integer) vLinha.elementAt( COL_CODCLI ) ), 
						(String) vLinha.elementAt( COL_AGENCIACLI ),
						(String) vLinha.elementAt( COL_IDENTCLI ), 
						Funcoes.dataAAAAMMDD( (Date) vLinha.elementAt( COL_DTVENC ) ), 
						Funcoes.strToBd( vLinha.elementAt( COL_VLRAPAG )).toString(),
						(String) vLinha.elementAt(COL_PESSOACLI),
						(String) vLinha.elementAt( COL_CPFCLI ),
						(String) vLinha.elementAt( COL_CNPJCLI ) } ) ); 
		   }
		}
		if ( retorno ) {
			retorno = persisteDados( hsCli, hsRec );
		}

		return retorno;
	}
	
	protected boolean completaTabela( final int linha, final Integer codCli, final String razCli, 
			final String agenciaCli, final String identCli, final String subTipo ) {

		boolean retorno = true;

		Object[] valores = DLIdentCli.execIdentCli( this, codCli, razCli, agenciaCli, identCli, subTipo );
		retorno = ( (Boolean) valores[ 0 ] ).booleanValue();

		if ( retorno ) {
			ajustaClientes( codCli, (String) valores[ 1 ], (String) valores[ 2 ], (String) valores[ 3 ] );
		}
		else {
			tab.setValor( false, linha, COL_SEL );
		}

		return retorno;
	}

	protected void ajustaClientes( final Integer codCli, final String agenciaCli, final String identCli, final String subTipo ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			if ( ( (Boolean) tab.getValor( i, COL_SEL ) ).booleanValue() && codCli.equals( (Integer) tab.getValor( i, COL_CODCLI ) ) ) {
				tab.setValor( agenciaCli, i, COL_AGENCIACLI );
				tab.setValor( identCli, i, COL_IDENTCLI );
				tab.setValor( subTipo, i, COL_STIPOFEBRABAN );
			}
		}
	}

	protected boolean persisteDados( final HashSet<FbnUtil.StuffCli> hsCli, 
			final HashSet<FbnUtil.StuffRec> hsRec ) {

		boolean retorno = true;
		for ( FbnUtil.StuffCli stfCli : hsCli ) {
			retorno = updateCliente( stfCli.getCodigo(), stfCli.getArgs()[ FbnUtil.EColcli.CODBANCO.ordinal() ], 
					stfCli.getArgs()[ FbnUtil.EColcli.TIPOFEBRABAN.ordinal() ], stfCli.getArgs()[ FbnUtil.EColcli.STIPOFEBRABAN.ordinal() ], 
					stfCli.getArgs()[ FbnUtil.EColcli.AGENCIACLI.ordinal() ], stfCli.getArgs()[ FbnUtil.EColcli.IDENTCLI
					.ordinal() ], stfCli.getArgs()[ FbnUtil.EColcli.TIPOREMCLI.ordinal() ] );
			if ( !retorno ) {
				retorno = false;
				break;
			}
		}
		if ( retorno ) {
			for ( FbnUtil.StuffRec stfRec : hsRec ) {
				retorno = updateReceber( stfRec.getCodrec(), stfRec.getNParcitrec(), 
						stfRec.getArgs()[ FbnUtil.EColrec.CODBANCO.ordinal() ], 
						stfRec.getArgs()[ FbnUtil.EColrec.TIPOFEBRABAN.ordinal() ], 
						stfRec.getArgs()[ FbnUtil.EColrec.STIPOFEBRABAN.ordinal() ], 
						stfRec.getArgs()[ FbnUtil.EColrec.SITREMESSA.ordinal() ] );
				if ( !retorno ) {
					retorno = false;
					break;
				}
			}
		}
		return retorno;
	}	
	
	protected boolean updateCliente( int codCli, String codBanco, String tipoFebraban, String stipoFebraban, String agenciaCli, String identCli, String tipoRemCli ) {

		boolean retorno = false;

		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT AGENCIACLI, IDENTCLI, STIPOFEBRABAN, TIPOREMCLI FROM FNFBNCLI " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " );
			sql.append( "AND CODEMPPF=? AND CODFILIALPF=? AND CODEMPBO=? AND CODFILIALBO=? AND CODBANCO=? AND TIPOFEBRABAN=?" );
						
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, codCli );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
			ps.setInt( 6, Aplicativo.iCodEmp );
			ps.setInt( 7, ListaCampos.getMasterFilial( "FNBANCO" ) );
			ps.setString( 8, codBanco );
			ps.setString( 9, tipoFebraban );
			
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				
				if ( ( !agenciaCli.equals( rs.getString( "AGENCIACLI" ) ) ) || ( !identCli.equals( rs.getString( "IDENTCLI" ) ) ) || ( !stipoFebraban.equals( rs.getString( "STIPOFEBRABAN" ) ) ) || ( !tipoRemCli.equals( rs.getString( "TIPOREMCLI" ) ) ) ) {
					
					StringBuilder sqlup = new StringBuilder();
					sqlup.append( "UPDATE FNFBNCLI SET AGENCIACLI=?, IDENTCLI=?, STIPOFEBRABAN=?, TIPOREMCLI=? " );
					sqlup.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " );
					sqlup.append( "AND CODEMPPF=? AND CODFILIALPF=? AND CODEMPBO=? AND CODFILIALBO=? AND CODBANCO=? AND TIPOFEBRABAN=?" ); 
					
					ps = con.prepareStatement( sqlup.toString() );
					ps.setString( 1, agenciaCli );
					ps.setString( 2, identCli );
					ps.setString( 3, stipoFebraban );
					ps.setString( 4, tipoRemCli );
					ps.setInt( 5, Aplicativo.iCodEmp );
					ps.setInt( 6, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
					ps.setInt( 7, codCli );
					ps.setInt( 8, Aplicativo.iCodEmp );
					ps.setInt( 9, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
					ps.setInt( 10, Aplicativo.iCodEmp );
					ps.setInt( 11, ListaCampos.getMasterFilial( "FNBANCO" ) );
					ps.setString( 12, codBanco );
					ps.setString( 13, tipoFebraban );
					ps.executeUpdate();
				}
			}
			else {
				
				StringBuilder sqlin = new StringBuilder();
				sqlin.append( "INSERT INTO FNFBNCLI (AGENCIACLI, IDENTCLI, CODEMP, CODFILIAL, " ); 
				sqlin.append( "CODCLI, CODEMPPF, CODFILIALPF, CODEMPBO, CODFILIALBO, CODBANCO, " );
				sqlin.append( "TIPOFEBRABAN, STIPOFEBRABAN, TIPOREMCLI) " );
				sqlin.append( "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)" );
				
				ps = con.prepareStatement( sqlin.toString() );
				ps.setString( 1, agenciaCli );
				ps.setString( 2, identCli );
				ps.setInt( 3, Aplicativo.iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setInt( 5, codCli );
				ps.setInt( 6, Aplicativo.iCodEmp );
				ps.setInt( 7, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
				ps.setInt( 8, Aplicativo.iCodEmp );
				ps.setInt( 9, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( 10, codBanco );
				ps.setString( 11, tipoFebraban );
				ps.setString( 12, stipoFebraban );
				ps.setString( 13, tipoRemCli );

				ps.executeUpdate();
			}
			if ( ! con.getAutoCommit() ) {
				con.commit();				
			}
			
			retorno = true;
			
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro atualizando cliente!\n" + e.getMessage() );
		}

		return retorno;
	}

	protected boolean updateReceber( int codRec, int nParcitrec, String codBanco, String tipoFebraban, String stipoFebraban, String sitRemessa ) {

		boolean retorno = false;
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, SITREMESSA " );
			sql.append( "FROM FNFBNREC WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setInt( 3, codRec );
			ps.setInt( 4, nParcitrec );
			
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( ( !codBanco.equals( rs.getString( "CODBANCO" ) ) ) || 
						( !tipoFebraban.equals( rs.getString( "TIPOFEBRABAN" ) ) ) || 
						( !stipoFebraban.equals( rs.getString( "STIPOFEBRABAN" ) ) ) || 
						( !sitRemessa.equals( rs.getString( "SITREMESSA" ) ) ) ) {
					
					StringBuilder sqlup = new StringBuilder();
					sqlup.append( "UPDATE FNFBNREC SET CODBANCO=?, TIPOFEBRABAN=?, STIPOFEBRABAN=?, SITREMESSA=? " );
					sqlup.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?" );
					
					ps = con.prepareStatement( sqlup.toString() );
					ps.setString( 1, codBanco );
					ps.setString( 2, tipoFebraban );
					ps.setString( 3, stipoFebraban );
					ps.setString( 4, sitRemessa );
					ps.setInt( 5, Aplicativo.iCodEmp );
					ps.setInt( 6, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
					ps.setInt( 7, codRec );
					ps.setInt( 8, nParcitrec );
					ps.executeUpdate();
				}
			}
			else {
				
				StringBuilder sqlin = new StringBuilder();
				sqlin.append( "INSERT INTO FNFBNREC (CODEMP, CODFILIAL, CODREC, NPARCITREC, " ); 
				sqlin.append( "CODEMPPF, CODFILIALPF, CODEMPBO, CODFILIALBO, CODBANCO, " );
				sqlin.append( "TIPOFEBRABAN, STIPOFEBRABAN, SITREMESSA) " );
				sqlin.append( "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );
				
				ps = con.prepareStatement( sqlin.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNFBNREC" ) );
				ps.setInt( 3, codRec );
				ps.setInt( 4, nParcitrec );
				ps.setInt( 5, Aplicativo.iCodEmp );
				ps.setInt( 6, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
				ps.setInt( 7, Aplicativo.iCodEmp );
				ps.setInt( 8, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( 9, codBanco );
				ps.setString( 10, tipoFebraban );
				ps.setString( 11, stipoFebraban );
				ps.setString( 12, sitRemessa );
				ps.executeUpdate();
			}
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
			
			retorno = true;

		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro atualizando situa��o do contas a receber!\n" + e.getMessage() );
		}

		return retorno;
	}
	
	protected boolean updatePrefere() {
		
		boolean retorno = true;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "UPDATE SGITPREFERE6 I SET NROSEQ=? " );
			sql.append( "WHERE I.CODEMP=? AND I.CODFILIAL=? " );
			sql.append( "AND I.CODEMPBO=? AND I.CODFILIALBO=? AND I.CODBANCO=? AND I.TIPOFEBRABAN=?" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, (Integer) prefs.get(FbnUtil.EPrefs.NROSEQ) );
			ps.setInt( 2, Aplicativo.iCodEmp);
			ps.setInt( 3, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "FNBANCO" ) );
			ps.setString( 6, (String) prefs.get( FbnUtil.EPrefs.CODBANCO ) );
			ps.setString( 7, TIPO_FEBRABAN );
			ps.executeUpdate();
			ps.close();
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( SQLException e ) {
			retorno = false;
			Funcoes.mensagemErro( this, "Erro atualizando par�metros!\n" + e.getMessage() );
		}

		return retorno;
	}
	
	abstract protected boolean execExporta();
	
	abstract public void imprimir( boolean bVisualizar ); 
	
	public void actionPerformed( ActionEvent evt ) {
	
		if ( evt.getSource() == btCarrega ) {
			carregaTab();
		}
		else if ( evt.getSource() == btSelTudo ) {
			selecionaTudo();
		}
		else if ( evt.getSource() == btSelNada ) {
			selecionaNada();
		}
		else if ( evt.getSource() == btExporta ) {
			execExporta();
		}
		else if( evt.getSource() == btVisImp ){
			imprimir( true );
		}
	}

	public void mouseClicked( MouseEvent e ) {

		if ( e.getClickCount() == 2 && e.getSource() == tab && tab.getLinhaSel() > -1 ) {

			if ( !"00".equals( tab.getValor( tab.getLinhaSel(), COL_SITRET ) ) ) {

				Funcoes.mensagemInforma( this, "Registro rejeitado!\n" + getMenssagemRet( (String) tab.getValor( tab.getLinhaSel(), COL_SITRET ) ) );
			}
			completaTabela( tab.getLinhaSel(), (Integer) tab.getValor( tab.getLinhaSel(), COL_CODCLI ), (String) tab.getValor( tab.getLinhaSel(), COL_RAZCLI ), (String) tab.getValor( tab.getLinhaSel(), COL_AGENCIACLI ), (String) tab.getValor( tab.getLinhaSel(), COL_IDENTCLI ), (String) tab
					.getValor( tab.getLinhaSel(), COL_STIPOFEBRABAN ) );
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

}
