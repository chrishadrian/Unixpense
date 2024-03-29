package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// JSON files were cited from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Expenses exp = new Expenses();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyExpenses() {
        try {
            Expenses exp = new Expenses();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyExpenses.json");
            writer.open();
            writer.write(exp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyExpenses.json");
            exp = reader.read();
            assertEquals(0, exp.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralExpenses() {
        try {
            Expenses exp = new Expenses();
            LocalDate expectedDate = LocalDate.of(2021,10,24);
            exp.addExpense(new Expense(expectedDate, "Groceries", 10, ""));
            exp.addExpense(new Expense(expectedDate, "Personal", 32.2, ""));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralExpenses.json");
            writer.open();
            writer.write(exp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralExpenses.json");
            exp = reader.read();
            List<Expense> expenses = exp.getExpenses();

            assertEquals(2, expenses.size());
            checkExpense(expectedDate, "Groceries", 10, "", expenses.get(0));
            checkExpense(expectedDate, "Personal", 32.2, "", expenses.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}