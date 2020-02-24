package com.raukhvarger.ms.webfs.front.view.fileviewer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@FileViewer(extension = "txt", icon = VaadinIcon.FILE_TEXT)
public class FileTxtViewer extends VerticalLayout {

    private final Logger logger = LoggerFactory.getLogger(FileTxtViewer.class);

    private TextArea textArea = new TextArea();
    private HorizontalLayout hl = new HorizontalLayout();
    private Button save = new Button(new Icon(VaadinIcon.CLOUD_UPLOAD));
    private Button reload = new Button(new Icon(VaadinIcon.CLOUD_DOWNLOAD));

    private final Path path;

    public FileTxtViewer(Path path) {
        this.path = path;
        init();
        reload();
    }

    private void save() {
        try {
            Files.write(path, textArea.getValue().getBytes());
        } catch (IOException e) {
            logger.error(String.format("Failed save file '%s'", path.toAbsolutePath().toString()), e);
        }
    }

    private void reload() {
        try {
            textArea.setValue(new String(Files.readAllBytes(path)));
        } catch (IOException e) {
            logger.error(String.format("Failed load file '%s'", path.toAbsolutePath().toString()), e);
        }
    }

    private void init() {
        setSizeFull();
        setPadding(false);

        textArea.setSizeFull();

        save.addClickListener(e -> save());
        reload.addClickListener(e -> reload());

        hl.add(save);
        hl.add(reload);

        add(hl);
        addAndExpand(textArea);
        textArea.getElement().callJsFunction("focus");
    }

}
