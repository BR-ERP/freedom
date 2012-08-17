/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)DLRCont.java <BR>
 * 
 *         Este arquivo � parte do sistema Freedom-ERP, o Freedom-ERP � um software livre; voc� pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); <BR>
 *         na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o. <BR>
 *         Este programa � distribuido na esperan�a que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. <BR>
 *         Veja a Licen�a P�blica Geral GNU para maiores detalhes. <BR>
 *         Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU junto com este programa, se n�o, <BR>
 *         escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Coment�rios sobre a classe...
 * 
 */

package org.freedom.modulos.crm.view.dialog.report;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Vector;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.modulos.cfg.view.frame.crud.plain.FMunicipio;
import org.freedom.modulos.cfg.view.frame.crud.plain.FUF;
import org.freedom.modulos.crm.view.frame.crud.plain.FAtividade;
import org.freedom.modulos.crm.view.frame.crud.plain.FOrigContato;
import org.freedom.modulos.std.view.frame.crud.plain.FSetor;
import org.freedom.modulos.std.view.frame.crud.plain.FTipoCli;

public class DLRCont extends FFDialogo {
	
	public static enum VALORES{ ORDEM, OBSERVACAO, DE, A, FISICA, CIDADE, JURIDICA, MODO, SETOR, DESCSETOR, TIPOIMP, CODTIPOCLI, SIGLAUF, CODMUNIC, CODGRUP, CODATIV, CODORIGCONT, EDIFICIO   } 

	private static final long serialVersionUID = 1L;

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgModo = null;
	
	private JRadioGroup<?, ?> rgTipoImp = null;

	private JPanelPad pnlbSelec = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinSelec = new JPanelPad( 350, 70 );

	private JPanelPad pnlbPessoa = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinPessoa = new JPanelPad( 450, 40 );

	private JTextFieldPad txtCid = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JLabelPad lbSelec = new JLabelPad( " Selec�o:" );

	private JLabelPad lbDe = new JLabelPad( "De:" );

	private JLabelPad lbA = new JLabelPad( "�:" );

	private JTextFieldPad txtDe = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtA = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JLabelPad lbOrdem = new JLabelPad( "Ordenar por:" );
	
	private JLabelPad lbTipoImp = new JLabelPad( "Relat�rio:" );

	private JLabelPad lbPessoa = new JLabelPad( " Selecionar pessoas:" );

	private JLabelPad lbCid = new JLabelPad( "Cidade" );

	private JLabelPad lbModo = new JLabelPad( "Modo do relat�rio:" );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir Observa��es ?", "S", "N" );

	private JCheckBoxPad cbFis = new JCheckBoxPad( "F�sica", "S", "N" );

	private JCheckBoxPad cbJur = new JCheckBoxPad( "Jur�dica", "S", "N" );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	private Vector<String> vLabsModo = new Vector<String>();

	private Vector<String> vValsModo = new Vector<String>();
	
	private Vector<String> vLabsTipoImp = new Vector<String>();

	private Vector<String> vValsTipoImp = new Vector<String>();

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescSiglaUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodMunicipio = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescMunicipio = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodAtiv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAtiv = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodOrigCont = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescOrigCont = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtEdificio = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private ListaCampos lcSetor = new ListaCampos( this );
	
	private ListaCampos lcTipoCli = new ListaCampos( this );

	private ListaCampos lcMunic = new ListaCampos( this );

	private ListaCampos lcUF = new ListaCampos( this );
	
	private ListaCampos lcGrupFK = new ListaCampos( this );
	
	private ListaCampos lcAtivFK = new ListaCampos( this );
	
	private ListaCampos lcOrigCont = new ListaCampos( this );
	
	

	public DLRCont( Component cOrig, DbConnection cn ) {

		super( cOrig );
		setTitulo( "Relat�rio de Contatos" );
		setAtribos( 480, 690 );

		montaRadioGroup();
		montaListaCampos();
		montaTela();

		setConexao(cn);

	}
	
	public void montaListaCampos(){
		
		/** SETOR */
		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "C�digo", ListaCampos.DB_PK, false ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descri��o", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "VD" );
		lcSetor.setReadOnly( true );
		txtCodSetor.setTabelaExterna( lcSetor, FSetor.class.getCanonicalName() );
		txtCodSetor.setFK( true );
		txtCodSetor.setNomeCampo( "CodSetor" );
		
		/** TIPO DE CLIENTE */
		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "C�d.tp.cli", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descri��o do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli, FTipoCli.class.getCanonicalName() );
		txtCodTipoCli.setFK( true );
		txtCodTipoCli.setNomeCampo( "CodTipoCli" );
		
