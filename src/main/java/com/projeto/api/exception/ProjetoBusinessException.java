package com.projeto.api.exception;

import java.io.Serial;

public final class ProjetoBusinessException extends ProjetoException {

  @Serial
  private static final long serialVersionUID = -1L;

  public ProjetoBusinessException(IExceptionCode cod, Object... params) {
    super(cod, params);
  }

}
