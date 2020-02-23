package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.front.service.DataProviders;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class UIGridFiles extends Grid<FileEntity> {

    private final Logger logger = LoggerFactory.getLogger(UIGridFiles.class);

    @Autowired
    private DataProviders dataProviders;

    @PostConstruct
    public void init() {
        setSizeFull();

        Grid self = this;
        addColumn(new ComponentRenderer<>(f -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.add(f.getIcon());
            hl.addAndExpand(new Label(f.getName()));
            hl.addClickListener(e -> self.select(f));
            return hl;
        })).setHeader("File name");


        addSelectionListener(e -> logger.info(e.toString()));

        setDataProvider(dataProviders.getFilesInFolderProvider());
    }
}
