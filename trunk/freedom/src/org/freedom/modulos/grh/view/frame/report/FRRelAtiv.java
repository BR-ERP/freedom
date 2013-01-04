/**
 * @version 21/06/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.grh <BR>
 *         Classe:
 * @(#)FreedomGRH.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Tela para filtro de relat�rio de atividades.
 * 
 */
package org.freedom.modulos.grh.view.frame.report;

import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import net.sf.jasperreports.engine.JasperPrintManager;

public class FRRelAtiv extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JCheckBoxPad cbEncaminhados = new JCheckBoxPad( "Encaminhados", new Boolean( true ), new Boolean( false ) );

	public FRRelAtiv() {

		setTitulo( "Relat�rio de atividades" );
		setAtribos( 160, 80, 290, 150 );

		txtDataini.setVlrDate( Funcoes.getDataIniMes( Funcoes.getMes( new Date() ), Funcoes.getAno( new Date() ) ) );
		txtDatafim.setVlrDate( Funcoes.getDataFimMes( Funcoes.getMes( new Date() ), Funcoes.getAno( new Date() ) ) );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 60, 15, 203, 2 );
		adic( new JLabelPad( "De:" ), 7, 30, 30, 20 );
		adic( txtDataini, 32, 30, 97, 20 );
		adic( new JLabelPad( "At�:" ), 135, 30, 30, 20 );
		adic( txtDatafim, 165, 30, 97, 20 );

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RHVAGACANDIDATO" ) );
		hParam.put( "DATAINI", txtDataini.getVlrDate() );
		hParam.put( "DATAFIM", txtDatafim.getVlrDate() );

		dlGr = new FPrinterJob( "relatorios/grhGerencVaga.jasper", "Resumo de atividades", "", this, hParam, con, null, false );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na gera��o do rel�torio!" + e.getMessage(), true, con, e );
			}
		}
	}

}
