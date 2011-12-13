/**
 * @version 31/03/2008 <BR>
 * 
 *          Projeto: Freedom <BR>
 * 
 *          Pacote: org.freedom.modulos.tmk <BR>
 *          Classe:
 * @(#)FGerencCampanhas.java <BR>
 * 
 *                           Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                           modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                           na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                           Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                           sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                           Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                           Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                           de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                           Gerenciamento de campanhas de marketing.
 * 
 */

package org.freedom.modulos.crm.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.object.EmailBean;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.crm.business.object.Campanha.EColCampanha;
import org.freedom.modulos.crm.dao.DAOCampanha;

public class FGerencCampanhas extends FTabDados implements ActionListener, TabelaEditListener, MouseListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCampanha = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinFiltros = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCabCamp = new JPanelPad( 0, 94 );

	private JPanelPad pinCabFiltros = new JPanelPad( 0, 94 );

	private JPanelPad pnCabCamp = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnCabFiltros = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinLbFiltros = new JPanelPad( 53, 15 );

	private JPanelPad pinRod = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JTextFieldPad txtCodCamp = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescCamp = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodEmailCamp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescEmailCamp = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private final JCheckBoxPad cbSelecionado = new JCheckBoxPad( "Sele��o", "S", "N" );

	private JTablePad tabCont = new JTablePad();

	private JButtonPad btRefresh = new JButtonPad( Icone.novo( "btAtualiza.gif" ) );

	private JButtonPad btSelectAll = new JButtonPad( Icone.novo( "btTudo.gif" ) );

	private JButtonPad btDeselectAll = new JButtonPad( Icone.novo( "btNada.gif" ) );

	private JButtonPad btEnviar = new JButtonPad( "Enviar", Icone.novo( "btEncaminharCand.gif" ) );

	private JButtonPad btAdicCampPart = new JButtonPad( Icone.novo( "btFlechaDir.gif" ) );

	private JButtonPad btDelCampPart = new JButtonPad( Icone.novo( "btFlechaEsq.gif" ) );

	private JButtonPad btAdicCampNPart = new JButtonPad( Icone.novo( "btFlechaDir.gif" ) );

	private JButtonPad btDelCampNPart = new JButtonPad( Icone.novo( "btFlechaEsq.gif" ) );

	private JCheckBoxPad cbEmailValido = new JCheckBoxPad( "Email v�lido", "S", "N" );

	private JRadioGroup<String, String> rgDestino;

	private JList lsCampDispPart = new JList();

	private JList lsCampFiltroPart = new JList();

	private JList lsCampDispNPart = new JList();

	private JList lsCampFiltroNPart = new JList();

	private JScrollPane spnCampDispPart = new JScrollPane( lsCampDispPart );

	private JScrollPane spnCampFiltroPart = new JScrollPane( lsCampFiltroPart );

	private JScrollPane spnCampDispNPart = new JScrollPane( lsCampDispNPart );

	private JScrollPane spnCampFiltroNPart = new JScrollPane( lsCampFiltroNPart );

	private JScrollPane spnTabCamp = new JScrollPane( tabCont );

	private JLabelPad lbContatos = new JLabelPad( "", SwingConstants.CENTER );

	private JLabelPad lbSelecionados = new JLabelPad( "", SwingConstants.CENTER );

	private ImageIcon imgEnviando = Icone.novo( "enviando.gif" );

	private ImageIcon imgPendente = Icone.novo( "clInativo.gif" );

	private ImageIcon imgEnviado = Icone.novo( "enviado.png" );

	private ImageIcon imgErro = Icone.novo( "erro_envio.gif" );

	private ListaCampos lcCampanha = new ListaCampos( this );

	private ListaCampos lcEmailCamp = new ListaCampos( this );

	private static Vector<String> vCampDispPart = new Vector<String>();

	private static Vector<String> vCampDispNPart = new Vector<String>();

	private static Vector<String> vCampFiltroPart = new Vector<String>();

	private static Vector<String> vCampFiltroNPart = new Vector<String>();

	private HashMap<String, Object> prefere = null;
	
	private DAOCampanha daocampanha = null;

	public FGerencCampanhas() {

		super( false );
		setTitulo( "Gerenciamento de campanhas" );
		setAtribos( 15, 30, 796, 450 );

		montaListaCampos();
		montaTela();
		montaListeners();

	}

	private void montaListaCampos() {

		// Campanha
		lcCampanha.add( new GuardaCampo( txtCodCamp, "CodCamp", "C�d.Camp", ListaCampos.DB_PK, null, false ) );
		lcCampanha.add( new GuardaCampo( txtDescCamp, "DescCamp", "Descri��o a campanha", ListaCampos.DB_SI, null, false ) );

		lcCampanha.montaSql( false, "CAMPANHA", "TK" );
		lcCampanha.setQueryCommit( false );
		lcCampanha.setReadOnly( true );

		txtCodCamp.setNomeCampo( "CodCamp" );
		txtCodCamp.setPK( true );
		txtCodCamp.setListaCampos( lcCampanha );

		// Email Campanha
		lcEmailCamp.add( new GuardaCampo( txtCodEmailCamp, "CodEmail", "C�d.Email", ListaCampos.DB_PK, null, false ) );
		lcEmailCamp.add( new GuardaCampo( txtDescEmailCamp, "DescEmail", "Descri��o do Email", ListaCampos.DB_SI, null, false ) );

		lcEmailCamp.montaSql( false, "EMAIL", "TK" );
		lcEmailCamp.setQueryCommit( false );
		lcEmailCamp.setReadOnly( true );

		txtCodEmailCamp.setNomeCampo( "CodEmail" );
		txtCodEmailCamp.setPK( true );
		txtCodEmailCamp.setListaCampos( lcEmailCamp );

	}

	private void montaTela() {

		Vector<String> labelsDest = new Vector<String>();
		labelsDest.addElement( "Contatos" );
		labelsDest.addElement( "Clientes" );
		labelsDest.addElement( "Ambos" );
		Vector<String> valDest = new Vector<String>();
		valDest.addElement( "O" );
		valDest.addElement( "C" );
		valDest.addElement( "A" );

		rgDestino = new JRadioGroup<String, String>( 1, 3, labelsDest, valDest );

		// Valores padr�o

		cbEmailValido.setVlrString( "S" );

		btRefresh.setToolTipText( "Refazer consulta" );
		btRefresh.setText( "Aplicar" );
		btEnviar.setToolTipText( "Enviar e-mail selecionados" );

		lbContatos.setForeground( Color.BLUE );
		lbSelecionados.setForeground( Color.BLUE );

		// Adi��o da aba campanha e seu conte�do

		adicTab( "Campanha", pinCampanha );
		pinCampanha.add( pnCabCamp );

		pnCabCamp.add( pinCabCamp, BorderLayout.NORTH );
		pnCabCamp.add( spnTabCamp, BorderLayout.CENTER );

		pinCabCamp.adic( new JLabelPad( "C�d.Campanha" ), 7, 0, 120, 20 );
		pinCabCamp.adic( txtCodCamp, 7, 20, 120, 20 );
		pinCabCamp.adic( new JLabelPad( "Nome da campanha" ), 130, 0, 330, 20 );
		pinCabCamp.adic( txtDescCamp, 130, 20, 330, 20 );
		pinCabCamp.adic( new JLabelPad( "C�d.Email" ), 7, 40, 120, 20 );
		pinCabCamp.adic( txtCodEmailCamp, 7, 60, 120, 20 );
		pinCabCamp.adic( new JLabelPad( "Descri��o do email" ), 130, 40, 330, 20 );
		pinCabCamp.adic( txtDescEmailCamp, 130, 60, 330, 20 );
		//pinCabCamp.adic( btRefresh, 600, 55, 120, 30 );
		pinCabCamp.adic( btSelectAll, 740, 25, 30, 30 );
		pinCabCamp.adic( btDeselectAll, 740, 55, 30, 30 );

		// Adi��o da aba filtro

		adicTab( "Filtros", pinFiltros );

		pinFiltros.add( pnCabFiltros );

		pnCabFiltros.add( pinCabFiltros, BorderLayout.CENTER );
		pinCabFiltros.adic( cbEmailValido, 3, 10, 120, 30 );
		pinCabFiltros.adic( rgDestino, 143, 10, 300, 30 );

		pinCabFiltros.adic( new JLabelPad( "Campanha dispon�veis:" ), 7, 50, 200, 20 );
		pinCabFiltros.adic( spnCampDispPart, 7, 70, 195, 100 );

		pinCabFiltros.adic( new JLabelPad( "Campanha dispon�veis:" ), 7, 185, 190, 20 );
		pinCabFiltros.adic( spnCampDispNPart, 7, 205, 195, 100 );

		pinCabFiltros.adic( btAdicCampPart, 210, 85, 30, 30 );
		pinCabFiltros.adic( btDelCampPart, 210, 125, 30, 30 );

		pinCabFiltros.adic( btAdicCampNPart, 210, 225, 30, 30 );
		pinCabFiltros.adic( btDelCampNPart, 210, 265, 30, 30 );

		pinCabFiltros.adic( new JLabelPad( "Participantes das campanhas:" ), 247, 50, 220, 20 );
		pinCabFiltros.adic( spnCampFiltroPart, 247, 70, 195, 100 );

		pinCabFiltros.adic( new JLabelPad( "N�o participantes das campanhas:" ), 247, 185, 220, 20 );
		pinCabFiltros.adic( spnCampFiltroNPart, 247, 205, 195, 100 );
		pinCabFiltros.adic( btRefresh, 320, 320, 120, 30 );

		// Montagem do rodap�
		pnRodape.add( btEnviar, BorderLayout.WEST );
		pinRod.add( lbContatos );
		pinRod.add( lbSelecionados );
		pnRodape.add( pinRod, BorderLayout.CENTER );
		pnRodape.add( btSair, BorderLayout.EAST );
		pnBordRod.setPreferredSize( new Dimension( 800, 35 ) );
		// Outras defini��es

		montaTab();

		lcEmailCamp.addCarregaListener( this );

	}

	private void montaListeners() {

		btRefresh.addActionListener( this );
		btSelectAll.addActionListener( this );
		btDeselectAll.addActionListener( this );
		btEnviar.addActionListener( this );
		btSair.addActionListener( this );
		btAdicCampPart.addActionListener( this );
		btDelCampPart.addActionListener( this );
		btAdicCampNPart.addActionListener( this );
		btDelCampNPart.addActionListener( this );

	}

	private void montaTab() {
		//		SELECTED, STATUS, CODEMP, CODFILIAL, TIPOCTO, CODCTO, RAZCTO, NOMECTO, EMAILCTO, CONTCTO, PROGRESS 


		tabCont.adicColuna( "sel." );
		tabCont.adicColuna( "sit." );
		tabCont.adicColuna( "codemp" );
		tabCont.adicColuna( "codfilial" );
		tabCont.adicColuna( "Tp." );
		tabCont.adicColuna( "C�digo" );
		tabCont.adicColuna( "Raz�o social" );
		tabCont.adicColuna( "Nome" );
		tabCont.adicColuna( "Email" );
		tabCont.adicColuna( "Cont.Cto." );
		tabCont.adicColuna( "..." );

		tabCont.setTamColuna( 30, EColCampanha.SELECTED.ordinal() );
		tabCont.setTamColuna( 30, EColCampanha.STATUS.ordinal() );
		tabCont.setTamColuna( 0, EColCampanha.CODEMP.ordinal() );
		tabCont.setTamColuna( 0, EColCampanha.CODFILIAL.ordinal() );
		tabCont.setTamColuna( 20, EColCampanha.TIPOCTO.ordinal() );
		tabCont.setTamColuna( 50, EColCampanha.CODCTO.ordinal() );
		tabCont.setTamColuna( 250, EColCampanha.RAZCTO.ordinal() );
		tabCont.setTamColuna( 250, EColCampanha.NOMECTO.ordinal() );
		tabCont.setTamColuna( 250, EColCampanha.EMAILCTO.ordinal() );
		tabCont.setTamColuna( 250, EColCampanha.CONTCTO.ordinal() );
		tabCont.setTamColuna( 100, EColCampanha.PROGRESS.ordinal() );

		//tabCont.setColunaEditavel( 0, true );
		//tabCont.setColunaInvisivel( 0 );
		tabCont.setColunaInvisivel( EColCampanha.CODEMP.ordinal() );
		tabCont.setColunaInvisivel( EColCampanha.CODFILIAL.ordinal() );

		tabCont.addMouseListener( this );
		tabCont.addTabelaEditListener( this );
	}
	/*
	private void carregaTabCont() {

		StringBuffer sql = new StringBuffer();
		StringBuffer where = new StringBuffer();
		boolean and = true;

		if ( ( "O".equals( rgDestino.getVlrString() ) ) || ( "A".equals( rgDestino.getVlrString() ) ) ) {
			sql.append( "SELECT 'O' TIPOCTO, CO.CODEMP, CO.CODFILIAL, CO.CODCTO, CO.NOMECTO, CO.EMAILCTO " );
			sql.append( "FROM TKCONTATO CO " );
			where.append( " WHERE CO.CODEMP=? AND CO.CODFILIAL=? " );

			if ( "S".equals( cbEmailValido.getVlrString() ) ) {
				where.append( " AND EMAILCTO IS NOT NULL AND EMAILCTO <> '' " );
			}
			if ( vCampFiltroPart.size() > 0 ) {
				String sIN = Funcoes.vectorToString( vCampFiltroPart, "','" );
				sIN = "'" + sIN + "'";

				where.append( " AND EXISTS (SELECT * FROM TKCAMPANHACTO CC " );
				where.append( " WHERE CC.CODEMP=" );
				where.append( Aplicativo.iCodEmp );
				where.append( " AND CC.CODFILIAL=" );
				where.append( ListaCampos.getMasterFilial( "TKCAMPANHA" ) );
				where.append( " AND CC.CODEMPCO=CO.CODEMP AND CC.CODFILIALCO=CO.CODFILIAL AND CC.CODCTO=CO.CODCTO " );
				where.append( " AND CC.CODCAMP IN (" + sIN + ")) " );

			}
			if ( vCampFiltroNPart.size() > 0 ) {
				String sIN = Funcoes.vectorToString( vCampFiltroNPart, "','" );
				sIN = "'" + sIN + "'";

				where.append( " AND NOT EXISTS (SELECT * FROM TKCAMPANHACTO CC " );
				where.append( " WHERE CC.CODEMP=" );
				where.append( Aplicativo.iCodEmp );
				where.append( " AND CC.CODFILIAL=" );
				where.append( ListaCampos.getMasterFilial( "TKCAMPANHA" ) );
				where.append( " AND CC.CODEMPCO=CO.CODEMP AND CC.CODFILIALCO=CO.CODFILIAL AND CC.CODCTO=CO.CODCTO " );
				where.append( " AND CC.CODCAMP IN (" + sIN + ")) " );

			}
			else {
				where.append( " AND NOT EXISTS(SELECT * FROM TKCAMPANHACTO CC WHERE CC.CODEMPCO=CO.CODEMP AND CC.CODFILIALCO=CO.CODFILIAL AND CC.CODCTO=CO.CODCTO) " );
			}

			sql.append( where );

		}
		if ( ( "C".equals( rgDestino.getVlrString() ) ) || ( "A".equals( rgDestino.getVlrString() ) ) ) {
			if ( sql.length() > 0 ) {
				sql.append( " UNION " );
				if ( where.length() > 0 ) {
					where.delete( 0, where.length() );
				}
			}
			sql.append( "SELECT 'C' TIPOCTO, C.CODEMP, C.CODFILIAL, C.CODCLI CODCTO, C.CONTCLI NOMECTO, C.EMAILCLI EMAILCTO " );
			sql.append( "FROM VDCLIENTE C " );
			where.append( " WHERE C.CODEMP=? AND C.CODFILIAL=? " );

			if ( "S".equals( cbEmailValido.getVlrString() ) ) {
				where.append( " AND EMAILCLI IS NOT NULL AND EMAILCLI <> '' " );
			}
			if ( vCampFiltroPart.size() > 0 ) {
				String sIN = Funcoes.vectorToString( vCampFiltroPart, "','" );
				sIN = "'" + sIN + "'";

				where.append( " AND EXISTS (SELECT * FROM TKCAMPANHACLI CC " );
				where.append( " WHERE CC.CODEMP=" );
				where.append( Aplicativo.iCodEmp );
				where.append( " AND CC.CODFILIAL=" );
				where.append( ListaCampos.getMasterFilial( "TKCAMPANHACLI" ) );
				where.append( " AND CC.CODEMPCL=C.CODEMP AND CC.CODFILIALCL=C.CODFILIAL AND CC.CODCLI=C.CODCLI " );
				where.append( " AND CC.CODCAMP IN (" + sIN + ")) " );

			}
			if ( vCampFiltroNPart.size() > 0 ) {
				String sIN = Funcoes.vectorToString( vCampFiltroNPart, "','" );
				sIN = "'" + sIN + "'";

				where.append( " AND NOT EXISTS (SELECT * FROM TKCAMPANHACLI CC " );
				where.append( " WHERE CC.CODEMP=" );
				where.append( Aplicativo.iCodEmp );
				where.append( " AND CC.CODFILIAL=" );
				where.append( ListaCampos.getMasterFilial( "TKCAMPANHA" ) );
				where.append( " AND CC.CODEMPCL=C.CODEMP AND CC.CODFILIALCL=C.CODFILIAL AND CC.CODCLI=C.CODCLI " );
				where.append( " AND CC.CODCAMP IN (" + sIN + ")) " );

			}
			else {
				where.append( " AND NOT EXISTS(SELECT * FROM TKCAMPANHACLI CC WHERE CC.CODEMPCL=C.CODEMP AND CC.CODFILIALCL=C.CODFILIAL AND CC.CODCLI=C.CODCLI) " );
			}

			sql.append( where );
		}
		sql.append( " ORDER BY 5" );

		tabCont.limpa();

		try {
			System.out.println( "SQL:" + sql.toString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "TKCONTATO" ) );

			ResultSet rs = ps.executeQuery();
			System.out.println( "SQL:" + sql.toString() );

			for ( int row = 0; rs.next(); row++ ) {
				tabCont.adicLinha();
				tabCont.setValor( new Boolean( false ), row, EColCampanha.SELECTED.ordinal() );
				tabCont.setValor( null, row, EColCampanha.STATUS.ordinal() );
				tabCont.setValor( rs.getInt( "CODEMP" ), row, EColCampanha.CODEMP.ordinal() );
				tabCont.setValor( rs.getInt( "CODFILIAL" ), row, EColCampanha.CODFILIAL.ordinal() );
				tabCont.setValor( rs.getString( "TIPOCTO" ), row, EColCampanha.TIPOCTO.ordinal() );
				tabCont.setValor( rs.getInt( "CODCTO" ), row, EColCampanha.CODCTO.ordinal() );
				tabCont.setValor( rs.getString( "NOMECTO" ), row, EColCampanha.NOMECTO.ordinal() );
				tabCont.setValor( rs.getString( "EMAILCTO" ), row, EColCampanha.EMAILCTO.ordinal() );
				tabCont.setValor( imgPendente, row, EColCampanha.PROGRESS.ordinal() );
			}
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar contatos!\n" + err.getMessage(), true, con, err );
		}

		lbContatos.setText( tabCont.getNumLinhas() + " contatos filtrados." );
		lbSelecionados.setText( "" );
	}
	*/

	private void selectAll() {

		for ( int i = 0; i < tabCont.getNumLinhas(); i++ ) {
			tabCont.setValor( new Boolean( true ), i, 0 );
		}
		countSelected();
	}

	private void deselectALl() {

		for ( int i = 0; i < tabCont.getNumLinhas(); i++ ) {
			tabCont.setValor( new Boolean( false ), i, 0 );
		}
		countSelected();
	}

	private void countSelected() {

		int selecionados = 0;
		for ( int row = 0; row < tabCont.getNumLinhas(); row++ ) {
			if ( (Boolean) tabCont.getValor( row, EColCampanha.SELECTED.ordinal() ) ) {
				selecionados++;
			}
		}
		lbSelecionados.setText( selecionados + " contatos selecionados" );
	}

	private void efetivarCampanha( String tipocto, int codempcto, int codfilialcto, int codcto, String tipo, String deschist ) {

		PreparedStatement ps = null;
		Integer codempat = 0;
		Integer codfilialat = 0;
		Integer codativ = 0;
		String sithist = "EF";

		try {

			if ( prefere == null ) {
				prefere = getPreferencias();
			}

			codempat = (Integer) prefere.get( "CODEMP" + tipo );
			codfilialat = (Integer) prefere.get( "CODFILIAL" + tipo );
			codativ = (Integer) prefere.get( "CODATIV" + tipo );

			if ( "TE".equals( tipo ) )
				sithist = "RJ";

			ps = con.prepareStatement( "EXECUTE PROCEDURE TKGERACAMPANHACTO(?,?,?,?,?,?,?,?,?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "TKCAMPANHA" ) );
			ps.setString( 3, txtCodCamp.getVlrString() );

			ps.setInt( 4, codempcto );
			ps.setInt( 5, codfilialcto );
			ps.setInt( 6, codcto );

			ps.setInt( 7, codempat );
			ps.setInt( 8, codfilialat );
			ps.setInt( 9, codativ );

			ps.setString( 10, sithist );
			ps.setString( 11, deschist );

			ps.execute();
			ps.close();

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private synchronized void enviarEmailCampanha() {

		if ( "".equals( txtCodCamp.getVlrString().trim() ) ) {
			Funcoes.mensagemInforma( this, "Campanha n�o informada." );
			return;
		}
		if ( "".equals( txtCodEmailCamp.getVlrString().trim() ) ) {
			Funcoes.mensagemInforma( this, "E-mail da campanha n�o informado." );
			return;
		}

		EmailBean emailpad = createEmailBean();

		try {

			for ( int row = 0; row < tabCont.getNumLinhas(); row++ ) {

				if ( (Boolean) tabCont.getValor( row, EColCampanha.SELECTED.ordinal() ) ) {

					EmailBean email = emailpad.getClone();
					email.setPara( tabCont.getValor( row, EColCampanha.EMAILCTO.ordinal() ).toString() );

					int codempcto = (Integer) tabCont.getValor( row, EColCampanha.CODEMP.ordinal() );
					int codfilialcto = (Integer) tabCont.getValor( row, EColCampanha.CODFILIAL.ordinal() );
					String tipocto = (String) tabCont.getValor( row, EColCampanha.TIPOCTO.ordinal() );
					int codcto = (Integer) tabCont.getValor( row, EColCampanha.CODCTO.ordinal() );

					email.createSession();
					Session session = email.getSession();

					try {

						tabCont.setValor( imgEnviando, row, EColCampanha.PROGRESS.ordinal() );

						MimeMessage msg = EmailBean.getMessage( session, email );
						msg.setContent( email.getCorpo(), email.getFormato() );

						email.send( msg );

						efetivarCampanha( tipocto, codempcto, codfilialcto, codcto, "CE", "EMAIL ENVIADO COM SUCESSO" );

						tabCont.setValor( imgEnviado, row, EColCampanha.PROGRESS.ordinal() );

					} catch ( Exception e ) {
						e.printStackTrace();
						tabCont.setValor( imgErro, row, EColCampanha.PROGRESS.ordinal() );
						efetivarCampanha( tipocto, codempcto, codfilialcto, codcto, "TE", "ERRO AO ENVIAR EMAIL\n" + e.getMessage() );
					} finally {
						System.gc();
					}

					tabCont.setValor( new Boolean( false ), row, EColCampanha.SELECTED.ordinal() );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private HashMap<String, Object> getPreferencias() {

		HashMap<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append( "SELECT CODEMPCE, CODFILIALCE, CODATIVCE, CODEMPTE, CODFILIALTE, CODATIVTE " );
			sql.append( "FROM SGPREFERE3 " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=?" );
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE3" ) );
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				ret.put( "CODEMPCE", rs.getInt( "CODEMPCE" ) );
				ret.put( "CODFILIALCE", rs.getInt( "CODFILIALCE" ) );
				ret.put( "CODATIVCE", rs.getInt( "CODATIVCE" ) );
				ret.put( "CODEMPTE", rs.getInt( "CODEMPTE" ) );
				ret.put( "CODFILIALTE", rs.getInt( "CODFILIALTE" ) );
				ret.put( "CODATIVTE", rs.getInt( "CODATIVTE" ) );
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE3!\n" + err.getMessage(), true, con, err );
		}
		return ret;
	}

	private void carregaCampFiltro() {

		int iPos = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();

		try {

			sSQL.append( "SELECT CODCAMP FROM TKCAMPANHA WHERE CODEMP=? AND CODFILIAL=? " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "TKCAMPANHA" ) );

			rs = ps.executeQuery();

			vCampDispPart.clear();
			vCampDispNPart.clear();

			vCampFiltroPart.clear();
			vCampFiltroNPart.clear();

			while ( rs.next() ) {

				vCampDispPart.addElement( rs.getString( "CODCAMP" ) );
				vCampDispNPart.addElement( rs.getString( "CODCAMP" ) );

			}

			rs.close();
			ps.close();

			con.commit();

			lsCampDispPart.setListData( vCampDispPart );
			lsCampDispNPart.setListData( vCampDispNPart );

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemInforma( this, "N�o foi carregar as campanhas para o filtro.\n" + err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void adicionaCampFiltroNPart() {

		if ( lsCampDispNPart.isSelectionEmpty() ) {
			return;
		}
		for ( int i = lsCampDispNPart.getMaxSelectionIndex(); i >= 0; i-- ) {

			if ( lsCampDispNPart.isSelectedIndex( i ) ) {
				vCampFiltroNPart.add( vCampDispNPart.elementAt( i ) );
				vCampDispNPart.remove( i );
			}
		}

		lsCampDispNPart.setListData( vCampDispNPart );
		lsCampFiltroNPart.setListData( vCampFiltroNPart );

	}

	private void adicionaCampFiltroPart() {

		if ( lsCampDispPart.isSelectionEmpty() ) {
			return;
		}
		for ( int i = lsCampDispPart.getMaxSelectionIndex(); i >= 0; i-- ) {

			if ( lsCampDispPart.isSelectedIndex( i ) ) {
				vCampFiltroPart.add( vCampDispPart.elementAt( i ) );
				vCampDispPart.remove( i );
			}
		}

		lsCampDispPart.setListData( vCampDispPart );
		lsCampFiltroPart.setListData( vCampFiltroPart );

	}

	private void removeCampFiltroPart() {

		if ( lsCampFiltroPart.isSelectionEmpty() ) {
			return;
		}

		for ( int i = lsCampFiltroPart.getMaxSelectionIndex(); i >= 0; i-- ) {

			if ( lsCampFiltroPart.isSelectedIndex( i ) ) {
				vCampDispPart.add( vCampFiltroPart.elementAt( i ) );
				vCampFiltroPart.remove( i );
			}
		}

		lsCampDispPart.setListData( vCampDispPart );
		lsCampFiltroPart.setListData( vCampFiltroPart );

	}

	private void removeCampFiltroNPart() {

		if ( lsCampFiltroNPart.isSelectionEmpty() ) {
			return;
		}

		for ( int i = lsCampFiltroNPart.getMaxSelectionIndex(); i >= 0; i-- ) {

			if ( lsCampFiltroNPart.isSelectedIndex( i ) ) {
				vCampDispNPart.add( vCampFiltroNPart.elementAt( i ) );
				vCampFiltroNPart.remove( i );
			}
		}

		lsCampDispNPart.setListData( vCampDispNPart );
		lsCampFiltroNPart.setListData( vCampFiltroNPart );

	}

	private boolean validaEmailCamp() {

		boolean ret = false;
		StringBuffer sql = new StringBuffer();
		try {

			sql.append( "SELECT CODEMAIL FROM TKCAMPANHAEMAIL WHERE CODEMP=? AND CODFILIAL=? AND CODCAMP=?" );

			try {

				PreparedStatement ps = con.prepareStatement( sql.toString() );

				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "TKCAMPANHA" ) );
				ps.setString( 3, txtCodCamp.getVlrString() );

				ResultSet rs = ps.executeQuery();

				ret = rs.next();

				con.commit();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao consultar email para a campanha!\n" + err.getMessage(), true, con, err );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return ret;
	}

	public EmailBean createEmailBean() {

		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		EmailBean email = new EmailBean();
		sql.append( "SELECT CM.HOSTSMTP, CM.USAAUTSMTP, CM.USASSL, CM.PORTASMTP, CM.USUARIOREMET, CM.SENHAREMET, CM.CRIPTSENHA, CM.EMAILREMET, CM.EMAILRESP, " );
		sql.append( "EM.ASSUNTO, EM.CORPO, EM.FORMATO, EM.CHARSET " );
		sql.append( "FROM TKCONFEMAIL CM, TKEMAIL EM " );
		sql.append( "WHERE CM.CODEMP=EM.CODEMPCM AND CM.CODFILIAL=EM.CODFILIAL AND CM.CODCONFEMAIL=EM.CODCONFEMAIL  " );
		sql.append( "AND EM.CODEMP=? AND EM.CODFILIAL=? AND EM.CODEMAIL=? " );

		try {
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "TKEMAIL" ) );
			ps.setInt( 3, txtCodEmailCamp.getVlrInteger() );

			rs = ps.executeQuery();
			if ( rs.next() ) {
				email.setHost( rs.getString( "HOSTSMTP" ) );
				email.setAutentica( rs.getString( "USAAUTSMTP" ) );
				email.setSsl( rs.getString( "USASSL" ) );
				email.setPorta( rs.getInt( "PORTASMTP" ) );
				email.setUsuario( rs.getString( "USUARIOREMET" ) );
				email.setSenha( rs.getString( "SENHAREMET" ), rs.getString( "CRIPTSENHA" ) );
				email.setDe( rs.getString( "EMAILREMET" ) );
				email.setEmailResp( rs.getString( "EMAILRESP" ) );
				email.setAssunto( rs.getString( "ASSUNTO" ) );
				email.setCorpo( rs.getString( "CORPO" ) );
				email.setFormato( rs.getString( "FORMATO" ) );
				email.setCharset( rs.getString( "CHARSET" ) );
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( null, "N�o foi poss�vel carregar as informa��es para envio de emial!\n" + e.getMessage() );
		}
		return email;

	}
	
	
	private void loadContcli(){
		try {
			Vector<Vector<Object>> datavector = daocampanha.loadContcli( rgDestino.getVlrString(), 
					Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "TKCAMPANHA" ), 
					Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "TKCONTATO" ), 
					Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCLIENTE" ),
					cbEmailValido.getVlrString(), vCampFiltroPart, vCampFiltroNPart, imgPendente );
			tabCont.limpa();
			for (Vector<Object> row: datavector) {
				tabCont.adicLinha(row);
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro carregando tabela de contato/cliente !\b" + err.getMessage() );
			err.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btRefresh ) {
			loadContcli();
		}
		else if ( evt.getSource() == btSelectAll ) {
			selectAll();
		}
		else if ( evt.getSource() == btDeselectAll ) {
			deselectALl();
		}
		else if ( evt.getSource() == btEnviar ) {
			Thread th = new Thread( new Runnable() {

				public void run() {

					enviarEmailCampanha();
				}
			} );
			try {
				th.start();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		else if ( evt.getSource() == btAdicCampPart ) {
			adicionaCampFiltroPart();
		}
		else if ( evt.getSource() == btDelCampPart ) {
			removeCampFiltroPart();
		}
		else if ( evt.getSource() == btAdicCampNPart ) {
			adicionaCampFiltroNPart();
		}
		else if ( evt.getSource() == btDelCampNPart ) {
			removeCampFiltroNPart();
		}

		else if ( evt.getSource() == btSair ) {
			dispose();
		}
	}

	public void valorAlterado( TabelaEditEvent evt ) {

	}

	public void mouseClicked( MouseEvent mevt ) {

		JTablePad tabEv = (JTablePad) mevt.getSource();

		if ( tabEv == tabCont && tabEv.getLinhaSel() > -1 ) {
			if ( mevt.getClickCount() == 1 || mevt.getClickCount() == 2 ) {
				tabCont.setValor( ! ( (Boolean) tabCont.getValor( tabCont.getLinhaSel(), 0 ) ).booleanValue(), tabCont.getLinhaSel(), 0 );
				tabCont.setValor( null, tabEv.getLinhaSel(), EColCampanha.PROGRESS.ordinal() );
				countSelected();
			}
		}
	}

	public void mouseEntered( MouseEvent e ) {

	}

	public void mouseExited( MouseEvent e ) {

	}

	public void mousePressed( MouseEvent e ) {

	}

	public void mouseReleased( MouseEvent e ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcEmailCamp ) {
			btEnviar.setEnabled( txtCodEmailCamp.getVlrInteger() != 0 );
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcEmailCamp ) {
			if ( "".equals( txtCodCamp.getVlrString() ) ) {
				Funcoes.mensagemInforma( this, "Escolha uma campanha" );
				cevt.cancela();
			}
			else {
				if ( !validaEmailCamp() ) {
					cevt.cancela();
				}
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCampanha.setConexao( con );
		lcEmailCamp.setConexao( con );
		carregaCampFiltro();
		
		
		daocampanha = new DAOCampanha( cn );
	}

	/* private class Columns {

		private final static int SELECTED = 0;

		private final static int STATUS = 1;

		private final static int EMPRESA = 2;

		private final static int FILIAL = 3;

		private final static int TIPO = 4;

		private final static int CODIGO = 5;

		private final static int NOME = 6;

		private final static int EMAIL = 7;

		private final static int PROGRESS = 8;
	}
	*/

}
