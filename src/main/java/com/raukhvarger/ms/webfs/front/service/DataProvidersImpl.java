package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("session")
public class DataProvidersImpl implements DataProviders {

    @Autowired
    @Qualifier("filesInFolderData")
    private List<FileEntity> filesInFolderData;

    @Autowired
    @Qualifier("foldersData")
    private List<FileEntity> foldersData;

    @Autowired
    @Qualifier("filesInFolderProvider")
    private CallbackDataProvider<FileEntity, Void> filesInFolderProvider;

    @Autowired
    @Qualifier("foldersProvider")
    private HierarchicalDataProvider<FileEntity, Void> foldersProvider;

    @Override
    public void updateFilesInFolderData(List<FileEntity> files) {
        filesInFolderData.clear();
        filesInFolderData.addAll(files);
        filesInFolderProvider.refreshAll();
    }

    @Override
    public void updateFoldersData(List<FileEntity> folders) {
        foldersData.clear();
        foldersData.addAll(folders);
        foldersProvider.refreshAll();
        updateFilesInFolderData(new ArrayList<>());
    }
}
