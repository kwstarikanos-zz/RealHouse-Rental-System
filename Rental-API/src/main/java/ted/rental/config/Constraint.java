package ted.rental.config;

public class Constraint {

    /*Username Constraints * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
    * Front end: ^[a-z\d\.]{5,}$
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public final static String USERNAME_PATTERN = "^[a-z\\d\\.]{5,}$";
    public final static String USERNAME_PATTERN_MESSAGE = "Username must consists at least 5 characters (A-Z, 0-9) and/or (\".\") between them!";
    public final static int USERNAME_MIN = 5;
    public final static int USERNAME_MAX = 20;

    /*Password Constraints * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
    * Front end: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public final static String PASSWORD_PATTERN = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    public final static String PASSWORD_PATTERN_MESSAGE = "Valid passwords must be a mix of upper case, lower case, number/special characters!";
    public final static int PASSWORD_MIN = 8;
    public final static int PASSWORD_MAX = 20;

    /*Firstname Constraints * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
    * Front end: /^([A-ZΑ-Ω]){1}[^0-9]*$/
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public final static String FIRSTNAME_PATTERN = "^([A-ZΑ-Ω]){1}[^0-9]*$";
    public final static String FIRSTNAME_PATTERN_MESSAGE = "A valid firstname must consist only of alphabet characters!";
    public final static int FIRSTNAME_MIN = 3;
    public final static int FIRSTNAME_MAX = 20;

    /*Lastname Constraints * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
    * Front end: /^([A-ZΑ-Ω]){1}[^0-9]*$/
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public final static String LASTNAME_PATTERN = "^([A-ZΑ-Ω]){1}[^0-9]*$";
    public final static String LASTNAME_PATTERN_MESSAGE = "A valid lastname must consist only of alphabet characters!";
    public final static int LASTNAME_MIN = 3;
    public final static int LASTNAME_MAX = 20;

    /*Phone Constraints * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * /
    * Front end: /^(?=^.{10,10}$)[0-9]*$/
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public final static String PHONE_PATTERN = "^(?=^.{10,10}$)[0-9]*$";
    public final static String PHONE_PATTERN_MESSAGE = "A valid phone must consist exactly 10 numbers!";
    public final static int PHONE_MIN = 10;
    public final static int PHONE_MAX = 10;

    /*Locations Constraints*/
    public final static int ROUTE_MAX = 45;
    public final static int STREET_MAX = 45;
    public final static int LOCALITY_MAX = 45;
    public final static int COUNTRY_MAX = 45;
    public final static int POSTAL_CODE_MAX = 10;
    public final static int FORMATTED_ADRESS_MAX = 200;
    public final static int ADMINISTRATIVE_AREA_LEVEL_MAX = 50;

    public final static int TOKEN_MAX = 255;

}
