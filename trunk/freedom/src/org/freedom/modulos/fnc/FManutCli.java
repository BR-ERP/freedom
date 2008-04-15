/**
 * @version 28/02/2007 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FCodRetorno.java <BR>
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
 * Tela de manuten��o dos dados dos clientes referentes ao esquema Febraban.
 * 
 */
package org.freedom.modulos.fnc;

import java.sql.Connection;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Boleto;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class FManutCli extends FDados implements RadioGroupListener, PostListener, CarregaListener {

	private static final long serialVersionUID = 1L;
	
	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodEmpPF = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private final JTextFieldPad txtCodFilialPF = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtAgencia = new JTextFieldPad( JTextFieldPad.TP_STRING, 9, 0 );

	private final JTextFieldPad txtIdentificacao = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtTipoFebraban = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JRadioGroup<?, ?> rgTipoFebraban;

	private final JRadioGroup<?, ?> rgSubTipoFebraban;

	private final ListaCampos lcBanco = new ListaCampos( this, "BO" );
	
	private final ListaCampos lcCliente = new ListaCampos( this, "" );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	private Vector<String> vLabs1 = new Vector<String>();

	private Vector<String> vVals1 = new Vector<String>();

	public FManutCli() {

		setTitulo( "C�digos de retorno" );
		setAtribos( 200, 60, 367, 290 );
		
		
		vLabs.add( "SIACC" );
		vLabs.add( "CNAB" );
		vVals.add( "01" );
		vVals.add( "02" );
		rgTipoFebraban = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );

		vLabs1.add( "D�bito em folha" );
		vLabs1.add( "D�bito em conta" );
		vVals1.add( "01" );
		vVals1.add( "02" );
		rgSubTipoFebraban = new JRadioGroup<String, String>( 2, 1, vLabs1, vVals1 );
		rgSubTipoFebraban.setVlrString( "02" );
		rgTipoFebraban.setVlrString( "01" );
		txtConta.setEnabled( false );
		
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "C�d.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "C�d.cli.", ListaCampos.DB_PK, true ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Raz�o social do cliente", ListaCampos.DB_SI, false ) );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		lcCliente.setQueryCommit( false );
		lcCliente.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCliente );

		montaTela();

		rgTipoFebraban.addRadioGroupListener( this );
		
		lcCampos.addPostListener( this );
		
		//txtIdentificacao.setAtivo( false );
	}

	private void montaTela() {
		
		adic( new JLabel( "Tipo:"), 7, 0, 333, 20  );
		adic( rgTipoFebraban, 7, 20, 333, 30  );	
		
		txtTipoFebraban.setVlrString( "01" );		
		lcCampos.add( new GuardaCampo( txtTipoFebraban, "TipoFebraban", "Tipo", ListaCampos.DB_PK, true ) );
		
		adicCampo( txtCodCli, 7, 70, 90, 20, "CodCli", "C�d.cli.", ListaCampos.DB_PF, txtRazCli, true );
		adicDescFK( txtRazCli, 100, 70, 240, 20, "RazCli", "Raz�o social do cliente" );
		adicCampo( txtCodBanco, 7, 110, 90, 20, "CodBanco", "C�d.banco", ListaCampos.DB_PF, txtNomeBanco, true );
		adicDescFK( txtNomeBanco, 100, 110, 240, 20, "NomeBanco", "Nome do banco" );
		adicCampo( txtConta, 7, 150, 90, 20, "NumContaCli", "Conta", ListaCampos.DB_SI, false );
		adicCampo( txtAgencia, 100, 150, 80, 20, "AgenciaCli", "Ag�ncia", ListaCampos.DB_SI, false );
		adicCampo( txtIdentificacao, 7, 190, 173, 20, "IdentCli", "Identifica��o", ListaCampos.DB_SI, false );
		adicDB( rgSubTipoFebraban, 190, 150, 150, 60, "STipoFebraban", "", false );
		adicCampoInvisivel( txtCodEmpPF, "CodEmpPF", "C�d.emp.pf.", ListaCampos.DB_SI, false );
		adicCampoInvisivel( txtCodFilialPF, "CodFilialPF", "C�d.filial.pf.", ListaCampos.DB_SI, false );

		setListaCampos( false, "FBNCLI", "FN" );
	}
	
	private boolean getIdentificacao() {
		
		boolean retorno = true;
		
		try {
			
			String agencia = Funcoes.strZero( txtAgencia.getVlrString().trim().replaceAll( "-", "" ), 5 );
			String conta = Funcoes.strZero( txtConta.getVlrString().trim().replaceAll( "-", "" ), 10 );
			String digito = Boleto.digVerif( agencia + conta, 11 );
			String identificacao = agencia + conta + digito;
			
			txtIdentificacao.setVlrString( identificacao );
		}
		catch ( Exception e ) {
			retorno = false;
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar identifica��o.\n" + e.getMessage(), true, con, e );
		}
		
		return retorno;
	}

	@ Override
	public void beforePost( PostEvent e ) {
		
		//if ( ! getIdentificacao() ) {
			//e.cancela();
		//}

		super.beforePost( e );
		
		txtCodEmpPF.setVlrInteger( Aplicativo.iCodEmp );
		txtCodFilialPF.setVlrInteger( ListaCampos.getMasterFilial( "SGPREFERE6" ) );
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if ( evt.getIndice() >= 0 ) {
			if(rgTipoFebraban.getVlrString().equals( "01" )) {
				txtConta.setEnabled( false );
			}
			else {
				txtConta.setEnabled( true );
			}
			lcCampos.limpaCampos( true ); 
			txtTipoFebraban.setVlrString( (String) vVals.elementAt( evt.getIndice() ) );
		} 
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
		lcCliente.setConexao( cn );
	}
	
	public void afterCarrega( CarregaEvent e ) { 
		if ( e.getListaCampos() == lcCampos ) {
			if(rgTipoFebraban.getVlrString().equals( "01" )) {
				txtConta.setEnabled( false );				
			}
			else {
				txtConta.setEnabled( true );
			}
		}
	}

	public void beforeCarrega( CarregaEvent e ) {

	}
	
	
	

}
