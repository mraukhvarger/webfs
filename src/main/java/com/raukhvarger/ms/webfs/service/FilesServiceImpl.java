package com.raukhvarger.ms.webfs.service;

import com.raukhvarger.ms.webfs.front.service.FileViewerExtensions;
import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.raukhvarger.ms.webfs.front.view.fileviewer.ext.ViewerExtension;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@UIScope
public class FilesServiceImpl implements FilesService {

    private final Logger logger = LoggerFactory.getLogger(FilesServiceImpl.class);

    private final FileViewerExtensions fileExtensions;

    public FilesServiceImpl(FileViewerExtensions fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    public List<FileViewerItem> getAllFilesByFolder(Path path) {
        if (path.toFile().listFiles() == null)
            return new ArrayList<>();

        List<File> files = new ArrayList<>();
        Collections.addAll(files, path.toFile().listFiles());
        return files.stream().map(f -> {
            Path filePath = f.toPath();
            String ext = getFileExtension(filePath);
            Optional<ViewerExtension> viewerExtension = fileExtensions.createExtension(ext);
            FileViewerItem fileViewerItem = FileViewerItem.builder()
                    .name(f.getName())
                    .extension(ext)
                    .size(FileUtils.byteCountToDisplaySize(f.length()))
                    .icon(VaadinIcon.FILE_O)
                    .isFolder(f.isDirectory())
                    .path(filePath)
                    .build();
            viewerExtension.ifPresent(v -> {
                fileViewerItem.setIcon(v.getIcon());
                fileViewerItem.setView(v::loadFileInViewer);
            });
            return fileViewerItem;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FileViewerItem> getFilesByFolder(Path path) {
        return getAllFilesByFolder(path).stream().filter(f -> !f.getIsFolder()).collect(Collectors.toList());
    }

    @Override
    public List<FileViewerItem> getFoldersByFolder(Path path) {
        return getAllFilesByFolder(path).stream().filter(FileViewerItem::getIsFolder).collect(Collectors.toList());
    }

    private String getFileExtension(Path path) {
        File f = path.toFile();
        return f.getName().contains(".") ? f.getName().substring(f.getName().lastIndexOf(".") + 1) : "";
    }

}
