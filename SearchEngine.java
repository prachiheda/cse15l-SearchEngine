import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strs = new ArrayList<>();
   
    

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            if (strs.isEmpty()){
                return String.format("your list is empty");
            }
            else {
                String list = "";
                for (String str:strs){
                    list= list + " " + str; 
                }
                return String.format(list);
            } 
            
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                   strs.add(parameters[1]);
                    return String.format("string %s added to list!", parameters[1]);
                }
            return String.format("Number incremented!");
        } else if (url.getPath().contains("/search")){
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String substring = ""; 
                    for (String str: strs){
                        if (str.contains(parameters[1])){
                            substring = substring + " " + str;
                        }
                    }
                    return String.format(substring);

            
            }
            
        }
            return "404 Not Found!";
    }
    
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
