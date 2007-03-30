/**
 * @version 17/05/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCLComis.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JLabelPad;

import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;

public class FCLComis extends FDados implements PostListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodClComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtDescClComis = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtPercFatClComis = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private JTextFieldPad txtPercPgtoComis = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 9, 2 );

	private ListaCampos lcClComis = new ListaCampos( this, "CL" );

	public FCLComis() {

		super();
		setTitulo( "Cadastro de Classifica��o de Comiss�es" );
		setAtribos( 50, 50, 400, 205 );

		setListaCampos( true, "CLCOMIS", "VD" );

		lcClComis.add( new GuardaCampo( txtCodClComis, "CodClComis", "C�d.c.comis.", ListaCampos.DB_PK, null, false ) );
		lcClComis.add( new GuardaCampo( txtDescClComis, "DescClComis", "Descri�ao da classifica��o da comiss�o", ListaCampos.DB_SI, null, false ) );
		lcClComis.montaSql( false, "VDCLCOMIS", "CL" );
		lcClComis.setQueryCommit( false );
		lcClComis.setReadOnly( true );

		JLabelPad lbDistriCom = new JLabelPad( " Distribui��o de Comiss�es " );
		lbDistriCom.setOpaque( true );
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		adicCampo( txtCodClComis, 7, 20, 80, 20, "CodClComis", "C�d.c.comis.", ListaCampos.DB_PK, null, true );
		adicCampo( txtDescClComis, 90, 20, 250, 20, "DescClComis", "Descri��o da classifica��o da comiss�o", ListaCampos.DB_SI, null, true );

		adic( lbDistriCom, 7, 50, 250, 20 );
		adic( lbLinha, 7, 72, 250, 2 );

		adicCampo( txtPercFatClComis, 7, 100, 120, 20, "PercFatClComis", "% S/Faturamento", ListaCampos.DB_SI, null, true );
		adicCampo( txtPercPgtoComis, 140, 100, 120, 20, "PercPgtoClComis", "% S/Recebimento.", ListaCampos.DB_SI, null, true );

		setListaCampos( true, "CLCOMIS", "VD" );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		setImprimir( true );

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp )
			imprimir( true );
		else if ( evt.getSource() == btImp )
			imprimir( false );

		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		DLRClComis dl = new DLRClComis();
		
		try {

			dl.setVisible( true );
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}
			
			sSQL.append ("SELECT CODCLCOMIS,DESCCLCOMIS,PERCFATCLCOMIS, PERCPGTOCLCOMIS FROM VDCLCOMIS ");
			sSQL.append("ORDER BY " + dl.getValor());
			
			ps = con.prepareStatement( sSQL.toString() );
			rs = ps.executeQuery();
			
			if ( "T".equals( dl.getTipo() ) ) {
				imprimirTexto( bVisualizar, rs );
			}
			else if ( "G".equals( dl.getTipo() ) ) {
				imprimirGrafico( bVisualizar, rs );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ){
				con.commit();
			}
			dl.dispose();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar relat�rio de classifica��o de cliente!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs ) {

		String sLinhaFina = Funcoes.replicate( "-", 133 );
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		imp.montaCab();

		try {
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Classifica��o de Comiss�es" );

			while ( rs.next() ) {

				if ( imp.pRow() == linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();
				}
				
				if ( imp.pRow() == 0 ) {
					imp.montaCab();
					imp.setTitulo( "Relat�rio de Classifica��o de Comiss�es" );
					imp.addSubTitulo( "Relat�rio de Classifica��o de Comiss�es" );
					imp.impCab( 136, true );

					imp.say( imp.pRow() + 0, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, "" );
					imp.say( imp.pRow() + 0, 0, "|" );
					imp.say( imp.pRow() + 0, 2, "C�digo" );
					imp.say( imp.pRow() + 0, 12, "|" );
					imp.say( imp.pRow() + 0, 14, "Descri��o" );
					imp.say( imp.pRow() + 0, 76, "|" );
					imp.say( imp.pRow() + 0, 80, "Perc.Fat." );
					imp.say( imp.pRow() + 0, 100, "|" );
					imp.say( imp.pRow() + 0, 108, "Perc.Receb." );
					imp.say( imp.pRow() + 0, 135, "|" );
					imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
					imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
				}

				imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
				imp.say( imp.pRow() + 0, 0, "|" );
				imp.say( imp.pRow() + 0, 2, rs.getString( "CodClComis" ) );
				imp.say( imp.pRow() + 0, 12, "|" );
				imp.say( imp.pRow() + 0, 14, rs.getString( "DescClComis" ) );
				imp.say( imp.pRow() + 0, 76, "|" );
				imp.say( imp.pRow() + 0, 80, rs.getString( "percFatClComis" ) );
				imp.say( imp.pRow() + 0, 100, "|" );
				imp.say( imp.pRow() + 0, 108, rs.getString( "PercPgtoClComis" ) );
				imp.say( imp.pRow() + 0, 135, "|" );

				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say( imp.pRow() + 1, 0, "" + imp.normal() );
			imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
			imp.eject();

			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}

			else {
				imp.print();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela de class.comiss�o!\n" + err.getMessage(), true, con, err );
		}
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/CLComis.jasper", "Classifica��o de Comiss�es", null, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de Classifica��o de Clientes!\n" + err.getMessage(), true, con, err );
			}
		}
	}
}

