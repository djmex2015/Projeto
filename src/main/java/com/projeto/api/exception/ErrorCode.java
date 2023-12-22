package com.projeto.api.exception;

import java.util.Locale;
import lombok.AllArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode implements IExceptionCode {

  PROJETO_NOT_FOUND(404, HttpStatus.NOT_FOUND, Level.WARN),
  PESSOA_NOT_FOUND(404, HttpStatus.NOT_FOUND, Level.DEBUG),
  PROJETO_CAN_NOT_BE_REMOVED(212, HttpStatus.PRECONDITION_FAILED, Level.WARN),
  PESSOA_NOT_FUNCIONARIA(212, HttpStatus.PRECONDITION_FAILED, Level.WARN);

  private static final String BASE_NAME = "exception.i18n";

  private final Integer internalCode;

  private final HttpStatus httpStatus;

  private final Level severity;

  @Override
  public String getName() {
    return this.name();
  }

  @Override
  public int getHttpStatus() {
    return httpStatus.value();
  }

  @Override
  public Level getSeverity() {
    return severity;
  }

  @Override
  public Integer getInternalCode() {
    return internalCode;
  }


  @Override
  public String getTranslation() {
    return I18n.translate(BASE_NAME, this.name(), Locale.ENGLISH);
  }

  @Override
  public String getTranslation(Object[] params) {
    return I18n.translate(BASE_NAME, this.name(), Locale.ENGLISH, params);
  }

}
