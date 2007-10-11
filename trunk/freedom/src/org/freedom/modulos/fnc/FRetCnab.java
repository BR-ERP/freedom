/**
 * @version 07/2007 <BR>
 * @author Setpoint Inform�tica Ltda. / Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRetCnab.java <BR>
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
 * Tela de retorno de arquivo de CNAB.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.Cursor;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.CnabUtil.Receber;
import org.freedom.modulos.fnc.CnabUtil.Reg;
import org.freedom.modulos.fnc.CnabUtil.Reg3P;
import org.freedom.modulos.fnc.CnabUtil.Reg5;
import org.freedom.telas.Aplicativo;


public class FRetCnab extends FRetFBN {

	private static final long serialVersionUID = 1l;
	
	private final CnabUtil cnabutil = new CnabUtil();
	
	public FRetCnab() {
		
		super();
	}

	/* (non-Javadoc)
	 * @see org.freedom.modulos.fnc.FRetFBN#execImportar()
	 */
	@Override
	protected boolean execImportar() {

		boolean retorno = true;
		ArrayList<Reg> registros = new ArrayList<Reg>();

		setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );

		if ( "".equals( txtCodBanco.getVlrString() ) ) {
			Funcoes.mensagemInforma( this, "Selecione o Banco!!" );
			txtCodBanco.requestFocus();
		}
		else {

			lbStatus.setText( "     Lendo do arquivo ..." );

			FileDialog fileDialogCnab = null;
			fileDialogCnab = new FileDialog( Aplicativo.telaPrincipal, "Importar arquivo." );
			//fileDialogSiacc.setFile( "*.cmp" );
			fileDialogCnab.setVisible( true );

			if ( fileDialogCnab.getFile() == null ) {
				lbStatus.setText( "" );
				retorno = false;
			}
			else {

				String sFileName = fileDialogCnab.getDirectory() + fileDialogCnab.getFile();
				File fileCnab = new File( sFileName );

				if ( fileCnab.exists() ) {

					try {

						FileReader fileReaderCnab = new FileReader( fileCnab );

						if ( fileReaderCnab == null ) {
							Funcoes.mensagemInforma( this, "Arquivo n�o encontrado" );
						}
						else {
							if ( leArquivo( fileReaderCnab, registros ) ) {

								if ( ! montaGrid( registros ) ) {
									Funcoes.mensagemInforma( this, "Nenhum registro de retorno encontrado." );
									lbStatus.setText( "" );
									retorno = false;
								}
							}
						}
					} catch ( IOException ioError ) {
						Funcoes.mensagemErro( this, "Erro ao ler o arquivo: " + sFileName + "\n" + ioError.getMessage() );
						lbStatus.setText( "" );
						retorno = false;
					}
				}
				else {
					Funcoes.mensagemErro( this, "Arquivo " + sFileName + " n�o existe!" );
					retorno = false;
				}
			}
		}

		setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
		
		return retorno;
	}	
	
	private boolean leArquivo( final FileReader fileReaderCnab, final ArrayList<Reg> list ) throws IOException {

		boolean retorno = true;
		char tipo;
		char seguimento;
		String line = null;
		BufferedReader in = new BufferedReader( fileReaderCnab );

		try {
			
			learquivo : while ( ( line = in.readLine() ) != null ) {

				tipo = line.charAt( 7 );
				
				switch ( tipo ) {
					case '1' :
						list.add( cnabutil.new Reg1( line ) );
						break;
					case '3' :
						
						seguimento = line.charAt( 13 );
						
						switch ( seguimento ) {
							case 'P' :
								list.add( cnabutil.new Reg3P( line ) );
								break;
							case 'Q' :
								list.add( cnabutil.new Reg3Q( line ) );
								break;
							case 'R' :
								list.add( cnabutil.new Reg3R( line ) );
								break;
							case 'S' :
								list.add( cnabutil.new Reg3S( line ) );
								break;
							case 'T' :
								list.add( cnabutil.new Reg3T( line ) );
								break;
							case 'U' :
								list.add( cnabutil.new Reg3U( line ) );
								break;
							default :
								break learquivo;
						}
						
						break;
					case '5' :
						list.add( cnabutil.new Reg5( line ) );
						break;
					default :
						break learquivo;
				}
			}

			lbStatus.setText( "     Arquivo lido ..." );
		} catch ( ExceptionCnab e ) {
			Funcoes.mensagemErro( this, "Erro lendo o arquivo!\n" + e.getMessage() );
			e.printStackTrace();
			retorno = false;
			lbStatus.setText( "" );
		}

		in.close();

		return retorno;
	}

	private boolean montaGrid( ArrayList<Reg> registros ) {

		boolean retorno = true;
		int row = 0;

		if ( registros != null ) {

			lbStatus.setText( "     Carregando tabela ..." );
			
			tab.limpa();

			try {

				List<Object> infocli = new ArrayList<Object>();
				String regJ = null;
				Reg3P reg3P = null;
				
				for ( Reg reg : registros ) {

					if ( reg instanceof Reg3P ) {

						reg3P = (Reg3P) reg;

						infocli.clear();

						if ( infocli.size() == EColInfoCli.values().length ) {

							Receber rec = reg3P.getIdentTitEmp();

							tab.adicLinha();
							//tab.setValor( (String) infocli.get( EColInfoCli.RAZCLI.ordinal() ), row, EColTab.RAZCLI.ordinal() ); // Raz�o social do cliente
							//tab.setValor( (Integer) infocli.get( EColInfoCli.CODCLI.ordinal() ), row, EColTab.CODCLI.ordinal() ); // C�d.cli.							
							tab.setValor( rec.getCodrec(), row, EColTab.CODREC.ordinal() ); // C�d.rec.							
							tab.setValor( rec.getDocrec(), row, EColTab.DOCREC.ordinal() ); // Doc
							tab.setValor( rec.getNrparcrec(), row, EColTab.NRPARC.ordinal() ); // Nro.Parc.							
							tab.setValor( Funcoes.bdToStr( rec.getValorApagar() ), row, EColTab.VLRAPAG.ordinal() ); // Valor
							tab.setValor( rec.getEmissao(), row, EColTab.DTREC.ordinal() ); // Emiss�o
							tab.setValor( rec.getVencimento(), row, EColTab.DTVENC.ordinal() ); // Vencimento
							//tab.setValor( Funcoes.bdToStr( (BigDecimal) ( (RegF) reg ).getValorDebCred() ), row, EColTab.VLRPAG.ordinal() ); // Valor pago
							//tab.setValor( (Date) ( (RegF) reg ).getDataVenc(), row, EColTab.DTPAG.ordinal() ); // Data pgto.
							tab.setValor( rec.getConta(), row, EColTab.NUMCONTA.ordinal() ); // Conta
							tab.setValor( rec.getPlanejamento(), row, EColTab.CODPLAN.ordinal() ); // Planejamento
							//tab.setValor( Funcoes.bdToStr( new BigDecimal( 0 ) ), row, EColTab.VLRDESC.ordinal() ); // VLRDESC
							//tab.setValor( Funcoes.bdToStr( new BigDecimal( 0 ) ), row, EColTab.VLRJUROS.ordinal() ); // VLRJUROS
							tab.setValor( "BAIXA AUTOM�TICA CNAB", row, EColTab.OBS.ordinal() ); // HIST�RICO
							//tab.setValor( (String) infocli.get( EColInfoCli.TIPOFEBRABAN.ordinal() ), row, EColTab.TIPOFEBRABAN.ordinal() );
							//tab.setValor( ( (RegF) reg ).getCodRetorno(), row, EColTab.CODRET.ordinal() ); // c�digo retorno
							//tab.setValor( getMenssagemRet( ( (RegF) reg ).getCodRetorno() ), row, EColTab.MENSSAGEM.ordinal() ); // Menssagem de erro*/
							
						}
					}
					else if ( reg instanceof Reg5 ) {
						row++;
					}
				}

				if ( row > 0 ) {
					lbStatus.setText( "     Tabela carregada ..." );
				}
				else {
					lbStatus.setText( "     Informa��es do cliente n�o encontradas ..." );
				}
				
			} catch ( Exception e ) {
				retorno = false;
				Funcoes.mensagemErro( this, "Erro no carregamento da tabela!\n" + e.getMessage() );
				e.printStackTrace();
				lbStatus.setText( "" );
			}
		}
		else {
			retorno = false;
		}

		return retorno;
	}
}
