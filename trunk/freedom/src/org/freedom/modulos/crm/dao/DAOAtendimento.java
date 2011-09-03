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
import java.sql.Types;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.object.Atendimento;

enum PROC_IU {
	NONE, IU, CODEMP, CODFILIAL, CODATENDO, CODEMPTO, CODFILIALTO, CODTPATENDO, 
	CODEMPAE, CODFILIALAE, CODATEND, CODEMPSA, CODFILIALSA, CODSETAT, 
	DOCATENDO, DATAATENDO, DATAATENDOFIN, HORAATENDO, HORAATENDOFIN, 
	OBSATENDO, CODEMPCL, CODFILIALCL, CODCLI,
	CODEMPCT, CODFILIALCT, CODCONTR, CODITCONTR, 
	CODEMPIR, CODFILIALIR, CODREC, NPARCITREC, 
	CODEMPCH, CODFILIALCH,CODCHAMADO, 
	OBSINTERNO, CONCLUICHAMADO, CODEMPEA, CODFILIALEA, CODESPEC, 
	CODEMPUS, CODFILIALUS, IDUSU, STATUSATENDO
}

public class DAOAtendimento extends AbstractDAO {
	private boolean financeiro = false;
	
	public DbConnection con = null;
	
	private boolean prefs[] = null;

	public DAOAtendimento( DbConnection cn ) {

		super( cn );
		//setPrefs();
	
	}
	
