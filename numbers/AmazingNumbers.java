package numbers;

import java.util.*;


public class AmazingNumbers {
    Scanner scanner = new Scanner(System.in);
    boolean on = true;

    String[] allProperties = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING", "SAD", "HAPPY"};

    String[] mu1 = {"EVEN", "ODD"};
    String[] mu2 = {"-EVEN", "-ODD"};
    String[] mu3 = {"DUCK", "SPY"};
    String[] mu4 = {"-DUCK", "-SPY"};
    String[] mu5 = {"SQUARE", "SUNNY"};
    String[] mu6 = {"-SQUARE", "-SUNNY"};
    String[] mu7 = {"SAD", "HAPPY"};
    String[] mu8 = {"-SAD", "-HAPPY"};

    long propertyCount;

    public void run() {
        System.out.println(
                "Welcome to Amazing Numbers!\n" +
                        "Supported requests:\n" +
                        "- enter a natural number to know its properties;\n" +
                        "- enter two natural numbers to obtain the properties of the list:\n" +
                        "  * the first parameter represents a starting number;\n" +
                        "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                        "- two natural numbers and properties to search for;\n" +
                        "- a property preceded by minus must not be present in numbers;\n" +
                        "- separate the parameters with one space;\n" +
                        "- enter 0 to exit.");
        start();
    }

    protected void start() {
        while (on) {
            System.out.println();
            System.out.print("\nEnter a request: ");
            String[] input = scanner.nextLine().split(" ");
            long num = Long.parseLong(input[0]);
            if (input.length == 1) {
                if (num == 0) {
                    System.out.println("\nGoodbye!");
                    on = false;
                } else {
                    calculate(num, -1);
                }
            } else {
                long count = Long.parseLong(input[1]);
                propertyCount = 0;
                if (count < 1) {
                    System.out.println("\n The second parameter should be a natural number.");
                }
                if (input.length == 2) {
                    processCount(num, count, allProperties, null);
                } else {
                    String[] propertyList = new String[input.length - 2];
                    for (int i = 2; i < input.length; i++) {
                        propertyList[i - 2] = input[i].toUpperCase();
                    }
                    Arrays.sort(propertyList);
                    ArrayList<String> invalid = new ArrayList<>();
                    ArrayList<String> negate = new ArrayList<>();
                    ArrayList<String> valid = new ArrayList<>();
                    for (String value : propertyList) {
                        if (!Arrays.asList(allProperties).contains(value.replace("-", "").toUpperCase())) {
                            invalid.add(value);
                        } else if (value.charAt(0) == '-') {
                            negate.add(value.replace("-", ""));
                        } else {
                            valid.add(value);
                        }
                    }
                    boolean isMutuallyExclusive = false;
                    if (invalid.size() == 1) {
                        System.out.println("\nThe property " + Arrays.toString(invalid.toArray()) + " is wrong.\n" +
                                "Available properties: " + Arrays.toString(allProperties));
                    } else if (invalid.size() > 1) {
                        System.out.println("\nThe properties " + Arrays.toString(invalid.toArray()) + " are wrong.\n" +
                                "Available properties: " + Arrays.toString(allProperties));
                    } else {
                        if (negate.size() > 0 && valid.size() > 0) {
                            for (String val : valid) {
                                for (String neg : negate) {
                                    if (Objects.equals(val, neg)) {
                                        isMutuallyExclusive = true;
                                        System.out.println("\nThe request contains mutually exclusive properties: " + "[" + val + ", " + "-" + neg + "]" +
                                                "\nThere are no numbers with these properties.");
                                    }
                                }
                            }
                        }
                        if (!isMutuallyExclusive) {
                            if (new HashSet<>(Arrays.asList(propertyList)).containsAll(Arrays.asList(mu1))) {
                                mutuallyExclusive(mu1);
                            } else if (new HashSet<>(Arrays.asList(propertyList)).containsAll(Arrays.asList(mu2))) {
                                mutuallyExclusive(mu2);
                            } else if (new HashSet<>(Arrays.asList(propertyList)).containsAll(Arrays.asList(mu3))) {
                                mutuallyExclusive(mu3);
                            } else if (new HashSet<>(Arrays.asList(propertyList)).containsAll(Arrays.asList(mu4))) {
                                mutuallyExclusive(mu4);
                            } else if (new HashSet<>(Arrays.asList(propertyList)).containsAll(Arrays.asList(mu5))) {
                                mutuallyExclusive(mu5);
                            } else if (new HashSet<>(Arrays.asList(propertyList)).containsAll(Arrays.asList(mu6))) {
                                mutuallyExclusive(mu6);
                            } else if (new HashSet<>(Arrays.asList(propertyList)).containsAll(Arrays.asList(mu7))) {
                                mutuallyExclusive(mu7);
                            } else if (new HashSet<>(Arrays.asList(propertyList)).containsAll(Arrays.asList(mu8))) {
                                mutuallyExclusive(mu8);
                            } else {
                                String[] validProps = new String[valid.size()];
                                validProps = valid.toArray(validProps);
                                String[] negateProps = new String[negate.size()];
                                negateProps = negate.toArray(negateProps);
                                processCount(num, count, validProps, negateProps);
                            }
                        }
                    }
                }
            }

        }
    }

