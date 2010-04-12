/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPProduto.java <BR>
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
 * Tela para cadastro de produtos.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import java.util.ArrayList;
import java.util.List;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.modulos.rep.RPPrefereGeral.EPrefere;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class RPProduto extends FDados implements ActionListener, InsertListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private final JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtDescProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodBarra = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtCodGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private final JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtRefProdFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtPesoLiq = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 10, Aplicativo.casasDec );
	
	private final JTextFieldPad txtSaldoProd = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 10, Aplicativo.casasDec );

	private final JTextFieldPad txtPesoBruto = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 10, Aplicativo.casasDec );

	private final JTextFieldPad txtEmbalagem = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtPercIPI = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, Aplicativo.casasDec );

	private final JTextFieldPad txtComiss = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 6, Aplicativo.casasDec );
	
	private final JTextFieldPad txtCubagem = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 12, 5 );
	
	private final JTextFieldFK txtDescGrupo = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtSgGrupo = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private final JTextFieldFK txtDescUnid = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtPreco1 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPreco2 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtPreco3 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final JTextFieldPad txtPreco4 = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private final ListaCampos lcGrupo = new ListaCampos( this, "GP" );
	
	private final ListaCampos lcUnidade = new ListaCampos( this, "UD" );
	
	private final ListaCampos lcFornecedor = new ListaCampos( this, "FO" );
	
	private List<Object> prefere = new ArrayList<Object>();
	

	public RPProduto() {

		super( false );
		setTitulo( "Cadastro de produtos" );		
		setAtribos( 50, 50, 500, 420 );
		
		montaListaCampos();
		
		montaTela();
		
		setListaCampos( true, "PRODUTO", "RP" );
		
		lcGrupo.addInsertListener( this );
		lcUnidade.addInsertListener( this );
		lcCampos.addInsertListener( this );
	}
	
	private void montaListaCampos() {
		
		/*******************
		 *      GRUPO      *
		 *******************/
		
		lcGrupo.add( new GuardaCampo( txtCodGrupo, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, true ) );
		lcGrupo.add( new GuardaCampo( txtDescGrupo, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrupo.add( new GuardaCampo( txtSgGrupo, "SiglaGrup", "Sigla", ListaCampos.DB_SI, false ) );
		lcGrupo.montaSql( false, "GRUPO", "RP" );
		lcGrupo.setQueryCommit( false );
		lcGrupo.setReadOnly( true );
		txtCodGrupo.setTabelaExterna( lcGrupo );
		
		/*******************
		 *     UNIDADE     *
		 *******************/
		
		lcUnidade.add( new GuardaCampo( txtCodUnid, "CodUnid", "C�d.unid.", ListaCampos.DB_PK, true ) );
		lcUnidade.add( new GuardaCampo( txtDescUnid, "DescUnid", "Descri��o da unidade", ListaCampos.DB_SI, false ) );
		lcUnidade.montaSql( false, "UNIDADE", "RP" );
		lcUnidade.setQueryCommit( false );
		lcUnidade.setReadOnly( true ); 
		txtCodUnid.setTabelaExterna( lcUnidade );
		
		/*******************
		 *    FORNECEDOR   *
		 *******************/
		
		lcFornecedor.add( new GuardaCampo( txtCodFor, "CodFor", "C�d.for.", ListaCampos.DB_PK, true ) );
		lcFornecedor.add( new GuardaCampo( txtRazFor, "RazFor", "Raz�o social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFornecedor.montaSql( false, "FORNECEDOR", "RP" );
		lcFornecedor.setQueryCommit( false );
		lcFornecedor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFornecedor );
		
	}
	
	private void montaTela() {
		
		adicCampo( txtCodProd, 7, 30, 100, 20, "CodProd", "C�d.prod.", ListaCampos.DB_PK, true );
		adicCampo( txtRefProd, 110, 30, 100, 20, "RefProd", "Ref�rencia", ListaCampos.DB_SI, true );
		adicCampo( txtDescProd, 213, 30, 260, 20, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodBarra, 7, 70, 100, 20, "CodBarProd", "C�d.barras", ListaCampos.DB_SI, false );
		adicCampo( txtPesoLiq, 110, 70, 100, 20, "PesoLiqProd", "Peso liq.", ListaCampos.DB_SI, false );
		adicCampo( txtPesoBruto, 213, 70, 100, 20, "PesoBrutProd", "Peso bruto", ListaCampos.DB_SI, false );
		adicCampo( txtEmbalagem, 316, 70, 157, 20, "EmbalaProd", "Embalagem", ListaCampos.DB_SI, false );
		
		adicCampo( txtPreco1, 7, 110, 100, 20, "PrecoProd1", "Pre�o 1", ListaCampos.DB_SI, false );
		adicCampo( txtPreco2, 110, 110, 100, 20, "PrecoProd2", "Pre�o 2", ListaCampos.DB_SI, false );
		adicCampo( txtPreco3, 213, 110, 100, 20, "PrecoProd3", "Pre�o 3", ListaCampos.DB_SI, false );
		adicCampo( txtPreco4, 316, 110, 157, 20, "PrecoCusto", "Pre�o 4", ListaCampos.DB_SI, false );
		
		adicCampo( txtPercIPI, 7, 150, 100, 20, "PercIPIProd", "% IPI", ListaCampos.DB_SI, false );
		adicCampo( txtComiss, 110, 150, 100, 20, "ComisProd", "% Comiss�o", ListaCampos.DB_SI, false );
		adicCampo( txtCubagem, 213, 150, 100, 20, "CubagemProd", "Cubagem", ListaCampos.DB_SI, false );
		adicCampo( txtSaldoProd, 316, 150, 100, 20, "SaldoProd", "Saldo", ListaCampos.DB_SI, false );
		
		adicCampo( txtCodGrupo, 7, 190, 100, 20, "CodGrup", "C�d.grupo", ListaCampos.DB_FK, txtDescGrupo, true );
		adicDescFK( txtDescGrupo, 110, 190, 363, 20, "DescGrupo", "Descri��o do grupo" );
		
		adicCampo( txtCodUnid, 7, 230, 100, 20, "CodUnid", "C�d.unidade", ListaCampos.DB_FK, txtDescUnid, true );
		adicDescFK( txtDescUnid, 110, 230, 363, 20, "DescUnid", "Descri��o da unidade" );
		
		adicCampo( txtCodFor, 7, 270, 100, 20, "CodFor", "C�d.for.", ListaCampos.DB_FK, txtRazFor, false );
		adicDescFK( txtRazFor, 110, 270, 363, 20, "RazFor", "Raz�o social do fornecedor" );
		
		adicCampo( txtRefProdFor, 7, 310, 203, 20, "RefProdFor", "Ref�rencia no fornecedor", ListaCampos.DB_SI, false );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcGrupo.setConexao( cn );
		lcUnidade.setConexao( cn );
		lcFornecedor.setConexao( cn );
		
		prefere = RPPrefereGeral.getPrefere( cn );
		
		txtCodGrupo.setVlrString( (String) prefere.get( EPrefere.CODGRUPO.ordinal() ) );
		lcGrupo.carregaDados();
		
		txtCodUnid.setVlrString( (String) prefere.get( EPrefere.CODUNID.ordinal() ) );
		lcUnidade.carregaDados();
	}

	public void afterInsert( InsertEvent ievt ) {

		if( ievt.getListaCampos() == lcCampos ){

			txtCodGrupo.setVlrString( (String) prefere.get( EPrefere.CODGRUPO.ordinal() ) );
			lcGrupo.carregaDados();
			
			txtCodUnid.setVlrString( (String) prefere.get( EPrefere.CODUNID.ordinal() ) );
			lcUnidade.carregaDados();
		}		
	}

	public void beforeInsert( InsertEvent ievt ) {}

}
