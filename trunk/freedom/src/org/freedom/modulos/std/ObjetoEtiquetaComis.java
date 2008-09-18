/**
 * @version 16/09/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)ObjetoEtiquetaComis.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 */
package org.freedom.modulos.std;

import org.freedom.componentes.ObjetoEtiqueta;

public class ObjetoEtiquetaComis extends ObjetoEtiqueta {

  public ObjetoEtiquetaComis() { 	
	  
      adicOpcao("C�digo do vendedor","#CODIGO#","CODVEND",new Integer(8),null,"C�d.Vend.");
      adicOpcao("Nome do vendedor","#NOME#","NOMEVEND",new Integer(40),null,"Nome Vend.");      
      adicOpcao("Endere�o do Vendedor","#ENDERE�O#","ENDVEND",new Integer(50),null,"Endere�o");
      adicOpcao("N�mero","#NUMERO#","NUMVEND",new Integer(20),null,"Nro.");
      adicOpcao("Complemento","#COMPLCLI#","COMPLCLI",new Integer(50),null,"Complemento");
      adicOpcao("Bairro do vendedor","#BAIRRO#","BAIRVEND",new Integer(30),null,"Bairro");
      adicOpcao("Cidade do vendedor","#CIDADE#","CIDVEND",new Integer(30),null,"Cidade");
      adicOpcao("CEP","#CEP#","CEPVEND",new Integer(9),"#####-###","Cep");
      adicOpcao("UF do Vendedor","#UF#","UFVEND",new Integer(2),null,"UF");   
     
      setNometabela( "VDVENDEDOR" );
      
  }
}
