/**
 * @version 04/01/2013 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.library.swing.component <BR>
 * Classe:
 * @(#)JButtonXLS.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para
 * Programas de Computador), <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste
 * Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa �
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Bot�o especialista em exporta��o de dados para formato XLS
 */

package org.freedom.library.swing.component;

import java.awt.FileDialog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.freedom.bmps.Icone;
import org.freedom.library.component.ResultSetToExcel;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;


public class JButtonXLS extends JButtonPad {
	
	/**
	 * 

	 */
	
	private static Icon iconXLS = Icone.novo("btXls.png");

	private static final long serialVersionUID = 1L;
	
	public JButtonXLS() {
		super(iconXLS);
		this.setEnabled(false);
		this.setToolTipText("Exporta��o para formato XLS");
	}

	public boolean execute(ResultSet rs) {
		boolean result = false;
		
		FileDialog fdExcel = null;
		fdExcel = new FileDialog( Aplicativo.telaPrincipal, "Salvar arquivo excell", FileDialog.SAVE );
		fdExcel.setFile( "relatorio.xls" );
		fdExcel.setVisible( true );
		if ( fdExcel.getFile() == null )
			return result;

		String filename = fdExcel.getDirectory() + fdExcel.getFile();
		
        //Map<String, List<Object>> beans = new HashMap<String, List<Object>>();
		try {
			
			//RowSetDynaClass rowSet = new RowSetDynaClass(rs, false);
			//rsc = new ResultSetCollection(rs, false);
			FileOutputStream out = new FileOutputStream(new File(filename));
			BufferedOutputStream output = new BufferedOutputStream(out);
			ResultSetToExcel rse = new ResultSetToExcel(rs, "report");
			rse.generate(output);
	        //beans.put( "report", rowSet.getRows() );
	        //XLSTransformer transformer = new XLSTransformer();
	        //transformer.transformXLS( output, beans);
	        result = true;
		} catch (NullPointerException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		}/* catch (SQLException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		} */catch (ParsePropertyException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		} /*catch (InvalidFormatException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		}*/ catch (IOException e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Funcoes.mensagemErro(null,"Erro convertendo resultado da consulta \n" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
}

