import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForceBreaker extends Thread {
    ForceBreaker(String name) {
        super(name);
    }

    private String encode(String originalString, String method) {
        try {
            StringBuilder encoded = new StringBuilder();

            MessageDigest md = MessageDigest.getInstance(method);
            byte[] originalBytes = md.digest(originalString.getBytes());

            for (byte originalByte : originalBytes) {
                encoded.append(Integer.toHexString((originalByte & 0xFF) | 0x100), 1, 3);
            }
            return encoded.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is no method like this.");
        }
        return null;
    }

    public String findSalt(int length, String hash, String result) {
        String[] alpha = ("a b c d e f g h i j k l m n o p q r s t u v w x y z A B C D E F G H I J K L M N O P Q R S T U V W X Y Z 0 1 2 3 4 5 6 7 8 9 . /")
                .split(" ");

        File file = new File("checkpoint" + length + ".txt");

        StringBuilder saltkey = new StringBuilder(10);
        StringBuilder saltkeysalt = new StringBuilder(10);
        StringBuilder keysalt = new StringBuilder(10);

        StringBuilder sk = new StringBuilder(32);
        StringBuilder sks = new StringBuilder(32);
        StringBuilder ks = new StringBuilder(32);

        int step = 0;
        int[] numHolder = new int[length];
        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                int i;
                int js = 0;
                int jb = 0;
                while ((i = fr.read()) != -1) {
                    char c = (char) i;

                    if(c == ',') {jb++; js = 0;}
                    else if (c=='['||c==']'||(c == ' ')||(c == '\n')) continue;
                    else {
                        if(js == 0) {
                            numHolder[jb] = Integer.parseInt(String.valueOf(c))*10;
                            js++;
                        }else numHolder[jb] += Integer.parseInt(String.valueOf(c));
                    }
                }
            } catch (IOException e) {
                System.out.println("Something went wrong reading checkpoint.");
            }
        }

        do {
            numHolder[step]++;
            if (numHolder[step] == alpha.length) {
                numHolder[step] = 0;
                step++;
            } else {
                step = 0;
            }

            StringBuilder salt = new StringBuilder();
            Arrays.stream(numHolder)
                    .forEach(n -> {
                        if (n != alpha.length) salt.append(alpha[n]);
                    });

            saltkey.append(salt).append(result);
            saltkey.append(salt).append(result).append(salt);
            keysalt.append(result).append(salt);

            sk.append(encode(saltkey.toString(), "MD5"));
            sk.append(encode(saltkeysalt.toString(), "MD5"));
            ks.append(encode(keysalt.toString(), "MD5"));

            if (saltkey.toString().equals(hash) || keysalt.toString().equals(hash) || saltkeysalt.toString().equals(hash)) {
                String str = null;
                file.delete();
                if (saltkey.toString().equals(result)) str = "salt+key";
                else if (keysalt.toString().equals(result)) str = "key+salt";
                else if (saltkeysalt.toString().equals(result)) str = "salt+key+salt";
                return String.format("Salt is '%s' with '%s' method.", salt, str);
            }

            saltkey.delete(0, saltkey.length());
            saltkeysalt.delete(0, saltkeysalt.length());
            keysalt.delete(0, keysalt.length());
            sk.delete(0, sk.length());
            sks.delete(0, sks.length());
            ks.delete(0, ks.length());

            salt.delete(0, salt.length());
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(Arrays.toString(numHolder));
            } catch (IOException e) {
                System.out.println("Error saving checkpoint");
            }
        } while (step != length);
        file.delete();
        return "Nothing found for length: " + length;
    }
}

class Launcher {
    public static void main(String[] args) {
        String code = "R3DC3";
        String target = "04cb7e248b73ffd858fc60eb477e5385";
        List<ForceBreaker> forceBreakers = new ArrayList<>();

        for (int i = 8, j = 0; i != 9; i++, j++) {
            forceBreakers.add(j, new ForceBreaker("" + i));
            forceBreakers.get(j).start();
            System.out.println(forceBreakers.get(j).findSalt(i, target, code));
        }

    }
}