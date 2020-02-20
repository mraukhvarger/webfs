package com.raukhvarger.ms.webfs.spring;

import com.vaadin.flow.server.ServletHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

@Component
public final class SecurityUtils {

	private static AppConfig appConfig;

	private SecurityUtils(AppConfig appConfig) {
		SecurityUtils.appConfig = appConfig;
	}

	static boolean isFrameworkInternalRequest(HttpServletRequest request) {
		final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
		return parameterValue != null
				&& Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
	}

	static boolean isUserLoggedIn() {
		if (appConfig.getDisableAuthorization())
			return true;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null
				&& !(authentication instanceof AnonymousAuthenticationToken)
				&& authentication.isAuthenticated();
	}
}