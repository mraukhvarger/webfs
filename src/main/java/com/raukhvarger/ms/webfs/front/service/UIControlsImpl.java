package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.raukhvarger.ms.webfs.service.FilesService;
import com.raukhvarger.ms.webfs.spring.AppProperties;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Consumer;

@Service
@UIScope
public class UIControlsImpl implements UIControls {

    private final Logger logger = LoggerFactory.getLogger(UIControlsImpl.class);

    private Consumer<Consumer<VerticalLayout>> openViewerTabAction;

    private final DataProviders dataProviders;
    private final FilesService filesService;
    private final AppProperties appProperties;

    public UIControlsImpl(DataProviders dataProviders, FilesService filesService, AppProperties appProperties) {
        this.dataProviders = dataProviders;
        this.filesService = filesService;
        this.appProperties = appProperties;
    }

    @Override
    public void openFolder(Path path) {
        dataProviders.updateFoldersData(filesService.getFoldersByFolder(path));
        dataProviders.updateFilesInFolderData(filesService.getFilesByFolder(path));
        dataProviders.getMainFormBinder().readBean(MainFormModel.builder().pathFieldValue(path.toAbsolutePath().toString()).build());
    }

    @Override
    public void openStartFolder() {
        openFolder(new File(appProperties.getStartDir()).toPath());
    }


    @Override
    public void openFile(FileViewerItem fileItem) {
        try {
            openViewerTabAction.accept(vl -> {
                vl.getChildren().forEach(c -> c.getElement().removeFromTree());
                vl.removeAll();
                vl.add(fileItem.getView().apply(fileItem.getPath()));
            });
        } catch (Exception e) {
            logger.error("Failed file extensions??? 0_o", e);
        }
    }

    @Override
    public void setOpenViewerTabAction(Consumer<Consumer<VerticalLayout>> action) {
        openViewerTabAction = action;
    }

}
