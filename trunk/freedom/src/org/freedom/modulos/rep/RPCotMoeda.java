/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPCotMoeda.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de cadastros de cota��es de valores das moedas cadastradas no sistema.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class RPCotMoeda extends FDados {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelCotacao = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCampos = new JPanelPad();
	
	private final JPanelPad panelTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JTextFieldPad txtDataCot = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtValor = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtDescMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final Tabela tabCotacao = new Tabela();
	
	
	public RPCotMoeda() {

		super();
		setTitulo( "Cadastro de Moedas" );
		setAtribos( 50, 50, 425, 270 );
		
		montaTela();
		
		setListaCampos( false, "COTMOEDA", "RP" );
		
		tabCotacao.adicColuna( "Data" );
		tabCotacao.adicColuna( "Valor" );
		
		tabCotacao.setTamColuna( 100, 0 );
		tabCotacao.setTamColuna( 200, 1 );
	}
	
	private void montaTela() {
		
		panelCampos.setPreferredSize( new Dimension( 400, 110 ) );
		
		setPainel( panelCampos );
		
		adicCampo( txtDataCot, 7, 30, 100, 20, "DataCot", "Data", ListaCampos.DB_PK, true );
		adicCampo( txtValor, 110, 30, 150, 20, "ValorCot", "Valor", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodMoeda, 7, 70, 100, 20, "CodMoeda", "C�d.moeda", ListaCampos.DB_SI, true );
		adicCampo( txtDescMoeda, 110, 70, 285, 20, "SingMoeda", "Nome no singular", ListaCampos.DB_SI, false );
		
		panelTabela.add( new JScrollPane( tabCotacao ), BorderLayout.CENTER );
		
		panelCotacao.add( panelCampos, BorderLayout.NORTH );		
		panelCotacao.add( panelTabela, BorderLayout.CENTER );
				
		pnCliente.add( panelCotacao, BorderLayout.CENTER );
	}
}
