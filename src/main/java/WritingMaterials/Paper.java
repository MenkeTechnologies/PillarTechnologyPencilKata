
import WritingMaterials.Constants.InvalidIndex;

import java.util.ArrayList;

/**
 * Created by jacobmenke on 7/2/17.
 */
class SentenceDelimitingCharacters {
    public static final Character PERIOD = '.';
    public static final Character QUESTION_MARK = '?';
    public static final Character EXCLAMATION_MARK = '!';
}

public class Paper {
    String contents = "";

    void addCharacter(Character character) {
        this.contents += character;
    }

    void setContent(String contents) {
        this.contents = contents;
    }

    public String readPage() {
        return contents;
    }

    public Paper(String contents) {
        this.contents = contents;
    }

    public Paper() {
    }

    public String readLastSentence() {

        int endingPunctuationIndexAfterText = InvalidIndex.InvalidIndex;

        outer:
        for (int i = (int) contents.chars().count() - 1; i >= 0; i--) {
            switch (contents.charAt(i)) {
                case '.':
                case '?':
                case '!':
                    endingPunctuationIndexAfterText = i;
                    break outer;
                default:
                    if (Character.isWhitespace(contents.charAt(i))) {
                        continue outer;
                    } else {
                        //we have found non sentence characters so this last sentence does not end in punctuation
                        break outer;
                    }
            }
        }

        if (contents.chars().filter(c -> c == SentenceDelimitingCharacters.PERIOD ||
                c == SentenceDelimitingCharacters.EXCLAMATION_MARK || c == SentenceDelimitingCharacters.QUESTION_MARK).count() > 1) {
            if (endingPunctuationIndexAfterText == InvalidIndex.InvalidIndex) {
                //last sentences doesn't end in punctuation
                Integer lastPunctuationIndex = returnNthOccurenceIndex(-1, SentenceDelimitingCharacters.PERIOD, SentenceDelimitingCharacters.EXCLAMATION_MARK,
                        SentenceDelimitingCharacters.QUESTION_MARK);

                return ltrim(contents.substring(lastPunctuationIndex + 1));
            } else {
                //last sentence ends in punctuation
                Integer lastPunctuationIndex = returnNthOccurenceIndex(-2, SentenceDelimitingCharacters.PERIOD, SentenceDelimitingCharacters.EXCLAMATION_MARK,
                        SentenceDelimitingCharacters.QUESTION_MARK);

                return ltrim(contents.substring(lastPunctuationIndex + 1));
            }
        } else {
            //the contents has one or fewer punctuations so just return this sentence as it must be the last sentence
            return ltrim(contents);
        }
    }

    private Integer returnNthOccurenceIndex(Integer nthOccurence, Character... characters) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < contents.toCharArray().length; i++) {

            for (char character : characters) {
                if (contents.charAt(i) == character) {
                    indices.add(i);
                }
            }
        }
        //allow negative indexing
        if (nthOccurence < 0) {
            return indices.get(indices.size() + nthOccurence);
        }
        return indices.get(nthOccurence);
    }

    String ltrim(String s) {
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        return s.substring(i);
    }
}
