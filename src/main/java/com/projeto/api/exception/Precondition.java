package com.projeto.api.exception;

import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Precondition {

  public static <T> T checkNotNull(T reference, IExceptionCode code, Object... params) throws ProjetoBusinessException {
    if (reference == null) {
      throw new ProjetoBusinessException(code, params);
    }
    return reference;
  }

  @SuppressWarnings("all")
  public static <T> T checkNotNull(Optional<T> reference, IExceptionCode code, Object... params) throws ProjetoBusinessException {
    return reference.orElseThrow(() -> new ProjetoBusinessException(code, params));
  }

  @SuppressWarnings("all")
  public static <T> void checkNull(T reference, IExceptionCode code, Object... params) throws ProjetoBusinessException {
    if (reference instanceof Optional) {
      Optional<T> optional = (Optional<T>) reference;
      if (optional.isPresent()) {
        throw new ProjetoBusinessException(code, params);
      }
      return;
    }

    if (reference != null) {
      throw new ProjetoBusinessException(code, params);
    }

  }

  public static void checkTrue(boolean reference, IExceptionCode code, Object... params) throws ProjetoBusinessException {
    if (!reference) {
      throw new ProjetoBusinessException(code, params);
    }
  }

  public static void checkFalse(boolean reference, IExceptionCode code, Object... params) throws ProjetoBusinessException {
    if (reference) {
      throw new ProjetoBusinessException(code, params);
    }
  }

}
