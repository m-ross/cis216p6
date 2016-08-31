/*	Program Name:	Lab 06 Shipping Utility
	Programmer:		Marcus Ross
	Date Due:		18 Oct 2013
	Description:	This program shows a menu to a shipping clerk user which allows them to choose between looking up a shipping price per pound based on ZIP and producing a shipping label.
*/

package lab06;

import lab06.Shipping;

public class Main {
public static void main (String[] args) {
		Shipping ship = new Shipping();
		ship.getLocInfo();
		ship.showMenu();
	}
}