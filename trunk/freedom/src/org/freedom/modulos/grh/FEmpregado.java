/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FEmpregado.java <BR>
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
 * Tela de cadastro de empregados.
 * 
 */

package org.freedom.modulos.grh;

import java.sql.Connection;
import java.util.Vector;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.PainelImagem;
import org.freedom.telas.FDados;

public class FEmpregado extends FDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCod = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodFuncao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodTurno = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodDepto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldFK txtDescFuncao = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtDescTurno = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtDescDepto = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDesc = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtApelido = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtEndEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtDataNasc = new JTextFieldPad( JTextFieldPad.TP_DATE, 50, 0 );
	
	private final JTextFieldFK txtIdade = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtNumEmpr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );
	
	private final JTextFieldPad txtCidEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtBairEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtCepEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private final JTextFieldPad txtFoneEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private final JTextFieldPad txtDtAdmissao = new JTextFieldPad( JTextFieldPad.TP_DATE, 12, 0 );
	
	private final JTextFieldPad txtCtps = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtUfCtps = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtCertifExercito = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtPisPasep = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private final JTextFieldPad txtRg = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private final JTextFieldPad txtOrgEmiss = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private final JTextFieldPad txtUfExpedRg = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtDataExpRg = new JTextFieldPad( JTextFieldPad.TP_DATE, 40, 0 );
	
	private final JTextFieldPad txtCpfEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );
	
	private final JTextFieldPad txtTituloEleit = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldPad txtZonaEleit = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 15, 0 );
	
	private final JTextFieldPad txtSecaoEleit = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 15, 0 );
	
	private final JTextFieldPad txtCnh = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final JTextFieldPad txtNomeMae = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private final JTextFieldPad txtNomePai = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private final JTextFieldPad txtComplemento = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtUfEmpregado = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtDtDemissao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private final JTextFieldPad txtEmail = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private final JTextFieldPad txtDddEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private final JTextFieldPad txtFoneEmpr2 = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private final JTextFieldPad txtCelEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private final JTextFieldPad txtSalarioIni = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );
	
	private JComboBoxPad cbStatus = null;
	
	private JComboBoxPad cbSexo = null;
	
	private PainelImagem fotoEmpr = new PainelImagem( 65000 );
	
	private final ListaCampos lcFuncao = new ListaCampos( this, "FO" );

	private final ListaCampos lcTurno = new ListaCampos( this, "TO" );

	private final ListaCampos lcDepto = new ListaCampos( this, "DP" );
	

	public FEmpregado() {

		super();
		setTitulo( "Cadastro de Empregados" );
		setAtribos( 50, 50, 590, 800 );

		montaListaCampos();
		
		montaTela();
		
		setImprimir( false );
	}
	
	private void montaListaCampos() {

		lcFuncao.add( new GuardaCampo( txtCodFuncao, "CodFunc", "C�d.Func.", ListaCampos.DB_PK, true ) );
		lcFuncao.add( new GuardaCampo( txtDescFuncao, "DescFunc", "Descri��o da fun��o", ListaCampos.DB_SI, false ) );
		lcFuncao.montaSql( false, "FUNCAO", "RH" );
		lcFuncao.setQueryCommit( false );
		lcFuncao.setReadOnly( true );
		txtCodFuncao.setTabelaExterna( lcFuncao );

		lcTurno.add( new GuardaCampo( txtCodTurno, "CodTurno", "C�d. turno", ListaCampos.DB_PK, true ) );
		lcTurno.add( new GuardaCampo( txtDescTurno, "DescTurno", "Descri��o do turno", ListaCampos.DB_SI, false ) );
		lcTurno.montaSql( false, "TURNO", "RH" );
		lcTurno.setQueryCommit( false );
		lcTurno.setReadOnly( true );
		txtCodTurno.setTabelaExterna( lcTurno );

		lcDepto.add( new GuardaCampo( txtCodDepto, "CodDep", "C�d.Depto.", ListaCampos.DB_PK, true ) );
		lcDepto.add( new GuardaCampo( txtDescDepto, "DescDep", "Descri��o do departamento", ListaCampos.DB_SI, false ) );
		lcDepto.montaSql( false, "DEPTO", "RH" );
		lcDepto.setQueryCommit( false );
		lcDepto.setReadOnly( true );
		txtCodDepto.setTabelaExterna( lcDepto );
	}

	private void montaTela() {
		
		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "AD" );
		vVals.addElement( "DE" );
		vVals.addElement( "EF" );
		vVals.addElement( "LM" );
		vVals.addElement( "AI" );
		vVals.addElement( "AP" );		
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "Admitido" );
		vLabs.addElement( "Demitido" );
		vLabs.addElement( "Em f�rias" );
		vLabs.addElement( "Licen�a maternidade" );
		vLabs.addElement( "Afastamento INSS" );
		vLabs.addElement( "Aposentado" );
		Vector<String> vLabs2 = new Vector<String>();
		vLabs2.addElement( "Masculino" );
		vLabs2.addElement( "Feminino" );
		Vector<String> vVals2 = new Vector<String>();
		vVals2.addElement( "M" );
		vVals2.addElement( "F" );
		
		
		cbStatus = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0 );
		cbSexo = new JComboBoxPad( vLabs2, vVals2, JComboBoxPad.TP_STRING, 2, 0 );
		
		adicCampo( txtCod, 7, 20, 80, 20, "MatEmpr", "Matricula", ListaCampos.DB_PK, true );
		adicCampo( txtDesc, 90, 20, 280, 20, "NomeEmpr", "Nome do empregado", ListaCampos.DB_SI, true );
		adicDB( fotoEmpr, 380, 20, 100, 133, "FotoEmpr", "Foto ( 3 x 4 )", false );
		adicCampo( txtApelido, 7, 60, 100, 20, "ApelidoEmpr", "Apelido", ListaCampos.DB_SI, true );
		adicDB( cbSexo, 110, 60, 120, 20, "SexoEmpr", "Sexo", false );
		adicCampo( txtDataNasc, 235, 60, 85, 20, "DtNascEmpr", "Data de nasc.", ListaCampos.DB_SI, false );
		adic( new JLabelPad( "Idade"), 325, 40, 45, 20 );
		adic( txtIdade, 325, 60, 45, 20 );
		adicCampo( txtCtps, 7, 100, 120, 20, "CtpsEmpr", "Ctps", ListaCampos.DB_SI, false );
		adicCampo( txtSerie, 130, 100, 80, 20, "SerieCtpsEmpr", "S�rie", ListaCampos.DB_SI, false );
		adicCampo( txtUfCtps, 213, 100, 30, 20, "UfCtpsEmpr", "Uf", ListaCampos.DB_SI, false);
		adicCampo( txtCertifExercito, 245, 100, 125, 20, "CertReservEmpr", "Cert. ex�rcito", ListaCampos.DB_SI, false );
		adicCampo( txtPisPasep, 7, 140, 110, 20, "PisPasepEmpr", "PIS/PASEP", ListaCampos.DB_SI, false );
		adicCampo( txtRg, 120, 140, 85, 20, "RgEmpr", "Rg", ListaCampos.DB_SI, false );
		adicCampo( txtOrgEmiss, 208, 140, 62, 20, "OrgExpRhEmpr", "Org.Emis.", ListaCampos.DB_SI, false );
		adicCampo( txtUfExpedRg, 272, 140, 30, 20, "UfRgEmpr", "Uf", ListaCampos.DB_SI, false );
		adicCampo( txtDataExpRg, 305, 140, 65, 20, "DtExpRgEmpr", "Data", ListaCampos.DB_SI, false );
		adicCampo( txtCpfEmpr, 7, 180, 85, 20, "CpfEmpr", "Cpf", ListaCampos.DB_SI, false );
		adicCampo( txtTituloEleit, 95, 180, 110, 20, "TitEleitEmpr", "Titulo Eleitoral", ListaCampos.DB_SI, false );
		adicCampo( txtZonaEleit, 208, 180, 60, 20, "ZonaEleitEmpr", "Zona", ListaCampos.DB_SI, false );
		adicCampo( txtSecaoEleit, 270, 180, 60, 20, "SecaoEleitEmpr", "Se��o", ListaCampos.DB_SI, false );
		adicCampo( txtCnh, 335, 180, 150, 20, "CnhEmpr", "CNH", ListaCampos.DB_SI, false );
		adicCampo( txtNomeMae, 7, 220, 230, 20, "MaeEmpr", "Nome da m�e", ListaCampos.DB_SI, false );
		adicCampo( txtNomePai, 245, 220, 240, 20, "PaiEmpr", "Nome do pai", ListaCampos.DB_SI, false );
        adicCampo( txtEndEmpr, 7, 260, 280, 20, "EndEmpr", "Endere�o", ListaCampos.DB_SI, false );
		adicCampo( txtNumEmpr, 290, 260, 80, 20, "NumEmpr", "N�mero", ListaCampos.DB_SI, false );
		adicCampo( txtComplemento, 375, 260, 110, 20, "ComplEndEmpr", "Complemento", ListaCampos.DB_SI, false );
		adicCampo( txtBairEmpr, 7, 300, 180, 20, "BairEmpr", "Bairro", ListaCampos.DB_SI, false );
        adicCampo( txtCidEmpr, 190, 300, 160, 20, "CidEmpr", "Cidade", ListaCampos.DB_SI, false );
        adicCampo( txtUfEmpregado, 353, 300, 30, 20, "UfEmpr", "Uf", ListaCampos.DB_SI, false);
        adicCampo( txtCepEmpr, 386, 300, 100, 20, "CepEmpr", "Cep", ListaCampos.DB_SI, false );	
        adicCampo( txtDtDemissao , 7, 340, 90, 20, "DtDemissaoEmpr", "Data Demiss�o", ListaCampos.DB_SI, false );
        adicDB( cbStatus, 100, 340, 100, 18, "StatusEmpr", "Status", false );
        adicCampo( txtEmail, 205, 340, 283, 20, "EmailEmpr", "E-mail", ListaCampos.DB_SI, false );
        adicCampo( txtDddEmpr, 7, 380, 50, 20, "DddEmpr", "DDD", ListaCampos.DB_SI, false );
        adicCampo( txtFoneEmpr, 60, 380, 130, 20, "FoneEmpr", "Telefone 1", ListaCampos.DB_SI, false );
        adicCampo( txtFoneEmpr2, 195, 380, 130, 20, "Fone2Empr", "Telefone 2", ListaCampos.DB_SI, false );
        adicCampo( txtCelEmpr, 330, 380, 160, 20, "CelEmpr", "Celular", ListaCampos.DB_SI, false );
        adicCampo( txtDtAdmissao , 7, 420, 90, 20, "DtAdmissao", "Data Admiss�o", ListaCampos.DB_SI, false );
        adicCampo( txtSalarioIni, 100, 420, 90, 20, "", "Sal�rio inicial", ListaCampos.DB_SI, false );
        adicCampo( txtCodTurno, 193, 420, 70, 20, "CodTurno", "C�d. Turno", ListaCampos.DB_FK, true );
		adicDescFK( txtDescTurno, 270, 420, 220, 20, "DescTurno", "Descri��o do turno" );		
		adicCampo( txtCodFuncao, 7, 460, 60, 20, "CodFunc", "C�d.Func.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescFuncao, 71, 460, 420, 20, "DescFunc", "Descri��o da fun��o" );
		adicCampo( txtCodDepto, 7, 500, 60, 20, "CodDep", "C�d.Depto.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescDepto, 71, 500, 420, 20, "DescDepto", "Descri��o do departamento" );
		
		txtCepEmpr.setMascara( JTextFieldPad.MC_CEP );
		txtFoneEmpr.setMascara( JTextFieldPad.MC_FONE );
	
		setListaCampos( true, "EMPREGADO", "RH" );
		lcCampos.setQueryInsert( false );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcFuncao.setConexao( cn );
		lcTurno.setConexao( cn );
		lcDepto.setConexao( cn );
	}
}
