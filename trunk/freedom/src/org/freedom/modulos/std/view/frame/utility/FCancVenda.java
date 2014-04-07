/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FCancVenda.java <BR>
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
 */

package org.freedom.modulos.std.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modules.nfe.bean.FreedomNFEKey;
import org.freedom.modules.nfe.control.AbstractNFEFactory;
import org.freedom.modules.nfe.control.AbstractNFEFactory.TYPE_PROC;
import org.freedom.modulos.nfe.database.jdbc.NFEConnectionFactory;


public class FCancVenda extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String APLIC_CONTRIB_NFE = "0";

	private static final String APLIC_FISCO_NFE = "3";
	
	private JPanelPad pinCli = new JPanelPad( 350, 100 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtDocVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtStatusVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtBloqVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodtipomov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldFK txtDesctipomov = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtChaveNfe = new JTextFieldPad( JTextFieldPad.TP_STRING, 44, 0 );
	
	private JTextFieldPad txtCodmodnota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );
	
	private JCheckBoxPad cbFiscaltipomov = new JCheckBoxPad( "Fiscal", "S", "N" );

	private JTextFieldPad txtOperTipomov = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextAreaPad txaMotivoCancVenda = new JTextAreaPad(250);

	private JScrollPane spnMotivoCancVenda = new JScrollPane( txaMotivoCancVenda );

	private JButtonPad btCancelar = new JButtonPad( "Cancelar", Icone.novo( "btCancelar.png" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.png" ) );

	private ListaCampos lcVenda = new ListaCampos( this );
	
	private ListaCampos lcTipomov = new ListaCampos( this, "TM" );
	
	private Object[] oPrefs = null;

	private NFEConnectionFactory nfecf = null;

	private enum POS_PREFS {
		 PROCEMINFE, AMBIENTENFE, CNPJFILIAL, SIGLAUF, TIPOEMISSAONFE
	}

	public FCancVenda() {

		super( false );
		setTitulo( "Cancelamento" );
		setAtribos( 50, 50, 450, 340 );

		Funcoes.setBordReq( txtCodVenda );
		txtDocVenda.setAtivo( false );
		txtSerie.setAtivo( false );
		txtVlrLiqVenda.setAtivo( false );
		//txaMotivoCancVenda.setBackground( Color.WHITE );

		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "C�d.Venda", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtDocVenda, "DocVenda", "Documento", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtSerie, "Serie", "S�rie", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtBloqVenda, "BloqVenda", "Bloqueio", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tp.venda", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "V. Liq.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodtipomov, "Codtipomov", "C�d.tipo mov.", ListaCampos.DB_FK, txtDesctipomov, false ) );
		lcVenda.add( new GuardaCampo( txtChaveNfe, "Chavenfevenda", "Chave de acesso", ListaCampos.DB_SI, false ) );
		
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setReadOnly( true );
		txtCodVenda.setTabelaExterna( lcVenda, null );
		txtCodVenda.setFK( true );
		txtCodVenda.setNomeCampo( "CodVenda" );

		lcTipomov.add( new GuardaCampo( txtCodtipomov, "Codtipomov", "C�d.tp.mov.", ListaCampos.DB_PK, null, false ) );
		lcTipomov.add( new GuardaCampo( txtDesctipomov, "Desctipomov", "Descri��o tipo de movimento", ListaCampos.DB_SI, null, false ) );
		lcTipomov.add( new GuardaCampo( txtCodmodnota, "Codmodnota", "C�d.mod.nf.", ListaCampos.DB_SI, null, false ) );
		lcTipomov.add( new GuardaCampo( cbFiscaltipomov, "Fiscaltipomov", "Fiscal", ListaCampos.DB_SI, null, false ) );
		lcTipomov.add( new GuardaCampo( txtOperTipomov, "OperTipoMov", "Opera��o", ListaCampos.DB_SI, null, false ) );
		lcTipomov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipomov.setReadOnly( true );
		txtCodtipomov.setTabelaExterna( lcTipomov, null );
		txtCodtipomov.setFK( true );
		txtCodtipomov.setNomeCampo( "Codtipomov" );
		txtCodtipomov.setSoLeitura( true );
		txtCodmodnota.setSoLeitura( true );
		txtChaveNfe.setEditable( false );
		cbFiscaltipomov.setEnabled( false );
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );

		btSair.setPreferredSize( new Dimension( 100, 30 ) );

		pnRod.setPreferredSize( new Dimension( 350, 30 ) );
		pnRod.add( btSair, BorderLayout.EAST );

		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pinCli, BorderLayout.CENTER );

		btCancelar.setToolTipText( "Cancela Venda" );

		pinCli.adic( new JLabelPad( "N� Pedido" ), 7, 0, 80, 20 );
		pinCli.adic( txtCodVenda, 7, 20, 80, 20 );
		pinCli.adic( new JLabelPad( "Doc." ), 90, 0, 67, 20 );
		pinCli.adic( txtDocVenda, 90, 20, 67, 20 );
		pinCli.adic( new JLabelPad( "S�rie" ), 160, 0, 67, 20 );
		pinCli.adic( txtSerie, 160, 20, 67, 20 );
		pinCli.adic( new JLabelPad( "Valor" ), 230, 0, 100, 20 );
		pinCli.adic( txtVlrLiqVenda, 230, 20, 100, 20 );
		pinCli.adic( new JLabelPad( "Fiscal" ), 333, 0, 60, 20 );
		pinCli.adic( cbFiscaltipomov, 333, 20, 60, 20 );
		pinCli.adic( new JLabelPad( "C�d.tp.mov." ), 7, 40, 80, 20 );
		pinCli.adic( txtCodtipomov, 7, 60, 80, 20 );
		pinCli.adic( new JLabelPad( "Descri��o do tipo de movimento" ), 90, 40, 246, 20 );
		pinCli.adic( txtDesctipomov, 90, 60, 246, 20 );
		pinCli.adic( new JLabelPad( "C�d.mod.nf." ), 339, 40, 70, 20 );
		pinCli.adic( txtCodmodnota, 339, 60, 70, 20 );
		pinCli.adic( new JLabelPad( "Chave de acesso da NFe" ), 7, 80, 400, 20 );
		pinCli.adic( txtChaveNfe, 7, 100, 400, 20 );
		pinCli.adic( new JLabelPad( "Motivo de cancelamento" ), 7, 120, 400, 20 );
		pinCli.adic( spnMotivoCancVenda, 7, 140, 400, 70 );
		
		pinCli.adic( btCancelar, 7, 230, 130, 30 );

		btSair.addActionListener( this );
		btCancelar.addActionListener( this );

	}

	public boolean cancelar( int codvenda, String status, String motivocancvenda ) {

		boolean result = false;
		int codemp = Aplicativo.iCodEmp;
		int codfilial = ListaCampos.getMasterFilial( "VDVENDA" );
		String justificativa = txaMotivoCancVenda.getVlrString().trim();
		if ( codvenda == 0 ) {
			Funcoes.mensagemInforma( null, "Nenhuma venda foi selecionada!" );
			txtCodVenda.requestFocus();
		} else if ( status.substring( 0, 1 ).equals( "C" ) ) {
			Funcoes.mensagemInforma( null, "Venda ja foi cancelada!!" );
		} else if (justificativa.length()<15) {
			Funcoes.mensagemInforma( null, "Tamanho m�nimo para motivo/justificativa � de 15 caracteres !" );
		} else if (justificativa.length()>255) {
			Funcoes.mensagemInforma( null, "Tamanho m�ximo para motivo/justificativa � de 255 caracteres !" );
		} else if ( "VPD".indexOf( status.substring( 0, 1 ) ) != -1 ) {
			if (!verifReceber(codemp, codfilial, codvenda )) {
				Funcoes.mensagemInforma( this, "Esta venda possui t�tulos recebidos.\nExecute o estorno na manuten��o de contas a receber !" );
				return result;
			}
			if ( Funcoes.mensagemConfirma( null, "Deseja realmente cancelar esta venda?" ) == JOptionPane.YES_OPTION ) {
				boolean cancVenda = true;
				int modelo = txtCodmodnota.getVlrInteger();
				String serie = txtSerie.getVlrString();
				int docvenda = txtDocVenda.getVlrInteger();
				String dirNFE = Aplicativo.strTemp;
				String tipovenda = "V";
				String fiscaltipomov = cbFiscaltipomov.getVlrString();
				String chavenfe = txtChaveNfe.getVlrString();
				if ( APLIC_CONTRIB_NFE.equals(oPrefs[POS_PREFS.PROCEMINFE.ordinal()]) && "S".equals( fiscaltipomov ) && !"S".equals( txtOperTipomov.getVlrString() )) {
					if ("".equals(chavenfe)) {
						Funcoes.mensagemInforma( this, "Nota fiscal eletr�nica sem chave de acesso n�o pode ser cancelada !" );
						return result;
					}
					FreedomNFEKey key = new FreedomNFEKey( codemp, codfilial, tipovenda, codvenda, modelo, serie, docvenda, dirNFE );
					nfecf.setMotivoCancNfe(justificativa);
					nfecf.setChaveNfe( chavenfe );
					nfecf.setKey(key);
					nfecf.post();
					// trocar isValid para isAutorizada
					cancVenda = nfecf.getObjNFEFactory().isAutorizada();
				}
				if (cancVenda) {
					
					PreparedStatement ps = null;
					StringBuilder sql = new StringBuilder(); 
	
					try {
						cancVendaFreedom(Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDVENDA" ), codvenda, status, motivocancvenda );
						result = true;
						FCancVendaOrc.cancelar( con, codvenda, txtTipoVenda.getVlrString(), txtStatusVenda.getVlrString(), txtBloqVenda.getVlrString(), false );
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( null, "Erro ao cancelar a venda!\n" + err.getMessage(), true, con, err );
					} finally {
						ps = null;
						sql = null;
						lcVenda.carregaDados();
					}
				}
			}

		}

		return result;

	}

	private boolean verifReceber( int codemp, int codfilial, int codvenda ) {
		boolean result = true;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("select ir.statusitrec from fnreceber r, fnitreceber ir ");
			sql.append("where r.codempva=? and r.codfilialva=? and r.tipovenda='V' and r.codvenda=? ");
			sql.append("and ir.codemp=r.codemp and ir.codfilial=r.codfilial and ir.codrec=r.codrec ");
			sql.append("and ir.statusitrec in ('RL','RP')");
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codvenda );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = false;
			}
			rs.close();
			ps.close();
			con.commit();
			
		} catch (SQLException err) {
			err.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException err2) {
				err2.printStackTrace();
			}
			result = false;
		}
		return result;
	}
	
	private void cancVendaFreedom(int codemp, int codfilial, int codvenda, String status, String motivocancvenda) throws SQLException {
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder(); 

		// Desbloquear a venda, caso seja necess�rio
		sql.append( "update vdvenda set emmanut='S', bloqvenda='N' ");
		sql.append( "where codemp=? and codfilial=? and codvenda=? and tipovenda='V' and bloqvenda='S' ");
		ps = con.prepareStatement( sql.toString() );
		int param = 1;
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codvenda );
		ps.executeUpdate();
		ps.close();
		// Ajusta o emmanut para 'N'
		sql.delete( 0, sql.length() ); 
		sql.append( "update vdvenda set emmanut='N' ");
		sql.append( "where codemp=? and codfilial=? and codvenda=? and tipovenda='V' and emmanut='S' ");
		ps = con.prepareStatement( sql.toString() );
		param = 1;
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codvenda );
		ps.executeUpdate();
		ps.close();
		// Verifica se o cliente est� inativo para evitar exce��o por trigger
		sql.delete( 0, sql.length() ); 
		sql.append( "select c.ativocli from vdcliente c, vdvenda v ");
		sql.append( "where v.codemp=? and v.codfilial=? and v.codvenda=? and v.tipovenda='V' ");
		sql.append( "and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli ");
		ps = con.prepareStatement( sql.toString() );
		param = 1;
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codvenda );
		ResultSet rs = ps.executeQuery();
		boolean ativocli = true;
		if (rs.next()) {
		    ativocli = "S".equals(rs.getString( "ativocli" ));
		}
		rs.close();
		ps.close();
		if (!ativocli) {
			// Ativa o cliente
			sql.delete( 0, sql.length() ); 
			sql.append( "update vdcliente c set c.ativocli='S' ");
			sql.append( "where exists( select * from vdvenda v ");
			sql.append( "where v.codemp=? and v.codfilial=? and v.codvenda=? and tipovenda='V' ");
			sql.append( "and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli )");
			ps = con.prepareStatement( sql.toString() );
			param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codvenda );
			ps.executeUpdate();
			ps.close();
		}
		// Executa o cancelamento
		sql.delete( 0, sql.length() ); 
		sql.append( "update vdvenda set motivocancvenda=?, statusvenda=? " ); 
		sql.append( "where codemp=? and codfilial=? and codvenda=? and tipovenda='V'");
		ps = con.prepareStatement( sql.toString() );
		param = 1;
		ps.setString( param++, motivocancvenda );
		ps.setString( param++, "C"+status.substring( 0, 1 ) );
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codvenda );
		ps.executeUpdate();
		ps.close();
		con.commit();
		if (!ativocli) {
			// Inativa o cliente se for necess�rio
			sql.delete( 0, sql.length() ); 
			sql.append( "update vdcliente c set c.ativocli='N' ");
			sql.append( "where exists( select * from vdvenda v ");
			sql.append( "where v.codemp=? and v.codfilial=? and v.codvenda=? and tipovenda='V' ");
			sql.append( "and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli )");
			ps = con.prepareStatement( sql.toString() );
			param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codvenda );
			ps.executeUpdate();
			ps.close();
			con.commit();
		}

	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair )
			dispose();
		else if ( evt.getSource() == btCancelar ) {
			if ("".equals( txaMotivoCancVenda.getVlrString().trim()) ) {
				Funcoes.mensagemInforma( this, "Preencha o motivo do cancelamento !" );
				txaMotivoCancVenda.requestFocus();
				
			} else {
				cancelar( txtCodVenda.getVlrInteger().intValue(), txtStatusVenda.getVlrString(), txaMotivoCancVenda.getVlrString() );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcVenda.setConexao( cn );
		lcTipomov.setConexao( cn );
		oPrefs = prefs();
		
		setNfecf( new NFEConnectionFactory( con, Aplicativo.getInstace().getConexaoNFE()
				, AbstractNFEFactory.TP_NF_OUT, false
				, (String) oPrefs[POS_PREFS.PROCEMINFE.ordinal()]
				, (String) oPrefs[POS_PREFS.AMBIENTENFE.ordinal()]
				, Aplicativo.strTemp, TYPE_PROC.CANCELAMENTO
				, (String) oPrefs[POS_PREFS.CNPJFILIAL.ordinal()]
				, (String) oPrefs[POS_PREFS.SIGLAUF.ordinal()],
				new Integer((String) oPrefs[POS_PREFS.TIPOEMISSAONFE.ordinal()])));
	}
	
	private Object[] prefs() {

		Object[] retorno = new Object[ POS_PREFS.values().length ];
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL.append( "SELECT COALESCE(P1.PROCEMINFE,'3') PROCEMINFE, COALESCE(P1.AMBIENTENFE,'2') AMBIENTENFE " );
			sSQL.append( ", F.CNPJFILIAL, F.SIGLAUF, coalesce(P1.TIPOEMISSAONFE,'1') TIPOEMISSAONFE ");
			sSQL.append( "FROM SGPREFERE1 P1 INNER JOIN SGFILIAL F ON " );
			sSQL.append( "F.CODEMP=P1.CODEMP AND F.CODFILIAL=P1.CODFILIAL ");
			sSQL.append( "WHERE P1.CODEMP=? AND P1.CODFILIAL=? " );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				retorno[ POS_PREFS.PROCEMINFE.ordinal()] = rs.getString( POS_PREFS.PROCEMINFE.toString() ); 
				retorno[ POS_PREFS.AMBIENTENFE.ordinal()] = rs.getString( POS_PREFS.AMBIENTENFE.toString() ); 
				retorno[ POS_PREFS.CNPJFILIAL.ordinal()] = rs.getString( POS_PREFS.CNPJFILIAL.toString() ); 
				retorno[ POS_PREFS.SIGLAUF.ordinal()] = rs.getString( POS_PREFS.SIGLAUF.toString() ); 
				retorno[ POS_PREFS.TIPOEMISSAONFE.ordinal()] = rs.getString( POS_PREFS.TIPOEMISSAONFE.toString() ); 
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}

	public void setNfecf( NFEConnectionFactory nfecf ) {

		this.nfecf = nfecf;
	}
}
