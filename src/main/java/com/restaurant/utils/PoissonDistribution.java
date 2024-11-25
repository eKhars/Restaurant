package com.restaurant.utils;

import java.util.Random;

public class PoissonDistribution {
    private final double lambda;
    private final Random random;

    public PoissonDistribution(double lambda) {
        this.lambda = lambda;
        this.random = new Random();
    }

    public int nextInt() {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= random.nextDouble();
        } while (p > L);

        return k - 1;
    }
}