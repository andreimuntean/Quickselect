import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Finds the index of the kth greatest integer in a list.
 *
 * @author Andrei Muntean
 */
public class Quickselect
{
    private static void swap(List<Integer> list, int firstIndex, int secondIndex)
    {
        int value = list.get(firstIndex);

        list.set(firstIndex, list.get(secondIndex));
        list.set(secondIndex, value);
    }

    private static void shuffle(List<Integer> list)
    {
        Random random = new Random();

        for (int i = 1; i < list.size(); ++i)
        {
            // Swaps values with a smaller index.
            int j = random.nextInt(i);

            swap(list, i, j);
        }
    }

    private static int partition(List<Integer> list, int startIndex, int endIndex)
    {
        int median = list.get(startIndex);
        int leftIndex = startIndex + 1;
        int rightIndex = endIndex;

        while (true)
        {
            while (leftIndex < endIndex && list.get(leftIndex) < median)
            {
                ++leftIndex;
            }

            while (rightIndex > startIndex && list.get(rightIndex) >= median)
            {
                --rightIndex;
            }

            if (leftIndex >= rightIndex)
            {
                break;
            }

            swap(list, leftIndex++, rightIndex--);
        }

        // Puts the median value (from startIndex) in the correct position.
        swap(list, startIndex, rightIndex);

        // Returns the median index.
        return rightIndex;
    }

    // Sorts the kth value into its correct position. All indexes are inclusive.
    private static void sortKthValue(List<Integer> list, int startIndex, int endIndex, int k)
    {
        int medianIndex = partition(list, startIndex, endIndex);

        if (k < medianIndex)
        {
            sortKthValue(list, startIndex, medianIndex - 1, k);
        }
        else if (k > medianIndex)
        {
            sortKthValue(list, medianIndex + 1, endIndex, k);
        }
    }

    /**
     * Finds the index of the kth greatest integer in a list.
     *
     * @param list A list of integers.
     * @param k A value between 1 and the size of the list.
     *
     * @return The index of the kth greatest integer in the list.
     */
    public static int select(List<Integer> list, int k)
    {
        // Eliminates duplicates and uses an auxiliary list during processing.
        List<Integer> temp = new ArrayList<>(new HashSet<>(list));

        if (temp.size() < k)
        {
            return -1;
        }

        shuffle(temp);
        sortKthValue(temp, 0, temp.size() - 1, temp.size() - k);
        
        int value = temp.get(temp.size() - k);

        // Returns the index of the kth greatest integer from the original list.
        return list.indexOf(value);
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length == 1)
        {
            List<Integer> list = new ArrayList<>();
            Scanner scanner = new Scanner(new File(args[0]));

            while (scanner.hasNextInt())
            {
                list.add(scanner.nextInt());
            }

            System.out.printf("k = ?\n> ");

            int k = (new Scanner(System.in)).nextInt();
            int index = select(list, k);

            if (index < 0)
            {
                System.out.println("Does not exist.");
            }
            else
            {
                System.out.printf("%d (index %d)%n", list.get(index), index);
            }
        }
        else
        {
            throw new Exception("No file path was specified.");
        }
    }
}
