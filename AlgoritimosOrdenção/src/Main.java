import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public List<Integer> mergeSort(List<Integer> arr) {
        if(arr.size() == 1){
            return arr;
        }
        int middle = arr.size() / 2;

        List<Integer> left = mergeSort(arr.subList(0, middle));
        List<Integer> right = mergeSort(arr.subList(middle, arr.size()));

        return merge(left, right);
    }

    public List<Integer> merge(List<Integer> left, List<Integer> right) {
        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        int j = 0;

        while(i < left.size() && j < right.size()){
            if(left.get(i) < right.get(j)){
                list.add(left.get(i));
                i++;
            } else{
                list.add(right.get(j));
                j++;
            }
        }

        list.addAll(left.subList(i, left.size()));
        list.addAll(right.subList(j, right.size()));

        return list;
    }

    public boolean binarySearch(ArrayList<Integer> arr, int search){
        quickSort(arr);
        int high = arr.size() - 1;
        int low = 0;

        while(high >= low){
            int mid = (high + low) / 2;

            if(arr.get(mid) == search){
                return true;
            } else if(arr.get(mid) < search){
                low = mid + 1;
            } else{
                high = mid - 1;
            }
        }

        return false;
    }

    public void quickSort(ArrayList<Integer> arr) {
        quick(arr, 0, arr.size() - 1);
    }

    private void quick(ArrayList<Integer> arr, int left, int right) {
        if(left >= right){
            return;
        }
        int index = partition(arr, left, right);

        quick(arr, left, index - 1);
        quick(arr, index, right);
    }

    private int partition(ArrayList<Integer> arr, int left, int right) {
        int pivot = arr.get((left + right) / 2);

        while(left <= right){
            while(arr.get(left) < pivot){
                left++;
            }
            while(arr.get(right) > pivot){
                right--;
            }
            if(left <= right){
                swap(arr, left, right);
                left++;
                right--;
            }
        }

        return left;
    }

    private void swap(ArrayList<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));

        Main main = new Main();

        System.out.println(main.binarySearch(arr, 11));
    }
}
