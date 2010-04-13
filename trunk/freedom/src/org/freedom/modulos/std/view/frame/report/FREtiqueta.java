/**
 * @version 19/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FREtiqueta.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Classe para impress�o de etiquetas de clientes.
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.business.object.EtiquetaCli;
import org.freedom.business.object.EtiquetaComis;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.object.Etiqueta;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.modulos.std.view.frame.crud.plain.FModEtiqueta;

public class FREtiqueta extends FRelatorio implements CarregaListener, RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodModEtiq = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescModEtiq = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodTipo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipo = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtUfCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0);
	
	private JTextFieldPad txtCidadeCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0);
	
	private JTextFieldPad txtModEtiq = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0);

	private ListaCampos lcModEtiq = new ListaCampos( this );

	private ListaCampos lcSetor = new ListaCampos( this );

	private ListaCampos lcTipo = new ListaCampos( this );

	private ListaCampos lcCliente = new ListaCampos( this );

	private ListaCampos lcPapel = new ListaCampos( this, "PL" );

	private ListaCampos lcVendedor = new ListaCampos( this, "VD" );

	private JTextAreaPad txaEtiqueta = new JTextAreaPad( 500 );

	private JTextFieldPad txtNColModEtiq = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtPostScript= new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private Etiqueta objEtiq = null;
	
	private EtiquetaComis objEtiqComis = new EtiquetaComis();

	private JTextFieldPad txtCodPapel = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDescPapel = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtLinPapel = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtColPapel = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtEECModEtiq = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtComprimido = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JPanelPad pnTotal = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnDet = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCab = new JPanelPad( 480, 230 );

	private JTablePad tab = new JTablePad();

	private JScrollPane spnDet = new JScrollPane( tab );

	private JButtonPad btAdiciona = new JButtonPad( Icone.novo( "btGerar.gif" ) );

	private JButtonPad btLimpa = new JButtonPad( Icone.novo( "btRetorno.gif" ) );

	private boolean bMontaTab = true;

	private JButtonPad btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JComboBoxPad cbAtivoCli = null;
	
	private JRadioGroup<String, Object> cbComissionados = null;
	
	private String tabelabd = null;

	public FREtiqueta() {

		setPanel( pnTotal );

		setTitulo( "Impress�o de etiquetas" );

		setAtribos( 20, 20, 630, 350 );

		pnDet.add( spnDet, BorderLayout.CENTER );
		pnTotal.add( pinCab, BorderLayout.NORTH );
		pnTotal.add( pnDet, BorderLayout.CENTER );

		Vector<String> lAtivo = new Vector<String>();
		Vector<String> vAtivo = new Vector<String>();
		lAtivo.addElement( "<--Selecione-->" );
		lAtivo.addElement( "Ativos" );
		lAtivo.addElement( "Inativos" );
		vAtivo.addElement( "" );
		vAtivo.addElement( "Ativos" );
		vAtivo.addElement( "Inativos" );

		cbAtivoCli = new JComboBoxPad( lAtivo, vAtivo, JComboBoxPad.TP_INTEGER, 5, 0 );

		 Vector<String> vLabs1 = new Vector<String>();
		 Vector<String> vVals1 = new Vector<String>();
		
		vLabs1.addElement("Comissionado");
 		vLabs1.addElement("Cliente"); 
 		vVals1.addElement("CM");
 		vVals1.addElement("CL");
		    
 		cbComissionados = new JRadioGroup<String, Object>( 1, 2, vLabs1.toArray(), vVals1.toArray() );
 		cbComissionados.setVlrString("CL");
 		cbComissionados.addRadioGroupListener( this );
 		
		lcPapel.add( new GuardaCampo( txtCodPapel, "Codpapel", "Cod.papel", ListaCampos.DB_PK, false ) );
		lcPapel.add( new GuardaCampo( txtDescPapel, "Descpapel", "Descri��o do papel", ListaCampos.DB_SI, false ) );
		lcPapel.add( new GuardaCampo( txtColPapel, "Colpapel", "Num. colunas", ListaCampos.DB_SI, false ) );
		lcPapel.add( new GuardaCampo( txtLinPapel, "Linpapel", "Lin. colunas", ListaCampos.DB_SI, false ) );
		lcPapel.montaSql( false, "PAPEL", "SG" );
		lcPapel.setQueryCommit( false );
		lcPapel.setReadOnly( true );
		txtCodPapel.setTabelaExterna( lcPapel );

		lcModEtiq.add( new GuardaCampo( txtCodModEtiq, "CodModEtiq", "C�d.mod.", ListaCampos.DB_PK, true ) );
		lcModEtiq.add( new GuardaCampo( txtDescModEtiq, "DescModEtiq", "Descri��o do modelo de etiqueta", ListaCampos.DB_SI, false ) );
		lcModEtiq.add( new GuardaCampo( txaEtiqueta, "TxaModEtiq", "Corpo", ListaCampos.DB_SI, false ) );
		lcModEtiq.add( new GuardaCampo( txtNColModEtiq, "NColModEtiq", "Colunas", ListaCampos.DB_SI, false ) );
		lcModEtiq.add( new GuardaCampo( txtCodPapel, "Codpapel", "C�d.papel", ListaCampos.DB_FK, false ) );
		lcModEtiq.add( new GuardaCampo( txtEECModEtiq, "EECModEtiq", "entre col.", ListaCampos.DB_SI, false ) );
		lcModEtiq.add( new GuardaCampo( txtComprimido, "Comprimido", "Imp. Comp.", ListaCampos.DB_SI, false ) );
		lcModEtiq.add( new GuardaCampo( txtPostScript, "PosScript", "Pos.Script", ListaCampos.DB_SI, false ) );
		lcModEtiq.add( new GuardaCampo( txtModEtiq, "ModEtiq", "modelo", ListaCampos.DB_SI, false ) );
		
		lcModEtiq.setReadOnly( true );
		lcModEtiq.montaSql( false, "MODETIQUETA", "SG" );
		txtCodModEtiq.setTabelaExterna( lcModEtiq );
		txtCodModEtiq.setFK( true );
		txtCodModEtiq.setNomeCampo( "CodModEtiq" );

		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "C�d.setor", ListaCampos.DB_PK, false ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descri��o do setor", ListaCampos.DB_SI, false ) );
		lcSetor.setReadOnly( true );
		lcSetor.montaSql( false, "SETOR", "VD" );
		txtCodSetor.setTabelaExterna( lcSetor );
		txtCodSetor.setFK( true );
		txtCodSetor.setNomeCampo( "CodSetor" );

		lcTipo.add( new GuardaCampo( txtCodTipo, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, txtDescTipo, false ) );
		lcTipo.add( new GuardaCampo( txtDescTipo, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipo.setReadOnly( true );
		lcTipo.montaSql( false, "TIPOCLI", "VD" );
		txtCodTipo.setTabelaExterna( lcTipo );
		txtCodTipo.setFK( true );
		txtCodTipo.setNomeCampo( "CodTipoCli" );

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.Comiss.", ListaCampos.DB_PK, txtNomeVend, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do Comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.setReadOnly( true );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CodVend" );

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli.", ListaCampos.DB_PK, txtRazCli, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		pinCab.adic( new JLabelPad( "C�d.mod." ), 7, 5, 80, 20 );
		pinCab.adic( txtCodModEtiq, 7, 25, 80, 20 );
		pinCab.adic( new JLabelPad( "Descri��o do modelo" ), 90, 5, 260, 20 );
		pinCab.adic( txtDescModEtiq, 90, 25, 260, 20 );

		pinCab.adic( new JLabelPad( "Status" ), 370, 5, 100, 20 );
		pinCab.adic( cbAtivoCli, 370, 25, 135, 25 );
		
		pinCab.adic( new JLabelPad("Cidade"), 370, 85, 100, 20 );
		pinCab.adic( txtCidadeCli, 370, 105, 100, 20 );
		
		pinCab.adic( new JLabelPad("UF"), 370, 125, 100, 20 );
		pinCab.adic( txtUfCli, 370, 145, 100, 20 );
		
		pinCab.adic( cbComissionados, 370, 180, 235, 30 );

		pinCab.adic( new JLabelPad( "C�d.tp.cli." ), 7, 45, 280, 20 );
		pinCab.adic( txtCodTipo, 7, 65, 80, 20 );
		pinCab.adic( new JLabelPad( "Descri��o do tipo de cliente" ), 90, 45, 280, 20 );
		pinCab.adic( txtDescTipo, 90, 65, 260, 20 );

		pinCab.adic( new JLabelPad( "C�d.setor" ), 7, 85, 280, 20 );
		pinCab.adic( txtCodSetor, 7, 105, 80, 20 );
		pinCab.adic( new JLabelPad( "Descri��o do setor" ), 90, 85, 280, 20 );
		pinCab.adic( txtDescSetor, 90, 105, 260, 20 );

		pinCab.adic( new JLabelPad( "C�d.Cli." ), 7, 125, 280, 20 );
		pinCab.adic( txtCodCli, 7, 145, 80, 20 );

		pinCab.adic( new JLabelPad( "Raz�o do cliente" ), 90, 125, 280, 20 );
		pinCab.adic( txtRazCli, 90, 145, 260, 20 );

		pinCab.adic( new JLabelPad( "C�d.Comiss." ), 7, 165, 280, 20 );
		pinCab.adic( txtCodVend, 7, 185, 80, 20 );

		pinCab.adic( new JLabelPad( "Nome do comissionado" ), 90, 165, 280, 20 );
		pinCab.adic( txtNomeVend, 90, 185, 260, 20 );

		btAdiciona.setToolTipText( "Adiciona" );
		btLimpa.setToolTipText( "Limpa o grid" );
		btExcluir.setToolTipText( "Exclui o �tem selecionado" );

		pinCab.adic( btAdiciona, 575, 15, 30, 30 );
		pinCab.adic( btLimpa, 575, 48, 30, 30 );
		pinCab.adic( btExcluir, 575, 81, 30, 30 );

		lcModEtiq.addCarregaListener( this );

		btAdiciona.addActionListener( this );
		btLimpa.addActionListener( this );
		btExcluir.addActionListener( this );
		setPrimeiroFoco( txtCodModEtiq );

	}

	private void excluir() {

		if ( tab.getLinhaSel() > -1 ) {
			tab.delLinha( tab.getLinhaSel() );
		}
	}

	public void montaTabela( JTablePad tb ) {

		if( cbComissionados.getVlrString().equals( "CL" ) ){
			objEtiq = new EtiquetaCli();
		}
		else if( cbComissionados.getVlrString().equals( "CM" )){
			objEtiq = new EtiquetaComis();
		}			
		
		objEtiq.setTexto( txaEtiqueta.getVlrString() );
		Vector<?> vLabelsColunas = objEtiq.getLabelsColunasAdic();
		Vector<?> vTamanhos = objEtiq.getTamsAdic();
		tb.limpa();
		
		for ( int i = 0; vLabelsColunas.size() > i; i++ ) {
			tb.adicColuna( vLabelsColunas.elementAt( i ).toString() );
			String sTmp = vTamanhos.elementAt( i ).toString();
			int iiTam = Integer.parseInt( sTmp ) * 5;
			tb.setTamColuna( iiTam, i );
		}
		for ( int i = 0; vLabelsColunas.size() > i; i++ ) {
			String sTmp = vTamanhos.elementAt( i ).toString();
			int iiTam = Integer.parseInt( sTmp ) * 7;
			tb.setTamColuna( iiTam, i );
		}
		tb.adicColuna( "C�d.cli." );
		tb.setTamColuna( 80, tb.getNumColunas()-1 );

		bMontaTab = false;
	}

	@SuppressWarnings("unchecked")
	public void adicItens() {

		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement( montaQuery() );
			rs = ps.executeQuery();

			Vector<Object> vLinha = new Vector<Object>();

			while ( rs.next() ) {
				vLinha = new Vector<Object>();
				for ( int i = 1; objEtiq.getCamposAdic().size() >= i; i++ ) {
					String sTmp = rs.getString( i ) != null ? rs.getString( i ) : "";
					vLinha.addElement( sTmp );
				}
				vLinha.addElement( new Integer(rs.getInt( "codcli" ) ) );
				tab.adicLinha( (Vector<Object>) vLinha.clone() );
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btAdiciona ) {
			if ( ( txtCodModEtiq.getVlrInteger().intValue() > 0 ) ) {
				if ( bMontaTab )
					montaTabela( tab );
				adicItens();
			}
			else if ( txtCodModEtiq.getVlrInteger().intValue() == 0 ) {
				Funcoes.mensagemInforma( this, "Voc� deve selecionar um modelo de etiqueta!" );
			}
		}
		else if ( evt.getSource() == btLimpa ) {
			tab.limpa();
		}
		else if ( evt.getSource() == btExcluir )
			excluir();

		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcModEtiq.setConexao( cn );
		lcSetor.setConexao( cn );
		lcTipo.setConexao( cn );
		lcPapel.setConexao( cn );
		lcCliente.setConexao( cn );
		lcVendedor.setConexao( cn );
	}
	
	private boolean removeEtiquetas() {

		boolean retorno = false;

		try {

			PreparedStatement ps = con.prepareStatement( "DELETE FROM VDETIQCLI E WHERE E.NRCONEXAO=?" );
			ps.setInt( 1, getNrConexao() );

			int exec = ps.executeUpdate();

			ps.close();

			con.commit();

			if ( exec > -1 ) {
				retorno = true;
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao limpar tabela temporaria de etiquetas!\n" + e.getMessage(), true, con, e );
		}

		return retorno;
	}

	private int getNrConexao() {

		int conexao = -1;

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT CURRENT_CONNECTION FROM SGEMPRESA E WHERE E.CODEMP=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				conexao = rs.getInt( "CURRENT_CONNECTION" );
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar n�mero da conex�o!\n" + e.getMessage(), true, con, e );
		}

		return conexao;
	}
	
	private boolean persistEtiquetas() {

		boolean retorno = false;

		int conexao = getNrConexao();

		StringBuilder sSql = new StringBuilder();

		sSql.append( "INSERT INTO VDETIQCLI " );
		sSql.append( "( NRCONEXAO, CODEMP, CODFILIAL, CODCLI ) " );
		sSql.append( "VALUES " );
		sSql.append( "( ?, ?, ?, ? )" );

		String sql = sSql.toString();

		int codcli = 0;
	
		etiquetas : {

			for ( int i = 0; i < tab.getNumLinhas(); i++ ) {

				codcli = (Integer) tab.getValor( i, tab.getNumColunas()-1 );
				
				if ( !insertEtiqueta( conexao, codcli, sql ) ) {
					break etiquetas;
				}
				
			}
			
			retorno = true;
		}

		return retorno;
	}

	private boolean insertEtiqueta( int conexao, int codcli, String sql ) {

		boolean retorno = true;

		try {

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, conexao );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "VDETIQCLI" ) );
			ps.setInt( 4, codcli );

			ps.execute();

			con.commit();
		} catch ( SQLException e ) {
			retorno = false;
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao relacionar etiquetas!\n" + e.getMessage(), true, con, e );
		}

		return retorno;
	}
	

	@SuppressWarnings("unchecked")
	public void imprimir( boolean bVisualizar ) {

		String sTxa = txaEtiqueta.getVlrString();
		FModEtiqueta etiquetas = new FModEtiqueta();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iNColModEtiq = txtNColModEtiq.getVlrInteger().intValue();
		ImprimeOS imp = null;
		ResultSet rs1 = null;
		StringBuffer sSQL = new StringBuffer();
		
		if ( txtPostScript.getVlrString().equals( "S" ) ) {
			
			if ( removeEtiquetas() ) {

				if ( persistEtiquetas() ) {
					
					sSQL.append( "SELECT C.CODCLI, C.RAZCLI, C.ENDCLI, C.NUMCLI, C.BAIRCLI, C.UFCLI, C.CIDCLI, C.CEPCLI  " );
					sSQL.append( "FROM VDCLIENTE C, VDETIQCLI E " );
					sSQL.append( "WHERE C.CODEMP=? AND C.CODFILIAL=? AND E.CODEMP=C.CODEMP AND E.CODFILIAL=C.CODFILIAL AND " );
					sSQL.append( "E.CODCLI=C.CODCLI AND E.NRCONEXAO=? ORDER BY C.RAZCLI" );
					
					try {
						
						PreparedStatement ps1 = con.prepareStatement( sSQL.toString() );
						ps1.setInt( 1, Aplicativo.iCodEmp );
						ps1.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
						ps1.setInt( 3, getNrConexao() );
						rs1 = ps1.executeQuery();
						
					} catch ( Exception e ) {
						e.printStackTrace();
					}
							
					FPrinterJob dlGr = null;
					dlGr = new FPrinterJob( "relatorios/etiquetas/clientes/" + txtModEtiq.getVlrString(), "Etiquetas", null, rs1, null, this );
					
					if( bVisualizar ){
						dlGr.setVisible( true );
					}else{
						try {
							JasperPrintManager.printReport( dlGr.getRelatorio(), true );
						} catch ( JRException e ) {
							Funcoes.mensagemErro( this, "Erro ao montar etiquetas\n"+e.getMessage() );
							e.printStackTrace();
						}  
					  
					}
					try {
						con.commit();
					} catch ( Exception e ) {
						e.printStackTrace();
					}

				}
			}
		}
		else {

			try {

				if ( txtCodModEtiq.getVlrString().equals( "" ) ) {
					Funcoes.mensagemInforma( this, "C�digo do modelo em branco!" );
					return;
				}
				imp = new ImprimeOS( "", con );
				imp.setImpEject( true );
				imp.verifLinPag();
				imp.setTitulo( "Etiquetas" );

				if ( sTxa != null ) {
					try {
						ps = con.prepareStatement( montaQuery() );
						rs = ps.executeQuery();
						Vector<Vector<?>> vCol = new Vector<Vector<?>>();
						Vector<Vector<Vector<?>>> vCols = new Vector<Vector<Vector<?>>>();
						Vector<?> vVal = new Vector<Object>();

						int iAdic = 0;
						for ( int i = 0; tab.getNumLinhas() > i; i++ ) {
							vVal = aplicCampos( i );
							vCol.addElement( vVal );
							iAdic++;

							if ( iNColModEtiq == iAdic ) {
								vCols.addElement( (Vector<Vector<?>>) vCol.clone() );
								vCol = new Vector<Vector<?>>();
								iAdic = 0;
							}
						}

						if ( vCol.size() > 0 ) // Recolhe os restos e joga em um nov elemento...
							vCols.addElement( vCol );

						impCol( imp, vCols );

						rs.close();
						ps.close();
						con.commit();

					} catch ( SQLException err ) {
						Funcoes.mensagemErro( this, "Erro ao consultar informa��es!\n" + err.getMessage(), true, con, err );
						err.printStackTrace();
					}

					imp.eject();
					imp.fechaGravacao();

					if ( bVisualizar ) {
						imp.preview( this );
					}
					else {
						imp.print();
					}

				}
			} finally {
				ps = null;
				rs = null;
				imp = null;
			}
		}
	}

	private String montaQuery() {
		
		String sSQL = "";
		try {
			String sCampos = "";
			Vector<?> vCamposAdic = null;
									
			vCamposAdic = objEtiq.getCamposAdic();
			
			String sWhere = "WHERE CODEMP=" + Aplicativo.iCodEmp + " AND CODFILIAL=" + ListaCampos.getMasterFilial( objEtiq.getNometabela() );

			try {
				if ( !txtCodSetor.getVlrString().equals( "" ) ) {
					sWhere += " AND CODSETOR=" + txtCodSetor.getVlrInteger().intValue();
					sWhere += " AND CODEMPSR=" + Aplicativo.iCodEmp;
					sWhere += " AND CODFILIALSR=" + lcSetor.getCodFilial();
				}
				if ( !txtCodTipo.getVlrString().equals( "" ) ) {
					sWhere += " AND CODTIPOCLI=" + txtCodTipo.getVlrInteger().intValue();
					sWhere += " AND CODEMPTC=" + Aplicativo.iCodEmp;
					sWhere += " AND CODFILIALTC=" + lcTipo.getCodFilial();
				}
				if ( !txtCodCli.getVlrString().equals( "" ) ) {
					sWhere += " AND CODCLI=" + txtCodCli.getVlrInteger().intValue();
				}
				if( !txtCidadeCli.getVlrString().equals( "" ) ){
					
					if( cbComissionados.getVlrString().equals( "CL" )){
						sWhere += " AND CIDCLI=" + "'" + txtCidadeCli.getVlrString().trim() + "'";
					}
					else if( cbComissionados.getVlrString().equals( "CM" )){
						sWhere += " AND CIDVEND=" + "'" + txtCidadeCli.getVlrString().trim() + "'";
					}
				}
				if (!txtUfCli.getVlrString().equals( "" )){
					
					if( cbComissionados.getVlrString().equals( "CL" )){
						sWhere += " AND UFCLI=" + "'" + txtUfCli.getVlrString().trim() + "'";
					}
					else if( cbComissionados.getVlrString().equals( "CM" )){
						sWhere += " AND UFVEND=" + "'" + txtUfCli.getVlrString().trim() + "'";
					}
				}
				
				if ( cbAtivoCli.getVlrString() != null ) {
					if ( cbAtivoCli.getVlrString().equals( "Ativos" ) )
					{
						if( cbComissionados.getVlrString().equals( "CL" )){
							sWhere += " AND ATIVOCLI='S'";
						}
						else if( cbComissionados.getVlrString().equals( "CM" )){
							sWhere += " AND ATIVOCOMIS='S'";
						}
						
					}
					else if ( cbAtivoCli.getVlrString().equals( "Inativos" ) ){
						
						if( cbComissionados.getVlrString().equals( "CL" )){
							sWhere += " AND ATIVOCLI='N'";
						}
						else if( cbComissionados.getVlrString().equals( "CM" )){
							sWhere += " AND ATIVOCOMIS='N'";
						}
					}
				}
				if ( !txtCodVend.getVlrString().equals( "" ) ) {
					
					if( cbComissionados.getVlrString().equals( "CL" )){
						sWhere += " AND CODVEND=" + txtCodVend.getVlrInteger().intValue();
						sWhere += " AND CODEMPVD=" + Aplicativo.iCodEmp;
						sWhere += " AND CODFILIALVD=" + lcVendedor.getCodFilial();
					}
					else if( cbComissionados.getVlrString().equals( "CM" )){
						sWhere += " AND CODVEND=" + txtCodVend.getVlrInteger().intValue();
					}
				}

				for ( int i = 0; vCamposAdic.size() > i; i++ ) {
					sCampos = sCampos + vCamposAdic.elementAt( i ).toString() + ",";
				}

				sSQL = "SELECT " + sCampos.substring( 0, sCampos.length() - 1 ) + "," + objEtiq.getPK() + " FROM " + 
				     objEtiq.getNometabela() + " " + sWhere + " ORDER BY 2";
				System.out.println("***" + sSQL);
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return sSQL;
	}

	private void impCol( ImprimeOS imp, Vector<Vector<Vector<?>>> vCols ) {

		try {
			int iColsEtiq = txtNColModEtiq.getVlrInteger().intValue();
			int iLins = txtLinPapel.getVlrInteger().intValue();
			int iColPapel = txtColPapel.getVlrInteger().intValue();
			int iEECEtiq = txtEECModEtiq.getVlrInteger().intValue();
			int iCol = 0;
			int iSalto = 1;
			int iNumLinEtiq = objEtiq.getNumLinEtiq();
			try {
				if ( txtComprimido.getVlrString() != null )
					if ( txtComprimido.getVlrString().equals( "S" ) )
						imp.say( imp.pRow(), 0, "" + imp.comprimido() );
				for ( int i1 = 0; vCols.size() > i1; i1++ ) {
					Vector<?> vCol = ( vCols.elementAt( i1 ) );
					for ( int iNumLinhaEtiqAtual = 0; iNumLinEtiq > iNumLinhaEtiqAtual; iNumLinhaEtiqAtual++ ) {
						for ( int i2 = 0; iColsEtiq > i2; i2++ ) {

							if ( vCol.size() > i2 ) {
								Vector<?> vEtiqueta = (Vector<?>) vCol.elementAt( i2 );
								String sImp = "";
								if ( vEtiqueta.size() > iNumLinhaEtiqAtual )
									sImp = vEtiqueta.elementAt( iNumLinhaEtiqAtual ).toString();
								imp.say( imp.pRow() + iSalto, iCol, sImp );
							}

							iSalto = 0;
							iCol += ( ( iColPapel / iColsEtiq ) + ( iEECEtiq ) );

							if ( iCol >= iColPapel ) {
								iCol = 0;
								iSalto = 1;

							}

							if ( ( imp.pRow() == ( iLins ) ) && ( ( iColsEtiq - 1 ) == ( i2 ) ) ) {
								imp.eject();
								imp.incPags();
								iCol = 0;
								iSalto = 1;

								if ( ( iNumLinhaEtiqAtual < ( iNumLinEtiq - 1 ) ) ) {
									iNumLinhaEtiqAtual = 0;
									i2 = -1;
								}

							}
						}
						iSalto = 1;
					}
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			vCols = null;
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

//		objEtiq.setTexto( txaEtiqueta.getVlrString() ); XXXX
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcModEtiq ) {
			if ( tab.getNumLinhas() > 0 ) {
				Funcoes.mensagemInforma( this, "Voc� deve limpar a sele��o atual caso queira trocar de modelo!" );
				lcModEtiq.cancelCarrega();
				cevt.cancela();
				return;
			}
		}
	}

	private Vector<?> aplicCampos( int iLinha ) {

		String sCampo = "";
		String sRetorno = txaEtiqueta.getVlrString();
		sRetorno = sRetorno.replaceAll( "\\\n", "[Q]" );
		Vector<?> vRet = null;
		if ( iLinha > -1 ) {
			try {
				Vector<?> vMascAdic = objEtiq.getMascarasAdic();
				Vector<?> vValAdic = objEtiq.getValoresAdic();
				Vector<?> vCamposAdic = objEtiq.getCamposAdic();
				if ( sRetorno != null ) {
					try {
						for ( int i = 0; vCamposAdic.size() > i; i++ ) {
							String sValAdic = vValAdic.elementAt( i ).toString();
							String sFragmento = sRetorno.substring( sRetorno.indexOf( "[" + sValAdic ) );
							sFragmento = sFragmento.substring( 0, ( "\\" + sFragmento ).indexOf( "]" ) );

							sCampo = tab.getValor( iLinha, i ).toString();

							if ( vMascAdic.elementAt( i ) != null )
								sCampo = Funcoes.setMascara( sCampo, vMascAdic.elementAt( i ).toString() );

							int iTmp = Funcoes.contaChar( sFragmento, '-' );

							if ( sCampo.length() >= iTmp )
								sCampo = sCampo.substring( 0, iTmp );
							else
								sCampo = sCampo + StringFunctions.replicate( " ", iTmp - sCampo.length() );

							sRetorno = sRetorno.replaceAll( "\\" + sFragmento, sCampo );

						}
					} catch ( Exception err ) {
						Funcoes.mensagemErro( this, "Erro na troca de dados!\n" + err.getMessage(), true, con, err );
					}
				}
				vRet = Funcoes.stringToVector( sRetorno, "[Q]" );
			} finally {
				sCampo = null;
				sRetorno = null;
			}
		}
		return vRet;
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if( cbComissionados.getVlrString().equals( "CM" )){
			
			txtCodCli.setVlrString( "" );
			txtCodSetor.setVlrString( "" );
			txtCodTipo.setVlrString( "" );
			
			txtDescTipo.setVlrString( "" );
			txtDescSetor.setVlrString( "" );
			txtRazCli.setVlrString( "" );
			
			txtCodCli.setSoLeitura( true );
			txtCodSetor.setSoLeitura( true );
			txtCodTipo.setSoLeitura( true );
		}
		if( cbComissionados.getVlrString().equals( "CL" )){
			
			txtCodCli.setSoLeitura( false );
			txtCodSetor.setSoLeitura( false );
			txtCodTipo.setSoLeitura( false );
		}
		
	}
}
