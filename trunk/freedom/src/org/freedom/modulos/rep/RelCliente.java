/**
 * @version 03/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelCliente.java <BR>
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
 * Relatorio de clientes, em dois modos: completo e resumido.
 * 
 */

package org.freedom.modulos.rep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class RelCliente extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JRadioGroup rgModo;
	
	private JRadioGroup rgOrdem;
	
	private final ListaCampos lcCli = new ListaCampos( this );
	
	private final ListaCampos lcVend = new ListaCampos( this );

	public RelCliente() {

		super();
		setTitulo( "Relatorio de clientes" );		
		setAtribos( 50, 50, 325, 290 );
		
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "RP" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setListaCampos( lcCli );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setPK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.vend.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "RP" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setListaCampos( lcVend );
		txtCodVend.setTabelaExterna( lcVend );
		txtCodVend.setPK( true );
		txtCodVend.setNomeCampo( "CodVend" );
		
		Vector<String> labs = new Vector<String>();
		labs.add( "completo" );
		labs.add( "resumido" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "R" );
		rgModo = new JRadioGroup( 1, 2, labs, vals );
		
		Vector<String> labs1 = new Vector<String>();
		labs1.add( "C�digo" );
		labs1.add( "Descri��o" );
		Vector<String> vals1 = new Vector<String>();
		vals1.add( "CODVEND" );
		vals1.add( "NOMEVEND" );
		rgOrdem = new JRadioGroup( 1, 2, labs1, vals1 );
		
		adic( new JLabel( "Modo :" ), 10, 10, 200, 20 );
		adic( rgModo, 10, 35, 290, 30 );
		adic( new JLabel( "Ordem do relatorio :" ), 10, 70, 200, 20 );
		adic( rgOrdem, 10, 95, 290, 30 );
		
		adic( new JLabel( "C�d.cli." ), 10, 130, 77, 20 );
		adic( txtCodCli, 10, 150, 77, 20 );
		adic( new JLabel( "Raz�o social do cliente" ), 90, 130, 210, 20 );
		adic( txtRazCli, 90, 150, 210, 20 );
		
		adic( new JLabel( "C�d.vend." ), 10, 170, 77, 20 );
		adic( txtCodVend, 10, 190, 77, 20 );
		adic( new JLabel( "Nome do vendedor" ), 90, 170, 210, 20 );
		adic( txtNomeVend, 90, 190, 210, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		try {
			
			String relatorio = "C".equals( rgModo.getVlrString() ) ? "rpclientecomp.jasper" : "rpclienteresum.jasper";
			String modo = "C".equals( rgModo.getVlrString() ) ? "( completo )" : " ( resumido )";
			String filtro = null;
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT CODCLI,RAZCLI,NOMECLI,CNPJCLI,INSCCLI, " );
			sql.append( "ENDCLI,CIDCLI,ESTCLI,CEPCLI,BAIRCLI,DDDCLI, " );
			sql.append( "FONECLI,FAXCLI,EMAILCLI " );
			sql.append( "FROM RPCLIENTE " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? " );
			if ( txtNomeVend.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CODVEND=" + txtCodVend.getVlrInteger().intValue() );
				filtro = "Vendedor : " + txtNomeVend.getVlrString().trim();
			}
			if ( txtNomeVend.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CODVEND=" + txtCodVend.getVlrInteger().intValue() );
				filtro = "Fornecedor : " + txtNomeVend.getVlrString().trim();
			}
			sql.append( "ORDER BY " + rgOrdem.getVlrString() );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPPRODUTO" ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "SUBREPORT_DIR", RelCliente.class.getResource( "relatorios/" ).getPath() );
			hParam.put( "REPORT_CONNECTION", con );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/"+relatorio, "CLIENTES" + modo, filtro, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
			
			dispose();
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}

	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcCli.setConexao( cn );
		lcVend.setConexao( cn );
	}

}
