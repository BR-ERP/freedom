/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLRProduto.java <BR>
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
 */

package org.freedom.modulos.std;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.JCheckBoxPad;
import org.freedom.library.JComboBoxPad;
import org.freedom.library.JLabelPad;
import org.freedom.library.JPanelPad;
import org.freedom.library.JRadioGroup;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;

import java.util.Vector;

import org.freedom.telas.FFDialogo;

public class DLRProduto extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgModo = null;

	private JPanelPad pinSelec = new JPanelPad( 172, 90 );

	private JPanelPad pinSelec2 = new JPanelPad( 172, 90 );

	private JTextFieldPad txtDe = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtA = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtDe2 = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 30, 0 );

	private JTextFieldPad txtA2 = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 30, 0 );

	private JTextFieldPad txtCodForn = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescForn = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodAlmox = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAlmox = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldFK txtDescMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtSiglaMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JCheckBoxPad cbAtivoProd = new JCheckBoxPad( "Somente Ativos", "S", "N" );
	
	private JComboBoxPad cbTipo;

	private ListaCampos lcAlmox = new ListaCampos( this );

	private ListaCampos lcCodForn = new ListaCampos( this );

	private ListaCampos lcMarca = new ListaCampos( this );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	private Vector<String> vLabsModo = new Vector<String>();

	private Vector<String> vValsModo = new Vector<String>();

	private Vector<String> vLabsTipo = new Vector<String>();

	private Vector<Object> vValsTipo = new Vector<Object>();
	

	public DLRProduto( DbConnection cn ) {

		setTitulo( "Relat�rio de Produtos" );
		setAtribos( 470, 470 );
		
		vLabs.addElement( "C�digo" );
		vLabs.addElement( "Descri��o" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );

		vLabsModo.addElement( "Resumido" );
		vLabsModo.addElement( "Completo" );
		vValsModo.addElement( "R" );
		vValsModo.addElement( "C" );
		rgModo = new JRadioGroup<String, String>( 1, 2, vLabsModo, vValsModo );
		rgModo.setVlrString( "R" );
		
		vValsTipo.addElement( "T" );
		vValsTipo.addElement( "P" );
		vValsTipo.addElement( "S" );
		vValsTipo.addElement( "F" );
		vValsTipo.addElement( "M" );
		vValsTipo.addElement( "O" );
		vValsTipo.addElement( "C" );
		vLabsTipo.addElement( "Todos" );
		vLabsTipo.addElement( "Com�rcio" );
		vLabsTipo.addElement( "Servi�o" );
		vLabsTipo.addElement( "Fabrica��o" );
		vLabsTipo.addElement( "Mat.prima" );
		vLabsTipo.addElement( "Patrimonio" );
		vLabsTipo.addElement( "Consumo" );
		cbTipo = new JComboBoxPad( vLabsTipo, vValsTipo, JComboBoxPad.TP_STRING, 1, 0 );

		lcAlmox.add( new GuardaCampo( txtCodAlmox, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, false ) );
		lcAlmox.add( new GuardaCampo( txtDescAlmox, "DescAlmox", "Descri��o do almoxarifado", ListaCampos.DB_SI, false ) );
		lcAlmox.montaSql( false, "ALMOX", "EQ" );
		lcAlmox.setReadOnly( true );
		txtCodAlmox.setTabelaExterna( lcAlmox );
		txtCodAlmox.setFK( true );
		txtCodAlmox.setNomeCampo( "CodAlmox" );

		lcCodForn.add( new GuardaCampo( txtCodForn, "CodFor", "C�d.for.", ListaCampos.DB_PK, false ) );
		lcCodForn.add( new GuardaCampo( txtDescForn, "RazFor", "Descri��o do fornecedor", ListaCampos.DB_SI, false ) );
		lcCodForn.montaSql( false, "FORNECED", "CP" );
		lcCodForn.setReadOnly( true );
		txtCodForn.setTabelaExterna( lcCodForn );
		txtCodForn.setFK( true );
		txtCodForn.setNomeCampo( "CodFor" );

		lcMarca.add( new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, false ) );
		lcMarca.add( new GuardaCampo( txtDescMarca, "DescMarca", "Descri��o da marca", ListaCampos.DB_SI, false ) );
		lcMarca.add( new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false ) );
		txtCodMarca.setTabelaExterna( lcMarca );
		txtCodMarca.setNomeCampo( "CodMarca" );
		txtCodMarca.setFK( true );
		lcMarca.setReadOnly( true );
		lcMarca.montaSql( false, "MARCA", "EQ" );

		adic( new JLabelPad( "Ordenar por:" ), 7, 5, 180, 20 );
		adic( rgOrdem, 7, 25, 260, 30 );
		adic( cbAtivoProd, 300, 25, 290, 30 );

		adic( new JLabelPad( "Modo do relat�rio:" ), 7, 60, 120, 20 );
		adic( rgModo, 7, 80, 260, 30 );

		adic( new JLabelPad( "Fluxo:" ), 270, 60, 120, 20 );
		adic( cbTipo, 270, 82, 170, 24 );

		adic( new JLabelPad( "Selec�o por descri��o:" ), 7, 115, 146, 20 );
		
		pinSelec.adic( new JLabelPad( "De:" ), 7, 5, 30, 20 );
		pinSelec.adic( txtDe, 7, 25, 243, 20 );
		pinSelec.adic( new JLabelPad( "A:" ), 7, 45, 30, 20 );
		pinSelec.adic( txtA, 7, 65, 243, 20 );
		adic( pinSelec, 7, 135, 260, 100 );

		adic( new JLabelPad( "Selec�o por c�digo:" ), 270, 115, 128, 20 );
		
		pinSelec2.adic( new JLabelPad( "De:" ), 7, 5, 30, 20 );
		pinSelec2.adic( txtDe2, 7, 25, 153, 20 );
		pinSelec2.adic( new JLabelPad( "A:" ), 7, 45, 30, 20 );
		pinSelec2.adic( txtA2, 7, 65, 153, 20 );
		adic( pinSelec2, 270, 135, 170, 100 );		

		adic( new JLabelPad( "C�d.for." ), 7, 250, 80, 20 );
		adic( txtCodForn, 7, 270, 80, 20 );
		adic( new JLabelPad( "Descri��o do fornecedor" ), 90, 250, 180, 20 );
		adic( txtDescForn, 90, 270, 350, 20 );
		adic( new JLabelPad( "C�d.almox." ), 7, 290, 250, 20 );
		adic( txtCodAlmox, 7, 310, 80, 20 );
		adic( new JLabelPad( "Descri��o do almoxarifado" ), 90, 290, 250, 20 );
		adic( txtDescAlmox, 90, 310, 350, 20 );
		adic( new JLabelPad( "C�d.marca" ), 7, 330, 350, 20 );
		adic( txtCodMarca, 7, 350, 80, 20 );
		adic( new JLabelPad( "Descri��o da Marca" ), 90, 330, 350, 20 );
		adic( txtDescMarca, 90, 350, 350, 20 );

		lcAlmox.setConexao( cn );
		lcCodForn.setConexao( cn );
		lcMarca.setConexao( cn );
		
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 14 ];
		if ( rgOrdem.getVlrString().compareTo( "C" ) == 0 ) {
			sRetorno[ 0 ] = "CODPROD";
		}
		else if ( rgOrdem.getVlrString().compareTo( "D" ) == 0 ) {
			sRetorno[ 0 ] = "DESCPROD";
		}
		sRetorno[ 1 ] = txtDe.getVlrString();
		sRetorno[ 2 ] = txtA.getVlrString();
		sRetorno[ 3 ] = cbAtivoProd.getVlrString();
		sRetorno[ 4 ] = txtCodForn.getVlrString();
		sRetorno[ 5 ] = txtDescForn.getVlrString();
		sRetorno[ 6 ] = rgModo.getVlrString();
		sRetorno[ 7 ] = txtCodAlmox.getVlrString();
		sRetorno[ 8 ] = txtDescAlmox.getVlrString();
		sRetorno[ 9 ] = txtCodMarca.getVlrString();
		sRetorno[ 10 ] = txtDescMarca.getVlrString();
		sRetorno[ 11 ] = txtDe2.getVlrString();
		sRetorno[ 12 ] = txtA2.getVlrString();
		sRetorno[ 13 ] = cbTipo.getVlrString();

		return sRetorno;
	}
}
