package com.raukhvarger.ms.webfs.front.view;

import com.raukhvarger.ms.webfs.front.service.UIControls;
import com.raukhvarger.ms.webfs.front.view.component.UIMenu;
import com.raukhvarger.ms.webfs.front.view.component.UITabs;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route
@PreserveOnRefresh
@UIScope
public class MainView extends VerticalLayout {

	private final Logger logger = LoggerFactory.getLogger(MainView.class);

	private final UIMenu uiMenu;
	private final UITabs uiTabs;
	private final UIControls uiControls;

	@Autowired
	public MainView(UIMenu uiMenu, UITabs uiTabs, UIControls uiControls) {
		this.uiMenu = uiMenu;
		this.uiTabs = uiTabs;
		this.uiControls = uiControls;
	}

	@PostConstruct
	public void init() {
		setHeightFull();

		add(uiMenu);
		addAndExpand(uiTabs);

		uiControls.openStartFolder();
	}

}