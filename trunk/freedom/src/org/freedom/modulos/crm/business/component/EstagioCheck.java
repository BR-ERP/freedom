package org.freedom.modulos.crm.business.component;

import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.infra.pojos.Constant;


public class EstagioCheck extends Constant {

	private static final long serialVersionUID = 1L;

	private ImageIcon img = null;
	
	public static final EstagioCheck EPE = new EstagioCheck("Est�gio PE", "EPE");

	public static final EstagioCheck E1I = new EstagioCheck("Est�gio 1 inconsistente", "E1I");

	public static final EstagioCheck E1O = new EstagioCheck("Est�gio 1 Ok", "E1O");
	
	public static final EstagioCheck E2I = new EstagioCheck("Est�gio 2 inconsistente", "E2I");

	public static final EstagioCheck E2O = new EstagioCheck("Est�gio 2 Ok", "E2O");
	
	public static final EstagioCheck E3I = new EstagioCheck("Est�gio 3 inconsistente", "E3I");

	public static final EstagioCheck E3O = new EstagioCheck("Est�gio 3 Ok", "E3O");	
	
	public static final EstagioCheck E4I = new EstagioCheck("Est�gio 4 inconsistente", "E4I");

	public static final EstagioCheck E4O = new EstagioCheck("Est�gio 4 Ok", "E4O");	
	
	public static final EstagioCheck E5I = new EstagioCheck("Est�gio 5 inconsistente", "E5I");

	public static final EstagioCheck E5O = new EstagioCheck("Est�gio 5 Ok", "E5O");	
	
	private static Vector<EstagioCheck> listEstagio = null;

	public ImageIcon getImg() {
	
		return img;
	}

	public static Vector<EstagioCheck> getListEstagio() {
		if (listEstagio==null) {
			listEstagio = new Vector<EstagioCheck>();
			listEstagio.addElement( EPE );
			listEstagio.addElement( E1I );
			listEstagio.addElement( E1O );
			listEstagio.addElement( E2I );
			listEstagio.addElement( E2O );			
			listEstagio.addElement( E3I );
			listEstagio.addElement( E3O );	
			listEstagio.addElement( E4I );
			listEstagio.addElement( E4O );	
			listEstagio.addElement( E5I );
			listEstagio.addElement( E5O );	
		}
		return listEstagio;
	}
	
	public static ImageIcon getImg(String estagio) {
		ImageIcon result = null;
		Vector<EstagioCheck> list = getListEstagio();
		for (int i=0; i<list.size(); i++) {
			if (list.elementAt( i ).getValue().equals( estagio )) {
				result = list.elementAt( i ).getImg();
				break;
			}
		}
		return result;
	}

	//Retorna o valor conforme a tabela.
	// Exemplo (Est�gio 1 na constant e est�gio 1 na tabela): E1I = 1I
	public String getValueTab() {
		String result = "";
		if ( ((String) getValue()!=null) && ((String) getValue()).length()>0) {
			// Retorna a String a partir do segundo caracter
			result = ((String) getValue()).substring(1);
		}
		return result;
	}
	
	public void setImg( ImageIcon img ) {
	
		this.img = img;
	}

	public EstagioCheck( String name, Object value ) {

		super( name, value );

	}
	
	public EstagioCheck( String name, Object value, ImageIcon img ) {

		super( name, value );
		setImg( img );

	}
	

}
