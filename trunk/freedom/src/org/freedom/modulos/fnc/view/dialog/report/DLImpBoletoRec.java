/**
 * @version 18/11/2008 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLImpBoletoRec.java <BR>
 * 
 *                         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                         Coment�rios sobre a classe...
 */

package org.freedom.modulos.fnc.view.dialog.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JInternalFrame;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.object.Empresa;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;

public class DLImpBoletoRec extends FDialogo {

	private static final long serialVersionUID = 1L;

	public JTextFieldPad txtCodModBol = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtImpInst = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldFK txtPreImpModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldFK txtClassModBol = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private ListaCampos lcModBol = new ListaCampos( this );

	private int codRec;

	private int parcRec;

	DbConnection con = null;

	private JInternalFrame owner = null;

	public DLImpBoletoRec( JInternalFrame owner, DbConnection con, int codRec, int parcRec ) {

		super();
		setAtribos( 370, 150 );
		setTitulo( "Impress�o de Boleto" );

		this.con = con;
		this.codRec = codRec;
		this.parcRec = parcRec;
		this.owner = owner;

		montaListaCampos();
		montaTela();

	}

	private void montaListaCampos() {

		/********************
		 * MODELO DE BOLETO *
		 ********************/
		lcModBol.add( new GuardaCampo( txtCodModBol, "CodModBol", "C�d.mod.", ListaCampos.DB_PK, true ) );
		lcModBol.add( new GuardaCampo( txtImpInst, "ImpInfoParc", "Imp.Info.", ListaCampos.DB_SI, false ) );
		lcModBol.add( new GuardaCampo( txtDescModBol, "DescModBol", "Descri��o do modelo de boleto", ListaCampos.DB_SI, false ) );
		lcModBol.add( new GuardaCampo( txtPreImpModBol, "PreImpModBol", "Pr�-impr.", ListaCampos.DB_SI, false ) );
		lcModBol.add( new GuardaCampo( txtClassModBol, "ClassModBol", "Classe do modelo", ListaCampos.DB_SI, false ) );
		lcModBol.setReadOnly( true );
		lcModBol.montaSql( false, "MODBOLETO", "FN" );
		txtCodModBol.setTabelaExterna( lcModBol, null );
		txtCodModBol.setFK( true );
		txtCodModBol.setNomeCampo( "CodModBol" );
		lcModBol.setConexao( con );
	}

	private void montaTela() {

		adic( new JLabelPad( "C�d.Mod.Bol." ), 7, 10, 80, 20 );
		adic( txtCodModBol, 7, 30, 80, 20 );
		adic( new JLabelPad( "Descri��o do modelo de boleto" ), 90, 10, 220, 20 );
		adic( txtDescModBol, 90, 30, 250, 20 );
	}

