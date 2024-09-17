import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        String[] texts = new String[1000];
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateRoute("RLRFR", 100);
        }

        for (String text : texts) {
            Thread thread = new Thread(() -> {
                int countR = 0;
                for (int j = 0; j < text.length(); j++) {

                    if (text.charAt(j) == 'R') {
                        countR++;
                    }
                }
                letterRFrequency(text);

            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(e.getMessage());
            }
        }
        printResults();
    }

    private static void letterRFrequency(String line) {
        int frequency = (int) line.chars().filter(ch -> ch == 'R').count();

        synchronized (sizeToFreq) {
            if (sizeToFreq.containsKey(frequency)) {
                sizeToFreq.put(frequency, sizeToFreq.get(frequency) + 1);
            } else {
                sizeToFreq.put(frequency, 1);
            }
        }
    }

    private static void printResults() {
        int maxFrequency = 0;
        int maxCount = 0;

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxFrequency = entry.getKey();
            }
        }
        System.out.println("Самое частое количество повторений " + maxFrequency + " (встретилось " + maxCount + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}




