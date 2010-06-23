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

package org.freedom.modulos.cfg.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;*/
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDados;

public class FCnae extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCnae = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private JTextFieldPad txtDescCnae = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private JTextFieldPad txtSecCnae = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtDivCnae = new JTextFieldPad( JTextFieldPad.TP_STRING, 5, 0 );
	
	private JTextFieldPad txtGrupCnae = new JTextFieldPad( JTextFieldPad.TP_STRING, 5, 0 );
	
	private JTextFieldPad txtDtrevCnae = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	public FCnae() {

		super();
		setTitulo( "Atividade Cnae" );
		setAtribos( 50, 50, 410, 165 );

		lcCampos.setUsaME( false );

		adicCampo( txtCodCnae, 7, 20, 70, 20, "CodCnae", "C�d.Cnae", ListaCampos.DB_PK, true );
		adicCampo( txtDescCnae, 80, 20, 280, 20, "DescCnae", "Descri��o do Cnae", ListaCampos.DB_SI, true );
		adicCampo( txtSecCnae, 7, 60, 50, 20, "SecCnae", "Se��o", ListaCampos.DB_SI, false );
		adicCampo( txtDivCnae, 60, 60, 60, 20, "DivCnae", "Divis�o", ListaCampos.DB_SI, false );
		adicCampo( txtGrupCnae, 125, 60, 60, 20, "GrupCnae", "Grupo", ListaCampos.DB_SI, false );
		adicCampo( txtDtrevCnae, 190, 60, 90, 20, "DtRevCnae", "Dt.Revis�o", ListaCampos.DB_SI, false );
				
		setListaCampos( true, "CNAE", "SG" );
		
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

		/*PreparedStatement ps = null;
		ResultSet rs = null;
		ImprimeOS imp = new ImprimeOS( "", con );	
		int linPag = imp.verifLinPag() - 1;	
		
		try {
		
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de atividades CNAE" );
			
			String sSQL = "SELECT CODCNAE, DESCCNAE, SIGLAPAIS,DDIPAIS FROM SGPAIS ORDER BY DescCnae";
			
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
				imp.say( 2, rs.getString( "CodCnae" ) != null ? rs.getString( "CodCnae" ) : ""  );
				imp.say( 15, rs.getString( "DescCnae" ) != null ? rs.getString( "DescCnae" ) : ""  );
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
		*/
	}
	
}
