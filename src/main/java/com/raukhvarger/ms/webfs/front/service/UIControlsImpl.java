package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@Scope("session")
public class UIControlsImpl implements UIControls {

    @Autowired
    private DataProviders dataProviders;

    @Autowired
    private FilesService filesService;

    @Override
    public void openFolder(Path path) {
        dataProviders.updateFoldersData(filesService.getFoldersByFolder(path));
        dataProviders.updateFilesInFolderData(filesService.getFilesByFolder(path));
    }
}
