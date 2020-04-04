package com.company;

import javax.swing.*;
import java.awt.event.*;


public class AppListener implements ActionListener {

    // region 0. Constants

    // endregion

    // region 1. Init widgets

    // endregion

    // region 2. Constructor

    public AppListener() {
        super();
    }

    // endregion

    // region 3. Listener function

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

    // endregion
}
