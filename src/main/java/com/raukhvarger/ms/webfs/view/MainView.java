package com.raukhvarger.ms.webfs.view;

import com.raukhvarger.ms.webfs.view.components.UIMainArea;
import com.raukhvarger.ms.webfs.view.components.UIMenu;
import com.raukhvarger.ms.webfs.view.components.UIPath;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@Route
@PreserveOnRefresh
@Scope("session")

public class MainView extends VerticalLayout {

	private final Logger logger = LoggerFactory.getLogger(MainView.class);

	@Autowired
	private UIPath uiPath;

	@Autowired
	private UIMenu uiMenu;

	@Autowired
	private UIMainArea uiMainArea;

	@PostConstruct
	public void init() {
		setHeightFull();

		add(uiMenu);
		add(uiPath);
		add(uiMainArea);
	}

}