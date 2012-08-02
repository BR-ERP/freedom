package org.freedom.modulos.lvf.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.hssf.record.formula.eval.ConcatEval;
import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;


public class DAOClFiscal extends AbstractDAO {
	
	public static enum INSERT_CLFISCAL {
		CODEMP, CODFILIAL, CODFISC, DESCFISC, TIPOFISC, TPREDICMSFISC, ALIQFISC, ALIQLFISC, REDFISC, ALIQIPIFISC, CODEMPRA, CODFILIALRA, CODREGRA, ORIGFISC, CODEMPTT,
		CODFILIALTT,  CODTRATTRIB, CODEMPME, CODFILIALME, CODMENS, SITPISFISC, SITCOFINSFISC, TIPOST, MARGEMVLAGR, CODNCM, CODNBM, EXTIPI, CODSERV ;
		
	}
	
	public static enum INSERT_CLITFISCAL {
		CODEMP, CODFILIAL, CODFISC, CODITFISC, ORIGFISC, CODEMPTT, CODFILIALTT, TIPOFISC, TPREDICMSFISC, REDFISC, CODTRATTRIB, NOUFITFISC, CODEMPFC, CODFILIALFC,
		CODFISCCLI, ALIQFISC, ALIQFISCINTRA, ALIQLFISC, ALIQIPIFISC, ALIQPISFISC, ALIQCOFINSFISC, ALIQCSOCIALFISC, ALIQIRFISC, ALIQFUNRURALFISC, ALIQIIFISC, CODEMPME, 
		CODFILIALME, CODMENS, CODEMPTM, CODFILIALTM, CODTIPOMOV, TIPOST, MARGEMVLAGR, GERALFISC, CODEMPSP, CODFILIALSP, CODSITTRIBPIS, IMPSITTRIBPIS, CODEMPSC, CODFILIALSC, 
        CODSITTRIBCOF, IMPSITTRIBCOF, CODEMPSI, CODFILIALSI, CODSITTRIBIPI, IMPSITTRIBIPI, TPCALCIPI, VLRIPIUNIDTRIB, MODBCICMS,  MODBCICMSST, CODPAIS, SIGLAUF, 
        VLRPISUNIDTRIB, VLRCOFUNIDTRIB, TIPOUSOITFISC, REDBASEST, REDBASEFRETE, CODEMPRA, CODFILIALRA, CODREGRA, CODEMPIS,  CODFILIALIS, CODSITTRIBISS, IMPSITTRIBISS, 
        ALIQISSFISC, RETENSAOISS, INDAPURIPI, CODEMPCN, CODFILIALCN, CSOSN, ALIQICMSIMP, PERCCREDPRESIMP, ADICIPIBASEICMS ;
	}
	
	public DAOClFiscal( DbConnection cn) {

		super( cn );
	}
	
	public boolean cloneClFiscal(Integer codemp, Integer codfilial, String newcodfisc, String codfisc) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Integer retorno = new Integer( 0 );
		
		try{
			
			//Insere dados copiadas na classifica��o correta.
			sql = new StringBuilder();
			sql.append( " INSERT INTO LFCLFISCAL(     CODEMP, CODFILIAL, CODFISC, DESCFISC, TIPOFISC, TPREDICMSFISC, ALIQFISC, ALIQLFISC, ");
			sql.append( "REDFISC, ALIQIPIFISC, CODEMPRA, CODFILIALRA, CODREGRA, ORIGFISC, CODEMPTT, ");
			sql.append( "CODFILIALTT,  CODTRATTRIB, CODEMPME, CODFILIALME, CODMENS, SITPISFISC, SITCOFINSFISC, TIPOST, MARGEMVLAGR, CODNCM, CODNBM, EXTIPI, CODSERV ) ");
			//Carrega Classifica��o que foi escolhida para ser copiada.
			sql.append( "SELECT ?, ?, ?, DESCFISC, TIPOFISC, TPREDICMSFISC, ALIQFISC, ALIQLFISC, ");
			sql.append( "REDFISC, ALIQIPIFISC, CODEMPRA, CODFILIALRA, CODREGRA, ORIGFISC, CODEMPTT, ");
			sql.append( "CODFILIALTT,  CODTRATTRIB, CODEMPME, CODFILIALME, CODMENS, SITPISFISC, SITCOFINSFISC, TIPOST, MARGEMVLAGR, CODNCM, CODNBM, EXTIPI, CODSERV ");
			sql.append( "FROM LFCLFISCAL where CODEMP=? and CODFILIAL=? AND CODFISC=? ");
			
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setString( param++, newcodfisc );
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setString( param++, codfisc );
			
			ps.execute();
			ps.close();
			
		} finally {
			ps = null;
		}
		
