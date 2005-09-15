/**
 * @version 05/07/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.layout <BR>
 * Classe: @(#)Layout.java <BR>
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
 * Coment�rios para a classe...
 */

package org.freedom.layout;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.componentes.TabVector;
public class Layout extends Object {
	//public boolean bEntrada = false;
	protected TabVector cab = null;
	protected TabVector itens = null;
	protected TabVector parc = null;
	protected TabVector adic = null;
	protected TabVector frete = null;
  
public Layout() { }
	public boolean imprimir(NF nf, ImprimeOS imp) {
		cab = nf.getTabVector(NF.T_CAB);
		itens = nf.getTabVector(NF.T_ITENS);
		parc = nf.getTabVector(NF.T_PARC);
		adic = nf.getTabVector(NF.T_ADIC);
		frete = nf.getTabVector(NF.T_FRETE);
		return false;
	}
	
}
