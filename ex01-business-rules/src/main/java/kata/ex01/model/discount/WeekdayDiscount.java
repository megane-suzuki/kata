package kata.ex01.model.discount;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.util.HolidayUtils;

import java.time.LocalDate;

public class WeekdayDiscount extends Discount {
    public WeekdayDiscount(HighwayDrive drive) {
        super(drive);
    }

    @Override
    protected int getRate() {
        if (!isDiscount()) {
            return 0;
        }

        return isMiddleUsed() ? 30 : isHeavyUsed() ? 50 : 0;
    }

    private boolean isDiscount() {
        var routeType = drive.getRouteType();
        if (!routeType.equals(RouteType.RURAL)) {
            return false;
        }

        for (LocalDate date : drive.getDriveDates()) {
            if (!HolidayUtils.isHoliday(date) && isDrivingInDiscountTime(date)) {
                return true;
            }
        }

        return false;
    }

    private boolean isDrivingInDiscountTime(LocalDate date) {
        var morningFrom = date.atTime(6, 0);
        var morningTo = date.atTime(9, 0);
        var eveningFrom = date.atTime(17, 0);
        var eveningTo = date.atTime(20, 0);

        return drive.isDriving(morningFrom, morningTo)
                || drive.isDriving(eveningFrom, eveningTo);
    }

    private boolean isMiddleUsed() {
        var count = drive.getDriver().getCountPerMonth();

        return 5 <= count && count <= 9;
    }

    private boolean isHeavyUsed() {
        var count = drive.getDriver().getCountPerMonth();

        return 10 <= count;
    }
}
