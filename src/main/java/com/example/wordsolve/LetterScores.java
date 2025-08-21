package com.example.wordsolve;

import java.util.Map;

public class LetterScores
{
    public static int getLetterScore(String letter)
    {
        return baseLetterScores.get(letter.toLowerCase());
    }

    /// Immutable java data struct. For scrabble scoring.
    private static final Map<String, Integer> baseLetterScores = Map.ofEntries(
            Map.entry("a", 1),
            Map.entry("b", 3),
            Map.entry("c", 3),
            Map.entry("d", 2),
            Map.entry("e", 1),
            Map.entry("f", 4),
            Map.entry("g", 2),
            Map.entry("h", 4),
            Map.entry("i", 1),
            Map.entry("j", 8),
            Map.entry("k", 5),
            Map.entry("l", 1),
            Map.entry("m", 3),
            Map.entry("n", 1),
            Map.entry("o", 1),
            Map.entry("p", 3),
            Map.entry("q", 10),
            Map.entry("r", 1),
            Map.entry("s", 1),
            Map.entry("t", 1),
            Map.entry("u", 1),
            Map.entry("v", 4),
            Map.entry("w", 4),
            Map.entry("x", 8),
            Map.entry("y", 4),
            Map.entry("z", 10)
    );
}