		/** MUNICIPIO */
		lcMunic.setUsaME( false );
		lcMunic.add( new GuardaCampo( txtCodMunicipio, "CodMunic", "C�d.Munic.", ListaCampos.DB_PK, false ) );
		lcMunic.add( new GuardaCampo( txtDescMunicipio, "NomeMunic", "Nome Munic.", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setReadOnly( true );
		txtCodMunicipio.setTabelaExterna( lcMunic, FMunicipio.class.getCanonicalName() );
		txtCodMunicipio.setFK( true );
		txtCodMunicipio.setNomeCampo( "CodMunic" );

		/** UF */
		lcUF.setUsaME( false );
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, false ) );
		lcUF.add( new GuardaCampo( txtDescSiglaUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		//lcUF.setWhereAdic( "CODPAIS = 76 ");
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setReadOnly( true );
		txtSiglaUF.setTabelaExterna( lcUF, FUF.class.getCanonicalName() );
		txtSiglaUF.setFK( true );
		txtSiglaUF.setNomeCampo( "SiglaUf" );
				
		/** INTERESSES */
		lcGrupFK.add( new GuardaCampo( txtCodGrup, "CodGrup", "C�d.grup.", ListaCampos.DB_PK, false ) );
		lcGrupFK.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descri��o do grupo", ListaCampos.DB_SI, false ) );
		lcGrupFK.montaSql( false, "GRUPO", "EQ" );
		lcGrupFK.setReadOnly( true );
		txtCodGrup.setFK( true );
		txtCodGrup.setTabelaExterna( lcGrupFK, null );
		txtCodGrup.setNomeCampo( "CodGrup" );
		
		/** ATIVIDADE */
		lcAtivFK.add( new GuardaCampo( txtCodAtiv, "CodAtiv", "C�d.ativ.", ListaCampos.DB_PK, false ) );
		lcAtivFK.add( new GuardaCampo( txtDescAtiv, "DescAtiv", "Descri��o da atividade", ListaCampos.DB_SI, false ) );
		lcAtivFK.montaSql( false, "ATIVIDADE", "TK" );
		lcAtivFK.setReadOnly( true );
		txtCodAtiv.setFK( true );
		txtCodAtiv.setTabelaExterna( lcAtivFK, FAtividade.class.getCanonicalName() );
		txtCodAtiv.setNomeCampo( "CodAtiv" );
		
		/** ORIGEM DO CONTATO */
		lcOrigCont.add( new GuardaCampo( txtCodOrigCont, "CodOrigCont", "C�d.origem", ListaCampos.DB_PK, false ) );
		lcOrigCont.add( new GuardaCampo( txtDescOrigCont, "DescOrigCont", "descri��o da origem", ListaCampos.DB_SI, false ) );
		lcOrigCont.montaSql( false, "ORIGCONT", "TK" );
		lcOrigCont.setReadOnly( true );
		txtCodOrigCont.setFK( true );
		txtCodOrigCont.setTabelaExterna( lcOrigCont, FOrigContato.class.getCanonicalName() );
		txtCodOrigCont.setNomeCampo( "CodOrigCont" );
		
	}
	
	public void montaTela(){
		
		pnlbSelec.add( lbSelec );
		adic( lbOrdem, 7, 5, 180, 20 );
		adic( rgOrdem, 7, 25, 220, 30 );
		adic( lbTipoImp, 230, 5, 180, 20 );
		adic( rgTipoImp, 230, 25, 220, 30 );
		adic( pnlbSelec, 10, 63, 80, 15 );
		pinSelec.adic( lbDe, 7, 10, 30, 20 );
		pinSelec.adic( txtDe, 40, 15, 380, 20 );
		pinSelec.adic( lbA, 7, 40, 30, 20 );
		pinSelec.adic( txtA, 40, 40, 380, 20 );
		adic( pinSelec, 7, 70, 433, 70 );
		pnlbPessoa.add( lbPessoa );
		adic( pnlbPessoa, 10, 148, 170, 15 );
		pinPessoa.adic( cbFis, 7, 10, 93, 20 );
		pinPessoa.adic( cbJur, 145, 10, 100, 20 );
		adic( pinPessoa, 7, 155, 290, 40 );
		adic( lbCid, 300, 155, 140, 20 );
		adic( txtCid, 300, 175, 140, 20 );
		adic( lbModo, 7, 200, 170, 20 );
		adic( rgModo, 7, 220, 433, 30 );
		adic( txtCodSetor, 7, 275, 80, 20, "C�d.setor" );
		adic( txtDescSetor, 90, 275, 350, 20, "Descri��o do setor" );
		adic( txtCodTipoCli, 7, 315, 80, 20, "C�d.tp.Cliente" );
		adic( txtDescTipoCli, 90, 315, 350, 20, "Descri��o do tipo de cliente" );
		adic( txtSiglaUF, 7, 355, 80, 20, "C�d.Sigla UF" );
		adic( txtDescSiglaUF, 90, 355, 350, 20, "Sigla UF" );
		adic( txtCodMunicipio, 7, 395, 80, 20, "C�d.Munic�pio" );
		adic( txtDescMunicipio, 90, 395, 350, 20, "Munic�pio" );
		adic( txtCodGrup, 7, 435, 80, 20, "C�d.Grupo" );
		adic( txtDescGrup, 90, 435, 350, 20, "Descri��o do Grupo" );
		adic( txtCodAtiv, 7, 475, 80, 20, "C�d.Atividade" );
		adic( txtDescAtiv, 90, 475, 350, 20, "Descri��o da Atividade" );
		adic( txtCodOrigCont, 7, 515, 80, 20, "C�d.Origem" );
		adic( txtDescOrigCont, 90, 515, 350, 20, "Descri��o da Origem" );
		adic( txtEdificio, 7, 555, 433, 20, "Edif�cio" );
		adic( cbObs, 7, 575, 220, 30 );
		
	}
	
	public void montaRadioGroup(){
		
		vLabs.addElement( "C�digo" );
		vLabs.addElement( "Nome" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );

		vLabsModo.addElement( "Resumido" );
		vLabsModo.addElement( "Completo" );
		vValsModo.addElement( "R" );
		vValsModo.addElement( "C" );
		rgModo = new JRadioGroup<String, String>( 1, 2, vLabsModo, vValsModo );
		rgModo.setVlrString( "R" );
		
		vLabsTipoImp.addElement( "Gr�fico" );
		vLabsTipoImp.addElement( "Texto" );
		vValsTipoImp.addElement( "G" );
		vValsTipoImp.addElement( "T" );
		rgTipoImp = new JRadioGroup<String, String>( 1, 2, vLabsTipoImp, vValsTipoImp );
		rgTipoImp.setVlrString( "G" );

		cbObs.setVlrString( "N" );
		cbFis.setVlrString( "N" );
		cbJur.setVlrString( "S" );
		
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 18 ];
		if ( rgOrdem.getVlrString().compareTo( "C" ) == 0 )
			sRetorno[ VALORES.ORDEM.ordinal() ] = "CODCTO";
		else if ( rgOrdem.getVlrString().compareTo( "D" ) == 0 )
			sRetorno[ VALORES.ORDEM.ordinal() ] = "NOMECTO";
		sRetorno[ VALORES.OBSERVACAO.ordinal() ] = cbObs.getVlrString();
		sRetorno[ VALORES.DE.ordinal() ] = txtDe.getText();
		sRetorno[ VALORES.A.ordinal() ] = txtA.getText();
		sRetorno[ VALORES.FISICA.ordinal() ] = cbFis.getVlrString();
		sRetorno[ VALORES.CIDADE.ordinal() ] = txtCid.getVlrString();
		sRetorno[ VALORES.JURIDICA.ordinal() ] = cbJur.getVlrString();
		sRetorno[ VALORES.MODO.ordinal() ] = rgModo.getVlrString();
		sRetorno[ VALORES.SETOR.ordinal() ] = txtCodSetor.getText();
		sRetorno[ VALORES.DESCSETOR.ordinal() ] = txtDescSetor.getText();
		sRetorno[ VALORES.TIPOIMP.ordinal() ] = rgTipoImp.getVlrString();
		sRetorno[ VALORES.CODTIPOCLI.ordinal() ] = txtCodTipoCli.getVlrInteger().toString();
		sRetorno[ VALORES.SIGLAUF.ordinal() ] = txtSiglaUF.getVlrString();
		sRetorno[ VALORES.CODMUNIC.ordinal() ] = txtCodMunicipio.getVlrString();
		sRetorno[ VALORES.CODGRUP.ordinal() ] = txtCodGrup.getVlrString();
		sRetorno[ VALORES.CODATIV.ordinal() ] = txtCodAtiv.getVlrInteger().toString();
		sRetorno[ VALORES.CODORIGCONT.ordinal() ] = txtCodOrigCont.getVlrInteger().toString();
		sRetorno[ VALORES.EDIFICIO.ordinal() ] = txtEdificio.getVlrString();
		
		return sRetorno;
	}
	
	public void setConexao(DbConnection cn){
		lcSetor.setConexao( cn );
		lcTipoCli.setConexao( cn );
		lcMunic.setConexao( cn );
		lcUF.setConexao( cn );
		lcGrupFK.setConexao( cn );
		lcAtivFK.setConexao( cn );
		lcOrigCont.setConexao( cn );
	
	}
}
