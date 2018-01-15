import java.io.Serializable;

public abstract class Phoneme implements Serializable {

	public PhonemeEnum phonemeEnum;

	public Phoneme(PhonemeEnum phonemeEnum) {
		this.phonemeEnum = phonemeEnum;
	}

	public boolean isVowel() {
		return phonemeEnum.isVowel();
	}

	public boolean isVoiced() {
		return phonemeEnum.isVoiced();
	}

	public PhonemeEnum getPhonemeEnum() {
		return phonemeEnum;
	}

	public void setPhonemeEnum(PhonemeEnum phonemeEnum) {
		this.phonemeEnum = phonemeEnum;
	}

	@Override
	public String toString() {
		return "" + phonemeEnum.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Phoneme phoneme = (Phoneme) o;

		return getPhonemeEnum() == phoneme.getPhonemeEnum();
	}

	@Override
	public int hashCode() {
		return getPhonemeEnum() != null ? getPhonemeEnum().hashCode() : 0;
	}
}
