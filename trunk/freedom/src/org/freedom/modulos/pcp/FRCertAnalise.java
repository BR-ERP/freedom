/**
 * @version 18/07/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * @author Reginaldo Garcia Heua <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRCertAnalise.java <BR>
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
 */
package org.freedom.modulos.pcp;

import java.sql.Connection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRCertAnalise extends FRelatorio{
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	 
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	 
	private JTextFieldPad txtCodFabProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	 
	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
			
	private JTextFieldFK txtRefProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldPad txtCodLote = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	
	private JTextFieldFK txtDtIniLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtDtVencLote = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtDtEmitPed = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodPed = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtCodCli = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtDocVenda = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 10, 0 );
	
    private ListaCampos lcProd = new ListaCampos( this, ""  );
    
    private ListaCampos lcLote = new ListaCampos( this, "" );
    
    private ListaCampos lcPedido = new ListaCampos( this, "" );
    
    private ListaCampos lcCliente = new ListaCampos( this, "" );
	  
	public FRCertAnalise(){
		
		super( false );
		setAtribos( 50, 50, 400, 250 );
		setTitulo( "Certificado de An�lise" );
		
		montaListaCampos();
		montaTela();
	}
	
	private void montaTela(){
		
		adic( new JLabelPad("C�d.Prod"), 7, 10, 70, 20 );
		adic( txtCodProd, 7, 30, 70, 20 );
		adic( new JLabelPad("Descri��o do produto"), 80, 10, 250, 20 );
		adic( txtDescProd, 80, 30, 280, 20 );
		adic( new JLabelPad("Pedido"), 7, 55, 70, 20 );
		adic( txtCodPed, 7, 75, 70, 20 );
		adic( new JLabelPad("C�d.Cli"), 80, 55, 70, 20 );
		adic( txtCodCli, 80, 75, 70, 20 );
		adic( new JLabelPad("Raz�o social do cliente"), 153, 55, 200, 20 );
		adic( txtRazCli, 153, 75, 205, 20 );	
		adic( new JLabelPad("Dt.Emit.Ped."), 7, 95, 80, 20 );
		adic( txtDtEmitPed, 7, 115, 70, 20 );
		adic( new JLabelPad("C�d.lote"),85, 95, 80, 20 );
		adic( txtCodLote, 85, 115, 100, 20 );
		adic( new JLabelPad("Dt.Lote"), 188, 95, 90, 20 );
		adic( txtDtIniLote, 188, 115, 80, 20 );
		adic( new JLabelPad("Dt.Venc.Lt"), 250, 95, 80, 20 );
		adic( txtDtVencLote, 273, 115, 85, 20 );
		

	}
	
	private void montaListaCampos(){
		
		/**************
		 *  Produto   * 
		 **************/
		
	     lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, false ));
         lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false ));
         lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ));
         lcProd.add( new GuardaCampo( txtCodFabProd, "codfabprod", "C�d.fab.prod.",ListaCampos.DB_SI, false ));
         txtCodProd.setTabelaExterna( lcProd );
         txtCodProd.setNomeCampo( "CodProd" );
         txtCodProd.setFK(true);
         lcProd.setReadOnly(true);
         lcProd.montaSql( false, "PRODUTO", "EQ" );
         
         /**************
 		 *    Lote     * 
 		 **************/
         
         lcLote.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.Prod", ListaCampos.DB_PF, false ));
         lcLote.add( new GuardaCampo( txtCodLote, "CodLote", "C�d.lote", ListaCampos.DB_PK, false ));
         lcLote.add( new GuardaCampo( txtDtVencLote, "VenctoLote", "Vencimento do lote", ListaCampos.DB_SI, false ));
         lcLote.add( new GuardaCampo( txtDtIniLote, "DIniLote", "Data do lote", ListaCampos.DB_SI, false ));
         txtCodLote.setTabelaExterna(lcLote);
         txtCodLote.setNomeCampo("CodLote");
         txtCodLote.setFK(true);
         lcLote.setReadOnly(true);
         lcLote.setDinWhereAdic("CODPROD = #N",txtCodProd);
         lcLote.montaSql(false, "LOTE", "EQ");
         
         /**************
  		 *   Pedido    * 
  		 **************/
         
         lcPedido.add( new GuardaCampo( txtCodPed, "CodVenda", "C�d.ped.", ListaCampos.DB_PK, false ));
         lcPedido.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.Cli", ListaCampos.DB_FK, false ));
         lcPedido.add( new GuardaCampo( txtDtEmitPed, "DtEmitVenda", "Dt.Emiss�o", ListaCampos.DB_FK, false ));
         lcPedido.add( new GuardaCampo( txtDocVenda, "DocVenda", "Doc.Venda", ListaCampos.DB_SI, false ));
         txtCodPed.setTabelaExterna( lcPedido );
         txtCodPed.setNomeCampo( "CodVenda" );
         txtCodPed.setFK(true);
         lcPedido.setReadOnly(true);
         lcPedido.montaSql( false, "VENDA", "VD" );
         
         /**************
   		 *   Cliente   * 
   		 **************/
          
          lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
          lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_FK, false ) );
          txtCodCli.setTabelaExterna( lcCliente );
          txtCodCli.setNomeCampo( "CodCli" );
          txtCodCli.setFK(true);
          lcCliente.setReadOnly(true);
          lcCliente.montaSql( false, "CLIENTE", "VD" );
         
         
	}

	public void imprimir( boolean b ) {
		
		imprimiGrafico( b, "" );
	}
	
	private void imprimiGrafico(  final boolean bVisualizar,  final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );		
		hParam.put( "CODLOTE", txtCodLote.getVlrString() );
		hParam.put( "DESCPROD", txtDescProd.getVlrString() );
		hParam.put( "FABRICACAO", txtDtIniLote.getVlrDate() );
		hParam.put( "VALIDADE", txtDtIniLote.getVlrDate() );
		hParam.put( "NF", txtCodPed.getVlrInteger() );
		hParam.put( "EMITNF", txtDtEmitPed.getVlrDate() );
		hParam.put( "CODCLI", txtCodCli.getVlrInteger() );
		hParam.put( "RAZCLI", txtRazCli.getVlrString() );
		hParam.put( "CODPROD", txtCodProd.getVlrInteger().toString() );
		hParam.put( "DOCVENDA", txtDocVenda.getVlrInteger() );
		

		dlGr = new FPrinterJob("relatorios/FRCertAnalise.jasper", "Certificado de An�lise", "", this, hParam, con);
		
		if ( bVisualizar ) {
			
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio Certifica��o de an�lise!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	public void setConexao( Connection cn ){
		
		super.setConexao( cn );
		lcProd.setConexao( cn );
		lcLote.setConexao( cn );
		lcPedido.setConexao( cn );
		lcCliente.setConexao( cn );
	}
}
