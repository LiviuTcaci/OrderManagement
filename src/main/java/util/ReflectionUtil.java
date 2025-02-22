package util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Utility class for reflection operations.
 */
public class ReflectionUtil {
    /**
     * Generates a DefaultTableModel from a list of objects using reflection.
     * @param objects The list of objects to generate the table model from.
     * @return The generated DefaultTableModel.
     */
    public static DefaultTableModel createTableModel(List<?> objects) {
        if (objects == null || objects.isEmpty()) {
            return new DefaultTableModel();
        }

        Class<?> clazz = objects.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        // Create column names from field names
        Vector<String> columnNames = new Vector<>();
        for (Field field : fields) {
            field.setAccessible(true);
            columnNames.add(field.getName());
        }

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Add rows to table model
        for (Object obj : objects) {
            Vector<Object> rowData = new Vector<>();
            for (Field field : fields) {
                try {
                    rowData.add(field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tableModel.addRow(rowData);
        }

        return tableModel;
    }

    /**
     * Retrieves the column names from the given class.
     * @param clazz The class to retrieve the column names from.
     * @return A vector of column names.
     */
    public static Vector<String> getColumnNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Vector<String> columnNames = new Vector<>();
        for (Field field : fields) {
            field.setAccessible(true);
            columnNames.add(field.getName());
        }
        return columnNames;
    }
}
