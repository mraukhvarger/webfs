package com.raukhvarger.ms.webfs.front.spring;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.front.model.MainFormModel;
import com.raukhvarger.ms.webfs.service.FilesService;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Configuration
public class UIBeansConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    @Scope("session")
    @Qualifier("filesInFolderData")
    public List<FileEntity> getFilesInFolder() {
        return new ArrayList<>();
    }

    @Bean
    @Scope("session")
    @Qualifier("filesInFolderProvider")
    public CallbackDataProvider<FileEntity, Void> getFilesInFolderDataProvider(@Autowired @Qualifier("filesInFolderData") List<FileEntity> files) {
        return new CallbackDataProvider<>(q -> files.subList(q.getOffset(), q.getOffset() + q.getLimit()).stream(), q -> files.size());
    }

    @Bean
    @Scope("session")
    @Qualifier("foldersData")
    public List<FileEntity> getFoldersData() {
        return new ArrayList<>();
    }

    @Bean
    @Scope("session")
    @Qualifier("foldersProvider")
    public HierarchicalDataProvider<FileEntity, Void> getFoldersDataProvider(@Autowired @Qualifier("foldersData") List<FileEntity> folders,
                                                                             @Autowired FilesService filesService) {
        return new AbstractBackEndHierarchicalDataProvider<FileEntity, Void>() {
            @Override
            public int getChildCount(HierarchicalQuery<FileEntity, Void> query) {
                if (query.getParent() == null)
                    return folders.size();
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
                    return folders.stream();
                else
                    return filesService.getFoldersByFolder(query.getParent().getPath()).stream();
            }
        };
    }

    @Bean
    @Scope("session")
    @Qualifier("mainFormBinder")
    public Binder<MainFormModel> getMainFormBinder() {
        return new Binder<>();
    }

}
