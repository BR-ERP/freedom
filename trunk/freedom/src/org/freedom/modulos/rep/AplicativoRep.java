package org.freedom.modulos.rep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.EmailBean;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.rep.RPPrefereGeral.EPrefere;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipal;

public class AplicativoRep extends AplicativoPD {

	public AplicativoRep( String sIcone, String sSplash, int iCodSis, String sDescSis, int iCodModu, String sDescModu, String sDirImagem, FPrincipal telaP, Class cLogin ) {

		super( sIcone, sSplash, iCodSis, sDescSis, iCodModu, sDescModu, sDirImagem, telaP, cLogin );

	}

	@ Override
	protected void carregaCasasDec() {
		
		try {

			PreparedStatement ps = con.prepareStatement( "SELECT CASASDEC,CASASDECFIN FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				casasDec = rs.getInt( "CASASDEC" );
				casasDecFin = rs.getInt( "CASASDECFIN" );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "N�o foi poss�vel obter o n�mero de casas decimais!\n" + err.getMessage(), true, con, err );
		} 
	}

	@ Override
	protected void carregaBuscaProd() {
		
		bBuscaProdSimilar = false;
		bBuscaCodProdGen = false;
	}

	@Override
	public void createEmailBean() {		

		List<Object> prefere = RPPrefereGeral.getPrefere( con );

		EmailBean mail = new EmailBean();
		mail.setHost( (String) prefere.get( EPrefere.SERVIDORSMTP.ordinal() ) );
		mail.setPorta( (Integer) prefere.get( EPrefere.PORTASMTP.ordinal() ) );
		mail.setUsuario( (String) prefere.get( EPrefere.USUARIOSMTP.ordinal() ) );
		mail.setSenha( ((String) prefere.get( EPrefere.SENHASMTP.ordinal() )).trim() );
		mail.setDe( mail.getEmailEmp( con ) );
		mail.setAutentica( (String) prefere.get( EPrefere.AUTENTICASMTP.ordinal() ) );
		mail.setSsl( (String) prefere.get( EPrefere.SSLSMTP.ordinal() ) );
		
		setEmailBean( mail );
	}

	public static void atualizaEmailBean( Connection con ) {
		
		List<Object> prefere = RPPrefereGeral.getPrefere( con );

		EmailBean mail = new EmailBean();
		mail.setHost( (String) prefere.get( EPrefere.SERVIDORSMTP.ordinal() ) );
		mail.setPorta( (Integer) prefere.get( EPrefere.PORTASMTP.ordinal() ) );
		mail.setUsuario( (String) prefere.get( EPrefere.USUARIOSMTP.ordinal() ) );
		mail.setSenha( ((String) prefere.get( EPrefere.SENHASMTP.ordinal() )).trim() );
		mail.setDe( mail.getEmailEmp( con ) );
		mail.setAutentica( (String) prefere.get( EPrefere.AUTENTICASMTP.ordinal() ) );
		mail.setSsl( (String) prefere.get( EPrefere.SSLSMTP.ordinal() ) );
		
		setEmailBean( mail );
	}
}
