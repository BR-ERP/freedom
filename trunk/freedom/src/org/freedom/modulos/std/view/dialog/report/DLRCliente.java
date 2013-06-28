/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLRCliente.java <BR>
 * 
 *                     Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *                     na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *                     Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *                     Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *                     Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Coment�rios sobre a classe...
 */

package org.freedom.modulos.std.view.dialog.report;

import java.awt.Component;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
//import org.freedom.modulos.cfg.view.frame.crud.plain.FMunicipio;
//import org.freedom.modulos.cfg.view.frame.crud.plain.FUF;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class DLRCliente extends FFDialogo {

	private static final long serialVersionUID = 1L;

	
	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldPad txtCodMunic = new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );
	
	private JTextFieldFK txtDescMunic = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	
	private JTextFieldPad txtCid = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtBairro = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtDe = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtA = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtEstCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodClasCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtDescClasCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir Observa��es ?", "S", "N" );

	private JCheckBoxPad cbFis = new JCheckBoxPad( "F�sica", "S", "N" );

	private JCheckBoxPad cbJur = new JCheckBoxPad( "Jur�dica", "S", "N" );

	private JCheckBoxPad cbAtiv = new JCheckBoxPad( "Somente Ativos", "S", "N" );

	private JCheckBoxPad cbInativ = new JCheckBoxPad( "Somente Inativos", "S", "N" );

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgModo = null;

	private JRadioGroup<?, ?> rgEnd = null;

	private ListaCampos lcSetor = new ListaCampos( this );

	private ListaCampos lcTipoCli = new ListaCampos( this );

	private ListaCampos lcClasCli = new ListaCampos( this );

	private ListaCampos lcVendedor = new ListaCampos( this );
	
	private ListaCampos lcUF = new ListaCampos( this );

	private ListaCampos lcMunic = new ListaCampos( this );
	
	private Vector<String> vLabsOrdem = new Vector<String>();

	private Vector<String> vValsOrdem = new Vector<String>();

	private Vector<String> vLabsModo = new Vector<String>();

	private Vector<String> vValsModo = new Vector<String>();

	private Vector<String> vLabsEnd = new Vector<String>();

	private Vector<String> vValsEnd = new Vector<String>();
	
	/*
	 * sRetorno[ 1 ] = cbObs.getVlrString();
		sRetorno[ 2 ] = txtDe.getVlrString();
		sRetorno[ 3 ] = txtA.getVlrString();
		sRetorno[ 4 ] = cbFis.getVlrString();
		//sRetorno[ 5 ] = txtCid.getVlrString();
		sRetorno[ 5 ] = txtCodMunic.getVlrString();
		sRetorno[ 6 ] = cbJur.getVlrString();
		sRetorno[ 7 ] = rgModo.getVlrString();
		sRetorno[ 8 ] = txtCodSetor.getVlrString();
		sRetorno[ 9 ] = txtDescSetor.getVlrString();
		sRetorno[ 10 ] = txtCodTipoCli.getVlrString();
		sRetorno[ 11 ] = txtDescTipoCli.getVlrString();
		sRetorno[ 12 ] = rgOrdem.getVlrString();
		sRetorno[ 13 ] = txtCodVend.getVlrString();
		sRetorno[ 14 ] = txtNomeVend.getVlrString();
		sRetorno[ 15 ] = txtCodClasCli.getVlrString();
		sRetorno[ 16 ] = txtDescClasCli.getVlrString();
		sRetorno[ 17 ] = rgEnd.getVlrString();
		sRetorno[ 18 ] = txtBairro.getVlrString().trim();
		//sRetorno[ 19 ] = txtEstCli.getVlrString().trim();
		sRetorno[ 19 ] = txtSiglaUF.getVlrString().trim();
		sRetorno[ 20 ] = cbAtiv.getVlrString().trim();
		sRetorno[ 21 ] = cbInativ.getVlrString().trim();
	 * */
	public enum RET_DLRCLIENTE {ORDEM, OBS, DE, A, FIS, CODMUNIC, JUR, MODO, CODSETOR, DESCSETOR, CODTIPOCLI, DESCTIPOCLI
		, ORDEM2, CODVEND, NOMEVEND, CODCLASCLI, DESCCLASCLI, END, BAIRRO, SIGLAUF, ATIV, INATIV };

	public DLRCliente ( Component cOrig, DbConnection cn ) {

		super( cOrig );
		setTitulo( "Relat�rio de Clientes" );
		//setAtribos( 465, 540 );
		setAtribos( 465, 600 );
		setLocationRelativeTo( this );

		vLabsOrdem.addElement( "C�digo" );
		vLabsOrdem.addElement( "Raz�o" );
		vLabsOrdem.addElement( "Cidade" );
		vValsOrdem.addElement( "C" );
		vValsOrdem.addElement( "R" );
		vValsOrdem.addElement( "I" );
		rgOrdem = new JRadioGroup<String, String>( 1, 3, vLabsOrdem, vValsOrdem );
		rgOrdem.setVlrString( "R" );

		vLabsModo.addElement( "Resumido 1" );
		vLabsModo.addElement( "Resumido 2" );
		vLabsModo.addElement( "Resumido 3" );
		vLabsModo.addElement( "Completo" );
		vLabsModo.addElement( "Alinhar  Filial" );
		vValsModo.addElement( "1" );
		vValsModo.addElement( "2" );
		vValsModo.addElement( "3" );
		vValsModo.addElement( "C" );
		vValsModo.addElement( "A" );

		rgModo = new JRadioGroup<String, String>( 2, 3, vLabsModo, vValsModo );
		rgModo.setVlrString( "R" );

		vLabsEnd.addElement( "Cadast." );
		vLabsEnd.addElement( "Ent." );
		vLabsEnd.addElement( "Cob." );
		vValsEnd.addElement( "A" );
		vValsEnd.addElement( "E" );
		vValsEnd.addElement( "C" );
		rgEnd = new JRadioGroup<String, String>( 1, 3, vLabsEnd, vValsEnd );
		rgEnd.setVlrString( "A" );

		cbObs.setVlrString( "N" );
		cbAtiv.setVlrString( "S" );
		cbInativ.setVlrString( "N" );

		cbFis.setVlrString( "S" );
		cbJur.setVlrString( "S" );
		
		
		lcUF.setUsaME( false );
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, false ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setReadOnly( true );
		
		txtSiglaUF.setTabelaExterna( lcUF, null );
		txtSiglaUF.setFK( true );
		txtSiglaUF.setNomeCampo( "SiglaUf" );
		
		
		lcMunic.setUsaME( false );
		lcMunic.add( new GuardaCampo( txtCodMunic, "CodMunic", "C�d.Munic.", ListaCampos.DB_PK, false ) );
		lcMunic.add( new GuardaCampo( txtDescMunic, "NomeMunic", "Nome Munic.", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setReadOnly( true );
		
		txtCodMunic.setTabelaExterna( lcMunic, null );
		txtCodMunic.setFK( true );
		txtCodMunic.setNomeCampo( "CodMunic" );
		
		
		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "C�d.setor", ListaCampos.DB_PK, false ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descri��o do setor", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "VD" );
		lcSetor.setReadOnly( true );
		
		txtCodSetor.setTabelaExterna( lcSetor, null );
		txtCodSetor.setFK( true );
		txtCodSetor.setNomeCampo( "CodSetor" );

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "C�d.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli, null );
		txtCodTipoCli.setFK( true );
		txtCodTipoCli.setNomeCampo( "CodTipoCli" );

		lcClasCli.add( new GuardaCampo( txtCodClasCli, "CodClasCli", "C�d.cl.cli.", ListaCampos.DB_PK, false ) );
		lcClasCli.add( new GuardaCampo( txtDescClasCli, "DescClasCli", "Descri��o da classifica��o do cliente", ListaCampos.DB_SI, false ) );
		lcClasCli.montaSql( false, "CLASCLI", "VD" );
		lcClasCli.setReadOnly( true );
		txtCodClasCli.setTabelaExterna( lcClasCli, null );
		txtCodClasCli.setFK( true );
		txtCodClasCli.setNomeCampo( "CodClasCli" );

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "C�d.comiss.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor, null );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CodVend" );

		JLabelPad lbOrdem = new JLabelPad( "   Ordenar por:" );
		lbOrdem.setOpaque( true );
		JLabelPad lbBordaOrdem = new JLabelPad();
		lbBordaOrdem.setBorder( BorderFactory.createEtchedBorder() );
		adic( lbOrdem, 15, 5, 100, 20 );
		adic( lbBordaOrdem, 7, 15, 433, 60 );
		adic( rgOrdem, 15, 30, 240, 30 );

		adic( cbObs, 270, 16, 165, 20 );
		adic( cbAtiv, 270, 34, 160, 20 );
		adic( cbInativ, 270, 52, 160, 20 );

		JLabelPad lbSelecao = new JLabelPad( "  C�d. Cliente:" );
		lbSelecao.setOpaque( true );
		JLabelPad lbBordaSelecao = new JLabelPad();
		lbBordaSelecao.setBorder( BorderFactory.createEtchedBorder() );
		adic( lbSelecao, 285, 75, 85, 20 );
		adic( lbBordaSelecao, 277, 85, 130, 70 );
		adic( new JLabelPad( "De:", SwingConstants.RIGHT ), 282, 100, 30, 20 );
		adic( txtDe, 317, 100, 70, 20 );
		adic( new JLabelPad( "�:", SwingConstants.RIGHT ), 282, 125, 30, 20 );
		adic( txtA, 317, 125, 70, 20 );
		
		adic( new JLabelPad( "Sigla UF" ), 7, 75, 70, 20 );
		adic( txtSiglaUF, 7, 95, 50, 20 );
		
		adic( new JLabelPad( "Desc. UF" ), 67, 75, 140, 20 );
		adic( txtNomeUF, 67, 95, 200, 20 );
		
		adic( new JLabelPad( "C�d. Mun." ), 7, 115, 85, 20 );
		adic( txtCodMunic, 7, 135, 50, 20 );
		
		adic( new JLabelPad( "Desc. Munic�pio" ), 67, 115, 140, 20 );
		adic( txtDescMunic, 67, 135, 200, 20 );
		
		adic( new JLabelPad( "Bairro" ), 7, 155, 140, 20 );
		adic( txtBairro, 7, 175, 163, 20 );
		
		/*adic( new JLabelPad( "UF" ), 277, 75, 30, 20 );
		adic( txtEstCli, 277, 95, 30, 20 );

		adic( new JLabelPad( "Cidade" ), 310, 75, 130, 20 );
		adic( txtCid, 310, 95, 130, 20 );
		
		adic( new JLabelPad( "Bairro" ), 277, 115, 140, 20 );
		adic( txtBairro, 277, 135, 163, 20 );*/

		JLabelPad lbEnd = new JLabelPad( "Endere�o :" );
		lbSelecao.setOpaque( true );
		adic( lbEnd, 7, 195, 85, 20 );
		adic( rgEnd, 7, 215, 259, 30 );

		JLabelPad lbBordaPessoa = new JLabelPad();
		lbBordaPessoa.setBorder( BorderFactory.createEtchedBorder() );
		adic( new JLabelPad( "Pessoa :" ), 270, 195, 75, 20 );
		adic( lbBordaPessoa, 270, 215, 170, 30 );
		adic( cbFis, 287, 220, 70, 20 );
		adic( cbJur, 357, 220, 80, 20 );

		adic( new JLabelPad( "Modo do relat�rio:" ), 7, 245, 170, 20 );
		adic( rgModo, 7, 265, 433, 50 );

		adic( new JLabelPad( "C�d.setor" ), 7, 320, 80, 20 );
		adic( txtCodSetor, 7, 340, 80, 20 );
		adic( new JLabelPad( "Descri��o do setor" ), 90, 320, 350, 20 );
		adic( txtDescSetor, 90, 340, 350, 20 );
		adic( new JLabelPad( "C�d.comiss." ), 7, 360, 80, 20 );
		adic( txtCodVend, 7, 380, 80, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 90, 360, 350, 20 );
		adic( txtNomeVend, 90, 380, 350, 20 );
		adic( new JLabelPad( "C�d.tp.cli." ), 7, 400, 80, 20 );
		adic( txtCodTipoCli, 7, 420, 80, 20 );
		adic( new JLabelPad( "Descri��o do tipo de cliente" ), 90, 400, 350, 20 );
		adic( txtDescTipoCli, 90, 420, 350, 20 );
		adic( new JLabelPad( "C�d.cl.cli." ), 7, 440, 80, 20 );
		adic( txtCodClasCli, 7, 460, 80, 20 );
		adic( new JLabelPad( "Descri��o da classifica��o do cliente" ), 90, 440, 350, 20 );
		adic( txtDescClasCli, 90, 460, 350, 20 );
		
		lcUF.setConexao( cn );
		lcMunic.setConexao( cn );
		lcSetor.setConexao( cn );
		lcTipoCli.setConexao( cn );
		lcClasCli.setConexao( cn );
		lcVendedor.setConexao( cn );

	}

	public String[] getValores() {

		String[] sRetorno = new String[ RET_DLRCLIENTE.values().length ];

		if ( rgOrdem.getVlrString().equals( "C" ) ) {
			sRetorno[ RET_DLRCLIENTE.ORDEM.ordinal() ] = "C1.CODCLI";
		}
		else if ( rgOrdem.getVlrString().equals( "R" ) ) {
			sRetorno[ RET_DLRCLIENTE.ORDEM.ordinal() ] = "C1.RAZCLI";
		}
		else if ( rgOrdem.getVlrString().equals( "I" ) ) {
			if ( "A".equals( rgEnd.getVlrString() ) ) {
				sRetorno[ RET_DLRCLIENTE.ORDEM.ordinal() ] = "C1.CIDCLI, C1.RAZCLI";
			}
			else if ( "E".equals( rgEnd.getVlrString() ) ) {
				sRetorno[ RET_DLRCLIENTE.ORDEM.ordinal() ] = "C1.CIDENT, C1.RAZCLI";
			}
			else if ( "C".equals( rgEnd.getVlrString() ) ) {
				sRetorno[ RET_DLRCLIENTE.ORDEM.ordinal() ] = "C1.CIDCOB, C1.RAZCLI";
			}
		}
		
		sRetorno[ RET_DLRCLIENTE.OBS.ordinal() ] = cbObs.getVlrString(); // 1
		sRetorno[ RET_DLRCLIENTE.DE.ordinal() ] = txtDe.getVlrString(); // 2
		sRetorno[ RET_DLRCLIENTE.A.ordinal() ] = txtA.getVlrString(); // 3
		sRetorno[ RET_DLRCLIENTE.FIS.ordinal() ] = cbFis.getVlrString(); // 4 
		sRetorno[ RET_DLRCLIENTE.CODMUNIC.ordinal() ] = txtCodMunic.getVlrString(); // 5
		sRetorno[ RET_DLRCLIENTE.JUR.ordinal() ] = cbJur.getVlrString(); // 6
		sRetorno[ RET_DLRCLIENTE.MODO.ordinal() ] = rgModo.getVlrString(); // 7
		sRetorno[ RET_DLRCLIENTE.CODSETOR.ordinal() ] = txtCodSetor.getVlrString(); // 8
		sRetorno[ RET_DLRCLIENTE.DESCSETOR.ordinal() ] = txtDescSetor.getVlrString(); // 9
		sRetorno[ RET_DLRCLIENTE.CODTIPOCLI.ordinal() ] = txtCodTipoCli.getVlrString(); // 10
		sRetorno[ RET_DLRCLIENTE.DESCTIPOCLI.ordinal() ] = txtDescTipoCli.getVlrString(); // 11
		sRetorno[ RET_DLRCLIENTE.ORDEM2.ordinal() ] = rgOrdem.getVlrString(); // 12
		sRetorno[ RET_DLRCLIENTE.CODVEND.ordinal() ] = txtCodVend.getVlrString(); // 13
		sRetorno[ RET_DLRCLIENTE.NOMEVEND.ordinal() ] = txtNomeVend.getVlrString(); // 14
		sRetorno[ RET_DLRCLIENTE.CODCLASCLI.ordinal() ] = txtCodClasCli.getVlrString(); // 15
		sRetorno[ RET_DLRCLIENTE.DESCCLASCLI.ordinal() ] = txtDescClasCli.getVlrString(); // 16
		sRetorno[ RET_DLRCLIENTE.END.ordinal() ] = rgEnd.getVlrString(); // 17
		sRetorno[ RET_DLRCLIENTE.BAIRRO.ordinal() ] = txtBairro.getVlrString().trim(); // 18
		sRetorno[ RET_DLRCLIENTE.SIGLAUF.ordinal() ] = txtSiglaUF.getVlrString().trim(); // 19
		sRetorno[ RET_DLRCLIENTE.ATIV.ordinal() ] = cbAtiv.getVlrString().trim(); // 20
		sRetorno[ RET_DLRCLIENTE.INATIV.ordinal() ] = cbInativ.getVlrString().trim(); // 21

		return sRetorno;

	}
}
