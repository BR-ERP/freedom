/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLLote.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.gms;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.swing.JScrollPane;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.JLabelPad;
import org.freedom.library.JTextAreaPad;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


public class DLSerie extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtNumSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDtFabricSerie = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtValidSerie = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JLabelPad lbNumSerie = new JLabelPad( "N�mero de s�rie" );

	private JLabelPad lbCodProd = new JLabelPad( "C�d.prod." );

	private JLabelPad lbDescProd = new JLabelPad( "Descri��o do produto" );

	private JLabelPad lbDtFabricSerie = new JLabelPad( "Data de Fabrica��o" );

	private JLabelPad lbDtValidSerie = new JLabelPad( "Data de validade" );
	
	private JLabelPad lbObsSerie = new JLabelPad( "Observa��es" );
		
	private JTextAreaPad txaObsSerie = new JTextAreaPad();
	
	private JScrollPane spnObsSerie = new JScrollPane( txaObsSerie );

	private boolean novo = true;
	
	private enum ORDEM_INSERT {
		CODEMP, CODFILIAL, CODPROD, NUMSERIE, DTFABRICSERIE, DTVALIDSERIE, OBSSERIE
	}

	private enum ORDEM_UPDATE {
		DTFABRICSERIE, DTVALIDSERIE, OBSSERIE, CODEMP, CODFILIAL, CODPROD, NUMSERIE 
	}
	
	public DLSerie( Component orig, String numserie, Integer codprod, String descprod, DbConnection cn ) {

		super( orig );
		setConexao( cn );
		setTitulo( "S�rie" );
		setAtribos( 380, 300 );

		txtCodProd.setEditable( false );

		txtNumSerie.setRequerido( true );

		adic( lbNumSerie, 7, 0, 353, 20 );
		adic( txtNumSerie, 7, 20, 353, 20 );

		adic( lbCodProd, 7, 40, 60, 20 );
		adic( txtCodProd, 7, 60, 60, 20 );

		adic( lbDescProd, 70, 40, 290, 20 );
		adic( txtDescProd, 70, 60, 290, 20 );
		
		adic( lbDtFabricSerie, 7, 80, 175, 20 );
		adic( txtDtFabricSerie, 7, 100, 175, 20 );

		adic( lbDtValidSerie, 185, 80, 175, 20 );
		adic( txtDtValidSerie, 185, 100, 175, 20 );
		
		adic(lbObsSerie, 7, 120, 353, 20 );
		adic(spnObsSerie, 7, 140, 353, 60 );
		
		txtNumSerie.setVlrString( numserie );
		txtCodProd.setVlrInteger( codprod );
		txtDescProd.setVlrString( descprod );

		txtNumSerie.requestFocus();
	}

	public void setNumSerie(String numserie) {
		txtNumSerie.setVlrString( numserie );
	}
	
	public void carregaSerie() {
		StringBuilder sql = new StringBuilder();
		
		try {
			
			
			sql.append( "select se.numserie, se.dtfabricserie, se.dtvalidserie, se.obsserie " );
			sql.append( "from eqserie se ");
			sql.append( "where se.codemp=? and se.codfilial=? and se.codprod=? and se.numserie=? " );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQSERIE" ) );
			ps.setInt( 3, txtCodProd.getVlrInteger() );
			ps.setString( 4, txtNumSerie.getVlrString() );			

			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				txtDtFabricSerie.setVlrDate( rs.getDate( "dtfabricserie" ) );
				txtDtValidSerie.setVlrDate( rs.getDate( "dtvalidserie" ) );
				txaObsSerie.setVlrString( rs.getString( "obsserie" ) );
				
				novo = false;
				
			}
			
			con.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean gravaSerie() {
		StringBuilder sql = new StringBuilder();
		boolean bRet = false;
		PreparedStatement ps = null;
		
		try {
		
			if(novo) {
				
				sql.append("INSERT INTO EQSERIE (CODEMP,CODFILIAL,CODPROD,NUMSERIE,DTFABRICSERIE,DTVALIDSERIE,OBSSERIE) VALUES(?,?,?,?,?,?,?)");
			
				ps = con.prepareStatement( sql.toString() );
			
		
			}
			else {
				
				sql.append("UPDATE EQSERIE SET DTFABRICSERIE=?, DTVALIDSERIE=? ,OBSSERIE=? ");
				sql.append("WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=? AND NUMSERIE=? ");
			
				ps = con.prepareStatement( sql.toString() );
				
			}	
		
			ps.setInt( novo ? ORDEM_INSERT.CODEMP.ordinal() +1 : ORDEM_UPDATE.CODEMP.ordinal() +1, Aplicativo.iCodEmp );
			ps.setInt( novo ? ORDEM_INSERT.CODFILIAL.ordinal() +1 : ORDEM_UPDATE.CODFILIAL.ordinal() +1, ListaCampos.getMasterFilial( "EQSERIE" ) );
			ps.setInt( novo ? ORDEM_INSERT.CODPROD.ordinal() +1 : ORDEM_UPDATE.CODPROD.ordinal() +1, txtCodProd.getVlrInteger() );
			
			ps.setString( novo ? ORDEM_INSERT.NUMSERIE.ordinal() +1 : ORDEM_UPDATE.NUMSERIE.ordinal() +1, txtNumSerie.getVlrString() );			
		
			if ( txtDtFabricSerie.getVlrString().equals( "" ) ) {
				ps.setNull( novo ? ORDEM_INSERT.DTFABRICSERIE.ordinal() +1 : ORDEM_UPDATE.DTFABRICSERIE.ordinal() +1, Types.DATE );
			}
			else {
				ps.setDate( novo ? ORDEM_INSERT.DTFABRICSERIE.ordinal() +1 : ORDEM_UPDATE.DTFABRICSERIE.ordinal() +1, Funcoes.dateToSQLDate( txtDtFabricSerie.getVlrDate() ) );
			}
			
			if ( txtDtValidSerie.getVlrString().equals( "" ) ) {
				ps.setNull( novo ? ORDEM_INSERT.DTVALIDSERIE.ordinal() +1 : ORDEM_UPDATE.DTVALIDSERIE.ordinal() +1, Types.DATE );
			}
			else {
				ps.setDate( novo ? ORDEM_INSERT.DTVALIDSERIE.ordinal() +1 : ORDEM_UPDATE.DTVALIDSERIE.ordinal() +1, Funcoes.dateToSQLDate( txtDtValidSerie.getVlrDate() ) );
			}
			
			if (txaObsSerie.getVlrString().equals( "" ) ) {
				ps.setNull( novo ? ORDEM_INSERT.OBSSERIE.ordinal() +1 : ORDEM_UPDATE.OBSSERIE.ordinal() +1, Types.CHAR );
			}
			else {
				ps.setString( novo ? ORDEM_INSERT.OBSSERIE.ordinal() +1 : ORDEM_UPDATE.OBSSERIE.ordinal() +1, txaObsSerie.getVlrString() );
			}
			
			ps.executeUpdate();

			ps.close();
			
			con.commit();

			bRet = true;
			
		} 
		catch ( SQLException err ) {
			if ( err.getErrorCode() == ListaCampos.FB_PK_DUPLA ) {
				Funcoes.mensagemErro( this, "Este n�mero de s�rie j� existe!" );
			}
			else {
				Funcoes.mensagemErro( this, "Erro ao inserir registro na tabela EQSERIE!\n" + err.getMessage(), true, con, err );
			}
		}
		return bRet;
	}

	public String getNumSerie() {

		return txtNumSerie.getVlrString();
		
	}
	
	public Date getDtFabricSerie() {
		return txtDtFabricSerie.getVlrDate();
	}
	
	public Date getDtValidSerie() {
		return txtDtValidSerie.getVlrDate();
	}
	
	public String getObsSerie() {
		return txaObsSerie.getVlrString();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( txtNumSerie.getVlrString().length() == 0 ) {
				Funcoes.mensagemInforma( this, "Campo n�mero de s�rie � requerido!" );
				txtNumSerie.requestFocus();
			}
			else {
				if ( gravaSerie() )
					super.actionPerformed( evt );
			}
		}
		else if ( evt.getSource() == btCancel ) {
			super.actionPerformed( evt );
		}
	}
}
