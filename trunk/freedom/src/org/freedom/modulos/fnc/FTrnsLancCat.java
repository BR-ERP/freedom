/**
 * @version 05/12/2002 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FTrnsLancCat.java <BR>
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
 * Coment�rios sobre a classe...
 */
package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FTrnsLancCat extends FFilho implements ActionListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelRodape = null;

	private final JPanelPad panelCentro = new JPanelPad();

	private final JButtonPad btVerificar = new JButtonPad( "Verificar" );

	private final JButtonPad btTransferir = new JButtonPad( "Transferir" );

	private final JLabel status = new JLabel( "Aguardando verifica��o.", SwingConstants.CENTER );

	private final JTextFieldPad txtCodPlanOrig = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtDescPlanOrgi = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtTipoPlanOrgi = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	private final JTextFieldPad txtCodPlanDest = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtDescPlanDest = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtTipoPlanDest = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	private final ListaCampos lcPlanOrig = new ListaCampos( this, "PN" );

	private final ListaCampos lcPlanDest = new ListaCampos( this, "PN" );
	
	private final String[] tabelas;
	
	private final Map<String, String> mapaTabelas = new HashMap<String, String>();
	

	public FTrnsLancCat() {

		super( false );
		setTitulo( " Tramsfer�ncia de lan�amentos entre categorias de planejamento" );
		setAtribos( 50, 50, 385, 320 );

		montaListaCampos();
		montaTela();

		btVerificar.addActionListener( this );
		btTransferir.addActionListener( this );
		
		lcPlanOrig.addCarregaListener( this );
		lcPlanDest.addCarregaListener( this );
		
		mapaTabelas.put( "CPRATEIO", "FN" );
		mapaTabelas.put( "EQPRODPLAN", "PN" );
		mapaTabelas.put( "FNCONTA", "PN" );
		mapaTabelas.put( "FNFBNCLI", "PN" );
		mapaTabelas.put( "FNITPAGAR", "PN" );
		mapaTabelas.put( "FNITRECEBER", "PN" );
		mapaTabelas.put( "FNLANCA", "PN" );
		mapaTabelas.put( "FNPAGTOCOMI", "PN" );
		mapaTabelas.put( "FNPLANOPAG", "PN" );
		mapaTabelas.put( "FNSUBLANCA", "PN" );
		mapaTabelas.put( "VDVENDEDOR", "PN" );
		
		tabelas = new String[]{ 
				"CPRATEIO",
				"EQPRODPLAN",
				"FNCONTA",
				"FNFBNCLI",
				"FNITPAGAR",
				"FNITRECEBER",
				"FNLANCA",
				"FNPAGTOCOMI",
				"FNPLANOPAG",
				"FNSUBLANCA",
				"VDVENDEDOR"
				};
	}

	private void montaListaCampos() {

		/**
		 * Planejamento de Origem
		 */

		lcPlanOrig.add( new GuardaCampo( txtCodPlanOrig, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, true ) );
		lcPlanOrig.add( new GuardaCampo( txtDescPlanOrgi, "DescPlan", "Descri��o do plano de contas", ListaCampos.DB_SI, false ) );
		lcPlanOrig.add( new GuardaCampo( txtTipoPlanOrgi, "TipoPlan", "Tipo", ListaCampos.DB_SI, false ) );
		lcPlanOrig.setWhereAdic( " NIVELPLAN=6 " );
		lcPlanOrig.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlanOrig.setQueryCommit( false );
		lcPlanOrig.setReadOnly( true );
		txtCodPlanOrig.setNomeCampo( "CodPlan" );
		txtCodPlanOrig.setTabelaExterna( lcPlanOrig );
		txtCodPlanOrig.setFK( true );
		txtCodPlanOrig.setRequerido( true );
		txtCodPlanOrig.setListaCampos( lcPlanOrig );
		txtDescPlanOrgi.setListaCampos( lcPlanOrig );
		txtTipoPlanOrgi.setListaCampos( lcPlanOrig );

		/**
		 * Planejamento de Destino
		 */

		lcPlanDest.add( new GuardaCampo( txtCodPlanDest, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, true ) );
		lcPlanDest.add( new GuardaCampo( txtDescPlanDest, "DescPlan", "Descri��o do plano de contas", ListaCampos.DB_SI, false ) );
		lcPlanDest.add( new GuardaCampo( txtTipoPlanDest, "TipoPlan", "Tipo", ListaCampos.DB_SI, false ) );
		lcPlanDest.setWhereAdic( " NIVELPLAN=6 " );
		lcPlanDest.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlanDest.setQueryCommit( false );
		lcPlanDest.setReadOnly( true );
		txtCodPlanDest.setNomeCampo( "CodPlan" );
		txtCodPlanDest.setTabelaExterna( lcPlanDest );
		txtCodPlanDest.setFK( true );
		txtCodPlanDest.setRequerido( true );
		txtCodPlanDest.setListaCampos( lcPlanDest );
		txtDescPlanDest.setListaCampos( lcPlanDest );
		txtTipoPlanDest.setListaCampos( lcPlanDest );
	}

	private void montaTela() {

		panelRodape = adicBotaoSair();
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		panelRodape.setPreferredSize( new Dimension( 600, 32 ) );

		pnCliente.add( panelCentro, BorderLayout.CENTER );
		panelCentro.setBorder( BorderFactory.createEtchedBorder() );
		panelCentro.setPreferredSize( new Dimension( 600, 200 ) );

		/**
		 * Planejamento de Origem
		 */

		panelCentro.adic( new JLabelPad( "C�d.plan. Origem" ), 7, 10, 100, 20 );
		panelCentro.adic( txtCodPlanOrig, 7, 30, 100, 20 );
		panelCentro.adic( new JLabelPad( "Descri��o do planejamento de Origem" ), 110, 10, 250, 20 );
		panelCentro.adic( txtDescPlanOrgi, 110, 30, 250, 20 );

		/**
		 * Planejamento de Destino
		 */

		panelCentro.adic( new JLabelPad( "C�d.plan. Destino" ), 7, 50, 100, 20 );
		panelCentro.adic( txtCodPlanDest, 7, 70, 100, 20 );
		panelCentro.adic( new JLabelPad( "Descri��o do planejamento de Destino" ), 110, 50, 250, 20 );
		panelCentro.adic( txtDescPlanDest, 110, 70, 250, 20 );

		/**
		 * Bot�es
		 */

		panelCentro.adic( btVerificar, 7, 120, 353, 30 );
		panelCentro.adic( btTransferir, 7, 160, 353, 30 );
		panelCentro.adic( status, 7, 220, 353, 20 );

		btTransferir.setEnabled( false );
		status.setBorder( BorderFactory.createLineBorder( Color.BLUE ) );
		status.setForeground( Color.BLUE );
	}
	
	private boolean valida() {
		
		boolean origemOk = false;
		String mensagem = null;
		int cm = 0;
		
		if ( txtCodPlanOrig.getVlrString() == null || txtCodPlanOrig.getVlrString().trim().length() == 0 ) {
			mensagem = "Planejamento de origem deve ser informado.";
			cm = 1;
		}
		else if ( txtCodPlanDest.getVlrString() == null || txtCodPlanDest.getVlrString().trim().length() == 0 ) {
			mensagem = "Planejamento de destino deve ser informado.";
			cm = 2;
		}
		else if ( "BC".indexOf( txtTipoPlanOrgi.getVlrString() ) > -1 ) {
			mensagem = "Planejamento de origem n�o pode ser CAIXA  ou BANCO.";
			cm = 3;
		}
		else if ( "BC".indexOf( txtTipoPlanDest.getVlrString() ) > -1 ) {
			mensagem = "Planejamento de destino n�o pode ser CAIXA  ou BANCO.";
			cm = 4;
		}
		else if ( ! txtTipoPlanOrgi.getVlrString().equals( txtTipoPlanDest.getVlrString() ) ) {
			mensagem = "Planejamento de destino deve ser do mesmo tipo que o de origem.";
			cm = 5;
		}
		else {
			origemOk = true;
		}
		
		if( ! origemOk ) {
			JOptionPane.showMessageDialog( panelCentro, mensagem, "Alerta", JOptionPane.INFORMATION_MESSAGE );
			if ( cm == 1 || cm ==3 ) {
				txtCodPlanOrig.requestFocus();	
			}
			else {
				txtCodPlanDest.requestFocus();
			}
		}
		
		return origemOk;
	}

	private void verificar() {

		if ( valida() ) {
			
			btVerificar.setEnabled( false );	
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuilder sql = new StringBuilder();
			int numeroDeRegistros = 0;
			
			String idFk = "";
			
			for ( String tabela : tabelas ) {
				
				idFk = mapaTabelas.get( tabela );
				
				sql.delete( 0, sql.length() );
				sql.append( "SELECT COUNT(*) FROM " );
				sql.append( tabela );
				sql.append( " WHERE" );
				sql.append( " CODEMP" + idFk + "=? AND" );
				sql.append( " CODFILIAL" + idFk + "=? AND" );
				sql.append( " CODPLAN=?" );
				
				try {
					
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, lcPlanOrig.getCodFilial() );
					ps.setString( 3, txtCodPlanOrig.getVlrString() );
					
					rs = ps.executeQuery();
					
					if ( rs.next() ) {
						numeroDeRegistros += rs.getInt( 1 );
					}
					
					rs.close();
					ps.close();
					
					con.commit();
				} catch ( SQLException e ) {
					e.printStackTrace();
					Funcoes.mensagemErro( this, "Erro ao consutar planejamento de origem.\n" + e.getMessage(), true, con, e );
					break;
				}
			}
			
			status.setText( numeroDeRegistros + " registros para o planejamento " + txtDescPlanOrgi.getVlrString() );
			
			if ( numeroDeRegistros > 0 ) {
				btTransferir.setEnabled( true );		
				btTransferir.requestFocus();				
			}
		}
	}

	private void transferir() {

		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		boolean problema = false;		
		String idFk = "";
		
		try {
			for ( String tabela : tabelas ) {
				
				idFk = mapaTabelas.get( tabela );
				
				sql.delete( 0, sql.length() );
				sql.append( "UPDATE " );
				sql.append( tabela );
				sql.append( " SET" );
				sql.append( " CODEMP" + idFk + "=?," );
				sql.append( " CODFILIAL" + idFk + "=?," );
				sql.append( " CODPLAN=?" );
				sql.append( " WHERE" );
				sql.append( " CODEMP" + idFk + "=? AND" );
				sql.append( " CODFILIAL" + idFk + "=? AND" );
				sql.append( " CODPLAN=?" );
				
				try {
					
					status.setText( "Atulizando tabela " + tabela );
					
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, lcPlanDest.getCodFilial() );
					ps.setString( 3, txtCodPlanDest.getVlrString() );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, lcPlanOrig.getCodFilial() );
					ps.setString( 6, txtCodPlanOrig.getVlrString() );
					ps.executeUpdate();
					
				} catch ( SQLException e ) {
					e.printStackTrace();
					problema = true;
					Funcoes.mensagemErro( this, "Erro ao atualizar planejamento de destino.\n" + e.getMessage(), true, con, e );
					status.setText( "" );
					break;
				}
			}
		} 
		finally {
			try {
				if ( problema ) {
					con.rollback();
				}
				else {
					
					sql.delete( 0, sql.length() );
					sql.append( "DELETE FROM FNSALDOLANCA " );
					sql.append( "WHERE CODEMPPN=? AND CODFILIALPN=? AND CODPLAN=?" );
					
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, lcPlanOrig.getCodFilial() );
					ps.setString( 3, txtCodPlanOrig.getVlrString() );
					ps.executeUpdate();
					
					con.commit();
					btTransferir.setEnabled( false );
					status.setText( "Transfer�ncia completada." );
				}
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource().equals( btVerificar ) ) {
			verificar();
		}
		else if ( e.getSource().equals( btTransferir ) ) {
			transferir();
		}
	}

	public void afterCarrega( CarregaEvent e ) {
		
		if ( e.getListaCampos() == lcPlanOrig || e.getListaCampos() == lcPlanDest ) {
			btVerificar.setEnabled( true );
			btTransferir.setEnabled( false );
			status.setText( "Aguardando verifica��o." );
		}
	}

	public void beforeCarrega( CarregaEvent e ) {
		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcPlanOrig.setConexao( cn );
		lcPlanDest.setConexao( cn );

	}
}
