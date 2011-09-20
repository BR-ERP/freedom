/**
 * @version 17/09/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gpe.object <BR>
 *         Classe:
 * @(#)Batida.java <BR>
 * 
 *                Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *              Classe de constantes e bean de transporte, utilizada em tela FBatida e na classe DAOBatida.
 * 
 */
package org.freedom.modulos.gpe.business.object;

import java.util.Date;


public class Batida {
	public static enum PREFS {LANCAPONTOAF};
	public static enum PARAM_PROC_CARREGA {NONE, CODEMP, CODFILIAL, IDUSU, AFTELA };
	public static enum PARAM_PROC_INSERE {NONE, CODEMP, CODFILIAL, DTBAT, HBAT, CODEMPEP, CODFILIALEP, MATEMPR }
	public static enum COL_PROC {CARREGAPONTO, DATAPONTO, HORAPONTO, CODEMPAE, 
		CODFILIALAE, CODATEND, CODEMPEP, CODFILIALEP, MATEMPR };
		
	private Integer codemp;
	
	private Integer codfilial;
	
	private Integer codempus;
	
	private Integer codfilialus;
	
	private String idusu;
	
	private String aftela;
	
	private String carregaponto;
	
	private Date dataponto;
	
	private String horaponto;
	
	private Integer codempae;
	
	private Integer codfilialae;
	
	private Integer codatend;
	
	private Integer codempep;
	
	private Integer codfilialep;
	
	private Integer matempr;

	
	
	public Integer getCodemp() {
	
		return codemp;
	}


	
	public void setCodemp( Integer codemp ) {
	
		this.codemp = codemp;
	}


	
	public Integer getCodfilial() {
	
		return codfilial;
	}


	
	public void setCodfilial( Integer codfilial ) {
	
		this.codfilial = codfilial;
	}


	public Integer getCodempus() {
	
		return codempus;
	}

	
	public void setCodempus( Integer codempus ) {
	
		this.codempus = codempus;
	}

	
	public Integer getCodfilialus() {
	
		return codfilialus;
	}

	
	public void setCodfiliaus( Integer codfilialus ) {
	
		this.codfilialus = codfilialus;
	}

	
	public String getIdusu() {
	
		return idusu;
	}

	
	public void setIdusu( String idusu ) {
	
		this.idusu = idusu;
	}

	
	public String getAftela() {
	
		return aftela;
	}

	
	public void setAftela( String aftela ) {
	
		this.aftela = aftela;
	}

	
	public String getCarregaponto() {
	
		return carregaponto;
	}

	
	public void setCarregaponto( String carregaponto ) {
	
		this.carregaponto = carregaponto;
	}

	
	public Date getDataponto() {
	
		return dataponto;
	}

	
	public void setDataponto( Date dataponto ) {
	
		this.dataponto = dataponto;
	}

	
	public String getHoraponto() {
	
		return horaponto;
	}

	
	public void setHoraponto( String horaponto ) {
	
		this.horaponto = horaponto;
	}

	
	public Integer getCodempae() {
	
		return codempae;
	}

	
	public void setCodempae( Integer codempae ) {
	
		this.codempae = codempae;
	}

	
	public Integer getCodfilialae() {
	
		return codfilialae;
	}

	
	public void setCodfilialae( Integer codfilialae ) {
	
		this.codfilialae = codfilialae;
	}

	
	public Integer getCodatend() {
	
		return codatend;
	}

	
	public void setCodatend( Integer codatend ) {
	
		this.codatend = codatend;
	}

	
	public Integer getCodempep() {
	
		return codempep;
	}

	
	public void setCodempep( Integer codempep ) {
	
		this.codempep = codempep;
	}

	
	public Integer getCodfilialep() {
	
		return codfilialep;
	}

	
	public void setCodfilialep( Integer codfilialep ) {
	
		this.codfilialep = codfilialep;
	}

	
	public Integer getMatempr() {
	
		return matempr;
	}

	
	public void setMatempr( Integer matempr ) {
	
		this.matempr = matempr;
	}
	
}
