/*******************************************************************************
 * Copyright (c) 2007 by CRP Henri TUDOR - SANTEC LUXEMBOURG 
 * check http://www.santec.tudor.lu for more information
 *  
 * Contributor(s):
 * Johannes Hermen  johannes.hermen(at)tudor.lu                            
 * Martin Heinemann martin.heinemann(at)tudor.lu  
 *  
 * This library is free software; you can redistribute it and/or modify it  
 * under the terms of the GNU Lesser General Public License (version 2.1)
 * as published by the Free Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but     
 * WITHOUT ANY WARRANTY; without even the implied warranty of               
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU        
 * Lesser General Public License for more details.                          
 * 
 * You should have received a copy of the GNU Lesser General Public         
 * License along with this library; if not, write to the Free Software      
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 *******************************************************************************/
package org.freedom.modulos.crm.agenda.visoes;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import lu.tudor.santec.bizcal.listeners.DateListener;
import lu.tudor.santec.bizcal.listeners.NamedCalendarListener;

public abstract class AbstractCalendarView extends JPanel implements DateListener, NamedCalendarListener {

	private static final long serialVersionUID = 1L;

	public AbstractCalendarView() {
		setOpaque(false);
	}

	public abstract JToggleButton getButton();

	public abstract String getViewName();

	// public abstract List getEvents();

	public abstract void print(boolean showPrinterDialog);

	public abstract CalendarView getView();

}
