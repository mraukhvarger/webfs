package com.raukhvarger.ms.webfs.view.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.tabs.PagedTabs;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class UITabs extends VerticalLayout {

    private final Logger logger = LoggerFactory.getLogger(UITabs.class);

    @Autowired
    private UIFileManager uiFileManager;

    @Autowired
    private UIViewer uiViewer;

    @PostConstruct
    private void init() {
        setSizeFull();
        setPadding(false);

        PagedTabs tabs = new PagedTabs();
        tabs.setSizeFull();

        tabs.add(uiFileManager, "File manager");
        tabs.add(uiViewer, "Viewer");

        addAndExpand(tabs);

        tabs.getContent().setPadding(false);
        tabs.getContent().setSizeFull();
        tabs.getContent().getChildren()
                .filter(c -> c instanceof VerticalLayout)
                .forEach(c -> {
                    ((VerticalLayout) c).setSizeFull();
                    ((VerticalLayout) c).setPadding(false);
                });
    }

}
