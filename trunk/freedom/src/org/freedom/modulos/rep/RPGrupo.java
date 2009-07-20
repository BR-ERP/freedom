/**
 * @version 02/2007 <BR>
 * @author Setpoint Inform�tica Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPGrupo.java <BR>
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
 * Tela de cadastro de grupo e sub-grupo de produtos.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class RPGrupo extends FFilho implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private final JPanelPad panelGrupos = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelRodape = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JPanelPad panelSair = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );

	private final JButton btGrupo = new JButton( "Grupo", Icone.novo( "btNovo.gif" ) );

	private final JButton btSubGrupo = new JButton( "Sub-Grupo", Icone.novo( "btNovo.gif" ) );

	private final Tabela tab = new Tabela();

	public RPGrupo() {

		super( false );
		setTitulo( "Cadastro de Grupos e Sub-Grupos" );
		setAtribos( 50, 50, 500, 350 );

		montaTela();

		tab.adicColuna( "C�d.grupo" );
		tab.adicColuna( "Descri��o do grupo" );
		tab.adicColuna( "Sigla" );

		tab.setTamColuna( 120, 0 );
		tab.setTamColuna( 280, 1 );
		tab.setTamColuna( 83, 2 );

		btSair.addActionListener( this );
		btGrupo.addActionListener( this );
		btSubGrupo.addActionListener( this );
		tab.addMouseListener( this );
		tab.addKeyListener( this );

	}

	private void montaTela() {

		getContentPane().setLayout( new BorderLayout() );

		panelGrupos.setBorder( BorderFactory.createEtchedBorder() );

		panelGrupos.add( new JScrollPane( tab ), BorderLayout.CENTER );

		btGrupo.setPreferredSize( new Dimension( 150, 30 ) );
		btSubGrupo.setPreferredSize( new Dimension( 150, 30 ) );
		btSair.setPreferredSize( new Dimension( 100, 30 ) );

		panelRodape.setPreferredSize( new Dimension( 100, 44 ) );
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );

		panelBotoes.add( btGrupo );
		panelBotoes.add( btSubGrupo );

		panelSair.add( btSair );

		panelRodape.add( panelBotoes, BorderLayout.WEST );
		panelRodape.add( panelSair, BorderLayout.EAST );

		getContentPane().add( panelGrupos, BorderLayout.CENTER );
		getContentPane().add( panelRodape, BorderLayout.SOUTH );
	}

	private void montaTab() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();

		try {

			tab.limpa();

			sql.append( "SELECT CODGRUP, DESCGRUP, SIGLAGRUP " );
			sql.append( "FROM RPGRUPO " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL =? " );
			sql.append( "ORDER BY CODGRUP" );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPGRUPO" ) );
			rs = ps.executeQuery();

			for ( int i = 0; rs.next(); i++ ) {
				tab.adicLinha();
				tab.setValor( rs.getString( "CODGRUP" ), i, 0 );
				tab.setValor( rs.getString( "DESCGRUP" ), i, 1 );
				tab.setValor( rs.getString( "SIGLAGRUP" ), i, 2 );
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela EQGRUPO! ! !\n" + e.getMessage() );
		}
	}

	private void gravaGrupo( String[] grupo ) {
		
		boolean update = grupo != null;

		DLGrupo dlgrupo = new DLGrupo( this );
		dlgrupo.setConexao( con );
		dlgrupo.setParams( grupo, null, DLGrupo.GRUPO );
		dlgrupo.setVisible( true );

		if ( dlgrupo.OK ) {
			
			PreparedStatement ps = null;
			String sql = null;

			try {
				
				grupo = dlgrupo.getGrupo();
				
				sql = update ? "UPDATE RPGRUPO SET DESCGRUP=?,SIGLAGRUP=? WHERE CODEMP=? AND CODFILIAL=? AND CODGRUP=?" : 
							   "INSERT INTO RPGRUPO ( CODEMP,CODFILIAL,CODGRUP,DESCGRUP,NIVELGRUP,SIGLAGRUP ) VALUES ( ?,?,?,?,?,? )";
	
				ps = con.prepareStatement( sql );
				
				if ( update ) {
					ps.setString( 1, grupo[ 1 ].trim() );
					ps.setString( 2, grupo[ 2 ].trim() );
					ps.setInt( 3, Aplicativo.iCodEmp );
					ps.setInt( 4, ListaCampos.getMasterFilial( "RPGRUPO" ) );
					ps.setString( 5, grupo[ 0 ].trim() );
				}
				else {
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "RPGRUPO" ) );
					ps.setString( 3, grupo[ 0 ].trim() + Funcoes.replicate( "0", 4 - grupo[ 0 ].trim().length() ) );
					ps.setString( 4, grupo[ 1 ].trim() );
					ps.setInt( 5, 1 );
					ps.setString( 6, grupo[ 2 ].trim() );	
				}

				if ( ps.executeUpdate() == 0 ) {

					Funcoes.mensagemInforma( this, "N�o foi poss�vel inserir na tabela de grupos." );
				}

				ps.close();

				con.commit();

				montaTab();

			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao gravar grupo\n" + e.getMessage() );
				e.printStackTrace();
			}

		}

		dlgrupo.dispose();
	}

	private void gravaSubGrupo( final String[] grupo, String[] subgrupo ) {
		
		boolean update = subgrupo != null;

		DLGrupo dlgrupo = new DLGrupo( this );
		dlgrupo.setConexao( con );
		dlgrupo.setParams( grupo, subgrupo, DLGrupo.SUB_GRUPO );
		dlgrupo.setVisible( true );

		if ( dlgrupo.OK ) {
			
			subgrupo = dlgrupo.getSubGrupo();
			
			PreparedStatement ps = null;
			String sql = null;

			try {
				
				sql = update ? "UPDATE RPGRUPO SET DESCGRUP=?,SIGLAGRUP=? WHERE CODEMP=? AND CODFILIAL=? AND CODGRUP=?" : 
							   "INSERT INTO RPGRUPO ( CODEMP,CODFILIAL,CODGRUP,DESCGRUP,NIVELGRUP,SIGLAGRUP,CODEMPSG,CODFILIALSG,CODSUBGRUP ) VALUES ( ?,?,?,?,?,?,?,?,? )";
	
				ps = con.prepareStatement( sql );
				
				if ( update ) {
					ps.setString( 1, subgrupo[ 1 ].trim() );
					ps.setString( 2, subgrupo[ 2 ].trim() );
					ps.setInt( 3, Aplicativo.iCodEmp );
					ps.setInt( 4, ListaCampos.getMasterFilial( "RPGRUPO" ) );
					ps.setString( 5, subgrupo[ 0 ].trim() );
				}
				else {
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "RPGRUPO" ) );
					ps.setString( 3, subgrupo[ 0 ].trim() );
					ps.setString( 4, subgrupo[ 1 ].trim() );
					ps.setInt( 5, ( subgrupo[ 0 ].trim().length() - 4 / 2 ) );
					ps.setString( 6, subgrupo[ 2 ].trim() );
					ps.setInt( 7, Aplicativo.iCodEmp );
					ps.setInt( 8, ListaCampos.getMasterFilial( "RPGRUPO" ) );
					ps.setString( 9, grupo[ 0 ].trim() );		
				}

				if ( ps.executeUpdate() == 0 ) {

					Funcoes.mensagemInforma( this, "N�o foi poss�vel inserir na tabela de grupos." );
				}

				ps.close();

				con.commit();

				montaTab();

			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao gravar sub-grupo\n" + e.getMessage() );
				e.printStackTrace();
			}

		}

		dlgrupo.dispose();
	}
	
	private void deletarGrupo() {
		
		PreparedStatement ps = null;
		String sql = null;

		try {
						
			sql = "DELETE FROM RPGRUPO WHERE CODEMP=? AND CODFILIAL=? AND CODGRUP=?";

			ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPGRUPO" ) );
			ps.setString( 3, ( (String) tab.getValor( tab.getLinhaSel(), 0 ) ).trim() );

			if ( ps.executeUpdate() == 0 ) {

				Funcoes.mensagemInforma( this, "N�o foi poss�vel excluir da tabela de grupos." );
			}

			ps.close();

			con.commit();

			montaTab();

		} catch ( Exception e ) {
			
			if ( e instanceof SQLException && ( (SQLException) e ).getErrorCode()  == 335544466) {
				Funcoes.mensagemErro( this ,"O registro possui v�nculos, n�o pode ser deletado!" );
			} 
			else {
				Funcoes.mensagemErro( this, "Erro ao excluir grupo\n" + e.getMessage() );
			}
			e.printStackTrace();
		}
	}

	private String[] getGrupo( final String codgrupo ) {
		
		String[] retorno = new String[ 3 ];
		try {
	
			String sql = "SELECT DESCGRUP, SIGLAGRUP FROM RPGRUPO WHERE CODEMP=? AND CODFILIAL=? AND CODGRUP=?";
			PreparedStatement ps = con.prepareStatement( sql );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPGRUPO" ) );
			ps.setString( 3, codgrupo );
			ResultSet rs = ps.executeQuery();
	
			if ( rs.next() ) {
	
				retorno[ 0 ] = codgrupo;
				retorno[ 1 ] = rs.getString( "DESCGRUP" );
				retorno[ 2 ] = rs.getString( "SIGLAGRUP" );
			}
	
			ps.close();
	
			con.commit();
	
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao gravar grupo\n" + e.getMessage() );
			e.printStackTrace();
		}
		
		return retorno;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btGrupo ) {
			if ( tab.getLinhaSel() > -1 ) {
				gravaGrupo( new String[] { 
						(String) tab.getValor( tab.getLinhaSel(), 0 ), 
						(String) tab.getValor( tab.getLinhaSel(), 1 ), 
						(String) tab.getValor( tab.getLinhaSel(), 2 ) }
					);	
			} 
			else {
				gravaGrupo( null );
			}
		}
		else if ( evt.getSource() == btSubGrupo ) {
			if ( tab.getLinhaSel() > -1 ) {
				gravaSubGrupo( new String[] { 
						(String) tab.getValor( tab.getLinhaSel(), 0 ), 
						(String) tab.getValor( tab.getLinhaSel(), 1 ), 
						(String) tab.getValor( tab.getLinhaSel(), 2 ) }
					, null );	
			} 
			else {
				Funcoes.mensagemInforma( this, "Selecione um grupo!" );
			}
		}
	}

	public void keyPressed( KeyEvent e ) {

		 if ( e.getKeyCode() == KeyEvent.VK_DELETE && e.getSource() == tab ) {
			 if ( tab.getLinhaSel() > -1 ) {
				 deletarGrupo();
			 }
			 else {
				 Funcoes.mensagemInforma( this, "Selecione um grupo para excluir!" ); 
			 }
		 }
	}

	public void keyTyped( KeyEvent kevt ) {

	}

	public void keyReleased( KeyEvent kevt ) { }

	public void mouseEntered( MouseEvent mevt ) { }

	public void mousePressed( MouseEvent mevt ) { }

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getSource() == tab && 
				mevt.getClickCount() == 2 && 
					tab.getLinhaSel() >= 0 ) {
			if ( ( (String) tab.getValor( tab.getLinhaSel(), 0 ) ).trim().length() == 4 ) {
				if ( tab.getLinhaSel() > -1 ) {

					gravaGrupo( new String[] { 
							(String) tab.getValor( tab.getLinhaSel(), 0 ), 
							(String) tab.getValor( tab.getLinhaSel(), 1 ), 
							(String) tab.getValor( tab.getLinhaSel(), 2 ) }
						);	
				}
			}
			else {
				
				gravaSubGrupo( getGrupo( (String) tab.getValor( tab.getLinhaSel(), 0 ) ),
						new String[] { 
						(String) tab.getValor( tab.getLinhaSel(), 0 ), 
						(String) tab.getValor( tab.getLinhaSel(), 1 ), 
						(String) tab.getValor( tab.getLinhaSel(), 2 ) } );
			}
		}

	}
	
	public void mouseExited( MouseEvent mevt ) {

	}

	public void mouseReleased( MouseEvent mevt ) {

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		montaTab();
	}
}
