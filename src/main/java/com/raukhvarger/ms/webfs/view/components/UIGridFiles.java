package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.vaadin.flow.component.grid.Grid;

public class UIGridFiles extends Grid<FileEntity> {

    private FileEntity[] files = {
            new FileEntity("readme.txt"),
            new FileEntity("console.log"),
            new FileEntity("app.bin")
    };

    public UIGridFiles() {
        setSizeFull();

        setItems(files);
        addColumn(FileEntity::getName).setHeader("File name");
    }
}
