package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.raukhvarger.ms.webfs.service.FilesService;
import com.raukhvarger.ms.webfs.front.service.DataProviders;
import com.raukhvarger.ms.webfs.spring.AppConfig;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
@Scope("session")
public class UITreeFiles extends TreeGrid<FileEntity> {

    @Autowired
    private FilesService filesService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private DataProviders dataProviders;

    @Autowired
    private UIEvents uiEvents;

    @Autowired
    @Qualifier("foldersProvider")
    private HierarchicalDataProvider foldersProvider;

    @PostConstruct
    public void init() {
        setSizeFull();

        addHierarchyColumn(FileEntity::getName);
//
//        HierarchicalDataProvider dataProvider =
//                new AbstractBackEndHierarchicalDataProvider<FileEntity, Void>() {
//                    @Override
//                    public int getChildCount(HierarchicalQuery<FileEntity, Void> query) {
//                        if (query.getParent() == null)
//                            return filesService.getFoldersByFolder(new File(appConfig.getStartDir()).toPath()).size();
//                        else
//                            return filesService.getFoldersByFolder(query.getParent().getPath()).size();
//                    }
//
//                    @Override
//                    public boolean hasChildren(FileEntity item) {
//                        return filesService.getFoldersByFolder(item.getPath()).size() > 0;
//                    }
//
//                    @Override
//                    protected Stream<FileEntity> fetchChildrenFromBackEnd(
//                            HierarchicalQuery<FileEntity, Void> query) {
//                        if (query.getParent() == null)
//                            return filesService.getFoldersByFolder(new File(appConfig.getStartDir()).toPath()).stream();
//                        else
//                            return filesService.getFoldersByFolder(query.getParent().getPath()).stream();
//                    }
//                };
        setDataProvider(foldersProvider);
        dataProviders.updateFoldersData(filesService.getFoldersByFolder(new File(appConfig.getStartDir()).toPath()));

        addItemClickListener(uiEvents.getFolderClickEvent());

    }
}
