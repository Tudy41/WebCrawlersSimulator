package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clasa ce permite crerea unei liste de tokenuri pe care fiecare robot o poate extrage in mod sincron din fiecare
 * thread corespunzator.
 */
public class SharedMemory {
    private final List<Token> tokens = new ArrayList<>();

    public SharedMemory(int n) {
        for (int i = 0; i < n * n * n; i++) {
            tokens.add(new Token(i));
        }
        Collections.shuffle(tokens);
    }

    public synchronized List<Token> extractTokens(int howMany) {
        List<Token> extracted = new ArrayList<>();
        Collections.shuffle(tokens);
        for (int i = 0; i < howMany; i++) {
            if (tokens.isEmpty()) {
                break;
            }
            extracted.add(tokens.get(i));
        }
        return extracted;
    }

}
