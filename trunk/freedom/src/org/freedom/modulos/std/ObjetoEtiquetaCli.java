/*
 * Created on 09/04/2005
 * Autor: anderson 
 * Descri��o: 
 */
package org.freedom.modulos.std;

import org.freedom.componentes.ObjetoEtiqueta;

/**
 * @author anderson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjetoEtiquetaCli extends ObjetoEtiqueta {

  public ObjetoEtiquetaCli() { 	
      adicOpcao("C�digo do cliente","#CODIGO#","CODCLI",new Integer(8),null,"C�d.Cli.");
      adicOpcao("Raz�o social do cliente","#RAZAO_SOCIAL#","RAZCLI",new Integer(50),null,"Raz�o");
      adicOpcao("Nome do cliente","#NOME#","NOMECLI",new Integer(50),null,"Nome");
      adicOpcao("CPF ou CNPJ do cliente","#CNPJ#","CNPJCLI",new Integer(18),"##.###.###/####-##","Cnpj");
      adicOpcao("Endere�o do cliente","#ENDERE�O#","ENDCLI",new Integer(50),null,"Endere�o");
      adicOpcao("Complemento","#COMPLEMENTO#","COMPLCLI",new Integer(20),null,"Complemento");
      adicOpcao("N�mero","#NUMERO#","NUMCLI",new Integer(20),null,"Nro.");
      adicOpcao("CEP","#CEP#","CEPCLI",new Integer(9),null,"Cep");
      adicOpcao("Bairro do cliente","#BAIRRO#","BAIRCLI",new Integer(30),null,"Bairro");
      adicOpcao("Cidade do cliente","#CIDADE#","CIDCLI",new Integer(30),null,"Cidade");
      adicOpcao("UF do cliente","#UF#","UFCLI",new Integer(2),null,"UF");
      adicOpcao("Contato do cliente","#CONTATO#","CONTCLI",new Integer(40),null,"Contato");
      
  }
}
