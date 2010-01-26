/**
 * @version 01/02/2001 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)Tabela.java <BR>
 * 
 * Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 * na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 * Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Objeto para guardar as informa��es necess�rias para a cria��o e utiliza��o de modelos de etiquetas.
 */

package org.freedom.objetos;

import java.util.Vector;

import org.freedom.funcoes.Funcoes;

public abstract class ObjetoEtiqueta {

	private Vector<String> vLabels = new Vector<String>();

	private Vector<String> vLabelsAdic = new Vector<String>();

	private Vector<String> vLabelsColunas = new Vector<String>();

	private Vector<String> vLabelsColunasAdic = new Vector<String>();

	private Vector<String> vValores = new Vector<String>();

	private Vector<String> vValoresAdic = new Vector<String>();

	private Vector<String> vCampos = new Vector<String>();

	private Vector<String> vCamposAdic = new Vector<String>();

	private Vector<Integer> vTams = new Vector<Integer>();

	private Vector<String> vTamsAdic = new Vector<String>();

	private Vector<String> vMascaras = new Vector<String>();

	private Vector<String> vMascarasAdic = new Vector<String>();

	private String sTexto = "";
	
	protected String PK = null;

	protected String nometabela = null;

	public ObjetoEtiqueta() {

	}

	public void adicOpcao( String sLabel, String sValor, String sCampo, Integer iTam, String sMascara, String sLabelColuna ) {

		vLabels.addElement( sLabel );
		vValores.addElement( sValor );
		vCampos.addElement( sCampo );
		vTams.addElement( iTam );
		vMascaras.addElement( sMascara );
		vLabelsColunas.addElement( sLabelColuna );
	}

	/**
	 * @return Returns the vLabels.
	 */
	public Vector<String> getLabels() {

		return vLabels;
	}

	/**
	 * @return Returns the vLabels.
	 */
	public Vector<String> getLabelsAdic() {

		return vLabelsAdic;
	}

	/**
	 * @return Returns the vCampos.
	 */
	public Vector<String> getCampos() {

		return vCampos;
	}

	/**
	 * @return Returns the vCamposAdic.
	 */
	public Vector<String> getCamposAdic() {

		return vCamposAdic;
	}

	/**
	 * @return Returns the vTams.
	 */
	public Vector<Integer> getTams() {

		return vTams;
	}

	/**
	 * @return Returns the vTamsAdic.
	 */
	public Vector<String> getTamsAdic() {

		return vTamsAdic;
	}

	/**
	 * @return Returns the vValores.
	 */
	public Vector<String> getValores() {

		return vValores;
	}

	/**
	 * @return Returns the vValoresAdic.
	 */
	public Vector<String> getValoresAdic() {

		return vValoresAdic;
	}

	/**
	 * @return Returns the vMascaras.
	 */
	public Vector<String> getMascaras() {

		return vMascaras;
	}

	/**
	 * @return Returns the vMascarasAdic.
	 */
	public Vector<String> getMascarasAdic() {

		return vMascarasAdic;
	}

	public Vector<String> getLabelsColunas() {

		return vLabelsColunas;
	}

	public Vector<String> getLabelsColunasAdic() {

		return vLabelsColunasAdic;
	}

	public void setTexto( String sTexto ) {

		this.sTexto = sTexto;
		getAdic();
	}
	
	public String getNometabela() {
		return nometabela;
	}

	protected void setNometabela(String nometabela) {
		this.nometabela = nometabela;		
	}

	public int getNumLinEtiq() {

		int iRet = 0;
		try {
			iRet = Funcoes.stringToVector( sTexto ).size();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return iRet;
	}

	public void getAdic() {

		vCamposAdic = new Vector<String>();
		vTamsAdic = new Vector<String>();
		vLabelsAdic = new Vector<String>();
		vMascarasAdic = new Vector<String>();
		vValoresAdic = new Vector<String>();
		vLabelsColunasAdic = new Vector<String>();

		for ( int i2 = 0; vValores.size() > i2; i2++ ) {
			if ( ( sTexto.indexOf( vValores.elementAt( i2 ).toString() ) ) > ( -1 ) ) {
				vCamposAdic.addElement( vCampos.elementAt( i2 ).toString() );
				vTamsAdic.addElement( vTams.elementAt( i2 ).toString() );
				vLabelsAdic.addElement( vLabels.elementAt( i2 ).toString() );
				vMascarasAdic.addElement( vMascaras.elementAt( i2 ) == null ? null : vMascaras.elementAt( i2 ).toString() );
				vValoresAdic.addElement( vValores.elementAt( i2 ).toString() );
				vLabelsColunasAdic.addElement( vLabelsColunas.elementAt( i2 ).toString() );
			}
		}

	}
	
	public String getPK() {
		return PK;
	}
	
	public abstract void setPK();
	
	
}
