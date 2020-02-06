package com.revature.eval.java.core;

import java.util.Arrays;

public class CreateBlendArray {
	public static final String BLENDS = "bl	br	ch	cl	cr	dr	fl	fr	gl	gr	pl	pr	sc sh	sk	sl	sm	sn	sp	st	sw	th	tr	tw	wh	wr sch	scr	shr	sph	spl	spr	squ	str	thr";

	public static void main(String[] args) {
		System.out.print("public static final String[] BLENDS_DIGRAPHS_TRIGRAPHS_OH_MY = {");

		final String[] blends = BLENDS.split("\\s");

		// use lambda expression to define a Comparator<String, String> based on the
		// length of the Strings. This effectively sorts the blends from longest to
		// shortest.
		Arrays.sort(blends, (String s1, String s2) -> s2.length() - s1.length());

		// this is going to give an extra comma, I'll just manually delete it since I'm
		// copying and pasting anyway.
		for (final String blend : blends) {
			System.out.print('"' + blend + "\",");
		}

		System.out.println("};");
	}
}
