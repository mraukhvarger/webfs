package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;

import java.nio.file.Path;
import java.util.List;

public interface DataProviders {

    List<FileViewerItem> getFilesInFolderData();

    List<FileViewerItem> getFoldersData();

    Path getCurrentFolder();

    Binder<MainFormModel> getMainFormBinder();

    DataProvider<FileViewerItem, Void> getFilesInFolderProvider();

    DataProvider<FileViewerItem, Void>getFoldersProvider();

    void updateFilesInFolderData(List<FileViewerItem> files);

    void updateFilesInFolderData();

    void updateFoldersData(List<FileViewerItem> files);

    void updateFoldersData();

}
