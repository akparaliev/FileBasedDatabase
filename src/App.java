import Repository.DbContext;

public class App {
    public static void main(String[] args) throws Exception {
        DbContext database = new DbContext("group.file");
    }
}
