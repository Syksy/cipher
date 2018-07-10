package com.ciphervaadinui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.awt.GridLayout;
import java.util.List;
import java.util.ArrayList;

/**
 * The main view contains a button and a template element.
 */
@HtmlImport("styles/shared-styles.html")
@Route("")
public class MainView extends VerticalLayout {
    static CipherVaadinDB db;
    String tileoutput = "";
            
    public MainView() {
        // Try connect to the Cipher DB
        System.out.println("Testing MainView");
        db = new CipherVaadinDB();        
        db.connect();
        List<CipherVaadinTile> tiles = db.getTiles();
        for(CipherVaadinTile tile : tiles){
            tileoutput += tile.toString();
        }
        
        CipherTemplate template = new CipherTemplate();

        GridLayout grid = new GridLayout(4,4);

        //add(grid, template);
        
        Button button;
        button = new Button("All tiles in DB",
                event -> template.setValue(tileoutput)
        );

        add(button, template);

        setClassName("main-layout");
    }
}
