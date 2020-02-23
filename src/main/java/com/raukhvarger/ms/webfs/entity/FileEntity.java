package com.raukhvarger.ms.webfs.entity;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.*;

import java.io.File;
import java.nio.file.Path;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
//@Document(collection = "Files")
public class FileEntity {

//    @MongoId(FieldType.OBJECT_ID)
    String id;

    @NonNull
//    @Field(name = "Name")
    private String name;

//    @Field(name = "Size")
    private Long size = 0L;

//    @Field(name = "Is_Dir")
    private Boolean isDir = false;

    private Path path;

    public Icon getIcon() {
        if (isDir)
            return new Icon(VaadinIcon.FOLDER);
        else
            return new Icon(VaadinIcon.FILE_O);
    }

    public FileEntity(File file) {
        name = file.getName();
        size = file.getTotalSpace();
        isDir = file.isDirectory();
        path = file.toPath();
    }
}