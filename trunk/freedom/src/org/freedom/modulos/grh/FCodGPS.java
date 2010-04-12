/**
 * @version 25/06/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe:
 * @(#)FPais.java <BR>
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

package org.freedom.modulos.grh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.ImprimeOS;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.FDados;

public class FCodGPS extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodGPS = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescGPS = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	public FCodGPS() {

		super();
		setTitulo( "Cadastro de C�digos de Pagamento de GPS" );
		setAtribos( 50, 50, 410, 165 );

		lcCampos.setUsaME( false );

		adicCampo( txtCodGPS, 7, 20, 70, 20, "CodGPS", "C�d.GPS", ListaCampos.DB_PK, true );
		adicCampo( txtDescGPS, 80, 20, 280, 20, "DescGPS", "Descri��o do c�digo de pagamento GPS/INSS", ListaCampos.DB_SI, true );
				
		setListaCampos( true, "CODGPS", "RH" );
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		
		setImprimir( true );
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		
		super.actionPerformed( evt );
		
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		ImprimeOS imp = new ImprimeOS( "", con );	
		int linPag = imp.verifLinPag() - 1;	
		
		try {
		
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de C�digos de pagamento de GPS/INSS" );
			
			String sSQL = "SELECT CODGPS,DESCGPS FROM RHCODGPS ORDER BY CODGPS";
			
			ps = con.prepareStatement( sSQL );
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
				
				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 136, false );
					
					imp.say( 0, imp.normal() );					
					imp.say( 2, "C�d.GPS" );
					imp.say( 15, "Descri��o" );
					
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, StringFunctions.replicate( "-", 135 ) );
					
				}
				
				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodGPS" ) != null ? rs.getString( "CodGPS" ) : ""  );
				imp.say( 15, rs.getString( "DescGPS" ) != null ? rs.getString( "DescGPS" ) : ""  );				
				
			}

			imp.pulaLinha( 1, imp.normal() );
			imp.say( imp.pRow() + 0, 0, StringFunctions.replicate( "=", 135 ) );
			
			imp.eject();
			imp.fechaGravacao();

			rs.close();
			ps.close();
			
			con.commit();
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de codigos de gps!" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
		
	}
	
}
