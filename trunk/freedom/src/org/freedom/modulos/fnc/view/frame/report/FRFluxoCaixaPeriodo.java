package org.freedom.modulos.fnc.view.frame.report;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;


public class FRFluxoCaixaPeriodo extends FRelatorio implements CarregaListener {
	
	private static final long serialVersionUID = 1L;
	
	private final JTextFieldPad txtDataIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDataFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JCheckBoxPad cbLancamentos = new JCheckBoxPad( "Listar Lan�amentos com valor zerado", "S", "N");
	
	//RadioGroup
	private Vector<String> vLabsTipo = new Vector<String>();
	
	private Vector<String> vValsTipo = new Vector<String>();
	
	private JRadioGroup	<String, String> rgTipoRel = null;
	
	private Vector<String> vLabsGraf = new Vector<String>();
	
	private Vector<String> vValsGraf = new Vector<String>();
	
	private JRadioGroup	<String, String> rgTipo = null;
	
	private Vector<String> vLabsOrdem = new Vector<String>();
	
	private Vector<String> vValsOrdem = new Vector<String>();
	
	private JRadioGroup	<String, String> rgOrdem = null;
	
	public FRFluxoCaixaPeriodo() {
		super();

		setTitulo( "Fluxo de caixa por per�odo" );
		setAtribos( 80, 80, 350, 260 );
		montaRadioGroup();
		montaTela();
	}
	
	private void montaTela(){

	
		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
		JLabel periodo = new JLabel( "Per�odo", SwingConstants.CENTER );
		periodo.setOpaque( true );

		adic( periodo, 20, 0, 80, 20 );
		adic( txtDataIni, 30, 20, 100, 20 );
		adic( new JLabel( "at�", SwingConstants.CENTER ), 140, 20, 40, 20 );
		adic( txtDataFim, 190, 20, 100, 20 );
		adic( bordaData, 10, 10, 300, 40 );
		
		adic ( rgTipoRel, 10, 57, 300, 30 );
		//adic ( rgTipo, 10, 93, 300, 30 );
		adic ( rgOrdem, 10, 109, 300, 30, "Ordenar por: " );
		adic ( cbLancamentos, 10, 142, 300, 30 );
		
		Calendar cal = Calendar.getInstance();
		txtDataFim.setVlrDate( cal.getTime() );
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 );
		txtDataIni.setVlrDate( cal.getTime() );
	}
	
	private void montaRadioGroup(){
		
		//Relat�rio Resumido ou Detalhado
		vLabsTipo .addElement( "Resumido" );
		vLabsTipo.addElement( "Detalhado" );
		vValsTipo.addElement( "R" );
		vValsTipo.addElement( "D" );
		
		rgTipoRel = new JRadioGroup<String, String>( 1, 2, vLabsTipo, vValsTipo );
		rgTipoRel.setVlrString( "D" );
		
		//Relat�rio Gr�fico ou Texto
		vLabsGraf .addElement( "Gr�fico" );
		vLabsGraf.addElement( "Texto" );
		vValsGraf.addElement( "G" );
		vValsGraf.addElement( "T" );
		
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabsGraf, vValsGraf );
		rgTipo.setVlrString( "G" );
		
		//Ordem do Relat�rio por emiss�o ou vencimento ou pagamento;
		vLabsOrdem .addElement( "Emiss�o" );
		vLabsOrdem.addElement( "Vencimento" );
		vLabsOrdem.addElement( "Pagamento" );
		vValsOrdem.addElement( "E" );
		vValsOrdem.addElement( "V" );
		vValsOrdem.addElement( "P" );
		
		rgOrdem = new JRadioGroup<String, String>( 1, 3, vLabsOrdem, vValsOrdem );
		rgOrdem.setVlrString( "E" );
	}
	
	/*
	private Blob carregaFotoemp(Blob fotoemp){
		try {
			PreparedStatement ps = con.prepareStatement( "SELECT FOTOEMP FROM SGEMPRESA WHERE CODEMP=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				fotoemp = rs.getBlob( "FOTOEMP" );
			}
			rs.close();
			ps.close();
			con.commit();

		} catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo.\n" + e.getMessage() );
			e.printStackTrace();
		}	
		return fotoemp;
	}
	*/

	public void imprimir( boolean bVisualizar ) {
		Blob fotoemp = null;
		
		try {
			PreparedStatement ps = con.prepareStatement( "SELECT FOTOEMP FROM SGEMPRESA WHERE CODEMP=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				fotoemp = rs.getBlob( "FOTOEMP" );
			}
			rs.close();
			ps.close();
			con.commit();

		} catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo.\n" + e.getMessage() );
			e.printStackTrace();
		}	
		
		StringBuilder sql = null;
		String sCab = null;
		String sOrdem = null;
		String sData = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			sCab = "Per�odo de " + txtDataIni.getVlrString()  + " a " +  txtDataFim.getVlrString();
			sql = new StringBuilder();
			sql.append( "SELECT ORDEM, TIPOLANCA, SUBTIPO, CODRECPAGLANC, NPARCRECPAGLANC, " );
			sql.append( "    DTEMISSAO, DTVENCTORECPAG, DOC, CODIGO, RAZAO,  HISTORICO, VALOR " );
			sql.append( "	from fnfluxocaixavw01" );

			if ( "E".equals( rgOrdem.getVlrString() ) ) {
				sOrdem = "order by ORDEM, DTEMISSAO " ;
				sData = "DTEMISSAO ";
			}
			if ( "V".equals( rgOrdem.getVlrString() ) ) {
				sOrdem = "order by ORDEM, DTVENCTORECPAG";
				sData = "DTVENCTORECPAG ";
			}
			if ( "P".equals( rgOrdem.getVlrString() ) ) {
				sOrdem = "order by ORDEM, DTVENCTORECPAG";
				sData = "DTVENCTORECPAG ";
			}
		
			sql.append( " WHERE CODEMP = ? AND CODFILIAL= ? AND " + sData + " BETWEEN ? AND ?" );
			sql.append( sOrdem );
			
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDataFim.getVlrDate() ) );

			rs = ps.executeQuery();

			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
		
		imprimiGrafico( bVisualizar, rs,  sCab, fotoemp, sOrdem );
	}
	
	private void imprimiGrafico(boolean bVisualizar, ResultSet rs, String sCab,  Blob fotoemp, String sOrdem){
		
		String report = "relatorios/fluxocaixaperiodo.jasper";
		String label = "Relat�rio de Fluxo de Caixa por Per�odo";
		
	    HashMap<String, Object> hParam = new HashMap<String, Object>();

	    try {
			hParam.put( "LOGOEMP",  new ImageIcon(fotoemp.getBytes(1, ( int ) fotoemp.length())).getImage() );
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo !\n" + e.getMessage()  );
			e.printStackTrace();
		}
	
		FPrinterJob dlGr = new FPrinterJob( report, label, sCab, rs, hParam , this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		} else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de Fluxo de Caixa por Per�odo!" + err.getMessage(), true, con, err );
			}
		}
		
	}

	public void afterCarrega( CarregaEvent cevt ) {
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}
}
