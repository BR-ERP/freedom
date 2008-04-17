package org.freedom.componentes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class CustosProd {
	
	private BigDecimal custoPEPSProd = new BigDecimal(0);
	private BigDecimal custoPEPSAlmox= new BigDecimal(0);
	private BigDecimal custoMPMProd = new BigDecimal(0);		
	private BigDecimal custoMPMAlmox = new BigDecimal(0);
	String sSQL = null;
	ResultSet rs = null;
	PreparedStatement ps = null;		
	
	public BigDecimal getCustoMPMAlmox() {
	
		return custoMPMAlmox;
	}

	
	public void setCustoMPMAlmox( BigDecimal custoMPMAlmox ) {
	
		this.custoMPMAlmox = custoMPMAlmox;
	}

	
	public BigDecimal getCustoMPMProd() {
	
		return custoMPMProd;
	}

	
	public void setCustoMPMProd( BigDecimal custoMPMProd ) {
	
		this.custoMPMProd = custoMPMProd;
	}

	
	public BigDecimal getCustoPEPSAlmox() {
	
		return custoPEPSAlmox;
	}

	
	public void setCustoPEPSAlmox( BigDecimal custoPEPSAlmox ) {
	
		this.custoPEPSAlmox = custoPEPSAlmox;
	}

	
	public BigDecimal getCustoPEPSProd() {
	
		return custoPEPSProd;
	}

	
	public void setCustoPEPSProd( BigDecimal custoPEPSProd ) {
	
		this.custoPEPSProd = custoPEPSProd;
	}

	public CustosProd (Integer codalmox, Integer codprod, Connection con) {
		try {
							
			sSQL = "SELECT NCUSTOPEPS, NCUSTOMPM, NCUSTOMPMAX, NCUSTOPEPSAX FROM EQPRODUTOSP01(?,?,?,?,?,?)";
			
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			ps.setInt( 3, codprod.intValue() );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "EQALMOX" ) );
			ps.setInt( 6, codalmox.intValue() );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				setCustoPEPSProd ( new BigDecimal( rs.getFloat( "NCUSTOPEPS" )));
				setCustoMPMProd ( new BigDecimal( rs.getFloat( "NCUSTOMPM" ) ));
				setCustoPEPSAlmox ( new BigDecimal( rs.getFloat( "NCUSTOPEPSAX" )));
				setCustoMPMAlmox ( new BigDecimal( rs.getFloat( "NCUSTOMPMAX" )));
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( null, "N�o foi poss�vel carregar o valor de custo PEPS!\n" + e.getMessage() );
		} 
		finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
	
	}

	
	
	
}
