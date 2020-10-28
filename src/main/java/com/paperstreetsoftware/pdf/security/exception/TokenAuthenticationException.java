package com.paperstreetsoftware.pdf.security.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {
	private static final long serialVersionUID = 5603442469786759804L;

	public TokenAuthenticationException(String msg) {
        super(msg);
    }

    public TokenAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
