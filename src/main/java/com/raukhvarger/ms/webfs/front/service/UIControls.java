package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface UIControls {

    void openFolder(Path path);

    void openStartFolder();

    void openFile(FileViewerItem fileItem);

    void setOpenViewerTabAction(Consumer<Consumer<VerticalLayout>> action);

}
