package com.projeto.api.exception;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public final class I18n {

  private static final Logger LOG = LoggerFactory.getLogger(I18n.class);

  public static String translate(String baseName, String key, Locale locale, Object... args) {
    return translate(baseName, key, locale, StandardCharsets.ISO_8859_1, args);
  }

  public static String translate(String baseName, String key, Locale locale, Charset charset, Object... args) {
    if (key == null) {
      return "";
    }

    ResourceBundle messages =
        ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));
    if (!messages.containsKey(key)) {
      LOG.warn("Unable to find the translation for key '{}'", key);
      return String.format("???%s???", key);
    }
    return MessageFormat.format(parseStringEncoding(messages.getString(key), charset), args);
  }

  private static String parseStringEncoding(String text, Charset charset) {
    return new String(text.getBytes(charset), StandardCharsets.UTF_8);
  }

}
