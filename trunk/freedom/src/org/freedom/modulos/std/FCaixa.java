/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FCaixa.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FCaixa extends FDados implements ActionListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private ListaCampos lcEst = new ListaCampos( this, "ET" );

	private JTextFieldPad txtCodCaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescCaixa = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtSeqIni = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtSeqMax = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldFK txtDescEst = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbECF = new JCheckBoxPad( "Emiss�o de Cupom Fiscal ?", "S", "N" );

	private JCheckBoxPad cbTEF = new JCheckBoxPad( "Possui Gerenciador Padr�o de TEF ?", "S", "N" );

	private JCheckBoxPad cbOrcamento = new JCheckBoxPad( "Habilitar venda somente com or�amento ?", "S", "N" );
	

	public FCaixa() {

		super( false );
		setTitulo( "Cadastro de caixa PDV" );
		setAtribos( 50, 50, 415, 270 );

		lcEst.add( new GuardaCampo( txtCodEst, "CodEst", "C�d.est.", ListaCampos.DB_PK, false ) );
		lcEst.add( new GuardaCampo( txtDescEst, "DescEst", "Descri��o da esta��o de trabalho", ListaCampos.DB_SI, false ) );
		lcEst.montaSql( false, "ESTACAO", "SG" );
		lcEst.setQueryCommit( false );
		lcEst.setReadOnly( true );
		txtCodEst.setTabelaExterna( lcEst );

		cbECF.setVlrString( "N" );
		cbTEF.setVlrString( "N" );
		cbOrcamento.setVlrString( "N" );
		
		adicCampo( txtCodCaixa, 7, 20, 80, 20, "CodCaixa", "C�d.caixa", ListaCampos.DB_PK, true );
		adicCampo( txtDescCaixa, 90, 20, 300, 20, "DescCaixa", "Descri��o do caixa", ListaCampos.DB_SI, true );
		adicCampo( txtCodEst, 7, 60, 80, 20, "CodEst", "C�d.est.", ListaCampos.DB_FK, txtDescEst, true );
		adicDescFK( txtDescEst, 90, 60, 300, 20, "DescEst", "Descri��o da esta��o de trabalho" );
		adicCampo( txtSeqIni, 7, 100, 190, 20, "SeqIni", "Seq. �nicial", ListaCampos.DB_SI, false );
		adicCampo( txtSeqMax, 200, 100, 190, 20, "SeqMax", "Seq. m�xima", ListaCampos.DB_SI, false );
		adicDB( cbECF, 10, 130, 380, 20, "ECFCaixa", "", true );
		adicDB( cbTEF, 10, 150, 380, 20, "TEFCaixa", "", true );
		adicDB( cbOrcamento, 10, 170, 380, 20, "ORCCaixa", "", true );
		setListaCampos( true, "CAIXA", "PV" );

		lcCampos.addCarregaListener( this );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcEst.setConexao( cn );
	}

	private boolean getTemVenda() {

		boolean retorno = false;

		try {

			PreparedStatement ps = con.prepareStatement( 
					"SELECT COUNT(SEQCAIXA) FROM PVSEQUENCIA WHERE CODEMP=? AND CODFILIAL=? AND CODCAIXA=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PVSEQUENCIA" ) );
			ps.setInt( 3, txtCodCaixa.getVlrInteger().intValue() );
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				retorno = ( rs.getInt( 1 ) > 0 );
			}

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro ao verificar sequencia.\n" + e.getMessage() );
			e.printStackTrace();
		} catch ( Exception e ) {
			e.printStackTrace();
		} 

		return retorno;

	}

	public void beforeCarrega( CarregaEvent e ) {

		txtSeqIni.setAtivo( true );
		txtSeqMax.setAtivo( true );
	}

	public void afterCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcCampos ) {
			if ( getTemVenda() )
				if ( txtSeqIni.getVlrInteger().intValue() > 0 && txtSeqMax.getVlrInteger().intValue() > 0 ) {
					txtSeqIni.setAtivo( false );
					txtSeqMax.setAtivo( false );
				}
		}
	}

}
