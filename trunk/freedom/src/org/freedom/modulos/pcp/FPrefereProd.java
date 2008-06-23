/**
 * @version 25/09/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FPrefereProd.java <BR>
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

package org.freedom.modulos.pcp;
import java.sql.Connection;
import java.util.Vector;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FTabDados;

public class FPrefereProd extends FTabDados {
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtClass = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtNomeResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtIdentProfResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCargoResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JPanelPad pinGeral = new JPanelPad();

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JComboBoxPad cbSitRMAOP = null;
	
	public FPrefereProd() {

		super();
		setTitulo( "Prefer�ncias de Produ��o" );
		setAtribos( 50, 50, 540, 350 );
		montaListaCampos();
		montaTela();

	}
	
	private void montaTela(){
	
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Pendente" );
		vLabs.addElement( "Aprovada" );
		
		vVals.addElement( "" );
		vVals.addElement( "PE" );
		vVals.addElement( "AF" );

		cbSitRMAOP = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0 );

		setPainel( pinGeral );
		adicTab( "Geral", pinGeral );

		JPanelPad pinRespon = new JPanelPad();
		JLabelPad lbRespon = new JLabelPad( " Repons�vel t�cnico da Produ��o" );
		lbRespon.setOpaque( true );

		JPanelPad pinOp = new JPanelPad();
		JLabelPad lbOP = new JLabelPad( " Informa��es padr�o para OP." );
		lbOP.setOpaque( true );
			
		adic( lbRespon, 12, 10, 200, 20 );
		adic( pinRespon, 7, 20, 250, 150 );
		setPainel( pinRespon );
		
		adicCampo( txtNomeResp, 7, 30, 230, 20, "NOMERESP", "Nome do repons�vel", ListaCampos.DB_SI, false );
		adicCampo( txtIdentProfResp, 7, 70, 230, 20, "IDENTPROFRESP", "Indent.prof.", ListaCampos.DB_SI, false );
		adicCampo( txtCargoResp, 7, 110, 230, 20, "CARGORESP", "Cargo", ListaCampos.DB_SI, false );
		setPainel( pinGeral );

		adic( lbOP, 272, 10, 200, 20 );
		adic( pinOp, 267, 20, 250, 150 );
		setPainel( pinOp );

		adicCampo( txtClass, 7, 30, 230, 20, "CLASSOP", "Classe padr�o para O.P.", ListaCampos.DB_SI, false );
		adicCampo( txtCodTipoMov, 7, 70, 50, 20, "CODTIPOMOV", "Cd.TM.", ListaCampos.DB_FK, txtDescTipoMov, true );
		adicDescFK( txtDescTipoMov, 60, 70, 175, 20, "DESCTIPOMOV", "Descri��o do tipo de mov." );
		adicDB( cbSitRMAOP, 7, 110, 230, 20, "SITRMAOP", "Situa��o padr�o para RMA", false );
		setListaCampos( false, "PREFERE5", "SG" );

		nav.setAtivo( 0, false );
		nav.setAtivo( 1, false );
		
	}
	
	private void montaListaCampos(){
		
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "C�d.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descri��o do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setWhereAdic( " TIPOMOV='OP' " );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		txtCodTipoMov.setFK( true );
		lcCampos.setMensInserir( false );
		
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcCampos.carregaDados();
		
	}
}
