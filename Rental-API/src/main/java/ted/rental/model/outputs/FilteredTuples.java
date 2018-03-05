package ted.rental.model.outputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteredTuples {

    private List<Object> typles = new ArrayList<>();

    private long count;

    private Object[] prices;

    public FilteredTuples() {

    }

    public FilteredTuples(List<Object> typles, long count, Object[] prices) {
        this.typles = typles;
        this.count = count;
        this.prices = prices;
    }

    public List<Object> getTyples() {
        return typles;
    }

    public long getCount() {
        return count;
    }

    public Double getMinPrice() {
        return (Double) prices[0];
    }

    public Double getMaxPrice() {
        return (Double) prices[1];
    }

    public Object[] getPrices() {
        return prices;
    }

    @Override
    public String toString() {
        return "FilteredTuples{" +
                "typles=" + typles +
                ", count=" + count +
                ", prices=" + Arrays.toString(prices) +
                '}';
    }
}
