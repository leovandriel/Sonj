package net.sf.sonj.io;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Serializes a JSON file from a tree of {@link Map} and {@link List}. It
 * traverses these collections in a depth-first fashion using their default
 * iterator and writes JSON formatted data to the provided Writer, all according
 * to the JSON specification on <a href="http://www.json.org">json.org</a>.
 * 
 * The JSON data can be formatted in various ways. By default, it will put each
 * entry on a new line, indented with tabs. It is however also possible to
 * customize the indentation, or to not indent or line break at all.
 * 
 * To avoid unrestricted looping to circularly referencing collections, the
 * depth-first iteration will be result in an exception if the depth exceeds the
 * value set with {@link #setMaxDepth(int)}.
 */
public class JsonWriter {
	private int encodingRange = 128;
	private int maxDepth = 256;
	private boolean allowNull = true;
	private boolean autoToString = true;
	private String indent = null;
	private Writer writer;

	/**
	 * Constructs an instance by setting the writer and initializing the fast
	 * indentation system.
	 * 
	 * @param writer
	 *            Any writer instance, preferably a buffered one.
	 */
	public JsonWriter(Writer writer) {
		this.writer = writer;
	}

	/**
	 * Writes the provided object in JSON format. All objects are allowed,
	 * however only some will be turned into JSON data structures. Namely
	 * {@link Map} into JSON object, {@link List} into JSON array,
	 * {@link Number} into JSON number. All other objects will be turned into
	 * strings using their toString function.
	 * 
	 * @param object
	 * @throws JsonWriteException
	 *             If unable to create a serialized representation of the
	 *             object.
	 */
	public void write(Object object) {
		writeObject(object, 0);
		try {
			writer.flush();
		} catch (IOException e) {
			throw new JsonWriteException("Unable to flush: " + e.getMessage(), e);
		}
	}

	private void writeObject(Object object, int depth) {
		if (depth > maxDepth) {
			throw new JsonWriteException("Max tree depth has been reached: " + depth);
		} else if (object instanceof Map<?, ?>) {
			writeMap((Map<?, ?>) object, depth);
		} else if (object instanceof List<?>) {
			writeList((List<?>) object, depth);
		} else if (object instanceof String) {
			writeString((String) object);
		} else if (object instanceof Number || object instanceof Boolean) {
			writeToWriter(object.toString());
		} else if (object != null) {
			if (!autoToString) {
				throw new JsonWriteException("Non-json object not allowed (auto-to-string is off): "
						+ object.getClass());
			}
			writeString(object.toString());
		} else {
			if (!allowNull) {
				throw new JsonWriteException("Value null not allowed (allow-null is off).");
			}
			writeToWriter("null");
		}
	}

	private void writeMap(Map<?, ?> object, int depth) {
		writeToWriter(JsonIo.objectOpen);
		boolean addSeparator = false;
		for (Entry<?, ?> entry : object.entrySet()) {
			if (addSeparator) {
				writeToWriter(JsonIo.itemSeparator);
			} else {
				addSeparator = true;
			}
			writeIndent(depth + 1);
			if (entry.getKey() instanceof String) {
				writeString((String) entry.getKey());
			} else {
				if (!autoToString) {
					throw new JsonWriteException("Non-json objects not allowed, auto-to-string is off.");
				}
				writeString(entry.getKey().toString());
			}
			writeToWriter(JsonIo.pairSeparator);
			writeObject(entry.getValue(), depth + 1);
		}
		if (!object.isEmpty()) {
			writeIndent(depth);
		}
		writeToWriter(JsonIo.objectClose);
	}

	private void writeList(List<?> array, int depth) {
		writeToWriter(JsonIo.arrayOpen);
		boolean addSeparator = false;
		for (Object entry : array) {
			if (addSeparator) {
				writeToWriter(JsonIo.itemSeparator);
			} else {
				addSeparator = true;
			}
			writeIndent(depth + 1);
			writeObject(entry, depth + 1);
		}
		if (!array.isEmpty()) {
			writeIndent(depth);
		}
		writeToWriter(JsonIo.arrayClose);
	}

	private void writeString(String string) {
		writeToWriter(JsonIo.stringDelimiter);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < string.length();) {
			int c = string.codePointAt(i);
			if (c >= encodingRange || (c < JsonIo.highestWhiteSpace && JsonIo.escapeArray[c] == 0)) {
				String hexString = Integer.toString(c, 16);
				builder.appendCodePoint(JsonIo.escapeCharacter);
				builder.appendCodePoint(JsonIo.unicodeCharacter);
				for (int j = hexString.length(); j < JsonIo.unicodeDigitCount; j++) {
					builder.appendCodePoint('0');
				}
				builder.append(hexString);
			} else if (c < JsonIo.highestEscape && JsonIo.escapeArray[c] != 0) {
				builder.appendCodePoint(JsonIo.escapeCharacter);
				builder.appendCodePoint(JsonIo.escapeArray[c]);
			} else {
				builder.appendCodePoint(c);
			}
			i += Character.charCount(c);
		}
		writeToWriter(builder.toString());
		writeToWriter(JsonIo.stringDelimiter);
	}

	private void writeToWriter(int codePoint) {
		try {
			writer.write(codePoint);
		} catch (IOException e) {
			throw new JsonWriteException("Unable to write character: " + e.getMessage(), e);
		}
	}

	private void writeToWriter(String string) {
		try {
			writer.write(string);
		} catch (IOException e) {
			throw new JsonWriteException("Unable to write string: " + e.getMessage(), e);
		}
	}

	/**
	 * The maximum allowed collection nesting depth. When this depth is
	 * exceeded, a {@link JsonWriteException} will be thrown.
	 * 
	 * @param maxDepth
	 */
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	/**
	 * The unicode code point range outside of which characters will be encoded
	 * using the \\uxxxx escape sequence.
	 * 
	 * @param encodingRange
	 *            The first character of the code point range that will be
	 *            escaped.
	 */
	public void setEncodingRange(int encodingRange) {
		this.encodingRange = encodingRange;
	}

	/**
	 * When set to false, this writer will throw a write exception instead of
	 * writing the null primitive.
	 */
	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	/**
	 * When set to false, this writer will throw a write exception instead of
	 * writing non json object using their toString function.
	 */
	public void setAutoToString(boolean autoToString) {
		this.autoToString = autoToString;
	}

	private void writeIndent(int depth) {
		if (indent != null) {
			writeToWriter("\n");
			for (int i = depth; i > 0; i--) {
				writeToWriter(indent);
			}
		}
	}

	/**
	 * Set this writer to using spaces to indent the output.
	 * 
	 * @param count
	 *            The number of space characters in one indent step.
	 */
	public void setIndentSpaces(int count) {
		StringBuilder builder = new StringBuilder();
		for (int i = count; i > 0; i--) {
			builder.append(" ");
		}
		indent = builder.toString();
	}

	/**
	 * Sets this writer to using tabs to indent the output.
	 */
	public void setIndentTabs() {
		indent = "\t";
	}

	/**
	 * Sets this writer to not use any indentation or line-breaking. This means
	 * no unnecessary white spaces will be written.
	 */
	public void setIndentNorLinebreak() {
		indent = null;
	}

	/**
	 * Set a specific indentation string, use null to not indent nor line break.
	 */
	public void setIndent(String indent) {
		this.indent = indent;
	}
}
