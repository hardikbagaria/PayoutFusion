package extras;

public class NumberToWordsConverter {

    private static final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                                                   "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
                                                   "Eighteen", "Nineteen"};
    private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] THOUSANDS = {"", "Thousand", "Lakh", "Crore"};

    public static String numberToWords(int num) {
        if (num == 0) return "Zero";
        return convert(num).trim();
    }

    public static String convert(int num) {
        if (num < 20) {
            return LESS_THAN_20[num];
        } else if (num < 100) {
            return TENS[num / 10] + (num % 10 > 0 ? " " + convert(num % 10) : "");
        } else if (num < 1000) {
            return LESS_THAN_20[num / 100] + " Hundred" + (num % 100 > 0 ? " and " + convert(num % 100) : "");
        } else if (num < 100000) {  // Up to Lakh
            return convert(num / 1000) + " Thousand" + (num % 1000 > 0 ? " " + convert(num % 1000) : "");
        } else if (num < 10000000) {  // Up to Crore
            return convert(num / 100000) + " Lakh" + (num % 100000 > 0 ? " " + convert(num % 100000) : "");
        } else {  // Up to Crores
            return convert(num / 10000000) + " Crore" + (num % 10000000 > 0 ? " " + convert(num % 10000000) : "");
        }
    }

    public static void main(String[] args) {
        int amount = 49202;
        System.out.println(numberToWords(amount));  // Output: One Crore Twenty Two Lakh Forty Five Thousand Six Hundred and Seventy Eight
    }
}

