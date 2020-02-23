package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataKeyMapper;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.IconRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.Rendering;
import com.vaadin.flow.dom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class UIGridFiles extends Grid<FileEntity> {

    private final Logger logger = LoggerFactory.getLogger(UIGridFiles.class);


    private FileEntity[] files = {
            new FileEntity("readme.txt"),
            new FileEntity("console.log"),
            new FileEntity("app.bin")
    };

    @Autowired
    @Qualifier("filesInFolderProvider")
    private CallbackDataProvider<FileEntity, Void> filesInFolderProvider;

    @PostConstruct
    public void init() {
        setSizeFull();

        setItems(files);
        Grid self = this;
        addColumn(new ComponentRenderer<>(f -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.add(f.getIcon());
            hl.addAndExpand(new Label(f.getName()));
            hl.addClickListener(e -> self.select(f));
            return hl;
        })).setHeader("File name");


        addSelectionListener(e -> logger.info(e.toString()));

        this.setDataProvider(filesInFolderProvider);
    }
}
