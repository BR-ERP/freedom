package org.freedom.infra.controller;

import java.awt.Window;
import java.io.IOException;
import java.util.Properties;

import org.freedom.infra.util.ini.ManagerIni;

/**
 * Projeto: <a href="http://sourceforge.net/projects/freedom-erp/">Freedom-infra</a> <br>
 * Este programa � licenciado de acordo com a LPG-PC <br>
 * (Licen�a P�blica Geral para Programas de Computador) vers�o 2.1.0 ou qualquer vers�o posterior. <br>
 * <br>
 * 
 * Est� classe visa padronizar caracteristicas comuns para as classes principais de aplica��es.
 * 
 * <br>
 * @author 		Alex Rodrigues
 * @version 	0.0.5 � 01/08/2008
 * 
 * @since 		16/05/2008
 */
public abstract class Aplication {

	/**
	 * Referencia a janela principal da aplica��o.
	 * 
	 * @since	16/05/2008
	 */
	private Window window;
	
	/**
	 * Mapa de parametros para a aplica��o. 
	 * 
	 * @since	16/05/2008
	 */
	private Properties properties;	
	
	
	/**
	 * Atua iniciando o mapa de parametros <code>properties</code>,
	 * invocando o metodo <code>initParameters</code>.
	 * Tamb�m invoca os metodos <code>init</code>, para inicia��o dos parametros da classe concreta e o
	 * metodo <code>show</code> para a exibi��o da janela {@link #window principal} associada a esta.
	 * 
	 * @see 	#initParameters()
	 * @see		#init()
	 * @see		#show()
	 * 
	 * @since	16/05/2008
	 */
	public Aplication() throws IOException {
		
		super();

		initParameters();	
		init();		
		show();	
	}

	/**
	 * Este acionado pelo construtor padr�o, da a oportunidade da subclasse 
	 * iniciar parametros ou tomar a��es que julgue necess�rias.
	 * 
	 * @see		#Aplication()
	 * 
	 * @since	16/05/2008
	 */
	protected abstract void init();
	
	/**
	 * Inicia o {@link #properties mapa de parametros}.
	 * 
	 * @since	16/05/2008
	 */
	protected void initParameters() throws IOException {

		ManagerIni mi = ManagerIni.createManagerIniParameter();
		properties = mi.getSession( "Aplication" );
	}
	
	/**
	 * Torna vis�vel a {@link #window janela principal}, caso sua refer�ncia n�o seja igual a <code>null</code>.
	 * 
	 * @see		java.awt.Component#setVisible(boolean)
	 * 
	 * @since	16/05/2008
	 */
	protected void show() {		
		if ( window != null ) {
			window.setVisible( true );
		}
	}
	
	public Window getWindow() {	
		return window;
	}
	
	public void setWindow( Window window ) {	
		this.window = window;
	}
	
	/**
	 * @see		java.util.Properties#get(Object)
	 */
	public Object getProperty( Object property ) {
		return properties.get( property );
	}
	
	/**
	 * @see		java.util.Properties#put(Object, Object)
	 */
	public Object putProperty( String name, Object property ) {
		return properties.put( name, property );
	}

}
