/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRReceber.java <BR>
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

package org.freedom.modulos.fnc.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import net.sf.jasperreports.engine.JasperPrintManager;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.funcoes.Funcoes;

public class FRCobranca extends FRelatorio implements RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCnpjCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTpCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescTpCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir observa��es?", "S", "N" );

	private JCheckBoxPad cbImpTotDia = new JCheckBoxPad( "Imprimir totalizador di�rio?", "S", "N" );

//	private JCheckBoxPad cbParPar = new JCheckBoxPad( "Imprimir pagamentos parciais?", "S", "N" );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JRadioGroup<String, String> rgTipoRel = null;

	private JRadioGroup<String, String> rgOrdem = null;

	private JRadioGroup<String, String> rgOrdem2 = null;

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcTipoCli = new ListaCampos( this );

	private ListaCampos lcSetor = new ListaCampos( this );

	private ListaCampos lcVendedor = new ListaCampos( this );

	private ListaCampos lcBanco = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private ListaCampos lcTipoCob = new ListaCampos( this );

	private boolean bPref = false;

	public FRCobranca() {

		super( false );
		setTitulo( "Receber/Recebidas" );
		setAtribos( 40, 50, 640, 470 );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		cbObs.setVlrString( "S" );
		cbImpTotDia.setVlrString( "S" );

		montaListaCampos();
		montaRadioGroups();
		montaTela();

	}

	private void montaListaCampos() {

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCnpjCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );

		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "C�d.setor", ListaCampos.DB_PK, false ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descri��o do setor", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "VD" );
		lcSetor.setReadOnly( true );
		txtCodSetor.setTabelaExterna( lcSetor );
		txtCodSetor.setFK( true );
		txtCodSetor.setNomeCampo( "CodSetor" );

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CodVend" );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco.", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "C�d.pl.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descri��o do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );

		txtCodTpCob.setNomeCampo( "CodTipoCob" );
		lcTipoCob.add( new GuardaCampo( txtCodTpCob, "CodTipoCob", "C�d.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTpCob, "DescTipoCob", "Descri��o do tipo de cobran�a.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTpCob.setTabelaExterna( lcTipoCob );
		txtCodTpCob.setListaCampos( lcTipoCob );
		txtDescTpCob.setListaCampos( lcTipoCob );
		txtCodTpCob.setFK( true );

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli );
		txtCodTipoCli.setFK( true );
		txtCodTipoCli.setNomeCampo( "CodTipoCli" );

	}

	private void montaRadioGroups() {

		Vector<String> vVals0 = new Vector<String>();
		Vector<String> vLabs0 = new Vector<String>();
		vLabs0.addElement( "Contas a receber" );
		vLabs0.addElement( "Contas recebidas" );
		vLabs0.addElement( "Ambas as contas" );
		vVals0.addElement( "R" );
		vVals0.addElement( "P" );
		vVals0.addElement( "A" );
		rgTipoRel = new JRadioGroup<String, String>( 3, 1, vLabs0, vVals0 );
		rgTipoRel.addRadioGroupListener( this );

		Vector<String> vVals1 = new Vector<String>();
		Vector<String> vLabs1 = new Vector<String>();
		vLabs1.addElement( "Emiss�o" );
		vLabs1.addElement( "Vencimento" );
		vLabs1.addElement( "Pagamento" );
		vVals1.addElement( "E" );
		vVals1.addElement( "V" );
		vVals1.addElement( "P" );
		rgOrdem = new JRadioGroup<String, String>( 1, 3, vLabs1, vVals1 );
		rgOrdem.setVlrString( "V" );

		Vector<String> vVals2 = new Vector<String>();
		Vector<String> vLabs2 = new Vector<String>();
		vLabs2.addElement( "Raz�o Social" );
		vLabs2.addElement( "Documento" );
		vVals2.addElement( "R" );
		vVals2.addElement( "D" );
		rgOrdem2 = new JRadioGroup<String, String>( 1, 2, vLabs2, vVals2 );
		rgOrdem2.setVlrString( "R" );
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		adic( new JLabelPad( "Periodo:" ), 7, 0, 80, 20 );
		adic( lbLinha, 7, 20, 247, 40 );
		
		adic( new JLabelPad( "De:", SwingConstants.CENTER ), 17, 30, 30, 20 );
		adic( txtDataini, 47, 30, 80, 20 );
		
		adic( new JLabelPad( "At�:", SwingConstants.CENTER ), 127, 30, 30, 20 );
		adic( txtDatafim, 157, 30, 80, 20 );

		adic( new JLabelPad( "Modo:" ), 257, 0, 170, 20 );
		adic( rgTipoRel, 257, 20, 160, 90 );		

		adic( cbObs, 420, 15, 180, 20 );
		adic( cbImpTotDia, 420, 40, 180, 20 );
//		adic( cbParPar, 420, 65, 250, 20 );			

		adic( new JLabelPad( "Primeira ordem:" ), 7, 110, 390, 20 );
		adic( rgOrdem, 7, 130, 410, 30 );
		adic( new JLabelPad( "Segunda ordem:" ), 7, 160, 390, 20 );
		adic( rgOrdem2, 7, 180, 410, 30 );

		adic( new JLabelPad( "C�d.cli." ), 7, 220, 80, 20 );
		adic( txtCodCli, 7, 240, 80, 20 );
		adic( new JLabelPad( "Raz�o social do cliente" ), 90, 220, 210, 20 );
		adic( txtRazCli, 90, 240, 210, 20 );
		adic( new JLabelPad( "C�d.setor" ), 7, 260, 80, 20 );
		adic( txtCodSetor, 7, 280, 80, 20 );
		adic( new JLabelPad( "Descri��o do setor" ), 90, 260, 210, 20 );
		adic( txtDescSetor, 90, 280, 210, 20 );
		adic( new JLabelPad( "C�d.comis." ), 7, 300, 80, 20 );
		adic( txtCodVend, 7, 320, 80, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 90, 300, 210, 20 );
		adic( txtNomeVend, 90, 320, 210, 20 );

		adic( new JLabelPad( "Cod.Tp.Cob" ), 303, 220, 80, 20 );
		adic( txtCodTpCob, 303, 240, 80, 20 );
		adic( new JLabelPad( "Descri��o do Tipo de Cobran�a" ), 386, 220, 220, 20 );
		adic( txtDescTpCob, 386, 240, 220, 20 );
		adic( new JLabelPad( "C�d.banco" ), 303, 260, 80, 20 );
		adic( txtCodBanco, 303, 280, 80, 20 );
		adic( new JLabelPad( "Descri��o do banco" ), 386, 260, 220, 20 );
		adic( txtDescBanco, 386, 280, 220, 20 );
		adic( new JLabelPad( "C�d.pl.pag." ), 303, 300, 80, 20 );
		adic( txtCodPlanoPag, 303, 320, 80, 20 );
		adic( new JLabelPad( "Descri��o do plano de pagamento" ), 386, 300, 220, 20 );
		adic( txtDescPlanoPag, 386, 320, 220, 20 );

		adic( new JLabelPad( "C�d.Tipo.Cli" ), 7, 340, 80, 20 );
		adic( txtCodTipoCli, 7, 360, 80, 20 );
		adic( new JLabelPad( "Descri��o do tipo de cliente" ), 90, 340, 210, 20 );
		adic( txtDescTipoCli, 90, 360, 210, 20 );


	}

	public void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		String sFrom = " ";
		String sFiltro = "";
		String sTipoRel = null;
		String sTitRel = null;
		String sTitRel1 = null;
		String sDtTotal = "";
		String sDtPago = "";
		String sObs = "";
		String sImpTotDia = "";
		String sCodBanco = null;
		String sCodTpCob = null;
		String sCodPlanoPag = null;
		String sCampoTotal = null;
		String sCampoOrdem = null;
		String sCampoOrdem2 = null;
		int iCodCli = 0;
		int iCodSetor = 0;
		int iCodVend = 0;
		int iParans = 0;

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		if ( ( ( rgTipoRel.getVlrString().equals( "R" ) ) || ( rgTipoRel.getVlrString().equals( "A" ) ) ) && ( rgOrdem.getVlrString().equals( "P" ) ) ) {
			Funcoes.mensagemInforma( this, "N�o pode ser ordenado por data de pagamento!" );
			return;
		}

		sTipoRel = rgTipoRel.getVlrString();

		sTitRel = "Relat�rio de cobran�a";

		if ( rgOrdem.getVlrString().equals( "P" ) ) {
			sTitRel1 = "PAGAMENTO";
//			if ( "S".equals( cbParPar.getVlrString() ) ) {
//				sCampoOrdem = "L.DATALANCA";
//			}
//			else {
				sCampoOrdem = "IT.DTPAGOITREC";
//			}
			sCampoTotal = "DTPAGOITREC";
		}
		else if ( rgOrdem.getVlrString().equals( "E" ) ) {
			sTitRel1 = "EMISS�O";
			sCampoOrdem = "IT.DTITREC";
			sCampoTotal = "DTITREC";
		}
		else {
			sTitRel1 = "VENCIMENTO";
			sCampoOrdem = "IT.DTVENCITREC";
			sCampoTotal = "DTVENCITREC";
		}

		if ( rgOrdem2.getVlrString().equals( "R" ) ) {
			sCampoOrdem2 = "C.RAZCLI";
		}
		else {
			sCampoOrdem2 = "R.DOCREC";
		}

		iCodCli = txtCodCli.getVlrInteger().intValue();
		iCodSetor = txtCodSetor.getVlrInteger().intValue();
		iCodVend = txtCodVend.getVlrInteger().intValue();
		sCodBanco = txtCodBanco.getVlrString();
		sCodPlanoPag = txtCodPlanoPag.getVlrString();
		sCodTpCob = txtCodTpCob.getVlrString();

		if ( iCodCli != 0 ) {
			sWhere.append( " AND R.CODEMPCL=? AND R.CODFILIALCL=? AND R.CODCLI=? " );
			sFiltro = "Cli.: " + iCodCli + " - " + Funcoes.copy( txtRazCli.getVlrString(), 30 ).trim();
		}
		if ( iCodSetor != 0 ) {
			if ( bPref ) {
				sWhere.append( " AND C.CODEMPSR=? AND C.CODFILIALSR=? AND C.CODSETOR=?" );
			}
			else {
				sWhere.append( " AND VD.CODEMPSE=? AND VD.CODFILIALSE=? AND VD.CODSETOR=? AND  VD.CODEMP=R.CODEMPVD AND VD.CODFILIAL=R.CODFILIALVD AND VD.CODVEND=R.CODVEND " );
				sFrom = ",VDVENDEDOR VD ";
			}
			sFiltro += ( !sFiltro.equals( "" ) ? " / " : "" ) + "Setor: " + iCodSetor + " - " + Funcoes.copy( txtDescSetor.getVlrString(), 30 ).trim();
		}
		if ( iCodVend != 0 ) {
			sWhere.append( " AND R.CODEMPVD=? AND R.CODFILIALVD=? AND R.CODVEND=? " );
			sFiltro += ( !sFiltro.equals( "" ) ? " / " : "" ) + "Repr.: " + iCodVend + " - " + Funcoes.copy( txtNomeVend.getVlrString(), 30 ).trim();
		}
		if ( sCodBanco.length() > 0 ) {
			sWhere.append( " AND IT.CODEMPBO=? AND IT.CODFILIALBO=? AND IT.CODBANCO=? " );
			sFiltro += ( !sFiltro.equals( "" ) ? " / " : "" ) + "Repr.: " + sCodBanco + " - " + Funcoes.copy( txtCodBanco.getVlrString(), 30 ).trim();
		}
		if ( sCodPlanoPag.length() > 0 ) {
			sWhere.append( " AND R.CODEMPPG=? AND R.CODFILIALPG=? AND R.CODPLANOPAG=? " );
			sFiltro += ( !sFiltro.equals( "" ) ? " / " : "" ) + "Repr.: " + sCodPlanoPag + " - " + Funcoes.copy( txtCodPlanoPag.getVlrString(), 30 ).trim();
		}
		if ( sCodTpCob.length() > 0 ) {
			sWhere.append( "AND IT.CODEMPTC=? AND IT.CODFILIALTC=? AND IT.CODTIPOCOB=? " );
			sFiltro += ( !sFiltro.equals( "" ) ? " / " : "" ) + "Repr.: " + sCodTpCob + " - " + Funcoes.copy( txtCodTpCob.getVlrString(), 30 ).trim();
		}

		if ( txtCodTipoCli.getVlrInteger() > 0 ) {
			sWhere.append( "AND C.CODEMPTI=? AND C.CODFILIALTI=? AND C.CODTIPOCLI=? " );
			sFiltro += ( !sFiltro.equals( "" ) ? " / " : "" ) + "Tipo Cli.: " + txtCodTipoCli.getVlrString() + " - " + Funcoes.copy( txtDescTipoCli.getVlrString(), 30 ).trim();
		}


		sSQL.append( "SELECT IT.DTITREC, IT.DTVENCITREC,IT.NPARCITREC,R.CODVENDA,R.CODCLI,C.RAZCLI, C.NOMECLI, C.DDDCLI, C.FONECLI, " );

