package com.raukhvarger.ms.webfs.front.view.component;

import com.raukhvarger.ms.webfs.front.service.UIControls;
import com.raukhvarger.ms.webfs.utils.CustomPagedTabs;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.vaadin.tabs.PagedTabs;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class UITabs extends VerticalLayout {

    private final Logger logger = LoggerFactory.getLogger(UITabs.class);

    private final UIControls uiControls;
    private final UIFileManager uiFileManager;
    private final UIViewer uiViewer;

    public UITabs(UIControls uiControls, UIFileManager uiFileManager, UIViewer uiViewer) {
        this.uiControls = uiControls;
        this.uiFileManager = uiFileManager;
        this.uiViewer = uiViewer;
    }

    @PostConstruct
    private void init() {
        setSizeFull();
        setPadding(false);

        PagedTabs tabs = new CustomPagedTabs();
        tabs.setSizeFull();
        tabs.getElement().setAttribute("style", getElement().getAttribute("style") + "; margin: 0;" );

        Tab fmTab = tabs.add(uiFileManager, "File manager");
        Tab vTab = tabs.add(uiViewer, "Viewer");

        uiControls.setOpenViewerTabAction((createConcreteViewer) -> {
            tabs.select(vTab);
            createConcreteViewer.accept(uiViewer.getContentLayout());
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
