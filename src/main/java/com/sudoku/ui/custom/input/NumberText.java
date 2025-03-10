package com.sudoku.ui.custom.input;

import com.sudoku.model.Space;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class NumberText extends JTextField {

    private final Space space;

    public NumberText(Space space) {
        this.space = space;
        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());

        if(space.isFixed()){
            this.setText(space.getActual().toString());
        }

        this.getDocument().addDocumentListener(new DocumentListener() {

            private void changeSpace(){
                if(getText().isEmpty()){
                    space.clearSpace();
                    return;
                }
                space.setActual(Integer.parseInt(getText()));
            }


            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }
}