/*		if ( "S".equals( cbParPar.getVlrString() ) ) {
			sSQL.append( "(CASE WHEN L.CODLANCA IS NOT NULL AND L.CODLANCA=" );
			sSQL.append( "(SELECT MIN(L2.CODLANCA) FROM FNLANCA L2 " );
			sSQL.append( "WHERE L2.CODEMPRC=IT.CODEMP AND L2.CODFILIALRC=IT.CODFILIAL AND " );
			sSQL.append( "L2.CODREC=IT.CODREC AND L2.NPARCITREC=IT.NPARCITREC" );
			if ( "P".equals( rgOrdem.getVlrString() ) ) {
				sSQL.append( " AND L2.DATALANCA BETWEEN ? AND ? " );
			}
			sSQL.append( ") THEN IT.VLRPARCITREC " );
			sSQL.append( "ELSE 0 END) VLRPARCITREC, " );
			sSQL.append( "COALESCE(L.VLRLANCA,IT.VLRPAGOITREC) VLRPAGOITREC, " );
			sSQL.append( "(CASE WHEN L.CODLANCA IS NOT NULL AND L.CODLANCA=" );
			sSQL.append( "(SELECT MIN(L2.CODLANCA) FROM FNLANCA L2 " );
			sSQL.append( "WHERE L2.CODEMPRC=IT.CODEMP AND L2.CODFILIALRC=IT.CODFILIAL AND " );
			sSQL.append( "L2.CODREC=IT.CODREC AND L2.NPARCITREC=IT.NPARCITREC" );
			if ( "P".equals( rgOrdem.getVlrString() ) ) {
				sSQL.append( " AND L2.DATALANCA BETWEEN ? AND ? " );
			}
			sSQL.append( ") THEN IT.VLRAPAGITREC " );
			sSQL.append( "ELSE 0 END) VLRAPAGITREC, " );
			sSQL.append( "COALESCE(L.DATALANCA,IT.DTPAGOITREC) DTPAGOITREC, " );
		}
		else {
*/
		sSQL.append( "IT.VLRPARCITREC, " );
			sSQL.append( "IT.VLRPAGOITREC, " );
			sSQL.append( "IT.VLRAPAGITREC, " );
			sSQL.append( "IT.DTPAGOITREC, " );

		//}

		sSQL.append( "R.DOCREC, IT.OBSITREC, " );
		sSQL.append( "(SELECT V.STATUSVENDA FROM VDVENDA V " );
		sSQL.append( "WHERE V.FLAG IN " + AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) );
		sSQL.append( " AND V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA AND V.CODVENDA=R.CODVENDA AND V.TIPOVENDA=R.TIPOVENDA) " );
		sSQL.append( "FROM FNRECEBER R,VDCLIENTE C " );
		sSQL.append( sFrom );
		sSQL.append( ",FNITRECEBER IT " );
