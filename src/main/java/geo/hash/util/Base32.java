package geo.hash.util;

import java.util.HashMap;

/**
 * Conversion methods between long values and geohash-style base 32 encoding.
 *
 * @author dave
 */
public class Base32 {

    private static final int MAX_LENGTH = 12;

    /**
     * Constructor.
     */
    private Base32() {
        // prevent instantiation.
    }

    /**
     * The characters used for encoding base 32 strings.
     */
    private final static char[] characters = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
            'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Used for lookup of index of characters in the above array.
     */
    private final static HashMap<Character, Integer> characterIndexes = new HashMap<Character, Integer>();

    static {
        int i = 0;
        for (char c : characters)
            characterIndexes.put(c, i++);
    }

    /**
     * Returns the base 32 encoding of the given length from a {@link Long}
     * geohash.
     *
     * @param i      the geohash
     * @param length the length of the returned hash
     * @return the string geohash
     */
    public static String encodeBase32(long i, int length) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative) {
            i = -i;
        }
        while (i <= -32) {
            buf[charPos--] = characters[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = characters[(int) (-i)];
        String result = new String(buf, charPos, (65 - charPos));
        if (negative) {
            return "-" + result;
        } else {
            return result;
        }
    }

    /**
     * Returns the base 32 encoding of length
     * from a {@link Long} geohash.
     *
     * @param i the geohash
     * @return the base32 geohash
     */
    public static String encodeBase32(long i) {
        return encodeBase32(i, MAX_LENGTH);
    }

    /**
     * Returns the conversion of a base32 geohash to a long.
     *
     * @param hash geohash as a string
     * @return long representation of hash
     */
    public static long decodeBase32(String hash) {
        boolean isNegative = hash.startsWith("-");
        int startIndex = isNegative ? 1 : 0;
        long base = 1;
        long result = 0;
        for (int i = hash.length() - 1; i >= startIndex; i--) {
            int j = getCharIndex(hash.charAt(i));
            result = result + base * j;
            base = base * 32;
        }
        if (isNegative) {
            result *= -1;
        }
        return result;
    }

    /**
     * Returns the index in the digits array of the character ch. Throws an
     * {@link IllegalArgumentException} if the character is not found in the
     * array.
     *
     * @param ch character to obtain index for
     * @return index of ch character in characterIndexes map.
     */
    // @VisibleForTesting
    private static int getCharIndex(char ch) {
        Integer result = characterIndexes.get(ch);
        if (result == null) {
            throw new IllegalArgumentException("not a base32 character: " + ch);
        } else {
            return result;
        }
    }

}