		return true;
	}
	
	public boolean cloneItClFiscal(Integer codemp, Integer codfilial, String newcodfisc, String codfisc, Integer itemclassifisc) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Integer retorno = new Integer( 0 );
		
		try{
			
			//Insere dados copiadas na classifica��o correta.
			sql = new StringBuilder();
			sql.append( "INSERT INTO lfitclfiscal ( CODEMP, CODFILIAL, CODFISC, CODITFISC, ORIGFISC, CODEMPTT, CODFILIALTT, TIPOFISC, TPREDICMSFISC, REDFISC, CODTRATTRIB, NOUFITFISC, CODEMPFC, CODFILIALFC, ");
			sql.append( "CODFISCCLI, ALIQFISC, ALIQFISCINTRA, ALIQLFISC, ALIQIPIFISC, ALIQPISFISC, ALIQCOFINSFISC, ALIQCSOCIALFISC, ALIQIRFISC, ALIQFUNRURALFISC, ALIQIIFISC, CODEMPME, ");
			sql.append( "CODFILIALME, CODMENS, CODEMPTM, CODFILIALTM, CODTIPOMOV, TIPOST, MARGEMVLAGR, GERALFISC, CODEMPSP, CODFILIALSP, CODSITTRIBPIS, IMPSITTRIBPIS, CODEMPSC, CODFILIALSC,"); 
			sql.append( "CODSITTRIBCOF, IMPSITTRIBCOF, CODEMPSI, CODFILIALSI, CODSITTRIBIPI, IMPSITTRIBIPI, TPCALCIPI, VLRIPIUNIDTRIB, MODBCICMS,  MODBCICMSST, CODPAIS, SIGLAUF, ");
			sql.append( "VLRPISUNIDTRIB, VLRCOFUNIDTRIB, TIPOUSOITFISC, REDBASEST, REDBASEFRETE, CODEMPRA, CODFILIALRA, CODREGRA, CODEMPIS,  CODFILIALIS, CODSITTRIBISS, IMPSITTRIBISS,"); 
			sql.append( "ALIQISSFISC, RETENSAOISS, INDAPURIPI, CODEMPCN, CODFILIALCN, CSOSN, ALIQICMSIMP, PERCCREDPRESIMP, ADICIPIBASEICMS ) ");

			sql.append( "select ?, ?, ?, ?, ORIGFISC, CODEMPTT, CODFILIALTT, TIPOFISC, TPREDICMSFISC, REDFISC, CODTRATTRIB, NOUFITFISC, CODEMPFC, CODFILIALFC, ");
			sql.append( "CODFISCCLI, ALIQFISC, ALIQFISCINTRA, ALIQLFISC, ALIQIPIFISC, ALIQPISFISC, ALIQCOFINSFISC, ALIQCSOCIALFISC, ALIQIRFISC, ALIQFUNRURALFISC, ALIQIIFISC, CODEMPME, "); 
			sql.append( "CODFILIALME, CODMENS, CODEMPTM, CODFILIALTM, CODTIPOMOV, TIPOST, MARGEMVLAGR, GERALFISC, CODEMPSP, CODFILIALSP, CODSITTRIBPIS, IMPSITTRIBPIS, CODEMPSC, CODFILIALSC, "); 
			sql.append( "CODSITTRIBCOF, IMPSITTRIBCOF, CODEMPSI, CODFILIALSI, CODSITTRIBIPI, IMPSITTRIBIPI, TPCALCIPI, VLRIPIUNIDTRIB, MODBCICMS,  MODBCICMSST, CODPAIS, SIGLAUF, ");
			sql.append( "VLRPISUNIDTRIB, VLRCOFUNIDTRIB, TIPOUSOITFISC, REDBASEST, REDBASEFRETE, CODEMPRA, CODFILIALRA, CODREGRA, CODEMPIS,  CODFILIALIS, CODSITTRIBISS, IMPSITTRIBISS, "); 
			sql.append( "ALIQISSFISC, RETENSAOISS, INDAPURIPI, CODEMPCN, CODFILIALCN, CSOSN, ALIQICMSIMP, PERCCREDPRESIMP, ADICIPIBASEICMS  from lfitclfiscal where codemp=? and codfilial=? and codfisc=? and coditfisc=? ");

			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setString( param++, newcodfisc );
			ps.setInt( param++, itemclassifisc );
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setString( param++, codfisc );
			ps.setInt( param++, itemclassifisc );
			
			ps.execute();
			ps.close();
			
		} finally {
			ps = null;
		}
		
		return true;
	}

	public Integer getQtdItemFiscal(Integer codemp, Integer codfilial, String codfisc) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Integer quantidade = new Integer( 0 );
		
		try{
			sql = new StringBuilder();
			sql.append( "select count(*) from lfitclfiscal where codemp=? and codfilial=? and codfisc=? ");
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setString( param++, codfisc );
			
			rs = ps.executeQuery();
			if(rs.next()){
				quantidade = rs.getInt( 1 );
			}
			
		}finally{
			rs = null;
			ps = null;
		}
		return quantidade;
	}
	
}
