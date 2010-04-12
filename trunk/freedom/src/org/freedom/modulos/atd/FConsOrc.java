/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.atd <BR>
 *         Classe: @(#)FConsOrc.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Formul�rio de consulta de or�amento.
 * 
 */

package org.freedom.modulos.atd;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTablePad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.std.view.frame.detail.FOrcamento;

public class FConsOrc extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 0, 260 );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodEnc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNomeEnc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeConv = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCid = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JLabelPad lbBorda = new JLabelPad();

	private JCheckBoxPad cbAberto = new JCheckBoxPad( "Aberto", "S", "N" );

	private JCheckBoxPad cbCompleto = new JCheckBoxPad( "Completo", "S", "N" );

	private JCheckBoxPad cbLiberado = new JCheckBoxPad( "Liberado", "S", "N" );

	private JCheckBoxPad cbFaturadoParcial = new JCheckBoxPad( "Faturado parcial", "S", "N" );

	private JCheckBoxPad cbFaturado = new JCheckBoxPad( "Faturado", "S", "N" );

	private JCheckBoxPad cbConveniado = new JCheckBoxPad( "Listar conveniados", "S", "N" );

	private JRadioGroup<?, ?> gbVenc;

	private JTablePad tab = new JTablePad();

	private JButtonPad btPrevimp = new JButtonPad( "Imprimir", Icone.novo( "btPrevimp.gif" ) );

	private JButtonPad btBusca = new JButtonPad( "Buscar", Icone.novo( "btPesquisa.gif" ) );

	private JScrollPane spnTab = new JScrollPane( tab );

	private ListaCampos lcConv = new ListaCampos( this, "PR" );

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcTipoConv = new ListaCampos( this, "AT" );

	private ListaCampos lcEnc = new ListaCampos( this, "EC" );

	private JTextFieldPad txtCodTpConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoConv = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	public FConsOrc() {

		super( false );
		setTitulo( "Pesquisa Or�amentos" );
		setAtribos( 10, 10, 605, 525 );

		txtDtIni.setRequerido( true );
		txtDtFim.setRequerido( true );
		lbBorda.setBorder( BorderFactory.createEtchedBorder() );

		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "D" );
		vVals.addElement( "V" );
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "Data de emiss�o" );
		vLabs.addElement( "Data de validade" );
		gbVenc = new JRadioGroup<String, String>( 2, 1, vLabs, vVals );

		lcConv.add( new GuardaCampo( txtCodConv, "CodConv", "C�d.conv.", ListaCampos.DB_PK, false ) );
		lcConv.add( new GuardaCampo( txtNomeConv, "NomeConv", "Nome do conveniado", ListaCampos.DB_SI, false ) );
		txtCodConv.setTabelaExterna( lcConv );
		txtCodConv.setNomeCampo( "CodConv" );
		txtCodConv.setFK( true );
		lcConv.setReadOnly( true );
		lcConv.montaSql( false, "CONVENIADO", "AT" );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );

		lcTipoConv.add( new GuardaCampo( txtCodTpConv, "CodTpConv", "C�d.tp.conv.", ListaCampos.DB_PK, false ) );
		lcTipoConv.add( new GuardaCampo( txtDescTipoConv, "DescTpConv", "Descri��o do tipo de conveniado", ListaCampos.DB_SI, false ) );
		txtCodTpConv.setTabelaExterna( lcTipoConv );
		txtCodTpConv.setNomeCampo( "CodTpConv" );
		txtCodTpConv.setFK( true );
		lcTipoConv.setReadOnly( true );
		lcTipoConv.montaSql( false, "TIPOCONV", "AT" );

		lcEnc.add( new GuardaCampo( txtCodEnc, "CodEnc", "C�d.enc.", ListaCampos.DB_PK, false ) );
		lcEnc.add( new GuardaCampo( txtNomeEnc, "NomeEnc", "Descri��o do encaminhador", ListaCampos.DB_SI, false ) );
		txtCodEnc.setTabelaExterna( lcEnc );
		txtCodEnc.setNomeCampo( "CodEnc" );
		txtCodEnc.setFK( true );
		lcEnc.setReadOnly( true );
		lcEnc.montaSql( false, "ENCAMINHADOR", "AT" );

		getTela().add( pnCli, BorderLayout.CENTER );
		pnCli.add( pinCab, BorderLayout.NORTH );
		pnCli.add( spnTab, BorderLayout.CENTER );
		adicBotaoSair();

		pinCab.adic( new JLabelPad( "Per�odo:" ), 380, 0, 90, 20 );
		pinCab.adic( txtDtIni, 380, 20, 87, 20 );
		pinCab.adic( new JLabelPad( "At�" ), 470, 20, 27, 20 );
		pinCab.adic( txtDtFim, 494, 20, 87, 20 );

		pinCab.adic( new JLabelPad( "C�d.cli." ), 7, 0, 280, 20 );
		pinCab.adic( txtCodCli, 7, 20, 70, 20 );
		pinCab.adic( new JLabelPad( "Raz�o social do cliente" ), 80, 0, 280, 20 );
		pinCab.adic( txtNomeCli, 80, 20, 294, 20 );

		pinCab.adic( new JLabelPad( "C�d.conv." ), 7, 40, 280, 20 );
		pinCab.adic( txtCodConv, 7, 60, 70, 20 );
		pinCab.adic( new JLabelPad( "Nome do conveniado" ), 80, 40, 280, 20 );
		pinCab.adic( txtNomeConv, 80, 60, 294, 20 );

		pinCab.adic( new JLabelPad( "Cidade" ), 380, 40, 60, 20 );
		pinCab.adic( txtCid, 380, 60, 200, 20 );
		pinCab.adic( cbConveniado, 390, 99, 180, 20 );

		pinCab.adic( new JLabelPad( "C�d.tp.conv." ), 7, 80, 250, 20 );
		pinCab.adic( txtCodTpConv, 7, 100, 70, 20 );
		pinCab.adic( new JLabelPad( "Descri��o do tipo de conveniado" ), 80, 80, 250, 20 );
		pinCab.adic( txtDescTipoConv, 80, 100, 294, 20 );

		pinCab.adic( new JLabelPad( "C�d.enc." ), 7, 122, 250, 20 );
		pinCab.adic( txtCodEnc, 7, 142, 70, 20 );
		pinCab.adic( new JLabelPad( "Descri��o do Encaminhador" ), 80, 122, 250, 20 );
		pinCab.adic( txtNomeEnc, 80, 142, 294, 20 );

		JLabel status = new JLabel( "Status", SwingConstants.CENTER );
		status.setOpaque( true );
		JLabel borda2 = new JLabel();
		borda2.setBorder( BorderFactory.createEtchedBorder() );
		pinCab.adic( status, 15, 165, 50, 18 );
		pinCab.adic( borda2, 7, 175, 266, 70 );
		pinCab.adic( cbAberto, 25, 180, 80, 20 );
		pinCab.adic( cbCompleto, 25, 200, 80, 20 );
		pinCab.adic( cbLiberado, 25, 220, 80, 20 );
		pinCab.adic( cbFaturadoParcial, 140, 180, 120, 20 );
		pinCab.adic( cbFaturado, 140, 200, 80, 20 );
		
		pinCab.adic( new JLabel( "Filtrar por:" ), 290, 164, 110, 20 );
		pinCab.adic( gbVenc, 290, 185, 150, 60 );

		pinCab.adic( btBusca, 460, 167, 120, 30 );
		pinCab.adic( btPrevimp, 460, 210, 120, 30 );

		cbAberto.setVlrString( "S" );

		txtDtIni.setVlrDate( new Date() );
		txtDtFim.setVlrDate( new Date() );

		tab.adicColuna( "Status" );
		tab.adicColuna( "Orc." );
		tab.adicColuna( "C�d.cli." );
		tab.adicColuna( "Raz�o social do cliente/conveniado" );
		tab.adicColuna( "Tipo de conveniado" );
		tab.adicColuna( "Encaminhador" );
		tab.adicColuna( "Data" );
		tab.adicColuna( "Validade" );
		tab.adicColuna( "Cidade" );
		tab.adicColuna( "Telefone" );

		tab.setTamColuna( 50, 0 );
		tab.setTamColuna( 50, 1 );
		tab.setTamColuna( 60, 2 );
		tab.setTamColuna( 220, 3 );
		tab.setTamColuna( 150, 4 );
		tab.setTamColuna( 150, 5 );
		tab.setTamColuna( 70, 6 );
		tab.setTamColuna( 70, 7 );
		tab.setTamColuna( 90, 8 );
		tab.setTamColuna( 100, 9 );

		btBusca.addActionListener( this );
		btPrevimp.addActionListener( this );
		tab.addMouseListener( new MouseAdapter() {

			public void mouseClicked( MouseEvent mevt ) {

				if ( mevt.getSource() == tab && mevt.getClickCount() == 2 )
					abreOrc();
			}
		} );
	}

	/**
	 * 
	 * Carrega os valores para a tabela de consulta. Este m�todo � executado ap�s carregar o ListaCampos da tabela.
	 * 
	 */
	private void carregaTabela() {

		StringBuilder sql = new StringBuilder();
		StringBuilder where = new StringBuilder();
		StringBuilder status = new StringBuilder();

		int iLin = 0;
		boolean bUsaOr = false;
		boolean bUsaWhere = false;
		boolean bConv = ( txtCodConv.getVlrInteger().intValue() > 0 );
		boolean bCli = ( txtCodCli.getVlrInteger().intValue() > 0 );
		boolean bEnc = ( txtCodEnc.getVlrInteger().intValue() > 0 );

		if ( "S".equals( cbAberto.getVlrString() ) ) {
			status.append( "'*','OA'" );
		}
		if ( "S".equals( cbCompleto.getVlrString() ) ) {
			if ( status.length() > 0 ) {
				status.append( "," );
			}
			status.append( "'OC'" );
		}
		if ( "S".equals( cbLiberado.getVlrString() ) ) {
			if ( status.length() > 0 ) {
				status.append( "," );
			}
			status.append( "'OL'" );
		}
		if ( "S".equals( cbFaturadoParcial.getVlrString() ) ) {
			if ( status.length() > 0 ) {
				status.append( "," );
			}
			status.append( "'FP'" );
		}
		if ( "S".equals( cbFaturado.getVlrString() ) ) {
			if ( status.length() > 0 ) {
				status.append( "," );
			}
			status.append( "'OV'" );
		}

		if ( status.length() > 0 ) {
			where.append( " AND O.STATUSORC IN(" + status.toString() + ") " );
		}

		if ( gbVenc.getVlrString().equals( "V" ) ) {
			where.append( " AND DTVENCORC BETWEEN ? AND ?" );
		}
		else {
			where.append( " AND DTORC BETWEEN ? AND ?" );
		}

		if ( bConv ) {
			where.append( " AND O.CODCONV=? AND O.CODEMPCV=O.CODEMP AND O.CODFILIALCV=O.CODFILIAL " );
		}
		if ( bCli ) {
			where.append( " AND O.CODCLI=" + txtCodCli.getVlrString() + " AND O.CODEMPCV=O.CODEMP AND CODFILIALCV=O.CODFILIAL " );
		}
		if ( bEnc ) {
			where.append( " AND O.CODCONV=C.CODCONV AND O.CODEMPCV=C.CODEMP AND O.CODFILIALCV=C.CODFILIAL " );
			where.append( "AND C.CODENC=" + txtCodEnc.getVlrString() );
			where.append( "AND C.CODEMPEC=O.CODEMP AND C.CODFILIALEC=" + lcEnc.getCodFilial() );
		}

		if ( ! "".equals( txtCid.getVlrString() ) ) {
			where.append( " AND C.CIDCONV  = '" + txtCid.getVlrString() + "'" );
		}

		if ( !txtCodTpConv.getVlrString().trim().equals( "" ) ) {
			where.append( "AND EXISTS(" );
			where.append( "    SELECT T2.CODTPCONV FROM ATTIPOCONV T2, ATCONVENIADO C2 " );
			where.append( "    WHERE T2.CODEMP=C2.CODEMPTC AND T2.CODFILIAL=C2.CODFILIALTC AND T2.CODTPCONV=C2.CODTPCONV AND " );
			where.append( "    C2.CODEMP=O.CODEMPCV AND C2.CODFILIAL=O.CODFILIALCV AND C2.CODCONV=O.CODCONV AND " );
			where.append( "    T2.CODEMP=" + Aplicativo.iCodEmp + " AND T2.CODFILIAL=" + ListaCampos.getMasterFilial( "ATTIPOCONV" ) + " AND " );
			where.append( "    T2.CODTPCONV=" + txtCodTpConv.getVlrString().trim() + " ) " );
		}

		try {

			sql.append( "SELECT O.STATUSORC,O.CODORC,O.DTORC,O.DTVENCORC,O.CODCONV,C.NOMECONV,O.CODCLI," );
			sql.append( "CL.NOMECLI,C.FONECONV ,T.DESCTPCONV, IT.VENCAUTORIZORC,IT.NUMAUTORIZORC,IT.CODPROD," ); 
			sql.append( "P.CODBARPROD,P.DESCPROD,C.CIDCONV, CL.CIDCLI,(SELECT EC.NOMEENC FROM ATENCAMINHADOR EC " );
			sql.append( "WHERE EC.CODENC=C.CODENC AND EC.CODEMP=C.CODEMPEC AND EC.CODFILIAL=C.CODFILIALEC) " ); 
			sql.append( "FROM VDORCAMENTO O,VDCLIENTE CL,ATCONVENIADO C, EQPRODUTO P, VDITORCAMENTO IT,ATTIPOCONV T " );
			sql.append( "WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.TIPOORC='O' AND IT.CODORC=O.CODORC AND IT.CODEMP=O.CODEMP AND " );
			sql.append( "IT.CODFILIAL=O.CODFILIAL AND IT.TIPOORC=O.TIPOORC AND T.CODTPCONV=C.CODTPCONV AND T.CODEMP=C.CODEMPTC AND " );
			sql.append( "T.CODFILIAL=C.CODFILIALTC AND CL.CODEMP=O.CODEMPCL AND CL.CODFILIAL=O.CODFILIALCL AND " );
			sql.append( "CL.CODCLI=O.CODCLI AND C.CODEMP=O.CODEMPCV AND C.CODFILIAL=O.CODFILIALCV AND O.CODCONV=C.CODCONV AND " );
			sql.append( "P.CODPROD=IT.CODPROD AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD " );
			sql.append( where );

			// System.out.println(sSQL);
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );

			if ( bConv ) {
				ps.setInt( 5, txtCodConv.getVlrInteger().intValue() );
			}

			ResultSet rs = ps.executeQuery();
			tab.limpa();

			while ( rs.next() ) {
				tab.adicLinha();
				tab.setValor( rs.getString( 1 ), iLin, 0 );
				tab.setValor( new Integer( rs.getInt( 2 ) ), iLin, 1 );

				if ( cbConveniado.getVlrString().equals( "S" ) ) {
					tab.setValor( new Integer( rs.getInt( 5 ) ), iLin, 2 );
					tab.setValor( rs.getString( 6 ), iLin, 3 );
					tab.setValor( rs.getString( 16 ), iLin, 8 );
				}
				else {
					tab.setValor( new Integer( rs.getInt( 7 ) ), iLin, 2 );
					tab.setValor( rs.getString( 8 ), iLin, 3 );
					tab.setValor( rs.getString( 17 ), iLin, 8 );
				}

				tab.setValor( rs.getString( 10 ), iLin, 4 );
				tab.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DtOrc" ) ), iLin, 6 );
				tab.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DtVencOrc" ) ), iLin, 7 );
				tab.setValor( rs.getString( 9 ) != null ? Funcoes.setMascara( rs.getString( 9 ), "(####)####-####" ) : "", iLin, 9 );
				tab.setValor( rs.getString( 18 ) != null ? rs.getString( 18 ) : "", iLin, 5 );
				iLin++;
			}

			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela VDOR�AMENTO!\n" + err.getMessage(), true, con, err );
		}
	}

	private void imprimir( boolean bVisualizar ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;

		try {

			imp.montaCab();
			imp.setTitulo( "Relat�rio de Or�amentos" );
			imp.limpaPags();

			for ( int iLin = 0; iLin < tab.getNumLinhas(); iLin++ ) {
				if ( imp.pRow() >= linPag ) {
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
					imp.incPags();
					imp.eject();
				}
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.say( imp.pRow(), 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "| N.ORC." );
					imp.say( imp.pRow(), 14, "|  Emiss�o" );
					imp.say( imp.pRow(), 27, "|  Validade" );
					imp.say( imp.pRow(), 40, "| Tipo conv." );
					imp.say( imp.pRow(), 54, "| Nome" );
					imp.say( imp.pRow(), 82, "| Encaminhador" );
					imp.say( imp.pRow(), 97, "| Cidade" );
					imp.say( imp.pRow(), 119, "| Telefone" );
					imp.say( imp.pRow(), 135, "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
				}
				imp.say( imp.pRow() + 1, 0, imp.comprimido() );
				imp.say( imp.pRow(), 0, "| " + tab.getValor( iLin, 1 ) );
				imp.say( imp.pRow(), 14, "| " + tab.getValor( iLin, 6 ) );
				imp.say( imp.pRow(), 27, "| " + tab.getValor( iLin, 7 ) );
				imp.say( imp.pRow(), 40, "| " + Funcoes.copy( (String) tab.getValor( iLin, 4 ), 11 ) );
				imp.say( imp.pRow(), 54, "| " + Funcoes.copy( (String) tab.getValor( iLin, 3 ), 25 ) );
				imp.say( imp.pRow(), 82, "| " + Funcoes.copy( (String) tab.getValor( iLin, 5 ), 12 ) );
				imp.say( imp.pRow(), 97, "| " + Funcoes.copy( (String) tab.getValor( iLin, 8 ), 19 ) );
				imp.say( imp.pRow(), 119, "|" + tab.getValor( iLin, 9 ) );
				imp.say( imp.pRow(), 135, "|" );
			}
			imp.say( imp.pRow() + 1, 0, imp.comprimido() );
			imp.say( imp.pRow(), 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );
			imp.eject();
			imp.fechaGravacao();

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de or�amentos!" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	private void abreOrc() {

		int iCodOrc = ( (Integer) tab.getValor( tab.getLinhaSel(), 1 ) ).intValue();
		if ( fPrim.temTela( "Orcamento" ) == false ) {
			FOrcamento tela = new FOrcamento();
			fPrim.criatela( "Orcamento", tela, con );
			tela.exec( iCodOrc );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btBusca ) {
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
		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcConv.setConexao( con );
		lcCli.setConexao( con );
		lcTipoConv.setConexao( con );
		lcEnc.setConexao( con );
	}
}
