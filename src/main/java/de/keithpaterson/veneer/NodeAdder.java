/* 
* This file is part of Veneer.
*
*    Veneer is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    Veneer is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with Veneer.  If not, see <http://www.gnu.org/licenses/>.
*/
    	
package de.keithpaterson.veneer;

import org.python.core.PyObject;

import javafx.scene.control.TreeItem;
import textures.texture.TextureReference;

public class NodeAdder extends PyObject {

	private TreeItem node;

	public NodeAdder(TreeItem node) {
		this.node = node;
	}
	
	/**
	 * 
	 * @param p
	 */

	public void append(PyObject p) {
		if (p instanceof TextureReference) {
			TextureReference t = (TextureReference) p;
			TreeItem<TextureReference> treeItem = new TreeItem<TextureReference>(t);
//			treeItem.setValue(value);
			node.getChildren().add(treeItem);
		}
		System.out.println(p);
	}

	
}
