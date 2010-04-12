/**
 * @version 15/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FGravaMoeda.java <BR>
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
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.JLabelPad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;

import org.freedom.ecf.app.ControllerECF;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FSangria extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldFK txtDataUOper = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldFK txtSaldoUOper = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private final JTextFieldFK txtStatusUOper = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldFK txtUsuUOper = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldFK txtUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldFK txtData = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtValor = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private final ControllerECF ecf;
	
	private char STATUS_OLD = '0';
	

	public FSangria() {

		super( Aplicativo.telaPrincipal );
		setTitulo( "Sangria de Caixa" );
		setAtribos( 435, 270 );
		
		ecf = new ControllerECF( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo, 
				AplicativoPDV.getEcflayout() );
		
		montaTela();
	}
	
	private void montaTela() {

		adic( new JLabelPad( "Data da �ltima opera��o" ), 7, 10, 200, 20 );
		adic( txtDataUOper, 7, 30, 200, 20 );
		adic( new JLabelPad( "Saldo atual do caixa" ), 210, 10, 200, 20 );
		adic( txtSaldoUOper, 210, 30, 200, 20 );
		
		adic( new JLabelPad( "�ltimo operador" ), 7, 50, 200, 20 );
		adic( txtUsuUOper, 7, 70, 200, 20 );
		adic( new JLabelPad( "Status atual do caixa" ), 210, 50, 200, 20 );
		adic( txtStatusUOper, 210, 70, 200, 20 );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		adic( lbLinha, 7, 109, 403, 2 );

		adic( new JLabelPad( "Operador atual" ), 7, 120, 200, 20 );
		adic( txtUsu, 7, 140, 200, 20 );
		adic( new JLabelPad( "Data" ), 210, 120, 100, 20 );
		adic( txtData, 210, 140, 100, 20 );
		adic( new JLabelPad( "Valor" ), 313, 120, 97, 20 );
		adic( txtValor, 313, 140, 97, 20 );

		txtUsu.setVlrString( Aplicativo.strUsuario );
		txtData.setVlrDate( new Date() );
		txtValor.setVlrBigDecimal( new BigDecimal( 0 ) );
	}

	private void loadInfoCaixa() {

		try {

			PreparedStatement ps = con.prepareStatement( 
					"SELECT DDTAMOVRET, CTIPOMOV, NVLRSLDMOV, CIDUSU FROM PVRETMOVCAIXASP(?,?,?,?)" );
			ps.setInt( 1, AplicativoPDV.iCodCaixa );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "PVMOVCAIXA" ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( new Date() ) );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				STATUS_OLD = rs.getString( "CTIPOMOV" ).toCharArray()[ 0 ];	

				txtStatusUOper.setVlrString( Funcoes.transStatusECF( STATUS_OLD ) );
				txtDataUOper.setVlrDate( rs.getDate( "DDTAMOVRET" ) );
				txtSaldoUOper.setVlrString( Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "NVLRSLDMOV" ) ) );
				txtUsuUOper.setVlrString( rs.getString( "CIDUSU" ) );
			}

			rs.close();
			ps.close();
			
			con.commit();

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro carregar informa��es do caixa!!\n" + e.getMessage(), true, con, e );
		} 
	}

	private void execSangria() {

		if ( txtValor.floatValue() <= 0.0f ) {
			Funcoes.mensagemInforma( this, "Valor de sangria inv�lido!" );
			return;
		}

		try {
			
			PreparedStatement ps = con.prepareStatement( "EXECUTE PROCEDURE PVSANGRIASP(?,?,?,?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PVMOVCAIXA" ) );
			ps.setBigDecimal( 3, txtValor.getVlrBigDecimal() );
			ps.setInt( 4, AplicativoPDV.iCodCaixa );
			ps.setDate( 5, Funcoes.dateToSQLDate( new Date() ) );
			ps.setString( 6, Aplicativo.strUsuario );
			ps.execute();

			ps.close();
			
			con.commit();
			
			if ( ! ecf.sangria( txtValor.getVlrBigDecimal() ) ) {
				Funcoes.mensagemErro( this, ecf.getMessageLog() );
			}
			if ( ! ecf.abrirGaveta() ) {
				Funcoes.mensagemErro( this, ecf.getMessageLog() );
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao executar sangria!\n" + err.getMessage(), true, con, err );
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			execSangria();
		}
		
		super.actionPerformed( evt );
	}

	@ Override
	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == btOK && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btOK.doClick();
		}
		else if ( e.getSource() == btCancel && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			btCancel.doClick();
		}
		else {
			super.keyPressed( e );
		}
	}

	@ Override
	public void setVisible( boolean arg ) {

		if ( arg ) {
			if ( STATUS_OLD == 'F' || STATUS_OLD == 'Z' ) {
				Funcoes.mensagemErro( this, "Caixa Fechado!" );
				super.setVisible( false );
			}
			else {
				super.setVisible( true );
			}
		}
		else {
			super.setVisible( arg );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		loadInfoCaixa();
	}
}
