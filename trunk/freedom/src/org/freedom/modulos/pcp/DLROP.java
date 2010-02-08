/**
 * @version 30/05/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)DLRTipoAtend.java <BR>
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

package org.freedom.modulos.pcp;

import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class DLROP extends FFDialogo {
	
	private static final long serialVersionUID = 1L;
	
	private JRadioGroup<?, ?> rgOrdem = null;
	
	private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
	
	private Vector<String> vLabs = new Vector<String>();
	
	private Vector<String> vVals = new Vector<String>();
	
	private static String SORT_CODPROD = "CODPROD";
	
	private static String SORT_SEQOP = "SEQOP";
	
	public DLROP() { 
		
		setTitulo( "Ordem de impress�o da OP (Fase 1)" );
		setAtribos( 330, 145 );
		
		vLabs.addElement( "Sequencia dos �tens" );
		vLabs.addElement( "C�digo do produto" );
		
		vVals.addElement( SORT_SEQOP );
		vVals.addElement( SORT_CODPROD );		
		
		rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
		rgOrdem.setVlrString("D");
		
		adic(lbOrdem,7,0,80,15);
		
		adic(rgOrdem,7,20,300,30);
		
	}
	
	public String getValor() {
				
		return rgOrdem.getVlrString();		
		
	}
}