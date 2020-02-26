package com.raukhvarger.ms.webfs.front.view.fileviewer;

import com.raukhvarger.ms.webfs.utils.CorrectTextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@FileViewer(extension = "txt", icon = VaadinIcon.FILE_TEXT)
public class FileTxtViewer extends VerticalLayout {

    private final Logger logger = LoggerFactory.getLogger(FileTxtViewer.class);

    private CorrectTextArea textArea = new CorrectTextArea();
    private HorizontalLayout hl = new HorizontalLayout();
    private Button save = new Button(new Icon(VaadinIcon.CLOUD_UPLOAD));
    private Button reload = new Button(new Icon(VaadinIcon.REFRESH));

    private Path path;

    public FileTxtViewer(Path path) {
        this.path = path;
        init();
    }

    private void save() {
        try {
            logger.info("Save file: " + path.toAbsolutePath().toString());
            Files.write(path, textArea.getValue().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error(String.format("Failed save file '%s'", path.toAbsolutePath().toString()), e);
        }
    }

    private void reload() {
        try {
            logger.info("Load file: " + path.toAbsolutePath().toString());
            String data = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            logger.info("File size: " + data.length());
            textArea.setValue(data);
        } catch (IOException e) {
            logger.error(String.format("Failed load file '%s'", path.toAbsolutePath().toString()), e);
        }
    }

    private void init() {
        reload();
        setSizeFull();
        setPadding(false);

        save.addClickListener(e -> save());
        reload.addClickListener(e -> reload());

        hl.add(save);
        hl.add(reload);

        add(hl);
        addAndExpand(textArea);
        textArea.getElement().callJsFunction("focus");
    }

}
