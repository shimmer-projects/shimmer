package io.github.shimmer.utils;

import lombok.val;

import java.time.LocalDate;

public final class Utils {

    public static void main(String[] args) {
        val localDate = LocalDate.of(2023, 12, 24);
        val localDate1 = localDate.plusDays(158);
        System.out.println(localDate1);
    }
}
