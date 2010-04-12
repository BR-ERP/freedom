/**
 * @version 16/03/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FTabJuros.java <BR>
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

package org.freedom.modulos.std.view.frame.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.funcoes.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JComboBoxPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;

public class FTabJuros extends FDetalhe implements CarregaListener, PostListener, ActionListener{

	private static final long serialVersionUID = 1L;
 
	private JPanelPad pinCab = new JPanelPad(0,90);
 
	private JPanelPad pinDet = new JPanelPad(0,60);
 
	private JTextFieldPad txtCodTbJ = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
 
	private JTextFieldPad txtDescTbJ = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  
	private JComboBoxPad cbTipo = null;
  
	private JTextFieldPad txtAno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  
	private JTextFieldPad txtMes = new JTextFieldPad(JTextFieldPad.TP_INTEGER,2,0);
 
	private JTextFieldPad txtPerc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,9,2);
	
	Vector<String> vLabs = new Vector<String>();
	
	Vector<String> vVals = new Vector<String>();
 
	public FTabJuros() {
    
		setTitulo("Tabelas de juros");
		setAtribos( 50, 20, 410, 410);
		setAltCab(140);
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		
		vLabs.addElement("<--Selecione-->");
		vLabs.addElement("Di�rio");
		vLabs.addElement("Mensal");
		vLabs.addElement("Bimestral");
		vLabs.addElement("Trimestral");
		vLabs.addElement("Semestral");
		vLabs.addElement("Anual");
		vLabs.addElement("Fixo");
		
		vVals.addElement("");
		vVals.addElement("D");
		vVals.addElement("M");
		vVals.addElement("B");
		vVals.addElement("T");
		vVals.addElement("S");
		vVals.addElement("A");
		vVals.addElement("F");
		cbTipo = new JComboBoxPad(vLabs,vVals,JComboBoxPad.TP_STRING, 1, 0);
    
		adicCampo(txtCodTbJ, 7, 20, 80, 20,"CodTbJ","C�d.tb.juros", ListaCampos.DB_PK, true);
		adicCampo(txtDescTbJ, 90, 20, 250, 20, "DescTbJ", "Descri��o da tabela", ListaCampos.DB_SI,true);
		adicDB(cbTipo, 7, 60, 150, 30, "TipoTbJ", "Tipo", true);
		setListaCampos( false, "TBJUROS", "FN");
		lcCampos.setQueryInsert(false);
		setPainel( pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);
		
		lcCampos.addPostListener( this );
		lcCampos.addCarregaListener( this );
		navRod.btNovo.addActionListener( this );
		
		adicCampo(txtAno, 7, 20, 80, 20,"AnoItTbJ","Ano", ListaCampos.DB_PK, true);
		adicCampo(txtMes, 90, 20, 57, 20,"MesItTbJ","Mes",ListaCampos.DB_PK,true);
		adicCampo(txtPerc, 150, 20, 90, 20,"PercItTbJ","Perc.", ListaCampos.DB_SI, true);
		setListaCampos( false, "ITTBJUROS", "FN");
		lcDet.setQueryInsert(false);
		montaTab();
	}
	

	@ Override
	public void afterPost( PostEvent pevt ) {

		int ano = new Integer(((new GregorianCalendar().get( Calendar.YEAR ))));
		int mes = new Integer(((new GregorianCalendar().get( Calendar.MONTH ))+1));
		
		if( pevt.getListaCampos() == lcCampos ){

			if ( "F".equals( cbTipo.getVlrString() ) ) {
					
				txtAno.setAtivo( false );
				txtMes.setAtivo( false );
				txtAno.setVlrInteger( ano );
				txtMes.setVlrInteger( mes );
				txtPerc.requestFocus();
			}
			
		}
		super.afterPost(pevt);
	}
	@ Override
	public void beforePost( PostEvent pevt ) {
	
		
		super.beforePost(pevt);
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if( cevt.getListaCampos() == lcCampos ){
			
			if( "F".equals( cbTipo.getVlrString() )){
				
				txtAno.setAtivo( false );
				txtMes.setAtivo( false );
				
				if( tab.getNumLinhas() == 1 ){
					navRod.btNovo.setEnabled( false );
				}
			}else if ( !"F".equals( cbTipo.getVlrString() )){
				
				txtAno.setAtivo( true );
				txtMes.setAtivo( true );
				navRod.btNovo.setEnabled( true );
			}
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}
	@ Override
	public void actionPerformed( ActionEvent evt ) {

		if( evt.getSource() == navRod.btNovo ){
			
			if ( "F".equals( cbTipo.getVlrString() )) {
				
				if( tab.getNumLinhas() == 1 ){
					Funcoes.mensagemInforma( this, "Est� opera��o n�o pode ser executada!" );
				}
			}
		}
		super.actionPerformed(evt);
	}
}
