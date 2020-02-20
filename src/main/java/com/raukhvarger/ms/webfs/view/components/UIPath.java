package com.raukhvarger.ms.webfs.view.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class UIPath extends HorizontalLayout {

    private TextField path = new TextField();
    private Button go = new Button("", new Icon(VaadinIcon.ARROW_RIGHT));

    public UIPath() {
        setWidthFull();
        path.setWidthFull();
        add(path);
        add(go);
    }
}
