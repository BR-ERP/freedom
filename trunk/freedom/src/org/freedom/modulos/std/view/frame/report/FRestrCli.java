/**
 * @version 23/05/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRegraFiscal.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;

import java.util.Date;

import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;

public class FRestrCli extends FDetalhe implements CarregaListener, InsertListener  {
	
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();
	
	private JPanelPad pinDet = new JPanelPad();
	
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
	
	private JTextFieldPad txtRazCli = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	
	private JTextFieldPad txtSeqRestr = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldPad txtCodTipoRestr = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldFK txtDescTipoRestr = new JTextFieldFK(JTextFieldFK.TP_STRING,50,0);
	
	private JTextFieldPad txtSitRestr = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
	
	private JTextFieldPad txtDataRestr = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	
	private JTextFieldPad txtDataCancRestr = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	
	private JTextFieldPad txtLegenda = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	
	private JTextAreaPad txaObs= new JTextAreaPad(512);
	
	private JScrollPane spnObs = new JScrollPane(txaObs);
	
	private ListaCampos lcTipoRestr = new ListaCampos(this, "TR");
	
	
	public FRestrCli () {
		
		setTitulo("Restri��o de clientes");
		setAtribos( 50, 50, 600, 450);
		pinCab = new JPanelPad(440,70);
		txtRazCli.setSoLeitura( true );
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		
		navRod.setAtivo( 5, false );
		

		adicCampo( txtCodCli, 7, 20, 100, 20,"Codcli","C�d.cli", ListaCampos.DB_PF,true);
		adicCampo( txtRazCli, 110, 20, 300, 20,"Razcli","Raz�o social", ListaCampos.DB_SI, false);  
		setListaCampos( true, "CLIENTE", "VD" );
		lcCampos.setQueryCommit(false);
		lcCampos.setReadOnly(true);
		
		
		lcTipoRestr.add(new GuardaCampo( txtCodTipoRestr, "CodTpRestr", "C�d.tp.restr.",  ListaCampos.DB_PK, false));
		lcTipoRestr.add(new GuardaCampo( txtDescTipoRestr, "DescTpRestr", "Descri�ao da restri��o",  ListaCampos.DB_SI, false));
		lcTipoRestr.montaSql(false, "TIPORESTR", "FN");    
		lcTipoRestr.setQueryCommit(false);
		lcTipoRestr.setReadOnly(true);
		txtCodTipoRestr.setTabelaExterna(lcTipoRestr);
		
		setAltDet( 150 );
		pinDet = new JPanelPad( 600, 90 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		txtLegenda.setSoLeitura( true );
		txtSitRestr.setSoLeitura( true );
		adicCampo( txtSeqRestr, 7, 20, 30, 20, "SeqRestr", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodTipoRestr, 40, 20, 80, 20, "CodTpRestr", "C�d.tp.restr.", ListaCampos.DB_FK, txtDescTipoRestr, true );
		adicDescFK( txtDescTipoRestr, 123, 20, 200, 20, "DescTpRestr", "Descri�ao da restri��o" );
		adic( new JLabelPad ("Situa��o"), 326, 0, 200, 20 );
		adic( txtLegenda, 326, 20, 200, 20 );
		adicCampo( txtSitRestr, 529, 20, 30, 20, "SitRestr", "", ListaCampos.DB_SI, false );
		adicCampo( txtDataRestr, 7, 60, 100, 20, "DtRestr", "Data cadastro", ListaCampos.DB_SI, true );
		adicCampo( txtDataCancRestr, 7, 100, 100, 20, "DtCancRestr", "Data canc.", ListaCampos.DB_SI, false );
		adicDBLiv( txaObs, 113, 60, 450, 60,"ObsRestr","Observa��o",false);
		adic( new JLabelPad ("Observa��o"), 113, 40, 200, 20 );
		setListaCampos( true, "RESTRICAO", "FN" );
		lcDet.setQueryInsert( true );
		
		montaTab();
		tab.setTamColuna( 200, 2 );
		tab.setTamColuna( 130, 4 );
		tab.setTamColuna( 130, 5 );
		tab.setTamColuna( 300, 6 );
		
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcTipoRestr.addCarregaListener( this );
		lcDet.addCarregaListener( this );
		lcDet.addInsertListener( this );
		lcCampos.addCarregaListener( this );
	}
	
	public void setConexao(DbConnection con) {
		
		super.setConexao( con );
		lcTipoRestr.setConexao( con );
	
	}

	public void afterCarrega( CarregaEvent cevt ) {

		
		if( cevt.getListaCampos() == lcDet ){
			
			if( "I".equals( txtSitRestr.getVlrString())){
				txtLegenda.setVlrString( "Inclus�o" );
			}else if("C".equals( txtSitRestr.getVlrString())){
				txtLegenda.setVlrString( "Cancelamento" );
			}
			txtDataCancRestr.setEnabled( true );
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		
	}

	public void afterInsert( InsertEvent ievt ) {

		if( ievt.getListaCampos() == lcDet ){
			txtLegenda.setVlrString( "" );
			txtDataCancRestr.setEnabled( false );
			txtDataRestr.setVlrDate( new Date() );
		}
	}

	public void beforeInsert( InsertEvent ievt ) {

	
	}
}
