

import dao.LogDAO;
import model.Bill;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for the LogDAO class.
 */
public class LogDAOTest {
    private LogDAO logDAO;

    /**
     * Sets up the LogDAO for testing.
     */
    @Before
    public void setUp() {
        logDAO = new LogDAO();
    }

    /**
     * Tests inserting and retrieving a bill.
     */
    @Test
    public void testInsertAndRetrieveBill() {
        Bill bill = new Bill(1, 1, "John Doe", 1, "Laptop", 2, 1999.98);
        logDAO.insertBill(bill);

        List<Bill> bills = logDAO.getAllBills();
        assertTrue(bills.contains(bill));
    }
}
