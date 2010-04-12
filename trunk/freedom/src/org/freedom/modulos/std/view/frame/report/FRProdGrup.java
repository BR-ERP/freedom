/**
 * @version 08/07/2009 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRProdGrup.java <BR>
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
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

public class FRProdGrup extends FRelatorio {
	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JLabelPad lbCodGrup = new JLabelPad("C�d.grupo");
  private JLabelPad lbDescGrup = new JLabelPad("Descri��o do grupo");
  private JLabelPad lbCodMarca = new JLabelPad("C�d.marca");
  private JLabelPad lbDescMarca = new JLabelPad("Descri��o da marca");
  private JRadioGroup<?, ?> rgOrdem = null;
  private Vector<String> vLabs = new Vector<String>(2);
  private Vector<String> vVals = new Vector<String>(2);
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  //private JCheckBoxPad cbGrupo = new JCheckBoxPad("Dividir por grupo","S","N");

  public FRProdGrup() {
    setTitulo("Relat�rio de Produtos por Grupo");
    setAtribos(80,80,350,240);
    vLabs.addElement("C�digo");
    vLabs.addElement("Descri��o");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

    lcGrup.add(new GuardaCampo( txtCodGrup,"CodGrup", "C�d.grupo", ListaCampos.DB_PK,false));
    lcGrup.add(new GuardaCampo( txtDescGrup,"DescGrup", "Descri��o do grupo", ListaCampos.DB_SI,false));
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");

    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK,false));
    lcMarca.add(new GuardaCampo( txtDescMarca,"DescMarca", "Descri��o da marca", ListaCampos.DB_SI,false));
    lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI,false));
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");

    adic(lbCodGrup,7,0,250,20);
    adic(txtCodGrup,7,20,80,20);
    adic(lbDescGrup,90,0,250,20);
    adic(txtDescGrup,90,20,197,20);
    adic(lbCodMarca,7,40,250,20);
    adic(txtCodMarca,7,60,80,20);
    adic(lbDescMarca,90,40,250,20);
    adic(txtDescMarca,90,60,197,20);
    adic(rgOrdem,7,90,250,30);
    //adic(cbGrupo,7,130,250,20);
  }

  
  private boolean comRef() {
    boolean bRetorno = false;
    String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
      rs = ps.executeQuery();
      if (rs.next()) {
        if (rs.getString("UsaRefProd").trim().equals("S"))
          bRetorno = true;
      }
      rs.close();
      ps.close();
      con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
    }
    return bRetorno;
  }

  public void imprimir(boolean bVisualizar) {
    String sOrdem = rgOrdem.getVlrString();
    String sCab = "";
    String sWhere = "";
    String sOrdenado = "";
    String sOrdemGrupo = "G.DESCGRUP, P.CODGRUP, ";
    String sCodgrup = "";
    String usaRefProd = "N"; 
    HashMap<String, Object> params = new HashMap<String, Object>();
    if ( comRef() ) {
    	usaRefProd = "S";
    }
    if (sOrdem.equals("C")) {
      if ("S".equals( usaRefProd ) ) {
        sOrdem = sOrdemGrupo+"P.REFPROD";
        sOrdenado = "ORDENADO POR REFER�NCIA";
      }
      else {
        sOrdem = sOrdemGrupo+"P.CODPROD";
        sOrdenado = "ORDENADO POR CODIGO";
      }
    }
    else {
      sOrdem = sOrdemGrupo+"P.DESCPROD";
      sOrdenado = "ORDENADO POR DESCRICAO"; 
    }
    sOrdenado = StringFunctions.replicate(" ",67-(sOrdenado.length()/2))+sOrdenado;
    sOrdenado += StringFunctions.replicate(" ",132-sOrdenado.length());
    
    if (txtCodGrup.getText().trim().length() > 0) {
            sWhere += " AND P.CODGRUP LIKE '"+txtCodGrup.getText().trim()+"%'";
            sCab = "GRUPO: "+txtDescGrup.getText().trim()+" - ";
    }
    if (txtCodMarca.getText().trim().length() > 0) {
            sWhere += " AND P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
            sCab += "MARCA: "+txtDescMarca.getText().trim();
    }
 
    String sSQL = "SELECT P.CODGRUP,P.CODPROD,P.REFPROD,P.DESCPROD," +
                  "G.DESCGRUP FROM EQPRODUTO P,EQGRUPO G " +
                  "WHERE G.CODEMP=P.CODEMPGP AND G.CODFILIAL=P.CODFILIALGP AND " +
                  "G.CODGRUP=P.CODGRUP AND " +
                  "P.ATIVOPROD='S' "+sWhere+" ORDER BY "+sOrdem;
                  
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
    	params.put( "USAREFPROD", usaRefProd );
    	ps = con.prepareStatement(sSQL);
    	rs = ps.executeQuery();
      
		FPrinterJob dlGr = new FPrinterJob( "relatorios/ProdGrup.jasper", "Produtos por Grupo",
				sCab, rs, params, this);
		
		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de produtos!" + err.getMessage(), true, con, err );
			}
		}
      
		rs.close();
		ps.close();
		con.commit();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de produtos!\n"+err.getMessage(),true,con,err);      
    }
    
    
  }
  public void setConexao(DbConnection cn) {
    super.setConexao(cn);
    lcGrup.setConexao(cn);
    lcMarca.setConexao(cn);
  }
}
