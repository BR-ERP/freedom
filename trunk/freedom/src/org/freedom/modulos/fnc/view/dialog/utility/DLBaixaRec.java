/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLBaixaRec.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Coment�rios sobre a classe...
 */

package org.freedom.modulos.fnc.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.fnc.business.component.Juros;
import org.freedom.modulos.fnc.library.swing.component.JTextFieldPlan;

public class DLBaixaRec extends FFDialogo implements CarregaListener, FocusListener, EditListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRazCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDescConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPlan txtCodPlan = new JTextFieldPlan( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtPercDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtPercJuros = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrAberto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtPagto = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtLiqItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrPago = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );

	private ListaCampos lcConta = new ListaCampos( this );

	private ListaCampos lcPlan = new ListaCampos( this );

	private ListaCampos lcCC = new ListaCampos( this );
	
	boolean clienteuniq = true;

	boolean bJurosPosCalc = false;
	
	private Integer anoBaseCC = null;
	
	private BigDecimal valorapaganterior = BigDecimal.ZERO;

	private BaixaRecBean baixaRecBean;
	
	boolean multiBaixa = false;
	boolean categoriaRequerida = true;

	public DLBaixaRec( Component cOrig ) {

		super( cOrig );
		setTitulo( "Baixa" );
		setAtribos( 380, 460 );

		
		this.montaListaCampos();
		this.montaTela();
	}
	
	public DLBaixaRec(Component cOrig, boolean multibaixa, boolean categoriaRequerida, boolean clienteuniq){
		super( cOrig );
		setTitulo( "Baixa" );
		setAtribos( 380, 460 );
		
		this.multiBaixa = multibaixa;
		this.categoriaRequerida = categoriaRequerida;
		this.clienteuniq = clienteuniq;
		
		montaListaCampos();
		montaTela();
	}
	
	private void montaListaCampos(){
		
		if(categoriaRequerida){
			txtCodPlan.setRequerido( true );
		}
		
		txtCodConta.setRequerido( true );
		txtDoc.setRequerido( true );
		txtDtPagto.setRequerido( true );
		txtDtLiqItRec.setRequerido( true );
		txtVlr.setRequerido( true );
		txtObs.setRequerido( true );

		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "N� Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta, null );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o", ListaCampos.DB_SI, false ) );
		lcPlan.setWhereAdic( "TIPOPLAN = 'R' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan, null );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "C�digo", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descri��o", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.setWhereAdic( "NIVELCC=10 " );
		lcCC.setDinWhereAdic( "ANOCC = #N", txtAnoCC );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC, null );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );
		txtAnoCC.setTabelaExterna( lcCC, null );
		txtAnoCC.setFK( true );
		txtAnoCC.setNomeCampo( "AnoCC" );

	}
	
	private void montaTela(){
		txtCodCli.setAtivo( false );
		txtRazCli.setAtivo( false );
		txtDescConta.setAtivo( false );
		txtDescPlan.setAtivo( false );
		txtDtEmis.setAtivo( false );
		txtDtVenc.setAtivo( false );
		txtVlrAberto.setAtivo( false );
		txtVlrParc.setAtivo( false );
		
		if(!clienteuniq){
			txtVlr.setAtivo( false );
		}
		
		if(multiBaixa){
			
			txtVlrPago.setAtivo( false );
			txtVlrDesc.setAtivo( false );
			txtVlrJuros.setAtivo( false );
			txtPercDesc.setAtivo( false );
			txtPercJuros.setAtivo( false );
		}


		adic( txtCodCli		, 7		, 20, 100, 20, "C�d.cli."  );
		adic( txtRazCli		, 110	, 20, 230, 20, "Raz�o social do cliente" );
		
		adic( txtCodConta	, 7		, 60, 100, 20, "N�mero" );
		adic( txtDescConta	, 110	, 60, 230, 20, "Descri��o da conta" );
		
		adic( txtCodPlan	, 7		, 100, 100, 20, "C�d.ctg." );
		adic( txtDescPlan	, 110	, 100, 230, 20, "Descri��o da categoria" );

		adic( txtCodCC		, 7		, 140, 100, 20, "C�d.c.c." );
		adic( txtDescCC		, 110	, 140, 230, 20, "Descri��o do centro de custo"  );

		adic( txtDoc		, 7		, 180, 100, 20, "Doc." );

		adic( txtPercDesc	, 7		, 220, 80, 20, "% Desc." );

		adic( txtVlrDesc	, 90	, 220, 80, 20, "Vlr.Desconto" );

		adic( txtPercJuros	, 173	, 220, 75, 20 );

		adic( txtVlrJuros	, 251	, 220, 88, 20, "Vlr. Juros" );

		adic( txtDtEmis		, 7		, 260, 80, 20, "Emiss�o" );
		adic( txtDtVenc		, 90	, 260, 80, 20, "Vencimento" );
		adic( txtDtPagto	, 173	, 260, 75, 20, "Recebimento" );
		adic( txtDtLiqItRec	, 251	, 260, 88, 20, "Liquida��o" );

		adic( txtVlrParc	, 7		, 300, 80, 20, "Vlr.Original" );
		adic( txtVlrAberto	, 90	, 300, 80, 20, "Vlr.Aberto" );
		adic( txtVlr		, 173	, 300, 167, 20, "Valor do Recebimento" );

		adic( txtObs		, 7		, 340, 333, 20, "Observa��es" );
		
		lcCC.addCarregaListener( this );
		txtVlr.addFocusListener( this );
		txtPercDesc.addFocusListener( this );
		txtVlrDesc.addFocusListener( this );
		txtVlrDesc.addEditListener( this );
		txtPercJuros.addFocusListener( this );
		txtVlrJuros.addFocusListener( this );
		txtVlrJuros.addEditListener( this );

	}

	private void calcDesc() {

		if ( txtPercDesc.getVlrDouble().doubleValue() != 0 ) {
			txtVlrDesc.setVlrBigDecimal( txtPercDesc.getVlrBigDecimal().multiply( valorapaganterior ).divide( new BigDecimal( 100 ), 2, BigDecimal.ROUND_HALF_UP ) );
		}
		atualizaAberto();
	}

	private void calcJuros() {

		if ( !bJurosPosCalc ) {
			if ( txtPercJuros.getVlrDouble().doubleValue() != 0 )
				txtVlrJuros.setVlrBigDecimal( txtPercJuros.getVlrBigDecimal().multiply( valorapaganterior ).divide( new BigDecimal( 100 ), 2, BigDecimal.ROUND_HALF_UP ) );
		}
		else
			txtVlrJuros.setVlrBigDecimal( txtPercJuros.getVlrBigDecimal().multiply( valorapaganterior ).divide( new BigDecimal( 100 ), 2, BigDecimal.ROUND_HALF_UP ).multiply( new BigDecimal( Funcoes.getNumDiasAbs( txtDtVenc.getVlrDate(), new Date() ) ) ) );
		atualizaAberto();
	}

	private void atualizaPagto() {

		if ( txtVlr.getVlrBigDecimal().compareTo( txtVlrAberto.getVlrBigDecimal() ) > 0 ) {
			txtVlrJuros.setVlrBigDecimal( txtVlr.getVlrBigDecimal().subtract( txtVlrAberto.getVlrBigDecimal() ) );
			atualizaAberto();
		}
	}

	private void atualizaAberto() {

		BigDecimal atualizado = valorapaganterior;

		BigDecimal descontos = txtVlrDesc.getVlrBigDecimal();
		BigDecimal juros = txtVlrJuros.getVlrBigDecimal();

		BigDecimal pagoparcial = null;

		if ( baixaRecBean != null ) {
			pagoparcial = baixaRecBean.getValorPagoParc();
		}

		pagoparcial = new BigDecimal( 0 );

		BigDecimal pago = txtVlrPago.getVlrBigDecimal();

		atualizado = atualizado.subtract( descontos );
		atualizado = atualizado.add( juros );
		atualizado = atualizado.subtract( pagoparcial );

		txtVlrAberto.setVlrBigDecimal( atualizado );
		txtVlr.setVlrBigDecimal( atualizado );

	}

	private int getAnoBaseCC() {

		int anoBase = 0;

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				anoBase = rs.getInt( "ANOCENTROCUSTO" );
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		}

		return anoBase;
	}

	private void emBordero( BaixaRecBean baixa ) {

		try {

			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT B.NUMCONTABOR FROM FNBORDERO B, FNITBORDERO I, FNITRECEBER R " );
			sql.append( "WHERE B.CODEMP=I.CODEMP AND B.CODFILIAL=I.CODFILIAL AND B.CODBOR=I.CODBOR AND " );
			sql.append( "I.CODEMPRC=R.CODEMP AND I.CODFILIALRC=R.CODFILIAL AND I.CODREC=R.CODREC AND I.NPARCITREC=R.NPARCITREC AND " );
			sql.append( "R.CODEMP=? AND R.CODFILIAL=? AND R.CODREC=? AND R.NPARCITREC=?" );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setInt( 3, baixa.getRecebimento() );
			ps.setInt( 4, baixa.getParcela() );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				txtCodConta.setVlrString( rs.getString( "NUMCONTABOR" ) );
				txtCodConta.setAtivo( false );
				txtCodConta.setRequerido( false );
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao buscar conta de adiantamento de recebiv�is.\n" + err.getMessage(), true, con, err );
		}
	}

	public void setValores( BaixaRecBean baixa ) {

		if ( baixa == null ) {
			return;
		}

		baixaRecBean = baixa;
		
		valorapaganterior = baixaRecBean.getValorAPagar().
		    subtract(baixaRecBean.getValorJuros()).add(baixaRecBean.getValorDesconto());
		
		if(multiBaixa){
			txtRazCli.setVlrString( "REC. MULTIPLOS" );
			
			txtVlr.setVlrBigDecimal( baixaRecBean.getValorAPagar() );
			txtVlrAberto.setVlrBigDecimal( baixa.getValorAPagar() );
			txtVlrParc.setVlrBigDecimal( baixa.getValorAPagar() );
			
			txtDtPagto.setVlrDate( new Date() );
			txtDtLiqItRec.setVlrDate( new Date() );
			return;
		}

		txtCodCli.setVlrInteger( baixa.getCliente() );
		txtRazCli.setVlrString( baixa.getRazaoSocialCliente() );
		txtCodConta.setVlrString( baixa.getConta() );
		txtCodPlan.setVlrString( baixa.getPlanejamento() );
		txtCodCC.setVlrString( baixa.getCentroCusto() );

		txtDoc.setVlrString( baixa.getDocumento() );
		txtDtEmis.setVlrDate( baixa.getDataEmissao() );
		txtDtVenc.setVlrDate( baixa.getDataVencimento() );
		txtDtPagto.setVlrDate( baixa.getDataPagamento() );
		txtDtLiqItRec.setVlrDate( baixa.getDataLiquidacao() );

		txtVlrParc.setVlrBigDecimal( baixa.getValorParcela() );

		txtVlrDesc.setVlrBigDecimal( baixa.getValorDesconto() );
		txtVlrJuros.setVlrBigDecimal( baixa.getValorJuros() );
		txtVlrAberto.setVlrBigDecimal( baixa.getValorAPagar() );

		if ( baixa.getValorPago() != null && baixa.getValorPago().floatValue() > 0 ) {
			txtVlrPago.setVlrBigDecimal( baixa.getValorPago() );
			txtVlr.setVlrBigDecimal( baixa.getValorPago() );
		}
		else {
			txtVlrPago.setVlrBigDecimal( baixa.getValorAPagar() );
			txtVlr.setVlrBigDecimal( baixa.getValorAPagar() );
		}

		txtObs.setVlrString( baixa.getObservacao() );

		if ( baixa.isEmBordero() && !multiBaixa ) {
			emBordero( baixa );
		}

		lcConta.carregaDados();
		lcPlan.carregaDados();
		lcCC.carregaDados();

		if ( ( ! ( bJurosPosCalc = Juros.getJurosPosCalc() ) ) && txtVlrJuros.getVlrBigDecimal().doubleValue() == 0 ) {
			adic( new JLabelPad( "% Juros." ), 180, 200, 57, 20 );
			Juros calcjuros = new Juros( txtDtVenc.getVlrDate(), txtVlrParc.getVlrBigDecimal() );
			txtVlrJuros.setVlrBigDecimal( calcjuros.getVlrjuros() );
		}
		else {
			adic( new JLabelPad( "% Dia." ), 180, 200, 57, 20 );
		}

	}

	public BaixaRecBean getValores() {

		if ( baixaRecBean == null ) {
			baixaRecBean = new BaixaRecBean();
		}

		baixaRecBean.setCliente( txtCodCli.getVlrInteger() );
		baixaRecBean.setRazaoSocialCliente( txtRazCli.getVlrString() );
		baixaRecBean.setConta( txtCodConta.getVlrString() );
		baixaRecBean.setPlanejamento( txtCodPlan.getVlrString() );
		baixaRecBean.setCentroCusto( txtCodCC.getVlrString() );
		baixaRecBean.setDocumento( txtDoc.getVlrString() );
		baixaRecBean.setDataEmissao( txtDtEmis.getVlrDate() );
		baixaRecBean.setDataVencimento( txtDtVenc.getVlrDate() );
		baixaRecBean.setDataPagamento( txtDtPagto.getVlrDate() );
		baixaRecBean.setDataLiquidacao( txtDtLiqItRec.getVlrDate() );
		baixaRecBean.setValorParcela( txtVlrParc.getVlrBigDecimal() );
		baixaRecBean.setValorAPagar( txtVlrAberto.getVlrBigDecimal() );
		baixaRecBean.setValorDesconto( txtVlrDesc.getVlrBigDecimal() );
		baixaRecBean.setValorJuros( txtVlrJuros.getVlrBigDecimal() );
		baixaRecBean.setValorPago( txtVlr.getVlrBigDecimal() );
		baixaRecBean.setObservacao( txtObs.getVlrString() );

		return baixaRecBean;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( txtCodConta.getVlrString().length() < 1 ) {
				Funcoes.mensagemInforma( this, "N�mero da conta � requerida!" );
			}
			else if ( txtCodPlan.getVlrString().length() < 13 && categoriaRequerida) {
				Funcoes.mensagemInforma( this, "C�digo da categoria � requerido!" );
			}
			else if ( txtDtPagto.getVlrString().length() < 10 ) {
				Funcoes.mensagemInforma( this, "Data do pagamento � requerida!" );
			}
			else if ( txtDtLiqItRec.getVlrString().length() < 10 ) {
				Funcoes.mensagemInforma( this, "Data da liquida��o � requerida!" );
			}
			else if ( txtVlr.getVlrString().length() < 4 ) {
				Funcoes.mensagemInforma( this, "Valor pago � requerido!" );
			}
			else if ( txtVlr.floatValue() <= 0 ) {
				Funcoes.mensagemInforma( this, "Valor pago deve ser maior que zero!" );
			}
			else {
				super.actionPerformed( evt );
			}
		}
		else {
			super.actionPerformed( evt );
		}
	}

	public void focusGained( FocusEvent fevt ) { }

	public void focusLost( FocusEvent fevt ) {

		if ( fevt.getSource() == txtPercDesc ) {
			calcDesc();
		}
		else if ( fevt.getSource() == txtPercJuros ) {
			calcJuros();
		}
		else if ( fevt.getSource() == txtVlrDesc ) {
			atualizaAberto();
		}
		else if ( fevt.getSource() == txtVlrJuros ) {
			atualizaAberto();
		}
		else if ( fevt.getSource() == txtVlr ) {
			atualizaPagto();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {
		if ( cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0 ) {
			txtAnoCC.setVlrInteger( anoBaseCC );
		}
	}

	public void afterCarrega( CarregaEvent cevt ) { }

	public void edit( EditEvent eevt ) {

		if ( eevt.getSource() == txtVlrDesc ) {
			txtPercDesc.setVlrBigDecimal( new BigDecimal( 0 ) );
		} else if ( eevt.getSource() == txtVlrJuros ) {
			txtPercJuros.setVlrBigDecimal( new BigDecimal( 0 ) );
		}
	}

	public void beforeEdit( EditEvent eevt ) { }

	public void afterEdit( EditEvent eevt ) { }

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcPlan.setConexao( cn );
		lcCC.setConexao( cn );
		
		anoBaseCC = getAnoBaseCC();
		txtAnoCC.setVlrInteger( anoBaseCC );
		
	}

	public class BaixaRecBean {

		private Integer recebimento;
		private Integer parcela;
		private Integer cliente;
		private String razaoSocialCliente;
		private String conta;
		private String planejamento;
		private String centroCusto;
		private String documento;
		private Date dataEmissao;
		private Date dataVencimento;
		private Date dataPagamento;
		private Date dataLiquidacao;
		private BigDecimal valorParcela;
		private BigDecimal valorAPagar;
		private BigDecimal valorDesconto;
		private BigDecimal valorJuros;
		private BigDecimal valorPago;
		private BigDecimal valorPagoParc;
		private String observacao;
		private boolean emBordero;
		
		public Integer getRecebimento() {
			return recebimento;
		}
		
		public void setRecebimento( Integer recebimento ) {
			this.recebimento = recebimento;
		}
		
		public Integer getParcela() {
			return parcela;
		}
		
		public void setParcela( Integer parcela ) {
			this.parcela = parcela;
		}
		
		public Integer getCliente() {
			return cliente;
		}
		
		public void setCliente( Integer cliente ) {
			this.cliente = cliente;
		}
		
		public String getRazaoSocialCliente() {
			return razaoSocialCliente;
		}
		
		public void setRazaoSocialCliente( String razaoSocialCliente ) {
			this.razaoSocialCliente = razaoSocialCliente;
		}
		
		public String getConta() {
			return conta;
		}
		
		public void setConta( String conta ) {
			this.conta = conta;
		}
		
		public String getPlanejamento() {
			return planejamento;
		}
		
		public void setPlanejamento( String planejamento ) {
			this.planejamento = planejamento;
		}
		
		public String getCentroCusto() {
			return centroCusto;
		}
		
		public void setCentroCusto( String centroCusto ) {
			this.centroCusto = centroCusto;
		}
		
		public String getDocumento() {
			return documento;
		}
		
		public void setDocumento( String documento ) {
			this.documento = documento;
		}
		
		public Date getDataEmissao() {
			return dataEmissao;
		}
		
		public void setDataEmissao( Date dataEmissao ) {
			this.dataEmissao = dataEmissao;
		}
		
		public Date getDataVencimento() {
			return dataVencimento;
		}
		
		public void setDataVencimento( Date dataVencimento ) {
			this.dataVencimento = dataVencimento;
		}
		
		public Date getDataPagamento() {
			return dataPagamento;
		}
		
		public void setDataPagamento( Date dataPagamento ) {
			this.dataPagamento = dataPagamento;
		}
		
		public Date getDataLiquidacao() {
			return dataLiquidacao;
		}
		
		public void setDataLiquidacao( Date dataLiquidacao ) {
			this.dataLiquidacao = dataLiquidacao;
		}
		
		public BigDecimal getValorParcela() {
			return valorParcela;
		}
		
		public void setValorParcela( BigDecimal valorParcela ) {
			this.valorParcela = valorParcela;
		}
		
		public BigDecimal getValorAPagar() {
			return valorAPagar;
		}
		
		public void setValorAPagar( BigDecimal valorAPagar ) {
			this.valorAPagar = valorAPagar;
		}
		
		public BigDecimal getValorDesconto() {
			return valorDesconto;
		}
		
		public void setValorDesconto( BigDecimal valorDesconto ) {
			this.valorDesconto = valorDesconto;
		}
		
		public BigDecimal getValorJuros() {
			return valorJuros;
		}
		
		public void setValorJuros( BigDecimal valorJuros ) {
			this.valorJuros = valorJuros;
		}
		
		public BigDecimal getValorPago() {
			return valorPago;
		}
		
		public void setValorPago( BigDecimal valorPago ) {
			this.valorPago = valorPago;
		}
		
		public BigDecimal getValorPagoParc() {
			return valorPagoParc;
		}
		
		public void setValorPagoParc( BigDecimal valorPagoParc ) {
			this.valorPagoParc = valorPagoParc;
		}
		
		public String getObservacao() {
			return observacao;
		}
		
		public void setObservacao( String observacao ) {
			this.observacao = observacao;
		}
		
		public boolean isEmBordero() {
			return emBordero;
		}
		
		public void setEmBordero( boolean emBordero ) {
			this.emBordero = emBordero;
		}

	};
}
