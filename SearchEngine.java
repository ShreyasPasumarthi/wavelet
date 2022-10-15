import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

class Handler implements URLHandler {
    ArrayList<String> list = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Make a search!");
        } else if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    list.add(parameters[1]);
                    return String.format("%s has been added!", parameters[1]);
                }
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                ArrayList<String> returnList = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).contains(parameters[1])) {
                        returnList.add(list.get(i));
                    }
                }
                return "The values that match your results are: " + Arrays.toString(returnList.toArray());
            } else {
                if (parameters[0].equals("all")) {
                    return "The list of all entries is: " + Arrays.toString(list.toArray());
                }
            }
        }
        return "404 Not Found!";
    }
}


public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
