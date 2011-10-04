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
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.component.EstagioCheck;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.business.object.Atendimento.EColAtend;
import org.freedom.modulos.crm.business.object.Atendimento.EColExped;
import org.freedom.modulos.crm.business.object.Atendimento.INIFINTURNO;
import org.freedom.modulos.crm.business.object.Atendimento.PARAM_PRIM_LANCA;
import org.freedom.modulos.crm.business.object.Atendimento.PREFS;
import org.freedom.modulos.crm.business.object.Atendimento.PROC_IU;
import org.freedom.modulos.gpe.business.object.Batida;

public class DAOAtendimento extends AbstractDAO {
	
	private Object prefs[] = null;
	private enum COLBAT {INITURNO, INIINTTURNO, FININTTURNO, FINTURNO};
	private enum COLBATLANCTO {BATIDA, LANCTO, DIF};
	
	// loop para checar est�gio
	// St�gios: EPE = est�gio pendente/sem nenhuma revis�o
	//			E1I = est�gio 1 situa��o (I) inconsist�ncia
	//			E1O = est�gio 1 situa��o (O) OK
	// Est�gio 1, verifica a necessidade de inser��o de hor�rios no ponto
	
	public DAOAtendimento( DbConnection cn )  {

		super( cn );
		//setPrefs();
	
	}
	
