/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe:
 * @(#)FTipoConv.java <BR>
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

package org.freedom.modulos.atd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.telas.FDados;

public class FTipoConv extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTipoConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescTipoConv = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodAuxTipoConv = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtClassOrc = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	public FTipoConv() {

		super();
		setTitulo( "Cadastro de Tipo de Conveniados" );
		setAtribos( 50, 50, 370, 160 );
		adicCampo( txtCodTipoConv, 7, 20, 80, 20, "CodTpConv", "C�d.tp.conv.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoConv, 90, 20, 250, 20, "DescTpConv", "Descri��o do tipo de conveniado", ListaCampos.DB_SI, true );
		adicCampo( txtCodAuxTipoConv, 7, 60, 140, 20, "CodAuxTpConv", "C�digo auxiliar", ListaCampos.DB_SI, false );
		adicCampo( txtClassOrc, 150, 60, 190, 20, "ClassTpConv", "Layout de or�amentos", ListaCampos.DB_SI, false );
		setListaCampos( true, "TIPOCONV", "AT" );
	
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

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;

		imp.limpaPags();
		imp.montaCab();
		imp.setTitulo( "Relat�rio de Tipos de Conveniados" );
		
		DLRTipoConv dl = new DLRTipoConv();
		dl.setVisible( true );
		if ( dl.OK == false ) {
			dl.dispose();
			return;
		}
		
		String sSQL = "SELECT CODTPCONV,DESCTPCONV FROM ATTIPOCONV ORDER BY " + dl.getValor();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement( sSQL );
			rs = ps.executeQuery();
			
			while ( rs.next() ) {

				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
				
				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 80, false );
					imp.say( 0, imp.normal() );
					imp.say( 2, "C�digo" );
					imp.say( 25, "Descri��o" );
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, StringFunctions.replicate( "-", 79 ) );
					
				}

				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CodTpConv" ) );
				imp.say( 25, rs.getString( "DescTpConv" ) );
								
			}

			imp.pulaLinha( 1, imp.normal() );
			imp.say( 0, StringFunctions.replicate( "=", 79 ) );
			imp.eject();

			imp.fechaGravacao();

			rs.close();
			ps.close();
			
			con.commit();
			
			dl.dispose();
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de tipos de conveniados!" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
		
	}
	
}
