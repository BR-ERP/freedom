/**
 * @version 04/12/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FAjustaSitOrc.java <BR>
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
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.atd.view.frame.tool;

import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JComboBoxPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

import java.util.Vector;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;


public class FAjustaSitOrc extends FDados implements ActionListener, CarregaListener {
	
	private static final long serialVersionUID = 1L;
	
	private ListaCampos lcOrc = new ListaCampos(this, "");
	private ListaCampos lcProd = new ListaCampos( this, "PD" );
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtCodBarras = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );
	private JTextFieldPad txtCodOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldFK txtDtOrc = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	private JTextFieldPad txtDtVencOrc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	private JTextFieldPad txtCodItOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqIni = new JTextFieldPad(JTextFieldPad.TP_INTEGER,9,0);
	private JTextFieldPad txtSeqMax = new JTextFieldPad(JTextFieldPad.TP_INTEGER,9,0);
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	private JComboBoxPad cbSitEntItOrc = null;
	private JComboBoxPad cbSitTermRItOrc = null;
	private Vector<String> vLabs1 = new Vector<String>();
	private Vector<String> vLabs2 =  new Vector<String>();
	private Vector<String> vVals1 = new Vector<String>();
	private Vector<String> vVals2 = new Vector<String>();

	public FAjustaSitOrc() {
		super();
		setTitulo("Ajusta Situa��o dos Or�amentos");
		setAtribos( 50, 50, 445, 270);

		vLabs1.addElement( "N�o entregue" );
		vLabs1.addElement( "Entregue" );
		vVals1.addElement( "N" );
		vVals1.addElement( "E" );
		cbSitEntItOrc = new JComboBoxPad(vLabs1, vVals1, JComboBoxPad.TP_STRING, 1, 0);

		/*E - Emitir
		N - N�o emitir
		O - Emitido*/
		
		vLabs2.addElement( "Emitir" );
		vLabs2.addElement( "N�o emitir" );
		vLabs2.addElement( "Emitido" );
		vVals2.addElement( "E" );
		vVals2.addElement( "N" );
		vVals2.addElement( "O" );
		cbSitTermRItOrc = new JComboBoxPad(vLabs2, vVals2, JComboBoxPad.TP_STRING, 1, 0);
		
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

		// FK Produto
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, txtDescProd, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Refer�ncia do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtCodBarras, "CodBarProd", "C�digo de barras", ListaCampos.DB_SI, false ) );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodProd.setTabelaExterna( lcProd );
		
		adicCampo(txtCodOrc, 7, 20, 70, 20, "CodOrc", "N�m.or�.", ListaCampos.DB_PF, true);
		adicDescFK( txtDtOrc, 80, 20, 90, 20, "DtOrc", "Dt.emiss�o");
		adicCampo(txtCodItOrc, 173, 20, 70, 20, "CodItOrc", "Item or�.", ListaCampos.DB_PK, true);
		adicCampo(txtCodProd, 7, 60, 70, 20, "CodProd", "C�d.prod.", ListaCampos.DB_FK, txtDescProd, true);
		adicDescFK(txtDescProd, 80, 60, 310, 20, "DescProd", "Descri��o do produto");
		adicDB(cbSitEntItOrc, 7, 100, 150, 30, "SitEntItOrc", "Situa��o da entrega",true);
		adicDB(cbSitTermRItOrc, 160, 100, 230, 30, "SitTermRItOrc", "Situa��o do termo de recebimento",true);
		setListaCampos( true, "ITORCAMENTO", "VD");
		nav.btNovo.setVisible( false );
		nav.btExcluir.setVisible( false );
		//nav.setAtivo( 1, false);
		lcCampos.addCarregaListener(this);
	}
	  
	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		lcOrc.setConexao( cn );
		lcProd.setConexao( cn );
	
	}
	
	public void beforeCarrega( CarregaEvent e ) { 
		txtSeqIni.setAtivo(true);
		txtSeqMax.setAtivo(true);
	}

	public void afterCarrega( CarregaEvent e ) { 
		if(e.getListaCampos() == lcCampos) {
		}
	}

}
