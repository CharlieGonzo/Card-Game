package org.example.cardgame24.util;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;

public class EquationSolver {
    // Initialize the engine once to save resources
    // Inside your class
    private static final String[] OPERATORS = {"+", "-", "*", "/"};

    public static String solve(Integer[] inputNums) {
        double[] nums = Arrays.stream(inputNums).mapToDouble(Double::valueOf).toArray();
        List<double[]> permutations = new ArrayList<>();
        generateUniquePermutations(nums, 0, permutations, new HashSet<>());

        for (double[] p : permutations) {
            for (String op1 : OPERATORS) {
                for (String op2 : OPERATORS) {
                    for (String op3 : OPERATORS) {
                        String res = testPatterns(p, new String[]{op1, op2, op3});
                        if (res != null) return res.replaceAll(" ","");
                    }
                }
            }
        }
        return null;
    }

    private static String testPatterns(double[] n, String[] o) {
        String[] patterns = {
                String.format("((%f %s %f) %s %f) %s %f", n[0], o[0], n[1], o[1], n[2], o[2], n[3]),
                String.format("(%f %s (%f %s %f)) %s %f", n[0], o[0], n[1], o[1], n[2], o[2], n[3]),
                String.format("%f %s ((%f %s %f) %s %f)", n[0], o[0], n[1], o[1], n[2], o[2], n[3]),
                String.format("%f %s (%f %s (%f %s %f))", n[0], o[0], n[1], o[1], n[2], o[2], n[3]),
                String.format("(%f %s %f) %s (%f %s %f)", n[0], o[0], n[1], o[2], n[2], o[1], n[3])
        };

        for (String expression : patterns) {
            try {
                double val = new ExpressionBuilder(expression).build().evaluate();
                if (Math.abs(val - 24.0) < 0.0001) {
                    return expression.replaceAll("\\.0+", "").replaceAll("\\s+", " ");
                }
            } catch (Exception ignored) { /* Handles division by zero */ }
        }
        return null;
    }

    private static void generateUniquePermutations(double[] a, int start, List<double[]> result, Set<String> seen) {
        if (start == a.length) {
            StringBuilder sb = new StringBuilder();
            for (double d : a) sb.append(d).append(",");
            if (seen.add(sb.toString())) {
                result.add(a.clone());
            }
            return;
        }
        for (int i = start; i < a.length; i++) {
            swap(a, start, i);
            generateUniquePermutations(a, start + 1, result, seen);
            swap(a, start, i);
        }
    }

    private static void swap(double[] a, int i, int j) {
        double temp = a[i]; a[i] = a[j]; a[j] = temp;
    }
}

