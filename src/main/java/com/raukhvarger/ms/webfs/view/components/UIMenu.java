package com.raukhvarger.ms.webfs.view.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class UIMenu extends HorizontalLayout {

    private final Logger logger = LoggerFactory.getLogger(UIMenu.class);

    public UIMenu() {
        setWidthFull();

        MenuBar leftMenu = new MenuBar();
        leftMenu.addItem("File", e -> logger.info("File is clicked"));
        leftMenu.addItem("Edit", e -> logger.info("Edit is clicked"));
        leftMenu.addItem("Custom", e -> logger.info("Custom is clicked"));

        MenuBar rigntMenu = new MenuBar();
        rigntMenu.addItem("Log out", e -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate("");
        });

        addAndExpand(leftMenu);
        add(rigntMenu);
    }
}
