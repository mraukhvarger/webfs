package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.nio.file.Path;
import java.util.function.Supplier;

public interface UIControls {

    void openFolder(Path path);

    void openStartFolder();

    void openFile(FileViewerItem fileItem);

    void setOpenViewerTabAction(Supplier<VerticalLayout> action);

}
