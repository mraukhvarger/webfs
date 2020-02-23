package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.service.FilesService;
import com.raukhvarger.ms.webfs.spring.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
@Scope("session")
public class UIControlsImpl implements UIControls {

    @Autowired
    private DataProviders dataProviders;

    @Autowired
    private FilesService filesService;

    @Autowired
    private AppConfig appConfig;

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
}
