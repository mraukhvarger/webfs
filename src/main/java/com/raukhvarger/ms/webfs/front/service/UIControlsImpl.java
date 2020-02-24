package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.raukhvarger.ms.webfs.service.FilesService;
import com.raukhvarger.ms.webfs.spring.AppConfig;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.function.Supplier;

@Service
@Scope("session")
public class UIControlsImpl implements UIControls {

    private final Logger logger = LoggerFactory.getLogger(UIControlsImpl.class);

    @Autowired
    private DataProviders dataProviders;

    @Autowired
    private FilesService filesService;

    @Autowired
    private AppConfig appConfig;

    private Supplier<VerticalLayout> openViewerTabAction;

    @Override
    public void openFolder(Path path) {
        dataProviders.updateFoldersData(filesService.getFoldersByFolder(path));
        dataProviders.updateFilesInFolderData(filesService.getFilesByFolder(path));
        dataProviders.getMainFormBinder().readBean(MainFormModel.builder().pathFieldValue(path.toAbsolutePath().toString()).build());
    }

    @Override
    public void openStartFolder() {
        openFolder(new File(appConfig.getStartDir()).toPath());
    }

    @Override
    public void openFile(FileViewerItem fileItem) {
        try {
            Constructor<?> constructor = fileItem.getView().getConstructor(Path.class);
            Object view = constructor.newInstance(fileItem.getPath());
            VerticalLayout vl = openViewerTabAction.get();
            vl.removeAll();
            vl.add((Component) view);
        } catch (Exception e) {
            logger.error("Failed file extensions??? 0_o", e);
        }
    }

    @Override
    public void setOpenViewerTabAction(Supplier<VerticalLayout> action) {
        openViewerTabAction = action;
    }

}
