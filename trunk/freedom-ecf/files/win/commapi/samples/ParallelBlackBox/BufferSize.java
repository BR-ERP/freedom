/*
 * @(#)BufferSize.java	1.1 98/06/02 SMI
 *
 * Author: Tom Corson
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license 
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */

import java.awt.Panel;
import java.awt.Label;
import java.awt.TextField;
import java.awt.BorderLayout;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.comm.ParallelPort;

public class BufferSize extends Panel implements MouseListener, ActionListener
{
	private int		value,
				defaultValue;
	private Label		label;
	private TextField	data;
	private ParallelPort	port = null;
	private boolean		inputBuffer;

	public BufferSize(int		size,
			   ParallelPort	port,
			   boolean	inputBuffer)
	{
		super();

		this.setPort(port);

		this.inputBuffer = inputBuffer;

		this.setLayout(new BorderLayout());

		this.label = new Label("Buffer Size");
		this.label.addMouseListener(this);
		this.add("West", this.label);

		this.data = new TextField(new Integer(defaultValue).toString(), 
					     size);
		this.data.addActionListener(this);
		this.add("East", this.data);

		this.showValue();

		this.defaultValue = this.value;
	}

	public void setPort(ParallelPort	port)
	{
		this.port = port;
	}

	public int getValue()
	{
		if (this.port != null)
		{
			/*
			 *  Get the buffer size.
			 */
	
			if (inputBuffer)
			{
				this.value = port.getInputBufferSize();
			}
	
			else
			{
				this.value = port.getOutputBufferSize();
			}
	
			return this.value;
		}

		else
		{
			return(0);
		}
	}

	public void showValue()
	{
		this.data.setText(new Integer(this.getValue()).toString());
	}

	public void setValue(int	val)
	{
		if (this.port != null)
		{
			/*
			 *  Set the new buffer size.
			 */
	
			if (inputBuffer)
			{
				port.setInputBufferSize(val);
			}
	
			else
			{
				port.setOutputBufferSize(val);
			}
		}

		this.showValue();
	}

	public void setDefaultValue(int	val)
	{
		this.defaultValue = val;
	}

	public void actionPerformed(ActionEvent e)
	{
		String	s = e.getActionCommand();

		try
		{
			Integer	newValue = new Integer(s);
	
			this.setValue(newValue.intValue());
		}

		catch (NumberFormatException ex)
		{
			System.out.println("Bad value = "
					  + e.getActionCommand());

			this.showValue();
		}
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		this.setValue(this.defaultValue);
	}

	public void mouseReleased(MouseEvent e)
	{
	}
}
