/**
 * @version 03/08/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRInvPeps.java <BR>
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
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRInvPeps extends FRelatorio {
	private static final long serialVersionUID = 1L;


  private final int TAM_GRUPO = 14;
  
  private JTextFieldPad txtData = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtPagina = new JTextFieldPad(JTextFieldPad.TP_INTEGER,6,0);
  private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
  private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
  private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,TAM_GRUPO,0);
  private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JCheckBoxPad cbSemEstoq = new JCheckBoxPad("Imprimir produtos sem estoque?","S","N");

  private ListaCampos lcAlmox = new ListaCampos(this);
  private ListaCampos lcGrup = new ListaCampos(this);
  private ListaCampos lcMarca = new ListaCampos(this);

  private JLabelPad lbCodAlmox = new JLabelPad("C�d.almox.");
  private JLabelPad lbCodMarca = new JLabelPad("C�d.marca");
  private JLabelPad lbCodGrup = new JLabelPad("C�d.grupo");
  private JLabelPad lbDescAlmox = new JLabelPad("Descri��o do almoxarifado");
  private JLabelPad lbDescMarca = new JLabelPad("Descri��o da marca");
  private JLabelPad lbDescGrup = new JLabelPad("Descri��o do grupo");
  
  private JRadioGroup rgOrdem = null;
  private Vector<String> vDesc = new Vector<String>();
  private Vector<String> vOpc = new Vector<String>();
  
  private JRadioGroup rgCusto = null;
  private Vector<String> vDescCusto = new Vector<String>();
  private Vector<String> vOpcCusto = new Vector<String>();
  private boolean[] bPrefs = null;
  
  public FRInvPeps() {
    setTitulo("Invent�rio");
    setAtribos(80,30,400,350);
    
    GregorianCalendar cal = new GregorianCalendar();
    cal.add(Calendar.DATE,0);
    txtData.setVlrDate(cal.getTime());
    txtPagina.setVlrInteger(new Integer(1));

    vDesc.addElement("Descri��o");
    vDesc.addElement("C�digo");
    vOpc.addElement("D");
    vOpc.addElement("C");
    rgOrdem = new JRadioGroup(2,1,vDesc,vOpc);
    
    vDescCusto.addElement("C.PEPS");
    vDescCusto.addElement("C.MPM");
    vDescCusto.addElement("P.BASE");
    vOpcCusto.addElement("P");
    vOpcCusto.addElement("M");
    vOpcCusto.addElement("B");
    
    rgCusto = new JRadioGroup(1,3,vDescCusto,vOpcCusto);
    

    cbSemEstoq.setVlrString("N");
    
    lcAlmox.add(new GuardaCampo( txtCodAlmox, "CodAlmox", "C�d.almox.", ListaCampos.DB_PK, false));
    lcAlmox.add(new GuardaCampo( txtDescAlmox, "DescAlmox", "Descri��o do almox.", ListaCampos.DB_SI, false));
    txtCodAlmox.setTabelaExterna(lcAlmox);
    txtCodAlmox.setNomeCampo("CodAlmox");
    txtCodAlmox.setFK(true);
    lcAlmox.setReadOnly(true);
    lcAlmox.montaSql(false, "ALMOX", "EQ");
    
    lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "C�d.marca", ListaCampos.DB_PK, false));
    lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descri��o da marca", ListaCampos.DB_SI, false));
    lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false));
    lcMarca.montaSql(false, "MARCA", "EQ");
    lcMarca.setReadOnly(true);
    txtCodMarca.setTabelaExterna(lcMarca);
    txtCodMarca.setFK(true);
    txtCodMarca.setNomeCampo("CodMarca");
    
    lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "C�d.gurpo", ListaCampos.DB_PK, false));
    lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false));
    lcGrup.montaSql(false, "GRUPO", "EQ");
    lcGrup.setReadOnly(true);
    txtCodGrup.setTabelaExterna(lcGrup);
    txtCodGrup.setFK(true);
    txtCodGrup.setNomeCampo("CodGrup");
    
    adic(new JLabelPad("Ordem:"),7,0,100,20);
    adic(rgOrdem,7,20,100,60);
    adic(new JLabelPad("Estoque de:"),117,0,100,20);
    adic(txtData,117,20,100,20);
    adic(new JLabelPad("P�gina inicial:"),117,40,100,20);
    adic(txtPagina,117,60,100,20);
    adic(lbCodAlmox,7,80,250,20);
    adic(txtCodAlmox,7,100,80,20);
    adic(lbDescAlmox,90,80,250,20);
    adic(txtDescAlmox,90,100,250,20);
    adic(lbCodMarca,7,120,250,20);
    adic(txtCodMarca,7,140,80,20);
    adic(lbDescMarca,90,120,250,20);
    adic(txtDescMarca,90,140,250,20);
    adic(lbCodGrup,7,160,250,20);
    adic(txtCodGrup,7,180,80,20);
    adic(lbDescGrup,90,160,250,20);
    adic(txtDescGrup,90,180,250,20);
    adic(cbSemEstoq,7,200,250,20);
    adic(rgCusto,7,220,250,30);
    
    
  }

  
  public void imprimir(boolean bVisualizar) {
  	String sSql = "";
  	String sCpCodigo = "";
  	String sSemEstoq = "";
  	String sCodMarca = "";
  	String sCodGrup = "";
  	String sFiltros1= "";
  	String sFiltros2= "";
  	int iCodAlmox = 0;
  	ImprimeOS imp = null;
  	int linPag = 0;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	double deCustoTot = 0;
  	double deSldProd = 0;

  	try {
  		
  		imp = new ImprimeOS("",con);
  		linPag = imp.verifLinPag()-1;
  		  		
  		sCpCodigo = (bPrefs[0]?"REFPROD":"CODPROD");
  		iCodAlmox = txtCodAlmox.getVlrInteger().intValue();
  		sSemEstoq = cbSemEstoq.getVlrString();
  		sCodMarca = txtCodMarca.getVlrString().trim();
  		sCodGrup = txtCodGrup.getVlrString().trim();
  		//iCodAlmox = txt
  		sSql = "SELECT "+sCpCodigo+",DESCPROD,SLDPROD,CUSTOUNIT,CUSTOTOT FROM EQRELPEPSSP(?,?,?,?,?,?,?,?,?,?,?,?,?) " +
		       (sSemEstoq.equals("N")?" WHERE SLDPROD!=0 ":"")+
  				"ORDER BY "+(rgOrdem.getVlrString().equals("D")?"DESCPROD":sCpCodigo);
  		System.out.println(sSql);
  		try {
  			if (sSemEstoq.equals("S")) 
  				sFiltros1 = "PROD.S/ESTOQUE";
  			else 
  				sFiltros1 = "PROD.C/ESTOQUE";
  			ps = con.prepareStatement(sSql);
  			ps.setInt(1,Aplicativo.iCodEmp);
  			ps.setInt(2,ListaCampos.getMasterFilial("EQPRODUTO"));
  			ps.setDate(3,Funcoes.dateToSQLDate(txtData.getVlrDate()));
  			if (iCodAlmox!=0) {
  	  			sFiltros1 += " / ALMOX.: "+iCodAlmox+"-"+txtDescAlmox.getVlrString().trim();
  			}
  			if (sCodMarca.equals("")) {
  	  			ps.setNull(4,Types.INTEGER);
  	  			ps.setNull(5,Types.SMALLINT);
  	  			ps.setNull(6,Types.CHAR);
  			}
  			else {
  	  			ps.setInt(4,Aplicativo.iCodEmp); 
  	  			ps.setInt(5,ListaCampos.getMasterFilial("EQMARCA"));
  	  			ps.setString(6,sCodMarca);
  	  			sFiltros2 += " / MARCA: "+sCodMarca+"-"+txtDescMarca.getVlrString().trim();
  			}
  			if (sCodGrup.equals("")) {
  	  			ps.setNull(7,Types.INTEGER);
  	  			ps.setNull(8,Types.SMALLINT);
  	  			ps.setNull(9,Types.CHAR);
  			}
  			else {
  	  			ps.setInt(7,Aplicativo.iCodEmp);
  	  			ps.setInt(8,ListaCampos.getMasterFilial("EQGRUPO"));
  	  			ps.setString(9,sCodGrup);
  	  			sFiltros2 += " / GRUPO: "+sCodGrup+"-"+txtDescGrup.getVlrString().trim();
  			}
  			ps.setString(10,rgCusto.getVlrString());
  			if (iCodAlmox==0) {
  				ps.setNull(11,Types.INTEGER);
  				ps.setNull(12,Types.INTEGER);
  				ps.setNull(13,Types.INTEGER);
  			}
  			else {
  				ps.setInt(11,Aplicativo.iCodEmp);
  				ps.setInt(12,ListaCampos.getMasterFilial("EQALMOX"));
  				ps.setInt(13,iCodAlmox);
  			}
  			rs = ps.executeQuery();
  			
  			imp.limpaPags();
  			
  			imp.montaCab(txtPagina.getVlrInteger().intValue()-1);
			imp.setTitulo("Relatorio de invent�rio de estoque");
  			imp.addSubTitulo(sFiltros1);
  			imp.addSubTitulo(sFiltros2);
  			
  			while ( rs.next() ) {
  				if (imp.pRow()>=(linPag-1)) {
  					imp.say(imp.pRow()+1,0,""+imp.comprimido());
  					imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
  					imp.incPags();
  					imp.eject();

  				}
	  			if (imp.pRow()==0) {	  				
	  				imp.impCab(136, true);
	  					  				
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"| CODIGO");
					imp.say(imp.pRow()+0,16,"| DESCRICAO ");
					imp.say(imp.pRow()+0,70,"| SALDO");
					imp.say(imp.pRow()+0,83,"| CUSTO UNIT.");
					imp.say(imp.pRow()+0,101,"| CUSTO TOTAL");
					imp.say(imp.pRow()+0,135,"|");
					imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
					
				}
				
  				imp.say(imp.pRow()+1,0,""+imp.comprimido());
  				imp.say(imp.pRow()+0,0,"|"+Funcoes.adicEspacosEsquerda(rs.getString(sCpCodigo).trim(),13));
  				imp.say(imp.pRow()+0,16,"| "+Funcoes.adicionaEspacos(rs.getString("DESCPROD"),50));
  				imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(rs.getDouble("SLDPROD")+"",10));
  				imp.say(imp.pRow()+0,83,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOUNIT")+""));
  				imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,rs.getDouble("CUSTOTOT")+""));
  				imp.say(imp.pRow()+0,135,"|");
  				deSldProd += rs.getDouble("SLDPROD");
  				deCustoTot += rs.getDouble("CUSTOTOT");
  				
  			}
  			
  			rs.close();
  			ps.close();
  			if (!con.getAutoCommit())
  				con.commit();
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"| TOTAL");
  			imp.say(imp.pRow()+0,70,"|"+Funcoes.adicEspacosEsquerda(Funcoes.arredDouble(deSldProd,2)+"",10));
			imp.say(imp.pRow()+0,83,"|");
  			imp.say(imp.pRow()+0,101,"|"+Funcoes.strDecimalToStrCurrency(15,2,deCustoTot+""));
  			imp.say(imp.pRow()+0,135,"|");
  			imp.say(imp.pRow()+1,0,""+imp.comprimido());
  			imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");

  			imp.eject();
  			imp.fechaGravacao();
  			
  		}
  		catch (SQLException err) {
  			Funcoes.mensagemErro(this,"Erro executando a consulta.\n"+err.getMessage(),true,con,err);
  			err.printStackTrace();
  		}
  		if (bVisualizar) {
  			imp.preview(this);
  		}
  		else {
  			imp.print();
  		}
  	}
  	finally {
  		sSql = null;
  		sCpCodigo = null;
  		sSemEstoq = null;
  	  	sCodMarca = null;
  	  	sCodGrup = null;
  	  	sFiltros1 = null;
  	    sFiltros2 = null;
  		imp = null;
  		ps = null;
  		rs = null;
  		deCustoTot = 0;
  		deSldProd = 0;
  	  	linPag = 0;
  	}

  }
  
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcAlmox.setConexao(cn);
    lcGrup.setConexao(cn);
    lcMarca.setConexao(cn);
    bPrefs = getPrefere();
  }

  private boolean[] getPrefere() {
    boolean[] bRetorno = {false};
    String sSQL = "SELECT USAREFPROD" +
    		" FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
      rs = ps.executeQuery();
      if (rs.next()) {
      	if (rs.getString("UsaRefProd")!=null) {
          if (rs.getString("UsaRefProd").trim().equals("S"))
             bRetorno[0] = true;
      	}
      }
      rs.close();
      ps.close();
      if (!con.getAutoCommit())
        con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
    }
    finally {
    	sSQL = null;
    	ps = null;
    	rs = null;
    }
    return bRetorno;
  }

}
