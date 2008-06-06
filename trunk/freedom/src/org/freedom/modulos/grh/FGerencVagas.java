/**
 * @version 31/03/2008 <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FGerencVagas.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Gerenciamento de vagas.
 * 
 */

package org.freedom.modulos.grh;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.TabelaEditEvent;
import org.freedom.acao.TabelaEditListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FGerencVagas extends FFilho implements ActionListener, TabelaEditListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 0, 94 );
	private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtCodVaga = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private final JTextFieldFK txtCodEmpr = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private final JTextFieldFK txtNomeEmpr = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	private final JTextFieldFK txtCodFunc = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );	
	private final JTextFieldFK txtDescFunc = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );
	private final JTextFieldFK txtFaixaSalIni = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
	private final JTextFieldFK txtFaixaSalFim = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
		
	private Tabela tab = new Tabela();
	private JButton btRefresh = new JButton(Icone.novo("btExecuta.gif"));
//	private JButton btOk = new JButton(Icone.novo("btOk.gif"));
	private JButton btEncaminharCand = new JButton("Encaminhar", Icone.novo("btEncaminharCand.gif"));
	private JButton btEfetivarCand = new JButton("Efetivar", Icone.novo("btEfetivarCand.gif"));
	private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
	private ImageIcon imgEditaCampo = Icone.novo("clEditar.gif");
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcVaga = new ListaCampos(this);
	private ListaCampos lcEmpregador = new ListaCampos(this,"EM");
	private ListaCampos lcFuncao = new ListaCampos(this,"FC");

	private JCheckBoxPad cbQualificacoes = new JCheckBoxPad("Qualifica��es",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbRestricoes = new JCheckBoxPad("Restri��es",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbCursos = new JCheckBoxPad("Cursos",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbExperiencia = new JCheckBoxPad("Experiencia",new Boolean(true),new Boolean(false));
	private JCheckBoxPad cbFaixaSalarial = new JCheckBoxPad("Faixa salarial",new Boolean(true),new Boolean(false));
	
	private final JCheckBoxPad cbSelecionado = new JCheckBoxPad( "Sele��o", "S", "N" );
	
	private BigDecimal bVlrAceito = new BigDecimal("0");
	private BigDecimal bVlrAprovado = new BigDecimal("0");
	private BigDecimal bVlrTotal = new BigDecimal("0");
	
	private JLabelPad lbFiltros = new JLabelPad( " Filtros" );
	private JPanelPad pinFiltros = new JPanelPad( 300, 150 );
	private JPanelPad pinLbFiltros = new JPanelPad( 53, 15 );
	
	public FGerencVagas() {
		super(false);
		setTitulo("Gerenciamento de vagas");
		setAtribos(15,30,796,380);

		/*cbQualificacoes.setVlrString("S");
		cbRestricoes.setVlrString("S");
		cbCursos.setVlrString("S");
		cbExperiencia.setVlrString( "S" );
		cbFaixaSalarial.setVlrString( "S" );*/
		
		
		
		cbQualificacoes.setVlrBoolean( new Boolean(true) );
		cbRestricoes.setVlrBoolean( new Boolean(true) );
		cbCursos.setVlrBoolean( new Boolean(true) );
		cbExperiencia.setVlrBoolean( new Boolean(true) );
		cbFaixaSalarial.setVlrBoolean( new Boolean(true) );

		btRefresh.setToolTipText( "Refazer consulta" );		
		btEncaminharCand.setToolTipText( "Encaminhar candidatos" );
		btEfetivarCand.setToolTipText( "Efetivar candidatos" );
//		btOk.setToolTipText("Confirmar aprova��o");
		
		btRefresh.addActionListener(this);
		btEncaminharCand.addActionListener(this);
		btEfetivarCand.addActionListener(this);
		btSair.addActionListener(this);

		JPanelPad pinRod = new JPanelPad(685,39);
			
		lcVaga.add(new GuardaCampo( txtCodVaga, "CodVaga", "C�d.Vaga",ListaCampos.DB_PK , null, false));		
		lcVaga.add(new GuardaCampo( txtCodEmpr, "CodEmpr","C�d.Empr.",ListaCampos.DB_FK, null, false));
		lcVaga.add(new GuardaCampo( txtCodFunc, "CodFunc","C�d.Func.",ListaCampos.DB_FK, null, false));
		lcVaga.add(new GuardaCampo( txtFaixaSalIni, "FaixaSalIni","Inicial",ListaCampos.DB_SI, null, false));
		lcVaga.add(new GuardaCampo( txtFaixaSalFim, "FaixaSalFim","Inicial",ListaCampos.DB_SI, null, false));

		lcVaga.montaSql(false,"VAGA","RH");
		lcVaga.setQueryCommit(false);
		lcVaga.setReadOnly(true);		

		txtCodVaga.setNomeCampo("CodVaga");
		txtCodVaga.setPK(true);
		txtCodVaga.setListaCampos(lcVaga);
		
		
		//FK Empregador
		lcEmpregador.add(new GuardaCampo( txtCodEmpr, "CodEmpr", "C�digo", ListaCampos.DB_PK, null, false));
		lcEmpregador.add(new GuardaCampo( txtNomeEmpr, "NomeEmpr", "Empregador", ListaCampos.DB_SI, null, false));
		lcEmpregador.montaSql(false, "EMPREGADOR","RH");    
		lcEmpregador.setQueryCommit(false);
		lcEmpregador.setReadOnly(true);
		txtCodEmpr.setTabelaExterna(lcEmpregador);
		
		//FK Funcao
		lcFuncao.add(new GuardaCampo( txtCodFunc, "CodFunc", "C�digo", ListaCampos.DB_PK, null, false));
		lcFuncao.add(new GuardaCampo( txtDescFunc, "DescFunc", "Fun��o", ListaCampos.DB_SI, null, false));
		lcFuncao.montaSql(false, "FUNCAO","RH");    
		lcFuncao.setQueryCommit(false);
		lcFuncao.setReadOnly(true);
		txtCodFunc.setTabelaExterna(lcFuncao);

		pinCab.adic(new JLabelPad("C�d.Vaga"),7,0,60,20);
		pinCab.adic(txtCodVaga,7,20,60,20);
		
		pinCab.adic(new JLabelPad("C�d.Empr."),70,0,60,20);
		pinCab.adic(txtCodEmpr,70,20,60,20);
		
		pinCab.adic(new JLabelPad("Empregador"),133,0,230,20);
		pinCab.adic(txtNomeEmpr,133,20,230,20);
		
		pinCab.adic(new JLabelPad("C�d.Func."),7,40,60,20);
		pinCab.adic(txtCodFunc,7,60,60,20);
		
		pinCab.adic(new JLabelPad("Fun��o"),70,40,230,20);
		pinCab.adic(txtDescFunc,70,60,293,20);
								
		pinLbFiltros.adic( lbFiltros, 0, 0, 80, 15 );
		pinLbFiltros.tiraBorda();

		pinCab.adic( pinLbFiltros, 375, 4, 55, 15 );
		pinCab.adic( pinFiltros, 372, 12, 300, 69 );
		
		pinCab.adic (btRefresh,745,12,30,68);
		
			
		pinFiltros.adic( cbQualificacoes, 3, 7, 130, 18 );
		pinFiltros.adic( cbRestricoes, 3, 25, 130, 18 );
		pinFiltros.adic( cbFaixaSalarial, 3, 43, 130, 18 );
		pinFiltros.adic (txtFaixaSalIni,136,43,50,18);
		pinFiltros.adic (txtFaixaSalFim,189,43,50,18);		
		
		pinFiltros.adic( cbCursos, 136, 7, 130, 18 );
		pinFiltros.adic( cbExperiencia, 136, 25, 130, 18 );
		
		pinRod.adic(btEncaminharCand,5,2,140,30);
		pinRod.adic(btEfetivarCand,148,2,130,30);
		pinRod.adic(btSair,675,2,100,30);
					
		getTela().add(pnCab,BorderLayout.CENTER);
		pnCab.add(pinCab,BorderLayout.NORTH);
		pnCab.add(pinRod,BorderLayout.SOUTH);
		pnCab.add(spnTab,BorderLayout.CENTER);

		tab.adicColuna("S/N");
		tab.adicColuna("C�d.");
		tab.adicColuna("Nome");
		tab.adicColuna("Fone");
		tab.adicColuna("Qualif.");
		tab.adicColuna("Restr.");
        tab.adicColuna("Cursos");
		tab.adicColuna("Exp.");
		tab.adicColuna("Sal�rio");
		tab.adicColuna("");
		
		tab.setTamColuna(30,0);
		tab.setTamColuna(55,1);
		tab.setTamColuna(250,2);
		tab.setTamColuna(80,3);
		tab.setTamColuna(55,4);
		tab.setTamColuna(55,5);
		tab.setTamColuna(55,6);
		tab.setTamColuna(55,7);
		tab.setTamColuna(80,8);
		tab.setTamColuna(20,9);
		
		tab.setColunaEditavel( 0, true );		
		tab.addMouseListener( this );
		
		//tab.setColunaEditavel(0,true);
		
//		tab.setDefaultEditor(String.class,new DefaultCellEditor(new JTextFieldPad(JTextFieldPad.TP_STRING,15,0)));
		
		cbQualificacoes.addMouseListener(
		  new MouseAdapter() {
			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource()==cbQualificacoes && mevt.getClickCount()==1){
					
				}
			}
		  }
		);		
		
		txtCodVaga.addKeyListener(
		  new KeyAdapter() {
		    public void keyPressed(KeyEvent kevt) {
		    	if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
//		    		montaTab();
		    	}
		    }
		  }
		);	
	}
    
	public void montaTab(){ 

		StringBuffer sql = new StringBuffer();		
		StringBuffer where = new StringBuffer();
		boolean and = false;
		
		sql.append( "SELECT CODCAND,NOMECAND,FONECAND,PRETENSAOSAL,QUALIFICACOES,RESTRICOES,CURSOS,EXPERIENCIA,STATUS " );
		sql.append( "FROM RHLISTACANDVAGASP(?,?,?,?)" );
				
		if(cbQualificacoes.getVlrBoolean() || cbCursos.getVlrBoolean() ||
		   cbExperiencia.getVlrBoolean() || cbFaixaSalarial.getVlrBoolean() ||
		   cbRestricoes.getVlrBoolean() ) {
		
			where.append( " WHERE " );
		
		}
				
		if(cbQualificacoes.getVlrBoolean()) {
			where.append("QUALIFICACOES > 0 " );
			and = true;
		}
		
		if(cbCursos.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "CURSOS > 0 " ) );
			and = true;
		}

		if(cbExperiencia.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "EXPERIENCIA > 0 " ) );
			and = true;
		}

		if(cbRestricoes.getVlrBoolean()) {
			where.append( ( and ? " AND " : "" ) + ( "RESTRICOES > 0 " ) );
			and = true;
		}

		if(cbFaixaSalarial.getVlrBoolean()) {
			where.append( (and ? " AND " : "" ) + ( "((PRETENSAOSAL BETWEEN  ? AND ? ) OR (PRETENSAOSAL IS NULL))" ) );			
		}
		
		sql.append( where );
		
		tab.limpa();
		
		try {
			System.out.println("SQL:" + sql.toString());
			
			PreparedStatement ps = con.prepareStatement(sql.toString());
			
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial( "ATATRIBUICAO" ));
			ps.setInt(3,txtCodVaga.getVlrInteger().intValue());					
			ps.setInt(4,txtCodFunc.getVlrInteger().intValue());						
			
			if(cbFaixaSalarial.getVlrBoolean()) {
				ps.setDouble( 5, txtFaixaSalIni.getVlrDouble() );
				ps.setDouble( 6, txtFaixaSalFim.getVlrDouble() );				
			}
						
			ResultSet rs = ps.executeQuery();
						
			while (rs.next()) {
			    Vector<Object> vVals = new Vector<Object>();
			    vVals.addElement( new Boolean( false ) );
			    vVals.addElement( rs.getString( "CODCAND" ) );
			    vVals.addElement( rs.getString( "NOMECAND" ) );
			    vVals.addElement( rs.getString( "FONECAND" ) );
			    vVals.addElement( rs.getString( "QUALIFICACOES" ) );
			    vVals.addElement( rs.getString( "RESTRICOES" ) );
			    vVals.addElement( rs.getString( "CURSOS" ) );
			    vVals.addElement( rs.getString( "EXPERIENCIA" ) );
			    vVals.addElement( (rs.getString( "PRETENSAOSAL" ) == null) ? "" : rs.getString( "PRETENSAOSAL" ) );
			    vVals.addElement( rs.getString( "STATUS" ) );
			    
				tab.adicLinha(vVals);				
			}
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao consultar candidatos!\n"+err.getMessage(),true,con,err);
		}
		tab.addTabelaEditListener(this);
	}
	
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcVaga.setConexao(con);
		lcEmpregador.setConexao(con);
		lcFuncao.setConexao(con);
	}

	public void actionPerformed( ActionEvent evt ) {
		if (evt.getSource() == btRefresh) {
			montaTab();
		}
		else if (evt.getSource() == btEncaminharCand) {
			encaminharCandidato();
		}
		else if (evt.getSource() == btEfetivarCand) {
			efetivarCandidato();
		}
		else if (evt.getSource() == btSair) {
			dispose();
		}
	}

	public void valorAlterado(TabelaEditEvent evt) {
/*		if ((tab.getColunaEditada()<2)) {

          }*/
    }
	
	private void encaminharCandidato() {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		int encaminhados = 0;		
		
		try {		
			
			if( Funcoes.mensagemConfirma( this, "Confirma o encaminhamento do(s) candidato(s) para a vaga?") == JOptionPane.YES_NO_OPTION ) {

				sql.append( "INSERT INTO RHVAGACANDIDATO(CODEMP,CODFILIAL,CODVAGA,CODEMPCA,CODFILIALCA,CODCAND,STVAGACAND) " );
				sql.append( "VALUES(?,?,?,?,?,?,?)" );	

				for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
	
					if ( ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() )
						continue;
					
					if( (tab.getValor( i, 9 ).toString().equals( "EF" )) ) { 
						Funcoes.mensagemInforma( this, "O candidato " + tab.getValor( i, 2 ).toString().trim() 
								+ " nao pode ser encaminhado, pois ja foi efetivado na vaga!" );	
						continue;					
					}
														
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "RHVAGACANDIDATO" ) );
					ps.setInt( 3, txtCodVaga.getVlrInteger().intValue() );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, ListaCampos.getMasterFilial( "RHCANDIDATO" ) );
					ps.setInt( 6, new Integer(tab.getValor( i, 1 ).toString()) );
					ps.setString( 7, "EN" );
					
					encaminhados += ps.executeUpdate();
					
					ps.close();			
					if ( !con.getAutoCommit() ) {
						con.commit();
					}
				}
				
				Funcoes.mensagemInforma( this, encaminhados > 0 ? 
						( encaminhados + " candidatos encaminhado" + ( encaminhados > 1 ? "s" : "" ) + " com sucesso!"):
					    ( "Nenhum candidato foi encaminhado para vaga! " ) ) ;				
			}
			
			montaTab();
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro ao encaminhar candidatos!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}		
	}

	private void efetivarCandidato() {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		int efetivados = 0;
		
		try {
			
			if( Funcoes.mensagemConfirma( this, "Confirma a efetiva�ao do(s) candidato(s) na vaga?") == JOptionPane.YES_NO_OPTION ) {
			
				sql.append( "UPDATE RHVAGACANDIDATO SET STVAGACAND='EF' " );
				sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODVAGA=? " );
				sql.append( "AND CODEMPCA=? AND CODFILIALCA=? AND CODCAND=? AND STVAGACAND='EN' " );	

				for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
	
					if ( ! ( (Boolean) tab.getValor( i, 0 ) ).booleanValue() )
						continue;
				
					if( ! (tab.getValor( i, 9 ).toString().equals( "EN" )) ) { 
						Funcoes.mensagemInforma( this, "O candidato " + tab.getValor( i, 2 ).toString().trim() 
								+ " deve ser encaminhado antes de efetivado!" );	
						continue;					
					}
												
					ps = con.prepareStatement( sql.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "RHVAGACANDIDATO" ) );
					ps.setInt( 3, txtCodVaga.getVlrInteger().intValue() );
					ps.setInt( 4, Aplicativo.iCodEmp );
					ps.setInt( 5, ListaCampos.getMasterFilial( "RHCANDIDATO" ) );
					ps.setInt( 6, new Integer(tab.getValor( i, 1 ).toString()) );
									
					efetivados += ps.executeUpdate();
					
					ps.close();			
					if ( !con.getAutoCommit() ) {
						con.commit();
					}
				}
				
				Funcoes.mensagemInforma( this, efetivados > 0 ? 
						( efetivados + " candidatos efetivado" + ( efetivados > 1 ? "s" : "" ) + " com sucesso!"):
					    ( "Nenhum candidato foi efetivado na vaga! " ) ) ;
				
			}
			
			montaTab();
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro ao efetivar candidatos!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}		
	}
	
	
	public void mouseClicked( MouseEvent mevt ) {
		
		Tabela tabEv = (Tabela) mevt.getSource();
	
		if ( mevt.getClickCount() == 1) {			
		}
		else if ( mevt.getClickCount() == 2 ) {			
			if ( tabEv == tab && tabEv.getLinhaSel() >= 0 ) {
				Funcoes.mensagemInforma( this, "Candidato selecionado!" );
			}
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

}
