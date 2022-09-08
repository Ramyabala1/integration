package com.emanas.middleware.utility;

public class PatternUtil {

	public static final String patternMessage = "Invalid characters passed.";

	public static final String address = "^[a-zA-Z0-9-#/()., ]*$";
	public static final String name = "^[a-zA-Z-/0-9.@ ]*$";
	public static final String username = "^[a-zA-Z0-9.@ ]*$";
	public static final String orgName = "^[a-zA-Z0-9/.():, &-]*$";
	public static final String alphabetOnly = "^[a-zA-Z ]*$";
	public static final String alphaNumeric = "^[a-zA-Z0-9 ]*$";
	public static final String commoncontacts = "^[a-zA-Z0-9.@-_]*$";
	public static final String realNumberOnly = "^[0-9]*\\.?[0-9]*$";
	public static final String pincodeOnly = "^[0-9]*$";
	public static final String phoneNumberOnly = "^[0-9]*$";
	public static final String fileNameOnly = "^[a-zA-Z0-9-_. ]*$";
	public static final String session = "^[a-zA-Z0-9-_.:#@]*$";
	public static final String uuids = "^[a-zA-Z0-9-_]*$";
	public static final String comments = "^[a-zA-Z0-9-#\n/().:;\\\"'%&@+=?_, ]*$";
	public static final String json = "^[a-zA-Z0-9-#/\\(\\).:;\\\"'%&@+=?_, \\{\\}\\[\\]]*$";
	public static final String email = "^[A-Za-z0-9+_.-]+@(.+)$";

}
