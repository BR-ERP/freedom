/**
 * @version 14/03/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRetFBN.java <BR>
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
 * Tela de leitura do arquivo de retorno.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.fnc.FbnUtil.EColcli;
import org.freedom.modulos.fnc.FbnUtil.EParcela;
import org.freedom.modulos.fnc.FbnUtil.StuffCli;
import org.freedom.modulos.fnc.FbnUtil.StuffParcela;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public abstract class FRetFBN extends FFilho implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	private final String TIPO_FEBRABAN;

	private JPanelPad panelRodape = null;

	private final JPanelPad panelRemessa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFiltros = new JPanelPad();

	private final JPanelPad panelTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFuncoes = new JPanelPad();

	private final JPanelPad panelStatus = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	protected final Tabela tab = new Tabela();

	protected final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	protected final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	protected final JButtonPad btImporta = new JButtonPad( "Importar", Icone.novo( "btSalvar.gif" ) );

	protected final JButtonPad btSelTudo = new JButtonPad( Icone.novo( "btTudo.gif" ) );

	protected final JButtonPad btSelNada = new JButtonPad( Icone.novo( "btNada.gif" ) );

	protected final JButtonPad btEdita = new JButtonPad( Icone.novo( "btEditar.gif" ) );

	protected final JButtonPad btBaixar = new JButtonPad( "Aplicar baixa", Icone.novo( "btGerar.gif" ) );

	protected final JLabel lbStatus = new JLabel();
	
	protected final ImageIcon imgRejEntrada = Icone.novo( "clRejEntrada.gif" );
	
	protected final ImageIcon imgRejBaixa = Icone.novo( "clRejBaixa.gif" );
	
	protected final ImageIcon imgAdvert = Icone.novo( "clAdvertencia.gif" );
	
	protected final ImageIcon imgConfEntrada = Icone.novo( "clConfEntrada.gif" );
	
	protected final ImageIcon imgConfBaixa = Icone.novo( "clConfBaixa.gif" );
	
	protected final ImageIcon imgIndefinido = Icone.novo( "clIndefinido.gif" );

	protected final ListaCampos lcBanco = new ListaCampos( this );
	

	public FRetFBN( final String tipoFebraban ) {

		super( false );
		setTitulo( "Leitura do arquivo de retorno" );
		setAtribos( 10, 10, 780, 540 );
		
		this.TIPO_FEBRABAN = tipoFebraban;

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setListaCampos( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setRequerido( true );
		txtNomeBanco.setListaCampos( lcBanco );

		montaTela();

		tab.adicColuna( "" );
		tab.adicColuna( "" );
		tab.adicColuna( "Raz�o social do cliente" );
		tab.adicColuna( "C�d.cli." );
		tab.adicColuna( "C�d.rec." );
		tab.adicColuna( "Doc" );
		tab.adicColuna( "Nro.Parc." );
		tab.adicColuna( "Valor" );
		tab.adicColuna( "Emiss�o" );
		tab.adicColuna( "Vencimento" );
		tab.adicColuna( "Valor pago" );
		tab.adicColuna( "Data pgto." );
		tab.adicColuna( "Nro.conta" );
		tab.adicColuna( "C�d.planej." );
		tab.adicColuna( "Valor desc." );
		tab.adicColuna( "Valor juros" );
		tab.adicColuna( "C�d.c.c." );
		tab.adicColuna( "Hist�rico" );
		tab.adicColuna( "Tp" );
		tab.adicColuna( "C�d.Retorno" );
		tab.adicColuna( "Menssagem de retorno" );
		
			
		tab.setTamColuna( 22, EColTab.STATUS.ordinal() );
		tab.setTamColuna( 20, EColTab.SEL.ordinal() );
		tab.setTamColuna( 250, EColTab.RAZCLI.ordinal() );
		tab.setTamColuna( 70, EColTab.CODCLI.ordinal() );
		tab.setTamColuna( 70, EColTab.CODREC.ordinal() );
		tab.setTamColuna( 80, EColTab.DOCREC.ordinal() );
		tab.setTamColuna( 70, EColTab.NRPARC.ordinal() );
		tab.setTamColuna( 70, EColTab.VLRAPAG.ordinal() );
		tab.setTamColuna( 70, EColTab.DTREC.ordinal() );
		tab.setTamColuna( 70, EColTab.DTVENC.ordinal() );
		tab.setTamColuna( 70, EColTab.VLRPAG.ordinal() );
		tab.setTamColuna( 70, EColTab.DTPAG.ordinal() );
		tab.setTamColuna( 70, EColTab.NUMCONTA.ordinal() );
		tab.setTamColuna( 100, EColTab.CODPLAN.ordinal() );
		tab.setTamColuna( 70, EColTab.VLRDESC.ordinal() );
		tab.setTamColuna( 70, EColTab.VLRJUROS.ordinal() );
		tab.setTamColuna( 70, EColTab.CODCC.ordinal() );
		tab.setTamColuna( 200, EColTab.OBS.ordinal() );
		tab.setTamColuna( 200, EColTab.TIPOFEBRABAN.ordinal() );
		tab.setTamColuna( 100, EColTab.CODRET.ordinal() );
		tab.setTamColuna( 250, EColTab.MENSSAGEM.ordinal() );

		tab.setColunaEditavel( EColTab.SEL.ordinal(), true );

		btSelTudo.addActionListener( this );
		btSelNada.addActionListener( this );
		btImporta.addActionListener( this );
		btEdita.addActionListener( this );
		btBaixar.addActionListener( this );
		btImporta.addKeyListener( this );
		tab.addMouseListener( this );

		btSelTudo.setToolTipText( "Selecionar tudo" );
		btSelNada.setToolTipText( "Limpar sele��o" );
		btEdita.setToolTipText( "Editar" );
		btBaixar.setToolTipText( "Aplicar baixa" );
	}

	private void montaTela() {

		pnCliente.add( panelRemessa, BorderLayout.CENTER );

		panelRemessa.add( panelFiltros, BorderLayout.NORTH );
		panelRemessa.add( panelTabela, BorderLayout.CENTER );
		panelRemessa.add( panelStatus, BorderLayout.SOUTH );

		panelFiltros.setPreferredSize( new Dimension( 300, 70 ) );
		panelFiltros.adic( new JLabel( "C�d.banco" ), 7, 10, 90, 20 );
		panelFiltros.adic( txtCodBanco, 7, 30, 90, 20 );
		panelFiltros.adic( new JLabel( "Nome do banco" ), 100, 10, 300, 20 );
		panelFiltros.adic( txtNomeBanco, 100, 30, 300, 20 );
		panelFiltros.adic( btImporta, 500, 25, 150, 30 );

		panelTabela.add( new JScrollPane( tab ), BorderLayout.CENTER );
		panelTabela.add( panelFuncoes, BorderLayout.EAST );

		panelFuncoes.setPreferredSize( new Dimension( 45, 50 ) );
		panelFuncoes.adic( btSelTudo, 5, 5, 30, 30 );
		panelFuncoes.adic( btSelNada, 5, 40, 30, 30 );
		panelFuncoes.adic( btEdita, 5, 75, 30, 30 );

		lbStatus.setForeground( Color.BLUE );

		panelStatus.setPreferredSize( new Dimension( 600, 30 ) );
		panelStatus.add( lbStatus, BorderLayout.WEST );

		panelRodape = adicBotaoSair();
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		panelRodape.setPreferredSize( new Dimension( 600, 32 ) );

		btBaixar.setPreferredSize( new Dimension( 150, 32 ) );
		panelRodape.add( btBaixar, BorderLayout.WEST );

	}

	private void selecionaTudo() {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, EColTab.SEL.ordinal() );
			
		}
	}

	private void selecionaNada() {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( false ), i, EColTab.SEL.ordinal() );
		}
	}

	protected abstract boolean execImportar();

	protected void edit() {

		DLBaixaRec dl = null;
		int row = tab.getLinhaSel();

		if ( row > -1 && row < tab.getRowCount() ) {
			
			if ( FPrefereFBB.TP_SIACC.equals( TIPO_FEBRABAN ) ) {
				if ( ! "00".equals( tab.getValor( row, EColTab.CODRET.ordinal() ) ) ) {
					Funcoes.mensagemInforma( this, "Registro rejeitado!\n" + tab.getValor( row, EColTab.MENSSAGEM.ordinal() ) );
					return;
				}				
			}
			
			dl = new DLBaixaRec( this );
			DLBaixaRec.BaixaRecBean baixaRecBean = dl.new BaixaRecBean();

			baixaRecBean.setCliente( (Integer)tab.getValor( row, EColTab.CODCLI.ordinal() ) );
			baixaRecBean.setRazaoSocialCliente( (String)tab.getValor( row, EColTab.RAZCLI.ordinal() ) );
			baixaRecBean.setConta( (String)tab.getValor( row, EColTab.NUMCONTA.ordinal() ) );
			baixaRecBean.setPlanejamento( (String)tab.getValor( row, EColTab.CODPLAN.ordinal() ) );
			baixaRecBean.setCentroCusto( (String)tab.getValor( row, EColTab.CODCC.ordinal() ) );
			baixaRecBean.setDocumento( (String)tab.getValor( row, EColTab.DOCREC.ordinal() ) );
			baixaRecBean.setDataEmissao( Funcoes.strDateToDate( (String)tab.getValor( row, EColTab.DTREC.ordinal() ) ) );
			baixaRecBean.setDataVencimento( Funcoes.strDateToDate( (String)tab.getValor( row, EColTab.DTVENC.ordinal() ) ) );
			baixaRecBean.setDataPagamento( Funcoes.strDateToDate( (String)tab.getValor( row, EColTab.DTPAG.ordinal() ) ) );
			baixaRecBean.setValorParcela( Funcoes.strToBd( tab.getValor( row, EColTab.VLRAPAG.ordinal() ) ) );//xxx???
			baixaRecBean.setValorAPagar( Funcoes.strToBd( tab.getValor( row, EColTab.VLRAPAG.ordinal() ) ) );
			baixaRecBean.setValorDesconto( Funcoes.strToBd( tab.getValor( row, EColTab.VLRDESC.ordinal() ) ) );
			baixaRecBean.setValorJuros( Funcoes.strToBd( tab.getValor( row, EColTab.VLRJUROS.ordinal() ) ) );
			baixaRecBean.setValorPago( Funcoes.strToBd( tab.getValor( row, EColTab.VLRPAG.ordinal() ) ) );
			baixaRecBean.setObservacao( (String)tab.getValor( row, EColTab.OBS.ordinal() ) );

			dl.setConexao( con );
			dl.setValores( baixaRecBean );
			
			dl.setVisible( true );

			if ( dl.OK ) {

				baixaRecBean = dl.getValores();

				//atualizaTabCli( (Integer) sVals[ DLBaixaRec.EColBaixa.CODCLI.ordinal() ], baixaRecBean );

				tab.setValor( new Boolean( Boolean.TRUE ), row, EColTab.SEL.ordinal() );
				tab.setValor( baixaRecBean.getConta(), row, EColTab.NUMCONTA.ordinal() );
				tab.setValor( baixaRecBean.getPlanejamento(), row, EColTab.CODPLAN.ordinal() );
				tab.setValor( baixaRecBean.getDocumento(), row, EColTab.DOCREC.ordinal() );
				tab.setValor( Funcoes.dateToStrDate( baixaRecBean.getDataPagamento() ), row, EColTab.DTPAG.ordinal())  ;
				tab.setValor( Funcoes.bdToStr( baixaRecBean.getValorPago() ), row, EColTab.VLRPAG.ordinal() );
				tab.setValor( Funcoes.bdToStr( baixaRecBean.getValorDesconto() ), row, EColTab.VLRDESC.ordinal() );
				tab.setValor( Funcoes.bdToStr( baixaRecBean.getValorJuros() ), row, EColTab.VLRJUROS.ordinal() );
				tab.setValor( baixaRecBean.getCentroCusto(), row, EColTab.CODCC.ordinal() );
				tab.setValor( baixaRecBean.getObservacao(), row, EColTab.OBS.ordinal() );
			}
		}
		else {
			Funcoes.mensagemInforma( this, "Selecione uma linha na lista!" );
		}
	}

	protected void atualizaTabCli( final int codcli, final DLBaixaRec.BaixaRecBean baixaRecBean ) {

		BigDecimal vlrpago = new BigDecimal( 0 );
		BigDecimal vlrdescjuros = new BigDecimal( 0 );

		if ( baixaRecBean != null ) {

			for ( int row = 0; row < tab.getRowCount(); row++ ) {

				if ( codcli == ( (Integer) tab.getValor( row, EColTab.CODCLI.ordinal() ) ) ) {

					vlrpago = Funcoes.strToBd( tab.getValor( row, EColTab.VLRPAG.ordinal() ) );
					vlrdescjuros = vlrpago.subtract( Funcoes.strToBd( tab.getValor( row, EColTab.VLRAPAG.ordinal() ) ) ).setScale( Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP );

					tab.setValor( baixaRecBean.getConta(), row, EColTab.NUMCONTA.ordinal() );
					tab.setValor( baixaRecBean.getPlanejamento(), row, EColTab.CODPLAN.ordinal() );
					tab.setValor( baixaRecBean.getDocumento(), row, EColTab.DOCREC.ordinal() );
					tab.setValor( baixaRecBean.getCentroCusto(), row, EColTab.CODCC.ordinal() );

					if ( vlrdescjuros.floatValue() > 0 ) {
						tab.setValor( vlrdescjuros, row, EColTab.VLRJUROS.ordinal() );
					}
					else {
						tab.setValor( vlrdescjuros, row, EColTab.VLRDESC.ordinal() );
					}
				}
			}
		}
	}
	
	protected String[] getDetRetorno( final String codbanco, final String codretorno, final String tipofebraban ) {
		
		String msg = null;
		String tipo = null;
				
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		
		try {
			
			sSQL.append( "SELECT DESCRET,TIPORET " );
			sSQL.append( "FROM FNFBNCODRET " );
			sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODEMPBO=? AND CODFILIALBO=? AND " );
			sSQL.append( "CODBANCO=? AND CODRET=? AND TIPOFEBRABAN=?" );
			
			ps = con.prepareStatement( sSQL.toString() );
			
			ps.setInt( 1,  Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "FNBANCO" ) );
			ps.setString( 5, codbanco );
			ps.setString( 6, codretorno );
			ps.setString( 7, tipofebraban );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {				
				msg = rs.getString( 1 );
				tipo = rs.getString( 2 );
			}
		
			con.commit();
		} 
		catch ( Exception e ) {
			Funcoes.mensagemInforma( this, "Erro ao montar grid. \n" + e.getMessage());
			e.printStackTrace();
		}
		
		String[] ret = {msg,tipo};
		
		return ret;		
	}
	
	protected HashSet<StuffCli> getClientes() {
		
		HashSet<StuffCli> clientes = null;
		StuffCli cliente = null;
		Integer codcli = null;
		String[] args = new String[ EColcli.values().length ];
		
		if ( tab.getNumLinhas() > 0 ) {
			
			lbStatus.setText( "     Verificando clientes ..." );
			
			clientes = new HashSet<StuffCli>();
			
			for ( int row=0; row < tab.getNumLinhas(); row++ ) {
				
				if ( (Boolean) tab.getValor( row, EColTab.SEL.ordinal() ) ) {
					codcli = (Integer) tab.getValor( row, EColTab.CODCLI.ordinal() );
				
					args[ EColcli.CODBANCO.ordinal() ] = txtCodBanco.getVlrString();
					args[ EColcli.TIPOFEBRABAN.ordinal() ] = (String) tab.getValor( row, EColTab.TIPOFEBRABAN.ordinal() );
					args[ EColcli.STIPOFEBRABAN.ordinal() ] = null;
					args[ EColcli.AGENCIACLI.ordinal() ] = null;
					args[ EColcli.IDENTCLI.ordinal() ] = null;
					args[ EColcli.TIPOREMCLI.ordinal() ] = null;
					args[ EColcli.CODEMPPF.ordinal() ] = null;
					args[ EColcli.CODFILIALPF.ordinal() ] = null;
					args[ EColcli.NUMCONTA.ordinal() ] = (String) tab.getValor( row, EColTab.NUMCONTA.ordinal() );
					args[ EColcli.CODPLAN.ordinal() ] = (String) tab.getValor( row, EColTab.CODPLAN.ordinal() );
					args[ EColcli.RAZCLI.ordinal() ] = (String) tab.getValor( row, EColTab.RAZCLI.ordinal() );
					
					cliente = new SiaccUtil().new StuffCli( codcli, args );
					clientes.add( cliente );
				}
			}
		}
		
		lbStatus.setText( "" );
		
		return clientes;
	}
	
	protected HashSet<StuffParcela> getParcelas() {
		
		HashSet<StuffParcela> parcelas = null;
		StuffParcela parcela = null;
		Integer codrec = null;
		Integer numparcrec = null;
		Object[] args = null;
		
		if ( tab.getNumLinhas() > 0 ) {
			
			lbStatus.setText( "     Verificando parcelas ..." );
			
			parcelas = new HashSet<StuffParcela>();
			
			for ( int row=0; row < tab.getNumLinhas(); row++ ) {
				
				if ( "".equals( ((String) tab.getValor( row, EColTab.DOCREC.ordinal() )).trim() ) ) {					
					lbStatus.setText( "" );
					Funcoes.mensagemErro( this, "N�mero do documento n�o informado para o recebimento " + tab.getValor( row, EColTab.CODREC.ordinal() ) );
					return null;
				}
				else if ( (Boolean) tab.getValor( row, EColTab.SEL.ordinal() ) ) {
					
					args = new Object[ EParcela.values().length ];
					codrec = (Integer) tab.getValor( row, EColTab.CODREC.ordinal() );
					numparcrec = (Integer) tab.getValor( row, EColTab.NRPARC.ordinal() );
				
					args[ EParcela.NUMCONTA.ordinal() ] = (String) tab.getValor( row, EColTab.NUMCONTA.ordinal() );
					args[ EParcela.CODPLAN.ordinal() ] = (String) tab.getValor( row, EColTab.CODPLAN.ordinal() );
					args[ EParcela.CODCC.ordinal() ] = (String) tab.getValor( row, EColTab.CODCC.ordinal() );
					args[ EParcela.DOCLANCAITREC.ordinal() ] = (String) tab.getValor( row, EColTab.DOCREC.ordinal() );
					//args[ EParcela.DTPAGOITREC.ordinal() ] = Funcoes.strDateToDate( (String) tab.getValor( row, EColTab.DTPAG.ordinal() ) );
					args[ EParcela.DTPAGOITREC.ordinal() ] = tab.getValor( row, EColTab.DTPAG.ordinal() );
					args[ EParcela.VLRPAGOITREC.ordinal() ] = Funcoes.strToBd( tab.getValor( row, EColTab.VLRPAG.ordinal() ) );
					args[ EParcela.VLRDESCITREC.ordinal() ] = Funcoes.strToBd(  tab.getValor( row, EColTab.VLRDESC.ordinal() ) );
					args[ EParcela.VLRJUROSITREC.ordinal() ] = Funcoes.strToBd( tab.getValor( row, EColTab.VLRJUROS.ordinal() ) );
					args[ EParcela.OBSITREC.ordinal() ] = (String) tab.getValor( row, EColTab.OBS.ordinal() );
					
					parcela = new SiaccUtil().new StuffParcela( codrec, numparcrec, args );
					parcelas.add( parcela );
				}
			}
		}
		
		lbStatus.setText( "" );
		
		return parcelas;
	}

	protected boolean validaCliente( StuffCli cliente ) {

		boolean retorno = true;
		String mensagem = null;
		
		if ( "".equals( cliente.getArgs()[ EColcli.NUMCONTA.ordinal() ] ) ) {
			mensagem = "Preencha o n�mero da conta!";
		}
		else if ( "".equals( cliente.getArgs()[ EColcli.CODPLAN.ordinal() ] ) ) {
			mensagem = "Preecha a categoria!";
		}
		
		if ( mensagem != null ) {
			Funcoes.mensagemInforma( this, mensagem + "\nClinte: " + cliente.getCodigo() + "-" + cliente.getArgs()[ EColcli.RAZCLI.ordinal() ] );
			retorno = false;
		}
		
		return retorno;
	}
	
	protected boolean updateClientes() {

		boolean retorno = false;
		HashSet<StuffCli> clientes = getClientes();
		
		if ( clientes != null ) {
			
			try {
				
				int count = 0;
								
				for ( StuffCli cliente : clientes ) {
					
					if ( ! validaCliente( cliente ) ) {
						break;
					}
					
					StringBuilder sSQL = new StringBuilder();
					
					sSQL.append( "SELECT NUMCONTA, CODPLAN " );
					sSQL.append( "FROM FNFBNCLI " ); 
					sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " );			
					sSQL.append( "AND CODEMPPF=? AND CODFILIALPF=? " );	
					sSQL.append( "AND CODEMPBO=? AND CODFILIALBO=? AND CODBANCO=? AND TIPOFEBRABAN=?" );
								
					PreparedStatement ps = con.prepareStatement( sSQL.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
					ps.setInt( 3, cliente.getCodigo() );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
					ps.setInt( 6, Aplicativo.iCodEmp );
					ps.setInt( 7, ListaCampos.getMasterFilial( "FNBANCO" ) );
					ps.setString( 8, cliente.getArgs()[ EColcli.CODBANCO.ordinal() ] );
					ps.setString( 9, cliente.getArgs()[ EColcli.TIPOFEBRABAN.ordinal() ] );
					ResultSet rs = ps.executeQuery();
					
					if ( rs.next() ) {
						
						if ( ! cliente.getArgs()[ EColcli.NUMCONTA.ordinal() ].equals( rs.getString( "NUMCONTA" ) ) 
								|| ! cliente.getArgs()[ EColcli.CODPLAN.ordinal() ].equals( rs.getString( "CODPLAN" ) ) ) {
														
							lbStatus.setText( "     Atualizando cliente [c�digo " + cliente.getCodigo()  + "] ..." );
							
							sSQL.delete( 0, sSQL.length() );
							sSQL.append( "UPDATE FNFBNCLI SET " );
							sSQL.append( "CODEMPCA=?, CODFILIALCA=?, NUMCONTA=?, " ); 
							sSQL.append( "CODEMPPN=?, CODFILIALPN=?, CODPLAN=? " );
							sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " );			
							sSQL.append( "AND CODEMPPF=? AND CODFILIALPF=? " );	
							sSQL.append( "AND CODEMPBO=? AND CODFILIALBO=? AND CODBANCO=? AND TIPOFEBRABAN=?" );
							
							PreparedStatement ps2 = con.prepareStatement( sSQL.toString() );
							ps2.setInt( 1, Aplicativo.iCodEmp );
							ps2.setInt( 2, ListaCampos.getMasterFilial( "FNCONTA" ) );
							ps2.setString( 3, cliente.getArgs()[ EColcli.NUMCONTA.ordinal() ] );
							ps2.setInt( 4, Aplicativo.iCodEmp );
							ps2.setInt( 5, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
							ps2.setString( 6, cliente.getArgs()[ EColcli.CODPLAN.ordinal() ] );
							ps2.setInt( 7, Aplicativo.iCodEmp );
							ps2.setInt( 8, Aplicativo.iCodFilial );
							ps2.setInt( 9, cliente.getCodigo() );
							ps2.setInt( 10, Aplicativo.iCodEmp );
							ps2.setInt( 11, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
							ps2.setInt( 12, Aplicativo.iCodEmp );
							ps2.setInt( 13, ListaCampos.getMasterFilial( "FNBANCO" ) );
							ps2.setString( 14, cliente.getArgs()[ EColcli.CODBANCO.ordinal() ] );
							ps2.setString( 15, cliente.getArgs()[ EColcli.TIPOFEBRABAN.ordinal() ] );
							ps2.executeUpdate();
							
							lbStatus.setText( "     Cliente [c�digo " + cliente.getCodigo()  + "] atualizado ..." );
							count++;
						}
					}
					
					con.commit();
					
					retorno = true;
					
					lbStatus.setText( "     Atualizado [ " + count + " ] clientes." );
				}
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro atualizando cliente!\n" + e.getMessage() );
				e.printStackTrace();
				lbStatus.setText( "" );
			}
		}

		return retorno;
	}
	
	protected boolean baixaReceber() {
		
		boolean retorno = false;
		
		try {
			
			StringBuffer sSQL = new StringBuffer();

			sSQL.append( "UPDATE FNITRECEBER SET " );
			sSQL.append( "CODEMPCA=?,CODFILIALCA=?,NUMCONTA=?," );
			sSQL.append( "CODEMPPN=?,CODFILIALPN=?,CODPLAN=?," );
			sSQL.append( "CODEMPCC=?,CODFILIALCC=?,ANOCC=?,CODCC=?," );
			sSQL.append( "DOCLANCAITREC=?,DTPAGOITREC=?," );
			sSQL.append( "VLRPAGOITREC=VLRPAGOITREC + ?,VLRDESCITREC=?,VLRJUROSITREC=?," );
			sSQL.append( "OBSITREC=?,STATUSITREC='RP' " );
			sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?" );
			
			PreparedStatement ps = null;
			
			HashSet<StuffParcela> parcelas = getParcelas();

			if ( parcelas != null ) {
				
				lbStatus.setText( "     Aplicando a baixa ..." );
				
				int count = 0;
				
				for( StuffParcela parcela : parcelas ) {
					
					ps = con.prepareStatement( sSQL.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "FNCONTA" ) );
					ps.setString( 3, (String) parcela.getArgs()[ EParcela.NUMCONTA.ordinal() ] );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
					ps.setString( 6, (String) parcela.getArgs()[ EParcela.CODPLAN.ordinal() ] );
					if ( parcela.getArgs()[ EParcela.ANOCC.ordinal() ] != null ) {
						ps.setInt( 7, Aplicativo.iCodEmp );
						ps.setInt( 8, ListaCampos.getMasterFilial( "FNCC" ) );
						ps.setInt( 9, (Integer) parcela.getArgs()[ EParcela.ANOCC.ordinal() ] );
						ps.setString( 10, (String) parcela.getArgs()[ EParcela.CODCC.ordinal() ] );
					}
					else {
						ps.setNull( 7, Types.INTEGER );
						ps.setNull( 8, Types.INTEGER );
						ps.setNull( 9, Types.SMALLINT );
						ps.setNull( 10, Types.INTEGER );
					}
					ps.setString( 11, (String) parcela.getArgs()[ EParcela.DOCLANCAITREC.ordinal() ] );
					ps.setDate( 12, Funcoes.strDateToSqlDate( (String) parcela.getArgs()[ EParcela.DTPAGOITREC.ordinal() ] ) );
					ps.setBigDecimal( 13, (BigDecimal) parcela.getArgs()[ EParcela.VLRPAGOITREC.ordinal() ] );
					ps.setBigDecimal( 14, (BigDecimal) parcela.getArgs()[ EParcela.VLRDESCITREC.ordinal() ] );
					ps.setBigDecimal( 15, (BigDecimal) parcela.getArgs()[ EParcela.VLRJUROSITREC.ordinal() ] );
					if ( parcela.getArgs()[ EParcela.OBSITREC.ordinal() ] != null ) {
						ps.setString( 16, (String) parcela.getArgs()[ EParcela.OBSITREC.ordinal() ] );
					}
					else {
						ps.setNull( 16, Types.CHAR );
					}
					ps.setInt( 17, Aplicativo.iCodEmp );
					ps.setInt( 18, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
					ps.setInt( 19, parcela.getCodrec() );
					ps.setInt( 20, parcela.getNumparcrec() );
					ps.executeUpdate();
					ps.close();
					
					con.commit();
					
					count++;
				}
				
				lbStatus.setText( "     Aplicada a baixa em [ " + count + " ] parcela(s)." );
				
				retorno = true;
			}
		}
		catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao fazer a baixa da parcela!", true, con, e );
			e.printStackTrace();
			lbStatus.setText( "" );
		}
		
		return retorno;
	}
		
	protected boolean updateStatusRetorno( int codRec, int nParcitrec, String codBanco, String tipoFebraban, String stipoFebraban, String sitRet ) {

		boolean retorno = false;
		PreparedStatement ps = null;
		
		try {
						
			StringBuilder sqlup = new StringBuilder();
			sqlup.append( "UPDATE FNFBNREC SET CODBANCO=?, TIPOFEBRABAN=?, STIPOFEBRABAN=?, SITRETORNO=? " );
			sqlup.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?" );
			
			ps = con.prepareStatement( sqlup.toString() );
			ps.setString( 1, codBanco );
			ps.setString( 2, tipoFebraban );
			ps.setString( 3, stipoFebraban );
			ps.setString( 4, sitRet );
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setInt( 7, codRec );
			ps.setInt( 8, nParcitrec );
			ps.executeUpdate();
			
			con.commit();
			
			retorno = true;

		} 
		catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro atualizando situa��o do contas a receber!\n" + e.getMessage() );
		}

		return retorno;
	}
	
	protected void baixar(){

		if ( updateClientes() ) {
			if ( baixaReceber() ) {
				tab.limpa();
			}
		}
	}

	protected Boolean setInfoCli( final Integer codrec, final Integer numparcrec, final List<Object> info ) {

		boolean retorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();

		if ( codrec != null && numparcrec != null && info != null ) {

			try {

				sSQL.append( "SELECT R.CODREC, IR.NPARCITREC," );
				sSQL.append( "COALESCE(IR.NUMCONTA, FC.NUMCONTA) NUMCONTA," );
				sSQL.append( "COALESCE(IR.CODPLAN, FC.CODPLAN) CODPLAN, " );
				sSQL.append( "C.RAZCLI, R.CODCLI, IR.VLRAPAGITREC, R.DOCREC, ");
				sSQL.append( "IR.DTVENCITREC, IR.DTITREC, FC.TIPOFEBRABAN " );
				sSQL.append( "FROM FNITRECEBER IR, VDCLIENTE C, FNRECEBER R " );
				sSQL.append( "LEFT OUTER JOIN FNFBNCLI FC ON " );
				sSQL.append( "FC.CODEMP=R.CODEMPCL AND FC.CODFILIAL=R.CODFILIALCL AND FC.CODCLI=R.CODCLI AND " );
				sSQL.append( "FC.CODEMPBO=? AND FC.CODFILIALBO=? AND FC.CODBANCO=? AND " );
				sSQL.append( "FC.TIPOFEBRABAN='01' " );
				sSQL.append( "WHERE IR.CODEMP=? AND IR.CODFILIAL=? AND IR.CODREC=? AND IR.NPARCITREC=? AND " );
				sSQL.append( "R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC AND " );
				sSQL.append( "C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI AND " );
				sSQL.append( "IR.STATUSITREC<>'RP' " );

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNFBNCLI" ) );
				ps.setInt( 3, txtCodBanco.getVlrInteger() );
				ps.setInt( 4, Aplicativo.iCodEmp );
				ps.setInt( 5, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
				ps.setInt( 6, codrec );
				ps.setInt( 7, numparcrec );
				rs = ps.executeQuery();

				if ( rs.next() ) {

					info.add( EColInfoCli.CODREC.ordinal(), rs.getInt( EColInfoCli.CODREC.toString() ) );
					info.add( EColInfoCli.NPARCITREC.ordinal(), rs.getInt( EColInfoCli.NPARCITREC.toString() ) );
					info.add( EColInfoCli.NUMCONTA.ordinal(), rs.getString( EColInfoCli.NUMCONTA.toString() ) );
					info.add( EColInfoCli.CODPLAN.ordinal(), rs.getString( EColInfoCli.CODPLAN.toString() ) );
					info.add( EColInfoCli.RAZCLI.ordinal(), rs.getString( EColInfoCli.RAZCLI.toString() ) );
					info.add( EColInfoCli.CODCLI.ordinal(), rs.getInt( EColInfoCli.CODCLI.toString() ) );
					info.add( EColInfoCli.VLRAPAGITREC.ordinal(), rs.getBigDecimal( EColInfoCli.VLRAPAGITREC.toString() ) );
					info.add( EColInfoCli.DOCREC.ordinal(), rs.getString( EColInfoCli.DOCREC.toString() ) );
					info.add( EColInfoCli.DTVENCITREC.ordinal(), Funcoes.sqlDateToDate( rs.getDate( EColInfoCli.DTVENCITREC.toString() ) ) );
					info.add( EColInfoCli.DTITREC.ordinal(), Funcoes.sqlDateToDate( rs.getDate( EColInfoCli.DTITREC.toString() ) ) );
					info.add( EColInfoCli.TIPOFEBRABAN.ordinal(), rs.getString( EColInfoCli.TIPOFEBRABAN.toString() ) );
				}
				
				con.commit();
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao buscar informa��es do cliente!\n" + e.getMessage(), true, con, e );
				e.printStackTrace();
				retorno = false;
			}
		}

		return retorno;
	}
	
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btSelTudo ) {
			selecionaTudo();
		}
		else if ( e.getSource() == btSelNada ) {
			selecionaNada();
		}
		else if ( e.getSource() == btImporta ) {
			execImportar();
		}
		else if ( e.getSource() == btEdita ) {
			edit();
		}
		else if ( e.getSource() == btBaixar ) {
			baixar();
		}
	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource() == btImporta ) {
			execImportar();
		}
	}
	public void keyReleased( KeyEvent e ) {}

	public void keyTyped( KeyEvent e ) {}

	public void mouseClicked( MouseEvent e ) {

		if ( e.getComponent() == tab ) {
			if ( e.getClickCount() == 2 ) {
				edit();
			}
		}
	}
	public void mouseEntered( MouseEvent e ) {}

	public void mouseExited( MouseEvent e ) {}

	public void mousePressed( MouseEvent e ) {}

	public void mouseReleased( MouseEvent e ) {}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

	protected enum EColTab {
		STATUS, SEL, RAZCLI, CODCLI, CODREC, DOCREC, NRPARC, VLRAPAG, DTREC, DTVENC, 
		VLRPAG, DTPAG, NUMCONTA, CODPLAN, VLRDESC, VLRJUROS, CODCC, OBS, TIPOFEBRABAN,
		CODRET,  MENSSAGEM;
		
	};

	protected enum EColInfoCli {
		CODREC, NPARCITREC, NUMCONTA, CODPLAN, RAZCLI, CODCLI, VLRAPAGITREC, 
		DOCREC, DTVENCITREC, DTITREC, TIPOFEBRABAN;
	};

}
