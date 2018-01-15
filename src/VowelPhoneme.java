import java.io.Serializable;

public class VowelPhoneme extends Phoneme implements Serializable {

	public int stress;

	public VowelPhoneme(PhonemeEnum phonemeEnum, int stress) {
		super(phonemeEnum);
		this.stress = stress;
		if (!super.phonemeEnum.isVowel())
			super.phonemeEnum = null;
	}

	@Override
	public String toString() {
		return "" + phonemeEnum.toString() + ":" + stress;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		VowelPhoneme that = (VowelPhoneme) o;

		if (stress != that.stress) return false;
		return phonemeEnum == that.phonemeEnum;
	}

	public boolean equalsSansStress(Object o) {
		if (this == o) return true;
		if (o == null || (!(o instanceof Phoneme) && !(o instanceof ConsonantPhoneme) && !(o instanceof VowelPhoneme))) return false;

		VowelPhoneme that = (VowelPhoneme) o;

		return phonemeEnum == that.phonemeEnum;
	}

	@Override
	public int hashCode() {
		int result = phonemeEnum.hashCode();
		result = 31 * result + stress;
		return result;
	}

}
