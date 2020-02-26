package com.raukhvarger.ms.webfs.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.function.SerializableSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.tabs.PagedTabs;


/**
 * Fix Vaadin removeFromTree error
  */ 
public class CustomPagedTabs extends PagedTabs {

    private final Logger logger = LoggerFactory.getLogger(CustomPagedTabs.class);

    @Override
    public void select(Tab tab) {
        SerializableSupplier<Component> supplier = tabsToSuppliers.get(tab);
        if (supplier != null) {
            Component component = supplier.get();
            VerticalLayout wrapper = new VerticalLayout(component);
            wrapper.setMargin(false);
            wrapper.setPadding(false);
            wrapper.setSizeFull();

            // fix: 
            content.getChildren().forEach(c -> c.getElement().removeFromTree());
            content.removeAll();
            // 
            
            content.add(wrapper);
            tabs.setSelectedTab(tab);
            selected = wrapper;
        } else
            content.remove(selected);
    }

}