/*
		if ( "S".equals( cbParPar.getVlrString() ) ) {
			sSQL.append( " LEFT OUTER JOIN FNLANCA L ON " );
			sSQL.append( "L.CODEMPRC=IT.CODEMP AND L.CODFILIALRC=IT.CODFILIAL AND " );
			sSQL.append( "L.CODREC=IT.CODREC AND L.NPARCITREC=IT.NPARCITREC " );
		}
*/
		sSQL.append( "WHERE R.FLAG IN " + AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) );
		sSQL.append( "AND R.CODEMP=? AND R.CODFILIAL=? AND " + sCampoOrdem + " BETWEEN ? AND ? " );
		sSQL.append( "AND IT.STATUSITREC IN (?,?,?) AND R.CODREC = IT.CODREC " );
		sSQL.append( "AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL " );
		sSQL.append( "AND C.CODEMP = R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI " );
		sSQL.append( sWhere.toString() );
		sSQL.append( " ORDER BY C.CODCLI, " + sCampoOrdem + " ," + sCampoOrdem2 );

		try {
			iParans = 1;
			ps = con.prepareStatement( sSQL.toString() );

	/*		if ( "S".equals( cbParPar.getVlrString() ) && "P".equals( rgOrdem.getVlrString() ) ) {
				ps.setDate( iParans++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( iParans++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setDate( iParans++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( iParans++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			}
*/
			ps.setInt( iParans++, Aplicativo.iCodEmp );
			ps.setInt( iParans++, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setDate( iParans++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( iParans++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			if ( sTipoRel.equals( "R" ) ) {
				ps.setString( iParans++, "R1" );
				ps.setString( iParans++, "RL" );
				ps.setString( iParans++, "RL" );
			}
			else if ( sTipoRel.equals( "P" ) ) {
				ps.setString( iParans++, "RP" );
				ps.setString( iParans++, "RL" );
				ps.setString( iParans++, "RL" );
			}
			else if ( sTipoRel.equals( "A" ) ) {
				ps.setString( iParans++, "R1" );
				ps.setString( iParans++, "RL" );
				ps.setString( iParans++, "RP" );
			}

			if ( iCodCli != 0 ) {
				ps.setInt( iParans++, Aplicativo.iCodEmp );
				ps.setInt( iParans++, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setInt( iParans++, iCodCli );
			}
			if ( iCodSetor != 0 ) {
				ps.setInt( iParans++, Aplicativo.iCodEmp );
				ps.setInt( iParans++, ListaCampos.getMasterFilial( "VDSETOR" ) );
				ps.setInt( iParans++, iCodSetor );
			}
			if ( iCodVend != 0 ) {
				ps.setInt( iParans++, Aplicativo.iCodEmp );
				ps.setInt( iParans++, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
				ps.setInt( iParans++, iCodVend );
			}
			if ( sCodBanco.length() > 0 ) {
				ps.setInt( iParans++, Aplicativo.iCodEmp );
				ps.setInt( iParans++, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( iParans++, sCodBanco );
			}
			if ( sCodPlanoPag.length() > 0 ) {
				ps.setInt( iParans++, Aplicativo.iCodEmp );
				ps.setInt( iParans++, ListaCampos.getMasterFilial( "FNPLANOPAG" ) );
				ps.setString( iParans++, sCodPlanoPag );
			}
			if ( sCodTpCob.length() > 0 ) {
				ps.setInt( iParans++, Aplicativo.iCodEmp );
				ps.setInt( iParans++, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
				ps.setString( iParans++, sCodTpCob );
			}
			if ( txtCodTipoCli.getVlrInteger() > 0 ) {
				ps.setInt( iParans++, lcTipoCli.getCodEmp() );
				ps.setInt( iParans++, lcTipoCli.getCodFilial() );
				ps.setInt( iParans++, txtCodTipoCli.getVlrInteger() );
			}


			rs = ps.executeQuery();

			String sCab = " Per�odo de " + txtDataini.getVlrString() + " at� " + txtDatafim.getVlrString() ;

			imprimirGrafico( bVisualizar, rs, sCab );

			rs.close();
			ps.close();
			con.commit();

		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de contas a receber!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}		
	}

	private void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "layout/rel/REL_COB_01.jasper", "Relat�rio de cobran�a", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o do relat�rio de cobran�a!" + err.getMessage(), true, con, err );				
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcSetor.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcBanco.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcTipoCli.setConexao( cn );
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if ( evt.getIndice() == 1 ) {
			rgOrdem.setVlrString( "P" );
		}
		else {
			rgOrdem.setVlrString( "V" );
		}
	}
}
