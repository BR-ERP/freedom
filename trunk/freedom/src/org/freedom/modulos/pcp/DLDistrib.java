/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLDistrib.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldPad;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLDistrib extends FFDialogo implements MouseListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private int casasDec = Aplicativo.casasDec;

	private JTextFieldPad txtCodOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSeqOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodProdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRefProdEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtSeqEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtQtdDist = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, casasDec );

	private JTextFieldPad txtQtdPrev = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, casasDec );

	private JTextFieldPad txtQtdDistpOp = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, casasDec );

	private JTextFieldPad txtQtdProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, casasDec );

	private JPanelPad pnDistrib = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCab = new JPanelPad( 400, 60 );

	private JTablePad tabDistrib = new JTablePad();
	
	private String lotePrincipal = null;


	public DLDistrib( DbConnection cn, Component cOrig, boolean bPref ) {
		
		super( cOrig );
		setConexao( cn );
		setTitulo( "Distribui��o" );
		setAtribos( 757, 380 );

		pinCab.setPreferredSize( new Dimension( 400, 100 ) );
		pnDistrib.add( pinCab, BorderLayout.NORTH );
		JScrollPane spnTabRec = new JScrollPane( tabDistrib );
		pnDistrib.add( spnTabRec, BorderLayout.CENTER );
		c.add( pnDistrib, BorderLayout.CENTER );

		setPainel( pinCab );

		adic( new JLabelPad( "N�.OP." ), 7, 0, 80, 20 );
		adic( txtCodOP, 7, 20, 80, 20 );
		adic( new JLabelPad( "Seq.OP." ), 90, 0, 80, 20 );
		adic( txtSeqOP, 90, 20, 80, 20 );
		if ( bPref ) {
			adic( new JLabelPad( "Refer�ncia" ), 173, 0, 80, 20 );
			adic( txtRefProdEst, 173, 20, 80, 20 );
		}
		else {
			adic( new JLabelPad( "C�d.prod." ), 173, 0, 80, 20 );
			adic( txtCodProdEst, 173, 20, 80, 20 );
		}

		adic( new JLabelPad( "Qtd.prev." ), 256, 0, 80, 20 );
		adic( txtQtdPrev, 256, 20, 80, 20 );

		adic( new JLabelPad( "Qtd.prod." ), 339, 0, 80, 20 );
		adic( txtQtdProd, 339, 20, 80, 20 );

		adic( new JLabelPad( "Qtd.dist." ), 422, 0, 80, 20 );
		adic( txtQtdDistpOp, 422, 20, 80, 20 );

		adic( new JLabelPad( "Qtd.dist.at." ), 505, 0, 80, 20 );
		adic( txtQtdDist, 505, 20, 80, 20 );

		adic( new JLabelPad( "Seq.Est." ), 7, 40, 80, 20 );
		adic( txtSeqEst, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descri��o da estrutura principal" ), 90, 40, 250, 20 );
		adic( txtDescEst, 90, 60, 300, 20 );

		txtCodOP.setAtivo( false );
		txtSeqOP.setAtivo( false );
		txtCodProdEst.setAtivo( false );
		txtRefProdEst.setAtivo( false );
		txtSeqEst.setAtivo( false );
		txtDescEst.setAtivo( false );
		txtQtdProd.setAtivo( false );
		txtQtdPrev.setAtivo( false );
		txtQtdDist.setAtivo( false );
		txtQtdDistpOp.setAtivo( false );

		tabDistrib.adicColuna( "" ); // seq.fase 0
		tabDistrib.adicColuna( "" );// cod fase 1
		tabDistrib.adicColuna( "" );// Descri��o da fase 2
		tabDistrib.adicColuna( "Seq." );// 3
		tabDistrib.adicColuna( "C�d.prod." );// 4
		tabDistrib.adicColuna( "Descri��o da estrutura" );// 5
		tabDistrib.adicColuna( "" );// Seq.est.dist. 6
		tabDistrib.adicColuna( "Qtd.distrib" );// 7
		tabDistrib.adicColuna( "Fator conv." );// 8
		tabDistrib.adicColuna( "Qtd.final" );// 9
		tabDistrib.adicColuna( "Lote" );// 10
		tabDistrib.adicColuna( "Validate" );// 11
		tabDistrib.adicColuna( "" );// C�d.almox 12
		tabDistrib.adicColuna( "" );// 13
		tabDistrib.adicColuna( "" );// 14

		tabDistrib.setTamColuna( 40, 3 );
		tabDistrib.setTamColuna( 70, 4 );
		tabDistrib.setTamColuna( 250, 5 );
		tabDistrib.setTamColuna( 60, 7 );
		tabDistrib.setTamColuna( 60, 10 );

		tabDistrib.setColunaInvisivel( 0 );
		tabDistrib.setColunaInvisivel( 1 );
		tabDistrib.setColunaInvisivel( 2 );
		tabDistrib.setColunaInvisivel( 6 );
		tabDistrib.setColunaInvisivel( 12 );
		tabDistrib.setColunaInvisivel( 13 );
		tabDistrib.setColunaInvisivel( 14 );

		tabDistrib.addMouseListener( this );
		// btOK.addActionListener(this);
	}

	public void mouseClicked( MouseEvent mevt ) {
		if ( mevt.getClickCount() == 2 ) {
			if ( mevt.getSource() == tabDistrib && tabDistrib.getLinhaSel() >= 0 ) {
				alteraDistrib();
			}
		}
	}

	public void alteraDistrib() {
		
		int iLinha = tabDistrib.getLinhaSel();
		int iCodProd = 0;
		int iSeqDist = 0;
		int iCodProdPrinc = 0;
		int iSeqProdPrinc = 0;
		float ftQtdade = 0;
		float ftQtdant = 0;
		float ftQtddig = 0;
		float ftQtddist = 0;
		float ftQtddistp = 0;
		float ftQtdprod = 0;
		float ftQtdprev = 0;
		float ftQtdutil = 0;
		float ftFator = 0;
		float ftFinal = 0;
		String sDescProd = null;
		DLFechaDistrib dl = null;
		boolean ok = false;
					
		iSeqDist = ( (Integer) tabDistrib.getValor( iLinha, 6 ) ).intValue();
		iCodProd = ( (Integer) tabDistrib.getValor( iLinha, 4 ) ).intValue();
		iCodProdPrinc = txtCodProdEst.getVlrInteger().intValue();
		iSeqProdPrinc = txtSeqEst.getVlrInteger().intValue();
		sDescProd = ( (String) tabDistrib.getValor( iLinha, 5 ) );
		ftQtdade = ( (BigDecimal) tabDistrib.getValor( iLinha, 7 ) ).floatValue();
		ftQtdant = ftQtdade;
		ftFator = ( (BigDecimal) tabDistrib.getValor( iLinha, 8 ) ).floatValue();
		ftQtddistp = txtQtdDistpOp.getVlrBigDecimal().floatValue();
		
		while ( !ok ) {
			
			dl = new DLFechaDistrib( DLDistrib.this, iSeqDist, iCodProd, sDescProd, ftQtdade );
			
			try {
				
				dl.setLotePrincipal( lotePrincipal );
				dl.setConexao( con );
				dl.setVisible( true );
				
				if ( dl.OK ) {
					
					ftQtddig = ( (BigDecimal) dl.getValor( 0 ) ).floatValue(); // Quantidade digitada
					ftQtddist = getSomaTab(); // Quantidade que j� foi distribuida(soma do valor total)
					ftQtdprod = txtQtdProd.getVlrBigDecimal().floatValue(); // Quantida produzida
					ftQtdprev = txtQtdPrev.getVlrBigDecimal().floatValue(); // Quantida prevista

					ftFinal = ftQtddig * ftFator; // valor total

					if ( ftQtddig > 0 ) {

						if ( ftQtdprod == 0 ) {

							int iresposta = Funcoes.mensagemConfirma( null, 
									  "Quantidade final produzida � inexistente!\n"
									+ "Deseja realizar a distribui��o a partir da quantidade prevista?" );

							if ( iresposta == JOptionPane.YES_OPTION ) {
								ftQtdutil = ftQtdprev;
							}
							else if ( iresposta == JOptionPane.NO_OPTION ) {
								ftQtdutil = ftQtdprod;
							}

						}
						else {
							ftQtdutil = ftQtdprod;
						}

						if ( ftQtdutil < ( ftQtddist + ftQtddistp + ftFinal - ( ftQtdant * ftFator ) ) ) {
							Funcoes.mensagemInforma( this,
									"Quantidade inv�lida! \nQuantidade total de distribui��o ultrapassa a quantidade dispon�vel para distribui��o!" );
							ftQtdade = ( ftQtdutil - ftQtddistp - ftQtddist ) / ftFator + ftQtdant;
						}
						else {
							
							tabDistrib.setValor( new BigDecimal( ftQtddig ), iLinha, 7 );
							tabDistrib.setValor( new BigDecimal( ftFinal ), iLinha, 9 );
							
							ftQtddist = getSomaTab();
							
							txtQtdDist.setVlrBigDecimal( new BigDecimal( ftQtddist ) );
							
							ok = true;
						}
					}
					else {
						Funcoes.mensagemInforma( this, "Quantidade inv�lida! \nQuantida deve ser maior que zero." );
					}
					if ( dl.getUsaModLote() ) {
						
						if ( dl.gravaLote() ) {
							
							tabDistrib.setValor( dl.getValor( 1 ), iLinha, 10 );
							tabDistrib.setValor( dl.getValor( 2 ), iLinha, 11 );
						}
						else {
							ok = false;
						}
					}
					else {
						tabDistrib.setValor( dl.getValor( 1 ), iLinha, 10 );
					}
				}
				else {
					ok = true;
				}
			}
			finally {
				if ( dl != null ) {
					dl.dispose();
				}
			}
		}
	}

	public float getSomaTab() {
		float ftTotal = 0;
		Vector<?> v = null;
		
		for ( int i = 0; i < tabDistrib.getNumLinhas(); i++ ) {
			v = tabDistrib.getLinha( i );
			if ( ( v != null ) && ( v.size() > 10 ) ) {
				if ( v.elementAt( 9 ) != null ) {
					ftTotal += ( (BigDecimal) v.elementAt( 9 ) ).floatValue();
				}
			}
		}

		return ftTotal;
	}

	public void carregaTabela( int iCodop, int iSeqop ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		Vector<Object> vLinha = null;
		int i = 0;
		
		try {
			
			sql = "SELECT D.CODPRODDE, ED.DESCEST, D.SEQEST, D.SEQEF, " + "D.CODFASE, F.DESCFASE, D.SEQDE, D.CODEMPDE, "
					+ "D.CODFILIALDE, D.CODPRODDE, D.SEQESTDE, " + "CAST(ID.QTDITEST*E.QTDEST AS NUMERIC(15," + casasDec + ")),"
					+ "O.QTDDISTPOP, PD.CODALMOX, PD.CODEMPAX, PD.CODFILIALAX " + "FROM PPDISTRIB D, PPOP O, PPESTRUTURA ED, PPFASE F, "
					+ "PPITESTRUTURA ID, PPESTRUTURA E , EQPRODUTO PD " + "WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.CODOP=? AND O.SEQOP=? AND "
					+ "D.CODEMP=O.CODEMPPD AND D.CODFILIAL=O.CODFILIALPD AND " + "D.CODPROD=O.CODPROD AND D.SEQEST=O.SEQEST AND " + "ED.CODEMP=D.CODEMPDE AND "
					+ "ED.CODFILIAL=D.CODFILIALDE AND ED.CODPROD=D.CODPRODDE AND " + "ED.SEQEST=D.SEQESTDE AND F.CODEMP=D.CODEMPFS AND "
					+ "F.CODFILIAL=D.CODFILIALFS AND F.CODFASE=D.CODFASE AND " + "ID.CODEMP=ED.CODEMP AND ID.CODFILIAL=ED.CODFILIAL AND "
					+ "ID.CODPROD=ED.CODPROD AND ID.SEQEST=ED.SEQEST AND " + "ID.CODEMPPD=D.CODEMP AND ID.CODFILIALPD=D.CODFILIAL AND "
					+ "ID.CODPRODPD=D.CODPROD AND E.CODEMP=D.CODEMP AND " + "E.CODFILIAL=D.CODFILIAL AND E.CODPROD=D.CODPROD AND "
					+ "E.SEQEST=D.SEQEST AND D.CODEMPDE=PD.CODEMP AND " + "D.CODFILIALDE=PD.CODFILIAL AND D.CODPRODDE=PD.CODPROD ";
			ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 3, iCodop );
			ps.setInt( 4, iSeqop );
			rs = ps.executeQuery();
			
			while ( rs.next() ) {

				vLinha = new Vector<Object>();
				vLinha.addElement( new Integer( rs.getInt( "SEQEF" ) ) );
				vLinha.addElement( new Integer( rs.getInt( "CODFASE" ) ) );
				vLinha.addElement( rs.getString( "DESCFASE" ) );
				vLinha.addElement( new Integer( rs.getInt( "SEQDE" ) ) );
				vLinha.addElement( new Integer( rs.getInt( "CODPRODDE" ) ) );
				vLinha.addElement( rs.getString( "DESCEST" ) );
				vLinha.addElement( new Integer( rs.getInt( "SEQESTDE" ) ) );
				vLinha.addElement( new BigDecimal( 0 ) );
				vLinha.addElement( rs.getBigDecimal( 12 ) ); // Fator de convers�o
				vLinha.addElement( new BigDecimal( 0 ) );
				vLinha.addElement( "" );
				vLinha.addElement( "" );
				vLinha.addElement( new Integer( rs.getInt( "CODALMOX" ) ) );
				vLinha.addElement( new Integer( rs.getInt( "CODEMPAX" ) ) );
				vLinha.addElement( new Integer( rs.getInt( "CODFILIALAX" ) ) );

				tabDistrib.adicLinha( vLinha );
				if ( i == 0 )
					txtQtdDistpOp.setVlrBigDecimal( new BigDecimal( rs.getFloat( "QTDDISTPOP" ) ) );
				i++;

			}
			rs.close();
			ps.close();
			con.commit();
		}
		catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando distribui��o!\n" + e.getMessage() );
		}
	}

	private int buscaTipoMov() {
		
		int codTipoMov = 0;
		
		try {
			
			PreparedStatement ps = con.prepareStatement( "SELECT CODTIPOMOV FROM SGPREFERE5 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				if ( rs.getString( 1 ) != null ) {
					codTipoMov = rs.getInt( 1 );
				}
				else {
					codTipoMov = 0;
					Funcoes.mensagemInforma( null, "N�o existe um tipo de movimento padr�o para OP definido nas prefer�ncias!" );
				}
			}
			rs.close();
			ps.close();
		}
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar documento de prefer�ncias!\n" + err.getMessage() );
		}
		return codTipoMov;
	}

	private void gravaDistrib() {
		
		Vector<?> linha = null;

		for ( int i = 0; i < tabDistrib.getNumLinhas(); i++ ) {
			linha = tabDistrib.getLinha( i );
			if ( ( (BigDecimal) linha.elementAt( 7 ) ).floatValue() > 0 )
				gravaOp( linha );
		}
	}

	public void geraRMA( int seqop ) {

		String sSQL = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

		try {

			ps2 = con.prepareStatement( "EXECUTE PROCEDURE EQGERARMASP(?,?,?,?)" );
			ps2.setInt( 1, Aplicativo.iCodEmp );
			ps2.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps2.setInt( 3, txtCodOP.getVlrInteger().intValue() );
			ps2.setInt( 4, seqop );
			ps2.execute();
			ps2.close();

			con.commit();

			try {
				ps3 = con.prepareStatement( "SELECT CODRMA FROM EQRMA WHERE CODEMP=? AND CODFILIAL=? AND CODEMPOF=CODEMP AND CODFILIALOF=? AND CODOP=? AND SEQOP=?" );
				ps3.setInt( 1, Aplicativo.iCodEmp );
				ps3.setInt( 2, ListaCampos.getMasterFilial( "PPITOP" ) );
				ps3.setInt( 3, ListaCampos.getMasterFilial( "PPOP" ) );
				ps3.setInt( 4, txtCodOP.getVlrInteger().intValue() );
				ps3.setInt( 5, seqop );

				rs2 = ps3.executeQuery();
				String sRma = "";

				while ( rs2.next() ) {
					sRma += rs2.getString( 1 ) + " - ";
				}

				if ( sRma.length() > 0 ) {
					Funcoes.mensagemInforma( this, "Foram geradas as seguintes RMA para a O.P. Secund�ria:\n" + sRma );
				}

				rs2.close();
			}
			catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro ao buscar RMA criada", true, con, err );
				err.printStackTrace();
			}

			con.commit();
		}
		catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar RMA", true, con, err );
			err.printStackTrace();
		}
	}

	private void gravaOp( Vector<?> op ) {
		
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;
		int seqop = 0;
		Date dtFabrOP = null;

		try {

			sql = "SELECT MAX(SEQOP) FROM PPOP WHERE CODEMP=? AND CODFILIAL=? AND CODOP=?";
			ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 3, txtCodOP.getVlrInteger().intValue() );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				seqop = rs.getInt( 1 ) + 1;
			}
			rs.close();
			ps.close();
			con.commit();

			sql = "SELECT DTFABROP FROM PPOP WHERE CODEMP=? AND CODFILIAL=? AND CODOP=? AND SEQOP=?";
			ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 3, txtCodOP.getVlrInteger().intValue() );
			ps.setInt( 4, txtSeqOP.getVlrInteger().intValue() );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				dtFabrOP = rs.getDate( 1 );
			}
			rs.close();
			ps.close();
			con.commit();

			sql = "INSERT INTO PPOP (CODEMP,CODFILIAL,CODOP,SEQOP,CODEMPPD,CODFILIALPD,CODPROD,SEQEST,DTFABROP,"
					+ "QTDPREVPRODOP,QTDFINALPRODOP,DTVALIDPDOP,CODEMPLE,CODFILIALLE,CODLOTE,CODEMPTM,CODFILIALTM,CODTIPOMOV,"
					+ "CODEMPAX,CODFILIALAX,CODALMOX,CODEMPOPM,CODFILIALOPM,CODOPM,SEQOPM,QTDDISTIOP,QTDSUGPRODOP)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 3, txtCodOP.getVlrInteger().intValue() );
			ps.setInt( 4, seqop );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "PPESTRUTURA" ) );
			ps.setInt( 7, ( (Integer) op.elementAt( 4 ) ).intValue() ); // C�digo do produto
			ps.setInt( 8, ( (Integer) op.elementAt( 6 ) ).intValue() ); // Sequencia da estrutura
			ps.setDate( 9, dtFabrOP ); // Data de fabrica��o
			ps.setFloat( 10, ( (BigDecimal) op.elementAt( 7 ) ).floatValue() ); // Qtdade prevista
			ps.setFloat( 11, 0 ); // Quantidade produzida
			ps.setDate( 12, ( Funcoes.strDateToSqlDate( (String) op.elementAt( 11 ) ) ) ); // data de validade
			ps.setInt( 13, Aplicativo.iCodEmp );
			ps.setInt( 14, ListaCampos.getMasterFilial( "EQLOTE" ) );
			ps.setString( 15, ( (String) op.elementAt( 10 ) ) ); // lote
			ps.setInt( 16, Aplicativo.iCodEmp );
			ps.setInt( 17, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			ps.setInt( 18, buscaTipoMov() ); // tipo de movimento
			ps.setInt( 19, ( (Integer) op.elementAt( 13 ) ).intValue() );
			ps.setInt( 20, ( (Integer) op.elementAt( 14 ) ).intValue() );
			ps.setInt( 21, ( (Integer) op.elementAt( 12 ) ).intValue() ); // C�digo do almoxarifado
			ps.setInt( 22, Aplicativo.iCodEmp );
			ps.setInt( 23, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setInt( 24, txtCodOP.getVlrInteger().intValue() ); // CODOP Principal
			ps.setInt( 25, txtSeqOP.getVlrInteger().intValue() ); // SEQOP Principal
			ps.setFloat( 26, ( (BigDecimal) op.elementAt( 9 ) ).floatValue() ); // Qtdade distribu�da
			ps.setFloat( 27, ( (BigDecimal) op.elementAt( 7 ) ).floatValue() ); // Qtdade sugerida

			ps.executeUpdate();
			ps.close();
			con.commit();

			geraRMA( seqop );

		}
		catch ( SQLException e ) {
			Funcoes.mensagemErro( null, "Erro ao gerar OP's de distribui��o!\n" + e.getMessage() );
			try {
				con.rollback();
			}
			catch ( SQLException eb ) {
			}
		}

	}

	public void carregaCampos( Object[] sValores ) {
		
		txtCodOP.setVlrInteger( (Integer) sValores[ 0 ] );
		txtSeqOP.setVlrInteger( (Integer) sValores[ 1 ] );
		txtCodProdEst.setVlrInteger( (Integer) sValores[ 2 ] );
		txtRefProdEst.setVlrString( (String) sValores[ 3 ] );
		txtSeqEst.setVlrInteger( (Integer) sValores[ 4 ] );
		txtDescEst.setVlrString( (String) sValores[ 5 ] );
		txtQtdProd.setVlrBigDecimal( (BigDecimal) sValores[ 6 ] );
		txtQtdPrev.setVlrBigDecimal( (BigDecimal) sValores[ 7 ] );
		txtQtdDist.setVlrBigDecimal( new BigDecimal( 0 ) );
		lotePrincipal = (String) sValores[ 8 ];
	}

	public void actionPerformed( ActionEvent evt ) {
		super.actionPerformed( evt );
		if ( evt.getSource() == btOK ) {
			gravaDistrib();
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

}