	private Integer getSequencia(Integer codemp, Integer codfilial, String tab) throws SQLException {
		Integer result = null;
		StringBuilder sql = new StringBuilder("select iseq from spgeranum( ?, ?, ? )");
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, codemp );
		ps.setInt( 2, codfilial );
		ps.setString( 3, tab );
		ResultSet rs = ps.executeQuery();
		return result;
	}
	

	public Atendimento loadModelAtend(Integer codemp, Integer codfilial, Integer codempmo, Integer codfilialmo, Integer codmodel) throws SQLException {
		Atendimento result = null;
		Integer codatendo = null;
		
		StringBuilder sql = new StringBuilder("select ");
			sql.append( "mod.codempto, mod.codfilialto, mod.codtpatendo, " );
			sql.append( "mod.codempsa, mod.codfilialsa, mod.codsetat, ");
			sql.append( "mod.obsatendo, mod.obsinterno, mod.statusatendo, " );
			sql.append( "mod.codempcl, mod.codfilialcl, mod.codcli, mod.codempct, ");
			sql.append( "mod.codfilialct, mod.codcontr, mod.coditcontr, " );
			sql.append( "mod.codempca, mod.codfilialca, mod.codclasatendo," );
			sql.append( "mod.codempch, mod.codfilialch, mod.codchamado, "); 
			sql.append( "mod.codempea, mod.codfilialea, mod.codespec " );
			sql.append( "from atmodatendo mod " );
			sql.append( "where " );
			sql.append( "mod.codemp=? and mod.codfilial=? and mod.codmodel=? " );

		if (codmodel!= null) {
			codatendo = getSequencia(codemp, codfilial, "AT");		
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codempmo );
			ps.setInt( 2, codfilialmo );
			ps.setInt( 3, codmodel );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new Atendimento();
				result.setCodemp( codemp );
				result.setCodfilial( codfilial );
				result.setCodempto( rs.getInt( "codempto" ) );
				result.setCodfilialto( rs.getInt( "codfilialto" ) );
				result.setCodtpatendo( rs.getInt( "codtpatendo" ) );
				result.setCodempsa( rs.getInt(  "codempsa" ) );
				result.setCodfilialsa( rs.getInt( "codfilialsa" ) );
				result.setCodsetat( rs.getInt("codsetat" ) );
				result.setObsatendo( rs.getString(  "obsatendo" ) );
				result.setObsinterno( rs.getString( "obsinterno" ) );
				result.setStatusatendo( rs.getString("statusatendo" ) );
				result.setCodempcl( rs.getInt( "codempcl" ) );
				result.setCodfilialcl( rs.getInt("codfilialcl"));
				result.setCodcli( rs.getInt( "codcli" ) );
				if ( rs.getString( "coditcontr" )!=null ) {
					result.setCodempct( rs.getInt("codempct") );
					result.setCodfilialct( rs.getInt( "codfilialct" ) );
					result.setCodcontr( rs.getInt( "codcontr" ) );
					result.setCoditcontr( rs.getInt( "coditcontr" ) );
				}
				result.setCodempca( rs.getInt( "codempca" ) );
				result.setCodfilialca( rs.getInt( "codfilialca" ) );
				result.setCodclasatendo( rs.getInt( "codclasatendo" ) );
				if ( rs.getString( "codchamado" )!=null ) {
					result.setCodempch( rs.getInt( "codempch" ) );
					result.setCodfilialch (rs.getInt( "codfilialch" ) );
					result.setCodchamado( rs.getInt( "codchamado" ) );
				}
				result.setCodempea( rs.getInt( "codempea" ) );
				result.setCodfilialea( rs.getInt( "codfilialea" ) );
				result.setCodespec( rs.getInt( "codespec" ) );
				result.setDocatendo( "0" );
				result.setConcluichamado( "N" );
			}
		
		}
		return result;
	}
	
	public void gerarEstagio34(Vector<Vector<Object>> vatend, Integer codemp, Integer codfilial,
			Integer codempmo, Integer codfilialmo,
			Integer codempae, Integer codfilialae, Integer codatend,
			Integer codempus, Integer codfilialus, String idusu) throws SQLException {
		Atendimento atendimento = null;
		Integer codmodel = null;
		Date dataatendo = null;
		Date dataatendofin = null;
		String horaini = null;
		String horafin = null;
		String sitrev = null;
		for (Vector<Object> row: vatend) {
			sitrev = (String) row.elementAt( EColAtend.SITREVATENDO.ordinal() );
			if ((sitrev.equals( EstagioCheck.E3I.getValueTab() )) || 
				(sitrev.equals( EstagioCheck.E4I.getValueTab() )) ) {
				codmodel = (Integer) row.elementAt( EColAtend.CODMODEL.ordinal() );
				if (codmodel!=null) {
					dataatendo = Funcoes.strDateToDate( (String) row.elementAt( EColAtend.DATAATENDO.ordinal() ) ); 
					dataatendofin = Funcoes.strDateToDate( (String) row.elementAt( EColAtend.DATAATENDO.ordinal() ) );
					horaini = (String) row.elementAt( EColAtend.HORAINI.ordinal() );
					horafin = (String) row.elementAt( EColAtend.HORAFIN.ordinal() );					
					atendimento = loadModelAtend( codemp, codfilial, codempmo, codfilialmo, codmodel );
					atendimento.setDataatendo( dataatendo );
					atendimento.setDataatendofin( dataatendofin );
					atendimento.setHoraatendo( horaini );
					atendimento.setHoraatendofin( horafin );
					atendimento.setCodempae( codempae );
					atendimento.setCodfilialae( codfilialae );
					atendimento.setCodatend( codatend );
					atendimento.setCodempus( codempus );
					atendimento.setCodfilialus( codfilialus );
					atendimento.setIdusu( idusu );
					
					insert(atendimento);
				}
			}
		}
	}
	
	public void insertIntervaloAtend(Integer codemp, Integer codfilial, 
			Date dataatendo, Date dataatendofin, 
			String horaini, String horafim,
			Integer codempae, Integer codfilialae, Integer codatend,
			Integer codempus, Integer codfilialus, String idusu) throws SQLException {
		
			Atendimento intervalo = loadModelAtend( codemp, codfilial, (Integer) prefs[PREFS.CODEMPMI.ordinal()], 
					(Integer) prefs[PREFS.CODFILIALMI.ordinal()], (Integer) prefs[PREFS.CODMODELMI.ordinal()] );
			intervalo.setDataatendo( dataatendo );
			intervalo.setDataatendofin( dataatendofin );
			intervalo.setHoraatendo( horaini );
			intervalo.setHoraatendofin( horafim );
			intervalo.setCodempae( codempae );
			intervalo.setCodfilialae( codfilialae );
			intervalo.setCodatend( codatend );
			intervalo.setCodempus( codempus );
			intervalo.setCodfilialus( codfilialus );
			intervalo.setIdusu( idusu );
			
			insert(intervalo);
	}
	
	public void insertIntervaloChegada(Integer codemp, Integer codfilial, 
			Date dataatendo, Date dataatendofin, 
			String horaini, String horafim,
			Integer codempae, Integer codfilialae, Integer codatend,
			Integer codempus, Integer codfilialus, String idusu) throws SQLException {
		
			Atendimento intervalo = loadModelAtend( codemp, codfilial, (Integer) prefs[PREFS.CODEMPME.ordinal()], 
					(Integer) prefs[PREFS.CODFILIALME.ordinal()], (Integer) prefs[PREFS.CODMODELME.ordinal()] );
			intervalo.setDataatendo( dataatendo );
			intervalo.setDataatendofin( dataatendofin );
			intervalo.setHoraatendo( horaini );
			intervalo.setHoraatendofin( horafim );
			intervalo.setCodempae( codempae );
			intervalo.setCodfilialae( codfilialae );
			intervalo.setCodatend( codatend );
			intervalo.setCodempus( codempus );
			intervalo.setCodfilialus( codfilialus );
			intervalo.setIdusu( idusu );
			
			insert(intervalo);
	}
	
	public void insertFaltaJustificada(Integer codemp, Integer codfilial, 
			Date dataatendo, Date dataatendofin, 
			String horaini, String horafim,
			Integer codempae, Integer codfilialae, Integer codatend,
			Integer codempus, Integer codfilialus, String idusu) throws SQLException {
		
			Atendimento intervalo = loadModelAtend( codemp, codfilial, (Integer) prefs[PREFS.CODEMPFJ.ordinal()], 
					(Integer) prefs[PREFS.CODFILIALFJ.ordinal()], (Integer) prefs[PREFS.CODMODELFJ.ordinal()] );
			intervalo.setCodemp( codemp );
			intervalo.setCodfilial( codfilial );
			intervalo.setDataatendo( dataatendo );
			intervalo.setDataatendofin( dataatendofin );
			intervalo.setHoraatendo( horaini );
			intervalo.setHoraatendofin( horafim );
			intervalo.setCodempae( codempae );
			intervalo.setCodfilialae( codfilialae );
			intervalo.setCodatend( codatend );
			intervalo.setCodempus( codempus );
			intervalo.setCodfilialus( codfilialus );
			intervalo.setIdusu( idusu );
			
			insert(intervalo);
	}
	
	public void insertFaltaInjustificada(Integer codemp, Integer codfilial, 
			Date dataatendo, Date dataatendofin, 
			String horaini, String horafim,
			Integer codempae, Integer codfilialae, Integer codatend,
			Integer codempus, Integer codfilialus, String idusu) throws SQLException {
		
			Atendimento intervalo = loadModelAtend( codemp, codfilial, (Integer) prefs[PREFS.CODEMPFI.ordinal()], 
					(Integer) prefs[PREFS.CODFILIALFI.ordinal()], (Integer) prefs[PREFS.CODMODELFI.ordinal()] );
			intervalo.setCodemp( codemp );
			intervalo.setCodfilial( codfilial );
			intervalo.setDataatendo( dataatendo );
			intervalo.setDataatendofin( dataatendofin );
			intervalo.setHoraatendo( horaini );
			intervalo.setHoraatendofin( horafim );
			intervalo.setCodempae( codempae );
			intervalo.setCodfilialae( codfilialae );
			intervalo.setCodatend( codatend );
			intervalo.setCodempus( codempus );
			intervalo.setCodfilialus( codfilialus );
			intervalo.setIdusu( idusu );
			
			insert(intervalo);
	}
	
	public int getAtendente(Integer Matempr){
		
		StringBuffer sql = new StringBuffer();
		StringBuffer where = new StringBuffer();
		int iRet = 0;
			try {
				
				sql.append( "select codatend from ATATENDENTE  "  );
				sql.append(" where  matempr = " + Matempr );
				
				PreparedStatement ps = getConn().prepareStatement( sql.toString() );
				ResultSet rs = ps.executeQuery();
				if ( rs.next() ) {
					iRet = rs.getInt( "Codatend" );
					return iRet;
				}				
				rs.close();
				ps.close();
				getConn().commit();
			} catch ( SQLException err ) {
				err.printStackTrace();
			}

			return iRet;
		}
	
	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ Atendimento.PREFS.values().length];
		
		try {
			sql = new StringBuilder("select codempmi, codfilialmi, codmodelmi,  " );
			sql.append( "codempme, mi.descmodel descmodelmi, "); 
			sql.append( "codfilialme, codmodelme, me.descmodel descmodelme, tempomaxint, coalesce(tolregponto,20) tolregponto, "); 
			sql.append( "mi.codempea codempia, mi.codfilialea codfilialia, ea.codespec codespecia, ea.descespec descespecia , " );
			sql.append( "codempfi,codfilialfi, codmodelfi, fi.descmodel descmodelfi, " );
			sql.append( "codempfj,codfilialfj, codmodelfj, fj.descmodel descmodelfj " );
			sql.append( "from sgprefere3 p " );
			sql.append( "left outer join atmodatendo mi " );
			sql.append( "on mi.codemp=p.codempmi and mi.codfilial=p.codfilialmi and mi.codmodel=p.codmodelmi ");
			sql.append( "left outer join atmodatendo me " );
			sql.append( "on me.codemp=p.codempme and me.codfilial=p.codfilialme and me.codmodel=p.codmodelme ");
			sql.append( "left outer join atespecatend ea " );
			sql.append( "on ea.codemp=mi.codempea and ea.codfilial=mi.codfilialea and ea.codespec=mi.codespec ");
			sql.append( "left outer join atmodatendo fi " );
			sql.append( "on fi.codemp=p.codempfi and fi.codfilial=p.codfilialfi and fi.codmodel=p.codmodelfi ");
			sql.append( "left outer join atmodatendo fj " );
			sql.append( "on fj.codemp=p.codempfj and fj.codfilial=p.codfilialfj and fj.codmodel=p.codmodelfj ");
			sql.append( "where  p.codemp=? and p.codfilial=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				prefs[ PREFS.CODEMPMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPMI.toString() ));
				prefs[ PREFS.CODFILIALMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODFILIALMI.toString() ));
				prefs[ PREFS.CODMODELMI.ordinal() ] = new Integer(rs.getInt( PREFS.CODMODELMI.toString() ));
				prefs[ PREFS.DESCMODELMI.ordinal() ] = rs.getString( PREFS.DESCMODELMI.toString() );
				prefs[ PREFS.CODEMPME.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPME.toString() ));
				prefs[ PREFS.CODFILIALME.ordinal() ] = new Integer(rs.getInt( PREFS.CODFILIALME.toString() ));
				prefs[ PREFS.CODMODELME.ordinal() ] = new Integer(rs.getInt( PREFS.CODMODELME.toString() ));
				prefs[ PREFS.DESCMODELME.ordinal() ] = rs.getString( PREFS.DESCMODELME.toString() );
				prefs[ PREFS.TEMPOMAXINT.ordinal() ] = new Integer(rs.getInt( PREFS.TEMPOMAXINT.toString() ));
				prefs[ PREFS.TOLREGPONTO.ordinal() ] = new Integer(rs.getInt( PREFS.TOLREGPONTO.toString() ));
				prefs[ PREFS.CODEMPIA.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPIA.toString() ));
				prefs[ PREFS.CODFILIALIA.ordinal() ] = new Integer(rs.getInt( PREFS.CODFILIALIA.toString() ));
				prefs[ PREFS.CODESPECIA.ordinal() ] = new Integer(rs.getInt( PREFS.CODESPECIA.toString() ));
				prefs[ PREFS.DESCESPECIA.ordinal() ] = rs.getString( PREFS.DESCESPECIA.toString() );
				prefs[ PREFS.CODEMPFI.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPFI.toString() ));
				prefs[ PREFS.CODFILIALFI.ordinal() ] = new Integer( rs.getInt( PREFS.CODFILIALFI.toString() ));
				prefs[ PREFS.CODMODELFI.ordinal() ] = new Integer( rs.getInt(  PREFS.CODMODELFI.toString() ));
				prefs[ PREFS.DESCMODELFI.ordinal() ] = rs.getString( PREFS.DESCMODELFI.toString() );
				prefs[ PREFS.CODEMPFJ.ordinal() ] = new Integer(rs.getInt( PREFS.CODEMPFJ.toString() ));
				prefs[ PREFS.CODFILIALFJ.ordinal() ] = new Integer( rs.getInt( PREFS.CODFILIALFJ.toString() ));
				prefs[ PREFS.CODMODELFJ.ordinal() ] = new Integer( rs.getInt(  PREFS.CODMODELFJ.toString() ));
				prefs[ PREFS.DESCMODELFJ.ordinal() ] = rs.getString( PREFS.DESCMODELFJ.toString() );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}
	
	public Object[] getPrefs() {
		return this.prefs;
	}
	
	// Retorna o primeiro ou o �ltimo lan�amento, dependendo da requis�o (Abertura ou Fechamento).
	public String getHoraPrimUltLanca(Integer codemp, Integer codfilial, 
			Date dataatendo, String horaini, String horafim,
			Integer codempae, Integer codfilialae, Integer codatend,
			String aftela) throws SQLException {
		String result = null;
		StringBuilder sql = new StringBuilder();
		sql.append( "select first 1 a.horaatendo, a.horaatendofin from atatendimento a ");
		sql.append( "where a.codemp=? and a.codfilial=? and a.dataatendo=? and ");
		sql.append( "a.codempae=? and a.codfilialae=? and a.codatend=? and " );
		if ("A".equals( aftela )) {
			sql.append( "a.horaatendo>=? " );
			sql.append( "order by a.dataatendo, a.horaatendo" );
		} else {
			sql.append( "a.horaatendofin<=? " );
			sql.append( "order by a.dataatendo desc, a.horaatendofin desc" );
		}
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( PARAM_PRIM_LANCA.CODEMP.ordinal(), codemp );
		ps.setInt( PARAM_PRIM_LANCA.CODFILIAL.ordinal(), codfilial );
		ps.setDate( PARAM_PRIM_LANCA.DATAATENDO.ordinal(), Funcoes.dateToSQLDate( dataatendo ) );
		ps.setInt( PARAM_PRIM_LANCA.CODEMPAE.ordinal(), codempae );
		ps.setInt( PARAM_PRIM_LANCA.CODFILIALAE.ordinal(), codfilialae );
		ps.setInt( PARAM_PRIM_LANCA.CODATEND.ordinal(), codatend );
		if ("A".equals( aftela )) {
			ps.setTime( PARAM_PRIM_LANCA.HORAATENDO.ordinal(), Funcoes.strTimeTosqlTime( horaini ) );
		} else {
			ps.setTime( PARAM_PRIM_LANCA.HORAATENDO.ordinal(), Funcoes.strTimeTosqlTime( horafim ) );
		}
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			if ("A".equals( aftela )) {
				result = rs.getString( PARAM_PRIM_LANCA.HORAATENDO.toString() );
			} else {
				result = rs.getString( PARAM_PRIM_LANCA.HORAATENDOFIN.toString() );
			}
		}
		rs.close();
		ps.close();
		getConn().commit();
		return result;
	}

	public void insert(Atendimento atd) throws SQLException {
	
		StringBuilder sql = new StringBuilder();

		sql.append( "EXECUTE PROCEDURE ATATENDIMENTOIUSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		
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
		ps.setTime( PROC_IU.HORAATENDO.ordinal() , Funcoes.strTimeTosqlTime( atd.getHoraatendo() ) ) ; // Hora inicial do atendimento
		ps.setTime( PROC_IU.HORAATENDOFIN.ordinal()	, Funcoes.strTimeTosqlTime( atd.getHoraatendofin() ) ) ; // Hora final do atendimento
		ps.setString( PROC_IU.OBSATENDO.ordinal(), atd.getObsatendo() ); // Descri��o do atendimento
		
		if (atd.getObsinterno()==null) {
			ps.setNull( PROC_IU.OBSINTERNO.ordinal(), Types.CHAR );
		} else {
			ps.setString( PROC_IU.OBSINTERNO.ordinal(), atd.getObsinterno() ); // Observa��es internas
		}
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

		getConn().commit();

	}
	
	
	public void update(Atendimento atd) throws Exception {

		StringBuilder sql = new StringBuilder();
		
		sql.append( "EXECUTE PROCEDURE ATATENDIMENTOIUSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );

		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		
		ps.setString( PROC_IU.IU.ordinal(), "U" ); // Define o modo insert para a procedure
		ps.setInt( PROC_IU.CODEMP.ordinal() , atd.getCodemp() ); // C�digo da empresa
		ps.setInt( PROC_IU.CODFILIAL.ordinal(), atd.getCodfilial() ); // C�digo da filial
		
		if ( atd.getCodatendo() == null ) {
			ps.setInt( PROC_IU.CODATENDO.ordinal(), Types.INTEGER );
		}
		else {
			ps.setInt( PROC_IU.CODATENDO.ordinal() , atd.getCodatendo() ); // C�digo do atendimento
		}

		
		if( atd.getCodtpatendo() == null ){
			ps.setNull( PROC_IU.CODEMPTO.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALTO.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODTPATENDO.ordinal(), Types.INTEGER );
		} 
		else {
			ps.setInt( PROC_IU.CODEMPTO.ordinal(), atd.getCodempto() );
			ps.setInt( PROC_IU.CODFILIALTO.ordinal(), atd.getCodfilialto() );
			ps.setInt( PROC_IU.CODTPATENDO.ordinal(), atd.getCodtpatendo() );
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
		
		
		ps.setString( PROC_IU.DOCATENDO.ordinal() , atd.getDocatendo() );
		ps.setDate( PROC_IU.DATAATENDO.ordinal(), Funcoes.dateToSQLDate( atd.getDataatendo() ) );
		ps.setDate( PROC_IU.DATAATENDOFIN.ordinal(), Funcoes.dateToSQLDate( atd.getDataatendofin() ) );
		ps.setTime( PROC_IU.HORAATENDO.ordinal(), Funcoes.strTimeTosqlTime( atd.getHoraatendo()) );
		ps.setTime( PROC_IU.HORAATENDOFIN.ordinal(), Funcoes.strTimeTosqlTime( atd.getHoraatendofin() ) );
		ps.setString( PROC_IU.OBSATENDO.ordinal(), atd.getObsatendo() );
		
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
		
		if( atd.getCodsetat() == null ){
			ps.setNull( PROC_IU.CODEMPSA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALSA.ordinal(), Types.INTEGER );
			ps.setNull(  PROC_IU.CODSETAT.ordinal(), Types.INTEGER );
		} 
		else {
			ps.setInt( PROC_IU.CODEMPSA.ordinal(), atd.getCodempsa() );
			ps.setInt( PROC_IU.CODFILIALSA.ordinal(), atd.getCodfilialsa() );
			ps.setInt( PROC_IU.CODSETAT.ordinal(), atd.getCodsetat() );
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
			ps.setInt( PROC_IU.CODCHAMADO.ordinal(), atd.getCodchamado() ); // C�digo do chamado
		}

		if ( atd.getCodcontr() == null ) {
			ps.setNull( PROC_IU.CODEMPCT.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALCT.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODCONTR.ordinal(), Types.INTEGER );
			ps.setInt( PROC_IU.CODITCONTR.ordinal(), Types.INTEGER );
		}
		else {
			ps.setNull( PROC_IU.CODEMPCT.ordinal(), atd.getCodempct() );
			ps.setNull( PROC_IU.CODFILIALCT.ordinal(), atd.getCodfilialct() );
			ps.setInt( PROC_IU.CODCONTR.ordinal(), atd.getCodcontr() );
			ps.setInt( PROC_IU.CODITCONTR.ordinal(), atd.getCoditcontr() );
		}

		ps.setString( PROC_IU.STATUSATENDO.ordinal(), atd.getStatusatendo() );
				
		if (atd.getObsinterno()==null) {
			ps.setNull( PROC_IU.OBSINTERNO.ordinal(), Types.CHAR );
		} else {
			ps.setString( PROC_IU.OBSINTERNO.ordinal(), atd.getObsinterno() ); // Observa��es internas
		}
		
		ps.setString( PROC_IU.CONCLUICHAMADO.ordinal(), atd.getConcluichamado() );

		if ( atd.getCodespec() == null ) {
			ps.setNull( PROC_IU.CODEMPEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODFILIALEA.ordinal(), Types.INTEGER );
			ps.setNull( PROC_IU.CODESPEC.ordinal(), Types.INTEGER );
		}
		else {

			ps.setInt( PROC_IU.CODEMPEA.ordinal(), atd.getCodempea() );
			ps.setInt(  PROC_IU.CODFILIALEA.ordinal(), atd.getCodfilialea() );
			ps.setInt( PROC_IU.CODESPEC.ordinal(), atd.getCodespec() );
		}

		ps.executeUpdate();
		ps.close();
		
		getConn().commit();		
	}

	public String checarSitrevEstagio1234(final Vector<Vector<Object>> vexped, Vector<Vector<Object>> vatend) {
	   String result = (String) EstagioCheck.EPE.getValue();
	   //String temp = null;
	   for (Vector<Object> row: vexped) {
		   result = (String) row.elementAt( EColExped.SITREVEXPED.ordinal() );
		   if ( EstagioCheck.EPE.getValueTab().equals(result) ) {
			   result = (String) EstagioCheck.EPE.getValue();
		   } else if ( EstagioCheck.E1O.getValueTab().equals(result) ) {
			   result = (String) EstagioCheck.E1O.getValue();
		   } else if ( EstagioCheck.E2O.getValueTab().equals(result) ) {
			   result = (String) EstagioCheck.E2O.getValue();
		   } else if ( EstagioCheck.E1I.getValueTab().equals(result) ) {
			   result = (String) EstagioCheck.E1I.getValue();
			   break;
		   } else if ( EstagioCheck.E2I.getValueTab().equals(result) ) {
			   result = (String) EstagioCheck.E2I.getValue();
			   break;
		   }
	   }
	   // Verifica se a posi��o 3 da string � igual "O" (Ok), ent�o parte para verificar o vetor de atendimentos
	   if (result.substring( 2 ).equals("O")) {
		   for (Vector<Object> row: vatend) {
			   result = (String) row.elementAt( EColAtend.SITREVATENDO.ordinal() );
			   if ( EstagioCheck.EPE.getValueTab().equals(result) ) {
				   result = (String) EstagioCheck.EPE.getValue();
			   } else if ( EstagioCheck.E3O.getValueTab().equals(result) ) {
				   result = (String) EstagioCheck.E3O.getValue();
			   } else if (EstagioCheck.E3I.getValueTab().equals( result ) ) {
				   result = (String)  EstagioCheck.E3I.getValue();
				   break;
			   } else if ( EstagioCheck.E4O.getValueTab().equals(result) ) {
				   result = (String) EstagioCheck.E4O.getValue();
			   } else if (EstagioCheck.E4I.getValueTab().equals( result ) ) {
				   result = (String)  EstagioCheck.E4I.getValue();
				   break;
			   }
		   }
	   }
	   return result;
	}

	public String checarSitrevEstagio2(final Vector<Vector<Object>> vexped) {
	   String result = (String) EstagioCheck.EPE.getValue();
	   String temp = null;
	   for (Vector<Object> row: vexped) {
		   temp = (String) row.elementAt( EColExped.SITREVEXPED.ordinal() );
		   if ( EstagioCheck.E1I.getValueTab().equals(temp) ) {
			   result = (String) EstagioCheck.E1I.getValue();
			   break;
		   }
	   }
	   return result;
	}
	
	/*
	 * Este m�doto ir� retornar um vetor com as batidas a registrar 
	 */
	public Vector<Batida> getRegistroBatidas(Vector<Vector<Object>> vexped, int nbatidas) {
		Vector<Batida> result = new Vector<Batida>();
		Batida batida = null;
		int colini = EColExped.HFIMTURNO.ordinal() + nbatidas + 1;
		Date dtbat = null;
		String hbat = null;
		for (Vector<Object> row: vexped) {
			dtbat = Funcoes.strDateToDate( (String) row.elementAt( EColExped.DTEXPED.ordinal() ) );
			for (int i=colini; i<row.size(); i++) {
				hbat = (String) row.elementAt( i );
				// Se a hora a registrar n�o for nula nem em branco
				if ( ! ( (hbat==null) || (hbat.trim().equals( "" ) ) ) ) {
					batida = new Batida();
					batida.setDataponto( dtbat );
					batida.setHoraponto( hbat );
					result.addElement( batida );
				}
			}
		}
		return result;
	}

	public String checarSitrevAtend(final Vector<Vector<Object>> vatend) {
		// loop para checar est�gio
		// St�gios: EPE = est�gio pendente/sem nenhuma revis�o
		//			E1I = est�gio 1 situa��o (I) inconsist�ncia
		//			E1O = est�gio 1 situa��o (O) OK
		// Est�gio 1, verifica a necessidade de intervalos entre atendimentos
		
		String sitrev = null;
		String sitrevant = null;
		for (Vector<Object> row: vatend) {
			sitrev = "E" + row.elementAt( EColAtend.SITREVATENDO.ordinal() );
			if ( EstagioCheck.EPE.getValue().equals( sitrev ) ) {
				break;
			} else if (sitrevant==null) {
				sitrevant=sitrev;
			} else if ( sitrevant.compareTo( sitrev ) > 0 ) {
				sitrevant=sitrev;
			} else if ( sitrev.compareTo( sitrevant ) > 0 ) {
				sitrev=sitrevant;
			}
		}
		return sitrev;
	}

	public String checarSitrevExped(final Vector<Vector<Object>> vexped) {
		// loop para checar est�gio
		// St�gios: EPE = est�gio pendente/sem nenhuma revis�o
		//			E1I = est�gio 1 situa��o (I) inconsist�ncia
		//			E1O = est�gio 1 situa��o (O) OK
		// Est�gio 1, verifica a necessidade de intervalos entre atendimentos
		
		String sitrev = null;
		String sitrevant = null;
		for (Vector<Object> row: vexped) {
			sitrev = "E" + row.elementAt( EColExped.SITREVEXPED.ordinal() );
			if ( EstagioCheck.EPE.getValue().equals( sitrev ) ) {
				break;
			} else if (sitrevant==null) {
				sitrevant=sitrev;
			} else if ( sitrevant.compareTo( sitrev ) > 0 ) {
				sitrevant=sitrev;
			} else if ( sitrev.compareTo( sitrevant ) > 0 ) {
				sitrev=sitrevant;
			}
		}
		return sitrev;
	}
	
    public boolean checar(final Vector<Vector<Object>> vexped, final Vector<Vector<Object>> vatend, int nbatidas) {
    	boolean result = false;
    	// Verifica o menor est�gio da revis�o
    	String sitrev = checarSitrevExped(vexped); 
    	if (EstagioCheck.EPE.getValue().equals( sitrev )) {
    		result = checarEstagio1(vexped, vatend, nbatidas);
    		if (!result) {
    			result = checarEstagio2(vexped, nbatidas);
    			if (!result) {
    				result = checarEstagio3( vexped, vatend, nbatidas );
    				if (!result) {
    					result = checarEstagio4( vatend );
    				}
    			}
    		}
    		//result = checarEstagio2(vatend);
    	}
    	return result;
    }
    
    // Atualiza lista de atendimentos com hor�rio de batidas do in�cio e fim do turno
    public boolean checarEstagio3(final Vector<Vector<Object>> vexped, final Vector<Vector<Object>> vatend, final int nbatidas) {
        boolean result = false;
		int posini = EColExped.HFIMTURNO.ordinal()+1;
		int numcols = posini + nbatidas;
        int posatend = -1;
        Vector<Object> atend = null;
        Vector<String> batidas = null;
        Vector<String> lanctos = null;
        Vector<Object[]> lanctosBatidas = null;
        Object[] lanctobatida = null;
        String dtatend = null;
        String inifinturno = null;
        String horatemp1 = null;
        String horatemp2 = null;
        long intervalo = 0;
        int intervalomin = 0;
        // toler�ncia de intervalo igual a 50% da toler�ncia de tempo para batida do ponto
        int tolintervalo = (Integer) prefs[PREFS.TOLREGPONTO.ordinal()]/2;
	
        for (Vector<Object> row: vexped) {
        	batidas = getBatidas( row, posini, numcols );        	
         	if ( (batidas!=null) && (batidas.size()>=4) ) {
         		// Data de registro do ponto e atendimento
         		dtatend = (String) row.elementAt( EColExped.DTEXPED.ordinal() );
         		//Busca lancamentos para data do registro de ponto;
         		lanctos = getHorariosLanctos( dtatend, vatend );
         		if (lanctos.size()>0) {
         			lanctosBatidas = getHorariosLanctosBatidas( batidas, lanctos );
         			if (lanctosBatidas.size()>0) {
         				for (int lb=0; lb<lanctosBatidas.size(); lb++) {
         					lanctobatida = lanctosBatidas.elementAt( lb );
         					// Se o resto da divis�o da posi��o da batida por 2 for maior que zero (impar), significa que � fechamento de turno
         					if ( lb%2>0 ) {
         						posatend = locateAtend(vatend, dtatend, (String) lanctobatida[COLBATLANCTO.LANCTO.ordinal()], true );
         						inifinturno = INIFINTURNO.F.toString();
         					} else {
         						posatend = locateAtend(vatend, dtatend, (String) lanctobatida[COLBATLANCTO.LANCTO.ordinal()], false );
         						inifinturno = INIFINTURNO.I.toString();
         					}
         					if (posatend>-1) {
         						// Se encontrou o lan�amento ajusta a coluna de batida
         						horatemp1 = (String) lanctobatida[COLBATLANCTO.BATIDA.ordinal()];
         						vatend.elementAt( posatend ).setElementAt( horatemp1, EColAtend.HORABATIDA.ordinal() );
         						// Coloca flag indicado I - In�cio ou F - Final de turno
         						vatend.elementAt( posatend ).setElementAt( inifinturno, EColAtend.INIFINTURNO.ordinal() );
         						// Se for in�cio de turno dever� recalcular o intervalo entre atendimentos
         						horatemp2 = (String) lanctobatida[COLBATLANCTO.LANCTO.ordinal()];
         						if (inifinturno.equals( INIFINTURNO.I.toString() )) {
         							intervalo = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( horatemp1  ), Funcoes.strTimeTosqlTime( horatemp2 ) );
         						} else {
         							intervalo = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( horatemp2  ), Funcoes.strTimeTosqlTime( horatemp1 ) );         							
         						}
     							intervalomin = (int) intervalo / 1000 / 60;
 								vatend.elementAt( posatend ).setElementAt( new Integer(intervalomin), EColAtend.INTERVATENDO.ordinal() );     							
     							if ( intervalomin!=0 ) {
    							
     								result = true;
	     							vatend.elementAt( posatend ).setElementAt( EstagioCheck.E3I.getValueTab(), EColAtend.SITREVATENDO.ordinal() );
	     							vatend.elementAt( posatend ).setElementAt( EstagioCheck.E3I.getImg(), EColAtend.SITREVATENDOIMG.ordinal() );
	
	     							if (intervalomin > 0) {
	     								
	     								// Caso o intervalo seja maior o a toler�ncia
	     								if (intervalomin>tolintervalo) {
	     									// Intervalo igula a toler�ncia
	     									intervalomin = tolintervalo;
	     	 								vatend.elementAt( posatend ).setElementAt( new Integer(intervalomin), EColAtend.INTERVATENDO.ordinal() );
	     	 								// Verifica��o do lan�amento
	     	 								if ( inifinturno.equals( INIFINTURNO.I.toString() ) ) {
	     	 									horatemp2 = Funcoes.longTostrTime( Funcoes.somaTime( Funcoes.strTimeTosqlTime( horatemp1 ),
	     	 											Funcoes.strTimeTosqlTime( 
	     	 													Funcoes.longTostrTime( (long) intervalomin * 1000 * 60 ) ) ) );
	     	 								} else {
	     	 									horatemp2 = Funcoes.longTostrTime( Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( 
	     	 													Funcoes.longTostrTime( (long) intervalomin * 1000 * 60 ) ), 
	     	 													Funcoes.strTimeTosqlTime( horatemp1 )) );	     	 									
	     	 								}
	     								}
	     								
	         							vatend.elementAt( posatend ).setElementAt( prefs[PREFS.CODMODELME.ordinal()], EColAtend.CODMODEL.ordinal() );
	         							vatend.elementAt( posatend ).setElementAt( prefs[PREFS.DESCMODELME.ordinal()], EColAtend.DESCMODEL.ordinal() );
	     								
	         							horatemp1 = Funcoes.copy( horatemp1, 5 );
	         							horatemp2 = Funcoes.copy( horatemp2, 5 );
	         							
		         						if (inifinturno.equals( INIFINTURNO.I.toString() )) {
		         							vatend.elementAt( posatend ).setElementAt( horatemp1 , EColAtend.HORAINI.ordinal() );
		         							vatend.elementAt( posatend ).setElementAt( horatemp2 , EColAtend.HORAFIN.ordinal() );
		         						} else {
		         							vatend.elementAt( posatend ).setElementAt( horatemp2 , EColAtend.HORAINI.ordinal() );
		         							vatend.elementAt( posatend ).setElementAt( horatemp1 , EColAtend.HORAFIN.ordinal() );
		         						}     					
	     							}
     							}
         					}
         				}
         			}
         		}
        	}
        }
        for (Vector<Object> row:vatend) {
        	if (row.elementAt( EColAtend.SITREVATENDO.ordinal() ) .equals( EstagioCheck.EPE.getValueTab() )) {
				row.setElementAt( EstagioCheck.E3O.getValueTab(), EColAtend.SITREVATENDO.ordinal() );
				row.setElementAt( EstagioCheck.E3O.getImg(), EColAtend.SITREVATENDOIMG.ordinal() );
        	}
        }
        return result;
    }
    
    private int locateAtend(Vector<Vector<Object>> vatend, String dataatendo, String horaatendo, boolean atendfin ) {
    	int result = -1;
    	String hora = null;
    	String data = null;
    	for (int i=0; i<vatend.size(); i++) {
    		data = (String) vatend.elementAt( i ).elementAt( EColAtend.DATAATENDO.ordinal() );
    		// Caso seja para verificar o hor�rio final
    		if (atendfin) {
        		hora = (String) vatend.elementAt( i ).elementAt( EColAtend.HORAATENDOFIN.ordinal() );
    		} else {
        		hora = (String) vatend.elementAt( i ).elementAt( EColAtend.HORAATENDO.ordinal() );
    		}
    		if ( (horaatendo.equals(hora )) && (dataatendo.equals(data )) ) {
    			result = i;
    			break;
    		}
    	}
    	return result;
    }

    public boolean checarEstagio2(final Vector<Vector<Object>> vexped, final int nbatidas) {
        boolean result = false;
		int posini = EColExped.HFIMTURNO.ordinal()+1;
		int numcols = posini + nbatidas;
        Vector<String> batidas = null;
        long intervalo1 = 0;
        long intervalo2 = 0;
        float intervhoras1 = 0;
        float intervhoras2 = 0;
        String tempo = "";
        float TEMPOMAXTURNO = 6f;
		
        for (Vector<Object> row: vexped) {
        	batidas = getBatidas( row, posini, numcols );        	
         	if ( (batidas!=null) && (batidas.size()>=4) ) {
         		//Verifica o intervalor entre batidas
        		intervalo1 = Funcoes.subtraiTime(
        				Funcoes.strTimeTosqlTime( batidas.elementAt( COLBAT.INITURNO.ordinal() ) ), 
        				Funcoes.strTimeTosqlTime( batidas.elementAt( COLBAT.INIINTTURNO.ordinal() ) )
        		);
        		// Calcula o intervalo em horas
        		intervhoras1 = intervalo1 / 1000f / 60f / 60f;
        		
        		intervalo2 = Funcoes.subtraiTime(
        				Funcoes.strTimeTosqlTime( batidas.elementAt( COLBAT.FININTTURNO.ordinal() ) ), 
        				Funcoes.strTimeTosqlTime( batidas.elementAt( COLBAT.FINTURNO.ordinal() ) )
        		);
        		intervhoras2 = intervalo2 / 1000f / 60f / 60f;
        		if ( (intervhoras1>TEMPOMAXTURNO) || (intervhoras2>TEMPOMAXTURNO) ) {
        			if ( intervhoras1>TEMPOMAXTURNO ) {
                		tempo = Funcoes.longTostrTime( intervalo1 );
        				row.setElementAt( tempo, numcols );
        			}
        			if ( intervhoras2>TEMPOMAXTURNO ) {
	            		tempo = Funcoes.longTostrTime( intervalo2 );
	    				row.setElementAt( tempo, numcols + 1 );
        			}
    				row.setElementAt( EstagioCheck.E2I.getImg(), EColExped.SITREVEXPEDIMG.ordinal() );
    				row.setElementAt( EstagioCheck.E2I.getValueTab(), EColExped.SITREVEXPED.ordinal() );
    				result = true;
        		}
        	}
         	if (!result) {
				row.setElementAt( EstagioCheck.E2O.getImg(), EColExped.SITREVEXPEDIMG.ordinal() );
				row.setElementAt( EstagioCheck.E2O.getValueTab(), EColExped.SITREVEXPED.ordinal() );
         	}
        }
        return result;
    }
    
    public boolean checarEstagio1(final Vector<Vector<Object>> vexped, final Vector<Vector<Object>> vatend, int nbatidas) {
    	boolean result = false;
    	Vector<Object> row = null;
    	int numcols = 0;
    	int qtdant = 0;
    	String dtexped = null;
    	Vector<String> batidas = null;
        Vector<String> turno = null;
        Vector<String> turnosembatida = null;
        Vector<String> hlanctos = null;
        Vector<String> hlanctosturno = null;
		qtdant = EColExped.HFIMTURNO.ordinal()+1;
		numcols = qtdant + nbatidas;
		for (int r=0; r<vexped.size(); r++) {
			row = vexped.elementAt( r );
			dtexped = (String) row.elementAt( EColExped.DTEXPED.ordinal() );
			// Retorna vetor com informa��es do turno de trabalho
	    	turno = getTurno( (String) row.elementAt( EColExped.HINITURNO.ordinal() ),
	    			(String) row.elementAt( EColExped.HINIINTTURNO.ordinal() ),
	    			(String) row.elementAt( EColExped.HFIMINTTURNO.ordinal() ),
	    			(String) row.elementAt( EColExped.HFIMTURNO.ordinal() ));
	    	// retorna as batidas registradas
			batidas = getBatidas(row, qtdant, numcols);
			// caso o n�mero de batidas seja menor que o esperado, buscar informa��es.
            if (batidas.size()<turno.size()) {
            	row.setElementAt( EstagioCheck.E1I.getImg(), EColExped.SITREVEXPEDIMG.ordinal() );
            	row.setElementAt( EstagioCheck.E1I.getValueTab(), EColExped.SITREVEXPED.ordinal() );
            	// Verifica os hor�rios do truno sem batidas
            	turnosembatida = getTurnosembatida( batidas, turno);
            	hlanctos = getHorariosLanctos(dtexped, vatend );
            	// Cruza turno sem batidas com lan�amentos
            	hlanctosturno = getHorariosLanctosTurno(turnosembatida, hlanctos);
            	// Salva no vetor os hor�rios com lan�amentos
            	result = setHorariosLanctos(row, numcols, hlanctosturno );
            } else {
            	row.setElementAt( EstagioCheck.E1O.getImg(), EColExped.SITREVEXPEDIMG.ordinal() );
            	row.setElementAt( EstagioCheck.E1O.getValueTab(), EColExped.SITREVEXPED.ordinal() );
            }
		}
    	return result;
    }

    private boolean setHorariosLanctos(final Vector<Object> row, int numcols, Vector<String> hlanctos) {
    	boolean result = false;
    	int tot = numcols + hlanctos.size();
    	int pos = 0;
    	for (int i=numcols; i<tot; i++) {
    		row.setElementAt( hlanctos.elementAt( pos ), i );
    		result = true;
    		pos ++;
    	}
    	return result;
    }

    private Vector<Object[]> getHorariosLanctosBatidas(Vector<String> batidas, Vector<String> lanctos) {
    	Vector<Object[]> result = new Vector<Object[]>();
    	Vector<Object[]> lanctosbatidas = new Vector<Object[]>();
    	Vector<Object[]> temp = new Vector<Object[]>();
    	Vector<Object[]> resulttmp = new Vector<Object[]>();
    	Object[] batlancto = null;
    	Object[] batlanctotmp = null;
    	String hlancto = null;
    	String hbatida = null;
    	int posdif = -1;
    	int posmin = -1;
    	long dif = 0;
    	long difant = 0;
    	int iniloop = 0;
    	// Cria vetor com batidas x lan�amentos
    	for ( int i=0; i<batidas.size(); i++) {
    		hbatida = batidas.elementAt( i );
    		for ( int t=0; t<lanctos.size(); t++ ) {
    			hlancto = lanctos.elementAt( t );
    			dif = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( hlancto ), Funcoes.strTimeTosqlTime( hbatida ));
    			if (dif<0) {
    				dif = dif * -1;
    			}
 /*   			if (dif==0) {
    				System.out.println("Batida: "+hbatida);
    				System.out.println("Lancto: "+hlancto);
    			} */
    			batlancto = new Object[COLBATLANCTO.values().length];
    			batlancto[COLBATLANCTO.BATIDA.ordinal()] = hbatida;
    			batlancto[COLBATLANCTO.LANCTO.ordinal()] = hlancto;
    			batlancto[COLBATLANCTO.DIF.ordinal()] = new Long(dif);
       			lanctosbatidas.addElement( batlancto );
    		}
    	}
    	// Clonar lan�amentos x batidas
    	for ( int i=0; i<lanctosbatidas.size(); i++ ) {
    		temp.addElement( lanctosbatidas.elementAt( i ) );
    	}

    	// Faz um loop at� que o temp n�o contenha elementos
    	while (temp.size()>0) {
    		posdif = -1;
    		difant = 0;
    		for (int i=0; i<temp.size(); i++ ) {
    			batlancto = temp.elementAt( i );
    			hbatida = (String) batlancto[COLBATLANCTO.BATIDA.ordinal()];
    			hlancto = (String) batlancto[COLBATLANCTO.LANCTO.ordinal()];
    			dif = (Long) batlancto[COLBATLANCTO.DIF.ordinal()];
    			if ( (posdif==-1) || (dif<difant) ) {
    				posdif = i;
    			}
				difant = dif;
    		}
    		if (posdif>-1) {
    			batlancto = temp.elementAt( posdif );
    			resulttmp.add( batlancto );
    			hbatida = (String) batlancto[COLBATLANCTO.BATIDA.ordinal()];
    			hlancto = (String) batlancto[COLBATLANCTO.LANCTO.ordinal()];
    			temp.removeElement( batlancto );
    			iniloop = temp.size()-1;
    			for (int i=iniloop; ( (i>=0) && (temp.size()>0) ); i--) {
    				batlanctotmp = temp.elementAt( i );
    				if ( (hbatida.equals( batlanctotmp[COLBATLANCTO.BATIDA.ordinal()] )) ||
    					 (hlancto.equals( batlanctotmp[COLBATLANCTO.LANCTO.ordinal()] )) ) {
    					temp.removeElement( batlanctotmp );
    				}
    			}
    		}
    		
    	}

    	// Rotinas para ordenar o vetor de retorno
    	iniloop = resulttmp.size();
		while (result.size()!=iniloop) {
			posmin = -1;
	    	for (int i=0; i<resulttmp.size(); i++) {
				batlancto = resulttmp.elementAt( i );
				hbatida = (String) batlancto[COLBATLANCTO.BATIDA.ordinal()];
				hlancto = (String) batlancto[COLBATLANCTO.LANCTO.ordinal()];
	    		if  (posmin == -1) { 
	    			posmin = i;
	    		} else {
	    			batlanctotmp = resulttmp.elementAt( posmin );
    				if (hbatida.compareTo( (String) batlanctotmp[COLBATLANCTO.BATIDA.ordinal()] )<0) {
    					posmin = i;
    				}
	    		}
	    	}
	    	if (posmin!=-1) {
    			batlanctotmp = resulttmp.elementAt( posmin );
	    		result.addElement( batlanctotmp );
    			resulttmp.removeElementAt( posmin );
	    	}
		}
    	
    	return result;
    }
    
    private Vector<String> getHorariosLanctosTurno(Vector<String> turnosembatida, Vector<String> hlanctos) {
    	Vector<String> result = new Vector<String>();
    	Vector<String> temp = new Vector<String>();
    	String hbat = null;
    	String hturno = null;
    	int posdif = -1;
    	long dif = 0;
    	long difant = 0;
    	// Clonar lan�amentos
    	for ( int i=0; i<hlanctos.size(); i++ ) {
    		temp.add( hlanctos.elementAt( i ) );
    	}
    	// Comparar atendimentos com hor�rio do turno sem batidas
    	for ( int i=0; i<turnosembatida.size(); i++) {
    		hturno = turnosembatida.elementAt( i );
    		posdif = -1;
    		for ( int t=0; t<temp.size(); t++ ) {
    			hbat = temp.elementAt( t );
    			dif = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( hbat ), Funcoes.strTimeTosqlTime( hturno ));
    			if (dif<0) {
    				dif = dif * -1;
    			}
    			if (posdif==-1) {
    				difant = dif;
    				posdif = t;
    			} else if (dif<difant) {
    				difant = dif;
    				posdif = t;
    			}
    		}
    		if (posdif>-1) {
       			result.addElement( temp.elementAt(posdif) );
       			temp.remove( posdif );
    		}
    	}
    	return result;
    }
    
    private Vector<String> getHorariosLanctos(String dtatend, Vector<Vector<Object>> vatend) {
    	Vector<String> result = new Vector<String>();
    	Vector<Object> row = null;
    	//Vector<Object> rowant = null;
    	int pos = primeiroAtend( vatend, dtatend );
    	String dataatendo = null;
    	String horaatendo = null;
    	String horaatendofin = null;
    	String horaatendofinant = null;
    	int intervalo = 0;
    	if (pos>-1) {
    		// Verifica o primeiro lan�amento na data e grava como poss�vel hor�rio para ponto
    		row = vatend.elementAt( pos );
    		dataatendo = (String) row.elementAt( EColAtend.DATAATENDO.ordinal() );
    		horaatendo = (String) row.elementAt( EColAtend.HORAATENDO.ordinal() );
    		horaatendofin = (String) row.elementAt( EColAtend.HORAATENDOFIN.ordinal() );
    		result.add( horaatendo );
    		pos++;
    		// loop enquando for a data a pesquisar
    		while ( (pos<vatend.size()) && (dtatend.equals( dataatendo )) ) {
    			// hora final do atendimento anterior
        		horaatendofinant = (String) row.elementAt( EColAtend.HORAATENDOFIN.ordinal() );
        		row = vatend.elementAt( pos );
        		dataatendo = (String) row.elementAt( EColAtend.DATAATENDO.ordinal() );
        		horaatendo = (String) row.elementAt( EColAtend.HORAATENDO.ordinal() );
        		horaatendofin = (String) row.elementAt( EColAtend.HORAATENDOFIN.ordinal() );
        		intervalo = (Integer) row.elementAt( EColAtend.INTERVATENDO.ordinal() );
        		// Se tiver intervalo adiciona a hora final anterior e a hora inicial do lan�amento atual
        		if (!dtatend.equals( dataatendo )) {
        			result.add( horaatendofinant );
        		} else if (intervalo>0) {
        			result.add( horaatendofinant );
        			result.add( horaatendo );
        		}
    			pos++;
    		}
    		// adiciona o hor�rio final para fechar o turno de trabalho
    		result.add( horaatendofin );
    	}
    	return result;
    }
    
    private Vector<String> getTurno( String hiniturno, String hiniintturno, String hfimintturno, String hfimturno ) {
    	Vector<String> result = new Vector<String>();
		if (hiniintturno.equals( hfimintturno )) { // verifica se � meio per�odo
			result.add( hiniturno );
			result.add( hfimturno );
		} else {
			result.add( hiniturno );
			result.add( hiniintturno );
			result.add( hfimintturno );
			result.add( hfimturno );
		} 	
    	return result;
    }
    
    private Vector<String> getTurnosembatida(Vector<String> batidas, Vector<String> turnos) {
    	Vector<String> result = new Vector<String>();
    	String hbat = null;
    	String hturno = null;
    	int posdif = -1;
    	long dif = 0;
    	long difant = 0;
    	// Clonar turnos
    	for ( int i=0; i<turnos.size(); i++ ) {
    		result.add( turnos.elementAt( i ) );
    	}
    	// Comparar batidas com hor�rios do turno de trabalho
    	for ( int i=0; i<batidas.size(); i++) {
    		hbat = batidas.elementAt( i );
    		posdif = -1;
    		for ( int t=0; t<result.size(); t++ ) {
    			hturno = result.elementAt( t );
    			dif = Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( hturno ), Funcoes.strTimeTosqlTime( hbat ));
    			if (dif<0) {
    				dif = dif * -1;
    			}
    			if (posdif==-1) {
    				difant = dif;
    				posdif = t;
    			} else if (dif<difant) {
    				difant = dif;
    				posdif = t;
    			}
    		}
    		if (posdif>-1) {
    			result.remove( posdif );
    		}
    	}
    	return result;
    }
    
    private Vector<String> getBatidas(Vector<Object> row, int posini, int numcols) {
    	Vector<String> result = new Vector<String>();
    	String hbat = null;
    	for (int i=posini; i<numcols; i++ ) {
    		hbat = (String) row.elementAt( i );
    		if (! "".equals( hbat )) {
    			result.add( hbat );
    		}
    	}
    	return result;
    }
    
    public boolean checarEstagio4(final Vector<Vector<Object>>  vatend) {
    	boolean result = false;
//    	Integer codemp = (Integer) prefs[PREFS.CODEMPMI.ordinal()];
//    	Integer codfilial = (Integer) prefs[PREFS.CODFILIALMI.ordinal()];
    	Integer codmodel = (Integer) prefs[PREFS.CODMODELMI.ordinal()];
    	String descmodel = (String) prefs[PREFS.DESCMODELMI.ordinal()];
    	int tempomaxint = (Integer) prefs[PREFS.TEMPOMAXINT.ordinal()];
    	Integer codespecia = (Integer) prefs[PREFS.CODESPECIA.ordinal()];
    	String descespecia = (String) prefs[PREFS.DESCESPECIA.ordinal()];
    	String dtatendopos = null;
    	String dtatendo = null;
    	String horaini = null;
    	String horafin = null;
    	String horaintervalo = null;
    	Vector<Object> row = null;
    	Vector<Object> rowPos = null;
    	int totalmin = 0;
    	int intervalo = 0;
    	int intervaloinserir = 0;
    	int totintervalo = 0;
    	int pos = 0;
    	// Abre um loop at� o final do vetor de atendimentos
		for (int i=0; i<vatend.size(); i++) {
			row = vatend.elementAt( i );
			dtatendo = (String) row.elementAt( EColAtend.DATAATENDO.ordinal() );
			intervalo = (Integer) row.elementAt( EColAtend.INTERVATENDO.ordinal() );
			// Se foi detectado que existe um intervalo sem lan�amentos
			if (intervalo>0) {
				pos = primeiroAtend(vatend, dtatendo); // Checar as datas do mesmo dia, para verificar se n�o ultrapassar� o limite para intervalos.
				dtatendopos = dtatendo;
				totalmin = 0;
				while ( ( pos<vatend.size() ) && (dtatendo.equals( dtatendopos ) ) ) {
					rowPos = vatend.elementAt( pos );
					dtatendopos = (String) rowPos.elementAt( EColAtend.DATAATENDO.ordinal() );
					if ( codespecia.equals((Integer) rowPos.elementAt( EColAtend.CODESPEC.ordinal() ))	) {
						totalmin += ( (Integer) rowPos.elementAt( EColAtend.TOTALMIN.ordinal() ) );
					}
					pos ++;
				}
				// Verificar se � poss�vel inserir lan�amentos
				intervaloinserir = intervalo;
				if (intervaloinserir>tempomaxint) {
					intervaloinserir = tempomaxint;
				}
				intervaloinserir = intervaloinserir - totalmin;
				// Se restar intervalo a inserir
				if ( row != null) {
					if ( (row!=null) && (intervaloinserir>0) ) {
						horaintervalo = Funcoes.longTostrTime( (long) intervaloinserir * 1000 * 60 );
						horafin = Funcoes.copy( (String) row.elementAt( EColAtend.HORAATENDO.ordinal() ), 5);
						horaini = Funcoes.copy( Funcoes.longTostrTime( 
								Funcoes.subtraiTime( Funcoes.strTimeTosqlTime( horaintervalo ),
										Funcoes.strTimeTosqlTime( horafin )
								)
						), 5);
						row.setElementAt( horaini, EColAtend.HORAINI.ordinal() );
						row.setElementAt( horafin, EColAtend.HORAFIN.ordinal() );
						row.setElementAt( EstagioCheck.E4I.getValueTab(), EColAtend.SITREVATENDO.ordinal() );
						row.setElementAt( EstagioCheck.E4I.getImg(), EColAtend.SITREVATENDOIMG.ordinal() );
						row.setElementAt( codmodel , EColAtend.CODMODEL.ordinal() );
						row.setElementAt( descmodel, EColAtend.DESCMODEL.ordinal() );

						result = true;
					} else {
						row.setElementAt( EstagioCheck.E4O.getValueTab(), EColAtend.SITREVATENDO.ordinal() );
						row.setElementAt( EstagioCheck.E4O.getImg(), EColAtend.SITREVATENDOIMG.ordinal() );
					}
				}
			}
			else if ( row!=null ) {
				row.setElementAt( EstagioCheck.E4O.getValueTab(), EColAtend.SITREVATENDO.ordinal() );
				row.setElementAt( EstagioCheck.E4O.getImg(), EColAtend.SITREVATENDOIMG.ordinal() );
			}
		}
    	return result;
    }
    
    public int primeiroAtend(Vector<Vector<Object>> vatend, String dtatend ) {
    	int result = -1;
    	Vector<Object> row = null;
    	for (int i=0; i<vatend.size(); i++) {
    		row = vatend.elementAt( i );
    		if (dtatend.equals( row.elementAt( EColAtend.DATAATENDO.ordinal() ) ) ) {
    			result = i;
    			break;
    		}
    	}
    	return result;
    }
}
