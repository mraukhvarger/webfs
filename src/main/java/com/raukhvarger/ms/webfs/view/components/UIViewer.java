package com.raukhvarger.ms.webfs.view.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class UIViewer extends VerticalLayout {

    @PostConstruct
    private void init() {
        addAndExpand(new Button("test"));
    }

}
