/**
 * @version 03/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.rep <BR>
 *         Classe:
 * @(#)RelCliente.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Relatorio de clientes, em dois modos: completo e resumido.
 * 
 */

package org.freedom.modulos.rep;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

public class RelCliente extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtCodTpCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtDescTpCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCidade = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JRadioGroup<String, String> rgModo;

	private JRadioGroup<String, String> rgOrdem;

	private final ListaCampos lcTipoCli = new ListaCampos( this );

	private final ListaCampos lcVend = new ListaCampos( this );

	public RelCliente() {

		super( false );
		setTitulo( "Relatorio de clientes" );
		setAtribos( 50, 50, 325, 330 );

		montaRadioGrupos();
		montaListaCampos();
		montaTela();
	}

	private void montaRadioGrupos() {

		Vector<String> labs = new Vector<String>();
		labs.add( "completo" );
		labs.add( "resumido" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "R" );
		rgModo = new JRadioGroup<String, String>( 1, 2, labs, vals );

		Vector<String> labs1 = new Vector<String>();
		labs1.add( "C�digo" );
		labs1.add( "Descri��o" );
		Vector<String> vals1 = new Vector<String>();
		vals1.add( "CODCLI" );
		vals1.add( "RAZCLI" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, labs1, vals1 );
	}

	private void montaListaCampos() {

		lcTipoCli.add( new GuardaCampo( txtCodTpCli, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTpCli, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "RP" );
		lcTipoCli.setQueryCommit( false );
		lcTipoCli.setReadOnly( true );
		txtCodTpCli.setListaCampos( lcTipoCli );
		txtCodTpCli.setTabelaExterna( lcTipoCli, null );
		txtCodTpCli.setPK( true );
		txtCodTpCli.setNomeCampo( "CodTipoCli" );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.vend.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "RP" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setListaCampos( lcVend );
		txtCodVend.setTabelaExterna( lcVend, null );
		txtCodVend.setPK( true );
		txtCodVend.setNomeCampo( "CodVend" );
	}

	private void montaTela() {

		adic( new JLabel( "Modo :" ), 10, 10, 200, 20 );
		adic( rgModo, 10, 35, 290, 30 );
		adic( new JLabel( "Ordem do relatorio :" ), 10, 70, 200, 20 );
		adic( rgOrdem, 10, 95, 290, 30 );

		adic( new JLabel( "C�d.tp.cli." ), 10, 130, 77, 20 );
		adic( txtCodTpCli, 10, 150, 77, 20 );
		adic( new JLabel( "Descri��o do tipo de cliente" ), 90, 130, 210, 20 );
		adic( txtDescTpCli, 90, 150, 210, 20 );

		adic( new JLabel( "C�d.vend." ), 10, 170, 77, 20 );
		adic( txtCodVend, 10, 190, 77, 20 );
		adic( new JLabel( "Nome do vendedor" ), 90, 170, 210, 20 );
		adic( txtNomeVend, 90, 190, 210, 20 );

		adic( new JLabel( "Cidade" ), 10, 210, 290, 20 );
		adic( txtCidade, 10, 230, 290, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		try {

			String relatorio = "C".equals( rgModo.getVlrString() ) ? "rpclientecomp.jasper" : "rpclienteresum.jasper";
			String modo = "C".equals( rgModo.getVlrString() ) ? "( completo )" : "( resumido )";
			String filtro = "";

			StringBuilder sql = new StringBuilder();

			sql.append( "SELECT CODCLI,RAZCLI,NOMECLI,CNPJCLI,INSCCLI, " );
			sql.append( "ENDCLI,CIDCLI,ESTCLI,CEPCLI,BAIRCLI,DDDCLI, " );
			sql.append( "FONECLI,FAXCLI,EMAILCLI " );
			sql.append( "FROM RPCLIENTE " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? " );
			if ( txtCodTpCli.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CODTIPOCLI=" + txtCodTpCli.getVlrInteger().intValue() );
				filtro = "Tipo de cliente : " + txtDescTpCli.getVlrString().trim();
			}
			if ( txtNomeVend.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CODVEND=" + txtCodVend.getVlrInteger().intValue() );
				filtro += "  Vendedor : " + txtNomeVend.getVlrString().trim();
			}
			if ( txtCidade.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CIDCLI LIKE '%" + txtCidade.getVlrString() + "%' " );
			}
			sql.append( "ORDER BY " + rgOrdem.getVlrString() );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPCLIENTE" ) );
			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con.getConnection() );

			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/" + relatorio, "CLIENTES " + modo, filtro, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcTipoCli.setConexao( cn );
		lcVend.setConexao( cn );
	}

}
