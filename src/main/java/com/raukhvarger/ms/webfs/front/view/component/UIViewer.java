package com.raukhvarger.ms.webfs.front.view.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class UIViewer extends VerticalLayout {

    private VerticalLayout vl = new VerticalLayout();

    @PostConstruct
    private void init() {
        setPadding(false);
        vl.setPadding(false);
        setSizeFull();
        addAndExpand(vl);
    }

    public VerticalLayout getContentLayout() {
        return vl;
    }

}
