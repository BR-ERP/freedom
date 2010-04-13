/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRBalancete.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Tela de filtros e gera��o de relat�rio financeiro de ponto de equilibrio.
 * 
 */

package org.freedom.modulos.std.view.frame.report;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FRelatorio;

public class FRPontoEqui extends FRelatorio {

	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0); 
	private JTextFieldFK  txtSiglaCC = new JTextFieldFK(JTextFieldPad.TP_STRING,19,0);
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldFK txtDescConta = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0); 
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);   
	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0); 
	private ListaCampos lcCC = new ListaCampos(this);
	private ListaCampos lcConta = new ListaCampos(this);
	
	public FRPontoEqui() {
		setTitulo("Apura��o de resultados");
		setAtribos(80,80,330,120);
		
		lcConta.add(new GuardaCampo( txtCodConta, "NumConta", "C�d.conta", ListaCampos.DB_PK, false));
		lcConta.add(new GuardaCampo( txtDescConta, "DescConta", "Descri��o da conta", ListaCampos.DB_SI,false));
		lcConta.montaSql(false, "CONTA", "FN");
		lcConta.setReadOnly(true);
		txtCodConta.setTabelaExterna(lcConta);
		txtCodConta.setFK(true);
		txtCodConta.setNomeCampo("NumConta");
		
		lcCC.add(new GuardaCampo( txtCodCC, "CodCC", "C�d.cc.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI,false));
		lcCC.add(new GuardaCampo( txtDescCC, "DescCC", "Descri��o do centro de custos", ListaCampos.DB_SI,false));
		lcCC.setReadOnly(true);
		lcCC.montaSql(false, "CC", "FN");
		txtCodCC.setTabelaExterna(lcCC);
		txtCodCC.setFK(true);
		txtCodCC.setNomeCampo("CodCC");
		txtSiglaCC.setListaCampos(lcCC);
		
		adic(new JLabelPad("Per�odo:"),7,5,120,20);
		adic(new JLabelPad("De:"),7,25,30,20);
		adic(txtDataini,40,25,117,20);
		adic(new JLabelPad("At�:"),160,25,22,20);
		adic(txtDatafim,185,25,120,20);
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
	}
	
	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		lcConta.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC="+buscaAnoBaseCC());
	}
	
	private int buscaAnoBaseCC() {
		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next())
				iRet = rs.getInt("ANOCENTROCUSTO");
			
			rs.close();
			ps.close();
			
			con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo!\n"+err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}
	
	public BigDecimal getRecDesp(String sGet) {
		BigDecimal bRet = new BigDecimal("0");  
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT SUM(SL.vlrsublanca * -1) VLRSUBLANCA " +
					  "FROM FNPLANEJAMENTO P, fnsublanca SL,FNLANCA L "+
					  "WHERE P.CODEMP=? AND P.CODFILIAL=? "+
					  "AND SL.codemp = P.codemp AND SL.codfilial = P.codfilial AND SL.codplan = P.codplan "+
					  "AND L.codemp = P.codemp AND L.codfilial = P.codfilial AND L.codlanca = SL.codLANca "+
					  "AND sl.datasublanca between ? and ? AND TIPOPLAN=?"+
					  "AND L.FLAG IN " + AplicativoPD.carregaFiltro(con,org.freedom.library.swing.frame.Aplicativo.iCodEmp);
		
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("FNSUBLANCA"));
			ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));		
			ps.setString(5,sGet);
			rs = ps.executeQuery();
			if (rs.next())
				bRet =  ( (rs.getBigDecimal("VLRSUBLANCA")==null) ? (new BigDecimal(0)) : (rs.getBigDecimal("VLRSUBLANCA").abs()) );
			
			rs.close();
			ps.close();
			
			con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar os valores de:"+sGet+".\n"+err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRet;
	}
	
	public BigDecimal getFIN(String sFin) {
		BigDecimal bFin = new BigDecimal("0");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT SUM(SL.vlrsublanca * -1) VLRSUBLANCA " +
					  "FROM FNPLANEJAMENTO P, fnsublanca SL,FNLANCA L "+
					  "WHERE P.TIPOPLAN IN ('R','D') "+
					  "AND P.CODEMP=? AND P.CODFILIAL=? "+
					  "AND SL.codemp = P.codemp AND SL.codfilial = P.codfilial AND SL.codplan = P.codplan "+
					  "AND L.codemp = P.codemp AND L.codfilial = P.codfilial AND L.codlanca = SL.codLANca "+
					  "AND sl.datasublanca between ? and ? AND P.FINPLAN=? "+
					  "AND L.FLAG IN " + AplicativoPD.carregaFiltro(con,org.freedom.library.swing.frame.Aplicativo.iCodEmp);
		
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("FNSUBLANCA"));
			ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			ps.setString(5,sFin);
			rs = ps.executeQuery();
			if (rs.next())
				bFin = ( (rs.getBigDecimal("VLRSUBLANCA")==null) ? (new BigDecimal(0)) : (rs.getBigDecimal("VLRSUBLANCA").abs()) );
			
			rs.close();
			ps.close();
			
			con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar os valores de:"+sFin+".\n"+err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}	
		return bFin;
	}
	
	public void imprimir(boolean bVisualizar) {
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}
				    		
		BigDecimal bdRec = getRecDesp("R");
		
		if (bdRec.equals(new BigDecimal(0))) {
			Funcoes.mensagemErro(this,"N�o existem valores para o per�odo especificado!");
			return;
		}				
		
		BigDecimal bdRV = getFIN("RV");
		BigDecimal bdER = getFIN("ER");
		BigDecimal bd01 = bdRV.subtract(bdER);
		BigDecimal bdCV = getFIN("CV");
		BigDecimal bd03 = bd01.subtract(bdCV);
		BigDecimal bdDesp = getRecDesp("D");
		BigDecimal bdCF = getFIN("CF");
		BigDecimal bdLO = bd03.subtract(bdCF);
		BigDecimal bdPE = new BigDecimal("0");
		if (!bdCF.equals(new BigDecimal("0")))	   
			bdPE = (bd03.divide(bdCF,6)).multiply(bd01);
		BigDecimal bdI = getFIN("I");
		BigDecimal bdRF = getFIN("RF");
		BigDecimal bdDF = getFIN("DF");
		BigDecimal bdCS = getFIN("CS");
		BigDecimal bdIR = getFIN("IR");

		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		imp.montaCab();
		imp.setTitulo("Apura��o de Resultados");
		imp.addSubTitulo("APURA��O DE RESULTADOS DE "+txtDataini.getVlrString()+" A "+txtDatafim.getVlrString());
		imp.limpaPags();
		    
		try {
			
			if (imp.pRow()==0) {
				imp.impCab(80, true);				     
				imp.say(imp.pRow(), 0, imp.normal());         
				imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",77) + "|" );   
				imp.say(imp.pRow() + 1, 0, imp.normal()); 
				imp.say(imp.pRow(), 0, "|");
				imp.say(imp.pRow(), 47, "|      Valor");
				imp.say(imp.pRow(), 65, "|           % |");
				imp.say(imp.pRow() + 1, 0, imp.normal());
				imp.say(imp.pRow(), 0, "|" + StringFunctions.replicate("-",77) + "|" );
			}
			
			impAux( imp, "|      Receitas:", 
					bdRec.toString(), 
					"100", 
					true );						
			impAux( imp, "|    | Receitas S/V (RV):", 
					bdRV.toString(), 
					bdRV.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "|    | Estorno de receitas (ER):", 
					bdER.toString(), 
					bdER.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 01 | Receita liquida (RV-ER):", 
					bd01.toString(), 
					bd01.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 02 | Custos variaveis (CV):", 
					bdCV.toString(), 
					bdCV.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 03 | Margem contribuicao (01-02):", 
					bd03.toString(), 
					bd03.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );
			
			impAux( imp, "|      Despesas:", 
					bdDesp.toString(), 
					bdDesp.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString(),
					true);			
			impAux( imp, "| 04 | Custos fixos (CF):", 
					bdCF.toString(), 
					bdCF.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 05 | Lucro operacional (03-04):", 
					bdLO.toString(), 
					bdLO.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 06 | Ponto de equilibrio ((03/04)*01):", 
					bdPE.toString(), 
					bdPE.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 07 | Investimentos (I):", 
					bdI.toString(), 
					bdI.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 08 | Receitas financeiras (RF):", 
					bdRF.toString(), 
					bdRF.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 09 | Despesas financeiras (DF):", 
					bdDF.toString(), 
					bdDF.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );						
			impAux( imp, "| 10 | Contribui��o social (CS):", 
					bdCS.toString(), 
					bdCS.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );			
			impAux( imp, "| 11 | IRPJ (IR):", 
					bdIR.toString(), 
					bdIR.multiply( new BigDecimal(100) ).divide( bdRec, 6 ).toString() );
			
			if (imp.pRow() == (linPag-1)) {
				imp.say(imp.pRow()+1,0,""+imp.normal());
				imp.say(imp.pRow()+0,0,"+"+StringFunctions.replicate("-",77)+"+");
				imp.eject();
				imp.incPags();          
			}
			
			imp.eject();  
			imp.fechaGravacao();
			  
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro ao consultar as bases financeiras!\n"+err.getMessage(),true,con,err);      
			err.printStackTrace();      
		}
		    
		if (bVisualizar)
			imp.preview(this);
		else 
			imp.print();
	}
	
	private void impAux( ImprimeOS imp, String texto, String valor, String perc ) {
		impAux( imp, texto, valor, perc, false );
	}
	
	private void impAux( ImprimeOS imp, String texto, String valor, String perc, boolean linhaGroca ) {
		String aux;
		
		if( linhaGroca )
			aux = "|" + StringFunctions.replicate("=",45) + "+" + StringFunctions.replicate("=",17) + "+" + StringFunctions.replicate("=",13) + "|";
		else 
			aux = "|----+" + StringFunctions.replicate("-",40) + "+" + StringFunctions.replicate("-",17) + "+" + StringFunctions.replicate("-",13) + "|";
		
		imp.say(imp.pRow() + 1, 0, imp.normal() );
		imp.say(imp.pRow(), 0, aux );
		
		imp.say(imp.pRow() + 1, 0, imp.normal() );
		imp.say(imp.pRow(),  0, texto );
		imp.say(imp.pRow(), 47, "| " + Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, valor ) + " |");		  	 
		imp.say(imp.pRow(), 67, Funcoes.strDecimalToStrCurrency(  9, Aplicativo.casasDecFin, perc ) + " % |");
		
		imp.say(imp.pRow() + 1, 0, imp.normal() );
		imp.say(imp.pRow(), 0, aux ); 
		
	}
}