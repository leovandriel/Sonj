package net.sf.sonj.io;

import net.sf.sonj.util.JsonException;

/**
 * An exception tied to {@link JsonWriter}, indicating some unexpected data
 * structure was detected.
 */
public class JsonWriteException extends JsonException {
	private static final long serialVersionUID = 1L;

	public JsonWriteException(String message) {
		super(message);
	}

	public JsonWriteException(String message, Throwable cause) {
		super(message, cause);
	}
}
