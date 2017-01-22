/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.keithpaterson.veneer;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author keith.paterson
 */
public class TextureNode extends LabelNode {
    
    private final SimpleStringProperty name;

    public TextureNode(String name) {
    	super(name);
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String fName) {
        name.set(fName);
    }

	public String getTexture() {
		// TODO Auto-generated method stub
		return null;
	}
}
