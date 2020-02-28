package com.raukhvarger.ms.webfs.front.view.fileviewer.ext;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.nio.file.Path;

public interface ViewerExtension {

    String getExtension();

    default VaadinIcon getIcon() {
        return VaadinIcon.FILE_O;
    }

    Component loadFileInViewer(Path path);

}
