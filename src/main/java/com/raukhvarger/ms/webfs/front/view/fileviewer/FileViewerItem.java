package com.raukhvarger.ms.webfs.front.view.fileviewer;

import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;

@Data
@Builder
public class FileViewerItem {

    private String name;

    @Builder.Default
    private String size = "0";

    @Builder.Default
    private Boolean isFolder = false;

    private Path path;

    private VaadinIcon icon;

    private String extension;

    private Class view;

}
