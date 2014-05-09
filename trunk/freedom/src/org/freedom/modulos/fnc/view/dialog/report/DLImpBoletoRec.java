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
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.std.view.frame.report.FRBoleto;

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

		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		sql.append( "select im.dvconvcob, itr.dtvencitrec,itr.nparcitrec,itr.vlrapagitrec, itr.vlrparcitrec, mb.preimpmodbol " );
		sql.append( ", f.unidfranqueada, f.razfilial, f.endfilial, f.numfilial, f.cnpjfilial, f.bairfilial, f.complfilial, f.siglauf, f.emailfilial " );
		sql.append( ", itr.vlrdescitrec, (itr.vlrjurositrec+itr.vlrmultaitrec) vlrmulta, r.docrec ");
		sql.append( ", coalesce(bc.codbanco, b.codbanco) codbanco, coalesce(bc.dvbanco,b.dvbanco) dvbanco " );
		sql.append( ", coalesce(bc.imgbolbanco, b.imgbolbanco) logobanco01 ");
		sql.append( ", case when bc.codbanco is null then coalesce(b.imgbolbanco2,b.imgbolbanco) else coalesce(bc.imgbolbanco2,bc.imgbolbanco) end logobanco02 ");
		sql.append( ", coalesce(bc.imgbolbanco, b.imgbolbanco) logobanco03, coalesce(bc.imgbolbanco, b.imgbolbanco) logobanco04 " );
		sql.append( ", im.codcartcob, mb.espdocmodbol espdoc, mb.aceitemodbol aceite, mb.mdecob, itr.dtitrec as dtemitvenda " );
		sql.append( ", c.razcli,c.cpfcli,c.cnpjcli, c.endcli,c.numcli,c.complcli,c.cepcli,c.baircli " );
		sql.append( ", c.cidcli,c.ufcli, coalesce(c.endcob, c.endcli) endcob,c.numcob,c.complcob,c.cepcob,c.baircob,c.cidcob,c.ufcob, p.codmoeda " );
		sql.append( ", c.pessoacli, (itr.dtvencitrec-cast('07.10.1997' as date)) fatvenc, m.codfbnmoeda " );
		sql.append( ", m.singmoeda, m.plurmoeda, m.decsmoeda, m.decpmoeda " );
		sql.append( ", ct.agenciaconta, ct.postoconta, im.numconta, mb.desclpmodbol, mb.instpagmodbol, im.convcob, itr.descpont, c.insccli, itr.obsitrec obs, tco.variacaocartcob ");
		sql.append( ", r.codrec, r.datarec, itr.seqnossonumero, r.vlrrec, mb.txamodbol, itr.doclancaitrec " );
		// implementa��o para permitir a impress�o de boleto pr�-impresso.
		sql.append( ",'' codorc, '' nomeconv, '' obsorc, r.docrec docvenda, 0 reciboitrec ");
		sql.append( ", (select count(*) from fnitreceber itr2 where itr2.codrec=r.codrec and itr2.codemp=r.codemp and itr2.codfilial=r.codfilial) parcs ");
		sql.append( ", r.codcli, c.nomecli, c.rgcli, c.fonecli, c.dddcli, r.codvenda, r.vlrapagrec, '' nomevend, '' nomevend2, '' nomevend3, '' nomevend4 ");
		sql.append( ", f.endfilial, f.numfilial, f.cnpjfilial, f.cepfilial, f.uffilial, f.cidfilial, f.unidfranqueada, f.wwwfranqueadora, f.marcafranqueadora " );
		sql.append( ", mu.nomemunic cidcli ");
		sql.append( " from fnreceber r, sgprefere1 p, fnmoeda m, fnbanco b, fnmodboleto mb " );
		sql.append( ", fnitmodboleto im, fnitreceber itr, sgfilial f, fnconta ct, fncartcob tco, vdcliente c " );
		sql.append( "left outer join sgmunicipio mu on mu.codpais=c.codpais and mu.siglauf=c.siglauf and mu.codmunic=c.codmunic ");
		sql.append( "left outer join fnbanco bc on bc.codemp=b.codempbp and bc.codfilial=b.codfilialbp and bc.codbanco=b.codbancobp " );
		sql.append( "where " );
		sql.append( "c.codemp=r.codempcl and c.codfilial=r.codfilialcl and c.codcli=r.codcli " );
		sql.append( "and p.codemp=r.codemp and p.codfilial=r.codfilial " );
		sql.append( "and f.codemp=r.codemp and f.codfilial=r.codfilial " );
		sql.append( "and m.codemp=p.codempmo and m.codfilial=p.codfilialmo and m.codmoeda=p.codmoeda " );
		sql.append( "and b.codemp=itr.codempbo and b.codfilial=itr.codfilialbo and b.codbanco=itr.codbanco " );
		sql.append( "and im.codemp=mb.codemp and im.codfilial=mb.codfilial and im.codmodbol=mb.codmodbol " );
		sql.append( "and im.codempbo=b.codemp and im.codfilialbo=b.codfilial and im.codbanco=b.codbanco " );
		sql.append( "and im.codempcb=itr.codempcb and im.codfilialcb=itr.codfilialcb and im.codcartcob=itr.codcartcob " );
		sql.append( "and ct.codemp=im.codempct and ct.codfilial=im.codfilialct and ct.numconta=im.numconta " );
		sql.append( "and itr.codemp=r.codemp and itr.codfilial=r.codfilial and itr.codrec=r.codrec " );
		sql.append( "and itr.statusitrec in ('R1','RL','RB','RR') " );
		sql.append( "and mb.codemp=? and mb.codfilial=? and mb.codmodbol=? " );
		sql.append( "and r.codemp=? and r.codfilial=? and r.codrec=? and itr.nparcitrec=? " );
		sql.append( "and tco.codemp=itr.codempcb and tco.codfilial=itr.codfilialcb and tco.codcartcob=itr.codcartcob ");
		sql.append( "and tco.codempbo=b.codemp and tco.codfilialbo=b.codfilial and tco.codbanco=b.codbanco ");

		try {

			System.out.println( "QUERY DUPLICATA:" + sql.toString() );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, txtCodModBol.getVlrInteger() );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 6, codRec );
			ps.setInt( 7, parcRec );
			rs = ps.executeQuery();
		
			if ( txtClassModBol.getVlrString() == null || "".equals( txtClassModBol.getVlrString() )) {
				imprimeTexto( TYPE_PRINT.VIEW, rs );
			}
			else {
				imprimeGrafico( TYPE_PRINT.VIEW, rs );
			}
			
			

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados do boleto!\n"+err.getMessage() );
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void imprimeTexto( final TYPE_PRINT bVisualizar, final ResultSet rs ) throws Exception {

		String sVal = null;
		ImprimeOS imp = null;
		imp = new ImprimeOS( "", con );
		imp.verifLinPag();
		imp.setTitulo( "Boleto" );
		String[] sNat = null;
		
		if ( rs.next() ) {
			
			sNat = new String[ 2 ];
			sNat[ 0 ] = "" ;
			sNat[ 1 ] = "" ;
			sVal = FRBoleto.aplicCampos( rs, sNat, FRBoleto.getMoeda() );

			if ( sVal != null ) {

				String[] sLinhas = ( sVal + " " ).split( "\n" );

				for ( int i = 0; i < sLinhas.length; i++ ) {
					if ( i == 0 ) {
						imp.say( imp.pRow() + 1, 0, imp.normal() + imp.comprimido() + "" );
						imp.say( imp.pRow(), 0, sLinhas[ i ] );
					}
					else {
						imp.say( imp.pRow() + 1, 0, sLinhas[ i ] );
					}
				}
			}
		}

		rs.close();
		con.commit();
		
		imp.fechaGravacao();

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			
			imp.preview( owner );
			
		}
		else {
			imp.print();
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
			parametros.put( "CODEMPMB", Aplicativo.iCodEmp );
			parametros.put( "CODFILIALMB", ListaCampos.getMasterFilial( "FNMODBOLETO" ) );
			parametros.put( "IMPDOC", txtImpInst.getVlrString() );
			parametros.put( "CODMODBOL", txtCodModBol.getVlrInteger() );
			parametros.put( "CODEMPRC", Aplicativo.iCodEmp );
			parametros.put( "CODFILIALRC", ListaCampos.getMasterFilial( "FNRECEBER" ) );
			parametros.put( "CODREC", codRec );
			parametros.put( "NPARCITREC", parcRec  );
			parametros.put( "SUBREPORT_DIR", "org/freedom/layout/dup/" );
			parametros.put( "REPORT_CONNECTION", con.getConnection() );
			
	
			if ( empresa != null ) {
				parametros.put( "RAZEMP", empresa.getAll().get( "RAZEMP" ) );
				if (empresa.getAll().get( "LOGOEMP" )!=null) { 
					parametros.put( "LOGOEMP", empresa.getAll().get( "LOGOEMP" ) );
				}
				if (empresa.getCidFilial()!=null) {
					parametros.put( "CIDFILIAL", empresa.getCidFilial());
				}
			}

			// Carregando parametros prefer�nciais
			
			sql.append( "SELECT COALESCE(P.TPNOSSONUMERO,'D') TPNOSSONUMERO, COALESCE(P.IMPDOCBOL,'N') IMPDOCBOL FROM SGPREFERE1 P " );
			sql.append( "WHERE P.CODEMP=? AND P.CODFILIAL=?" );

			PreparedStatement ps = Aplicativo.getInstace().getConexao().prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				
				parametros.put( "TPNOSSONUMERO", rs.getString( "TPNOSSONUMERO" ) );
				parametros.put( "IMPDOCBOL", rs.getString( "IMPDOCBOL" ) );
			
			}
			
			rs.close();
			ps.close();			
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return parametros;
	}

	private void imprimeGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs ) {

		String classBol = "";
		if ( txtClassModBol.getVlrString().indexOf( '/', 0 ) == -1 ) {
			classBol = "layout/bol/" + txtClassModBol.getVlrString();
		}
		else {
			classBol = txtClassModBol.getVlrString();
		}

		FPrinterJob dlGr = new FPrinterJob( classBol, "Boleto", null, rs, getParametros(), owner );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
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
