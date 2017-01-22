package com.aldaviva.easter4j;

import com.aldaviva.easter4j.computus.Computus;
import com.aldaviva.easter4j.test.TestDates;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.testng.Assert.assertEquals;

public abstract class ComputusTest {

    private static final String DATA_PROVIDER = "easterDates";

    public abstract Computus getComputus();

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void yearMustBeNonNegative(){
        getComputus().getEaster(-1);
    }

    @Test
    public void yearCanBeZero(){
        getComputus().getEaster(0);
    }

    @Test(dataProvider = DATA_PROVIDER)
    public void isCorrectDateReturned(final TestDates.TestDate expectation) {
        final Computus computus = getComputus();
        final int year = expectation.getYear();

        final int actualDayOfMarch = computus.getEaster(year);
        final GregorianCalendar actualDate = new GregorianCalendar(year, 3 - 1, 1);
        actualDate.add(Calendar.DAY_OF_YEAR, actualDayOfMarch - 1);

        final Calendar expected = new GregorianCalendar(year, expectation.getMonth() - 1, expectation.getDay());
        assertEquals(actualDate, expected);
    }

    @DataProvider(name = DATA_PROVIDER)
    public Object[][] getDataPoints() {
        return TestDates.provideTestDateData();
    }

}
