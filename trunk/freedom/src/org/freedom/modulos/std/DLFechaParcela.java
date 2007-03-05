/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLFechaRec.java <BR>
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
 */

package org.freedom.modulos.std;

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.ListaCampos;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.telas.FFDialogo;

public class DLFechaParcela extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtParcItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDtVencItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrDescItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );
	
	private JTextFieldPad txtCodTipCob = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
	  
	private JTextFieldFK txtDescTipoCob = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	
	private ListaCampos lcCob = new ListaCampos(this,"CO");
	
	private BigDecimal bgParcItRec;
	private Date dDtVencItRec;
	private BigDecimal bgDescItRec;

	public DLFechaParcela( Component cOrig, Connection cn, BigDecimal bgParcItRec, Date dDtVencItRec, BigDecimal bgDescItRec ) {

		super( cOrig );
		this.con = cn;
		setTitulo( "Parcela" );
		setAtribos( 100, 100, 350, 180 );
		this.bgParcItRec = bgParcItRec;
		this.dDtVencItRec = dDtVencItRec;
		this.bgDescItRec = bgDescItRec;
		txtParcItRec.setVlrBigDecimal( bgParcItRec );
		txtDtVencItRec.setVlrDate( dDtVencItRec );
		txtVlrDescItRec.setVlrBigDecimal( bgDescItRec );
		if ( bgDescItRec == null ) {
			txtVlrDescItRec.setAtivo( false );
		}
		lcCob.add( new GuardaCampo( txtCodTipCob, "CodTipoCob", "C�d.Tip.Cob",ListaCampos.DB_PK, false ));
		lcCob.add( new GuardaCampo( txtDescTipoCob,"DescTipoCob", "Descri��o Tipo de Cobran�a", ListaCampos.DB_SI, false));
		lcCob.setQueryCommit(false);
		lcCob.setReadOnly(true);
        lcCob.setConexao( cn );
  		lcCob.montaSql(false, "TIPOCOB", "FN");
		txtCodTipCob.setTabelaExterna(lcCob);
		txtCodTipCob.setFK(true);
		txtCodTipCob.setNomeCampo("CodTipoCob");
		adic( new JLabelPad( "Valor" ), 7, 0, 100, 20 );
		adic( new JLabelPad( "Vencimento" ), 110, 0, 100, 20 );
		adic( new JLabelPad( "Desconto" ), 220, 0, 100, 20 );
		adic( new JLabelPad("C�d.Tp.Cob"),7,40,80,20);
		adic( new JLabelPad("Descri��o do tipo cobran�a"),90, 40, 230, 20);
		adic( txtParcItRec, 7, 20, 100, 20 );
		adic( txtDtVencItRec, 110, 20, 100, 20 );
		adic( txtVlrDescItRec, 220, 20, 100, 20 );
		adic(txtCodTipCob, 7, 60, 80, 20);
		adic(txtDescTipoCob, 90, 60, 230, 20);
		
	}
	 
	public Object[] getValores() {

		Object[] oRetorno = new Object[ 3 ];
		oRetorno[ 0 ] = txtParcItRec.getVlrBigDecimal();
		oRetorno[ 1 ] = txtDtVencItRec.getVlrDate();
		oRetorno[ 2 ] = txtVlrDescItRec.getVlrBigDecimal();

		return oRetorno;
	}
}
