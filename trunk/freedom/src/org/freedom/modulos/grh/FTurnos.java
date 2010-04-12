/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FTurnos.java <BR>
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
 * Tela de cadastro de turnos
 * 
 */

package org.freedom.modulos.grh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.JRadioGroup;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;

public class FTurnos extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodTurno = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtDescTurno = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	private final JTextFieldPad txtNhsTurno = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private final JTextFieldPad txtHIniTurno = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	private final JTextFieldPad txtHIniIntTurno = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	private final JTextFieldPad txtHFimIntTurno = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	private final JTextFieldPad txtHFimTurno = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	private final Vector<String> vTipoTurnoLab = new Vector<String>();

	private final Vector<String> vTipoTurnoVal = new Vector<String>();

	private JRadioGroup<String, String> rgTipoTurno = null;
	

	public FTurnos() {

		super();
		setTitulo( "Cadastro de Turnos" );
		setAtribos( 50, 50, 440, 250 );

		vTipoTurnoLab.addElement( "Normal ( manh� e tarde )" );
		vTipoTurnoLab.addElement( "Manh�" );
		vTipoTurnoLab.addElement( "Tarde" );
		vTipoTurnoLab.addElement( "Noite" );
		vTipoTurnoLab.addElement( "Especial" );
		vTipoTurnoVal.addElement( "N" );
		vTipoTurnoVal.addElement( "M" );
		vTipoTurnoVal.addElement( "T" );
		vTipoTurnoVal.addElement( "O" );
		vTipoTurnoVal.addElement( "E" );
		rgTipoTurno = new JRadioGroup<String, String>( 3, 2, vTipoTurnoLab, vTipoTurnoVal );
		rgTipoTurno.setVlrString( "N" );
		
		montaTela();
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		setImprimir( true );
	}
	
	private void montaTela() {

		adicCampo( txtCodTurno, 7, 20, 80, 20, "CodTurno", "C�d.turno", ListaCampos.DB_PK, true );
		adicCampo( txtDescTurno, 90, 20, 223, 20, "DescTurno", "Descri��o do turno", ListaCampos.DB_SI, true );
		adicCampo( txtNhsTurno, 316, 20, 100, 20, "NhsTurno", "N� H.semanais", ListaCampos.DB_SI, true );
		adicCampo( txtHIniTurno, 7, 60, 100, 20, "HIniTurno", "Inic�o do turno", ListaCampos.DB_SI, true );
		adicCampo( txtHIniIntTurno, 110, 60, 100, 20, "HIniIntTurno", "Inic�o do intervalo", ListaCampos.DB_SI, true );
		adicCampo( txtHFimIntTurno, 213, 60, 100, 20, "HFimIntTurno", "Fim do intervalo", ListaCampos.DB_SI, true );
		adicCampo( txtHFimTurno, 316, 60, 100, 20, "HFimTurno", "fim do turno", ListaCampos.DB_SI, true );
		adicDB( rgTipoTurno, 7, 100, 409, 70, "TipoTurno", "Tipo de Turno:", true );
		
		setListaCampos( true, "TURNO", "RH" );
		lcCampos.setQueryInsert( false );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {
		
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RHDEPTO" ) );

		dlGr = new FPrinterJob( "relatorios/grhTurnos.jasper", "Lista de Turnos", "", this, hParam, con, null, false );

		if ( bVisualizar ) {
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
