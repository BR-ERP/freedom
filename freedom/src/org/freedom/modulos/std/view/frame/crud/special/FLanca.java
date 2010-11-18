/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FLanca.java <BR>
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

package org.freedom.modulos.std.view.frame.crud.special;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.swing.frame.FPrincipal;
import org.freedom.modulos.fnc.business.object.Cheque;
import org.freedom.modulos.fnc.view.dialog.utility.DLEditaPag;
import org.freedom.modulos.fnc.view.frame.crud.detail.FCheque;
import org.freedom.modulos.std.view.dialog.utility.DLDataTransf;

public class FLanca extends FFilho implements ActionListener, ChangeListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 600, 64 );

	private JPanelPad pnNav = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 7 ) );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCentro = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );;

	private JPanelPad pinPeriodo = new JPanelPad( 260, 50 );

	private JLabelPad lbPeriodo = new JLabelPad( " Per�odo" );

	private JPanelPad pinSaldo = new JPanelPad( 400, 50 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btCalcSaldo = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btPrim = new JButtonPad( Icone.novo( "btPrim.gif" ) );

	private JButtonPad btAnt = new JButtonPad( Icone.novo( "btAnt.gif" ) );

	private JButtonPad btProx = new JButtonPad( Icone.novo( "btProx.gif" ) );

	private JButtonPad btUlt = new JButtonPad( Icone.novo( "btUlt.gif" ) );

	private JButtonPad btNovo = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btEditar = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	private JLabelPad lbA = new JLabelPad( "�" );

	private JLabelPad lbPinSaldo = new JLabelPad( " Saldo" );

	private JPanelPad pinLbPeriodo = new JPanelPad( 53, 15 );

	private JPanelPad pinLbSaldo = new JPanelPad( 45, 15 );

	private JLabelPad lbDataSaldo = new JLabelPad( "Data" );

	private JLabelPad lbSaldo = new JLabelPad( "Saldo" );
	
	private JLabelPad lbSaldoComposto = new JLabelPad( "Saldo Composto" );

	private JLabelPad lbAtualSaldo = new JLabelPad( "Atualiza" );

	private JLabelPad lbDataSaldoVal = new JLabelPad( "" );

//	private JLabelPad lbVlrSaldo = new JLabelPad( "" );
	
	private JTextFieldPad txtVlrSaldo = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin ) ;
	
	private JTextFieldPad txtVlrSaldoComposto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin ) ;

	private JLabelPad lbAtualSaldoVal = new JLabelPad( "N�O" );

	private JTablePad tab = new JTablePad();

	private JScrollPane spnTab = new JScrollPane( tab );

	private JTabbedPanePad tpn = new JTabbedPanePad();

	private String[] sPlanos = null;

	private String sCodPlan = "";

	private String[] sContas = null;

	private String sConta = "";

	private Date dIniLanca = null;

	private Date dFimLanca = null;
	
	private JButtonPad btAbreCheque = new JButtonPad( Icone.novo( "btCheque.png" ) ); 
	
	private enum enum_tab_lanca {  CODLANCA, DATASUBLANCA, TRANSFLANCA, ORIGSUBLANCA, NUMCONTA, DOCLANCA, VLRSUBLANCA, HISTBLANCA, CHEQUES, CODPAG, NPARCPAG, SEQCHEQ  };

	public FLanca() {

		super( false );
		
		setTitulo( "Lan�amentos Financeiros" );
		setAtribos( 50, 25, 767, 350 );

		Container c = getContentPane();

		c.setLayout( new BorderLayout() );

		pnRod.setPreferredSize( new Dimension( 600, 32 ) );

		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pnCentro, BorderLayout.CENTER );
		c.add( pinCab, BorderLayout.NORTH );

		tpn.setTabLayoutPolicy( JTabbedPanePad.SCROLL_TAB_LAYOUT );
		tpn.setPreferredSize( new Dimension( 600, 30 ) );
		
		pnCentro.add( tpn, BorderLayout.SOUTH );
		pnCentro.add( spnTab, BorderLayout.CENTER );

		pinLbPeriodo.adic( lbPeriodo, 0, 0, 51, 15 );
		pinLbPeriodo.tiraBorda();
		
		pinLbSaldo.adic( lbPinSaldo, 0, 0, 46, 15 );
		pinLbSaldo.tiraBorda();

		pinCab.adic( pinLbPeriodo, 10, 2, 51, 15 );
		pinCab.adic( pinPeriodo, 7, 10, 260, 44 );
		pinCab.adic( pinLbSaldo, 270, 2, 46, 15 );		
		pinCab.adic( pinSaldo, 270, 10, 400, 44 );

		pinPeriodo.adic( txtDataini, 7, 10, 100, 20 );
		pinPeriodo.adic( lbA, 110, 10, 7, 20 );
		pinPeriodo.adic( txtDatafim, 120, 10, 97, 20 );
		pinPeriodo.adic( btExec, 220, 5, 30, 30 );

		lbDataSaldoVal.setForeground( new Color( 0, 140, 0 ) );
		txtVlrSaldo.setEditable( false );
		txtVlrSaldo.setForeground( new Color( 0, 140, 0 ) );
		txtVlrSaldo.setBackground( null );
		
		txtVlrSaldoComposto.setEditable( false );
		txtVlrSaldoComposto.setBackground( null );

		txtVlrSaldoComposto.setForeground( new Color( 0, 140, 0 ) );		
		lbAtualSaldoVal.setForeground( new Color( 0, 140, 0 ) );

		pinSaldo.adic( lbDataSaldo, 7, 6, 50, 15 );
		pinSaldo.adic( lbDataSaldoVal, 7, 21, 100, 15 );
		
		pinSaldo.adic( lbSaldo, 80, 6, 50, 15 );		
		pinSaldo.adic( txtVlrSaldo, 80, 21, 87, 15 );

		pinSaldo.adic( lbSaldoComposto, 175, 6, 100, 15 );		
		pinSaldo.adic( txtVlrSaldoComposto, 175, 21, 97, 15 );

		pinSaldo.adic( lbAtualSaldo, 290, 6, 50, 15 );
		pinSaldo.adic( lbAtualSaldoVal, 290, 21, 57, 15 );
		
		pinSaldo.adic( btCalcSaldo, 360, 5, 30, 30 );

		btSair.setPreferredSize( new Dimension( 100, 31 ) );

		pnNav.setPreferredSize( new Dimension( 240, 30 ) );

		pnRod.setBorder( BorderFactory.createEtchedBorder() );
		pnRod.add( btSair, BorderLayout.EAST );
		pnRod.add( pnNav, BorderLayout.WEST );

		pnNav.add( btPrim );
		pnNav.add( btAnt );
		pnNav.add( btProx );
		pnNav.add( btUlt );
		pnNav.add( btNovo );
		pnNav.add( btExcluir );
		pnNav.add( btEditar );
		pnNav.add( btAbreCheque );
		
		btAbreCheque.setEnabled( false );

		tab.adicColuna( "N�Lan�." );
		tab.adicColuna( "Data" );
		tab.adicColuna( "Tsf." );
		tab.adicColuna( "Orig." );
		tab.adicColuna( "Conta tsf." );
		tab.adicColuna( "N� doc." );
		tab.adicColuna( "Valor" );
		tab.adicColuna( "Hist�rico" );
		tab.adicColuna( "Cheques" );
		tab.adicColuna( "Cod.Pag." );
		tab.adicColuna( "N.Parc.Pag." );
		tab.adicColuna( "Seq.Cheque" );

		tab.setTamColuna( 60, enum_tab_lanca.CODLANCA.ordinal() );
		tab.setTamColuna( 70, enum_tab_lanca.DATASUBLANCA.ordinal() );
		tab.setTamColuna( 20, enum_tab_lanca.TRANSFLANCA.ordinal() );
		tab.setTamColuna( 30, enum_tab_lanca.ORIGSUBLANCA.ordinal() );
		tab.setTamColuna( 72, enum_tab_lanca.NUMCONTA.ordinal() );
		tab.setTamColuna( 65, enum_tab_lanca.DOCLANCA.ordinal() );
		tab.setTamColuna( 80, enum_tab_lanca.VLRSUBLANCA.ordinal() );
		tab.setTamColuna( 287, enum_tab_lanca.HISTBLANCA.ordinal() );		
		tab.setTamColuna( 55, enum_tab_lanca.CHEQUES.ordinal() );

		tab.setColunaInvisivel( enum_tab_lanca.CODPAG.ordinal() );
		tab.setColunaInvisivel( enum_tab_lanca.NPARCPAG.ordinal() );
		tab.setColunaInvisivel( enum_tab_lanca.SEQCHEQ.ordinal() );
		
		tab.addMouseListener( this );
		
		btSair.addActionListener( this );
		btPrim.addActionListener( this );
		btAnt.addActionListener( this );
		btProx.addActionListener( this );
		btUlt.addActionListener( this );
		btNovo.addActionListener( this );
		btEditar.addActionListener( this );
		btExcluir.addActionListener( this );
		btExec.addActionListener( this );
		btCalcSaldo.addActionListener( this );
		btAbreCheque.addActionListener( this );
		
		tpn.addChangeListener( this );

		Calendar cPeriodo = Calendar.getInstance();
		
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

	}

	private void mostraSaldoComposto(boolean mostra) {
		
		txtVlrSaldoComposto.setVisible( mostra );
		lbSaldoComposto.setVisible( mostra );
		
	}
	
	private void montaTabela( Date dini, Date dfim ) {

		tab.limpa();
		
		String sSQL = "SELECT S.CODLANCA, S.DATASUBLANCA, COALESCE(L.TRANSFLANCA,'') TRANSFLANCA, COALESCE(S.ORIGSUBLANCA,'') ORIGSUBLANCA," 
			+ "COALESCE(L.DOCLANCA,'') DOCLANCA,S.VLRSUBLANCA,COALESCE(L.HISTBLANCA,'') HISTBLANCA," 
			+ "COALESCE((SELECT C.NUMCONTA FROM FNSUBLANCA S1,FNCONTA C " 
			+ "WHERE S1.CODSUBLANCA=0 AND S1.CODLANCA=S.CODLANCA AND "
			+ "S1.CODEMP=S.CODEMP AND S1.CODFILIAL=S.CODFILIAL AND " 
			+ "C.CODPLAN=S1.CODPLAN AND C.CODEMP=S1.CODEMPPN AND " 
			+ "C.CODFILIAL=S1.CODFILIALPN ),'') NUMCONTA, L.CODPAG, L.NPARCPAG "
			+ " FROM FNSUBLANCA S, FNLANCA L " 
			+ " WHERE S.DATASUBLANCA BETWEEN ? AND ? AND"
			+ " S.CODLANCA = L.CODLANCA AND S.CODEMP=L.CODEMP AND S.CODFILIAL=L.CODFILIAL" 
			+ " AND S.CODPLAN = ? AND L.CODEMP=? AND L.CODFILIAL=?" 
			+ " ORDER BY S.DATASUBLANCA,S.CODLANCA";
		
		try {
			
			
			PreparedStatement ps = con.prepareStatement( sSQL );
			
			ps.setDate( 1, Funcoes.dateToSQLDate( dini ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( dfim ) );
			ps.setString( 3, sCodPlan );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "FNSUBLANCA" ) );

			ResultSet rs = ps.executeQuery();

			for ( int i = 0; rs.next(); i++ ) {
				
				
				tab.adicLinha();
				
				tab.setValor( rs.getString( enum_tab_lanca.CODLANCA.name() ), i, enum_tab_lanca.CODLANCA.ordinal() );
				
				tab.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( enum_tab_lanca.DATASUBLANCA.name() ) ), i, enum_tab_lanca.DATASUBLANCA.ordinal() );
				
				tab.setValor( rs.getString( enum_tab_lanca.TRANSFLANCA.name() ), i, enum_tab_lanca.TRANSFLANCA.ordinal() );
				
				if ( "S".equals( rs.getString( enum_tab_lanca.TRANSFLANCA.name() ) ) ) {
					tab.setValor( rs.getString(enum_tab_lanca.NUMCONTA.name()), i, enum_tab_lanca.NUMCONTA.ordinal() );
				}
				
				tab.setValor( rs.getString( enum_tab_lanca.ORIGSUBLANCA.name()) , i, enum_tab_lanca.ORIGSUBLANCA.ordinal() );
				
				tab.setValor( rs.getString( enum_tab_lanca.DOCLANCA.name()), i, enum_tab_lanca.DOCLANCA.ordinal() );
				tab.setValor( Funcoes.strDecimalToStrCurrency( 8, 2, rs.getString( enum_tab_lanca.VLRSUBLANCA.name() ) ), i, enum_tab_lanca.VLRSUBLANCA.ordinal() );
				tab.setValor( rs.getString( enum_tab_lanca.HISTBLANCA.name()), i, enum_tab_lanca.HISTBLANCA.ordinal() );				
				
				tab.setValor( rs.getString( enum_tab_lanca.CODPAG.name()), i, enum_tab_lanca.CODPAG.ordinal() );
				tab.setValor( rs.getString( enum_tab_lanca.NPARCPAG.name()), i, enum_tab_lanca.NPARCPAG.ordinal() );
				
				Vector<Cheque> cheques = DLEditaPag.buscaCheques( rs.getInt( enum_tab_lanca.CODPAG.name()), rs.getInt( enum_tab_lanca.NPARCPAG.name() ));
				
				if( cheques.size()>0 ) {
					
					Vector<String> numcheques = new Vector<String>();
					Vector<String> seqcheques = new Vector<String>();
					
					for ( int ic = 0; cheques.size() > ic; ic++ ) {

						Cheque cheque = (Cheque) cheques.get( ic );
						numcheques.add( cheque.getNumcheq().toString() );
						seqcheques.add( cheque.getSeqcheq().toString() );

					}
					
					tab.setValor( numcheques, i, enum_tab_lanca.CHEQUES.ordinal() );
					tab.setValor( seqcheques, i, enum_tab_lanca.SEQCHEQ.ordinal() );
					
				}
				
			}
			
			rs.close();
			ps.close();

			atualizaSaldo();
			atualizaSaldoComposto();
			
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao montar a tabela!\n" + err.getMessage(), true, con, err );
		}
	}
	
	@ SuppressWarnings ( "unchecked" )
	private void abreCheque() {

		if ( tab.getLinhaSel() > -1 ) {

			Integer seqcheque = null;
			Vector<String> seqcheq = (Vector) tab.getValor( tab.getLinhaSel(), enum_tab_lanca.SEQCHEQ.ordinal()); 
			
			if(seqcheq!=null) {
				seqcheque = Integer.parseInt( (String) seqcheq.elementAt( 0 ) );
			}

			if(seqcheque!=null) {
			
				FCheque tela = null;
				
				if ( Aplicativo.telaPrincipal.temTela( FCheque.class.getName() ) ) {
					tela = (FCheque) Aplicativo.telaPrincipal.getTela( FCheque.class.getName() );
				}
				else {
					tela = new FCheque();
					Aplicativo.telaPrincipal.criatela( "Cheque", tela, con );
				}
				
				if(seqcheque>0) {
					tela.exec( seqcheque );
				}
			}
			
		}

	}

	private void montaTabs() {

		tpn.setTabPlacement( SwingConstants.BOTTOM );
		/*
		 * String sSQL = "SELECT (SELECT COUNT(C1.NUMCONTA) FROM FNCONTA C1,FNPLANEJAMENTO P1 "+ "WHERE C1.ATIVACONTA='S' AND P1.NIVELPLAN = 6 AND P1.TIPOPLAN IN ('B','C') AND C1.CODPLAN=P1.CODPLAN" + " AND C1.CODEMP=P1.CODEMP AND C1.CODFILIAL=P1.CODFILIAL AND P1.CODEMP=P.CODEMP" +
		 * " AND P1.CODFILIAL=P.CODFILIAL),P.CODPLAN,C.NUMCONTA,C.DESCCONTA"+ " FROM FNPLANEJAMENTO P,FNCONTA C WHERE C.ATIVACONTA='S' AND P.NIVELPLAN = 6"+ " AND P.TIPOPLAN IN ('B','C') AND C.CODPLAN = P.CODPLAN" +
		 * " AND C.CODEMP = P.CODEMP AND C.CODFILIAL=P.CODFILIAL AND P.CODEMP=? AND P.CODFILIAL=? ORDER BY 4";
		 */

		String sSQL = "SELECT (SELECT COUNT(C1.NUMCONTA) FROM FNCONTA C1,FNPLANEJAMENTO P1 " 
			+ "WHERE C1.ATIVACONTA='S' AND P1.NIVELPLAN = 6 AND P1.TIPOPLAN IN ('B','C') AND C1.CODPLAN=P1.CODPLAN " 
			+ "AND C1.CODEMP=P1.CODEMP AND C1.CODFILIAL=P1.CODFILIAL AND P1.CODEMP=P.CODEMP "
  		    + "AND P1.CODFILIAL=P.CODFILIAL),P.CODPLAN,C.NUMCONTA,C.DESCCONTA " + "FROM FNPLANEJAMENTO P,FNCONTA C WHERE C.ATIVACONTA='S' AND P.NIVELPLAN = 6 " 
  		    + "AND P.TIPOPLAN IN ('B','C') AND C.CODPLAN = P.CODPLAN "
			+ "AND C.CODEMP = P.CODEMP AND C.CODFILIAL=P.CODFILIAL AND P.CODEMP=? AND P.CODFILIAL=? " + "AND ( TUSUCONTA='S' OR EXISTS (SELECT * FROM FNCONTAUSU CU " + "WHERE CU.CODEMP=C.CODEMP AND CU.CODFILIAL=C.CODFILIAL AND " + "CU.NUMCONTA=C.NUMCONTA AND CU.CODEMPUS=" + Aplicativo.iCodEmp
			+ " AND CU.CODFILIALUS=" + ListaCampos.getMasterFilial( "SGUSUARIO" ) + "AND CU.IDUSU='" + Aplicativo.strUsuario + "') ) " + "ORDER BY 4";

		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
			ResultSet rs = ps.executeQuery();
			for ( int i = 0; rs.next(); i++ ) {
				if ( i == 0 ) {
					sPlanos = new String[ rs.getInt( 1 ) ];
					sContas = new String[ rs.getInt( 1 ) ];
				}
				sContas[ i ] = rs.getString( "NumConta" );
				sPlanos[ i ] = rs.getString( "CodPlan" );
				tpn.addTab( rs.getString( "DescConta" ).trim(), new JPanelPad( JPanelPad.TP_JPANEL ) );
			}
			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			System.out.println( "Erro ao montar as tabs!\n" + err.getMessage() );
			err.printStackTrace();
		}
	}

	private void atualizaSaldoComposto() {

		int iCodEmp = 0;
		int iCodFilial = 0;
		StringBuilder sql = new StringBuilder();
 
		sql.append( "select	cv.numconta, "); 
		
		sql.append( "coalesce(sum(( ");
		
		sql.append( "	select s.saldosl from fnsaldolanca s ");
		sql.append( "	where s.codplan=ct2.codplan and s.codemp=ct2.codemppn and s.codfilial=ct2.codfilialpn and s.codemppn=ct2.codemppn and ");
		sql.append( "	s.codfilialpn=ct2.codfilialpn and s.datasl=");
		sql.append( "	(select max(s1.datasl) ");		
		sql.append( "		from fnsaldolanca s1 where s1.datasl <= ? and s1.codplan=s.codplan ");
		sql.append( "		and s1.codemp=s.codemp and s1.codfilial=s.codfilial and s1.codemppn=s.codemppn and s1.codfilialpn=s.codfilialpn ");
		sql.append( "	)" );

		sql.append( ")),0) saldovinculado ");
		
		sql.append( "from fncontavinculada cv, fnconta ct2, fnconta ct1 ");
		sql.append( "where ct2.codemp=cv.codempcv and ct2.codfilial=cv.codfilialcv and ct2.numconta=cv.numcontacv ");
		sql.append( "and  ct1.codemppn=? and ct1.codfilialpn=? and ct1.codplan=? ");
		sql.append( "and ct1.numconta=cv.numconta and ct1.codemp=cv.codemp and ct1.codfilial=cv.codfilial ");
		sql.append( "group by 1 ");  
		
		try {
			iCodEmp = Aplicativo.iCodEmp;
			iCodFilial = ListaCampos.getMasterFilial( "FNSALDOLANCA" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );

			ps.setDate( 1, Funcoes.dateToSQLDate( dFimLanca ) );
			ps.setInt( 2, iCodEmp );
			ps.setInt( 3, iCodFilial );
			ps.setString( 4, sCodPlan );	
			
			System.out.println("Query do saldo composto:" + sql.toString());

			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				BigDecimal SaldoComposto = txtVlrSaldo.getVlrBigDecimal();
				
				SaldoComposto = SaldoComposto.add( rs.getBigDecimal( "SaldoVinculado" ) );
				
				txtVlrSaldoComposto.setVlrBigDecimal( SaldoComposto );
				mostraSaldoComposto( true );
			}
			else {
				txtVlrSaldoComposto.setVlrBigDecimal( new BigDecimal(0) );
				mostraSaldoComposto( false );
			}
			
			rs.close();
			ps.close();
			// con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao atualizar o saldo composto!\n" + err.getMessage(), true, con, err );
		}
	}
	
	private void atualizaSaldo() {

		int iCodEmp = 0;
		int iCodFilial = 0;

		String sSQL = "SELECT S.DATASL,S.SALDOSL FROM FNSALDOLANCA S WHERE S.CODPLAN=?" + " AND S.CODEMP=? AND S.CODFILIAL=? AND S.CODEMPPN=? AND S.CODFILIALPN=?" + " AND S.DATASL=(SELECT MAX(S1.DATASL)" + " FROM FNSALDOLANCA S1 WHERE S1.DATASL <= ? AND S1.CODPLAN=S.CODPLAN"
				+ " AND S1.CODEMP=S.CODEMP AND S1.CODFILIAL=S.CODFILIAL" + " AND S1.CODEMPPN=S.CODEMPPN AND S1.CODFILIALPN=S.CODFILIALPN)";
		try {
			iCodEmp = Aplicativo.iCodEmp;
			iCodFilial = ListaCampos.getMasterFilial( "FNSALDOLANCA" );
			PreparedStatement ps = con.prepareStatement( sSQL );

			ps.setString( 1, sCodPlan );
			ps.setInt( 2, iCodEmp );
			ps.setInt( 3, iCodFilial );
			ps.setInt( 4, iCodEmp );
			ps.setInt( 5, iCodFilial );
			ps.setDate( 6, Funcoes.dateToSQLDate( dFimLanca ) );

			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				lbDataSaldoVal.setText( StringFunctions.sqlDateToStrDate( rs.getDate( "DataSl" ) ) );
//				lbVlrSaldo.setText( Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "SaldoSl" ) ) );
				txtVlrSaldo.setVlrBigDecimal( rs.getBigDecimal( "SaldoSl" ) );
				lbAtualSaldoVal.setText( "SIM" );
			}
			else {
				lbDataSaldoVal.setText( "" );
				txtVlrSaldo.setVlrBigDecimal( new BigDecimal(0) );
				lbAtualSaldoVal.setText( "SEM" );
			}
			rs.close();
			ps.close();
			// con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao atualizar o saldo!\n" + err.getMessage(), true, con, err );
		}
	}

	private void prim() {

		if ( ( tab != null ) & ( tab.getNumLinhas() > 0 ) )
			tab.setLinhaSel( 0 );
	}

	private void ant() {

		int iLin = 0;
		if ( ( tab != null ) & ( tab.getNumLinhas() > 0 ) ) {
			iLin = tab.getLinhaSel();
			if ( iLin > 0 )
				tab.setLinhaSel( iLin - 1 );
		}
	}

	private void prox() {

		int iLin = 0;
		if ( ( tab != null ) & ( tab.getNumLinhas() > 0 ) ) {
			iLin = tab.getLinhaSel();
			if ( iLin < ( tab.getNumLinhas() - 1 ) )
				tab.setLinhaSel( iLin + 1 );
		}
	}

	private void ult() {

		if ( ( tab != null ) & ( tab.getNumLinhas() > 0 ) )
			tab.setLinhaSel( tab.getNumLinhas() - 1 );
	}

	private boolean validaPeriodo() {

		boolean bRetorno = false;
		if ( txtDataini.getText().trim().length() == 0 ) {
		}
		else if ( txtDataini.getText().trim().length() < 10 ) {
			Funcoes.mensagemInforma( this, "Data inicial inv�lida!" );
		}
		else if ( txtDatafim.getText().trim().length() < 10 ) {
			Funcoes.mensagemInforma( this, "Data final inv�lida!" );
		}
		else if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final inicial que a data final!" );
		}
		else {
			dIniLanca = txtDataini.getVlrDate();
			dFimLanca = txtDatafim.getVlrDate();
			bRetorno = true;
		}
		return bRetorno;
	}

	private void excluir() {

		if ( ( tab.getLinhaSel() >= 0 ) & ( Funcoes.mensagemConfirma( this, "Deseja realmente excluir este lancamento?" ) == 0 ) ) {
			try {
				PreparedStatement ps = con.prepareStatement( "DELETE FROM FNLANCA WHERE CODLANCA=? AND CODEMP=? AND CODFILIAL=?" );
				ps.setString( 1, (String) tab.getValor( tab.getLinhaSel(), 0 ) );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "FNLANCA" ) );
				ps.executeUpdate();
				ps.close();
				con.commit();
				montaTabela( dIniLanca, dFimLanca );
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao excluir o lan�amento!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	private void novo() {

		if ( validaPeriodo() ) {
			Container cont = getContentPane();
			while ( true ) {
				if ( cont instanceof FPrincipal )
					break;
				cont = cont.getParent();
			}
			if ( ! ( (FPrincipal) cont ).temTela( "FSubLanca" ) ) {
				FSubLanca form = new FSubLanca( null, sCodPlan, dIniLanca, dFimLanca );
				( (FPrincipal) cont ).criatela( "FSubLanca", form, con );
				form.addInternalFrameListener( new InternalFrameAdapter() {

					public void internalFrameClosed( InternalFrameEvent ievt ) {

						adicLanca( ( (FSubLanca) ievt.getSource() ).getValores() );
					}
				} );
			}
		}
	}

	/*
	 * Se o lancamento nao for uma transferencia e nao ter origem nesta conta ele nao pode ser editado
	 */
	private void editar() {

		if ( ( tab.getLinhaSel() >= 0 ) & ( validaPeriodo() ) ) {
			if ( ( tab.getValor( tab.getLinhaSel(), 3 ).equals( "N" ) ) & ( tab.getValor( tab.getLinhaSel(), 2 ).equals( "N" ) ) ) {
				Funcoes.mensagemInforma( this, "Este lan�amento n�o pode ser editato nesta conta!" );
			}
			else if ( ( tab.getValor( tab.getLinhaSel(), 3 ).equals( "N" ) ) & ( tab.getValor( tab.getLinhaSel(), 2 ).equals( "S" ) ) ) {
				DLDataTransf dl = new DLDataTransf( this );
				dl.setVisible( true );
				if ( !dl.OK ) {
					dl.dispose();
					return;
				}
				Date dDtNova = dl.getValor();
				dl.dispose();
				String sSQL = "UPDATE FNSUBLANCA SET DATASUBLANCA=? WHERE CODLANCA = ? AND CODEMP=? AND CODFILIAL=? AND CODSUBLANCA > 0";
				try {
					PreparedStatement ps = con.prepareStatement( sSQL );
					ps.setDate( 1, Funcoes.dateToSQLDate( dDtNova ) );
					ps.setInt( 2, Integer.parseInt( (String) tab.getValor( tab.getLinhaSel(), 0 ) ) );
					ps.setInt( 3, Aplicativo.iCodEmp );
					ps.setInt( 4, ListaCampos.getMasterFilial( "FNSUBLANCA" ) );
					ps.executeUpdate();
					ps.close();
					con.commit();
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro ao atualizar a data da transfer�ncia!\n" + err.getMessage(), true, con, err );
				}
				tab.setValor( Funcoes.dateToStrDate( dDtNova ), tab.getLinhaSel(), 1 );

			}
			else {
				Container cont = getContentPane();
				while ( true ) {
					if ( cont instanceof FPrincipal )
						break;
					cont = cont.getParent();
				}
				if ( ! ( (FPrincipal) cont ).temTela( "FSubLanca" ) ) {
					FSubLanca form = new FSubLanca( (String) tab.getValor( tab.getLinhaSel(), 0 ), sCodPlan, dIniLanca, dFimLanca );
					( (FPrincipal) cont ).criatela( "FSubLanca", form, con );
					form.addInternalFrameListener( new InternalFrameAdapter() {

						public void internalFrameClosed( InternalFrameEvent ievt ) {

							altLanca( ( (FSubLanca) ievt.getSource() ).getValores() );
						}
					} );
				}
			}
		}
	}

	private void adicLanca( String[] sVals ) {

		int iLin = -1;
		if ( ( sVals[ 0 ].length() > 0 ) && ( testaCodLanca( Integer.parseInt( sVals[ 0 ] ) ) ) && ( sCodPlan.equals( sVals[ 6 ] ) ) && ( !dIniLanca.after( Funcoes.strDateToDate( sVals[ 1 ] ) ) ) && ( !dFimLanca.before( Funcoes.strDateToDate( sVals[ 1 ] ) ) ) ) {
			for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
				if ( ( (String) tab.getValor( i, 0 ) ).trim().equals( sVals[ 0 ] ) ) {
					tab.tiraLinha( i );
					break;
				}
			}
			tab.adicLinha();
			iLin = tab.getNumLinhas() - 1;
			tab.setValor( sVals[ 0 ], iLin, 0 );
			tab.setValor( sVals[ 1 ], iLin, 1 );
			tab.setValor( sVals[ 2 ], iLin, 2 );
			tab.setValor( "S", iLin, 3 );
			if ( sVals[ 2 ].equals( "S" ) )
				tab.setValor( sConta, iLin, 4 );
			else
				tab.setValor( "", iLin, 4 );
			tab.setValor( sVals[ 3 ], iLin, 5 );
			tab.setValor( sVals[ 4 ], iLin, 6 );
			tab.setValor( sVals[ 5 ], iLin, 7 );
		}
		lbAtualSaldoVal.setText( "N�O" );
	}

	private void altLanca( String[] sVals ) {

		int iLin = -1;
		if ( ( sCodPlan.equals( sVals[ 6 ] ) ) & ( !dIniLanca.after( Funcoes.strDateToDate( sVals[ 1 ] ) ) ) & ( !dFimLanca.before( Funcoes.strDateToDate( sVals[ 1 ] ) ) ) ) {
			for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
				if ( ( (String) tab.getValor( i, 0 ) ).trim().equals( sVals[ 0 ] ) ) {
					iLin = i;
					break;
				}
			}
			tab.setValor( sVals[ 0 ], iLin, 0 );
			tab.setValor( sVals[ 1 ], iLin, 1 );
			tab.setValor( sVals[ 2 ], iLin, 2 );
			tab.setValor( "S", iLin, 3 );
			if ( sVals[ 2 ].equals( "S" ) )
				tab.setValor( sConta, iLin, 4 );
			tab.setValor( sVals[ 3 ], iLin, 5 );
			tab.setValor( sVals[ 4 ], iLin, 6 );
			tab.setValor( sVals[ 5 ], iLin, 7 );
		}
		lbAtualSaldoVal.setText( "N�O" );
	}

	private boolean testaCodLanca( int iCodLanca ) {

		boolean bRetorno = false;
		try {
			PreparedStatement ps = con.prepareStatement( "SELECT CODLANCA FROM FNLANCA WHERE CODLANCA=? AND CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, iCodLanca );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNLANCA" ) );
			if ( ( ps.executeQuery() ).next() )
				bRetorno = true;

			// con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao testar o c�digo do lna�amento!\n" + err.getMessage(), true, con, err );
		}
		return bRetorno;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair )
			dispose();
		else if ( evt.getSource() == btPrim )
			prim();
		else if ( evt.getSource() == btAnt )
			ant();
		else if ( evt.getSource() == btProx )
			prox();
		else if ( evt.getSource() == btUlt )
			ult();
		else if ( evt.getSource() == btNovo )
			novo();
		else if ( evt.getSource() == btEditar )
			editar();
		else if ( evt.getSource() == btExcluir )
			excluir();
		else if ( evt.getSource() == btExec ) {
			if ( validaPeriodo() ) {
				montaTabela( dIniLanca, dFimLanca );
			}
		}
		else if ( evt.getSource() == btCalcSaldo ) {
			if ( validaPeriodo() ) {
				atualizaSaldo();
				atualizaSaldoComposto();
			}
		}
		else if(evt.getSource() == btAbreCheque ) {
			abreCheque( );
		}
		
	}

	public void stateChanged( ChangeEvent cevt ) {

		sCodPlan = sPlanos[ tpn.getSelectedIndex() ];
		sConta = sContas[ tpn.getSelectedIndex() ];
		if ( validaPeriodo() ) {
			montaTabela( dIniLanca, dFimLanca );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		montaTabs();
	}

	@ SuppressWarnings ( "unchecked" )
	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getSource() == tab && mevt.getClickCount() == 2 ) {
			editar();
		}
		else if ( mevt.getSource() == tab && mevt.getClickCount() == 1 ) {
					
			btAbreCheque.setEnabled( false );
					
			if( tab.getValor( tab.getLinhaSel(), enum_tab_lanca.SEQCHEQ.ordinal())!=null && !"".equals( tab.getValor( tab.getLinhaSel(), enum_tab_lanca.SEQCHEQ.ordinal())) ) {
						
				Vector<String> seqcheq = (Vector<String>) tab.getValor( tab.getLinhaSel(), enum_tab_lanca.SEQCHEQ.ordinal()); 
						
				if(seqcheq!=null) {
					Integer seqcheque = Integer.parseInt( (String) seqcheq.elementAt( 0 ) );
							
					if(seqcheque>0) {
						btAbreCheque.setEnabled( true );
					}
				}
			}
					
		}
		
	}

	public void mouseEntered( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mouseExited( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mousePressed( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}

	public void mouseReleased( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}
}
