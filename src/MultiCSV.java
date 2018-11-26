import java.io.File;
import java.io.FilenameFilter;

public class MultiCSV {
    public MultiCSV(String folder) {
        File dir = new File(folder);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept (File dir, String name) {
                return name.endsWith(".csv");
            }
        };
        String[] children = dir.list(filter);
        for (String child:children) {
            System.out.println(child);
        }
    }
}
