package com.raukhvarger.ms.webfs.view;

import com.raukhvarger.ms.webfs.front.service.UIEventsImpl;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout {

	public static final String ROUTE = "login";

	private LoginOverlay login = new LoginOverlay();

	@Autowired
	public LoginView(UIEventsImpl uiEvents) {
		login.setOpened(true);
		login.setForgotPasswordButtonVisible(false);

		login.setTitle("Welcome to WebFS!");

		add(login);

		login.addLoginListener(uiEvents.getLogoutClickEvent(login));
	}
}