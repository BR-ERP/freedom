/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FDetalhe.java <BR>
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
 * Coment�rios da classe.....
 */

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;

import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;

public class FDetalhe extends FDados {
  private GridLayout glRod = new GridLayout(1,1);
  private GridLayout glCab = new GridLayout(1,1);
  private GridLayout glNavCab = new GridLayout(1,1);
  private BorderLayout blMaster = new BorderLayout();
  private BorderLayout blCab = new BorderLayout();
  private BorderLayout blNavCab = new BorderLayout();
  public  JPanelPad pnMaster = new JPanelPad(JPanelPad.TP_JPANEL,blMaster);
  public  JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,blCab);
  private JPanelPad pnBordCab = new JPanelPad(JPanelPad.TP_JPANEL,glCab);
  public  JPanelPad pnDet = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pnBordDet = new JPanelPad(JPanelPad.TP_JPANEL,glRod);
  public JPanelPad pnNavCab = new JPanelPad(JPanelPad.TP_JPANEL,blNavCab);
  private JPanelPad pnBordNavCab = new JPanelPad(JPanelPad.TP_JPANEL,glNavCab);
  public  JPanelPad pnCliCab = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  public  Navegador navRod = new Navegador(true);
  public  ListaCampos lcDet = new ListaCampos(this);
  public  Tabela tab = new Tabela();
  public  JScrollPane spTab = new JScrollPane(tab);  
  boolean bSetAreaCab = true;
  boolean bSetAreaDet = true;
  public FDetalhe () {
    pnRodape.remove(nav);
    pnRodape.add(navRod, BorderLayout.WEST); 
    
    pnCliente.remove(pinDados);
    pnCliente.add(pnMaster, BorderLayout.CENTER);

// Reconstruiu o FDados
//********************************************
// Agora ir� construir o FDetalhe 
    
    pnNavCab.add(nav, BorderLayout.WEST);
    
    pnBordNavCab.setPreferredSize(new Dimension(500,30));
    pnBordNavCab.setBorder(br);
    pnBordNavCab.add(pnNavCab);
    
    pnCab.add(pnCliCab, BorderLayout.CENTER);
    pnCab.add(pnBordNavCab, BorderLayout.SOUTH);
    
    pnBordCab.setPreferredSize(new Dimension(500,100));
    pnBordCab.setBorder(br);
    pnBordCab.add(pnCab);

    pnBordDet.setPreferredSize(new Dimension(500,50));
    pnBordDet.setBorder(br);
    pnBordDet.add(pnDet);    
    
    pnMaster.add(pnBordCab, BorderLayout.NORTH);
    pnMaster.add(pnBordDet, BorderLayout.SOUTH);
    pnMaster.add(spTab, BorderLayout.CENTER);
    
    lcDet.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcDet);
    lcDet.setTabela(tab);
  }
  public void montaTab() {
    lcDet.montaTab();      
  }
  public void setAltDet(int Alt) {
    pnBordDet.setPreferredSize(new Dimension(500,Alt));
  }
  public void setAltCab(int Alt) {
    pnBordCab.setPreferredSize(new Dimension(500,Alt));
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
  	lcDet.setConexao(cn);
  }
}
