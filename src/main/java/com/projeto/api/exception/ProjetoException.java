package com.projeto.api.exception;

import java.io.Serial;
import java.util.Arrays;

public abstract class ProjetoException extends Exception {

  @Serial
  private static final long serialVersionUID = -1L;

  private final IExceptionCode code;

  private final Object[] params;

  protected ProjetoException(IExceptionCode cod, Object... params) {
    this(null, cod, params);
  }

  protected ProjetoException(Exception cause, IExceptionCode cod, Object... params) {
    super(cause);
    this.code = cod;
    this.params = Arrays.copyOf(params, params.length);
  }

  @Override
  public String getMessage() {
    if (params.length > 0) {
      return code.getTranslation(params);
    }
    return code.getTranslation();
  }

}