    public void calculate(long num, long count) {
        StringBuilder response = new StringBuilder();
         if (num < 1) {
            response.append("\nThe first parameter should be a natural number or zero.");
        } else if (count == -1) {
            response.append("\n" + "Properties of ").append(num)
                    .append("\n buzz: ").append(buzz(num))
                    .append("\n duck: ").append(duck(num))
                    .append("\n palindromic: ").append(palindromic(num))
                    .append("\n gapful: ").append(gapful(num))
                    .append("\n spy: ").append(spy(num))
                    .append("\n square: ").append(square(num))
                    .append("\n sunny: ").append(sunny(num))
                    .append("\n jumping: ").append(jumping(num))
                    .append("\n sad: ").append(sad(num))
                    .append("\n happy: ").append(happy(num))
                    .append("\n even: ").append(num % 2 == 0)
                    .append("\n odd: ").append(num % 2 == 1);
        }
        System.out.println(response);
    }

    private void multipleCalculations(long num, String[] properties, String[] negate) {
        StringBuilder response = new StringBuilder();
        response.append("\n").append(num).append(" is ");
        if (num % 2 == 0) {
            response.append("even, ");
        }
        if (num % 2 == 1) {
            response.append("odd, ");
        }
        if (buzz(num)) {
            response.append("buzz, ");
        }
        if (duck(num)) {
            response.append("duck, ");
        }
        if (palindromic(num)) {
            response.append("palindromic, ");
        }
        if (gapful(num)) {
            response.append("gapful, ");
        }
        if (spy(num)) {
            response.append("spy, ");
        }
        if (square(num)) {
            response.append("square, ");
        }
        if (sunny(num)) {
            response.append("sunny, ");
        }
        if (jumping(num)) {
            response.append("jumping, ");
        }
        if (sad(num)) {
            response.append("sad, ");
        }
        if (happy(num)) {
            response.append("happy, ");
        }
        response.deleteCharAt(response.length() - 2);
        String[] responseArray = String.valueOf(response).toUpperCase().replaceAll(",", "").split(" ");
        List<String> responseList = Arrays.asList(responseArray);
        if (properties == allProperties) {
            propertyCount++;
            System.out.print(response);
        } else {
            if (!Arrays.asList(negate).isEmpty()) {
                if (!new HashSet<>(responseList).containsAll(Arrays.asList(properties)) || responseList.stream().anyMatch(List.of(negate)::contains)) {
                    response.delete(0, response.length());
                } else {
                    propertyCount++;
                    System.out.print(response);
                }
            } else {
                if (!new HashSet<>(responseList).containsAll(Arrays.asList(properties))) {
                    response.delete(0, response.length());
                } else {
                    propertyCount++;
                    System.out.print(response);
                }
            }
        }
    }

    private void processCount(long num, long count, String[] properties, String[] negate) {
        for (long i = propertyCount; propertyCount < count; i++) {
            multipleCalculations(num + i, properties, negate);
        }
    }

    private void mutuallyExclusive(String[] array) {
        System.out.println("\nThe request contains mutually exclusive properties: " + Arrays.toString(array) +
                "\nThere are no numbers with these properties.");
    }

    private static boolean buzz(long num) {
        return num % 10 == 7 || num % 7 == 0;
    }

    private static boolean duck(long num) {
        while (num != 0) {
            if (num % 10 == 0) {
                return true;
            }
            num = num / 10;
        }
        return false;
    }

    private static boolean palindromic(long num) {
        String numStr = String.valueOf(num);
        return numStr.equals(new StringBuilder(numStr).reverse().toString());
    }

    private static boolean gapful(long num) {
        StringBuilder numStr = new StringBuilder(String.valueOf(num));
        if (numStr.length() < 3) {
            return false;
        }
        long firstLast = Long.parseLong(String.valueOf(numStr.charAt(0)) + (numStr.charAt(numStr.length() - 1)));
        return num % firstLast == 0;
    }

    private static boolean spy(long num) {
        String numStr = String.valueOf(num);
        String[] strArray = numStr.split("");
        int[] digits = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            int digit = Integer.parseInt(strArray[i]);
            digits[i] = digit;
        }
        long sum = 0;
        for (int digit : digits) {
            sum += digit;
        }
        long product = 1;
        for (int digit : digits) {
            product = product * digit;
        }
        return sum == product;
    }

    private static boolean square(long num) {
        double sqrt = Math.sqrt(num);
        return ((sqrt - Math.floor(sqrt)) == 0);
    }

    private static boolean sunny(long num) {
        num++;
        double sqrt = Math.sqrt(num);
        return ((sqrt - Math.floor(sqrt)) == 0);
    }

    private static boolean jumping(long num) {
        for (long previous = num % 10, rest = num / 10; rest > 0; rest /= 10) {
            long current = rest % 10;
            long delta = previous - current;
            if (delta * delta != 1) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    private static boolean sad(long num) {
        return !happy(num);
    }

    private static boolean happy(long num) {
        long n = num;
        long sum = 0;
        while (n > 0) {
            long x = n % 10;
            sum = sum + (x * x);
            n = n / 10;
        }
        if (sum == 1) {
            return true;
        } else if (sum == 4) {
            return false;
        }
        return happy(sum);
    }
}