	public void imprimir() {

		if ( txtCodModBol.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Modelo de boleto n�o selecionado!" );
			txtCodModBol.requestFocus();
			return;
		}

		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		sSQL.append( "SELECT IM.DVCONVCOB, ITR.DTVENCITREC,ITR.NPARCITREC,ITR.VLRAPAGITREC, ITR.VLRPARCITREC, MB.PREIMPMODBOL, " );
		sSQL.append( "ITR.VLRDESCITREC, (ITR.VLRJUROSITREC+ITR.VLRMULTAITREC) VLRMULTA, R.DOCREC,ITR.CODBANCO, B.DVBANCO, " );
		sSQL.append( "B.IMGBOLBANCO LOGOBANCO01, COALESCE(B.IMGBOLBANCO2,B.IMGBOLBANCO) LOGOBANCO02, B.IMGBOLBANCO LOGOBANCO03, B.IMGBOLBANCO LOGOBANCO04, IM.CODCARTCOB, " );
		sSQL.append( "MB.ESPDOCMODBOL ESPDOC, MB.ACEITEMODBOL ACEITE, MB.MDECOB, ITR.dtitrec AS DTEMITVENDA, " );
		sSQL.append( "C.RAZCLI,C.CPFCLI,C.CNPJCLI, C.ENDCLI,C.NUMCLI,C.COMPLCLI,C.CEPCLI,C.BAIRCLI, " );
		sSQL.append( "C.CIDCLI,C.UFCLI, C.ENDCOB,C.NUMCOB,C.COMPLCOB,C.CEPCOB,C.BAIRCOB,C.CIDCOB,C.UFCOB, P.CODMOEDA, " );
		sSQL.append( "C.PESSOACLI, (ITR.DTVENCITREC-CAST('07.10.1997' AS DATE)) FATVENC, M.CODFBNMOEDA, " );
		sSQL.append( "CT.AGENCIACONTA, IM.NUMCONTA, MB.DESCLPMODBOL, MB.INSTPAGMODBOL, IM.CONVCOB, ITR.DESCPONT, C.INSCCLI, ITR.OBSITREC OBS, TCO.VARIACAOCARTCOB, R.CODREC, itr.seqnossonumero " );
		sSQL.append( "FROM VDCLIENTE C, FNRECEBER R, SGPREFERE1 P, FNMOEDA M, FNBANCO B, FNMODBOLETO MB, " );
		sSQL.append( "FNITMODBOLETO IM, FNITRECEBER ITR, SGFILIAL F, FNCONTA CT, FNCARTCOB TCO " );
		sSQL.append( "WHERE " );
		sSQL.append( "C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI " );
		sSQL.append( "AND P.CODEMP=R.CODEMP AND P.CODFILIAL=R.CODFILIAL " );
		sSQL.append( "AND F.CODEMP=R.CODEMP AND F.CODFILIAL=R.CODFILIAL " );
		sSQL.append( "AND M.CODEMP=P.CODEMPMO AND M.CODFILIAL=P.CODFILIALMO AND M.CODMOEDA=P.CODMOEDA " );
		sSQL.append( "AND B.CODEMP=ITR.CODEMPBO AND B.CODFILIAL=ITR.CODFILIALBO AND B.CODBANCO=ITR.CODBANCO " );
		sSQL.append( "AND IM.CODEMP=MB.CODEMP AND IM.CODFILIAL=MB.CODFILIAL AND IM.CODMODBOL=MB.CODMODBOL " );
		sSQL.append( "AND IM.CODEMPBO=B.CODEMP AND IM.CODFILIALBO=B.CODFILIAL AND IM.CODBANCO=B.CODBANCO " );
		sSQL.append( "AND IM.CODEMPCB=ITR.CODEMPCB AND IM.CODFILIALCB=ITR.CODFILIALCB AND IM.CODCARTCOB=ITR.CODCARTCOB " );
		sSQL.append( "AND CT.CODEMP=IM.CODEMPCT AND CT.CODFILIAL=IM.CODFILIALCT AND CT.NUMCONTA=IM.NUMCONTA " );
		sSQL.append( "AND ITR.CODEMP=R.CODEMP AND ITR.CODFILIAL=R.CODFILIAL AND ITR.CODREC=R.CODREC " );
		sSQL.append( "AND ITR.STATUSITREC IN ('R1','RL','RB') " );
		sSQL.append( "AND MB.CODEMP=? AND MB.CODFILIAL=? AND MB.CODMODBOL=?" );
		sSQL.append( "AND R.CODEMP=? AND R.CODFILIAL=? AND R.CODREC=? AND ITR.nparcitrec=? " );
		sSQL.append( "AND TCO.CODEMP=ITR.CODEMPCB AND TCO.CODFILIAL=ITR.CODFILIALCB AND TCO.CODCARTCOB=ITR.CODCARTCOB ");

		try {

			System.out.println( "QUERY DUPLICATA:" + sSQL.toString() );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, txtCodModBol.getVlrInteger() );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 6, codRec );
			ps.setInt( 7, parcRec );
			rs = ps.executeQuery();

			imprimeGrafico( true, rs );

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados do boleto!" );
		}
	}

	private HashMap<String, Object> getParametros() {
		
		StringBuilder sql = new StringBuilder();
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		try {

			// Carregando parametros da empresa

			Empresa empresa = new Empresa( con );
	
			parametros.put( "CODEMP", Aplicativo.iCodEmp );
			parametros.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			parametros.put( "IMPDOC", txtImpInst.getVlrString() );
	
			if ( Aplicativo.empresa != null ) {
				parametros.put( "RAZEMP", empresa.getAll().get( "RAZEMP" ) );
//				parametros.put( "RAZEMP", empresa.getAll().get( "RAZEMP" ) );
			}

			// Carregando parametros prefer�nciais
			
			sql.append( "SELECT P.TPNOSSONUMERO FROM SGPREFERE1 P " );
			sql.append( "WHERE P.CODEMP=? AND P.CODFILIAL=?" );

			PreparedStatement ps = Aplicativo.getInstace().getConexao().prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				
				parametros.put( "TPNOSSONUMERO", rs.getString( "TPNOSSONUMERO" ) );
			
			}
			
			rs.close();
			ps.close();			
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return parametros;
	}

	private void imprimeGrafico( final boolean bVisualizar, final ResultSet rs ) {

		String classBol = "";
		if ( txtClassModBol.getVlrString().indexOf( '/', 0 ) == -1 ) {
			classBol = "layout/bol/" + txtClassModBol.getVlrString();
		}
		else {
			classBol = txtClassModBol.getVlrString();
		}

		FPrinterJob dlGr = new FPrinterJob( classBol, "Boleto", null, rs, getParametros(), owner );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro ao tentar imprimir boleto!" + err.getMessage(), true, con, err );
			}
		}
	}
}
