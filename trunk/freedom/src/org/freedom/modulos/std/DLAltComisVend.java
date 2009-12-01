/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLAltComisVenda.java <BR>
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
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLAltComisVend extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtPercComis = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private int iCodVenda = 0;

	public DLAltComisVend( Component cOrig, int iCodVenda, BigDecimal bigComis, DbConnection con ) {

		super( cOrig );

		setTitulo( "Altera��o de comiss�o" );
		setAtribos( 250, 120 );
		setConexao( con );
		this.iCodVenda = iCodVenda;

		setToFrameLayout();

		txtPercComis.setVlrBigDecimal( bigComis );

		adic( new JLabelPad( "% Comiss�o" ), 7, 0, 133, 20 );
		adic( txtPercComis, 7, 20, 140, 20 );
		adic( btExec, 160, 10, 30, 30 );

		btExec.addActionListener( this );
	}

	private void alterar() {

		if ( txtPercComis.getVlrDouble().doubleValue() < 0 ) {
			Funcoes.mensagemInforma( this, "Percentual inv�lido!" );
			return;
		}
		String sSQL = "UPDATE VDVENDA SET PERCMCOMISVENDA=? WHERE CODVENDA=? AND CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setBigDecimal( 1, txtPercComis.getVlrBigDecimal() );
			ps.setInt( 2, iCodVenda );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.executeUpdate();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao alterar a venda!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btExec ) {
			alterar();
		}
	}

	public BigDecimal getValore() {

		return txtPercComis.getVlrBigDecimal();
	}
}
