/**
 * @version 16/09/2008 <BR>
 * @author Setpoint Inform�tica Ltda./Reginaldo Garcia Heua <BR>
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

/**
 * @author anderson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjetoEtiquetaComis extends ObjetoEtiqueta {

  public ObjetoEtiquetaComis() { 	
	  
      adicOpcao("C�digo do comissionado","#CODIGO_COMIS#","CODCOMIS",new Integer(8),null,"C�d.Comis.");
      adicOpcao("Nome do comissionado","#NOME_COMIS#","NOMECOMIS",new Integer(50),null,"Nome comis.");      
      adicOpcao("CPF ou CNPJ do cliente","#CNPJ#","CNPJCLI",new Integer(18),"##.###.###/####-##","Cnpj");
      adicOpcao("Endere�o do cliente","#ENDERE�O#","ENDCLI",new Integer(50),null,"Endere�o");
      adicOpcao("Endere�o de cobran�a","#ENDCOB#","(COALESCE(ENDCOB,ENDCLI))",new Integer(50),null,"Endere�o");
      adicOpcao("Endere�o de entrega","#ENDENT#","(COALESCE(ENDENT,ENDCLI))",new Integer(50),null,"Endere�o ent.");
      adicOpcao("Complemento","#COMPLCLI#","COMPLCLI",new Integer(50),null,"Complemento");
      adicOpcao("Complemento end cob.","#COMPLCOB#","CASE WHEN ENDCOB IS NULL THEN COMPLCLI ELSE COMPLCOB END AS COMPLEMENTOC",new Integer(20),null,"Complemento cob.");
      adicOpcao("Complemento end ent.","#COMPLENT#","CASE WHEN ENDENT IS NULL THEN COMPLCLI ELSE COMPLENT END AS COMPLEMENTOE",new Integer(20),null,"Complemento cob.");
      adicOpcao("N�mero","#NUMERO#","NUMCLI",new Integer(20),null,"Nro.");
      adicOpcao("N�mero end. cob.","#NUMCOB#","CASE WHEN ENDCOB IS NULL THEN NUMCLI ELSE NUMCOB END AS NUMEROC",new Integer(20),null,"Nro.");
      adicOpcao("N�mero end. ent.","#NUMENT#","CASE WHEN ENDENT IS NULL THEN NUMCLI ELSE NUMENT END AS NUMEROE",new Integer(20),null,"Nro.");
      adicOpcao("CEP","#CEP#","CEPCLI",new Integer(9),"#####-###","Cep");
      adicOpcao("CEP cob.","#CEPCOB#","CASE WHEN ENDCOB IS NULL THEN CEPCLI ELSE CEPCOB END AS CEPC",new Integer(20),null,"CEP cob.");
      adicOpcao("CEP ent.","#CEPENT#","CASE WHEN ENDENT IS NULL THEN CEPCLI ELSE CEPENT END AS CEPE",new Integer(20),null,"CEP ent.");
      adicOpcao("Bairro do cliente","#BAIRRO#","BAIRCLI",new Integer(30),null,"Bairro");
      adicOpcao("Bairro end. cob.","#BAIRCOB#","CASE WHEN ENDCOB IS NULL THEN BAIRCLI ELSE BAIRCOB END AS BAIRC",new Integer(20),null,"Bairro cob.");
      adicOpcao("Bairro end. ent.","#BAIRENT#","CASE WHEN ENDENT IS NULL THEN BAIRCLI ELSE BAIRENT END AS BAIRE",new Integer(20),null,"Bairro ent.");
      adicOpcao("Cidade do cliente","#CIDADE#","CIDCLI",new Integer(30),null,"Cidade");
      adicOpcao("Cidade end. cob.","#CIDCOB#","CASE WHEN ENDCOB IS NULL THEN CIDCLI ELSE CIDCOB END AS CIDC",new Integer(20),null,"Cidade cob.");
      adicOpcao("Cidade end. ent.","#CIDENT#","CASE WHEN ENDENT IS NULL THEN CIDCLI ELSE CIDENT END AS CIDE",new Integer(20),null,"Cidade ent.");
      adicOpcao("UF do cliente","#UF#","UFCLI",new Integer(2),null,"UF");
      adicOpcao("UF end. cob.","#UFCOB#","CASE WHEN ENDCOB IS NULL THEN UFCLI ELSE UFCOB END AS UFC",new Integer(20),null,"UF cob.");
      adicOpcao("UF end. ent.","#UFENT#","CASE WHEN ENDENT IS NULL THEN UFCLI ELSE UFENT END AS UFE",new Integer(20),null,"UF ent.");      
      adicOpcao("Contato do cliente","#CONTATO#","CONTCLI",new Integer(40),null,"Contato");
      
  }
}
