package com.raukhvarger.ms.webfs.front.view.component;

import com.raukhvarger.ms.webfs.front.service.DataProviders;
import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class UITreeFiles extends TreeGrid<FileViewerItem> {

    private final DataProviders dataProviders;
    private final UIEvents uiEvents;

    public UITreeFiles(DataProviders dataProviders, UIEvents uiEvents) {
        this.dataProviders = dataProviders;
        this.uiEvents = uiEvents;
    }

    @PostConstruct
    public void init() {
        setSizeFull();

        addHierarchyColumn(FileViewerItem::getName);
        setDataProvider(dataProviders.getFoldersProvider());

        addItemClickListener(uiEvents.getFolderClickEvent());
    }
}
