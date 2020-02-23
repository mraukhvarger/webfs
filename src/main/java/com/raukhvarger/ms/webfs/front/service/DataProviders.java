package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;

import java.util.List;

public interface DataProviders {

    void updateFilesInFolderData(List<FileEntity> files);

    void updateFoldersData(List<FileEntity> files);

}
