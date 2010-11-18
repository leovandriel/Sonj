package net.sf.sonj.io;

import net.sf.sonj.util.JsonException;

/**
 * An exception tied to {@link JsonReader}, indicating some unexpected input was
 * read.
 */
public class JsonReadException extends JsonException {
	private static final long serialVersionUID = 1L;

	public JsonReadException(String message) {
		super(message);
	}

	public JsonReadException(String message, Throwable cause) {
		super(message, cause);
	}
}
