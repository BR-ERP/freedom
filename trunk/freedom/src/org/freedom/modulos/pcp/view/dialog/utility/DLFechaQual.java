/**
 * @version 17/07/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)DLFechaQual.java <BR>
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
 * Coment�rios sobre a classe...
 */
package org.freedom.modulos.pcp.view.dialog.utility;

import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

import java.util.HashMap;
import java.util.Vector;
import org.freedom.funcoes.Funcoes;

public class DLFechaQual extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDescAfer = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtVlrAfer = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrMin = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrMax = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );

	private Vector<String> vLabs1 = new Vector<String>();

	private Vector<String> vVals1 = new Vector<String>();

	private JRadioGroup<?, ?> rgTipo = null;

	String tipo = "";
		
	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtCasasDec = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 5, 0 );
	

	public DLFechaQual( String sDescAnalise, String sTipo, BigDecimal bVlrMin, BigDecimal bVlrMax, BigDecimal vlrAfer, String sAfer, String status, boolean editable, String codUnid, DbConnection con ) {

		setTitulo( "Qualidade" );
		setAtribos( 390, 220 );
		
		adic( new JLabelPad( "Descri��o da analise" ), 7, 5, 360, 20 );
		adic( txtDescEst, 7, 25, 360, 20 );
		
		txtVlrMin.setDecimal( Integer.valueOf( Funcoes.getCasasDecUnid( codUnid, con )));
		txtVlrMax.setDecimal( Integer.valueOf( Funcoes.getCasasDecUnid( codUnid, con )));
		txtVlrAfer.setDecimal( Integer.valueOf( Funcoes.getCasasDecUnid( codUnid, con )));
		
		tipo = sTipo;

		vLabs1.addElement( "Pendente" );
		vLabs1.addElement( "Recusada" );
		vLabs1.addElement( "Aprovada" );
		vLabs1.addElement( "Corrigida" );
		vVals1.addElement( "PE" );
		vVals1.addElement( "RC" );
		vVals1.addElement( "AP" );
		vVals1.addElement( "CO" );

		rgTipo = new JRadioGroup<String, String>( 1, 3, vLabs1, vVals1 );

		if ( "DT".equals( sTipo ) ) {

			adic( new JLabelPad( "Aferi��o" ), 7, 45, 360, 20 );
			adic( txtDescAfer, 7, 65, 360, 20 );
			adic( rgTipo, 7, 95, 360, 30 );
			rgTipo.setVlrString( status );

			txtDescAfer.setRequerido( true );
			txtDescAfer.setVlrString( sAfer );
		}
		else if ( "MM".equals( sTipo ) ) {

			adic( new JLabelPad( "Vlr.M�n." ), 7, 45, 70, 20 );
			adic( txtVlrMin, 7, 65, 70, 20 );
			adic( new JLabelPad( "Vlr.M�x." ), 80, 45, 70, 20 );
			adic( txtVlrMax, 80, 65, 70, 20 );
			adic( new JLabelPad( "Aferi��o" ), 7, 85, 210, 20 );
			adic( txtVlrAfer, 7, 105, 70, 20 );
			adic( txtCodUnid, 7, 155, 70, 20 );
			adic( txtCasasDec, 80, 155, 70, 20 );

			txtVlrMin.setSoLeitura( true );
			txtVlrMax.setSoLeitura( true );
			txtVlrAfer.setRequerido( true );

			txtVlrMin.setVlrBigDecimal( bVlrMin );
			txtVlrMax.setVlrBigDecimal( bVlrMax );
			txtVlrAfer.setVlrBigDecimal( vlrAfer == null ? new BigDecimal( 0 ) : vlrAfer );
		}

		txtDescEst.setVlrString( sDescAnalise );
		txtDescEst.setAtivo( false );
		
		if ( ! editable ) {
			rgTipo.setAtivo( false );
			txtVlrAfer.setAtivo( false );
			txtDescAfer.setAtivo( false );
		}

	}

	public String getStatus() {

		return rgTipo.getVlrString();
	}

	public HashMap<String, Object> getValor() {

		HashMap<String, Object> hRet = new HashMap<String, Object>();

		try {
			hRet.put( "DESCAFER", txtDescAfer.getVlrString() );
			hRet.put( "VLRAFER", txtVlrAfer.getVlrBigDecimal() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return hRet;
	}

	private int casasDec(){
		
		int ret =0;
		
		
		return ret;
		
	}
	public void ok() {

		if ( "DT".equals( tipo ) ) {
			if ( ( txtDescAfer.getVlrString().equals( "" ) ) ) {
				Funcoes.mensagemInforma( this, "Informe a descri��o!" );
				return;
			}
			else {
				super.ok();
			}
		}
		else if ( "MM".equals( tipo ) ) {
			if ( ( txtVlrAfer.getVlrString().equals( "" ) ) ) {
				Funcoes.mensagemInforma( this, "Informe o valor!" );
				return;
			}
			else {
				super.ok();
			}
		}
	}
}
