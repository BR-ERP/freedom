/**
 * @version 25/06/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe:
 * @(#)FCidade.java <BR>
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
 * 
 */

package org.freedom.modulos.cfg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.FDados;

public class FBairro extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodBairro = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtNomeBairro = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtNomeCidade = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcMunic = new ListaCampos( this );
	
	private ListaCampos lcUF = new ListaCampos( this );
	
	private ListaCampos lcPais = new ListaCampos( this );
	
	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodMun = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private JTextFieldFK txtDescMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	public FBairro() {
		this(false, null);
	}
	
	public FBairro(boolean novo, DbConnection pcon ) {

		super();
		
		setTitulo( "Cadastro de Bairros" );
		setAtribos( 50, 50, 415, 245 );

		montaListaCampos();
		
		lcCampos.setUsaME( false );

		adicCampo( txtCodBairro, 7, 140, 75, 20, "CodBairro", "C�d.Bairro", ListaCampos.DB_PK, true );
		adicCampo( txtNomeBairro, 85, 140, 300, 20, "NomeBairro", "Nome do bairro", ListaCampos.DB_SI, true );
		
		adicCampo( txtCodPais, 7, 20, 75, 20, "CodPais", "Cod.pa�s", ListaCampos.DB_PF, true );
		adicDescFK( txtDescPais, 85, 20, 300, 20, "DescPais", "Nome do pa�s" );

		adicCampo( txtSiglaUF, 7, 60, 75, 20, "SiglaUf", "Sigla UF", ListaCampos.DB_PF, true );
		adicDescFK( txtNomeUF, 85, 60, 300, 20, "NomeUF", "Nome UF" );
		
		adicCampo( txtCodMun, 7, 100, 75, 20, "CodMunic", "Cod.munic.", ListaCampos.DB_PF, true );
		adicDescFK( txtDescMun, 85, 100, 300, 20, "NomeMunic", "Nome do municipio" );		

		setListaCampos( true, "BAIRRO", "SG" );
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		lcCampos.setQueryInsert( false );
		
		setImprimir( true );
		
		if(novo) {
			setConexao( pcon );
			lcCampos.insert( true );
		}
		
	}
	
	public void setCodPais(Integer codpais) {
		txtCodPais.setVlrInteger( codpais );
		lcPais.carregaDados();
	}

	public void setSiglaUF(String siglauf) {
		txtSiglaUF.setVlrString( siglauf );
		lcUF.carregaDados();
	}

	public void setCodMunic(String codmunic) {
		txtCodMun.setVlrString( codmunic );
		lcMunic.carregaDados();
	}
	
	private void montaListaCampos() {
		
		/***************
		 * PA�S *
		 **************/

		lcPais.setUsaME( false );
		lcPais.add( new GuardaCampo( txtCodPais, "CodPais", "Cod.pa�s.", ListaCampos.DB_PK, true ) );
		lcPais.add( new GuardaCampo( txtDescPais, "NomePais", "Nome", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais );

		/***************
		 * UF *
		 **************/

		lcUF.setUsaME( false );
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, true ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "CODPAIS = #S", txtCodPais );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUF.setTabelaExterna( lcUF );

		/***************
		 * MUNICIPIO *
		 **************/

		lcMunic.setUsaME( false );
		lcMunic.add( new GuardaCampo( txtCodMun, "CodMunic", "C�d.Muni", ListaCampos.DB_PK, true ) );
		lcMunic.add( new GuardaCampo( txtDescMun, "NomeMunic", "Nome Muni.", ListaCampos.DB_SI, false ) );		
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setQueryCommit( false );
		lcMunic.setReadOnly( true );
		txtCodMun.setTabelaExterna( lcMunic );
		
		
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		
		super.actionPerformed( evt );
		
	}

	private void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		ImprimeOS imp = new ImprimeOS( "", con );	
		int linPag = imp.verifLinPag() - 1;	
		
		try {
		
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relat�rio de Bairros" );
			
			String sSQL = "SELECT BR.CODBAIRRO, BR.NOMEBAIRRO, MN.CODMUNIC, MN.NOMEMUNIC " 
						+ "FROM SGBAIRRO BR, SGMUNICIPIO MN "
						+ "WHERE BR.CODPAIS=MN.CODPAIS AND BR.SIGLAUF=MN.SIGLAUF AND BR.CODMUNIC=MN.CODMUNIC "
						+ "ORDER BY MN.NOMEMUNIC,BR.NOMEBAIRRO";
			
			ps = con.prepareStatement( sSQL );
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				if ( imp.pRow() >= linPag ) {
					imp.incPags();
					imp.eject();
				}
				
				if ( imp.pRow() == 0 ) {
					
					imp.impCab( 136, false );
					
					imp.say( 0, imp.normal() );					
					
					imp.say( 2, "C�d.Bair." );
					imp.say( 15, "Nome Bairro" );
					imp.say( 80, "Cod.Munic." );
					imp.say( 110, "Nome Munic." );
					
					imp.pulaLinha( 1, imp.normal() );
					imp.say( 0, Funcoes.replicate( "-", 135 ) );
					
				}
				
				imp.pulaLinha( 1, imp.normal() );
				imp.say( 2, rs.getString( "CODBAIRRO" ) != null ? rs.getString( "CODBAIRRO" ) : ""  );
				imp.say( 15, rs.getString( "NOMEBAIRRO" ) != null ? rs.getString( "NOMEBAIRRO" ) : ""  );
				imp.say( 80, rs.getString( "CODMUNIC" ) != null ? rs.getString( "CODMUNIC" ) : ""  );
				imp.say( 110, rs.getString( "NOMEMUNIC" ) != null ? rs.getString( "NOMEMUNIC" ) : "" );
				
				
			}

			imp.pulaLinha( 1, imp.normal() );
			imp.say( imp.pRow() + 0, 0, Funcoes.replicate( "=", 135 ) );
			
			imp.eject();
			imp.fechaGravacao();

			rs.close();
			ps.close();
			
			con.commit();
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de bairros!" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
		
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcMunic.setConexao( cn );
		lcPais.setConexao( cn );
		lcUF.setConexao( cn );
		
	}
	
}
