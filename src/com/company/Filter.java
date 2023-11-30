package com.company;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Filter {

    private HashSet<Character> absent;
    private HashMap<Integer, Character> bulls;
    private HashMap<Integer, HashSet<Character>>  cows;

    public Filter() {
        absent = new HashSet<>();
        bulls = new HashMap<>();
        cows = new HashMap<>();
    }

    public void addFilter(Set<Character> absent, Map<Integer, Character> bulls, Map<Integer, Character> cows) {
        this.absent.addAll(absent);
        this.bulls.putAll(bulls);
        for (Integer i: cows.keySet()) {
            if (this.cows.containsKey(i)) {
                this.cows.get(i).add(cows.get(i));
            }
            else {
                this.cows.put(i, new HashSet<Character>());
                this.cows.get(i).add(cows.get(i));
            }
        }
    }

    @Override
    public String toString() {
        return "Filter{" +
                "absent=" + absent +
                ", bulls=" + bulls +
                ", cows=" + cows +
                '}';
    }

    public boolean checkWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            Character ch = word.charAt(i);
            if (absent.contains(ch)) { return false; } // Отсутствующие
            if (bulls.containsKey(i) && !bulls.get(i).equals(ch)) { return false; } // На своих местах
            if (cows.containsKey(i)) {
                for (Character c : cows.get(i)) {
                    if (c.equals(ch)) { return false; }  // Не на своих местах
                }
            }
        }
        return true;
    }
}
