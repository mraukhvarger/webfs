package com.raukhvarger.ms.webfs.front.view.component;

import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class UIMenu extends HorizontalLayout {

    private final Logger logger = LoggerFactory.getLogger(UIMenu.class);

    private final UIEvents uiEvents;

    public UIMenu(UIEvents uiEvents) {
        this.uiEvents = uiEvents;
    }

    @PostConstruct
    private void init() throws IOException {
        setWidthFull();

        MenuBar leftMenu = new MenuBar();

        MenuBar rigntMenu = new MenuBar();
        rigntMenu.addItem("Log out", e -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate("");
        });

        Image image = new Image("images/logo.png", "WebFS");
        image.setMaxHeight("48pt");
        image.addClickListener(e -> UI.getCurrent().navigate(""));
        add(image);

        addAndExpand(leftMenu);
        add(rigntMenu);
    }
}
