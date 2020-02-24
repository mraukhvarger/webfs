package com.raukhvarger.ms.webfs.front.view.component;

import com.raukhvarger.ms.webfs.front.service.UIControls;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
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
    private UIControls uiControls;

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
        tabs.getElement().setAttribute("style", getElement().getAttribute("style") + "; margin: 0;" );

        Tab fmTab = tabs.add(uiFileManager, "File manager");
        Tab vTab = tabs.add(uiViewer, "Viewer");

        uiControls.setOpenViewerTabAction(() -> {
            tabs.select(vTab);
            return uiViewer.getContentLayout();
        });

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
