package org.freedom.modulos.crm.view.frame.main;

import java.sql.SQLException;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrincipalPD;
import org.freedom.modulos.gpe.dao.DAOBatida;
import org.freedom.modulos.gpe.view.frame.crud.plain.FBatida;


public class FPrincipalCRM extends FPrincipalPD {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FPrincipalCRM( String sDirImagem, String sImgFundo ) {

		super( sDirImagem, sImgFundo );
	}

	@ Override
	public void fecharJanela() {

		// TODO Auto-generated method stub
		super.fecharJanela();
	}

	@ Override
	public void inicializaTela() {
		super.inicializaTela();

	}

	@ Override
	public void remConFilial() {
		super.remConFilial();
	}
	
	@Override
	public void windowOpen() {
		super.windowOpen();
		if (carregaPonto()) {
			showPonto();
		}
	}

	public boolean carregaPonto() {
		boolean result = false;
		DAOBatida daobatida = new DAOBatida( con );
		try {
			daobatida.setPrefs( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGPREFERE3" ) );
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando prefer�ncias!\n" + e.getMessage() );
			e.printStackTrace();
		}
		result = daobatida.carregaPonto( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGUSUARIO" ), Aplicativo.strUsuario );
		return result;
	}
	
	public void showPonto() {
		if (carregaPonto()) {
			FBatida batida = new FBatida();
			criatela( "Digita��o de Livro Ponto", batida, con );
		}
	}
	
}
