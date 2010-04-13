/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FConsSolItem.java <BR>
 *                   Este programa � licenciado de acordo com a LPG-PC (Licen�a
 *                   P�blica Geral para Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e
 *                   REPRODU��ES deste Programa. <BR>
 *                   Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com
 *                   este Programa, voc� pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou
 *                   ALTERAR este Programa � preciso estar <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 *                   <BR>
 *                   Formul�rio de consulta de RMA.
 */

package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.gms.view.frame.crud.detail.FRma;

public class FConsSolItem extends FFilho implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanelPad pinCab = new JPanelPad(0, 145);
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnLegenda = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(0,5));
	private JTextFieldPad txtDtIni = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JTextFieldPad txtDtFim = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING, 19, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 4, 0);
	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTablePad tab = new JTablePad();
	private ImageIcon imgCancelada = Icone.novo("clVencido.gif");
	private ImageIcon imgExpedida = Icone.novo("clPago.gif");
	private ImageIcon imgAprovada = Icone.novo("clPagoParcial.gif");
	private ImageIcon imgPendente = Icone.novo("clNaoVencido.gif");
	private ImageIcon imgColuna = null;
	private JButtonPad btCalc = new JButtonPad(Icone.novo("btExecuta.gif"));
	private JButtonPad btBusca = new JButtonPad("Buscar", Icone.novo("btPesquisa.gif"));
	private JButtonPad btPrevimp = new JButtonPad("Imprimir", Icone.novo("btPrevimp.gif"));
	private	JButtonPad btSair = new JButtonPad("Sair", Icone.novo("btSair.gif"));
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcAlmox = new ListaCampos(this, "AM");
	private ListaCampos lcUsuario = new ListaCampos(this, "");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	boolean bAprovaParcial = false;
	boolean bExpede = false;
	boolean bAprova = false;
	private Vector<?> vSitSol = new Vector<Object>();
	public FConsSolItem() {
		super(false);
		setTitulo("Sum�rio de Solicita��es de Compra");
		setAtribos(10, 10, 795, 480);

		btCalc.setToolTipText("Criar cota��o sumarizada");
		btCalc.addActionListener(this);
		
		txtDtIni.setRequerido(true);
		txtDtFim.setRequerido(true);

		txtCodAlmoxarife.setNomeCampo("CodAlmox");
		txtCodAlmoxarife.setFK(true);
		
		lcAlmox.add(new GuardaCampo(txtCodAlmoxarife, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, null, false));
		lcAlmox.add(new GuardaCampo(txtDescAlmoxarife, "DescAlmox", "Desc.almox;", ListaCampos.DB_SI, null, false));
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);

		txtDescAlmoxarife.setSoLeitura(true);
		txtCodAlmoxarife.setTabelaExterna(lcAlmox);
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		
		txtCodProd.setNomeCampo("CodProd");
		txtCodProd.setFK(true);
		
		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "C�d.prod.", ListaCampos.DB_PK, null, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd", "Descri��o do produto", ListaCampos.DB_SI, null, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Refer�ncia", ListaCampos.DB_SI, null, false));
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);

		txtDescProd.setSoLeitura(true);
		txtRefProd.setSoLeitura(true);
		txtCodProd.setTabelaExterna(lcProd);
		lcProd.montaSql(false, "PRODUTO", "EQ");		
				
		txtCodUsu.setNomeCampo("IDUSU");
		txtCodUsu.setFK(true);

		lcUsuario.add(new GuardaCampo(txtCodUsu, "IDUSU", "ID usuario", ListaCampos.DB_PK, null, false));
		lcUsuario.add(new GuardaCampo(txtNomeUsu, "NOMEUSU", "Nome do usuario", ListaCampos.DB_SI, null, false));
		lcUsuario.setQueryCommit(false);
		lcUsuario.setReadOnly(true);

		txtNomeUsu.setSoLeitura(true);
		txtCodUsu.setTabelaExterna(lcUsuario);
		lcUsuario.montaSql(false, "USUARIO", "SG");

		lcCC.add(new GuardaCampo(txtCodCC, "CodCC", "C�d.cc.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtAnoCC, "AnoCC", "Ano.cc.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC", "Descri��o do centro de custo", ListaCampos.DB_SI, false));
		lcCC.setReadOnly(true);
		lcCC.setQueryCommit(false);
		lcCC.montaSql(false, "CC", "FN");
		txtCodCC.setTabelaExterna(lcCC);
		txtCodCC.setFK(true);
		txtCodCC.setNomeCampo("CodCC");
		
		Container c = getTela();
		c.add(pnRod, BorderLayout.SOUTH);
		c.add(pnCli, BorderLayout.CENTER);
		pnCli.add(pinCab, BorderLayout.NORTH);
		pnCli.add(spnTab, BorderLayout.CENTER);

		btSair.setPreferredSize(new Dimension(100, 30));
		
		pnLegenda.add(new JLabelPad("Cancelada",imgCancelada,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Aprovada",imgAprovada,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Em Cota��o",imgExpedida,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Pendente",imgPendente,SwingConstants.CENTER));
		pnLegenda.add(btCalc);
		
		pnRod.add(pnLegenda,BorderLayout.WEST);
		pnRod.add(btSair, BorderLayout.EAST);
		
		pinCab.adic(new JLabelPad("Per�odo:"), 7, 5, 50, 20);
		pinCab.adic(txtDtIni, 7, 25, 95, 20);
		pinCab.adic(new JLabelPad("At�"), 111, 25, 27, 20);
		pinCab.adic(txtDtFim, 139, 25, 95, 20);

		pinCab.adic(new JLabelPad("C�d.usu."), 237, 5, 70, 20);
		pinCab.adic(txtCodUsu, 237, 25, 80, 20);
		pinCab.adic(new JLabelPad("Nome do usu�rio"), 320, 5, 153, 20);
		pinCab.adic(txtNomeUsu, 320, 25, 163, 20);
		
		pinCab.adic(new JLabelPad("C�d.prod."), 7, 45, 80, 20);
		pinCab.adic(txtCodProd, 7, 65, 80, 20);
		pinCab.adic(new JLabelPad("Descri��o do produto"), 90, 45, 200, 20);
		pinCab.adic(txtDescProd, 90, 65, 200, 20);

		pinCab.adic(new JLabelPad("C�d.c.c."), 7, 85, 70, 20);
		pinCab.adic(txtCodCC, 7, 105, 140, 20);
		pinCab.adic(new JLabelPad("Centro de custo"), 150, 85, 410, 20);
		pinCab.adic(txtDescCC, 150, 105, 180, 20);

		pinCab.adic(btBusca, 352, 57, 130, 30);
		pinCab.adic(btPrevimp, 352, 93, 130, 30);

		txtDtIni.setVlrDate(new Date());
		txtDtFim.setVlrDate(new Date());

		tab.adicColuna("");//0
		tab.adicColuna("Cotar");//1		
		tab.adicColuna("C�d.prod.");//2
		tab.adicColuna("Ref.prod");//3
		tab.adicColuna("Descri��o do produto");//4
		tab.adicColuna("Qt. requerida");//5
		tab.adicColuna("Qt. aprovada");//6
		tab.adicColuna("Saldo");//7
		
		tab.setTamColuna(12, 0);
		tab.setTamColuna(35, 1);
		tab.setTamColuna(70, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(320, 4);
		tab.setTamColuna(90, 5);
		tab.setTamColuna(90, 6);
		tab.setTamColuna(80, 7);

		tab.setColunaEditavel(1, true);
		
		btBusca.addActionListener(this);
		btPrevimp.addActionListener(this);

		tab.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource() == tab && mevt.getClickCount() == 2)
					abreRma();
			}
		});
		btSair.addActionListener(this);

	}

	private void habCampos(){
		getAprova();
		if(!bExpede){
			if(bAprova){
			  if(bAprovaParcial){
			  	txtCodCC.setVlrString(Aplicativo.strCodCCUsu);
				txtAnoCC.setVlrString(Aplicativo.strAnoCCUsu);
				txtCodCC.setNaoEditavel(true);
			  	lcUsuario.setWhereAdic("CODCC='"+Aplicativo.strCodCCUsu+"' AND ANOCC="+Aplicativo.strAnoCCUsu);
			  }
			  else {
			  	txtCodCC.setNaoEditavel(false);
			  	
			  }
			  txtCodUsu.setNaoEditavel(false);
			}
			else {
			  txtCodUsu.setVlrString(Aplicativo.strUsuario);		  
			  txtCodCC.setVlrString(Aplicativo.strCodCCUsu);
			  txtAnoCC.setVlrString(Aplicativo.strAnoCCUsu);
	
			  txtCodUsu.setNaoEditavel(true);
			  txtCodCC.setNaoEditavel(true);
			  lcUsuario.carregaDados();
			  lcCC.carregaDados();
			}
		}		
	}
	
	/**
	 * Carrega os valores para a tabela de consulta. Este m�todo � executado ap�s
	 * carregar o ListaCampos da tabela.
	 */
	private void carregaTabela() {
		String where = "";
		boolean usaOr = false;
		boolean usaWhere = false;
		boolean usuario = (!txtCodUsu.getVlrString().trim().equals(""));
		boolean almoxarifado = false;
		boolean CC = (!txtCodCC.getVlrString().trim().equals(""));
		String sCodProd = txtCodProd.getVlrString();

		
		if (where.trim().equals("")) {
			where = " (IT.SitAprovItSol ='AP' OR IT.SitAprovItSol ='AT')";
		} 
		else {
			where = where + " OR (IT.SitAprovItSol ='AP' OR IT.SitAprovItSol ='AT')";
			usaOr = true;
		}
		usaWhere = true;
		if (usaWhere && usaOr)
			where = " AND (" + where + ")";
		else if (usaWhere)
			where = " AND " + where;
		else
			where = " AND IT.SitItSol='PE'";
		
		if (sCodProd.length() > 0) 
			where += " AND IT.CODPROD = '" + sCodProd + "'";
		
		if (almoxarifado)
			where += " AND IT.CODALMOX=? AND IT.CODEMPAM=? AND IT.CODFILIALAM=? ";

		if (CC)
			where += " AND O.ANOCC=? AND O.CODCC=? AND O.CODEMPCC=? AND O.CODFILIALCC=? ";

		if (usuario)
			where += " AND (O.IDUSU=?) ";

		String sSQL = "	SELECT IT.CODPROD,IT.REFPROD, PD.DESCPROD, "+
		  "SUM(IT.QTDITSOL), SUM(IT.QTDAPROVITSOL), PD.SLDPROD "+
		"FROM CPSOLICITACAO O, CPITSOLICITACAO IT, EQPRODUTO PD "+
		"WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL "+
		"AND PD.CODEMP=IT.CODEMP AND PD.CODFILIAL=IT.CODFILIAL AND PD.CODPROD=IT.CODPROD "+
		"AND ((IT.DTAPROVITSOL BETWEEN ? AND ?) OR  (O.DTEMITSOL BETWEEN ? AND ?)) "+
		where + " " +
		"GROUP BY IT.CODPROD,IT.REFPROD, PD.DESCPROD, PD.SLDPROD "+
		"ORDER BY PD.DESCPROD, IT.CODPROD, IT.REFPROD, PD.SLDPROD";
		System.out.println(sSQL);
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			int param = 1;
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));

			if (almoxarifado) {
				ps.setInt(param++, txtCodAlmoxarife.getVlrInteger().intValue());
				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, Aplicativo.iCodFilial);
			}

			if (CC) {
				ps.setInt(param++, txtAnoCC.getVlrInteger().intValue());
				ps.setString(param++, txtCodCC.getVlrString());
				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, Aplicativo.iCodFilial);
			}

			if (usuario) {
				ps.setString(param++, txtCodUsu.getVlrString());
			}

			ResultSet rs = ps.executeQuery();
			
			int iLin = 0;

			tab.limpa();
			vSitSol = new Vector<Object>();
			while (rs.next()) {
				tab.adicLinha();
				
				imgColuna = imgAprovada;

				tab.setValor(imgColuna, iLin, 0);//SitItSol
				tab.setValor(new Boolean(false), iLin, 1);
				tab.setValor(rs.getString(1) == null ? "" : rs.getString(1) + "",iLin, 2);//CodProd 
				tab.setValor(rs.getString(2) == null ? "" : rs.getString(2) + "",iLin, 3);//CodProd 
				tab.setValor(rs.getString(3) == null ? "" : rs.getString(3).trim() + "",iLin, 4);//DescProd
				tab.setValor(rs.getString(4) == null ? "" : rs.getString(4) + "",iLin, 5);//Qtd Req
				tab.setValor(rs.getString(5) == null ? "" : rs.getString(5) + "",iLin, 6);//Qtd Aprov
				tab.setValor(rs.getString(6) == null ? "" : rs.getString(6) + "",iLin, 7);//Saldo Prod

				iLin++;
			}

			con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela CPSOLICITACAO!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		BigDecimal bTotalLiq = new BigDecimal("0");
		boolean bImpCot = false;

		/*
		 * bImpCot = Funcoes.mensagemConfirma(this, "Deseja imprimir informa��es de
		 * cota��es de pre�o?") == 0 ? true : false;
		 */

		

		try {
			imp.limpaPags();
			for (int iLin = 0; iLin < tab.getNumLinhas(); iLin++) {
				if (imp.pRow() == 0) {
					imp.montaCab();
					imp.setTitulo("Relat�rio de Requisi��es de material");
					imp.addSubTitulo("Relat�rio de Requisi��es de material");
					imp.impCab(136, true);
					//	imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "| Rma.");
					imp.say(imp.pRow() + 0, 15, "| Emiss�o");
					imp.say(imp.pRow() + 0, 29, "| Situa��o");
					imp.say(imp.pRow() + 0, 45, "| Motivo.");
					imp.say(imp.pRow() + 0, 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());

					if (bImpCot) {
						imp.say(imp.pRow() + 0, 0, "| Nro. Pedido");
						imp.say(imp.pRow() + 0, 15, "| Nro. Nota");
						imp.say(imp.pRow() + 0, 29, "| Data Fat.");
						imp.say(imp.pRow() + 0, 41, "| ");
						imp.say(imp.pRow() + 0, 56, "| ");
						imp.say(imp.pRow() + 0, 87, "| Vlr. Item Fat.");
						imp.say(imp.pRow() + 0, 105, "| ");
						imp.say(imp.pRow() + 0, 124, "| ");
						imp.say(imp.pRow() + 0, 135, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());

					}

					imp.say(imp.pRow() + 0, 0,"|"+StringFunctions.replicate("-", 133)+"|");

				}

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "|" + tab.getValor(iLin, 1));
				imp.say(imp.pRow() + 0, 15, "| " + tab.getValor(iLin, 2));
				imp.say(imp.pRow() + 0, 29, "| " + vSitSol.elementAt(iLin).toString());
				String sMotivo = ""+tab.getValor(iLin, 3);
				imp.say(imp.pRow() + 0, 45, "| " + sMotivo.substring(0, sMotivo.length()>89?89:sMotivo.length()).trim());
				imp.say(imp.pRow() + 0, 135, "| ");

				if (bImpCot) {
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 2, "|" + tab.getValor(iLin, 2));
					imp.say(imp.pRow() + 0, 15, "|" + tab.getValor(iLin, 3));
					imp.say(imp.pRow() + 0, 29, "|");
					imp.say(imp.pRow() + 0, 41, "|");
					imp.say(imp.pRow() + 0, 56, "|");
					imp.say(imp.pRow() + 0, 87, "|" + tab.getValor(iLin, 12));
					imp.say(imp.pRow() + 0, 105, "|");
					imp.say(imp.pRow() + 0, 124, "|");
					imp.say(imp.pRow() + 0, 135, "|");
				}

				
				if (tab.getValor(iLin, 9) != null) {
					bTotalLiq = bTotalLiq.add(new BigDecimal(Funcoes
							.strCurrencyToDouble("" + tab.getValor(iLin, 9))));
				}

				if (imp.pRow() >= linPag) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow() + 1, 0,"+"+StringFunctions.replicate("-", 133)+"+");
			imp.eject();

			imp.fechaGravacao();

			con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro consulta tabela de or�amentos!\n"
					+ err.getMessage(),true,con,err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private void abreRma() {
		int iRma = ((Integer) tab.getValor(tab.getLinhaSel(), 1)).intValue();
		if (fPrim.temTela("Requisi��o de material") == false) {
			FRma tela = new FRma();
			fPrim.criatela("Requisi��o de material", tela, con);
			tela.exec(iRma);
		}
	}

    private void getAprova() {
		String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVRMAUSU,ALMOXARIFEUSU " +
				      "FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? " +
				      "AND IDUSU=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGUSUARIO"));
			ps.setString(3, Aplicativo.strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				String sAprova = rs.getString("APROVRMAUSU");
				String sExpede = rs.getString("ALMOXARIFEUSU");
				if(sAprova!=null){
					if(!sAprova.equals("ND")) {
						if(sAprova.equals("TD"))						
							bAprova = true;
						else if( (Aplicativo.strCodCCUsu.equals(rs.getString("CODCC"))) &&
								 (Aplicativo.iCodEmp==rs.getInt("CODEMPCC")) &&
								 (ListaCampos.getMasterFilial("FNCC")==rs.getInt("CODFILIALCC")) &&
								 (sAprova.equals("CC"))	) { 
							bAprova = true;	
							bAprovaParcial = true;
						}						
					}
				}
				if(sExpede!=null){
					if(sExpede.equals("S")){
						bExpede = true;
					}
					else {
						bExpede = false;
					}
				}
			}
			con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage(),true,con,err);
		}
    }
  
    public void criaCotacao(int codprod) {
		String sSQLautoincrement = "SELECT coalesce(MAX(SS.CODSUMSOL) + 1, 1) AS CODSUMSOL FROM cpsumsol SS WHERE SS.CODEMP=? AND SS.CODFILIAL=?";
		
		String sSQLsumsol = "INSERT INTO CPSUMSOL "+
		"(CODEMP, CODFILIAL, CODSUMSOL, CODEMPPD, CODFILIALPD, CODPROD, REFPROD, QTDITSOL, QTDAPROVITSOL, SITITSOL, SITAPROVITSOL) "+
		"SELECT ?, ?, ?, "+
		  "IT.CODEMPPD, IT.CODFILIALPD, IT.CODPROD,IT.REFPROD, "+
		  "SUM(IT.QTDITSOL), SUM(IT.QTDAPROVITSOL), 'PE', 'PE' "+
		"FROM CPSOLICITACAO O, CPITSOLICITACAO IT "+
		"WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL "+
		"AND CODEMPPD = ? AND CODFILIALPD = ? AND CODPROD = ? "+
		"group BY IT.CODEMPPD, IT.CODFILIALPD, IT.CODPROD, IT.REFPROD";

		String sSQLitsumsol = "INSERT INTO CPITSUMSOL " +
		"(CODEMP, CODFILIAL, CODSUMSOL, CODEMPSC, CODFILIALSC, CODSOL, CODITSOL) " +
			"SELECT ?, ?, ?, IT.CODEMP, IT.CODFILIAL, IT.CODSOL, IT.CODITSOL "+
		"FROM CPSOLICITACAO O, CPITSOLICITACAO IT "+
		"WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL "+
		"AND CODEMPPD = ? AND CODFILIALPD = ? AND CODPROD = ?";
		
		int codsumsol = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sSQLautoincrement);			
			ps.setInt(1,Aplicativo.iCodEmp);	  	
			ps.setInt(2,Aplicativo.iCodFilial);		  	
		    
	    	ResultSet rs = ps.executeQuery();
	    	
	    	if (rs.next()) {
				codsumsol = rs.getInt("CODSUMSOL");
	    	}	
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao atualizar a tabela CPSUMSOL!\n"+err.getMessage(),true,con,err);
		}
		
		try {	
			PreparedStatement ps2 = con.prepareStatement(sSQLsumsol);
							
			ps2.setInt(1,Aplicativo.iCodEmp);	  	
			ps2.setInt(2,Aplicativo.iCodFilial);
			ps2.setInt(3,codsumsol);
			ps2.setInt(4,Aplicativo.iCodEmp);	  	
			ps2.setInt(5,Aplicativo.iCodFilial);
			ps2.setInt(6,codprod);
			
			ps2.execute();
			
			con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao atualizar a tabela SUMSOL!\n"+err.getMessage(),true,con,err);
		}	

		try {	
			PreparedStatement ps3 = con.prepareStatement(sSQLitsumsol);
							
			ps3.setInt(1,Aplicativo.iCodEmp);	  	
			ps3.setInt(2,Aplicativo.iCodFilial);
			ps3.setInt(3,codsumsol);
			ps3.setInt(4,Aplicativo.iCodEmp);	  	
			ps3.setInt(5,Aplicativo.iCodFilial);
			ps3.setInt(6,codprod);
			
			ps3.execute();
			
			con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao atualizar a tabela ITSUMSOL!\n"+err.getMessage(),true,con,err);
		}    
    }
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btCalc) {
			if (tab.getRowCount() <= 0) {
				Funcoes.mensagemInforma(this,"N�o ha nenhum �tem para ser cotado");
				return;
			}
			
		    int iLin = 0;
		    while (iLin<tab.getRowCount()) {
		    	if (Boolean.valueOf(tab.getValor(iLin, 1).toString()).booleanValue())
		    		criaCotacao(Integer.parseInt(tab.getValor(iLin, 3).toString().trim()));
		    	iLin++;
		    }
		}
		
		if (evt.getSource() == btSair) {
			dispose();
		}
		if (evt.getSource() == btBusca) {
			if (txtDtIni.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this, "Digite a data inicial!");
			else if (txtDtFim.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this, "Digite a data final!");
			else
				carregaTabela();
			if (evt.getSource() == btPrevimp) {
				imprimir(true);
			}
		}
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}

	}
	
	private int buscaVlrPadrao() {
		int iRet = 0;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				iRet = rs.getInt("ANOCENTROCUSTO");
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao buscar o ano-base para o centro de custo.\n"
							+ err.getMessage());
		}

		return iRet;
	}

	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		lcAlmox.setConexao(cn);
		lcProd.setConexao(cn);
		lcUsuario.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC=" + buscaVlrPadrao());
		habCampos();
	}
}