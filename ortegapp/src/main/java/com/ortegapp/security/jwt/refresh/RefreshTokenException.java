package com.ortegapp.security.jwt.refresh;

import com.ortegapp.security.errorhandling.JwtTokenException;

public class RefreshTokenException extends JwtTokenException {

    public RefreshTokenException(String msg) {
        super(msg);
    }

}


