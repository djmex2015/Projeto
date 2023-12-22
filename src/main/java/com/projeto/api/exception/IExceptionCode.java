package com.projeto.api.exception;

import java.io.Serializable;
import org.slf4j.event.Level;

public interface IExceptionCode extends Serializable {

  default String getName() {
    return "";
  }

  int getHttpStatus();

  Level getSeverity();

  Integer getInternalCode();

  String getTranslation();

  String getTranslation(Object... params);

}
