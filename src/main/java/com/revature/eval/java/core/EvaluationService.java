package com.revature.eval.java.core;

import java.time.Duration;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluationService {

	/**
	 * 1. Without using the StringBuilder or StringBuffer class, write a method that
	 * reverses a String. Example: reverse("example"); -> "elpmaxe"
	 * 
	 * @param string
	 * @return
	 */
	public String reverse(String string) {
		// create char array of the lenghth of the string
		final char[] str = new char[string.length()];

		// go forward through char array
		for (int i = 0; i < str.length; ++i) {
			// go backwards through string
			str[i] = string.charAt(str.length - 1 - i);
		}

		return new String(str);
	}

	/**
	 * 2. Convert a phrase to its acronym. Techies love their TLA (Three Letter
	 * Acronyms)! Help generate some jargon by writing a program that converts a
	 * long name like Portable Network Graphics to its acronym (PNG).
	 * 
	 * @param phrase
	 * @return
	 */
	public String acronym(String phrase) {
		// convert to upper case so that acronyms will be capitalized
		phrase = phrase.toUpperCase();

		// split on all non-alpha characters
		final String[] words = phrase.split("[^\\p{Alpha}]+");

		// the acronym will be the length of the number of words
		final char[] ack = new char[words.length];

		// add first character of each word to acryonym
		for (int i = 0; i < ack.length; i++) {
			ack[i] = words[i].charAt(0);
		}

		return new String(ack);
	}

	/**
	 * 3. Determine if a triangle is equilateral, isosceles, or scalene. An
	 * equilateral triangle has all three sides the same length. An isosceles
	 * triangle has at least two sides the same length. (It is sometimes specified
	 * as having exactly two sides the same length, but for the purposes of this
	 * exercise we'll say at least two.) A scalene triangle has all sides of
	 * different lengths.
	 *
	 */
	static class Triangle {
		private double sideOne;
		private double sideTwo;
		private double sideThree;

		public Triangle() {
			super();
		}

		public Triangle(double sideOne, double sideTwo, double sideThree) {
			this();
			this.sideOne = sideOne;
			this.sideTwo = sideTwo;
			this.sideThree = sideThree;
		}

		public double getSideOne() {
			return sideOne;
		}

		public void setSideOne(double sideOne) {
			this.sideOne = sideOne;
		}

		public double getSideTwo() {
			return sideTwo;
		}

		public void setSideTwo(double sideTwo) {
			this.sideTwo = sideTwo;
		}

		public double getSideThree() {
			return sideThree;
		}

		public void setSideThree(double sideThree) {
			this.sideThree = sideThree;
		}

		// all three sides should be equal (use transitive property to only test 1 and
		// 2, then 2 and 3)
		public boolean isEquilateral() {
			return sideOne == sideTwo && sideTwo == sideThree;
		}

		// test for all combinations of two sides (total of three)
		public boolean isIsosceles() {
			return sideOne == sideTwo || sideOne == sideThree || sideTwo == sideThree;
		}

		// test for all combinations of two sides (total of three)
		public boolean isScalene() {
			return sideOne != sideTwo && sideOne != sideThree && sideTwo != sideThree;
		}

	}

	/**
	 * 4. Given a word, compute the scrabble score for that word.
	 * 
	 * --Letter Values-- Letter Value A, E, I, O, U, L, N, R, S, T = 1; D, G = 2; B,
	 * C, M, P = 3; F, H, V, W, Y = 4; K = 5; J, X = 8; Q, Z = 10; Examples
	 * "cabbage" should be scored as worth 14 points:
	 * 
	 * 3 points for C, 1 point for A, twice 3 points for B, twice 2 points for G, 1
	 * point for E And to total:
	 * 
	 * 3 + 2*1 + 2*3 + 2 + 1 = 3 + 2 + 6 + 3 = 5 + 9 = 14
	 * 
	 * @param string
	 * @return
	 */
	public int getScrabbleScore(String string) {
		// convert to upper case because that's how I wrote the switch statement below
		final String upper = string.toUpperCase();

		// if upper contains anything other than upper case characters, this isn't a
		// valid scrabble word.
		if (!upper.matches("\\p{Upper}+"))
			throw new IllegalArgumentException("invalide scrabble string: " + string);

		int score = 0;
		final int len = string.length();

		// iterate through each character in string
		for (int i = 0; i < len; ++i) {
			score += getScrabbleScore(upper.charAt(i));
		}

		return score;
	}

	// helper method which computes the value for each scrabble letter (assumes it's
	// a valid character)
	private static int getScrabbleScore(final char c) {
		switch (c) {
		case 'D':
		case 'G':
			return 2;
		case 'B':
		case 'C':
		case 'M':
		case 'P':
			return 3;
		case 'F':
		case 'H':
		case 'V':
		case 'W':
		case 'Y':
			return 4;
		case 'K':
			return 5;
		case 'J':
		case 'X':
			return 8;
		case 'Q':
		case 'Z':
			return 10;
		default: // default is the rest of the alphabet
			return 1;
		}
	}

	/**
	 * 5. Clean up user-entered phone numbers so that they can be sent SMS messages.
	 * 
	 * The North American Numbering Plan (NANP) is a telephone numbering system used
	 * by many countries in North America like the United States, Canada or Bermuda.
	 * All NANP-countries share the same international country code: 1.
	 * 
	 * NANP numbers are ten-digit numbers consisting of a three-digit Numbering Plan
	 * Area code, commonly known as area code, followed by a seven-digit local
	 * number. The first three digits of the local number represent the exchange
	 * code, followed by the unique four-digit number which is the subscriber
	 * number.
	 * 
	 * The format is usually represented as
	 * 
	 * 1 (NXX)-NXX-XXXX where N is any digit from 2 through 9 and X is any digit
	 * from 0 through 9.
	 * 
	 * Your task is to clean up differently formatted telephone numbers by removing
	 * punctuation and the country code (1) if present.
	 * 
	 * For example, the inputs
	 * 
	 * +1 (613)-995-0253 613-995-0253 1 613 995 0253 613.995.0253 should all produce
	 * the output
	 * 
	 * 6139950253
	 * 
	 * Note: As this exercise only deals with telephone numbers used in
	 * NANP-countries, only 1 is considered a valid country code.
	 */
	public String cleanPhoneNumber(String string) {
		// remove all non-digits from string
		string = string.replaceAll("[^\\d]+", "");

		// if it's now 10 digits, that's the phone number
		if (string.length() == 10) {
			return string;
		} else if (string.length() == 11 && string.charAt(0) == '1') {
			// it CAN be of length 11 but the first character must be a 1
			return string.substring(1);
		} else {
			// if it's not 10 or 11 digits, it's invalid. It's also invalid if it's 11
			// digits with a prefix not 1.
			throw new IllegalArgumentException("invalid phone number: " + string);
		}
	}

	/**
	 * 6. Given a phrase, count the occurrences of each word in that phrase.
	 * 
	 * For example for the input "olly olly in come free" olly: 2 in: 1 come: 1
	 * free: 1
	 * 
	 * @param string
	 * @return
	 */
	public Map<String, Integer> wordCount(String string) {
		// split string on non-alpha characters, this gives me an array of words
		final String[] words = string.split("[^\\p{Alpha}]+");
		final Map<String, Integer> wordCount = new HashMap<String, Integer>();

		for (final String word : words) {
			// if the wordCount map doesn't contain word, then this is the first
			// encounter--add it and its count to 1--otherwise just add one to the previous
			// count.
			if (!wordCount.containsKey(word))
				wordCount.put(word, 1);
			else
				wordCount.put(word, wordCount.get(word) + 1);
		}

		return wordCount;
	}

	/**
	 * 7. Implement a binary search algorithm.
	 * 
	 * Searching a sorted collection is a common task. A dictionary is a sorted list
	 * of word definitions. Given a word, one can find its definition. A telephone
	 * book is a sorted list of people's names, addresses, and telephone numbers.
	 * Knowing someone's name allows one to quickly find their telephone number and
	 * address.
	 * 
	 * If the list to be searched contains more than a few items (a dozen, say) a
	 * binary search will require far fewer comparisons than a linear search, but it
	 * imposes the requirement that the list be sorted.
	 * 
	 * In computer science, a binary search or half-interval search algorithm finds
	 * the position of a specified input value (the search "key") within an array
	 * sorted by key value.
	 * 
	 * In each step, the algorithm compares the search key value with the key value
	 * of the middle element of the array.
	 * 
	 * If the keys match, then a matching element has been found and its index, or
	 * position, is returned.
	 * 
	 * Otherwise, if the search key is less than the middle element's key, then the
	 * algorithm repeats its action on the sub-array to the left of the middle
	 * element or, if the search key is greater, on the sub-array to the right.
	 * 
	 * If the remaining array to be searched is empty, then the key cannot be found
	 * in the array and a special "not found" indication is returned.
	 * 
	 * A binary search halves the number of items to check with each iteration, so
	 * locating an item (or determining its absence) takes logarithmic time. A
	 * binary search is a dichotomic divide and conquer search algorithm.
	 * 
	 */
	static class BinarySearch<T extends Comparable<T>> {
		private List<T> sortedList;

		public int indexOf(T t) {
			// List cannot contain a null object (this cannot be sorted)
			if (t == null)
				return -1;

			// left and right represent the current search range, which is initially the
			// entire list. left is the first index to be searched and right one past the
			// last index to be searched--in this way, right - left represents the number of
			// elements in this range.
			int left = 0;
			int right = sortedList.size();

			// I'm going to test left, mid, and right, which means that we need at least
			// three values in the range
			while (right - left > 2) {
				// see if left or right item is this (this helps prevent worst case where it's
				// the first or last item)
				final int leftRightTest = indexOfLeftRight(t, left, right - 1);
				if (leftRightTest >= 0)
					return leftRightTest;

				// find the middle by taking the average of left and right
				final int middleIndex = (left + right) / 2;
				final T middleItem = sortedList.get(middleIndex);

				// store the comparison for the middle term since this will decide whether or
				// not we go left or right
				final int cmp = t.compareTo(middleItem);

				// if cmp is zero, middleTerm is equal to t, just return middleIndex
				if (cmp == 0)
					return middleIndex;

				// go left or right depending on cmp
				if (cmp < 0) {// search left
					// we just eliminated the current left, so increment left
					++left;
					right = middleIndex;
				} else {// search right
					// we just eliminated right, so decrement right
					--right;
					left = middleIndex + 1;
				}
			}

			// there are two elements left in range, the "left" and "right"--check them
			if (right - left == 2) {
				final int index = indexOfLeftRight(t, left, right - 1);
				if (index >= 0)
					return index;
			} else if (right - left == 1) {
				// there was only one left, the "left", so just check it
				if (sortedList.get(left).equals(t))
					return left;
			}
			// else t was never found
			return -1;
		}

		// helper method to test left and right side of range.
		private int indexOfLeftRight(T t, final int left, final int right) {
			// test to see if any three of the indexes are the element we're searching for.
			// If so, return that index.
			if (sortedList.get(left).equals(t))
				return left;
			if (sortedList.get(right).equals(t))
				return right;

			return -1;
		}

		public BinarySearch(List<T> sortedList) {
			super();
			this.sortedList = sortedList;
		}

		public List<T> getSortedList() {
			return sortedList;
		}

		public void setSortedList(List<T> sortedList) {
			this.sortedList = sortedList;
		}

	}

	/**
	 * 8. Implement a program that translates from English to Pig Latin.
	 * 
	 * Pig Latin is a made-up children's language that's intended to be confusing.
	 * It obeys a few simple rules (below), but when it's spoken quickly it's really
	 * difficult for non-children (and non-native speakers) to understand.
	 * 
	 * Rule 1: If a word begins with a vowel sound, add an "ay" sound to the end of
	 * the word. Rule 2: If a word begins with a consonant sound, move it to the end
	 * of the word, and then add an "ay" sound to the end of the word. There are a
	 * few more rules for edge cases, and there are regional variants too.
	 * 
	 * See http://en.wikipedia.org/wiki/Pig_latin for more details.
	 * 
	 * @param string
	 * @return
	 */
	public String toPigLatin(String string) {
		// split on non-alpha characters
		final String words[] = string.split("[^\\p{Alpha}]+");

		final StringBuilder pigLatin = new StringBuilder(string.length());

		// handle fence post, now every subsequent word will have a space before it
		pigLatin.append(convertWordToPigLatin(words[0]));

		// just convert each word to pig latin individually
		for (int i = 1; i < words.length; ++i)
			pigLatin.append(" " + convertWordToPigLatin(words[i]));

		return pigLatin.toString();
	}

	// list is from https://www.enchantedlearning.com/consonantblends/
	// added "qu" (sorted from longest to shortest)
	public static final String[] BLENDS_DIGRAPHS_TRIGRAPHS_OH_MY = { "sch", "scr", "shr", "sph", "spl", "spr", "squ",
			"str", "thr", "bl", "br", "ch", "cl", "cr", "dr", "fl", "fr", "gl", "gr", "pl", "pr", "sc", "sh", "sk",
			"sl", "sm", "sn", "sp", "st", "sw", "th", "tr", "tw", "wh", "wr", "qu" };

	public static final String convertWordToPigLatin(String word) {
		// check to see if first letter is a vowel
		if (isVowel(word.charAt(0))) {
			return word + "ay";
		}
		// else see if it's a blend
		for (final String blend : BLENDS_DIGRAPHS_TRIGRAPHS_OH_MY) {
			if (word.startsWith(blend)) {
				// remove blend from beginning of word
				// need to use "replaceFirst" because just using "replace" will replace all
				// instances of the blend (if it appears more than once)
				word = word.replaceFirst(blend, "");
				// add to the end
				return word + blend + "ay";
			}
		}

		// nope, just a single consonant, remove it and add to the end
		final char first = word.charAt(0);

		return word.substring(1) + first + "ay";
	}

	public static boolean isVowel(final char c) {
		switch (c) {
		case 'a':
		case 'A':
		case 'e':
		case 'E':
		case 'i':
		case 'I':
		case 'o':
		case 'O':
		case 'u':
		case 'U':
			return true;
		default: // then it's a consonant (or a y...)
			return false;
		}
	}

	/**
	 * 9. An Armstrong number is a number that is the sum of its own digits each
	 * raised to the power of the number of digits.
	 * 
	 * For example:
	 * 
	 * 9 is an Armstrong number, because 9 = 9^1 = 9 10 is not an Armstrong number,
	 * because 10 != 1^2 + 0^2 = 2 153 is an Armstrong number, because: 153 = 1^3 +
	 * 5^3 + 3^3 = 1 + 125 + 27 = 153 154 is not an Armstrong number, because: 154
	 * != 1^3 + 5^3 + 4^3 = 1 + 125 + 64 = 190 Write some code to determine whether
	 * a number is an Armstrong number.
	 * 
	 * @param input
	 * @return
	 */
	public boolean isArmstrongNumber(int input) {
		// an armstrong number must be a natural number (i.e. non-negative)
		if (input < 0)
			return false;

		// get the number of digits by taking the log base 10. Think about it:
		// log_{10}(100) = 2 but it's 3 digits, so need to add 1. 500 is 2.??? so I
		// still need to add one and then the cast to an int will round it down.
		final int numDigits = (int) (Math.log10(input) + 1);
		int sum = 0;

		// need to keep the original input to test at the end
		int copy = input;

		// keep dividing by 10 until copy is 0
		while (copy > 0) {
			// get current one's digit (modulo 10)
			final int digit = copy % 10;

			// add power of that digit
			sum += (int) Math.pow(digit, numDigits);

			// reduce by dividing by 10
			copy /= 10;
		}

		return sum == input;
	}

	/**
	 * 10. Compute the prime factors of a given natural number.
	 * 
	 * A prime number is only evenly divisible by itself and 1.
	 * 
	 * Note that 1 is not a prime number.
	 * 
	 * @param l
	 * @return
	 */
	public List<Long> calculatePrimeFactorsOf(long l) {
		if (l <= 1)
			throw new IllegalArgumentException("Cannot factor numbers <= 1: " + l);

		final List<Long> list = new ArrayList<Long>();
		long stop = (long) Math.sqrt(l);

		for (long test = 2; test <= stop; ++test) {
			if (l % test == 0) {
				// add this factor to list
				list.add(test);

				// divide out ALL of these prime factors so that the next number that l is
				// divisible by is guaranteed to be prime (and not some multiple of this prime)
				l /= test;
				while (l % test == 0) {
					l /= test;
					list.add(test);
					// keep adding prime each time to get full prime factorization
				}

				// update stop because you don't need to go to the original square root anymore,
				// you're now trying to factor a new number that you know doesn't contain any
				// prime factors below test
				stop = (long) Math.sqrt(l);
			}
		}

		if (l > 1) {
			// what's remaining is a prime number
			list.add(l);
		}

		return list;
	}

	/**
	 * 11. Create an implementation of the rotational cipher, also sometimes called
	 * the Caesar cipher.
	 * 
	 * The Caesar cipher is a simple shift cipher that relies on transposing all the
	 * letters in the alphabet using an integer key between 0 and 26. Using a key of
	 * 0 or 26 will always yield the same output due to modular arithmetic. The
	 * letter is shifted for as many values as the value of the key.
	 * 
	 * The general notation for rotational ciphers is ROT + <key>. The most commonly
	 * used rotational cipher is ROT13.
	 * 
	 * A ROT13 on the Latin alphabet would be as follows:
	 * 
	 * Plain: abcdefghijklmnopqrstuvwxyz Cipher: nopqrstuvwxyzabcdefghijklm It is
	 * stronger than the Atbash cipher because it has 27 possible keys, and 25
	 * usable keys.
	 * 
	 * Ciphertext is written out in the same formatting as the input including
	 * spaces and punctuation.
	 * 
	 * Examples: ROT5 omg gives trl ROT0 c gives c ROT26 Cool gives Cool ROT13 The
	 * quick brown fox jumps over the lazy dog. gives Gur dhvpx oebja sbk whzcf bire
	 * gur ynml qbt. ROT13 Gur dhvpx oebja sbk whzcf bire gur ynml qbt. gives The
	 * quick brown fox jumps over the lazy dog.
	 */
	static class RotationalCipher {
		private int key;

		public RotationalCipher(int key) {
			super();
			this.key = key % 26;
		}

		public String rotate(String string) {
			// rotated string will be same length as original
			final char[] rotated = new char[string.length()];

			// iterate through strings
			for (int i = 0; i < rotated.length; ++i) {
				final char c = string.charAt(i);

				// if it's alphabetic, rotate it, else don't do anything
				if (Character.isAlphabetic(c)) {
					// handle upper and lower case letters separately
					// just add the amount and then modulo 26 in case it overflows
					if (Character.isUpperCase(c)) {
						rotated[i] = (char) ('A' + ((c - 'A' + key) % 26));
					} else {
						rotated[i] = (char) ('a' + ((c - 'a' + key) % 26));
					}
				} else {
					rotated[i] = c;
				}
			}

			return new String(rotated);
		}

	}

	// keep list of primes rather than generating them on every call
	private List<Integer> primes = new ArrayList<Integer>();

	/**
	 * 12. Given a number n, determine what the nth prime is.
	 * 
	 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see
	 * that the 6th prime is 13.
	 * 
	 * If your language provides methods in the standard library to deal with prime
	 * numbers, pretend they don't exist and implement them yourself.
	 * 
	 * @param i
	 * @return
	 */
	public int calculateNthPrime(int i) {
		// catch illegal arguments
		if (i <= 0)
			throw new IllegalArgumentException("The nth prime must be a cardinal number, not " + i);

		// if we already have the prime, just return it from the list of primes
		if (i <= primes.size())
			return primes.get(i - 1);

		// start with the end of the primes list and generate up to i
		for (int count = primes.size(); count < i; ++count)
			primes.add(nextPrime());

		// primes is 0-indexed
		return primes.get(i - 1);
	}

	private int nextPrime() {
		// I can easily skip ahead two knowing that all primes greater than 2
		// are odd (i.e. not divisible by 2)

		if (primes.size() == 0)// if primes is empty, the next prime is 2
			return 2;
		if (primes.size() == 1) // if there's one prime (2), the next is 3
			return 3;

		// else proceed by skipping evens (should double the speed if we didn't do that)
		int nextPrime = primes.get(primes.size() - 1) + 2;

		// keep going until we find the next prime
		while (true) {
			final int stop = (int) Math.sqrt(nextPrime);

			// since I have a list of primes, I can just test those (rather than having to
			// try non-prime factors.
			for (final int prime : primes) {
				// this means we checked all primes up to the sqrt of nextPrime, meaning that
				// nextPrime is indeed prime
				if (prime > stop)
					return nextPrime;

				// stop this for loop if we found a factor of nextPrime, proceed to checking the
				// next candidate
				if (nextPrime % prime == 0)
					break;
			}

			// skip by 2's (so we're not checking evens...even though they'd fail the modulo
			// immediately)
			nextPrime += 2;
		}
		// nothing to return here because the only way out of the above while loop is to
		// return a value
	}

	/**
	 * 13 & 14. Create an implementation of the atbash cipher, an ancient encryption
	 * system created in the Middle East.
	 * 
	 * The Atbash cipher is a simple substitution cipher that relies on transposing
	 * all the letters in the alphabet such that the resulting alphabet is
	 * backwards. The first letter is replaced with the last letter, the second with
	 * the second-last, and so on.
	 * 
	 * An Atbash cipher for the Latin alphabet would be as follows:
	 * 
	 * Plain: abcdefghijklmnopqrstuvwxyz Cipher: zyxwvutsrqponmlkjihgfedcba It is a
	 * very weak cipher because it only has one possible key, and it is a simple
	 * monoalphabetic substitution cipher. However, this may not have been an issue
	 * in the cipher's time.
	 * 
	 * Ciphertext is written out in groups of fixed length, the traditional group
	 * size being 5 letters, and punctuation is excluded. This is to make it harder
	 * to guess things based on word boundaries.
	 * 
	 * Examples Encoding test gives gvhg Decoding gvhg gives test Decoding gsvjf
	 * rxpyi ldmul cqfnk hlevi gsvoz abwlt gives thequickbrownfoxjumpsoverthelazydog
	 *
	 */
	static class AtbashCipher {

		/**
		 * Question 13
		 * 
		 * @param string
		 * @return
		 */
		public static String encode(String string) {
			// returned encoded won't be the same size as string so use a StringBuilder
			final StringBuilder encoded = new StringBuilder();
			final int len = string.length(); // don't keep calling string.length()
			int letterCount = 0; // used to ensure blocks of length 5

			for (int i = 0; i < len; ++i) {
				// encode the character
				final char encodedC = switchChar(string.charAt(i));

				if (encodedC != 0) {// don't encode non-characters
					// don't add a space until the next alpha character is found
					if (letterCount == 5) {
						// we've written five characters, add space and reset counter
						letterCount = 0;
						encoded.append(' ');
					}
					encoded.append(encodedC);
					++letterCount;
				}
			}

			return encoded.toString();
		}

		/**
		 * Question 14
		 * 
		 * @param string
		 * @return
		 */
		public static String decode(String string) {
			// encoded string has extra spaces, so the decoded won't be the same length
			final StringBuilder decoded = new StringBuilder();
			final int len = string.length(); // don't keep calling length()

			for (int i = 0; i < len; ++i) {
				// encoding and decoding mean just switching back and forth
				final char encodedC = switchChar(string.charAt(i));

				// write character to decoded if it's an alpha character
				if (encodedC != 0) {
					decoded.append(encodedC);
				}
			}

			return decoded.toString();
		}

		// helper method for atbash cipher. Switches the character, changes it to lower
		// case, and returns 0 if the character is a non-alphanumeric characters (which
		// are ignored in the atbash cipher above)
		public static char switchChar(final char c) {
			if (Character.isAlphabetic(c)) {
				// we're going to make it lower case, but we need to subtract from a different
				// 'A' depending on upper or lower case
				if (Character.isUpperCase(c)) {
					return (char) ('z' - (c - 'A'));
				} else {
					return (char) ('z' - (c - 'a'));
				}
			} else if (Character.isDigit(c))// return numerals unchanged
				return c;
			return 0;// return 0 for non-alphanumeric characters
		}
	}

	/**
	 * 15. The ISBN-10 verification process is used to validate book identification
	 * numbers. These normally contain dashes and look like: 3-598-21508-8
	 * 
	 * ISBN The ISBN-10 format is 9 digits (0 to 9) plus one check character (either
	 * a digit or an X only). In the case the check character is an X, this
	 * represents the value '10'. These may be communicated with or without hyphens,
	 * and can be checked for their validity by the following formula:
	 * 
	 * (x1 * 10 + x2 * 9 + x3 * 8 + x4 * 7 + x5 * 6 + x6 * 5 + x7 * 4 + x8 * 3 + x9
	 * * 2 + x10 * 1) mod 11 == 0 If the result is 0, then it is a valid ISBN-10,
	 * otherwise it is invalid.
	 * 
	 * Example Let's take the ISBN-10 3-598-21508-8. We plug it in to the formula,
	 * and get:
	 * 
	 * (3 * 10 + 5 * 9 + 9 * 8 + 8 * 7 + 2 * 6 + 1 * 5 + 5 * 4 + 0 * 3 + 8 * 2 + 8 *
	 * 1) mod 11 == 0 Since the result is 0, this proves that our ISBN is valid.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isValidIsbn(String string) {
		// remove all non-digits/x's (upper or lower case 'x')
		string = string.replaceAll("[^\\dxX]+", "");

		// must be exactly of length 10 AND the first 9 must be all digits, followed by
		// either a digit or 'x' (upper or lower case)
		if (string.length() != 10 || !string.matches("\\d{9}[\\dxX]"))
			return false;
		// else iterate through digits

		int sum = 0;

		// compute sum for first 9 digits (the regex above already ensures the first 9
		// characters are all digits)
		for (int i = 0; i < 9; ++i) {
			sum += (10 - i) * (string.charAt(i) - '0');
		}

		// the last digit could be x
		final char last = string.charAt(9);

		// add 10 if it's an x otherwise, just add the last digit
		switch (last) {
		case 'x':
		case 'X':
			sum += 10;
			break;
		default:
			sum += last - '0';
		}

		// return modulo 11 of sum
		return sum % 11 == 0;
	}

	/**
	 * 16. Determine if a sentence is a pangram. A pangram (Greek: παν γράμμα, pan
	 * gramma, "every letter") is a sentence using every letter of the alphabet at
	 * least once. The best known English pangram is:
	 * 
	 * The quick brown fox jumps over the lazy dog.
	 * 
	 * The alphabet used consists of ASCII letters a to z, inclusive, and is case
	 * insensitive. Input will not contain non-ASCII symbols.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isPangram(String string) {
		// make it easier by just handling lower case letters
		string = string.toLowerCase();

		// use a set so that no duplicates are allowed
		final Set<Character> charSet = new HashSet<Character>(26);

		final int len = string.length();// don't keep calling length()

		for (int i = 0; i < len; ++i) {
			// if it's alphabetic, add it to the set, otherwise don't
			final char c = string.charAt(i);
			if (Character.isAlphabetic(c))
				charSet.add(c);
		}

		// if it has all of the letters, the set's size will be 26
		return charSet.size() == 26;
	}

	private final TemporalAmount gigasecond = Duration.ofSeconds(1000000000l);

	/**
	 * 17. Calculate the moment when someone has lived for 10^9 seconds.
	 * 
	 * A gigasecond is 10^9 (1,000,000,000) seconds.
	 * 
	 * @param given
	 * @return
	 */
	public Temporal getGigasecondDate(Temporal given) {

		if (given instanceof LocalDate) {
			// convert to second precision of LocalDateTime (assume we're starting from the
			// start of the day)
			// this is sort of the "base" case (this should return a value because this is a
			// LocalDateTime).
			return getGigasecondDate(((LocalDate) given).atStartOfDay());
		} else if (given instanceof Year) {
			// convert to a LocalDate (day 1 of the year) (which will then be handled by the
			// above case
			return getGigasecondDate(((Year) given).atDay(1));
		} else if (given instanceof YearMonth) {
			// similar to above, this will return a LocalDate which will then be handled by
			// this first case.
			return getGigasecondDate(((YearMonth) given).atDay(1));
		} else {
			// if we encounter something that's not one of the above and isn't compatible
			// with seconds, this will throw an exception.
			return given.plus(gigasecond);
		}
	}

	/**
	 * 18. Given a number, find the sum of all the unique multiples of particular
	 * numbers up to but not including that number.
	 * 
	 * If we list all the natural numbers below 20 that are multiples of 3 or 5, we
	 * get 3, 5, 6, 9, 10, 12, 15, and 18.
	 * 
	 * The sum of these multiples is 78.
	 * 
	 * @param i
	 * @param set
	 * @return
	 */
	public int getSumOfMultiples(int i, int[] set) {
		// use a sieve so that you just go through each number's multiples once
		final boolean[] sieve = new boolean[i];
		int sum = 0;// keep running sum

		// go through each multiple in the set
		for (final int multiple : set) {
			// fill in sieve for each multiple
			for (int num = multiple; num < i; num += multiple) {
				// if this number isn't present, add it to the sieve (set that entry to true)
				// and add it to the sum (next time this number is seen, it's sieve entry will
				// be true and it won't be re-added to the sum)
				if (!sieve[num]) {
					sieve[num] = true;
					sum += num;
				}
			}
		}

		return sum;
	}

	/**
	 * 19. Given a number determine whether or not it is valid per the Luhn formula.
	 * 
	 * The Luhn algorithm is a simple checksum formula used to validate a variety of
	 * identification numbers, such as credit card numbers and Canadian Social
	 * Insurance Numbers.
	 * 
	 * The task is to check if a given string is valid.
	 * 
	 * Validating a Number Strings of length 1 or less are not valid. Spaces are
	 * allowed in the input, but they should be stripped before checking. All other
	 * non-digit characters are disallowed.
	 * 
	 * Example 1: valid credit card number 1 4539 1488 0343 6467 The first step of
	 * the Luhn algorithm is to double every second digit, starting from the right.
	 * We will be doubling
	 * 
	 * 4_3_ 1_8_ 0_4_ 6_6_ If doubling the number results in a number greater than 9
	 * then subtract 9 from the product. The results of our doubling:
	 * 
	 * 8569 2478 0383 3437 Then sum all of the digits:
	 * 
	 * 8+5+6+9+2+4+7+8+0+3+8+3+3+4+3+7 = 80 If the sum is evenly divisible by 10,
	 * then the number is valid. This number is valid!
	 * 
	 * Example 2: invalid credit card number 1 8273 1232 7352 0569 Double the second
	 * digits, starting from the right
	 * 
	 * 7253 2262 5312 0539 Sum the digits
	 * 
	 * 7+2+5+3+2+2+6+2+5+3+1+2+0+5+3+9 = 57 57 is not evenly divisible by 10, so
	 * this number is not valid.
	 * 
	 * @param string
	 * @return
	 */
	public boolean isLuhnValid(String string) {
		// remove all white space
		string = string.replaceAll("\\s+", "");

		// strip out all non-digits (this shouldn't do anything for a valid Luhn Number
		// String. I can't just do this from the beginning because I need to know
		// whether or not there are invalid characters (i.e. non-digits) in the string
		final String digits = string.replaceAll("[^\\d]+", "");
		final int len = digits.length();// save length of digits for later

		// if the replaceAll above actually removed anything, it's not a valid Luhn
		// Number String. It's also invalid if it's only of length 1 or of even length
		// (the first digit is supposed to be the check digit and shouldn't be included
		// in the sum)
		if (len <= 1 || len != string.length() || len % 2 != 1)
			return false;
//		// else validate Luhn Number String

		int sum = 0;

		// start with the second digit (ignore the first, check digit)
		for (int i = 1; i < len; i += 2) {
			// double every second digit (subtract 9 if greater than 9
			sum += doubleLuhnNumber(digits.charAt(i));
			// I'm guaranteed to have this "next" digit because I checked above that the
			// number of digits is odd.
			sum += digits.charAt(i + 1) - '0';
		}

		return sum % 10 == 0;
	}

	// this assumes c is a valid digit
	private static int doubleLuhnNumber(final char c) {
		final int n = 2 * (c - '0');
		return n >= 10 ? n - 9 : n;
	}

	// these hold the indexes into the OPERATORS array of the strings (this makes
	// figuring out which operation to do easier)
	public static final int PLUS = 0;
	public static final int MINUS = 1;
	public static final int MULT = 2;
	public static final int DIVIDE = 3;

	// setup map so that we can get to the operation without having to do a search
	public static final Map<String, Integer> OPERATION_MAP = new HashMap<String, Integer>(4);

	/**
	 * I'm using a regex to match the entire input string for math operations (after
	 * I switch to lower case). The below goes through each piece of the regex. The
	 * idea is pretty simple, grab three groups: 1) left operand, 2) operation, and
	 * 3) right operand (trim off of the rest).
	 * 
	 * "\Awhat\sis\s" (really "what is"):
	 * 
	 * The first part matches the beginning of the string and starts with "what is "
	 * <-- I use white space instead of literal spaces and there can be as many as
	 * you want.
	 * 
	 * (-?[\\d]+) - +/- integer, left operand (1st group)
	 * 
	 * I then match a positive or negative integer (which could actually start with
	 * a 0 but who cares).
	 * 
	 * (\\s+[^\\d]+\\s+) - operation (2nd group)
	 * 
	 * Then I match not digits in between surrounded by whitespace (which I will
	 * trim off below).
	 * 
	 * (-?[\\d]+) - same as right operand (3rd group)
	 * 
	 * Finally I match the last integer (positive or negative).
	 * 
	 * "\?\z"
	 * 
	 * Finally I match the end of input with a lone "?" and the end of input (\z).
	 */
	public static final Pattern MATH_PATTERN = Pattern
			.compile("\\Awhat\\s+is\\s+(-?\\d+)(\\s[^\\d]+\\s)(-?\\d+)\\?\\z");

	// setup map
	static {
		OPERATION_MAP.put("plus", PLUS);
		OPERATION_MAP.put("minus", MINUS);
		OPERATION_MAP.put("multiplied by", MULT);
		OPERATION_MAP.put("divided by", DIVIDE);
	}

	/**
	 * 20. Parse and evaluate simple math word problems returning the answer as an
	 * integer.
	 * 
	 * Add two numbers together.
	 * 
	 * What is 5 plus 13?
	 * 
	 * 18
	 * 
	 * Now, perform the other three operations.
	 * 
	 * What is 7 minus 5?
	 * 
	 * 2
	 * 
	 * What is 6 multiplied by 4?
	 * 
	 * 24
	 * 
	 * What is 25 divided by 5?
	 * 
	 * 5
	 * 
	 * @param string
	 * @return
	 */
	public int solveWordProblem(String string) {
		// trim off leading/trailing whitespace then convert to lower case
		string = string.trim().toLowerCase();

		// create matcher for this string
		final Matcher matcher = MATH_PATTERN.matcher(string);

		// if it doesn't match my regex, it's not a recognized request
		if (!matcher.matches())
			throw new IllegalArgumentException("Unrecognized request: " + string);

		// group 1 is the left operand, group 2 is the operation, and group 3 is the
		// right operand
		final int leftOperand = Integer.parseInt(matcher.group(1));
		final int rightOperand = Integer.parseInt(matcher.group(3));
		final String operand = matcher.group(2).trim();

		// use operation map to jump to the operation, if this returns null, I can't put
		// it into my switch statement below so catch that case here.
		final Integer op = OPERATION_MAP.get(operand);

		if (op == null)
			throw new IllegalArgumentException("Unrecognized operation: " + operand);

		// switch on the integer values
		switch (op) {
		case PLUS:
			return leftOperand + rightOperand;
		case MINUS:
			return leftOperand - rightOperand;
		case MULT:
			return leftOperand * rightOperand;
		case DIVIDE:
			return leftOperand / rightOperand;
		default:
			// this isn't going to happen
			throw new IllegalArgumentException("Unknown operation: " + operand);
		}
	}
}
