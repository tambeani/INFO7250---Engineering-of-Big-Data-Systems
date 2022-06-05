package com.info7250.mongodb;

//Java implementation of the approach
import java.util.*;

class GFG
{

//Function that returns true if c is a vowel
static boolean isVowel(char c)
{
	return (c == 'a' || c == 'e' ||
			c == 'i' || c == 'o' || c == 'u');
}

//Function to return the count of sub-strings
//that contain every vowel at least
//once and no consonant
static int countSubstringsUtil(char []s)
{
	int count = 0;

	// Map is used to store count of each vowel
	Map<Character, Integer> mp = new HashMap<Character, Integer>();

	int n = s.length;

	// Start index is set to 0 initially
	int start = 0;

	for (int i = 0; i < n; i++)
	{
		if(mp.containsKey(s[i]))
		{
			mp.put(s[i], mp.get(s[i]) + 1);
		}
		else
		{
			mp.put(s[i], 1);
		}

		// If substring till now have all vowels
		// atleast once increment start index until
		// there are all vowels present between
		// (start, i) and add n - i each time
		while (mp.containsKey('a') && mp.containsKey('e') &&
			mp.containsKey('i') && mp.containsKey('o') &&
			mp.containsKey('u') && mp.get('a') > 0 &&
			mp.get('e') > 0 && mp.get('i') > 0 &&
			mp.get('o') > 0 && mp.get('u') > 0)
		{
			count += n - i;
			mp.put(s[start], mp.get(s[start]) - 1);

			start++;
		}
	}
	return count;
}

//Function to extract all maximum length
//sub-strings in s that contain only vowels
//and then calls the countSubstringsUtil() to find
//the count of valid sub-strings in that string
static int countSubstrings(String s)
{
	int count = 0;
	String temp = "";

	for (int i = 0; i < s.length(); i++)
	{

		// If current character is a vowel then
		// append it to the temp string
		if (isVowel(s.charAt(i)))
		{
			temp += s.charAt(i);
		}

		// The sub-string containing all vowels ends here
		else
		{

			// If there was a valid sub-string
			if (temp.length() > 0)
				count += countSubstringsUtil(temp.toCharArray());

			// Reset temp string
			temp = "";
		}
	}

	// For the last valid sub-string
	if (temp.length() > 0)
		count += countSubstringsUtil(temp.toCharArray());

	return count;
}

//Driver code
public static void main(String[] args)
{
	String s = "aaeiouxa";

	System.out.println(countSubstrings(s));
}
}

//This code is contributed by Princi Singh
