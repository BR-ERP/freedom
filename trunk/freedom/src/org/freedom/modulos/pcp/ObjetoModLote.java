/*
 * Created on 17/06/2005
 * Autor: anderson 
 * Descri��o:  
 */
package org.freedom.modulos.pcp;

import org.freedom.componentes.ObjetoModeloLote;

/**
 * @author anderson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjetoModLote extends ObjetoModeloLote {

  public ObjetoModLote() { 	
      adicOpcao("C�digo do produto","#CODPROD#",new Integer(8));
      adicOpcao("Dia","#DIA#",new Integer(2));
      adicOpcao("M�s","#MES#",new Integer(2));
      adicOpcao("Ano","#ANO#",new Integer(4));
      adicOpcao("N�mero da produ��o no dia","#NPRODDIA#",new Integer(5));                  
  }
}
