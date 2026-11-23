
import java.util.ArrayList;
import java.util.List;

class ProductOfNumbers {

    private List<Integer> prefixProducts;

    public ProductOfNumbers() {
        prefixProducts = new ArrayList<>();
        prefixProducts.add(1);
    }

    public void add(int num) {

        // Reset when zero is encountered
        if (num == 0) {
            prefixProducts.clear();
            prefixProducts.add(1);
        } else {
            int lastProduct =
                prefixProducts.get(prefixProducts.size() - 1);

            prefixProducts.add(lastProduct * num);
        }
    }

    public int getProduct(int k) {

        int size = prefixProducts.size();

        // A zero exists among the last k numbers
        if (size <= k) {
            return 0;
        }

        // Calculate product using prefix products
        return prefixProducts.get(size - 1) /
               prefixProducts.get(size - 1 - k);
    }
}
