package net.esc20.txeis.EmployeeAccess.util;

import java.io.Serializable;

public class Selectable implements Serializable
{
	private static final long serialVersionUID = -3290889813360196940L;
	
	private boolean selected = false;

	public Selectable() {}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Selectable(boolean selected) { this.selected = selected; }
}