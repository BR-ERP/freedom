package org.freedom.business.object;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.SwingConstants;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.infra.pojos.Constant;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.frame.Aplicativo;

public class RecMerc implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Status do recebimento
	
	public static final Constant STATUS_NAO_SALVO = new Constant("N�o Salvo", null );
	
	public static final Color COR_NAO_SALVO = Color.GRAY;
	
	public static final Constant STATUS_PENDENTE = new Constant("Pendente", "PE"); 
	
	public static final Color COR_PENDENTE = Color.ORANGE;
	
	public static final Constant STATUS_PESAGEM_1 = new Constant("Pesagem 1", "E1");
	
	public static final Color COR_PESAGEM_1 = Color.BLUE;
	
	public static final Constant STATUS_PESAGEM_2 = new Constant("Pesagem 1", "E2");
	
	public static final Color COR_PESAGEM_2 = Color.BLUE;
		
	public static final Constant STATUS_RECEBIMENTO_FINALIZADO = new Constant("Finalizado", "FN");
	
	public static final Color COR_RECEBIMENTO_FINALIZADO = new Color( 45, 190, 60 );

	public static final Constant STATUS_NOTA_ENTRADA_EMITIDA = new Constant("Nota emitida", "NE");
	
	public static final Color COR_NOTA_ENTRADA_EMITIDA = Color.RED;
	
	private HashMap<String, Object> primeirapesagem = null;
	
	private HashMap<String, Object> segundapesagem = null;
	
	private HashMap<String, Object> rendapesagem = null;
	
	private Integer ticket = null;
	
	private DbConnection con = null;
	
	public RecMerc(Integer ticket, DbConnection con) {
		
		setTicket( ticket );
		setConexao( con );
		
		buscaPesagens();
		
	}
	
	private void buscaPesagens() {
		
		buscaPrimeiraPesagem();
		buscaSegundaPesagem();
		buscaRenda();
	
	}
	
	public static void atualizaStatus( String status, JLabelPad lbstatus ) {

		lbstatus.setForeground( Color.WHITE );
		lbstatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbstatus.setOpaque( true );
		lbstatus.setHorizontalAlignment( SwingConstants.CENTER );
		
		if ( status == STATUS_NAO_SALVO.getValue()) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_NAO_SALVO );
		}
		else if ( STATUS_PENDENTE.getValue().equals( status )) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_PENDENTE );
		}
		else if ( STATUS_PESAGEM_1.getValue().equals( status )) {
			lbstatus.setText( STATUS_PESAGEM_1.getName() );
			lbstatus.setBackground( COR_PESAGEM_1 );
		}
		else if ( STATUS_PESAGEM_2.getValue().equals( status )) {
			lbstatus.setText( STATUS_PESAGEM_2.getName() );
			lbstatus.setBackground( COR_PESAGEM_2 );
		}
		else if ( STATUS_RECEBIMENTO_FINALIZADO.getValue().equals( status )) {
			lbstatus.setText( STATUS_RECEBIMENTO_FINALIZADO.getName() );
			lbstatus.setBackground( COR_RECEBIMENTO_FINALIZADO );
		}
		else if ( STATUS_NOTA_ENTRADA_EMITIDA.getValue().equals( status )) {
			lbstatus.setText( STATUS_NOTA_ENTRADA_EMITIDA.getName() );
			lbstatus.setBackground( COR_NOTA_ENTRADA_EMITIDA );
		}

	}	
	
	private void buscaPrimeiraPesagem() {

		HashMap<String, Object> pesagem = null;
		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			pesagem = new HashMap<String, Object>();

			sql.append( "select first 1 a.pesoamost peso, a.dataamost data, a.horaamost hora, pd.codunid, a.seqamostragem " );
			sql.append( "from eqrecamostragem a, eqitrecmerc i, eqprocrecmerc p, eqproduto pd " );
			sql.append( "where " );
			sql.append( "a.codemp=i.codemp and a.codfilial=i.codfilial and a.ticket=i.ticket and a.coditrecmerc=i.coditrecmerc " );
			sql.append( "and i.codemp=? and i.codfilial=? and i.ticket=? " );
			sql.append( "and p.codemp=i.codemptp and p.codfilial=i.codfilialtp and p.codprocrecmerc=i.codprocrecmerc and p.tipoprocrecmerc=? " );
			sql.append( "and pd.codemp=i.codemppd and pd.codfilial=i.codfilialpd and pd.codprod=i.codprod " );
			sql.append( "order by a.dataamost desc, a.codamostragem desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, TipoRecMerc.PROCESSO_PESAGEM_INICIAL.getValue() );
			
			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "peso", rs.getBigDecimal( "peso" ) );
				pesagem.put( "data", Funcoes.dateToStrDate( rs.getDate( "data" ) ) );
				pesagem.put( "hora", rs.getString( "hora" ) );
				pesagem.put( "unid", rs.getString( "codunid" ).trim() );
				pesagem.put( "interno", rs.getString( "seqamostragem" ) );

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		setPrimeirapesagem( pesagem );
	}

	private void buscaSegundaPesagem() {

		HashMap<String, Object> pesagem = null;
		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			pesagem = new HashMap<String, Object>();

			sql.append( "select first 1 a.pesoamost peso, a.dataamost data, a.horaamost hora, pd.codunid " );
			sql.append( "from eqrecamostragem a, eqitrecmerc i, eqprocrecmerc p, eqproduto pd " );
			sql.append( "where " );
			sql.append( "a.codemp=i.codemp and a.codfilial=i.codfilial and a.ticket=i.ticket and a.coditrecmerc=i.coditrecmerc " );
			sql.append( "and i.codemp=? and i.codfilial=? and i.ticket=? " );
			sql.append( "and p.codemp=i.codemptp and p.codfilial=i.codfilialtp and p.codprocrecmerc=i.codprocrecmerc and p.tipoprocrecmerc=? " );
			sql.append( "and pd.codemp=i.codemppd and pd.codfilial=i.codfilialpd and pd.codprod=i.codprod " );
			sql.append( "order by a.dataamost, a.codamostragem desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, TipoRecMerc.PROCESSO_PESAGEM_FINAL.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "peso", rs.getBigDecimal( "peso" ) );
				pesagem.put( "data", Funcoes.dateToStrDate( rs.getDate( "data" ) ) );
				pesagem.put( "hora", rs.getString( "hora" ) );
				pesagem.put( "unid", rs.getString( "codunid" ).trim() );

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		setSegundapesagem( pesagem );
	}

	private void buscaRenda() {

		HashMap<String, Object> pesagem = null;
		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			pesagem = new HashMap<String, Object>();

			sql.append( "select first 1 i.mediaamostragem media, i.rendaamostragem renda " );
			sql.append( "from eqitrecmerc i, eqprocrecmerc p " );
			sql.append( "where " );
			sql.append( "i.codemp=? and i.codfilial=? and i.ticket=? " );
			sql.append( "and p.codemp=i.codemptp and p.codfilial=i.codfilialtp and p.codprocrecmerc=i.codprocrecmerc and p.tipoprocrecmerc=? " );
			sql.append( "order by i.coditrecmerc desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, TipoRecMerc.PROCESSO_DESCARREGAMENTO.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "media", rs.getBigDecimal( "media" ) );
				pesagem.put( "renda", rs.getString( "renda" ) );

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		setRendapesagem( pesagem );
	}

	
	public HashMap<String, Object> getPrimeirapesagem() {
	
		return primeirapesagem;
	}

	
	public void setPrimeirapesagem( HashMap<String, Object> primeirapesagem ) {
	
		this.primeirapesagem = primeirapesagem;
	}

	
	public HashMap<String, Object> getSegundapesagem() {
	
		return segundapesagem;
	}

	
	public void setSegundapesagem( HashMap<String, Object> segundapesagem ) {
	
		this.segundapesagem = segundapesagem;
	}

	
	public HashMap<String, Object> getRendapesagem() {
	
		return rendapesagem;
	}

	
	public void setRendapesagem( HashMap<String, Object> rendapesagem ) {
	
		this.rendapesagem = rendapesagem;
	}

	
	public DbConnection getCon() {
	
		return con;
	}

	
	public void setCon( DbConnection con ) {
	
		this.con = con;
	}

	
	public Integer getTicket() {
	
		return ticket;
	}

	private void setConexao(DbConnection con) {
		this.con = con;
	}
	
	private void setTicket(Integer ticket) {
		this.ticket = ticket;
	}
	
	private Integer getCodCompra() {
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codcompra = 1;
		
		try {
		
			sql.append( "select coalesce(max(codcompra)) + 1 from vdvenda " );  
			sql.append( "where codemp=? and codfilial=? " );

			ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				codcompra = rs.getInt( 1 );
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return codcompra;
		
	}
	
	public void geraCompra() {
		
		StringBuilder sql = new StringBuilder();
		
		Integer ticket = null;
		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		String unid = null;
		PreparedStatement ps = null;
		
		try {
			
				HashMap<String, Object> p1 = getPrimeirapesagem();

				peso1 = (BigDecimal) p1.get( "peso" );

				HashMap<String, Object> p2 = getSegundapesagem();

				peso2 = (BigDecimal) p2.get( "peso" );
				unid = (String) p2.get( "unid" );

				pesoliq = peso1.subtract( peso2 );

				// Executando procedure para gera��o da compra e vinculos.
					
				sql.append( "insert into cpcompra (");
				sql.append( "codemp, codfilial, codcompra, codemppg, codfilialpg, codplanopag,)" );
				sql.append( "codempfr, codfilialfr, codfor, codempse, codfilialse, codemptm, codfilialtm, codtipomov," );
				sql.append( "doccompra, dtentcompra, dtemitcompra, tipofretecompra" );
				sql.append( ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );
					
				ps = con.prepareStatement( sql.toString() );
					
				int param = 1;
					
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
				ps.setInt( param++, getCodCompra() );
				
				
					
					
				ps.execute();
					
					
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	

}



