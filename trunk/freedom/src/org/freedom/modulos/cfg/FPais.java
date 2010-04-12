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

package org.freedom.modulos.cfg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FPais extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNomePais = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSiglaPais2 = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtSiglaPais3 = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );
	
	private JTextFieldPad txtDDIPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodBacenPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );
	
	private JTextFieldPad txtCodEANPais = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	public FPais() {

		super();
		setTitulo( "Cadastro de Paises" );
		setAtribos( 50, 50, 410, 165 );

		lcCampos.setUsaME( false );

		adicCampo( txtCodPais, 7, 20, 70, 20, "CodPais", "C�d.pais", ListaCampos.DB_PK, true );
		adicCampo( txtNomePais, 80, 20, 177, 20, "NomePais", "Nome do pais", ListaCampos.DB_SI, true );
		adicCampo( txtSiglaPais2, 260, 20, 40, 20, "Sigla2cPais", "Sigla", ListaCampos.DB_SI, true );
		adicCampo( txtSiglaPais3, 303, 20, 40, 20, "Sigla3cPais", "Sigla", ListaCampos.DB_SI, true );
		adicCampo( txtDDIPais, 345, 20, 40, 20, "DDIPais", "DDI", ListaCampos.DB_SI, false );
		adicCampo( txtCodBacenPais, 7, 60, 70, 20, "CodBacenPais", "Cd.Bacen", ListaCampos.DB_SI, false );
		adicCampo( txtCodEANPais, 80, 60, 70, 20, "CodEANPais", "Cd.EAN", ListaCampos.DB_SI, false );
				
		setListaCampos( true, "PAIS", "SG" );
		
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
			imp.setTitulo( "Relat�rio de paises cadastrados" );
			
			String sSQL = "SELECT CODPAIS,NOMEPAIS,SIGLAPAIS,DDIPAIS FROM SGPAIS ORDER BY NOMEPAIS";
			
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
					imp.say( 2, "C�d.pais" );
					imp.say( 15, "Nome" );
					imp.say( 80, "Sigla" );
					imp.say( 100, "DDI" );
					
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, StringFunctions.replicate( "-", 135 ) );
					
				}
				
				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodPais" ) != null ? rs.getString( "CodPais" ) : ""  );
				imp.say( 15, rs.getString( "NomePais" ) != null ? rs.getString( "NomePais" ) : ""  );
				imp.say( 80, rs.getString( "SiglaPais" ) != null ? rs.getString( "SiglaPais" ) : ""  );
				imp.say( 100, rs.getString( "DDIPais" ) != null ? rs.getString( "DDIPais" ) : "" );
				
				
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
			Funcoes.mensagemErro( this, "Erro consulta tabela de paises!" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
		
	}
	
}
