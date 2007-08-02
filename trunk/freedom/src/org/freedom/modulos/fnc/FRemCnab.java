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
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Boleto;
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
import org.freedom.modulos.fnc.FbnUtil.EPrefs;
import org.freedom.modulos.fnc.FbnUtil.StuffCli;
import org.freedom.modulos.fnc.FbnUtil.StuffRec;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;


public class FRemCnab extends FRemFBN {
	
	public static final long serialVersionUID = 1L;
	
	private final CnabUtil cnabutil = new CnabUtil();
	
	private int loteServico = 1;
	
	private int seqLoteServico = 1;
	
	private int codMovimento = 1;

	public FRemCnab() {

		super( TIPO_FEBRABAN_CNAB );
	}
	
	private String[] getContaCli( final int codcli ) {
		
		String[] args = new String[5];
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT FC.AGENCIACLI, FC.NUMCONTACLI " );
			sql.append( "FROM FNFBNCLI FC " );
			sql.append( "WHERE FC.CODEMP=? AND FC.CODFILIAL=? AND FC.CODCLI=?  " );
			sql.append( "AND FC.CODEMPPF=? AND FC.CODFILIALPF=? " );
			sql.append( "AND FC.CODEMPBO=? AND FC.CODFILIALBO=? AND FC.CODBANCO=? AND FC.TIPOFEBRABAN=?" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNFBNCLI" ) );
			ps.setInt( 3, codcli );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
			ps.setInt( 6, Aplicativo.iCodEmp );
			ps.setInt( 7, ListaCampos.getMasterFilial( "FNFBNCLI" ) );
			ps.setString( 8, txtCodBanco.getVlrString() );
			ps.setString( 9, TIPO_FEBRABAN_CNAB );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				if ( rs.getString( "AGENCIACLI" ) != null ) {
					String[] agencia = Boleto.getCodSig( rs.getString( "AGENCIACLI" ) );
					args[ 0 ] = agencia[ 0 ];
					args[ 1 ] = agencia[ 1 ];
				}
				else {
					args[ 0 ] = "";
					args[ 1 ] = "";
				}
				
				if ( rs.getString( "NUMCONTACLI" ) != null ) {
					String[] conta = Boleto.getCodSig( rs.getString( "NUMCONTACLI" ) );
					args[ 2 ] = conta[ 0 ];
					args[ 2 ] = conta[ 1 ];
				}
				else {
					args[ 2 ] = "";
					args[ 2 ] = "";
				}

				args[ 4 ] = "";
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return args;
	}
	
	private String[] getCliente( final int codcli ) {
		
		String[] args = new String[10];
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT C.RAZCLI, C.ENDCLI, C.NUMCLI, C.BAIRCLI, C.CEPCLI, C.CIDCLI, C.UFCLI, C.CNPJCLI, C.CPFCLI " );
			sql.append( "FROM VDCLIENTE C " );
			sql.append( "WHERE C.CODEMP=? AND C.CODFILIAL=? AND C.CODCLI=?  " );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, codcli );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				for ( int i=0; i<args.length-1; i++ ) {
					
					args[ i ] = rs.getString( i + 1 );					
				}
				
				args[ DadosCliente.CNPJCPF.ordinal() ] = args[ DadosCliente.CNPJ.ordinal() ] == null ? "2" : "1";
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return args;
	}
	
	private Reg1 getReg1() {
		
		Reg1 reg = cnabutil.new Reg1();
		
		reg.setCodBanco( txtCodBanco.getVlrString() );
		reg.setLoteServico( loteServico );
		reg.setTipoOperacao( null );
		reg.setFormaLancamento( "00" );
		reg.setTipoInscEmp( 2 );
		reg.setCpfCnpjEmp( (String) prefs.get( EPrefs.CNPFEMP ) );
		reg.setCodConvBanco( (String) prefs.get( EPrefs.CODCONV ) );
		reg.setAgencia( (String) prefs.get( EPrefs.AGENCIA ) );
		reg.setDigAgencia( (String) prefs.get( EPrefs.DIGAGENCIA ) );
		reg.setConta( (String) prefs.get( EPrefs.NUMCONTA ) );
		reg.setDigConta( (String) prefs.get( EPrefs.DIGCONTA ) );
		reg.setDigAgConta( null );
		reg.setRazEmp( (String) prefs.get( FbnUtil.EPrefs.NOMEEMP ) );
		reg.setMsg1( null );
		reg.setMsg2( null );
		reg.setNrRemRet( (Integer) prefs.get(FbnUtil.EPrefs.NROSEQ) );
		reg.setDataRemRet( Calendar.getInstance().getTime() );
		reg.setDataCred( null );
		
		return reg;
	}
	
