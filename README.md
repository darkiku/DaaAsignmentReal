# DAA Assignment 1

This project is part of my university course **Design and Analysis of Algorithms (DAA)**.  
The goal of the assignment is to implement several classical algorithms, measure their performance,  
and create a small framework for experiments, testing, and reporting.  

---

## Algorithms Implemented

### 1. MergeSortX
- Merge Sort with **reuse buffer** (scratch array) to avoid repeated allocations.  
- **Insertion Sort cutoff** for small subarrays (to make it faster in practice).  
- Recursion depth is tracked using a counter.  

### 2. QuickSortY
- Quick Sort with **randomized pivot**.  
- Always recurses first on the smaller partition (to avoid deep recursion).  
- Works well for large random arrays and also handles duplicates.  

### 3. DetSelectZ (Deterministic Select)
- **Median of Medians (MoM5)** algorithm for selecting the k-th smallest element.  
- Guarantees worst-case linear time O(n).  
- Useful for selection problems without fully sorting the array.  

### 4. ClosestPair2D
- Classical **divide and conquer** algorithm for finding the closest pair of points in the plane.  
- Works in **O(n log n)** time.  
- The algorithm uses sorting by x and y coordinates and checks only 7 neighbors in the strip.  

---

## Metrics and Utilities

For all algorithms I measured:
- **Comparisons (comp)**: number of element comparisons.  
- **Allocations (alloc)**: number of elements copied or moved (rough approximation).  
- **Recursion depth (depth)**: maximum depth of recursive calls.  

Helpers:
- `ArrUtil`: swap, shuffle, guard checks.  
- `CsvEmitter`: writes results to CSV file.  
- `DepthTracker`: tracks recursion depth with ThreadLocal.  

---

## CLI Runner

The class `MainRunner` can run experiments from command line.  
It accepts several arguments:

--algo <mergesort|quicksort|select|closest>
--n <size>
--trials <t>
--seed <s>

--out <file>

### Example
java -cp target/daa-assignment1-1.0-SNAPSHOT.jar da.cli.MainRunner --algo mergesort --n 1000 --trials 3 --out results.csv


This will run MergeSort on arrays of size 1000, repeat 3 times, and save results in `results.csv`.

---

## Benchmarks

I also implemented a small **JMH benchmark** (`SelectVsSortBench`):  
- Compare performance of **Deterministic Select** vs **full sort + median**.  
- Benchmarks are done for different input sizes.  

---
 Testing

All code is tested with **JUnit 5**.  
Tests cover:
- Sorting correctness (sorted arrays, random arrays, duplicates).  
- Selection correctness (compare with Arrays.sort).  
- Closest pair correctness (compared with brute force).  

Command to run all tests:
mvn test


All tests pass successfully .

---

## Report Summary

- The assignment helped me understand both **theory and practice** of DAA.  
- MergeSort and QuickSort are both efficient, but their recursion strategies are different.  
- Deterministic Select is slower in practice than QuickSelect, but gives strong guarantees.  
- Closest Pair algorithm was the most interesting, since it uses both divide & conquer and geometry.  
- By adding metrics and CLI, I learned how to measure algorithm complexity in a real project.  

---

## Changelog

**v1.0.0**
- Implemented MergeSortX, QuickSortY, DetSelectZ, ClosestPair2D  
- Added Metrics, Utilities, CLI, CSV writer  
- Added JUnit tests and JMH benchmark  
- Wrote report and fixed small bugs  
