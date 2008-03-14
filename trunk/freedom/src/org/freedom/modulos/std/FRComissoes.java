/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRComissoes.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 *  
 */

package org.freedom.modulos.std;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRComissoes extends FRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	private Vector<String> vVals = new Vector<String>();
	
	private Vector<String> vLabs = new Vector<String>();
	
	private Vector<String> vVals1 = new Vector<String>();
	
	private Vector<String> vLabs1 = new Vector<String>();
	
	private Vector<String> vValsOrdem = new Vector<String>();
	
	private Vector<String> vLabsOrdem = new Vector<String>();
	
	private JRadioGroup<?, ?> rgEmitRel = null;
	
	private JRadioGroup<?, ?> rgTipoRel = null;
	
	private JRadioGroup<?, ?> rgOrdemRel = null;
	
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	
	private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	
	private JCheckBoxPad cbNLiberada = new JCheckBoxPad("N�o Liber.", "S", "N");
	
	private JCheckBoxPad cbLiberada = new JCheckBoxPad("Liberadas", "S", "N");
	
	private JCheckBoxPad cbPaga = new JCheckBoxPad("Pagas", "S", "N");
	
	private JCheckBoxPad cbResumido = new JCheckBoxPad("Relat�rio Resumido", "S", "N");
	
	private ListaCampos lcVend = new ListaCampos(this);

	public FRComissoes() {
	  
		super();		
		setTitulo("Comiss�es");
		setAtribos(80, 80, 360, 400 );

		Funcoes.setBordReq(txtCodVend);

		vVals.addElement("E");
		vVals.addElement("V");
		vVals.addElement("P");
		vLabs.addElement("Emiss�o");
		vLabs.addElement("Vencimento");
		vLabs.addElement("Pagto. comiss�o");
		rgEmitRel = new JRadioGroup<String, String>(3, 2, vLabs, vVals);
		rgEmitRel.setVlrString("E");
		rgEmitRel.setAtivo(0, true);
		rgEmitRel.setAtivo(1, true);

		vValsOrdem.addElement("E");
		vValsOrdem.addElement("V");
		vValsOrdem.addElement("P");
		vValsOrdem.addElement("D");
		vLabsOrdem.addElement("Emiss�o");
		vLabsOrdem.addElement("Vencimento");
		vLabsOrdem.addElement("Pagto. comiss�o");
		vLabsOrdem.addElement("Duplicata");
		rgOrdemRel = new JRadioGroup<String, String>(2, 2, vLabsOrdem, vValsOrdem);
		rgOrdemRel.setVlrString("E");
		rgOrdemRel.setAtivo(0, true);
		rgOrdemRel.setAtivo(1, true);
		
		
		vVals1.addElement("G");
		vVals1.addElement("T");
		vLabs1.addElement("Grafico");
		vLabs1.addElement("Texto");
		rgTipoRel = new JRadioGroup<String, String>(2, 2, vLabs1, vVals1 );
		rgTipoRel.setVlrString("G");
		
		lcVend.add(new GuardaCampo(txtCodVend, "CodVend", "C�d.comiss.",ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo(txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false));
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVend);
		txtCodVend.setFK(true);
		txtCodVend.setNomeCampo("CodVend");
		
		montaTela();
	}
	public void montaTela(){
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad("Periodo:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque(true);
		
		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 320, 45 );
		
		adic( new JLabelPad("De:"), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad("At�:"), 152, 25, 37, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );
		
		adic( new JLabelPad("C�d.comiss."), 7, 57, 250, 20);
		adic( txtCodVend, 7, 77, 80, 20);
		adic( new JLabelPad("Nome do comissionado"), 90, 57, 320, 20);
		adic( txtDescVend, 90, 77, 235, 20);
		
		JLabelPad lbOrdem = new JLabelPad("Ordem:" , SwingConstants.CENTER );
		lbOrdem.setOpaque(true);
		
		adic( lbOrdem, 7, 100, 80, 20 );
		adic( rgOrdemRel, 7, 115, 320, 65 );
		adic( rgEmitRel, 7, 185, 155, 65 );
		adic( rgTipoRel, 165, 185, 163, 65 );
		adic( cbNLiberada, 5, 260, 100, 20 );
		adic( cbLiberada, 135, 260, 97, 20 );
		adic( cbPaga, 265, 260, 100, 20 );
		
		adic(cbResumido, 5, 287, 170, 20);
		cbResumido.setSelected(true);

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,	cPeriodo.get(Calendar.DAY_OF_MONTH) - 30);
		txtDataini.setVlrDate(cPeriodo.getTime());
	}

	public void setConexao(Connection cn) {
		
		super.setConexao(cn);
		lcVend.setConexao(cn);
	}

	public void imprimir( boolean bVisualizar ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sEmitRel = "";
		String sFiltro = "";
		String sOrdem = "";
		String sOrdemRel = "";
		String sDataini = "";
		String sDatafim = "";
		String sNLiberada = "";
		String sLiberada = "";
		String sPaga = "";
		String sDataFiltro = "";
		String sTitDataFiltro = "";
		int iCodVend = txtCodVend.getVlrInteger().intValue();
		
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}
			
		sEmitRel = rgEmitRel.getVlrString();
		sOrdemRel = rgOrdemRel.getVlrString();

		sNLiberada = cbNLiberada.getVlrString();
		sLiberada = cbLiberada.getVlrString();
		sPaga = cbPaga.getVlrString();

		sDataini = txtDataini.getVlrString();
		sDatafim = txtDatafim.getVlrString();
			
		if (sEmitRel.equals("E")) {
			
			sDataFiltro = "C.DATACOMI";
			sTitDataFiltro = "Emiss�o";
			
		} else if (sEmitRel.equals("V")) {
			
			sDataFiltro = "C.DTVENCCOMI";
			sTitDataFiltro = "Vencimento";
		
			
		} else if (sEmitRel.equals("P")) {
			
			sDataFiltro = "C.DTPAGTOCOMI";
			sTitDataFiltro = "Pagto. Comiss�o";
		}
			
		if (sOrdemRel.equals("E")) {
			
			sOrdem = "C.DATACOMI, R.DOCREC, IR.NPARCITREC";
		
		} else if (sOrdemRel.equals("V")) {
			
			sOrdem = "C.DTVENCCOMI, R.DOCREC, IR.NPARCITREC";
		
		} else if (sOrdemRel.equals("P")) {
			
			sOrdem = "C.DTPAGTOCOMI, R.DOCREC, IR.NPARCITREC";
			
		} else if (sOrdemRel.equals("D")) {
			
			sOrdem = "R.DOCREC, IR.NPARCITREC";
		}
		
		String sSQL = "SELECT C.DATACOMI,V.CODVENDA,V.DOCVENDA,C.STATUSCOMI,"
					+ "CL.CODCLI,CL.RAZCLI,C.VLRVENDACOMI,P.DESCPLANOPAG,"
					+ "C.VLRCOMI,C.DTVENCCOMI,R.DOCREC,IR.NPARCITREC,"
					+ "C.TIPOCOMI,C.VLRAPAGCOMI, C.VLRPAGOCOMI,C.DTPAGTOCOMI,"
					+ "IR.VLRPARCITREC "
					+ "FROM FNRECEBER R, FNITRECEBER IR, VDVENDA V, VDCOMISSAO C,"
					+ "VDCLIENTE CL, FNPLANOPAG P WHERE V.FLAG IN "
					+ AplicativoPD.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)
					+ " AND R.CODEMPVD = ? AND R.CODFILIALVD = ? AND R.CODVEND = ?"
					+ " AND C.CODEMP = ? AND C.CODFILIAL = ? "
					+ " AND R.CODEMP = C.CODEMPRC AND R.CODFILIAL = C.CODFILIALRC "
					+ " AND R.CODREC = C.CODREC AND V.CODEMP = R.CODEMPVA "
					+ " AND IR.CODEMP = C.CODEMPRC AND IR.CODFILIAL = C.CODFILIALRC "
					+ " AND IR.CODREC = C.CODREC AND IR.NPARCITREC = C.NPARCITREC "
					+ " AND V.CODFILIAL = R.CODFILIALVA AND V.TIPOVENDA = R.TIPOVENDA "
					+ " AND V.CODVENDA = R.CODVENDA AND CL.CODEMP = R.CODEMPCL "
					+ " AND CL.CODFILIAL = R.CODFILIALCL AND CL.CODCLI = R.CODCLI "
					+ " AND P.CODEMP = V.CODEMPPG AND P.CODFILIAL = V.CODFILIALPG "
					+ " AND P.CODPLANOPAG = V.CODPLANOPAG "
					+ " AND "
					+ sDataFiltro
					+ " BETWEEN ? AND ? AND C.STATUSCOMI IN (?,?,?)"
					+ " ORDER BY " + sOrdem;
			
		try {
			
			ps = con.prepareStatement( sSQL );
			ps.setInt(1, Aplicativo.iCodEmp );
			ps.setInt(2, ListaCampos.getMasterFilial( "VDVENDEDOR" ));
			ps.setInt(3, iCodVend );
			ps.setInt(4, Aplicativo.iCodEmp );
			ps.setInt(5, ListaCampos.getMasterFilial( "VDCOMISSAO" ));
			ps.setDate(6, Funcoes.dateToSQLDate( txtDataini.getVlrDate()));
			ps.setDate(7, Funcoes.dateToSQLDate( txtDatafim.getVlrDate()));

				
			if (sNLiberada.equals("S")) {
					
				ps.setString(8, "C1");
				sFiltro = "N�O LIBERADAS";
				
			} else {
				ps.setString(8, "XX");
			}
			if (sLiberada.equals("S")) {
					
				ps.setString(9, "C2");
				sFiltro += (sFiltro.equals("") ? "" : " - ") + "LIBERADAS";
				
			} else {
					
				ps.setString(9, "XX");
			}
			if (sPaga.equals("S")) {
					
				ps.setString(10, "CP");
				sFiltro += (sFiltro.equals("") ? "" : " - ") + "PAGAS";
			} else {
					
				ps.setString(10, "XX");
				
			}
			rs = ps.executeQuery();
			
		}catch (SQLException err ){
			
			err.printStackTrace();
		}
		
		if("T".equals( rgTipoRel.getVlrString())){
			
			imprimiTexto( rs, bVisualizar, "" );
		}
		else{
			imprimiGrafico( rs, bVisualizar, "" ); 
		}
	}

	public void imprimiTexto(  ResultSet rs, boolean bVisualizar, String sCab ) {
		
	
		ImprimeOS imp = null;
		int linPag = 0;
		
		BigDecimal bPercComi = new BigDecimal("0");
		BigDecimal bVlrVenda = new BigDecimal("0");
		BigDecimal bVlrComi = new BigDecimal("0");
		BigDecimal bVlrPago = new BigDecimal("0");
		BigDecimal bVlrAPag = new BigDecimal("0");
		BigDecimal bVlrTotVenda = new BigDecimal("0");
		BigDecimal bVlrTotComi = new BigDecimal("0");
		BigDecimal bVlrTotPago = new BigDecimal("0");
		BigDecimal bVlrTotAPag = new BigDecimal("0");
		
		int iCodVend = txtCodVend.getVlrInteger().intValue();
		
		
		try {
			
			boolean hasData = false;
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relat�rio de Compras");
			imp.addSubTitulo("RELATORIO DE COMISSOES - PERIODO DE " + txtDataini.getVlrDate() + " ATE " + txtDatafim.getVlrDate());
			imp.addSubTitulo( sCab.toString() );
			imp.limpaPags();				
			
			while (rs.next()) {
				
				hasData = true;
						
				if (imp.pRow() >= (linPag - 1)) {
						
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("-", 133) + "|");
					imp.incPags();		
					imp.eject();
						
				}
				
				if (imp.pRow() == 0) {
							
					
					imp.impCab(136, true);
				
					String sVendedor = "COMISSIONADO: " + iCodVend + " - " + txtDescVend.getVlrString();
							
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("=", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|");
					imp.say(imp.pRow() + 0, (135 - sVendedor.length()) / 2,sVendedor);
					imp.say(imp.pRow() + 0, 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("=", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("-", 133) + "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());					
						
					
					if (cbResumido.getStatus()) {
						
						imp.say(imp.pRow() + 0, 0, "| CLIENTE");
						imp.say(imp.pRow() + 0, 38, "|  DUPLIC.");							
						imp.say(imp.pRow() + 0, 49, "| PEDIDO");
						imp.say(imp.pRow() + 0, 60, "| EMISSAO");
						imp.say(imp.pRow() + 0, 73, "| VENCTO.");
						imp.say(imp.pRow() + 0, 84, "| VLR.PARC.A");							
						imp.say(imp.pRow() + 0, 98, "|VLR.COMI.");
						imp.say(imp.pRow() + 0, 108, "|    %");
						imp.say(imp.pRow() + 0, 114, "| VLR.PAGO");
								imp.say(imp.pRow() + 0, 124, "| DT.PGTO.");							
							
					} else {
							
						imp.say(imp.pRow() + 0, 0, "| CLIENTE");
						imp.say(imp.pRow() + 0, 24, "|  DUPLIC.");							
						imp.say(imp.pRow() + 0, 35, "| PEDIDO");
						imp.say(imp.pRow() + 0, 44, "|L");
						imp.say(imp.pRow() + 0, 46, "|ST");
						imp.say(imp.pRow() + 0, 49, "| EMISSAO");
						imp.say(imp.pRow() + 0, 60, "| VENCTO.");
						imp.say(imp.pRow() + 0, 71, "| VLR.PARC.A");
						imp.say(imp.pRow() + 0, 84, "|VLR.COMI.");
						imp.say(imp.pRow() + 0, 93, "|    %");
						imp.say(imp.pRow() + 0, 104, "| VLR.PAGO");
						imp.say(imp.pRow() + 0, 114, "|VLR.A PG.");
						imp.say(imp.pRow() + 0, 124, "| DT.PGTO.");							
						
					}
					
					imp.say(imp.pRow() + 0, 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("-", 133) + "|");

				}
			
				if (rs.getFloat("VLRVENDACOMI") > 0){
					
					bPercComi = new BigDecimal(rs.getFloat("VLRCOMI") * 100 / rs.getFloat("VLRPARCITREC")).setScale(4,BigDecimal.ROUND_HALF_UP);
				}
				else
					
					bPercComi = new BigDecimal("0");
						
					
				bVlrVenda = rs.getBigDecimal("VLRPARCITREC").setScale(2,BigDecimal.ROUND_HALF_UP);
				bVlrComi = rs.getBigDecimal("VLRCOMI").setScale(2,BigDecimal.ROUND_HALF_UP);
				bVlrPago = rs.getBigDecimal("VLRPAGOCOMI").setScale(2,BigDecimal.ROUND_HALF_UP);
				bVlrAPag = rs.getBigDecimal("VLRAPAGCOMI").setScale(2,BigDecimal.ROUND_HALF_UP);
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				
				if (cbResumido.getStatus()) {
				
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.adicionaEspacos(rs.getString("RAZCLI"),36));
					imp.say(imp.pRow() + 0, 38, "|" + Funcoes.adicEspacosEsquerda(rs.getString("DOCREC") + "-" + rs.getString("NPARCITREC"), 10));
					imp.say(imp.pRow() + 0, 49, "|" + Funcoes.adicEspacosEsquerda(rs.getInt("CODVENDA") + "", 8));
					imp.say(imp.pRow() + 0, 60, "|" + Funcoes.dateToStrDate(rs.getDate("DATACOMI")));
					imp.say(imp.pRow() + 0, 73, "|" + Funcoes.dateToStrDate(rs.getDate("DTVENCCOMI")));
					imp.say(imp.pRow() + 0, 84, "|" + Funcoes.strDecimalToStrCurrency(12, 2, ""+bVlrVenda));
					imp.say(imp.pRow() + 0, 98, "|" + Funcoes.strDecimalToStrCurrency(9, 2, ""+bVlrComi));
					imp.say(imp.pRow() + 0, 108, "|" + Funcoes.alinhaDir(Funcoes.strDecimalToStrCurrency(5, 2, ""+bPercComi),5));
					imp.say(imp.pRow() + 0, 114, "|" + Funcoes.strDecimalToStrCurrency(9, 2, ""+bVlrPago));
					imp.say(imp.pRow() + 0, 124, "|" + (rs.getDate("DTPAGTOCOMI") == null ? "" : Funcoes.dateToStrDate(rs.getDate("DTPAGTOCOMI"))));
				} else {
				
					imp.say(imp.pRow() + 0, 0, "|" + Funcoes.adicionaEspacos(rs.getString("RAZCLI"),22));
					imp.say(imp.pRow() + 0, 24, "|" + Funcoes.adicEspacosEsquerda(rs.getString("DOCREC") + "-" + rs.getString("NPARCITREC"), 10));
					imp.say(imp.pRow() + 0, 35, "|" + Funcoes.adicEspacosEsquerda(rs.getInt("CODVENDA") + "", 8));
					imp.say(imp.pRow() + 0, 44, "|" + rs.getString("TIPOCOMI"));
					imp.say(imp.pRow() + 0, 46, "|" + rs.getString("STATUSCOMI"));
					imp.say(imp.pRow() + 0, 49, "|" + Funcoes.dateToStrDate(rs.getDate("DATACOMI")));
					imp.say(imp.pRow() + 0, 60, "|" + Funcoes.dateToStrDate(rs.getDate("DTVENCCOMI")));
					imp.say(imp.pRow() + 0, 71, "|" + Funcoes.strDecimalToStrCurrency(12, 2, ""+bVlrVenda));
					imp.say(imp.pRow() + 0, 84, "|" + Funcoes.strDecimalToStrCurrency(9, 2, ""+bVlrComi));
					imp.say(imp.pRow() + 0, 94, "|" + Funcoes.alinhaDir(Funcoes.strDecimalToStrCurrency(5, 4, ""+bPercComi),9));
					imp.say(imp.pRow() + 0, 104, "|" + Funcoes.strDecimalToStrCurrency(9, 2, ""+bVlrPago));
					imp.say(imp.pRow() + 0, 114, "|" + Funcoes.strDecimalToStrCurrency(9, 2, ""+bVlrAPag));
					imp.say(imp.pRow() + 0, 124, "|" + (rs.getDate("DTPAGTOCOMI") == null ? "" : Funcoes.dateToStrDate(rs.getDate("DTPAGTOCOMI"))));
					
				}
				
				imp.say(imp.pRow() + 0, 135, "|");
				bVlrTotVenda = bVlrTotVenda.add(bVlrVenda);
				bVlrTotComi = bVlrTotComi.add(bVlrComi);
				bVlrTotPago = bVlrTotPago.add(bVlrPago);
				bVlrTotAPag = bVlrTotAPag.add(bVlrAPag);
				
			}
			
			imp.say(imp.pRow() + ((hasData) ? 1 : 0), 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("=", 133) + "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			
			if (cbResumido.getStatus()) 	{				
					
				imp.say(imp.pRow() + 0, 0, "| TOTAL ->");
				imp.say(imp.pRow() + 0, 38, "|");
				imp.say(imp.pRow() + 0, 84, "|" + Funcoes.strDecimalToStrCurrency(12, 2, "" + bVlrTotVenda.setScale(2,BigDecimal.ROUND_HALF_UP)));
				imp.say(imp.pRow() + 0, 98, "|" + Funcoes.strDecimalToStrCurrency(9, 2, "" + bVlrTotComi.setScale(2,BigDecimal.ROUND_HALF_UP)));
				imp.say(imp.pRow() + 0, 108, "|");
				imp.say(imp.pRow() + 0, 114, "|" + Funcoes.strDecimalToStrCurrency(9, 2, "" + bVlrTotPago.setScale(2,BigDecimal.ROUND_HALF_UP)));
				imp.say(imp.pRow(), 124, "|");
				
			} else {
			
				imp.say(imp.pRow() + 0, 0, "| TOTAL ->");
				imp.say(imp.pRow() + 0, 24, "|");
				imp.say(imp.pRow() + 0, 71, "|" + Funcoes.strDecimalToStrCurrency(12, 2, "" + bVlrTotVenda.setScale(2,BigDecimal.ROUND_HALF_UP)));
				imp.say(imp.pRow() + 0, 83, "|" + Funcoes.strDecimalToStrCurrency(9, 2, "" + bVlrTotComi.setScale(2,BigDecimal.ROUND_HALF_UP)));
				imp.say(imp.pRow() + 0, 94, "|");
				imp.say(imp.pRow() + 0, 104, "|" + Funcoes.strDecimalToStrCurrency(9, 2, "" + bVlrTotPago.setScale(2,BigDecimal.ROUND_HALF_UP)));
				imp.say(imp.pRow() + 0, 114, "|" + Funcoes.strDecimalToStrCurrency(9, 2, "" + bVlrTotAPag.setScale(2,BigDecimal.ROUND_HALF_UP)));
				imp.say(imp.pRow(), 124, "|");
				
			}
					
			imp.say(imp.pRow(), 135, "|");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133) + "+");

			imp.eject();

			imp.fechaGravacao();
	
			if (!con.getAutoCommit()){
				con.commit();
			}
		} catch ( Exception e ) {
		
			e.printStackTrace();
		}
		if (bVisualizar) {
			imp.preview(this);
	
		} else {
			imp.print();
		
		}
	} 
	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar,  final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRComissoes.jasper", "Relat�rio de Comiss�es", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de Comiss�es!" + err.getMessage(), true, con, err );
			}
		}
	}
}
