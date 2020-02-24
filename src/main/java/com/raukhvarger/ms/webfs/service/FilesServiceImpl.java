package com.raukhvarger.ms.webfs.service;

import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewer;
import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilesServiceImpl implements FilesService {

    private List<FileViewerItem> fileViewerItems;

    @PostConstruct
    private void init() {
        fileViewerItems = getFileViewerClasses();
    }

    public List<FileViewerItem> getAllFilesByFolder(Path path) {
        if (path.toFile().listFiles() == null)
            return new ArrayList<>();

        List<File> files = new ArrayList<>();
        Collections.addAll(files, path.toFile().listFiles());
        return files.stream().map(f -> {
            Path filePath = f.toPath();
            FileViewerItem extFile = getFileViewer(filePath);
            return FileViewerItem.builder()
                    .name(f.getName())
                    .extension(getFileExtension(filePath))
                    .size(FileUtils.byteCountToDisplaySize(f.length()))
                    .icon(extFile.getIcon())
                    .isFolder(f.isDirectory())
                    .view(extFile.getView())
                    .path(filePath)
                    .build();
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

    private List<FileViewerItem> getFileViewerClasses() {
        List<FileViewerItem> result = new ArrayList<>();

        Reflections ref = new Reflections("com.raukhvarger.ms.webfs.front.view.fileviewer");
        for (Class<?> cl : ref.getTypesAnnotatedWith(FileViewer.class)) {
            result.add(
                    FileViewerItem.builder()
                            .view(cl)
                            .extension(cl.getAnnotation(FileViewer.class).extension())
                            .icon(cl.getAnnotation(FileViewer.class).icon())
                            .build()
            );
        }

        return result;
    }

    @Override
    public FileViewerItem getFileViewer(Path path) {
        return fileViewerItems.stream()
                .filter(ext -> getFileExtension(path).endsWith(ext.getExtension()))
                .findFirst()
                .orElse(FileViewerItem.builder()
                        .icon(VaadinIcon.FILE_O)
                        .build()
                );
    }

}
