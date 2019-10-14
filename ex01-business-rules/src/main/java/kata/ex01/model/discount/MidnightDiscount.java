package kata.ex01.model.discount;

import kata.ex01.model.HighwayDrive;

import java.time.LocalDate;
import java.time.LocalTime;

public class MidnightDiscount extends Discount {
    public MidnightDiscount(HighwayDrive drive) {
        super(drive);
    }

    @Override
    protected int getRate() {
        return isDiscount() ? 30 : 0;
    }

    private boolean isDiscount() {
        for (LocalDate date : drive.getDriveDates()) {
            var from = date.atTime(0, 0);
            var to = date.atTime(4, 0);

            if (drive.isDriving(from, to)) {
                return true;
            }
        }

        return false;
    }
}
