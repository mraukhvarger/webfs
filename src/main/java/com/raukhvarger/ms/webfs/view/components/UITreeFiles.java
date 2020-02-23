package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.front.service.DataProviders;
import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.vaadin.flow.component.treegrid.TreeGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class UITreeFiles extends TreeGrid<FileEntity> {

    @Autowired
    private DataProviders dataProviders;

    @Autowired
    private UIEvents uiEvents;

    @PostConstruct
    public void init() {
        setSizeFull();

        addHierarchyColumn(FileEntity::getName);
        setDataProvider(dataProviders.getFoldersProvider());

        addItemClickListener(uiEvents.getFolderClickEvent());
    }
}
