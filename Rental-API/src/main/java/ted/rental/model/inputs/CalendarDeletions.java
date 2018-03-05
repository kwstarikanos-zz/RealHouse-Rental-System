package ted.rental.model.inputs;


import java.util.Date;

public class CalendarDeletions {

    private Date begin;

    private Date end;

    public CalendarDeletions() {
    }

    public CalendarDeletions(Date begin, Date end) {
        this.begin = begin;
        this.end = end;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
