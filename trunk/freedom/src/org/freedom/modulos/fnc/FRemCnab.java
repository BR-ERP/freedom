/**
 * @version 07/2007 <BR>
 * @author Setpoint Inform�tica Ltda. / Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRemCnab.java <BR>
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
 * Tela de remessa de arquivo de CNAB.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.CnabUtil.Reg;
import org.freedom.modulos.fnc.CnabUtil.Reg1;
import org.freedom.modulos.fnc.CnabUtil.Reg3P;
import org.freedom.modulos.fnc.CnabUtil.Reg3Q;
import org.freedom.modulos.fnc.CnabUtil.Reg3R;
import org.freedom.modulos.fnc.CnabUtil.Reg3S;
import org.freedom.modulos.fnc.CnabUtil.Reg3T;
import org.freedom.modulos.fnc.CnabUtil.Reg3U;
import org.freedom.modulos.fnc.CnabUtil.Reg5;
import org.freedom.modulos.fnc.FbnUtil.StuffCli;
import org.freedom.modulos.fnc.FbnUtil.StuffRec;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;

import static org.freedom.modulos.fnc.FbnUtil.*; 


public class FRemCnab extends FRemFBN {
	
	public static final long serialVersionUID = 1L;
	
	private final CnabUtil cnabutil = new CnabUtil();

	public FRemCnab() {

		super( TIPO_FEBRABAN_CNAB );
	}
	
	private Reg1 getReg1() {
		
		Reg1 reg = cnabutil.new Reg1();
		
		reg.setCodBanco( txtCodBanco.getVlrString() );
		//reg.setLoteServico( loteServico );
		//reg.setTipoOperacao( tipoOperacao );
		reg.setFormaLancamento( "00" );
		reg.setTipoInscEmp( 2 );
		reg.setCpfCnpjEmp( (String) prefs.get( EPrefs.CNPFEMP ) );
		reg.setCodConvBanco( (String) prefs.get( EPrefs.CODCONV ) );
		reg.setAgencia( (String) prefs.get( EPrefs.AGENCIA ) );
		reg.setDigAgencia( (String) prefs.get( EPrefs.DIGAGENCIA ) );
		reg.setConta( (String) prefs.get( EPrefs.NUMCONTA ) );
		reg.setDigConta((String) prefs.get( EPrefs.DIGCONTA ) );
		reg.setDigAgConta( (String) prefs.get( EPrefs.DIGAGCONTA ) );
		reg.setRazEmp( (String) prefs.get( FbnUtil.EPrefs.NOMEEMP ) );
		reg.setMsg1( null );
		reg.setMsg2( null );
		reg.setNrRemRet( 0 );
		reg.setDataRemRet( Calendar.getInstance().getTime() );
		reg.setDataCred( null );
		
		return reg;
	}
	
	private Reg3P getReg3P( final StuffRec rec ) {
		
		Reg3P reg = cnabutil.new Reg3P();

		reg.setCodBanco( txtCodBanco.getVlrString() );
		//reg.setLoteServico( loteServico );
		//reg.setSeqLote( seqLote );
		//reg.setCodMovimento( codMovimento );
		
		/*reg.setAgencia( agencia );
		reg.setDigAgencia( digAgencia );
		reg.setConta( conta );
		reg.setDigConta( digConta );
		reg.setDigAgConta( digAgConta );
		reg.setIdentTitulo( identTitulo );
		reg.setCodCarteira( codCarteira );
		reg.setFormaCadTitulo( formaCadTitulo );
		reg.setTipoDoc( tipoDoc );
		reg.setIdentEmitBol( identEmitBol );
		reg.setIdentDist( identDist );
		reg.setDocCobranca( docCobranca );
		reg.setDtVencTitulo( dtVencTitulo );
		reg.setVlrTitulo( vlrTitulo );
		reg.setAgenciaCob( agenciaCob );
		reg.setDigAgenciaCob( digAgenciaCob );
		reg.setEspecieTit( especieTit );
		reg.setAceite( aceite );
		reg.setDtEmitTit( dtEmitTit );
		reg.setCodJuros( codJuros );
		reg.setDtJuros( dtJuros );
		reg.setVlrJurosTaxa( vlrJurosTaxa ); 
		reg.setCodDesc( codDesc );
		reg.setDtDesc( dtDesc );
		reg.setVlrpercConced( vlrpercConced );
		reg.setVlrIOF( vlrIOF );
		reg.setVlrAbatimento( vlrAbatimento );
		reg.setIdentTitEmp( identTitEmp );
		reg.setCodProtesto( codProtesto );
		reg.setDiasProtesto( diasProtesto );
		reg.setCodBaixaDev( codBaixaDev );
		reg.setDiasBaixaDevol( diasBaixaDevol );  
		reg.setCodMoeda( codMoeda );
		reg.setContrOperCred( contrOperCred );*/
		
		return reg;
	}
	
	private Reg3Q getReg3Q() {
		
		Reg3Q reg = null;

		/*reg.setCodBanco( codBanco );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLote );
		reg.setCodMovimento( codMovimento );
		
		reg.setTipoInscCli( tipoInscCli );
		reg.setCpfCnpjCli( cpfCnpjCli );
		reg.setRazCli( razCli );
		reg.setEndCli( endCli );
		reg.setBairCli( bairCli );
		reg.setCepCli( cepCli );
		reg.setCidCli( cidCli );
		reg.setUfCli( ufCli );
		reg.setTipoInscAva( tipoInscAva );
		reg.setCpfCnpjAva( cpfCnpjAva );
		reg.setRazAva( razAva );
		reg.setCodCompensacao( codCompensacao );
		reg.setNossoNumero( nossoNumero );*/
		
		return reg;
	}
	
	private Reg3R getReg3R() {
		
		Reg3R reg = null;

		/*reg.setCodBanco( codBanco );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLote );
		reg.setCodMovimento( codMovimento );
		
		reg.setCodDesc2( codDesc2 );
		reg.setDataDesc2( dataDesc2 );
		reg.setVlrPercConced2( vlrPercConced2 );
		reg.setCodDesc3( codDesc3 );
		reg.setDataDesc3( dataDesc3 );
		reg.setVlrPercConced3( vlrPercConced3 );
		reg.setCodMulta( codMulta );
		reg.setDataMulta( dataMulta );
		reg.setVlrPercMulta( vlrPercMulta );
		reg.setMsgSacado( msgSacado );
		reg.setMsg3( msg3 );
		reg.setMsg4( msg4 );
		reg.setCodBancoDeb( codBancoDeb );
		reg.setAgenciaDeb( agenciaDeb );
		reg.setContaDeb( contaDeb );
		reg.setCodOcorrSacado( codOcorrSacado );*/
		
		return reg;
	}
	
	private Reg3S getReg3S() {
		
		Reg3S reg = null;

		/*reg.setCodBanco( codBanco );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLote );
		reg.setCodMovimento( codMovimento );
		
		reg.setTipoImpressao( tipoImpressao );
		reg.setLinhaImp( linhaImp );
		reg.setMsgImp( msgImp );
		reg.setTipoChar( tipoChar );		
		reg.setMsg5( msg5 );
		reg.setMsg6( msg6 );
		reg.setMsg7( msg7 );
		reg.setMsg8( msg8 );
		reg.setMsg9( msg9 );*/
		
		return reg;
	}
	
	private Reg3T getReg3T() {
		
		Reg3T reg = null;

		/*reg.setCodBanco( codBanco );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLote );
		reg.setCodMovimento( codMovimento );
		
		reg.setAgencia( agencia );
		reg.setDigAgencia( digAgencia );
		reg.setConta( conta );
		reg.setDigConta( digConta );
		reg.setDigAgConta( digAgConta );
		reg.setIdentTitBanco( identTitBanco );
		reg.setCarteira( carteira );
		reg.setDocCob( docCob );
		reg.setDataVencTit( dataVencTit );
		reg.setVlrTitulo( vlrTitulo );
		reg.setCodBanco( codBanco );
		reg.setAgenciaCob( agenciaCob );
		reg.setDigAgenciaCob( digAgenciaCob );
		reg.setIdentTitEmp( identTitEmp );
		reg.setCodMoeda( codMoeda );
		reg.setTipoInscCli( tipoInscCli );
		reg.setCpfCnpjCli( cpfCnpjCli );
		reg.setRazCli( razCli );
		reg.setContratoCred( contratoCred );
		reg.setVlrTarifa( vlrTarifa ); 
		reg.setCodRejeicoes( codRejeicoes );*/
		
		return reg;
	}
	
	private Reg3U getReg3U() {
		
		Reg3U reg = null;

		/*reg.setCodBanco( codBanco );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLote );
		reg.setCodMovimento( codMovimento );
		
		reg.setVlrJurosMulta( vlrJurosMulta );
		reg.setVlrDesc( vlrDesc );
		reg.setVrlAbatCancel( vrlAbatCancel );
		reg.setVlrIOF( vlrIOF );
		reg.setVlrPago( vlrPago );
		reg.setVlrLiqCred( vlrLiqCred );
		reg.setVlrOutrasDesp( vlrOutrasDesp );
		reg.setVlrOutrosCred( vlrOutrosCred );
		reg.setDataOcorr( dataOcorr );
		reg.setDataEfetvCred( dataEfetvCred );
		reg.setCodOcorrSac( codOcorrSac );
		reg.setDataOcorrSac( dataOcorrSac );
		reg.setVlrOcorrSac( vlrOcorrSac );
		reg.setCompOcorrSac( compOcorrSac );
		reg.setCodBancoCompens( codBancoCompens );
		reg.setNossoNrCompens( nossoNrCompens );*/
		
		return reg;
	}
	
	private Reg5 getReg5() {
		
		Reg5 reg = null;
		
		/*reg.setCodBanco( codBanco );
		reg.setLoteServico( loteServico );
		reg.setQtdRegistros( qtdRegistros );
		reg.setQtdSimples( qtdSimples );
		reg.setVlrSimples( vlrSimples );
		reg.setQtdVinculado( qtdVinculado );
		reg.setVlrVinculado( vlrVinculado );
		reg.setQtdCalculado( qtdCalculado );
		reg.setVlrCalculado( vlrCalculado );
		reg.setQtdDescontado( qtdDescontado );
		reg.setVlrDescontado( vlrDescontado );
		reg.setAvisoLanca( avisoLanca );*/
		
		return reg;
	}
	
	protected boolean execExporta() {
		
		boolean retorno = false;
		String sFileName = null;
		File fileSiacc = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		HashSet<StuffCli> hsCli = new HashSet<StuffCli>();
		HashSet<StuffRec> hsRec = new HashSet<StuffRec>();
	
		if ( consisteExporta( hsCli, hsRec ) ) {
			
			retorno = setPrefs();
			
			if ( retorno ) {
				
				lbStatus.setText( "     criando arquivo ..." );
		
				FileDialog fileDialogSiacc = null;
				fileDialogSiacc = new FileDialog( Aplicativo.telaPrincipal, "Exportar arquivo.", FileDialog.SAVE );
				sFileName = "remessa"+prefs.get( SiaccUtil.EPrefs.NROSEQ )+".txt";
				fileDialogSiacc.setFile( sFileName );
				fileDialogSiacc.setVisible( true );
		
				if ( fileDialogSiacc.getFile() == null ) {
					lbStatus.setText( "" );
					return retorno;
				}
		
				sFileName = fileDialogSiacc.getDirectory() + fileDialogSiacc.getFile();
		
				fileSiacc = new File( sFileName );
		
				try {
					fileSiacc.createNewFile();
					fw = new FileWriter( fileSiacc );
					bw = new BufferedWriter( fw );
		
					lbStatus.setText( "     gravando arquivo ..." );
					retorno = gravaRemessa( bw, hsCli, hsRec );
				} catch ( IOException ioError ) {
					Funcoes.mensagemErro( this, "Erro Criando o arquivo!\n " + sFileName + "\n" + ioError.getMessage() );
					lbStatus.setText( "" );
					return retorno;
				}
				
				lbStatus.setText( "     pronto ..." );
				//atualizaSitremessaExp(hsCli, hsRec);
			}
			
		}
		return retorno;
	}
	
	private boolean gravaRemessa( final BufferedWriter bw, final HashSet< StuffCli > hsCli, final HashSet< StuffRec > hsRec ) {
		
		boolean retorno = false;
		
		try {
			
			ArrayList< Reg > registros = new ArrayList< Reg >();
			
			registros.add( getReg1() );
			
			for ( StuffRec rec : hsRec ) {
				registros.add( getReg3P( rec ) );
				registros.add( getReg3Q() );
				registros.add( getReg3R() );
				registros.add( getReg3S() );
				registros.add( getReg3T() );
				registros.add( getReg3U() );
			}
			
			registros.add( getReg5() );
			
			for ( Reg reg : registros ) {
				bw.write( reg.getLine() );
			}
			
			bw.flush();
			bw.close();
		} 
		catch ( ExceptionCnab e ) {
			Funcoes.mensagemErro( this, e.getMessage() );
			lbStatus.setText( "" );
			retorno = false;
		}
		catch ( IOException ioError ) {
			Funcoes.mensagemErro( this, "Erro gravando no arquivo!\n" + ioError.getMessage() );
			lbStatus.setText( "" );
			retorno = false;
		}
		
		return retorno;
	}

	public void imprimir( boolean visualizar ) {

		ResultSet rs = null;
		String sDtFiltro = "E".equals( rgData.getVlrString() ) ? "IR.DTITREC" : "IR.DTVENCITREC";
		PreparedStatement ps = null;

		if ( txtCodBanco.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "C�digo do banco � requerido!" );
			return;
		}

		try {

			StringBuilder sSQL = new StringBuilder();

			sSQL.append( "SELECT IR.CODREC, IR.NPARCITREC, R.DOCREC, R.CODCLI, C.RAZCLI, IR.DTITREC, IR.DTVENCITREC," );
			sSQL.append( "IR.VLRAPAGITREC, FC.AGENCIACLI, FC.IDENTCLI, COALESCE(FR.SITREMESSA,'00') SITREMESSA, " );
			sSQL.append( "FR.SITRETORNO, COALESCE(COALESCE(FR.STIPOFEBRABAN,FC.STIPOFEBRABAN),'02') STIPOFEBRABAN, " );
			sSQL.append( "COALESCE(FC.TIPOREMCLI,'B') TIPOREMCLI, C.PESSOACLI, C.CPFCLI, C.CNPJCLI " );
			sSQL.append( "FROM VDCLIENTE C," );
			sSQL.append( "FNRECEBER R LEFT OUTER JOIN FNFBNCLI FC ON " );
			sSQL.append( "FC.CODEMP=R.CODEMPCL AND FC.CODFILIAL=R.CODFILIALCL AND FC.CODCLI=R.CODCLI ," );
			sSQL.append( "FNITRECEBER IR LEFT OUTER JOIN FNFBNREC FR ON " );
			sSQL.append( "FR.CODEMP=IR.CODEMP AND FR.CODFILIAL=IR.CODFILIAL AND " );
			sSQL.append( "FR.CODREC=IR.CODREC AND FR.NPARCITREC=IR.NPARCITREC AND " );
			sSQL.append( "FR.CODEMPBO=IR.CODEMPBO AND FR.CODFILIALBO=IR.CODFILIALBO AND FR.CODBANCO=IR.CODBANCO " );
			sSQL.append( "WHERE R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC AND " );
			sSQL.append( "C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI AND " );
			sSQL.append( sDtFiltro );
			sSQL.append( " BETWEEN ? AND ? AND IR.STATUSITREC IN ('R1','RL') AND " );
			sSQL.append( "IR.CODEMPBO=? AND IR.CODFILIALBO=? AND IR.CODBANCO=? " );
			sSQL.append( where );
			sSQL.append( "ORDER BY C.RAZCLI, R.CODREC, IR.NPARCITREC " );
			ps = con.prepareStatement( sSQL.toString() );

			ps.setDate( 1, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, Aplicativo.iCodFilial );
			ps.setInt( 5, txtCodBanco.getVlrInteger() );

			rs = ps.executeQuery();

			HashMap< String, Object > hParam = new HashMap< String, Object >();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "REPORT_CONNECTION", con );

			FPrinterJob dlGr = new FPrinterJob( "relatorios/RemSiacci.jasper", "RELAT�RIO DE REMESSA", null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
}
