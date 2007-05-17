/**
 * @version 05/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)DLEnviaPedido.java <BR>
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
 * Formul�rio para envio do pedido por e-mail.
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.freedom.acao.Processo;
import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ProcessoSec;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.rep.RPPrefereGeral.EPrefere;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLEnviarPedido extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelRodape = null;

	private final JTextFieldPad txtFrom = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtTo = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtHost = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtUser = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextAreaPad txtMessage = new JTextAreaPad( 1000 );

	private final JButton btEnviar = new JButton( "Enviar", Icone.novo( "btEnviarMail.gif" ) );

	private final JProgressBar progress = new JProgressBar();

	private final JLabel status = new JLabel();

	private JasperPrint pedido = null;

	private List<Object> prefere = null;

	private String titulo = "";

	public DLEnviarPedido( final Component cOrig ) {

		super( cOrig );
		setTitulo( "Enviar Pedido" );
		setAtribos( 405, 330 );
		setResizable( false );

		montaTela();

		progress.setMaximum( 10 );
		progress.setStringPainted( true );
		btEnviar.addActionListener( this );
	}

	private void montaTela() {

		adic( new JLabel( "Para:" ), 10, 10, 370, 20 );
		adic( txtFrom, 10, 30, 370, 20 );
		adic( new JLabel( "Menssagem:" ), 10, 50, 370, 20 );
		adic( new JScrollPane( txtMessage ), 10, 70, 370, 100 );
		adic( new JLabel( "Usuario" ), 10, 170, 370, 20 );
		adic( txtUser, 10, 190, 370, 20 );
		adic( progress, 10, 220, 370, 20 );
		adic( status, 10, 240, 370, 20 );

		status.setForeground( Color.BLUE );

		panelRodape = adicBotaoSair();

		btEnviar.setPreferredSize( new Dimension( 100, 26 ) );
		panelRodape.add( btEnviar, BorderLayout.WEST );
	}

	public void setPedido( final JasperPrint pedido ) {

		this.pedido = pedido;
	}

	private void setStatus( final String msg ) {

		status.setText( msg != null ? msg : "" );
	}

	public void preparar( final String titulo, final int codcli ) {

		this.titulo = titulo;
		txtHost.setVlrString( (String) prefere.get( EPrefere.SERVIDORSMTP.ordinal() ) );
		txtUser.setVlrString( (String) prefere.get( EPrefere.USUARIOSMTP.ordinal() ) );
		txtTo.setVlrString( getEmailEmp() );
		txtFrom.setVlrString( getEmailCli( codcli ) );
	}

	private String getEmailEmp() {

		String email = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();

		try {

			sSQL.append( "SELECT EMAILFILIAL FROM SGFILIAL WHERE CODEMP=? AND CODFILIAL=?" );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			rs = ps.executeQuery();

			if ( rs.next() ) {

				email = rs.getString( "EMAILFILIAL" ) != null ? rs.getString( "EMAILFILIAL" ).trim() : null;
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao buscar email da filial!\n" + e.getMessage() );
			e.printStackTrace();
		}

		return email;
	}

	private String getEmailCli( final int codcli ) {

		String email = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();

		try {

			sSQL.append( "SELECT EMAILCLI FROM RPCLIENTE WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=?" );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			ps.setInt( 3, codcli );
			rs = ps.executeQuery();

			if ( rs.next() ) {

				email = rs.getString( "EMAILCLI" ) != null ? rs.getString( "EMAILCLI" ).trim() : "";
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao buscar email do cliente!\n" + e.getMessage() );
			e.printStackTrace();
		}

		return email;
	}

	private boolean validaEnviar() {

		boolean retorno = false;

		validar : {

			if ( txtHost.getVlrString() == null || txtHost.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Servidor SMTP inv�lido!\nVerifique as prefer�ncias do sistema." );
				dispose();
				break validar;
			}
			if ( txtTo.getVlrString() == null || txtTo.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Email da filial inv�lido!\nVerifique o cadastro da filial." );
				dispose();
				break validar;
			}
			if ( txtFrom.getVlrString() == null || txtFrom.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "E-mail n�o informado!" );
				break validar;
			}
			if ( txtUser.getVlrString() == null || txtUser.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Usu�rio n�o informado!" );
				break validar;
			}

			retorno = true;
		}

		return retorno;
	}

	private void enviar() {

		if ( validaEnviar() ) {

			int progresso = 0;
			
			try {

				setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );

				progress.setValue( progresso++ );
				setStatus( "Preparando o e-mail..." );

				Properties props = new Properties();
				props.put( "mail.smtp.host", txtHost.getVlrString() );

				progress.setValue( progresso++ );
				Session session = Session.getDefaultInstance( props );

				// cria a mensagem
				progress.setValue( progresso++ );
				MimeMessage msg = new MimeMessage( session );
				msg.setFrom( new InternetAddress( txtFrom.getVlrString() ) );

				progress.setValue( progresso++ );
				InternetAddress[] address = { new InternetAddress( txtTo.getVlrString() ) };
				msg.setRecipients( Message.RecipientType.TO, address );
				msg.setSubject( titulo );

				// cria a primeira parte da mensagem
				progress.setValue( progresso++ );
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setText( txtMessage.getVlrString() );

				// cria a segunda parte da mensage
				progress.setValue( progresso++ );
				MimeBodyPart mbp2 = new MimeBodyPart();

				// cria o arquivo
				progress.setValue( progresso++ );
				setStatus( "Criando arquivo de anexo em " + Aplicativo.strTemp );

				String filename = Aplicativo.strTemp + "pedido" + Calendar.getInstance().getTimeInMillis() + ".pdf";
				JasperExportManager.exportReportToPdfFile( pedido, filename );
				File file = new File( filename );

				if ( !file.exists() ) {
					return;
				}

				// anexa o arquivo na mensagem
				progress.setValue( progresso++ );
				setStatus( "Anexando arquivo..." );

				FileDataSource fds = new FileDataSource( filename );
				mbp2.setDataHandler( new DataHandler( fds ) );
				mbp2.setFileName( fds.getName() );

				// cria a Multipart
				progress.setValue( progresso++ );
				Multipart mp = new MimeMultipart();
				mp.addBodyPart( mbp1 );
				mp.addBodyPart( mbp2 );

				// adiciona a Multipart na mensagem
				msg.setContent( mp );

				// configura a data: cabecalho
				msg.setSentDate( Calendar.getInstance().getTime() );

				// envia a mensagem
				progress.setValue( progresso++ );
				setStatus( "Enviando e-mail..." );
				Transport.send( msg );

				setCursor( Cursor.getDefaultCursor() );

				progress.setValue( progresso++ );
				Funcoes.mensagemInforma( this, "E-mail enviado com sucesso." );
				
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao enviar pedido!\n" + e.getMessage(), true, con, e );
				e.printStackTrace();
			}
			
			setStatus( null );
		}
	}

	@ Override
	public void actionPerformed( ActionEvent e ) {

		super.actionPerformed( e );

		if ( e.getSource() == btEnviar ) {

			ProcessoSec pSec = new ProcessoSec( 500, 
				new Processo() {
					public void run() {
						status.updateUI();
						progress.updateUI();
					}
				}, 
				new Processo() {
					public void run() {
						enviar();
					}
				} 
			);
			
			pSec.iniciar();
		}
	}

	@ Override
	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		prefere = RPPrefereGeral.getPrefere( cn );
	}
}
