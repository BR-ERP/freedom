/**
 * @version 01/08/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FTipoAnalise.java <BR>
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
 * Tela para cadastro de tipos de clientes.
 * 
 */
package org.freedom.modulos.pcp;

import org.freedom.infra.model.jdbc.DbConnection;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;


public class FTipoAnalise extends FDados {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodTpAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtDescTpAnalise = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextAreaPad txaObsTpAnalise = new JTextAreaPad();
	
	private JTextFieldPad txtCodUnid = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private JTextFieldFK txtDescUnid = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldPad txtCodMetodo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescMetodo = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	
	private JTextFieldPad txtTituloMet = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
		
	private Vector<String> vLabs1 = new Vector<String>();
	
	private Vector<String> vVals1 = new Vector<String>();
	
	private JRadioGroup<?, ?> rgTipo = null;

	ListaCampos lcUnid = new ListaCampos( this, "UD" );
	
	ListaCampos lcMetodo = new ListaCampos( this, "MA" );

	public FTipoAnalise(){
	
		setTitulo( "Tipos de Analise" );
		setAtribos( 50, 50, 435, 330 );
		montaListaCampos();
		montaTela();
		
	} 
	
	private void montaTela(){
		
		vLabs1.addElement("M�nimo e M�ximo");
 		vLabs1.addElement("Descritivo"); 
 		vVals1.addElement("MM");
 		vVals1.addElement("DT");
		    
 		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
 		rgTipo.setVlrString("MM");
 		
 		adicCampo( txtCodTpAnalise, 7, 20, 100, 20 , "CodTpAnalise", "C�d.Tp.An.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTpAnalise, 110, 20, 300, 20, "DescTpAnalise", "Descri��o do tipo de analise", ListaCampos.DB_SI, true );
		adicCampo( txtCodUnid, 7, 65, 100, 20, "CodUnid", "C�d.Unid", ListaCampos.DB_FK, txtDescUnid, false );
		adicDescFK( txtDescUnid, 110, 65, 300, 20, "DescUnid", "Descri��o da Unidade" );
		adicCampo( txtCodMetodo, 7, 105, 100, 20, "CodMtAnalise", "C�d.M�todo", ListaCampos.DB_FK, txtDescMetodo, true );
		adicDescFK( txtDescMetodo, 110, 105, 300, 20, "DescMtAnalise", "Descri��o do m�todo anal�tico" );
		adicDB( txaObsTpAnalise, 7, 200, 402, 50, "ObsTpAnalise", "Observa��es", false );
		adicDB( rgTipo, 7, 150, 402, 30, "TipoExpec", "Tipo de expecifica��o", true );
		setListaCampos( true, "TIPOANALISE", "PP" );
		
	}
	
	private void montaListaCampos(){
		
		/*****************
		 *    Unidade    *
		 *****************/
		
		lcUnid.add( new GuardaCampo( txtCodUnid, "CodUnid", "C�d.Unidade", ListaCampos.DB_PK, null, false ) );
		lcUnid.add( new GuardaCampo( txtDescUnid, "DescUnid", "Descri��o da unidade", ListaCampos.DB_SI, false ) );
		lcUnid.montaSql( false, "UNIDADE", "EQ" );
		lcUnid.setReadOnly( true );
		lcUnid.setQueryCommit( false );
		txtCodUnid.setListaCampos( lcUnid );
		txtCodUnid.setTabelaExterna( lcUnid );
		
		/*****************
		 *    M�todo     *
		 *****************/
		
		lcMetodo.add( new GuardaCampo( txtCodMetodo, "CodMtAnalise", "C�d.M�todo", ListaCampos.DB_PK, null, false ) );
		lcMetodo.add( new GuardaCampo( txtDescMetodo, "DescMtAnalise", "Descri��o do m�todo anal�tico", ListaCampos.DB_SI, false ) );
		lcMetodo.add( new GuardaCampo( txtTituloMet, "TituloAnalise", "Titulo", ListaCampos.DB_SI, false ) );
		lcMetodo.montaSql( false, "METODOANALISE", "PP" );
		lcMetodo.setReadOnly( true );
		lcMetodo.setQueryCommit( false );
		txtCodMetodo.setListaCampos( lcMetodo );
		txtCodMetodo.setTabelaExterna( lcMetodo );
	}
	public void setConexao( DbConnection cn ){
		
		super.setConexao( cn );
		lcUnid.setConexao( cn );
		lcMetodo.setConexao( cn );
	}
}
