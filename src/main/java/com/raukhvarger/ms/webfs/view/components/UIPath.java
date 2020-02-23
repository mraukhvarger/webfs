package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.front.service.DataProviders;
import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class UIPath extends HorizontalLayout {

    private final Logger logger = LoggerFactory.getLogger(UIPath.class);

    private TextField path = new TextField();
    private Button go = new Button(new Icon(VaadinIcon.ARROW_RIGHT));
    private Button up = new Button(new Icon(VaadinIcon.LEVEL_UP));

    @Autowired
    private UIEvents uiEvents;

    @Autowired
    private DataProviders dataProviders;

    @PostConstruct
    private void init() {
        setWidthFull();
        add(path);
        add(go);
        add(up);

        path.setWidthFull();
        path.addKeyPressListener(Key.ENTER, uiEvents.getOpenFolderEvent(() -> path.getValue()));
        go.addClickListener(uiEvents.getOpenFolderEvent(() -> path.getValue()));
        up.addClickListener(uiEvents.getOpenFolderParentEvent(() -> path.getValue()));

        dataProviders.getMainFormBinder().bind(path, MainFormModel::getPathFieldValue, MainFormModel::setPathFieldValue);
    }
}
