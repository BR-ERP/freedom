/*
 * Created on 17/06/2005
 * Autor: anderson 
 * Descri��o:  
 */
package org.freedom.modulos.pcp;

import org.freedom.objetos.ObjetoModeloLote;

/**
 * @author anderson
 */
public class ObjetoModLote extends ObjetoModeloLote {

	public ObjetoModLote() {
		
		adicOpcao( "C�digo do produto", VLR_CODPROD, new Integer( 8 ) );
		adicOpcao( "Dia", VLR_DIA, new Integer( 2 ) );
		adicOpcao( "M�s", VLR_MES, new Integer( 2 ) );
		adicOpcao( "Ano", VLR_ANO, new Integer( 4 ) );
		adicOpcao( "N�mero da produ��o no dia", VLR_NPRODDIA, new Integer( 5 ) );
		adicOpcao( "Lote principal", VLR_LOTEPRINC, new Integer( 13 ) );
	}
}
