/**
 * @version 05/07/2005 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)TabVector.java <BR>
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
 * Coment�rios sobre a classe...
 *  
 */
package org.freedom.componentes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

public class TabVector {

	private Vector<Vector<Object>> vRows = new Vector<Vector<Object>>();
	int cols = 0;
	int row = -1;
	public TabVector(int cols) {
		super();
		this.cols = cols;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public boolean next() {
		if (row<(vRows.size()-1)) {
			row++;
			return true;
		}
		else
			return false;
	}
	
	public void addRow() {
		Vector<Object> vTemp = new Vector<Object>();
		vTemp.setSize(cols);
		vRows.addElement(vTemp);
		row++;
	}
	
	public void setInt(int pos, int value) {
		setObject(pos, new Integer(value));
	}
	
	public int getInt(int pos) {
		int value = 0;
		Object o = getObject(pos);
		if (o!=null) 
			value = ((Integer) o).intValue();
		return value;
	}

	public void setString(int pos, String value) {
		setObject(pos, value);
	}
	
	public String getString(int pos) {
		String value = null;
		Object o = getObject(pos);
		if (o!=null) 
			value = o.toString();
		return value;
	}
	
	public void setDate(int pos, Date value) {
		setObject(pos, value);
	}
	
	public Date getDate(int pos) {
		Date value = null;
		Object o = getObject(pos);
		if (o!=null) 
			value = (Date) o;
		return value;
	}

	//M�todo em desuso pois pode ocasionar problemas de arredondamento.	
	@Deprecated
	public void setFloat(int pos, float value) {
		setObject(pos, new BigDecimal(value));
	}

	//M�todo em desuso pois pode ocasionar problemas de arredondamento.
	@Deprecated
	public float getFloat(int pos) {
		float value = 0;
		Object o = getObject(pos);
		if (o!=null) 
			value = ((BigDecimal) o).floatValue();
		return value;
	}
	
	public void setBigDecimal(int pos, BigDecimal value) {
		setObject(pos, value);
	}
	
	public BigDecimal getBigDecimal(int pos) {
		BigDecimal value = new BigDecimal(0);
		Object o = getObject(pos);
		if (o!=null) 
			value = (BigDecimal) o;
		return value;
	}
	
	public void setObject(int pos, Object obj) {
		Vector<Object> v = getCol(this.row);
		if (v!=null) { 
			v.setElementAt(obj, pos);
			vRows.setElementAt(v, this.row);
		}
	}

	public Object getObject(int pos) {
		Object o = null;
		Vector<Object> v = getCol(this.row);
		if (v!=null) 
			o = v.elementAt(pos);
		return o;
	}
	
	public Vector<Object> getCol(int row) {
		Vector<Object> col = null;
		if ((row!=-1) && (row<vRows.size()) )
			col = vRows.elementAt(row);
		return col;	
	}

}
