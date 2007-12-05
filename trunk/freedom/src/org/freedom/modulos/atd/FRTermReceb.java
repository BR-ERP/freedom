/**
 * @version 05/12/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Reginaldo Garcia Heua<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FRTermReceb.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */
package org.freedom.modulos.atd;

import java.sql.Connection;


import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FRelatorio;


public class FRTermReceb extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private ListaCampos lcOrc = new ListaCampos(this, "");
	
	private JTextFieldPad txtCodOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDtOrc = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtVencOrc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodItOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	
	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtCodBarras = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	public FRTermReceb(){
		
		setTitulo( "Termo de Recebimento" );
		setAtribos( 30, 20, 400, 300 );
		
		montaTela();
		montaListaCampos();
		
	}
	
	private void montaTela(){
		
		adic( new JLabelPad("Num.Orc"), 7, 5, 70, 20 );
		adic( txtCodOrc, 7, 25, 70, 20 );
		adic( new JLabelPad("Dt.emiss�o"), 80, 5, 90, 20 );
		adic( txtDtOrc, 80, 25, 90, 20 );
		//adic( new JLabelPad("Item or�."), 173, 5, 70, 20 );
		//adic(txtCodItOrc, 173, 25, 70, 20 );
		//adic( new JLabelPad("Cod.Prod"), 7, 45, 70, 20 );
		//adic( txtCodProd, 7, 65, 70, 20 );
		//adic( new JLabelPad("Descri��o do produto"), 80, 45, 290, 20 );
		//adic(txtDescProd, 80, 65, 290, 20 );
		
	}
	
	private void montaListaCampos(){
		
		lcOrc.add( new GuardaCampo( txtCodOrc, "CodOrc", "C�d.Or�.", ListaCampos.DB_PK, false ) );
		lcOrc.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodConv, "CodConv", "C�d.conv.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtOrc, "DtOrc", "Dt.emiss�o", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtVencOrc, "DtVencOrc", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.p.pag.", ListaCampos.DB_SI, false ) );
		lcOrc.montaSql( false, "ORCAMENTO", "VD" );
		lcOrc.setQueryCommit( false );
		lcOrc.setReadOnly( true );
		txtCodOrc.setTabelaExterna(lcOrc);
		txtCodProd.setAtivo( false );
		
	}
	@ Override
	public void imprimir( boolean b ) {

		
	}
	public void setConexao(Connection cn) {
		
		super.setConexao(cn);
		lcOrc.setConexao( cn );
	}

}
