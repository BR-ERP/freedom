/**
 * @version 02/08/2011 <BR>
 * @author Setpoint Tecnologia em Inform�tica Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.infra.dao <BR>
 * Classe: @(#)AbstractDAO.java <BR>
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
 * Classe base para implementa��es de m�todos de acesso a dados para tabela ATATENDIMENTO
 */

package org.freedom.modulos.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.view.frame.utility.FCRM;


public class DAOAtendimento extends AbstractDAO {

	public DbConnection con = null;
	

		

	public DAOAtendimento( DbConnection cn ) {

		super( cn );
	
	}
	
	private boolean[] getPrefs() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		boolean[] ret = new boolean[ .values().length];
		
		try {
			sql = new StringBuilder("AT.CODTPATENDO  ,AT.CODATEND , AT.CODSETAT, AT.STATUSATENDO, AT.DOCATENDO,  " );
			sql.append(  "AT.DATAATENDO, AT.DATAATENDOFIN,AT.HORAATENDO, AT.HORAATENDOFIN, AT.OBSATENDO,AT.CODCONTR,  " );
			sql.append(  " AT.CODITCONTR, AT.CODCHAMADO, AT.CODESPEC " );
			sql.append(  "FROM ATENDIMENTO AT " );
			sql.append( "WHERE AT.CODEMP=? AND AT.CODFILIAL=? " );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( "S".equals( rs.getString( PREFS.CODTPATENDO.toString() ) ) ) 
					ret[ PREFS.CODTPATENDO.ordinal() ] = true;
				if ( "S".equals(rs.getString( PREFS.CODATEND.toString() ) ) )
					ret[ PREFS.CODATEND.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.CODSETAT.toString() ) ) )
					ret[ PREFS.CODSETAT.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.STATUSATENDO.toString() ) ) )
					ret[ PREFS.STATUSATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.DOCATENDO.toString() ) ) )
					ret[ PREFS.DOCATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.DATAATENDO.toString() ) ) )
					ret[ PREFS.DATAATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.DATAATENDOFIN.toString() ) ) )
					ret[ PREFS.DATAATENDOFIN.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.HORAATENDO.toString() ) ) )
					ret[ PREFS.HORAATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.HORAATENDOFIN.toString() ) ) )
					ret[ PREFS.HORAATENDOFIN.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.OBSATENDO.toString() ) ) )
					ret[ PREFS.OBSATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.CODCONTR.toString() ) ) )
					ret[ PREFS.CODCONTR.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.CODITCONTR.toString() ) ) )
					ret[ PREFS.CODITCONTR.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.CODCHAMADO.toString() ) ) )
					ret[ PREFS.CODCHAMADO.ordinal() ] = true;
				if ( "S".equals( rs.getString(PREFS.CODESPEC.toString() ) ) )
					ret[ PREFS.CODESPEC.ordinal() ] = true;
				
	
			}
			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
		return ret;
	}
	
	
	
	
	private void insertAtend() throws Exception {

	
		StringBuilder sql = new StringBuilder();

		sql.append( "EXECUTE PROCEDURE ATADICATENDIMENTOCLISP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );

		ps.setString( 1, (String) ORets[ 0 ] ); // Tipo de atendimento
		ps.setString( 2, (String) ORets[ 1 ] ); // Codigo do atendente
		ps.setString( 3, (String) ORets[ 2 ] ); // Setor de atendimento
		ps.setInt( 4, Aplicativo.iCodEmp ); // C�digo da empresa
		ps.setInt( 5, Aplicativo.iCodFilialPad ); // C�digo da filial
		ps.setInt( 6, Integer.parseInt( (String) ORets[ 3 ] ) ); // Nro. do atendimento
		ps.setDate( 7, Funcoes.dateToSQLDate( (Date) ORets[ 4 ] ) ); // Data de inicio do atendimento
		ps.setDate( 8, Funcoes.dateToSQLDate( (Date) ORets[ 5 ] ) ); // Data final do atendimento
		ps.setTime( 9, Funcoes.strTimeTosqlTime( ORets[ 6 ].toString() ) ); // Hora inicial do atendimento
		ps.setTime( 10, Funcoes.strTimeTosqlTime( ORets[ 7 ].toString() ) ); // Hora final do atendimento
		ps.setString( 11, (String) ORets[ 8 ] ); // Descri��o do atendimento
		ps.setInt( 12, Aplicativo.iCodEmp ); // C�digo da empresa do cliente
		ps.setInt( 13, Aplicativo.iCodFilialPad ); // C�digo da filial do cliente
		ps.setInt( 14, txtCodCli.getVlrInteger() ); // C�digo do cliente

		if ( cbContrato.getVlrInteger() == -1 ) {
			ps.setNull( 15, Types.INTEGER ); // C�digo da empresa do contrato
			ps.setNull( 16, Types.INTEGER ); // C�digo da filial do contrato
			ps.setNull( 17, Types.INTEGER ); // C�digo do contrato
		}
		else {
			ps.setInt( 15, Aplicativo.iCodEmp ); // C�digo da empresa do contrato
			ps.setInt( 16, Aplicativo.iCodFilialPad ); // C�digo da filial do contrato
			ps.setInt( 17, cbContrato.getVlrInteger() ); // C�digo do contrato
		}
		if ( cbitContrato.getVlrInteger() == -1 ) {
			ps.setInt( 18, cbContrato.getVlrInteger() ); // C�digo do �tem de contrato
		}
		else {
			ps.setInt( 18, cbitContrato.getVlrInteger() ); // C�digo do �tem de contrato
		}

		if ( codrec != null && nparcitrec != null ) {
			ps.setInt( 19, codrec ); // C�digo do contas a receber
			ps.setInt( 20, nparcitrec ); // C�digo do �tem do contas a receber
		}
		else {
			ps.setNull( 19, Types.INTEGER ); // C�digo do contas a receber
			ps.setNull( 20, Types.INTEGER ); // C�digo do �tem do contas a receber
		}

		if ( txtCodChamado.getVlrInteger() > 0 ) {
			ps.setInt( 21, lcChamado.getCodEmp() ); // C�digo da empresa do chamado
			ps.setInt( 22, lcChamado.getCodFilial() ); // C�digo da filial do chamado
			ps.setInt( 23, txtCodChamado.getVlrInteger() ); // C�digo do chamado
		}
		else {
			ps.setNull( 21, Types.INTEGER );
			ps.setNull( 22, Types.INTEGER );
			ps.setNull( 23, Types.INTEGER );
		}

		ps.setString( 24, txaObsInterno.getVlrString() );

		ps.setString( 25, cbConcluiChamado.getVlrString() );

		if ( txtCodEspec.getVlrInteger() > 0 ) {
			ps.setInt( 26, lcEspec.getCodEmp() ); // C�digo da empresa do especifica��o
			ps.setInt( 27, lcEspec.getCodFilial() ); // C�digo da filial da especifica��o
			ps.setInt( 28, txtCodEspec.getVlrInteger() ); // C�digo da especifica��o
		}
		else if (financeiro){
			ps.setNull( 26, Types.INTEGER );
			ps.setNull( 27, Types.INTEGER );
			ps.setNull( 28, Types.INTEGER );
		}
		else {
			Funcoes.mensagemInforma(null,"Informe a especifica��o do atendimento!");
			txtCodEspec.requestFocus();
		}


		ps.execute();
		ps.close();

		con.commit();

		if(corig instanceof FCRM) {
			
			(( FCRM ) corig).carregaAtendimentos();	
			
		}
		
		
	}
	
	private void updateAtend() throws Exception {

		StringBuilder sql = new StringBuilder();

		sql.append( "update atatendimento a set  " );

		sql.append( "a.codatend=?, a.dataatendo=?, a.dataatendofin=?, " );
		sql.append( "a.horaatendo=?, a.horaatendofin=?, a.obsatendo=?, " );
		sql.append( "a.codempto=?, a.codfilialto=?, a.codtpatendo=?, " );
		sql.append( "a.codempsa=?, a.codfilialsa=?, a.codsetat=?, " );
		sql.append( "a.codempch=?, a.codfilialch=?, a.codchamado=?, " );
		sql.append( "a.codempct=?, a.codfilialct=?, a.codcontr=?, a.coditcontr=?, " );
		sql.append( "a.statusatendo=?, a.obsinterno=?, a.concluichamado=?, " );
		sql.append( "a.codempea=?, a.codfilialea=?, a.codespec=?, ");
		sql.append( "a.codempcl=?, a.codfilialcl=?, a.codcli=? ");

		sql.append( "where a.codemp=? and a.codfilial=? and a.codatendo=? " );

		PreparedStatement ps = con.prepareStatement( sql.toString() );

		ps.setInt( 1, txtCodAtend.getVlrInteger() );
		ps.setDate( 2, Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate() ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( txtDataAtendimentoFin.getVlrDate() ) );

		ps.setTime( 4, Funcoes.strTimeTosqlTime( txtHoraini.getVlrString() ) );
		ps.setTime( 5, Funcoes.strTimeTosqlTime( txtHorafim.getVlrString() ) );
		ps.setString( 6, txaDescAtend.getVlrString() );

		ps.setInt( 7, Aplicativo.iCodEmp );
		ps.setInt( 8, ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
		ps.setInt( 9, cbTipo.getVlrInteger() );

		ps.setInt( 10, Aplicativo.iCodEmp );
		ps.setInt( 11, ListaCampos.getMasterFilial( "ATSETOR" ) );
		ps.setInt( 12, cbSetor.getVlrInteger() );

		if ( txtCodChamado.getVlrInteger() > 0 ) {
			ps.setInt( 13, lcChamado.getCodEmp() ); // C�digo da empresa do chamado
			ps.setInt( 14, lcChamado.getCodFilial() ); // C�digo da filial do chamado
			ps.setInt( 15, txtCodChamado.getVlrInteger() ); // C�digo do chamado
		}
		else {
			ps.setNull( 13, Types.INTEGER );
			ps.setNull( 14, Types.INTEGER );
			ps.setNull( 15, Types.INTEGER );
		}

		ps.setInt( 16, Aplicativo.iCodEmp );
		ps.setInt( 17, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );

		if ( cbContrato.getVlrInteger() == -1 ) {
			ps.setNull( 18, Types.INTEGER );
		}
		else {
			ps.setInt( 18, cbContrato.getVlrInteger() );
		}
		if ( cbitContrato.getVlrInteger() == -1 ) {
			ps.setInt( 19, cbContrato.getVlrInteger() );
		}
		else {
			ps.setInt( 19, cbitContrato.getVlrInteger() );
		}

		ps.setString( 20, cbStatus.getVlrString() );

		ps.setString( 21, txaObsInterno.getVlrString() );

		ps.setString( 22, cbConcluiChamado.getVlrString() );

		if ( txtCodEspec.getVlrInteger() > 0 ) {
			ps.setInt( 23, Aplicativo.iCodEmp );
			ps.setInt( 24, ListaCampos.getMasterFilial( "ATESPECATEND" ) );
			ps.setInt( 25, txtCodEspec.getVlrInteger() );
		}
		else {
			ps.setNull( 23, Types.INTEGER );
			ps.setNull( 24, Types.INTEGER );
			ps.setNull( 25, Types.INTEGER );
		}


		ps.setInt( 26, Aplicativo.iCodEmp );
		ps.setInt( 27, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
		ps.setInt( 28, txtCodCli.getVlrInteger() );

		ps.setInt( 29, Aplicativo.iCodEmp );
		ps.setInt( 30, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
		ps.setInt( 31, txtCodAtendo.getVlrInteger() );

		ps.executeUpdate();

		con.commit();
		
		ps.close();
		
		if(corig instanceof FCRM) {
			
			(( FCRM ) corig).carregaAtendimentos();	
			
		}
		

	}
	



}
