/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPlanoPag.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Agrupamento de produtos similares.
 * 
 */

package org.freedom.modulos.std;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.telas.FDetalhe;

public class FSimilar extends FDetalhe implements CarregaListener, InsertListener, PostListener {
	private static final long serialVersionUID = 1L;
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
	private JTextFieldPad txtCodSim = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtDescSim = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldPad txtCodBarras = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private ListaCampos lcProd = new ListaCampos(this,"PD");
	
	public FSimilar () {
		setTitulo("Agrupamento de produtos similares");
		setAtribos( 100, 10, 480, 380);        
		
		//  ********************  Lista campos adicional  ********************    
		
		lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, txtDescProd, false));
		lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo( txtRefProd,  "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo( txtCodBarras, "CodBarProd", "C�digo de barras", ListaCampos.DB_SI, false));
		lcProd.setWhereAdic("ATIVOPROD='S'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);
		lcProd.setQueryInsert(false);
		txtCodProd.setTabelaExterna(lcProd);
		
		//  ********************  Master  ************************************
		
		pnMaster.setPreferredSize(new Dimension(500,200));
		setAltCab(90);
		pinCab = new JPanelPad();
		setListaCampos(lcCampos);
		setPainel( pinCab, pnCliCab);
		
		adicCampo(txtCodSim, 7, 20, 80, 20,"CodSim","C�d.Agp.",ListaCampos.DB_PK,null,true);
		adicCampo(txtDescSim, 90, 20, 320, 20,"DescSim","Descri��o do agrupamento",ListaCampos.DB_SI,null,true);
		
		setListaCampos( true, "SIMILAR", "EQ");
		lcCampos.setQueryInsert(true);    
		
		//  ********************  Detalhe  ***********************************    
		
		setAltDet(60);
		pinDet = new JPanelPad(440,50);
		setPainel( pinDet, pnDet);
		
		setListaCampos(lcDet);
		setNavegador(navRod);
		 
		adicCampo(txtCodProd,7,20,80,20,"CodProd","C�d.prod",ListaCampos.DB_PF,txtDescProd,true);
		adicDescFK(txtDescProd,90,20,320,20,"Descprod","Descri��o do produto");
		
		setListaCampos( false, "ITSIMILAR", "EQ");
		
		lcDet.setQueryInsert(false);    
		lcDet.setQueryCommit(false);
		    
		montaTab();
		tab.setTamColuna(80,0);
		tab.setTamColuna(320,1);
		lcCampos.addCarregaListener(this);
		lcCampos.addInsertListener(this);
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
	}
	  
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp)
			imprimir(true);
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}
	  
	private void imprimir(boolean bVisualizar) { }
	  
	public void beforeInsert(InsertEvent ievt) { }  
	  
	public void beforePost(PostEvent pevt) {  }
	  
	public void beforeCarrega(CarregaEvent cevt) {  }
	  
	public void afterCarrega(CarregaEvent cevt) {  }
	  
	public void afterInsert(InsertEvent ievt) {  }
	  
	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		lcProd.setConexao(cn);      
	}
}
