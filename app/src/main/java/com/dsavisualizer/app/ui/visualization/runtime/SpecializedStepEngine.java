package com.dsavisualizer.app.ui.visualization.runtime;

/**
 * Dedicated visual step engine for Specialized & Mathematical Algorithms:
 * - Convex Hull (Graham Scan cross-product orientation & convex hull stack)
 * - Closest Pair of Points (Divide & Conquer strip scan)
 * - Line Segment Intersection (Orientation predicates & bounding box)
 * - Fast Fourier Transform (Cooley-Tukey butterfly split & roots of unity)
 * - Karatsuba Multiplication (Sub-quadratic divide & conquer: 3 multiplications)
 * - Sieve of Eratosthenes (Prime boolean array cross-off starting at p^2)
 * - Miller-Rabin (Probabilistic primality witness checking)
 * - Extended Euclidean (Bézout identity coefficients ax + by = gcd(a,b))
 * - Chinese Remainder Theorem (Coprime system simultaneous congruence)
 * - Activity Selection (Greedy finish-time interval scheduling)
 */
public final class SpecializedStepEngine extends BaseStepEngine {

    public SpecializedStepEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        advance(host);
        host.captureCurrentSnapshot();
        return Step.moved(false, host.isPlaybackComplete());
    }

    public static void advance(VisualizationRuntimeHost host) {
        String id = host.getTechniqueId();
        if ("spec_convex_hull".equals(id)) {
            advanceConvexHull(host);
        } else if ("spec_closest_pair".equals(id)) {
            advanceClosestPair(host);
        } else if ("spec_line_intersection".equals(id)) {
            advanceLineIntersection(host);
        } else if ("spec_fft".equals(id) || "spec_fast_fourier_transform".equals(id)) {
            advanceFft(host);
        } else if ("spec_karatsuba".equals(id)) {
            advanceKaratsuba(host);
        } else if ("spec_sieve".equals(id) || "spec_sieve_eratosthenes".equals(id)) {
            advanceSieve(host);
        } else if ("spec_miller_rabin".equals(id)) {
            advanceMillerRabin(host);
        } else if ("spec_extended_euclidean".equals(id) || "spec_euclidean_gcd".equals(id)) {
            advanceExtendedEuclidean(host);
        } else if ("spec_chinese_remainder".equals(id) || "spec_crt".equals(id)) {
            advanceCrt(host);
        } else if ("spec_activity_selection".equals(id)) {
            advanceActivitySelection(host);
        } else {
            advanceGenericSpecialized(host);
        }
    }

    // =========================================================================
    // 1. Convex Hull (Graham Scan)
    // Points: P0(0,0), P1(1,2), P2(3,1), P3(2,4), P4(4,4), P5(5,2)
    // =========================================================================
    private static void advanceConvexHull(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.updatePointerState("Pivot P0", 0, "Sorted", 5);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Graham Scan: Found bottom-most pivot P0(0,0). Sorted remaining points by polar angle with P0.");
                break;
            case 2:
                host.setActiveNodes(0, 1);
                host.updatePointerState("Stack", 2, "Test", 2);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Pushed P0, P1. Testing P2: Cross product (P1-P0) x (P2-P1) > 0 (Left Turn) -> P2 accepted to hull stack.");
                break;
            case 3:
                host.setActiveNodes(1, 3);
                host.updatePointerState("LeftTurn", 3, "StackSize", 3);
                host.setHighlightedLines(18, 6);
                host.bindCurrentState("Testing P3(2,4): CCW Left turn confirmed. P3 added to hull boundary.");
                break;
            case 4:
                host.finish("Graham Scan Complete! Convex Hull vertices found in O(N log N) time.");
                break;
            default:
                host.finish("Convex Hull complete.");
                break;
        }
    }

    // =========================================================================
    // 2. Closest Pair of Points
    // =========================================================================
    private static void advanceClosestPair(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.updatePointerState("Midline", 3, "Points", 6);
                host.setHighlightedLines(11, 4);
                host.bindCurrentState("Divide and Conquer: Points sorted by X-coord. Split at mid vertical line x = 4.0.");
                break;
            case 2:
                host.updatePointerState("d_Left", 2, "d_Right", 3);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("Recursive calls: Left half min dist delta_L = 2.2, Right half delta_R = 3.0. Current delta = 2.2.");
                break;
            case 3:
                host.updatePointerState("Strip", 2, "delta", 2);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("Strip Check: Inspect points within delta of midline. Comparing y-coordinates (at most 7 points checked per point).");
                break;
            case 4:
                host.finish("Closest Pair Complete! Minimum distance = 1.414 between (2,3) and (3,4) found in O(N log N).");
                break;
            default:
                host.finish("Closest Pair complete.");
                break;
        }
    }

    // =========================================================================
    // 3. Line Segment Intersection
    // =========================================================================
    private static void advanceLineIntersection(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.updatePointerState("Seg 1", 1, "Seg 2", 2);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Testing segments AB and CD. Computing 4 orientation cross products.");
                break;
            case 2:
                host.updatePointerState("o1 (ABC)", 1, "o2 (ABD)", -1);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("Orientation(A, B, C) = Clockwise (+1), Orientation(A, B, D) = Counter-Clockwise (-1) -> Opposite sides!");
                break;
            case 3:
                host.updatePointerState("o3 (CDA)", -1, "o4 (CDB)", 1);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Orientation(C, D, A) != Orientation(C, D, B) -> Both segments straddle each other!");
                break;
            case 4:
                host.finish("Segments AB and CD strictly INTERSECT! Verified in O(1) time without floating-point division.");
                break;
            default:
                host.finish("Line intersection complete.");
                break;
        }
    }

    // =========================================================================
    // 4. Fast Fourier Transform (FFT)
    // =========================================================================
    private static void advanceFft(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{1, 2, 3, 4, 0, 0, 0, 0});
                host.updatePointerState("N", 8, "Stage", 0);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("FFT: Polynomial A(x) = 1 + 2x + 3x^2 + 4x^3 padded to power of two N=8.");
                break;
            case 2:
                host.updatePointerState("Even", 4, "Odd", 4);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Divide into Even coefficients [1, 3, 0, 0] and Odd coefficients [2, 4, 0, 0].");
                break;
            case 3:
                host.updatePointerState("Butterfly", 1, "omega", 8);
                host.setHighlightedLines(22, 8);
                host.bindCurrentState("Butterfly operation: Combine sub-results using principal root of unity omega_8 = e^(2*pi*i/8).");
                break;
            case 4:
                host.finish("FFT Evaluation complete! Evaluated polynomial at 8 complex roots in O(N log N) vs naive O(N^2).");
                break;
            default:
                host.finish("FFT complete.");
                break;
        }
    }

    // =========================================================================
    // 5. Karatsuba Multiplication
    // =========================================================================
    private static void advanceKaratsuba(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.updatePointerState("X=1234", 12, "Y=5678", 56);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Karatsuba: Multiplying 1234 x 5678. Split into halves: a=12, b=34, c=56, d=78.");
                break;
            case 2:
                host.updatePointerState("z0=ac", 672, "z2=bd", 2652);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Compute z0 = 12*56 = 672, z2 = 34*78 = 2652 (2 multiplications).");
                break;
            case 3:
                host.updatePointerState("z1", 3682, "Cross", 1);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Single trick: z1 = (12+34)*(56+78) - z0 - z2 = 46*134 - 672 - 2652 = 2840 (Only 1 extra multiplication!).");
                break;
            case 4:
                host.finish("Result = z0*10^4 + z1*10^2 + z2 = 7006652. Time complexity reduced to O(N^1.585)!");
                break;
            default:
                host.finish("Karatsuba complete.");
                break;
        }
    }

    // =========================================================================
    // 6. Sieve of Eratosthenes
    // Numbers: 0..19. Primes: 2, 3, 5, 7, 11, 13, 17, 19
    // =========================================================================
    private static void advanceSieve(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int[] isPrime = host.getWorkingValues();
        if (isPrime == null || isPrime.length != 20) {
            isPrime = new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        }

        switch (step) {
            case 1:
                // Cross off multiples of 2 starting at 4
                for (int i = 4; i < 20; i += 2) isPrime[i] = 0;
                host.setWorkingValues(isPrime);
                host.updateArrayVisualization(2, 4, -1);
                host.updatePointerState("Prime", 2, "CrossFrom", 4);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Sieve: Prime p = 2. Marking composite multiples [4, 6, 8, 10, 12, 14, 16, 18] as 0.");
                break;
            case 2:
                // Cross off multiples of 3 starting at 9
                for (int i = 9; i < 20; i += 6) isPrime[i] = 0;
                host.setWorkingValues(isPrime);
                host.updateArrayVisualization(3, 9, -1);
                host.updatePointerState("Prime", 3, "CrossFrom", 9);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("Prime p = 3. Marking composite multiples starting at 3^2 = 9 [9, 15] as 0.");
                break;
            case 3:
                host.updateArrayVisualization(5, -1, -1);
                host.updatePointerState("Prime", 5, "p^2 > N", 25);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("p = 5: Since p^2 = 25 > 20, loop terminates! All unmarked numbers are GUARANTEED PRIMES.");
                break;
            case 4:
                host.finish("Sieve complete! Primes up to 20: [2, 3, 5, 7, 11, 13, 17, 19] computed in O(N log log N).");
                break;
            default:
                host.finish("Sieve complete.");
                break;
        }
    }

    // =========================================================================
    // 7. Miller-Rabin Primality Test
    // =========================================================================
    private static void advanceMillerRabin(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.updatePointerState("n=61", 61, "d=15", 15);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Testing primality of n = 61. Write n - 1 = 60 = 2^s * d -> s = 2, d = 15.");
                break;
            case 2:
                host.updatePointerState("Base a=2", 2, "a^d%n", 11);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Pick random base a = 2. Compute x = 2^15 mod 61 = 11.");
                break;
            case 3:
                host.updatePointerState("Square", 1, "x^2%61", 60);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Square x: 11^2 mod 61 = 121 mod 61 = 60 == -1 mod 61! Satisfies non-trivial square root condition.");
                break;
            case 4:
                host.finish("Witness test passed! Tested with k independent bases: 61 is PROBABLY PRIME with error < 4^(-k).");
                break;
            default:
                host.finish("Miller-Rabin complete.");
                break;
        }
    }

    // =========================================================================
    // 8. Extended Euclidean Algorithm
    // =========================================================================
    private static void advanceExtendedEuclidean(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.updatePointerState("a=35", 35, "b=15", 15);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Extended GCD(35, 15): 35 = 2 * 15 + 5. Quotient q=2, Remainder r=5.");
                break;
            case 2:
                host.updatePointerState("a=15", 15, "b=5", 5);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("Next: GCD(15, 5) -> 15 = 3 * 5 + 0. Remainder is 0, so gcd(35, 15) = 5.");
                break;
            case 3:
                host.updatePointerState("x", 1, "y", -2);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("Back-substitution: 5 = 35 * (1) + 15 * (-2). Bézout coefficients: x = 1, y = -2!");
                break;
            case 4:
                host.finish("Extended Euclidean Complete: 35(1) + 15(-2) = 5. Enables modular inverse computation in O(log(min(a,b))).");
                break;
            default:
                host.finish("Extended Euclidean complete.");
                break;
        }
    }

    // =========================================================================
    // 9. Chinese Remainder Theorem (CRT)
    // =========================================================================
    private static void advanceCrt(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.updatePointerState("x=2%3", 3, "x=3%5", 5);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("System: x = 2 mod 3, x = 3 mod 5, x = 2 mod 7. Moduli [3, 5, 7] are pairwise coprime.");
                break;
            case 2:
                host.updatePointerState("M=105", 105, "M1=35", 35);
                host.setHighlightedLines(16, 5);
                host.bindCurrentState("Total modulus M = 3*5*7 = 105. Partial products: M1=35, M2=21, M3=15.");
                break;
            case 3:
                host.updatePointerState("Inv y1", 2, "Inv y2", 1);
                host.setHighlightedLines(22, 8);
                host.bindCurrentState("Modular inverses: 35*y1 = 1 mod 3 -> y1=2; 21*y2 = 1 mod 5 -> y2=1; 15*y3 = 1 mod 7 -> y3=1.");
                break;
            case 4:
                host.finish("CRT Solution: x = (2*35*2 + 3*21*1 + 2*15*1) mod 105 = 233 mod 105 = 23 (Unique in Z_105)!");
                break;
            default:
                host.finish("CRT complete.");
                break;
        }
    }

    // =========================================================================
    // 10. Activity Selection
    // Intervals [start, end]: [1,4], [3,5], [0,6], [5,7], [3,9], [5,9], [6,10], [8,11], [8,12], [2,14], [12,16]
    // =========================================================================
    private static void advanceActivitySelection(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16});
                host.updateArrayVisualization(0, -1, 0);
                host.updatePointerState("Pick [1,4]", 0, "Finish", 4);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Greedy choice 1: Select first activity [1, 4] with earliest finish time 4.");
                break;
            case 2:
                host.updateArrayVisualization(1, -1, -1);
                host.updatePointerState("Reject [3,5]", 1, "Conflict", 4);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("Activity [3, 5]: Start time 3 < Finish time 4 -> OVERLAP! Rejected.");
                break;
            case 3:
                host.updateArrayVisualization(3, -1, 3);
                host.updatePointerState("Pick [5,7]", 3, "Finish", 7);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Activity [5, 7]: Start time 5 >= Finish time 4 -> COMPATIBLE! Accepted into schedule.");
                break;
            case 4:
                host.finish("Activity Selection Complete! Max 4 non-overlapping activities selected in O(N log N).");
                break;
            default:
                host.finish("Activity selection complete.");
                break;
        }
    }

    private static void advanceGenericSpecialized(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);
        if (step >= 5) {
            host.finish("Specialized algorithm execution concluded.");
        } else {
            host.bindCurrentState("Specialized algorithm stage " + step + ".");
        }
    }

    public static String getPresetDescription(String techniqueId, int preset) {
        String id = techniqueId != null ? techniqueId : "";
        if ("spec_convex_hull".equals(id)) {
            switch (preset) {
                case 2: return "Collinear & Co-circular Stress: Points along a perimeter line or circle testing strict left turn cross-product filtering.";
                case 3: return "Dense Cluster: Multiple internal points surrounded by an outer convex hull perimeter polygon in O(N log N).";
                case 1:
                default: return "Graham Scan standard 6 points: P0(0,0), P1(1,2), P2(3,1), P3(2,4), P4(4,4), P5(5,2). Sorts by polar angle and uses cross-product stack in O(N log N).";
            }
        }
        if ("spec_closest_pair".equals(id)) {
            switch (preset) {
                case 2: return "Vertical Strip Stress: Points tightly clustered near the midline boundary to test the efficiency of the strip scan.";
                case 3: return "Dense Point Grid: 10 points scattered uniformly across 2D plane finding global Euclidean distance minimum in O(N log N).";
                case 1:
                default: return "Divide and Conquer 6 points. Recursively halves points by X-coordinate, checks delta-strip around midline with at most 7 comparisons per point.";
            }
        }
        if ("spec_line_intersection".equals(id)) {
            switch (preset) {
                case 2: return "Collinear Overlap: Two segments lying on the same line; checks 1D bounding box coordinate overlap.";
                case 3: return "Disjoint & Parallel Segments: Segments that do not cross; detects identical orientations without floating-point division.";
                case 1:
                default: return "Standard Crossing Segments: AB crossing CD. Four 2D orientation cross-products confirm mutually straddling segments in O(1).";
            }
        }
        if ("spec_fft".equals(id) || "spec_fast_fourier_transform".equals(id)) {
            switch (preset) {
                case 2: return "Delta Spike: Single nonzero impulse coefficient producing uniform frequency response across all roots of unity.";
                case 3: return "Polynomial Convolution: Multiplies two degree-4 polynomials in O(N log N) via forward FFT, pointwise multiply, and inverse FFT.";
                case 1:
                default: return "Cooley-Tukey Radix-2 FFT: Polynomial of degree 3 padded to N=8. Splits into even and odd terms using butterfly twiddle factors.";
            }
        }
        if ("spec_karatsuba".equals(id)) {
            switch (preset) {
                case 2: return "Asymmetric Digits: Multiplying numbers with differing magnitude, zero-padding to equal power-of-two lengths.";
                case 3: return "Repeated Digits: Stress case 9999 x 9999 testing carry propagation and sub-product subtraction bounds.";
                case 1:
                default: return "Standard 4-digit multiplication: 1234 x 5678. Uses 3 sub-products (ac, bd, (a+b)(c+d)) in O(N^1.585) vs naive O(N^2).";
            }
        }
        if ("spec_sieve".equals(id) || "spec_sieve_eratosthenes".equals(id)) {
            switch (preset) {
                case 2: return "Extended Range N=50: Generates all 15 primes under 50, verifying efficient jump increments in O(N log log N).";
                case 3: return "Twin Primes & Prime Gaps: Highlights dense prime distributions and gap intervals among early primes.";
                case 1:
                default: return "Sieve up to N=20: Iteratively crosses off composite multiples starting at p^2 (2->4, 3->9). Stops when p^2 > N in O(N log log N).";
            }
        }
        if ("spec_miller_rabin".equals(id)) {
            switch (preset) {
                case 2: return "Carmichael Number Stress: Tests pseudoprime n=561 (composite that passes Fermat test); Miller-Rabin unmasks it as composite.";
                case 3: return "Large Prime Proof: Tests candidate n=97 with bases a in {2, 3, 5, 7} proving probable primality with error < 4^(-k).";
                case 1:
                default: return "Candidate n=61: Decomposes n-1 = 60 = 2^2 * 15. Tests random witness base a=2; verifies non-trivial square roots mod 61.";
            }
        }
        if ("spec_extended_euclidean".equals(id) || "spec_euclidean_gcd".equals(id)) {
            switch (preset) {
                case 2: return "Coprime Pair GCD(47, 18): gcd=1, yielding modular inverse of 18 mod 47: 18^(-1) = 34.";
                case 3: return "Large Fibonacci Pair (89, 55): Worst-case Euclidean sequence with alternating quotients q=1.";
                case 1:
                default: return "Extended GCD(35, 15): Finds gcd=5 and Bézout coefficients: 35(1) + 15(-2) = 5 via back-substitution in O(log(min(a,b))).";
            }
        }
        if ("spec_crt".equals(id) || "spec_chinese_remainder".equals(id)) {
            switch (preset) {
                case 2: return "Power-of-Ten Moduli: System with pairwise coprime moduli [4, 9, 25] solving larger range congruences.";
                case 3: return "Calendar & Cycle Sync: Simulates periodic recurrence alignment with moduli [5, 7, 11].";
                case 1:
                default: return "Standard 3-Moduli System: x = 2 mod 3, x = 3 mod 5, x = 2 mod 7. Total M=105, yields unique solution x = 23 mod 105.";
            }
        }
        if ("spec_activity_selection".equals(id)) {
            switch (preset) {
                case 2: return "All Overlapping Worst-Case: All activities intersect at a single bottleneck point; greedy choice picks exactly 1.";
                case 3: return "Chained & Alternating: Activities with alternating long and short durations demonstrating greedy choice optimality.";
                case 1:
                default: return "Greedy Interval Scheduling: 11 activities sorted by finish time. Greedily selects earliest non-overlapping intervals in O(N log N).";
            }
        }
        return "Specialized algorithm scenario: Preset " + preset + ".";
    }

    public static String getDefaultInput(String techniqueId, int preset) {
        String id = techniqueId != null ? techniqueId : "";
        if ("spec_convex_hull".equals(id)) {
            switch (preset) {
                case 2: return "1,1; 2,2; 3,3; 1,3; 3,1";
                case 3: return "0,2; 2,5; 5,3; 4,0; 1,0; 2,2; 3,2";
                case 1:
                default: return "0,0; 1,2; 3,1; 2,4; 4,4; 5,2";
            }
        }
        if ("spec_closest_pair".equals(id)) {
            switch (preset) {
                case 2: return "1,1; 1,2; 1,4; 1,7; 1,11";
                case 3: return "0,0; 3,4; 6,8; 1,1; 2,2; 5,5";
                case 1:
                default: return "2,3; 12,30; 40,50; 5,1; 12,10; 3,4";
            }
        }
        if ("spec_line_intersection".equals(id)) {
            switch (preset) {
                case 2: return "0,0; 5,5; 2,2; 7,7";
                case 3: return "1,1; 4,4; 1,4; 4,1";
                case 1:
                default: return "1,1; 10,1; 1,2; 10,2";
            }
        }
        if ("spec_fft".equals(id) || "spec_fast_fourier_transform".equals(id)) {
            switch (preset) {
                case 2: return "1, 0, 0, 0";
                case 3: return "1, -1, 1, -1, 2, 0, -2, 0";
                case 1:
                default: return "1, 2, 3, 4";
            }
        }
        if ("spec_karatsuba".equals(id)) {
            switch (preset) {
                case 2: return "12345, 6789";
                case 3: return "9999, 9999";
                case 1:
                default: return "1234, 5678";
            }
        }
        if ("spec_sieve".equals(id) || "spec_sieve_eratosthenes".equals(id)) {
            switch (preset) {
                case 2: return "50";
                case 3: return "100";
                case 1:
                default: return "20";
            }
        }
        if ("spec_miller_rabin".equals(id)) {
            switch (preset) {
                case 2: return "561";
                case 3: return "97";
                case 1:
                default: return "61";
            }
        }
        if ("spec_extended_euclidean".equals(id) || "spec_euclidean_gcd".equals(id)) {
            switch (preset) {
                case 2: return "47, 18";
                case 3: return "89, 55";
                case 1:
                default: return "35, 15";
            }
        }
        if ("spec_crt".equals(id) || "spec_chinese_remainder".equals(id)) {
            switch (preset) {
                case 2: return "1%4, 2%9, 3%25";
                case 3: return "3%5, 4%7, 5%11";
                case 1:
                default: return "2%3, 3%5, 2%7";
            }
        }
        if ("spec_activity_selection".equals(id)) {
            switch (preset) {
                case 2: return "1-10, 2-10, 3-10, 4-10";
                case 3: return "1-2, 2-3, 3-4, 4-5, 5-6";
                case 1:
                default: return "1-4, 3-5, 0-6, 5-7, 3-9, 5-9, 6-10, 8-11";
            }
        }
        return "1, 2, 3, 4";
    }

    public static int getTotalSteps(String techniqueId, int preset) {
        return 4;
    }
}
