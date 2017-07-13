import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by jacobmenke on 7/2/17.
 */
public class Paper {
    String contents = "";

    public void addContent(String contents) {
        this.contents += contents;
    }

    public void addCharacter(Character character) {
        this.contents += character;
    }

    public void eraseAndSetContents(String contents) {
        this.contents = contents;
    }

    public String read() {
        return contents;
    }

    public Paper(String contents) {
        this.contents = contents;
    }

    public Paper() {
    }

    public String readLastSentence(){
        if (contents.chars().filter(c->c=='.').count() > 1){
            Integer indexOfSecondToLastPeriod = returnNthOccurenceIndex('.',-2);
            return ltrim(contents.substring(indexOfSecondToLastPeriod+1));
        } else {
            return ltrim(contents);
        }

    }

    private Integer returnNthOccurenceIndex(Character character, Integer nthOccurence){
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < contents.toCharArray().length; i++) {
            if (contents.charAt(i) == character){
                indices.add(i);
            }
        }
        //allow negative indexing
        if (nthOccurence < 0){
            return indices.get(indices.size()+nthOccurence);
        }
        return indices.get(nthOccurence);
    }

    public String findIndexOfLastSentenceContaining(String searchTerm){
        Pattern.compile("" + searchTerm + "").matcher(contents);
        return "";
    }

    String ltrim(String s){
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))){
         i++;
        }

        return s.substring(i);
    }
}
