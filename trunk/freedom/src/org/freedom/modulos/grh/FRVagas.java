/**
 * @version 12/08/2008 <BR>
 * @author Setpoint Inform�tica Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FRVagas.java <BR>
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
 * Tela para cadastro de estruturas de produtos.
 * 
 */
package org.freedom.modulos.grh;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.sf.jasperreports.engine.JasperPrintManager;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRVagas extends FRelatorio{

	private static final long serialVersionUID = 1L;
	
	public FRVagas(){
		
		super( false );
		setTitulo( "Lista de Vagas" );
		setAtribos( 50, 50, 350, 200 );
	}

	public void imprimir( boolean bVisualizar ) {
		
		FPrinterJob dlGr = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer sWhere =  new StringBuffer();
		
		try {
			
			sql.append( "SELECT E.NOMEEMPR, F.DESCFUNC, T.DESCTURNO, V.FAIXASALINI, V.FAIXASALFIM " );
			sql.append( "FROM RHVAGA V, RHEMPREGADOR E, RHFUNCAO F, RHTURNO T " );
			sql.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND " );
			sql.append( "V.CODEMPEM=E.CODEMP AND V.CODFILIALEM=E.CODFILIAL AND V.CODEMPR=E.CODEMPR AND " );
			sql.append( "V.CODEMPFC=F.CODEMP AND V.CODFILIALFC=F.CODFILIAL AND V.CODFUNC=F.CODFUNC AND  " );
			sql.append( "V.CODEMPTN=T.CODEMP AND V.CODFILIALTN=T.CODFILIAL AND V.CODTURNO=T.CODTURNO " );
			sql.append( "ORDER BY E.NOMEEMPR, F.DESCFUNC, T.DESCTURNO, V.FAIXASALINI, V.FAIXASALFIM " );
			
			//	sql.append( "AND V.CODFUNC=? AND V.CODEMPR=? " );
			
			
		} catch ( Exception e ) {
			
		} 

		dlGr = new FPrinterJob( "relatorios/grhVagas.jasper", "Lista de Vagas", "", rs, null, this );
	 
		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na gera��o do rel�torio!" + e.getMessage(), true, con, e );
			}
		}
	}
}
