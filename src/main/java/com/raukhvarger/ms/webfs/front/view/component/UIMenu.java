package com.raukhvarger.ms.webfs.front.view.component;

import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.StreamResource;
import org.bouncycastle.util.io.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@Scope("session")
@StyleSheet("login")
public class UIMenu extends HorizontalLayout {

    private final Logger logger = LoggerFactory.getLogger(UIMenu.class);

    @Autowired
    private UIEvents uiEvents;

    @PostConstruct
    private void init() throws IOException {
        setWidthFull();

        MenuBar leftMenu = new MenuBar();

        MenuBar rigntMenu = new MenuBar();
        rigntMenu.addItem("Log out", e -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate("");
        });

        byte[] imageBytes = Streams.readAll(getClass().getClassLoader().getResourceAsStream("images/logo.png"));
        StreamResource resource = new StreamResource("logo.png", () -> new ByteArrayInputStream(imageBytes));
        Image image = new Image(resource, "WebFS");
        image.setMaxHeight("48pt");
        image.addClickListener(e -> UI.getCurrent().navigate(""));
        add(image);

        addAndExpand(leftMenu);
        add(rigntMenu);
    }
}
