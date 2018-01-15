
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordSyllables extends SyllableList implements Serializable {

	//Pertains specifically to syllables in 1 word

	public SyllableList getRhymeTailFromStress() {
		//Find the most stressed syllable
		int highestStress = Integer.MIN_VALUE;
		int i = 0;
		int mostStressedSyllableIndex = -1;
		for (Syllable syllable : this) {
			if (syllable.getNucleus().stress == 1 && highestStress != 1) {
				highestStress = 1;
				mostStressedSyllableIndex = i;
			}
			else if (syllable.getNucleus().stress == 2 && highestStress != 1 && highestStress != 2) {
				highestStress = 2;
				mostStressedSyllableIndex = i;
			}
			i++;
		}

		//Concatenate all phonemes from the most stressed syllables's rhyme onward
		boolean reachedHighestStress = false;
		i = 0;
		SyllableList wordRhyme = new SyllableList();
		for (Syllable syllable : this) {
			if (reachedHighestStress)
				wordRhyme.add(syllable);

			if (i == mostStressedSyllableIndex) {
				reachedHighestStress = true;
				wordRhyme.add(new Syllable(new ArrayList<>(), syllable.getNucleus(), syllable.getCoda()));
			}
			i++;
		}
		return wordRhyme;
	}

	public List<Phoneme> getPhonemes() {
		return this.getPronunciation();
	}

	public List<Phoneme> getPronunciation() {
		List<Phoneme> result = new ArrayList<>();
		for (Syllable s : this)
			result.addAll(s.getAllPhonemes());
		return result;
	}

	public Syllable last() {
		return this.get(this.size() - 1);
	}

}
