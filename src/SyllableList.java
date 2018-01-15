
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SyllableList extends ArrayList<Syllable> implements Serializable {

	//Can represent the syllables of 1 or more filterWords

	public List<VowelPhoneme> getVowels() {
		List<VowelPhoneme> vowels = new ArrayList<>();
		for (Syllable syllable : this)
			vowels.add(syllable.getVowel());
		return vowels;
	}

	public List<ConsonantPhoneme> getConsonants() {
		List<ConsonantPhoneme> vowels = new ArrayList<>();
		for (Syllable syllable : this)
			vowels.addAll(syllable.getConsonants());
		return vowels;
	}

	@Override
	public String toString() {
		String sb = "";
		int i = 0;
		for (Syllable syllable : this) {
			if (i != 0)
				sb += " ";
			sb += syllable.toString();
			i++;
		}
		return sb;
	}

	public boolean equalsSansStress(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		SyllableList that = (SyllableList) o;
		for (int s = 0; s < this.size(); s++)
			if (!this.get(s).equalsSansStress(that.get(s)))
				return false;
		return true;
	}

}
