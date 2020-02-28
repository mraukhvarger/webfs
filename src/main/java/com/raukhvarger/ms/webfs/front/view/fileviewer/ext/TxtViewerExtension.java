package com.raukhvarger.ms.webfs.front.view.fileviewer.ext;

import com.raukhvarger.ms.webfs.utils.CorrectTextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component("txtViewer")
@Scope("prototype")
public class TxtViewerExtension extends VerticalLayout implements ViewerExtension {

    private final Logger logger = LoggerFactory.getLogger(TxtViewerExtension.class);

    private CorrectTextArea textArea = new CorrectTextArea();
    private HorizontalLayout hl = new HorizontalLayout();
    private Button save = new Button(new Icon(VaadinIcon.CLOUD_UPLOAD));
    private Button reload = new Button(new Icon(VaadinIcon.REFRESH));

    private Path path;

    public TxtViewerExtension() {
        init();
    }

    @Override
    public String getExtension() {
        return "txt";
    }

    @Override
    public VaadinIcon getIcon() {
        return VaadinIcon.FILE_TEXT;
    }

    @Override
    public com.vaadin.flow.component.Component loadFileInViewer(Path path) {
        reload(path);
        return this;
    }

    private void save() {
        try {
            logger.info("Save file: " + path.toAbsolutePath().toString());
            Files.write(path, textArea.getValue().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error(String.format("Failed save file '%s'", path.toAbsolutePath().toString()), e);
        }
    }

    private void reload(Path path) {
        this.path = path;
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
        setSizeFull();
        setPadding(false);

        save.addClickListener(e -> save());
        reload.addClickListener(e -> reload(path));

        hl.add(save);
        hl.add(reload);

        add(hl);
        addAndExpand(textArea);
        textArea.getElement().callJsFunction("focus");
    }

}
