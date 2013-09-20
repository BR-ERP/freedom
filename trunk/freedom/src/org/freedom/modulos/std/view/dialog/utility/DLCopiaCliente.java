package org.freedom.modulos.std.view.dialog.utility;

/**
 * 
 * @version 30/07/2011 <BR>
 * @author Setpoint Inform�tica Ltda. <BR>
 * @author Fabiano Frizzo(ffrizzo at gmail.com) <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.dialog.utility <BR>
 *         Classe:
 * @(#)DLCopiaCliente.java <BR>
 * 
 *                   Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                   na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                   Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                   Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                   Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 */


import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FDialogo;
import org.freedom.library.swing.frame.Aplicativo;


public class DLCopiaCliente extends FDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCnpj = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );
	private JTextFieldPad txtCpf = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );
	
	private String tipoPessoa;
	private String cnpjAtual;
	private boolean permiteMesmoCnpj;
	private boolean validacpf = false;
	
	public DLCopiaCliente(String tipoPessoa, String cnpjAtual, boolean permiteMesmoCnpj, boolean validacpf) {
		super();
		setTitulo( "Copiar Cliente" );
		setAtribos( 230, 130 );
		
		this.tipoPessoa = tipoPessoa;
		this.cnpjAtual = cnpjAtual;
		this.permiteMesmoCnpj = permiteMesmoCnpj;
		this.validacpf = validacpf;
		
		this.montaTela();
	}

	private void montaTela() {
		
		txtCpf.setMascara( JTextFieldPad.MC_CPF );
		txtCnpj.setMascara( JTextFieldPad.MC_CNPJ );

		if(permiteMesmoCnpj){
			txtCnpj.setText( cnpjAtual );
		}
		
		if("F".equals( tipoPessoa )){
			adic( new JLabelPad( "Cpf" ), 7, 10, 100, 20 );
			adic( txtCpf, 7, 30, 130, 20 );
		}else if("J".equals( tipoPessoa )){
			adic( new JLabelPad( "Cnpj" ), 7, 10, 100, 20 );
			adic( txtCnpj, 7, 30, 130, 20 );
		}
	}
	
	private boolean duploCNPJCPF() {

		boolean bRetorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select cnpjcli, cpfcli from vdcliente where codemp=? and codfilial=? and ");
		if ("F".equals(tipoPessoa)) {
			sql.append("cpfcli=?");
		} else {
			sql.append("cnpjcli=?");
		}
		try {
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setString( 3, getDocumento() );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				bRetorno = true;
			}
			rs.close();
			ps.close();
			con.commit();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao checar CNPJ.\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
		}
		return bRetorno;
	}
	
	public String getDocumento(){
		if("F".equals( tipoPessoa )){
			return txtCpf.getVlrString();
		}
		
		return txtCnpj.getVlrString();
	}
	
	@ Override
	public void actionPerformed( ActionEvent evt ) {

		if(evt.getSource() == btOK){
			if(!permiteMesmoCnpj && duploCNPJCPF()){
				if ("F".equals( tipoPessoa )) {
					Funcoes.mensagemInforma( this, "Cpf j� cadastrado");
				} else {
					Funcoes.mensagemInforma( this, "Cnpj j� cadastrado");
				}
				return;
			}
			if ("F".equals( tipoPessoa )) {
				if (validacpf && !Funcoes.ValidaCPF( getDocumento() )) {
					Funcoes.mensagemInforma( this, "CPF Inv�lido !" );
					return;
				}
			} else {
				if (!Funcoes.ValidaCNPJ( getDocumento() )) {
					Funcoes.mensagemInforma(this, "CNPJ Inv�lido !");
					return;
				}
			}
		}
		
		super.actionPerformed( evt );
	}
}

