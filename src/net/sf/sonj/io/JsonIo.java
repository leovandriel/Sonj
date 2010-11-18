package net.sf.sonj.io;

public class JsonIo {
	protected static final int objectOpen = '{';
	protected static final int objectClose = '}';
	protected static final int arrayOpen = '[';
	protected static final int arrayClose = ']';
	protected static final int itemSeparator = ',';
	protected static final int pairSeparator = ':';
	protected static final int stringDelimiter = '"';
	protected static final int escapeCharacter = '\\';
	protected static final int unicodeCharacter = 'u';
	protected static final int unicodeDigitCount = 4;
	protected static final int highestEscape = 128;
	protected static final int highestWhiteSpace = ' ';
	protected static final int[] escapeArray = new int[highestEscape];
	protected static final int[] unescapeArray = new int[highestEscape];
	static {
		escapeArray['"'] = '"';
		escapeArray['/'] = '/';
		escapeArray['\b'] = 'b';
		escapeArray['\f'] = 'f';
		escapeArray['\n'] = 'n';
		escapeArray['\r'] = 'r';
		escapeArray['\t'] = 't';
		escapeArray['\\'] = '\\';
		for (int i = 0; i < escapeArray.length; i++) {
			if (escapeArray[i] != 0) {
				unescapeArray[escapeArray[i]] = i;
			}
		}
	}
}
