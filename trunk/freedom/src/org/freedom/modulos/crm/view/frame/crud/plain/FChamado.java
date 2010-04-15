/**
 * @version 16/50/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FHistPad.java <BR>
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
 * Tela para cadastro de historicos padr�o.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FChamado extends FDados implements ActionListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescChamado = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private final JTextAreaPad txaDetChamado = new JTextAreaPad( 1000 );
	
	private final JTextAreaPad txaObsChamado = new JTextAreaPad( 1000 );
	
	private final JScrollPane spnDetChamado = new JScrollPane( txaDetChamado );
	
	private final JScrollPane spnObsChamado = new JScrollPane( txaObsChamado );
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
//	private final JPanelPad panelCampos = new JPanelPad();
	
	private JPanelPad panelCabecalho = new JPanelPad( 700, 140 );
	
	private final JPanelPad pinCab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad panelDetalhamento = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );
	
	private JPanelPad panelTxa = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );
	
	private JComboBoxPad cbTpChamado = null;	

	public FChamado() {

		super();
		setTitulo( "Cadastro de chamados" );
		setAtribos( 50, 50, 440, 380 );

		montaCombos();
		montaTela();
				
		cbTpChamado.addComboBoxListener( this );
		
	}
	
	private void montaTela() {

		pinCab.add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelCabecalho, BorderLayout.NORTH );
		
		setPainel( panelCabecalho );
				
		adicCampo( txtCodChamado, 7, 20, 70, 20, "CodChamado", "C�d.Cham.", ListaCampos.DB_PK, true );
		adicCampo( txtDescChamado, 80, 20, 330, 20, "DescChamado", "Descri��o do chamado", ListaCampos.DB_SI, true );
		adicDBLiv( txaDetChamado, "DetChamado", "Detalhamamento", false );		
		adicDBLiv( txaObsChamado, "ObsChamado", "Observa��es", false );
		
		setListaCampos( true, "CHAMADO", "CR" );
		
		adic( new JLabelPad( "Tipo de chamado" ), 7, 40, 223, 20 );
		adic( cbTpChamado, 7, 60, 223, 20 );

		panelGeral.add( panelDetalhamento, BorderLayout.CENTER );
		panelDetalhamento.add(spnDetChamado);
		panelDetalhamento.add(spnObsChamado);
		
		spnDetChamado.setBorder( BorderFactory.createTitledBorder( "Detalhamento" ) );
		spnObsChamado.setBorder( BorderFactory.createTitledBorder( "Observa��es" ) );
		
		this.add( pinCab );
		
	}
	
	private void montaCombos() {
		
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Portador" );
		vLabs.addElement( "Valor" );
		vLabs.addElement( "N�mero do documento" );
		vLabs.addElement( "Data" );
		vLabs.addElement( "Hist�rico digitado" );

		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "" );
		vVals.addElement( "<PORTADOR>" );
		vVals.addElement( "<VALOR>" );
		vVals.addElement( "<DOCUMENTO>" );
		vVals.addElement( "<DATA>" );
		vVals.addElement( "<HISTORICO>" );

		cbTpChamado = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 20, 0 );

	}

	public void valorAlterado( JComboBoxEvent evt ) {


		
	}

}
