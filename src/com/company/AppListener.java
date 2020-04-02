package com.company;

import javax.swing.*;
import java.awt.event.*;


public class AppListener implements ActionListener {

    public AppListener() {
        super();


    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()){
            case "Preorder":
                System.out.println("preorder");
                break;
            case "Inorder":
                System.out.println("inorder");
                break;
            case "Postorder":
                System.out.println("postorder");
                break;
        }

    }
}
