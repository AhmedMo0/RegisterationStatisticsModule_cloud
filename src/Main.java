import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static String base = "app/data/batch/";
    private static String dbFileName = "dbFile.csv";
    private static Integer studentCnt = 0;
    private static Map<String, Integer> coursesMap = new HashMap<>();

    private static int filesCnt;



    private static void readDB() throws FileNotFoundException {
        Scanner scan = new Scanner(new File( base + dbFileName));

        while(scan.hasNext())
        {
            studentCnt++;

            String[] line = scan.nextLine().split(";");

            String[] courses = line[2].split(",");

            for (String course: courses)
            {
                course = course.toLowerCase();
                Integer cnt = coursesMap.containsKey(course)? coursesMap.get(course): 0;

                coursesMap.put(course, cnt+1);
            }
        }


        scan.close();
    }


    private static Set<String> listFiles(String dir) {
        File file = new File(dir);
        filesCnt =  file.listFiles().length;

        return Stream.of(file.listFiles())
                .filter(f -> f.isFile() && f.getName().contains("verified"))
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) throws FileNotFoundException {

        readDB();
        System.out.printf("Number of users: %d\n", studentCnt);

        for (var entry: coursesMap.entrySet())
        {
            System.out.printf("Number of students registered in %s course: %d\n",
                    entry.getKey(), entry.getValue());
        }

        Integer vFiles = listFiles(base).size();
        filesCnt--;// to exclude dbFile

        System.out.printf("Number of batch files: %d\n", filesCnt);
        System.out.printf("Number of verified batch files: %d\n", vFiles);
    }
}
