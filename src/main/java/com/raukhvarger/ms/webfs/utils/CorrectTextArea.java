package com.raukhvarger.ms.webfs.utils;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.textfield.TextField;

@Tag("textarea")
public class CorrectTextArea extends TextField {

    public CorrectTextArea() {
        setWidth("calc(100% - 30pt)");
        setHeight("calc(100% - 30pt)");
        getStyle().set("padding", "15pt");
        getElement().setAttribute("style", getElement().getAttribute("style") + ";\n" +
                "resize: none;\n" +
                "font-size: larger;\n" +
                "");
    }

}