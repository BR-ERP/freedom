/**
 * @version 12/07/2011 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms.business.object <BR>
 *         Classe: @(#)Expedicao.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * 
 *         Classe para regras de negocio de expedi�ao de produtos acabados.
 * 
 */

package org.freedom.modulos.gms.business.object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.infra.pojos.Constant;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.fnc.view.dialog.utility.DLInfoPlanoPag;
import org.freedom.modulos.std.business.component.Orcamento;

public class Expedicao implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Status do recebimento
	
	/*
	PE - Pendente;
	PI - Pesagem de entrada.
	PS - Pesagem de sa�da.
	NE - Notas de sa�da emitidas;
	FN - Finalizada;
	
	*/
	public static final Constant STATUS_NAO_SALVO = new Constant( "N�o Salvo", null );

	public static final Color COR_NAO_SALVO = Color.GRAY;

	public static final Constant STATUS_PENDENTE = new Constant( "Pendente", "PE" );

	public static final Color COR_PENDENTE = Color.ORANGE;

	public static final Constant STATUS_PESAGEM_INICIAL = new Constant( "Pesagem inicial", "PI" );

	public static final Color COR_PESAGEM_INICIAL = Color.BLUE;

	public static final Constant STATUS_PESAGEM_SAIDA = new Constant( "Pesagem sa�da", "PS" );

	public static final Color COR_PESAGEM_SAIDA = Color.BLUE;

	public static final Constant STATUS_ROMANEIO_EMITIDO = new Constant( "Romaneio emitido", "RE" );

	public static final Color COR_ROMANEIO_EMITIDO = new Color( 45, 190, 60 );

	public static String IMG_TAMANHO_M = "16x16";

	public static String IMG_TAMANHO_P = "10x10";

	private HashMap<String, Object> primeirapesagem = null;

	private HashMap<String, Object> segundapesagem = null;

	private HashMap<String, Object> rendapesagem = null;

	private Integer ticket = null;

	private DbConnection con = null;

	private Component orig = null;

	private String tipofrete = null;

	private Integer codroma = null;

	private Integer codfrete = null;

	private Integer codtran = null;

	private Object[] oPrefs = null;

	private Date dtsaida = null;

	private BigDecimal precopeso = null;

	private String status = null;

	private enum COLS_ITEXPEDICAO {
		CODEMP, CODFILIAL, TICKET, CODITEXPED, CODEMPPD, REFPROD, CODFILIALPD, CODPROD ;
	}

	public static ImageIcon getImagem( String status, String tamanho ) {

		ImageIcon img = null;

		ImageIcon IMG_PENDENTE = Icone.novo( "blAzul0_" + tamanho + ".png" );

		ImageIcon IMG_PESAGEM1 = Icone.novo( "blAzul1_" + tamanho + ".png" );

		ImageIcon IMG_DESCARREGAMENTO = Icone.novo( "blAzul2_" + tamanho + ".png" );

		ImageIcon IMG_FINALIZADO = Icone.novo( "os_pronta_" + tamanho + ".png" );

		ImageIcon IMG_PEDIDO = Icone.novo( "os_orcamento_" + tamanho + ".png" );

		ImageIcon IMG_NOTA = Icone.novo( "os_finalizada_" + tamanho + ".png" );

		try {

			if ( status.equals( STATUS_PENDENTE.getValue() ) ) {
				return IMG_PENDENTE;
			}
			else if ( status.equals( STATUS_PESAGEM_INICIAL.getValue() ) ) {
				return IMG_PESAGEM1;
			}
			else if ( status.equals( STATUS_PESAGEM_SAIDA.getValue() ) ) {
				return IMG_DESCARREGAMENTO;
			}
			else if ( status.equals( STATUS_ROMANEIO_EMITIDO.getValue() ) ) {
				return IMG_PEDIDO;
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return img;
	}

	public Expedicao( Component orig, Integer ticket, DbConnection con ) {

		setTicket( ticket );
		setConexao( con );
		setOrig( orig );

		CarregaExpedicao();

		buscaPesagens();

	}

	private void buscaPesagens() {

		buscaPrimeiraPesagem();
		buscaSegundaPesagem();

	}

	private void geraPrefereOrc() {

		oPrefs = Orcamento.getPrefere();
	}

	public static void atualizaStatus( String status, JLabelPad lbstatus ) {

		lbstatus.setForeground( Color.WHITE );
		lbstatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbstatus.setOpaque( true );
		lbstatus.setHorizontalAlignment( SwingConstants.CENTER );

		if ( status == STATUS_NAO_SALVO.getValue() ) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_NAO_SALVO );
		}
		else if ( STATUS_PENDENTE.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_PENDENTE );
		}
		else if ( STATUS_PESAGEM_INICIAL.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_PESAGEM_INICIAL.getName() );
			lbstatus.setBackground( COR_PESAGEM_INICIAL );
		}
		else if ( STATUS_PESAGEM_SAIDA.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_PESAGEM_SAIDA.getName() );
			lbstatus.setBackground( COR_PESAGEM_SAIDA );
		}
		else if ( STATUS_ROMANEIO_EMITIDO.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_ROMANEIO_EMITIDO.getName() );
			lbstatus.setBackground( COR_ROMANEIO_EMITIDO );
		}

	}

	public static Vector<String> getLabels() {

		Vector<String> ret = new Vector<String>();

		ret.add( STATUS_PENDENTE.getName() );
		ret.add( STATUS_PESAGEM_INICIAL.getName() );
		ret.add( STATUS_PESAGEM_SAIDA.getName() );
		ret.add( STATUS_ROMANEIO_EMITIDO.getName() );

		return ret;

	}

	public static Vector<Object> getValores() {

		Vector<Object> ret = new Vector<Object>();

		ret.add( STATUS_PENDENTE.getValue() );
		ret.add( STATUS_PESAGEM_INICIAL.getValue() );
		ret.add( STATUS_PESAGEM_SAIDA.getValue() );
		ret.add( STATUS_ROMANEIO_EMITIDO.getValue() );

		return ret;

	}

	private void buscaPrimeiraPesagem() {

		HashMap<String, Object> pesagem = null;
		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			pesagem = new HashMap<String, Object>();

			sql.append( "select first 1 a.pesoamost peso, a.dataamost data, a.horaamost hora, pd.codunid, a.seqamostragem " );
			sql.append( "from eqexpedamostragem a, eqitexpedicao i, eqprocexped p, eqproduto pd " );
			sql.append( "where " );
			sql.append( "a.codemp=i.codemp and a.codfilial=i.codfilial and a.ticket=i.ticket and a.coditexped=i.coditexped " );
			sql.append( "and i.codemp=? and i.codfilial=? and i.ticket=? " );
			sql.append( "and p.codemp=i.codempte and p.codfilial=i.codfilialte and p.codprocexped=i.codprocexped and p.tipoprocexped=? " );
			sql.append( "and pd.codemp=i.codemppd and pd.codfilial=i.codfilialpd and pd.codprod=i.codprod " );
			sql.append( "order by a.dataamost desc, a.codamostragem desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQEXPEDICAO" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, (String) TipoExpedicao.PROCESSO_PESAGEM_INICIAL.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "peso", rs.getBigDecimal( "peso" ) );
				pesagem.put( "data", Funcoes.dateToStrDate( rs.getDate( "data" ) ) );
				pesagem.put( "hora", rs.getString( "hora" ) );
				pesagem.put( "unid", rs.getString( "codunid" ).trim() );

			}

			// con.commit();

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
			sql.append( "from eqexpedamostragem a, eqitexpedicao i, eqprocexped p, eqproduto pd " );
			sql.append( "where " );
			sql.append( "a.codemp=i.codemp and a.codfilial=i.codfilial and a.ticket=i.ticket and a.coditexped=i.coditexped " );
			sql.append( "and i.codemp=? and i.codfilial=? and i.ticket=? " );
			sql.append( "and p.codemp=i.codempte and p.codfilial=i.codfilialte and p.codprocexped=i.codprocexped and p.tipoprocexped=?  and p.codtipoexped=i.codtipoexped " );
			sql.append( "and pd.codemp=i.codemppd and pd.codfilial=i.codfilialpd and pd.codprod=i.codprod " );
			sql.append( "order by a.dataamost, a.codamostragem desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQEXPEDICAO" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, (String) TipoExpedicao.PROCESSO_PESAGEM_FINAL.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "peso", rs.getBigDecimal( "peso" ) );
				pesagem.put( "data", Funcoes.dateToStrDate( rs.getDate( "data" ) ) );
				pesagem.put( "hora", rs.getString( "hora" ) );
				pesagem.put( "unid", rs.getString( "codunid" ).trim() );

			}

			// con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		setSegundapesagem( pesagem );
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

	public DbConnection getCon() {

		return con;
	}

	public void setCon( DbConnection con ) {

		this.con = con;
	}

	public Integer getTicket() {

		return ticket;
	}

	private void setConexao( DbConnection con ) {

		this.con = con;
	}

	private void setTicket( Integer ticket ) {

		this.ticket = ticket;
	}

	private void geraCodFrete() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codfrete = 1;

		try {

			sql.append( "select coalesce(max(codfrete),0) + 1 from lffrete " );
			sql.append( "where codemp=? and codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFFRETE" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codfrete = rs.getInt( 1 );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		setCodfrete( codfrete );

	}

	private void CarregaExpedicao() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codtipomov = null;
		String serietipomov = null;
		String seqserietipomov = null;

		try {

			sql.append( "select ex.tipofrete ,  " );
			sql.append( "ex.codtran, ex.dtsaida, ex.status " );

			sql.append( "from eqexpedicao ex left outer join eqtipoexpedicao te on " );
			sql.append( "te.codemp=ex.codemp and te.codfilial=ex.codfilial and te.codtipoexped=ex.codtipoexped " );

			sql.append( "left outer join sgfilial fi on " );
			sql.append( "fi.codemp=ex.codemp and fi.codfilial=ex.codfilial " );

			sql.append( "where ex.codemp=? and ex.codfilial=? and ex.ticket=? " );

			ps = con.prepareStatement( sql.toString() );

			System.out.println( "SQL:" + sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQEXPEDICAO" ) );
			ps.setInt( 3, getTicket() );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				setTipofrete( rs.getString( "tipofrete" ) );
				setCodtran( rs.getInt( "codtran" ) );
				setDtsaida( Funcoes.sqlDateToDate( rs.getDate( "dtsaida" ) ) );
//				setPrecopeso( rs.getBigDecimal( "vlrfrete" ) );;
				setStatus( rs.getString( "status" ) );
			}

			// con.commit();

		} catch ( Exception e ) {
			Funcoes.mensagemErro( orig, "Erro ao buscar informa��es do recebimento de mercadorias!", true, con, e );

			e.printStackTrace();
		}

	}

	public Integer geraFreteExpedicao() {

		StringBuilder sql = new StringBuilder();

		Integer ticket = null;
		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		PreparedStatement ps = null;

		Integer ret = null;
		
		try {

			HashMap<String, Object> p1 = getPrimeirapesagem();

			peso1 = (BigDecimal) p1.get( "peso" );

			HashMap<String, Object> p2 = getSegundapesagem();

			peso2 = (BigDecimal) p2.get( "peso" );

			pesoliq = peso1.subtract( peso2 );

			geraCodFrete();

			sql.append( "insert into lffrete (" );
			sql.append( "codemp, codfilial, codfrete,  " );
			sql.append( "codemptn, codfilialtn, codtran, " );
			sql.append( "codemptm, codfilialtm, codtipomov, serie, docfrete, " );
			sql.append( "tipofrete, tipopgto, " );
			sql.append( "codempre, codfilialre, codremet, " );
			sql.append( "codempde, codfilialde, coddestinat, " );
			sql.append( "dtemitfrete, qtdfrete, vlrmercadoria, vlrfrete, " );
			sql.append( "pesobruto, pesoliquido, codempex, codfilialex, ticketex " );

			sql.append( ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ? )" );

			ps = con.prepareStatement( sql.toString() );

			int param = 1;

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "LFFRETE" ) );
			ps.setInt( param++, getCodfrete() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setInt( param++, getCodtran() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );

			Integer codtipomov = TipoMov.getTipoMovFrete();
			String serie = TipoMov.getSerieTipoMov( codtipomov );

			ps.setInt( param++, codtipomov );
			ps.setString( param++, serie );

			// ps.setInt( param++, TipoMov.getDocSerie( serie ));

			ps.setInt( param++, getTicket() );

			ps.setString( param++, getTipofrete() );

			ps.setString( param++, "A" ); // Frete a pagar

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGUNIFCOD" ) );
//			ps.setInt( param++, getCodremet() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGUNIFCOD" ) );
//			ps.setInt( param++, getCoddestinat() );

			ps.setDate( param++, Funcoes.dateToSQLDate( getDtent() ) );

			ps.setBigDecimal( param++, pesoliq );

			ps.setBigDecimal( param++, getValorLiqVendas() );

			ps.setBigDecimal( param++, getValorFrete( getPrecopeso(), pesoliq ) );

			ps.setBigDecimal( param++, pesoliq );

			ps.setBigDecimal( param++, pesoliq );

			ps.setInt( param++, Aplicativo.iCodEmp );

			ps.setInt( param++, ListaCampos.getMasterFilial( "EQEXPEDICAO" ) );

			ps.setInt( param++, getTicket() );

			ps.execute();

			ps.close();

			// / Vincula Compra/Frete

			sql = new StringBuilder();

			sql.append( "insert into lffretevenda (codemp, codfilial, codfrete, codvenda, tipovenda) ");
			sql.append( "(select vd.codemp, vd.codfilial, "+ getCodfrete() +", vd.codvenda, vd.tipovenda ");
			sql.append( "from vdvenda vd, vditromaneio ir ");
			sql.append( "where ir.codempva=vd.codemp and ir.codfilialva=vd.codfilial and ir.codvenda=vd.codvenda and ir.tipovenda=vd.tipovenda ");
			sql.append( "and ir.codemp=? and ir.codfilial=? and ir.codroma=? ) " );
		
			ps = con.prepareStatement( sql.toString() );

			param = 1;

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "LFFRETE" ) );
			ps.setInt( param++, getCodRoma() );


			ps.execute();

			con.commit();

			ps.close();
			
			ret = getCodfrete(); 

		} 
		catch ( Exception e ) {
			
			Funcoes.mensagemErro( null, "Erro ao gerar conhecimento de frete!", true, con, e );
			
			e.printStackTrace();
		
			try {
				con.rollback();
			} 
			catch ( Exception err ) {
				err.printStackTrace();
			}
			
		}
		
		return ret;
		
	}

	public BigDecimal getValorLiqVendas() {

		BigDecimal ret = null;

		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select sum(vd.vlrliqvenda) vlrliqvenda from vdvenda vd, vditromaneio ir where " );
			
			sql.append( "ir.codempva=vd.codemp and ir.codfilialva=vd.codfilial and ir.codvenda=vd.codvenda and ir.tipovenda=vd.tipovenda and " );
			sql.append( "ir.codemp=? and ir.codfilial=? and ir.codroma=? " );

			PreparedStatement ps = Aplicativo.getInstace().getConexao().prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setInt( 3, getCodRoma() );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				ret = rs.getBigDecimal( "vlrliqvenda" );

			}

			rs.close();
			ps.close();

		} 
		catch ( SQLException e ) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public HashMap<String,BigDecimal> getRomaneio() {

		HashMap<String,BigDecimal> ret = new HashMap<String, BigDecimal>();

		try {

			StringBuilder sql = new StringBuilder();

			sql.append( "select ");
			sql.append( "coalesce(sum(fr.qtdfretevd),0.00) qtd, coalesce(sum(fr.pesobrutvd),0.00) peso ");
			sql.append( "from eqexpedicao ex, vditromaneio ro ");

			sql.append( "left outer join vdfretevd fr on fr.codemp=ro.codempva and fr.codfilial=ro.codfilialva and fr.codvenda=ro.codvenda and fr.tipovenda=ro.tipovenda ");
			sql.append( "left outer join vdvenda vd on vd.codemp=fr.codemp and vd.codfilial=fr.codfilial and vd.codvenda=fr.codvenda and vd.tipovenda=fr.tipovenda ");

			sql.append( "where ");
			sql.append( "ro.codemp=ex.codempro and ro.codfilial=ex.codfilialro and ro.codroma=ex.codroma and ");
			sql.append( "ex.codemp=? and ex.codfilial=? and ex.ticket=?" );

			PreparedStatement ps = Aplicativo.getInstace().getConexao().prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQEXPEDICAO" ) );
			ps.setInt( 3, getTicket() );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				
				ret.put( "QTD", rs.getBigDecimal( "QTD" ) );
				ret.put( "PESO", rs.getBigDecimal( "PESO" ) );
		
			}

			rs.close();
			ps.close();

		} 
		catch ( SQLException e ) {
			e.printStackTrace();
		}
		return ret;
	}


	public Integer excluiOrcOS(Integer codorc) {
		
		Integer ret = 0;

		try {

			StringBuilder sql_item = new StringBuilder();

			sql_item.append( "delete from vditorcamento where codemp=? and codfilial=? and codorc=? " );
			
			StringBuilder sql_orc = new StringBuilder();

			sql_item.append( "delete from vdorcamento where codemp=? and codfilial=? and codorc=? " );

			
			// Apagando itens de or�amento
			PreparedStatement ps = Aplicativo.getInstace().getConexao().prepareStatement( sql_item.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDITORCAMENTO" ) );
			ps.setInt( 3, codorc );

			ps.executeUpdate();

			ps.close();
			con.commit();

			// Apagando cabe�alho do or�amento
			ps = Aplicativo.getInstace().getConexao().prepareStatement( sql_orc.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDITORCAMENTO" ) );
			ps.setInt( 3, codorc );

			ret = ps.executeUpdate();

			ps.close();
			con.commit();
			

		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return ret;
		
	}

	public Vector<HashMap<String, Object>> carregaItExpedicao() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<HashMap<String, Object>> ret = new Vector<HashMap<String, Object>>();

		try {

			sql.append( "select " );
			sql.append( COLS_ITEXPEDICAO.CODEMP.name() );
			sql.append( "," );
			sql.append( COLS_ITEXPEDICAO.CODFILIAL.name() );
			sql.append( "," );
			sql.append( COLS_ITEXPEDICAO.TICKET.name() );
			sql.append( "," );
			sql.append( COLS_ITEXPEDICAO.CODITEXPED.name() );
			sql.append( "," );
			sql.append( COLS_ITEXPEDICAO.CODEMPPD.name() );
			sql.append( "," );
			sql.append( COLS_ITEXPEDICAO.CODFILIALPD.name() );
			sql.append( "," );
			sql.append( COLS_ITEXPEDICAO.CODPROD.name() );

			sql.append( " from " );
			sql.append( "eqitexpedicao ite " );

			sql.append( "where " );

			sql.append( "codemp=? and codfilial=? and ticket=? " );

			System.out.println( "query de itens de expedicao:" + sql.toString() );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQEXPEDICAO" ) );
			ps.setInt( 3, getTicket() );

			rs = ps.executeQuery();

			while ( rs.next() ) {

				HashMap<String, Object> item = new HashMap<String, Object>();

				item.put( COLS_ITEXPEDICAO.CODEMP.name(), rs.getInt( COLS_ITEXPEDICAO.CODEMP.name() ) );
				item.put( COLS_ITEXPEDICAO.CODFILIAL.name(), rs.getInt( COLS_ITEXPEDICAO.CODFILIAL.name() ) );
				item.put( COLS_ITEXPEDICAO.TICKET.name(), rs.getInt( COLS_ITEXPEDICAO.TICKET.name() ) );
				item.put( COLS_ITEXPEDICAO.CODITEXPED.name(), rs.getInt( COLS_ITEXPEDICAO.CODITEXPED.name() ) );
				item.put( COLS_ITEXPEDICAO.CODEMPPD.name(), rs.getInt( COLS_ITEXPEDICAO.CODEMPPD.name() ) );
				item.put( COLS_ITEXPEDICAO.CODFILIALPD.name(), rs.getInt( COLS_ITEXPEDICAO.CODFILIALPD.name() ) );
				item.put( COLS_ITEXPEDICAO.CODPROD.name(), rs.getInt( COLS_ITEXPEDICAO.CODPROD.name() ) );

				ret.add( item );

			}

			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return ret;

	}
	private Integer buscaPlanoPag() {

		Integer codplanopag = null;

		try {

			DLInfoPlanoPag dl = new DLInfoPlanoPag( getOrig(), con );
			dl.setConexao( con );

			dl.setVisible( true );

			if ( dl.OK ) {
				codplanopag = dl.getValor();
				dl.dispose();
			}
			else {
				dl.dispose();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return codplanopag;
	}

	public BigDecimal getValorFrete( BigDecimal precopeso, BigDecimal peso ) {

		BigDecimal ret = null;

		try {

			ret = precopeso.multiply( peso );

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return ret;

	}

	public Component getOrig() {

		return orig;
	}

	public void setOrig( Component orig ) {

		this.orig = orig;
	}

	public String getTipofrete() {

		return tipofrete;
	}

	public void setTipofrete( String tipofrete ) {

		this.tipofrete = tipofrete;
	}

	public Integer getCodRoma() {

		return codroma;
	}

	public void setCodRomaneio( Integer codroma ) {

		this.codroma = codroma;
	}

	public Integer getCodfrete() {

		return codfrete;
	}

	public void setCodfrete( Integer codfrete ) {

		this.codfrete = codfrete;
	}

	public Integer getCodtran() {

		return codtran;
	}

	public void setCodtran( Integer codtran ) {

		this.codtran = codtran;
	}

	public Date getDtent() {

		return dtsaida;
	}

	public void setDtsaida( Date dtsaida ) {

		this.dtsaida = dtsaida;
	}

	public BigDecimal getPrecopeso() {

		return precopeso;
	}

	public void setPrecopeso( BigDecimal precopeso ) {

		this.precopeso = precopeso;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus( String status ) {

		this.status = status;
	}

}
