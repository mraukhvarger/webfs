package com.raukhvarger.ms.webfs.front.view.component;

import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.front.service.DataProviders;
import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class UIPath extends HorizontalLayout {

    private final Logger logger = LoggerFactory.getLogger(UIPath.class);

    private TextField path = new TextField();
    private Button go = new Button(new Icon(VaadinIcon.ARROW_RIGHT));
    private Button up = new Button(new Icon(VaadinIcon.LEVEL_UP));
    private Button newFolder = new Button(new Icon(VaadinIcon.FOLDER_ADD));
    private Button newFile = new Button(new Icon(VaadinIcon.FILE_ADD));

    private final UIEvents uiEvents;
    private final DataProviders dataProviders;

    public UIPath(UIEvents uiEvents, DataProviders dataProviders) {
        this.uiEvents = uiEvents;
        this.dataProviders = dataProviders;
    }

    @PostConstruct
    private void init() {
        setWidthFull();
        addAndExpand(path);
        add(go);
        add(up);
        add(newFolder);
        add(newFile);

        path.addKeyPressListener(Key.ENTER, uiEvents.getOpenFolderEvent(() -> path.getValue()));
        go.addClickListener(uiEvents.getOpenFolderEvent(() -> path.getValue()));
        up.addClickListener(uiEvents.getOpenFolderParentEvent(() -> path.getValue()));
        newFolder.addClickListener(uiEvents.getCreateFolderClickEvent());
        newFile.addClickListener(uiEvents.getCreateFileClickEvent());

        dataProviders.getMainFormBinder().bind(path, MainFormModel::getPathFieldValue, MainFormModel::setPathFieldValue);
    }
}