	private Reg3P getReg3P( final StuffRec rec ) {
		
		Reg3P reg = cnabutil.new Reg3P();

		reg.setCodBanco( txtCodBanco.getVlrString() );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLoteServico++ );
		reg.setCodMovimento( codMovimento );

		String[] args = getContaCli( Integer.parseInt( rec.getArgs()[ EColTab.COL_CODCLI.ordinal() ] ) );
		
		reg.setAgencia( args[ 0 ] );
		reg.setDigAgencia( args[ 1 ] );
		reg.setConta( args[ 2 ] );
		reg.setDigConta( args[ 3 ] );
		reg.setDigAgConta( args[ 4 ] );
		/*reg.setIdentTitulo( Boleto.geraNossoNumero( 
				"modalidade", 
				"convenio", 
				rec.getArgs()[ EColTab.COL_CODREC.ordinal() ], 
				rec.getArgs()[ EColTab.COL_NRPARC.ordinal() ] );*/
		reg.setCodCarteira( 0 );
		reg.setFormaCadTitulo( 0 );
		reg.setTipoDoc( 0 );
		reg.setIdentEmitBol( 0 );
		reg.setIdentDist( 0 );
		reg.setDocCobranca( (String) rec.getArgs()[ EColTab.COL_DOCREC.ordinal() ] );
		reg.setDtVencTitulo( Funcoes.strDateToDate( rec.getArgs()[ EColTab.COL_DTVENC.ordinal() ] ) );
		reg.setVlrTitulo( new BigDecimal( rec.getArgs()[ EColTab.COL_VLRAPAG.ordinal() ] ) );		
		reg.setAgenciaCob( null );
		reg.setDigAgenciaCob( null );
		reg.setEspecieTit( 2 );
		reg.setAceite( 'A' );
		reg.setDtEmitTit( Funcoes.strDateToDate( rec.getArgs()[ EColTab.COL_DTREC.ordinal() ] ) );
		reg.setCodJuros( 3 );
		reg.setDtJuros( null );
		reg.setVlrJurosTaxa( new BigDecimal( 0 ) ); 		
		reg.setCodDesc( 6 );
		reg.setDtDesc( null );
		reg.setVlrpercConced( new BigDecimal( 0 ) );
		reg.setVlrIOF( new BigDecimal( 0 ) );
		reg.setVlrAbatimento( new BigDecimal( 0 ) );
		reg.setIdentTitEmp( rec.getArgs()[ EColTab.COL_CODREC.ordinal() ] );
		reg.setCodProtesto( 3 );
		reg.setDiasProtesto( 0 );
		reg.setCodBaixaDev( 2 );
		reg.setDiasBaixaDevol( 0 );  
		reg.setCodMoeda( 9 );
		reg.setContrOperCred( null );
		
