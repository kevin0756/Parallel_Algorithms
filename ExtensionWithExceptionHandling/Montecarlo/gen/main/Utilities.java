

package main;


public final class Utilities {
    public static boolean DEBUG = false;

    private static java.lang.String className = "Utilities";

    public static java.lang.String which(java.lang.String executable, java.lang.String pathEnv) {
        java.lang.String executablePath;
        java.lang.String[] paths;
        paths = main.Utilities.splitString(java.lang.System.getProperty("path.separator"), pathEnv);
        for (int i = 0; i < (paths.length); i++) {
            if ((paths[i].length()) > 0) {
                java.io.File pathFile = new java.io.File(paths[i]);
                if (pathFile.isDirectory()) {
                    java.lang.String[] filesInDirectory;
                    filesInDirectory = pathFile.list();
                    for (int j = 0; j < (filesInDirectory.length); j++) {
                        if (main.Utilities.DEBUG) {
                            java.lang.System.out.println(("DBG: Matching " + (filesInDirectory[j])));
                        }
                        if (filesInDirectory[j].equals(executable)) {
                            executablePath = ((paths[i]) + (java.lang.System.getProperty("file.separator"))) + executable;
                            return executablePath;
                        }
                    }
                }else {
                    if (main.Utilities.DEBUG) {
                        java.lang.System.out.println((("DBG: path " + (paths[i])) + " is not a directory!"));
                    }
                }
            }
        }
        executablePath = executable + " not found.";
        return executablePath;
    }

    public static java.lang.String joinString(java.lang.String joinChar, java.lang.String[] stringArray) {
        return main.Utilities.joinString(joinChar, stringArray, 0);
    }

    public static java.lang.String joinString(java.lang.String joinChar, java.lang.String[] stringArray, int index) {
        java.lang.String methodName = "join";
        java.lang.StringBuffer tmpString;
        int nStrings = java.lang.reflect.Array.getLength(stringArray);
        if (nStrings <= index) {
            tmpString = new java.lang.StringBuffer();
        }else {
            tmpString = new java.lang.StringBuffer(stringArray[index]);
            for (int i = index + 1; i < nStrings; i++) {
                tmpString.append(joinChar).append(stringArray[i]);
            }
        }
        return tmpString.toString();
    }

    public static java.lang.String[] splitString(java.lang.String splitChar, java.lang.String arg) {
        java.lang.String methodName = "split";
        java.lang.String[] myArgs;
        int nArgs = 0;
        int foundIndex = 0;
        int fromIndex = 0;
        while ((foundIndex = arg.indexOf(splitChar, fromIndex)) > (-1)) {
            nArgs++;
            fromIndex = foundIndex + 1;
        } 
        if (main.Utilities.DEBUG) {
            java.lang.System.out.println(((((("DBG " + (main.Utilities.className)) + ".") + methodName) + ": ") + nArgs));
        }
        myArgs = new java.lang.String[nArgs + 1];
        nArgs = 0;
        fromIndex = 0;
        while ((foundIndex = arg.indexOf(splitChar, fromIndex)) > (-1)) {
            if (main.Utilities.DEBUG) {
                java.lang.System.out.println(((((((("DBG " + (main.Utilities.className)) + ".") + methodName) + ": ") + fromIndex) + " ") + foundIndex));
            }
            myArgs[nArgs] = arg.substring(fromIndex, foundIndex);
            nArgs++;
            fromIndex = foundIndex + 1;
        } 
        myArgs[nArgs] = arg.substring(fromIndex);
        return myArgs;
    }
}

