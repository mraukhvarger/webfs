package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.entity.Person;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;

public class UITreeFiles extends TreeGrid<FileEntity> {

    private FileEntity[] files = {
            new FileEntity("/"),
            new FileEntity("usr"),
            new FileEntity("var")
    };

    public UITreeFiles() {
        setSizeFull();

        TreeData<FileEntity> td = new TreeData<>();

        addHierarchyColumn(FileEntity::getName);
        td.addItems(null, files[0]);
        td.addItems(files[0], files[1]);
        td.addItems(files[1], files[2]);

        setDataProvider(new TreeDataProvider<>(td));
        expand(files);
    }
}
