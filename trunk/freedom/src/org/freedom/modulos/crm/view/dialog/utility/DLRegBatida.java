/**
 * @version 19/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)DLRegBatida.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Tela que registra Batida de ponto do empregado.
 * 
 */

package org.freedom.modulos.crm.view.dialog.utility;

import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.modulos.gpe.business.object.Batida;
import org.freedom.modulos.grh.view.frame.crud.plain.FTurnos;

public class DLRegBatida extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldFK txtMatempr = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 6, 0 );
	
	private final JTextFieldFK txtNomeempr = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0);
	
	private final JTextFieldFK txtDtbat = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );

	private final JTextFieldPad txtHbat = new JTextFieldPad( JTextFieldPad.TP_TIME, 5, 0 );
	
	private final ListaCampos lcBatida = new ListaCampos(this);
	
	private final ListaCampos lcEmpr = new ListaCampos(this, "EP");
	
	private Batida batida = null;
	
	public DLRegBatida() {

		super();
		setTitulo( "Registro da batida." );
		setAtribos( 50, 50, 500, 300 );
		montaListaCampos();
		montaTela();
	
	}
	
	public void montaTela(){

		adic( new JLabelPad( "Matr�cula" ), 7, 5, 150, 20 );
		adic( txtMatempr, 7, 25, 70, 20, "Matempr" );
		adic( new JLabelPad( "Nome" ), 7, 5, 150, 20 );
		adic( txtNomeempr, 80, 25, 300, 20, "Nomeempr");
		adic( new JLabelPad( "Data" ), 7, 45, 150, 20 );
		adic( txtDtbat, 7, 65, 90, 20, "Dtbat" );
		adic( new JLabelPad( "Hor�rio" ), 7, 45, 150, 20 );
		adic( txtHbat, 100, 65, 90, 20, "Hbat");
		
	}
	
	public void setValores(Batida batida) {
		this.batida = batida;
		txtMatempr.setVlrInteger( batida.getMatempr() );
		lcEmpr.carregaDados();
		txtDtbat.setVlrDate( batida.getDataponto() );
		txtHbat.setVlrString( batida.getHoraponto() );
	}
	
	public void montaListaCampos(){
		lcEmpr.add( new GuardaCampo( txtMatempr, "Matempr", "Matr�cula", ListaCampos.DB_PK, true ) );
		lcEmpr.add( new GuardaCampo( txtNomeempr, "Nomeempr", "Nome", ListaCampos.DB_SI, false ) );
		lcEmpr.montaSql( false, "EMPREGADO", "RH" );
		lcEmpr.setQueryCommit( false );
		lcEmpr.setReadOnly( true );
		txtMatempr.setTabelaExterna( lcEmpr, FTurnos.class.getCanonicalName() );
	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcEmpr.setConexao( cn );

	}
}
