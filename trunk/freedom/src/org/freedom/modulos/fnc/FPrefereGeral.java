/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe: @(#)FPrefereGeral.java <BR>
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

package org.freedom.modulos.fnc;
import java.sql.Connection;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.telas.FTabDados;

public class FPrefereGeral extends FTabDados {
	private Painel pinGeral = new Painel(330, 350);
	private Painel pinFin = new Painel();
	private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
	private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private ListaCampos lcMoeda = new ListaCampos(this,"MO");
	public FPrefereGeral() {
		setTitulo("Prefer�ncias Gerais");
		setAtribos(50, 50, 355, 200);
		
		lcMoeda.add(new GuardaCampo(txtCodMoeda, "CodMoeda","C�d.moeda", ListaCampos.DB_PK, true));
		lcMoeda.add(new GuardaCampo(txtDescMoeda, "SingMoeda","Descri��o da moeda", ListaCampos.DB_SI, false));
		lcMoeda.montaSql(false, "MOEDA", "FN");
		lcMoeda.setQueryCommit(false);
		lcMoeda.setReadOnly(true);
		txtCodMoeda.setTabelaExterna(lcMoeda);

//Geral
		
		setPainel(pinGeral);
		adicTab("Geral", pinGeral);
		adicCampo(txtAnoCC, 7,25,100,20, "AnoCentroCusto","Ano Base C.C.", ListaCampos.DB_SI, true);

//Financeiro
	
		setPainel(pinFin);
		adicTab("Financeiro", pinFin);

		adicCampo(txtCodMoeda,7,20,70,20,"CodMoeda","C�d.moeda", ListaCampos.DB_FK, txtDescMoeda, true);
		adicDescFK(txtDescMoeda,80,20,230,20,"SingMoeda","Descri��o da moeda corrente.");

		nav.setAtivo(0,false);
		lcCampos.setPodeExc(false);

		setListaCampos(false, "PREFERE1", "SG");
		
	}
	public void execShow(Connection cn) {
		lcMoeda.setConexao(cn);
		super.execShow(cn);
		lcCampos.carregaDados();
	}
}
