package phonebook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        var directory = Files.readAllLines(new File("directory.txt").toPath());
        var findNames = Files.readAllLines(new File("find.txt").toPath());

        linearSearch(findNames, directory);
        System.out.println();
        sortAndJumpSearch(findNames, directory);
        System.out.println();
        quickSortAndBinarySearch(findNames, directory);
        System.out.println();
        hashTableSearch(findNames, directory);

    }

    public static void linearSearch(List<String> names, List<String> data) {
        System.out.println("Start searching (linear search)...");

        var start = System.currentTimeMillis();
        var found = 0;

        for (String name: names) {
            if (linearSearch(name, data)){
                found += 1;
            }
        }
        var end = System.currentTimeMillis();

        long millis = end - start;
        long minutes = (millis / 1000)  / 60;
        int seconds = (int)((millis / 1000) % 60);

        var output = String.format("Found %d / 500 entries. Time taken: %d min. %d sec. %d ms.", found, minutes, seconds, millis);

        System.out.println(output);
    }

    public static boolean linearSearch(String name, List<String> data) {
        for (String entry : data) {
            if (entry.contains(name)) {
                return true;
            }
        }
        return false;
    }

    public static void sortAndJumpSearch(List<String> names, List<String> data) {
        System.out.println("Start searching (bubble sort + jump search)...");
        var startTotal = System.currentTimeMillis();
        List<String> sortedData = javaSort(data);
        var endSort = System.currentTimeMillis();

        var startSearch = System.currentTimeMillis();
        var found = 0;
        for (String name: names) {
            if (jumpSearch(name, sortedData)){
                found += 1;
            }
        }
        var end = System.currentTimeMillis();

        long millis = end - startTotal;
        long minutes = (millis / 1000)  / 60;
        int seconds = (int)((millis / 1000) % 60);

        var totalOutput = String.format("Found %d / 500 entries. Time taken: %d min. %d sec. %d ms.", found, minutes, seconds, millis);
        System.out.println(totalOutput);


        long millisSort = endSort - startTotal;
        long minutesSort = (millisSort / 1000)  / 60;
        int secondsSort = (int)((millisSort / 1000) % 60);
        var sortOutput = String.format("Sorting time: %d min. %d sec. %d ms.", minutesSort, secondsSort, millisSort);
        System.out.println(sortOutput);

        long millisSearch = end - startSearch;
        long minutesSearch = (millisSearch / 1000)  / 60;
        int secondsSearch = (int)((millisSearch / 1000) % 60);
        var searchOutput = String.format("Searching time: %d min. %d sec. %d ms.", minutesSearch, secondsSearch, millisSearch);
        System.out.println(searchOutput);
    }

    public static void quickSortAndBinarySearch(List<String> names, List<String> data) {
        System.out.println("Start searching (quick sort + binary search)...");
        var startTotal = System.currentTimeMillis();
        List<String> sortedData = javaParSort(data);
        var endSort = System.currentTimeMillis();
        var startSearch = System.currentTimeMillis();
        var found = 0;
        for (String name: names) {
            if (binarySearch(name, sortedData)){
                found += 1;
            }
        }
        var end = System.currentTimeMillis();

        long millis = end - startTotal;
        long minutes = (millis / 1000)  / 60;
        int seconds = (int)((millis / 1000) % 60);

        var totalOutput = String.format("Found %d / 500 entries. Time taken: %d min. %d sec. %d ms.", found, minutes, seconds, millis);
        System.out.println(totalOutput);


        long millisSort = endSort - startTotal;
        long minutesSort = (millisSort / 1000)  / 60;
        int secondsSort = (int)((millisSort / 1000) % 60);
        var sortOutput = String.format("Sorting time: %d min. %d sec. %d ms.", minutesSort, secondsSort, millisSort);
        System.out.println(sortOutput);

        long millisSearch = end - startSearch;
        long minutesSearch = (millisSearch / 1000)  / 60;
        int secondsSearch = (int)((millisSearch / 1000) % 60);
        var searchOutput = String.format("Searching time: %d min. %d sec. %d ms.", minutesSearch, secondsSearch, millisSearch);
        System.out.println(searchOutput);

    }

    public static void hashTableSearch(List<String> names, List<String> data) {
        System.out.println("Start searching (hash table)...");
        var startTotal = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        for (String entry: data) {
            map.put(getNameFromData(entry), getPhoneNumFromData(entry));
        }
        var endCreateHashMap = System.currentTimeMillis();
        var found = 0;
        for (String name: names) {
            if (map.containsKey(name)){
                found += 1;
            }
        }
        var end = System.currentTimeMillis();

        long millis = end - startTotal;
        long minutes = (millis / 1000)  / 60;
        int seconds = (int)((millis / 1000) % 60);

        var totalOutput = String.format("Found %d / 500 entries. Time taken: %d min. %d sec. %d ms.", found, minutes, seconds, millis);
        System.out.println(totalOutput);


        long millisCreate = endCreateHashMap - startTotal;
        long minutesCreate = (millisCreate / 1000)  / 60;
        int secondsCreate = (int)((millisCreate / 1000) % 60);
        var createOutput = String.format("Creating time %d min. %d sec. %d ms.", minutesCreate, secondsCreate, millisCreate);
        System.out.println(createOutput);

        long millisSearch = end - endCreateHashMap;
        long minutesSearch = (millisSearch / 1000)  / 60;
        int secondsSearch = (int)((millisSearch / 1000) % 60);
        var searchOutput = String.format("Searching time: %d min. %d sec. %d ms.", minutesSearch, secondsSearch, millisSearch);
        System.out.println(searchOutput);

    }


    public static boolean jumpSearch(String name, List<String> data) {
        int intervalLength = (int) Math.sqrt(data.size());
        for (int i = 0; i <= data.size(); i += intervalLength)  {
            var entry = data.get(i);
            if (getNameFromData(entry).equals(name)) {
                return true;
            }
            else if (getNameFromData(entry).compareTo(name) < 0) {
                continue;
            }
            else {
                var j = i;
                while (j >= 0 && getNameFromData(data.get(j)).compareTo(name) >= 0){
                    j--;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean binarySearch(String name, List<String> data) {
        if (data.isEmpty()) return false;
        else if (data.size() == 1) {
            return getNameFromData(data.get(0)).equals(name);
        }
        int mid = data.size() / 2;
        if (getNameFromData(data.get(mid)).equals(name)) {
            return true;
        } else if (getNameFromData(data.get(mid)).compareTo(name) < 0) {
            return binarySearch(name, data.subList(mid + 1, data.size()));
        }
        return binarySearch(name, data.subList(0, mid));
    }

    public static String getNameFromData(String line) {
        return line.substring(line.indexOf(" ") + 1);
    }

    public static String getPhoneNumFromData(String line) {
        return line.substring(0, line.indexOf(" "));
    }

    public static List<String> bubbleSort(List<String> data, int n)
    {
        int i, j;
        String temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++)
        {
            swapped = false;
            for (j = 0; j < n - i - 1; j++)
            {
                if (getNameFromData(data.get(j)).compareTo(getNameFromData(data.get(j + 1))) > 0)
                {
                    // swap arr[j] and arr[j+1]
                    temp = data.get(j);
                    data.set(j, data.get(j + 1));
                    data.set(j + 1, temp);
                    swapped = true;
                }
            }

            if (!swapped)
                break;
        }
        return data;
    }
    public static List<String> javaSort(List<String> data) {
        return data.stream().sorted(Comparator.comparing(Main::getNameFromData)).collect(Collectors.toList());
    }
    public static List<String> javaParSort(List<String> data) {
        return data.parallelStream().sorted(Comparator.comparing(Main::getNameFromData)).collect(Collectors.toList());
    }
}
