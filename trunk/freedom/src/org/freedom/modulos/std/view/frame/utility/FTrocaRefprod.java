/**
 * @version 23/10/2013 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./Robson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.utility <BR>
 *         Classe: @(#)FTrocaRefprod.java <BR>
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
 *         Classe respons�vel pela substitui��o de refer�ncia dos produtos.
 */

package org.freedom.modulos.std.view.frame.utility;

import java.sql.SQLException;
import java.util.Date;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.dao.DAOTrocaRefprod;

public class FTrocaRefprod extends FDetalhe implements InsertListener, PostListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtId = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtMotivo = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtDtTroca = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtSituacao = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodprod = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRefprodold = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtRefprodnew = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDescprod = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSituacaoIt = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtId_troca = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtId_it = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private DAOTrocaRefprod daotrocarefprod = null;

	public FTrocaRefprod() {

		setTitulo( "Troca refer�ncia dos produtos" );
		setAtribos( 10, 10, 600, 400 );

		setAltCab( 90 );
		pinCab = new JPanelPad( 420, 90 );

		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );

		lcProd.add( new GuardaCampo( txtCodprod, "codprod", "C�d.prod.", ListaCampos.DB_PK, true ) );
		lcProd.add( new GuardaCampo( txtRefprodold, "refprod", "Refer�ncia", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescprod, "descprod", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodprod.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );
		
		txtSituacao.setSoLeitura( true );
		txtSituacaoIt.setSoLeitura( true );

		adicCampo( txtId, 7, 20, 70, 20, "id", "ID.", ListaCampos.DB_PK, true );
		adicCampo( txtMotivo, 80, 20, 330, 20, "motivo", "Motivo", ListaCampos.DB_SI, true );
		adicCampo( txtDtTroca, 413, 20, 80, 20, "dttroca", "Data troca", ListaCampos.DB_SI, true);
		adicCampo( txtSituacao, 496, 20, 80, 20, "situacao", "Situa��o", ListaCampos.DB_SI, false);
		setListaCampos( false, "TROCAREFPROD", "EQ" );

		setAltDet( 100 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtId_it, 7, 20, 40, 20, "id_it", "ID.", ListaCampos.DB_PK, true );
		adicCampo( txtCodprod, 50, 20, 70, 20, "Codprod", "C�d.prod.", ListaCampos.DB_FK, txtDescprod, true );
		adicDescFK( txtDescprod, 123, 20, 330, 20, "Descprod", "Descri��o do produto" );
		adicCampo( txtRefprodold, 7, 60, 150, 20, "refprodold", "Refer�ncia atual", ListaCampos.DB_SI, true);
		adicCampo( txtRefprodnew, 160, 60, 150, 20, "refprodnew", "Refer�ncia nova", ListaCampos.DB_SI, true);
		adicCampo( txtSituacaoIt, 313, 60, 80, 20, "situacao", "Situa��o", ListaCampos.DB_SI, false);
		
		setListaCampos( false, "ITTROCAREFPROD", "EQ" );

		montaTab();
		int col = 0;
		tab.setTamColuna( 40, col++ );
		tab.setTamColuna( 45, col++ );
		tab.setTamColuna( 350, col++ );
		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );
		txtId.setEditable( false );
		txtId_it.setAtivo( false );
		txtRefprodold.setAtivo( false );
		lcCampos.addCarregaListener( this );
	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcProd.setConexao( cn );
		daotrocarefprod = new DAOTrocaRefprod( cn, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQTROCAREFPROD" ) );
	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void afterInsert( InsertEvent ievt ) {

		try {
			if ( ievt.getListaCampos() == lcCampos ) {
				txtId.setVlrInteger( lcCampos.gerarSeqId() );
				txtSituacao.setVlrDate( new Date() );
			} else if (ievt.getListaCampos() == lcDet) {
				txtId_troca.setVlrInteger( txtId.getVlrInteger() );
				txtId_it.setVlrInteger( lcDet.gerarSeqId() );
			}
		} catch ( SQLException e ) {
			try {
				con.rollback();
			} catch ( SQLException e1 ) {
				e1.printStackTrace();
			}
			Funcoes.mensagemErro( this, "Erro buscando sequencia para ID.\n" + e.getMessage() );
		}

	}

	public void beforePost(PostEvent pevt) {
		super.beforePost( pevt );
		if (pevt.getListaCampos()==lcCampos) {
			try {
				StringBuffer seek = daotrocarefprod.seekRefprod( txtRefprodnew.getVlrString() );
				if (seek.length()>0) {
					pevt.cancela();
					Funcoes.mensagemInforma( this, seek.toString() );
					return;
				}
			} catch (Exception e) {
				pevt.cancela();
				Funcoes.mensagemErro( this, "Erro pesquisando refer�ncia !\n"+e.getMessage() );
			}
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {
		if (cevt.getListaCampos()==lcCampos) {
			if ( ! txtId_troca.getVlrString().equals( txtId.getVlrString() )) {
				txtId_troca.setVlrInteger( txtId.getVlrInteger() );
				lcDet.carregaDados();
			}
		}
	}
}
