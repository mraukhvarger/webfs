package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;

import java.nio.file.Path;
import java.util.List;

public interface DataProviders {

    List<FileEntity> getFilesInFolderData();

    List<FileEntity> getFoldersData();

    Path getCurrentFolder();

    Binder<MainFormModel> getMainFormBinder();

    DataProvider<FileEntity, Void> getFilesInFolderProvider();

    DataProvider<FileEntity, Void>getFoldersProvider();

    void updateFilesInFolderData(List<FileEntity> files);

    void updateFilesInFolderData();

    void updateFoldersData(List<FileEntity> files);

    void updateFoldersData();

}
