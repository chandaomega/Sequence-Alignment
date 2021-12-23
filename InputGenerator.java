package inputGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputGenerator {

    private String x = null;
    private String y = null;
    private List<Integer> indexX;
    private List<Integer> indexY;

    public InputGenerator(String filePath) throws FileNotFoundException {
        indexX = new ArrayList<>();
        indexY = new ArrayList<>();

        parseInputFromFile(filePath);
    }

    public String getX() {
        return transform(x, indexX);
    }

    public String getY() {
        return transform(y, indexY);
    }

    private void parseInputFromFile(String filePath) throws FileNotFoundException {
            File file = new File(filePath);
            Scanner fileReader = new Scanner(file);

            Pattern pattern = Pattern.compile("^\\d+$");
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                if (x == null) {
                    x = data;
                } else if (y == null && pattern.matcher(data).matches()) {
                    indexX.add(Integer.parseInt(data));
                } else if (y != null && pattern.matcher(data).matches()) {
                    indexY.add(Integer.parseInt(data));
                } else if (y == null){
                    y = data;
                }
            }
            fileReader.close();
    }

    private String transform(String input, List<Integer> indices) {
        StringBuilder sb = new StringBuilder(input);
        for (int i : indices) {
            StringBuilder sbCopy = new StringBuilder(sb);
            sb.insert(i + 1, sbCopy);
        }
        return sb.toString();
    }

}


