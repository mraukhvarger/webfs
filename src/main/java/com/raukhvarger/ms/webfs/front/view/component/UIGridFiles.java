package com.raukhvarger.ms.webfs.front.view.component;

import com.raukhvarger.ms.webfs.front.service.DataProviders;
import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
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
public class UIGridFiles extends Grid<FileViewerItem> {

    private final Logger logger = LoggerFactory.getLogger(UIGridFiles.class);

    @Autowired
    private DataProviders dataProviders;

    @Autowired
    private UIEvents uiEvents;

    @PostConstruct
    public void init() {
        setSizeFull();

        addColumn(new ComponentRenderer<>(f -> {
            if (f.getView() != null) {
                Button b = new Button("Open", uiEvents.getOpenFileWithViewerEvent(f));
                b.setSizeFull();
                return b;
            }
            else
                return new Label();
        })).setHeader("Open with Viewer").setAutoWidth(true).setFlexGrow(0);

        Grid self = this;
        addColumn(new ComponentRenderer<>(f -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.add(new Icon(f.getIcon()));
            hl.addAndExpand(new Label(f.getName()));
            hl.addClickListener(e -> self.select(f));
            return hl;
        })).setHeader("File name");

        addColumn(FileViewerItem::getSize).setHeader("Size").setWidth("90pt").setFlexGrow(0);

        setDataProvider(dataProviders.getFilesInFolderProvider());
    }

}
