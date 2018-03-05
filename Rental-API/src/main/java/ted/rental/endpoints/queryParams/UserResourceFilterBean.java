package ted.rental.endpoints.queryParams;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public class UserResourceFilterBean {

    private @QueryParam("start")
    int start;

    private @QueryParam("size")
    int size;

    private @QueryParam("depth")
    int depth;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

}
