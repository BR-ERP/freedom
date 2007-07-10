package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FFDialogo;


public class DLSelRecibo extends FFDialogo {

	private Tabela tab = new Tabela();
	private final JPanelPad pnRec = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout()  );;

	public DLSelRecibo( Frame fOrig, boolean bModal ) {

		super(fOrig, bModal);
		setAtribos( 500, 300 );
		pnRec.add( new JScrollPane( tab ) );
		c.add( pnRec );
		
		
	}
	
	public void carregaTab(List<Object[]> lsCab, Vector<Object> vRec) {
		Object[] cab = null;
		Vector<Object> linha = null;
		if (lsCab!=null) {
			tab.adicColuna( "Sel." );
			tab.setTamColuna( lsCab.size(), 50 );
			tab.setColunaEditavel( 0, true );
			for(int i=0; i<lsCab.size(); i++ ) {
				cab = lsCab.get( i );
				tab.adicColuna( cab[0] );
				tab.setTamColuna( ((Integer) cab[1]).intValue(), i+1 );
			}
			
			for (int i=0; i<vRec.size(); i++) {
				linha = new Vector<Object>();
//				 Adicionar a coluna de sele��o
				linha.addElement( new Boolean( (i==0) ) ); // seleciona a primeira linha
				for (int i2=0; i2<(((Vector) vRec.elementAt( i )).size()); i2++) {
					linha.addElement(((Vector) vRec.elementAt( i )).elementAt(i2));
				}
				tab.adicLinha(linha);
			}
			
		}
	}
	
	public List<Integer> getParcRecibo() {
		Boolean sel = null;
		List<Integer> lsRet = new ArrayList<Integer>();
		for (int i=0; i < tab.getNumLinhas(); i++) {
			sel = (Boolean) tab.getValor( i, 0 );
			if (sel) {
				lsRet.add( (Integer) tab.getValor( i, 1 ) ); // Coluna da parcela
			}
		}
		return lsRet;
	}
	
}
