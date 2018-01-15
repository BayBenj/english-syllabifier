
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Syllable implements Serializable {

	private List<ConsonantPhoneme> onset = new ArrayList<>();
	private VowelPhoneme nucleus;
	private List<ConsonantPhoneme> coda = new ArrayList<>();

	public Syllable() {}

	public Syllable(List<ConsonantPhoneme> onset, VowelPhoneme nucleus, List<ConsonantPhoneme> coda) {
		this.onset = onset;
		this.nucleus = nucleus;
		this.coda = coda;
	}

	public List<Phoneme> getPronunication() {
		List<Phoneme> result = new ArrayList<Phoneme>();
		if (this.hasOnset())
			result.addAll(this.getOnset());
		if (this.hasNucleus())
			result.add(this.getNucleus());
		if (this.hasCoda())
			result.addAll(this.getCoda());
		return result;
	}

	public VowelPhoneme getVowel() {
		return this.getNucleus();
	}

	public List<ConsonantPhoneme> getConsonants() {
		List<ConsonantPhoneme> consonants = new ArrayList<>();
		consonants.addAll(this.getOnset());
		consonants.addAll(this.getCoda());
		return consonants;
	}

	public Pronunciation getRhyme() {
		Pronunciation rhyme = new Pronunciation();
		if (nucleus != null && nucleus.phonemeEnum != null)
			rhyme.add(nucleus);
		if (coda != null && !coda.isEmpty())
			rhyme.addAll(coda);
		return rhyme;
	}

	public Pronunciation getAllPhonemes() {
		Pronunciation all = new Pronunciation();
		if (onset != null && !onset.isEmpty())
			all.addAll(onset);
		if (nucleus != null && nucleus.phonemeEnum != null)
			all.add(nucleus);
		if (coda != null && !coda.isEmpty())
			all.addAll(coda);
		return all;
	}

	public List<ConsonantPhoneme> getOnset() {
		return onset;
	}

	public void setOnset(List<ConsonantPhoneme> onset) {
		this.onset = (List<ConsonantPhoneme>) onset;
	}

	public List<ConsonantPhoneme> getCoda() {
		return coda;
	}

	public void setCoda(List<ConsonantPhoneme> coda) {
		this.coda = (List<ConsonantPhoneme>) coda;
	}

	public void setRhyme(VowelPhoneme nucleus, List<ConsonantPhoneme> coda) {
		this.setNucleus(nucleus);
		this.setCoda(coda);
	}

	public VowelPhoneme getNucleus() {
		return nucleus;
	}

	public void setNucleus(VowelPhoneme nucleus) {
		this.nucleus = nucleus;
	}

	public int getStress() {
		return nucleus.stress;
	}

	public void setStress(int stress) {
		nucleus.stress = stress;
	}

	public boolean hasOnset() {
		if (this.onset != null && !this.onset.isEmpty())
			return true;
		return false;
	}

	public boolean hasNucleus() {
		if (this.nucleus != null)
			return true;
		return false;
	}

	public boolean hasCoda() {
		if (this.coda != null && !this.coda.isEmpty())
			return true;
		return false;
	}

	public List<PhonemeEnum> getPhonemeEnums() {
		List<PhonemeEnum> result = new ArrayList<>();
		for (Phoneme p : this.getAllPhonemes()) {
			result.add(p.getPhonemeEnum());
		}
		return result;
	}

	@Override
	public String toString() {
		return "" +  onset.toString() + nucleus.toString() + coda.toString();
	}

	public String toString(Object instanceOfDesiredResultFormat) {
		//TODO implement this
		return "" + onset.toString() + nucleus.toString() + coda.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Syllable syllable = (Syllable) o;

		if (getOnset() == null && syllable.getOnset() != null) return false;
		if (getOnset() != null && syllable.getOnset() == null) return false;
		if (getNucleus() == null && syllable.getNucleus() != null) return false;
		if (getNucleus() != null && syllable.getNucleus() == null) return false;
		if (getCoda() == null && syllable.getCoda() != null) return false;
		if (getCoda() != null && syllable.getCoda() == null) return false;

		if (getOnset() != null ? !getOnset().equals(syllable.getOnset()) : syllable.getOnset() != null) return false;
		if (getNucleus() != null ? !getNucleus().equals(syllable.getNucleus()) : syllable.getNucleus() != null)
			return false;
		return getCoda() != null ? getCoda().equals(syllable.getCoda()) : syllable.getCoda() == null;
	}

	@Override
	public int hashCode() {
		int result = getOnset() != null ? getOnset().hashCode() : 0;
		result = 31 * result + (getNucleus() != null ? getNucleus().hashCode() : 0);
		result = 31 * result + (getCoda() != null ? getCoda().hashCode() : 0);
		return result;
	}

	public boolean equalsSansStress(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Syllable syllable = (Syllable) o;

		if (getOnset() != null ? !getOnset().equals(syllable.getOnset()) : syllable.getOnset() != null) return false;
		if (getNucleus() != null ? !getNucleus().equalsSansStress(syllable.getNucleus()) : syllable.getNucleus() != null)
			return false;
		return getCoda() != null ? getCoda().equals(syllable.getCoda()) : syllable.getCoda() == null;
	}

}
