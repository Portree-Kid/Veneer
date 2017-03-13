package de.keithpaterson.veneer;

import org.python.core.PyException;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class TextureFileTreeItem<T> extends TreeItem<LabelNode> {
	
	

	private PyException error;

	/**
	 * @return the error
	 */
	public PyException getError() {
		return error;
	}

	public void setError(PyException pyException) {
		this.error = pyException;
	}

//	ObservableList<TreeItem<LabelNode>> list = new ArrayObservableList<>();
//	
//	
//
//	@Override
//	public ObservableList<TreeItem<LabelNode>> getChildren() {
//		return list;
//	}
//
//	@Override
//	public boolean isLeaf() {
//		return list.size()>0;
//	}
//
//	@Override
//	public String toString() {
//		return "BLBLB";
//	}
}
