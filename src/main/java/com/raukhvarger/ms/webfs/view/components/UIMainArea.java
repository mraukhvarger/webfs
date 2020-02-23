package com.raukhvarger.ms.webfs.view.components;

import com.raukhvarger.ms.webfs.front.service.UIEvents;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class UIMainArea extends SplitLayout {

    private final Logger logger = LoggerFactory.getLogger(UIMainArea.class);

    @Autowired
    private UITreeFiles uiTreeFiles;

    @Autowired
    private UIGridFiles uiGridFiles;

    @Autowired
    private UIEvents uiEvents;

    @PostConstruct
    void init() {
        addToPrimary(uiTreeFiles);
        addToSecondary(rightPanel());
        setSizeFull();
    }

    private VerticalLayout rightPanel() {
        MemoryBuffer buffer = new MemoryBuffer();

        HorizontalLayout hl = new HorizontalLayout();
        Upload upload = new Upload(buffer);
        upload.addSucceededListener(uiEvents.getUploadFileEvent(buffer));
        hl.addAndExpand(upload);

        VerticalLayout right = new VerticalLayout();
        right.setPadding(false);
        right.addAndExpand(uiGridFiles);
        right.add(hl);
        return right;
    }

}
