package com.user.api.exception;

import java.util.UUID;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(UUID userId ) {
    super("User " + userId + " already exists");
  }
}
