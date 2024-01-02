import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter3 = new AtomicInteger();
    public static AtomicInteger counter4 = new AtomicInteger();
    public static AtomicInteger counter5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[10_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
            System.out.println(texts[i]);
    /*        texts[i].toCharArray();
            for (int j = 0; j < texts[i].length(); j++) {
                System.out.println(j);
            }

     */
        }


        Thread casePalindrome = new Thread(
                () -> {
                    for (String text : texts) {
                        if (isCasePalindrome(text) & !isCaseSame(text)) {
                            counter(text.length());
                        }
                    }
                });
        casePalindrome.start();

        Thread caseSame = new Thread(
                () -> {
                    for (String text : texts) {
                        if (isCaseSame(text)) {
                            counter(text.length());
                        }
                    }
                });
        caseSame.start();

        Thread caseOrder = new Thread(
                () -> {
                    for (String text : texts) {
                        if (isCaseOrder(text) & !isCaseSame(text)) {
                            counter(text.length());
                        }
                    }
                });
        caseOrder.start();

        casePalindrome.join();
        caseSame.join();
        caseOrder.join();

        System.out.println("Красивых слов длиной 3: " + counter3);
        System.out.println("Красивых слов длиной 4: " + counter4);
        System.out.println("Красивых слов длиной 5: " + counter5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isCasePalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isCaseSame(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static boolean isCaseOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void counter(int length) {
        if (length == 3) {
            counter3.getAndIncrement();
        } else if (length == 4) {
            counter4.getAndIncrement();
        } else if (length == 5) {
            counter5.getAndIncrement();
        }
    }
}