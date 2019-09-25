package com.folderclear.constant;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class GlobalBorder {

	public final static Border RAISEDBORDER = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
			GlobalColor.MAINGRAY, GlobalColor.BORDERBLACK, GlobalColor.BORDERGRAY);
	public final static Border LOWEREDBORDER = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
			GlobalColor.MAINGRAY, GlobalColor.BORDERBLACK, GlobalColor.BORDERGRAY);
	public final static Border LINEBORDER = BorderFactory.createBevelBorder(BevelBorder.RAISED, GlobalColor.BORDERBLACK,
			GlobalColor.BORDERGRAY, GlobalColor.BORDERBLACK, GlobalColor.BORDERGRAY);
	public final static Border MENURAISEDBORDER = BorderFactory.createBevelBorder(BevelBorder.RAISED,
			GlobalColor.MAINGRAY, Color.white, GlobalColor.MAINGRAY, GlobalColor.BORDERGRAY);
	public final static Border MENULOWEREDBORDER = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
			GlobalColor.MAINGRAY, Color.white, GlobalColor.BORDERGRAY, GlobalColor.MAINGRAY);

}
