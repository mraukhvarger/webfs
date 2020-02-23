package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
@Scope("session")
public class UIPath extends HorizontalLayout {

    private final Logger logger = LoggerFactory.getLogger(UIPath.class);

    private TextField path = new TextField();
    private Button go = new Button(new Icon(VaadinIcon.ARROW_RIGHT));
    private Button add = new Button(new Icon(VaadinIcon.PLUS));

    @Autowired
    private UIEvents uiEvents;

    @Autowired
    @Qualifier("mainFormBinder")
    private Binder<MainFormModel> binder;

    @PostConstruct
    public void init() {
        setWidthFull();
        path.setWidthFull();
        add(path);
        add(go);

        path.addKeyPressListener(Key.ENTER, uiEvents.getOpenFolderEvent(() -> path.getValue()));
        go.addClickListener(uiEvents.getOpenFolderEvent(() -> path.getValue()));

        binder.bind(path, MainFormModel::getPathFieldValue, MainFormModel::setPathFieldValue);
    }
}
