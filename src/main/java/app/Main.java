package app;

import service.GradeImporter;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {

            System.out.println(
                    "Використання: java -jar importer.jar <input.txt> <report.txt>"
            );

            return;
        }

        Path inputPath = Path.of(args[0]);
        Path outputPath = Path.of(args[1]);

        GradeImporter importer = new GradeImporter();

        importer.importFile(
                inputPath,
                outputPath
        );
    }
}