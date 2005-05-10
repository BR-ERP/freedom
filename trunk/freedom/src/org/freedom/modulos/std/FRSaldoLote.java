/**
 * @version 03/12/2003 <BR>
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
import java.util.Date;
import java.util.Vector;

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
import org.freedom.telas.FRelatorio;


public class FRSaldoLote extends FRelatorio {
  private Connection con;
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
  private JTextFieldPad txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldPad txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JCheckBoxPad cbLoteZerado = null; 
  private JLabelPad lbCodGrup = new JLabelPad("C�d.grupo");
  private JLabelPad lbDescCodGrup = new JLabelPad("Descri��o do grupo");
  private JLabelPad lbCodMarca = new JLabelPad("C�d.marca");
  private JLabelPad lbDescCodMarca = new JLabelPad("Ddescri��o da marca");
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);
  private JRadioGroup rgOrdem = null;
  private Vector vLabs = new Vector(2);
  private Vector vVals = new Vector(2);
  public FRSaldoLote() {
    setTitulo("Relat�rio de Saldos de Lotes");
    setAtribos(80,80,350,235);

    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grupo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");

    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, false));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descri��o da marca", ListaCampos.DB_SI, false));
    lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false));
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");

    cbLoteZerado = new JCheckBoxPad("Exibir lotes com saldos zerados?","S","N");
    cbLoteZerado.setVlrString("N");
    
    vLabs.addElement("C�digo");
    vLabs.addElement("Descri��o");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    
    adic(lbCodGrup,7,0,250,20);
    adic(txtCodGrup,7,20,80,20);
    adic(lbDescCodGrup,90,0,250,20);
    adic(txtDescGrup,90,20,200,20);
    adic(lbCodMarca,7,40,250,20);
    adic(txtCodMarca,7,60,80,20);
    adic(lbDescCodMarca,90,40,250,20);
    adic(txtDescMarca,90,60,200,20);
    adic(new JLabelPad("Ordem:"),7,80,250,20);
    adic(rgOrdem,7,100,283,30);
    adic(cbLoteZerado,7,140,250,20);
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
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
    }
    return bRetorno;
  }

  /**
   *  Impress�o. <BR>
   *  Imprime um relat�rio para o usu�rio.
   * 
   */

  public void imprimir(boolean bVisualizar) {
    String sCab = "";
    String sCampo = "";
    String sWhere = "";
    String sOrdem = rgOrdem.getVlrString();
    double dConta = 0;
    boolean bComRef = false;
	int iCodProdAnt = -1;
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
//    imp.montaCab();
    imp.setTitulo("Relatorio Saldos de Lotes");
    
    if (txtCodGrup.getText().trim().length() > 0) {
            sWhere += " AND P.CODGRUP LIKE '"+txtCodGrup.getText().trim()+"%'";
            String sTmp = "GRUPO: "+txtDescGrup.getText().trim();
            sCab += "\n"+imp.comprimido();
            sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
            sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |"; 
    }
    if (txtCodMarca.getText().trim().length() > 0) {
            sWhere += " AND P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
            String sTmp = "MARCA: "+txtDescMarca.getText().trim();
            sCab += "\n"+imp.comprimido();
            sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
            sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
    }
    if (cbLoteZerado.getVlrString().equals("N")) {
    	sWhere += " AND L.SLDLIQLOTE > 0";
    	String sTmp = "LOTES COM SALDO";
    	sCab += "\n"+imp.comprimido();
    	sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
    	sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
    }
    bComRef = comRef();
    sCampo = bComRef ? "REFPROD" : "CODPROD";   
    if (sOrdem.equals("C")) {
    	String sOrdenado = "";
    	if (bComRef) {
    		sOrdem = "P.REFPROD";
    		sOrdenado = "ORDENADO POR REFERENCIA";
    	}
    	else {
    		sOrdem = "P.CODPROD";
    		sOrdenado = "ORDENADO POR CODIGO";
    	}
    	sCab += "\n"+imp.comprimido();
    	sOrdenado = "|"+Funcoes.replicate(" ",68-(sOrdenado.length()/2))+sOrdenado;
    	sCab += sOrdenado+Funcoes.replicate(" ",134-sOrdenado.length())+" |";
    }
    else {
    	sOrdem = "P.DESCPROD";
    	String sOrdenado = "ORDENADO POR DESCRICAO"; 
    	sCab += "\n"+imp.comprimido();
    	sOrdenado = "|"+Funcoes.replicate(" ",68-(sOrdenado.length()/2))+sOrdenado;
    	sCab += sOrdenado+Funcoes.replicate(" ",134-sOrdenado.length())+" |";
    } 
    String sSQL = "SELECT P.CODPROD,P."+sCampo+",P.DESCPROD,L.CODLOTE,L.VENCTOLOTE,L.SLDLIQLOTE "+
		  "FROM EQPRODUTO P, EQLOTE L "+
		  "WHERE L.CODPROD = P.CODPROD"+sWhere+" ORDER BY "+sOrdem;
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ResultSet rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
        if (imp.pRow()==0) {
           imp.impCab(136, false);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| Emitido em :"+Funcoes.dateToStrDate(new Date()));
           imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,68,"SALDOS DE LOTES");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,136,"|");
           if (sCab.length() > 0) imp.say(imp.pRow()+0,0,sCab);
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"| C�digo");
           imp.say(imp.pRow()+0,16,"| Descri��o");
           imp.say(imp.pRow()+0,69,"| Lote");
           imp.say(imp.pRow()+0,85,"| Vencimento");
           imp.say(imp.pRow()+0,98,"| Saldo");
           imp.say(imp.pRow()+0,136,"|");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
         }
         if (rs.getInt("CodProd") != iCodProdAnt && iCodProdAnt != -1) {
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|");
			imp.say(imp.pRow()+0,75,"Saldo do produto: ");
			imp.say(imp.pRow()+0,98,"| "+Funcoes.strDecimalToStrCurrency(15,1,""+dConta));
			imp.say(imp.pRow()+0,136,"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
			dConta = 0;
         }
         imp.say(imp.pRow()+1,0,""+imp.comprimido());
         imp.say(imp.pRow()+0,0,"| "+(sCampo.equals("REFPROD") ? rs.getString("REFPROD") : Funcoes.alinhaDir(rs.getInt("CODPROD"),13)));
         imp.say(imp.pRow()+0,16,"| "+rs.getString("DESCPROD"));
         imp.say(imp.pRow()+0,69,"| "+rs.getString("CODLOTE"));
         imp.say(imp.pRow()+0,85,"| "+Funcoes.sqlDateToStrDate(rs.getDate("VENCTOLOTE")));
         imp.say(imp.pRow()+0,98,"| "+Funcoes.strDecimalToStrCurrency(15,1,rs.getString("SLDLIQLOTE")));
         imp.say(imp.pRow()+0,136,"|");
         dConta += rs.getDouble("SLDLIQLOTE");
         iCodProdAnt = rs.getInt("CodProd");    

		if (imp.pRow()>=linPag) {
			 imp.say(imp.pRow()+1,0,""+imp.comprimido());
			 imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
			 imp.incPags();
			 imp.eject();
		}
      }

	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  imp.say(imp.pRow()+0,0,"|");
	  imp.say(imp.pRow()+0,75,"Saldo do produto: ");
	  imp.say(imp.pRow()+0,98,"| "+Funcoes.strDecimalToStrCurrency(15,1,""+dConta));
	  imp.say(imp.pRow()+0,136,"|");
	  imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  
	  imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
      
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
