import java.util.*;

public class URLShortener {
    private static final String BASE_URL = "http://short.ly/";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_KEY_LENGTH = 6;

    private Map<String, String> shortToLong = new HashMap<>();
    private Map<String, String> longToShort = new HashMap<>();
    private Random random = new Random();

    // Generate random key
    private String generateKey() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_KEY_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    // Shorten a long URL
    public String shortenURL(String longURL) {
        if (longToShort.containsKey(longURL)) {
            return BASE_URL + longToShort.get(longURL);
        }

        String key;
        do {
            key = generateKey();
        } while (shortToLong.containsKey(key));

        shortToLong.put(key, longURL);
        longToShort.put(longURL, key);

        return BASE_URL + key;
    }

    // Expand a short URL
    public String expandURL(String shortURL) {
        String key = shortURL.replace(BASE_URL, "");
        return shortToLong.getOrDefault(key, "URL not found");
    }

    // For testing
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        URLShortener shortener = new URLShortener();

        while (true) {
            System.out.println("\n1. Shorten URL\n2. Expand URL\n3. Exit");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Enter long URL: ");
                String longUrl = sc.nextLine();
                System.out.println("Shortened URL: " + shortener.shortenURL(longUrl));
            } else if (choice == 2) {
                System.out.print("Enter short URL: ");
                String shortUrl = sc.nextLine();
                System.out.println("Original URL: " + shortener.expandURL(shortUrl));
            } else {
                break;
            }
        }

        sc.close();
    }
}
