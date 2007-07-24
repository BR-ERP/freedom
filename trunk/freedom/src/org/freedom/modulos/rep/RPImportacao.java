/**
 * @version 07/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPImportacao.java <BR>
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
 * Formulario de importa��o de dados do sistema Repwim para o modulo de representa��es do Freedom-ERP.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;
import org.freedom.telas.FFilho;
import org.freedom.telas.FObservacao;

public class RPImportacao extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JPanelPad panelImportacao = new JPanelPad();

	private final JPanelPad panelRodape = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JPanelPad panelSair = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JTextFieldPad txtDiretorio = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private final JCheckBoxPad cbRPVendedor = new JCheckBoxPad( "vendedores", "S", "N" );
	
	private final JCheckBoxPad cbRPFornecedor = new JCheckBoxPad( "fornecedores", "S", "N" );
	
	private final JCheckBoxPad cbRPTransportadora = new JCheckBoxPad( "transportadoras", "S", "N" );
	
	private final JCheckBoxPad cbRPProduto = new JCheckBoxPad( "produtos", "S", "N" );
	
	private final JCheckBoxPad cbRPTipocliente = new JCheckBoxPad( "tipos de cliente", "S", "N" );
	
	private final JCheckBoxPad cbRPCliente = new JCheckBoxPad( "clientes", "S", "N" );
	
	private final JCheckBoxPad cbRPPlanopagamento = new JCheckBoxPad( "planos de pagamento", "S", "N" );
	
	private final JCheckBoxPad cbRPPedido = new JCheckBoxPad( "pedidos", "S", "N" );

	private final JButton btConectar = new JButton( "Conectar" );

	private final JButton btImportar = new JButton( "Importar" );

	private final JButton btDirtorio = new JButton( "..." );

	private final JButton btLogError = new JButton( "log" );

	private final JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );

	private final JLabel status = new JLabel( "Selecione a base de dados ..." );
	
	private Connection conexaoparadox = null;
	
	private StringBuilder falhas = new StringBuilder();
	
	private long indexerro = 0;


	public RPImportacao() {

		super( false );
		setTitulo( "Importa��o de dados" );
		setAtribos( 100, 100, 420, 380 );

		montaTela();

		btConectar.addActionListener( this );
		btImportar.addActionListener( this );
		btDirtorio.addActionListener( this );
		btLogError.addActionListener( this );
		btSair.addActionListener( this );

		btConectar.setEnabled( false );
		btImportar.setEnabled( false );
		
		cbRPVendedor.setVlrString( "S" );
		cbRPProduto.setVlrString( "S" );
		cbRPTransportadora.setVlrString( "S" );
		cbRPFornecedor.setVlrString( "S" );
		cbRPTipocliente.setVlrString( "S" );
		cbRPCliente.setVlrString( "S" );
		cbRPPlanopagamento.setVlrString( "S" );
		cbRPPedido.setVlrString( "S" );
		
		status.setForeground( Color.BLUE );
	}

	private void montaTela() {

		getContentPane().setLayout( new BorderLayout() );

		panelImportacao.adic( new JLabel( "Local da base de dados" ), 10, 20, 350, 20 );
		panelImportacao.adic( txtDiretorio, 10, 40, 350, 20 );
		panelImportacao.adic( btDirtorio, 370, 38, 24, 24 );		
		
		JLabel tabelas = new JLabel( "Tabelas", SwingConstants.CENTER );
		tabelas.setOpaque( true );
		JLabel linha1 = new JLabel();
		linha1.setBorder( BorderFactory.createEtchedBorder() );
		
		panelImportacao.adic( tabelas, 30, 70, 80, 20 );
		panelImportacao.adic( linha1, 10, 80, 384, 180 );
		
		panelImportacao.adic( cbRPVendedor, 30, 90, 200, 20 );
		panelImportacao.adic( cbRPFornecedor, 30, 110, 200, 20 );
		panelImportacao.adic( cbRPProduto, 30, 130, 200, 20 );
		panelImportacao.adic( cbRPTransportadora, 30, 150, 200, 20);
		panelImportacao.adic( cbRPTipocliente, 30, 170, 200, 20 );
		panelImportacao.adic( cbRPCliente, 30, 190, 200, 20 );
		panelImportacao.adic( cbRPPlanopagamento, 30, 210, 200, 20 );
		panelImportacao.adic( cbRPPedido, 30, 230, 200, 20 );

		panelImportacao.adic( status, 10, 270, 330, 20 );
		
		panelImportacao.adic( btLogError, 340, 268, 54, 24 );

		btConectar.setPreferredSize( new Dimension( 120, 30 ) );
		btImportar.setPreferredSize( new Dimension( 120, 30 ) );

		panelRodape.setPreferredSize( new Dimension( 100, 44 ) );
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );

		panelBotoes.add( btConectar );
		panelBotoes.add( btImportar );

		panelSair.add( btSair );

		panelRodape.add( panelBotoes, BorderLayout.WEST );
		panelRodape.add( panelSair, BorderLayout.EAST );

		getContentPane().add( panelImportacao, BorderLayout.CENTER );
		getContentPane().add( panelRodape, BorderLayout.SOUTH );
	}

	private void getDiretorio() {

		try {

			FileDialog fileDialog = new FileDialog( Aplicativo.telaPrincipal, "Selecionar diretorio." );
			fileDialog.setVisible( true );

			if ( fileDialog.getDirectory() != null ) {

				txtDiretorio.setVlrString( fileDialog.getDirectory() );
				btConectar.setEnabled( true );
				btImportar.setEnabled( false );

				status.setText( "Conectar banco de dados ..." );
				btConectar.requestFocus();
			}
			else {

				txtDiretorio.setVlrString( "" );
				btConectar.setEnabled( false );
				btImportar.setEnabled( false );

				btDirtorio.requestFocus();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private Connection getConexaoparadox() {	
		return conexaoparadox;
	}

	
	private void setConexaoparadox( Connection conexaoparadox ) {	
		this.conexaoparadox = conexaoparadox;
	}

	private void conectar() {

		try {
			
			falhas = new StringBuilder();
			indexerro = 0;

			Login login = new Login( this );
			login.setVisible( true );

			if ( login.OK ) {

				String user = login.getUser();
				String password = login.getPassword();

				try {

					Connection conparadox = null;
					Properties props = new Properties();

					Class.forName( "com.hxtt.sql.paradox.ParadoxDriver" );
					
					props.put( "user", user );
					props.put( "password", password );

					conparadox = DriverManager.getConnection( "jdbc:paradox:/" + txtDiretorio.getVlrString().trim(), props );
					conparadox.setAutoCommit( false );
					
					setConexaoparadox( conparadox );
					status.setText( "Conex�o executada ..." );
					
					btImportar.setEnabled( true );
					btConectar.setEnabled( false );
					
				}
				catch ( ClassNotFoundException e ) {
					Funcoes.mensagemErro( this, "Driver n�o encontrado!\n" + e.getMessage() );
					e.printStackTrace();
				}
				catch ( java.sql.SQLException e ) {
					if ( e.getErrorCode() == 335544472 ) {
						Funcoes.mensagemErro( this, "Nome do usu�rio ou senha inv�lidos!" );
					}
					else {
						Funcoes.mensagemErro( this, "N�o foi poss�vel estabelecer conex�o com o banco de dados.\n" + e.getMessage() );
					}
					e.printStackTrace();
				}

			}

			login.dispose();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void importar() {
		
		int opt = Funcoes.mensagemConfirma( this, "Confirma importa��o dos dodos?" );
		
		if ( opt == JOptionPane.YES_OPTION ) {
			
			if ( "S".equals( cbRPVendedor.getVlrString() ) ) { 
				importarVendedor();
			}
			if ( "S".equals( cbRPFornecedor.getVlrString() ) ) {
				importarFornecedor();
			}
			if ( "S".equals( cbRPProduto.getVlrString() ) ) {
				importarProduto();
			}
			if ( "S".equals( cbRPTransportadora.getVlrString() ) ) {
				importarTransportadora();
			}
			if ( "S".equals( cbRPTipocliente.getVlrString() ) ) {
				importarTipoCliente();
			}
			if ( "S".equals( cbRPCliente.getVlrString() ) ) {
				importarCliente();
			}
			if ( "S".equals( cbRPPlanopagamento.getVlrString() ) ) {
				importarPlanopagamento();
			}
			if ( "S".equals( cbRPPedido.getVlrString() ) ) {
				importarPedidos();
			}
					
			status.setText( "Dados importados..." );
		}
	}
	
	private boolean executeGeneric( final String insert ) {
		
		boolean retorno = true;
		
		try {
			
			PreparedStatement ps = con.prepareStatement( insert );
			ps.execute();
				
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( Exception e ) {
			Funcoes.mensagemInforma( this, "Erro ao execurar script!\n" + e.getMessage() );
			e.printStackTrace();
			retorno = false;
		}
		
		return retorno;
	}
	
	private void importarGeneric( final String sql ) {
					
		Connection session = getConexaoparadox();
		List< String > dadosparadox = new ArrayList< String >();
		
		try {
		
			PreparedStatement ps = session.prepareStatement( sql );
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				dadosparadox.add( rs.getString( 1 ) );
			}
			
			if ( ! session.getAutoCommit() ) {
				session.commit();
			}
		}
		catch ( SQLException e ) {
			Funcoes.mensagemInforma( this, "Erro ao buscar dados!\n" + e.getMessage() );
			e.printStackTrace();
			return;
		}
		
		PreparedStatement psf = null;
		
		for ( String insert : dadosparadox ) {
			
			try {					
				psf = con.prepareStatement( insert );
				psf.execute();
				
				if ( ! con.getAutoCommit() ) {
					con.commit();
				}
			}
			catch ( SQLException e ) {
				indexerro++;
				System.out.println( "[" + indexerro + "]" + e.getMessage() );
				falhas.append( "[" + indexerro + "]" +insert + "\n" );
			}
		}
	}

	private void importarFornecedor() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "select 'INSERT INTO RPFORNECEDOR " );
		sql.append( "( CODEMP,CODFILIAL,CODFOR,RAZFOR,NOMEFOR,CNPJFOR,INSCFOR,ENDFOR,CIDFOR,ESTFOR," );
		sql.append( "CEPFOR,BAIRFOR,FONEFOR,FAXFOR,EMAILFOR,CODREPFOR ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RPFORNECEDOR" ) );
		sql.append( ",'||codfor||','||char(39)||razfor||char(39)||','||char(39)||nomefor||char(39)||','||" );
		sql.append( "char(39)||cgcfor||char(39)||','||char(39)||inscfor||char(39)||','||char(39)||endfor||" );
		sql.append( "char(39)||','||char(39)||cidfor||char(39)||','||char(39)||estfor||char(39)||','||" );
		sql.append( "char(39)||cepfor||char(39)||','||char(39)||bairfor||char(39)||','||char(39)||fonefor||" );
		sql.append( "char(39)||','||char(39)||faxfor||char(39)||','||char(39)||emailfor||char(39)||','||" );
		sql.append( "coalesce(codrepfor,'null')||')' from forneced" );
		
		status.setText( "Importando Fornecedores..." );
		
		importarGeneric( sql.toString() );
	}
	
	private void importarVendedor() {		
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "SELECT 'INSERT INTO RPVENDEDOR " );
		sql.append( "( CODEMP,CODFILIAL,CODVEND,NOMEVEND,ENDVEND,CIDVEND,BAIRVEND," );
		sql.append( "CEPVEND,ESTVEND,DDDVEND,FONEVEND,FAXVEND,PERCCOMIS,EMAILVEND ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RPVENDEDOR" ) );
		sql.append( ",'||Codvend||','||char(39)||Nomevend||char(39)||','||" );
		sql.append( "char(39)||Endvend||char(39)||','||char(39)||Cidvend||char(39)||','||" );
		sql.append( "char(39)||Bairvend||char(39)||','||char(39)||Cepvend||char(39)||','||" );
		sql.append( "char(39)||Estvend||char(39)||','||'NULL'||','||char(39)||Fonevend||" );
		sql.append( "char(39)||','||char(39)||Faxvend||char(39)||','||COALESCE(Perccomvend,'NULL')||','||" );
		sql.append( "char(39)||Emailvend||char(39)||' )' FROM VENDEDOR" );
		
		status.setText( "Importando Vendedores..." );
		
		importarGeneric( sql.toString() );
	}
	
	private void importarProduto() {
		
		status.setText( "Importando Produtos..." );
		
		StringBuilder insProduto = new StringBuilder();		
		
		insProduto.append( "INSERT INTO RPGRUPO ( CODEMP,CODFILIAL,CODGRUP,DESCGRUP,NIVELGRUP ) VALUES ( " );
		insProduto.append( AplicativoRep.iCodEmp + "," );
		insProduto.append( ListaCampos.getMasterFilial( "RPGRUPO" ) + "," );
		insProduto.append( "'0001','GRUPO DE PRODUTOS', 1 )" );
		
		executeGeneric( insProduto.toString() );
		
		StringBuilder insUnidade = new StringBuilder();		
		
		insUnidade.append( "INSERT INTO RPUNIDADE ( CODEMP,CODFILIAL,CODUNID,DESCUNID ) VALUES ( " );
		insUnidade.append( AplicativoRep.iCodEmp + "," );
		insUnidade.append( ListaCampos.getMasterFilial( "RPUNIDADE" ) + "," );
		insUnidade.append( "'UN','UNIDADE' )" );
		
		executeGeneric( insUnidade.toString() );
		
		StringBuilder sql = null;
		
		for ( int i=1; i <=100; i++) {
			
			sql = new StringBuilder();		
			
			sql.append( "SELECT 'INSERT INTO RPPRODUTO " );
			sql.append( "( CODEMP,CODFILIAL,CODPROD,DESCPROD,REFPROD,CODBARPROD,CODEMPGP,CODFILIALGP,CODGRUP,CODEMPUD,"  );
			sql.append( "CODFILIALUD,CODUNID,CODEMPFO,CODFILIALFO,CODFOR,REFPRODFOR,PESOLIQPROD,PESOBRUTPROD,COMISPROD,"  );
			sql.append( "PERCIPIPROD,PRECOPROD1,PRECOPROD2,PRECOPROD3 ) VALUES ( " );
			sql.append( Aplicativo.iCodEmp );
			sql.append( "," );
			sql.append( ListaCampos.getMasterFilial( "RPFORNECEDOR" ) );
			sql.append( ",'||CODPROD||','||CHAR(39)||DESCPROD||CHAR(39)||','||CHAR(39)||CODPROD||CHAR(39)||','||" );
			sql.append( "CHAR(39)||CODBARPROD||CHAR(39)||','||" );
			sql.append( Aplicativo.iCodEmp );
			sql.append( "||','||" );
			sql.append( ListaCampos.getMasterFilial( "RPPRODUTO" ) );
			sql.append( "||','||CHAR(39)||'0001'||CHAR(39)||','||" );
			sql.append( Aplicativo.iCodEmp );
			sql.append( "||','||" );
			sql.append( ListaCampos.getMasterFilial( "RPPRODUTO" ) );
			sql.append( "||','||CHAR(39)||'UN'||CHAR(39)||','||" );
			sql.append( Aplicativo.iCodEmp );
			sql.append( "||','||" );
			sql.append( ListaCampos.getMasterFilial( "RRPRODUTO" ) );
			sql.append( "||','||CODFOR||','||CHAR(39)||CODFORPROD||CHAR(39)||','||COALESCE(PESOPROD,'NULL')||','||COALESCE(PESOPROD,'NULL')||','||" );
			sql.append( "COALESCE(COMISPROD,'NULL')||','||COALESCE(PERCIPIPROD,'NULL')||','||COALESCE(PRECOPROD,'NULL')||','||" );
			sql.append( "COALESCE(PRECOPROD2,'NULL')||','||COALESCE(PRECOPROD3,'NULL')||' )' FROM PRODUTO WHERE CODPROD>0 AND CODPROD<" + (i*1000) );
			
			importarGeneric( sql.toString() );
		}
	}

	private void importarTransportadora() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "select ' INSERT INTO RPTRANSP " );
		sql.append( "( CODEMP,CODFILIAL,CODTRAN,RAZTRAN,NOMETRAN,CNPJTRAN,INSCTRAN,ENDTRAN," );
		sql.append( "CIDTRAN,ESTTRAN,CEPTRAN,BAIRTRAN,FONETRAN,FAXTRAN ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RPTRANSP" ) );
		sql.append( ",'||codtransp||','||char(39)||nometransp||char(39)||','||char(39)||nometransp||" );
		sql.append( "char(39)||','||char(39)||cnpjtransp||char(39)||','||char(39)||insctransp||" );
		sql.append( "char(39)||','||char(39)||endtransp||char(39)||','||char(39)||cidtransp||" );
		sql.append( "char(39)||','||char(39)||esttransp||char(39)||','||char(39)||ceptransp||" );
		sql.append( "char(39)||','||char(39)||bairtransp||char(39)||','||char(39)||fonetransp||" );
		sql.append( "char(39)||','||char(39)||faxtransp||char(39)||')' from transp" );
		
		status.setText( "Importando Tramsportadoras..." );
		
		importarGeneric( sql.toString() );
	}
	
	private void importarTipoCliente() {

		StringBuilder sql = new StringBuilder();
		
		sql.append( "SELECT 'INSERT INTO RPTIPOCLI ( CODEMP,CODFILIAL,CODTIPOCLI,DESCTIPOCLI,TIPOCLI ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RPTIPOCLI" ) );
		sql.append( ",'||CODTIPOCLI||','||char(39)||DESCTIPOCLI||char(39)||','||char(39)||TIPOCLI||char(39)||' )' FROM TIPOCLI" );
		
		status.setText( "Importando Tipos de Clientes..." );
		
		importarGeneric( sql.toString() );
	}
	
	private void importarCliente() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "SELECT 'INSERT INTO RPCLIENTE ( CODEMP,CODFILIAL,CODCLI,RAZCLI,NOMECLI,CODEMPVO,CODFILIALVO,CODVEND," );
		sql.append( "CODEMPTC,CODFILIALTC,CODTIPOCLI,CNPJCLI,INSCCLI,ENDCLI,CIDCLI,ESTCLI,CEPCLI,BAIRCLI,FONECLI,FAXCLI," );
		sql.append( "EMAILCLI,ENDCOBCLI,BAIRCOBCLI,CIDCOBCLI,ESTCOBCLI,CEPCOBCLI,ENDENTCLI,BAIRENTCLI,CIDENTCLI,CEPENTCLI," );
		sql.append( "ESTENTCLI,INSCENTCLI,CNPJENTCLI,ATIVCLI ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RRCLIENTE" ) );
		sql.append( "'||','||CODCLI||','||char(39)||RAZCLI||char(39)||','||char(39)||NOMECLI||char(39)||','||'" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "'||','||" );
		sql.append( ListaCampos.getMasterFilial( "RPCLIENTE" ) );
		sql.append( "||','||CODVEND||','||'" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "'||','||" );
		sql.append( ListaCampos.getMasterFilial( "RPCLIENTE" ) );
		sql.append( "||','||CODTIPOCLI||','||char(39)||CGCCLI||char(39)||','||char(39)||INSCCLI||char(39)||','||char(39)||ENDCLI||" );
		sql.append( "char(39)||','||char(39)||CIDCLI||char(39)||','||char(39)||ESTCLI||char(39)||','||char(39)||CEPCLI||char(39)||','||" );
		sql.append( "char(39)||BAIRCLI||char(39)||','||char(39)||FONECLI||char(39)||','||char(39)||FAXCLI||char(39)||','||char(39)||" );
		sql.append( "EMAILCLI||char(39)||','||char(39)||ENDCOBCLI||char(39)||','||char(39)||BAIRCOBCLI||char(39)||','||char(39)||" );
		sql.append( "CIDCOBCLI||char(39)||','||char(39)||ESTCOBCLI||char(39)||','||char(39)||CEPCOBCLI||char(39)||','||char(39)||" );
		sql.append( "ENDENTCLI||char(39)||','||char(39)||BAIRENTCLI||char(39)||','||char(39)||CIDENTCLI||char(39)||','||char(39)||" );
		sql.append( "CEPENTCLI||char(39)||','||char(39)||ESTENTCLI||char(39)||','||char(39)||INSCENTCLI||char(39)||','||char(39)||" );
		sql.append( "CGCENTCLI||char(39)||','||char(39)||'S'||char(39)|| ')' FROM CLIENTE" );
		
		status.setText( "Importando Clientes..." );
		
		importarGeneric( sql.toString() );
	}
	
	private void importarPlanopagamento() {
		
		status.setText( "Importando Planos de pagamento..." );

		StringBuilder sql = new StringBuilder();
		
		sql.append( "select 'insert into RPPLANOPAG ( CODEMP,CODFILIAL,CODPLANOPAG,DESCPLANOPAG,PARCPLANOPAG )" );
		sql.append( "values ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RPPARCPLANOPAG" ) );
		sql.append( ",' || p.Codplanopag || ',' || char(39) || p.Descplanopag || char(39) ||" ); 
		sql.append( "',' || (select count(i.coditplano) from itplano i where i.codplanopag=p.codplanopag ) ||" );
		sql.append( "' )' from planopag p" );
				
		importarGeneric( sql.toString() );
		
		StringBuilder sqlparc = new StringBuilder();
		
		sqlparc.append( "SELECT 'INSERT INTO RPPARCPLANOPAG " );
		sqlparc.append( "( CODEMP,CODFILIAL,CODPLANOPAG,NROPARCPAG,DIASPAG,PERCPAG,JUROSPARCPAG ) " );
		sqlparc.append( "VALUES ( " );
		sqlparc.append( Aplicativo.iCodEmp );
		sqlparc.append( "," );
		sqlparc.append( ListaCampos.getMasterFilial( "RPPARCPLANOPAG" ) );
		sqlparc.append( ",'||Codplanopag||','||Coditplano||','|| coalesce(Diasitplano,0)||','||coalesce(Percitplano,0)||','||coalesce(Jurositplano,0)||' )'" );
		sqlparc.append( "FROM ITPLANO" );
		
		importarGeneric( sqlparc.toString() );
	}
	
	private void importarPedidos() {
		
		status.setText( "Importando Pedido..." );
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "select 'insert into RPPEDIDO ( " );
		sql.append( "CODEMP,CODFILIAL,CODPED,DATAPED,CODCLI,CODEMPCL,CODFILIALCL,CODVEND,CODEMPVD,CODFILIALVD," );
		sql.append( "CODPLANOPAG,CODEMPPG,CODFILIALPG,CODMOEDA,CODEMPMO,CODFILIALMO,CODFOR,CODEMPFO,CODFILIALFO," );
		sql.append( "CODTRAN,CODEMPTP,CODFILIALTP,TIPOFRETEPED,TIPOREMPED,NUMPEDCLI,NUMPEDFOR,OBSPED )" );
		sql.append( "values ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RPPEDIDO" ) );
		sql.append( ",'||Codped||','||" );
		sql.append( "char(39)||extract(year FROM dataped)||'-'||extract(month FROM dataped)||'-'||extract(day FROM dataped)||char(39)" );
		sql.append( "||','||Codcli||','||" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "||','||" );
		sql.append( ListaCampos.getMasterFilial( "RPCVENDEDOR" ) );
		sql.append( "||','||Codvend||','||" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "||','||" );
		sql.append( ListaCampos.getMasterFilial( "RPPLANOPAG" ) );
		sql.append( "||','||" );
		sql.append( "Codplanopag||','||" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "||','||" );
		sql.append( ListaCampos.getMasterFilial( "RPMOEDA" ) );
		sql.append( "||','||" );
		sql.append( "char(39)||'R$'||char(39)||','||" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "||','||" );
		sql.append( ListaCampos.getMasterFilial( "RPMOEDA" ) );
		sql.append( "||','||" );
		sql.append( "Codfor||','||" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "||','||" );
		sql.append( ListaCampos.getMasterFilial( "RPFORNECEDOR" ) );
		sql.append( "||','||" );
		sql.append( "(case when CodTransp is not null then CodTransp else 'null' end)||','||" );
		sql.append( "(case when CodTransp is not null then " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( " else 'null' end)||','||" );
		sql.append( "(case when CodTransp is not null then " );
		sql.append( ListaCampos.getMasterFilial( "RPTRANSP" ) );
		sql.append( " else 'null' end)||','||" );
		sql.append( "char(39)||Tiporemped||char(39)||','||char(39)||Tipofreteped||char(39)||','||" );
		sql.append( "coalesce(Numcliped,0)||','||coalesce(Numforped,0)||','||char(39)||coalesce(Obs,'')||char(39)||')' from pedido" );
		
		importarGeneric( sql.toString() );
		
		StringBuilder sqlitem = new StringBuilder();
		
		sqlitem.append( "select 'insert into RPITPEDIDO (CODEMP,CODFILIAL,CODPED,CODITPED,CODPROD,CODFILIALPD,CODEMPPD,QTDITPED,PRECOITPED" );
		sqlitem.append( ",VLRITPED,VLRLIQITPED,PERCIPIITPED,VLRIPIITPED,PERCDESCITPED,VLRDESCITPED,PERCADICITPED" );
		sqlitem.append( ",VLRADICITPED,PERCRECITPED,VLRRECITPED,PERCPAGITPED,VLRPAGITPED) values ( " );
		sqlitem.append( Aplicativo.iCodEmp );
		sqlitem.append( "," );
		sqlitem.append( ListaCampos.getMasterFilial( "RPPEDIDO" ) );
		sqlitem.append( ",'||Codped||','||Coditped||','||Codprod||','||" );
		sqlitem.append( Aplicativo.iCodEmp );
		sqlitem.append( "||','||" );
		sqlitem.append( ListaCampos.getMasterFilial( "RPMOEDA" ) );
		sqlitem.append( "||','||" );
		sqlitem.append( "coalesce(Qtditped,0)||','||coalesce(Precoitped,0)||','||" );
		sqlitem.append( "(coalesce(Qtditped,0)*coalesce(Precoitped,0))||','||" );
		sqlitem.append( "((coalesce(Qtditped,0)*coalesce(Precoitped,0))+coalesce(Valipiitped,0)-coalesce(Valdescitped,0)+coalesce(Valadicitped,0))||','||" ); 
		sqlitem.append( "coalesce(Percipiitped,0)||','||coalesce(Valipiitped,0)||','||" );
		sqlitem.append( "coalesce(Percdescitped,0)||','||coalesce(Valdescitped,0)||','||" );
		sqlitem.append( "coalesce(Percadicitped,0)||','||coalesce(Valadicitped,0)||','||" );
		sqlitem.append( "coalesce(Perccomritped,0)||','||coalesce(Valcomritped,0)||','||" );
		sqlitem.append( "coalesce(Perccompitped,0)||','||coalesce(Valcompitped,0)||')' from ITPED" );
		
		importarGeneric( sqlitem.toString() );
	}
	
	private void getLogError() {
		
		FObservacao log = new FObservacao( "Scripts n�o executados...", falhas.toString() );
		log.setAtribos( 500, 400 );
		log.setVisible( true );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btConectar ) {
			conectar();
		}
		else if ( evt.getSource() == btImportar ) {			
			importar();
		}
		else if ( evt.getSource() == btDirtorio ) {
			getDiretorio();
		}
		else if ( evt.getSource() == btLogError ) {
			getLogError();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
	}

	private class Login extends FFDialogo {

		private static final long serialVersionUID = 1l;

		private final JTextFieldPad txtUser = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

		private final JPasswordFieldPad txtPassword = new JPasswordFieldPad( 20 );

		Login( final Component cOrig ) {

			super( cOrig );
			setTitulo( "Login" );
			setAtribos( 250, 160 );

			adic( new JLabelPad( "Usu�rio: ", SwingConstants.RIGHT ), 10, 15, 65, 20 );
			adic( txtUser, 80, 15, 130, 20 );
			adic( new JLabelPad( "Senha: ", SwingConstants.RIGHT ), 10, 40, 65, 20 );
			adic( txtPassword, 80, 40, 130, 20 );
		}

		String getUser() {

			return txtUser.getVlrString();
		}

		String getPassword() {

			return txtPassword.getVlrString();
		}
	}
}
