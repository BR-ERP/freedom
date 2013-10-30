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

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.Historico;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FListHistorico;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.dao.DAOTrocaRefprod;
import org.freedom.modulos.std.dao.DAOTrocaRefprod.Change;
import org.freedom.modulos.std.dao.DAOTrocaRefprod.SIT_LOG_TROCARP;
import org.freedom.modulos.std.dao.DAOTrocaRefprod.Table;

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

	private JProgressBar pbAndamento = new JProgressBar();

	private JButtonPad btExecutar = new JButtonPad( Icone.novo( "btGerar.png" ) );

	JPanelPad pnAndamento = new JPanelPad();

	private DAOTrocaRefprod daotrocarefprod = null;

	public FTrocaRefprod() {

		setTitulo( "Troca refer�ncia dos produtos" );
		setAtribos( 10, 10, 600, 450 );

		setAltCab( 130 );
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
		adicCampo( txtDtTroca, 413, 20, 80, 20, "dttroca", "Data troca", ListaCampos.DB_SI, true );
		adicCampo( txtSituacao, 496, 20, 80, 20, "situacao", "Situa��o", ListaCampos.DB_SI, false );
		setListaCampos( false, "TROCAREFPROD", "EQ" );
		adic( btExecutar, 7, 50, 30, 30 );
		adic( pbAndamento, 43, 50, 300, 30 );

		setAltDet( 100 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtId_it, 7, 20, 40, 20, "id_it", "ID.", ListaCampos.DB_PK, true );
		adicCampo( txtCodprod, 50, 20, 70, 20, "Codprod", "C�d.prod.", ListaCampos.DB_FK, txtDescprod, true );
		adicDescFK( txtDescprod, 123, 20, 330, 20, "Descprod", "Descri��o do produto" );
		adicCampo( txtRefprodold, 7, 60, 150, 20, "refprodold", "Refer�ncia atual", ListaCampos.DB_SI, true );
		adicCampo( txtRefprodnew, 160, 60, 150, 20, "refprodnew", "Refer�ncia nova", ListaCampos.DB_SI, true );
		adicCampo( txtSituacaoIt, 313, 60, 80, 20, "situacao", "Situa��o", ListaCampos.DB_SI, false );

		setListaCampos( false, "ITTROCAREFPROD", "EQ" );

		montaTab();
		int col = 0;
		tab.setTamColuna( 60, col++ );
		tab.setTamColuna( 100, col++ );
		tab.setTamColuna( 350, col++ );
		lcCampos.addInsertListener( this );
		lcDet.addInsertListener( this );
		txtId.setEditable( false );
		txtId_it.setAtivo( false );
		txtRefprodold.setAtivo( false );
		lcCampos.addCarregaListener( this );
		lcDet.addPostListener( this );
		btExecutar.addActionListener( this );
		setImprimir( true );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcProd.setConexao( cn );
		daotrocarefprod = new DAOTrocaRefprod( cn, Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "EQTROCAREFPROD" ) );
	}

	public void beforeInsert( InsertEvent ievt ) {

		if ( ievt.getListaCampos() == lcDet ) {
			if ( !"PE".equals( txtSituacao.getVlrString() ) ) {
				ievt.cancela();
				Funcoes.mensagemInforma( this, "Este processo de troca j� foi conclu�do.\nN�o � poss�vel inserir novo item !" );
				return;
			}
		}
	}

	public void afterInsert( InsertEvent ievt ) {

		try {
			if ( ievt.getListaCampos() == lcCampos ) {
				txtId.setVlrInteger( lcCampos.gerarSeqId() );
				txtDtTroca.setVlrDate( new Date() );
			}
			else if ( ievt.getListaCampos() == lcDet ) {
				if ( "PE".equals( txtSituacao.getVlrString() ) ) {
					txtId_troca.setVlrInteger( txtId.getVlrInteger() );
					txtId_it.setVlrInteger( lcDet.gerarSeqId() );
				}
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

	public void beforePost( PostEvent pevt ) {

		super.beforePost( pevt );
		if ( pevt.getListaCampos() == lcDet ) {
			try {
				StringBuffer seek = daotrocarefprod.seekRefprod( txtRefprodnew.getVlrString() );
				if ( seek.length() > 0 ) {
					pevt.cancela();
					Funcoes.mensagemInforma( this, seek.toString() );
					return;
				}
			} catch ( Exception e ) {
				pevt.cancela();
				Funcoes.mensagemErro( this, "Erro pesquisando refer�ncia !\n" + e.getMessage() );
			}
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCampos ) {
			if ( !txtId_troca.getVlrString().equals( txtId.getVlrString() ) ) {
				txtId_troca.setVlrInteger( txtId.getVlrInteger() );
				lcDet.carregaDados();
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
		if ( evt.getSource() == btExecutar ) {
			execute();
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( TYPE_PRINT.PRINT );
		}
		else if ( evt.getSource() == btPrevimp ) {
			imprimir( TYPE_PRINT.VIEW );
		}
	}

	private void imprimir( TYPE_PRINT bVisualizar ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "EQTROCAREFPROD" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );

		StringBuilder filtros = new StringBuilder();
		filtros.append( "ID da troca: " );
		filtros.append( txtId.getVlrInteger() );

		try {
			ResultSet rs = daotrocarefprod.getResultSetRelatorio( txtId.getVlrInteger() );

			FPrinterJob dlGr = new FPrinterJob( "layout/rel/trocarefprod.jasper", "Relat�rio de troca de refer�ncias", filtros.toString(), rs, hParam, this );

			if ( bVisualizar == TYPE_PRINT.VIEW ) {
				dlGr.setVisible( true );
			}
			else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro na impress�o do relat�rio!\n" + err.getMessage(), true, con, err );
				}
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch ( SQLException err ) {
				err.printStackTrace();
			}
			Funcoes.mensagemErro( this, "Erro criando consultando banco de dados para emiss�o do relat�rio !\n" + e.getMessage() );

		}
	}

	private void execute() {

		if ( txtId.getVlrInteger().intValue() == 0 || txtId_it.getVlrInteger() == 0 ) {
			Funcoes.mensagemInforma( this, "Selecione a(s) refer�ncia(s) para execu��o da(s) troca(s) !" );
			return;
		}
		if ( Funcoes.mensagemConfirma( this, "Confirma execu��o da troca !" ) == JOptionPane.YES_NO_OPTION ) {
			try {
				final Vector<Table> tables = daotrocarefprod.selectTableChange();
				final Vector<Change> valuesChange = daotrocarefprod.selectValuesChange( txtId.getVlrInteger() );
				if ( tables.size() == 0 ) {
					Funcoes.mensagemInforma( this, "N�o foram encontradas tabelas para execu��o da troca !" );
					return;
				}
				Thread th = new Thread( new Runnable() {

					public void run() {

						try {
							executeChange( txtId.getVlrInteger(), tables, valuesChange );
						} catch ( Exception e ) {
							e.printStackTrace();
						}
					}
				} );
				th.start();
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro executando a troca !\n" + e.getMessage() );
			}
		}
	}

	private void executeChange( Integer id, Vector<Table> tables, Vector<Change> valuesChange ) throws Exception {

		Integer codfilialpf = ListaCampos.getMasterFilial( "SGPREFERE1" );
		boolean atualizaagenda = daotrocarefprod.getAtualizaAgenda( codfilialpf );
		try {
			if ( atualizaagenda && fPrim != null && fPrim.getThreadAgenda() != null ) {
		        try {
		        	fPrim.getThreadAgenda().interrupt();
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
			}

			Vector<Historico> listErr = new Vector<Historico>();
			pbAndamento.setMinimum( 0 );
			pbAndamento.setMaximum( tables.size() * valuesChange.size() );
			SIT_LOG_TROCARP situacao = SIT_LOG_TROCARP.OK;
			int i = 0;
			for ( Change value : valuesChange ) {
				for ( Table table : tables ) {
					table.setCodfilial( ListaCampos.getMasterFilial( table.getTable_name() ) );
					try {
						daotrocarefprod.executeChange( value, table );
						// throw new Exception( "Erro for�ado para testes!" );
					} catch ( Exception e ) {
						e.printStackTrace();
						StringBuilder mensagem = new StringBuilder();
						mensagem.append( "Erro executando troca de refer�ncia\n" );
						mensagem.append( "C�d.prod.: " );
						mensagem.append( value.getCodprod() );
						mensagem.append( " - Ref. antiga: " );
						mensagem.append( value.getRefprodold() );
						mensagem.append( " - Ref. nova: " );
						mensagem.append( value.getRefprodnew() );
						mensagem.append( "\n" );
						mensagem.append( e.getMessage() );
						Historico historico = new Historico();
						historico.setDataOperacao( new Date() );
						historico.setTipoOperacao( "ERRO" );
						historico.setHistorico( mensagem.toString() );
						historico.setId( value.getId_it() );
						listErr.addElement( historico );
						break;
					}
					i++;
					pbAndamento.setValue( i );
					pbAndamento.updateUI();
				}

			}
			if ( listErr.size() > 0 ) {
				situacao = SIT_LOG_TROCARP.ER;
				FListHistorico hist = new FListHistorico( listErr );
				String titulo = "Inconsist�ncias - troca de refer�ncias dos produtos";
				fPrim.temTela( titulo );
				fPrim.criatela( titulo, hist, con );
			}
			else {
				Funcoes.mensagemInforma( this, "Troca de refer�ncias executada com sucesso !" );
			}
			daotrocarefprod.updateSitucao( false, id, situacao );
			lcCampos.carregaDados();
		} finally {
			if ( atualizaagenda && fPrim != null ) {
				try {
					fPrim.criaThreadAtualiza();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
