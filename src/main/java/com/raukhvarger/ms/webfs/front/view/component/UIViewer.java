package com.raukhvarger.ms.webfs.front.view.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
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
