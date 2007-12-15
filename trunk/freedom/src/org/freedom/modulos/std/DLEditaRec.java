/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLEditaRec.java <BR>
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
 */

package org.freedom.modulos.std;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.freedom.componentes.JLabelPad;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLEditaRec extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRazCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDescConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );
	
	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldFK txtDescCartCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcBanco = new ListaCampos( this );

	private ListaCampos lcConta = new ListaCampos( this );

	private ListaCampos lcPlan = new ListaCampos( this );

	private ListaCampos lcCC = new ListaCampos( this );
	
	private final ListaCampos lcCartCob = new ListaCampos( this, "CB" );
	
	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );
	
	public enum EColEdit{CODCLI, RAZCLI, NUMCONTA, CODPLAN, CODCC, DOC, DTEMIS, DTVENC,
		VLRJUROS, VLRDESC, VLRPARC, OBS, CODBANCO, CODTPCOB, DESCTPCOB,CODCARTCOB, DESCCARTCOB};

	public enum EColRet{ NUMCONTA, CODPLAN, CODCC, DOC, VLRJUROS, VLRDESC, 
		DTVENC, OBS, CODBANCO, CODTPCOB, DESCTPCOB, CODCARTCOB, DESCCARTCOB};		

	public DLEditaRec( Component cOrig, final boolean bEdita ) {

		super( cOrig );
		setTitulo( bEdita?"Editar":"Visualizar" );
		setAtribos( 380, 510 );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

		/************************
         * CARTEIRA DE COBRAN�A *
         ************************/
		
		txtCodCartCob.setNomeCampo( "CodCartCob" );
		lcCartCob.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "C�d.cart.cob", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtDescCartCob, "DescCartCob", "Desc.Cart.Cob", ListaCampos.DB_SI, false ) );
		lcCartCob.setDinWhereAdic( "CODBANCO = #S", txtCodBanco );
		lcCartCob.montaSql( false, "CARTCOB", "FN" );
		lcCartCob.setQueryCommit( false );
		lcCartCob.setReadOnly( true );		
		txtCodCartCob.setTabelaExterna( lcCartCob );
		txtCodCartCob.setListaCampos( lcCartCob );
		txtDescCartCob.setListaCampos( lcCartCob );
		txtCodCartCob.setFK( true );
		
		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "N� Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descri��o da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "C�d.plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descri��o do planejamento", ListaCampos.DB_SI, false ) );
		lcPlan.setWhereAdic( "TIPOPLAN = 'R' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "C�d.c.c.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla c.c.", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descri��o do centro de custos", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.setWhereAdic( "NIVELCC=10" );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );
		txtAnoCC.setTabelaExterna( lcCC );
		txtAnoCC.setFK( true );
		txtAnoCC.setNomeCampo( "AnoCC" );
		
		txtCodTipoCob.setNomeCampo( "CodTipoCob" );
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "C�d.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descri��o do tipo de cobran�a.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob );
		txtCodTipoCob.setListaCampos( lcTipoCob );
		txtDescTipoCob.setListaCampos( lcTipoCob );
		txtCodTipoCob.setFK( true );

		txtCodCli.setAtivo( false );
		txtRazCli.setAtivo( false );
		txtDescConta.setAtivo( false );
		txtDescPlan.setAtivo( false );
		txtDtEmis.setAtivo( false );
		txtVlrParc.setAtivo( false );

		adic( new JLabelPad( "C�d.cli." ), 7, 0, 250, 20 );
		adic( txtCodCli, 7, 20, 80, 20 );
		adic( new JLabelPad( "Raz�o social do cliente" ), 90, 0, 250, 20 );
		adic( txtRazCli, 90, 20, 250, 20 );
		adic( new JLabelPad( "Cod.Tp.Cob" ), 7, 40, 80, 20);
		adic( txtCodTipoCob, 7, 60, 80, 20 );
		adic( new JLabelPad("Descri��o do tipo de cobran�a"), 90, 40, 250, 20 );
		adic(txtDescTipoCob, 90, 60, 250, 20 );
		adic( new JLabelPad( "C�d.banco" ), 7, 80, 250, 20 );
		adic( txtCodBanco, 7, 100, 80, 20 );
		adic( new JLabelPad( "Descri��o do banco" ), 90, 80, 250, 20 );
		adic( txtDescBanco, 90, 100, 250, 20 );
		
		adic( new JLabelPad( "C�d.cart.cob." ), 7, 120, 250, 20 );
		adic( txtCodCartCob, 7, 140, 80, 20 );
		adic( new JLabelPad( "Descri��o da carteira de cobran�a" ), 90, 120, 250, 20 );
		adic( txtDescCartCob, 90, 140, 250, 20 );

		adic( new JLabelPad( "N�conta" ), 7, 160, 250, 20 );
		adic( txtCodConta, 7, 180, 80, 20 );
		adic( new JLabelPad( "Descri��o da conta" ), 90, 160, 250, 20 );
		adic( txtDescConta, 90, 180, 250, 20 );
		
		adic( new JLabelPad( "C�d.catg." ), 7, 200, 250, 20 );
		adic( txtCodPlan, 7, 220, 100, 20 );
		adic( new JLabelPad( "Descri��o da categoria" ), 110, 200, 250, 20 );
		adic( txtDescPlan, 110, 220, 230, 20 );
		
		adic( new JLabelPad( "C�d.c.c." ), 7, 240, 250, 20 );
		adic( txtCodCC, 7, 260, 100, 20 );
		adic( new JLabelPad( "Descri��o do centro de custo" ), 110, 240, 250, 20 );
		adic( txtDescCC, 110, 260, 230, 20 );
		
		adic( new JLabelPad( "Doc." ), 7, 280, 110, 20 );
		adic( txtDoc, 7, 300, 110, 20 );
		adic( new JLabelPad( "Emiss�o" ), 120, 280, 107, 20 );
		adic( txtDtEmis, 120, 300, 107, 20 );
		adic( new JLabelPad( "Vencimento" ), 230, 280, 110, 20 );
		adic( txtDtVenc, 230, 300, 110, 20 );
		
		adic( new JLabelPad( "Vlr.juros." ), 7, 320, 110, 20 );
		adic( txtVlrJuros, 7, 340, 110, 20 );
		adic( new JLabelPad( "Vlr.desc." ), 120, 320, 107, 20 );
		adic( txtVlrDesc, 120, 340, 107, 20 );
		adic( new JLabelPad( "Vlr.parcela" ), 230, 320, 110, 20 );
		adic( txtVlrParc, 230, 340, 110, 20 );
		
		adic( new JLabelPad( "Observa��es" ), 7, 360, 240, 20 );
		adic( txtObs, 7, 380, 333, 20 );

		lcCC.addCarregaListener( this );
		
		if(!bEdita) {
			txtAnoCC.setAtivo( bEdita );
			txtCodBanco.setAtivo( bEdita );
			txtCodCartCob.setAtivo( bEdita );
			txtCodCC.setAtivo( bEdita );
			txtCodCli.setAtivo( bEdita );
			txtCodConta.setAtivo( bEdita );
			txtCodPlan.setAtivo( bEdita );
			txtCodTipoCob.setAtivo( bEdita );
			txtDoc.setAtivo( bEdita );
			txtDtEmis.setAtivo( bEdita );
			txtDtVenc.setAtivo( bEdita );
			txtObs.setAtivo( bEdita );
			txtSiglaCC.setAtivo( bEdita );
			txtVlrDesc.setAtivo( bEdita );
			txtVlrJuros.setAtivo( bEdita );
			txtVlrParc.setAtivo( bEdita );
			btOK.setVisible( bEdita );
		}
		
		
	}

	public void setValores( Object[] sVals ) {

		txtCodCli.setVlrInteger( (Integer) sVals[ EColEdit.CODCLI.ordinal() ] );
		txtRazCli.setVlrString( (String) sVals[ EColEdit.RAZCLI.ordinal() ] );
		txtCodConta.setVlrString( (String) sVals[ EColEdit.NUMCONTA.ordinal() ] );
		txtCodPlan.setVlrString( (String) sVals[ EColEdit.CODPLAN.ordinal() ] );
		txtCodCC.setVlrString( (String) sVals[ EColEdit.CODCC.ordinal() ] );
		txtDoc.setVlrString( (String) sVals[ EColEdit.DOC.ordinal() ] );
		txtDtEmis.setVlrDate( (Date) sVals[ EColEdit.DTEMIS.ordinal() ] );
		txtDtVenc.setVlrDate( (Date) sVals[ EColEdit.DTVENC.ordinal() ] );
		txtVlrJuros.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRJUROS.ordinal() ] );
		txtVlrDesc.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRDESC.ordinal() ] );
		txtVlrParc.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRPARC.ordinal() ] );
		txtObs.setVlrString( (String) sVals[ EColEdit.OBS.ordinal() ] );
		txtCodBanco.setVlrString((String) sVals[ EColEdit.CODBANCO.ordinal() ] );
		txtCodTipoCob.setVlrString( ( String) sVals[ EColEdit.CODTPCOB.ordinal() ] );
		txtDescTipoCob.setVlrString( (String) sVals[ EColEdit.DESCTPCOB.ordinal()] );
		txtCodCartCob.setVlrString( ( String) sVals[ EColEdit.CODCARTCOB.ordinal() ] );
		txtDescCartCob.setVlrString( (String) sVals[ EColEdit.DESCCARTCOB.ordinal()] );
		
	}

	public Object[] getValores() {

		Object[] oRetorno = new Object[ 13 ];
		oRetorno[ EColRet.NUMCONTA.ordinal() ] = txtCodConta.getVlrString();
		oRetorno[ EColRet.CODPLAN.ordinal() ] = txtCodPlan.getVlrString();
		oRetorno[ EColRet.CODCC.ordinal() ] = txtCodCC.getVlrString();
		oRetorno[ EColRet.DOC.ordinal() ] = txtDoc.getVlrString();
		oRetorno[ EColRet.VLRJUROS.ordinal() ] = txtVlrJuros.getVlrBigDecimal();
		oRetorno[ EColRet.VLRDESC.ordinal() ] = txtVlrDesc.getVlrBigDecimal();
		oRetorno[ EColRet.DTVENC.ordinal() ] = txtDtVenc.getVlrDate();
		oRetorno[ EColRet.OBS.ordinal() ] = txtObs.getVlrString();
		oRetorno[ EColRet.CODBANCO.ordinal() ] = txtCodBanco.getVlrString();
		oRetorno[ EColRet.CODTPCOB.ordinal() ] = txtCodTipoCob.getVlrString();
		oRetorno[ EColRet.DESCTPCOB.ordinal() ] = txtDescTipoCob.getVlrString();
		oRetorno[ EColRet.CODCARTCOB.ordinal() ] = txtCodCartCob.getVlrString();
		oRetorno[ EColRet.DESCCARTCOB.ordinal() ] = txtDescCartCob.getVlrString();
		return oRetorno;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK && txtDtVenc.getVlrString().length() < 10 ) {
				
			Funcoes.mensagemInforma( this, "Data do vencimento � requerido!" );
		}
		else {
			
			super.actionPerformed( evt );
		}
	}

	private int buscaAnoBaseCC() {

		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
			
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
		}
		
		return iRet;
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0 ) {
			
			txtAnoCC.setVlrInteger( new Integer( buscaAnoBaseCC() ) );
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcConta.carregaDados();
		lcPlan.setConexao( cn );
		lcPlan.carregaDados();
		lcCC.setConexao( cn );
		lcCC.carregaDados();
		lcBanco.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcBanco.carregaDados();
		lcCartCob.setConexao( cn );
		lcCartCob.carregaDados();
		
	}
}
