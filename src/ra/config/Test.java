package ra.config;
public class Test {
    public static void main(String[] args) {
        String [] tableHeaders = { "Employee Name", "Salary", "Designation","Department"};

        String[][] tableData = {
                { "Mike Kurt", "10000", "Developer", "IT"  },
                { "Steve Musk", "20000", "Lead DevOps", "IT" },
                { "Larry Jobs", "30000", "Java Developer", "IT" },
                { "Elon Peters", "400000", "Manager", "IT" },
                { "Jake Burg", "50000000", "CEO", "IT"  },
        };

//        ASCIITable.getInstance().printTable(tableHeaders, tableData);
    }
}
