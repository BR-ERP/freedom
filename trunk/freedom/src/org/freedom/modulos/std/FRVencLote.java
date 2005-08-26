/**
 * @version 20/07/2002 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVencLote.java <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;


/**
 * Tela para verifica��o de validade dos Lotes 
 *
 * @version 1.0 20/07/2002
 * @author Fernado Oliveira da Silva
 */

public class FRVencLote extends FRelatorio {
	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JCheckBoxPad cbLoteZerado = null; 
  private JLabelPad lbCodGrup = new JLabelPad("C�d.grupo");
  private JLabelPad lbDescCodGrup = new JLabelPad("Descri��o do grupo");
  private JLabelPad lbCodMarca = new JLabelPad("C�d.marca");
  private JLabelPad lbDescCodMarca = new JLabelPad("Descri��o da marca");
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  public FRVencLote() {
    setTitulo("Relat�rio de Vencimentos de Lotes");
    setAtribos(80,80,310,250);
    
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do gurpo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");

    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, false));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descri��o da marca", ListaCampos.DB_SI, false));
    lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI,false),"txtSiglaMarca");
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");

    cbLoteZerado = new JCheckBoxPad("Exibir lotes com saldos zerados?","S","N");
    cbLoteZerado.setVlrString("N");
    
    
    
    adic(new JLabelPad("Per�odo de vencimentos:"),7,0,250,20);
    adic(new JLabelPad("De: "),7,20,40,20);
    adic(txtDataini,50,20,97,20);
    adic(new JLabelPad(" at�: "),150,20,37,20);
    adic(txtDatafim,190,20,100,20);
    adic(cbLoteZerado,7,45,250,30);
    adic(lbCodGrup,7,80,250,20);
    adic(txtCodGrup,7,100,80,20);
    adic(lbDescCodGrup,90,80,250,20);
    adic(txtDescGrup,90,100,200,20);
    adic(lbCodMarca,7,120,250,20);
    adic(txtCodMarca,7,140,80,20);
    adic(lbDescCodMarca,90,120,250,20);
    adic(txtDescMarca,90,140,200,20);
    

    GregorianCalendar cPeriodo = new GregorianCalendar();
    txtDataini.setVlrDate(cPeriodo.getTime());
    cPeriodo.add(Calendar.MONTH,3);
    txtDatafim.setVlrDate(cPeriodo.getTime());

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
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
    }
    return bRetorno;
  }

  /**
   *  Impress�o. <BR>
   *  Imprime um relat�rio para o usu�rio.
   * 
   */

  public void imprimir(boolean bVisualizar) {
    String sCodProd;
    String sWhere = "";
    String sFiltros1 = "";
    String sFiltros2 = "";
    
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
//    imp.montaCab();
    imp.setTitulo("Relatorio de Vencimentos de Lotes");
    
    if (!txtCodGrup.getVlrString().trim().equals("")) {
            sWhere += " AND P.CODGRUP LIKE '"+txtCodGrup.getText().trim()+"%'";
            sFiltros1 = "GRUPO: "+txtDescGrup.getVlrString().trim();
    }
    if (txtCodMarca.getText().trim().length() > 0) {
            sWhere += " AND P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
            sFiltros1 += (sFiltros1.equals("")?"":" / ")+"MARCA: "+txtDescMarca.getVlrString().trim();
    }
    if (cbLoteZerado.getVlrString().equals("N")){
    	sWhere +=" AND L.SLDLIQLOTE >0 ";
    	sFiltros1 += (sFiltros1.equals("")?"":" / ")+"Produtos com saldos";
    }
    	
    if (!txtDataini.getText().trim().equals("") && !txtDatafim.getText().trim().equals("")) {
    	sWhere += " AND L.VENCTOLOTE BETWEEN '"+
		  Funcoes.dateToStrDB(txtDataini.getVlrDate())+"' AND '"+
    	  Funcoes.dateToStrDB(txtDatafim.getVlrDate())+"'";
    	sFiltros2 = "PERIODO DE "+txtDataini.getVlrString()+" ATE "+txtDatafim.getVlrString();
    }
   if (comRef()) 
      sCodProd = "REFPROD";
    else
      sCodProd = "CODPROD";
    
    String sSQL = "SELECT P."+sCodProd+",P.DESCPROD,L.CODLOTE,L.VENCTOLOTE,L.SLDLIQLOTE "+
		  "FROM EQPRODUTO P, EQLOTE L "+
		  "WHERE L.CODPROD = P.CODPROD"+sWhere+" ORDER BY VENCTOLOTE";
    
//    System.out.println(sSQL);
    
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ResultSet rs = ps.executeQuery();
      imp.limpaPags();
      imp.montaCab();
      while ( rs.next() ) {
        if (imp.pRow()==0) {
           imp.impCab(136, true);
           if (!sFiltros1.trim().equals("")) {
              imp.say(imp.pRow()+0,0,"|");
           	  imp.say(imp.pRow()+0,67-(sFiltros1.length()/2),sFiltros1);
              imp.say(imp.pRow()+0,135,"|");
              imp.say(imp.pRow()+1,0,""+imp.comprimido());
           }
           if (!sFiltros2.trim().equals("")) {
            imp.say(imp.pRow()+0,0,"|");
         	  imp.say(imp.pRow()+0,67-(sFiltros2.length()/2),sFiltros2);
            imp.say(imp.pRow()+0,135,"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
         }
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| C�digo");
           imp.say(imp.pRow()+0,16,"| Descri��o");
           imp.say(imp.pRow()+0,69,"| Lote");
           imp.say(imp.pRow()+0,85,"| Vencimento");
           imp.say(imp.pRow()+0,98,"| Saldo");
           imp.say(imp.pRow()+0,135,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
         }

         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"| "+(sCodProd.equals("REFPROD") ? rs.getString("REFPROD") : Funcoes.alinhaDir(rs.getInt("CODPROD"),13)));
         imp.say(imp.pRow()+0,16,"| "+rs.getString("DESCPROD"));
         imp.say(imp.pRow()+0,69,"| "+rs.getString("CODLOTE"));
         imp.say(imp.pRow()+0,85,"| "+Funcoes.sqlDateToStrDate(rs.getDate("VENCTOLOTE")));
         imp.say(imp.pRow()+0,98,"| "+Funcoes.strDecimalToStrCurrency(15,1,rs.getString("SLDLIQLOTE")));
         imp.say(imp.pRow()+0,135,"|");
         
		if (imp.pRow()>=linPag) {
			 imp.say(imp.pRow()+1,0,""+imp.comprimido());
			 imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
			 imp.incPags();
			 imp.eject();
		}
             
      }

      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
      
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro ao consultar a tabela PRODUTOS!\n"+err.getMessage(),true,con,err);
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }

  /**
   *  Ajusta conex�o da tela. <BR>
   *  Adiciona a conex�o vigente a este formul�rio.
   *  
   *  @param cn: Conexao valida e ativa que ser� repassada e esta tela.
   *  @see org.freedom.telas.FFilho#setConexao
   */

  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcGrup.setConexao(cn);
    lcMarca.setConexao(cn);
  }
}
