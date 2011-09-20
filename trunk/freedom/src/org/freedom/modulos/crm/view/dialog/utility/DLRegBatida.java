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
import java.sql.SQLException;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.object.Atendimento.PREFS;
import org.freedom.modulos.crm.dao.DAOAtendimento;
import org.freedom.modulos.gpe.business.object.Batida;
import org.freedom.modulos.gpe.dao.DAOBatida;
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
	
	private DAOBatida daobatida = null;
	
	public DLRegBatida() {

		super();
		setTitulo( "Registro da batida." );
		setAtribos( 50, 50, 450, 200 );
		montaListaCampos();
		montaTela();
	
	}
	
	public void montaTela(){

		adic( txtMatempr, 7, 25, 70, 20, "Matr�cula" );
		adic( txtNomeempr, 80, 25, 300, 20, "Nome");
		adic( txtDtbat, 7, 65, 90, 20, "Data" );
		adic( txtHbat, 100, 65, 90, 20, "Hor�rio");
		
	}
	
	public void setValores(DAOBatida daobatida, Batida batida) {
		this.daobatida = daobatida;
		this.batida = batida;
		txtMatempr.setVlrInteger( batida.getMatempr() );
		lcEmpr.carregaDados();
		txtDtbat.setVlrDate( batida.getDataponto() );
		txtHbat.setVlrString( batida.getHoraponto() );
	}
	
	public void montaListaCampos(){
		lcEmpr.add( new GuardaCampo( txtMatempr, "Matempr", "Matr�cula", ListaCampos.DB_PK, false ) );
		lcEmpr.add( new GuardaCampo( txtNomeempr, "Nomeempr", "Nome", ListaCampos.DB_SI, false ) );
		lcEmpr.montaSql( false, "EMPREGADO", "RH" );
		lcEmpr.setQueryCommit( false );
		lcEmpr.setReadOnly( true );
		txtMatempr.setTabelaExterna( lcEmpr, FTurnos.class.getCanonicalName() );
	}

	public void actionPerformed( ActionEvent evt ) {
		boolean result = false;
		if (evt.getSource()==btOK) {
            result = gravaBatida();			
		} else if (evt.getSource()==btCancel) {
			result = true;
		}
		if ( result ) {
			super.actionPerformed( evt );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcEmpr.setConexao( cn );

	}
	
	public boolean gravaBatida() {
		boolean result = true;
		String horaant = null;
		if (daobatida!=null) {
			try {
				horaant = batida.getHoraponto();
				batida.setCodemp( Aplicativo.iCodEmp );
				batida.setCodfilial( ListaCampos.getMasterFilial( "PEBATIDA" ) );
				batida.setHoraponto( txtHbat.getVlrString() );
				daobatida.executeProcInsereBatida( batida );
				insertIntervaloChegada(txtHbat.getVlrString(), horaant);
			} catch (SQLException e) {
				result = false;
				Funcoes.mensagemErro( this, "Erro registrando o ponto !\n" + e.getMessage());
			}
		}
		return result;
	}
	
	private void insertIntervaloChegada(String horaini, String horafim) {
		Long diferenca = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( horaini), Funcoes.strTimeTosqlTime( horafim ) );
		String horaPrimUltLancto = null;
		if ( diferenca<0 ) {
			// Cria um DAOAtendimento para inser��o de atendimento
			DAOAtendimento daoatend = new DAOAtendimento( con );
			try {
				// Carrega o prefer�ncias com modelo de atendimento para intervalo de chegada ou sa�da.
				daoatend.setPrefs( Aplicativo.iCodEmp , ListaCampos.getMasterFilial("SGPREFERE3") );
				// Continua se tiver modelo de atendimento para intervalo de chegada ou sa�da
				if ( daoatend.getPrefs()[PREFS.CODMODELME.ordinal()] != null) {
					// Verifica se j� existe lan�amento para o atendente e retorna a hora do atendimento.
					horaPrimUltLancto = daoatend.getHoraPrimUltLanca( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDIMENTO" ),
							batida.getDataponto(), horaini, horafim,
							batida.getCodempae(), batida.getCodfilialae(), batida.getCodatend(),
							batida.getAftela() );
					// Caso n�o exista lan�amentos no CRM ou o hor�rio do primeiro atendimento seja maior que a hora final do atendimento continua.
					if ( (horaPrimUltLancto==null) || (horafim.compareTo( horaPrimUltLancto )<0) ) {
						// Se tiver atendimento o hor�rio final do intervalo dever� ser o hor�rio inicial do atendimento j� registrado. 
						if (horaPrimUltLancto!=null) {
							horafim = horaPrimUltLancto;
						}
						daoatend.insertIntervaloChegada( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "ATATENDIMENTO" ), 
						  batida.getDataponto(), batida.getDataponto(), horaini, horafim, 
						  batida.getCodempae(), batida.getCodfilialae(), batida.getCodatend(), 
						  batida.getCodempus(), batida.getCodfilialus(), batida.getIdusu() );
					}
				}
			}
			catch (Exception e) {
				Funcoes.mensagemErro( this, "Erro inserindo lan�amento automatizado de intervalo !\n" + e.getMessage() );
				e.printStackTrace();
			}			
		}
	}
}
