package com.raukhvarger.ms.webfs.view;

import com.raukhvarger.ms.webfs.spring.CustomRequestCache;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout {

	public static final String ROUTE = "login";

	private LoginOverlay login = new LoginOverlay();

	@Autowired
	public LoginView(AuthenticationManager authenticationManager,
					 CustomRequestCache requestCache) {
		login.setOpened(true);
		login.setForgotPasswordButtonVisible(false);

		login.setTitle("Welcome to WebFS!");

		add(login);

		login.addLoginListener(e -> {
			try {
				final Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword()));

				if(authentication != null ) {
					login.close();
					SecurityContextHolder.getContext().setAuthentication(authentication);
					UI.getCurrent().navigate(requestCache.resolveRedirectUrl());
				}

			} catch (AuthenticationException ex) {
				login.setError(true);
			}
		});
	}
}