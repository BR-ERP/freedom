/**
 * @version 18/04/2005 <BR>
 * @author Setpoint Inform�tica Ltda / Alexandre Marcondes.
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)IFilho.java <BR>
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
 * ? <BR>
 * 
 */
package org.freedom.telas;

import java.awt.Component;
import java.awt.Container;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.swing.JPanelPad;


public interface IFilho {
    public abstract void setTitulo(String tit, String name);

    public abstract void setAtribos(int Esq, int Topo, int Larg, int Alt);

    public abstract void setTela(Container c);

    public abstract Container getTela();
    
    public abstract JPanelPad adicBotaoSair();

    public abstract void setFirstFocus(Component firstFocus);

    public abstract void firstFocus();

    public abstract void setConexao(DbConnection cn);

    public abstract void execShow();

    public abstract boolean getInitFirstFocus();

    public abstract void setInitFirstFocus(boolean initFirstFocus);

    public abstract void setTelaPrim(FPrincipal fP);
}