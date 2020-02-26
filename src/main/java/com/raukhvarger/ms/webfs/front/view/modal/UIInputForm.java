package com.raukhvarger.ms.webfs.front.view.modal;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class UIInputForm extends Dialog {

    private TextField input = new TextField();
    private Button ok = new Button(new Icon(VaadinIcon.ENTER_ARROW));
    private Label label = new Label();

    public UIInputForm() {

        HorizontalLayout hl = new HorizontalLayout();
        hl.setAlignItems(FlexComponent.Alignment.BASELINE);
        hl.add(label);
        hl.add(input);
        hl.add(ok);

        add(hl);
    }

    @Override
    public void open() {
        super.open();
        input.getElement().callJsFunction("focus");
    }

    public void addOKEventListener(ComponentEventListener okEvent) {
        ok.addClickListener(okEvent);
        input.addKeyPressListener(Key.ENTER, okEvent);
    }

    public void setLabelCaption(String caption) {
        label.setText(caption);
    }

    public String getInputValue() {
        return input.getValue();
    }
}