		return reg;
	}
	
	private Reg3Q getReg3Q( final StuffRec rec ) {
		
		Reg3Q reg = cnabutil.new Reg3Q();

		reg.setCodBanco( txtCodBanco.getVlrString() );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLoteServico++ );
		reg.setCodMovimento( codMovimento );
		
		String[] dadosCliente = getCliente( Integer.parseInt( rec.getArgs()[ EColTab.COL_CODCLI.ordinal() ] ) );
		
		reg.setTipoInscCli( Integer.parseInt( dadosCliente[ DadosCliente.CNPJCPF.ordinal()] ) );
		
		if ( 2 == reg.getTipoInscCli() ) {
			reg.setCpfCnpjCli( dadosCliente[ DadosCliente.CNPJ.ordinal() ] );
		}
		else if ( 1 == reg.getTipoInscCli() ) {
			reg.setCpfCnpjCli( dadosCliente[ DadosCliente.CPF.ordinal() ] );
		}
		else {
			reg.setTipoInscCli( 0 );
			reg.setCpfCnpjCli( "0" );
		}
		
		reg.setRazCli( dadosCliente[ DadosCliente.RAZCLI.ordinal() ] );
		reg.setEndCli( dadosCliente[ DadosCliente.ENDCLI.ordinal() ] );
		reg.setBairCli( dadosCliente[ DadosCliente.BAIRCLI.ordinal() ] );
		reg.setCepCli( dadosCliente[ DadosCliente.CEPCLI.ordinal() ] );
		reg.setCidCli( dadosCliente[ DadosCliente.CIDCLI.ordinal() ] );
		reg.setUfCli( dadosCliente[ DadosCliente.UFCLI.ordinal() ] );		
		reg.setTipoInscAva( 0 );
		reg.setCpfCnpjAva( "0" );		
		reg.setRazAva( null );
		//reg.setCodCompensacao( 0 );
		//reg.setNossoNumero( null );
		
		return reg;
	}
	
	private Reg3R getReg3R( final StuffRec rec ) {
		
		Reg3R reg = cnabutil.new Reg3R();

		reg.setCodBanco( txtCodBanco.getVlrString() );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLoteServico++ );
		reg.setCodMovimento( codMovimento );
		
		reg.setCodDesc2( 6 );
		reg.setDataDesc2( null );
		reg.setVlrPercConced2( new BigDecimal(0) );
		reg.setCodDesc3( 0 );
		reg.setDataDesc3( null );
		reg.setVlrPercConced3( new BigDecimal(0) );
		reg.setCodMulta( 2 );
		reg.setDataMulta( null );
		reg.setVlrPercMulta( new BigDecimal(0) );
		reg.setMsgSacado( null );
		reg.setMsg3( null );
		reg.setMsg4( null );
		
		reg.setCodBancoDeb( null );
		reg.setAgenciaDeb( null );
		reg.setContaDeb( null );
		
		//sreg.setCodOcorrSacado( 0 );
		
		return reg;
	}
	
	private Reg3S getReg3S( final StuffRec rec ) {
		
		Reg3S reg = cnabutil.new Reg3S();

		reg.setCodBanco( txtCodBanco.getVlrString() );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLoteServico++ );
		reg.setCodMovimento( codMovimento );
		
		reg.setTipoImpressao( 3 );
		reg.setLinhaImp( 10 );//verificar...
		reg.setMsgImp( null );
		reg.setTipoChar( 1 );		
		reg.setMsg5( null );//verificar...
		reg.setMsg6( null );//verificar...
		reg.setMsg7( null );//verificar...
		reg.setMsg8( null );//verificar...
		reg.setMsg9( null );//verificar...
		
		return reg;
	}
	
	private Reg3T getReg3T( final StuffRec rec ) {
		
		Reg3T reg = cnabutil.new Reg3T();

		reg.setCodBanco( txtCodBanco.getVlrString() );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLoteServico++ );
		reg.setCodMovimento( codMovimento );
		
		String[] args = getContaCli( Integer.parseInt( rec.getArgs()[ EColTab.COL_CODCLI.ordinal() ] ) );
		
		reg.setAgencia( args[ 0 ] );
		reg.setDigAgencia( args[ 1 ] );
		reg.setConta( args[ 2 ] );
		reg.setDigConta( args[ 3 ] );
		reg.setDigAgConta( args[ 4 ] );
		/*reg.setIdentTitulo( Boleto.geraNossoNumero( 
				"modalidade", 
				"convenio", 
				rec.getArgs()[ EColTab.COL_CODREC.ordinal() ], 
				rec.getArgs()[ EColTab.COL_NRPARC.ordinal() ] );*/
		
		reg.setCarteira( 0 );
		reg.setDocCob( rec.getArgs()[ EColTab.COL_DOCREC.ordinal() ] );
		reg.setDataVencTit( null );
		reg.setVlrTitulo( new BigDecimal(0) );
		
		reg.setCodBanco( null );
		reg.setAgenciaCob( null );
		reg.setDigAgenciaCob( null );
		
		reg.setIdentTitEmp( rec.getArgs()[ EColTab.COL_CODREC.ordinal() ] );
		reg.setCodMoeda( 9 );
		
		String[] dadosCliente = getCliente( Integer.parseInt( rec.getArgs()[ EColTab.COL_CODCLI.ordinal() ] ) );
		
		reg.setTipoInscCli( Integer.parseInt( dadosCliente[ DadosCliente.CNPJCPF.ordinal()] ) );
		
		if ( 2 == reg.getTipoInscCli() ) {
			reg.setCpfCnpjCli( dadosCliente[ DadosCliente.CNPJ.ordinal() ] );
		}
		else if ( 1 == reg.getTipoInscCli() ) {
			reg.setCpfCnpjCli( dadosCliente[ DadosCliente.CPF.ordinal() ] );
		}
		else {
			reg.setTipoInscCli( 0 );
			reg.setCpfCnpjCli( "0" );
		}
		
		reg.setRazCli( dadosCliente[ DadosCliente.RAZCLI.ordinal() ] );	
		reg.setContratoCred( (String) prefs.get( EPrefs.CODCONV ) );
		reg.setVlrTarifa( new BigDecimal(0) ); 
		reg.setCodRejeicoes( null );
		
		return reg;
	}
	
	private Reg3U getReg3U( final StuffRec rec ) {
		
		Reg3U reg = cnabutil.new Reg3U();

		reg.setCodBanco( txtCodBanco.getVlrString() );
		reg.setLoteServico( loteServico );
		reg.setSeqLote( seqLoteServico++ );
		reg.setCodMovimento( codMovimento );
		
		reg.setVlrJurosMulta( new BigDecimal(0) );
		reg.setVlrDesc( new BigDecimal(0) );
		reg.setVlrAbatCancel( new BigDecimal(0) );
		reg.setVlrIOF( new BigDecimal(0) );
		reg.setVlrPago( new BigDecimal(0) );
		reg.setVlrLiqCred( new BigDecimal(0) );
		reg.setVlrOutrasDesp( new BigDecimal(0) );
		reg.setVlrOutrosCred( new BigDecimal(0) );
		reg.setDataOcorr( null );
		reg.setDataEfetvCred( null );
		reg.setCodOcorrSac( null );
		reg.setDataOcorrSac( null );
		reg.setVlrOcorrSac( new BigDecimal(0) );
		reg.setCompOcorrSac( null );
		reg.setCodBancoCompens( null );
		//reg.setNossoNrCompens( null );
		
		return reg;
	}
	
	private Reg5 getReg5() {
		
		Reg5 reg = cnabutil.new Reg5();
		
		reg.setCodBanco( txtCodBanco.getVlrString() );
		reg.setLoteServico( loteServico );
		reg.setQtdRegistros( ++seqLoteServico );
		reg.setQtdSimples( 0 );
		reg.setVlrSimples( new BigDecimal(0) );
		reg.setQtdVinculado( 0 );
		reg.setVlrVinculado( new BigDecimal(0) );
		reg.setQtdCalculado( 0 );
		reg.setVlrCalculado( new BigDecimal(0) );
		reg.setQtdDescontado( 0 );
		reg.setVlrDescontado( new BigDecimal(0) );
		reg.setAvisoLanca( null );
		
		return reg;
	}
	
	protected boolean execExporta() {
		
		boolean retorno = false;
		String sFileName = null;
		BufferedWriter bw = null;
		HashSet<StuffCli> hsCli = new HashSet<StuffCli>();
		HashSet<StuffRec> hsRec = new HashSet<StuffRec>();
	
		if ( consisteExporta( hsCli, hsRec ) ) {
			
			retorno = setPrefs();
			
			if ( retorno ) {
				
				lbStatus.setText( "     criando arquivo ..." );
		
				FileDialog fileDialogCnab = null;
				fileDialogCnab = new FileDialog( Aplicativo.telaPrincipal, "Exportar arquivo.", FileDialog.SAVE );
				sFileName = "remessa"+prefs.get( EPrefs.NROSEQ )+".txt";
				fileDialogCnab.setFile( sFileName );
				fileDialogCnab.setVisible( true );
		
				if ( fileDialogCnab.getFile() == null ) {
					lbStatus.setText( "" );
					return retorno;
				}
		
				sFileName = fileDialogCnab.getDirectory() + fileDialogCnab.getFile();
				
				try {

					File fileCnab = new File( sFileName );
					fileCnab.createNewFile();

					FileWriter fw = new FileWriter( fileCnab );
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
			
			loteServico++;
			seqLoteServico = 1;
			int regs = 0;
			
			ArrayList< Reg > registros = new ArrayList< Reg >();
						
			registros.add( getReg1() );
			
			for ( StuffRec rec : hsRec ) {
				registros.add( getReg3P( rec ) );
				registros.add( getReg3Q( rec ) );
				//registros.add( getReg3R() );
				//registros.add( getReg3S() );
				//registros.add( getReg3T() );
				//registros.add( getReg3U() );
				regs++;
			}
			
			registros.add( getReg5() ); 
			
			for ( Reg reg : registros ) {
				bw.write( reg.getLine() );
			}
			
			bw.flush();
			bw.close();
			
			System.out.println( "[ " + regs + " ] registros gravados." );
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
	
	enum DadosCliente {
		RAZCLI, ENDCLI, NUMCLI, BAIRCLI, CEPCLI, CIDCLI, UFCLI, CNPJ, CPF, CNPJCPF;
	}
}
