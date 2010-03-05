/**
 * @version 10/02/2010 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLBaixaRec.java <BR>
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
 * Tela para captura da pesagem de cargas.
 * 
 */

package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;
import javax.comm.SerialPort;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.EditEvent;
import org.freedom.bmps.Icone;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.infra.driver.scale.AbstractScale;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;
import org.freedom.telas.SwingParams;

public class DLPesagem extends FFDialogo implements CarregaListener, FocusListener  {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtPeso1 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtPeso2 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtHora = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );
	
	private JLabelPad lbpeso1 = new JLabelPad(( "Peso 1" ));
	
	private JLabelPad lbpeso2 = new JLabelPad(( "Peso 2" ));
	
	private SerialPort porta =  null;
	
	private AbstractScale balanca = null;
	
	private String tipoprocrecmerc = null;
	
	private String driverbal = null;
	
	private Integer portabal = null;
	
	private Integer timeout = null;
	
	private Integer baundrate = null;
	
	private Integer databits = null;
	
	private Integer stopbits = null;
	
	private Integer parity = null;	
	
	public Container c = getContentPane();
	
	private JPanelPad pnGeral = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pnCampos = new JPanelPad();
	
	private JPanelPad pnMensagens = new JPanelPad(500, 34);
	
	private JLabelPad mensagem = new JLabelPad("");
	
	private JButtonPad btRefazer = new JButtonPad( "Refazer", Icone.novo( "btsoliccp.gif" ) );
	
	public DLPesagem( Component cOrig, String tipoprocrecmerc ) {

		super( cOrig );
		
		this.tipoprocrecmerc = tipoprocrecmerc;
		
		montatela();
		
		adicListeners();		
				
	}
	
	private void montatela() {
		
		setTitulo( "Pesagem" );
		
		int irow = 0; // Variavel para salto na posi��o do campo de data e hora
		
		c.add( pnGeral, BorderLayout.CENTER );
		
		pnGeral.add( pnCampos, BorderLayout.CENTER );
		pnGeral.add( pnMensagens, BorderLayout.SOUTH );
		
		setMensagem( "Iniciando par�metros...", Color.BLUE, null, false );
		
		pnMensagens.adic( mensagem, 4, 0, 200, 27 );
		
		pnCampos.adic( lbpeso1 , 7, 0, 252, 20 );
		pnCampos.adic( txtPeso1, 7, 20, 252, 50 );
		
		if( FTipoRecMerc.DESCARREGAMENTO.equals( tipoprocrecmerc ) ) {
			 
			pnCampos.adic( lbpeso2 , 7, 80, 252, 20 );
			pnCampos.adic( txtPeso2, 7, 100, 252, 50 );
			
			irow = 80;
		}
		
		setAtribos( 280, 280 + irow );		
		
		pnCampos.adic( new JLabelPad( "Data" ), 7, 80 + irow, 135, 20 );
		pnCampos.adic( txtData, 7, 100 + irow, 135, 50 );
		
		pnCampos.adic( new JLabelPad( "Hora" ), 147, 80 + irow, 110, 20 );
		pnCampos.adic( txtHora, 147, 100 + irow, 110, 50 );
		
	}
	
	private void adicListeners() {
		
		btRefazer.addActionListener( this );
		
	}
	
	private void setMensagem(String msg, Color cortexto, Color corpainel, boolean refazer) {
		
		pnMensagens.remove( btRefazer );
		
		if( cortexto!=null ) {
			mensagem.setForeground( cortexto );
		}
		else {
			mensagem.setForeground( Color.BLACK );
		}
		
		if( corpainel!=null ) {
			pnMensagens.setBackground( corpainel );
		}
		else {
			pnMensagens.setBackground( Color.GRAY );
		}
		
		mensagem.setText( msg );
		
		if(refazer) {
			
			pnGrid.removeAll();

			pnGrid.add( btRefazer );
			pnGrid.add( btCancel );
			
		}
		else {
			pnGrid.removeAll();
			
			pnGrid.add( btOK );
			pnGrid.add( btCancel );
			
		}
		
	}
	
	private void buscaPeso() {
		
		try {
			
			if(balanca!=null) {
				
				setMensagem( "Inicializando balan�a...", Color.BLUE, null, false );
				
				balanca.initialize( portabal, AbstractScale.TIMEOUT_ACK, baundrate, databits, stopbits, parity );
				
				setMensagem( "Recuperando dados da balan�a...", Color.BLUE, null, false );
				
				Date data = balanca.getDate();
				Time hora = balanca.getTime();
				BigDecimal peso = balanca.getWeight();
				
				if(peso != null) {
					if(txtPeso1.getVlrBigDecimal().floatValue()>0 && tipoprocrecmerc.equals( FTipoRecMerc.DESCARREGAMENTO )){
						txtPeso2.setVlrBigDecimal( peso );
					}
					else {
						txtPeso1.setVlrBigDecimal( peso );
					}
					
				}
				else {
					setMensagem( "Peso inv�lido na leitura!", Color.WHITE, Color.RED, true );
					return;
				}				
				if(data != null) {
					txtData.setVlrDate( data );
				}
				else {
					setMensagem( "Data inv�lida na leitura!", Color.ORANGE, null, false );
					return;
				}
				
				if(hora != null) {
					txtHora.setVlrTime( hora );
				}
				else {
					setMensagem( "Hora inv�lida na leitura!", Color.ORANGE, null, false );
					return;
				}
				
				setMensagem( "Leitura realizada com sucesso!", Color.WHITE, Color.GREEN, false );
				
			}
			else {
				setMensagem( "Balan�a n�o inicializada!", Color.WHITE, Color.RED, true );
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void buscaParansBalanca() {
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		
		try {
			
			sql.append( "select first 1 driverbal, portabal, speedbal, bitsbal, stopbitbal, paritybal " );
			sql.append( "from sgestacaobal " );
			sql.append( "where codemp=? and codfilial=? and codest=? and tipoprocrecmerc=? " );
			
			ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, Aplicativo.iNumEst );
			ps.setString( 4, tipoprocrecmerc );
			
			rs = ps.executeQuery();
			
			
			if(rs.next()) {
				
				driverbal = rs.getString( "driverbal" );
				portabal = rs.getInt( "portabal" );
				baundrate = rs.getInt( "speedbal" );
				databits = rs.getInt( "bitsbal" );
				stopbits = rs.getInt( "stopbitbal" );
				parity = rs.getInt( "paritybal" );
				
				
			}
			else {
				setMensagem( "Balan�a n�o localizada!", Color.RED, null, false );
			}

			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void instanciaBalanca() {
				
		try {
			
			if(balanca!=null){
				balanca.inactivePort();
			}
		
			buscaParansBalanca();
			
			Class<AbstractScale> classbalanca = ( (Class<AbstractScale>) Class.forName( StringFunctions.alltrim( driverbal ) ));    						

			balanca = classbalanca.newInstance();
			
			setMensagem( "Balan�a " + balanca.getName(), Color.BLUE, null, false );				
			
			buscaPeso();
				
		}
		catch (Exception e) {
			setMensagem( "Erro ao instanciar driver... ", Color.RED, null, false );
			e.printStackTrace();		
		}
		
		
	}
	
	private void ajustaCampos() {
		
		txtData.setAtivo( false );
		txtHora.setAtivo( false );		
		
		txtPeso1.setAtivo( balanca==null );
		txtPeso2.setAtivo( balanca==null );
		
		txtPeso1.setFont( SwingParams.getFontboldextra(20) );
		txtPeso2.setFont( SwingParams.getFontboldextra(20) );
		txtData.setFont( SwingParams.getFontboldextra(10) );
		txtHora.setFont( SwingParams.getFontboldextra(10) );
		
	}
	
	public BigDecimal getPeso1() {
		return txtPeso1.getVlrBigDecimal();
	}

	public BigDecimal getPeso2() {
		return txtPeso2.getVlrBigDecimal();
	}
	
	public Date getData() {
		return txtData.getVlrDate();
	}
	
	public String getHora() {
		return txtHora.getVlrString();
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {				
				super.actionPerformed( evt );			
		}
		else if (evt.getSource() == btRefazer) {
			instanciaBalanca();
		}
		else {
			super.actionPerformed( evt );
		}
	}

	public void focusGained( FocusEvent fevt ) { }

	public void focusLost( FocusEvent fevt ) {

	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

	}

	public void beforeEdit( EditEvent eevt ) { }

	public void afterEdit( EditEvent eevt ) { }

	public void setConexao( DbConnection cn ) {
	
		super.setConexao( cn );
		
		instanciaBalanca();
		
		ajustaCampos();
	
	}
	

}
