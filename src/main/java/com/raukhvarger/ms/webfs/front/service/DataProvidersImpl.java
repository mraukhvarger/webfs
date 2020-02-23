package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.service.FilesService;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Scope("session")
public class DataProvidersImpl implements DataProviders {

    private final Logger logger = LoggerFactory.getLogger(DataProvidersImpl.class);

    private List<FileEntity> filesInFolderData;

    private List<FileEntity> foldersData;

    private Binder<MainFormModel> mainFormBinder;

    private CallbackDataProvider<FileEntity, Void> filesInFolderProvider;

    private HierarchicalDataProvider<FileEntity, Void> foldersProvider;

    @Autowired
    private FilesService filesService;

    @PostConstruct
    private void init() {
        filesInFolderData = new ArrayList<>();
        foldersData = new ArrayList<>();
        mainFormBinder = new Binder<>();

        // Инициализация провайдера данных для таблицы файлов, внутри папки
        filesInFolderProvider =
                new CallbackDataProvider<>(q -> filesInFolderData.subList(
                        q.getOffset(), q.getOffset() + q.getLimit()).stream(),
                        q -> filesInFolderData.size());

        // Инициализация провайдера данных для дерева папок
        foldersProvider = new AbstractBackEndHierarchicalDataProvider<FileEntity, Void>() {
            @Override
            public int getChildCount(HierarchicalQuery<FileEntity, Void> query) {
                if (query.getParent() == null)
                    return foldersData.size();
                else
                    return filesService.getFoldersByFolder(query.getParent().getPath()).size();
            }

            @Override
            public boolean hasChildren(FileEntity item) {
                return filesService.getFoldersByFolder(item.getPath()).size() > 0;
            }

            @Override
            protected Stream<FileEntity> fetchChildrenFromBackEnd(
                    HierarchicalQuery<FileEntity, Void> query) {
                if (query.getParent() == null)
                    return foldersData.stream();
                else
                    return filesService.getFoldersByFolder(query.getParent().getPath()).stream();
            }
        };
    }

    @Override
    public List<FileEntity> getFilesInFolderData() {
        return filesInFolderData;
    }

    @Override
    public List<FileEntity> getFoldersData() {
        return foldersData;
    }

    @Override
    public Path getCurrentFolder() {
        MainFormModel model = new MainFormModel();
        try {
            mainFormBinder.writeBean(model);
        } catch (ValidationException e) {
            logger.error("Read current path is faild: " + e.getLocalizedMessage(), e);
        }
        return new File(model.getPathFieldValue()).toPath();
    }

    @Override
    public Binder<MainFormModel> getMainFormBinder() {
        return mainFormBinder;
    }

    @Override
    public DataProvider<FileEntity, Void> getFilesInFolderProvider() {
        return filesInFolderProvider;
    }

    @Override
    public DataProvider<FileEntity, Void> getFoldersProvider() {
        return foldersProvider;
    }

    @Override
    public void updateFilesInFolderData(List<FileEntity> files) {
        filesInFolderData.clear();
        filesInFolderData.addAll(files);
        filesInFolderProvider.refreshAll();
    }

    @Override
    public void updateFilesInFolderData() {
        updateFilesInFolderData(filesService.getFilesByFolder(getCurrentFolder()));
    }

    @Override
    public void updateFoldersData(List<FileEntity> folders) {
        foldersData.clear();
        foldersData.addAll(folders);
        foldersProvider.refreshAll();
        updateFilesInFolderData(new ArrayList<>());
    }

    @Override
    public void updateFoldersData() {
        updateFoldersData(filesService.getFoldersByFolder(getCurrentFolder()));
    }

}
