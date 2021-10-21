package Dictionary;

public class Word {
    public String word_target; // từ mới
    public String word_explain; // giải nghĩa

    public Word() {
        this.word_target = "word_taget";
        this.word_explain = "word_explain";
    }

    public Word(String newWord, String meaning) {
        this.word_target = newWord;
        this.word_explain = meaning;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public String getWord() {
        String[] meaning = word_explain.split("; ");
        String getMean = "";
        for (int i = 1; i < meaning.length; i++) {
            getMean = getMean + "\n\t\t\t\t\t\t|" + meaning[i];
        }
        return ("\t\t| " + this.word_target + "\t\t|" + meaning[0] + getMean);
    }
}