/**
 * @version 17/07/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FSitTrib.java <BR>
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

package org.freedom.modulos.lvf.view.crud.comum;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.lvf.view.dialog.report.DLRSitTrib;

public class FSitTrib extends FDados implements ActionListener, RadioGroupListener, CarregaListener {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodSitTrib = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
	private JTextFieldPad txtDescSitTrib = new JTextFieldPad(JTextFieldPad.TP_STRING,200,0);
	private JTextFieldPad txtImpSitTrib = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
	private JRadioGroup<String, String> rgImpSitTrib = null;

	public FSitTrib () {
		super();
		setTitulo("Situa��o Tribut�ria");
		setAtribos( 50, 50, 434, 200);

		Vector<String> vImpTribLabs = new Vector<String>();		

		vImpTribLabs.addElement( "ICMS" );
		vImpTribLabs.addElement( "IPI" );
		vImpTribLabs.addElement( "PIS" );
		vImpTribLabs.addElement( "COFINS" );		
		vImpTribLabs.addElement( "IR" );
		vImpTribLabs.addElement( "Contrib.Social" );

		Vector<String> vImpTribVals = new Vector<String>();
		vImpTribVals.addElement( "IC" );
		vImpTribVals.addElement( "IP" );
		vImpTribVals.addElement( "PI" );
		vImpTribVals.addElement( "CO" );		
		vImpTribVals.addElement( "IR" );
		vImpTribVals.addElement( "CS" );

		rgImpSitTrib = new JRadioGroup<String, String>( 2, 3, vImpTribLabs, vImpTribVals );

		adic( new JLabelPad("Imposto"), 7,0,403,20);
		adic( rgImpSitTrib, 7, 20, 403, 60);

		rgImpSitTrib.setBorder( BorderFactory.createEtchedBorder( Color.RED, Color.GRAY ) );

		adicCampo(txtCodSitTrib, 7, 100, 70, 20,"CodSitTrib","C�d.sit.trib.",ListaCampos.DB_PK, true);
		adicCampoInvisivel( txtImpSitTrib, "impsittrib", "Imposto", ListaCampos.DB_PK, true );
		adicCampo(txtDescSitTrib, 80, 100, 330, 20,"DescSitTrib","Descri��o da situa��o tribut�ria",ListaCampos.DB_SI, true);		

		setListaCampos( false, "SITTRIB", "LF");

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		lcCampos.setQueryInsert(false);    
		btPrevimp.addActionListener(this);
		btImp.addActionListener(this);
		rgImpSitTrib.addRadioGroupListener( this );

		lcCampos.addCarregaListener( this );

		setImprimir(true);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btPrevimp)
			imprimir(true);
		else if (evt.getSource() == btImp) 
			imprimir(false);
		super.actionPerformed(evt);
	}

	private void imprimir(boolean bVisualizar) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLRSitTrib dl = null;
		ImprimeOS imp = null;
		int linPag = 0;

		dl = new DLRSitTrib();
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}

		try {

			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio de Situa��es Tribut�rias");
			imp.limpaPags();

			sSQL = "SELECT CODSITTRIB,DESCSITTRIB,IMPSITTRIB FROM LFSITTRIB WHERE CODEMP=? AND CODFILIAL=? " 
				+  "ORDER BY "+dl.getValor();

			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("LFSITTRIB"));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()==0) {
					imp.impCab(80, false);
					imp.say(imp.pRow(), 0, imp.normal());
					imp.say(imp.pRow(), 2, "C�digo");
					imp.say(imp.pRow(), 30, "Descri��o");
					imp.say(imp.pRow(), 90, "Imposto");
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, StringFunctions.replicate("-",79));
				}
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 2, rs.getString("Codsittrib"));
				imp.say(imp.pRow(), 30, rs.getString("Descsittrib"));
				imp.say(imp.pRow(), 90, rs.getString("impsittrib"));
				if (imp.pRow()>=linPag) {
					imp.say(imp.pRow() + 1, 0, imp.normal());
					imp.say(imp.pRow(), 0, StringFunctions.replicate("-", 79));
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow() + 1, 0, imp.normal());
			imp.say(imp.pRow(), 0, StringFunctions.replicate("=",79));
			imp.eject();

			imp.fechaGravacao();
			con.commit();
			dl.dispose();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de Situa��o Tribut�ria!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			dl = null;
		}

		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}

	public void valorAlterado( RadioGroupEvent e ) {

		if ( e.getSource() == rgImpSitTrib ) {
			txtImpSitTrib.setVlrString( rgImpSitTrib.getVlrString() );
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCampos ) {
			rgImpSitTrib.setVlrString( txtImpSitTrib.getVlrString());
		}

	}

	public void beforeCarrega( CarregaEvent cevt ) {}

}
