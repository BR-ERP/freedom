package org.freedom.infra.util.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * Projeto: <a href="http://sourceforge.net/projects/freedom-erp/">Freedom-infra</a> <br>
 * Este programa � licenciado de acordo com a LPG-PC <br>
 * (Licen�a P�blica Geral para Programas de Computador) vers�o 2.1.0 ou qualquer vers�o posterior. <br>
 * <br>
 * 
 * Factory para cria��o de objeto <code>FreedomLogger</code>. 
 * 
 * @see			FreedomLogger
 * 
 * @author 		Anderson Sanchez
 * @version 	0.0.1 � 30/06/2008
 * 
 * @since 		30/06/2008
 */
public class FreedomLoggerFactory implements LoggerFactory {

	public FreedomLoggerFactory() { 
	}

	public Logger makeNewLoggerInstance(String name) {
		return new FreedomLogger(name);
	}
}
