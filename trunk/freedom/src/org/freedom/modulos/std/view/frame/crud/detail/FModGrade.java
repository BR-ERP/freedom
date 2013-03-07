/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FModGrade.java <BR>
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

package org.freedom.modulos.std.view.frame.crud.detail;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaProd;

public class FModGrade extends FDetalhe implements PostListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTextFieldPad txtCodModG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescProdModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtDescCompModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtRefModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtCodFabModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtCodBarModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtCodItModG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtOrdemItModG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtDescItModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodVarG = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtRefItModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldPad txtCodFabItModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtCodBarItModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescVarG = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldPad txtDescCompItModG = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	private ListaCampos lcVarG = new ListaCampos( this, "VG" );
	
	private Integer ordem = 1;

	public FModGrade() {

		setTitulo( "Cadastro de Modelos da Grade" );
		setAtribos( 50, 20, 620, 400 );
		setAltCab( 120 );
		pinCab = new JPanelPad( 590, 110 );
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, true ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd.setWhereAdic( "ATIVOPROD='S'" );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );

		adicCampo( txtCodModG, 7, 20, 70, 20, "CodModG", "C�d.mod.g.", ListaCampos.DB_PK, true );
		adicCampo( txtDescModG, 80, 20, 197, 20, "DescModG", "Descri��o do modelo de grade", ListaCampos.DB_SI, true );
		adicCampo( txtCodProd, 280, 20, 77, 20, "CodProd", "C�d.prod.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescProd, 360, 20, 200, 20, "DescProd", "Desci��o do produto" );
		adicCampo( txtDescProdModG, 7, 60, 144, 20, "DescProdModG", "Desc. inicial", ListaCampos.DB_SI, true );
		adicCampo( txtDescCompModG, 154, 60, 187, 20, "DescCompProdModG", "Descri��o completa inicial", ListaCampos.DB_SI, false );
		
		adicCampo( txtRefModG, 344, 60, 70, 20, "RefModG", "Ref.inic.", ListaCampos.DB_SI, true );
		adicCampo( txtCodFabModG, 417, 60, 70, 20, "CodFabModG", "C�d.fab.inic.", ListaCampos.DB_SI, true );
		adicCampo( txtCodBarModG, 490, 60, 70, 20, "CodBarModG", "C�d.bar.inic.", ListaCampos.DB_SI, true );
		setListaCampos( true, "MODGRADE", "EQ" );
		setAltDet( 120 );
		pinDet = new JPanelPad( 590, 110 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		lcVarG.add( new GuardaCampo( txtCodVarG, "CodVarG", "C�d.var.g.", ListaCampos.DB_PK, true ) );
		lcVarG.add( new GuardaCampo( txtDescVarG, "DescVarG", "Descri��o da variante", ListaCampos.DB_SI, false ) );
		lcVarG.montaSql( false, "VARGRADE", "EQ" );
		lcVarG.setQueryCommit( false );
		lcVarG.setReadOnly( true );
		txtCodVarG.setTabelaExterna( lcVarG, null );

		adicCampo( txtCodItModG, 7, 20, 70, 20, "CodItModG", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtOrdemItModG, 80, 20, 70, 20, "OrdemItModG", "Ordem", ListaCampos.DB_SI, true );
		adicCampo( txtCodVarG, 153, 20, 77, 20, "CodVarG", "C�d.var.g.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescVarG, 233, 20, 150, 20, "DescVarG", "Descri��o da variante" );
		adicCampo( txtDescItModG, 386, 20, 150, 20, "DescItModG", "Descri��o", ListaCampos.DB_SI, true );
		adicCampo( txtRefItModG, 7, 60, 87, 20, "RefItModG", "Ref.inicial", ListaCampos.DB_SI, true );
		adicCampo( txtCodFabItModG, 100, 60, 87, 20, "CodFabItModG", "C�d.fab.inic.", ListaCampos.DB_SI, true );
		adicCampo( txtCodBarItModG, 190, 60, 100, 20, "CodBarItModG", "C�d.bar.inic.", ListaCampos.DB_SI, true );
		adicCampo( txtDescCompItModG, 293, 60, 267, 20, "DescCompItModG", "Descri��o completa do item", ListaCampos.DB_SI, false );
		lcDet.setOrdem( "OrdemItModG" );
		setListaCampos( true, "ITMODGRADE", "EQ" );
		
		montaTab();
		
		lcDet.addPostListener( this );
		lcDet.addCarregaListener( this );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcProd.setConexao( cn );
		lcVarG.setConexao( cn );
		txtCodProd.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProd.getWhereAdic() ) );
	}
	
	@ Override
	public void afterPost( PostEvent pevt ) {
		if(pevt.getListaCampos() == lcDet ) {
			if(ordem != txtOrdemItModG.getVlrInteger()) {
				lcCampos.carregaDados();
			}
		}
	
		super.afterPost( pevt );
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub
		
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if(cevt.getListaCampos() == lcDet ) {
			ordem = txtOrdemItModG.getVlrInteger();
		}
	}
}
