package org.freedom.modulos.pdv;

/**
 * @version 30/06/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FFechaVenda.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 * 
 */

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.drivers.ECFDriver;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class DLFechaDia extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldFK txtDataHora = new JTextFieldFK( JTextFieldPad.TP_STRING, 16, 0 );

	private JTextFieldFK txtVlrCaixa = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JCheckBoxPad cbReducaoZ = new JCheckBoxPad( "Deseja executar a redu��o Z?", "S", "N" );

	private ECFDriver ecf = new ECFDriver( ! AplicativoPDV.usaEcfDriver() );
	
	private Date datacaixa = null;
	
	private boolean naoExecutouReducaoZ = false;

	public DLFechaDia() {

		super( Aplicativo.telaPrincipal );
				
		setTitulo( "Fechamento de caixa" );
		setAtribos( 313, 170 );

		cbReducaoZ.setVlrString( "N" );
	}
	
	private void montaTela() {
		
		naoExecutouReducaoZ = naoExecutouReducaoZ();

		adic( new JLabelPad( "Data e Hora: " ), 7, 10, 110, 20 );
		adic( txtDataHora, 7, 30, 140, 20 );
		adic( new JLabelPad( "Saldo do caixa: " ), 155, 10, 120, 20 );
		adic( txtVlrCaixa, 150, 30, 140, 20 );
		
		if ( naoExecutouReducaoZ ) {		
			adic( cbReducaoZ, 10, 60, 280, 20 );
		}
	}
	
	private boolean naoExecutouReducaoZ() {
		
		int procedureresult = -1;
		
		try {
			
			PreparedStatement ps = con.prepareStatement( "SELECT IRETORNO FROM PVVERIFCAIXASP(?,?,?,?,?,?)" ); 
			ps.setInt( 1, AplicativoPDV.iCodCaixa );
			ps.setInt( 2, AplicativoPDV.iCodEmp );
			ps.setInt( 3, AplicativoPDV.iCodFilial );
			ps.setDate( 4, Funcoes.dateToSQLDate( new Date() ) );
			ps.setInt( 5, AplicativoPDV.iCodFilialPad );
			ps.setString( 6, AplicativoPDV.strUsuario );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				procedureresult = rs.getInt( 1 );	
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
				
		if ( procedureresult != 3 && procedureresult != 11 ) {
			return true;				
		}
			
		return false;
	}

	private void loadCaixa() {
	
		try {
			
			PreparedStatement ps = con.prepareStatement( 
				"SELECT FIRST 1 DTAMOV, VLRSLDMOV FROM PVMOVCAIXA " +
				"WHERE CODEMP=? AND CODFILIAL=? AND CODCAIXA=? ORDER BY DTAMOV DESC, NROMOV DESC" );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, AplicativoPDV.iCodCaixa );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				datacaixa = rs.getDate( "DTAMOV" ) == null ? new Date() : rs.getDate( "DTAMOV" );
				txtDataHora.setVlrString( ( new SimpleDateFormat( "dd/MM/yyyy HH:mm" ) ).format( datacaixa ) );
				txtVlrCaixa.setVlrBigDecimal( new BigDecimal( ( rs.getBigDecimal( "VLRSLDMOV" ) != null ? rs.getDouble( "VLRSLDMOV" ) : 0 ) ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "N�o foi poss�vel buscar o saldo atual.\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
		
	}

	private boolean execFechamento( boolean bReduz ) {

		boolean bRet = false;
		
		try {

			// Fecha o caixa:	
			
			PreparedStatement ps = con.prepareStatement( "EXECUTE PROCEDURE PVFECHACAIXASP(?,?,?,?,?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PVMOVCAIXA" ) );
			ps.setInt( 3, AplicativoPDV.iCodCaixa );
			ps.setDate( 4, Funcoes.dateToSQLDate( datacaixa ) );
			ps.setString( 5, bReduz ? "S" : "N" );
			ps.setInt( 6, Aplicativo.iCodFilial );
			ps.setString( 7, Aplicativo.strUsuario );
			ps.execute();
			
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}			
			
			bRet = true;
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao executar fechamento do caixa!\n" + err.getMessage(), true, con, err );
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro ao executar fechamento do caixa." );
		}
		
		return bRet;
		
	}

	private boolean execSangria() {

		boolean bRet = false;

		// Sangria para o troco:

		try {
			
			PreparedStatement ps = con.prepareStatement( "EXECUTE PROCEDURE PVSANGRIASP(?,?,?,?,?,?)" );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PVMOVCAIXA" ) );
			ps.setBigDecimal( 3, txtVlrCaixa.getVlrBigDecimal() );
			ps.setInt( 4, AplicativoPDV.iCodCaixa );
			ps.setDate( 5, Funcoes.dateToSQLDate( datacaixa ) );
			ps.setString( 6, Aplicativo.strUsuario );
			ps.execute();
			
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			bRet = true;
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao executar o troco!\n" + err.getMessage(), true, con, err );
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro ao executar o troco." );
		}
		
		return bRet;
	}

	private void fechaCaixa( boolean bReduz ) {
		
		if ( AplicativoPDV.bECFTerm ) {

			if ( naoExecutouReducaoZ && txtVlrCaixa.getVlrBigDecimal().floatValue() > 0.0F ) {

				if ( execSangria() && ! AplicativoPDV.bModoDemo ) {

					ecf.sangria( txtVlrCaixa.getVlrBigDecimal() );
				}
			}
			
			if ( ! AplicativoPDV.bModoDemo ) {
			
				ecf.leituraX();
			}			

			if ( execFechamento( bReduz ) ) {

				Funcoes.mensagemInforma( null, "O caixa foi fechado." );

				if ( bReduz && !AplicativoPDV.bModoDemo ) {

					FLeFiscal fiscal = new FLeFiscal();
					fiscal.setConexao( con );

					if ( fiscal.getReducaoZ( Calendar.getInstance().getTime(), AplicativoPDV.iCodCaixa ) ) {

						if ( ecf.reducaoZ() ) {

							fiscal.salvaReducaoZ();
						}
						else {

							Funcoes.mensagemErro( null, "Erro ao executar a redu��o Z!" );
						}
					}
					else {

						Funcoes.mensagemErro( null, "Erro ao executar a redu��o Z!" );
					}
				}
			}					
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			
			if ( Funcoes.mensagemConfirma( null, "Confirma fechamento?" ) == JOptionPane.YES_OPTION ) {
			
				if ( cbReducaoZ.getVlrString().equals( "S" ) ) {
				
					if ( Funcoes.mensagemConfirma( null, "Aten��o!\nSe for executada a 'Redu��o Z'\no caixa ser� fechado em definitivo!\nDeseja executar assim mesmo?" ) == JOptionPane.YES_OPTION ) {
						
						fechaCaixa( true );
					}
					else {
						
						return;
					}
				}
				else {
					
					fechaCaixa( false );
				}
			}
			else {
				
				return;
			}
		}
		
		super.actionPerformed( evt );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		montaTela();
		loadCaixa();
	}
}
