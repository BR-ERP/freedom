/**
 * @version 21/08/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLTipoOrcamento.java <BR>
 * 
 *                 Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                 modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                 na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                 Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                 sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                 Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                 Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                 de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Dialog para sele��o dos tipos de produtos deve ser gerados em or�amento de ordem de sevi�o.
 */

package org.freedom.modulos.gms.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

public class DLTipoProdServOrc extends FFDialogo {

	private static final long serialVersionUID = 1L;

	public static final short COMPONENTES = 0;
	
	public static final short SERVICOS = 1;
	
	public static final short NOVOS = 2;
	
	private JCheckBoxPad cbComponentes = new JCheckBoxPad( "Componentes?", "S", "N" );
	
	private JCheckBoxPad cbServicos = new JCheckBoxPad( "Servi�os?", "S", "N" );
	
	private JCheckBoxPad cbNovos = new JCheckBoxPad( "Produtos novos?", "S", "N" );
	
	private JLabelPad lbTitulo = new JLabelPad( "Selecione os tipos de produto/servi�o que devem ser or�ados." );

	public DLTipoProdServOrc( Component orig ) {

		super( orig );

		setConexao( Aplicativo.getInstace().con );
	
		setTitulo( "Tipo de produto/servi�o" );
		setAtribos( 380, 270 );

		adic( lbTitulo, 7, 0, 353, 60 );
		
		adic( cbComponentes, 7, 60, 200, 20 );
		adic( cbServicos, 7, 100, 200, 20 );
		adic( cbNovos, 7, 140, 200, 20 );

		cbComponentes.setVlrString( "S" );
		cbServicos.setVlrString( "S" );
		
	}

	public String getComponentes() {
		return cbComponentes.getVlrString();
	}
	
	public String getServicos() {
		return cbServicos.getVlrString();
	}

	public String getNovos() {
		return cbNovos.getVlrString();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			super.actionPerformed( evt );
		}
		else if ( evt.getSource() == btCancel ) {
			super.actionPerformed( evt );
		}
	}
}