	/*private void setPrefs() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new boolean[ Atendimento.PREFS.values().length];
		
		try {
			sql = new StringBuilder("AT.CODTPATENDO, AT.CODATEND , AT.CODSETAT, AT.STATUSATENDO, AT.DOCATENDO,  " );
			sql.append(  "AT.DATAATENDO, AT.DATAATENDOFIN,AT.HORAATENDO, AT.HORAATENDOFIN, AT.OBSATENDO, AT.CODCLI, " );
			sql.append(  " AT.CODCONTR, AT.CODITCONTR, AT.CODCHAMADO, AT.OBSINTERNO, AT.CODESPEC " );
			sql.append(  "FROM ATENDIMENTO AT " );
			sql.append( "WHERE AT.CODEMP=? AND AT.CODFILIAL=? " );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "ATATENDIMENTO" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( "S".equals( rs.getString( Atendimento.PREFS.CODTPATENDO.toString() ) ) ) 
					prefs[ Atendimento.PREFS.CODTPATENDO.ordinal() ] = true;
				if ( "S".equals(rs.getString( Atendimento.PREFS.CODATEND.toString() ) ) )
					prefs[ Atendimento.PREFS.CODATEND.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODSETAT.toString() ) ) )
					prefs[ Atendimento.PREFS.CODSETAT.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.STATUSATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.STATUSATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.DOCATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.DOCATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.DATAATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.DATAATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.DATAATENDOFIN.toString() ) ) )
					prefs[ Atendimento.PREFS.DATAATENDOFIN.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.HORAATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.HORAATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.HORAATENDOFIN.toString() ) ) )
					prefs[ Atendimento.PREFS.HORAATENDOFIN.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.OBSATENDO.toString() ) ) )
					prefs[ Atendimento.PREFS.OBSATENDO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODCLI.toString() ) ) )
					prefs[ Atendimento.PREFS.CODCLI.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODCONTR.toString() ) ) )
					prefs[ Atendimento.PREFS.CODCONTR.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODITCONTR.toString() ) ) )
					prefs[ Atendimento.PREFS.CODITCONTR.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODCHAMADO.toString() ) ) )
					prefs[ Atendimento.PREFS.CODCHAMADO.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.CODESPEC.toString() ) ) )
					prefs[ Atendimento.PREFS.CODESPEC.ordinal() ] = true;
				if ( "S".equals( rs.getString(Atendimento.PREFS.OBSINTERNO.toString() ) ) )
					prefs[ Atendimento.PREFS.OBSINTERNO.ordinal() ] = true;
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
	}
	
	*/
	public void insert(Atendimento atd) throws Exception {
	
		StringBuilder sql = new StringBuilder();

		sql.append( "EXECUTE PROCEDURE ATATENDIMENTOIUSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		
		
	/*	     
	CODEMPUS, CODFILIALUS, IDUSU, STATUSATENDO
}*/
		
		
		ps.setString( PROC_IU.IU.ordinal(), "I" ); // Define o modo insert para a procedure
		ps.setInt( PROC_IU.CODEMP.ordinal() , atd.getCodemp() ); // C�digo da empresa
		ps.setInt( PROC_IU.CODFILIAL.ordinal(), atd.getCodfilial() ); // C�digo da filial
		
		if ( atd.getCodatendo() == null ) {
			ps.setInt( PROC_IU.CODATENDO.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODATENDO.ordinal() , atd.getCodatendo() ); // C�digo do atendimento
		}
		
		if ( atd.getCodtpatendo() == null) {
			ps.setNull( PROC_IU.CODEMPTO.ordinal(), Types.INTEGER ); // C�digo da empresa tipo atendimento
			ps.setNull( PROC_IU.CODFILIALTO.ordinal(), Types.INTEGER ); // C�digo da filial tipo atendimento
			ps.setNull( PROC_IU.CODTPATENDO.ordinal(), Types.INTEGER ); // C�digo tipo atendimento
		}
		else {
			ps.setInt( PROC_IU.CODEMPTO.ordinal(), atd.getCodempto() ); // C�digo da empresa tipo atendimento
			ps.setInt( PROC_IU.CODFILIALTO.ordinal(), atd.getCodfilialto() ); // C�digo da filial tipo atendimento
			ps.setInt( PROC_IU.CODTPATENDO.ordinal(), atd.getCodtpatendo() ); // // C�digo tipo atendimento
		}

		if ( atd.getCodatend() == null) {
			ps.setNull( PROC_IU.CODEMPAE.ordinal(), Types.INTEGER ); // C�digo da empresa atendente
			ps.setNull( PROC_IU.CODFILIALAE.ordinal(), Types.INTEGER ); // C�digo da filial atendente
			ps.setNull( PROC_IU.CODATEND.ordinal(), Types.INTEGER ); // C�digo atendente
		}
		else {
			ps.setInt( PROC_IU.CODEMPAE.ordinal(), atd.getCodempae() ); // C�digo da empresa atendente
			ps.setInt( PROC_IU.CODFILIALAE.ordinal(), atd.getCodfilialae() ); // C�digo da filial atendente
			ps.setInt( PROC_IU.CODATEND.ordinal(), atd.getCodatend() ); // C�digo atendente
		}
		
		if ( atd.getCodatend() == null) {
			ps.setNull( PROC_IU.CODEMPSA.ordinal(), Types.INTEGER ); // C�digo da empresa situa��o atendimento
			ps.setNull( PROC_IU.CODFILIALSA.ordinal(), Types.INTEGER ); // C�digo da filial do contrato
			ps.setNull( PROC_IU.CODSETAT.ordinal(), Types.INTEGER ); // C�digo do contrato
		}
		else {
			ps.setInt( PROC_IU.CODEMPSA.ordinal(), atd.getCodempae() ); // C�digo da empresa do contrato
			ps.setInt( PROC_IU.CODFILIALSA.ordinal(), atd.getCodfilialae() ); // C�digo da filial do contrato
			ps.setInt( PROC_IU.CODSETAT.ordinal() , atd.getCodsetat() ); // Setor de atendimento
		}
		
		if ( atd.getIdusu() == null ) {
			ps.setInt( PROC_IU.CODEMPUS.ordinal(), Types.INTEGER ); // C�digo Empresa Usu�rio
			ps.setInt( PROC_IU.CODFILIALUS.ordinal(), Types.INTEGER ); // C�digo Filial Usu�rio
			ps.setInt( PROC_IU.IDUSU.ordinal(), Types.INTEGER ); // Id Usu�rio
		}
		else
		{
			ps.setInt( PROC_IU.CODEMPUS.ordinal(), atd.getCodempus() ); // C�digo Empresa Usu�rio
			ps.setInt( PROC_IU.CODFILIALUS.ordinal(), atd.getCodfilialus() ); // C�digo Filial Usu�rio
			ps.setString( PROC_IU.IDUSU.ordinal(), atd.getIdusu() ); // Id Usu�rio
		}
		
		ps.setString( PROC_IU.DOCATENDO.ordinal() , atd.getDocatendo() ); // Nro. do atendimento
		ps.setDate( PROC_IU.DATAATENDO.ordinal() ,Funcoes.dateToSQLDate( atd.getDataatendo() ) ); // Data de inicio do atendimento
		ps.setDate( PROC_IU.DATAATENDOFIN.ordinal(), Funcoes.dateToSQLDate( atd.getDataatendofin() ) ); // Data final do atendimento
		ps.setTime( PROC_IU.HORAATENDO.ordinal() , Funcoes.strTimeTosqlTime( atd.getHoraatendo().toString() ) ) ; // Hora inicial do atendimento
		ps.setTime( PROC_IU.HORAATENDOFIN.ordinal()	, Funcoes.strTimeTosqlTime( atd.getHoraatendofin().toString() ) ) ; // Hora final do atendimento
		ps.setString( PROC_IU.OBSATENDO.ordinal(), atd.getObsatendo() ); // Descri��o do atendimento
		ps.setString( PROC_IU.OBSINTERNO.ordinal(), atd.getObsinterno() ); // Observa��es internas
		ps.setString( PROC_IU.CONCLUICHAMADO.ordinal(), atd.getConcluichamado() );
		
		ps.setString( PROC_IU.STATUSATENDO.ordinal(), atd.getStatusatendo() ); // Status atendimento
		
		if ( atd.getCodcli() == null) {
			ps.setNull( PROC_IU.CODEMPCL.ordinal(), Types.INTEGER ); // C�digo da empresa do contrato
			ps.setNull( PROC_IU.CODFILIALCL.ordinal(), Types.INTEGER ); // C�digo da filial do contrato
			ps.setNull( PROC_IU.CODCLI.ordinal(), Types.INTEGER ); // C�digo do contrato
		}
		else {
			ps.setInt( PROC_IU.CODEMPCL.ordinal(), atd.getCodempcl() ); // C�digo da empresa do cliente
			ps.setInt( PROC_IU.CODFILIALCL.ordinal(), atd.getCodfilialcl() ); // C�digo da filial do cliente
			ps.setInt( PROC_IU.CODCLI.ordinal() , atd.getCodcli() ); // C�digo do cliente
		} 
		
		if ( atd.getCodcontr() == null) {
			ps.setNull( PROC_IU.CODEMPCT.ordinal(), Types.INTEGER ); // C�digo da empresa do contrato
			ps.setNull( PROC_IU.CODFILIALCT.ordinal(), Types.INTEGER ); // C�digo da filial do contrato
			ps.setNull( PROC_IU.CODCONTR.ordinal(), Types.INTEGER ); // C�digo do contrato
			ps.setNull( PROC_IU.CODITCONTR.ordinal(), Types.INTEGER ); // C�digo do item do contrato
		}
		else {
			ps.setInt( PROC_IU.CODEMPCT.ordinal(), Aplicativo.iCodEmp ); // C�digo da empresa do contrato
			ps.setInt( PROC_IU.CODFILIALCT.ordinal(), Aplicativo.iCodFilialPad ); // C�digo da filial do contrato
			ps.setInt( PROC_IU.CODCONTR.ordinal(), atd.getCodcontr()  ); // C�digo do contrato
			ps.setInt( PROC_IU.CODITCONTR.ordinal(), atd.getCoditcontr()  ); // C�digo do �tem de contrato
		}

		if ( atd.getCodrec()== null ) {
			ps.setNull( PROC_IU.CODEMPIR.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALIR.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODREC.ordinal(), Types.INTEGER ); // C�digo do contas a receber
			ps.setNull( PROC_IU.NPARCITREC.ordinal(), Types.INTEGER ); // C�digo do �tem do contas a receber
		}
		else {
			ps.setInt( PROC_IU.CODEMPIR.ordinal(), atd.getCodempir() ); // C�digo do contas a receber
			ps.setInt( PROC_IU.CODFILIALIR.ordinal(), atd.getCodfilialir() ); // C�digo do �tem do contas a receber
			ps.setInt( PROC_IU.CODREC.ordinal(), atd.getCodrec() ); // C�digo do contas a receber
			ps.setInt( PROC_IU.NPARCITREC.ordinal(), atd.getNparcitrec() ); // C�digo do �tem do contas a receber
		}

		if ( atd.getCodchamado() == null ) {
			ps.setNull( PROC_IU.CODEMPCH.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALCH.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODCHAMADO.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODEMPCH.ordinal(), atd.getCodempch() ); // C�digo da empresa do chamado
			ps.setInt( PROC_IU.CODFILIALCH.ordinal(), atd.getCodfilialch() ); // C�digo da filial do chamado
			ps.setInt( PROC_IU.CODCHAMADO.ordinal() ,  atd.getCodchamado() ); // C�digo do chamado
		
		}
		
		if ( atd.getCodespec() == null  ) {
			ps.setNull( PROC_IU.CODEMPEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODESPEC.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODEMPEA.ordinal(), atd.getCodempae() ); // C�digo da empresa do especifica��o
			ps.setInt( PROC_IU.CODFILIALEA.ordinal(),atd.getCodfilialae() ); // C�digo da filial da especifica��o
			ps.setInt( PROC_IU.CODESPEC.ordinal(), atd.getCodespec() ); // C�digo da especifica��o
		}

		ps.execute();
		ps.close();

		con.commit();

	}
	
/*private void updateAtend(Atendimento atd) throws Exception {

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

		ps.setInt( PROC_IU.CODATEND.ordinal(), atd.getCodatend());
		ps.setDate( PROC_IU.DATAATENDO.ordinal(), Funcoes.dateToSQLDate( txtDataAtendimento.getVlrDate() ) );
		ps.setDate( PROC_IU.DATAATENDOFIN.ordinal(), Funcoes.dateToSQLDate( txtDataAtendimentoFin.getVlrDate() ) );

		ps.setTime( PROC_IU.HORAATENDO.ordinal(), Funcoes.strTimeTosqlTime( txtHoraini.getVlrString() ) );
		ps.setTime( PROC_IU.HORAATENDOFIN.ordinal(), Funcoes.strTimeTosqlTime( txtHorafim.getVlrString() ) );
		ps.setString( PROC_IU.OBSATENDO.ordinal(), txaDescAtend.getVlrString() );

		ps.setInt( PROC_IU.CODEMP.ordinal(), Aplicativo.iCodEmp );
		ps.setInt( PROC_IU.CODFILIAL.ordinal(), ListaCampos.getMasterFilial( "ATTIPOATENDO" ) );
		ps.setInt(  PROC_IU.CODTPATENDO.ordinal(), cbTipo.getVlrInteger() );

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
	
*/

}
