package org.freedom.infra.x.UIMaker;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;

import org.freedom.infra.x.swing.FDPassaword;

public class PasswordField extends FDPassaword {

	private static final long serialVersionUID = 1l;

	public static final float ALFA_NONE = -1f;

	private float alfa = ALFA_NONE;

	private int arcWidth;

	private int arcHeight;

	public PasswordField() {

		this("");
	}

	public PasswordField(String text) {

		super(text);
	}

	public PasswordField(float alfa) {

		this(alfa, null, 0, 0);
	}

	public PasswordField(float alfa, Color color) {

		this(alfa, color, 0, 0);
	}

	public PasswordField(float alfa, int arcWidth, int arcHeight) {

		this(alfa, null, arcWidth, arcHeight);
	}

	public PasswordField(float alfa, int arcWidthHeigth) {

		this(alfa, null, arcWidthHeigth, arcWidthHeigth);
	}

	public PasswordField(float alfa, Color color, int arcWidthHeigth) {

		this(alfa, color, arcWidthHeigth, arcWidthHeigth);
	}

	public PasswordField(float alfa, Color color, int arcWidth, int arcHeight) {

		this();

		setAlfaColor(alfa, color);
		setArc(arcWidth, arcHeight);
	}

	public float getAlfa() {

		return alfa;
	}

	public void setAlfa(final float alfa) {

		this.alfa = alfa >= 0 ? alfa : ALFA_NONE;
	}

	public void setAlfaColor(final float alfa, final Color color) {

		setAlfa(alfa);
		setBackground(color);
	}

	public int getArcWidth() {
		return arcWidth;
	}

	public void setArcWidth(int arcWidth) {
		this.arcWidth = arcWidth;
		Insets it = getBorder().getBorderInsets(this);
		setBorder(BorderFactory.createEmptyBorder(it.top, arcWidth / 2, it.bottom, arcWidth / 2));
	}

	public int getArcHeight() {
		return arcHeight;
	}

	public void setArcHeight(int arcHeight) {
		this.arcHeight = arcHeight;
		Insets it = getBorder().getBorderInsets(this);
		setBorder(BorderFactory.createEmptyBorder(it.top, arcHeight / 2, it.bottom, arcHeight / 2));
	}

	public void setArc(int arcWidth, int arcHeight) {
		setArcWidth(arcWidth);
		setArcHeight(arcHeight);
	}

	@Override
	protected void paintComponent(Graphics g) {

		if (alfa > ALFA_NONE) {
			paintAlfa(g);
		}
		else if (( arcWidth > 0 || arcHeight > 0 )) {
			paintArc(g);
		}

		super.paintComponent(g);
	}

	private void paintAlfa(Graphics g) {

		setOpaque(false);

		Graphics2D g2d = ( Graphics2D ) g;

		Composite oldComposite = g2d.getComposite();
		Color oldforeground = getForeground();

		AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alfa);
		g2d.setComposite(alphaComposite);

		Rectangle r = g.getClipBounds();
		Color c = getBackground();
		if (c == null) {
			c = Color.lightGray;
		}
		g2d.setColor(c);
		if (r != null) {
			g.fillRoundRect(r.x, r.y, r.width, r.height, arcWidth >= 0 ? arcWidth : 0, arcHeight >= 0 ? arcHeight : 0);
		}
		else {
			g.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth >= 0 ? arcWidth : 0, arcHeight >= 0 ? arcHeight : 0);
		}

		g2d.setComposite(oldComposite);
		g2d.setColor(oldforeground);
	}

	private Graphics paintArc(Graphics g) {

		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());

		Rectangle r = g.getClipBounds();
		Color c = getBackground();
		if (c == null) {
			c = Color.lightGray;
		}
		g.setColor(c);
		if (r != null) {
			g.fillRoundRect(r.x, r.y, r.width, r.height, arcWidth >= 0 ? arcWidth : 0, arcHeight >= 0 ? arcHeight : 0);
		}
		else {
			g.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth >= 0 ? arcWidth : 0, arcHeight >= 0 ? arcHeight : 0);
		}

		return g;
	}
}
